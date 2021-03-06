
import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;
public class BCCTests {
	
	private WebDriver driver = null;
	private String geckoDriverLocation = "C:\\Users\\Amilcar\\Documents\\GMU\\SWE 437\\Assignment 8\\geckodriver-v0.20.0-win64\\geckodriver.exe";
	@Before
	public void setUp() {
		if(driver == null){
			System.setProperty("webdriver.gecko.driver",geckoDriverLocation);
			driver = new FirefoxDriver();
		}
		driver.get("https://cs.gmu.edu:8443/offutt/servlet/quotes.quoteserve");
	}
	@After
	public void cleanUp(){

		/*try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		driver.close();	
	}

	/*=============================================
	 * Waits until an element with tag waitTagName
	 * appears or TimedOut. After finding waitTagName,
	 * return child elements of waitTagName with tag
	 * tagName
	 * @Parameters: String waitTagName, tag to wait for
	 * 				String tagName, tag to find
	 * @Return: List<WebElement>, list of web elements found 
	 ==============================================*/
	public List<WebElement> checkIfSearchResultsExist(String waitTagName,String tagName) throws TimeoutException {
		WebDriverWait wait = new WebDriverWait(driver, 10); // Waiter, will timeout after 10 seconds
		WebElement resultDL = wait.until( ExpectedConditions.presenceOfElementLocated(By.tagName(waitTagName))); // Wait for element waitTagName to appear
		List<WebElement> results = resultDL.findElements(By.tagName(tagName)); // Get all child elements of the waitTagName element that are tagName
		return results;
	}
	/*=============================================
	 * (Base case of the IDM's BCC tests)
	 * Tests the search results when the searchText
	 * exists within the web app's data and the
	 * searchScope is quote
	 ==============================================*/
	@Test
	public void searchTextWithResultSearchScopeQuote(){
		String searchTerm = "software"; // Search term we know exists
		WebElement searchText = driver.findElement(By.name("searchText")); // Get searchText element
		List<WebElement> searchScopes = driver.findElements(By.name("searchScope")); // Get the radio buttons
		for(int x = 0; x < searchScopes.size();x++){ // Loop through the radio buttons and click on the correct searchScope
			String scopeValue = searchScopes.get(x).getAttribute("value");
			if(scopeValue.equalsIgnoreCase("quote")){
				searchScopes.get(x).click();
				break;
			}
		}
		searchText.sendKeys(searchTerm); //Enter searchTerm into the textbox
		searchText.submit();			// Submit to get results
		List<WebElement> results = null; // Results from search
		try{
			results = checkIfSearchResultsExist("dl","dt"); //Get the results from search
		}
		catch(TimeoutException e){
			fail(); // Since we expect results the test fails if we can't find them
		}
		if(results.size() == 0) // Check if we actually have results rather then an empty shell
			fail();
		for(int x = 0; x < results.size();x++){ // Loop through results and make sure they are all correct
			assertTrue(results.get(x).getText().toLowerCase().contains(searchTerm));		
		}
		
        //check that the text of the last 'User Search' matches this search
        assertTrue( checkLastUserSearch( searchTerm ) );
	}
	/*=============================================
	 * Tests the search results when the searchText
	 * exists within the web app's data and the
	 * searchScope is author
	 ==============================================*/
	@Test
	public void searchTextWithResultSearchScopeAuthor() {
		WebElement element = driver.findElement(By.name("searchText")); //Get element for searchText
		element.sendKeys("software"); // Enter in search term
		WebElement btt = driver.findElement(By.id("author")); // Set searchScope to author
		btt.click();
		element.submit(); // Submit for results 
		List<WebElement> results = null;
		try{
		results = checkIfSearchResultsExist("dl","dd");//Get the results from search
		}
		catch(TimeoutException e){
			fail(); // Since we expect results the test fails if we can't find them
		}
		if(results.size() == 0) // Check if we actually have results rather then an empty shell
			fail();
		for(int x = 0; x < results.size();x++){// Loop through results and make sure they are all correct
			assertTrue(results.get(x).getText().toLowerCase().contains("software"));	
		}
		
        //check that the text of the last 'User Search' matches this search
        assertTrue( checkLastUserSearch( "software" ) );
	}
	/*=============================================
	 * Tests the search results when the searchText
	 * exists within the web app's data and the
	 * searchScope is both
	 ==============================================*/
	@Test
	public void searchTextWithResultSearchScopeBoth() {
		WebElement element = driver.findElement(By.name("searchText")); //Get element for searchText
		element.sendKeys("software"); // Enter in search term
		WebElement btt = driver.findElement(By.id("both")); // Set searchScope to both
		btt.click();
		element.submit(); // Submit for results 
		List<WebElement> resultsText = null; //Quote text search results
		List<WebElement> resultsAuthor = null; //Quote author search results
		try{
		resultsText = checkIfSearchResultsExist("dl","dt"); // Get quote text results
		resultsAuthor = checkIfSearchResultsExist("dl","dd"); // Get quote author results
		}
		catch(TimeoutException e){
			fail(); // Since we expect results the test fails if we can't find them
		}
		if(resultsText.size() == 0 || resultsAuthor.size() == 0|| resultsAuthor.size() != resultsText.size())
			fail(); // Makes sure none of the results are empty and they are the same size (since they come in pairs)
		for(int x = 0; x < resultsText.size();x++){// Loop through results and make sure either the text or author contains the search term
			assertTrue(resultsText.get(x).getText().toLowerCase().contains("software") || 
					resultsAuthor.get(x).getText().toLowerCase().contains("software") );	
		}
        //check that the text of the last 'User Search' matches this search
        assertTrue( checkLastUserSearch( "software" ) );
	}
	/*=============================================
	 * Tests the search results when the searchText
	 * doesn't exist and the searchScope is author
	 ==============================================*/
	@Test
	public void searchTextNoneSearchScopeQuote() {
		WebElement element = driver.findElement(By.name("searchText"));
		element.clear(); // Get the searchText and make sure its empty
		WebElement btt = driver.findElement(By.id("quote"));
		btt.click(); // Set searchScope to quote
		element.submit(); //Submit for results
		
        //wait for page to load
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			fail("Problem with thread.sleep in searchTextNoneSearchScopeQuote()");
		}
        
		WebElement resultArea = driver.findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"))
				.findElement(By.tagName("td"));
		try {
			resultArea.findElement(By.tagName("p"));
		} catch (NoSuchElementException e) {
			try {
			resultArea.findElement(By.tagName("dl"));
			}
			catch (NoSuchElementException e2) {
			 return;
			}
		}
		
		fail();
	}
	/*
	 * Tests for correct search result when using a string that
	 * 	is known to produce no results and scope is to quote.
	 */
	@Test
	public void searchTextNoResultSearchScopeQuote()
	{
		//Expected test results:
		//		A paragraph element with �did not match any quotes" in its text.
		//
		//		The last link in the "User Searches" lists is the test search, with matching search text.

		String searchString = "1------------------4";
        //Place focus on search text field
		WebElement searchTextField = driver.findElement(By.name("searchText"));
		//Populate search text field
		searchTextField.sendKeys(searchString);
		//Place focus on quote radio button
        WebElement searchScope = driver.findElement(By.id("quote"));
	    //Select quote radio button
        searchScope.click();
        //Submit form
        searchTextField.submit();
        //wait for page to load
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			fail("Problem with thread.sleep in searchTextNoResultSearchScopeQuote()");
		}
        
        //Checking test result
        String secondPElement = driver.findElement(By.tagName("table")).findElement( By.tagName("tbody")).findElement( By.tagName("tr"))
        		.findElement(By.tagName("td")).findElement(By.tagName("p")).getText();
        assertTrue( secondPElement.equals("Your search - " + searchString + " - did not match any quotes."));
        
        //check that the text of the last 'User Search' matches this search
        assertTrue( checkLastUserSearch( searchString ) );
        
	}
	/*
	 * Helper function, used to check if the last entry in 'User Searches"
	 * 	matches string searchText
	 */
	public boolean checkLastUserSearch( String searchText ){
		/*
		 * returns a boolean value:
		 * 		returns true if the last user search text matches parameter searchText,
		 * 		returns false otherwise.
		 */
		
		//get the list of User Searches
		WebElement outerTable = driver.findElement( By.tagName("table"));
        List<WebElement> listOfSearches = outerTable.findElements( By.tagName("ol")).get(0).findElements(By.tagName("li"));
		int numUserSearches = listOfSearches.size();
		//check for null
		if( numUserSearches == 0)
			return false;
		//return the last user search string
		return searchText.equals(listOfSearches.get(numUserSearches - 1).getText());
	}
}
