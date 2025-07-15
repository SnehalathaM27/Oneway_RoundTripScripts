package com.tripgain.testscripts;

import java.awt.AWTException;
import java.io.IOException;
import java.text.ParseException;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
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
import com.tripgain.collectionofpages.Tripgain_Login;
import com.tripgain.collectionofpages.Tripgain_homepage;
import com.tripgain.collectionofpages.Tripgain_registrationpage;
import com.tripgain.collectionofpages.Tripgain_resultspage;
import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;



import com.tripgain.common.ExtantManager;
import com.tripgain.common.Getdata;

@Listeners(com.tripgain.common.TestListener.class)
public class TC_172_validatingRegistrationPage extends BaseClass {

	WebDriver driver;    
	ExtentReports extent;
    ExtentTest test;
    String className = "";
    Log Log;  // Declare Log object
    ScreenShots screenShots;  // Declare Log object
    ExtantManager extantManager;
  
    int number=1;

	@Test
	public void myTest() throws IOException, InterruptedException, AWTException, TimeoutException, ParseException
	{
		Tripgain_homepage tripgainhomepage = new Tripgain_homepage(driver);

        Tripgain_registrationpage tr=new Tripgain_registrationpage(driver);
        Thread.sleep(3000);
        tr.clickregisterpage();
        tr.selectSalutation("Mrs");
        tr.enterfirstname("sneha");
        tr.enterlastname("latha");
        tr.selectregistrationas("Business Traveller");
        tr.entercompanyname("TG");
        tr.enterworkemail("TG@gmail.com");
        tr.entermobile("789654321");
        tr.enterpassword("fgtr@45");
        tr.clickregisterbutton();
      
  
         driver.quit();
         
       }
	
	  @BeforeMethod
	    @Parameters("browser")
	    public void launchApplication(String browser)
	    {
	       extantManager=new ExtantManager();
	       extantManager.setUpExtentReporter(browser);
	       className = this.getClass().getSimpleName();
	       String testName=className+"_"+number;
	       extantManager.createTest(testName);  // Get the ExtentTest instance
	       test=ExtantManager.getTest();
	       extent=extantManager.getReport();
	       test.log(Status.INFO, "Execution Started Successful"); 
	       driver=launchBrowser(browser);      
	       Log = new Log(driver, test);
	       screenShots=new ScreenShots(driver, test);
	    }

	    @AfterMethod
	    public void tearDown() {
	       if (driver != null) {
	          driver.quit();
	          extantManager.flushReport();
	       }
	    }
	
}
