package com.cynnent.driverfactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.cynnent.exceptions.NoSuitableDriverException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeDriverFactory implements WebDriverFactory {
    private Logger log = LogManager.getLogger(getClass());

    public WebDriver createWebDriver() throws NoSuitableDriverException {
        try {
            log.info("Setting up ChromeDriver...");
            WebDriverManager.chromedriver().setup();

            // Optionally, configure Chrome options
            ChromeOptions options = new ChromeOptions();
            // Add any desired options
            options.addArguments("--remote-allow-origins=*");

            log.debug("Creating ChromeDriver instance...");
            // Create and return the ChromeDriver instance
            return new ChromeDriver(options);
        } catch (Exception e) {
            log.error("Unable to create WebDriver: {}", e.getMessage());
            throw new NoSuitableDriverException("Unable to create WebDriver: " + e.getMessage());
        }
    }
}
