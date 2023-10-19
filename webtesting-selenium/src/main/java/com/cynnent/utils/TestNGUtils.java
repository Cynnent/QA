package com.cynnent.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.cynnent.extentfactory.ExtentFactory;

public class TestNGUtils {

	private static final Logger logger = LogManager.getLogger(TestNGUtils.class);

	public static <T> void assertEquals(T actual, T expected, String message) {
		ExtentTest extentTest = ExtentFactory.getInstance().getExtent();
		try {
			Assert.assertEquals(actual, expected, message);
			logger.info("Assertion passed: " + actual + " is equal to " + expected);
			extentTest.pass(message + " :Assertion passed: " + actual + " is equal to " + expected);
		} catch (AssertionError e) {
			logger.error("Assertion failed: " + actual + " is not equal to " + expected, e);
			extentTest.fail(e.getMessage());
			try {
				extentTest.addScreenCaptureFromBase64String(Takescreenshot.getbase64screenshot(),message);
			} catch (IOException ex) { // TODO Auto-generated catch block
				logger.error("Failed to capture screenshot", ex);
			}
			throw e;
		}
	}

//    public static void assertEquals(String actual, String expected, String message) {
//        try {
//            Assert.assertEquals(actual, expected, message);
//            logger.info("Assertion passed: " + actual + " is equal to " + expected);
//        } catch (AssertionError e) {
//        	logger.error("Assertion failed: " + actual + " is not equal to " + expected, e);
//        	throw e;
//        }
//    }

	public static void assertGreaterThan(int actual, int expected) {
		try {
			Assert.assertTrue(actual > expected,
					"Expected value to be greater than " + expected + ", but found " + actual);
			logger.info("Assertion passed: " + actual + " is greater than " + expected);
		} catch (AssertionError e) {
			logger.error("Assertion failed: " + actual + " is not greater than " + expected, e);
			throw e;
		}
	}

	public static void assertGreaterThan(double actual, double expected) {
		try {
			Assert.assertTrue(actual > expected,
					"Expected value to be greater than " + expected + ", but found " + actual);
			logger.info("Assertion passed: " + actual + " is greater than " + expected);
		} catch (AssertionError e) {
			logger.error("Assertion failed: " + actual + " is not greater than " + expected, e);
			throw e;
		}
	}

	public static void assertStringContains(String actual, String expected) {
		try {
			Assert.assertTrue(actual.contains(expected),
					"Expected string to contain '" + expected + "', but found '" + actual + "'");
			logger.info("Assertion passed: '" + actual + "' contains '" + expected + "'");
		} catch (AssertionError e) {
			logger.error("Assertion failed: '" + actual + "' does not contain '" + expected + "'", e);
			throw e;
		}
	}

	public static void assertBoolean(boolean actual, boolean expected) {
		try {
			Assert.assertEquals(actual, expected, "Expected boolean value to be " + expected + ", but found " + actual);
			logger.info("Assertion passed: " + actual + " is equal to " + expected);
		} catch (AssertionError e) {
			logger.error("Assertion failed: " + actual + " is not equal to " + expected, e);
			throw e;
		}
	}

	public static void assertByte(byte actual, byte expected) {
		try {
			Assert.assertEquals(actual, expected, "Expected byte value to be " + expected + ", but found " + actual);
			logger.info("Assertion passed: " + actual + " is equal to " + expected);
		} catch (AssertionError e) {
			logger.error("Assertion failed: " + actual + " is not equal to " + expected, e);
			throw e;
		}
	}

	public static void assertChar(char actual, char expected) {
		try {
			Assert.assertEquals(actual, expected, "Expected char value to be " + expected + ", but found " + actual);
			logger.info("Assertion passed: " + actual + " is equal to " + expected);
		} catch (AssertionError e) {
			logger.error("Assertion failed: " + actual + " is not equal to " + expected, e);
			throw e;
		}
	}

	public static void assertArrayEquals(Object[] actual, Object[] expected) {
		try {
			Assert.assertEquals(actual, expected, "Expected array to be equal to the provided array");
			logger.info("Assertion passed: Arrays are equal");
		} catch (AssertionError e) {
			logger.error("Assertion failed: Arrays are not equal", e);
			throw e;
		}
	}

	public static String getTestMethodName(ITestResult result) {
		return result.getMethod().getMethodName();
	}

	public static String getTestClassAndMethodName(ITestResult result) {
		String className = result.getMethod().getTestClass().getName();
		String methodName = result.getMethod().getMethodName();
		return className + "." + methodName;
	}

	public static String getTestSuiteName(ITestContext context) {
		return context.getCurrentXmlTest().getSuite().getName();
	}

	public static String getTestParameter(ITestContext context, String parameterName) {
		return context.getCurrentXmlTest().getParameter(parameterName);
	}

	public static void log(String message) {
		Reporter.log(message);
	}

	public static void log(String message, boolean isDebugEnabled) {
		if (isDebugEnabled) {
			log("[DEBUG] " + message);
		} else {
			log(message);
		}
	}

	public static void logTestInfo(ITestResult result) {
		String testName = getTestMethodName(result);
		String testClassAndMethod = getTestClassAndMethodName(result);
		log("Executing test: " + testName);
		log("Test Class and Method: " + testClassAndMethod);
	}

	public static void logTestParameters(ITestContext context) {
		Map<String, String> testParameters = context.getCurrentXmlTest().getAllParameters();
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, String> entry : testParameters.entrySet()) {
			stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
		}
		String testParametersString = stringBuilder.toString();
		if (testParametersString.endsWith(", ")) {
			testParametersString = testParametersString.substring(0, testParametersString.length() - 2);
		}
		log("Test Parameters: " + testParametersString);
	}
}
