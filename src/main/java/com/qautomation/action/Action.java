package com.qautomation.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qautomation.base.Base;

public class Action extends Base {

	private static Action action;
	

	public static Action getInstance() {
		action = new Action();
		return action;
	}
	
	public void loadConfig() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "\\Configuration\\Config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void implicitWait(WebDriver driver, int timeout) {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

	}

	public void pageLoadTimeOut(WebDriver driver, int timeout) {

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

	}

	public void switchToFrameByName(WebDriver driver, String Name) {

		switchTodefaultPage();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name(Name)));

	}

	public boolean isDisplayed(WebDriver driver, WebElement locatorname) {

		elementWait(driver, locatorname);

		return locatorname.isDisplayed();
	}

	public void click(WebDriver driver, WebElement locatorname) {

		try {
			elementWait(driver, locatorname);
			locatorname.click();
		} catch (Exception e) {
			System.out.print("Could not Click the WebElement : " + locatorname);
		}
		// TODO Auto-generated method stub

	}

	public void switchToNewWindow(int indexPage) throws InterruptedException {

		for (int i = 0; i < 5; i++) {

			Set<String> handles = getDriver().getWindowHandles();
			if (handles.size() > 1) {

				System.out.println(handles);
				List<String> windowsString = new ArrayList<String>(handles);
				String reqWindow = windowsString.get(indexPage);
				getDriver().switchTo().window(reqWindow);
				getDriver().manage().window().maximize();
				break;
			}
			Thread.sleep(30000);
		}
	}

	public void type(WebDriver driver, WebElement locatorname, String userinput) {

		elementWait(driver , locatorname);
		locatorname.sendKeys(userinput);

	}

	public void elementWait(WebDriver driver,WebElement ele) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
			wait.until(ExpectedConditions.visibilityOfAllElements(ele));
		} catch (Exception e) {
			System.out.print("Could not find the WebElement : " + ele);
		}

	}

	public void switchTodefaultPage() {

		getDriver().switchTo().defaultContent();
		// TODO Auto-generated method stub

	}

	public void selectFromDropDownListByName(WebDriver driver, WebElement ele, String visibleTest) {

		elementWait(driver, ele);
		Select dropDown = new Select(ele);
		dropDown.deselectAll();
		dropDown.selectByVisibleText(visibleTest);

	}

	public String takeScreenshot(WebDriver driver , String methodName) {
		String fileName = getScreenShotName(methodName);
		String path = System.getProperty("user.dir") + "\\ScreenShot\\" + fileName;
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(source, new File(path));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		return path;
	}

	public String getScreenShotName(String methodName) {
		Date d = new Date();
		String fileName = methodName + "_" + d.toString().replace(":", "_").replace(" ", "_") + ".png";
		return fileName;

	}

	public void reportLogger(String message, String status) {
		if (status == "info") {
			getExtentTest().log(Status.INFO, message);
		} else if (status == "pass") {
			getExtentTest().log(Status.INFO, message);
		} else if (status == "skip") {
			getExtentTest().log(Status.INFO, message);
		} else if (status == "fail") {
			getExtentTest().log(Status.INFO, message);
		}
	}

	public void reportLoggerwithElemnetScreenshot(WebDriver driver, String message, String status, WebElement ele) {
		elementWait(driver, ele);
		String path = ele.getScreenshotAs(OutputType.BASE64);
		if (status == "info") {
			getExtentTest().log(Status.INFO, message,
					MediaEntityBuilder.createScreenCaptureFromBase64String(path).build());
		} else if (status == "pass") {
			getExtentTest().log(Status.PASS, message,
					MediaEntityBuilder.createScreenCaptureFromBase64String(path).build());
		} else if (status == "skip") {
			getExtentTest().log(Status.SKIP, message,
					MediaEntityBuilder.createScreenCaptureFromBase64String(path).build());
		} else if (status == "fail") {
			getExtentTest().log(Status.FAIL, message,
					MediaEntityBuilder.createScreenCaptureFromBase64String(path).build());
		}
	}

	public void reportLoggerwithFullPageScreenshot(WebDriver driver, String message, String status, WebElement ele) {
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		String path = takesScreenshot.getScreenshotAs(OutputType.BASE64);
		if (status == "info") {
			getExtentTest().log(Status.INFO, message,
					MediaEntityBuilder.createScreenCaptureFromBase64String(path).build());
		} else if (status == "pass") {
			getExtentTest().log(Status.PASS, message,
					MediaEntityBuilder.createScreenCaptureFromBase64String(path).build());
		} else if (status == "skip") {
			getExtentTest().log(Status.SKIP, message,
					MediaEntityBuilder.createScreenCaptureFromBase64String(path).build());
		} else if (status == "fail") {
			getExtentTest().log(Status.FAIL, message,
					MediaEntityBuilder.createScreenCaptureFromBase64String(path).build());
		}
	}

}