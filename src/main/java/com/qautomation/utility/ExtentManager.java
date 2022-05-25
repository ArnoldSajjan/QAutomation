package com.qautomation.utility;

import java.util.Date;

import com.qautomation.base.Base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager extends Base {

	private static ExtentReports extentreport;

	public static ExtentReports createInstance() {
		action.loadConfig();
		String fileName = getReportName();
		String directory = System.getProperty("user.dir") + "\\ExtentReports\\";
		String path = directory + fileName;
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(path);
		htmlReporter.config().setDocumentTitle("Automation Test Report");
		htmlReporter.config().setReportName(prop.getProperty("reportname"));
		htmlReporter.config().setTheme(Theme.STANDARD);
		extentreport = new ExtentReports();
		extentreport.attachReporter(htmlReporter);
		extentreport.setSystemInfo("ProjectName", prop.getProperty("projectname"));
		extentreport.setSystemInfo("Tester", prop.getProperty("testername"));
		extentreport.setSystemInfo("Browser", prop.getProperty("browser"));
		return extentreport;
	}

	public static String getReportName() {
		Date d = new Date();
		fileName = "AutomationReport" + "_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";
		return fileName;

	}

}