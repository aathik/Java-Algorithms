package Assignment_1;

import java.util.Random;
import java.text.DecimalFormat;
//Cuckoo Hash table class
//
//CONSTRUCTION: a hashing function family and
//            an approximate initial size or default of 101
//
//******************PUBLIC OPERATIONS*********************
//bool insert( x )       --> Insert x
//bool remove( x )       --> Remove x
//bool contains( x )     --> Return true if x is present
//void makeEmpty( )      --> Remove all items
//int  size( )           --> Return number of items


/**
* Cuckoo hash table implementation of hash tables.
* @author Mark Allen Weiss
*/


public class CuckooHashTable<AnyType>
{
	private static final DecimalFormat df = new DecimalFormat("0.00");
 /**
  * Construct the hash table.
  * @param hf the hash family
  */
 public CuckooHashTable( HashFamily<? super AnyType> hf )
 {
     this( hf, DEFAULT_TABLE_SIZE );
 }

 /**
  * Construct the hash table.
  * @param hf the hash family
  * @param size the approximate initial size.
  */
 public CuckooHashTable( HashFamily<? super AnyType> hf, int size )
 {
     allocateArray( nextPrime( size ) );
     doClear( );
     hashFunctions = hf;
     numHashFunctions = hf.getNumberOfFunctions( );
 }

 private Random r = new Random( );
 
 private static final double MAX_LOAD = 0.40;
 private static final int ALLOWED_REHASHES = 1;
 
 private int rehashes = 0;
   
 private boolean insertHelper1( AnyType x )
 {
     final int COUNT_LIMIT = 100;
     
     while( true )
     {
         int lastPos = -1;
         int pos;

         for( int count = 0; count < COUNT_LIMIT; count++ )
         {
             for( int i = 0; i < numHashFunctions; i++ )
             {
                 pos = myhash( x, i );

                 if( array[ pos ] == null )
                 {
                     array[ pos ] = x;
                     currentSize++;
                     return true;
                 }
             }

             // none of the spots are available. Kick out a random one
             int i = 0;
             do
             {
                 pos = myhash( x, r.nextInt( numHashFunctions ) );
             } while( pos == lastPos && i++ < 5 );

             AnyType tmp = array[ lastPos = pos ];
             array[ pos ] = x;
             x = tmp;
         }

         if( ++rehashes > ALLOWED_REHASHES )
         {
             expand( );      // Make the table bigger
             rehashes = 0;
         }
         else
             rehash( );
     }
 }
 
 private boolean insertHelper2( AnyType x )
 {
     final int COUNT_LIMIT = 100;
     
     while( true )
     {
         for( int count = 0; count < COUNT_LIMIT; count++ )
         {
             int pos = myhash( x, count % numHashFunctions );

             AnyType tmp = array[ pos ];
             array[ pos ] = x;

             if( tmp == null )
                 return true;
             else
                 x = tmp;
         }
     
         if( ++rehashes > ALLOWED_REHASHES )
         {
             expand( );      // Make the table bigger
             rehashes = 0;
         }
         else
             rehash( );
     }
 }
 
 /**
  * Insert into the hash table. If the item is
  * already present, return false.
  * @param x the item to insert.
  */
 public boolean insert( AnyType x )
 {         
     if( contains( x ) )
         return false;
     
     if( currentSize >= array.length * MAX_LOAD )
         expand( );
     
     return insertHelper1( x );
 }

 private int myhash( AnyType x, int which )
 {
     int hashVal = hashFunctions.hash( x, which );
     
     hashVal %= array.length;
     if( hashVal < 0 )
         hashVal += array.length;
     
     return hashVal;
 }
     
 private void expand( )
 {
     rehash( (int) ( array.length / MAX_LOAD ) );
 }
 
 private void rehash( )
 {
     //System.out.println( "NEW HASH FUNCTIONS " + array.length );
     hashFunctions.generateNewFunctions( );
     rehash( array.length );
 }
 
 private void rehash( int newLength )
 {
     //System.out.println( "REHASH: " + array.length + " " + newLength + " " + currentSize );
     AnyType [ ] oldArray = array;    // Create a new double-sized, empty table
         
     allocateArray( nextPrime( newLength ) );
     
     currentSize = 0;
     
         // Copy table over
     for( AnyType str : oldArray )
         if( str != null )
             insert( str );
 }

 
 /**
  * Gets the size of the table.
  * @return number of items in the hash table.
  */
 public int size( )
 {
     return currentSize;
 }
 
 /**
  * Gets the length (potential capacity) of the table.
  * @return length of the internal array in the hash table.
  */
 public int capacity( )
 {
     return array.length;
 }
 
 /**
  * Method that searches all hash function places.
  * @param x the item to search for.
  * @return the position where the search terminates, or -1 if not found.
  */
 private int findPos( AnyType x )
 {
     for( int i = 0; i < numHashFunctions; i++ )
     {
         int pos = myhash( x, i );
         if( array[ pos ] != null && array[ pos ].equals( x ) )
             return pos;
     }
     
     return -1;
 }

 /**
  * Remove from the hash table.
  * @param x the item to remove.
  * @return true if item was found and removed
  */
 public boolean remove( AnyType x )
 {
     int pos = findPos( x );
     
     if( pos != -1 )
     {
         array[ pos ] = null;
         currentSize--;
     }
     
     return pos != -1;
 }

 /**
  * Find an item in the hash table.
  * @param x the item to search for.
  * @return the matching item.
  */
 public boolean contains( AnyType x )
 {
     return findPos( x ) != -1;
 }
 
 /**
  * Make the hash table logically empty.
  */
 public void makeEmpty( )
 {
     doClear( );
 }

 private void doClear( )
 {
     currentSize = 0;
     for( int i = 0; i < array.length; i++ )
         array[ i ] = null;
 }
 

 
 private static final int DEFAULT_TABLE_SIZE = 101;

 private final HashFamily<? super AnyType> hashFunctions;
 private final int numHashFunctions;
 private AnyType [ ] array; // The array of elements
 private int currentSize;              // The number of occupied cells

 /**
  * Internal method to allocate array.
  * @param arraySize the size of the array.
  */
 private void allocateArray( int arraySize )
 {
     array = (AnyType[]) new Object[ arraySize ];
 }

 /**
  * Internal method to find a prime number at least as large as n.
  * @param n the starting number (must be positive).
  * @return a prime number larger than or equal to n.
  */
 protected static int nextPrime( int n )
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
	 System.out.println("CuckooHashTable: \n");
     
     for(int j=1; j<=5; j++) {
    	 CuckooHashTable<String> H = new CuckooHashTable<>( new StringHashFamily( 3 ), 2000);
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
 	     
 	    //System.out.println("Size after insertion" + H.size() );
 	     
 	     startTime = System.nanoTime();
 	     for(int i=0; i<n; i++) {
 	    	 int del = rand.nextInt(insertValues.length);
 				String val = insertValues[del];
 				if(H.contains(val)) {
 					H.remove(val);
 					System.out.println("Value deleted!!");
 				}
 	     }
 	     endTime = System.nanoTime();
 	     avgTime = (double) (endTime - startTime)/n;
 	     System.out.println("Average time for each search and deletion:"+ df.format(avgTime) +" ns\n");
 	     //System.out.println("Size after deletion" + H.size() );
 	     
     }
     
     System.out.println( "Finished. ");
 }

}
