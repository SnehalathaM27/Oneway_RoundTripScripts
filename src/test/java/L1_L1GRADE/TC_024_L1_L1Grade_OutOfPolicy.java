package L1_L1GRADE;

import java.awt.AWTException;
import java.io.IOException;
import java.text.ParseException;
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
import com.tripgain.collectionofpages.Tripgain_Login;
import com.tripgain.collectionofpages.Tripgain_RoundTripResultsScreen;
import com.tripgain.collectionofpages.Tripgain_homepage;
import com.tripgain.collectionofpages.Tripgain_resultspage;
import com.tripgain.common.DataProviderUtils;
import com.tripgain.common.ExtantManager;
import com.tripgain.common.GenerateDates;
import com.tripgain.common.Getdata;
import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;
import com.tripgain.testscripts.BaseClass;

public class TC_024_L1_L1Grade_OutOfPolicy extends BaseClass{
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
    public void myTest(Map<String, String> excelData) throws InterruptedException, IOException, ParseException {
        System.out.println("Running test with: " + excelData);
	    String[] data = Getdata.getexceldata();
        String userName = data[0]; 
        String password = data[1];
        number++;

      String origin = excelData.get("Origin");
        String destination = excelData.get("Destination");
        String travelClass = excelData.get("Class");
        int adults = Integer.parseInt(excelData.get("Adults"));
        String expectedValue = excelData.get("ExpectedValue");
        String fromDateSelection = excelData.get("FromDate");
        String fromMonthYearSelection = excelData.get("FromMonthYear");
        String returnDateselection = excelData.get("ReturnDate");
        String returnMonthYearSelection = excelData.get("ReturnMonthYear");




      
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
        
        
        tripgainhomepage.Menu("Profile");
        tripgainhomepage.validatePolicyGrade("L1 - L1 Test Grade",Log,screenShots);
        tripgainhomepage.travelMenu("Flights",Log,screenShots);

         tripgainhomepage.Clickroundtrip();
         /*
          public void searchFlightsOnHomePage(Log Log, ScreenShots screenShots, String fromLocations, String toLocations,
            String day, String MonthandYear, 
            String returnday, String returnMonthandYear,
            String classes, int Adults) 
        returnDateselection, returnMonthYearSelection, fromDate, fromMonthYear, returnDate, returnMonthYear
          */
       
         tripgainhomepage.searchFlightsOnHomePage(Log, screenShots, origin,destination,fromDate,fromMonthYear,returnDate,returnMonthYear,travelClass, adults);

         Thread.sleep(5000);
        trs.verifyFlightsDetailsOnResultScreenForLocal(1, Log, screenShots);
 Thread.sleep(12000); 
 trs.validatePolicy(Log, screenShots, "Out of Policy");


 String[] userInput = trs.userEnterData(); 
 String fromLocation= userInput[0];
 System.out.println(fromLocation);
 String toLocation=userInput[1];
 System.out.println(toLocation);
 String journeyDateValue=userInput[2];
 System.out.println(journeyDateValue);
 String returnDateValue=userInput[3];
 System.out.println(returnDateValue);
 
 Thread.sleep(3000);
 
 Object[] Fromvalues=trs.selectFlightInRoundTripFromLocation(2);
 //stop,priceFrom,departTime,arrivalTime
 String stop=(String) Fromvalues[0];
 double fromPrice=(double) Fromvalues[1];
 String departTime=(String) Fromvalues[2];
 String arrivalTime=(String) Fromvalues[3];
 String  fromduration=(String) Fromvalues[4];
 
 
 String [] onwardFlightDetails  =trs.validateFlightDetailsFromLocation(fromLocation,toLocation,journeyDateValue,stop,departTime,arrivalTime, Log, screenShots);
 trs.viewFlightDetailsClosePopup();
 Thread.sleep(2000);
 Object[] tovalues=trs.selectFlightInRoundTripToLocation(1);
 String toStop=(String) tovalues[0];
 double toPrice=(double) tovalues[1];
 String todepartTime=(String) tovalues[2];
 String toarrivalTime=(String) tovalues[3];
 String toDuration=(String) tovalues[4];
 
 
 
 String [] returnFlightDetails =  trs.validateFlightDetailsReturnLocation(fromLocation,toLocation,returnDateValue,toStop,todepartTime,toarrivalTime, Log, screenShots);
 trs.viewFlightDetailsClosePopup();
 System.out.println("2 start return");
 trs.roundTripFooterValidation(fromLocation,toLocation,departTime,arrivalTime,fromduration,stop,fromPrice,
         toLocation,fromLocation,todepartTime,toarrivalTime,toDuration,toStop,toPrice,Log , screenShots);

 Thread.sleep(3000);
 trs.bookingPagePolicy(Log, screenShots, "Out of Policy");
 
     

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
	        //  driver.quit();
	          extantManager.flushReport();
	       }
	    }
		
		
	}





