package assignment4_Q3;


import java.io.*;

import org.jsoup.*;



public class HTMLJsoup {
	
	
private static void CreateFiles(String text, String location) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(location));	// creating file on the provided destination
		writer.write(text);
		writer.close();
		
	}
 private static String generateFileName(File file){
		
		return file.getName().replace("htm", "txt");	// create new name by fetching the old name and switching the extension
	
	}
 
private static String SOURCE = "src\\assignment4_Q3\\W3C Web Pages\\";
private static String DESTINATION = "src\\assignment4_Q3\\JsoupParser_TextFiles\\";

	public static void main(String[] args) throws IOException {
		
		
		try {
			   
			   System.out.println("-----JSOUP Text Parser----");
			   System.out.println();
			   System.out.println("Reading HTML pages from W3C Web Pages");
			   File sourceFolder = new File(SOURCE);
			   File[] files = sourceFolder.listFiles();
			   //System.out.println(files);
			   if (files != null) {
					
					for (File file : files) {				
						FileReader fileContents = new FileReader(file);
						org.jsoup.nodes.Document doc = Jsoup.parse(file, "UTF-8", "");					// parse the input file

						CreateFiles(doc.text(), DESTINATION + generateFileName(file));
					}
					
					System.out.println("HTML to Text conversion completed");
				
				} 
				else										
					System.out.println("Empty folder");
			   

		   }
		   catch (Exception e) {
		     e.printStackTrace();
		   }
//		org.jsoup.nodes.Document doc = Jsoup.connect("http://luisrueda.cs.uwindsor.ca/researchint/transcriptomics").get();
//		//org.jsoup.nodes.Document doc = Jsoup.connect("http://blogs.windsorstar.com/news/woman-to-be-charged-with-child-abandonment-after-infants-found-in-apartment-stairwell").get();
//		String text = doc.text();
//		System.out.println(text);
//		PrintWriter out = new PrintWriter("jsoupText.txt");
//		out.println(text);
//		out.close();
//		String html = doc.html();
//		out = new PrintWriter("jsoupHTML.html");
//		out.println(html);
//		out.close();
//    	String program = "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe jsoupHTML.html";
//    	Process p = Runtime.getRuntime().exec(program);
	}
}
