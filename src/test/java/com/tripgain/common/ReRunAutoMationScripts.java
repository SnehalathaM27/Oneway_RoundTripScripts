package com.tripgain.common;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class ReRunAutoMationScripts implements IRetryAnalyzer{
	private int retryCount=0;
	private static final int maxCount=5;
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if(retryCount<maxCount) {
			retryCount++;
			return true;
		}
		
		return false;
	}

}
