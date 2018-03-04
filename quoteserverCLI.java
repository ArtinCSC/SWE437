
/* SWE437-001
 * Assignment 3
 * Developed by: Amilcar Martinez and Artin Melakian
 * 
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class quoteserverCLI {

	// quoteList will hold the list of quotes read in from quotes.xml
	// private static QuoteList quoteList;
	// searchList will hold the last five Strings that have been searched
	private static ArrayList<String> searchList = new ArrayList<String>();
	private static String quoteFileName = "quotes.xml";
	private static QuoteSaxParser parser = new QuoteSaxParser(quoteFileName);
	// Loads all quotes in quotes.xlm into quoteList
	protected static QuoteList quoteList = parser.getQuoteList();

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		Scanner menuInput = new Scanner(System.in);
		String searchText = null;
		ArrayList<String> tempKeywords = new ArrayList<String>();
		tempKeywords.add("keyword");

		// QuoteSaxParser parser = new QuoteSaxParser(quoteFileName);
		// // Loads all quotes in quotes.xlm into quoteList
		// quoteList = parser.getQuoteList();
		// Get random quote and print it
		Quote randomQuote = quoteList.getRandomQuote();
		printRandomQuote(randomQuote);

		// Main while loop, program runs until user triggers program Exit.
		// See Switch statement.
		while (true) {
			// Print the main menu and prompt for user input
			menu();
			int input = menuInput.nextInt();

			switch (input) {
			case 1: // User input is 1, print another random quote
				randomQuote = quoteList.getRandomQuote();
				printRandomQuote(randomQuote);
				break;

			case 2: // user input is 2, prompt user for search string, search by author
				searchText = helper(s, "Enter the author's name to search:\n");
				maintainSearchList(searchText);
				search(searchText, QuoteList.SearchAuthorVal);
				break;

			case 3: // user input is 3, prompt user for search string, search by quote
				searchText = helper(s, "Enter your quote to search:\n");
				maintainSearchList(searchText);
				search(searchText, QuoteList.SearchTextVal);
				break;

			case 4: // user input is 4, prompt user for search string, search by both quote and
					// author
				searchText = helper(s, "Enter your quote/author's name to search:\n");
				maintainSearchList(searchText);
				search(searchText, QuoteList.SearchBothVal);
				break;
				
			case 5: // user input is 5, prompt user for search string, search Keywords
				
				searchText = helper(s, "Enter the Keyword:\n");
				maintainSearchList(searchText);
				search(searchText, QuoteList.SearchKeyword );
				break;
				
			case 6: // user input is 6, prompt user to enter quote and author, append to list if
					// the quote doesn't already exist.
				System.out.println("Enter the quote and author separately.");
				appendQuote(helper(s, "Enter the quote:"), helper(s, "Enter the author:"), helperEnterKeywords() );
				break;

			case 7: // user input is 7, exit the program
				System.exit(0);
				break;
			case 8:// user input 8 - 12 corresponds to the "RECENT SEARCH" menu. Take the recent
					// search the user selected and display its results again.
			case 9:
			case 10:
			case 11:
			case 12:
				if ((input - 8) < searchList.size())
				// Subtract 7 from input, its the actual location of the recent search
				// selected by the user in recent searches string list.
				// If the actual location selected exists, print it, else print error.
				{
					search(searchList.get(input - 8), 4);
					maintainSearchList(searchList.get(input - 8));
				} else {
					System.out.println("Invalid selection, empty search string.");
				}
				break;

			} // end switch

			System.out.println();

		} // end while loop
		
	} // end of main
	
	private static ArrayList<String> helperEnterKeywords()
	{
		Scanner inputNumKeywords = new Scanner(System.in);
		ArrayList<String> tempStringList = new ArrayList<String>();
		String tempString;
		int numQuotes;
		boolean keepAsking;
		//prompt user for the number of keywords that need to be entered.
		//If invalid input entered, keep prompting
		do{
			System.out.print("How many keywords do you want to enter (0-10): ");
			numQuotes = inputNumKeywords.nextInt();
			if( numQuotes < 0 || numQuotes > 10){
				System.out.println("Enter a number between 0 and 10. Try again");
				keepAsking  = true;
			}else
				keepAsking = false;
		}while( keepAsking );
		//prompt user to enter keywords.
		
		Scanner newUserInput = new Scanner(System.in);
		for(int i = 0; i < numQuotes ; i++){
		
			tempString = helper(newUserInput, "Enter keyword:");
			tempStringList.add(tempString);
		}

		return tempStringList;
	}
	
	/**
	 * @purpose To print the program banner and the random quote passed as argument.
	 * 
	 * @param quote
	 *            Object Quote type
	 */
	private static void printRandomQuote(Quote quote) {

		System.out.println(
				"         The GMU Quote Generator" + "\n_______________________________________________________");
		System.out.println("Random quote of the day\n");
		System.out.println(quote.getQuoteText());
		System.out.println("                  " + quote.getAuthor());
	}

	/**
	 * @purpose Maintains the previous five searches in an array list searchList.
	 * 
	 * @param prevSearch
	 *            String type, the user's input.
	 */
	private static void maintainSearchList(String prevSearch) {

		searchList.add(prevSearch);
		if (searchList.size() > 5)
			searchList.remove(0);

	}

	/**
	 * @purpose prompts the user for input string.
	 * @param s
	 *            Scanner type to get the user's input.
	 * @param message
	 *            String type, the message that will be display to user to get the
	 *            input.
	 * @return String type of the user's prompts.
	 */
	private static String helper(Scanner s, String message) {

		System.out.print(message);
		String searchText = "";
		searchText = s.nextLine();
		return searchText;
	}

	/**
	 * @purpose to append a new quote to the list of the existing quote list.
	 * 
	 * @param quote
	 *            String type, user input quote to add to the existing quote list.
	 * @param author
	 *            String type, user input author's name of quote, if it is null, the
	 *            unknown author will be added to list.
	 */
	protected static void appendQuote(String quote, String author, ArrayList<String> keywords) {

		// check if user didn't enter a quote, or entered all spaces
		if (quote.equals(null) || quote.trim().equals("")) {
			System.out.println("Sorry:(\nCould not catch the quote!\nPlease try again!");
			return;
		}

		// If existing quotes are similar to the user provided quote, check if one
		// of the similar quotes is exactly the same. If the quote already exists
		// notify the user and don't add quote and author.
		QuoteList searchRes = quoteList.search(quote, QuoteList.SearchTextVal);
		if (searchRes.getSize() != 0) {
			Quote quoteTmp;
			for (int i = 0; i < searchRes.getSize(); i++) {

				// If the quote already exists, regardless of case, don't add the quote.
				quoteTmp = searchRes.getQuote(i);
				if (quoteTmp.getQuoteText().equalsIgnoreCase(quote)) {
					System.out.println("The quote already exist");
					return;
				}
			}
		} // end of if statement

		// check if author was left blank, or if it is only made of spaces.
		// when either condition is true, set author to Unknown
		if (author.equals(null) || author.trim().equals(""))
			author = "Unknown";

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(quoteFileName);

			// Get the root element
			// Node quote_list = doc.getFirstChild();

			// Get the quote-list element by tag name directly
			Node attQuote_list = doc.getElementsByTagName(QuoteSaxHandler.QuoteListElem).item(0);
			Element attQuote = doc.createElement(QuoteSaxHandler.QuoteElem);

			// append a new node (quote) to quote attribute
			Element quote_text = doc.createElement(QuoteSaxHandler.QuoteTextElem);
			quote_text.appendChild(doc.createTextNode(quote));
			attQuote.appendChild(quote_text);

			// append a new node (author) to quote attribute
			Element quote_author = doc.createElement(QuoteSaxHandler.QuoteAuthorElem);
			quote_author.appendChild(doc.createTextNode(author));
			attQuote.appendChild(quote_author);

			if (keywords.size() > 0)
				for (int k = 0; k < keywords.size(); k++) {
					String key = keywords.get(k);
					// append a new node keywords to quote attribute
					Element keyword = doc.createElement(QuoteSaxHandler.QuoteKeywordElem);
					keyword.appendChild(doc.createTextNode(key));
					attQuote.appendChild(keyword);
				}

			attQuote_list.appendChild(attQuote);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(quoteFileName));
			transformer.transform(source, result);

			System.out.println("\nThe new quote was added to the list.");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
		// read quotes.xml
		QuoteSaxParser parser = new QuoteSaxParser(quoteFileName);
		quoteList = parser.getQuoteList();

	}

	/**
	 * @purpose To take the users search string and context and print results.
	 * 
	 * @param searchText
	 *            String type the user's input to search for specific quote
	 * @param searchScopeInt
	 *            Integer type the scope of search number 2 search by author, number
	 *            3 search by quote, and number 4 search by both (author and quote)
	 */
	private static void search(String searchText, int searchScopeInt) {

		if (searchText != null && !searchText.equals("")) { // Received a search request
//			switch (searchScopeInt) {
//			case 2:
//				searchScopeInt = QuoteList.SearchAuthorVal;
//				break;
//			case 3:
//				searchScopeInt = QuoteList.SearchTextVal;
//				break;
//			case 4:
//				searchScopeInt = QuoteList.SearchBothVal; 
//				break;
//			case 5:
//				searchScopeInt = QuoteList.SearchKeyword;
//				break;
//			} // end of switch statement

			QuoteList searchRes = quoteList.search(searchText, searchScopeInt);
			Quote quoteTmp;
			if (searchRes.getSize() == 0) {
				System.out.println("Your search - " + searchText + " - did not match any quotes.");
			} else {
				System.out.println("\n");
				for (int i = 0; i < searchRes.getSize(); i++) {
					quoteTmp = searchRes.getQuote(i);
					System.out.println(quoteTmp.getQuoteText());
					System.out.println(" 			" + quoteTmp.getAuthor());
				}
			}
		} // end of primary if statement

	}

	/**
	 * @purpose Prints the main menu and recent searches menu side by side. Move the
	 *          recent search string list, which can have a max of 5 elements, to a
	 *          temp string list. if the search list doesn't yet have 5 elements,
	 *          the temp string list will contain the string "(blank)" to make up
	 *          for the lack of valid saved searches.
	 */
	private static void menu() {

		ArrayList<String> tempList = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			if (i < searchList.size()) {
				tempList.add(searchList.get(i));
			} else {
				tempList.add("(blank)");
			}
		}
		System.out.println("_______________________________________________________");
		System.out.print(String.format("%-40s%s", "MAIN MENU", "RECENT SEARCHES") + "\n"
				+ String.format("%-40s%s%s", "1. Another random quote", "8. ", tempList.get(0)) + "\n"
				+ String.format("%-40s%s%s", "2. Search a quote by author", "9. ", tempList.get(1)) + "\n"
				+ String.format("%-40s%s%s", "3. Search a quote by quote", "10. ", tempList.get(2)) + "\n"
				+ String.format("%-40s%s%s", "4. Search a quote by both", "11. ", tempList.get(3)) + "\n"
				+ String.format("%-40s%s%s", "5. Search by Keyword", "12. ", tempList.get(4)) + "\n"
				+ String.format("%-40s", "6. Add a quote") + "\n"
				+ String.format("%-40s%s", "7. Exit ", " ") + "\n" + ">> ");
	}

}
