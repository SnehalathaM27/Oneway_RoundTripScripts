package com.tripgain.testscripts;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
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
import com.tripgain.collectionofpages.Tripgain_resultspage;
import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;
import com.tripgain.common.getDataFromExcel;
import com.tripgain.common.DataProviderUtils;
import com.tripgain.common.ExtantManager;
import com.tripgain.common.GenerateDates;
import com.tripgain.common.Getdata;

@Listeners(com.tripgain.common.TestListener.class)
public class TC_174_VerifyOnwardArrivalTime06_12Selection extends BaseClass {

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
    	System.out.println("Running test with: " + excelData);    //To get Data from Excel    
    	   
    		String[] data = Getdata.getexceldata();  
    		String userName = data[0];    
    		String password = data[1];  
    	
        String[] dates=GenerateDates.GenerateDatesToSelectFlights();
        String fromDate=dates[0];
       String fromMonthYear=dates[2];
       Thread.sleep(2000);

    //   Map<String, String> excelData = getDataFromExcel.getExcelData("TC_174");
       String origin = excelData.get("Origin");
       String destination = excelData.get("Destination");
       String travelClass = excelData.get("Class");
       int adults = Integer.parseInt(excelData.get("Adults"));
       String times = excelData.get("Times");
       int flightStartHour = Integer.parseInt(excelData.get("FlightStartHour"));
       int flightStartMinute = Integer.parseInt(excelData.get("FlightStartMinute"));
       int flightEndHour = Integer.parseInt(excelData.get("FlightEndHour"));
       int flightEndMinute = Integer.parseInt(excelData.get("FlightEndMinute"));
        
        // Login to TripGain Application
        Tripgain_Login tripgainLogin= new Tripgain_Login(driver);
        tripgainLogin.enterUserName(userName);
        tripgainLogin.enterPasswordName(password);
        tripgainLogin.clickButton(); 
		Log.ReportEvent("PASS", "Enter UserName and Password is Successful");
		Thread.sleep(2000);
		screenShots.takeScreenShot1();

        
        //Functions to Search on Home Page     
        Tripgain_homepage tripgainhomepage = new Tripgain_homepage(driver);
        Tripgain_resultspage tripgainresultspage=new Tripgain_resultspage(driver);
Thread.sleep(6000);
tripgainhomepage.searchFlightsOnHomePage(origin, destination,  fromDate, fromMonthYear, travelClass, adults, Log, screenShots);
        Thread.sleep(5000); 
        tripgainresultspage.validateFlightsResults(Log, screenShots);
        Thread.sleep(3000); 
      tripgainresultspage.selectOnWardArrivalTime(times);
      Thread.sleep(3000); 
     // tripgainresultspage.validateOnwardArrivalTime(Log, screenShots, "06 - 12");
      tripgainresultspage.validateFlightsArrivalTimeOnResultScreen(flightStartHour, flightStartMinute, flightEndHour, flightEndMinute, Log, screenShots);

      Thread.sleep(3000); 
      //Function to Logout from Application
		//tripgainhomepage.logOutFromApplication(Log, screenShots);
       }
	
    @BeforeMethod
    @Parameters("browser")
    public void launchApplication(String browser, ITestContext context){    
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
    		}}
 
	
}
