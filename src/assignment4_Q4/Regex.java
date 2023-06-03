package assignment4_Q4;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

	private static String SOURCE = "src\\assignment4_Q3\\JsoupParser_TextFiles\\";

	public static void main( String args[] ) {

		try {
			File sourceFile = new File(SOURCE);		
			File[] files = sourceFile.listFiles();

			String regexPhone = "(\\()?(\\d){3}(\\))?[- ](\\d){3}-(\\d){4}";
			Pattern phonePattern = Pattern.compile(regexPhone);

			String regexEmail = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,4}\\b";
			Pattern emailPattern = Pattern.compile(regexEmail, Pattern.CASE_INSENSITIVE);
			
			Charset charset = StandardCharsets.UTF_8;
		

			System.out.println("Phone numbers with position-\n");
			for(File file : files) {	
				
				List<String> lines = Files.readAllLines(new File(SOURCE, file.getName()).toPath(), charset);
				for (String line : lines) { 										
					
					Matcher m = phonePattern.matcher(line);								

					while (m.find()) {												

						System.out.println("'" + m.group(0) + "' Found on Page - " + file.getName());	

					}

				}
				
			}
			
			System.out.println("\nEmail IDs with position-\n");
			for(File file : files) {	
				
				List<String> lines = Files.readAllLines(new File(SOURCE, file.getName()).toPath(), charset);
				for (String line : lines) { 										
					
					Matcher m = emailPattern.matcher(line);								

					while (m.find()) {												

						System.out.println("'" + m.group(0) + "' Found on Page - " + file.getName());	

					}

				}
				
			}
			

		}catch (Exception e) {
		     e.printStackTrace();
		   }
		// Now create matcher object.

	}
}
