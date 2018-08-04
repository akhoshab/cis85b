/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab03;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author ayla
 */
public final class CSVReader 
{    
    //csvFile is input to this class and inventoryList is the output for this class
    private static String csvFile;
    private static ArrayList<ArrayList<String>> inventoryList; 
    
    CSVReader(String csvFile, ArrayList<ArrayList<String>> inventoryList) throws IOException 
    {
        CSVReader.csvFile = csvFile;
        CSVReader.inventoryList = inventoryList;
        //System.out.println("CSVReader()");
        CSVParser();
    }

    public void CSVParser() 
    {        
        BufferedReader br = null;
        String line;
        String cvsSplitBy = ",";
        
        System.out.println("CVSUpdate(): " + csvFile);
        
        try {
            br = new BufferedReader(new FileReader(csvFile));
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                //for one item of inventory .. il array is the container 
                ArrayList<String> il = new ArrayList<>();
                // use comma as separator
                String[] inventory = line.split(cvsSplitBy, 3);
                //System.out.println("lineCount = " + lineCount + " Line = " + line);
                //System.out.println("Inventory line lenth = " + inventory.length);
                if (inventory.length >= 3) {
                    System.out.println("Inventory = " + inventory[0] + 
                         ", " + inventory[1] + ", " + inventory[2]);                        
                    il.add(inventory[0]);
                    il.add(inventory[1]);
                    il.add(inventory[2]);
                    //container for container
                    inventoryList.add(il);
                    lineCount++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("file name not found : "+ CSVReader.csvFile);
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
    }
    
    public static void main(String[] args) throws IOException 
    {        
        //System.out.println("Main()");
    }

}

  

    