package assignment4_Q1;

import java.util.ArrayList;
import java.util.List;

import assignment4_Q1.In;

/***************************************************************
 *  Compilation:  javac BoyerMoore.java
 *  Execution:    java BoyerMoore pattern text
 *
 *  Reads in two strings, the pattern and the input text, and
 *  searches for the pattern in the input text using the
 *  bad-character rule part of the Boyer-Moore algorithm.
 *  (does not implement the strong good suffix rule)
 *
 *  % java BoyerMoore abracadabra abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad 
 *  pattern:               abracadabra
 *
 *  % java BoyerMoore rab abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad 
 *  pattern:         rab
 *
 *  % java BoyerMoore bcara abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad 
 *  pattern:                                   bcara
 *
 *  % java BoyerMoore rabrabracad abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:                        rabrabracad
 *
 *  % java BoyerMoore abacad abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern: abacad
 *
 ***************************************************************/

public class BoyerMoore {
    private final int R;     // the radix
    private int[] right;     // the bad-character skip array

    private char[] pattern;  // store the pattern as a character array
    private String pat;      // or as a string

    // pattern provided as a string
    public BoyerMoore(String pat) {
        this.R = 256;
        this.pat = pat;

        // position of rightmost occurrence of c in the pattern
        right = new int[R];
        for (int c = 0; c < R; c++)
            right[c] = -1;
        for (int j = 0; j < pat.length(); j++)
            right[pat.charAt(j)] = j;
    }

    // pattern provided as a character array
    public BoyerMoore(char[] pattern, int R) {
        this.R = R;
        this.pattern = new char[pattern.length];
        for (int j = 0; j < pattern.length; j++)
            this.pattern[j] = pattern[j];

        // position of rightmost occurrence of c in the pattern
        right = new int[R];
        for (int c = 0; c < R; c++)
            right[c] = -1;
        for (int j = 0; j < pattern.length; j++)
            right[pattern[j]] = j;
    }

    // return offset of first match; N if no match
    public int search(String txt) {
        int M = pat.length();
        int N = txt.length();
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M-1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i+j)) {
                    skip = Math.max(1, j - right[txt.charAt(i+j)]);
                    break;
                }
            }
            if (skip == 0) return i;    // found
        }
        return N;                       // not found
    }


    // return offset of first match; N if no match
    public int search(char[] text) {
        int M = pattern.length;
        int N = text.length;
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M-1; j >= 0; j--) {
                if (pattern[j] != text[i+j]) {
                    skip = Math.max(1, j - right[text[i+j]]);
                    break;
                }
            }
            if (skip == 0) return i;    // found
        }
        return N;                       // not found
    }

    public static List<Integer> ComputeBoyerMoore(String text, String pattern){
    	List<Integer> posList = new ArrayList<Integer>();
    	int offsetPosition = 0, searchPosition = 0;
		int patternLength = pattern.length();
		String subString;
		BoyerMoore boyermoore1 = new BoyerMoore (pattern);
		
    	while ((offsetPosition <= (text.length() + patternLength - 1))) {
			subString = text.substring(offsetPosition);
			searchPosition = boyermoore1.search(subString);
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
    	StdOut.println("Boyer Moore Algorithm");
    	StdOut.println("Reading Hard disk.txt\n");
		
		In file = new In("Hard disk.txt");
		String text = file.readAll();
		
		String[] patterns = {"hard", "disk", "hard disk", "hard drive", "hard dist", "xltpru"};
		List<Integer> positionList = new ArrayList<Integer>();
		
		long StartTime = System.nanoTime();
		for(int i=0;i<100;i++) {
				for(String pattern: patterns) {
					StdOut.println("Positions of '"+ pattern+ "' :");
					positionList = ComputeBoyerMoore(text, pattern);
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
		
		StdOut.println("CPU Time for Boyer Moore Algorithm: "+ (endTime-StartTime));
 	   
		
		
		

      }
}