package com.tripgain.testscripts;

import java.awt.AWTException;
import java.io.IOException;
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
import com.tripgain.Tripgain_ApprovalPage;
import com.tripgain.collectionofpages.Tripgain_Bookingpage;
import com.tripgain.collectionofpages.Tripgain_Login;
import com.tripgain.collectionofpages.Tripgain_RoundTripResultsScreen;
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
public class TC_191_ValidateApprovalPage extends BaseClass {

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
        String[] dates=GenerateDates.GenerateDatesToSelectFlights();
        String fromDate=dates[0];
       String fromMonthYear=dates[2];
       Thread.sleep(2000);

       String origin = excelData.get("Origin");
       String destination = excelData.get("Destination");
       String travelClass = excelData.get("Class");
       int adults = Integer.parseInt(excelData.get("Adults"));
       int selectFlightBasedOnIndex = Integer.parseInt(excelData.get("selectFlightBasedOnIndex"));
       int DepatureIndex = Integer.parseInt(excelData.get("DepatureIndex"));
       int ArrivalIndex = Integer.parseInt(excelData.get("ArrivalIndex"));
       int PriceIndex = Integer.parseInt(excelData.get("PriceIndex"));

     

        
        // Login to TripGain Application
        Tripgain_Login tripgainLogin= new Tripgain_Login(driver);
        Tripgain_RoundTripResultsScreen trs=new Tripgain_RoundTripResultsScreen(driver);
        tripgainLogin.enterUserName(userName);
        tripgainLogin.enterPasswordName(password);
        tripgainLogin.clickButton(); 
		Log.ReportEvent("PASS", "Enter UserName and Password is Successful");
		Thread.sleep(2000);
		screenShots.takeScreenShot1();

        
        //Functions to Search on Home Page     
        Tripgain_homepage tripgainhomepage = new Tripgain_homepage(driver);
        Tripgain_resultspage tripgainresultspage=new Tripgain_resultspage(driver);
        Tripgain_Bookingpage tripgainbookingpage =new Tripgain_Bookingpage(driver);
        Tripgain_ApprovalPage TripgainApprovalPage=new com.tripgain.Tripgain_ApprovalPage(driver);

        
        String profile[]= tripgainhomepage.MenuOption("Profile");
        tripgainhomepage.validatePolicyGradeL1("L1 - L1 Test Grade",Log,screenShots);
        String travel= tripgainhomepage.travelMenu("Flights", Log, screenShots);
        
        
Thread.sleep(6000);
tripgainhomepage.searchFlightsOnHomePage(origin, destination,  fromDate, fromMonthYear, travelClass, 2, Log, screenShots);
        Thread.sleep(5000); 
        String[] userInput = trs.userEnterData(); 

        //Functions to Validate flights on Home Page
        tripgainresultspage.validateFlightsResults(Log,screenShots);
        tripgainresultspage.validateFlightDetailsOnResultScreen(Log,screenShots);
        
        
        tripgainresultspage.selectFlightBasedOnIndex(selectFlightBasedOnIndex);
        tripgainresultspage.validateDataAfterSelectingFlight(Log, screenShots, DepatureIndex, ArrivalIndex,PriceIndex );
        tripgainresultspage.selectFlightBasedOnIndex(selectFlightBasedOnIndex);
        
        String[] resultData = tripgainresultspage.selectFlightUntilFareTypeFound1("Flexi", userInput[0], userInput[1], userInput[2], Log, screenShots);

        System.out.println("Flight Details:");
        System.out.println("From: " + resultData[0]);
        System.out.println("To: " + resultData[1]);
        System.out.println("Date: " + resultData[2]);
        System.out.println("Flight Code: " + resultData[3]);
        System.out.println("Departure Time: " + resultData[4]);
        System.out.println("Arrival Time: " + resultData[5]);
        // System.out.println("Price: " + resultData[6]); // Optional: Uncomment if needed
        System.out.println("Flight Name: " + resultData[7]);
        System.out.println("Stops: " + resultData[8]);
        System.out.println("Price: " + resultData[9]);

        tripgainresultspage.validateFlightDetailsOnBookingPage(resultData, Log, screenShots);
        tripgainbookingpage.sendApproval(Log, screenShots);

        Thread.sleep(3000);

        TripgainApprovalPage.approvalPageValidation(resultData[0], resultData[1], travel, Log, screenShots, profile);
		Thread.sleep(4000);

        TripgainApprovalPage.validateDetailsNavBar1(resultData, Log, screenShots);

      //Function to Logout from Application
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
