/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication11;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author ayla
 */
public class JavaApplication11 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TreeMap<String, String> barcodes = new TreeMap<String, String>();
        ProductList pt = new ProductList();
        CartsList ct = new CartsList();
        initBarCodes(barcodes);
        
        int barlen = 9;
        int numChar;
        int index = 0;
        for (ArrayList<String> lists: ct.cl) {
            for (String elements: lists)    {
                String pstr = ""; //products string
                String barstr = ""; //barcode string 
                Double price_val = 0.0; // price value
                //element len divded by bar len
                numChar = elements.length() / barlen;
                for( int i = 0; i < numChar; i++){
                    index = i * barlen;
                    barstr = elements.substring(index, index + barlen);
                    pstr = pstr + barcodes.get(barstr);
                }
                price_val = (pt.pls).get(pstr);
                System.out.println("product = " + pstr + " price =  " + price_val);
            }
        } 
    }
    
    
    public static void initBarCodes(TreeMap barcodes) {
        barcodes.put("011000100", " ");
        barcodes.put("010000101", "-");
        barcodes.put("010001010", "+");
        barcodes.put("010101000", "$");
        barcodes.put("000101010", "%");
        barcodes.put("010010100", "*");
        barcodes.put("110000100", ".");
        barcodes.put("010100010", "/");
        barcodes.put("000110100", "0");
        barcodes.put("100100001", "1");
        barcodes.put("001100001", "2");
        barcodes.put("101100000", "3");
        barcodes.put("000110001", "4");
        barcodes.put("100110000", "5");
        barcodes.put("001110000", "6");
        barcodes.put("000100101", "7");
        barcodes.put("100100100", "8");
        barcodes.put("001100100", "9");
        barcodes.put("100001001", "A");
        barcodes.put("001001001", "B");
        barcodes.put("101001000", "C");
        barcodes.put("000011001", "D");
        barcodes.put("100011000", "E");
        barcodes.put("001011000", "F");
        barcodes.put("000001101", "G");
        barcodes.put("100001100", "H");
        barcodes.put("001001100", "I");
        barcodes.put("000011100", "J");
        barcodes.put("100000011", "K");
        barcodes.put("001000011", "L");
        barcodes.put("101000010", "M");
        barcodes.put("000010011", "N");
        barcodes.put("100010010", "O");
        barcodes.put("001010010", "P");
        barcodes.put("000000111", "Q");
        barcodes.put("100000110", "R");
        barcodes.put("001000110", "S");
        barcodes.put("000010110", "T");
        barcodes.put("110000001", "U");
        barcodes.put("011000001", "V");
        barcodes.put("111000000", "W");
        barcodes.put("010010001", "X");
        barcodes.put("110010000", "Y");
        barcodes.put("011010000", "Z");
    }
  
}
