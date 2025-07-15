package TestScripts_Oneway_RoundTrip;
import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.tripgain.collectionofpages.Tripgain_Login;
import com.tripgain.collectionofpages.Tripgain_RoundTripResultsScreen;
import com.tripgain.collectionofpages.Tripgain_homepage;
import com.tripgain.collectionofpages.Tripgain_resultspage;
import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;
import com.tripgain.common.getDataFromExcel;
import com.tripgain.testscripts.BaseClass;
import com.tripgain.common.ExtantManager;
import com.tripgain.common.GenerateDates;
import com.tripgain.common.Getdata;

@Listeners(com.tripgain.common.TestListener.class)
public class TC_02 extends BaseClass{
	WebDriver driver;    
	ExtentReports extent;
    ExtentTest test;
    String className = "";
    Log Log;  // Declare Log object
    ScreenShots screenShots;  // Declare Log object
    ExtantManager extantManager;
  
    private WebDriverWait wait;

	@Test
	public void myTest() throws IOException, InterruptedException, AWTException
	{
	    String[] data = Getdata.getexceldata();
        String userName = data[0]; 
        String password = data[1];
        
        String[] dates=GenerateDates.GenerateDatesToSelectFlights();
        String fromDate=dates[0];
        String fromMonthYear=dates[2];
        String returnDate=dates[1]; 
        String returnMonthYear=dates[3];
        
        
        Map<String, String> excelData = getDataFromExcel.getExcelData("TC_02");
        String origin = excelData.get("Origin");
        String destination = excelData.get("Destination");
        String fromDates = excelData.get("FromDate");
        String fromMonthYears = excelData.get("FromMonthYear");
        String returnDates = excelData.get("ReturnDate");
        String returnMonthYears = excelData.get("ReturnMonthYear");

        
        String travelClass = excelData.get("Class");
        int adults = Integer.parseInt(excelData.get("Adults"));
        int selectflightbasedindex = Integer.parseInt(excelData.get("SelectFlightBasedIndex"));
        int depatureindex = Integer.parseInt(excelData.get("DepatureIndex"));
        int arrivalindex = Integer.parseInt(excelData.get("ArrivalIndex"));
        int priceindex = Integer.parseInt(excelData.get("PriceIndex"));
        String stops1 = excelData.get("Stops1");
        String numberOfStops1 = excelData.get("NumberOfStops1");
        String expectedvalue = excelData.get("ExpectedValue");
        String flightairlines = excelData.get("Airlines");
        int selectFlightBasedIndexCheckInBaggage = Integer.parseInt(excelData.get("SelectFlightBasedIndexCheckInBaggage"));
        int selectReturnFlightBasedIndexCheckInBaggage = Integer.parseInt(excelData.get("SelectReturnFlightBasedIndexCheckInBaggage"));

        
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
        Thread.sleep(3000); 
        tripgainhomepage.Clickroundtrip();

        tripgainhomepage.searchFlightsOnHomePage(Log, screenShots,origin, destination,  fromDates, fromMonthYears, returnDates, returnMonthYears, travelClass, adults);

       
        Thread.sleep(3000); 

        trs.validateFlightsResultsForRoundTrip(Log, screenShots);

      //Function to validate Check In Baggage Functionality on Result Screen
  		trs.clickroundtripcheckinbaggage();
  		Thread.sleep(4000);

  		//trs.validateCheckInBaggageFlightsOnResultScreenRoundTrip(Log, screenShots, selectFlightBasedIndexCheckInBaggage);
  		Thread.sleep(3000);
  		trs.validatereturncheckinbaggageroundtrip(Log, screenShots, 3);
  		trs.validatereturnCheckInBaggageFlightsOnResultScreenRoundTrip(Log, screenShots, selectReturnFlightBasedIndexCheckInBaggage);
        Thread.sleep(3000);

 		 //Function for stops
        trs.roundTripClickOnWardStops(stops1);  
        trs.validateFlightsStopsOnResultScreen(numberOfStops1,Log, screenShots);
       Thread.sleep(3000);

        //Function for Airline Filter Selection (Single Airline)
       
     //Function for Airline Filter Selection (Single Airline)
       
       String AirlineName=trs.clickAirlineCheckboxRoundtripDomestic1(flightairlines);
      //tripgainresultspage.ValidateAirlineSelectionRoundtrip(Log, screenShots, "Lufthansa,United Airlines");

trs.validateAirlinesDomesticroundtrip(2, Log, screenShots, AirlineName);

        //Function for Refundable Fare Filter Selection
         trs.clickRefundableFareRoundTrip();
         Thread.sleep(3000);
         trs.validateRefundableFare(2, Log, screenShots);
         Thread.sleep(3000);
         trs.validateRefundableFare(2, Log, screenShots);
         
         
  		//tripgainhomepage.logOutFromApplication(Log, screenShots);
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

	


