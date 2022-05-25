package com.qautomation.utility;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.qautomation.base.Base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ListenerClass extends Base implements ITestListener {

	private static ExtentReports extent = ExtentManager.createInstance();

	public void onFinish(ITestContext arg0) {

		getExtentTest().log(Status.INFO, "=======================END====================");
		if (getDriver() != null) {
			getDriver().quit();
		}

		if (extent != null) {
			extent.flush();
		}
		openReport();

	}

	public void onStart(ITestContext arg0) {

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub

		String logText = "<b>Test Method " + result.getMethod().getDescription() + "Failed</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
		getExtentTest().log(Status.FAIL, m);
		String expectionMessage = result.getThrowable().getMessage();
		getExtentTest().fail("<details><summary><b><font color=red>Exception Occured, Click to see details:"
				+ "</font></b></summary>" + expectionMessage.replaceAll(",", "<br>") + "</details>\n");
		try {
			writeexcel.updateExeclTestData(result.getMethod().getMethodName(), prop.getProperty("testername"),
					prop.getProperty("browser"), "Failed", expectionMessage);
		} catch (Exception e) {
			System.out.println(e);
		}
		String path = action.takeScreenshot(result.getMethod().getMethodName());
		getExtentTest().fail("<b><font color=red>" + "Failed ScreenShot" + "</font></b>",
				MediaEntityBuilder.createScreenCaptureFromPath(path).build());

	}

	public void onTestSkipped(ITestResult result) {

		try {
			writeexcel.updateExeclTestData(result.getMethod().getMethodName(), prop.getProperty("testername"),
					prop.getProperty("browser"), "Skipped", result.getMethod().getDescription() + " Skipped");
		} catch (Exception e) {
			System.out.println(e);
		}
		String logText = "<b>Test Method " + result.getMethod().getDescription() + "Failed</b>";
		Markup markup = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		getExtentTest().log(Status.SKIP, markup);

	}

	public void onTestStart(ITestResult result) {

		action.loadConfig();
		launchApp();
		setExtentTest(extent.createTest(result.getMethod().getDescription()));
		getExtentTest().log(Status.INFO, "=============Automation Testing Started===========");

	}

	public void onTestSuccess(ITestResult result) {

		try {
			writeexcel.updateExeclTestData(result.getMethod().getMethodName(), prop.getProperty("testername"),
					prop.getProperty("browser"), "Passed", result.getMethod().getDescription() + " Passed");
		} catch (Exception e) {
			System.out.println(e);
		}
		String logText = "<b> " + result.getMethod().getDescription() + " Passed</b>";
		Markup markup = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		getExtentTest().log(Status.PASS, markup);
		String path = action.takeScreenshot(result.getMethod().getMethodName());
		getExtentTest().pass("<b><font color=green>" + "Passed ScreenShot" + "</font></b>",
				MediaEntityBuilder.createScreenCaptureFromPath(path).build());

	}

}