package com.cn.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.cn.basepage.BrowserEngine;
import com.cn.configlog.FileHelper;

public class Test_LaunchBrowser {

	public WebDriver driver;

	@BeforeClass
	public void setUp() throws IOException {
		FileHelper.initLogFile(this.getClass().getSimpleName() + ".log");
		BrowserEngine browserEngine = new BrowserEngine();
		browserEngine.initConfigData();
		driver = browserEngine.getBrowser();

	}

	@Test
	public void clickNews() {
		driver.findElement(By.id("key")).sendKeys("iPhone 7");
		driver.findElement(By.xpath("//*[@id='search']/div/div[2]/button")).click();
		Assert.assertEquals(driver.findElement(By.id("key")).getText(), "xinxin");
	}

	@AfterClass
	public void tearDown() {

		driver.quit();
	}

}
