# cis85b-lab0
package javaapplication10;

/**
 *
 * @author ayla
 */
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private ArrayList<Point> points;

    public DrawPanel() {
        points = new ArrayList<Point>();
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                points.add(new Point(e.getX(), e.getY()));
                repaint();
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int size = 40;
        int count = 0;
        int c1 = 0;
        for (Point point : points)  {
            if (c1 == 0) {
               g2.setColor(Color.red);
               g2.drawOval(point.x, point.y, 2*size, 2*size);                
            } else if (c1 == 1) {
               g2.setColor(Color.blue);
               g2.drawPolygon(new int[] {point.x, point.x+size, point.x+ 2 * size }, new int[] {point.y, point.y+size, point.y }, 3);
            } else {
                g2.setColor(Color.black);
                g2.drawRect(point.x, point.y, 2*size, 2*size);
            }
            
            if (c1 >= 2) {
                c1 = 0;
            } else {
               c1++;  
            } 
                
            g2.setColor(Color.green);            
            if (count >= 1 ) {
                Point p1 = points.get(count);
                Point p2 = points.get(count-1);
                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
            count++;
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.add(new DrawPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 400);
                frame.setVisible(true);
            }
        });
    }

}
