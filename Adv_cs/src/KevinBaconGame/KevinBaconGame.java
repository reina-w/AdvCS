package KevinBaconGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class KevinBaconGame {
	
	//the graph has actors as its vertices, and movies as its edges <actor, movie>
	LabeledGraph<Integer, Integer> myGraph = new LabeledGraph<Integer, Integer>();

	//initiate maps for actors and movies
	HashMap<Integer, String> actor = new HashMap<Integer, String>();
	HashMap<Integer, String> movie = new HashMap<Integer, String>();
	
	public KevinBaconGame() throws IOException {
		
		//read in the actor file
		BufferedReader br = new BufferedReader(new FileReader("actors.txt"));
		
		String s = "";
		
		//build the map for actor names and their numbers
		while((s = br.readLine()) != null) {
			
			String[] words = s.split("~");
			int num = Integer.parseInt(words[0]);
			actor.put(num, words[1]);
//			testing
//			System.out.println(num);
//			System.out.println(words[1]);
		}
		
		//read in the movie file
		br = new BufferedReader(new FileReader("movies.txt"));
		
		s = "";
		
		//build the map for actor names and their numbers
		while((s = br.readLine()) != null) {
					
			String[] words = s.split("~");
			int num = Integer.parseInt(words[0]);
			movie.put(num, words[1]);
//			testing
//			System.out.println(num);
//			System.out.println(words[1]);
		}
		
		//read in the movie-actor file
		br = new BufferedReader(new FileReader("movie-actor.txt"));
				
		s = "";
		
		int curMov = 0;
		ArrayList<Integer> sameMov = new ArrayList<>();
		
		while((s = br.readLine()) != null) {
			
			String[] nums = s.split("~");
			int mov = Integer.parseInt(nums[0]);
			int act = Integer.parseInt(nums[1]);
			
			if(curMov == 0) {
				curMov = mov;
				
			} else if(curMov == mov) {
				myGraph.addVertex(act);
				sameMov.add(act);
				
			} else if(curMov != mov) {
				for(int i=0; i<sameMov.size(); i++) {
					for(int j=i+1; j<sameMov.size(); j++) {
						myGraph.connect(sameMov.get(i), sameMov.get(j), mov);
					}
				}
				curMov = mov;
				sameMov.clear();
			}
			
			
		}
		
		
	}
	
	public static void main(String[] args) throws IOException {
		
		KevinBaconGame myGame = new KevinBaconGame();
	}

}
