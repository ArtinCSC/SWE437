
import java.util.ArrayList;
import java.util.Random;

/**
 * List of all the quotes.
 * 
 * @author Mongkoldech Rajapakdee & Jeff Offutt Date: Nov 2009
 */
public class QuoteList {
	private ArrayList<Quote> quoteArray;

	// These constants are used in the servlet
	/* package */ static final int SearchAuthorVal = 0;
	/* package */ static final int SearchTextVal = 1;
	/* package */ static final int SearchBothVal = 2;
	/* package */ static final int SearchKeyword = 3;
	/* package */ static final int SearchThreeval = 4;


	// For returning a random quote
	private Random randomGen;
	private final int seed = 19580427;

	// Default constructor creates a new list and initializes the random seed
	public QuoteList() {
		this.quoteArray = new ArrayList<Quote>();
		randomGen = new Random(seed);
	}

	// Called when a quote is found, added to the array
	public void setQuote(Quote q) {
		quoteArray.add(q);
	}

	// Current size of the quote list
	public int getSize() {
		return quoteArray.size();
	}

	// Returns the ith quote from the list
	public Quote getQuote(int i) {
		return (Quote) quoteArray.get(i);
	}


   /**
    * Search the quotes in the list, based on searchString
    * @param searchString String input for search
    * @param mode search in the author, quotr, or both
    * @return QuoteList containing the search results (may be multiple quotes)
    */
   public QuoteList search (String searchString, int mode)
   {
      QuoteList returnQuote = new QuoteList();
      Quote quote;
      for (int i = 0; i < quoteArray.size(); i++)
      {
         quote = quoteArray.get (i);
         /*
         if (mode == SearchAuthorVal && quote.getAuthor().toLowerCase().indexOf (searchString.toLowerCase()) != -1)

         {  // Found a matching author, save it
            // System.out.println ("Matched Author ");
            returnQuote.setQuote (quote);
         } else if (mode == SearchTextVal && quote.getQuoteText().toLowerCase().indexOf (searchString.toLowerCase()) != -1)
         {  // Found a matching quote, save it
            // System.out.println ("Matched Text ");
            returnQuote.setQuote (quote);
         } else if ((mode == SearchBothVal) &&
                    (quote.getAuthor().toLowerCase().indexOf (searchString.toLowerCase()) != -1 ||
                     quote.getQuoteText().toLowerCase().indexOf (searchString.toLowerCase()) != -1))
         {  // Found a matching author or quote, save it
            // System.out.println ("Matched Both ");
            returnQuote.setQuote (quote);
         } else if ((mode == SearchKeyword)  && quote.getKeywords().size() != 0 ) {
        	 
        	 for(int j = 0; j < quote.getKeywords().size(); j++) {
        		if( quote.getKeywords().get(j).equalsIgnoreCase(searchString)) {
        			returnQuote.setQuote(quote);
        		break;
        		}
        	 }
        	 
         } else if (mode == SearchThreeval &&
                 (quote.getAuthor().toLowerCase().indexOf (searchString.toLowerCase()) != -1 ||
                 quote.getQuoteText().toLowerCase().indexOf (searchString.toLowerCase()) != -1 ||
                 quote.getKeywords().size() != 0 ) ) {
        	
        	 for(int j = 0; j < quote.getKeywords().size(); j++) {
         		if( quote.getKeywords().get(j).equalsIgnoreCase(searchString)) {
         			returnQuote.setQuote(quote);
         		break;
         		}
        	 }
         }
         */
         if(mode == SearchAuthorVal && checkMatch(true,false,false,quote,searchString))
         	returnQuote.setQuote(quote);
         else if(mode == SearchTextVal && checkMatch(false,true,false,quote,searchString))
         	returnQuote.setQuote(quote);
         else if(mode == SearchBothVal && checkMatch(true,true,false,quote,searchString))
         	returnQuote.setQuote(quote);
         else if(mode == SearchKeyword && checkMatch(false,false,true,quote,searchString))
         	returnQuote.setQuote(quote);
         else if(mode == SearchThreeval && checkMatch(true,true,true,quote,searchString))
         	returnQuote.setQuote(quote);
      }
      return returnQuote;
   }
	/**
	 * Check to see if quote contains searchString within its text, author, or keyword depending on the params
	 * @param checkAuthor boolean for whether the author should be checked for matches
	 * @param checkText boolean for whether the text should be checked for matches
	 * @param checkKeyword boolean for whether the keywords should be checked for matches
	 * @param quote quote to search
	 * @param searchString String input for search
	 * @return boolean of if a match was found
	 */
   public boolean checkMatch(boolean checkAuthor, boolean checkText, boolean checkKeyword, Quote quote, String searchString){
   		if(checkAuthor && quote.getAuthor().toLowerCase().indexOf (searchString.toLowerCase()) != -1)
   			return true;
   		else if(checkText && quote.getQuoteText().toLowerCase().indexOf (searchString.toLowerCase()) != -1)
   			return true;
   		else if(checkKeyword){
			for(int j = 0; j < quote.getKeywords().size(); j++) {
				if( quote.getKeywords().get(j).equalsIgnoreCase(searchString)) {
					return true;
				}
			}
		}
		return false;
   }

	/**
	 * Retuen a random quote object from the list.
	 * 
	 * @return a random Quote
	 */
	public Quote getRandomQuote() {
		return quoteArray.get(randomGen.nextInt(quoteArray.size()));
	}
}
