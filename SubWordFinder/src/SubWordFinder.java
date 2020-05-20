/**
 * This is a program that finds all of the words in the dictionary 
 * that can be split up into sub words and outputs them to a text file --> "subwords.txt"
 * @author 20george
 * @version 12-4-18
 */
import java.util.*;
import java.io.*;

public class SubWordFinder implements WordFinder {
	private ArrayList<ArrayList<String>> dictionary;
	private String alphabet = "abcdefghijklmnopqrstuvwxyz";
	/**
	 * Populates the dictionary from the text file contents
	 * The dictionary object should contain 26 buckets, each
	 * bucket filled with an ArrayList<String>
	 * The String objects in the buckets are sorted A-Z because
	 * of the nature of the text file words.txt
	 */
	public void populateDictionary() {
		Scanner in;
		try {
			in = new Scanner(new File("words_all_os.txt"));
			while (in.hasNext()) {
				String temp = in.nextLine();
				dictionary.get(alphabet.indexOf(temp.charAt(0))).add(temp);
			}
			in.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
/**
 * constructor for the class
 */
	public SubWordFinder() {
		dictionary = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < 26; i++) {
			dictionary.add(new ArrayList<String>());
		}
	 }

	/**
	 * Retrieves all SubWord objects from the dictionary.
	 * To do this the program looks through every word in the dictionary
	 * to see if it is a SubWord object
	 * @return An ArrayList containing the SubWord objects
	 * pulled from the file words_all_os.txt
	 */
	public ArrayList<SubWord> getSubWords() {
		ArrayList<SubWord> subwords = new ArrayList<SubWord>();
		for(ArrayList<String> bucket : dictionary) {
			for (String word : bucket) {
				for(int i = 3; i < word.length() - 1; i++) {
					String front = word.substring(0, i);
					String back = word.substring(i);
					if(inDictionary(front) && inDictionary(back)) {
						subwords.add(new SubWord(word, front, back));
					}
				}
			}
		
		}
		return subwords;
	}
	/**
	 * Look through the entire dictionary object to see if 
	 * word exists in dictionary
	 * @param word The item to be searched for in dictionary
	 * @return true if word is in dictionary, false otherwise
	 */
	public boolean inDictionary(String word) {
		try {
			ArrayList<String> bucket = dictionary.get(alphabet.indexOf(word.charAt(0)));
			int low = 0;
			int high = bucket.size() - 1;
			int mid;
			while(low <= high) {
				mid = (low+high)/2;
				if (bucket.get(mid).compareTo(word) < 0) {
					// item must be to the right
					low = mid + 1;
					
				}
				else if(bucket.get(mid).compareTo(word) > 0) {
					high = mid - 1;
				}
				else 
					return true;
			}
			return false;
		}
		catch (Exception e) {
			System.out.println("Invalid input, does not exist in dictionary");
			return false;
		}
	}
/**
 * main method of the class
 * @param args
 */
	public static void main(String[] args) {
		SubWordFinder app = new SubWordFinder();
		app.populateDictionary();
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("/Users/20george/eclipse-workspace/SubWordFinder/subwords.txt"));
			for(SubWord temp : app.getSubWords()) {
				System.out.println(temp);
				pw.write(temp + "\r" + "\n");
			}
			pw.close();
		}
		catch (Exception e) {
		}
	}

}
