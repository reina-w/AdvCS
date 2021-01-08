package CodeWar;

public class Prime {

	public static void main(String[] args) {
		
		System.out.println(isPrime(73));

	}
	
	public static boolean isPrime(int num) {
		
		if(num <= 1) return false;
		for(int i=2; i<= Math.sqrt(num); i++) {
			if(num % i == 0) return false;
		}
	    return true; 
	}

}
