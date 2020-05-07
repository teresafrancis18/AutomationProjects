# Context
A sample Test Suite to demonstrate Selenium test automation of a website using the below:
 - Selenium Webdriver
 - TestNG
 - Maven

# Scenario to be Tested and Approach used for Testing:
Scenario:
JUST EAT has a website available at https://www.just-eat.co.uk/ that you can use to find takeaway restaurants in a postcode area.

Feature: Use the website to find restaurants
•	So that I can order food
•	As a hungry customer
•	I want to be able to find restaurants in my area
 
 Scenario: Search for restaurants in an area
•	Given I want food in "AR51 1AA"
•	When I search for restaurants
•	Then I should see some restaurants in "AR51 1AA"

# Approach Used for Testing:
First 10 entries of the retrieved list of restaurants is verified for their postal code and those with exact match of post code is picked assuming that exact match records will be displayed first in the list.
Test will be failed if none of the first 10 entries have exact match to the post code entered.
Any restaurants with matching post code after 10th entry is not considered as it should be displayed at top and is not fulfilling the requirement.
This approach helped in reducing the test execution time considerably when compared to checking all restaurants with postal code AR51 1AA.

# Tests included in the Suite
1. Test Launch of website
2. Test for Accessibility issues
3. Test to search restaurants with valid postcode
4. Test for error message when invalid postcode is entered


# Setup Instructions
  Pre-requisites:
- chromedriver.exe
- Java 1.8 or above
- Maven 3 or above

# Test Execution Option 1 - Command Line and Maven commands

1. Navigate to the working directory where the project is downloaded.
> cd filepath
2. Enter the maven instruction as shown below
> mvn clean test -Dwebdriver.chrome.driver="path of chromedriver.exe"

Test Results will be published as .html in target folder in the below path:
> target/surefire-reports/emailable-report.html. Sample File id provided.


# Test Execution Option 2 - using Eclipse IDE and TestNG
Pre-requisite:
Eclipse IDE

Steps:
1. Import the project to Eclipse IDE
2. Set Run Configurations
> testng.xml->Run As-> Run Configurations
3. Enter Arguments->VM arguments to the below value
> -Dwebdriver.chrome.driver= "File path of chromedriver.exe"
4. Run the Test
> testng.xml->Run As->TestNG Suite

Results can be viewed in TestNG console.

# Useful Links
Download the required .exe from the below links:
| Installables | Link |
| ------ | ------ |
| ChromeDriver | [https://chromedriver.chromium.org/downloads]|
| Eclispe | [https://www.eclipse.org/downloads/packages/]|
| Java | [https://www.oracle.com/java/technologies/javase-downloads.html]|

