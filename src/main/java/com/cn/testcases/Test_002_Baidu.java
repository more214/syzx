package com.cn.testcases;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.cn.basepage.BrowserEngine;
import com.cn.configlog.FileHelper;
import com.cn.objectpage.BaiDuMainPage;
import com.cn.objectpage.SearchPage;

public class Test_002_Baidu {

	public WebDriver driver;

	@BeforeClass
	public void setup() {
		FileHelper.initLogFile(this.getClass().getSimpleName() + ".log");
		BrowserEngine be = new BrowserEngine();
		be.initConfigData();
		driver = be.getBrowser();

	}

	@Test
	public void test_BaiduSearch() {
		BaiDuMainPage bp = PageFactory.initElements(driver, BaiDuMainPage.class);
		bp.submitText("kkkkmnm");
		Assert.assertTrue(false);
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test_BaiduSearch_01() {
		BaiDuMainPage bp = PageFactory.initElements(driver, BaiDuMainPage.class);
		bp.submitText("kkkkmnm");
		SearchPage sp = PageFactory.initElements(driver, SearchPage.class);
		Assert.assertEquals(sp.getSearchResult(), "英文结果");
	}

	@Test
	public void test_BaiduSearch_02() {
		BaiDuMainPage bp = PageFactory.initElements(driver, BaiDuMainPage.class);
		bp.submitText("kkkkmnm");
		Assert.assertTrue(false);
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterClass
	public void tearDownBroswer() {
		driver.close();

	}

}
