package in.harshalshewale.driver;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Driver {

	public static void main(String[] args) throws Exception {
		executeTests();
	}

	public static void executeTests() throws Exception {

		Map<String, String> testClassesToRun = new HashMap<String, String>();
		testClassesToRun.put("MyFirstTest", "yes");
		testClassesToRun.put("MySecondTest", "No");
		testClassesToRun.put("MyThirdTest", "Yes");

		for (Map.Entry<String, String> classToRun : testClassesToRun.entrySet()) {

			if (classToRun.getValue().equalsIgnoreCase("yes")) {

				String className = classToRun.getKey();

				System.out.println("className from hashmap :  " + className);

				// java reflection

				Class<?> clazz = Class.forName("in.harshalshewale.test." + className);
				System.out.println("Class Name : " + clazz);
				Method method = clazz.getDeclaredMethod("runTest");
				Object object = clazz.getDeclaredConstructor().newInstance();
				method.invoke(object);

			}

		}

	}

}
