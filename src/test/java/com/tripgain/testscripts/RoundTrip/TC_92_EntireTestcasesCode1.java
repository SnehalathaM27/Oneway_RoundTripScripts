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

public class TC_92_EntireTestcasesCode1 extends BaseClass{
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
        System.out.println(origin);
        
        String destination = excelData.get("Destination");
        System.out.println(destination);

        String travelClass = excelData.get("Class");
        System.out.println(travelClass);
        
        int Adults = Integer.parseInt(excelData.get("Adults"));
        System.out.println(Adults);
        
        
        String clickOnWardStops = excelData.get("clickOnWardStops");
        System.out.println(clickOnWardStops);

        
        String roundTripClickReturnStops = excelData.get("roundTripClickReturnStops");
        System.out.println(roundTripClickReturnStops);
        

        int VerifyFlightsDetailsOnResultScreenForLocalIndex = Integer.parseInt(excelData.get("VerifyFlightsDetailsOnResultScreenForLocalIndex"));
        System.out.println(VerifyFlightsDetailsOnResultScreenForLocalIndex);
        
        String selectAirLines = excelData.get("selectAirLines");
        System.out.println(selectAirLines);
        
        int selectFromAndToFlightsBasedOnIndex = Integer.parseInt(excelData.get("selectFromAndToFlightsBasedOnIndex"));
        System.out.println(selectFromAndToFlightsBasedOnIndex);
        

        String onwardMealSelect = excelData.get("onwardMealSelect");
        String[] onwardMealSelectSplit = onwardMealSelect.split(",");
        System.out.println(onwardMealSelect);

        
        String returnMealSelect = excelData.get("returnMealSelect");
        String[] returnMealSelectSplit = returnMealSelect.split(",");
        System.out.println(returnMealSelect);

        String selectCurrencyDropDownValues = excelData.get("selectCurrencyDropDownValues");
        System.out.println(selectCurrencyDropDownValues);

        String validateCurrencyRoundTrip = excelData.get("validateCurrencyRoundTrip");
        System.out.println(validateCurrencyRoundTrip);

        String clickAirlineCheckboxesRoundtripDomestic = excelData.get("clickAirlineCheckboxesRoundtripDomestic");
        System.out.println(clickAirlineCheckboxesRoundtripDomestic);
        
        int FlightCardIndex = Integer.parseInt(excelData.get("FlightCardIndex"));
        System.out.println(FlightCardIndex);

        String validateFlightsStopsOnResultScreen = excelData.get("validateFlightsStopsOnResultScreen");
        System.out.println(validateFlightsStopsOnResultScreen);
        

        String validateFlightsreturnStopsOnResultScreen = excelData.get("validateFlightsreturnStopsOnResultScreen");
        System.out.println(validateFlightsreturnStopsOnResultScreen);
        
        String flightNames=excelData.get("flightNames");
        System.out.println(flightNames);
        
        int selectToFlightsBasedOnIndex = Integer.parseInt(excelData.get("selectToFlightsBasedOnIndex"));
        System.out.println(selectToFlightsBasedOnIndex);
        
        
        String OnwardBaggageSelect = excelData.get("onwardBaggageSelect");
        System.out.println(OnwardBaggageSelect);
        String[] OnwardBaggageSelectSplit = OnwardBaggageSelect.split(",");
        
        String ReturnBaggageSelect = excelData.get("returnBaggageSelect");
        System.out.println(ReturnBaggageSelect);
        String[] ReturnBaggageSelectSplit = ReturnBaggageSelect.split(",");


      
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
        tripgainhomepage.searchFlightsOnHomePage(Log, screenShots,origin, destination,  fromDate, fromMonthYear, returnDate, returnMonthYear, travelClass, Adults);
        Thread.sleep(5000);

        trs.validateFlightsResultsForRoundTrip(Log, screenShots);
Thread.sleep(15000); 
		
