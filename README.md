# XMLParser
Parses XML File from SMS Backup and Restore

Uses the W3 XML DOM Look Here for official tutorial --> https://www.w3schools.com/xml/dom_intro.asp

<b>Current Goals:</b>
 
-Trim down MMS Nodes to exclude any part where ct != "text/plain" --> This can be in every mms even those that are 'text_only="0"'

*This should hopefully make working with and potential loading future xml files faster*

<b>Future Goals:</b>

-figure out how to determine who sent a mms (sms is done via the data "type")

-future testing in sms is the user phone always type="2"?

-stitch together multiple xml files to make a bigger singular file
 OR
 split files into smaller sections

<b>Completed Goals:</b>

-Trim down SMS Nodes to exclude the data: 
"protocol", "date", "subject", "toa", "sc_toa", "service_center", "read", "status", "locked", and "date_sent"


## Maven Usage Guide
To compile the source and build a package for the project, run `mvn package`
Then, to run the main project, use `java -cp target/xml-parser-0.0.1-jar-with-dependencies.jar:. org.HTLayton.SmsXmlToTxt`
on Linux/MacOS, or `java -cp target/xml-parser-0.0.1-jar-with-dependencies.jar;. org.HTLayton.SmsXmlToTxt` on Windows.
