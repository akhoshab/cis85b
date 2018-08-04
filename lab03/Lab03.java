/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab03;

import java.io.IOException;

/**
 *
 * @author ayla
 */
public class Lab03 {
    static ProductThread pt;
    static ConsumerThread ct1;
    static ConsumerThread ct2;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException 
    {
        pt = new ProductThread();
        pt.start();
        ct1 = new ConsumerThread(1);
        ct1.start();
        ct2 = new ConsumerThread(2);
        ct2.start();
    }
}
