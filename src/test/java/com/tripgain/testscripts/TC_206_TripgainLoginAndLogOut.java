package com.tripgain.testscripts;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.tripgain.collectionofpages.Tripgain_Bookingpage;
import com.tripgain.collectionofpages.Tripgain_Login;
import com.tripgain.collectionofpages.Tripgain_RoundTripResultsScreen;
import com.tripgain.collectionofpages.Tripgain_homepage;
import com.tripgain.collectionofpages.Tripgain_registrationpage;
import com.tripgain.collectionofpages.Tripgain_resultspage;
import com.tripgain.common.Log;
import com.tripgain.common.ReRunAutoMationScripts;
import com.tripgain.common.ScreenShots;
import com.tripgain.common.getDataFromExcel;
import com.tripgain.common.DataProviderUtils;
import com.tripgain.common.ExtantManager;
import com.tripgain.common.GenerateDates;
import com.tripgain.common.Getdata;

@Listeners(com.tripgain.common.TestListener.class)
public class TC_206_TripgainLoginAndLogOut extends BaseClass {
	WebDriver driver;    
	ExtentReports extent;
    ExtentTest test;
    String className = "";
    Log Log;  // Declare Log object
    ScreenShots screenShots;  // Declare Log object
    ExtantManager extantManager;

    
	@Test(retryAnalyzer=ReRunAutoMationScripts.class)
	public void myTest() throws IOException, InterruptedException, AWTException
	{
	    String[] data = Getdata.getexceldata();
        String userName = data[0]; 
        String password = data[1];
        
        
        // Login to TripGain Application
        Tripgain_Login tripgainLogin= new Tripgain_Login(driver);
        tripgainLogin.enterUserName(userName);
        tripgainLogin.enterPasswordName(password);
        tripgainLogin.clickButton(); 
		Log.ReportEvent("PASS", "Enter UserName and Password is Successful");	
		screenShots.takeScreenShot1();

        
        //Functions to Search on Home Page     
        Tripgain_homepage tripgainhomepage = new Tripgain_homepage(driver); 
        Thread.sleep(4000);
      //  tripgainLogin.clickButton();
        tripgainhomepage.logOutFromApplication(Log, screenShots);
//		screenshot.takeScreenShot1();

		driver.quit();

       }
	@BeforeClass
	@Parameters("browser")
	public void launchApplication(String browser)
	{
		extantManager=new ExtantManager();
		extantManager.setUpExtentReporter(browser);
        className = this.getClass().getSimpleName();
        extantManager.createTest(className);  // Get the ExtentTest instance
        test=ExtantManager.getTest();
        extent=extantManager.getReport();
        test.log(Status.INFO, "Execution Started Successful");	
        driver=launchBrowser(browser);      
        Log = new Log(driver, test);
        screenShots=new ScreenShots(driver, test);
	}
}
