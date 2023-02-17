	package Assignment_1;

import java.util.Scanner;
import java.util.Random;

//BinarySearchTree class
//
//CONSTRUCTION: with no initializer
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//void remove( x )       --> Remove x
//boolean contains( x )  --> Return true if x is present
//Comparable findMin( )  --> Return smallest item
//Comparable findMax( )  --> Return largest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//void printTree( )      --> Print tree in sorted order
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
* Implements an unbalanced binary search tree.
* Note that all "matching" is based on the compareTo method.
* @author Mark Allen Weiss
*/
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{
/**
* Construct the tree.
*/
public BinarySearchTree( )
{
   root = null;
}

/**
* Insert into the tree; duplicates are ignored.
* @param x the item to insert.
*/
public void insert( AnyType x )
{
   root = insert( x, root );
}

/**
* Remove from the tree. Nothing is done if x is not found.
* @param x the item to remove.
*/
public void remove( AnyType x )
{
   root = remove( x, root );
}

/**
* Find the smallest item in the tree.
* @return smallest item or null if empty.
*/
public AnyType findMin( )
{
   if( isEmpty( ) )
       return null;
   return findMin( root ).element;
}

/**
* Find the largest item in the tree.
* @return the largest item of null if empty.
*/
public AnyType findMax( )
{
   if( isEmpty( ) )
       return null;
   return findMax( root ).element;
}

/**
* Find an item in the tree.
* @param x the item to search for.
* @return true if not found.
*/
public boolean contains( AnyType x )
{
   return contains( x, root );
}

/**
* Make the tree logically empty.
*/
public void makeEmpty( )
{
   root = null;
}

/**
* Test if the tree is logically empty.
* @return true if empty, false otherwise.
*/
public boolean isEmpty( )
{
   return root == null;
}

/**
* Print the tree contents in sorted order.
*/
public void printTree( )
{
   if( isEmpty( ) )
       System.out.println( "Empty tree" );
   else
       printTree( root );
}

/**
* Internal method to insert into a subtree.
* @param x the item to insert.
* @param t the node that roots the subtree.
* @return the new root of the subtree.
*/
private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
{
   if( t == null )
       return new BinaryNode<>( x, null, null );
   
   int compareResult = x.compareTo( t.element );
       
   if( compareResult < 0 )
       t.left = insert( x, t.left );
   else if( compareResult > 0 )
       t.right = insert( x, t.right );
   else
       ;  // Duplicate; do nothing
   return t;
}

/**
* Non recursive method, created by LR - 29-092014

private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
{
   if( t == null )
       return new BinaryNode<>( x, null, null );
   
   while (t != null) {     
  	 int compareResult = x.compareTo( t.element );
       
  	 if( compareResult < 0 )
  		 t = t.left;
  	 else if( compareResult > 0 )
  		 t = t.right;
  	 else
  		 ;  // Duplicate; do nothing
   }
  	 return t;
}*/

/**
* Internal method to remove from a subtree.
* @param x the item to remove.
* @param t the node that roots the subtree.
* @return the new root of the subtree.
*/
private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t )
{
   if( t == null )
       return t;   // Item not found; do nothing
       
   int compareResult = x.compareTo( t.element );
       
   if( compareResult < 0 )
       t.left = remove( x, t.left );
   else if( compareResult > 0 )
       t.right = remove( x, t.right );
   else if( t.left != null && t.right != null ) // Two children
   {
       t.element = findMin( t.right ).element;
       t.right = remove( t.element, t.right );
   }
   else
       t = ( t.left != null ) ? t.left : t.right;
   return t;
}

/**
* Internal method to find the smallest item in a subtree.
* @param t the node that roots the subtree.
* @return node containing the smallest item.
*/
private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t )
{
   if( t == null )
       return null;
   else if( t.left == null )
       return t;
   return findMin( t.left );
}

/**
* Internal method to find the largest item in a subtree.
* @param t the node that roots the subtree.
* @return node containing the largest item.
*/
private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t )
{
   if( t != null )
       while( t.right != null )
           t = t.right;

   return t;
}

/**
* Internal method to find an item in a subtree.
* @param x is item to search for.
* @param t the node that roots the subtree.
* @return node containing the matched item.
*/
private boolean contains( AnyType x, BinaryNode<AnyType> t )
{
   if( t == null )
       return false;
       
   int compareResult = x.compareTo( t.element );
       
   if( compareResult < 0 )
       return contains( x, t.left );
   else if( compareResult > 0 )
       return contains( x, t.right );
   else
       return true;    // Match
}

/**
* Internal method to print a subtree in sorted order.
* @param t the node that roots the subtree.
*/
private void printTree( BinaryNode<AnyType> t )
{
   if( t != null )
   {
       printTree( t.left );
       System.out.println( t.element );
       printTree( t.right );
   }
}

/**
* Internal method to compute height of a subtree.
* @param t the node that roots the subtree.
*/
private int height( BinaryNode<AnyType> t )
{
   if( t == null )
       return -1;
   else
       return 1 + Math.max( height( t.left ), height( t.right ) );    
}

// Basic node stored in unbalanced binary search trees
private static class BinaryNode<AnyType>
{
       // Constructors
   BinaryNode( AnyType theElement )
   {
       this( theElement, null, null );
   }

   BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt )
   {
       element  = theElement;
       left     = lt;
       right    = rt;
   }

   AnyType element;            // The data in the node
   BinaryNode<AnyType> left;   // Left child
   BinaryNode<AnyType> right;  // Right child
}


 /** The tree root. */
private BinaryNode<AnyType> root;


   // Test program
public static void main( String [ ] args )
{
   
   final int NUMS = 100000;  // must be even
   long startTime;
   long endTime;
   double avgTime;
   Random rand = new Random();

   Scanner scan = new Scanner(System.in);
   
   while(true) {
	   System.out.println("\nMENU\nPress 1 to perform the below tasks: \n"
	   		+ "i. Inserting Integers from 1 to 100,000 in order\n"
	   		+ "ii. Searching Random Integers from 1 to 100,000\n"
	   		+ "iii. Deleting Integers from 100,000 down to 1 in order");
	   System.out.println("\nPress 2 to perform the below tasks: \n"
	   		+ "i. Inserting Random Integers from 1 to 100,000\n"
	   		+ "ii. Searching Random Integers from 1 to 100,000\n"
	   		+ "iii. Deleting Random Integers from 1 to 100,000\n");
	   System.out.println("Press 3 to exit");
	   System.out.println("Enter your choice:");
	   int choice = scan.nextInt();
	   switch(choice) {
	   	case 1:
	   		BinarySearchTree<Integer> t = new BinarySearchTree<>( );
	   		System.out.println("i. Inserting integers from 1 to 100,000 in order....");
	   		startTime = System.nanoTime();
	   		for( int i = 1; i <= NUMS; i++)
	   	    {
	   	        t.insert( i );
	   	    } 
	   		endTime = System.nanoTime();
	   		avgTime = (double) (endTime - startTime)/NUMS;
	   		System.out.println("Average time to insert integers from 1 to 100,000 in order: "+ avgTime + " ns\n");
	   		//System.out.println( "Tree after insertions:" );
	   	    //t.printTree( );
	   		System.out.println("ii. Searching Random Integers from 1 to 100,000....");
	   		startTime = System.nanoTime();
	   		for( int i = 1; i <= NUMS; i++)
	   		{
	   			int randKey = rand.nextInt(NUMS) + 1;
	   			if (!t.contains( randKey )) {
	   				//System.out.println("key " + randKey + " not found" );
	   				continue;
	   			}
	   			
	   		}
	   		endTime = System.nanoTime();
	   		avgTime = (double) (endTime - startTime)/NUMS;
	   		System.out.println("Average time for searching random integers from 1 to 100,000: "+ avgTime + " ns\n");
	   		
	   		System.out.println("iii. Deleting Integers from 100,000 down to 1 in order......");
	   		startTime = System.nanoTime();
	   		for( int i = NUMS; i > 0; i--)
	   		{
	   			t.remove( i );
	   		}
	   		endTime = System.nanoTime();
	   		avgTime = (double) (endTime - startTime)/NUMS;
	   		System.out.println("Average time for deleting integers from 100,000 down to 1 in order: "+ avgTime + " ns\n");
	   		//System.out.println( "Tree after removals:" );
	   		//t.printTree( );
	   		break;
	   		
	   	case 2:
	   		BinarySearchTree<Integer> h = new BinarySearchTree<>( );
	   		System.out.println("i. Inserting Random Integers from 1 to 100,000....");
	   		startTime = System.nanoTime();
	   		for( int i = 1; i <= NUMS; i++)
	   	    {
	   			int randKey = rand.nextInt(NUMS) + 1;
	   			//System.out.println( "randomKey: " + randKey);
	   	        h.insert( randKey );
	   	    } 
	   		endTime = System.nanoTime();
	   		avgTime = (double) (endTime - startTime)/NUMS;
	   		System.out.println("Average time to insert random integers from 1 to 100,000: "+ avgTime + " ns\n");
	   		//System.out.println( "Tree after insterions:" );
	   	    //h.printTree( );
	   		System.out.println("ii. Searching Random Integers from 1 to 100,000.....");
	   		startTime = System.nanoTime();
	   		for( int i = 1; i <= NUMS; i++)
	   		{
	   			int randKey = rand.nextInt(NUMS) + 1;
	   			if (!h.contains( randKey )) {
	   				//System.out.println("key " + randKey + " not found" );
	   				continue;
	   			}
	   			
	   		}
	   		endTime = System.nanoTime();
	   		avgTime = (double) (endTime - startTime)/NUMS;
	   		System.out.println("Average time for searching random integers from 1 to 100,000: "+ avgTime + " ns\n");
	   		
	   		System.out.println("iii. Deleting Random Integers from 1 to 100,000.....");
	   		startTime = System.nanoTime();
	   		for( int i = NUMS; i > 0; i--)
	   		{
	   			int randKey = rand.nextInt(NUMS) + 1;
	   			//System.out.println( "randKey: " + randKey);
	   			h.remove( randKey );
	   		}
	   		endTime = System.nanoTime();
	   		avgTime = (double) (endTime - startTime)/NUMS;
	   		System.out.println("Average time for deleting random integers from 1 to 100,000: "+ avgTime + " ns\n");
	   		//System.out.println( "Tree after removals:" );
	   		//h.printTree( ); 
	   		break;
	   		
	   		
	   	case 3:
            System.exit(0);
        
	   	default:
            System.out.println("Invalid choice!!! Please make a valid choice. \\n\\n");
	   		
	   }
   }
}
}
