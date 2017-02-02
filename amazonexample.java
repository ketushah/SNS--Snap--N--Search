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
 
public class amazonexample {
 
  public static void main(String[] args) {
 
	Document doc;
	try 
	{
 
		// need http protocol
		doc = Jsoup.connect("http://www.amazon.in/s/ref=sr_nr_n_0?fst=as%3Aoff&rh=n%3A1350387031%2Cn%3A1375495031%2Ck%3Awatches&keywords=watches&ie=UTF8&qid=1423471297&rnid=1350388031").get();
 
		// get page title
		String title = doc.title();
		System.out.println("title : " + title);
		
		int i=0;
		
		NextPage:
		{
			System.out.println("Next Page:");
			
		/*Elements links = doc.select("a[class]");  
        for (Element link : links) 
		{
			if(link.attr("class").equals("a-link-normal s-access-detail-page  a-text-normal"))
			{
				String linkURL=link.attr("href");
				//System.out.println("Links: "+linkURL);
				Document docin=Jsoup.connect(linkURL).get();
				Elements header=docin.select("h1");
				System.out.println("Title: "+header.text());
				Elements prices=docin.select("span[class]");
				for(Element price:prices)
				{
					if(price.attr("class").equals("a-size-medium a-color-price"))
					{
						System.out.println("Price: "+price.text());
					}
				}
				Elements images=docin.select("img[id]");
				for(Element image:images)
				{
					i++;
					if(image.attr("id").equals("landingImage"))
					{
						String imageurl=image.attr("src");
						//System.out.println(imageurl);
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

		}*/
		Elements nextpages=doc.select("a[class]");
		System.out.println("Outside Next For");
		for (Element nextpage : nextpages)
		{
			if(nextpage.attr("class").equals("pagnNext"))
			{
				System.out.println("Inside Nexr If");
				String nextURL=nextpage.attr("href");
				System.out.println(nextURL);
				doc=Jsoup.connect("http://www.amazon.in"+nextURL).get();
			}
		}
		break NextPage;
		}
	} 
	catch (Exception e) 
	{
		System.out.println(e);
	}
 
  }
 
}
