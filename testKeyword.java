import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class testKeyword {

	QuoteSaxParser parser = new QuoteSaxParser("quotes.xml");
	QuoteList quoteList = parser.getQuoteList();
	
	//test 1
	@Test
	public void testKeywordAdd() {
		String quote = "Today is Monday";
		String author = "Amilcar Martinez";
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("week");
		keywords.add("day");
		quoteserverCLI.appendQuote(quote,author,keywords);
	}

}
