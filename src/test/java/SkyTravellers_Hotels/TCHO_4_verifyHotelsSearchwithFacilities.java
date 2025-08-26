package SkyTravellers_Hotels;

import java.awt.AWTException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

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
import com.tripgain.collectionofpages.SiteChecker;
import com.tripgain.collectionofpages.SkyTravelers_Hotels_BookingPage;
import com.tripgain.collectionofpages.SkyTravelers_Hotels_DescriptionPage;
import com.tripgain.collectionofpages.SkyTravelers_Hotels_Login;
import com.tripgain.collectionofpages.SkyTravelers_Hotels_SearchPage;
import com.tripgain.collectionofpages.SkyTravelers_Hotels_confirmBookingPage;
import com.tripgain.collectionofpages.TripPlanner;
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

public class TCHO_4_verifyHotelsSearchwithFacilities extends BaseClass{
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
    public void myTest(Map<String, String> excelData) throws InterruptedException, IOException, ParseException, TimeoutException {
        System.out.println("Running test with: " + excelData);
	    String[] data = Getdata.getexceldata();
        String userName = data[0]; 
        String password = data[1];
        System.out.println(userName);
        System.out.println(password);
        number++;

       
        String origin = excelData.get("Origin");
        System.out.println(origin);
        String roomCount = excelData.get("RoomCount");
        String adultCount = excelData.get("AdultCount");
        String childCount = excelData.get("ChildCount");
        String ChildAge = excelData.get("childAge");
        String email = excelData.get("Email");
        String phno = excelData.get("Phno");
        String payment = excelData.get("Payment");

        
    
			String[] dates=GenerateDates.GenerateDatesToSelectFlights();
	        String fromDate=dates[0];
	        String fromMonthYear=dates[2];
	        String returnDate=dates[1]; 
	        String returnMonthYear=dates[3];
	        
        // Login to TripGain Application
        Tripgain_Login tripgainLogin= new Tripgain_Login(driver);
     // Login to TripGain Application
        SkyTravelers_Hotels_Login SkyTravelersHotelsLogin= new SkyTravelers_Hotels_Login(driver);
        SkyTravelersHotelsLogin.enterUserName(userName);
        SkyTravelersHotelsLogin.enterPasswordName(password);
        SkyTravelersHotelsLogin.clickButton(); 
		Log.ReportEvent("PASS", "Enter UserName and Password is Successful");
		Thread.sleep(2000);
		SkyTravelersHotelsLogin.verifyHomePageIsDisplayed(Log, screenShots);

        
		SkyTravelers_Hotels_SearchPage SkyTravelersHotelsSearchPage = new SkyTravelers_Hotels_SearchPage(driver);
		SkyTravelers_Hotels_DescriptionPage SkyTravelersHotelsDescriptionPage=new SkyTravelers_Hotels_DescriptionPage(driver);
		SkyTravelers_Hotels_BookingPage SkyTravelersHotelsBookingPage=new SkyTravelers_Hotels_BookingPage(driver);
		SkyTravelers_Hotels_confirmBookingPage SkyTravelersHotelsconfirmBookingPage=new SkyTravelers_Hotels_confirmBookingPage(driver);
		
		SkyTravelersHotelsSearchPage.clickOnHotels();
		Thread.sleep(3000);
		SkyTravelersHotelsSearchPage.enterDestinationForHotels(origin);
		SkyTravelersHotelsSearchPage.selectDate(fromDate, fromMonthYear);
		SkyTravelersHotelsSearchPage.selectReturnDate(returnDate, returnMonthYear);
		SkyTravelersHotelsSearchPage.fillRoomDetails(roomCount, adultCount, childCount, ChildAge);
		SkyTravelersHotelsSearchPage.clickSearchHotelsAndWaitForResults(Log);;
		Thread.sleep(1000);
		
		String[] checkindateResultPage = SkyTravelersHotelsSearchPage.getCheckInDateTextFromResultPage();
		String[] checkoutdateResultPage =SkyTravelersHotelsSearchPage.getCheckOutDateTextFromResultPage();
		String[] ResultPageRoomsAndGuests = SkyTravelersHotelsSearchPage.getRoomAndGuestTextFromResultPage();
	
	String hotelNameFromDropdown = SkyTravelersHotelsSearchPage.clickAndSearchHotelName(Log, screenShots);
    Thread.sleep(2000);

	//	String facilities = SkyTravelersHotelsSearchPage.clickFacilities(Log, screenShots);
		Thread.sleep(3000);
		List<String> hotelsNameText = SkyTravelersHotelsSearchPage.getHotelsNameTextFromresultScreen(Log, screenShots);
		Thread.sleep(1000);
		String[] hotelcardLoc = SkyTravelersHotelsSearchPage.getHotelLocationFromCardFromResultsPage();
		
		String[] resultPageAmount = SkyTravelersHotelsSearchPage.getHotelAmountFromCardFromResultsPage();
		
		String facilitiesTitleText = SkyTravelersHotelsSearchPage.getFacilitiesTitleTextFromResultpage(Log);

		SkyTravelersHotelsSearchPage.validateHotelNameSelectionOnResultpage(hotelNameFromDropdown, hotelsNameText, Log, screenShots);

		
		Thread.sleep(2000);
		
		
		SkyTravelersHotelsSearchPage.clickOnSelectRoomButtonOnResultpage();
		Thread.sleep(3000);
		
		 String descPageHotelName = SkyTravelersHotelsDescriptionPage.getHotelNameFromDescPage();

	String[] descPageDay = SkyTravelersHotelsDescriptionPage.getCheckInDateFromDescPage();           // ["19"]
	    String[] descPageMonth = SkyTravelersHotelsDescriptionPage.getCheckInMonthFromDescPage();
	    
	    String[] descPagecheckoutDay=SkyTravelersHotelsDescriptionPage.getCheckoutDateFromDescPage();
	    String[] descPagecheckoutMon=SkyTravelersHotelsDescriptionPage.getCheckoutMonthFromDescPage();

	    String[] descGuestCount = SkyTravelersHotelsDescriptionPage.getGuestsCountFromDescPage();
	    String[] descRoomCount = SkyTravelersHotelsDescriptionPage.getRoomCountFromDescPage();
	    
	    String[] descAmount = SkyTravelersHotelsDescriptionPage.getAmountFromDescPage();
	    
	    
   String[] descStarRating = SkyTravelersHotelsDescriptionPage.getstarRatingFromDescPage();
   
   
	    
   SkyTravelersHotelsDescriptionPage.validateHotelNameFromResultPageToDescPage(descPageHotelName, hotelsNameText, Log, screenShots);

   SkyTravelersHotelsDescriptionPage.validateCheckInDate(descPageDay, descPageMonth, checkindateResultPage, Log, screenShots);
		Thread.sleep(1000);

		SkyTravelersHotelsDescriptionPage.validateCheckOutDate(descPagecheckoutDay, descPagecheckoutMon, checkoutdateResultPage, Log, screenShots);
		
		SkyTravelersHotelsDescriptionPage.validateRoomAndGuestCountsFromResultToDescPage(ResultPageRoomsAndGuests, descRoomCount, descGuestCount, Log, screenShots);
		
		SkyTravelersHotelsDescriptionPage.validateHotelAmountFromResultToDescPage(descAmount, resultPageAmount, Log, screenShots);

		
		
		
		SkyTravelersHotelsDescriptionPage.clcikAmenitiesOnDescPage();
		String descPageAmenities = SkyTravelersHotelsDescriptionPage.getAmenitiesFromDescPage();
		SkyTravelersHotelsDescriptionPage.validateFacilitiesTitleWithDescPageAmenities(facilitiesTitleText, descPageAmenities, Log, screenShots);
	//	List<String> cancellationDetails = SkyTravelersHotelsDescriptionPage.getFormattedCancellationPoliciesByIndex(1);
		String[] DescRoomDetails = SkyTravelersHotelsDescriptionPage.clickOnBookButtonForRoomsOnDescPage(1); //0 clicks on 1 room --//1 clicks on 2nd room....
		

		
		
		
		String[] bookingHotelname = SkyTravelersHotelsBookingPage.getHotelNameFromBookingPage();
	String[] bookingcheckindate = SkyTravelersHotelsBookingPage.getHotelCheckInDateFromBookingPage();
	String[] bookingcheckinmonth = SkyTravelersHotelsBookingPage.gethotelcheckInMonthFromBookingPage();
	String[] bookingcheckoutdate = SkyTravelersHotelsBookingPage.getHotelCheckOutDateFromBookingPage();
	String[] bookingcheckoutmonth = SkyTravelersHotelsBookingPage.gethotelcheckOutMonthFromBookingPage();
	String[] bookingGuestsCount = SkyTravelersHotelsBookingPage.getPeopleCountromBookingPage();
	String[] bookingRoomText = SkyTravelersHotelsBookingPage.getRoomTextFromBookingPage();
	String[] bookingNightsText = SkyTravelersHotelsBookingPage.getNightsFromBookingPage();
	String[] bookingmealText = SkyTravelersHotelsBookingPage.getMealPlanFromBookingPage();
	String[] bookingcancellationText = SkyTravelersHotelsBookingPage.getCancellationPolicyromBookingPage();
String[] bookingLocation = SkyTravelersHotelsBookingPage.getHotelLocationFromBookingPage();
	String[] bookingHotelChargesAmount = SkyTravelersHotelsBookingPage.getHotelChargesBookingPage();
	String[] BookingbottomAmount = SkyTravelersHotelsBookingPage.getFinalBottomAmountFromBookingPage();
	String[] bookingTotalAmount = SkyTravelersHotelsBookingPage.getTotalAmountFromBookingPage();
	
		
	SkyTravelersHotelsBookingPage.validateHotelNameFromDescAndBookingPage(descPageHotelName, bookingHotelname, Log, screenShots);
	SkyTravelersHotelsBookingPage.validateCheckInDateFromDescAndBookingPage(descPageDay, bookingcheckindate, Log, screenShots);
		SkyTravelersHotelsBookingPage.validateCheckInMonthFromDescAndBookingPage(descPageMonth, bookingcheckinmonth, Log, screenShots);
		SkyTravelersHotelsBookingPage.validateCheckOutDateFromDescAndBookingPage(descPagecheckoutDay, bookingcheckoutdate, Log, screenShots);
		SkyTravelersHotelsBookingPage.validateCheckOutMonthFromDescAndBookingPage(descPagecheckoutMon, bookingcheckoutmonth, Log, screenShots);
		SkyTravelersHotelsBookingPage.validateGuestCountFromDescAndBookingPage(descGuestCount, bookingGuestsCount, Log, screenShots);
		SkyTravelersHotelsBookingPage.validateRoomTypeFromDescAndBookingPage(DescRoomDetails, bookingRoomText, Log, screenShots);
		SkyTravelersHotelsBookingPage.validateNightsFromDescAndBookingPage(DescRoomDetails, bookingNightsText, Log, screenShots);
		SkyTravelersHotelsBookingPage.validateMealPlanFromDescAndBookingPage(DescRoomDetails, bookingmealText, Log, screenShots);
	//	SkyTravelersHotelsSearchPage.validateCancellationPolicyFromDescAndBookingPage(DescRoomDetails, bookingcancellationText, Log, screenShots);
		SkyTravelersHotelsBookingPage.validateHotelLocationFromResultsAndBookingPage(hotelcardLoc, bookingLocation, Log, screenShots);
		SkyTravelersHotelsBookingPage.validateHotelPriceFromDescAndBookingPage(DescRoomDetails, bookingHotelChargesAmount, Log, screenShots);
		SkyTravelersHotelsBookingPage.validateTotalAndFinalBottomAmountFromBookingPage(bookingTotalAmount, BookingbottomAmount, Log, screenShots);
	//	SkyTravelersHotelsBookingPage.validateCancellationPolicies(cancellationDetails, Log, screenShots);
		
		SkyTravelersHotelsBookingPage.enterEmail(email);
		SkyTravelersHotelsBookingPage.enterPhoneNumber(phno);
		SkyTravelersHotelsBookingPage.enterTravellerDetailsOnBookingPageAndReturnTheText();
		SkyTravelersHotelsBookingPage.clickPaymentOptions(payment);
		SkyTravelersHotelsBookingPage.clickAcceptanceAndConditionsCheckBox();
		SkyTravelersHotelsBookingPage.confirmBookingButton();
		
		
		
		
		String[] confirmHotelName = SkyTravelersHotelsconfirmBookingPage.getHotelNameTextFromConfirmPage();
		//String[] confirmHotelLoc = SkyTravelersHotelsconfirmBookingPage.getHotelLocationTextFromConfirmPage();
		String[] confirmHotelCheckInDate = SkyTravelersHotelsconfirmBookingPage.getHotelCheckInDateFromConfirmPage();
		String[] confirmHotelcheckInMonth = SkyTravelersHotelsconfirmBookingPage.gethotelcheckInMonthFromConfirmPage();
		String[] confirmHotelCheckOutdate = SkyTravelersHotelsconfirmBookingPage.getHotelCheckOutDateFromConfirmPage();
		String[] confirmHotelCheckOutMon = SkyTravelersHotelsconfirmBookingPage.gethotelcheckOutMonthFromConfirmPage();
		String[] confirmHotelMealPlan = SkyTravelersHotelsconfirmBookingPage.getMealPlanFromConfirmPage();
		String[] confirmHotelPeopleCount = SkyTravelersHotelsconfirmBookingPage.getPeopleCountFromConfirmPage();
		String[] confirmHotelRoomText = SkyTravelersHotelsconfirmBookingPage.getRoomTextFromConfirmPage();
		String[] confirmHotelAmount = SkyTravelersHotelsconfirmBookingPage.getAmountFromConfirmPage();

		
		SkyTravelersHotelsconfirmBookingPage.validateHotelNameFromBookingAndConfirmPage(bookingHotelname, confirmHotelName, Log, screenShots);
	//	SkyTravelersHotelsconfirmBookingPage.validateHotelLocationFromBookingAndConfirmPage(bookingLocation, confirmHotelLoc, Log, screenShots);
		SkyTravelersHotelsconfirmBookingPage.validateHotelCheckInDayFromBookingAndConfirmPage(bookingcheckindate, confirmHotelCheckInDate, Log, screenShots);
		SkyTravelersHotelsconfirmBookingPage.validateHotelCheckInMonthFromBookingAndConfirmPage(bookingcheckinmonth, confirmHotelcheckInMonth, Log, screenShots);
		SkyTravelersHotelsconfirmBookingPage.validateHotelCheckOutDayFromBookingAndConfirmPage(bookingcheckoutdate, confirmHotelCheckOutdate, Log, screenShots);
		SkyTravelersHotelsconfirmBookingPage.validateHotelCheckOutMonthFromBookingAndConfirmPage(bookingcheckoutmonth, confirmHotelCheckOutMon, Log, screenShots);
		SkyTravelersHotelsconfirmBookingPage.validateRoomTypeFromBookingAndConfirmPage(bookingRoomText, confirmHotelRoomText, Log, screenShots);
		SkyTravelersHotelsconfirmBookingPage.validateMealPlanFromBookingAndConfirmPage(bookingmealText, confirmHotelMealPlan, Log, screenShots);
		SkyTravelersHotelsconfirmBookingPage.validatePeopleCountFromBookingAndConfirmPage(bookingGuestsCount, confirmHotelPeopleCount, Log, screenShots);
		SkyTravelersHotelsconfirmBookingPage.validateTotalAmountFromBookingAndConfirmPage(BookingbottomAmount, confirmHotelAmount, Log, screenShots);
		
		SkyTravelersHotelsconfirmBookingPage.clcikBookNowButtonOnConfirmPage();
		
		SkyTravelersHotelsconfirmBookingPage.validateBillingInformationDisplayed(Log);
		
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