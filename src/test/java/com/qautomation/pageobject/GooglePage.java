package com.qautomation.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qautomation.base.Base;

public class GooglePage extends Base{

	@FindBy(name = "q")
	WebElement search;
	
	
	public GooglePage()
	{
		PageFactory.initElements(getDriver(), this);
	}
	
	
	public void Google(String value)
	{
		System.out.println(value);
		action.type(getDriver(), search, value);
	}
}
