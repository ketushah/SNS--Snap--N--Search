// Java Document
// Java Document
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
 
public class snapdealdb {
 
  public static void main(String[] args) throws Exception
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
public static void crawlimage(String urlfun,int i,int pg) throws Exception
{
	//Crawl cwl;
	Document doc;
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sns","root","");
	String vendor= "SnapDeal";
	double prc=0;
	String prdURL="";
	String Name="";
	String URL="";
	String location="";
	Statement st=con.createStatement();
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
			if(link.attr("class").equals("hit-ss-logger somn-track lazyBg prodLink "))
			{
				String linkURL=link.attr("href");
				prdURL=linkURL;
				//System.out.println("Links: "+linkURL);
				Document docin=Jsoup.connect(linkURL).get();
				Elements header=docin.select("h1");
				Name=header.text();
				Name=Name.replaceAll("'","");
				System.out.println(Name);
				//System.out.println("Title: "+header.text());
				Elements prices=docin.select("nobr span span[class]");
				for(Element price:prices)
				{
					if(price.attr("class").equals("mvMinPrice"))
					{
						String p=price.text();
						p=p.replace(",","");
						prc=Double.parseDouble(p);
						//System.out.println("Price: "+price.text());
					}
				}
				Elements images=docin.select("img[itemprop]");
				for(Element image:images)
				{
					i++;
					if(image.attr("itemprop").equals("image"))
					{
						String imageurl=image.attr("src");
						URL=imageurl;
						//System.out.println(imageurl);
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
				int cnt=st.executeUpdate("insert into images values(null,'"+Name+"','"+vendor+"','"+prc+"','"+prdURL+"','"+URL+"','"+location+"');");
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