package assignment4_Q2;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import assignment4_Q1.BoyerMoore;
import assignment4_Q2.In;
import assignment4_Q2.Queue;
import assignment4_Q2.TST;
import assignment4_Q2.StdOut;


/*************************************************************************
 *  Compilation:  javac TST.java
 *  Execution:    java TST < words.txt
 *  Dependencies: StdIn.java
 *
 *  Symbol table with string keys, implemented using a ternary search
 *  trie (TST).
 *
 *
 *  % java TST < shellsST.txt
 *  by 4
 *  sea 6
 *  sells 1
 *  she 0
 *  shells 3
 *  shore 7
 *  the 5

 *
 *  % java TST
 *  theory the now is the time for all good men

 *  Remarks
 *  --------
 *    - can't use a key that is the empty string ""
 *
 *************************************************************************/

public class TST<Value> {
    private int N;       // size
    private Node root;   // root of TST

    private class Node {
        private char c;                 // character
        private Node left, mid, right;  // left, middle, and right subtries
        private Value val;              // value associated with string
    }

    // return number of key-value pairs
    public int size() {
        return N;
    }

   /**************************************************************
    * Is string key in the symbol table?
    **************************************************************/
    public boolean contains(String key) {
        return get(key) != null;
    }

    public Value get(String key) {
        if (key == null) throw new NullPointerException();
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }

    // return subtrie corresponding to given key
    private Node get(Node x, String key, int d) {
        if (key == null) throw new NullPointerException();
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        if (x == null) return null;
        char c = key.charAt(d);
        if      (c < x.c)              return get(x.left,  key, d);
        else if (c > x.c)              return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid,   key, d+1);
        else                           return x;
    }


   /**************************************************************
    * Insert string s into the symbol table.
    **************************************************************/
    public void put(String s, Value val) {
        if (!contains(s)) N++;
        root = put(root, s, val, 0);
    }

    private Node put(Node x, String s, Value val, int d) {
        char c = s.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if      (c < x.c)             x.left  = put(x.left,  s, val, d);
        else if (c > x.c)             x.right = put(x.right, s, val, d);
        else if (d < s.length() - 1)  x.mid   = put(x.mid,   s, val, d+1);
        else                          x.val   = val;
        return x;
    }


   /**************************************************************
    * Find and return longest prefix of s in TST
    **************************************************************/
    public String longestPrefixOf(String s) {
        if (s == null || s.length() == 0) return null;
        int length = 0;
        Node x = root;
        int i = 0;
        while (x != null && i < s.length()) {
            char c = s.charAt(i);
            if      (c < x.c) x = x.left;
            else if (c > x.c) x = x.right;
            else {
                i++;
                if (x.val != null) length = i;
                x = x.mid;
            }
        }
        return s.substring(0, length);
    }

    // all keys in symbol table
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<String>();
        collect(root, "", queue);
        return queue;
    }

    // all keys starting with given prefix
    public Iterable<String> prefixMatch(String prefix) {
        Queue<String> queue = new Queue<String>();
        Node x = get(root, prefix, 0);
        if (x == null) return queue;
        if (x.val != null) queue.enqueue(prefix);
        collect(x.mid, prefix, queue);
        return queue;
    }

    // all keys in subtrie rooted at x with given prefix
    private void collect(Node x, String prefix, Queue<String> queue) {
        if (x == null) return;
        collect(x.left,  prefix,       queue);
        if (x.val != null) queue.enqueue(prefix + x.c);
        collect(x.mid,   prefix + x.c, queue);
        collect(x.right, prefix,       queue);
    }


    // return all keys matching given wildcard pattern
    public Iterable<String> wildcardMatch(String pat) {
        Queue<String> queue = new Queue<String>();
        collect(root, "", 0, pat, queue);
        return queue;
    }
 
    private void collect(Node x, String prefix, int i, String pat, Queue<String> q) {
        if (x == null) return;
        char c = pat.charAt(i);
        if (c == '.' || c < x.c) collect(x.left, prefix, i, pat, q);
        if (c == '.' || c == x.c) {
            if (i == pat.length() - 1 && x.val != null) q.enqueue(prefix + x.c);
            if (i < pat.length() - 1) collect(x.mid, prefix + x.c, i+1, pat, q);
        }
        if (c == '.' || c > x.c) collect(x.right, prefix, i, pat, q);
    }

    private static void SearchTrie(String pattern, TST<Queue<Integer>> tst) {
		
		Queue<Integer> queue = tst.get(pattern);
		System.out.println("\nPattern: " + pattern);
		
		if (queue == null)
			System.out.println("This pattern was not found.");
		else 
		{																	// pattern found
			queue.dequeue();												
			System.out.print("Number of occurences: " + queue.size());		// print number of occurrences
			System.out.print("\nOffsets: ");
			
			for (int item : queue) {									
				System.out.print(queue.dequeue() + ", ");					// print occurrences
			}
		}
		
		System.out.print("\n");
		
	}

    // test client
    public static void main(String[] args) {
    	
    	StdOut.println("Reading Protein.txt\n");
    	In file = new In("Protein.txt");
    	
    	String text = file.readAll();
    	
    	TST<Queue<Integer>> tst = new TST<Queue<Integer>>();
    	List<Integer> offsets =  new ArrayList<Integer>();
    	StringTokenizer tokenizer = new StringTokenizer(text, " ", false);
    	Queue<Integer> index;
    	int pos = 0;
    	while (tokenizer.hasMoreTokens()) {			// loop through the tokens
			
			String key = tokenizer.nextToken().toString();
			offsets = BoyerMoore.ComputeBoyerMoore(text, key);	// get the offset using BoyerMoore algorithm
			
			index = new Queue<Integer>();
			index.enqueue(pos);
			
			for (int offset : offsets) {			// loop through the offsets and add it to the queue
				index.enqueue(offset);
			}
			
			tst.put(key, index);					// create trie with its key and offset queue
			pos++;
			
		}
		
    	StdOut.println("Trie created successfully using TST.");
    	
    	String[] patterns = { "protein", "complex", "PPI", "prediction" };
    	
    	for(String pattern : patterns) {		
			SearchTrie(pattern, tst);	
		}

    }
}