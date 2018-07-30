
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
   
   // Constructor
   public ProductList() {
      try {
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();
        // Create a tree map. 
        TreeMap<String, Double> pl = new TreeMap<String, Double>();
         DefaultHandler handler;
         
         handler = new DefaultHandler() {
            boolean bName = false;
            boolean bPrice = false;
            public void startElement(String uri, String localName,String qName, 
                Attributes attributes) throws SAXException {

		System.out.println("Start Element :" + qName);

		if (qName.equalsIgnoreCase("NAME")) {
			bName = true;
		}

		if (qName.equalsIgnoreCase("PRICE")) {
			bPrice = true;
		}
            }
            
            public void endElement(String uri, String localName,
		String qName) throws SAXException {

		System.out.println("End Element :" + qName);

            }
     
            @Override
            public void characters(char ch[], int start, int length) throws SAXException {

		if (bName) {      
			bName = false;
			System.out.println("Name : " + new String(ch, start, length));
                        pname = new String(ch, start, length);
                        pl.put(pname, 0.0);
		}

                if (bPrice) {
			bPrice = false;
                        double value = Double.parseDouble(new String(ch, start, length));
                        double price = pl.get(pname)+ value;  
                        pl.put(pname, price);  
                        System.out.println("Name :  " + pname  +"   Price : " + new String(ch, start, length));

                        
		}

            }

         };
         
         saxParser.parse(new File("/home/ashur/NetBeansProjects/JavaApplication11/src/javaapplication11/Products_small.xml"), handler);
      
      
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   // Entry main method
   public static void main(String args[]) {
      new ProductList();
   }

      
}
