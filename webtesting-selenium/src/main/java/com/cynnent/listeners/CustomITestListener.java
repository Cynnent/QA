package com.cynnent.listeners;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.cynnent.extentfactory.ExtentFactory;
import com.cynnent.extentfactory.ExtentReport;
import com.cynnent.framework.TestContextManager;
import com.cynnent.utils.Takescreenshot;
//import com.cynnent.reporters.ExtentReport;
import com.cynnent.utils.TestNGUtils;

public class CustomITestListener implements ITestListener {
    private static final Logger log = LogManager.getLogger(CustomITestListener.class);

    private ExtentReports report;

    Set<String> stringSet = new HashSet<>();
//    private ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
    	log.info("*** Test Suite {} started ***", context.getName());
        try {
            log.info("Initializing ExtentReports instance...");
//            report = ExtentReport.getInstance();
            report=ExtentReport.setupExtentReport();//onereport
            TestContextManager.setTestContext(context);
            log.info("TestContext set for thread: " + Thread.currentThread().getId());
        } catch (Exception e) {
            log.error("Failed to get the ExtentReports instance: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void onTestStart(ITestResult result) {
    	log.info("*** Running Test Case - {} ...", result.getTestClass().getName());
    	log.info("*** Running Test - {}...", result.getMethod().getMethodName());

        String browsername = result.getTestContext().getCurrentXmlTest().getParameter("browser");
        String testdescription = result.getMethod().getDescription();
        ExtentTest test = report.createTest(result.getMethod().getMethodName() + "_" + browsername, testdescription);

        ExtentFactory.getInstance().setExtent(test);
//        TestNGUtils.setExtentTest(test); //--- check working or not
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("*** Executed Test Case - " + result.getTestClass().getName() + " ...");
        log.info("*** Executed Test - " + result.getMethod().getMethodName() + " Passed...");
        ExtentTest test = ExtentFactory.getInstance().getExtent();
        test.pass(result.getMethod().getDescription() + " - Passed"); 
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("Test Case Failed: " + result.getMethod().getDescription(), result.getThrowable());
        ExtentTest test = ExtentFactory.getInstance().getExtent();
        test.fail(result.getMethod().getDescription()+" - Failed"); 
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("Test Skipped: " + result.getMethod().getDescription());
        ExtentTest test = ExtentFactory.getInstance().getExtent();
        test.skip(result.getMethod().getDescription()+ " - Skipped"); 
    }
    
    @AfterMethod
    public void cleanupTestMethod() {
//        TestNGUtils.removeExtentTest(); //report.flush();
    	ExtentFactory.getInstance().removeExtentObject();
    }

    @Override
    public void onFinish(ITestContext context) {
        report.flush();
        
        File reportFile = ExtentReport.getReportFile();
        String reportPath = reportFile.getAbsolutePath();
        stringSet.add(reportPath);
//        EmailSender.sendEmailReport(reportPath);
//        log.info("Report Path: " + reportPath);
        log.info("*** Test Suite " + context.getName() + " finished ***");
        
    }
    
    

    // Other methods are left as it is
}
