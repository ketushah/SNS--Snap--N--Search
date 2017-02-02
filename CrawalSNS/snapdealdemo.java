// Java Document
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
 
public class snapdealdemo {
 
  public static void main(String[] args) 
  {
	  String URL="http://www.snapdeal.com/products/lifestyle-watches?start=1&sort=plrty&pageno=1&endPoint=5&q=&keyword=";
	  //Crawl crwl=new Crawl();
	  int i=0;
	  int p=1;
	  Crawl.crawlimage(URL,i,p);
  }
}

class Crawl
{ 
public static void crawlimage(String urlfun,int i,int pg)
{
	//Crawl cwl;
	Document doc;
	try 
	{
 
		// need http protocol
		doc = Jsoup.connect(urlfun).get();
 
		// get page title
		String title = doc.title();
		System.out.println("title : " + title);
		
		
		
		Elements links = doc.select("a[class]");  
        for (Element link : links)  //Accessing links present in the URL passed in the Method Call
		{
			if(link.attr("class").equals("hit-ss-logger somn-track lazyBg prodLink "))
			{
				String linkURL=link.attr("href");
				System.out.println("Links: "+linkURL);
				Document docin=Jsoup.connect(linkURL).get();
				Elements header=docin.select("h1");
				System.out.println("Title: "+header.text());
				Elements prices=docin.select("input[id]");
				for(Element price:prices)
				{
					if(price.attr("id").equals("sellingPriceInpt"))
					{
						System.out.println("Price: "+price.attr("value"));
					}
				}
				Elements images=docin.select("img[itemprop]");
				for(Element image:images)
				{
					i++;
					if(image.attr("itemprop").equals("image"))
					{
						String imageurl=image.attr("src");
						System.out.println(imageurl);
						String destinationFile = "images/image"+i+".jpg";
						URL url = new URL(imageurl);
						InputStream is = url.openStream();
						OutputStream os = new FileOutputStream(destinationFile);

						byte[] b = new byte[2048];
						int length;

						while ((length = is.read(b)) != -1) 
						{
							os.write(b, 0, length);
						}
					}
				}
				System.out.println();
			}
			else
			{
				continue;
			}

		}
		
		System.out.println("Next Page:");
		pg+=15;
		//URL URLfun=new URL(urlfun);
		String nextURL="http://www.snapdeal.com/products/lifestyle-watches?start="+pg+"&sort=plrty&pageno=1&endPoint=5&q=&keyword=";
		System.out.println(nextURL);
		Crawl.crawlimage(nextURL,i,pg);
		
			
		/*Elements nextpages=doc.select("a[class]");
		
		for (Element nextpage : nextpages)
		{
			if(nextpage.attr("class").equals("pagnNext"))
			{
				
				String nextURL=nextpage.attr("href");
				System.out.println(nextURL);
				String nextpageurl=new String("http://www.amazon.in"+nextURL);
				System.out.println();
				Crawl cwl=new Crawl();
				cwl.crawlimage(nextpageurl,i+1);
			}
		}*/
	} 
	catch (Exception e) 
	{
		System.out.println(e);
	}
}
}