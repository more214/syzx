package com.cn.basepage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import com.cn.configlog.FileHelper;
import com.cn.dbconfig.ExcelDataProvider;

public class BrowserEngine {

	public String browserName;
	public String serverURL;
	public static WebDriver driver;

	public void initConfigData() {

		Properties p = new Properties();
		// 加载配置文件
		try {
			InputStream ips;
			ips = new FileInputStream(".\\TestConfig\\config.properties");
			p.load(ips);
			FileHelper.info("Start to select browser name from properties file");
			browserName = p.getProperty("browserName");
			FileHelper.info("Your had select test browser type is: " + browserName);
			serverURL = p.getProperty("URL");
			FileHelper.info("The test server URL is: " + serverURL);
			ips.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public WebDriver getBrowser() {

		if (browserName.equalsIgnoreCase("Firefox")) {

			System.setProperty("webdriver.gecko.driver", ".\\Tools\\geckodriver.exe");
			driver = new FirefoxDriver();

			FileHelper.info("Launching Firefox ...");

		} else if (browserName.equalsIgnoreCase("Chrome")) {

			System.setProperty("webdriver.chrome.driver",
					"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
			driver = new ChromeDriver();
			FileHelper.info("加载 Chrome浏览器 ...");

		} else if (browserName.equalsIgnoreCase("IE")) {

			System.setProperty("webdriver.ie.driver", ".\\Tools\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			FileHelper.info("Launching IE ...");
		}

		driver.get(serverURL);
		FileHelper.info("打开 URL: " + serverURL);
		driver.manage().window().maximize();
		FileHelper.info("Maximize browser...");
		callWait(5);
		return driver;
	}

	@BeforeClass
	public void setup() {
		FileHelper.initLogFile(this.getClass().getSimpleName() + ".log");
		BrowserEngine be = new BrowserEngine();
		be.initConfigData();
		driver = be.getBrowser();

	}

	@AfterClass
	public void tearDown() {

		FileHelper.info("关闭浏览器...");
		driver.quit();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	/**
	 * 隐式时间等待方法
	 */
	public void callWait(int time) {

		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		FileHelper.info("Wait for " + time + " seconds.");
	}
}
