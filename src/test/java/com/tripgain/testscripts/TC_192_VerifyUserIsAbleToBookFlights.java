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
public class TC_192_VerifyUserIsAbleToBookFlights extends BaseClass {

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
		int selectFlightIndex = Integer.parseInt(excelData.get("SelectFlightIndex"));
		int adults = Integer.parseInt(excelData.get("Adults"));
		int stopsIndex = (int) Double.parseDouble(excelData.get("StopsIndex"));
		String reason=excelData.get("Reason");
		String fareType=excelData.get("FareType");
		String title=excelData.get("Title");
        
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
        Tripgain_Bookingpage tripgainbookingpage =new Tripgain_Bookingpage(driver);

Thread.sleep(6000);
tripgainhomepage.searchFlightsOnHomePage(origin, destination,  fromDate, fromMonthYear, travelClass, 2, Log, screenShots);
        Thread.sleep(5000); 
        tripgainresultspage.validateFlightsResults(Log, screenShots);
        Thread.sleep(3000); 
      //Functions to validate Default Currency Code is Selected        
        tripgainresultspage.verifyDefaultCurrencyIsSelected(Log, screenShots);

        //Function to Validate Date On Result Screen
        tripgainresultspage.validateAllDatesInResults(Log,screenShots);
		Thread.sleep(4000);

        //Function to get and Validate Flight Details
        tripgainresultspage.selectFlightBasedOnIndex(1);
        String stops=tripgainresultspage.getStopsText(stopsIndex);
        String classes=tripgainresultspage.getClassText();
		Thread.sleep(4000);

//        tripgain_resultspage.validateCabinClasses(classes,Log,screenShots);
        String[] flightDetails=tripgainresultspage.getFlightDetails(stops,Log,screenShots);
		Thread.sleep(4000);

        String[] fareDetails=tripgainresultspage.clickOnContinueBasedOnFareType(fareType,reason);
        tripgainresultspage.validateFlightDetailsInBookingScreen(Log,screenShots,fareDetails[0],fareDetails[1],stops,classes,flightDetails[0],flightDetails[1],flightDetails[2],flightDetails[3],flightDetails[4],flightDetails[5]);
        String[] titles = title.split(",");
		Thread.sleep(4000);

        tripgainresultspage.enterAdultDetailsForDomestic(titles,adults,Log,screenShots);
		Thread.sleep(4000);

        int price=tripgainresultspage.selectSeatFormPickSeat(Log,screenShots);
		Thread.sleep(4000);

        tripgainresultspage.selectMealAndBaggageForEachPassengerOnBookingScreen(Log,screenShots);
		Thread.sleep(4000);

        tripgainresultspage.getAndValidateMealBaggagePriceDetails(price,Log,screenShots);
		Thread.sleep(4000);

        tripgainresultspage.selectDepartment();
        tripgainresultspage.selectProject();
        tripgainresultspage.selectCostcenter();
		Thread.sleep(4000);

        //Functions to Send Approval on Booking Page
        tripgainbookingpage.clickOnSendApprovalButton();
		Thread.sleep(4000);

        tripgainbookingpage.validateSendApprovalToastMessage(Log, screenShots);

    
      
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
