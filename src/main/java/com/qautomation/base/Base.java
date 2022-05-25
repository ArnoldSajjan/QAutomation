package com.qautomation.base;




import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import com.aventstack.extentreports.ExtentTest;
import com.qautomation.action.Action;
import com.qautomation.utility.ExcelLibrary;
import com.qautomation.utility.WriteExcel;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	public static Action action = Action.getInstance();
	public static WriteExcel writeexcel = WriteExcel.getInstance();
	public static Properties prop;
	public static String fileName = "";
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	protected static String path = System.getProperty("user.dir")+"\\TestData\\TestData.xlsx";

	public static ExtentTest getExtentTest()
	{
		return extentTest.get();
	}
 
	public static WebDriver getDriver()
	{
		return driver.get();
	}
	
	public static void setExtentTest(ExtentTest test)
	{
		extentTest.set(test);
	}
	
	public static void launchApp() {

		System.setProperty("webdriver.ie.driver", "C:\\IE\\IEDriverServer.exe");
		// WebDriverManager.iedriver().arch32().setup();;
		String browserName = prop.getProperty("browser");
		switch(browserName.toLowerCase())
		{
		case "iemode":
			InternetExplorerOptions ieOptions = new InternetExplorerOptions();
			ieOptions.attachToEdgeChrome();
			ieOptions.withEdgeExecutablePath("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
			driver.set(new InternetExplorerDriver(ieOptions));
			break;
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
			break;
		
		}

		action.implicitWait(60);
		action.pageLoadTimeOut(60);
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
	}

	
	public static void openReport() {
		
		if (prop.getProperty("openreport").toLowerCase().contains("yes"))
		{
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
			getDriver().manage().window().maximize();
			getDriver().get(System.getProperty("user.dir") + "\\ExtentReports\\" + fileName);
		}
		

	}
	

	public Object[][] dataSupplier(String Sheetname, int cellno) throws Exception {

		ExcelLibrary excellibrary = new ExcelLibrary(path);
		int lastRowNum = excellibrary.getRowCount(Sheetname);
		int lastCellNum = excellibrary.getCellCount(Sheetname, cellno);
		Object[][] obj = new Object[lastRowNum][1];

		for (int i = 0; i < lastRowNum; i++) {
			Map<Object, Object> datamap = new HashMap<Object, Object>();
			for (int j = 0; j < lastCellNum; j++) {
				datamap.put(excellibrary.getCellData(Sheetname, cellno, j), excellibrary.getCellData(Sheetname, i + 1, j));
			}
			obj[i][0] = datamap;

		}
		
		return obj;
	}
	

}