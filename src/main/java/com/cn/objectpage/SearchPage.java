package com.cn.objectpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.cn.basepage.BasePage;

public class SearchPage extends BasePage {

	public SearchPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(linkText = "英文结果")
	private WebElement linkTextCode;

	public String getSearchResult() {

		waitForElementToLoad(20, linkTextCode);
		return linkTextCode.getText();
	}

}
