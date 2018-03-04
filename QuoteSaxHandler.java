
import java.util.ArrayList;

import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import org.xml.sax.*;

/**
 * SAX handler for SAX Parser
 * 
 * @author Mongkoldech Rajapakdee & Jeff Offutt Date: Nov 2009 Uses the sax
 *         parser to parse an XML file into a list of quotes
 */

public class QuoteSaxHandler extends DefaultHandler {
	private QuoteList quoteList = new QuoteList();
	private Quote quoteTmp = null; // temporary Quote
	private String currentElement = null; // current element name
	private ArrayList<String> keywordTemp = null;

	// Node names in XML file
	public static final String QuoteListElem = "quote-list";
	public static final String QuoteElem = "quote";
	public static final String QuoteAuthorElem = "author";
	public static final String QuoteTextElem = "quote-text";
	public static final String QuoteKeywordElem = "keyword";

	public QuoteSaxHandler() {
		super();
	}

	public QuoteList getQuoteList() {
		return quoteList;
	}

	@Override
	public void startDocument() {
		// System.out.println ("Start document"); // For testing
	}

	@Override
	public void endDocument() {
		// System.out.println ("End document"); // For testing
	}

	@Override
	public void startElement(String uri, String name, String qName, Attributes atts) {
		if (qName.equalsIgnoreCase(QuoteListElem)) {
			currentElement = QuoteListElem;
		} else if (qName.equalsIgnoreCase(QuoteElem)) {
			currentElement = QuoteElem;
			quoteTmp = new Quote();
			keywordTemp = new ArrayList<String>();
		} else if (qName.equalsIgnoreCase(QuoteAuthorElem)) {
			currentElement = QuoteAuthorElem;
		} else if (qName.equalsIgnoreCase(QuoteTextElem)) {
			currentElement = QuoteTextElem;
		} else if (qName.equalsIgnoreCase(QuoteKeywordElem)){
			currentElement = QuoteKeywordElem;
		}
	}

	@Override
	public void endElement(String uri, String name, String qName) {
		if (qName.equalsIgnoreCase(QuoteElem)) {
			quoteTmp.setKeywords(keywordTemp);
			quoteList.setQuote(quoteTmp);
			quoteTmp = null;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) {
		String value = new String(ch, start, length);
		if (!value.trim().equals("")) {
			if (currentElement.equalsIgnoreCase(QuoteTextElem)) {
				quoteTmp.setQuoteText(value);
			} else if (currentElement.equalsIgnoreCase(QuoteAuthorElem)) {
				quoteTmp.setAuthor(value);

			} else if (currentElement.equalsIgnoreCase(QuoteKeywordElem)){
				keywordTemp.add(value);
			}
		}
	}

}
