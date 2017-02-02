import java.io.IOException;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
 
public class jsoupex1 {
 
  public static void main(String[] args) {
 
	Document doc;
	try {
 
		// need http protocol
		doc = Jsoup.connect("http://www.flipkart.com/watches/pr?p%5B%5D=facets.ideal_for%255B%255D%3DMen&p%5B%5D=sort%3Dpopularity&sid=r18&facetOrder%5B%5D=ideal_for&otracker=ch_vn_watches_men_nav_catergorylinks_0_AllBrands#jumpTo=280|15").get();
 
		// get page title
		String title = doc.title();
		System.out.println("title : " + title);
		int i=0;
		
		Elements images = doc.select("img[data-src]");  
        for (Element image : images) 
		{  
                System.out.println("image"+(i+1)+".jpg Created!!!");
				String imageUrl = image.attr("data-src");
				i++;
				String destinationFile = "image"+i+".jpg";
				URL url = new URL(imageUrl);
				InputStream is = url.openStream();
				OutputStream os = new FileOutputStream(destinationFile);

				byte[] b = new byte[2048];
				int length;

				while ((length = is.read(b)) != -1) {
					os.write(b, 0, length);
				}

                //System.out.println("height : " + image.attr("height"));  
                //System.out.println("width : " + image.attr("width"));  
                //System.out.println("alt : " + image.attr("alt"));  
 
		// get all links
		/*Elements links = doc.select("a[href]");
		for (Element link : links) {
 
			// get the value from href attribute
			System.out.println("\nlink : " + link.attr("href"));
			System.out.println("text : " + link.text());*/
 
		}
		Elements links = doc.select("a[data-images]");
		for(Element link:links)
		{
			System.out.println("Source:\n"+link.attr("data-images"));
			System.out.println();
		}
		i=0;
		Elements imgs = doc.select("img[src]");
		for(Element img:imgs)
		{
			//System.out.println("HREF:\n"+img.attr("src"));
			System.out.println("watch"+i+".jpg Created!!!");
				String imageUrl = img.attr("src");
				i++;
				String destinationFile = "watch"+i+".jpg";
				URL url = new URL(imageUrl);
				InputStream is = url.openStream();
				OutputStream os = new FileOutputStream(destinationFile);

				byte[] b = new byte[2048];
				int length;

				while ((length = is.read(b)) != -1) {
					os.write(b, 0, length);
				}
		}
 
	} catch (Exception e) {
		System.out.println(e);
	}
 
  }
 
}
