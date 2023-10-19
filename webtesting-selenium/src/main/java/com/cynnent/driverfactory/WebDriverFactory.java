package com.cynnent.driverfactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.cynnent.exceptions.NoSuitableDriverException;

public interface WebDriverFactory {
    Logger log = LogManager.getLogger(WebDriverFactory.class);

    WebDriver createWebDriver() throws NoSuitableDriverException;
}
