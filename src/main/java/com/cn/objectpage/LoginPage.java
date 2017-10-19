package com.cn.objectpage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.cn.basepage.BasePage;

public class LoginPage extends BasePage {

	/*
	 * 登录页面，这里主要测试账号和密码的登录方式，第三方登录暂时不考虑
	 */

	// 元素定位
	// 点击 账户登录 菜单
	@FindBy(xpath = "//*[@id='content']/div/div[1]/div/div[2]/a")
	WebElement loginType_byAccount;

	// 用户名
	@FindBy(id = "loginname")
	WebElement login_userName;

	// 密码
	@FindBy(id = "nloginpwd")
	WebElement login_password;

	// 密码
	@FindBy(id = "loginsubmit")
	WebElement login_submitBtn;

	// 构造函数
	public LoginPage(WebDriver driver) {
		super(driver);

	}

	/*
	 * 用户名和密码登录
	 */
	public HomePage login(String username, String password) {

		click(loginType_byAccount);
		type(login_userName, username);
		type(login_password, password);
		click(login_submitBtn);
		return new HomePage(driver);
	}

}