		// -------------------------------------------------------------------------------------------------------
/*
		                         //Slider
		double values[] =trs.defaultPriceRangeOfSlider(driver,Log,screenShots);
		double minValue=values[0];
		double minimumValue=minValue+3000;
		trs.adjustMinimumSliderToValue(driver,minimumValue);
		
		trs.verifyPriceRangeValuesOnResultScreen(Log,screenShots);
		
		// -------------------------------------------------------------------------------------------------------

		Thread.sleep(3000);

                                    //Currency
		trs.defaultCurrencyValue(Log,screenShots);
		
//		 //Function click On Currency Drop Down
  		tripgainresultspage.clickOnCurrencyDropDown();
//	
//		//Function to select Currency Drop Down Values
  		tripgainresultspage.selectCurrencyDropDownValues(driver,selectCurrencyDropDownValues);
//		
  		tripgainresultspage.validateCurrencyRoundTrip(validateCurrencyRoundTrip,Log,screenShots);
  	// -------------------------------------------------------------------------------------------------------
  		Thread.sleep(3000);


	                                 //AirLines
		 String airlinename=trs.clickAirlineCheckboxRoundtripDomestic1(clickAirlineCheckboxesRoundtripDomestic);
	        
	     trs.validateAirlinesDomesticroundtrip(FlightCardIndex, Log, screenShots, airlinename);
	     Thread.sleep(2000);
	 //    tripgain_roundtrip_resultpage.selectAirLines(clickAirlineCheckboxesRoundtripDomestic);
        
// -------------------------------------------------------------------------------------------------------
	     
	     Thread.sleep(3000);

		trs.selectAirLines(selectAirLines);
		
// -------------------------------------------------------------------------------------------------------
		
		Thread.sleep(3000);

		
		//Method to click OnWardStops
		
		trs.roundTripClickOnWardStops(clickOnWardStops);  
		trs.validateFlightsStopsOnResultScreen(validateFlightsStopsOnResultScreen,Log, screenShots);
	
// -------------------------------------------------------------------------------------------------------
		
		Thread.sleep(3000);

		
	                       //Method to click round Trip Click Return Stops
		
		  trs.roundTripClickReturnStops(roundTripClickReturnStops);
	        trs.validateFlightsreturnStopsOnResultScreen(validateFlightsreturnStopsOnResultScreen,Log, screenShots);
		
//	---------------------------------------------------------------------------------------------------------
	        
	*/
Thread.sleep(3000);

	        
		 //Function to validate Check In Baggage Functionality on Result Screen

	        tripgainresultspage.clickOnCheckInBaggage();

	        System.out.println(flightNames);
			 String[] names = flightNames.split(",");
	         String airlineName=trs.clickOnParticularAirline(names);
	         Thread.sleep(3000);
	         trs.selectFromAndToFlightsBasedOnIndex(selectFromAndToFlightsBasedOnIndex);
	         Thread.sleep(2000);
	         trs.validateAllBaggageDetailsForAllAirline(airlineName,Log,screenShots);
	         Thread.sleep(2000);
	         trs.closePopup1();
	         Thread.sleep(2000);
	         trs.selectToFlightsBasedOnIndex(selectToFlightsBasedOnIndex);
	         trs.validateAllBaggageDetailsForAllAirline(airlineName,Log,screenShots);
	         Thread.sleep(2000);
	         trs.closePopup1();
      
	         Thread.sleep(3000);
 //------------------------------------------------------------------------------------------------------------
        trs.clickOnContinue();
        
        Thread.sleep(3000);
 
 //--------------------------------------------------------------------------------------------------------
        Thread.sleep(3000);

        
                                      //pick seat
         
        tripgainresultspage.selectSeatFormPickSeat(Log, screenShots);
    	
 //-------------------------------------------------------------------------------------------------------------
        Thread.sleep(3000);

                                         //Meals
    	
        double selectMealsOnwardTotalPrice1 = trs.selectMealsOnward();
        double selectMealsOnwardTotalPrice2 = trs.selectMealsReturn();
        double totalMeal = trs.addTotalMealsPrice(Log, screenShots,selectMealsOnwardTotalPrice1,selectMealsOnwardTotalPrice2);

        trs.validateMealsPrice(Log, screenShots,totalMeal);
 
 //-------------------------------------------------------------------------------------------------------
        Thread.sleep(3000);

        
                          // Project details 
        trs.selectDepartmentRoundtrip();
        trs.selectProjectRoundtrip();
        trs.selectCostcenterRoundtrip();

        //Functions to Send Approval on Booking Page
        trs.clickOnSendApprovalButton();
        trs.validateSendApprovalToastMessage(Log, screenShots);
        
    //----------------------------------------------------------------------------------------------------------

        
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