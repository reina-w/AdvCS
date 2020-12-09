package Compression;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
	
	void createCode(branch b, String code) {
		
		branch left = b.c1;
		branch right = b.c2;
		
		//base case
		if(b.isLeaf) {
			codeMap.put(b.info, code);
		}
		
		//recursive case
		createCode(left, code + "0");
		createCode(right, code + "1");
	}
	
	void printCodeMap() {
		
		Set<Character> keys = codeMap.keySet();
		for(char ch : keys) {
			System.out.println(ch + " = " + codeMap.get(ch));
		}
	}
	
	void compress() throws IOException {
		
		BufferedBitWriter bw = new BufferedBitWriter("textCompressed");
		BufferedReader in = new BufferedReader(new FileReader("story.txt"));
		
		while(in.read() != -1) {
			
			Character ch = (char)in.read();
			String code = codeMap.get(ch);
			for(int i=0; i<code.length(); i++) {
				if(code.charAt(i) == '0') bw.writeBit(false);
				else bw.writeBit(true);
			}
		}
		
		bw.close();
	}
	
	void decompress() {
		
	}
	
	HashMap<Character, Integer> readMap = new HashMap<Character, Integer>();
	HashMap<Character, String> codeMap = new HashMap<Character, String>();
	priorityQ<branch> pq = new priorityQ<branch>();
	
	public huffmanEncoding () throws IOException {
		
		BufferedReader in = new BufferedReader(new FileReader("story.txt"));
		
		while(in.read() != -1) {
			
			Character ch = (char)in.read();
			if(readMap.get(ch) == null) readMap.put(ch, 1);
			else readMap.put(ch, readMap.get(ch)+1);
		}
		
		in.close();
		
		Set<Character> keys = readMap.keySet();
		for(char ch : keys) {
			pq.add(new branch(ch, true), readMap.get(ch));
		}
		
		while(pq.size() != 1) {
			
			Node<branch> b1 = pq.pop();
			Node<branch> b2 = pq.pop();
			int p = b1.priority+b2.priority;
			pq.add(new branch(b1.info, b2.info, false), p);
		}
		
		createCode(pq.pop().info, "");
		
		printCodeMap();
	}
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new huffmanEncoding();
	}

}
