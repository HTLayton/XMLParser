/**
 * Created by Hunter on 11/12/2017.
 */

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

public class SmsXmlToTxt {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        //Get Specific Original File
        File xmlIn = new File("./TestXML.xml");
        System.out.println("Test");

        //Create a Copy file to manipulate
        File xmlCopy = new File("./TrimmedXML.xml");
        xmlCopy.createNewFile();
		//Catch instance of no test input file
        try{
			Files.copy(xmlIn.toPath(), xmlCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		catch(NoSuchFileException exep){
			System.err.println("The file was not found: ./TestXML.xml");	
			return;
		}
        System.out.println("Test");
       
        //Convert file to an ArrayList<String> containing each line
        ArrayList<String> copyLines = (ArrayList<String>)Files.readAllLines(xmlCopy.toPath());
        //Iterate over each line in the file 
        for(int i = 0; i < copyLines.size(); i++){
            //Skip it if it's empty
            String currentLine = copyLines.get(i);
            if(currentLine.length() == 0){
                continue;
            }
            String temp = null;
            //If the string contains problematic unicode
            if(currentLine.contains("&#")){
                temp = "";
                //Split by spaces, trimming leading whitespace 
                String[] split = currentLine.trim().split(" ");
                //Check for bad unicode in each token
                for(int j = 0; j < split.length; j++){
                    String tok = split[j];
                    if(tok.charAt(0) == '&' && tok.charAt(1) == '#'){
                        //If there's a quote, add that in and get rid of extra space
                        if(tok.contains("\"")){
                            temp = temp.substring(0, temp.length() - 1).concat("\" "); 
                        }
                    }
                    //Otherwise, concatenate this token with a space (since we got rid of them)
                    else{
                        temp = temp.concat(tok) + " ";
                    }
                }
            }
            //If we ran into a problematic sequence, copy edited line
            if(temp != null){
                copyLines.set(i, "  " + temp);
            }
        }
        
        //Write each new line to the xmlCopy file for processing
        BufferedWriter writer = new BufferedWriter(new FileWriter(xmlCopy));
        for(String currLine : copyLines){
            writer.write(currLine + "\n");
        }
        writer.close();
        
        //Use DOM model for parsing through XML File
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(xmlCopy.toString());

        //create parent node
        Node smses = doc.getElementsByTagName("smses").item(0);

        //Make a nodelist based on parent
        NodeList list = smses.getChildNodes();

        //iterate through Node list and print by name
        for(int i = 0; i < list.getLength(); i++){
            Node node = list.item(i);
            System.out.println("Node "+ i + " is " + node.getNodeName());
            if(node.getAttributes() != null) {
                System.out.println(node.getAttributes().getNamedItem("body"));
            }
            System.out.println();
            /**if("sms".equals(node.getNodeName()))
                System.out.println("Node " + (i) + " is an SMS Node");
            else if("mms".equals(node.getNodeName()))
                System.out.println("Node " + (i) + " is an MMS Node");
            else
                System.out.println("Node " + (i) + " is an Unknown Node Type");
             **/
        }

    }

}
