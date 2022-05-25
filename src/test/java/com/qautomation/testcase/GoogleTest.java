package com.qautomation.testcase;

import java.util.Map;


import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qautomation.dataproviders.DataProviders;
import com.qautomation.pageobject.GooglePage;
import com.qautomation.utility.ListenerClass;

@Listeners(ListenerClass.class)
public class GoogleTest{

	
	@Test(description = "Google Test" ,dataProvider = "SearchThrid", dataProviderClass= DataProviders.class)
	public void googleTest(Map<String,String> map)
	{
		new GooglePage().Google(map.get("Value"));
	}
}
