
package com.cn.testcases;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cn.configlog.FileHelper;
import com.cn.objectpage.HomePage;
import com.cn.objectpage.functions.Login;

public class Test_HomePage_Search {

	public static WebDriver driver;

	@BeforeClass
	public void setUp() throws IOException {
		FileHelper.initLogFile(this.getClass().getSimpleName() + ".log");
		Login login = new Login();
		login.initSetup();
		login.loginValid();
		driver = login.driver;

	}

	@Test
	public void test_searchGoods() {

		HomePage hp = PageFactory.initElements(driver, HomePage.class);
		hp.searchWithKeyword("Java");
		Assert.assertEquals(false, "sadad");
	}

	@AfterClass
	public void tearDown() throws InterruptedException {

		Thread.sleep(3000);
	}
}
