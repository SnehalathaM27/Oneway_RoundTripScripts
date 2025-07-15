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
import com.tripgain.collectionofpages.Tripgain_FutureDates;
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
public class TC_207_VerifyFromFieldErrorMessageOnHomeScreen extends BaseClass {
	WebDriver driver;    
	ExtentReports extent;
    ExtentTest test;
    String className = "";
    Log Log;  // Declare Log object
    ScreenShots screenShots;  // Declare Log object
    ExtantManager extantManager;
    int number=1;
    
    private WebDriverWait wait;

    @Test(dataProvider = "sheetBasedData", dataProviderClass = DataProviderUtils.class)
    public void myTest(Map<String, String> excelData) throws InterruptedException, IOException {
        System.out.println("Running test with: " + excelData);
    	
        //To get Data from Excel
        String[] data = Getdata.getexceldata();
        String userName = data[0]; 
        String password = data[1]; 
        number++;

        
        String destination = excelData.get("Destination");
        String travelClass = excelData.get("Class");
        int adults = Integer.parseInt(excelData.get("Adults"));
        
        int selectFlightBasedOnIndex = Integer.parseInt(excelData.get("selectFlightBasedOnIndex"));
        String DepatureIndex = excelData.get("DepatureIndex");
        String ArrivalIndex = excelData.get("ArrivalIndex");
        String PriceIndex = excelData.get("PriceIndex");
        
        //Functions to Login TripGain Application
        Tripgain_Login tripgainLogin= new Tripgain_Login(driver);
        tripgainLogin.enterUserName(userName);
        tripgainLogin.enterPasswordName(password);
        tripgainLogin.clickButton();   
        Thread.sleep(2000);
        tripgainLogin.verifyHomePageIsDisplayed(Log,screenShots); 

        //Functions to Search flights on Home Page     
        Tripgain_homepage tripgainhomepage = new Tripgain_homepage(driver);
        Tripgain_resultspage tripgainresultspage=new Tripgain_resultspage(driver);
        Tripgain_FutureDates futureDates = new Tripgain_FutureDates(driver);
        
        
        Map<String, Tripgain_FutureDates.DateResult> dateResults = futureDates.furtherDate();
        Tripgain_FutureDates.DateResult date15 = dateResults.get("datePlus15");
        tripgainhomepage.validateErrorMsgForFromField(destination,date15.month , date15.year, date15.day ,travelClass, adults,Log, screenShots);  
        Thread.sleep(5000); 
        tripgainhomepage.verifyErrorMsgForFromField(Log,screenShots);
        
        //Function to close browser
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
