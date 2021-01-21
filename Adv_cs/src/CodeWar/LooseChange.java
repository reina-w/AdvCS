package CodeWar;

import java.util.HashMap;

public class LooseChange {
	
	public static HashMap<String, Integer> looseChange(int cent) {
		
		HashMap<String, Integer> map = new HashMap<>();
		map.put("Pennies", 0);
		map.put("Nickels", 0);
		map.put("Dimes", 0);
		map.put("Quarters", 0);
		
		//your code here
		
		if(cent > 0) {
			
			map.put("Quarters", cent/25);
			
			int remain = cent % 25;
			map.put("Dimes", remain/10);
			
			remain %= 10;
			map.put("Nickels", remain / 5);
			
			remain %= 5;
			map.put("Pennies", remain);
			
		}
		
		return map;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(looseChange(56));
		System.out.println(looseChange(-435));

	}

}
