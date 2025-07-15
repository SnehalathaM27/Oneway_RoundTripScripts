package com.tripgain.testscripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;
import com.tripgain.common.ExtantManager;
import com.tripgain.common.ScreenShots;

public class BaseClass{

      
		WebDriver driver;
		public WebDriver launchBrowser(String browser)
	{
		   if (browser.equalsIgnoreCase("chrome")) {
	            // Set the path to the ChromeDriver executable (optional if already set in system PATH)
	            driver = new ChromeDriver();

	        } else if (browser.equalsIgnoreCase("firefox")) {
	            // Set the path to the GeckoDriver executable (optional if already set in system PATH)
	            driver = new FirefoxDriver();
	        } else if (browser.equalsIgnoreCase("edge")) {
	            // Set the path to the EdgeDriver executable (optional if already set in system PATH)
	            driver = new EdgeDriver();
	        } else {
	            throw new IllegalArgumentException("Unsupported browser: " + browser);
	        }
		    driver.get("https://v3.tripgain.com/login");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			return driver;
	    }
		
		@AfterSuite
		public void tearDown()
		{
			ExtantManager extantManager=new ExtantManager();
			extantManager.finalizeExtentReport();
			extantManager.flushReport();	
			}
	
}
