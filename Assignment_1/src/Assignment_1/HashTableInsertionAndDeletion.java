package Assignment_1;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Random;

public class HashTableInsertionAndDeletion {
	
	public static String[] randomStringCreationAndInsertion(int n, Hashtable<String, String> hashTable) {
		String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		
		String[] values = new String[n];
		
		long startTime = System.nanoTime();
		
		for (int i=0;i<n;i++) {
			StringBuilder sb = new StringBuilder(n);
			for (int j = 0; j < 10; j++) {
			   int index = (int)(alphaNumericString.length() * Math.random());
			   sb.append(alphaNumericString.charAt(index));
			  }
			String val = sb.toString();
			hashTable.put(val, val);		
			values[i] = val;
		}
		long endTime = System.nanoTime();
		double avgTime = (double) (endTime - startTime)/n;
		System.out.println("Average time for each insertion:"+ avgTime +" ns");
		return values;
	 }
	
	public static void SearchAndDeletion (int n, String[] values, Hashtable<String, String> hashTable) {
		Random rand = new Random();
		long startTime = System.nanoTime();
		for (int i=0;i<n;i++) {
			int del = rand.nextInt(values.length);
			String val = values[del];
			if(hashTable.containsKey(val)) {
				hashTable.remove(val);
			}
		}
		long endTime = System.nanoTime();
		double avgTime = (double) (endTime - startTime)/n;
		System.out.println("Average time for each search and deletion:"+ avgTime +" ns");
	}
	
	
	
	
	public static void main(String[] args) {
		Hashtable<String, String> hashTable = new Hashtable<>();
		System.out.println("Enter the value of n: ");
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		System.out.println("n: "+ n);
		String[] insertedValues = randomStringCreationAndInsertion(n, hashTable);
		SearchAndDeletion(n, insertedValues, hashTable);
	}
}
