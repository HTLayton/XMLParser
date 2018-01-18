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
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;
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

        //Use DOM model for parsing through XML File
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(xmlCopy.toString());
        System.out.println("Test");


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
