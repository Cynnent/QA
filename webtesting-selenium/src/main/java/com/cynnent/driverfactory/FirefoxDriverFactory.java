package com.cynnent.driverfactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.cynnent.exceptions.NoSuitableDriverException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FirefoxDriverFactory implements WebDriverFactory {
    private Logger log = LogManager.getLogger(getClass());

    public WebDriver createWebDriver() throws NoSuitableDriverException {
        try {
            log.info("Setting up FirefoxDriver...");
            WebDriverManager.firefoxdriver().setup();

            // Optionally, configure Firefox options
            FirefoxOptions options = new FirefoxOptions();
            // Add any desired options

            log.debug("Creating FirefoxDriver instance...");
            // Create and return the FirefoxDriver instance
            return new FirefoxDriver(options);
        } catch (Exception e) {
            log.error("Unable to create WebDriver: {}", e.getMessage());
            throw new NoSuitableDriverException("Unable to create WebDriver: " + e.getMessage());
        }
    }
}
