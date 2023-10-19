package com.cynnent.driverfactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import com.cynnent.exceptions.NoSuitableDriverException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EdgeDriverFactory implements WebDriverFactory {
    private Logger log = LogManager.getLogger(getClass());

    public WebDriver createWebDriver() throws NoSuitableDriverException {
        try {
            log.info("Setting up EdgeDriver...");
            WebDriverManager.edgedriver().setup();
//            System.setProperty("webdriver.edge.driver", "./src/main/resources/Driver/msedgedriver.exe");

            // Optionally, configure Edge options
            EdgeOptions options = new EdgeOptions();
            // Add any desired options
            options.addArguments("--remote-allow-origins=*");

            log.debug("Creating EdgeDriver instance...");
            // Create and return the EdgeDriver instance
            return new EdgeDriver(options);
        } catch (Exception e) {
            log.error("Unable to create WebDriver: {}", e.getMessage());
            throw new NoSuitableDriverException("Unable to create WebDriver: " + e.getMessage());
        }
    }
}
