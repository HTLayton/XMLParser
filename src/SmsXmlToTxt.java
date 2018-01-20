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

    private static void deleteTroubleChars(ArrayList<String> copyLines, File xmlCopy) throws IOException{
        //Iterate over each line in the file 
        for(int i = 0; i < copyLines.size(); i++){
            //Skip it if it's empty
            String currentLine = copyLines.get(i);
            if(currentLine.length() == 0){
                continue;
            }
            //While it contains a problem string 
            while(currentLine.contains(";&#")){
                //Get rid of it 
                int firstInd = currentLine.indexOf(";&#");
                currentLine = currentLine.substring(0, firstInd-8) + currentLine.substring(firstInd+9);
            }
            //Copy the corrected line 
            copyLines.set(i, currentLine);
        }
        
        //Write each new line to the xmlCopy file for processing
        BufferedWriter writer = new BufferedWriter(new FileWriter(xmlCopy));
        for(String currLine : copyLines){
            writer.write(currLine + "\n");
        }
        writer.close();
    }

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
            System.err.println("File was not found: ./TestXML.xml");
            return;
        }
        System.out.println("Test");
        
        //Convert file to an ArrayList<String> containing each line
        ArrayList<String> copyLines = (ArrayList<String>)Files.readAllLines(xmlCopy.toPath());
        //Delete troublesome unicode characters 
        deleteTroubleChars(copyLines, xmlCopy);
        
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
