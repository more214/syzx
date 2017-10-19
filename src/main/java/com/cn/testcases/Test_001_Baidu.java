package com.cn.testcases;


import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.cn.configlog.FileHelper;
import com.cn.dbconfig.ExcelDataProvider;

public class Test_001_Baidu {

	private WebDriver driver;

	@Test(dataProvider = "testData")
	public void test_New(Map<String, String> data) {
		FileHelper.initLogFile(this.getClass().getSimpleName() + ".log");
		System.setProperty("webdriver.chrome.driver",
				"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
		driver = new ChromeDriver();
		FileHelper.info("启动chrome浏览器");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FileHelper.info("设置隐式时间10秒");

		driver.get("https://www.baidu.com");
		FileHelper.info("打开百度首页");

		driver.findElement(By.id("kw")).sendKeys(data.get("text"));
		FileHelper.info("搜索输入框输入关键字selenium");
	}

	@AfterClass
	public void quit() {
		driver.quit();
	}

	/**
	 * 测试数据提供者 - 方法
	 */
	@DataProvider(name = "testData")
	public Iterator<Object[]> dataFortestMethod() throws IOException {
		String moduleName = null; // 模块的名字
		String caseNum = null; // 用例编号
		String className = this.getClass().getName();
		int dotIndexNum = className.indexOf("."); // 取得第一个.的index
		int underlineIndexNum = className.indexOf("_"); // 取得第一个_的index

		if (dotIndexNum > 0) {
			moduleName = className.substring(7, className.lastIndexOf(".")); // 取到模块的名称
		}
		if (underlineIndexNum > 0) {
			caseNum = className.substring(underlineIndexNum + 1, underlineIndexNum + 4); // 取到用例编号
		}
		// 将模块名称和用例的编号传给 ExcelDataProvider ，然后进行读取excel数据
		return new ExcelDataProvider(moduleName, caseNum);
	}
}
