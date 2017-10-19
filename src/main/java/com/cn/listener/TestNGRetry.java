package com.cn.listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.cn.configlog.FileHelper;

/*
 * 用例失败自动重跑逻辑
 *
 *
 */
public class TestNGRetry implements IRetryAnalyzer {

	private int retryCount = 0;
	private int maxRetryCount = 0;

	public boolean retry(ITestResult result) {
		if (retryCount <= maxRetryCount) {
			String message = "running retry for  '" + result.getName() + "' on class " + this.getClass().getName()
					+ " Retrying " + retryCount + " times";
			FileHelper.info(message);
			retryCount++;
			return true;
		}
		return false;
	}
}