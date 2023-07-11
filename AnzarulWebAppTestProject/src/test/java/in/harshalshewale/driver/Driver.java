package in.harshalshewale.driver;

import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import in.harshalshewale.util.DatabaseUtil;
import in.harshalshewale.util.FileUtil;
import in.harshalshewale.util.JsonUtil;

public class Driver {

	public static String testExecutionID = null;
	public static String TestSecnarioURL = null;
	public static String environment = null;
	public static String testCaseName = null;
	public static String executeMode = null;

	private static final Logger LOGGER = Logger.getLogger(DatabaseUtil.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	public static void main(String[] args) throws Exception {
		executeTests();
	}

	public static void executeTests() throws Exception {

		// Create Execution ID with current time stamp
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		testExecutionID = dateFormat.format(timestamp);
		LOGGER.info("Test Execution ID: " + testExecutionID);

		environment = FileUtil.readPropertyFile("test", "environment");
		LOGGER.info("Test Environment: " + environment);

		if (environment.equalsIgnoreCase("dev")) {

			TestSecnarioURL = FileUtil.readPropertyFile("test", "test_scenarios_dev");

		} else if (environment.equalsIgnoreCase("uat")) {

			TestSecnarioURL = FileUtil.readPropertyFile("test", "test_scenarios_uat");
		}

		LOGGER.info("Test Scenario JSON URL: " + TestSecnarioURL);

		URL url = new URL(TestSecnarioURL);

		String testSecnarioJson = JsonUtil.readJsonFromURL(url);

		LOGGER.info("Test Scenario JSON: " + testSecnarioJson);

		// Parse JSON and put TestCaseName and Execute Mode to HashMap

		String totalNumberOfTestCases = JsonUtil.getJsonValue(testSecnarioJson, "$.data.length()");
		int totalNumberOfTestCasesInInt = Integer.parseInt(totalNumberOfTestCases);

		LOGGER.info("Total Number Of Test Cases: " + totalNumberOfTestCasesInInt);

		Map<String, String> tests = new HashMap<String, String>();

		for (int i = 0; i < totalNumberOfTestCasesInInt; i++) {

			testCaseName = JsonUtil.getJsonValue(testSecnarioJson, "$.data[" + i + "].TestCaseName");
			executeMode = JsonUtil.getJsonValue(testSecnarioJson, "$.data[" + i + "].Execute");
			tests.put(testCaseName, executeMode);

		}

		// Iterate over HashMap and call tests which has execute mode "Yes"

		LOGGER.info("Tests Cases Stored in HashMap: " + tests);

		for (Map.Entry<String, String> testToRun : tests.entrySet()) {

			if (testToRun.getValue().equalsIgnoreCase("yes")) {

				String currentTestCase = testToRun.getKey();

				LOGGER.info("Current Executing Test Case : " + currentTestCase);

				// java reflection
				Class<?> clazz = Class.forName("in.harshalshewale.test." + currentTestCase);
				System.out.println("Class Name : " + clazz);
				Method method = clazz.getDeclaredMethod("runTest");
				Object object = clazz.getDeclaredConstructor().newInstance();
				method.invoke(object);

			}

		}

	}

}
