package automationscripts.JustEatAutomation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Automation 
{
	//driver variable is declared as a global variable
	org.openqa.selenium.WebDriver driver;
	
	//Method to complete the test setup before test
	@BeforeTest
	@Parameters("url")
	public void setup(String url)
	{
	driver=new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
	driver.manage().window().maximize();
	driver.get(url);
	}
	
	//Method for executing the test scenario
	@Test
	@Parameters("postcode")
	public void search(String postcode)
	{
	driver.findElement(By.name("postcode")).sendKeys(postcode);
	driver.findElement(By.cssSelector("button[data-test-id='find-restaurants-button']")).click();
	WebDriverWait wait=new WebDriverWait(driver,5);
	wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@data-test-id='openrestaurants']/section/a")));	
	//Get top ten restaurants from the list
	List<WebElement> allRestaurants=driver.findElements(By.xpath("//div[@data-test-id='openrestaurants']/section/a"));
	String click=Keys.chord(Keys.CONTROL,Keys.ENTER);
	for(int i=0;i<10;i++)
	{
		allRestaurants.get(i).sendKeys(click);
	}
	ArrayList<String> restaurant= new ArrayList<String>();
	ArrayList<String> postalcode=new ArrayList<String>();
	Set<String> windowNames= driver.getWindowHandles();
	Iterator<String> iter=windowNames.iterator();
	iter.next();
	while(iter.hasNext())
	{
		driver.switchTo().window(iter.next());
		restaurant.add(driver.findElement(By.xpath("//div[@class='details']/h1")).getText());
		postalcode.add(driver.findElement(By.xpath("//div[@class='details']/p[2]/span[3]")).getText());
	}
   System.out.println("List of restaurants with postal code: AR51 1AA");
   int count=0;
   for(int i=0;i<postalcode.size();i++)
   {
	   if(postalcode.get(i).equals(postcode))
	   {
		   count++;
		   System.out.println(restaurant.get(i));
	   }
		   
   }
   Assert.assertTrue(count>0,"No restaurants with the entered postal code");
}
	
	//Method for closing the browsers
	@AfterTest
	public void closetest()
	{
	driver.quit();
	}
	
	
	
	
	
	
	
	
	
	
}
