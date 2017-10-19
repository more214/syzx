package com.cn.objectpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.cn.basepage.BasePage;

public class BaiDuMainPage extends BasePage {

	public BaiDuMainPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//*[@id='kw']")
	private WebElement NAME;

	@FindBy(xpath = "//*[@id='su']")
	private WebElement SUBMIT;

	public SearchPage submitText(String text) {
		waitForElementToLoad(20, NAME);
		type(NAME, text);
		waitForElementToLoad(20, SUBMIT);
		click(SUBMIT);
		return new SearchPage(driver);
	}
}
