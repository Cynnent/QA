package com.cynnent.extentfactory;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtentFactory {
    private static final Logger log = LogManager.getLogger(ExtentFactory.class);
    
   
    
    // Singleton design Pattern
    // private constructor so that no one else can create object of this class
    private ExtentFactory() {
    }

    private static final ExtentFactory instance = new ExtentFactory();
    
    public static ExtentFactory getInstance() {
        return instance;
    }

    // factory design pattern --> define separate factory methods for creating objects and create objects by calling that methods
    private ThreadLocal<ExtentTest> extentthreadlocal = new ThreadLocal<ExtentTest>();

    public ExtentTest getExtent() {
        return extentthreadlocal.get();
    }

    public void setExtent(ExtentTest obj_extentTest) {
    	extentthreadlocal.set(obj_extentTest);
    }

    public void removeExtentObject() {
    	extentthreadlocal.remove();
    }

    public void logInfo(String message) {
        ExtentTest extentTest = extentthreadlocal.get();
        if (extentTest != null) {
            extentTest.info(message);
            log.info(message);
        }
    }

    public void logError(String message) {
        ExtentTest extentTest = extentthreadlocal.get();
        if (extentTest != null) {
            extentTest.fail(message);
            log.error(message);
        }
    }

    public void logDebug(String message) {
        ExtentTest extentTest = extentthreadlocal.get();
        if (extentTest != null) {
            extentTest.info("[DEBUG] " + message);
            log.debug("[DEBUG] " + message);
        }
    }

    public void logWarning(String message) {
        ExtentTest extentTest = extentthreadlocal.get();
        if (extentTest != null) {
            extentTest.warning(message);
            log.warn(message);
        }
    }
}
