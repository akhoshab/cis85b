
package javaapplication11;

/**
 *
 * @author ayla
 */
import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
   
/**
 * Use SAX Parser to display product list.
 */
public class ProductList {
   private String pname;
   private String pnames; // pname short
   public TreeMap<String, Double> pl;
   public TreeMap<String, Double> pls; //products list short 
   
   // Constructor
   public ProductList() {
      try {
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();
        // Create a tree map. 
        pl = new TreeMap<String, Double>();
        pls = new TreeMap<String, Double>(); // short list
         //int count = 0;
         DefaultHandler handler;
         
         handler = new DefaultHandler() {
            boolean bName = false;
            boolean bPrice = false;
            public void startElement(String uri, String localName,String qName, 
                Attributes attributes) throws SAXException {

		//System.out.println("Start Element :" + qName);

		if (qName.equalsIgnoreCase("NAME")) {
			bName = true;
		}

		if (qName.equalsIgnoreCase("PRICE")) {
			bPrice = true;
		}
            }
            
            public void endElement(String uri, String localName,
		String qName) throws SAXException {

		//System.out.println("End Element :" + qName);

            }
     
            @Override
            public void characters(char ch[], int start, int length) throws SAXException {

		if (bName) {      
			bName = false;
			//System.out.println("Name : " + new String(ch, start, length));
                        pname = new String(ch, start, length);
                        pnames = pname.substring(0, 5);
                        pl.put(pname, 0.0);
		        pls.put(pnames.toUpperCase(), 0.0);
		}

                if (bPrice) {
			bPrice = false;
                        double value = Double.parseDouble(new String(ch, start, length));
                        double price = pl.get(pname)+ value;  
                        pl.put(pname, price);  
		        pls.put(pnames.toUpperCase(), price);
                        System.out.println("Name :  " + pnames  + " [" + pname  + "] Price : " + new String(ch, start, length));

                        
		}

            }

         };
         
         saxParser.parse(new File("/home/ashur/NetBeansProjects/JavaApplication11/src/javaapplication11/Products.xml"), handler);
      
      
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   // Entry main method
   public static void main(String args[]) {
      new ProductList();
   }

      
}