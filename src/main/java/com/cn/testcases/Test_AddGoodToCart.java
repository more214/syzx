package com.cn.testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.cn.configlog.FileHelper;
import com.cn.configlog.SendEmailByPDF;
import com.cn.objectpage.GoodsDetailsPage;
import com.cn.objectpage.SearchResultListPage;

//import org.testng.annotations.Listeners;
//import com.github.yev.FailTestScreenshotListener;

//@Listeners(FailTestScreenshotListener.class)
public class Test_AddGoodToCart {

	@BeforeTest
	public void setUp() {
		FileHelper.initLogFile(this.getClass().getSimpleName() + ".log");
	}

	@Test
	public void testAddToCart() throws InterruptedException {

		SearchResultListPage srlp = PageFactory.initElements(Test_HomePage_Search.driver, SearchResultListPage.class);
		srlp.clickItemImg();

		// 切换窗口到商品详情页
		srlp.switchWindow();
		GoodsDetailsPage gdp = PageFactory.initElements(Test_HomePage_Search.driver, GoodsDetailsPage.class);
		gdp.addGoodToCart();
	}

	@AfterClass
	public void tearDown() throws InterruptedException {

		Test_HomePage_Search.driver.quit();
		// Thread.sleep(3000);
	}

	// After complete execution send pdf report by email
	@AfterSuite
	public void tearDownandSend() {
		SendEmailByPDF.sendPDFReportByGMail("********@qq.com", "**********", "********@everjiankang.com", "PDF Report",
				"");
	}

}
