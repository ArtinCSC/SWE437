
/*
 * 	Written By: Arin Melakian, Vincent Liu, Amilcar Martinez
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		quoteList = new QuoteList();
		keywords = new ArrayList<String>();
	}
	public void createTestXML(){
		try {
			PrintWriter out = new PrintWriter(new FileWriter("testQuotes.xml"));
			out.println("<?xml version="+'"'+"1.0"+'"'+"?>");
			out.println("<quote-list>\n<quote>");
			out.println("<quote-text>I know that you believe you understand what you think I said, but I am not sure you realize that what you heard is not what I meant.</quote-text>");
			out.println("<author>Richard Nixon</author>\n<keyword>believe</keyword>");
			out.println("<keyword>understand</keyword>\n</quote>\n</quote-list>");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Test 1
	@Test
	public void testKeywordAdd() {
		String quote = "Today is Monday";
		String author = "Amilcar Martinez";
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
		quoteList = new QuoteList();

		// add quotes to quotelist
		String quote = "Today is Monday";
		String author = "Amilcar Martinez";

		
		keywords.add("week");
		keywords.add("day");
		Quote quote1 = new Quote(author,quote, keywords);


		// add quote to quoteList
		quoteList.setQuote(quote1);

		// search for quotes with keyword
		assertNotEquals(quoteList.search("week", QuoteList.SearchKeyword).getSize(), 0);
	}

	
    //Test 6
    @Test
    public void testKeywordSearchCheckQuanity()
    {
    	//Create the text and authors for the 3 quotes
		String[] quoteText = {"Today is Monday", "Today is Tuesday","Today is Wednesday"};
		String[] quoteAuthors = {"Amilcar Martinez","Amilcar Martinez","Amilcar Martinez"};
		
		//Create the keywords for the first quote
		ArrayList<String> quoteKeywords1 = new ArrayList<String>();
		quoteKeywords1.add("week");
		quoteKeywords1.add("day");
		
		//Create the keywords for the second quote
		ArrayList<String> quoteKeywords2 = new ArrayList<String>();
		quoteKeywords1.add("first");
		
		//Create the keywords for the third quote
		ArrayList<String> quoteKeywords3 = new ArrayList<String>();
		quoteKeywords3.add("Wednesday");
		quoteKeywords3.add("third");
		quoteKeywords3.add("week");
		
		//Create an ArrayList for all the keywords (allows iteration through quotes)
		ArrayList<ArrayList<String>> keywords = new ArrayList<ArrayList<String>>();
		keywords.add(quoteKeywords1);
		keywords.add(quoteKeywords2);
		keywords.add(quoteKeywords3);
		
		//Create and add quotes to QuoteList
		for(int x = 0; x < quoteText.length;x++){
			Quote q = new Quote(quoteAuthors[x],quoteText[x],keywords.get(x));
			quoteList.setQuote(q);
		}
		
		//search for quotes with keyword
		assertEquals(quoteList.search("week", QuoteList.SearchKeyword).getSize(), 2);
    }
    
    @Test
    public void testParseXMLWithKeywords(){
    	createTestXML();
    	
    	String expectedQuoteText = "I know that you believe you understand what you think I said, but I am not sure you realize that what you heard is not what I meant.";
    	String expectedAuthor = "Richard Nixon";
    	keywords.add("believe");
    	keywords.add("understand");
    	
        QuoteSaxParser parser = new QuoteSaxParser("testQuotes.xml");
        quoteList = parser.getQuoteList();
        
        assertEquals(1,quoteList.getSize());
        
        Quote q = quoteList.getQuote(0);
        //Compare Quote text and author
        assertTrue(expectedQuoteText.equals(q.getQuoteText()));
        assertTrue(expectedAuthor.equals(q.getAuthor()));
        //Compare keywords
        ArrayList<String> qArrayList = q.getKeywords();
        for(int x = 0; x < qArrayList.size();x++){
        	assertTrue(keywords.contains(qArrayList.get(x)));
        	keywords.remove(qArrayList.get(x));
        } 
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
    	// Loads all quotes in quotes.xml into quoteList
    	quoteList = parser.getQuoteList();
    	
    	//search for entered quote
    	assertEquals ( quoteList.search( "Four545", QuoteList.SearchKeyword).getQuote(0).getQuoteText() ,
    			"SWE437 is a fun class. Second Test." );
        
    }

}
