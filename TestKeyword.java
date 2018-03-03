
import java.security.InvalidParameterException;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class testKeyword {
    private QuoteList quoteList;
    private ArrayList<String> keywords;
    @Before
    public void set() {
        //QuoteSaxParser parser = new QuoteSaxParser("quotes.xml");
    	quoteList = new QuoteList();//parser.getQuoteList();
    	keywords = new ArrayList<String>();
    }
    
    @Test
    public void testKeywordAdd() {
        String quote = "Today is Monday";
        String author = "Amilcar Martinez";
        //ArrayList<String> keywords = new ArrayList<String>();
        keywords.add("week");
        keywords.add("day");
        quoteserverCLI.appendQuote(quote,author,keywords);
    }
    
    @Test
    public void testQuoteKeyword() {
       // ArrayList<String> keywords = new ArrayList<String>();
        keywords.add("week");
        keywords.add("day");
        Quote quote = new Quote("author", "quote", keywords);
    }
    
    @Test (expected = InvalidParameterException.class)
    /*
     *  Tests to see if the quote class will throw an InvalidParameterException 
     *  when a quote is created with more then 10 keywords
     */
    public void testQuoteKeywordLimit(){
    	for(int x = 0; x < 11;x++)
    		keywords.add("Keyword"+x);
    	Quote q = new Quote("Test Author","Test Quote",keywords);
    }
}

