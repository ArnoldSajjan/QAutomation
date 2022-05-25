package com.qautomation.dataproviders;


import org.testng.annotations.DataProvider;
import com.qautomation.base.Base;


public class DataProviders extends Base{

	/**
	 * Provider your TestData sheet Name Eg:- return dataSupplier("YOUR SHEET NAME"), your Cell No)
	 * @return
	 * @throws Exception
	 */

	@DataProvider(name = "SearchThrid")
	public Object[][] Search() throws Exception {

		return dataSupplier("Search", 0);

	}

}