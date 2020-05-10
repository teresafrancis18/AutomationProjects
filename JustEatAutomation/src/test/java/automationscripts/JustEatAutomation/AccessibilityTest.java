package automationscripts.JustEatAutomation;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.deque.axe.AXE;

public class AccessibilityTest
{
	WebDriver driver;
	URL axe_Script=Automation.class.getResource("axe.min.js");

	   
		@Test
		public void check_Accessibility()
		{
			AXE.inject(driver, axe_Script);
			JSONObject response=new AXE.Builder(driver, axe_Script).analyze();
			JSONArray violations=response.getJSONArray("violations");
			if(violations.length()==0)
			{
				Assert.assertTrue(true,"No violations found");
			}
			else
			{
				AXE.writeResults("AccessibilityResponse", response);
				Assert.assertTrue(false, AXE.report(violations));
			}
		}
}
