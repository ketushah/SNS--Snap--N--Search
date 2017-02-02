// Java Document
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class JSoupTest {


public static void main(String args[]) {

    try {


        Document doc=Jsoup.connect("http://www.google.com").get();
		int i=0;

        // get page title
        String title = doc.title();
        System.out.println(title);

        //gets all links
        Elements links = doc.select("a[href]");
        for (Element link : links) {
			
		i++;
		if(i==2)
		{
			continue;
		}

        // get the value from href attribute
        System.out.println("\nlink : " + link.attr("href"));
		Document docin=Jsoup.connect(link.attr("href")).get();
		
		String title1 = docin.title();
        System.out.println(title1);
		

        }


    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}
}