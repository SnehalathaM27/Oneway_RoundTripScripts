package com.tripgain.testscripts.RoundTrip;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.tripgain.collectionofpages.Tripgain_FutureDates;
import com.tripgain.collectionofpages.Tripgain_Login;
import com.tripgain.collectionofpages.Tripgain_RoundTripResultsScreen;
import com.tripgain.collectionofpages.Tripgain_homepage;
import com.tripgain.collectionofpages.Tripgain_resultspage;
import com.tripgain.collectionofpages.policyDates;
import com.tripgain.common.DataProviderUtils;
import com.tripgain.common.ExtantManager;
import com.tripgain.common.GenerateDates;
import com.tripgain.common.Getdata;
import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;
import com.tripgain.testscripts.BaseClass;

public class TC_93_EntireTestcasesCode2 extends BaseClass{
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
	    String[] data = Getdata.getexceldata();
        String userName = data[0]; 
        String password = data[1];
        number++;

        String origin = excelData.get("Origin");
        String destination = excelData.get("Destination");
        String travelClass = excelData.get("Class");
        int adults = Integer.parseInt(excelData.get("Adults"));

        String times = excelData.get("Times");
        int flightStartHour = Integer.parseInt(excelData.get("FlightStartHour"));
        int flightStartMinute = Integer.parseInt(excelData.get("FlightStartMinute"));
        int flightEndHour = Integer.parseInt(excelData.get("FlightEndHour"));
        int flightEndMinute = Integer.parseInt(excelData.get("FlightEndMinute"));
        
        int index = Integer.parseInt(excelData.get("Index"));
        
        int policyIndex = Integer.parseInt(excelData.get("PolicyIndex"));
        
        String stops = excelData.get("Stops");
        
        String policyText = excelData.get("PolicyText");
        
        int bookingIndex = Integer.parseInt(excelData.get("BookingIndex"));

        int departFareIndex = Integer.parseInt(excelData.get("DepartFareIndex"));
        int returnfareIndex = Integer.parseInt(excelData.get("ReturnfareIndex"));
        String departFare = excelData.get("DepartFare");
        String returnFare = excelData.get("ReturnFare");


        
      
        String[] dates=GenerateDates.GenerateDatesToSelectFlights();
        String fromDate=dates[11];
        String fromMonthYear=dates[12];
        String returnDate=dates[1]; 
        String returnMonthYear=dates[3];
        
        
        // Login to TripGain Application
        Tripgain_Login tripgainLogin= new Tripgain_Login(driver);
        tripgainLogin.enterUserName(userName);
        tripgainLogin.enterPasswordName(password);
        tripgainLogin.clickButton(); 
		Log.ReportEvent("PASS", "Enter UserName and Password is Successful");
		Thread.sleep(2000);
		screenShots.takeScreenShot1();

		 
		//Functions to Search flights on Home Page  
		
        Tripgain_homepage tripgainhomepage = new Tripgain_homepage(driver);
        Tripgain_resultspage tripgainresultspage=new Tripgain_resultspage(driver);
        Tripgain_RoundTripResultsScreen trs=new Tripgain_RoundTripResultsScreen(driver);
		
	
        tripgainhomepage.Clickroundtrip();
        tripgainhomepage.searchFlightsOnHomePage(Log, screenShots,origin, destination,  fromDate, fromMonthYear, returnDate, returnMonthYear, travelClass, adults);
        Thread.sleep(5000);

        trs.validateFlightsResultsForRoundTrip(Log, screenShots);
Thread.sleep(15000); 
		
//--------------------------------------------------------------------------------------------------

                                  //Function to click on  Depart Time 
/*
trs.selectOnWardDepartTimeroundtrip(times);
//trs.validateRoundTripOnwardDepartureTimeIsSelected(Log, screenShots, "00 - 06");
trs.validateFlightsDepartureTimeOnResultScreen(flightStartHour, flightStartMinute, flightEndHour, flightEndMinute, Log, screenShots);

//--------------------------------------------------------------------------------------------------------------
Thread.sleep(3000);

                                        //Refundable fare

trs.clickRefundableFareRoundTrip();
Thread.sleep(3000);
trs.validateRefundableFare(index, Log, screenShots);

//------------------------------------------------------------------------------------------------------- 

Thread.sleep(3000);

                                        //Policy Filter

 
 trs.validateOutOfPolicyFilterRoundTrip(policyIndex, policyText, Log, screenShots);
 Thread.sleep(5000);
 driver.navigate().back();
  */
 //---------------------------------------------------------------------------------------------------------
 
 Thread.sleep(5000);

    
 trs.roundTripClickOnWardStops(stops);  

  //Checking Location details From Resultpg To Bookingpg  */
  trs.validateLocationsFromResultToBookingPage(Log, screenShots, bookingIndex);

  //----------------------------------------------------------------------------------------------------
  

		Thread.sleep(5000);
		  driver.navigate().back();
			Thread.sleep(6000);
			
//----------------------------------------------------------------------------------------
			


                              //fare rules for round trip
  
  trs.validateDepatureFaretypeToBookingPg(departFareIndex, departFare, Log, screenShots);
  Thread.sleep(3000);
  driver.navigate().back();
  Thread.sleep(3000);
  trs.validateReturnFaretypeToBookingPg(returnfareIndex, returnFare, Log, screenShots);
  
  //----------------------------------------------------------------------------------------------------
  
                                   //Function to Logout from Application
  
    		//tripgainhomepage.logOutFromApplication(Log, screenShots);
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