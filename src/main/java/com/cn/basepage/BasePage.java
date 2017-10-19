package com.cn.basepage;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.cn.configlog.FileHelper;

public class BasePage {

	public static WebDriver driver;
	public static String pageTitle;
	public static String pageUrl;

	/*
	 * 构造方法
	 */
	public BasePage(WebDriver driver) {
		BasePage.driver = driver;
	}

	/*
	 * 获取webdriver
	 */
	public static WebDriver getDriver() {

		if (driver == null) {
			System.setProperty("webdriver.firefox.marionette",
					"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		return driver;

	}

	/************************************
	 * ------------------------元素封装---------------------------------
	 *********************************************/
	/*
	 * 在文本框内输入字符
	 */
	protected void type(WebElement element, String text) {
		try {
			if (element.isEnabled()) {
				element.clear();
				FileHelper.info("清除输入字符： " + element.toString());
				element.sendKeys(text);
				FileHelper.info("输入字符: " + text);
			}
		} catch (Exception e) {
			FileHelper.error(e.getMessage());
		}

	}

	/*
	 * 在文本输入框执行清除操作
	 */
	protected void clean(WebElement element) {

		try {
			if (element.isEnabled()) {
				element.clear();
				FileHelper.info("元素: " + element.toString() + " 已经被清除");
			}
		} catch (Exception e) {
			FileHelper.error(e.getMessage());
		}

	}

	/*
	 * 点击元素，这里指点击鼠标左键
	 */
	protected void click(WebElement element) {

		try {
			if (element.isEnabled()) {
				element.click();
				FileHelper.info("元素: " + element.toString() + " 已点击");
			}
		} catch (Exception e) {
			FileHelper.error(e.getMessage());
			Assert.fail("元素未找到或找到多个元素无法定位点击！");
		}

	}
	
	/*
	 * 不能点击时候重试点击操作 
	 */
	public void clickTheClickable(WebElement element, long startTime, int timeOut) throws Exception {
		try {
			element.click();
		} catch (Exception e) {
			if (System.currentTimeMillis() - startTime > timeOut) {
				Assert.fail(element+ " is unclickable");
				throw new Exception(e);
			} else {
				Thread.sleep(500);
				FileHelper.info(element + " is unclickable, try again");
				clickTheClickable(element, startTime, timeOut);
			}
		}
	}
	

	/*
	 * 从元素字符串中切出想要的字符串
	 */

	public  String  sliceUp(WebElement element, int text1, int text2) {

		String text = element.getText().substring(text1, text2);
	
		return text;
	}

	
	/*
	 * 动态设置webpage上的元素
	 */
	public void setWebElement(String value){
		String locator ="//div[text()='恭喜，%var% 已被注册']";
		locator = locator.replace("%var%", value);
		WebElement refinish = driver.findElement(By.xpath(locator));			
	}	
	
	/*
	 * 检查元素是否显示
	 */
	public boolean isDisplayed(WebElement element) {
		boolean isDisplay = false;
		if (element.isDisplayed()) {
			FileHelper.info("元素: [" + getLocatorByElement(element, ">") + "] 已显示");
			isDisplay = true;
		} else if (element.isDisplayed() == false) {
			FileHelper.error("元素: [" + getLocatorByElement(element, ">") + "] 没有显示");
			isDisplay = false;
			Assert.fail("元素没有显示！");
		}
		return isDisplay;
	}

	/*
	 * 检查元素是不是存在
	 */
	public boolean doesElementsExist(WebElement element) {
		try {
			if (element != null) {
			}
			return true;
		} catch (NoSuchElementException nee) {

			return false;
		}
	}

	/*
	 * 单选按钮
	 */
	public List<WebElement> webRadioGroup(List<WebElement> elements) {

		try {
			List<WebElement> radios = elements; // 定位所有单选按钮
			return radios;
		} catch (IndexOutOfBoundsException e) {
			FileHelper.error("单选按钮不存在！", e);
			Assert.fail("单选按钮不存在！");
			return null;
		} catch (ElementNotVisibleException e) {
			FileHelper.error("XPath匹配多个单选按钮！", e);
			Assert.fail("XPath匹配多个单选按钮！");
			return null;
		}
	}

	/*
	 * 下拉选项根据index选择，先定位下拉元素element
	 */

	public void dropDownListByIndex(WebElement element, int indexnumber) {

		try {
			if (element.isEnabled()) {
				org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(element);
				select.selectByIndex(indexnumber);
				FileHelper.info("元素 " + element.toString() + " 已选择");
			}
		} catch (Exception e) {
			FileHelper.info(e.getMessage());
			Assert.fail("元素没有被选择！");
		}

	}

	/*
	 * 下拉选项根据VisibleText选择，先定位下拉元素element
	 */
	public void dropDownListByVisibleText(WebElement element, String text) {

		try {
			if (element.isEnabled()) {
				org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(element);
				select.deselectByVisibleText(text);
				FileHelper.info("元素 " + element.toString() + " 已选择");
			}
		} catch (Exception e) {
			FileHelper.error(e.getMessage());
			Assert.fail("元素没有被选择！");
		}

	}

	/*
	 * 检查元素是否被勾选
	 */
	public boolean isSelected(WebElement element) {
		boolean flag = false;
		if (element.isSelected() == true) {
			FileHelper.info("The element: [" + getLocatorByElement(element, ">") + "] is selected");
			flag = true;
		} else if (element.isSelected() == false) {
			FileHelper.error("The element: [" + getLocatorByElement(element, ">") + "] is not selected");
			flag = false;
			Assert.fail("元素没有被勾选！");
		}
		return flag;
	}

	/*
	 * 判断实际文本时候包含期望文本
	 * 
	 * @param actual 实际文本
	 * 
	 * @param expect 期望文本
	 */
	public void isContains(String actual, String expect) {
		try {
			Assert.assertTrue(actual.contains(expect));
		} catch (AssertionError e) {
			FileHelper.error("The [" + actual + "] is not contains [" + expect + "]");
			Assert.fail("The [" + actual + "] is not contains [" + expect + "]");
		}
		FileHelper.info("The [" + actual + "] is contains [" + expect + "]");
	}

	/*
	 * 判断一个页面元素是否显示在当前页面
	 */
	protected void verifyElementIsPresent(WebElement element) {

		try {
			if (element.isDisplayed()) {
				FileHelper.info("This Element " + element.toString().trim() + " is present.");
			}
		} catch (Exception e) {
			FileHelper.error("元素没有显示！" + e.getMessage());
			Assert.fail("元素没有显示！");
		}
	}

	/*
	 * 在给定的时间内去查找元素，如果没找到则超时，抛出异常
	 */
	public void waitForElementToLoad(int timeOut, final WebElement element) {
		FileHelper.info("开始查找元素[" + element.toString() + "]");
		try {
			(new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {

				public Boolean apply(WebDriver driver) {
					return element.isDisplayed();
				}
			});
		} catch (TimeoutException e) {
			FileHelper.error("超时!! " + timeOut + " 秒之后还没找到元素 [" + element + "]");
			Assert.fail("超时!! " + timeOut + " 秒之后还没找到元素 [" + element + "]");

		}
		FileHelper.info("找到了元素 [" + element + "]");
	}

	/*
	 * 获取页面的标题
	 */
	protected String getCurrentPageTitle() {

		pageTitle = driver.getTitle();

		FileHelper.info("Current page title is " + pageTitle);
		return pageTitle;
	}

	/*
	 * 获取页面的url
	 */
	protected String getCurrentPageUrl() {

		pageUrl = driver.getCurrentUrl();

		FileHelper.info("Current page title is " + pageUrl);
		return pageUrl;
	}

	/*
	 * 刷新方法包装
	 */
	public void refresh() {
		driver.navigate().refresh();

		FileHelper.info("页面刷新成功！");
	}

	/*
	 * 后退方法包装
	 */
	public void back() {
		driver.navigate().back();

		FileHelper.info("页面返回！");
	}

	/*
	 * 前进方法包装
	 */
	public void forward() {
		driver.navigate().forward();
		// Logger.Output(LogType.LogTypeName.INFO,"页面前进！");
		FileHelper.info("页面前进！");

	}

	/*
	 * 元素悬停
	 */
	public void hover(WebElement element) {

		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).perform();
		} catch (NoSuchElementException e) {
			FileHelper.error("悬停对象不存在！", e);
		} catch (ElementNotVisibleException e) {
			FileHelper.error("XPath匹配多个悬停对象！", e);
		}

	}

