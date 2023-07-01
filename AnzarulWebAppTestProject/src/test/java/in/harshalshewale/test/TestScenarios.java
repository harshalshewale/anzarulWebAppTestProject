package in.harshalshewale.test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TestScenarios {

	public static void main(String[] args) throws Exception {
		executeTests();
	}

	public static void executeTests() throws Exception {

		Map<String, String> testClassesToRun = new HashMap<String, String>();
		testClassesToRun.put("MyFirstTest", "yes");
		testClassesToRun.put("MySecondTest", "yes");
		testClassesToRun.put("MyThirdTest", "no");

		for (Map.Entry<String, String> classToRun : testClassesToRun.entrySet()) {

			if (classToRun.getValue().equalsIgnoreCase("yes")) {

				String className = classToRun.getKey();

				System.out.println("className from hashmap :  " + className);

				Class<?> classToRunTest = Class.forName("in.harshalshewale.test." + className);

				Class<?> clazz = Class.forName("in.harshalshewale.test." + className);
				System.out.println("Class Name : " + clazz);
				Method m = clazz.getDeclaredMethod("runTest");
				Object t = clazz.getDeclaredConstructor().newInstance();
				Object o = m.invoke(t);

			}

		}

	}

}
