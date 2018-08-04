/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab03;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author ayla
 */
public class ConsumerThread extends Thread {

    Socket s;
    private BufferedReader input;
    private PrintWriter output;
    private final JFrame frame;
    private final JTextField dataField = new JTextField(40);
    private final JTextArea messageArea = new JTextArea(8, 60);
    int clientNumber;
    
    public ConsumerThread(int clientNumber)            
    { 
        // get carts list -- TBD
        // go through it and make requests from product list one at a time
        // get the final report from inventory after all carts are processed
        String frameTitle = "Consumer Client No. " + clientNumber;
        int frame_x;
        int frame_y;
        this.clientNumber = clientNumber;
        
        frame = new JFrame(frameTitle);
        frame_x = (clientNumber + 1) * 100; 
        frame_y = (clientNumber + 1) * 100; 
        frame.setLocation(frame_x, frame_y);
        // Layout GUI
        messageArea.setEditable(false);
        frame.getContentPane().add(dataField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");

        // Add Listeners
        dataField.addActionListener((ActionEvent e) -> {
            output.println(dataField.getText());
            String response;
            try {
                response = input.readLine();
                if (response == null || response.equals("")) {
                    System.exit(0);
                }
            } catch (IOException ex) {
                response = "Error: " + ex;
            }
            messageArea.append(response + "\n");
            dataField.selectAll();
        } /**
         * Responds to pressing the enter key in the textfield
         * by sending the contents of the text field to the
         * server and displaying the response from the server
         * in the text area.  If the response is "." we exit
         * the whole application, which closes all sockets,
         * streams and windows. -- explanation taken from LMU CMSI examples 
         */ );
    }    

    /**
     * Implements the connection logic by prompting the end user for
     * the server's IP address, connecting, setting up streams, and
     * consuming the welcome messages from the server.  
     * The server then sends three lines of text to the
     * client immediately after establishing a connection.
     * @throws java.io.IOException
     */
    public void connectToServer() throws IOException {

        // Get the server address from a dialog box.
        String serverAddress = JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Lab3 Program",
            JOptionPane.QUESTION_MESSAGE);

        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, 9090);
        input = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);

        // Consume the initial welcoming messages from the server
        for (int i = 0; i < 3; i++) {
            messageArea.append(input.readLine() + "\n");
        }
    }
    
    @Override
    public void run() 
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        try {
            connectToServer();
        } catch (IOException ex) {
            Logger.getLogger(ConsumerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
