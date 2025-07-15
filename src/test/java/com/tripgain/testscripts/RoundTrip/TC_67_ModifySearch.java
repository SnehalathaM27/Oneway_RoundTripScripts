package com.tripgain.testscripts.RoundTrip;

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

public class TC_67_ModifySearch extends BaseClass{
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
    public void myTest(Map<String, String> excelData) throws Exception {
        System.out.println("Running test with: " + excelData);
	    String[] data = Getdata.getexceldata();
        String userName = data[0]; 
        String password = data[1];
        number++;

        String origin = excelData.get("Origin");
        String destination = excelData.get("Destination");
        String travelClass = excelData.get("Class");
        int adults = Integer.parseInt(excelData.get("Adults"));
        String from = excelData.get("From");
        String day = excelData.get("Day");
        String monthandYear = excelData.get("MonthandYear");
        int modifyAdults = Integer.parseInt(excelData.get("ModifyAdults"));
        int index = Integer.parseInt(excelData.get("Index"));
        String returnDate1 = excelData.get("ReturnDate1");
        String returnMonthYear1 = excelData.get("ReturnMonthYear1");

        
        String[] dates=GenerateDates.GenerateDatesToSelectFlights();
        String fromDate=dates[0];
        String fromMonthYear=dates[2];
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

        
        //Functions to Search on Home Page     
        Tripgain_homepage tripgainhomepage = new Tripgain_homepage(driver);
        Tripgain_resultspage tripgainresultspage=new Tripgain_resultspage(driver);
        Tripgain_RoundTripResultsScreen trs=new Tripgain_RoundTripResultsScreen(driver);
        
        
        
        Tripgain_FutureDates TripgainFutureDates=new Tripgain_FutureDates(driver);
        Map<String, Tripgain_FutureDates.DateResult> dateResults = TripgainFutureDates.furtherDate();
        Tripgain_FutureDates.DateResult date5 = dateResults.get("datePlus5");//from date
        Tripgain_FutureDates.DateResult date15 = dateResults.get("datePlus15");//return date
        policyDates  policyDates= new policyDates(driver);
       
        String monthYear=date5.month + " " + date5.year;

         tripgainhomepage.Clickroundtrip();
       //  policyDates.searchFlightsOnHomePage(origin, destination, date5.month, date5.year, date5.day, date15.month, date15.year, date15.day, travelClass, adults);

         tripgainhomepage.searchFlightsOnHomePage(Log, screenShots,origin, destination,  fromDate, fromMonthYear, returnDate1, returnMonthYear1, travelClass, adults);
         Thread.sleep(5000);
         trs.validateFlightsResultsForRoundTrip(Log, screenShots);
 Thread.sleep(15000); 
 
 //Function to validate Modify search
 
 tripgainhomepage.modifySearch(from, day, monthandYear, modifyAdults);
 //tripgainhomepage.modifySearch(from, date15.month, date15.year, date15.day, modifyAdults);
 Thread.sleep(7000);
 trs.validateBeforeSwap(Log, screenShots, index);
 
// tripgainhomepage.selectDate(returnDate, returnMonthYear);
 trs.ValidateBookingPageFlightsDetails(Log, screenShots);
 
 
 
 
 
 
 
 
 
 
 
 
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