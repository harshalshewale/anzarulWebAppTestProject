package in.harshalshewale.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import in.harshalshewale.driver.Driver;

public class TestUtil {

	public static void takeScreenShot(WebDriver driver, String fileName) throws IOException {

		// create folder

		// Get root directory of where your program
		String currentDirectory = System.getProperty("user.dir");

		TakesScreenshot screenshot = ((TakesScreenshot) driver);

		File source = screenshot.getScreenshotAs(OutputType.FILE);

		File destination = new File(
				currentDirectory + "/target/screenshots/" + Driver.testExecutionID + "/" + fileName);

		FileUtils.copyFile(source, destination);

	}

}
