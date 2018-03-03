
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

    //Test 6
    @Test
    public void testKeywordSearchCheckQuanity()
    {
    	QuoteList list = new QuoteList();
		
		//first quote
		String quote1 = "Today is Monday";
		String author1 = "Amilcar Martinez";
		ArrayList<String> keywords1 = new ArrayList<String>();
		keywords1.add("week");
		keywords1.add("day");
		Quote Quote1 = new Quote(author1, quote1, keywords1);
		
		//second quote
		String quote2 = "Today is Tuesday";
		String author2 = "Amilcar Martinez";
		ArrayList<String> keywords2 = new ArrayList<String>();
		keywords2.add("first");
		Quote Quote2 = new Quote(author2, quote2, keywords2);
		
		//third quote
		String quote3 = "Today is Wednesday";
		String author3 = "Amilcar Martinez";
		ArrayList<String> keywords3 = new ArrayList<String>();
		keywords3.add("Wednesday");
		keywords3.add("third");
		keywords3.add("week");
		Quote Quote3 = new Quote(author3,quote3, keywords3);
		
		//add quote to quoteList
		list.setQuote( Quote1 );
		list.setQuote( Quote2 );
		list.setQuote( Quote3 );
		
		//search for quotes with keyword
		assertEquals(list.search("week", QuoteList.SearchKeyword).getSize(), 2);
    }
    
    
    @Test
    public void testSearchForAppendedQuote()
    {
        String quote = "SWE437 is a fun class. Second Test.";
        String author = "Prof. Offutt";
        //ArrayList<String> keywords = new ArrayList<String>();
        keywords.add("Monday");
        keywords.add("Four545");
        quoteserverCLI.appendQuote(quote,author,keywords);
        
        //read xml file
    	QuoteSaxParser parser = new QuoteSaxParser("quotes.xml");
    	// Loads all quotes in quotes.xlm into quoteList
    	QuoteList quotelist = parser.getQuoteList();       
    	quotelist = parser.getQuoteList();
    	
    	//search for entered quote
    	assertEquals ( quotelist.search( "Four545", QuoteList.SearchKeyword).getQuote(1).getQuoteText() , 
    			"SWE437 is a fun class. Second Test." );
        
    }

}
