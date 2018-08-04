/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ayla
 */
public class ProductThread extends Thread {
    String csvFile = "/home/aylabox/NetBeansProjects/lab03/src/lab03/Inventory.csv";
    ArrayList<ArrayList<String>>inventoryList = new ArrayList<>();
    CSVReader csvReader;
    //this tree map is pair of values .. product name and price 
    TreeMap<String, Double> productPrice = new TreeMap<>();    
    //this tree map is another pair of values ... product name and inventory number 
    TreeMap<String, Integer> productInventory = new TreeMap<>();    
    ServerSocket listener;
    int clientNumber = 0;
            
    public ProductThread()
    //the controrucor does two things...
        //1) parsed the cvs file and stored the data in the 2 tree maps 
        //2) initialized servers listener socket 
    { 
        try {            
            csvReader = new CSVReader(csvFile, inventoryList);
            for (ArrayList<String> lists: inventoryList) {
                // there was garbage data at the top of the file ... this is error checking 
                if (lists.size() == 3) {
                    productPrice.put(lists.get(0), Double.parseDouble(lists.get(1)));
                    productInventory.put(lists.get(0), Integer.parseInt(lists.get(2)));
                }
            }
            //this port has to be bigger than 1000 ... can use 1234
            //this one will listen to port 9090
            listener = new ServerSocket(9090);           
            
        } catch (IOException ex) {
            Logger.getLogger(ProductThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() 
    {
        try {
            while (true) {
                //each time it receives a message it spwans a request thread ... thread in a thread 
                clientNumber++;
                RequestThread rt = new RequestThread(listener.accept(), 
                    clientNumber, productPrice, productInventory);
                rt.start();

            }
            
        } catch (IOException ex) {
            Logger.getLogger(ProductThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                listener.close();
            } catch (IOException ex) {
                Logger.getLogger(ProductThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }

    // A private thread to handle request on a particular cart    
    private static class RequestThread extends Thread {    
        private final Socket socket;
        private final int clientNumber;
        //getting data from socket
        private BufferedReader input;
        //writing to the socket
        private PrintWriter output;
        private final TreeMap<String, Double> productPrice;
        private final TreeMap<String, Integer> productInventory;
        
        public RequestThread(Socket socket, int clientNumber, 
                TreeMap<String, Double> productPrice, 
                TreeMap<String, Integer> productInventory) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            this.productPrice = productPrice;
            this.productInventory = productInventory;
            System.out.println("Client Request No. = " + clientNumber + " at " + socket);
        }

        @Override
        public void run() {
            try {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream(), true);
                
                // Send a welcome message to the client.
                output.println("Hello, you are client #" + clientNumber + ".");
                output.println("Enter a product name (or a period to quit)\n");

                // Get messages from the client, line by line; 
                while (true) {
                    //local variables
                    String inputMsg;
                    String outputMsg;                    
                    String productName;
                    Double priceNumber;
                    Integer inventoryNumber;
                    
                    inputMsg = input.readLine();
                    if (inputMsg == null || inputMsg.equals(".")) {
                        break;
                    }
                    
                    if (inputMsg.equals("Inventory")) {
                        // send out the inventory for each product to client
                        synchronized(this) {
                            Integer rowcount = 0; 
                            for(Map.Entry<String,Integer> entry : productInventory.entrySet()) {
                                rowcount++;
                                String key;
                                key = entry.getKey();
                                Integer value = entry.getValue();
                                if (value < 100) {
                                    outputMsg = "Product[" + rowcount.toString() + "]: " + key + " Inventory(< 100) = " + value;
                                    System.out.println(outputMsg);                               
                                    output.println(outputMsg);                                                       
                                }
                            }                       
                        }
                    } else {
                        productName = inputMsg;
                        synchronized(this) {
                            //input is product name then we get the info from the treemaps
                            priceNumber = productPrice.get(productName);
                            inventoryNumber = productInventory.get(productName);
                            if (priceNumber == null) {
                                outputMsg = "Product: " + productName + ": Not Available";                                            
                            } else if (inventoryNumber <= 0 || inventoryNumber == null) {
                                outputMsg = "Product: " + productName + ": Price = " + 
                                    priceNumber + ": Out of Inventory";                    
                            } else {
                                outputMsg = "Product: " + productName + ", Price = " + 
                                     priceNumber + ", Inventory Number = " + inventoryNumber;                        
                                inventoryNumber--;
                                productInventory.put(productName, inventoryNumber);
                            }                        
                        }
                        output.println(outputMsg);                        
                    }
                }
            } catch (IOException e) {
                System.out.println("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Couldn't close a socket, what's going on?");
                }
                System.out.println("Connection with client# " + clientNumber + " closed");
            }
        }        
    }
}
