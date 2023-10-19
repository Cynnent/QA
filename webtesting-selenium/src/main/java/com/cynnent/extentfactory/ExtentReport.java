package com.cynnent.extentfactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExtentReport {
    private static final Logger log = LogManager.getLogger(ExtentReport.class);
    private static ExtentReports extent;
	private static File reportFile;
	private static Set<String> generatedReports=new HashSet<>();

    public static ExtentReports setupExtentReport() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss-sss");
        Date date = new Date();
        String actualDate = format.format(date);

        String reportPath = System.getProperty("user.dir") +
                "/Reports/ExecutionReport_" + actualDate + ".html";

        ExtentSparkReporter sparkReport = new ExtentSparkReporter(reportPath);

        extent = new ExtentReports();
        extent.attachReporter(sparkReport);

        sparkReport.config().setDocumentTitle("Numeracle");
        sparkReport.config().setTheme(Theme.DARK);
        sparkReport.config().setReportName("Numeracle Execution Report");
        
        // Get the output file
        reportFile = sparkReport.getFile();
        log.info("Report File: " + reportFile.getAbsolutePath());
        generatedReports.add(reportFile.getAbsolutePath());
        log.info(generatedReports.toString());
        
        log.info("Extent report setup completed.");
        
        return extent;
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
            log.info("Extent report flushed successfully.");
        }
    }
    
    public static  Set<String> getAllReportFile() {
        return generatedReports;
    }
    
    public static File getReportFile() {
        return reportFile;
    }
    
}
