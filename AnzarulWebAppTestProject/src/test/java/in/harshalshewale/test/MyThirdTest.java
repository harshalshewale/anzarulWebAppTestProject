package in.harshalshewale.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import in.harshalshewale.util.FileUtil;
import in.harshalshewale.util.JsonUtil;

public class MyThirdTest extends BaseTest {

	private static String testId;
	private static String testSuiteName;
	private static String testCaseName;
	private static String testDescription;

	public MyThirdTest() {

		super(testId, testSuiteName, testCaseName, testDescription);

	}

	@Override
	public void runTest() throws Exception {

		// this is actual Selenium test

		System.out.println("Im inside my third test of Selenium");

		String testDataFile = JsonUtil.readTestDataJsonFile("MySecondTest.json");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");

		WebDriver driver = new ChromeDriver(options);
		driver.get("https://www.selenium.dev/selenium/web/web-form.html");

		Thread.sleep(5000);

		// Get title

		String title = driver.getTitle();
		assertEquals("Web form", title);

		String myTextIdLocator = FileUtil.readLocators("homePage", "home.id.myTextId");
		String myTextIdTestData = JsonUtil.getJsonValue(testDataFile, "$.data[0].textInput");
		driver.findElement(By.id(myTextIdLocator)).sendKeys(myTextIdTestData);

		String passwordLocator = FileUtil.readLocators("homePage", "home.name.password");
		String passwordTestData = JsonUtil.getJsonValue(testDataFile, "$.data[0].password");
		driver.findElement(By.name(passwordLocator)).sendKeys(passwordTestData);

		driver.quit();

	}

}
