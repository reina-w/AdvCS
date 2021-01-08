package CodeWar;

import java.util.ArrayList;

public class CountingDuplicates {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(duplicateCount("aaabcdd"));
	}
	
	public static int duplicateCount(String text) {
	    // Write your code here
		
		String text1 = text.toLowerCase();
		int len = text1.length();
		ArrayList checked = new ArrayList<>();
		ArrayList dup = new ArrayList<>();
		
		for(int i=0; i<len; i++) {
			char ch = text1.charAt(i);
			if(!checked.contains(ch)) {
				checked.add(ch);
			} else {
				if(!dup.contains(ch)) {
					dup.add(ch);
				} else {
					continue;
				}
			}
		}
		
		return dup.size();
	    
	  }

}
