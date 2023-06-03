package Assignment_2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class RadixSort
{
	private static final DecimalFormat df = new DecimalFormat("0.00");
    /*
     * Radix sort an array of Strings
     * Assume all are all ASCII
     * Assume all have same length
     */
    public static void radixSortA( String [ ] arr, int stringLen )
    {
        final int BUCKETS = 256;
        
        ArrayList<ArrayList<String>> buckets = new ArrayList<ArrayList<String>>(BUCKETS);
        //ArrayList<String[]> buckets = new ArrayList<String[]>(BUCKETS);
        
        for( int i = 0; i < BUCKETS; i++ )
        	//this.get(index).add(element);
        	//ArrayList<String> element = new ArrayList<String>();
        	buckets.add(new ArrayList<String>());
        	//buckets.set(i, new ArrayList<String>());
            //buckets[i] = new ArrayList<String>();
        
        for( int pos = stringLen - 1; pos >= 0; pos-- )
        {
            for( String s : arr )
                //buckets[ s.charAt( pos ) ].add( s );
            	buckets.get(s.charAt(pos)).add(s);

            int idx = 0;
            for( ArrayList<String> thisBucket : buckets )
            {
                for( String s : thisBucket )
                    arr[ idx++ ] = s;
                
                thisBucket.clear( );
            }
        }
    }
       
    /*
     * Counting radix sort an array of Strings
     * Assume all are all ASCII
     * Assume all have same length
     */
    public static void countingRadixSort( String [ ] arr, int stringLen )
    {
        final int BUCKETS = 256;
        
        int N = arr.length;
        String [ ] buffer = new String[ N ];

        String [ ] in = arr;
        String [ ] out = buffer;
        
        for( int pos = stringLen - 1; pos >= 0; pos-- )
        {
            int[ ] count = new int [ BUCKETS + 1 ];
            
            for( int i = 0; i < N; i++ )
                count[ in[ i ].charAt( pos ) + 1 ]++;

            for( int b = 1; b <= BUCKETS; b++ )
                count[ b ] += count[ b - 1 ];

            for( int i = 0; i < N; i++ )
                out[ count[ in[ i ].charAt( pos ) ]++ ] = in[ i ];
            
              // swap in and out roles
            String [ ] tmp = in;
            in = out;
            out = tmp;
        }
        
           // if odd number of passes, in is buffer, out is arr; so copy back
        if( stringLen % 2 == 1 )
            for( int i = 0; i < arr.length; i++ )
                out[ i ] = in[ i ];
    }
    
    /*
     * Radix sort an array of Strings
     * Assume all are all ASCII
     * Assume all have length bounded by maxLen
     */
    /*
    public static void radixSort( String [ ] arr, int maxLen )
    {
        final int BUCKETS = 256;
        
        ArrayList<String> [ ] wordsByLength = new ArrayList<>[ maxLen + 1 ];
        ArrayList<String> [ ] buckets = new ArrayList<>[ BUCKETS ];
        
        for( int i = 0; i < wordsByLength.length; i++ )
            wordsByLength[ i ] = new ArrayList<>( );
        
        for( int i = 0; i < BUCKETS; i++ )
            buckets[ i ] = new ArrayList<>( );
        
        for( String s : arr )
            wordsByLength[ s.length( ) ].add( s );
       
        int idx = 0;
        for( ArrayList<String> wordList : wordsByLength )
            for( String s : wordList )
                arr[ idx++ ] = s;
        
        int startingIndex = arr.length;    
        for( int pos = maxLen - 1; pos >= 0; pos-- )
        {
            startingIndex -= wordsByLength[ pos + 1 ].size( );
            
            for( int i = startingIndex; i < arr.length; i++ )
                buckets[ arr[ i ].charAt( pos ) ].add( arr[ i ] );
            
            idx = startingIndex;
            for( ArrayList<String> thisBucket : buckets )
            {
                for( String s : thisBucket )
                    arr[ idx++ ] = s;
                
                thisBucket.clear( );
            }
        }
    }*/

    // Print the array
    private static void print( String[] a)
    {
        for( int i = 0; i < a.length; i++ )
        	System.out.print(a[i] + " ");
        System.out.println();
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

    // Do some tests
    // Note: radix sort works only for fixed length strings (radixSortA)
    // For variable length strings, radixSort has to be modified
    public static void main( String [ ] args )
    {
    	long startTime, endTime;
    	double avgTime;
    	int n = 100000;
    	for(int k=4; k<=10; k=k+2) {
    		System.out.println( "Radix sort for String of length "+ k);
            startTime = System.nanoTime( );
    		for(int j=0;j<10;j++) {
    			String[] b = createRandomString(n, k);
                radixSortA( b, k );
                
    		}
    		endTime = System.nanoTime( );
            avgTime = (double) (endTime - startTime)/10;
            System.out.println( "Average time for Radix sort: "+ df.format(avgTime) +" ns\n");
            //print(b);
    		
    	}
    	
    	
    }
    
}