	/*
	 * 等待alert出现
	 */
	public Alert switchToPromptedAlertAfterWait(long waitMillisecondsForAlert) throws NoAlertPresentException {
		final int ONE_ROUND_WAIT = 200;
		NoAlertPresentException lastException = null;

		long endTime = System.currentTimeMillis() + waitMillisecondsForAlert;

		for (long i = 0; i < waitMillisecondsForAlert; i += ONE_ROUND_WAIT) {

			try {
				Alert alert = driver.switchTo().alert();
				return alert;
			} catch (NoAlertPresentException e) {
				lastException = e;
				FileHelper.error("没有找到alter：", e);
				Assert.fail("alter弹出失败");
			}
			pause(ONE_ROUND_WAIT);

			if (System.currentTimeMillis() > endTime) {
				break;
			}
		}
		throw lastException;
	}

	/*
	 * 暂停当前用例的执行，暂停的时间为：sleepTime
	 */
	public void pause(int sleepTime) {
		if (sleepTime <= 0) {
			return;
		}
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			FileHelper.error("错误信息为：", e);
		}
	}

	/*
	 * 根据元素来获取此元素的定位值
	 */
	public String getLocatorByElement(WebElement element, String expectText) {
		String text = element.toString();
		String expect = null;
		try {
			expect = text.substring(text.indexOf(expectText) + 1, text.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
			FileHelper.error("failed to find the string [" + expectText + "]");
			Assert.fail("元素查找失败");
		}
		return expect;

	}

	/*********************************
	 * ------切换frame、window---------
	 *************************************************/
	/*
	 * 处理多窗口之间切换
	 */
	public void switchWindow() {
		String currentWindow = driver.getWindowHandle();// 获取当前窗口句柄
		Set<String> handles = driver.getWindowHandles();// 获取所有窗口句柄
		FileHelper.info("当前窗口数量： " + handles.size());
		// Logger.Output(LogType.LogTypeName.INFO, "当前窗口数量： " + handles.size());
		Iterator<String> it = handles.iterator();
		while (it.hasNext()) {
			if (currentWindow == it.next()) {
				continue;
			}
			try {
				driver.close();// 关闭旧窗口
				WebDriver window = driver.switchTo().window(it.next());// 切换到新窗口
				// Logger.Output(LogType.LogTypeName.INFO, "new page title is "
				// + window.getTitle());
				FileHelper.info("new page title is " + window.getTitle());
			} catch (Exception e) {
				// Logger.Output(LogType.LogTypeName.ERROR, "法切换到新打开窗口" +
				// e.getMessage());
				FileHelper.error("法切换到新打开窗口" + e.getMessage());
			}
			// driver.close();//关闭当前焦点所在的窗口
		}
		// driver.switchTo().window(currentWindow);//回到原来页面
	}

	/*
	 * 切换frame - 根据frame在当前页面中的顺序来定位
	 */
	public void inFrame(int frameNum) {
		driver.switchTo().frame(frameNum);
	}

	/*
	 * 切换frame - 根据页面元素定位
	 */
	public void switchFrame(WebElement element) {
		try {
			FileHelper.info("正在跳进frame:[" + getLocatorByElement(element, ">") + "]");
			driver.switchTo().frame(element);
		} catch (Exception e) {
			FileHelper.error("跳进frame: [" + getLocatorByElement(element, ">") + "] 失败");
			Assert.fail("跳进frame: [" + getLocatorByElement(element, ">") + "] 失败");
		}
		FileHelper.error("进入frame: [" + getLocatorByElement(element, ">") + "]成功 ");
	}

}
