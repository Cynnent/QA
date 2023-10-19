package com.cynnent.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.cynnent.framework.TestBase;

/**
 * @author: Kannan Palaniappan
 */
public class Takescreenshot {
	private static final Logger logger = LogManager.getLogger(TestBase.class);

	public static String getscreenshot() throws IOException {
		File src = ((TakesScreenshot) TestBase.getDriver()).getScreenshotAs(OutputType.FILE);

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy HH-mm-ss");
		Date date = new Date();
		String actualDate = format.format(date);

		String screenshotPath = System.getProperty("user.dir") + "/Reports/Screenshots/" + actualDate + ".jpeg";
		File dest = new File(screenshotPath);

		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenshotPath;
	}

	public static  String getbase64screenshot() throws IOException {		
		File src = ((TakesScreenshot) TestBase.getDriver()).getScreenshotAs(OutputType.FILE);		
		return getBase64Image(src);		
	}

	private static String getBase64Image(File file) throws IOException {
		byte[] fileContent = Files.readAllBytes(file.toPath());
		String base64Image = Base64.getEncoder().encodeToString(fileContent);
		return "data:image/png;base64," + base64Image;
	}

}
