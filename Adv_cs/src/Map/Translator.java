package Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Translator {
	
	HashMap<String, String> myMap = new HashMap<String, String>();
	
	public Translator() throws IOException {
		
		BufferedReader in = new BufferedReader(new FileReader("EnglishToChineseDictionary.txt"));
		
		for(String line = in.readLine(); line != null; line = in.readLine()) {
			
			String a = line;
			String b = in.readLine();
			myMap.put(a, b);
		}
		
		in.close();
		
		while(true) {
			
			Scanner s = new Scanner(System.in);
			String input = s.nextLine();
			String input_l = input.toLowerCase();
			System.out.println(myMap.get(input_l));
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		new Translator();
	}

}
