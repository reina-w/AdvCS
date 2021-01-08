package CodeWar;

public class PigLatin {

	public static void main(String[] args) {
		
		System.out.println(pigIt("Pig latin is cool"));
		System.out.println(pigIt("Hello world !"));

	}
	
	public static String pigIt(String str) {
		
		String punc = "`~!@#$%^&*()_+{}|:<>?-=[];'./,";
        
		String[] words = str.split(" ");
		
		String output = "";
		for(int i=0; i<words.length; i++) {
			
			if(punc.contains(words[i])) output += words[i] + " ";
			else {
				String newWord = words[i].substring(1) + words[i].substring(0, 1) + "ay";
				output += newWord + " ";
			}
		}
		
		return output.substring(0, output.length()-1);
    }

}
