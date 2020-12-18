package Compression;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
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
    
    public String toString() {
    	return ""+info;
    }
}


public class huffmanEncoding {
	
	void createCode(branch b, String code) {
		
		branch left = b.c1;
		branch right = b.c2;
		
		//base case
		if(b.isLeaf) {
			codeMap.put(b.info, code);
			return;
		}
		
		//recursive case
		createCode(left, code + "0");
		createCode(right, code + "1");
	}
	
	void printCodeMap() throws IOException {
		
		PrintWriter pw = new PrintWriter(new FileWriter("codeMap.txt"));
		
		Set<Character> keys = codeMap.keySet();
		for(char ch : keys) {
			pw.println(ch);
			pw.println(codeMap.get(ch));
		}
		pw.close();
	}
	
	void compress() throws IOException {
		
		BufferedBitWriter bw = new BufferedBitWriter("textCompressed");
		BufferedReader in = new BufferedReader(new FileReader("story.txt"));
		
		int i = 0;
		while((i = in.read()) != -1) {
			
			char ch = (char)i;
//			System.out.println(ch);
			String code = codeMap.get(ch);
			for(int j=0; j<code.length(); j++) {
				if(code.charAt(j) == '0') bw.writeBit(false);
				else bw.writeBit(true);
			}
		}
		
		bw.close();
		in.close();
	}
	
	void decompress() throws IOException{
		
		PrintWriter pw = new PrintWriter(new FileWriter("textDecompressed"));
		BufferedBitReader br = new BufferedBitReader("textCompressed");
		Set<String> dekeys = deCodeMap.keySet();
		
		String code = "";
		while (br.hasNext()) { 
			
			boolean bit = br.readBit(); 
			
			if(bit) code += "1";
			else code += "0";
			
			for(String s : dekeys) {
				if(code.equals(s)) {
					pw.print(deCodeMap.get(s));
					code = "";
					break;
				}
			}
		}
		pw.close();
		br.close();
	}
	
	HashMap<Character, Integer> readMap = new HashMap<Character, Integer>();
	HashMap<Character, String> codeMap = new HashMap<Character, String>();
	HashMap<String, Character> deCodeMap = new HashMap<String, Character>();
	priorityQ<branch> pq = new priorityQ<branch>();
	
	public huffmanEncoding () throws IOException {
		
		BufferedReader in = new BufferedReader(new FileReader("story.txt"));
		
		int i = 0;
		while((i = in.read()) != -1) {
			
			char ch = (char)i;
//			ch = ch.toLowerCase(ch);
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
		compress();
		
		BufferedReader br= new BufferedReader(new FileReader("codeMap.txt"));
		
		for(String line = br.readLine(); line != null; line = br.readLine()) {
			
			Character a = line.charAt(0);
			String b = br.readLine();
			deCodeMap.put(b, a);
		}
		
		br.close();
		decompress();
	}
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new huffmanEncoding();
	}

}
