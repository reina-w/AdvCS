package Map;
import java.util.HashMap;

public class Map {

	public static void main(String[] args) {
		
		HashMap<String, Integer> myMap = new HashMap<String, Integer>();
		
		myMap.put("Elgin", 17);
		
		myMap.put("Reina", 16);
		
		myMap.put("Elgin", 18);
		
		System.out.println(myMap.get("Elgin"));
		
		System.out.println(myMap.get("David")); //null
		
		
	}
	
}
