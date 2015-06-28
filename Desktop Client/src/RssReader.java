import it.sauronsoftware.feed4j.FeedIOException;
import it.sauronsoftware.feed4j.FeedParser;
import it.sauronsoftware.feed4j.FeedXMLParseException;
import it.sauronsoftware.feed4j.UnsupportedFeedException;
import it.sauronsoftware.feed4j.bean.Feed;
import it.sauronsoftware.feed4j.bean.FeedItem;
import java.net.MalformedURLException;
import java.net.URL;

public class RssReader {
	
	/**
	 * Authors: Daniel Hosseini, Lídia Nyman, Odzaya Batsaikhan.
	 */

	Feed feed = null;
	int items = 0;

	public String[] RssCall(String StockName) {

		/**
		 * The constructor takes the searched stockname puts in in the url link
		 * and tries to find the stock news otherwise it will catch the error and print it out for testing
		 */
		URL url = null;

		try {
			url = new URL("http://feeds.finance.yahoo.com/rss/2.0/headline?s="
					+ StockName + " &region=US&lang=en-US");

			feed = FeedParser.parse(url);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FeedIOException e) {
			e.printStackTrace();
		} catch (FeedXMLParseException e) {
			e.printStackTrace();
		} catch (UnsupportedFeedException e) {
			e.printStackTrace();
		}
		
		/**
		 * Getting the length of the feed, so if a stock has 14 news it will put items = 14
		 */
		 int items = feed.getItemCount();
	// for testing
	//	 System.out.println(items);
		String[] strs = new String[items];
		/**
		 * Puts the item titles in a arrays and returns it
		 */

		for (int i = 0; i <= items - 1; i++) {

			FeedItem item = feed.getItem(i);

		//	System.out.println(i + " Title: " + item.getTitle());
			strs[i] = item.getTitle();

		}
		return strs;

	}

}
