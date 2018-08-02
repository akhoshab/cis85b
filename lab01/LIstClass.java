/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication11;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author ashur
 */
public class LIstClass {
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        Collection c;
        c = new ArrayList();
        System.out.println(c.getClass().getName());
        for (int i=1; i <= 10; i++) {
            c.add( i+"*" + i +" ="+i*i);
        }
        Iterator iter =c.iterator();
        while (iter.hasNext())
           System.out.println(iter.next());
        
        File xmlFile = new File("/home/ashur/NetBeansProjects/JavaApplication11/src/javaapplication11/Products_small.xml");
        InputStream is = new FileInputStream(xmlFile);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(is);
        int count = 0;
        while(reader.hasNext())
        {
            if(reader.hasText())
            {
            count++;
            System.out.println("Line Count = " + count);
            System.out.println(reader.getText());
            }
            reader.next();
        }
        System.out.println("Total Lines = " + count);

    //    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//	DocumentBuilder builder = factory.newDocumentBuilder();
//        Document document = builder.parse("/home/ashur/NetBeansProjects/JavaApplication11/src/javaapplication11/{Products.xml");
//        
//        InputSource input= new InputSource(new FileReader(xmlFile));
//        SAXParserFactory factory=SAXParserFactory.newInstance();
//        SAXParser parser=factory.newSAXParser();
//        
    }
    
}

    //AirSigmetHandler handler=new AirSigmetHandler(airSigmet);
    //XMLReader xmlReader=parser.getXMLReader();
    //xmlReader.setContentHandler(handler);
    //xmlReader.parse(input);
