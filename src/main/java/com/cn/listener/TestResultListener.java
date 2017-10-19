package com.cn.listener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import com.cn.basepage.BasePage;
import com.cn.configlog.FileHelper;


public class TestResultListener implements ITestListener {

	public ITestContext context = null; 


	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		this.context = context;
	}
	@Override
	public void onTestFailure(ITestResult result) {
		FileHelper.info("onTestFailure(" + result + ")");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String fileName = result.getName() + "_" + df.format(new Date()) + ".png";
		String filePath = "result/screenshot/" + fileName;
		try {
			File screenshot = ((TakesScreenshot) BasePage.driver).getScreenshotAs(OutputType.FILE);
			File destFile = new File(filePath);
			FileUtils.copyFile(screenshot, destFile);
			FileHelper.info("[" + fileName + "] 截图成功-->保存在：" + "[ " + filePath + " ]");

		} catch (Exception e) {

			FileHelper.error(filePath = "[" + fileName + "]" + " ,截图失败-->原因：" + e.getMessage());
		}
		if (!"".equals(filePath)) {
			Reporter.setCurrentTestResult(result);
			Reporter.log(filePath);
			// 把截图写入到Html报告中方便查看
			
			boolean escapeHtml = Reporter.getEscapeHtml();
			Reporter.setEscapeHtml(false);
			Reporter.log("<img src=\""+filePath+"\" hight='786' width='1024'/>");
			Reporter.setEscapeHtml(escapeHtml);
		}
	}

	@Override
	public void onTestSkipped(ITestResult tr) {

		FileHelper.info(tr.getName() + " 测试用例失败，开始重跑被跳过！");
	}

	@Override
	public void onTestSuccess(ITestResult tr) {

		FileHelper.info(tr.getName() + " 测试用例执行成功！");
	}

	@Override
	public void onTestStart(ITestResult tr) {
	
		FileHelper.info(tr.getName() + " 测试用例开始执行！");
	}

	@Override
	public void onFinish(ITestContext testContext) {

		// List of test results which we will delete later
		ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
		// collect all id's from passed test
		Set<Integer> passedTestIds = new HashSet<Integer>();
		for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) {
			FileHelper.info("执行成功的用例 = " + passedTest.getName());
			passedTestIds.add(getId(passedTest));
		}

		// Eliminate the repeat methods
		Set<Integer> skipTestIds = new HashSet<Integer>();
		for (ITestResult skipTest : testContext.getSkippedTests().getAllResults()) {
			FileHelper.info("被跳过的用例 = " + skipTest.getName());
			// id = class + method + dataprovider
			int skipTestId = getId(skipTest);
			if (skipTestIds.contains(skipTestId) || passedTestIds.contains(skipTestId)) {
				testsToBeRemoved.add(skipTest);
			} else {
				skipTestIds.add(skipTestId);
			}
		}

		// Eliminate the repeat failed methods
		Set<Integer> failedTestIds = new HashSet<Integer>();
		for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) {
			FileHelper.info("执行失败的用例 = " + failedTest.getName());
			// id = class + method + dataprovider
			int failedTestId = getId(failedTest);

			// if we saw this test as a failed test before we mark as to be
			// deleted
			// or delete this failed test if there is at least one passed
			// version
			if (failedTestIds.contains(failedTestId) || passedTestIds.contains(failedTestId)) {
				testsToBeRemoved.add(failedTest);
			} else {
				failedTestIds.add(failedTestId);
			}
		}

		// finally delete all tests that are marked
		for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator(); iterator
				.hasNext();) {
			ITestResult testResult = iterator.next();
			if (testsToBeRemoved.contains(testResult)) {
				FileHelper.info("移除重复失败的用例 = " + testResult.getName());
				iterator.remove();
			}
		}

	}

	private int getId(ITestResult result) {
		int id = result.getTestClass().getName().hashCode();
		id = id + result.getMethod().getMethodName().hashCode();
		id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
		return id;
	}



	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

}
