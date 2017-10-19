
package com.cn.objectpage.functions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;

import com.cn.basepage.BrowserEngine;
import com.cn.objectpage.HomePage;
import com.cn.objectpage.LoginPage;

public class Login {

	public WebDriver driver;
	protected ITestContext testContext;

	// 第一步，获取浏览器类型，打开浏览器。打开测试网站
	public void initSetup() {

		BrowserEngine browser = new BrowserEngine();
		browser.initConfigData();
		driver = browser.getBrowser();
	}

	// 用page factory类来加载初始化主页元素，点击登录这个链接
	public void loginValid() {

		HomePage homepage = PageFactory.initElements(driver, HomePage.class);
		// 点击主页上的登录链接
		homepage.clickLoginLnk();
		// 跳转到登录页面，初始化登录界面的元素
		LoginPage loginpage = PageFactory.initElements(driver, LoginPage.class);
		// 测试一个有效的用户名登录
		loginpage.login("13269225141", "Pa88word!~");
	}

}
