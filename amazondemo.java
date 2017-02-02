// Java Document
import java.io.IOException;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
 
public class amazondemo {
 
  public static void main(String[] args) 
  {
	  String URL="http://www.amazon.in/s/ref=sr_nr_n_0?fst=as%3Aoff&rh=n%3A1350387031%2Cn%3A1375495031%2Ck%3Awatches&keywords=watches&ie=UTF8&qid=1423471297&rnid=1350388031";
	  Crawl crwl=new Crawl();
	  crwl.crawlimage(URL);
  }
}

class Crawl
{ 
public void crawlimage(String url)
{
	Document doc;
	try 
	{
 
		// need http protocol
		doc = Jsoup.connect(url).get();
 
		// get page title
		String title = doc.title();
		System.out.println("title : " + title);
		
		System.out.println("Next Page:");
			
		Elements nextpages=doc.select("a[class]");
		
		for (Element nextpage : nextpages)
		{
			if(nextpage.attr("class").equals("pagnNext"))
			{
				
				String nextURL=nextpage.attr("href");
				System.out.println(nextURL);
				String nextpageurl=new String("http://www.amazon.in"+nextURL);
				System.out.println();
				Crawl cwl=new Crawl();
				cwl.crawlimage(nextpageurl);
			}
		}
	} 
	catch (Exception e) 
	{
		System.out.println(e);
	}
}
}