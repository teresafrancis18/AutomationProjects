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
	
	//Test for checking the URL launch
	@Test(priority=1)
	@Parameters("url")
	public void launch_JustEat(String url)
	{
		driver.get(url);
		Assert.assertEquals(driver.getCurrentUrl(),url);
	}
	
	//Test to verify whether error message is displayed when incorrect post code is entered
	@Test(priority=2)
	@Parameters("invalid_postcode")
	public void check_invalid_postcode(String postcode)
	{
		driver.findElement(By.name("postcode")).sendKeys(postcode);
		//Search button click
		driver.findElement(By.cssSelector("button[data-test-id='find-restaurants-button']")).click();
		if(driver.findElement(By.id("errorMessage")).isDisplayed())
		{
			Assert.assertTrue(true,"Error message not displayed when invalid postcode is entered ");
		}
		driver.findElement(By.name("postcode")).clear();
	}
	
	//Test for Searching restaurants on providing a post code
	@Test(priority=3)
	@Parameters("postcode")
	public void search_restaurants_postalcode(String postcode)
	{
	driver.findElement(By.name("postcode")).clear();
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
	ArrayList<String> rest_List= new ArrayList<String>();
	ArrayList<String> postcode_List=new ArrayList<String>();
	ArrayList<String> my_Area_Rests =new ArrayList<String>();
	Set<String> openWindows= driver.getWindowHandles();
	Iterator<String> iter=openWindows.iterator();
	String parent=iter.next();
	//Logic to retrieve the restaurant names and post code for first 10 items in list
	while(iter.hasNext())
	{
		driver.switchTo().window(iter.next());
		rest_List.add(driver.findElement(By.xpath("//div[@class='details']/h1")).getText());
		postcode_List.add(driver.findElement(By.xpath("//div[@class='details']/p[2]/span[3]")).getText());
	}
	driver.switchTo().window(parent);
	//Logic to retrieve the restaurants with the required post code
   for(int i=0;i<postcode_List.size();i++)
   {
	   if(postcode_List.get(i).equals(postcode))
	   {
		   my_Area_Rests.add(rest_List.get(i));
	   }		   
   }
   //To fail the test if there is no matching restaurants
   Assert.assertTrue(my_Area_Rests.size()>0,"No restaurants retrieved with entered post code.");
   //Logic to print all retrieved restaurants
   System.out.println("List of restaurants with postal code:"+postcode);
   for(int i=0;i<my_Area_Rests.size();i++)
   {
	   System.out.println(my_Area_Rests.get(i));
   }
   }
	
	//Test to search with Restaurant name
	@Test(priority=4)
	@Parameters("restaurant_Name")
	public void search_restaurant_name(String restaurant_Name)
	{
		String rest_Name;
		driver.findElement(By.id("dish-search")).sendKeys(restaurant_Name);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		List<WebElement> rests_ByName=driver.findElements(By.xpath("//div[@data-test-id='openrestaurants']/section"));
		for(int i=0;i<rests_ByName.size();i++)
		{
			rest_Name=rests_ByName.get(i).findElement(By.xpath("//a[@data-test-id='restaurant_info']/div[3]/h3")).getText();
			Assert.assertTrue(rest_Name.toLowerCase().contains(restaurant_Name),"Search is displaying incorrect results");
		}
		
	}
	//To close driver after test execution
	@AfterTest
	public void teardown()
	{
	driver.quit();
	}

	
}
