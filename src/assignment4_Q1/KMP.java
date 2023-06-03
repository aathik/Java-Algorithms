package assignment4_Q1;

import java.util.ArrayList;
import java.util.List;

import assignment4_Q1.KMP;

/***************************************************************
*
*  Compilation:  javac KMP.java
*  Execution:    java KMP pattern text
*
*  Reads in two strings, the pattern and the input text, and
*  searches for the pattern in the input text using the
*  KMP algorithm.
*X
*  % java KMP abracadabra abacadabrabracabracadabrabrabracad
*  text:    abacadabrabracabracadabrabrabracad 
*  pattern:               abracadabra          
*
*  % java KMP rab abacadabrabracabracadabrabrabracad
*  text:    abacadabrabracabracadabrabrabracad 
*  pattern:         rab
*
*  % java KMP bcara abacadabrabracabracadabrabrabracad
*  text:    abacadabrabracabracadabrabrabracad 
*  pattern:                                   bcara
*
*  % java KMP rabrabracad abacadabrabracabracadabrabrabracad 
*  text:    abacadabrabracabracadabrabrabracad
*  pattern:                        rabrabracad
*
*  % java KMP abacad abacadabrabracabracadabrabrabracad
*  text:    abacadabrabracabracadabrabrabracad
*  pattern: abacad
*
***************************************************************/

public class KMP {
   private final int R;       // the radix
   private int[][] dfa;       // the KMP automoton

   private char[] pattern;    // either the character array for the pattern
   private String pat;        // or the pattern string

   // create the DFA from a String
   public KMP(String pat) {
       this.R = 256;
       this.pat = pat;

       // build DFA from pattern
       int M = pat.length();
       dfa = new int[R][M]; 
       dfa[pat.charAt(0)][0] = 1; 
       for (int X = 0, j = 1; j < M; j++) {
           for (int c = 0; c < R; c++) 
               dfa[c][j] = dfa[c][X];     // Copy mismatch cases. 
           dfa[pat.charAt(j)][j] = j+1;   // Set match case. 
           X = dfa[pat.charAt(j)][X];     // Update restart state. 
       } 
   } 

   // create the DFA from a character array over R-character alphabet
   public KMP(char[] pattern, int R) {
       this.R = R;
       this.pattern = new char[pattern.length];
       for (int j = 0; j < pattern.length; j++)
           this.pattern[j] = pattern[j];

       // build DFA from pattern
       int M = pattern.length;
       dfa = new int[R][M]; 
       dfa[pattern[0]][0] = 1; 
       for (int X = 0, j = 1; j < M; j++) {
           for (int c = 0; c < R; c++) 
               dfa[c][j] = dfa[c][X];     // Copy mismatch cases. 
           dfa[pattern[j]][j] = j+1;      // Set match case. 
           X = dfa[pattern[j]][X];        // Update restart state. 
       } 
   } 

   // return offset of first match; N if no match
   public int search(String txt) {

       // simulate operation of DFA on text
       int M = pat.length();
       int N = txt.length();
       int i, j;
       for (i = 0, j = 0; i < N && j < M; i++) {
           j = dfa[txt.charAt(i)][j];
       }
       if (j == M) return i - M;    // found
       return N;                    // not found
   }


   // return offset of first match; N if no match
   public int search(char[] text) {

       // simulate operation of DFA on text
       int M = pattern.length;
       int N = text.length;
       int i, j;
       for (i = 0, j = 0; i < N && j < M; i++) {
           j = dfa[text[i]][j];
       }
       if (j == M) return i - M;    // found
       return N;                    // not found
   }
   
   public static List<Integer> ComputeKMP(String text, String pattern){
	   	List<Integer> posList = new ArrayList<Integer>();
	   	int offsetPosition = 0, searchPosition = 0;
		int patternLength = pattern.length();
		String subString;
		
		KMP kmp = new KMP(pattern);
			
	   	while ((offsetPosition <= (text.length() + patternLength - 1))) {
				subString = text.substring(offsetPosition);
				searchPosition = kmp.search(subString);
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

	   StdOut.println("KMP Algorithm");
  		StdOut.println("Reading Hard disk.txt\n");
		
		In file = new In("Hard disk.txt");
		String text = file.readAll();
		
		String[] patterns = {"hard", "disk", "hard disk", "hard drive", "hard dist", "xltpru"};
		List<Integer> positionList = new ArrayList<Integer>();
		
		long StartTime = System.nanoTime();
		for(int i=0;i<100;i++) {
				for(String pattern: patterns) {
					StdOut.println("Positions of '"+ pattern+ "' :");
					positionList = ComputeKMP(text, pattern);
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
		
		StdOut.println("CPU Time for KMP Algorithm: "+ (endTime-StartTime));
      
		
   }
}
