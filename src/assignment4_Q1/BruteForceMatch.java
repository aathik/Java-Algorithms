package assignment4_Q1;

import java.util.ArrayList;
import java.util.List;

/***************************************************************
*
*  Compilation:  javac Brtue.java
*  Execution:    java Brute pattern text
*
*  Reads in two strings, the pattern and the input text, and
*  searches for the pattern in the input text using brute force.
*
*  % java Brute abracadabra abacadabrabracabracadabrabrabracad
*  text:    abacadabrabracabracadabrabrabracad 
*  pattern:               abracadabra          
*
*  % java Brute rab abacadabrabracabracadabrabrabracad
*  text:    abacadabrabracabracadabrabrabracad 
*  pattern:         rab                         
* 
*  % java Brute rabrabracad abacadabrabracabracadabrabrabracad
*  text:    abacadabrabracabracadabrabrabracad
*  pattern:                        rabrabracad

*
*  % java Brute bcara abacadabrabracabracadabrabrabracad
*  text:    abacadabrabracabracadabrabrabracad 
*  pattern:                                   bcara
* 
*  % java Brute abacad abacadabrabracabracadabrabrabracad
*  text:    abacadabrabracabracadabrabrabracad
*  pattern: abacad
*
***************************************************************/

public class BruteForceMatch {

  /***************************************************************************
   *  String versions
   ***************************************************************************/

   // return offset of first match or N if no match
   public static int search1(String pat, String txt) {
       int M = pat.length();
       int N = txt.length();

       for (int i = 0; i <= N - M; i++) {
           int j;
           for (j = 0; j < M; j++) {
               if (txt.charAt(i+j) != pat.charAt(j))
                   break;
           }
           if (j == M) return i;            // found at offset i
       }
       return N;                            // not found
   }

   // return offset of first match or N if no match
   public static int search2(String pat, String txt) {
       int M = pat.length();
       int N = txt.length();
       int i, j;
       for (i = 0, j = 0; i < N && j < M; i++) {
           if (txt.charAt(i) == pat.charAt(j)) j++;
           else { i -= j; j = 0;  }
       }
       if (j == M) return i - M;    // found
       else        return N;        // not found
   }


  /***************************************************************************
   *  char[] array versions
   ***************************************************************************/

   // return offset of first match or N if no match
   public static int search1(char[] pattern, char[] text) {
       int M = pattern.length;
       int N = text.length;

       for (int i = 0; i <= N - M; i++) {
           int j;
           for (j = 0; j < M; j++) {
               if (text[i+j] != pattern[j])
                   break;
           }
           if (j == M) return i;            // found at offset i
       }
       return N;                            // not found
   }

   // return offset of first match or N if no match
   public static int search2(char[] pattern, char[] text) { 
       int M = pattern.length;
       int N = text.length;
       int i, j;
       for (i = 0, j = 0; i < N && j < M; i++) {
           if (text[i] == pattern[j]) j++;
           else { i -= j; j = 0;  }
       }
       if (j == M) return i - M;    // found
       else        return N;        // not found
   } 
   
   public static List<Integer> ComputeBruteForce(String text, String pattern){
   	List<Integer> posList = new ArrayList<Integer>();
   	int offsetPosition = 0, searchPosition = 0;
	int patternLength = pattern.length();
	String subString;
	
		
   	while ((offsetPosition <= (text.length() + patternLength - 1))) {
			subString = text.substring(offsetPosition);
			searchPosition = BruteForceMatch.search1(pattern, subString);
			if (searchPosition == subString.length())
				break;
			posList.add(offsetPosition + searchPosition);
			offsetPosition += searchPosition + patternLength;
		}
		
		if (posList.size() != 0)
			return posList;
		else
			posList.add(searchPosition);
		
		return posList;
   }


   // test client
   public static void main(String[] args) {
	   
	   StdOut.println("Brute Force Algorithm");
   		StdOut.println("Reading Hard disk.txt\n");
		
		In file = new In("Hard disk.txt");
		String text = file.readAll();
		
		String[] patterns = {"hard", "disk", "hard disk", "hard drive", "hard dist", "xltpru"};
		List<Integer> positionList = new ArrayList<Integer>();
		
		long StartTime = System.nanoTime();
		for(int i=0;i<100;i++) {
				for(String pattern: patterns) {
					StdOut.println("Positions of '"+ pattern+ "' :");
					positionList = ComputeBruteForce(text, pattern);
					if(positionList.size() == 1 && positionList.get(0) == text.length()) {
						StdOut.println(" Pattern not found");
						
					}
					else {
						StdOut.println(positionList);
						
					}
					StdOut.println();			
					
				}
		}
		long endTime = System.nanoTime();
		
		StdOut.println("CPU Time for Brute Force Algorithm: "+ (endTime-StartTime));
       
   }
}