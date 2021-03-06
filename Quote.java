import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Quote data object.
 * 
 * @author Mongkoldech Rajapakdee & Jeff offutt Date: Nov 2009 A quote has two
 *         parts, an author and a quoteText. This bean class provides getters
 *         and setters for both, plus a toString()
 */
public class Quote {
	private String author;
	private String quoteText;
	private ArrayList<String> keywords;

	// Default constructor does nothing
	public Quote() {
	}

	// Constructor that assigns both strings
	public Quote(String author, String quoteText) {
		this.author = author;
		this.quoteText = quoteText;
	}

	// Constructor that assigns both strings
	public Quote(String author, String quoteText, ArrayList<String> keywords) {
		this.author = author;
		this.quoteText = quoteText;
		this.keywords = keywords;

		if (this.keywords.size() > 10)
			throw new InvalidParameterException("Quote can have a max of 10 keywords");
	}


	// Getter and setter for author
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	

	public ArrayList<String> getKeywords() {
		return keywords;
	}


	@Override
	public String toString() {
		return "Quote {" + "author='" + author + '\'' + ", quoteText='" + quoteText + '\'' + '}';
	}
   // Getter and setter for quoteText
   public String getQuoteText ()
   {
      return quoteText;
   }
   public void setQuoteText (String quoteText)
   {
      this.quoteText = quoteText;
   }
   

   public void setKeywords(ArrayList<String> keywords){
	   this.keywords = keywords;
   }

}
