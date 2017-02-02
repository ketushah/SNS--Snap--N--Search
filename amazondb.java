// Java Document
//for flipkart URL:http://www.flipkart.com/search?q=watches&ag=acon&sid=search.flipkart.com&otracker=search&start=26
//increment of 25 each time in start value
import java.io.IOException;
import java.sql.*;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
 
public class amazondb {
 
  public static void main(String[] args) throws Exception
  {
	  String URL="http://www.amazon.in/s/ref=sr_nr_n_0?fst=as%3Aoff&rh=n%3A1350387031%2Cn%3A1375495031%2Ck%3Awatches&keywords=watches&ie=UTF8&qid=1423471297&rnid=1350388031";
	  Crawl crwl=new Crawl();
	  int i=0;
	  crwl.crawlimage(URL,i);
  }
}

class Crawl
{ 
public void crawlimage(String urlfun,int i) throws Exception
{
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sns","root","");
	String vendor= "Amazon";
	float prc=0;
	String Name="";
	String URL="";
	String prdURL="";
	String location="";
	Statement st=con.createStatement();
	Document doc;
	try 
	{
 
		// need http protocol
		doc = Jsoup.connect(urlfun).get();
 
		// get page title
		String title = doc.title();
		System.out.println("title : " + title);
		
		
		
		Elements links = doc.select("a[class]");  
        for (Element link : links) 
		{
			if(link.attr("class").equals("a-link-normal s-access-detail-page  a-text-normal"))
			{
				String linkURL=link.attr("href");
				prdURL=linkURL;
				//System.out.println("Links: "+linkURL);
				Document docin=Jsoup.connect(linkURL).get();
				Elements header=docin.select("h1");
				//Name="Watch";
				Name=header.text();
				Name=Name.replaceAll("'s","");
				System.out.println("Title: "+header.text());
				Elements prices=docin.select("td span[class]");
				for(Element price:prices)
				{
					if(price.attr("class").equals("a-size-medium a-color-price"))
					{
						String p=price.text();
						p=p.replace(",","");
						p=p.replace("\u00a0","");
						p=p.trim();
						System.out.println(p);
						prc=Float.parseFloat(p);
						//prc=p;
						//System.out.println("Price: "+price.text());
					}
				}
				Elements images=docin.select("img[id]");
				for(Element image:images)
				{
					i++;
					if(image.attr("id").equals("landingImage"))
					{
						String imageurl=image.attr("src");
						URL=imageurl;
						System.out.println(imageurl);
						String destinationFile = "images/image"+i+".jpg";
						location=destinationFile;
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
				int cnt=st.executeUpdate("insert into images values(null,'"+Name+"','"+vendor+"',"+prc+",'"+prdURL+"','"+URL+"','"+location+"');");
				System.out.println();
			}
			else
			{
				continue;
			}

		}
		
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
				cwl.crawlimage(nextpageurl,i+1);
			}
		}
	} 
	catch (Exception e) 
	{
		System.out.println(e);
	}
}
}