package KevinBaconGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class KevinBaconGame {
	
	Graph<String> myGraph = new Graph<String>();

	HashMap<Integer, String> actor = new HashMap<Integer, String>();
	HashMap<Integer, String> movie = new HashMap<Integer, String>();
	
	public KevinBaconGame() throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader("actors.txt"));
		
		String s = "";
		
		while((s = br.readLine()) != null) {
			
			String[] words = s.split("~");
			int num = Integer.parseInt(words[0]);
			actor.put(num, words[1]);
		}
		
		
	}

}
