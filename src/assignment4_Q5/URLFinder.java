package assignment4_Q5;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLFinder {
	
	private static String SOURCE = "src\\assignment4_Q3\\JsoupParser_TextFiles\\";
	
	private static void FindPatternInFolder(File[] files, Pattern pattern) throws IOException{
		for(File file : files) {	
			List<String> lines = Files.readAllLines(new File(SOURCE, file.getName()).toPath(), Charset.defaultCharset());
			for (String line : lines) { 										
				
				Matcher m = pattern.matcher(line);								

				while (m.find()) {												

					System.out.println("Found '" + m.group(0) + "' at " + m.start(0) + " on " + file.getName());

				}

			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		File sourceFile = new File(SOURCE);		
		File[] files = sourceFile.listFiles();
		
		
		
			
		while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.println("\nFinding Patterns with -");
			System.out.println("1. Links with domain w3.org.\n");
			System.out.println("2. Links that contain folders: e.g., www.w3.org/TR/owl-features/ \n");
			System.out.println("3. Links that contain references in a Web page and may contain folders; for example: www.w3.org/TR/owl-features/#DefiningSimpleClasses \n");
			System.out.println("4. Links with domain .net, .com, .org \n");
			int option = sc.nextInt();
			
			switch(option) {
			case 1:
				String regexA = "(https?|http?)://[a-zA-Z0-9.-]+\\.(w3.org)//?[A-Za-z0-9//]*";
				Pattern patternA = Pattern.compile(regexA);
				FindPatternInFolder(files, patternA);
				break;
				
			case 2:
				String regexB = "(https?|http?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+](/){1}[-a-zA-Z0-9+]";
				Pattern patternB = Pattern.compile(regexB);
				FindPatternInFolder(files, patternB);
				break;
				
			case 3:
				String regexC = "(https?|http?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|](/){1}[#]{1}[-a-zA-Z0-9+&@#/%?=~_|!:,.;]+";
				Pattern patternC = Pattern.compile(regexC);
				FindPatternInFolder(files, patternC);
				break;	
				
			case 4:
				String regexD = "(https?|http?)://[a-zA-Z0-9.-]+\\.(net?|com?|org?)//?[A-Za-z0-9//]*";
				Pattern patternD = Pattern.compile(regexD);
				FindPatternInFolder(files, patternD);
				break;	
			
			case 5:
                break;
                
            default:
                System.out.println("Invalid choice");
				
			}
			
				
			
		}
		
	}

}
