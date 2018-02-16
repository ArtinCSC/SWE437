import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QuoteListTest {

//	@Test
//	void testSearch() {
//		QuoteList qSearch = new QuoteList();
//		
//		qSearch = qSearch.search(null, 0);
//		assertNotNull(qSearch);
//		
//		qSearch = qSearch.search(null, 1);
//		assertNotNull(qSearch);
//		
//		qSearch = qSearch.search(null, 2);
//		assertNotNull(qSearch);
//		
//		qSearch = qSearch.search("", 0);
//		assertNotNull(qSearch);
//		
//		qSearch = qSearch.search("", 1);
//		assertNotNull(qSearch);
//		
//		qSearch = qSearch.search("", 2);
//		assertNotNull(qSearch);
//		
//		qSearch = qSearch.search(" ", 0);
//		assertNotNull(qSearch);
//		
//		qSearch = qSearch.search(" ", 1);
//		assertNotNull(qSearch);
//		
//		qSearch = qSearch.search(" ", 2);
//		assertNotNull(qSearch);
//		
//		qSearch = qSearch.search("gfgjdafj", 0);
//		assertNotNull(qSearch);
//		
//		qSearch = qSearch.search("gfgjdafj", 1);
//		assertNotNull(qSearch);
//		
//		qSearch = qSearch.search("gfgjdafj", 2);
//		assertNotNull(qSearch);
//		
//		
//		
//		
//		QuoteList qSearch2 = new QuoteList();
//
//		qSearch2.setQuote(new Quote("A right is not what someone gives you; it's what no one can take from you."
//				, "Ramsey Clark"));
//		
////		qSearch = qSearch.search(null, 0);
////		
////		assertEquals(qSearch2, qSearch);
////		
////		qSearch = qSearch.search(null, 1);
////		assertEquals(qSearch2, qSearch);
////		
////		qSearch = qSearch.search(null, 2);
////		assertEquals(qSearch2, qSearch);
//		
//		QuoteList qSearch3 = new QuoteList();
//		
//		
//		qSearch = qSearch.search("Ramsey", 0);
//		assertEquals(qSearch2, qSearch);
//		
////		qSearch = qSearch.search("Ramsey", 1);
////		assertEquals(qSearch2, qSearch);
////		
////		qSearch = qSearch.search("Ramsey", 2);
////		assertEquals(qSearch2, qSearch);
//	}

	@Test
	void testSearchAuthor() {
		QuoteList quoteList = new QuoteList();
		Quote unrelatedQuote = new Quote("Unknown", "This quote is unrelated to the search");
		Quote textQuote = new Quote("H. L. Mencken", "My name is John Keats");
		Quote authorQuote = new Quote("John Keats", "I am H. L. Mencken");
		Quote authorQuote2 = new Quote("John Keats", "I am a second John Keats");
		//Test if search will find an unrelated quote
		quoteList.setQuote(unrelatedQuote);
		QuoteList searchResult = quoteList.search("John Keats", QuoteList.SearchAuthorVal);
		assertEquals(0, searchResult.getSize(), "Found quote while searching through unrelated quote by author");
		//Test if search will find a quote with the search term in the quote text
		quoteList.setQuote(textQuote);
		searchResult = quoteList.search("John Keats", QuoteList.SearchAuthorVal);
		assertEquals(0, searchResult.getSize(), "Found quote while searching through quote with author name in the quote");
		//Test if search will find the a quote that has an author that matches the search term
		quoteList.setQuote(authorQuote);
		searchResult = quoteList.search("John Keats", QuoteList.SearchAuthorVal);
		assertEquals(1, searchResult.getSize(), "Did not found quote while searching through quote with author name in the author");
		assertEquals(searchResult.getQuote(0).getQuoteText(), "I am H. L. Mencken", "Expected quote text not found");
		assertEquals(searchResult.getQuote(0).getAuthor(), "John Keats", "Expected author not found");
		//Test if search will find multiple quotes that have an author that matches the search term
		quoteList.setQuote(authorQuote2);
		searchResult = quoteList.search("John Keats", QuoteList.SearchAuthorVal);
		assertEquals(2, searchResult.getSize(), "Did not found quote while searching through quote with author name in the author");
		assertEquals(searchResult.getQuote(0).getQuoteText(), "I am H. L. Mencken", "Expected quote text not found");
		assertEquals(searchResult.getQuote(0).getAuthor(), "John Keats", "Expected author not found");
		assertEquals(searchResult.getQuote(1).getQuoteText(), "I am a second John Keats", "Expected quote text not found");
		assertEquals(searchResult.getQuote(1).getAuthor(), "John Keats", "Expected author not found");
	}
	
	@Test
	void testSearchQuote() {
		QuoteList quoteList = new QuoteList();
		Quote unrelatedQuote = new Quote("Unknown", "This quote is unrelated to the search");
		Quote textQuote = new Quote("John Keats", "My name is  H. L. Mencken");
		Quote authorQuote = new Quote("H. L. Mencken", "I am John Keats");
		Quote authorQuote2 = new Quote("John Keats", "I am a second John Keats");
		//Test if search will find an unrelated quote
		quoteList.setQuote(unrelatedQuote);
		QuoteList searchResult = quoteList.search("John Keats", QuoteList.SearchTextVal);
		assertEquals(0, searchResult.getSize(), "Found quote while searching through unrelated quote by text");
		//Test if search will find a quote with the search term in the quote's author
		quoteList.setQuote(textQuote);
		searchResult = quoteList.search("John Keats", QuoteList.SearchTextVal);
		assertEquals(0, searchResult.getSize(), "Found quote while searching through quote with search term in the quote's author");
		//Test if search will find the a quote that has an author that matches the search term
		quoteList.setQuote(authorQuote);
		searchResult = quoteList.search("John Keats", QuoteList.SearchTextVal);
		assertEquals(1, searchResult.getSize(), "Did not found quote while searching through quote with author name in the author");
		assertEquals(searchResult.getQuote(0).getQuoteText(), "I am John Keats", "Expected quote text not found");
		assertEquals(searchResult.getQuote(0).getAuthor(), "H. L. Mencken", "Expected author not found");
		//Test if search will find multiple quotes that have an author that matches the search term
		quoteList.setQuote(authorQuote2);
		searchResult = quoteList.search("John Keats", QuoteList.SearchTextVal);
		assertEquals(2, searchResult.getSize(), "Did not found quote while searching through quote with author name in the author");
		assertEquals(searchResult.getQuote(0).getQuoteText(), "I am John Keats", "Expected quote text not found");
		assertEquals(searchResult.getQuote(0).getAuthor(), "H. L. Mencken", "Expected author not found");
		assertEquals(searchResult.getQuote(1).getQuoteText(), "I am a second John Keats", "Expected quote text not found");
		assertEquals(searchResult.getQuote(1).getAuthor(), "John Keats", "Expected author not found");

	}
	
	@Test
	void testSearchBoth() {
		QuoteList quoteList = new QuoteList();

	}
	
	@Test
	void testSearchEmptyQuoteList() {
		QuoteList quoteList = new QuoteList();
		QuoteList searchResult = new QuoteList();

		searchResult = quoteList.search("", QuoteList.SearchAuthorVal);
		assertEquals(0, searchResult.getSize(), "Test search empty quoteList in Mode author");
		
		searchResult = quoteList.search("", QuoteList.SearchTextVal);
		assertEquals(0, searchResult.getSize(), "Test search empty quoteList in Mode text");
		
		searchResult = quoteList.search("", QuoteList.SearchBothVal);
		assertEquals(0, searchResult.getSize(), "Test search empty quoteList in Mode both");

	}
	
}
