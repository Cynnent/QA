package com.cynnent.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.cynnent.extentfactory.ExtentFactory;

public abstract class BasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected ExtentTest extentTest;
	protected Logger log;
	
	enum SelectMethod {
		BY_VISIBLE_TEXT, BY_VALUE, BY_INDEX
	}

//	public BasePage(WebDriver driver, ExtentTest extentTest) {
//		this.driver = driver;
//		wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Set the desired timeout value
//		this.extentTest = extentTest;
//		log = LogManager.getLogger(this.getClass());
//		PageFactory.initElements(new DefaultFieldDecorator(new DefaultElementLocatorFactory(driver)), this);
//	}

	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.extentTest = ExtentFactory.getInstance().getExtent();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		log = LogManager.getLogger(BasePage.class);
	}

	public void setExtentTest(ExtentTest extentTest) {
		this.extentTest = extentTest;
	}

//	public void setDriver(WebDriver driver) {
//        this.driver = driver;
//        wait = new WebDriverWait(driver,  Duration.ofSeconds(30));
//    }

	protected void enterText(WebElement element, String value, String elementName, String elementType) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.sendKeys(value);
			extentTest.log(Status.INFO, value + " entered in " + elementName + " " + elementType);
			log.info("{} entered in {} {}", value, elementName, elementType);
		} catch (Exception e) {
			extentTest.log(Status.FAIL, "Failed to enter text in " + elementName + " " + elementType);
			log.error("Failed to enter text in {} {}", elementName, elementType);
		}
	}

	protected void clickElement(WebElement element, String elementName, String elementType) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			extentTest.log(Status.INFO, "Clicked on " + elementName + " " + elementType);
			log.info("Clicked on {} {}", elementName, elementType);
		} catch (Exception e) {
			extentTest.log(Status.FAIL, "Failed to click on " + elementName + " " + elementType);
			log.error("Failed to click on {} {}", elementName, elementType);
		}
	}

	protected void selectOptionFromDropdown(WebElement dropdownElement, String optionToSelect, String dropdownName, SelectMethod selectMethod) {
		try {
			Select dropdown = new Select(dropdownElement);
			 switch (selectMethod) {
             case BY_VISIBLE_TEXT:
                 dropdown.selectByVisibleText(optionToSelect);
                 break;
             case BY_VALUE:
                 dropdown.selectByValue(optionToSelect);
                 break;
             case BY_INDEX:
                 int index = Integer.parseInt(optionToSelect);
                 dropdown.selectByIndex(index);
                 break;
             default:
                 throw new IllegalArgumentException("Invalid select method: " + selectMethod);
         }
			extentTest.log(Status.INFO, "Selected option '" + optionToSelect + "' from " + dropdownName + " dropdown");
			log.info("Selected option '{}' from {} dropdown", optionToSelect, dropdownName);
		} catch (Exception e) {
			extentTest.log(Status.FAIL, "Failed to select option from " + dropdownName + " dropdown");
			log.error("Failed to select option from {} dropdown", dropdownName);
		}
	}

	protected void hoverOverElement(WebElement element, String elementName) {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(element).build().perform();
			extentTest.log(Status.INFO, "Hovered over " + elementName);
			log.info("Hovered over {}", elementName);
		} catch (Exception e) {
			extentTest.log(Status.FAIL, "Failed to hover over " + elementName);
			log.error("Failed to hover over {}", elementName);
		}
	}

	protected void switchToFrame(WebElement frameElement) {
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
			extentTest.log(Status.INFO, "Switched to frame");
			log.info("Switched to frame");
		} catch (Exception e) {
			extentTest.log(Status.FAIL, "Failed to switch to frame");
			log.error("Failed to switch to frame");
		}
	}

	protected void switchToDefaultContent() {
		try {
			driver.switchTo().defaultContent();
			extentTest.log(Status.INFO, "Switched to default content");
			log.info("Switched to default content");
		} catch (Exception e) {
			extentTest.log(Status.FAIL, "Failed to switch to default content");
			log.error("Failed to switch to default content");
		}
	}

	protected void scrollToElement(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", element);
			extentTest.log(Status.INFO, "Scrolled to element");
			log.info("Scrolled to element");
		} catch (Exception e) {
			extentTest.log(Status.FAIL, "Failed to scroll to element");
			log.error("Failed to scroll to element");
		}
	}

	protected void highlightElement(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
					element);
			extentTest.log(Status.INFO, "Highlighted element");
			log.info("Highlighted element");
		} catch (Exception e) {
			extentTest.log(Status.FAIL, "Failed to highlight element");
			log.error("Failed to highlight element");
		}
	}

	protected void takeScreenshot(String screenshotName) {
	    try {
	        TakesScreenshot ts = (TakesScreenshot) driver;
	        byte[] screenshotBytes = ts.getScreenshotAs(OutputType.BYTES);
	        saveScreenshot(screenshotBytes, screenshotName);
	        log.info("Screenshot captured: {}", screenshotName);
	    } catch (Exception e) {
	        log.error("Failed to capture screenshot: {}", screenshotName, e);
	    }
	}

	protected void captureScreenshot(String screenshotName) {
		String finalScreenshotName = null;
		try {
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			finalScreenshotName = (screenshotName != null) ? screenshotName : methodName;

			TakesScreenshot ts = (TakesScreenshot) driver;
			byte[] screenshotBytes = ts.getScreenshotAs(OutputType.BYTES);
			extentTest.addScreenCaptureFromBase64String(new String(Base64.getEncoder().encode(screenshotBytes)),
					screenshotName);
			extentTest.log(Status.INFO, "Screenshot captured: " + finalScreenshotName);
			log.info("Screenshot captured: {}", finalScreenshotName);
		} catch (Exception e) {
			extentTest.log(Status.FAIL, "Failed to capture screenshot: " + finalScreenshotName);
			log.error("Failed to capture screenshot: {}", finalScreenshotName, e);
		}
	}

	private void saveScreenshot(byte[] screenshotBytes, String screenshotName) {
		// Below are the steps to implement yet to decide how to go with...
	    // Implement code to save the screenshot to a desired location
	    // code using FileUtils from Apache Commons IO:
	    // FileUtils.writeByteArrayToFile(new File("path/to/save/" + screenshotName + ".png"), screenshotBytes);
	}

	protected void waitForPageLoad() {
	    try {
	        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	        extentTest.log(Status.INFO, "Page loaded successfully");
	        log.info("Page loaded successfully");
	    } catch (Exception e) {
	        captureScreenshot("");
	        extentTest.log(Status.FAIL, "Failed to wait for page load");
	        log.error("Failed to wait for page load", e);
	        throw new RuntimeException("Failed to wait for page load", e);
	    }
	}

	protected boolean isElementDisplayed(WebElement element) {
	    try {
	        boolean isDisplayed = element.isDisplayed();
	        log.info("Element is displayed: {}", isDisplayed);
	        extentTest.log(Status.INFO, "Element is displayed: " + isDisplayed);
	        return isDisplayed;
	    } catch (NoSuchElementException | StaleElementReferenceException e) {
	        log.error("Error occurred while checking element display status", e);
	        extentTest.log(Status.FAIL, "Error occurred while checking element display status");
	        return false;
	    }
	}

	protected String getElementText(WebElement element) {
	    try {
	        wait.until(ExpectedConditions.visibilityOf(element));
	        String text = element.getText();
	        log.info("Element text: {}", text);
	        extentTest.log(Status.INFO, "Element text: " + text);
	        return text;
	    } catch (NoSuchElementException | StaleElementReferenceException e) {
	        log.error("Error occurred while getting element text", e);
	        extentTest.log(Status.FAIL, "Error occurred while getting element text");
	        return "";
	    }
	}

	protected List<List<String>> readTestDataFromTable(WebElement table) {
	    List<List<String>> tableData = new ArrayList<>();
	    try {
	        WebElement tbody = table.findElement(By.tagName("tbody"));
	        List<WebElement> tableRows = tbody.findElements(By.tagName("tr"));
	        for (WebElement row : tableRows) {
	            List<WebElement> cells = row.findElements(By.tagName("td"));
	            List<String> rowData = cells.stream().map(WebElement::getText).collect(Collectors.toList());
	            tableData.add(rowData);
	        }

	        log.info("Read test data from table: {}", tableData);
	        extentTest.log(Status.INFO, "Read test data from table: " + tableData);
	    } catch (Exception e) {
	        log.error("Error occurred while reading table data from table located by: {}", table, e);
	        extentTest.log(Status.FAIL, "Error occurred while reading table data from table located by: " + table);
	    }

	    return tableData;
	}

	protected <T> T getElementValue(WebElement element, String elementType, Class<T> dataType) {
	    try {
	        T value;
	        switch (elementType.toLowerCase()) {
	            case "dropdown":
	                value = dataType.cast(new Select(element).getFirstSelectedOption().getText());
	                break;

	            case "checkbox":
	            case "radio":
	                value = dataType.cast(element.isSelected());
	                break;

	            case "textbox":
	                value = dataType.cast(element.getAttribute("value"));
	                break;

	            case "link":
	                String hrefValue = element.getAttribute("href");
	                String linkText = element.getText();

	                if (hrefValue != null && !hrefValue.isEmpty()) {
	                    value = dataType.cast(hrefValue);
	                } else {
	                    value = dataType.cast(linkText);
	                }
	                break;

	            default:
	                throw new IllegalArgumentException("Invalid element type: " + elementType);
	        }
	        log.info("Retrieved {} value: {}", elementType, value);
	        extentTest.log(Status.INFO, "Retrieved " + elementType + " value: " + value);
	        return value;
	    } catch (Exception e) {
	        log.error("Failed to retrieve value from element of type: {}", elementType, e);
	        extentTest.log(Status.FAIL, "Failed to retrieve value from element of type: " + elementType);
	        throw e;
	    }

	}

	
	// Other common functions/methods

}
