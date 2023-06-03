package Assignment_1;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.text.DecimalFormat;

// SeparateChaining Hash table class
//
// CONSTRUCTION: an approximate initial size or default of 101
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// void makeEmpty( )      --> Remove all items

/**
 * Separate chaining table implementation of hash tables.
 * Note that all "matching" is based on the equals method.
 * @author Mark Allen Weiss
 */
public class SeparateChainingHashTable<AnyType>
{
	private static final DecimalFormat df = new DecimalFormat("0.00");
    /**
     * Construct the hash table.
     */
    public SeparateChainingHashTable( )
    {
        this( DEFAULT_TABLE_SIZE );
    }

    /**
     * Construct the hash table.
     * @param size approximate table size.
     */
    public SeparateChainingHashTable( int size )
    {
        theLists = new LinkedList[ nextPrime( size ) ];
        for( int i = 0; i < theLists.length; i++ )
            theLists[ i ] = new LinkedList<>( );
    }

    /**
     * Insert into the hash table. If the item is
     * already present, then do nothing.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        List<AnyType> whichList = theLists[ myhash( x ) ];
        if( !whichList.contains( x ) )
        {
            whichList.add( x );

                // Rehash; see Section 5.5
            if( ++currentSize > theLists.length )
                rehash( );
        }
    }

    /**
     * Remove from the hash table.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        List<AnyType> whichList = theLists[ myhash( x ) ];
        if( whichList.contains( x ) )
    {
        whichList.remove( x );
            currentSize--;
    }
    }

    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return true if x isnot found.
     */
    public boolean contains( AnyType x )
    {
        List<AnyType> whichList = theLists[ myhash( x ) ];
        return whichList.contains( x );
    }

    /**
     * Make the hash table logically empty.
     */
    public void makeEmpty( )
    {
        for( int i = 0; i < theLists.length; i++ )
            theLists[ i ].clear( );
        currentSize = 0;    
    }

    /**
     * A hash routine for String objects.
     * @param key the String to hash.
     * @param tableSize the size of the hash table.
     * @return the hash value.
     */
    public static int hash( String key, int tableSize )
    {
        int hashVal = 0;

        for( int i = 0; i < key.length( ); i++ )
            hashVal = 37 * hashVal + key.charAt( i );

        hashVal %= tableSize;
        if( hashVal < 0 )
            hashVal += tableSize;

        return hashVal;
    }

    private void rehash( )
    {
        List<AnyType> [ ]  oldLists = theLists;

            // Create new double-sized, empty table
        theLists = new List[ nextPrime( 2 * theLists.length ) ];
        for( int j = 0; j < theLists.length; j++ )
            theLists[ j ] = new LinkedList<>( );

            // Copy table over
        currentSize = 0;
        for( List<AnyType> list : oldLists )
            for( AnyType item : list )
                insert( item );
    }

    private int myhash( AnyType x )
    {
        int hashVal = x.hashCode( );

        hashVal %= theLists.length;
        if( hashVal < 0 )
            hashVal += theLists.length;

        return hashVal;
    }
    
    private static final int DEFAULT_TABLE_SIZE = 101;

        /** The array of Lists. */
    private List<AnyType> [ ] theLists; 
    private int currentSize;

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }
    
    public static String[] createRandomString (double num) {
   	 
   	 String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
   		
   	 int n = (int)num;
   	 String[] values = new String[n];
   	 for (int i=0;i<n;i++) {
   			StringBuilder sb = new StringBuilder(n);
   			for (int j = 0; j < 10; j++) {
   			   int index = (int)(alphaNumericString.length() * Math.random());
   			   sb.append(alphaNumericString.charAt(index));
   			  }
   			String val = sb.toString();	
   			values[i] = val;
   		}
   	return values;
   	 
    }


        // Simple main
    public static void main( String [ ] args )
    {
        
        
        Random rand = new Random();
        System.out.println("SeparateChainingHashTable: \n");
        
     	 for(int j=1; j<=20; j++) {
     		SeparateChainingHashTable<String> H = new SeparateChainingHashTable<>( );
     		double n = Math.pow(2, j);
     		System.out.println( "For i="+j+":");
     		
     		String[] insertValues = createRandomString(n);
            long startTime = System.nanoTime();
            for(int i=0; i<n; i++) {
           	 H.insert(insertValues[i]);
            }
            long endTime = System.nanoTime();
            double avgTime = (double) (endTime - startTime)/n;
            System.out.println("Average time for each insertion:"+ df.format(avgTime) +" ns");
      
            
            startTime = System.nanoTime();
            for( int i = 1; i < n; i+=2 )
            {
            	int del = rand.nextInt(insertValues.length);
        		String val = insertValues[del];
                if( H.contains( val ) ) {
                    //System.out.println( "found"+ i + ":" + insertValues[i] );
                	H.remove( val );
                }
            }
            endTime = System.nanoTime();
            avgTime = (double) (endTime - startTime)/n;
            System.out.println("Average time for each search and deletion:"+ df.format(avgTime) +" ns\n");
           
     		 
     	 }
     	System.out.println( "Finished");
        
        
    }

}