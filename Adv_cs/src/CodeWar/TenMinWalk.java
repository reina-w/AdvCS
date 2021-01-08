package CodeWar;

public class TenMinWalk {

	public static void main(String[] args) {
		
		System.out.println(isValid(new char[] {'n','s','n','s','n','s','n','s','n','s'}));
		

	}
	
	public static boolean isValid(char[] walk) {
	    
		int x = 0, y = 0;
		
		if(walk.length != 10) return false;
		
		for(int i=0; i< walk.length; i++) {
			if(walk[i] == 'n') y++;
			else if(walk[i] == 's') y--;
			else if(walk[i] == 'e') x++;
			else if(walk[i] == 'w') x--;
		}
		
		if(x == 0 && y == 0) return true;
		else return false;
	}
}
