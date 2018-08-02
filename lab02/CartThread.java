/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab02app;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author ayla
 */
public class CartThread extends Thread {
    
    private TreeMap<String, String> barcodes;
    private ArrayList<String> cart; 
    private TreeMap<String, Double> pls;
    private int ThreadIndex;
    //instantiator or constructor 
    public CartThread(int index, ArrayList<String> cart, TreeMap<String, Double> pls, 
            TreeMap<String, String> barcodes)
    { 
        this.ThreadIndex = index;
        this.cart = cart;
        this.pls = pls;
        this.barcodes = barcodes;
//        System.out.println("CartThread " + index);        
    }
    
    public void run() 
    {
        int barlen = 9; // barcode length for one character
        int numChar;
        int index = 0;
        //System.out.println("CartThread " + ThreadIndex + " START \n");
        
        for (String elements: this.cart)    {
            String pstr = ""; //products string
            String barstr = ""; //barcode string 
            Double price_val = 0.0; // price value
            //element length divded by barcode length 9
            numChar = elements.length() / barlen;
            for(int i = 0; i < numChar; i++){
                    index = i * barlen;
                    barstr = elements.substring(index, index + barlen);
                    pstr = pstr + this.barcodes.get(barstr);
            }
            // pls is the products database and it has to be synchronized (protected)
            synchronized(this) {
                price_val = this.pls.get(pstr);
            }
            //main output
            System.out.println("ThreadIndex = " + ThreadIndex + " product = " + pstr + " price =  " + price_val);
        }
        System.out.println("CartThread " + ThreadIndex + " DONE \n");
    }
    
    
}

