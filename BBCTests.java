
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
			System.setProperty("webdriver.gecko.driver","C:\\Users\\Wave\\Downloads\\geckodriver-v0.20.0-win64\\geckodriver.exe");
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
	@Test
	public void test1(){
		WebElement searchText = driver.findElement(By.name("searchText"));
		List<WebElement> searchScopes = driver.findElements(By.name("searchScope"));
		//WebElement submitButton = driver.findElement(By.name("submit"));
		for(int x = 0; x < searchScopes.size();x++){
			String scopeValue = searchScopes.get(x).getAttribute("value");
			if(scopeValue.equalsIgnoreCase("quote")){
				searchScopes.get(x).click();
				break;
			}
		}
		searchText.sendKeys("cow");
		searchText.submit();
		//submitButton.click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement results = wait.until( ExpectedConditions.presenceOfElementLocated(By.tagName("dl")) );

		//WebElement results = driver.findElement(By.tagName("dl"));
		System.out.println(results.getText());
	}
	
		@Test
	public void test2() {

		WebElement element = driver.findElement(By.name("searchText"));
		element.sendKeys("software");
		WebElement btt = driver.findElement(By.id("author"));
		btt.click();
		element.submit();
		WebDriverWait wait = new WebDriverWait(driver, 1);
		WebElement results = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("dd")));
		assertTrue(results.getText().toLowerCase().contains("software"));			
	}
	
	@Test
	public void test3() {

		WebElement element = driver.findElement(By.name("searchText"));
		element.sendKeys("software");
		WebElement btt = driver.findElement(By.id("both"));
		btt.click();
		element.submit();
		WebDriverWait wait = new WebDriverWait(driver, 1);
		WebElement results = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("dl")));
		assertTrue(results.getText().toLowerCase().contains("software"));			
	}
	
	@Test
	public void searchTextNoResultSeachScopeQuote()
	{
		String searchString = "1------------------4";
		
	}
}
