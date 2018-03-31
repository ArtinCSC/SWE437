
import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;
public class BBCTests {
	
	private WebDriver driver = null;
	@Before
	public void setUp() {
		if(driver == null){
			//System.setProperty("webdriver.gecko.driver","C:\Users\Amilcar\Documents\SWE 437\geckodriver-v0.20.0-win64\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		driver.get("https://cs.gmu.edu:8443/offutt/servlet/quotes.quoteserve");
	}
	@After
	public void cleanUp(){
		//driver.close();
	}
	
	@Test
	public void searchTextNoResultSeachScopeQuote()
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
        
        
	}
	
	public String getLastUserSearchResult(){
		String lastUserSearchResult = null;
		WebElement outerTable = driver.findElement( By.tagName("table"));
		WebElement outerTableData = outerTable.findElement( By.tagName( "tbody")).findElement( By.tagName("tr"));
		
		return lastUserSearchResult;
	}
}
