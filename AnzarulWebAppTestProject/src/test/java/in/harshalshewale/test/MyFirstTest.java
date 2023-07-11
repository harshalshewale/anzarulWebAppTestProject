package in.harshalshewale.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import in.harshalshewale.driver.Driver;
import in.harshalshewale.util.DatabaseUtil;
import in.harshalshewale.util.FileUtil;
import in.harshalshewale.util.JsonUtil;
import in.harshalshewale.util.TestUtil;

public class MyFirstTest extends BaseTest {

	private static final Logger LOGGER = Logger.getLogger(MyFirstTest.class);

	private static int TEST_ID = 001;
	private static String TEST_SUITE_NAME = "Register";
	private static String TEST_CASE_NAME = "MyFirstTest";
	private static String TEST_DESCRIPTION = "Register user with valid data";

	public MyFirstTest() {

		super(TEST_ID, TEST_SUITE_NAME, TEST_CASE_NAME, TEST_DESCRIPTION);

	}

	@Override
	public void runTest() throws Exception {

		try {

			LOGGER.info("Executing Test : MyFirstTest");

			// Get Test Date for test case
			String testDataFile = JsonUtil.readTestDataJsonFile("MyFirstTest.json");

			// Get WebDriver Object
			WebDriver driver = getWebDriver();

			// Open Web Page
			driver.get("https://www.selenium.dev/selenium/web/web-form.html");

			Thread.sleep(5000);

			// Get title of page
			String title = driver.getTitle();
			assertEquals("Web form", title);
			TestUtil.takeScreenShot(driver, TEST_CASE_NAME + "_home_page_title.png");

			// Fill the form (Register User)
			String myTextIdLocator = FileUtil.readLocators("homePage", "home.id.myTextId");
			String myTextIdTestData = JsonUtil.getJsonValue(testDataFile, "$.data[0].textInput");
			driver.findElement(By.id(myTextIdLocator)).sendKeys(myTextIdTestData);

			String passwordLocator = FileUtil.readLocators("homePage", "home.name.password");
			String passwordTestData = JsonUtil.getJsonValue(testDataFile, "$.data[0].password");
			driver.findElement(By.name(passwordLocator)).sendKeys(passwordTestData);
			TestUtil.takeScreenShot(driver, TEST_CASE_NAME + "_register_user_form.png");

			driver.quit();

			// write result in to database
			DatabaseUtil.insertTestResult(Driver.testExecutionID, TEST_SUITE_NAME, TEST_ID, TEST_CASE_NAME, "PASS",
					"No Comment");

		} catch (Exception e) {
			driver.quit();
			DatabaseUtil.insertTestResult(Driver.testExecutionID, TEST_SUITE_NAME, TEST_ID, TEST_CASE_NAME, "FAIL",
					e.getMessage());
			e.printStackTrace();
		} catch (AssertionError a) {
			driver.quit();
			DatabaseUtil.insertTestResult(Driver.testExecutionID, TEST_SUITE_NAME, TEST_ID, TEST_CASE_NAME, "FAIL",
					a.getMessage());
			a.printStackTrace();
		}

	}

}
