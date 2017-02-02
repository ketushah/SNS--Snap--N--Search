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
 
public class flipkartdb {
 
  public static void main(String[] args) throws Exception
  {
	  String URL="http://www.flipkart.com/search?q=watches&ag=acon&sid=search.flipkart.com&otracker=search&start=1";
	  //Crawl crwl=new Crawl();
	  int i=0;
	  int p=1;
	  Crawl.crawlimage(URL,i,p);
  }
}

class Crawl
{ 
public static void crawlimage(String urlfun,int i,int pg) throws Exception
{
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sns","root","");
	String vendor= "Flipkart";
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
			if(link.attr("class").equals("fk-display-block"))
			{
				String linkURL="http://www.flipkart.com"+link.attr("href");
				prdURL=linkURL;
				//System.out.println("Links: "+linkURL);
				Document docin=Jsoup.connect(linkURL).get();
				Elements header=docin.select("h1");
				Name=header.text();
				Name=Name.replaceAll("'s","");
				System.out.println(Name);
				Elements prices=docin.select("span[class]");
				for(Element price:prices)
				{
					if(price.attr("class").equals("selling-price omniture-field"))
					{
						String p=price.text();
						p=p.replace("Rs.","");
						p=p.replace(",","");
						p=p.replaceAll("\u00a0","");
						p=p.trim();
						System.out.println(p);
						prc=Float.parseFloat(p);
						//prc=p;
						//System.out.println("Price: "+price.text());
					}
				}
				Elements images=docin.select("img[class]");
				for(Element image:images)
				{
					i++;
					if(image.attr("class").equals("productImage  current"))
					{
						String imageurl=image.attr("data-src");
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
		pg+=25;
		//URL URLfun=new URL(urlfun);
		String nextURL="http://www.flipkart.com/search?q=watches&ag=acon&sid=search.flipkart.com&otracker=search&start="+pg+"";
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