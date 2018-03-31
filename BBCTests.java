
import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;
public class BBCTests {
	
	private WebDriver driver = null;
	//private String geckoDriverLocation = "C:\\Users\\Wave\\Downloads\\geckodriver-v0.20.0-win64\\geckodriver.exe";
	@Before
	public void setUp() {
		if(driver == null){
			//System.setProperty("webdriver.gecko.driver",geckoDriverLocation);
			driver = new FirefoxDriver();
		}
		driver.get("https://cs.gmu.edu:8443/offutt/servlet/quotes.quoteserve");
	}
	@After
	public void cleanUp(){

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.close();	}
/*
	@Test
	public void test1(){
		WebElement searchText = driver.findElement(By.name("searchText"));
		List<WebElement> searchScopes = driver.findElements(By.name("searchScope"));
		for(int x = 0; x < searchScopes.size();x++){
			String scopeValue = searchScopes.get(x).getAttribute("value");
			if(scopeValue.equalsIgnoreCase("quote")){
				searchScopes.get(x).click();
				break;
			}
		}
		searchText.sendKeys("cow");
		searchText.submit();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement results = wait.until( ExpectedConditions.presenceOfElementLocated(By.tagName("dl")) );

		System.out.println(results.getText());
	}
*/
	public List<WebElement> checkIfSearchResultsExist(String waitTagName,String tagName) throws TimeoutException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement resultDL = wait.until( ExpectedConditions.presenceOfElementLocated(By.tagName(waitTagName)));
		List<WebElement> results = resultDL.findElements(By.tagName(tagName));
		return results;
	}
	@Test
	public void searchTextWithResultSearchScopeQuote(){
		String searchTerm = "software";
		WebElement searchText = driver.findElement(By.name("searchText"));
		List<WebElement> searchScopes = null;
		searchScopes = driver.findElements(By.name("searchScope"));
		for(int x = 0; x < searchScopes.size();x++){
			String scopeValue = searchScopes.get(x).getAttribute("value");
			if(scopeValue.equalsIgnoreCase("quote")){
				searchScopes.get(x).click();
				break;
			}
		}
		searchText.sendKeys(searchTerm);
		searchText.submit();
		List<WebElement> results = null;
		try{
		results = checkIfSearchResultsExist("dl","dt");
		}
		catch(TimeoutException e){
			fail();
		}
		if(results.size() == 0)
			fail();
		for(int x = 0; x < results.size();x++){
			//System.out.println(results.get(x).getText());
			assertTrue(results.get(x).getText().toLowerCase().contains(searchTerm));		
		}
		
        //check that the text of the last 'User Search' matches this search
        assertTrue( checkLastUserSearch( searchTerm ) );
	}
	@Test
	public void searchTextWithResultSearchScopeAuthor() {

		WebElement element = driver.findElement(By.name("searchText"));
		element.sendKeys("software");
		WebElement btt = driver.findElement(By.id("author"));
		btt.click();
		element.submit();
		List<WebElement> results = null;
		try{
		results = checkIfSearchResultsExist("dl","dd");
		}
		catch(TimeoutException e){
			fail();
		}
		if(results.size() == 0)
			fail();
		for(int x = 0; x < results.size();x++){
			assertTrue(results.get(x).getText().toLowerCase().contains("software"));	
		}
		
        //check that the text of the last 'User Search' matches this search
        assertTrue( checkLastUserSearch( "software" ) );
	}
	
	@Test
	public void searchTextWithResultSearchScopeBoth() {
		WebElement element = driver.findElement(By.name("searchText"));
		element.sendKeys("software");
		WebElement btt = driver.findElement(By.id("both"));
		btt.click();
		element.submit();
		List<WebElement> resultsText = null;
		List<WebElement> resultsAuthor = null;
		resultsText = checkIfSearchResultsExist("dl","dt");
		resultsAuthor = checkIfSearchResultsExist("dl","dd");
		if(resultsText.size() == 0 || resultsAuthor.size() == 0|| resultsAuthor.size() != resultsText.size())
			fail();
		for(int x = 0; x < resultsText.size();x++){
			assertTrue(resultsText.get(x).getText().toLowerCase().contains("software") || 
					resultsAuthor.get(x).getText().toLowerCase().contains("software") );	
		}
        //check that the text of the last 'User Search' matches this search
        assertTrue( checkLastUserSearch( "software" ) );
	}
	
		@Test
	public void searchTextNoneSearchScopeQuote() {

		WebElement element = driver.findElement(By.name("searchText"));
		element.clear();

		WebElement btt = driver.findElement(By.id("quote"));
		btt.click();
		element.submit();
		WebDriverWait wait = new WebDriverWait(driver, 1);

		WebElement results = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
		WebElement resultArea = results.findElement(By.tagName("tbody")).findElement(By.tagName("tr"))
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

	}
	
	@Test
	public void searchTextNoResultSearchScopeQuote()
	{
		//Expected test results:
		//		No description list html element. A paragraph element with “did not match any quotes” in its text.
		//
		//		The last link in the ‘User Searches’ lists is the test search, with matching search text and scope in the URL.

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
        //Wait for page to load
        WebDriverWait wait = new WebDriverWait(driver, 1);
        WebElement htmlTable = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
        //Checking test result
        String secondPElement = htmlTable.findElement( By.tagName("tbody")).findElement( By.tagName("tr"))
        		.findElement(By.tagName("td")).findElement(By.tagName("p")).getText();
        assertTrue( secondPElement.equals("Your search - " + searchString + " - did not match any quotes."));
        
        //check that the text of the last 'User Search' matches this search
        assertTrue( checkLastUserSearch( searchString ) );
        
	}
	
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
