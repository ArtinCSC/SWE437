
/*
 * 	Written By: Arin Melakian, Vincent Liu, Amilcar Martinez
 */
import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class TestKeyword {
	private QuoteList quoteList;
	private ArrayList<String> keywords;

	@Before
	public void set() {
		// QuoteSaxParser parser = new QuoteSaxParser("quotes.xml");
		quoteList = new QuoteList();// parser.getQuoteList();
		keywords = new ArrayList<String>();
	}

	// Test 1
	@Test
	public void testKeywordAdd() {
		String quote = "Today is Monday";
		String author = "Amilcar Martinez";
		// ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("week");
		keywords.add("day");
		quoteserverCLI.appendQuote(quote, author, keywords);
	}

	// Test 2
	@Test
	public void testQuoteKeyword() {
		// ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("week");
		keywords.add("day");
		Quote quote = new Quote("author", "quote", keywords);
	}

	// Test 3
	@Test(expected = InvalidParameterException.class)
	/*
	 * Tests to see if the quote class will throw an InvalidParameterException when
	 * a quote is created with more then 10 keywords
	 */
	public void testQuoteKeywordLimit() {
		for (int x = 0; x < 11; x++)
			keywords.add("Keyword" + x);
		Quote q = new Quote("Test Author", "Test Quote", keywords);
	}

	// Test 4
	@Test
	public void testKeywordSearchNotEmpty() {
		QuoteList list = new QuoteList();

		// add quotes to quotelist
		String quote = "Today is Monday";
		String author = "Amilcar Martinez";
		ArrayList<String> keywords1 = new ArrayList<String>();
		keywords1.add("week");
		keywords1.add("day");
		Quote quote1 = new Quote(author, quote, keywords1);

		// add quote to quoteList
		list.setQuote(quote1);

		// search for quotes with keyword
		assertNotEquals(list.search("week", QuoteList.SearchKeyword).getSize(), 0);
	}

}
