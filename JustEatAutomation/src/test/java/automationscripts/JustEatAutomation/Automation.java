package automationscripts.JustEatAutomation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
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
	//Driver variable is declared as a global variable
	WebDriver driver;
	
	//Setup the browser for test
	@BeforeTest
	public void browser_setup()
	{
	driver=new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
	driver.manage().window().maximize();
	}
	
	@Test//Test for checking the application launch
	@Parameters("url")
	public void launch_JustEat(String url)
	{
		driver.get(url);
	}
	
	//Test for Searching restaurants on providing a post code
	@Test(dependsOnMethods= {"launch_JustEat"})
	@Parameters("postcode")
	public void search_restaurants_postalcode(String postcode)
	{
	ArrayList<String> my_area_rest =new ArrayList<String>();
	//Input the post code to be searched
	driver.findElement(By.name("postcode")).sendKeys(postcode);
	//Search button click
	driver.findElement(By.cssSelector("button[data-test-id='find-restaurants-button']")).click();
	
	//To handle the delay in display of results
	WebDriverWait wait=new WebDriverWait(driver,5);
	wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@data-test-id='openrestaurants']/section/a")));	
	
	//Logic for retrieving top 10 restaurants from the list displayed
	List<WebElement> allRestaurants=driver.findElements(By.xpath("//div[@data-test-id='openrestaurants']/section/a"));
	String click=Keys.chord(Keys.CONTROL,Keys.ENTER);
	//Checking only the first 10 results to reduce the script execution time
	for(int i=0;i<10;i++)
	{
		allRestaurants.get(i).sendKeys(click);
	}
	ArrayList<String> restaurant= new ArrayList<String>();
	ArrayList<String> postalcode=new ArrayList<String>();
	Set<String> windowNames= driver.getWindowHandles();
	Iterator<String> iter=windowNames.iterator();
	iter.next();
	//Logic to retrieve the restaurant names and post code for first 10 items in list
	while(iter.hasNext())
	{
		driver.switchTo().window(iter.next());
		restaurant.add(driver.findElement(By.xpath("//div[@class='details']/h1")).getText());
		postalcode.add(driver.findElement(By.xpath("//div[@class='details']/p[2]/span[3]")).getText());
	}
	//Logic to retrieve the restaurants with the required post code
   for(int i=0;i<postalcode.size();i++)
   {
	   if(postalcode.get(i).equals(postcode))
	   {
		   my_area_rest.add(restaurant.get(i));
	   }		   
   }
   //To fail the test if there is no matching restaurants
   Assert.assertTrue(my_area_rest.size()>0,"No restaurants found matching the entered post code!");
   //Logic to print all retrieved restaurants
   System.out.println("List of restaurants with postal code:"+postcode);
   for(int i=0;i<my_area_rest.size();i++)
   {
	   System.out.println(my_area_rest.get(i));
   }
   }
	
		
	//To close driver after test execution
	@AfterTest
	public void closetest()
	{
	driver.quit();
	}
	
}
