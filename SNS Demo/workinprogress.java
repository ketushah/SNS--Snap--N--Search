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
 
public class workinprogress {
 
  public static void main(String[] args) {
 
	Document doc;
	try 
	{
 
		// need http protocol
		doc = Jsoup.connect("http://www.flipkart.com/watches/wrist-watches/pr?sid=r18,f13&otracker=product_breadCrumbs_Wrist%20Watches").get();
 
		// get page title
		String title = doc.title();
		System.out.println("title : " + title);
		int i=0;
		
		Elements links = doc.select("a[class]");  
        for (Element link : links) 
		{
			if(link.attr("class").equals("fk-display-block"))
			{
				String linkURL=link.attr("href");
				System.out.println(linkURL);
				Document docin=Jsoup.connect("http://www.flipkart.com/").get();
				Element header=docin.select("h1");
				System.out.println("Title: "+header.text());
				Elements prices=docin.select("span[class]");
				for(Element price:prices)
				{
					if(price.attr("class").equals("price"))
					{
						System.out.println("Price: "+price.text());
					}
				}
				Elements images=docin.select("img[data-src]");
				for(Element image:images)
				{
					i++;
					String imageurl=image.attr("data-src");
					String destinationFile = "image"+i+".jpg";
					URL url = new URL(imageUrl);
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
			else
			{
				continue;
			}

		}
 
	} 
	catch (Exception e) 
	{
		System.out.println(e);
	}
 
  }
 
}
