package TestScripts_Oneway_RoundTrip;
import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
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
import com.tripgain.collectionofpages.Tripgain_Login;
import com.tripgain.collectionofpages.Tripgain_RoundTripResultsScreen;
import com.tripgain.collectionofpages.Tripgain_homepage;
import com.tripgain.collectionofpages.Tripgain_resultspage;
import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;
import com.tripgain.common.getDataFromExcel;
import com.tripgain.testscripts.BaseClass;
import com.tripgain.common.DataProviderUtils;
import com.tripgain.common.ExtantManager;
import com.tripgain.common.GenerateDates;
import com.tripgain.common.Getdata;

@Listeners(com.tripgain.common.TestListener.class)
public class TC_01 extends BaseClass{
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
        String[] dates=GenerateDates.GenerateDatesToSelectFlights();
        String fromDate=dates[9];
        String fromMonthYear=dates[10];
        
        String origin = excelData.get("Origin");
        String destination = excelData.get("Destination");
        String fromDates = excelData.get("fromDate");
        String fromMonthYears = excelData.get("fromMonthYear");
        String travelClass = excelData.get("Class");
        int adults = Integer.parseInt(excelData.get("Adults"));
        int selectflightbasedindex = Integer.parseInt(excelData.get("SelectFlightBasedIndex"));
        int depatureindex = Integer.parseInt(excelData.get("DepatureIndex"));
        int arrivalindex = Integer.parseInt(excelData.get("ArrivalIndex"));
        int priceindex = Integer.parseInt(excelData.get("PriceIndex"));
        String stops0 = excelData.get("Stops0");
        String numberofstops0 = excelData.get("NumberOfStops0");
        String expectedvalue = excelData.get("ExpectedValue");
        String flightairlines = excelData.get("Airlines");
        int selectFlightBasedIndexCheckInBaggage = Integer.parseInt(excelData.get("SelectFlightBasedIndexCheckInBaggage"));
        String airlines1 = excelData.get("Airlines1");

        
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

        tripgainhomepage.searchFlightsOnHomePage(origin, destination, fromDates, fromMonthYears, travelClass , adults, Log, screenShots);
       
        Thread.sleep(3000); 
        wait = new WebDriverWait(driver,Duration.ofSeconds(20));

        
        tripgainresultspage.validateFlightsResults(Log, screenShots);
        tripgainresultspage.validateFlightDetailsOnResultScreen(Log, screenShots);  
        tripgainresultspage.validateAllDatesInResults(Log, screenShots);

       
        Thread.sleep(9000); 

        //Function to validate stops
        //tripgainresultspage.clickOnStops(stops0);
        trs.roundTripClickOnWardStops(stops0);  
        wait = new WebDriverWait(driver,Duration.ofSeconds(20));

 tripgainresultspage.validateFlightsStopsOnResultScreen(numberofstops0, Log, screenShots);

 Thread.sleep(3000);
 
 ///Function to validate Refundable 
 tripgainresultspage.clickOnRefundableFare();
 tripgainresultspage.validateRefundableFareFlights(Log, screenShots);
 wait = new WebDriverWait(driver,Duration.ofSeconds(20));

    //Function to Validate policy
    tripgainresultspage.clickOnPolicy();
    tripgainresultspage.validatePolicy(Log, screenShots, expectedvalue);
    wait = new WebDriverWait(driver,Duration.ofSeconds(20));
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollTo(0, 0);");

    //Function to validate Airlines
    Thread.sleep(3000); 
    tripgainresultspage.clickAirlineCheckboxes(airlines1);
       Thread.sleep(3000);
          tripgainresultspage.validateAirLinesList(Log, screenShots, airlines1);
          
          Thread.sleep(3000);
        //Function to Validate SME Check box Functionality
         tripgainresultspage.validateSME(Log, screenShots);
         js.executeScript("window.scrollTo(0, 0);");

       //Function to validate Check In Baggage Functionality on Result Screen
           tripgainresultspage.clickOnCheckInBaggage();
           tripgainresultspage.selectFlightBasedOnIndex(selectflightbasedindex);
           tripgainresultspage.validateCheckInBaggageFlightsOnResultScreen(Log, screenShots);
           
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