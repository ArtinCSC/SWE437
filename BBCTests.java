
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;
public class BBCTests {
	
	private WebDriver driver = null;
	private String geckoDriverLocation = "C:\\Users\\Wave\\Downloads\\geckodriver-v0.20.0-win64\\geckodriver.exe";
	@Before
	public void setUp() {
		if(driver == null){
			System.setProperty("webdriver.gecko.driver",geckoDriverLocation);
			driver = new FirefoxDriver();
		}
		driver.get("https://cs.gmu.edu:8443/offutt/servlet/quotes.quoteserve");
	}
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
			System.out.println(results.get(x).getText());
			if(!results.get(x).getText().toLowerCase().contains(searchTerm.toLowerCase()))
					fail();
		}
		
	}
}
