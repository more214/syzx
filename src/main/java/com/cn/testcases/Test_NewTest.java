package com.cn.testcases;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.cn.basepage.BrowserEngine;
import com.cn.configlog.FileHelper;
import com.cn.objectpage.HomePage;

public class Test_NewTest {
	public WebDriver driver;

	@BeforeClass
	public void setUp() throws IOException {
		FileHelper.initLogFile(this.getClass().getSimpleName() + ".log");
		BrowserEngine browserEngine = new BrowserEngine();
		browserEngine.initConfigData();
		driver = browserEngine.getBrowser();
	}

	@Test
	public void testAddToCart() throws InterruptedException {

		HomePage hp = PageFactory.initElements(driver, HomePage.class);
		hp.searchWithKeyword("Java");
		Assert.assertEquals(false, "sadad");
	}

	@AfterClass
	public void tearDown() {

		Test_HomePage_Search.driver.quit();
	}

}
