package Compression;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;


class branch {
	branch c1, c2;
	char info;
	boolean isLeaf;
    branch(){};
    branch(branch c1, branch c2, boolean isLeaf) {
        this.c1 = c1;
        this.c2 = c2;
        this.isLeaf = false;
    }
    
    branch(char info, boolean isLeaf) {
    	this.info = info;
        this.isLeaf = true;
    }
}

public class huffmanEncoding {
	
	HashMap<Character, Integer> myMap = new HashMap<Character, Integer>();
	priorityQ<branch> pq = new priorityQ<branch>();
	
	public huffmanEncoding () throws IOException {
		
		BufferedReader in = new BufferedReader(new FileReader("story.txt"));
		
		while(in.read() != -1) {
			
			Character ch = (char)in.read();
			if(myMap.get(ch) == null) myMap.put(ch, 1);
			else myMap.put(ch, myMap.get(ch)+1);
		}
		
		in.close();
		
		Set<Character> keys = myMap.keySet();
		for(char ch : keys) {
			pq.add(new branch(ch, true), myMap.get(ch));
		}
		
		while(pq.size() != 1) {
			
			Node<branch> b1 = pq.pop();
			Node<branch> b2 = pq.pop();
			int p = b1.priority+b2.priority;
			pq.add(new branch(b1.info, b2.info, false), p);
		}
		
	}
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		new huffmanEncoding();
	}

}
