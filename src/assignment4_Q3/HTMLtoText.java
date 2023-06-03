package assignment4_Q3;

import java.io.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

import assignment4_Q3.HTMLtoText;

import java.net.*;

public class HTMLtoText extends HTMLEditorKit.ParserCallback {
 StringBuffer s;

 public HTMLtoText() {}

 public void parse(Reader in) throws IOException {
   s = new StringBuffer();
   ParserDelegator delegator = new ParserDelegator();
   // the third parameter is TRUE to ignore charset directive
   delegator.parse(in, this, Boolean.TRUE);
 }

 public void handleText(char[] text, int pos) {
   s.append(text);
 }

 public String getText() {
   return s.toString();
 }
 
 private static void CreateFiles(String text, String location) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(location));	// creating file on the provided destination
		writer.write(text);
		writer.close();
		
	}
 private static String generateFileName(File file){
		
		return file.getName().replace("htm", "txt");	// create new name by fetching the old name and switching the extension
	
	}
 
private static String SOURCE = "src\\assignment4_Q3\\W3C Web Pages\\";
private static String DESTINATION = "src\\assignment4_Q3\\HTMLToTextParser_TextFiles\\";

 public static void main (String[] args) {
   try {
	   
	   System.out.println("-----HTML To Text Parser----");
	   System.out.println();
	   System.out.println("Reading HTML pages from W3C Web Pages");
	   File sourceFolder = new File(SOURCE);
	   File[] files = sourceFolder.listFiles();
	   //System.out.println(files);
	   if (files != null) {
			
			for (File file : files) {				
				FileReader fileContents = new FileReader(file);
				HTMLtoText HTparser = new HTMLtoText();									// using HTMLtoText parser
				HTparser.parse(fileContents);												// parse the input file
				fileContents.close();
				
				CreateFiles(HTparser.getText(),  DESTINATION + generateFileName(file));
			}
			
			System.out.println("HTML to Text conversion completed");
		
		} 
		else										
			System.out.println("Empty folder");
	   

   }
   catch (Exception e) {
     e.printStackTrace();
   }
 }
}