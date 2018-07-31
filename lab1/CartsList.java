/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication11;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author ayla
 */
   
/**
 * Use SAX Parser to display product list.
 */
public class CartsList {
    
   private String pname;
   public ArrayList<ArrayList<String>> cl; 
   
   // Constructor
   public CartsList() {
      try {
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();
        // Create a two dimensional array. 
        cl = new ArrayList<ArrayList<String>>();
        DefaultHandler handler;
        int numCarts = cl.size();
	System.out.println("START: numCarts = " + numCarts);
         
        handler = new DefaultHandler() {
            boolean bCart = false;
            boolean bItem = false;
            int cart_count = 0;
            public void startElement(String uri, String localName,String qName, 
                Attributes attributes) throws SAXException {

//		System.out.println("Start Element :" + qName);
		if (qName.equalsIgnoreCase("CART")) {
			bCart = true;
//                        String cart = "Cart = " + Integer.toString(cart_count);
//                        System.out.println("==>" + cart);
                        // add the cart entry                         
                        cl.add(new ArrayList<String>());                                                
                        cart_count++;
		}

		if (qName.equalsIgnoreCase("ITEM")) {
			bItem = true;
		}
            }
            
            public void endElement(String uri, String localName,
		String qName) throws SAXException {

//		System.out.println("End Element :" + qName);
		if (qName.equalsIgnoreCase("CART")) {
			bCart = false;
		}

            }
     
            @Override
            public void characters(char ch[], int start, int length) throws SAXException {

                if (bItem) {
			bItem = false;
//			System.out.println("Cart = " + cart_count + " Item = " + new String(ch, start, length));
                        int numCarts = cl.size();
                        int cartIndex = numCarts - 1;
//			System.out.println("numCarts = " + numCarts);
                        cl.get(cartIndex).add(new String(ch, start, length));
		}

            }

         };
         
        saxParser.parse(new File("/home/ashur/NetBeansProjects/JavaApplication11/src/javaapplication11/Carts.xml"), handler);
        numCarts = cl.size();
      	System.out.println("END: numCarts = " + numCarts);

        //int cart = 0;
        //for (ArrayList<String> lists: cl) {
        //    System.out.println("cart = " + cart);
        //    cart++;
        //    for (String elements: lists)    {
        //        System.out.println("item = " + elements);
        //    }
        //}      
      
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   // Entry main method
   public static void main(String args[]) {
      new CartsList();
   }

      
}