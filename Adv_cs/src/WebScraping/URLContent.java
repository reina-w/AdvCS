package WebScraping;

import org.jsoup.Jsoup;
import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class URLContent {
	
	public static void main(String[] args) {
	
		try {
			// connects to the given url
		Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/Dartmouth_College").get();
		
		// gets the first section of the page labeled 'bodyContent'
		Element body = doc.select("div#bodyContent").first();
		
		System.out.println(body.text());
		
		// from this section, gets all hyperlinks (tagged with an 'a')
		Elements links = body.select("a");
		
		// gets the 7th hyperlink (which turns out to be the page for 'private schools')
		Element link = links.get(7);
		
		// connects to this hyperlink using its href attribute (the destination url)
		Document nextPage = Jsoup.connect("https://en.wikipedia.org/"+link.attr("href")).get();
		
		// prints out the first paragraph (using the 'p' tag) in this new url 
		//System.out.println(nextPage.select("p").first().text());
	
	
	
	} catch (IOException e) {
		System.out.println("Couldn't connect");
}
	}

}
