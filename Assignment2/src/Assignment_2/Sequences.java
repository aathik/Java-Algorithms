package Assignment_2;

import java.text.DecimalFormat;
import java.util.Random;

public class Sequences {
	private static final DecimalFormat df = new DecimalFormat("0.00");

	public static int editDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
	 
		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];
	 
		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
	 
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}
	 
		//iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);
	 
				//if last two chars equal
				if (c1 == c2) {
					//update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;
	 
					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}
	 
		return dp[len1][len2];
	}
	
	public static String[] createRandomString (int n, int k) {
     	 
     	 String randomString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvxyz";
     	
     	 String[] values = new String[n];
     	 for (int i=0;i<n;i++) {
     			StringBuilder sb = new StringBuilder(n);
     			for (int j = 0; j < k; j++) {
     			   int index = (int)(randomString.length() * Math.random());
     			   sb.append(randomString.charAt(index));
     			  }
     			String val = sb.toString();	
     			values[i] = val;
     		}
     	return values;
     	 
      }
	
	public static void main(String[] args) {
		int n = 100000;
		long startTime, endTime;
    	double avgTime;
    	int dist, totalDist = 0;
    	System.out.println("Edit Distance for Strings of length 10");
    	String[] a = createRandomString(n, 10);
    	String[] b = createRandomString(n, 10);
    	startTime = System.nanoTime( );
    	for(int i=0;i<n;i++) {
    		dist = editDistance(a[i], b[i]);
    		totalDist = totalDist + dist;
    	}
    	endTime = System.nanoTime( );
        avgTime = (double) (endTime - startTime)/n;
        System.out.println( "Average dist : "+ totalDist/n);
        System.out.println( "Average time : "+ df.format(avgTime) +" ns\n");
        totalDist = 0;
        
        System.out.println("Edit Distance for Strings of length 20");
    	String[] c = createRandomString(n, 20);
    	String[] d = createRandomString(n, 20);
    	startTime = System.nanoTime( );
    	for(int i=0;i<n;i++) {
    		dist = editDistance(c[i], d[i]);
    		totalDist = totalDist + dist;
    	}
    	endTime = System.nanoTime( );
        avgTime = (double) (endTime - startTime)/n;
        System.out.println( "Average dist : "+ totalDist/n);
        System.out.println( "Average time : "+ df.format(avgTime) +" ns\n");
        totalDist = 0;
        
        System.out.println("Edit Distance for Strings of length 50");
    	String[] e = createRandomString(n, 50);
    	String[] f = createRandomString(n, 50);
    	startTime = System.nanoTime( );
    	for(int i=0;i<n;i++) {
    		dist = editDistance(e[i], f[i]);
    		totalDist = totalDist + dist;
    	}
    	endTime = System.nanoTime( );
        avgTime = (double) (endTime - startTime)/n;
        System.out.println( "Average dist : "+ totalDist/n);
        System.out.println( "Average time : "+ df.format(avgTime) +" ns\n");
        totalDist = 0;
        
        System.out.println("Edit Distance for Strings of length 100");
    	String[] g = createRandomString(n, 100);
    	String[] h = createRandomString(n, 100);
    	startTime = System.nanoTime( );
    	for(int i=0;i<n;i++) {
    		dist = editDistance(g[i], h[i]);
    		totalDist = totalDist + dist;
    	}
    	endTime = System.nanoTime( );
        avgTime = (double) (endTime - startTime)/n;
        System.out.println( "Average dist : "+ totalDist/n);
        System.out.println( "Average time : "+ df.format(avgTime) +" ns\n");
        
    	
}//main
}//class