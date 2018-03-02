import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class TestKeyword {

	@Before	
	public void set() {
	QuoteSaxParser parser = new QuoteSaxParser("quotes.xml");
	QuoteList quoteList = parser.getQuoteList();

	}
	
	@Test	
	public void testKeywordAdd() {
		String quote = "Today is Monday";
		String author = "Amilcar Martinez";
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("week");
		keywords.add("day");
		quoteserverCLI.appendQuote(quote,author,keywords);
	}
	
	@Test	
	public void testKeywordAdd2() {
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("week");
		keywords.add("day");
		Quote quote = new Quote("author", "quote", keywords);
		
	}
}
