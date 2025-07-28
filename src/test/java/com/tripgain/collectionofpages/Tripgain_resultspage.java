package com.tripgain.collectionofpages;
import java.awt.AWTException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;

public class Tripgain_resultspage {

	WebDriver driver;

	public Tripgain_resultspage(WebDriver driver) {

		PageFactory.initElements(driver, this);
		this.driver=driver;
	}
	
	
	
	
	@FindBy(xpath = "//input[@id='react-select-5-input']")
	WebElement fromlocationresultspage;
	
	public void clickOnfromSuggestion(String locationCode) throws AWTException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'select__option') and contains(text(),'" + locationCode + "')]")));
				fromlocationresultspage.click();
	}
	
	
	//Method to set "from Location" 
	public void ResultspagefromLocation(String text) {
		fromlocationresultspage.sendKeys(text);
	}
	
	
	
	@FindBy(xpath = "//input[@id='react-select-6-input']")
	WebElement tolocationresultspage;
	
	//Method to set "To Location" 
	public void ResultspageToLocation(String text) {
		tolocationresultspage.sendKeys(text);
	}
	public void clickOnToSuggestion() throws AWTException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='tg-select__input' and contains(@id, 'react-select-5-input')]")));
				tolocationresultspage.click();
	}
	
    //-------------------------------------------------------------------------------------------------------------
	public void clickonStops(String... stops) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		for (String stop : stops) {
			try {
				WebElement stopButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@class='filter-stops']//button[text()='" + stop + "']")));

				// Scroll into view
				js.executeScript("arguments[0].scrollIntoView({block: 'center'});", stopButton);

				try {
					// Wait for element to be clickable
					wait.until(ExpectedConditions.elementToBeClickable(stopButton)).click();
					System.out.println("Clicked on stop filter: " + stop);
				} catch (ElementClickInterceptedException e) {
					System.out.println("Standard click failed, attempting JS click for stop: " + stop);
					js.executeScript("arguments[0].click();", stopButton);
				}

			} catch (Exception e) {
				System.err.println("Failed to click on stop filter: " + stop);
				e.printStackTrace();
				// Optionally: capture screenshot or log for report
			}
		}
	}

	 //Method to Validate flights on Result Page
	
	public void validateFlightsResults(Log Log,ScreenShots ScreenShots)
	{
	    try {
	       Thread.sleep(15000);
	       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	       wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='oneway-header']/following-sibling::div")));
	       WebElement flights=driver.findElement(By.xpath("//div[@class='oneway-header']/following-sibling::div"));   

	       if(flights.isDisplayed())
	       {
	          Log.ReportEvent("PASS", "Flights are displayed based on User Search is Successful");
	          ScreenShots.takeScreenShot1();
	       }  
	       else {
	          Log.ReportEvent("FAIL", "Flights are Not displayed based on User Search Please Change Filter");
	          ScreenShots.takeScreenShot1();
	          Assert.fail();

	       }
	    }
	    catch(Exception e){
	       Log.ReportEvent("FAIL", "Flights are Not displayed based on User Search Please Change Filter");
	       ScreenShots.takeScreenShot1();
	       Assert.fail();
	       e.printStackTrace();
	    }     
	}

	public void valiadatemodifysearch() {
		String resulttext = driver.findElement(By.xpath("//p[@data-tgfullsector]")).getText();
		String from = driver.findElement(By.xpath("//input[contains(@id,'react-select-8-input')]")).getText();
		String to = driver.findElement(By.xpath("//input[contains(@id,'react-select-9-input')]")).getText();
		// combine it and compare both
	    String locations = from + "-" + to;
	if(resulttext.equals(locations)) {
		System.out.println("Equals");
	
} else {
	System.out.println("not Equals");
}
	}
 
    //Method to Select flight based on Index
    public void clickOnSelectFlightBasedOnIndex(int index) throws InterruptedException
    {
        Thread.sleep(2000);
        driver.findElement(By.xpath("(//button[text()='Select'])["+index+"]")).click();
        Thread.sleep(2000);

    }
    
    
  //Method to Validate Flights Image and Name is Displayed
    public void validateFlightImageAndNameIsDisplayed()
    {
        driver.findElement(By.xpath("(//div[@class='owf-carrier-info'])[1]")).isDisplayed();
    }
    //Method to Validate Flights Departure Time is Displayed
    public void validateFlightDepartureTimeIsDisplayed()
    {
        driver.findElement(By.xpath("(//div[@class='owf-deptime'])[1]")).isDisplayed();
    }
    //Method to Validate Flights Duration Time is Displayed
    public void validateFlightDurationTimeIsDisplayed()
    {
        driver.findElement(By.xpath("(//div[@class='owf-traveltime'])[1]")).isDisplayed();
    }
    //Method to Validate Flights Arrival Time is Displayed
    public void validateFlightArrivalTimeIsDisplayed()
    {
        driver.findElement(By.xpath("(//div[@class='owf-arrtime'])[1]")).isDisplayed();
    }
    //Method to Validate Flights Price is Displayed
    public void validateFlightPriceIsDisplayed()
    {
        driver.findElement(By.xpath("(//div[@class='owf-price'])[1]")).isDisplayed();
    }
    //Method to Validate Flights Footer Details is Displayed
    public void validateFlightFooterDetailsIsDisplayed()
    {
        driver.findElement(By.xpath("(//div[@class='owf-footer'])[1]")).isDisplayed();
    }

    //Method to Validate Flight Details on Result Screen
    public void validateFlightDetailsOnResultScreen(Log Log, ScreenShots ScreenShots)
    {
        try
        {
           validateFlightImageAndNameIsDisplayed();
           validateFlightDepartureTimeIsDisplayed();
           validateFlightDurationTimeIsDisplayed();
           validateFlightArrivalTimeIsDisplayed();
           validateFlightPriceIsDisplayed();
           validateFlightFooterDetailsIsDisplayed();
           Log.ReportEvent("PASS", "Flights Basic Details are Displayed on Result Screen");
           ScreenShots.takeScreenShot1();
        }catch(Exception e){
           Log.ReportEvent("FAIL", "Flights Basic Details are Not Displayed on Result Screen"+e.getMessage());
           ScreenShots.takeScreenShot1();
           Assert.fail();

        }
    }
    
    
  //Validate All Dates In Results OneWay
    public void validateAllDatesInResults(Log Log, ScreenShots ScreenShots) {
        try {
            // Retrieve the user-selected date from the input field
            WebElement dateInput = driver.findElement(By.xpath("//*[contains(@class,'tg-fsonwarddate')]"));
            String userSelectedDate = dateInput.getAttribute("value");
            System.out.println("User selected date: " + userSelectedDate);

            // Remove ordinal suffixes from the day (e.g., "7th" becomes "7")
            String cleanedDate = userSelectedDate.replaceAll("(\\d+)(st|nd|rd|th)", "$1");

            // Define the input and output date formats
            SimpleDateFormat inputFormat = new SimpleDateFormat("d-MMM-yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Parse and format the cleaned date
            String formattedUserDate = outputFormat.format(inputFormat.parse(cleanedDate));
            System.out.println("Formatted user date: " + formattedUserDate);

            // Find all displayed flight dates
            List<WebElement> flightDates = driver.findElements(By.xpath("//*[contains(@class,'tg-deptime')]//span"));

            // Initialize a flag to track if any date matches
            boolean dateFound = false;

            // Iterate through each flight date and compare with the selected date
            for (WebElement flightDate : flightDates) {
                String flightDateValue = flightDate.getAttribute("data-tgdepdate");
                
                if (flightDateValue.equals(formattedUserDate)) {
                    dateFound = true;
                    System.out.println("user Entered date: " + formattedUserDate);
                    System.out.println("Flight date Found On The Result: " + flightDateValue);// Exit the loop once a match is found
                }
            }

            // Log the result once in the report
            if (dateFound) {
                Log.ReportEvent("PASS", "Flights are displayed based on user search dates. User entered: " + userSelectedDate + ", Found: " + formattedUserDate);
            } else {
                Log.ReportEvent("FAIL", "Flights are not displayed based on user search dates. User entered: " + userSelectedDate + ", Searched: " + formattedUserDate);
            }


            // Capture a screenshot of the current state
            ScreenShots.takeScreenShot1();

        } catch (Exception e) {
            // Handle exceptions and log the error
            Log.ReportEvent("ERROR", "An error occurred during date validation: " + e.getMessage());
            ScreenShots.takeScreenShot1();
        }



    }
    

  //Method to Click on Continue Button on Continue Flight Booking Popup
    public void clickOnContinueBookingFlightPopup() throws InterruptedException
    {
        driver.findElement(By.xpath("//div[@class='bottom-container-1']//button[text()='Continue']")).click();
        Thread.sleep(1000);
    }
  //Method to Validate data after selection of Flight on Result Screen
    public void validateDataAfterSelectingFlight(Log Log,ScreenShots ScreenShots,int departureIndex,int arrivalIndex,int priceIndex)
    {
        try {
           String departureTime=driver.findElement(By.xpath("(//h6[@data-tgdeptime])["+departureIndex+"]")).getText();
           String arrivalTime=driver.findElement(By.xpath("(//h6[@data-tgarrivaltime])["+arrivalIndex+"]")).getText();

           // Concatenate with dash
           String finalTime = departureTime + "-" + arrivalTime;
           System.out.println("----------------------------------------------------------"); // Output: 16:00-02:15
           System.out.println("Final Time Range: " + finalTime);
           System.out.println("----------------------------------------------------------"); // Output: 16:00-02:15

           String price=driver.findElement(By.xpath("(//span[@data-tgprice])["+priceIndex+"]")).getText();
           String priceOnly=price.split(" ")[1];
           String onlyPrice = priceOnly.split("\n")[0];
           String bookingPrice=driver.findElement(By.xpath("//*[contains(@class,'tg-owbar-price')]")).getText();
           String bookingPriceOnly=bookingPrice.split(" ")[1];
           String finalBookingPriceOnly = bookingPriceOnly.split("\n")[0];


           String timingsOnly=driver.findElement(By.xpath("//p[@data-tgfulltime]")).getText();
           // Remove all whitespace characters (spaces, tabs, etc.)
           String arrivalAndDeparture = timingsOnly.replaceAll("\\s+", "");


           System.out.println("Arrival and Departure time "+arrivalAndDeparture); // Output: 16:00-02:15
           System.out.println("----------------------------------------------------------"); // Output: 16:00-02:15      
           System.out.println(onlyPrice);
           System.out.println(finalBookingPriceOnly);

           String fromLocation = driver.findElement(By.xpath("(//div[contains(@class,'tg-select__single-value')])[1]")).getText();
           int start = fromLocation.indexOf('(');
           int end = fromLocation.indexOf(')');

           String fromLocationCode = null;
           if (start != -1 && end != -1) {
              fromLocationCode = fromLocation.substring(start + 1, end);
              System.out.println(fromLocationCode);  // Output: BLR
           }

           String ToLocation = driver.findElement(By.xpath("(//div[contains(@class,'tg-select__single-value')])[2]")).getText();
           int start1 = ToLocation.indexOf('(');
           int end1 = ToLocation.indexOf(')');

           String toLocationCode = null;
           if (start1 != -1 && end1 != -1) {
              toLocationCode = ToLocation.substring(start1 + 1, end1);
              System.out.println(toLocationCode);  // Output: BLR
           }
               String codes=fromLocationCode+"-"+toLocationCode;
           String fromAndToCode = driver.findElement(By.xpath("//p[@data-tgfullsector]")).getText();
           String cleanedTimeRange = fromAndToCode.replaceAll("\\s+", "");

           if(codes.contentEquals(cleanedTimeRange))
           {
              if(priceOnly.contains(finalBookingPriceOnly) && finalTime.contentEquals(arrivalAndDeparture))
              {
                 Log.ReportEvent("PASS", "Flights Price, Arrival and Departure Time is displaying Same");
                 ScreenShots.takeScreenShot1();
              }
              else {
                 Log.ReportEvent("FAIL", "Flights Price, Arrival and Departure Time is displaying are Not Same");
                 ScreenShots.takeScreenShot1();
                 Assert.fail();

              }
           }else{
              clickOnContinueBookingFlightPopup();
              String origenCode=driver.findElement(By.xpath("(//h2[text()='Airport Change']/parent::div//strong)[1]")).getText();
              String destinationCode=driver.findElement(By.xpath("(//h2[text()='Airport Change']/parent::div//strong)[2]")).getText();

              if(origenCode.contains(fromLocationCode) && destinationCode.contentEquals(toLocationCode))
              {
                 Log.ReportEvent("PASS", "Flights Price, Arrival and Departure Time is displaying Same");
                 ScreenShots.takeScreenShot1();
              }
              else {
                 Log.ReportEvent("FAIL", "Flights Price, Arrival and Departure Time is displaying are Not Same");
                 ScreenShots.takeScreenShot1();
                 Assert.fail();

              }
              cancelbuttonpopup();
           }

        }catch(Exception e)
        {
           Log.ReportEvent("FAIL", "Flights Price, Arrival and Departure Time is displaying Same"+ e.getMessage());
           ScreenShots.takeScreenShot1();
           Assert.fail();


        }

    } 
    
    public void cancelbuttonpopup() {
    	driver.findElement(By.xpath("//button[text()='No, Cancel']")).click();
    }
    //-------------------------------------------------------------------------------------------------------------
    
  //Method to Verify Default Currency Drop down is Selected on Home Page.
  	public void verifyDefaultCurrencyIsSelected(Log Log,ScreenShots ScreenShots)
  	{
  		try {
  			new WebDriverWait(driver, Duration.ofSeconds(90)).until(
  				    webDriver -> ((JavascriptExecutor) webDriver)
  				        .executeScript("return document.readyState").equals("complete")
  				);

  			Thread.sleep(4000);
  			WebElement defaultCurrencyDropdownSelected=driver.findElement(By.xpath("//div[text()='INR']"));
  			if(defaultCurrencyDropdownSelected.isDisplayed())
  			{
  				Log.ReportEvent("PASS", "Default Currency INR is Selected on Currency Dropdown is Successful");
  				ScreenShots.takeScreenShot1();
  			}
  			else {
  				Log.ReportEvent("FAIL", "Default Currency INR is Not Selected on Currency Dropdown");
  				ScreenShots.takeScreenShot1();
  			}
  		}catch(Exception e){
  			Log.ReportEvent("FAIL", "Default Currency INR is Not Selected on Currency Dropdown");
  			ScreenShots.takeScreenShot1();
  			e.printStackTrace();
  		}

  	}

  	@FindBy(xpath="//label[text()='Currency']//parent::div//parent::div//input")
    WebElement currencyDropDown;
  	
	@FindBy(id = "mui-component-select-currency")
	WebElement Currency_DropDown;

	//Method to click On Currency DropDown
	 public void clickOnCurrencyDropDown() throws InterruptedException {
	        Thread.sleep(2000);
	        //  driver.findElement(By.xpath("//label[text()='Currency']//parent::div//parent::div//input")).click();
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("window.scrollBy(0, -document.body.scrollHeight)");
	        Thread.sleep(2000);
	        currencyDropDown.click();
	    }

		//Method to select Currency DropDown Values
	 public void selectCurrencyDropDownValues(WebDriver driver,String value) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        System.out.println(value);
	        WebElement CurrencyValue = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//div[text()='"+value+"']")
	                ));

	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        // 🛠️ FIXED: "argument" → "arguments"
	        js.executeScript("arguments[0].scrollIntoView(true);", CurrencyValue);

	        wait.until(ExpectedConditions.elementToBeClickable(CurrencyValue)).click();
	    }
		
		//Method to Validate Selected Currency is Displayed on Result Screen
		public void validateCurrencyOnResultScreen(String currencyValue,Log Log, ScreenShots ScreenShots)
		{
			
			try {
				new WebDriverWait(driver, Duration.ofSeconds(90)).until(
					    webDriver -> ((JavascriptExecutor) webDriver)
					        .executeScript("return document.readyState").equals("complete")
					);

				WebElement currency = driver.findElement(By.cssSelector(".tg-other-price"));
			    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".tg-other-price")));
			    String priceText = currency.getText();

			    // Console output
			    System.out.println("currency : " + priceText);

			    // Log success with price text
			    Log.ReportEvent("PASS", "currency price displayed: " + priceText);

			    // Take screenshot
			    ScreenShots.takeScreenShot1();

			} catch (NoSuchElementException e) {
			    System.out.println("Element not found: " + e.getMessage());
			    Log.ReportEvent("FAIL", "Element not found: " + e.getMessage());
			    ScreenShots.takeScreenShot1();
			} catch (Exception e) {
			    System.out.println("An error occurred: " + e.getMessage());
			    Log.ReportEvent("FAIL", "An unexpected error occurred: " + e.getMessage());
			    ScreenShots.takeScreenShot1();
			}
		}
		//or
		//Method to Validate Selected Currency is Displayed on Result Screen
		public void validateCurrency(String currencyValue, Log Log, ScreenShots ScreenShots) {
		    try {
		        Thread.sleep(6000);
		        
		        // Use CSS selector for elements with both classes
		        List<WebElement> currencyTexts = driver.findElements(By.cssSelector(".other-currency-price.tg-other-price"));
		        
		        for (WebElement currencyText : currencyTexts) {
		            System.out.println(currencyText.getText());
		            currencyText.getText().contains(currencyValue);
		        }
		        
		        String currencyData = currencyTexts.get(0).getText();
		        String currencyCode = currencyData.substring(0, 3);
		        System.out.println(currencyCode);
		        System.out.println(currencyValue);

		        if (currencyValue.contentEquals(currencyCode)) {
		            Log.ReportEvent("PASS", "Currencies are Displayed Based on User Search " + currencyCode + " is Successful");
		            ScreenShots.takeScreenShot1();
		        } else {
		            Log.ReportEvent("FAIL", "Currencies are Not Displayed Based on User " + currencyCode);
		            ScreenShots.takeScreenShot1();
		            Assert.fail();
		        }
		    } catch (Exception e) {
		        Log.ReportEvent("FAIL", "Currencies are Not Displayed Based on User Search: " + e.getMessage());
		        ScreenShots.takeScreenShot1();
		        Assert.fail();
		    }
		}

		
		//or
		 //    Method to validate Currency 
		public void validateCurrencyRoundTrip(String expectedCurrency, Log log, ScreenShots screenShots) {
	        try {
	            // Wait to ensure elements are loaded
	            Thread.sleep(5000);

	            // Get footer prices and extract currency symbols
	            String footerDivSelectedCurrencyFrom = driver.findElement(By.xpath("//div[@data-tgflotherprice]")).getText().split(" ")[0].trim();
	            String footerDivSelectedCurrencyTo = driver.findElement(By.xpath("//div[@data-tgfltootherprice]")).getText().split(" ")[0].trim();
	            String footerDivSelectedCurrencyTotal = driver.findElement(By.xpath("//div[@data-tgothertotal]")).getText().split(" ")[0].trim();

	            // Wait for visible elements if needed
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

	            // Fetch FROM and TO currency elements
	            List<WebElement> fromCurrencyElements = driver.findElements(By.xpath("//*[contains(@class, 'tg-fromothercurrency')]"));
	            List<WebElement> toCurrencyElements = driver.findElements(By.xpath("//*[contains(@class, 'tg-toothercurrency')]"));

	            // Validate FROM currencies
	            boolean allFromMatch = true;
	            for (WebElement value : fromCurrencyElements) {
	                String currency = value.getText().split(" ")[0].trim();
	                System.out.println("FROM Currency found: " + currency);
	                if (!currency.equals(expectedCurrency)) {
	                    allFromMatch = false;
	                }
	            }

	            // Validate TO currencies
	            boolean allToMatch = true;
	            for (WebElement value : toCurrencyElements) {
	                String currency = value.getText().split(" ")[0].trim();
	                System.out.println("TO Currency found: " + currency);
	                if (!currency.equals(expectedCurrency)) {
	                    allToMatch = false;
	                }
	            }

	            // Validate footer values
	            boolean isFooterMatch = footerDivSelectedCurrencyFrom.equals(expectedCurrency) &&
	                                    footerDivSelectedCurrencyTo.equals(expectedCurrency) &&
	                                    footerDivSelectedCurrencyTotal.equals(expectedCurrency);

	            // Final result check
	            if (isFooterMatch && allFromMatch && allToMatch) {
	                log.ReportEvent("PASS", "All currency values match expected: " + expectedCurrency);
	            } else {
	                log.ReportEvent("FAIL", String.format("Expected: '%s'. Found Footer -> From: '%s', To: '%s', Total: '%s'.",
	                        expectedCurrency, footerDivSelectedCurrencyFrom, footerDivSelectedCurrencyTo, footerDivSelectedCurrencyTotal));
	                if (!allFromMatch) {
	                    log.ReportEvent("FAIL", "One or more FROM prices do not match expected: " + expectedCurrency);
	                }
	                if (!allToMatch) {
	                    log.ReportEvent("FAIL", "One or more TO prices do not match expected: " + expectedCurrency);
	                }
	                Assert.fail("Currency mismatch found in one or more locations.");
	            }

	            screenShots.takeScreenShot1(); // Capture after validation

	        } catch (Exception e) {
	            // Log full stack trace
	            StringWriter sw = new StringWriter();
	            PrintWriter pw = new PrintWriter(sw);
	            e.printStackTrace(pw);
	            log.ReportEvent("ERROR", "Exception during currency validation: " + e.getMessage());
	            log.ReportEvent("ERROR", "Stack Trace:\n" + sw.toString());
	            screenShots.takeScreenShot1();
	            Assert.fail("Exception occurred during currency validation: " + e.getMessage());
	        }
	    }
	    //-------------------------------------------------------------------------------------------------------------

//		// Method to click On Check In Baggage
//		public void clickOnCheckInBaggage() {
//		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
//		    WebElement clickCheckinbaggage = wait.until(ExpectedConditions.elementToBeClickable(
//		        By.xpath("//legend[text()='CHECK-IN BAGGAGE']/parent::div//input")));
//		    
//	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//legend[text()='CHECK-IN BAGGAGE']//parent::div//input")));
//
//		 	 clickCheckinbaggage.click();
//		}
		
		//Method to click On Check In Baggage
	    public void clickOnCheckInBaggage() throws InterruptedException
	    {
	        Thread.sleep(2000);
	        driver.findElement(By.xpath("//legend[text()='CHECK-IN BAGGAGE']//parent::div//input")).click();
	    }
	
		public void checkinBaggage(String index) throws InterruptedException
		{
		    clickOnCheckInBaggage();
		    driver.findElement(By.xpath("//div[@class='MuiPaper-root MuiPaper-elevation MuiPaper-rounded MuiPaper-elevation1 relative expansion-panel mb-8   css-xnuxc9']['"+index+"']"));
		    driver.findElement(By.xpath("//button[text()='Select']")).click();
		    List<WebElement> checkInBaggage = driver.findElements(By.xpath("//span[text()='Check-in baggage:']//strong"));
		    for(WebElement element : checkInBaggage)
		    {
		        String checkInBaggagetext = element.getText();
		        if(checkInBaggagetext.contains("0PC") || checkInBaggagetext.contains("0KG"))
		        {
		            System.out.println("Fail,Check-in baggage is not available");
		            
		        }
		        else
		        {
		            System.out.println("Pass,Check-in baggage is available");
		            
		        }    
		        
		    }
		    
		     		    
		}
		
		//Method to validate Check In Baggage Functionality
		public void validateCheckInBaggageFlightsOnResultScreen(Log Log,ScreenShots ScreenShots)
		{
		    try {
		       List<WebElement> checkInBaggage = driver.findElements(By.xpath("//span[text()='Check-in baggage:']//strong"));
		       for(WebElement element : checkInBaggage)
		       {
		          String checkInBaggageText = element.getText();
		          if(checkInBaggageText.contains("0PC") || checkInBaggageText.contains("0KG"))
		          {
                      System.out.println("Fail,Check-in baggage is not available");

		          }
		          else
		          {
			          Log.ReportEvent("PASS", "Check-in baggage is available"); 
		             System.out.println("Pass,Check-in baggage is available");
		          }
		       }
		    }
		    catch(Exception e)
		    {
		       Log.ReportEvent("FAIL", "Check In Baggage flights are Not displayed on Result Screen");
		       ScreenShots.takeScreenShot1();
		       e.printStackTrace();
		       Assert.fail();

		    }
		}
		
    
		
   //Method to validate Check In Baggage Functionality by Unchecking
    
    public void validateCheckInBaggageFlightsOnResultScreenByUnChecking(Log Log,ScreenShots ScreenShots)
    {
        try {
        	
            Thread.sleep(4000);
            String value=driver.findElement(By.xpath("((//h6[contains(@class,'MuiTypography-root MuiTypography-h6')])[1]/parent::div//span)[2]")).getText();
            Thread.sleep(3000);
                    
            if(value.contentEquals("Cabin Baggage Included")||value.contentEquals("Hand Baggage Only"))
            {
                Log.ReportEvent("PASS", "Both Check In Baggage and Non Check In flights are displayed on Result Screen is Successful");
                ScreenShots.takeScreenShot1();
            }
            else {
                Log.ReportEvent("FAIL", "Both Check In Baggage and Non Check In flights are Not displayed on Result Screen");
                ScreenShots.takeScreenShot1();
            }
        }
        catch(Exception e)
        {
            Log.ReportEvent("FAIL", "Both Check In Baggage and Non Check In flights are Not displayed on Result Screen");
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
        }

    }


	//Method to verify checkin Baggage is displaye---other way
	
	public void validatecheckinbaggagefilter(Log Log,ScreenShots ScreenShots) throws InterruptedException {
		driver.findElement(By.xpath("(//button[text()='Select'])[1]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		WebElement fareRules = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Fare Rules']")));
		fareRules.click();

        ScreenShots.takeScreenShot1(); 	
		String xpath = "//p[contains(@class, 'MuiTypography-body2') and contains(text(), 'PC')]";

        WebElement baggageInfo = driver.findElement(By.xpath(xpath));
        String baggageText = baggageInfo.getText().trim(); // e.g., "0 PC  Checkin"

        String pcCountStr = baggageText.split(" ")[0]; // Extracts "0"
        int pcCount = Integer.parseInt(pcCountStr);

        if (pcCount == 0) {
            System.out.println("There is no check-in baggage.");        
            ScreenShots.takeScreenShot1(); 	

        } else {
            System.out.println("It has check-in baggage.");
            ScreenShots.takeScreenShot1(); 	
       }
        driver.findElement(By.xpath("//button[text()='Close']")).click();
    }

	
    //-------------------------------------------------------------------------------------------------------------
	
	                              //Method to click on Refundable Fare
	
	public void clickOnRefundableFare() throws InterruptedException
	{
	       Thread.sleep(5000);
	    driver.findElement(By.xpath("//legend[text()='REFUNDABLE FARE']//parent::div//input")).click();
	}
	
	//Method to validate Refundable fare filter
	public void validateRefundableFareFlightsBasedOnIndex(Log log, ScreenShots screenShots) {
        try {
            List<WebElement> fares = driver.findElements(By.xpath("//span[@data-tgflrefundabletype]"));
            boolean allAreRefundable = true;

            for (WebElement fare : fares) {
                String fareText = fare.getText().trim();
                System.out.println("Fare type: " + fareText);

                if (fareText.equalsIgnoreCase("Non-refundable")) {
                    allAreRefundable = false;
                    break;
                }
            }

            if (allAreRefundable) {
                log.ReportEvent("PASS", "✅ All flights are 'Refundable' (no 'Non-refundable' found) on Result Screen in View Flights.");
            } else {
                log.ReportEvent("FAIL", "❌ One or more flights are 'Non-refundable' or do not include 'Refundable' .");
                Assert.fail("Detected non-refundable flight or missing 'Refundable' keyword.");
            }
        } catch (Exception e) {
            log.ReportEvent("ERROR", "Exception occurred during refundable fare validation: " + e.getMessage());
            screenShots.takeScreenShot1();  // use instance call here
            Assert.fail("Exception during refundable fare validation: " + e.getMessage());
        }
    }
//Method to validate Refundable fare filter
    public void validateRefundableFareFlights(Log Log, ScreenShots ScreenShots) {
        try {
            Thread.sleep(5000);

            List<WebElement> refundableFareDatas = driver.findElements(By.xpath("//div[@class='owf-amenities']"));

            boolean allAreRefundable = true;

            for (WebElement refundableFareData : refundableFareDatas) {
                WebElement span = refundableFareData.findElement(By.tagName("span"));
                String ariaLabel = span.getAttribute("aria-label").toLowerCase();
                System.out.println("Aria-label: " + ariaLabel);

                // Must NOT contain "non-refundable", and must contain "refundable"
                if (ariaLabel.contains("non-refundable") || !ariaLabel.contains("refundable")) {
                    allAreRefundable = false;
                    break;
                }
            }

            if (allAreRefundable) {
                Log.ReportEvent("PASS", "✅ All flights are 'Refundable' (no 'Non-refundable' found) on Result Screen.");
            } else {
                Log.ReportEvent("FAIL", "❌ One or more flights are 'Non-refundable' or do not include 'Refundable'.");
                Assert.fail("Detected non-refundable flight or missing 'Refundable' keyword.");
            }

            ScreenShots.takeScreenShot1();

        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Exception while validating refundable fare data: " + e.getMessage());
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
            Assert.fail();
        }
    }
    
   

  
  
  public void validateRefundableFareInsideCards(Log Log, ScreenShots ScreenShots) {
	    try {

	        // Get the Refundable text element
	        WebElement containerRefundableText = driver.findElement(By.xpath("//*[contains(@class,'tg-fare-refundableinfo')]"));
	        String refundableText = containerRefundableText.getText().trim();

	        // Check if policy text contains "Refundable"
	        if (refundableText.contains("Refundable")) {
	            Log.ReportEvent("PASS", "Refundable filter validation passed: Flight card text contains 'Refundable'.");
	            ScreenShots.takeScreenShot1();
	        } else {
	            Log.ReportEvent("FAIL", "Refundable filter validation failed:  Flight card text does not contain 'Refundable'. Text found: " + refundableText);
	            ScreenShots.takeScreenShot1();
	            Assert.fail("Refundable text validation failed.");
	        }
	    } catch (Exception e) {
	        Log.ReportEvent("FAIL", "Exception in validateRefundableFareInsideCards: " + e.getMessage());
	        ScreenShots.takeScreenShot1();
	        e.printStackTrace();
	        Assert.fail();
	    }
	}

	
	//Method for to click select button bse on index
  
  public void clickFlightCardSelectButtonBasedOnindex(int Index) throws InterruptedException {
	Thread.sleep(3000);
	WebElement flightCard = driver.findElement(By.xpath("(//*[contains(@class,'tg-flight-card')]//button[text()='Select'])[" + Index + "]"));
	Thread.sleep(500);
	JavascriptExecutor js = (JavascriptExecutor) driver;
	js.executeScript("arguments[0].click();", flightCard);
	 
  }

  //Method to validate both Refundable and NonRefundable fare after unselecting
    public void validateUnCheckingRefundableFareFlights(Log Log, ScreenShots ScreenShots) {
        try {
            Thread.sleep(5000);

            List<WebElement> refundableFareDatas = driver.findElements(By.xpath("//div[@class='owf-amenities']/span"));

            boolean foundRefundable = false;
            boolean foundNonRefundable = false;

            for (WebElement refundableFareData : refundableFareDatas) {
                String text = refundableFareData.getAttribute("aria-label").toLowerCase();

                if (text.contains("non-refundable")) {
                    System.out.println("Flight Fare: Non-refundable");
                    foundNonRefundable = true;
                } else if (text.contains("refundable")) {
                    System.out.println("Flight Fare: Refundable");
                    foundRefundable = true;
                } else {
                    System.out.println(text);
                    System.out.println("Flight Fare: Unknown or not specified :"+text);
                }
            }

            // Summary log after checking all fares
            if (foundRefundable && foundNonRefundable) {
                Log.ReportEvent("PASS", "✅ After unchecking 'Refundable', both 'Refundable' and 'Non-refundable' fares are displayed.");
            } else if (foundNonRefundable && !foundRefundable) {
                Log.ReportEvent("INFO", "ℹ️ Only 'Non-refundable' fares are displayed after unchecking 'Refundable'.");
            } else if (foundRefundable && !foundNonRefundable) {
                Log.ReportEvent("INFO", "ℹ️ Only 'Refundable' fares are displayed after unchecking 'Refundable'.");
            } else {
                Log.ReportEvent("INFO", "⚠️ No recognizable fare types (Refundable/Non-refundable) found.");
            }

            ScreenShots.takeScreenShot1();

        } catch (Exception e) {
            Log.ReportEvent("FAIL", "❌ Exception while validating fares after unchecking 'Refundable': " + e.getMessage());
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
            Assert.fail();
        }
    }
    
    
    public void validateUnCheckingRefundableFareFlightsBasedOnIndex(Log log, ScreenShots screenShots) {
        try {
            List<WebElement> fares = driver.findElements(By.xpath("//span[@data-tgflrefundabletype]"));
            boolean foundRefundable = false;
            boolean foundNonRefundable = false;

            for (WebElement fare : fares) {
                String fareValue = fare.getText().trim();
                System.out.println("Fare Value: " + fareValue);

                if (fareValue.equalsIgnoreCase("Refundable")) {
                    foundRefundable = true;
                } else if (fareValue.equalsIgnoreCase("Non-refundable")) {
                    foundNonRefundable = true;
                } else {
                    System.out.println("Flight Fare: Unknown or not specified: " + fareValue);
                }
            }

            // Summary log after checking all fares
            if (foundRefundable && foundNonRefundable) {
                log.ReportEvent("PASS", "✅ After unchecking 'Refundable', both 'Refundable' and 'Non-refundable' fares are displayed.");
            } else if (!foundRefundable && foundNonRefundable) {
                log.ReportEvent("INFO", "ℹ️ Only 'Non-refundable' fares are displayed after unchecking 'Refundable'.");
            } else if (foundRefundable && !foundNonRefundable) {
                log.ReportEvent("INFO", "ℹ️ Only 'Refundable' fares are displayed after unchecking 'Refundable'.");
            } else {
                log.ReportEvent("INFO", "⚠️ No recognizable fare types (Refundable/Non-refundable) found.");
            }

            screenShots.takeScreenShot1();  // Capture final result

        } catch (Exception e) {
            log.ReportEvent("ERROR", "Exception during unchecking refundable fare validation: " + e.getMessage());
            screenShots.takeScreenShot1();
            Assert.fail("Exception occurred: " + e.getMessage());
        }
    }
    //-------------------------------------------------------------------------------------------------------------

    

	                               //Method to click on AirLine Stops
    public void clickOnStops(String... stops) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(280));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (String stop : stops) {
            WebElement stopbutton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@class,'filter-stops')]//button[text()='"+stop+"']")
            ));
            js.executeScript("arguments[0].scrollIntoView(true);", stopbutton); 
            Thread.sleep(500); 
            js.executeScript("arguments[0].click();", stopbutton); 

        }
        }
	
	  //Method to Validate Stops is Selected on Result Screen
   /* public void validateStopsSelected(Log Log,ScreenShots ScreenShots,String... stops)
    {
        try {
            for(String stop: stops)
            {
                Thread.sleep(5000);
                WebElement selected=driver.findElement(By.xpath("//div[@class='filter-stops']//button[text()='"+stop+"' and contains(@class,'selected-filter')]"));
                WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(70));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='filter-stops']//button[text()='\"+stop+\"' and contains(@class,'selected-filter')]")));
                if(selected.isDisplayed())
                {
                    Log.ReportEvent("PASS", ""+stop+" "+ "Stop are Selected on Result Screen");
                }
                else {
                    Log.ReportEvent("FAIL", ""+stop+" "+ "Stop are Not Selected on Result Screen");
                    ScreenShots.takeScreenShot1();
                }
            }
            ScreenShots.takeScreenShot1();
        }
        catch(Exception e)
        {
            Log.ReportEvent("FAIL", "Stop are Not Selected on Result Screen");
            ScreenShots.takeScreenShot1();
        }
        
    }*/
	
/*	//Method to validate flights stops on Result Screen
	public void validateFlightsStopsOnResultScreen(String numberOfStops,Log Log,ScreenShots ScreenShots)
	{
	    try {
	       Thread.sleep(5000);
	       List<WebElement> flightStops=driver.findElements(By.xpath("//span[@data-tgstops]"));
	       boolean stops=true;
	       for(WebElement flightStop:flightStops)
	       {
	          String stop=flightStop.getText();
	          if(stop.contentEquals(numberOfStops))
	          {
	             System.out.println(stop);
		          Log.ReportEvent("PASS", "Stops are selected based on User Searched is Successful");

	          }
	          else {
	             stops=false;
	             Log.ReportEvent("FAIL", "FAIL are Not displayed based on User Searched");
	             ScreenShots.takeScreenShot1();
	             Assert.fail();
	          }
	       }
	       if(stops==true)
	       {
	    	   Thread.sleep(3000);
	          ScreenShots.takeScreenShot1();
	       }
	          Log.ReportEvent("PASS", "Flights displayed based on User Searched is Successful");


	    }
	    catch(Exception e)
	    {
	       Log.ReportEvent("FAIL", "Flights are Not displayed based on User Searched");
	       ScreenShots.takeScreenShot1();
	       Assert.fail();
	       e.printStackTrace();
	    }

	}*/


	// Method to validate flights stops on Result Screen
	public void validateFlightsStopsOnResultScreen(String numberOfStops, Log Log, ScreenShots ScreenShots) {
	    try {
	        Thread.sleep(5000);
	        List<WebElement> flightStops = driver.findElements(By.xpath("//*[@data-tgstops]"));
	        boolean stops = true;

	        for (WebElement flightStop : flightStops) {
	            String stop = flightStop.getText();
	            System.out.println(stop);

	            if (!stop.contentEquals(numberOfStops)) {
	                stops = false;
	                Log.ReportEvent("FAIL", "Stop mismatch: Expected [" + numberOfStops + "], but found [" + stop + "]");
	                ScreenShots.takeScreenShot1();
	                Assert.fail(); // Fail immediately on mismatch
	                break;
	            }
	        }

	        if (stops) {
	            Thread.sleep(3000);
	            ScreenShots.takeScreenShot1();
	            Log.ReportEvent("PASS", "All flights have stops as per user selection: " + numberOfStops);
	        }

	    } catch (Exception e) {
	        Log.ReportEvent("FAIL", "Exception occurred while validating stops");
	        ScreenShots.takeScreenShot1();
	        Assert.fail("Exception: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

    //-------------------------------------------------------------------------------------------------------------

	                                //Method to click ONWARD DEPART TIME
    public void selectOnWardDepartTime(String... times) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (String time : times) {
            String trimmedTime = time.trim();
           // String xpath = "//legend[normalize-space()='ONWARD DEPART TIME']/following-sibling::*//small[normalize-space()='" + trimmedTime + "']";
            String xpath = "//*[contains(@class,'depart-time tg-onward-dep-time')]//small[text()='" + trimmedTime + "']";
            WebElement departTime = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", departTime);
            wait.until(ExpectedConditions.elementToBeClickable(departTime));
            js.executeScript("arguments[0].click();", departTime);
        }
    }

	   //Method to Validate OnwardDeparture Time is Selected on Result Screen
	
    public void validateOnwardDepartureTimeIsSelected(Log log, ScreenShots screenshots, String... timeRanges) {
        System.out.println(" Departure Time Validation ");

        for (String range : timeRanges) {
            System.out.println("\nChecking range: " + range);
            try {
                Thread.sleep(2000);
                int start = Integer.parseInt(range.split("-")[0].trim());
                int end = Integer.parseInt(range.split("-")[1].trim());
                boolean overnight = end <= start;

                List<WebElement> times = driver.findElements(By.xpath(
                    "//h6[contains(@id, 'deptime')]"));

                int valid = 0, invalid = 0;
                for (WebElement depaturetime : times) {
                    int hour = Integer.parseInt(depaturetime.getText().trim().split(":")[0]);
                    boolean inRange = overnight ? (hour >= start || hour < end) : (hour >= start && hour < end);
                    if (inRange) {
                        System.out.println(depaturetime.getText() + "VALID");
                        valid++;
                    } else {
                        System.out.println(depaturetime.getText() + "INVALID");
                        log.ReportEvent("FAIL", depaturetime.getText() + " not in range " + range);
                        invalid++;
                    }
                }

                System.out.println("Valid: " + valid + ", Invalid: " + invalid);
                log.ReportEvent(invalid == 0 ? "PASS" : "FAIL", invalid == 0 ? "All times in range" +range: "Some times not in range");
                screenshots.takeScreenShot1();

            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
                log.ReportEvent("FAIL", "Error: " + e.getMessage());
                screenshots.takeScreenShot1();
            }
        }

        System.out.println("Validation Complete ");
    }
    
    //    or

  //Method to validate flights departure time on result screen
//    public void validateFlightsDepartureTimeOnResultScreen(int flightStartHour, int flightStartMinute, int flightEndHour, int flightEndMinute, Log Log, ScreenShots ScreenShots) {
//        try {
//            Thread.sleep(5000);
//
//            List<String> flightsDepartureData = new ArrayList<>();
//            List<WebElement> airlineDepartureCount = driver.findElements(By.xpath("//*[contains(@class,'tg-deptime')]"));
//
//            if (airlineDepartureCount.size() == 0) {
//                Log.ReportEvent("FAIL", "No Flights are Available on User Search");
//                ScreenShots.takeScreenShot1();
//                Assert.fail();
//            }
//
//            LocalTime startTime = LocalTime.of(flightStartHour, flightStartMinute);
//            LocalTime endTime = LocalTime.of(flightEndHour, flightEndMinute);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//
//            for (WebElement airlineDeparture : airlineDepartureCount) {
//                Thread.sleep(1000);
//                String departureText = airlineDeparture.getText().trim();
//                System.out.println(departureText);
//                flightsDepartureData.add(departureText);
//
//                LocalTime timeToCheck = LocalTime.parse(departureText, formatter);
//
//                if (flightStartHour != 18) {
//                    if (timeToCheck.isBefore(startTime) || !timeToCheck.isBefore(endTime)) {
//                        Log.ReportEvent("FAIL", "Flight at " + timeToCheck + " is outside user selected range: " + startTime + " to " + endTime);
//                        ScreenShots.takeScreenShot1();
//                        Assert.fail("Invalid flight time: " + timeToCheck);
//                    }
//                } else {
//                    // For evening time range (e.g., 18:00 to 23:59)
//                    if (timeToCheck.isBefore(startTime)) {
//                        Log.ReportEvent("FAIL", "Flight at " + timeToCheck + " is before the evening start time: " + startTime);
//                        ScreenShots.takeScreenShot1();
//                        Assert.fail("Invalid evening flight time: " + timeToCheck);
//                    }
//                }
//            }
//
//            // If loop completes without failure, log PASS
//            Log.ReportEvent("PASS", "All flights displayed are within the user selected range: " + startTime + " to " + endTime);
//            ScreenShots.takeScreenShot1();
//
//        } catch (Exception e) {
//            Log.ReportEvent("FAIL", "Exception while validating flight times: " + e.getMessage());
//            ScreenShots.takeScreenShot1();
//            e.printStackTrace();
//            Assert.fail();
//        }
//    }

    
    public void validateFlightsDepartureTimeOnResultScreen(int flightStartHour, int flightStartMinute, int flightEndHour, int flightEndMinute, Log Log, ScreenShots ScreenShots) {
        try {
            Thread.sleep(5000);

            List<String> flightsDepartureData = new ArrayList<>();
            
            // Replace with actual XPath expression
            List<WebElement> airlineDepartureCount = driver.findElements(By.xpath("//*[contains(@class,'tg-deptime')]"));

            if (airlineDepartureCount.size() == 0) {
                Log.ReportEvent("FAIL", "No Flights are Available on User Search");
                ScreenShots.takeScreenShot1();
                Assert.fail();
            }

            LocalTime startTime = LocalTime.of(flightStartHour, flightStartMinute);
            LocalTime endTime = LocalTime.of(flightEndHour, flightEndMinute);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

            for (WebElement airlineDeparture : airlineDepartureCount) {
                Thread.sleep(1000);
                String departureText = airlineDeparture.getText().trim();
                flightsDepartureData.add(departureText);

                // Remove any newline characters or unwanted whitespace
                departureText = departureText.replaceAll("\\n", " ").trim();

                // Extract only the time part (e.g., "01:10" from "01:10 19th")
                String timePart = departureText.split(" ")[0];  // Split on space and get the first part (time)

                // Now parse the time part only
                LocalTime timeToCheck = LocalTime.parse(timePart, formatter);

                if (flightStartHour != 18) {
                    if (timeToCheck.isBefore(startTime) || !timeToCheck.isBefore(endTime)) {
                        Log.ReportEvent("FAIL", "Flight at " + timeToCheck + " is outside user selected range: " + startTime + " to " + endTime);
                        ScreenShots.takeScreenShot1();
                        Assert.fail("Invalid flight time: " + timeToCheck);
                    }
                } else {
                    // For evening time range (e.g., 18:00 to 23:59)
                    if (timeToCheck.isBefore(startTime)) {
                        Log.ReportEvent("FAIL", "Flight at " + timeToCheck + " is before the evening start time: " + startTime);
                        ScreenShots.takeScreenShot1();
                        Assert.fail("Invalid evening flight time: " + timeToCheck);
                    }
                }
            }

            // If loop completes without failure, log PASS
            Log.ReportEvent("PASS", "All flights displayed are within the user selected range: " + startTime + " to " + endTime);
            ScreenShots.takeScreenShot1();

        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Exception while validating flight times: " + e.getMessage());
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
            Assert.fail();
        }
    }

    //-------------------------------------------------------------------------------------------------------------


	                                 //Method to click ONWARD ARRIVAL TIME
    public void selectOnWardArrivalTime(String... times) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (String time : times) {
            String trimmedTime = time.trim();
            String xpath = "//*[contains(@class,'depart-time tg-onward-arr-time')]//small[normalize-space()='" + trimmedTime + "']";

            WebElement departTime = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", departTime);
            wait.until(ExpectedConditions.elementToBeClickable(departTime));
            js.executeScript("arguments[0].click();", departTime);
        }
    }

    //Method to validate Onward Arrival Time
    public void validateOnwardArrivalTime(Log log, ScreenShots screenshots, String... timeRanges) {
    	System.out.println("Arrival Time Validation");
    	for (String range : timeRanges) {
            System.out.println("\nChecking range: " + range);
            try {
                Thread.sleep(2000);
                int start = Integer.parseInt(range.split("-")[0].trim());  // Ex: 00-06 ---> 0
                int end = Integer.parseInt(range.split("-")[1].trim());     // -------->6
                boolean overnight = end <= start;

                List<WebElement> times = driver.findElements(By.xpath(
                    "//h6[@data-tgarrivaltime]"));

                int valid = 0, invalid = 0;
                for (WebElement arrivaltimes : times) {
                    int hour = Integer.parseInt(arrivaltimes.getText().trim().split(":")[0]);
                    boolean inRange = overnight ? (hour >= start || hour < end) : (hour >= start && hour < end);
                    if (inRange) {
                        System.out.println(arrivaltimes.getText() + "VALID");
                        valid++;
                    } else {
                        System.out.println(arrivaltimes.getText() + "INVALID");
                        log.ReportEvent("FAIL", arrivaltimes.getText() + " not in range " + range);
                        invalid++;
                    }
                }

                System.out.println("Valid: " + valid + ", Invalid: " + invalid);
                log.ReportEvent(invalid == 0 ? "PASS" : "FAIL", invalid == 0 ? "All times in range" +range: "Some times not in range");
                screenshots.takeScreenShot1();

            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
                log.ReportEvent("FAIL", "Error: " + e.getMessage());
                screenshots.takeScreenShot1();
            }
        }

        System.out.println("Validation Complete ");
    }
    
    //or
    
  //Method to validate flights Arrival time on result screen
   
    
    public void validateFlightsArrivalTimeOnResultScreen(int flightStartHour, int flightStartMinute, int flightEndHour, int flightEndMinute, Log Log, ScreenShots ScreenShots) {
        try {
            Thread.sleep(5000);

            List<String> flightsarrivalData = new ArrayList<>();
            
            // Replace with actual XPath expression
            List<WebElement> airlineArrivalCount = driver.findElements(By.xpath("//*[contains(@class,'tg-arrtime')]"));

            if (airlineArrivalCount.size() == 0) {
                Log.ReportEvent("FAIL", "No Flights are Available on User Search");
                ScreenShots.takeScreenShot1();
                Assert.fail();
            }

            LocalTime startTime = LocalTime.of(flightStartHour, flightStartMinute);
            LocalTime endTime = LocalTime.of(flightEndHour, flightEndMinute);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

            for (WebElement airlineArrival : airlineArrivalCount) {
                Thread.sleep(1000);
                String arrivalText = airlineArrival.getText().trim();
                flightsarrivalData.add(arrivalText);

                // Remove any newline characters or unwanted whitespace
                arrivalText = arrivalText.replaceAll("\\n", " ").trim();

                // Extract only the time part (e.g., "01:10" from "01:10 19th")
                String timePart = arrivalText.split(" ")[0];  // Split on space and get the first part (time)

                // Now parse the time part only
                LocalTime timeToCheck = LocalTime.parse(timePart, formatter);

                if (flightStartHour != 18) {
                    if (timeToCheck.isBefore(startTime) || !timeToCheck.isBefore(endTime)) {
                        Log.ReportEvent("FAIL", "Flight at " + timeToCheck + " is outside user selected range: " + startTime + " to " + endTime);
                        ScreenShots.takeScreenShot1();
                        Assert.fail("Invalid flight time: " + timeToCheck);
                    }
                } else {
                    // For evening time range (e.g., 18:00 to 23:59)
                    if (timeToCheck.isBefore(startTime)) {
                        Log.ReportEvent("FAIL", "Flight at " + timeToCheck + " is before the evening start time: " + startTime);
                        ScreenShots.takeScreenShot1();
                        Assert.fail("Invalid evening flight time: " + timeToCheck);
                    }
                }
            }

            // If loop completes without failure, log PASS
            Log.ReportEvent("PASS", "All flights displayed are within the user selected range: " + startTime + " to " + endTime);
            ScreenShots.takeScreenShot1();

        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Exception while validating flight times: " + e.getMessage());
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
            Assert.fail();
        }
    }


    //-------------------------------------------------------------------------------------------------------------

    
	                                   //Method to click on SME / Corporate Fare
	//Method to click on SME / Corporate Fare
		
		
		public void clickOnSmeAndCorporateFareOnly() {
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		    try {
				WebElement smeCheckbox = driver.findElement(By.xpath("//legend[text()='SME / Corporate Fare']//parent::div//input"));


		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", smeCheckbox);
		        try {
		            smeCheckbox.click();
		        } catch (Exception e) {
		            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", smeCheckbox);
		        }
		    } catch (Exception e) {
		        System.out.println("SME Checkbox not found or not clickable: " + e.getMessage());
		    }
		}
		
		// Method to validate SME/Corporate Fare
	    public void validateSME(Log log, ScreenShots screenShots) {
	        try {
	            JavascriptExecutor js = (JavascriptExecutor) driver;
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	            js.executeScript("window.scrollBy(0, -document.body.scrollHeight)");
	            
	            // Get total flights before clicking SME checkbox
	            Thread.sleep(3000);
	            WebElement flightCountElementBefore = getFlightCountElement();
	            Thread.sleep(3000);
	            String totalFlightsFoundBefore = flightCountElementBefore.getText();
	            System.out.println("Flights before SME filter: " + totalFlightsFoundBefore);
	            js.executeScript("arguments[0].scrollIntoView(true);", flightCountElementBefore);
	            log.ReportEvent("INFO", "Total Flights Found Before Clicking SME CheckBox: " + totalFlightsFoundBefore);
	            screenShots.takeScreenShot1();

	            // Click SME filter checkbox
	            clickOnSmeAndCorporateFareOnly();
	            Thread.sleep(2000);
	            js.executeScript("window.scrollBy(0, -document.body.scrollHeight)");

	            // Wait and scroll slightly to allow refresh
	            //  wait.until(ExpectedConditions.stalenessOf(flightCountElementBefore)); // Wait for change
	            //  js.executeScript("window.scrollBy(0, -document.body.scrollHeight)");
	            Thread.sleep(2000); // Optional - can remove if no flakiness

	            // Get total flights after applying filter
	            WebElement flightCountElementAfter = getFlightCountElement();
	            String totalFlightsFoundAfter = flightCountElementAfter.getText();
	            System.out.println("Flights after SME filter: " + totalFlightsFoundAfter);
	            js.executeScript("arguments[0].scrollIntoView(true);", flightCountElementAfter);
	            log.ReportEvent("INFO", "Total Flights Found After Clicking SME CheckBox: " + totalFlightsFoundAfter);
	            screenShots.takeScreenShot1();

	            // Optional: assert flight count changed
	            Assert.assertNotEquals(totalFlightsFoundBefore, totalFlightsFoundAfter, "Flight count did not change after applying SME filter.");

	            // Select a flight (index 1)
	            selectFlightBasedOnIndex(1);

	            // Wait for fare element
	            WebElement fareElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//h6[@data-tgfaretype]")
	                    ));
	            js.executeScript("arguments[0].scrollIntoView(true);", fareElement);
	            String fareText = fareElement.getText();
	            log.ReportEvent("INFO", "Fare Element Text: " + fareText);

	            // Validate fare type
	            if (fareText.equalsIgnoreCase("SME fare") || fareText.equalsIgnoreCase("corporate fare")) {
	                log.ReportEvent("PASS", "SME/Corporate Fare Only flights are displayed");
	                screenShots.takeScreenShot1();
	            } else {
	                log.ReportEvent("FAIL", "SME/Corporate Fare Only flights are not displayed");
	                screenShots.takeScreenShot1();
	                Assert.fail("Displayed fare is not SME or Corporate");
	            }

	        } catch (TimeoutException e) {
	            log.ReportEvent("FAIL", "Timeout: The fare element was not visible within the specified time.");
	            screenShots.takeScreenShot1();
	            Assert.fail("Timeout occurred: " + e.getMessage());
	        } catch (NoSuchElementException e) {
	            log.ReportEvent("FAIL", "Element not found: " + e.getMessage());
	            screenShots.takeScreenShot1();
	            Assert.fail("Element not found: " + e.getMessage());
	        } catch (InterruptedException e) {
	            log.ReportEvent("FAIL", "Thread interrupted: " + e.getMessage());
	            screenShots.takeScreenShot1();
	            Assert.fail("Interrupted: " + e.getMessage());
	        } catch (Exception e) {
	            log.ReportEvent("FAIL", "Unexpected error: " + e.getMessage());
	            screenShots.takeScreenShot1();
	            Assert.fail("Unexpected error: " + e.getMessage());
	        }
	    }

	    // Helper to get flight count element
	    private WebElement getFlightCountElement() {
	        return driver.findElement(By.xpath("//span[@id='flight_count']"));
	    }
		
	    
	  //Method to validate Unselecting SME Fare CheckBox
	    public void validateUnSelectingSMEFare(Log log, ScreenShots screenShots) {
	        try {
	            JavascriptExecutor js = (JavascriptExecutor) driver;
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	            // Scroll up to refresh page view
	            js.executeScript("window.scrollBy(0, -document.body.scrollHeight)");

	            // Get flight count after SME filter applied
	            WebElement flightCountElementBefore = wait.until(ExpectedConditions.visibilityOf(getFlightCountElement()));
	            String flightsAfterSMEChecked = flightCountElementBefore.getText();
	            js.executeScript("arguments[0].scrollIntoView(true);", flightCountElementBefore);
	            log.ReportEvent("INFO", "Flights Found After SME Filter Applied: " + flightsAfterSMEChecked);
	            screenShots.takeScreenShot1();

	            //Function again calling close the details of fights
	            js.executeScript("window.scrollBy(0, -document.body.scrollHeight)");
	            selectFlightBasedOnIndex(1);

	            // Uncheck SME checkbox
	            clickOnSmeAndCorporateFareOnly();



	            Thread.sleep(3000);

	            // Wait for checkbox to become clickable and check state
	            WebElement smeCheckBox = driver.findElement(By.xpath("//legend[text()='SME / Corporate Fare']//parent::div//input"));



	            if (!smeCheckBox.isSelected()) {
	                log.ReportEvent("PASS", "SME check box is unselected");
	                screenShots.takeScreenShot1();
	            } else {
	                log.ReportEvent("FAIL", "SME check box is still selected");
	                screenShots.takeScreenShot1();
	                Assert.fail("Expected SME checkbox to be unselected");
	            }



	            // Get flight count after SME checkbox is unselected
	            js.executeScript("window.scrollBy(0, -document.body.scrollHeight)");
	            WebElement flightCountElementAfter = wait.until(ExpectedConditions.visibilityOf(getFlightCountElement()));
	            String flightsAfterSMEUnchecked = flightCountElementAfter.getText();
	            js.executeScript("arguments[0].scrollIntoView(true);", flightCountElementAfter);
	            log.ReportEvent("INFO", "Flights Found After SME Filter Removed: " + flightsAfterSMEUnchecked);
	            screenShots.takeScreenShot1();

	            // Validate flight count changed
	            Assert.assertNotEquals(flightsAfterSMEChecked, flightsAfterSMEUnchecked, "Flight count did not change after unchecking SME");

	        } catch (Exception e) {
	            log.ReportEvent("FAIL", "Exception while validating SME checkbox unselection: " + e.getMessage());
	            screenShots.takeScreenShot1();
	            e.printStackTrace();
	            Assert.fail("Exception occurred during SME checkbox unselection validation: " + e.getMessage());
	        }
	    }
		

		//Method to Validate No Flights are Displayed on Result Screen
		public void validateNoFlightsAreDisplayedOnResultScreen(Log Log, ScreenShots ScreenShots)
		{
			try {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-testid='AirplaneIcon']")));
				boolean value=driver.findElement(By.xpath("//*[@data-testid='AirplaneIcon']")).isDisplayed();
				if(value=true)
				{
					Log.ReportEvent("PASS", "No Airlines are Dispayed Please Modify the Search");
					ScreenShots.takeScreenShot1();
				}
				else {
					Log.ReportEvent("FAIL", "Airlines are Dispayed");
					ScreenShots.takeScreenShot1();
				}
			}
			catch(Exception e)
			{
				Log.ReportEvent("FAIL", "Airlines are Dispayed"+e.getMessage());
				ScreenShots.takeScreenShot1();
				e.printStackTrace();
			}
		}


//----------------------------------------------------------------------------------------------------------
		
		// Method for POLICY
		public void clickOnPolicy() {

//			WebElement policyCheckbox = driver.findElement(By.xpath("//legend[text()='POLICY']//parent::div//input"));
//			policyCheckbox.click();
			driver.findElement(By.cssSelector(".tg-inpolicy input")).click();
		}
		
	//Method to validate InPolicy Fliter
	    public void validateInPolicyFliter(Log Log, ScreenShots ScreenShots)
	    {
	        try {
	            WebElement policy=driver.findElement(By.className("inpolicy tg-policy"));
	            String policies=policy.getText();
	            System.out.println(policies);
	            if(policies.equals("In Policy"))
	            {
	                Log.ReportEvent("Pass", "All In Policy Flights are displayed");
	                ScreenShots.takeScreenShot1();
	            }
	            else if(policies.equals("Out of Policy"))
	            {
	                Log.ReportEvent("Pass", "All Out Of Policy Flights are displayed");
	                ScreenShots.takeScreenShot1();
	            }
	            else
	            {
	                Log.ReportEvent("Fail", "All In Policy Flights are Not displayed");
	                ScreenShots.takeScreenShot1();
	            }

	            List<WebElement> inPolicy=driver.findElements(By.className("inpolicy tg-policy"));

	            for(WebElement policy1:inPolicy)
	            {
	                String values=policy1.getText();
	                System.out.println(values);
	            }
	        }
	        catch (Exception e) {
	            Log.ReportEvent("FAIL", "Exception while checking In Policy Flights: " + e.getMessage());
	            ScreenShots.takeScreenShot1();
	            e.printStackTrace();
	        }

	    }
	    
	    //or
	    

	    public void validatePolicy(Log Log, ScreenShots ScreenShots, String expectedValue) {
			try {
				// Wait until at least one policy element is present
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				wait.until(ExpectedConditions.or(
						ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='inpolicy tg-policy']")),
						ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='outofpolicy tg-policy']"))
						));

				// Re-locate all policy elements freshly (avoid stale)
				List<WebElement> inPolicyElements = driver.findElements(By.xpath("//div[@class='inpolicy tg-policy']"));
				List<WebElement> outOfPolicyElements = driver.findElements(By.xpath("//div[@class='outofpolicy tg-policy']"));

				List<WebElement> allPolicies = new ArrayList<>();
				allPolicies.addAll(inPolicyElements);
				allPolicies.addAll(outOfPolicyElements);

				if (allPolicies.isEmpty()) {
					Log.ReportEvent("FAIL", "❌ No policy elements found.");
					ScreenShots.takeScreenShot1();
					Assert.fail("No policy elements to validate.");
					return;
				}

				List<String> mismatchedPolicies = new ArrayList<>();
				boolean allMatch = true;

				// Re-fetch text inside loop to avoid stale elements
				for (int i = 0; i < allPolicies.size(); i++) {
					try {
						WebElement freshPolicy = allPolicies.get(i);
						String policyText = freshPolicy.getText().trim();
						System.out.println("Policy Text: " + policyText);

						if (!policyText.equalsIgnoreCase(expectedValue)) {
							mismatchedPolicies.add(policyText);
							allMatch = false;
						}
					} catch (StaleElementReferenceException se) {
						// Try re-fetching if stale
						allPolicies = driver.findElements(By.xpath("//div[@class='inpolicy tg-policy' or @class='outofpolicy tg-policy']"));
						i--; // retry the same index
					}
				}

				if (allMatch) {
					Log.ReportEvent("PASS", "✅ All displayed policies match: '" + expectedValue + "'");
				} else {
					Log.ReportEvent("FAIL", "❌ Some policies do not match '" + expectedValue + "'. Mismatched: " + mismatchedPolicies);
					Assert.fail("One or more policies do not match expected value.");
				}

				ScreenShots.takeScreenShot1();

			} catch (Exception e) {
				Log.ReportEvent("FAIL", "❌ Exception while validating policy: " + e.getMessage());
				ScreenShots.takeScreenShot1();
				e.printStackTrace();
				Assert.fail("Exception during policy validation.");
			}
		}

	    //-------------------------------------------------------------------------------------------------------------
	  
	    
	  //Method to validate Policy Filter UnChecked InPolicy and OutOfPolicy
	  //Method to validate Policy Filter UnChecked InPolicy and OutOfPolicy
	    public void validatePolicyFilterUnChecked(Log Log, ScreenShots ScreenShots) {
	        try {
	            // Step 0: Verify that the Policy Filter is unchecked
	            WebElement policyFilterCheckbox = driver.findElement(By.xpath("//legend[text()='POLICY']//parent::div//input"));
	            boolean isChecked = policyFilterCheckbox.isSelected();

	            if (isChecked) {
	                Log.ReportEvent("FAIL", "❌ Policy filter is still checked. Uncheck it before running this test.");
	                ScreenShots.takeScreenShot1();
	                Assert.fail("Policy filter is not unchecked.");
	                return;
	            }

	            Log.ReportEvent("INFO", "🔍 Policy filter is confirmed to be unchecked.");

	            // Step 1: Wait for at least one in-policy or out-of-policy element to be visible
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	            wait.until(ExpectedConditions.or(
	                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.inpolicy.tg-policy")),
	                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.outofpolicy.tg-policy"))
	                    ));

	            // Step 2: Collect policy status elements
	            List<WebElement> inPolicyElements = driver.findElements(By.cssSelector("div.inpolicy.tg-policy"));
	            List<WebElement> outOfPolicyElements = driver.findElements(By.cssSelector("div.outofpolicy.tg-policy"));

	            boolean foundInPolicy = !inPolicyElements.isEmpty();
	            boolean foundOutOfPolicy = !outOfPolicyElements.isEmpty();

	            // Log what was found
	            if (foundInPolicy && foundOutOfPolicy) {
	                Log.ReportEvent("PASS", "✅ Both 'In Policy' and 'Out of Policy' flights are displayed when the filter is unchecked.");
	            } else if (foundInPolicy) {
	                Log.ReportEvent("PASS", "✅ Only 'In Policy' flights are displayed. Filter is unchecked but only some results are visible.");
	            } else if (foundOutOfPolicy) {
	                Log.ReportEvent("PASS", "✅ Only 'Out of Policy' flights are displayed. Filter is unchecked but only some results are visible.");
	            } else {
	                Log.ReportEvent("FAIL", "❌ No flights displayed with either 'In Policy' or 'Out of Policy' status.");
	                ScreenShots.takeScreenShot1();
	                Assert.fail("No valid policy statuses detected after filter unchecked.");
	            }

	            // Step 3: Output each policy for debug
	            // Step 3: Output each policy for debug
	            for (WebElement policy : inPolicyElements) {
	                System.out.println("In Policy flight displayed: " + policy.getText().trim());
	            }
	            for (WebElement policy : outOfPolicyElements) {
	                System.out.println("Out of Policy flight displayed: " + policy.getText().trim());
	            }

	            // Step 4: Final screenshot
	            ScreenShots.takeScreenShot1();

	        } catch (TimeoutException e) {
	            Log.ReportEvent("FAIL", "❌ Timed out waiting for policy status elements.");
	            ScreenShots.takeScreenShot1();
	            e.printStackTrace();
	            Assert.fail();
	        } catch (Exception e) {
	            Log.ReportEvent("FAIL", "❌ Exception during policy filter unchecked validation: " + e.getMessage());
	            ScreenShots.takeScreenShot1();
	            e.printStackTrace();
	            Assert.fail();
	        }
	    }
	    //---------------------------------------------------------------------------------------------

	                                   //Method for AIRLINES
	 // Method to select multiple airlines based on passed names
	    public void airLines(String... airlines) throws InterruptedException {
	        for (String airline : airlines) {
	        	Thread.sleep(3000);
	            driver.findElement(By.xpath("//*[text()='AIRLINES']//parent::div//span[text()='" + airline + "']//parent::div/parent::li//input")).click();
	        }
	    }

	    // Method to validate if the selected airlines are displayed correctly
	    public void validateAirlines(Log Log, ScreenShots ScreenShots, String... expectedAirlines) {
	        try {
	            Thread.sleep(3000); 

	            // Find the list of airline name elements displayed on the screen
	            List<WebElement> displayedAirlines = driver.findElements(By.xpath("//*[contains(@class,'tg-flightcarrier')]"));

	            // If no airlines are found,fail message appears
	            if (displayedAirlines.isEmpty()) {
	                Log.ReportEvent("FAIL", "No airlines are displayed on the results screen.");
	                ScreenShots.takeScreenShot1();
	                Assert.fail("No airlines displayed on the screen.");
	            }

	            // Loop through each expected airline name passed by the user
	            for (String expectedAirline : expectedAirlines) {
	                boolean airlineFound = false;

	                //check with user expected airline 
	                for (WebElement displayedAirlineElement : displayedAirlines) {
	                    String displayedAirline = displayedAirlineElement.getText().trim();

	                    if (displayedAirline.equalsIgnoreCase(expectedAirline)) {
	                    	Thread.sleep(3000);
	                        airlineFound = true;
	                        Log.ReportEvent("PASS", "The selected airline is displayed correctly: " + displayedAirline);
	                        ScreenShots.takeScreenShot1();
	                        break;  // Break if the airline is found
	                    }
	                }

	                // If the airline was not found, log failure
	                if (!airlineFound) {
	                    Log.ReportEvent("FAIL", "The selected airline is not displayed correctly. Expected: " + expectedAirline);
	                    ScreenShots.takeScreenShot1();
	                    Assert.fail("Airline mismatch: Expected: " + expectedAirline + " not found.");
	                }
	            }
	        } catch (Exception e) {
	            Log.ReportEvent("FAIL", "Exception while validating airlines: " + e.getMessage());
	            ScreenShots.takeScreenShot1();
	            e.printStackTrace();
	            Assert.fail();
	        }
	    }

	
    //-------------------------------------------------------------------------------------------------------------

	                                     //Method for Clear Filter
	public void clickOnClearFilter()
	{
		driver.findElement(By.xpath("//button[text()='Clear Filters']")).click();
	}

	                   //Method to validate clear filters
	
	public void verifyflightscount(Log Log, ScreenShots ScreenShots) {
	    try {
	      
	     // --- STEP 1: Get original count ---
	        String originalCount = driver.findElement(By.xpath("//p[contains(text(), 'Total') ]")).getText().trim();
	        System.out.println("[Before Filters] Flight count: Total " + originalCount + " flights found.");
	        Log.ReportEvent("INFO", "Original flight count: " + originalCount);
            ScreenShots.takeScreenShot1();

	        // --- STEP 2: Apply filter (e.g., click on airline) ---
	        Tripgain_resultspage tripgainresultspage=new Tripgain_resultspage(driver);
	        tripgainresultspage.adjustMaximumSliderToValue(driver, 242061);
            ScreenShots.takeScreenShot1();

	        // --- STEP 3: Wait until flight count changes ---
	        int retries = 10;
	        while (retries > 0) {
	            String currentCount = driver.findElement(By.xpath("//span[@id='flight_count']")).getText().trim();
	            if (!currentCount.equals(originalCount)) {
	                break;
	            }
	            Thread.sleep(1000); // Wait 1 second between retries
	            retries--;
	        }

	        // --- STEP 4: Get updated count ---
	        String filteredCount = driver.findElement(By.xpath("//p[contains(text(), 'Total')]")).getText();
	        System.out.println("[After Filter] Flight count: " + filteredCount);
	        System.out.println("Total count " + originalCount + " flights found.");
	        Log.ReportEvent("INFO", "Filtered flight count: " + filteredCount);
            ScreenShots.takeScreenShot1();

	        
	       
	    } catch (Exception e) {
	        System.out.println("ERROR: Verification failed - " + e.getMessage());
	        Log.ReportEvent("FAIL", "Clear Filters verification failed - " + e.getMessage());
	        ScreenShots.takeScreenShot1();
	        e.printStackTrace();
	    }
	}
	
    //-------------------------------------------------------------------------------------------------------------
	
	//Method To Adjust Minimum Slider Value on Slide Bar(Low to High Price)
	//Method to Adjust Minimum Value In Slider
		public double[] adjustMinimumSliderToValue(WebDriver driver, double targetValue) {
		    double minValue = -1;
		    double maxValue = -1;
	 
		    try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	 
		        // Wait until the minimum slider input is visible
		        WebElement minSliderInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
		                By.xpath("//input[@data-index='0']")));
	 
		        // Get min and max values from slider attributes
		        minValue = Double.parseDouble(minSliderInput.getAttribute("aria-valuemin"));
		        maxValue = Double.parseDouble(minSliderInput.getAttribute("aria-valuemax"));
	 
		        System.out.println("Min Value: " + minValue + ", Max Value: " + maxValue);
	 
		        // Clamp the target value within the slider range
		        double clampedValue = Math.max(minValue, Math.min(maxValue, targetValue));
		        double percentage = (clampedValue - minValue) / (maxValue - minValue);
	 
		        System.out.println("Target Value: " + clampedValue + ", Percentage: " + percentage);
	 
		        // Get the slider track and calculate width
		        WebElement sliderTrack = driver.findElement(By.xpath("//*[contains(@class, 'MuiSlider-track')]"));
		        int trackWidth = sliderTrack.getSize().getWidth();
		        int targetOffset = (int) (trackWidth * percentage);
	 
		        System.out.println("Track Width: " + trackWidth + ", Target Offset: " + targetOffset);
	 
		        // Get slider thumb and track X offset
		        WebElement thumbHandle = driver.findElement(By.xpath("(//*[@data-index='0'])[1]"));
		        int thumbX = thumbHandle.getLocation().getX();
		        int trackX = sliderTrack.getLocation().getX();
		        int currentOffset = thumbX - trackX;
		        int moveBy = targetOffset - currentOffset;
	 
		        System.out.println("Current Offset: " + currentOffset + ", Move By: " + moveBy);
	 
		        // Perform the slider movement
		        Actions action = new Actions(driver);
		        action.moveToElement(thumbHandle)
		                .pause(Duration.ofMillis(300))
		                .clickAndHold()
		                .moveByOffset(moveBy, 0)
		                .pause(Duration.ofMillis(200))
		                .release()
		                .perform();
	 
		        // Optional wait for UI to update
		        Thread.sleep(1000);
	 
		        // Log updated value
		        String newMinValue = minSliderInput.getAttribute("aria-valuenow");
		        System.out.println("Updated Min Value: " + newMinValue);
	 
		    } catch (TimeoutException te) {
		        System.err.println("❌ Timeout waiting for slider element: " + te.getMessage());
		    } catch (NoSuchElementException ne) {
		        System.err.println("❌ Slider element not found: " + ne.getMessage());
		    } catch (InterruptedException ie) {
		        System.err.println("❌ Thread sleep was interrupted: " + ie.getMessage());
		        Thread.currentThread().interrupt(); // restore interrupted status
		    } catch (Exception e) {
		        System.err.println("❌ Unexpected error while adjusting slider: " + e.getMessage());
		        e.printStackTrace();
		    }
	 
		    return new double[]{minValue, maxValue};
		}
	 
    //-------------------------------------------------------------------------------------------------------------

	//Method To verify Default Price Range in Price Slider
	//Method To verify Default Price Range in Price Slider
		public double[] verifyDefaultPriceRangeinPriceSlider(Log Log, ScreenShots ScreenShots, WebDriver driver) {
	 
			try {
				// Wait for the page and slider to load
				Thread.sleep(8000); // Consider using WebDriverWait instead for dynamic waits
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	 
				// Wait for the min slider input element
				WebElement minSliderInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
						//By.xpath("//span[@data-index='0']//input[@type='range']")));
						By.xpath("//*[@data-index='0']//input[@type='range']")));
				// Get min and max values from slider
				double minValue = Double.parseDouble(minSliderInput.getAttribute("aria-valuemin"));
				double maxValue = Double.parseDouble(minSliderInput.getAttribute("aria-valuemax"));
	 
				System.out.println("Min value: " + minValue);
				System.out.println("Max value: " + maxValue);
	 
				// Get all price elements
				List<WebElement> priceElements = driver.findElements(By.xpath(
					//	"//div[contains(@class, 'owf-price-fare') and contains(@class, 'bold') and contains(@class, 'price')]//span[@data-tgprice]"));
	                       "//*[@data-tgprice]"));
				if (minValue >= 0 && maxValue > minValue) {
					String message = "PASS: Default price range displayed is: Min = " + minValue + ", Max = " + maxValue;
					Log.ReportEvent("PASS", message);
					ScreenShots.takeScreenShot1();
	 
					// Loop through each price element
					for (WebElement price : priceElements) {
						String priceText = price.getText().replaceAll("[^\\d.]", ""); // Remove currency symbols, commas, etc.
						try {
							double priceValue = Double.parseDouble(priceText);
							if (priceValue >= minValue && priceValue <= maxValue) {
								System.out.println("Price within range: " + priceValue);
							} else {
								System.out.println("Price out of range: " + priceValue);
							}
						} catch (NumberFormatException ex) {
							System.out.println("Skipping invalid price format: " + priceText);
						}
					}
				} else {
					String message = "FAIL: Default price range is invalid. Min = " + minValue + ", Max = " + maxValue;
					Log.ReportEvent("FAIL", message);
					ScreenShots.takeScreenShot1();
					Assert.fail(message);
				}
	 
				return new double[]{minValue, maxValue}; // Return the price range
	 
			} catch (Exception e) {
				Log.ReportEvent("ERROR", "Exception while checking default price range: " + e.getMessage());
				e.printStackTrace();
				ScreenShots.takeScreenShot1();
				Assert.fail("Exception occurred during price range validation");
				return new double[0]; // Required fallback
			}
		}
    //-------------------------------------------------------------------------------------------------------------

  //Method to Validate Prices are displaying based on User Select/Slide
   /* public void verifyPriceRangeValuesOnResultScreen(Log Log, ScreenShots ScreenShots,int index) {
        try {
            String minPriceValue = driver.findElement(By.xpath("//input[@data-index='"+index+"']")).getAttribute("aria-valuenow");
            String maxPriceValue = driver.findElement(By.xpath("//input[@data-index='"+index+"']")).getAttribute("aria-valuemax");
            String priceRange = driver.findElement(By.xpath("(//div[@class='owf-price']/h6)[1]")).getText();

            // Use regex to extract price like ₹ 38,949
            Pattern pattern = Pattern.compile("₹\\s?\\d{1,3}(,\\d{3})*");
            Matcher matcher = pattern.matcher(priceRange);

            if (matcher.find()) {
                String rawPrice = matcher.group(); // Example: ₹ 38,949

                // Remove ₹ and commas to get clean number
                String priceValue = rawPrice.replaceAll("[₹,\\s]", ""); // → 38949

                // Parse to integers
                int priceValueRange = Integer.parseInt(priceValue);
                int minPriceValueRange = Integer.parseInt(minPriceValue);
                int maxPriceValueRange = Integer.parseInt(maxPriceValue);
                System.out.println(priceValueRange);
                System.out.println(minPriceValueRange);
                System.out.println(maxPriceValueRange);
                if(index==0)
                {
                    if (priceValueRange >=minPriceValueRange && priceValueRange <= maxPriceValueRange) {
                        Log.ReportEvent("PASS", "Price extracted: " + priceValue + " - Flights are filtered based on Price Range on Slidebar");
                    } else {
                        Log.ReportEvent("FAIL", "Price extracted: " + priceValue + " - Flights are NOT filtered based on Price Range on Slidebar");
                    }
                }else {
                    if (priceValueRange <=minPriceValueRange && priceValueRange <= maxPriceValueRange) {
                        Log.ReportEvent("PASS", "Price extracted: " + priceValue + " - Flights are filtered based on Price Range on Slidebar");
                    } else {
                        Log.ReportEvent("FAIL", "Price extracted: " + priceValue + " - Flights are NOT filtered based on Price Range on Slidebar");
                    }
                }

            } else {
                Log.ReportEvent("FAIL", "Price value not found in text: " + priceRange);
            }

            ScreenShots.takeScreenShot1();
        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Exception occurred while verifying price range filtering: " + e.getMessage());
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
        }
    }*/
    
  //Method to Validate Price Range Of the Price Fliter
    public void verifyPriceRangeValues(Log Log, ScreenShots ScreenShots) {
        try {
            Thread.sleep(3000);

            // Get current slider values
            int min = Integer.parseInt(driver.findElement(By.xpath("//input[@data-index='0']")).getAttribute("aria-valuenow"));
            int max = Integer.parseInt(driver.findElement(By.xpath("//input[@data-index='1']")).getAttribute("aria-valuenow"));

            System.out.println("Slider Min: " + min);
            System.out.println("Slider Max: " + max);

            // Get all price elements
            List<WebElement> priceElements = driver.findElements(By.xpath(
                "//span[@data-tgprice]"
            ));

            boolean allPricesInRange = true;

            for (WebElement priceElement : priceElements) {
                String rawPrice = priceElement.getText(); // e.g., "₹ 52,354" or "INR 45,678"
                String cleanPrice = rawPrice.replaceAll("[^0-9]", ""); // Keep digits only
                try {
                    int price = Integer.parseInt(cleanPrice);
                    if (price >= min && price <= max) {
                        System.out.println("Price within range: ₹" + price);
                    } else {
                        System.out.println("❌ Price out of range: ₹" + price);
                        allPricesInRange = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Skipping invalid price: " + rawPrice);
                    allPricesInRange = false;
                }
            }

            if (allPricesInRange) {
                Log.ReportEvent("PASS", "✅ All flight prices are within the range ₹" + min + " - ₹" + max);
            } else {
                Log.ReportEvent("FAIL", "❌ Some flight prices are outside the range ₹" + min + " - ₹" + max);
                Assert.fail("Some prices are out of range.");
            }

            ScreenShots.takeScreenShot1();

        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Exception during price validation: " + e.getMessage());
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
            Assert.fail();
        }
    }
   
    //-------------------------------------------------------------------------------------------------------------

  //Method To Adjust Maximum Slider Value on Slide Bar(High to Low Price)
  //Method To Adjust Minimum Slider Value on Slide Bar( High to Low Price)
  	public double[] adjustMaximumSliderToValue(WebDriver driver, double targetValue) {
  	    double minValue = -1;
  	    double maxValue = -1;
   
  	    try {
  	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
   
  	        // Locate the second thumb input (data-index='1')
  	        WebElement sliderInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
  	                By.xpath("//input[@data-index='1']")));
   
  	        Thread.sleep(1000); // Optional: wait for animation/UI rendering
   
  	        // Get min/max from attributes
  	        minValue = Double.parseDouble(sliderInput.getAttribute("aria-valuemin"));
  	        maxValue = Double.parseDouble(sliderInput.getAttribute("aria-valuemax"));
   
  	        System.out.println("Min: " + minValue + ", Max: " + maxValue);
   
  	        // Clamp and calculate percentage
  	        double percentage = Math.max(0, Math.min(1, (targetValue - minValue) / (maxValue - minValue)));
  	        System.out.println("Target Value: " + targetValue + ", Percentage: " + percentage);
   
  	        // Get track's location and width
  	        WebElement sliderTrack = driver.findElement(By.xpath("//*[contains(@class, 'MuiSlider-track')]"));
  	        Point trackLocation = sliderTrack.getLocation();
  	        int trackStartX = trackLocation.getX();
  	        int trackWidth = sliderTrack.getSize().getWidth();
   
  	        int targetX = (int) (trackStartX + (percentage * trackWidth));
   
  	        // Locate thumb and get its current position
  	        WebElement thumb = driver.findElement(By.xpath("//input[@data-index='1']"));
  	        Point thumbLocation = thumb.getLocation();
  	        int thumbX = thumbLocation.getX();
   
  	        int moveBy = targetX - thumbX;
   
  	        System.out.println("Moving Thumb 2 by offset: " + moveBy);
   
  	        // Move the second thumb
  	        Actions actions = new Actions(driver);
  	        actions.clickAndHold(thumb).moveByOffset(moveBy, 0).release().perform();
   
  	        System.out.println("✅ Thumb 2 moved to target value: " + targetValue);
   
  	    } catch (TimeoutException te) {
  	        System.err.println("❌ Timeout waiting for slider input: " + te.getMessage());
  	    } catch (NoSuchElementException ne) {
  	        System.err.println("❌ Slider element not found: " + ne.getMessage());
  	    } catch (InterruptedException ie) {
  	        System.err.println("❌ Thread.sleep interrupted: " + ie.getMessage());
  	        Thread.currentThread().interrupt(); // restore interrupt status
  	    } catch (Exception e) {
  	        System.err.println("❌ Unexpected error while adjusting max slider: " + e.getMessage());
  	        e.printStackTrace();
  	    }
   
  	    return new double[]{minValue, maxValue};
  	}
   
 
    
    //-------------------------------------------------------------------------------------------------------------

	                           //Method to Validate Airline List
	public void validateAirLinesList(String airlinename)
	{
		String airline=driver.findElement(By.xpath("(//div[@class='owf-carrier-info']//p)[1]")).getText();

		Assert.assertEquals(airlinename, airline);


		List<WebElement> airlineName=driver.findElements(By.xpath("//div[@class='mb-60']//div[@class='owf-carrier-info']"));

		for(WebElement name:airlineName)
		{

			String names=name.getText();
			System.out.println(names);

		}


	}
	
	                        //Method to get Number Of Airlines in the Result Page
	public void getNumberOfAirLines()
	{
		List<WebElement> airlineName=driver.findElements(By.xpath("//div[@class='mb-60']//div[@class='owf-carrier-info']"));
		int total=airlineName.size();
		System.out.println(total);
	}

    //-------------------------------------------------------------------------------------------------------------

	                      //Method to Select Horizontal Date on Home Page and Validate.
	public void selectHorizontalDate(String dayAbbreviation, int day, String monthAbbreviation) throws TimeoutException, ParseException, InterruptedException {

		String suffix = getDaySuffix(day);
		String formattedDate = String.format("%s, %d%s %s", dayAbbreviation, day, suffix, monthAbbreviation);

		System.out.println("Looking for date: " + formattedDate);
		System.out.println("------------------------------------");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2)); // Wait up to 10 seconds

		try {
			// Check if the date is visible initially
			WebElement dateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + formattedDate + "']")));
			dateElement.click(); // Click the date element
		} catch (TimeoutException e) {
			// If the element is not found, keep navigating until the date is found
			while (true) {
				try {
					// Click the next arrow to go to the next month/section
					WebElement nextArrow = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@data-testid='ChevronRightIcon']")));
					nextArrow.click();

					// Check if the date element appears
					WebElement dateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + formattedDate + "']")));
					dateElement.click(); // Click the date element
					break; // Break the loop once the date is found and clicked
				} catch (TimeoutException ex) {
					// If the next arrow is not clickable or the date is not found, keep trying
					System.out.println("Date not found in the current view. Trying next...");
				}
			}
		}

		// Clean the ordinal suffix (st, nd, rd, th)
		String cleanedDate = formattedDate.replaceAll("(\\d+)(st|nd|rd|th)", "$1");

		// Parse the date
		SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM", Locale.ENGLISH);
		Date date = inputFormat.parse(cleanedDate);

		// Set year to 2025
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, 2025);

		// Get day with suffix
		int days = cal.get(Calendar.DAY_OF_MONTH);
		String dayWithSuffix = getDayWithSuffix(days);

		// Format final date
		SimpleDateFormat monthFormat = new SimpleDateFormat("MMM-yyyy", Locale.ENGLISH);
		String formattedMonthYear = monthFormat.format(cal.getTime());

		String finalDate = dayWithSuffix + "-" + formattedMonthYear;
		System.out.println("Date as been Selected"+ finalDate); // Output: 19th-Apr-2025

		Thread.sleep(3000);
		String selectedDate=driver.findElement(By.xpath("//input[@class='DayPickerInput input' and not(contains(@placeholder, 'Return Date (Optional)'))]")).getAttribute("value");
		System.out.println(selectedDate); // Output: 19th-Apr-2025

		Assert.assertEquals(finalDate, selectedDate,"Date had been Modification is Successful");
		System.out.println("Date as been Selected"+ " "+finalDate +" and Validated on Home page"+" "+ selectedDate); // Output: 19th-Apr-2025

	}


	private String getDaySuffix(int day) {
		if (day >= 11 && day <= 13) return "th";
		switch (day % 10) {
		case 1: return "st";
		case 2: return "nd";
		case 3: return "rd";
		default: return "th";
		}
	}

	// Method to get day with suffix (st, nd, rd, th)
	private static String getDayWithSuffix(int day) {
		if (day >= 11 && day <= 13) {
			return day + "th";
		}
		switch (day % 10) {
		case 1: return day + "st";
		case 2: return day + "nd";
		case 3: return day + "rd";
		default: return day + "th";
		}
	}
	
	
	//Method to Verify Horizontal date picker is Displayed on Home Page.
	//Method to Verify Horizontal date picker is Displayed on Home Page.
    public void verifyHorizontalDatePickerIsDisplayed(Log Log,ScreenShots ScreenShots)
    {
        try {
        	Thread.sleep(4000); 
        	WebElement horizontalDatePicker=driver.findElement(By.xpath("//*[@class='main-container']/child::*[contains(@class,'date-scroller')]"));
            if(horizontalDatePicker.isDisplayed())
            {
                Log.ReportEvent("PASS", "Horizontal date picker displayed is Successful");
                ScreenShots.takeScreenShot1();
            }
            else {
                Log.ReportEvent("FAIL", "Horizontal date picker is Not displayed");
                ScreenShots.takeScreenShot1();
                Assert.fail();
            }
        }catch(Exception e){
            Log.ReportEvent("FAIL", "Horizontal date picker is Not displayed");
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
            Assert.fail();
        }

    }
    
    //Method to validate selected Horizontal date picker date is displayed
    public void verifySelectedHorizontalDatePickerIsDisplayed(Log Log,ScreenShots ScreenShots)
    {
        try {
            WebElement SelectedhorizontalDatePicker=driver.findElement(By.xpath("(//span[contains(@class, 'bold') and contains(@class, 'caption')])[1]"));
            if(SelectedhorizontalDatePicker.isDisplayed())
            {
                Log.ReportEvent("PASS", "Selected Horizontal date picker displayed is Successful");
                ScreenShots.takeScreenShot1();
            }
            else {
                Log.ReportEvent("FAIL", " Selected Horizontal date picker is Not displayed");
                ScreenShots.takeScreenShot1();
            }
        }catch(Exception e){
            Log.ReportEvent("FAIL", "Selected Horizontal date picker is Not displayed");
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
        }
        
    }

    //-------------------------------------------------------------------------------------------------------------

	                         //Method to Select flight based on Index
    public void selectFlightBasedOnIndex(int index) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll to bottom
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(1000);

        try {
            String xpath = "//*[contains(@class,'tg-flight-card ')]//button[text()='Select']";
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

            try {
                button.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", button);
            }

        } catch (TimeoutException e) {
            System.out.println("Select button not clickable for index: " + index);
        }
    }


//	                      //Method to Validate data after selection of Flight
//	public void validateDataAfterSelectingFlight()
//	{
//		String departureTime=driver.findElement(By.xpath("(//div[@class='owf-deptime']/h6)[1]")).getText();
//		String timeOnly=departureTime.split(" ")[0];
//		String arrivalTime=driver.findElement(By.xpath("(//div[@class='owf-arrtime']/h6)[1]")).getText();
//		String arrivalOnly=arrivalTime.split(" ")[0];
//
//		// Concatenate with dash
//		String finalTime = timeOnly.substring(0, 5) + "-" + arrivalOnly.substring(0, 5);
//		System.out.println("----------------------------------------------------------"); // Output: 16:00-02:15
//		System.out.println("Final Time Range: " + finalTime);
//		System.out.println("----------------------------------------------------------"); // Output: 16:00-02:15
//
//		String price=driver.findElement(By.xpath("(//div[@class='owf-price']/h6)[1]")).getText();
//		String priceOnly=price.split(" ")[1];
//		String bookingPrice=driver.findElement(By.xpath("//button[text()='Other Fares']/parent::div")).getText();
//		String bookingPriceOnly=bookingPrice.split(" ")[1];
//
//		String timingsOnly=driver.findElement(By.xpath("//button[text()='Flight Details']/preceding-sibling::p")).getText();
//		// Remove all whitespace characters (spaces, tabs, etc.)
//		String arrivalAndDeparture = timingsOnly.replaceAll("\\s+", "");
//
//
//		System.out.println("Arrival and Departure time "+arrivalAndDeparture); // Output: 16:00-02:15
//		System.out.println("----------------------------------------------------------"); // Output: 16:00-02:15      
//
//
//		Assert.assertEquals(priceOnly, bookingPriceOnly);
//		Assert.assertEquals(finalTime,arrivalAndDeparture);
//
//	}
//	
    //-------------------------------------------------------------------------------------------------------------

		                     	
	//Method to validate multiple airlines               
	
	public void selectMultipleAirlines(WebDriver driver, Log log, ScreenShots screenShots, String... airlines) {
	    for (String airline : airlines) {
	        System.out.println("Checking airline: " + airline);

	        try {
	            String xpath = "//div[@class='owf-carrier-info']";
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(0));
	            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

	            if (!checkbox.isSelected()) {
	                checkbox.click();
	                log.ReportEvent("PASS", "Selected: " + airline);
	                System.out.println(xpath);
	            } else {
	                log.ReportEvent("INFO", airline + " already selected.");
	                System.out.println("INFO: " + airline + " already selected.");
	            }
	        } catch (Exception e) {
	            log.ReportEvent("FAIL", "Could not select: " + airline);
	            screenShots.takeScreenShot1();
	            System.out.println("FAIL: Could not select " + airline);
	        }
	    }
	}


	//----------------------------------------------------------------------------------------------------
	                     
    
    //Method to Check Duration Time is Sorting In Result Screen
    public void validateDurationTimeSortFunctionalityOnResultScreen(Log Log, ScreenShots ScreenShots,String order) {
        try {
           ArrayList<String> flightsDurationData=new ArrayList();
           List<WebElement> airlineDurationCount=driver.findElements(By.xpath("//h6[@data-tgtotaljourneyduration]"));
           System.out.println(airlineDurationCount.size());

           for(WebElement airlineDurationList:airlineDurationCount)
           {
              String airlineDurationText=airlineDurationList.getText();
              System.out.println(airlineDurationText);
              flightsDurationData.add(airlineDurationText);
           }
           System.out.println(flightsDurationData);

           // Convert duration strings like "1h 0m" to total minutes
           List<Integer> timesInMinutes = new ArrayList<>();
           for (String duration : flightsDurationData) {
              int totalMinutes = 0;
              if (duration.contains("h")) {
                 String[] parts = duration.split("h");
                 totalMinutes += Integer.parseInt(parts[0].trim()) * 60;
                 if (parts.length > 1 && parts[1].contains("m")) {
                    totalMinutes += Integer.parseInt(parts[1].replace("m", "").trim());
                 }
              } else if (duration.contains("m")) {
                 totalMinutes += Integer.parseInt(duration.replace("m", "").trim());
              }
              timesInMinutes.add(totalMinutes);
              System.out.println("Converted to minutes: " + totalMinutes);
           }

           System.out.println(timesInMinutes);


           // Check if times are in ascending order
           boolean isDurationSorted = true;
           for (int i = 0; i < timesInMinutes.size() - 1; i++) {
              if(order.contentEquals("Ascending"))
              {
                 if (timesInMinutes.get(i) < timesInMinutes.get(i + 1)) {
                    isDurationSorted = false;
                    break;
                 }
              }else if(order.contentEquals("Descending")){
                 if (timesInMinutes.get(i) > timesInMinutes.get(i + 1)) {
                    isDurationSorted = false;
                    break;
                 }
              }

           }
           if(isDurationSorted=true)
           {
              Log.ReportEvent("PASS", "Flights Duration Time is Sorted Successful");
              ScreenShots.takeScreenShot1();
           }
           else
           {
              Log.ReportEvent("PASS", "Flights Duration Time is Not Sorted");
              ScreenShots.takeScreenShot1();
              Assert.fail();
           }

        } catch (Exception e) {
           e.printStackTrace();
           Log.ReportEvent("FAIL", "Flights Duration Time is Not Sorted");
           ScreenShots.takeScreenShot1();
           Assert.fail();
        }
    }

    //-----------------------------------------------------------------------------------------------------
    
  //Method to Click on Filters on Result Screen
    public void clickOnFiltersOnResultScreen(String filterName) throws InterruptedException {
        driver.findElement(By.xpath("//small[text()='"+filterName+"']")).click();
        Thread.sleep(3000);

    }
    
    
    
  //Method to Check Airline Sort Functionality on Result Screen
    public void validateAirlineSortFunctionalityOnResultScreen(Log Log, ScreenShots ScreenShots,String order) {
        try {
           ArrayList<String> flightsAirlineData=new ArrayList();
           List <WebElement> airlineArrivalCount=driver.findElements(By.xpath("//p[@data-tgcarrier]"));
           System.out.println(airlineArrivalCount.size());
           for(WebElement airLineNames:airlineArrivalCount)
           {
              String airlineText=airLineNames.getText();
              flightsAirlineData.add(airlineText);
              System.out.println(airlineText);
           }

           boolean isSorted = isSortedAlphabeticallyAirlines(flightsAirlineData,order);
           System.out.println("Is the list sorted in ascending order? " + isSorted);
           if(isSorted==true)
           {
              Log.ReportEvent("PASS", "Airlines are Sorted in Order is Successful");
              ScreenShots.takeScreenShot1();
           }
           else{
              Log.ReportEvent("FAIL", "Airlines are Not Sorted in order is Successful");
              ScreenShots.takeScreenShot1();
              Assert.fail();
           }

        } catch (Exception e) {
           Log.ReportEvent("FAIL", "Airlines are Not Sorted in order is Successful");
           ScreenShots.takeScreenShot1();
           Assert.fail();
        }
    }


    //Method to Check Airlines Sorted in Ascending or Descending
    public static boolean isSortedAlphabeticallyAirlines(ArrayList<String> list,String order) {
        for (int i = 0; i < list.size() - 1; i++) {
           String airlineText1 = extractFirstWord(list.get(i));
           String airlineText2 = extractFirstWord(list.get(i + 1));
           if(order.contentEquals("Ascending"))
           {
              if (airlineText1.compareToIgnoreCase(airlineText2) > 0) {
                 return false;  // not sorted
              }
           }
           else if(order.contentEquals("Descending"))
           {
              if (airlineText1.compareToIgnoreCase(airlineText2) < 0) {
                 return false;  // not sorted
              }
           }
        }
        return true;
    }
    // Extracts the first word before a space or parenthesis
    public static String extractFirstWord(String str) {
        str = str.trim();
        int endIndex = str.indexOf(' ');
        if (endIndex == -1 || str.indexOf('(') < endIndex && str.indexOf('(') != -1) {
           endIndex = str.indexOf('(');
        }
        return (endIndex != -1) ? str.substring(0, endIndex).trim() : str;
    }
    
    //------------------------------------------------------------------------------------------------------
    
 
  //Method to Check Price is Sorting In Result Screen
    
    public void validatePriceSortFunctionalityOnResultScreen(Log Log, ScreenShots ScreenShots,String order) {
        try {
           ArrayList<Integer> flightsPriceData=new ArrayList();
           List<WebElement> airlinePrices=driver.findElements(By.xpath("//span[@data-tgprice]"));
           for(WebElement airlinePrice:airlinePrices)
           {
              String price=airlinePrice.getText();
              String priceList=price.substring(2);
              String pricedata = priceList.replace(",", "");
              flightsPriceData.add(Integer.parseInt(pricedata));
           }
           System.out.println(flightsPriceData);

           // Check if times are in ascending order
           boolean isPriceSorted = true;
           for (int i = 0; i < flightsPriceData.size() - 1; i++) {
              if(order.contentEquals("Ascending"))
              {
                 if (flightsPriceData.get(i) > flightsPriceData.get(i + 1)) {
                    isPriceSorted = false;
                    break;
                 }
              }else if(order.contentEquals("Descending")){
                 if (flightsPriceData.get(i) < flightsPriceData.get(i + 1)) {
                    isPriceSorted = false;
                    break;
                 }
              }

           }
           if(isPriceSorted=true)
           {
              Log.ReportEvent("PASS", "Flights Price is Sorted Successful");
              ScreenShots.takeScreenShot1();
           }
           else
           {
              Log.ReportEvent("PASS", "Flights Price is Not Sorted");
              ScreenShots.takeScreenShot1();
              Assert.fail();
           }

        } catch (Exception e) {
           e.printStackTrace();
           Log.ReportEvent("FAIL", "Flights Price is Not Sorted");
           ScreenShots.takeScreenShot1();
           Assert.fail();
        }
    }

  //Method to Validate Price Range Of the Price Fliter
    public void verifyPriceRangeValuesOnResultScreen(Log Log, ScreenShots ScreenShots) {
        try {
            Thread.sleep(3000);

            // Get current slider values
            int min = Integer.parseInt(driver.findElement(By.xpath("//input[@data-index='0']")).getAttribute("aria-valuenow"));
            int max = Integer.parseInt(driver.findElement(By.xpath("//input[@data-index='1']")).getAttribute("aria-valuenow"));

            System.out.println("Slider Min: " + min);
            System.out.println("Slider Max: " + max);

            // Get all price elements
            List<WebElement> priceElements = driver.findElements(By.xpath(
                "//span[@data-tgprice]"
            ));

            boolean allPricesInRange = true;

            for (WebElement priceElement : priceElements) {
                String rawPrice = priceElement.getText(); // e.g., "₹ 52,354" or "INR 45,678"
                String cleanPrice = rawPrice.replaceAll("[^0-9]", ""); // Keep digits only
                try {
                    int price = Integer.parseInt(cleanPrice);
                    if (price >= min && price <= max) {
                        System.out.println("Price within range: ₹" + price);
                    } else {
                        System.out.println("❌ Price out of range: ₹" + price);
                        allPricesInRange = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Skipping invalid price: " + rawPrice);
                    allPricesInRange = false;
                }
            }

            if (allPricesInRange) {
                Log.ReportEvent("PASS", "✅ All flight prices are within the range ₹" + min + " - ₹" + max);
            } else {
                Log.ReportEvent("FAIL", "❌ Some flight prices are outside the range ₹" + min + " - ₹" + max);
                Assert.fail("Some prices are out of range.");
            }

            ScreenShots.takeScreenShot1();

        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Exception during price validation: " + e.getMessage());
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
            Assert.fail();
        }
    }
    //-------------------------------------------------------------------------------------------------------------
    
    public void refreshpage(Log Log,ScreenShots ScreenShots) {
    	driver.navigate().refresh();
        ScreenShots.takeScreenShot1(); 	
    }
	
    //-------------------------------------------------------------------------------------------------------------

    @FindBy(xpath = "//legend[text()='AIRLINES']/parent::div//*[@data-testid='CheckBoxOutlineBlankIcon']")
    List<WebElement> airlinemultipleCheckboxes;

    /*public void clickAirlineCheckboxes(String... airlineNames) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));

        for (String airlineName : airlineNames) {
            try {
                String selectedAirline = "//li[.//span[text()='" + airlineName + "']]//input[@type='checkbox']";

            	String xpath = "//li[.//span[text()='" + airlineName + "']]//span[contains(@class,'MuiCheckbox-root')]";
                WebElement checkboxContainer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                WebElement checkboxInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(selectedAirline)));

                // Scroll into view
//                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkboxContainer);

                // Only click if the current state doesn't match the desired state
                
                    checkboxInput.click();
                

                System.out.println("Clicked checkbox for: " + airlineName);
            } catch (Exception e) {
                System.out.println("Could not find/click checkbox for: " + airlineName);
            }
        }
    }*/
    
  //Method to Select Airlines on Result Screen
    public void clickAirlineCheckboxes(String... airlineNames) {
        for (String airlineName : airlineNames) {
           try {
              WebElement airline=driver.findElement(By.xpath("//legend[text()='AIRLINES']"));
              Actions move= new Actions(driver);
              move.moveToElement(airline).perform();
              Thread.sleep(1000);
              driver.findElement(By.xpath("//legend[text()='AIRLINES']/parent::div//span[normalize-space(text())='"+airlineName+"']/parent::div/parent::li//input")).click();
              System.out.println("Clicked checkbox for: " + airlineName);
              Thread.sleep(1000);
           } catch (Exception e) {
              System.out.println("Could not find/click checkbox for: " + airlineName);
              Assert.fail();

           }
        }

    }

    
    //-------------------------------------------------------------------------------------------------------------

  //Method to Validate Airline List  one way
    public void validateAirLinesList(Log Log, ScreenShots ScreenShots, String... airlinename) {
        try {
            // Get all airlines displayed on the result page
            List<WebElement> airlineElements = driver.findElements(By.xpath("//p[@data-tgcarrier]"));
            List<String> displayedAirlines = new ArrayList<>();

            for (WebElement element : airlineElements) {
                String airline = element.getText().trim();
                displayedAirlines.add(airline);
            }

            // Flag to track overall result
            boolean allMatch = true;

            // Check each airline passed to the method
            for (String expectedAirline : airlinename) {
                if (displayedAirlines.contains(expectedAirline.trim())) {
                    Log.ReportEvent("PASS", "Expected airline is showing: " + expectedAirline);
                } else {
                    Log.ReportEvent("FAIL", "Expected airline NOT found: " + expectedAirline);
                    allMatch = false;
                }
            }

            if (allMatch) {
                Log.ReportEvent("PASS", "All selected airlines are correctly shown in the results.");
            } else {
                Log.ReportEvent("FAIL", "Some selected airlines are missing in the results.");
            }

            ScreenShots.takeScreenShot1();

            // Print all displayed airlines
            System.out.println("Displayed Airlines:");
            displayedAirlines.forEach(System.out::println);

        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Exception occurred while validating airlines list: " + e.getMessage());
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
        }
    }
    
    //-------------------------------------------------------------------------------------------------------------

  //select and deselect  single checkbox
    public void deselctsingleAirlineCheckbox(String airlineName, boolean select) {
        String xpath = "//li[.//span[text()='" + airlineName + "']]//input[@type='checkbox']";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement checkboxInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkboxInput);

        boolean isChecked = checkboxInput.isSelected();

        // Only click if the current state doesn't match the desired state
        if (select && !isChecked || !select && isChecked) {
            checkboxInput.click();
        }
    }
    
    //click on swap button
    public void clickswap() {
    	driver.findElement(By.xpath("//button[@title='swap airports']")).click();
    }
    
    //method to validate swap
    public void validateAirportSwap(Log Log, ScreenShots ScreenShots) {
        String fromBefore = driver.findElement(By.xpath("(//div[@class='tg-select__single-value css-1dimb5e-singleValue'])[1]")).getText();
        String toBefore = driver.findElement(By.xpath("(//div[@class='tg-select__single-value css-1dimb5e-singleValue'])[2]")).getText();

        driver.findElement(By.xpath("//button[@title='swap airports']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
            By.xpath("(//div[@class='tg-select__single-value css-1dimb5e-singleValue'])[1]"),
            toBefore));

        String fromAfter = driver.findElement(By.xpath("(//div[@class='tg-select__single-value css-1dimb5e-singleValue'])[1]")).getText();
        String toAfter = driver.findElement(By.xpath("(//div[@class='tg-select__single-value css-1dimb5e-singleValue'])[2]")).getText();

        if (fromBefore.equals(toAfter) && toBefore.equals(fromAfter)) {
            Log.ReportEvent("PASS", "Swap successful");
                ScreenShots.takeScreenShot1();

        } else {
        	Log.ReportEvent("FAIL", "Swap Failed");
            ScreenShots.takeScreenShot1();
            
        }
    }
        
     //-------------------------------------------------------------------------------------------------------------
    
  //Method to Check Departure Time is Sorting In Result Screen
    public void validateDepartureTimeSortFunctionalityOnResultScreen(Log Log, ScreenShots ScreenShots,String order) {
        try {
           ArrayList<String> flightsDepartureData=new ArrayList();
           List <WebElement> airlineDepartureCount=driver.findElements(By.xpath("//h6[@data-tgdeptime]"));
           System.out.println(airlineDepartureCount.size());
           for(WebElement airlineDepartureTime:airlineDepartureCount)
           {
              String airlineDepartureText=airlineDepartureTime.getText();
              flightsDepartureData.add(airlineDepartureText);
              System.out.println(airlineDepartureText);
           }
           System.out.println(flightsDepartureData);

           // Convert time strings to minutes
           List<Integer> timesInMinutes = new ArrayList<>();
           for (String time : flightsDepartureData) {
              String[] parts = time.split(":");
              int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
              timesInMinutes.add(minutes);
           }
           System.out.println(timesInMinutes);

           // Check if times are in ascending order
           boolean isDepartureSorted = true;
           for (int i = 0; i < timesInMinutes.size() - 1; i++) {
              if(order.contentEquals("Ascending"))
              {
                 if (timesInMinutes.get(i) < timesInMinutes.get(i + 1)) {
                    isDepartureSorted = false;
                    break;
                 }
              }else if(order.contentEquals("Descending")){
                 if (timesInMinutes.get(i) > timesInMinutes.get(i + 1)) {
                    isDepartureSorted = false;
                    break;
                 }
              }

           }
           if(isDepartureSorted=true)
           {
              Log.ReportEvent("PASS", "Flights Departure Time is Sorted Successful");
              ScreenShots.takeScreenShot1();
           }
           else
           {
              Log.ReportEvent("PASS", "Flights Departure Time is Not Sorted");
              ScreenShots.takeScreenShot1();
              Assert.fail();
           }

        } catch (Exception e) {
           e.printStackTrace();
           Log.ReportEvent("FAIL", "Flights Departure Time is Not Sorted");
           ScreenShots.takeScreenShot1();
           Assert.fail();
        }
    }

    //-----------------------------------------------------------------------------------------------------
    
  //Method to Check Arrival Time is Sorting In Result Screen
    public void validateArrivalTimeSortFunctionalityOnResultScreen(Log Log, ScreenShots ScreenShots,String order) {
        try {
           ArrayList<String> flightsArrivalData=new ArrayList();
           List<WebElement>airlineArrivalCount=driver.findElements(By.xpath("//h6[@data-tgarrivaltime]"));
           for(WebElement airlineArrivalList: airlineArrivalCount)
           {
              String airlineArrivalText=airlineArrivalList.getText();
              System.out.println(airlineArrivalText);
              flightsArrivalData.add(airlineArrivalText);
           }
           System.out.println(flightsArrivalData);

           // Convert time strings to minutes
           List<Integer> timesInMinutes = new ArrayList<>();
           for (String time : flightsArrivalData) {
              String[] parts = time.split(":");
              int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
              timesInMinutes.add(minutes);
           }

           System.out.println(timesInMinutes);


           // Check if times are in ascending order
           boolean isArrivalSorted = true;
           for (int i = 0; i < timesInMinutes.size() - 1; i++) {
              if(order.contentEquals("Ascending"))
              {
                 if (timesInMinutes.get(i) < timesInMinutes.get(i + 1)) {
                    isArrivalSorted = false;
                    break;
                 }
              }else if(order.contentEquals("Descending")){
                 if (timesInMinutes.get(i) > timesInMinutes.get(i + 1)) {
                    isArrivalSorted = false;
                    break;
                 }
              }

           }
           if(isArrivalSorted=true)
           {
              Log.ReportEvent("PASS", "Flights Arrival Time is Sorted Successful");
              ScreenShots.takeScreenShot1();
           }
           else
           {
              Log.ReportEvent("PASS", "Flights Arrival Time is Not Sorted");
              ScreenShots.takeScreenShot1();
              Assert.fail();
           }

        } catch (Exception e) {
           e.printStackTrace();
           Log.ReportEvent("FAIL", "Flights Arrival Time is Not Sorted");
           ScreenShots.takeScreenShot1();
           Assert.fail();
        }
    }
    
    
  
//Method to click for layover Airports
    
   /* public void clickLayoverAirports1(String... layoverAirports) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (String airport : layoverAirports) {
            try {
                // XPath using contains() for better flexibility
            	String xpath = String.format(
            		    "//legend[contains(text(),'LAYOVER AIRPORTS')]/following-sibling::div//span[contains(text(),'%s')]/ancestor::li//small[text()='Only']",
            		    airport);

                WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

                // Scroll to the checkbox
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", checkbox);
                checkbox.click();

                System.out.println("Clicked checkbox for: " + airport);
            } catch (Exception e) {
                System.err.println("Could not find/click checkbox for: " + airport);
                e.printStackTrace();
            }
        }
    }*/
    
    /*public void clickLayoverAirport(String airport) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            String checkboxXpath = String.format(
                "//legend[contains(text(),'LAYOVER AIRPORTS')]/following-sibling::div//span[contains(text(),'%s')]/ancestor::li//small[text()='Only']",
                airport
            );

            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(checkboxXpath)));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", checkbox);
            checkbox.click();

            System.out.println("Successfully clicked checkbox for: " + airport);
        } catch (Exception e) {
            System.err.println("Failed to click checkbox for: " + airport);
            e.printStackTrace();
            throw e; // Re-throw to handle in calling method if needed
        }
    }*/
    
    public void clickLayoverAirport(String airport) {
        String checkboxXpath = String.format(
            "//legend[contains(text(),'LAYOVER AIRPORTS')]/following-sibling::div//span[contains(text(),'%s')]/ancestor::li//small[text()='Only']",
            airport
        );

        WebElement checkbox = driver.findElement(By.xpath(checkboxXpath));
        checkbox.click();

        System.out.println("Clicked checkbox for: " + airport);
    }

    
    //Method to validate layover airports 
    public boolean validateSelectedAirport(String airport,Log Log, ScreenShots ScreenShots) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Extract the first word (e.g., "Mumbai" from "Mumbai (BOM)")
            String searchTerm = airport.split("\\s+")[0]; 
            String validationXpath = String.format(
                "//strong[@data-tglayoverairport and contains(text(),'%s')]", 
                searchTerm
            );

            WebElement validatedAirport = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(validationXpath))
            );
            
            System.out.println("Validation PASSED: '" + airport + "' found in results.");
            Log.ReportEvent("PASS", "Successfully Displayed Selected Layover Airport"+ airport);
            ScreenShots.takeScreenShot1();
            return true;
        } catch (Exception e) {
            System.err.println("Validation FAILED: '" + airport + "' not found in results.");
            Log.ReportEvent("FAIL", "Not Displayed Selected Layover Airport"+ airport);
            ScreenShots.takeScreenShot1();
            return false;
        }
    }
    //================================================================================
    
  //Method to select Flight Based On Index From And Return
    public void selectFlightBasedOnIndexFromAndReturn(int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        try {
            // Construct XPath for the 'From' flight selection
            String fromXPath = "(//div[contains(@class, 'round-trip-from-results')]//div[contains(@class, 'MuiCardContent-root') and contains(@class, 'round-trip-card') and contains(@class, 'css-1qw96cp')])[" + index + "]";
            WebElement fromDiv = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(fromXPath)));

            // Perform actions on the 'From' flight element
            fromDiv.click();

            // Construct XPath for the 'Return' flight selection
            String returnXPath = "(//div[contains(@class, 'round-trip-to-results')]//div[contains(@class, 'MuiCardContent-root') and contains(@class, 'round-trip-card') and contains(@class, 'css-1qw96cp')])[" + index + "]";
            WebElement returnDiv = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(returnXPath)));

            // Perform actions on the 'Return' flight element
            returnDiv.click();

            // Click the 'Continue' button
            WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Continue']")));
            continueButton.click();

        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + e.getMessage());
        } catch (ElementNotInteractableException e) {
            System.out.println("Element not interactable: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    //==============================================================================
    
    //Method to validate types of fare for one way
    
    
    public String selectFaretype(int index, String fareTypeArg, Log Log, ScreenShots ScreenShots) throws InterruptedException {
        boolean fareTypeFound = false;
        String selectedFarePrice = "";

        Thread.sleep(3000);
        String xpathExpression = "(//button[text()='Select'])[" + index + "]";
        WebElement button = driver.findElement(By.xpath(xpathExpression));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
        Thread.sleep(1000);
        button.click();

        List<WebElement> allFareTypes = driver.findElements(By.xpath("//div[@data-tgflfaretype]"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (WebElement fareType : allFareTypes) {
            String fareTypeText = fareType.getText().trim();

            if (fareTypeText.contains(fareTypeArg)) {
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fareType);

                WebElement priceElem = fareType.findElement(By.xpath("./following-sibling::div[@data-tgflfare]"));
                wait.until(ExpectedConditions.visibilityOf(priceElem));

                String priceText = priceElem.getText().trim();
                selectedFarePrice = priceText.replaceAll("[^0-9]", ""); 

                // Continue button for that fare type
                String continueBtnXPath = "//div[@data-tgflfaretype][normalize-space()='" + fareTypeText + "']/parent::div/parent::div//button[2]";
                WebElement continueButton = driver.findElement(By.xpath(continueBtnXPath));
                wait.until(ExpectedConditions.elementToBeClickable(continueButton));
                ScreenShots.takeScreenShot1();
                js.executeScript("arguments[0].click();", continueButton);

                Log.ReportEvent("PASS", "User selected FareType: " + fareTypeText + " | Price: ₹" + priceText);
                fareTypeFound = true;
                break;
            }
        }

        if (!fareTypeFound) {
            if (allFareTypes.size() > 1) {
                WebElement fallbackFare = allFareTypes.get(1);
                String fallbackFareText = fallbackFare.getText().trim();
                js.executeScript("arguments[0].scrollIntoView(true);", fallbackFare);

                WebElement priceElem = fallbackFare.findElement(By.xpath("./following-sibling::div[@data-tgflfare]"));
                wait.until(ExpectedConditions.visibilityOf(priceElem));
                String priceText = priceElem.getText().trim();
                selectedFarePrice = priceText.replaceAll("[^0-9]", "");

                String fallbackXPath = "//div[@data-tgflfaretype][normalize-space()='" + fallbackFareText + "']/parent::div/parent::div//button[2]";
                WebElement fallbackBtn = driver.findElement(By.xpath(fallbackXPath));
                wait.until(ExpectedConditions.elementToBeClickable(fallbackBtn));
                fallbackBtn.click();

                Log.ReportEvent("PASS", "Fallback FareType selected: " + fallbackFareText + " | Price: ₹" + priceText);
            } else {
                Log.ReportEvent("FAIL", "No FareTypes found to click.");
            }
        }

        return selectedFarePrice; 
    }

                                              //or
    public String selectFaretypeONEWAY(int index, String fareTypeArg, Log Log, ScreenShots ScreenShots) throws InterruptedException {
        boolean fareTypeFound = false;
        String selectedFarePrice = "";

        Thread.sleep(3000);
        String xpathExpression = "(//button[text()='Select'])[" + index + "]";
        WebElement button = driver.findElement(By.xpath(xpathExpression));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
        Thread.sleep(1000);
        button.click();

        List<WebElement> allFareTypes = driver.findElements(By.xpath("//div[@data-tgflfaretype]"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (WebElement fareType : allFareTypes) {
            String fareTypeText = fareType.getText().trim();

            if (fareTypeText.contains(fareTypeArg)) {
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fareType);

                WebElement priceElem = fareType.findElement(By.xpath("./following-sibling::div[@data-tgflfare]"));
                wait.until(ExpectedConditions.visibilityOf(priceElem));

                String priceText = priceElem.getText().trim();
                selectedFarePrice = priceText.replaceAll("[^0-9]", "");  // ✅ Extract digits only

                // Click Continue
                String continueBtnXPath = "//div[@data-tgflfaretype][normalize-space()='" + fareTypeText + "']/parent::div/parent::div//button[2]";
                WebElement continueButton = driver.findElement(By.xpath(continueBtnXPath));
                wait.until(ExpectedConditions.elementToBeClickable(continueButton));
                ScreenShots.takeScreenShot1();
                js.executeScript("arguments[0].click();", continueButton);

                Log.ReportEvent("PASS", "User selected FareType: " + fareTypeText + " | Price: ₹" + selectedFarePrice);
                fareTypeFound = true;
                break;
            }
        }

        if (!fareTypeFound && allFareTypes.size() > 1) {
            WebElement fallbackFare = allFareTypes.get(1);
            js.executeScript("arguments[0].scrollIntoView(true);", fallbackFare);

            WebElement priceElem = fallbackFare.findElement(By.xpath("./following-sibling::div[@data-tgflfare]"));
            wait.until(ExpectedConditions.visibilityOf(priceElem));
            String priceText = priceElem.getText().trim();
            selectedFarePrice = priceText.replaceAll("[^0-9]", "");  // ✅ Extract digits only

            WebElement fallbackBtn = fallbackFare.findElement(By.xpath("./parent::div/parent::div//button[2]"));
            wait.until(ExpectedConditions.elementToBeClickable(fallbackBtn));
            fallbackBtn.click();

            Log.ReportEvent("PASS", "Fallback FareType selected | Price: ₹" + selectedFarePrice);
        }

        return selectedFarePrice;  // ✅ Only numeric price like "13196"
    }


    
    public void selectFareAndValidate(int index, String fareTypeArg, Log Log, ScreenShots ScreenShots) throws InterruptedException {
        try {
            boolean fareTypeFound = false;
            String selectedFarePrice = "";

            Thread.sleep(3000);
            String xpathExpression = "(//button[text()='Select'])[" + index + "]";
            WebElement button = driver.findElement(By.xpath(xpathExpression));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
            Thread.sleep(1000);
            button.click();

            List<WebElement> allFareTypes = driver.findElements(By.cssSelector("div.fare-type.tg-fare-type"));

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            for (WebElement fareType : allFareTypes) {
                String fareTypeText = fareType.getText().trim();

                if (fareTypeText.contains(fareTypeArg)) {
                    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fareType);

                    WebElement priceElem = fareType.findElement(By.xpath("./following-sibling::div[@data-tgflfare]"));
                    wait.until(ExpectedConditions.visibilityOf(priceElem));

                    String priceText = priceElem.getText().trim();
                    selectedFarePrice = priceText.replaceAll("[^0-9]", ""); 

                    // Click Continue
                    String continueBtnXPath = "//div[@data-tgflfaretype][normalize-space()='" + fareTypeText + "']/parent::div/parent::div//button[2]";
                    WebElement continueButton = driver.findElement(By.xpath(continueBtnXPath));
                    wait.until(ExpectedConditions.elementToBeClickable(continueButton));
                    ScreenShots.takeScreenShot1();
                    js.executeScript("arguments[0].click();", continueButton);

                    Log.ReportEvent("PASS", "User selected FareType: " + fareTypeText + " | Price: ₹" + selectedFarePrice);
                    fareTypeFound = true;
                    break;
                }
            }

            // If FareType not found, select fallback (2nd one)
            if (!fareTypeFound && allFareTypes.size() > 1) {
                WebElement fallbackFare = allFareTypes.get(1);
                js.executeScript("arguments[0].scrollIntoView(true);", fallbackFare);

                WebElement priceElem = fallbackFare.findElement(By.xpath("./following-sibling::div[@data-tgflfare]"));
                wait.until(ExpectedConditions.visibilityOf(priceElem));
                String priceText = priceElem.getText().trim();
                selectedFarePrice = priceText.replaceAll("[^0-9]", "");

                WebElement fallbackBtn = fallbackFare.findElement(By.xpath("./parent::div/parent::div//button[2]"));
                wait.until(ExpectedConditions.elementToBeClickable(fallbackBtn));
                fallbackBtn.click();

                Log.ReportEvent("PASS", "Fallback FareType selected | Price: ₹" + selectedFarePrice);
            }

            Thread.sleep(3000);
            reasonForSelectionPopUp();
            Thread.sleep(3000);
            //  Wait for Booking Page and validate price
            WebDriverWait waitBooking = new WebDriverWait(driver, Duration.ofSeconds(60));
            WebElement bookingPriceElement = waitBooking.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[span[normalize-space(text())='Grand Total']]/h6")
            ));
            String bookingPriceText = bookingPriceElement.getText().trim();
            String bookingPagePrice = bookingPriceText.replaceAll("[^0-9]", "");

            Log.ReportEvent("Info", "Booking Page Grand Total (numeric): " + bookingPagePrice);

            if (selectedFarePrice.equals(bookingPagePrice)) {
                Log.ReportEvent("PASS", "Fare price matched! Selected: ₹" + selectedFarePrice + ", Booking: ₹" + bookingPagePrice);
            } else {
                Log.ReportEvent("FAIL", "Fare price mismatch! Selected: ₹" + selectedFarePrice + ", Booking: ₹" + bookingPagePrice);
                ScreenShots.takeScreenShot1();
            }

        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Exception during fare selection or validation: " + e.getMessage());
            ScreenShots.takeScreenShot1();
        }
    }


    //============================================================================================================
    
    
    public String selectFaretype1(String fareTypeArg, Log Log, ScreenShots ScreenShots) throws InterruptedException {
        boolean fareTypeFound = false;
        String selectedFarePrice = "";

        Thread.sleep(3000);

        List<WebElement> allFareTypes = driver.findElements(By.xpath("//div[@data-tgflfaretype]"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (WebElement fareType : allFareTypes) {
            String fareTypeText = fareType.getText().trim();

            if (fareTypeText.contains(fareTypeArg)) {
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fareType);

                // Get price using following sibling
                WebElement priceElem = fareType.findElement(By.xpath("./following-sibling::div[@data-tgflfare]"));
                wait.until(ExpectedConditions.visibilityOf(priceElem));

                String priceText = priceElem.getText().trim();
                selectedFarePrice = priceText.replaceAll("[^0-9]", "");

                // Get Continue button relative to current fareType element
                WebElement continueButton = fareType.findElement(By.xpath("../../..//button[2]"));
                wait.until(ExpectedConditions.elementToBeClickable(continueButton));
                ScreenShots.takeScreenShot1();
                js.executeScript("arguments[0].click();", continueButton);

                Log.ReportEvent("PASS", "User selected FareType: " + fareTypeText + " | Price: ₹" + selectedFarePrice);
                System.out.println("User selected FareType: " + fareTypeText);
                fareTypeFound = true;
                break;
            }
        }

        // Fallback if no match
        if (!fareTypeFound) {
            if (allFareTypes.size() > 1) {
                WebElement fallbackFare = allFareTypes.get(1);
                js.executeScript("arguments[0].scrollIntoView(true);", fallbackFare);
                String fallbackFareText = fallbackFare.getText().trim();

                WebElement priceElem = fallbackFare.findElement(By.xpath("./following-sibling::div[@data-tgflfare]"));
                wait.until(ExpectedConditions.visibilityOf(priceElem));
                selectedFarePrice = priceElem.getText().replaceAll("[^0-9]", "");

                WebElement fallbackBtn = fallbackFare.findElement(By.xpath("../../..//button[2]"));
                wait.until(ExpectedConditions.elementToBeClickable(fallbackBtn));
                js.executeScript("arguments[0].click();", fallbackBtn);

                Log.ReportEvent("PASS", "Fallback FareType selected: " + fallbackFareText + " | Price: ₹" + selectedFarePrice);
            } else {
                Log.ReportEvent("FAIL", "No FareTypes found to click.");
            }
        }

        return selectedFarePrice;
    }

    //-----------------------------------------------------------------
    
    public void selectReasonAndProceed(String reasonValue, Log Log, ScreenShots ScreenShots) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // XPath for the label text dynamically
            String labelXPath = "//h2[text()='Reason for Selection']/following::label[normalize-space(.)='" + reasonValue + "']";

            // Wait for label to be clickable
            WebElement reasonLabel = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(labelXPath)));

            // Scroll to label and click it (which should toggle the input)
            js.executeScript("arguments[0].scrollIntoView(true);", reasonLabel);
            reasonLabel.click();

            Log.ReportEvent("PASS", "Clicked on Reason label: " + reasonValue);
            ScreenShots.takeScreenShot1();

            // Click Proceed to Booking
            WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Proceed to Booking']")));
            js.executeScript("arguments[0].scrollIntoView(true);", proceedButton);
            proceedButton.click();

            Log.ReportEvent("PASS", "Clicked on 'Proceed to Booking'");
            ScreenShots.takeScreenShot1();

        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Failed to select reason or proceed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //0r
    
  //Method to close reason For Selection PopUp
    public void reasonForSelectionPopUp() throws InterruptedException {
        String value = "Personal Preference";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            Thread.sleep(8000);
        //     WebElement popup=driver.findElement(By.xpath("//h2[@id='alert-dialog-title']"));
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[@id='alert-dialog-title']")
        ));

            if (popup.isDisplayed()) {
                WebElement reasonOption = driver.findElement(
                    By.xpath("//span[text()='" + value + "']//parent::label")
                );
                reasonOption.click();
                //click on Proceed to Booking
                driver.findElement(By.xpath("//button[text()='Proceed to Booking']")).click();
                Thread.sleep(3000);
                //click on Continue button
//                driver.findElement(By.xpath("//div[@class='bottom-container-1']//button[text()='Continue']")).click();
            }

        } catch (TimeoutException e) {
            System.out.println("Popup did not appear in time.");
        }
    }

    
    //============================================================================
    
    //Method to valiadte types of fare price till booking page  for one way 
    
    public String getSelectedFarePrice(String fareTypeArg) {
        String selectedPrice = "";

        try {
            // Get all fare types
            List<WebElement> allFareTypes = driver.findElements(By.xpath("//div[@data-tgflfaretype]"));

            for (WebElement fareType : allFareTypes) {
                String fareTypeText = fareType.getText().trim();

                if (fareTypeText.contains(fareTypeArg)) {
                    // Get the fare price using your original XPath — do NOT change
                    WebElement priceElement = fareType.findElement(By.xpath("./following-sibling::div[@data-tgflfare]"));
                    selectedPrice = priceElement.getText().replaceAll("[^0-9]", ""); // Clean price
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching selected fare price: " + e.getMessage());
        }

        return selectedPrice;
    }

    //===========================================================================================
  
    public void validateFareWithBookingPage(String expectedFarePrice, Log Log, ScreenShots ScreenShots) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            WebElement bookingPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[span[normalize-space(text())='Grand Total']]/h6")
            ));

            String bookingPagePriceText = bookingPriceElement.getText().trim();
            String bookingPagePrice = bookingPagePriceText.replaceAll("[^0-9]", "");  // ✅ Remove ₹ and commas

            Log.ReportEvent("Info", "Booking Page Grand Total (numeric): " + bookingPagePrice);

            if (expectedFarePrice.equals(bookingPagePrice)) {
                Log.ReportEvent("PASS", "✅ Fare price matched! Selected: ₹" + expectedFarePrice + ", Booking: ₹" + bookingPagePrice);
            } else {
                Log.ReportEvent("FAIL", "❌ Fare price mismatch! Selected: ₹" + expectedFarePrice + ", Booking: ₹" + bookingPagePrice);
                ScreenShots.takeScreenShot1();
            }

        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Exception while validating fare price: " + e.getMessage());
            ScreenShots.takeScreenShot1();
        }
    }


    //===========================================================================================
    
    //Method for getting all the fares
    
    	public void printAllFareTypes(int index,Log Log,ScreenShots ScreenShots) {
    	    try {
    	    	 Thread.sleep(3000);
    	         String xpathExpression = "(//button[text()='Select'])[" + index + "]";
    	         WebElement button = driver.findElement(By.xpath(xpathExpression));
    	         JavascriptExecutor js = (JavascriptExecutor) driver;
    	         js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
    	         Thread.sleep(1000);
    	         button.click();
    	    	
    	    	
    	         List<WebElement> fareTypes = driver.findElements(By.cssSelector("div.fare-type.tg-fare-type"));

	            ScreenShots.takeScreenShot1();

    	        int count = fareTypes.size();

    	        System.out.println("Total Fare Types Found: " + count);
    	        Log.ReportEvent("INFO", "Total Fare Types Found: " + count);


    	        for (int i = 0; i < count; i++) {
    	            String text = fareTypes.get(i).getText().trim();
    	            System.out.println("Fare Type " + (i + 1) + ": " + text);
    	            Log.ReportEvent("INFO", "Fare Type " + (i + 1) + ": " + text);

    	        }


    	    } catch (Exception e) {
    	        Log.ReportEvent("FAIL", "Error while retrieving fare types: " + e.getMessage());
    	        e.printStackTrace();
    	    }
    	}

  //-------------------------------------------------------------------------------------------------------
    	
  public void changeSelectionButton() {
	  driver.findElement(By.xpath("//button[normalize-space(text())='Change Selection']")).click();
  }
    	
  //===========================================================================================

 //Method to validate change airport location from result page to booking page
  
  public String[] getLocationCodesFromResultPage(Log Log, ScreenShots ScreenShots, int index) throws InterruptedException {
	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

	  try {
//	      String xpathExpression = "(//button[normalize-space()='Select'])[" + index + "]";
//	      WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
//
//	      JavascriptExecutor js = (JavascriptExecutor) driver;
//	      js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
//	      Thread.sleep(1000); // Optional, only if necessary
//	      button.click();

	        int fromDestIndex = 1;
	        String fromStopType = "";

	        WebElement fromStopElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("(//span[@data-tgnumstops])[" + index + "]")));
	        String fromStopCount = fromStopElement.getAttribute("data-tgnumstops").trim();

	        switch (fromStopCount) {
	            case "0":
	                fromStopType = "Nonstop";
	                fromDestIndex = 1;
	                break;
	            case "1":
	                fromStopType = "1 stops";
	                fromDestIndex = 2;
	                break;
	            case "2":
	                fromStopType = "2 stops";
	                fromDestIndex = 3;
	                break;
	            default:
	                Log.ReportEvent("FAIL", "Unknown stop type: " + fromStopCount);
	                ScreenShots.takeScreenShot1();
	                Assert.fail("Unrecognized stop type: " + fromStopCount);
	        }

	        WebElement originElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("(//h6[@data-tgfloriginairport])[1]")));
	        WebElement destElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("(//h6[@data-tgfldestinationairport])[" + fromDestIndex + "]")));

	        String originCode = originElem.getAttribute("data-tgfloriginairport").split(",")[0].trim().toUpperCase();
	        String destCode = destElem.getAttribute("data-tgfldestinationairport").split(",")[0].trim().toUpperCase();

	        System.out.println("Result page Origin: " + originCode);
	        System.out.println("Result page Destination: " + destCode);

	        Log.ReportEvent("INFO", "Result Page - Origin: " + originCode + ", Destination: " + destCode);
	        return new String[]{originCode, destCode};

	    } catch (Exception e) {
	        Log.ReportEvent("FAIL", "Error in result page location extraction: " + e.getMessage());
	        ScreenShots.takeScreenShot1();
	        Assert.fail("Exception in getLocationCodesFromResultPage");
	        return null;
	    }
	}

                                                    //or
  
  private String[] storedResultPageLocationCodes = null;
  private int storedResultPageDestIndex = 1;

  public void storeLocationCodesFromResultPage(Log Log, ScreenShots ScreenShots, int index) throws InterruptedException {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
      JavascriptExecutor js = (JavascriptExecutor) driver;

      try {
          // Click on the Select button for given index
          String xpathExpression = "(//button[normalize-space()='Select'])[" + index + "]";
          WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
          js.executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
          Thread.sleep(500);
          button.click();

          // Get stop count
          WebElement fromStopElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
              By.xpath("(//span[@data-tgnumstops])[" + index + "]")));
          String fromStopCount = fromStopElement.getAttribute("data-tgnumstops").trim();

          int fromDestIndex;
          switch (fromStopCount) {
              case "0":
                  fromDestIndex = 1;
                  break;
              case "1":
                  fromDestIndex = 2;
                  break;
              case "2":
                  fromDestIndex = 3;
                  break;
              default:
                  Log.ReportEvent("FAIL", "Unknown stop type: " + fromStopCount);
                  ScreenShots.takeScreenShot1();
                  Assert.fail("Unrecognized stop type: " + fromStopCount);
                  fromDestIndex = 1;
          }

          storedResultPageDestIndex = fromDestIndex;

          // Extract Origin and Destination
          WebElement originElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
              By.xpath("(//h6[@data-tgfloriginairport])[1]")));
          WebElement destElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
              By.xpath("(//h6[@data-tgfldestinationairport])[" + fromDestIndex + "]")));

          String originCode = originElem.getAttribute("data-tgfloriginairport").split(",")[0].trim().toUpperCase();
          String destCode = destElem.getAttribute("data-tgfldestinationairport").split(",")[0].trim().toUpperCase();

          Log.ReportEvent("INFO", "Result Page - Origin: " + originCode + ", Destination: " + destCode);

          storedResultPageLocationCodes = new String[]{originCode, destCode};

      } catch (Exception e) {
          Log.ReportEvent("FAIL", "Error while storing result page locations: " + e.getMessage());
          ScreenShots.takeScreenShot1();
          Assert.fail("Exception in storeLocationCodesFromResultPage");
      }
  }

  //===============================================================
  
  //Method to click on yes continue button for airport change
  
  public void clickYesContinueButton() {
	  driver.findElement(By.xpath("//button[text()='Yes, Continue']")).click();
  }
 
  //===========================================================================================

  //get location from airport change popup
  
  public String[] getLocationCodesFromChangePopup(Log Log, ScreenShots ScreenShots) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));

	        WebElement originCodeElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("(//typography//strong)[1]")));
	        WebElement destCodeElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("(//typography//strong)[2]")));

	        String originCode = originCodeElem.getText().trim().toUpperCase();
	        String destCode = destCodeElem.getText().trim().toUpperCase();

	        System.out.println("Airport change popup origin code: " + originCode);
	        System.out.println("Airport change popup destination code: " + destCode);

	        Log.ReportEvent("INFO", "Popup - Origin: " + originCode + ", Destination: " + destCode);
	        return new String[]{originCode, destCode};

	    } catch (Exception e) {
	        Log.ReportEvent("FAIL", "Error extracting popup airport codes: " + e.getMessage());
	        ScreenShots.takeScreenShot1();
	        Assert.fail("Exception in getLocationCodesFromChangePopup");
	        return null;
	    }
	}


  //===========================================================================================

    	
  public void validateLocationCodes(Log Log, ScreenShots ScreenShots, int index) throws InterruptedException {
	    String[] resultPageCodes = getLocationCodesFromResultPage(Log, ScreenShots, index);
	    String[] popupCodes = getLocationCodesFromChangePopup(Log, ScreenShots);

	    if (resultPageCodes == null || popupCodes == null) {
	        Log.ReportEvent("FAIL", "Validation skipped due to missing codes.");
	        return;
	    }

	    String resultOrigin = resultPageCodes[0];
	    String resultDest = resultPageCodes[1];
	    String popupOrigin = popupCodes[0];
	    String popupDest = popupCodes[1];

	    if (resultOrigin.equals(popupOrigin) && resultDest.equals(popupDest)) {
	        Log.ReportEvent("PASS", "Location validation successful. Origin: " + resultOrigin + ", Destination: " + resultDest);
	    } else {
	        Log.ReportEvent("FAIL", "Mismatch! Result Page - " + resultOrigin + " to " + resultDest +
	                                     " | Popup - " + popupOrigin + " to " + popupDest);
	        ScreenShots.takeScreenShot1();
	    }
	}
  
  //===========================================================================================

  
  public void selectFaretypeOneway(String fareTypeArg, Log Log, ScreenShots ScreenShots) throws InterruptedException {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    boolean fareTypeFound = false;

	    
	    Thread.sleep(3000); // Replace with explicit wait ideally

	    System.out.println("Looking for fare type: " + fareTypeArg);
	    List<WebElement> allFareType = driver.findElements(By.xpath("//div[@data-tgflfaretype]"));

	    for (WebElement fareType : allFareType) {
	        String fareTypeText = fareType.getText().trim();
	        System.out.println("Found fare: " + fareTypeText);

	        if (fareTypeText.contains(fareTypeArg)) {
	            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fareType);
	            Log.ReportEvent("PASS", "FareType Found: " + fareTypeText);

	            try {
	            	Thread.sleep(3000);
	            	// Get the continue button relative to current fareType element
	                WebElement fareTypeButton = fareType.findElement(By.xpath("./parent::div/parent::div//button[2]"));

	                // Wait and click
	                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
	                wait.until(ExpectedConditions.elementToBeClickable(fareTypeButton));

	                js.executeScript("arguments[0].click();", fareTypeButton);

	                Log.ReportEvent("PASS", "Clicked On FareType: " + fareTypeText);
	                System.out.println("Clicked On FareType: " + fareTypeText);
	                fareTypeFound = true;
	                break;
	            } catch (Exception e) {
	                System.out.println("Exception while clicking button: " + e.getMessage());
	                Log.ReportEvent("FAIL", "Failed to click on FareType button: " + e.getMessage());
	            }
	        }
	    }

	    if (!fareTypeFound) {
	        // Fallback
	        allFareType.get(1).click();
	        System.out.println("UserExpected FareType Not found hence clicked on FirstIndex FareType");
	        Log.ReportEvent("PASS", "UserExpected FareType Not found hence clicked on FirstIndex FareType");
	    }
	}

  //===========================================================================================

//Method to validate and get location details from booking page
  
  public void validateBookingPageLocationAgainstResult(Log Log, ScreenShots ScreenShots) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    try {
	        if (storedResultPageLocationCodes == null || storedResultPageLocationCodes.length != 2) {
	            Log.ReportEvent("FAIL", "No stored result page locations found for comparison.");
	            Assert.fail("Missing result page data.");
	        }

	        String expectedOrigin = storedResultPageLocationCodes[0];
	        String expectedDest = storedResultPageLocationCodes[1];
	        int destIndex = storedResultPageDestIndex;
	        
	        WebElement bookingOriginElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        	    By.xpath("(//h6[@data-tgdepartfloriginairport])[1]")));
	        	js.executeScript("arguments[0].scrollIntoView({block: 'center'});", bookingOriginElem);

	        	WebElement bookingDestElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        	    By.xpath("(//h6[@data-tgdepartfldestinationairport])[" + destIndex + "]")));
	        	js.executeScript("arguments[0].scrollIntoView({block: 'center'});", bookingDestElem);

	        	// Booking page origin/destination using airport codes
	        	String actualOrigin = bookingOriginElem.getAttribute("data-tgdepartfloriginairport").split(",")[0].trim().toUpperCase();
	        	String actualDest = bookingDestElem.getAttribute("data-tgdepartfldestinationairport").split(",")[0].trim().toUpperCase();

     
	        	
	         Log.ReportEvent("INFO", "Booking Page - Origin: " + actualOrigin + ", Destination: " + actualDest);

	        // Compare
	        if (expectedOrigin.equals(actualOrigin) && expectedDest.equals(actualDest)) {
	            Log.ReportEvent("PASS", "Locations match: " + expectedOrigin + " → " + expectedDest);
	        } else {
	            Log.ReportEvent("FAIL", "Mismatch. Result Page: " + expectedOrigin + " → " + expectedDest +
	                    " | Booking Page: " + actualOrigin + " → " + actualDest);
	            ScreenShots.takeScreenShot1();
	            Assert.fail("Origin/Destination mismatch.");
	        }

	    } catch (Exception e) {
	        Log.ReportEvent("FAIL", "Exception in booking validation: " + e.getMessage());
	        ScreenShots.takeScreenShot1();
	        Assert.fail("Exception in validateBookingPageLocationAgainstResult");
	    }
	}

  //===========================================================================================

  public String[] validateFlightResultInResultPage(String Fromlocation, String Tolocation, String journeyDate, Log Log, ScreenShots ScreenShots) {
      try {
          // Clean the day suffix (e.g. "2nd", "3rd", "4th") to just the number (e.g. "2", "3", "4")
          journeyDate = journeyDate.replaceAll("(st|nd|rd|th)", ""); // "2nd-Jul-2025" -> "2-Jul-2025"
          SimpleDateFormat inputFormat = new SimpleDateFormat("d-MMM-yyyy");
          SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

          Date parsedDate = inputFormat.parse(journeyDate);
          String formattedJourneyDate = outputFormat.format(parsedDate);

//          // Convert journeyDate from "27th-Jun-2025" to "2025-06-27"
//          SimpleDateFormat inputFormat = new SimpleDateFormat("dd'th'-MMM-yyyy");
//          SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
//          System.out.println(journeyDate);
//          Date parsedDate = inputFormat.parse(journeyDate);
//          String formattedJourneyDate = outputFormat.format(parsedDate);

          // Get stops text from page, e.g. "2 stops", "Nonstop"
          String stops = driver.findElement(By.xpath("(//span[@data-tgnumstops])[1]")).getText().trim();
          System.out.println("Stops raw string: '" + stops + "'");

          // Parse stops count from stops text
          int stopsCount = 0;
          if (stops.equalsIgnoreCase("Nonstop") || stops.equals("0")) {
              stopsCount = 0;
          } else {
              String stopsNumber = stops.replaceAll("[^0-9]", "");
              if (!stopsNumber.isEmpty()) {
                  stopsCount = Integer.parseInt(stopsNumber);
              }
          }

          int expectedLegs = stopsCount + 1;
          System.out.println("Stops count parsed: " + stopsCount + ", Expected legs: " + expectedLegs);

          // Get origin and destination elements
          List<WebElement> origins = driver.findElements(By.xpath("//h6[@data-tgfloriginairport]"));
          List<WebElement> destinations = driver.findElements(By.xpath("//h6[@data-tgfldestinationairport]"));

          // Validate count of legs
          if (origins.size() != expectedLegs || destinations.size() != expectedLegs) {
              Log.ReportEvent("FAIL", "Mismatch in flight legs! Expected: " + expectedLegs +
                  ", Found origins: " + origins.size() + ", destinations: " + destinations.size());
              ScreenShots.takeScreenShot1();
              Assert.fail("Flight legs count does not match stops count");
          }

          // Check connecting flights for matching airports
          for (int i = 0; i < expectedLegs; i++) {
              String originCode = origins.get(i).getAttribute("data-tgfloriginairport").trim();
              String destCode = destinations.get(i).getAttribute("data-tgfldestinationairport").trim();
              String nextOrigin = (i + 1 < origins.size()) ? origins.get(i + 1).getAttribute("data-tgfloriginairport").trim() : "N/A";

              if (!destCode.equalsIgnoreCase(nextOrigin) && !nextOrigin.equals("N/A")) {
                  Log.ReportEvent("FAIL", "❌ Mismatch: " + (i + 1) + "st destination '" + destCode +
                      "' does not match " + (i + 2) + "nd origin '" + nextOrigin + "'");
                  ScreenShots.takeScreenShot1();
                  Assert.fail("Connecting flight airports do not match");
              } else if (!nextOrigin.equals("N/A")) {
                  Log.ReportEvent("PASS", "✅ " + (i + 1) + "st destination '" + destCode +
                      "' matches " + (i + 2) + "nd origin '" + nextOrigin + "'");
              }

              Log.ReportEvent("INFO", "Flight leg " + (i + 1) + ": " + originCode + " -> " + destCode);
          }

          // Collect stop locations (intermediate destinations)
          List<String> stopLocations = new ArrayList<>();
          for (int i = 0; i < destinations.size() - 1; i++) {
              String stop = destinations.get(i).getAttribute("data-tgfldestinationairport").trim();
              stopLocations.add(stop);
          }
          String stopsString = stopLocations.isEmpty() ? "Nonstop" : String.join(", ", stopLocations);

          // Final origin and destination
          String finalDestination = destinations.get(destinations.size() - 1).getAttribute("data-tgfldestinationairport").trim();
          String originAirport = origins.get(0).getAttribute("data-tgfloriginairport").trim();

          // Get flight details
          String departureDate = driver.findElement(By.xpath("//span[@data-tgdepdate]")).getAttribute("data-tgdepdate");
      //    String flightcode = driver.findElement(By.xpath("(//p[@data-tgflightnumber])[1]")).getAttribute("data-tgflightnumber").trim();
          String flightcodeRaw = driver.findElement(By.xpath("//p[@data-tgflightnumber]"))
                  .getAttribute("data-tgflightnumber")
                  .trim();
          String flightcode = flightcodeRaw.replaceAll("[^A-Z0-9]", " ").replaceAll("\\s+", " ").trim();

          String departureTime = driver.findElement(By.id("deptime_0")).getText().trim();
          String arrivalTime = driver.findElement(By.xpath("(//h6[@data-tgarrivaltime])[1]")).getText();
          String price = driver.findElement(By.xpath("(//span[@data-tgprice])[1]")).getText();
          String flightName = driver.findElement(By.xpath("(//p[@data-tgcarrier])[1]")).getText();

          // Final validation
          if (originAirport.equalsIgnoreCase(Fromlocation) &&
              finalDestination.equalsIgnoreCase(Tolocation) &&
              departureDate.equals(formattedJourneyDate)) {

              Log.ReportEvent("PASS", "✅ Flight matched. Origin: " + originAirport + ", Destination: " + finalDestination +
                  ", Date: " + departureDate + ", price: " + price + ", flightName: " + flightName +
                  ", flightcode: " + flightcode + ", departureTime: " + departureTime +
                  ", arrivalTime: " + arrivalTime + ", Stops: " + stopsString);
              
              ScreenShots.takeScreenShot1();
              
              return new String[] {
                  originAirport,
                  finalDestination,
                  departureDate,
                  flightcode,
                  departureTime,
                  arrivalTime,
                  price,
                  flightName,
                  stopsString
              };
          } else {
              Log.ReportEvent("FAIL", "❌ Flight mismatch. Expected: From " + Fromlocation + ", To " + Tolocation + ", Date " + journeyDate +
                  ". Found: From " + originAirport + ", To " + finalDestination + ", Date " + departureDate);
              ScreenShots.takeScreenShot1();
              Assert.fail("Flight search validation failed.");
          }

      } catch (Exception e) {
          Log.ReportEvent("FAIL", "❌ Error during validation: " + e.getMessage());
          e.printStackTrace();
          ScreenShots.takeScreenShot1();
          Assert.fail("Exception in validateFlights method.");
      }

      return new String[] { "", "", "", "", "", "", "", "", "" };
  }
public void validateFlightDetailsOnBookingPage(String[] expectedDetails, Log Log, ScreenShots ScreenShots) {
      try {
          // Unpack expected data
          String expectedOrigin = expectedDetails[0];
          String expectedDestination = expectedDetails[1];
          String expectedDate = expectedDetails[2];
          String expectedFlightCode = expectedDetails[3];
          String expectedDepartureTime = expectedDetails[4];
          String expectedArrivalTime = expectedDetails[5];
          String expectedPrice = expectedDetails[6];
          String expectedFlightName = expectedDetails[7];
          String expectedStopLocationsRaw = expectedDetails[8]; // e.g., "VNS, BOM" or "Nonstop"

          List<WebElement> legs = driver.findElements(By.xpath("//div[@class='flight-card-mini']"));

          List<String> actualStopLocations = new ArrayList<>();
          String firstOrigin = "", lastDestination = "";
          String fullFlightCode = "", fullFlightName = "";
          String firstDepTime = "", lastArrTime = "";
          String depDate = "", arrDate = "";

          for (int i = 0; i < legs.size(); i++) {
              WebElement leg = legs.get(i);

              String origin = getAttribute(leg, ".//h6[@data-tgdepartflorigin]", "data-tgdepartflorigin");
              if (origin.isEmpty()) origin = getAttribute(leg, ".//h6[@data-tgreturnflorigin]", "data-tgreturnflorigin");

              String destination = getAttribute(leg, ".//h6[@data-tgreturnfldestination]", "data-tgreturnfldestination");
              if (destination.isEmpty()) destination = getAttribute(leg, ".//h6[@data-tgdepartfldestination]", "data-tgdepartfldestination");

              String depTime = getText(leg, ".//h6[@data-tgdepartfldeptime]");
              if (depTime.isEmpty()) depTime = getText(leg, ".//h6[@data-tgreturnfldeptime]");

              String arrTime = getText(leg, ".//h6[@data-tgdepartflarrtime]");
              if (arrTime.isEmpty()) arrTime = getText(leg, ".//h6[@data-tgreturnflarrtime]");

              String carrierInfo = getAttribute(leg, ".//span[@data-tgdepartflcarriername]", "data-tgdepartflcarriername");
              if (carrierInfo.isEmpty()) carrierInfo = getAttribute(leg, ".//span[@data-tgreturnflcarriername]", "data-tgreturnflcarriername");

              if (i == 0) {
                  firstOrigin = origin;
                  firstDepTime = depTime;
                  depDate = getAttribute(leg, ".//small[@data-tgdepartfldepdate]", "data-tgdepartfldepdate");
                  if (depDate.isEmpty()) depDate = getAttribute(leg, ".//small[@data-tgreturnfldepdate]", "data-tgreturnfldepdate");
              }

              if (i == legs.size() - 1) {
                  lastDestination = destination;
                  lastArrTime = arrTime;
                  arrDate = getAttribute(leg, ".//small[@data-tgdepartflarrdate]", "data-tgdepartflarrdate");
                  if (arrDate.isEmpty()) arrDate = getAttribute(leg, ".//small[@data-tgreturnflarrdate]", "data-tgreturnflarrdate");
              }

              if (i != legs.size() - 1) {
                  actualStopLocations.add(destination);
              }

              if (!carrierInfo.isEmpty() && carrierInfo.contains("-")) {
                  String[] parts = carrierInfo.split("-");
                  if (parts.length >= 2) {
                      fullFlightName += parts[0].trim() + " | ";
                      fullFlightCode += parts[1].trim().replace(")", "") + " | ";
                  }
              }
          }

          // Clean trailing separators
          fullFlightCode = fullFlightCode.replaceAll(" \\| $", "");
          Set<String> uniqueFlightNames = new LinkedHashSet<>(Arrays.asList(fullFlightName.split("\\s*\\|\\s*")));
          String actualFlightName = String.join(" | ", uniqueFlightNames);

          // Normalize stop locations
          List<String> normalizedActualStops = actualStopLocations.stream()
              .map(stop -> stop.contains("-") ? stop.substring(stop.lastIndexOf("-") + 1).trim() : stop.trim())
              .collect(Collectors.toList());

          List<String> expectedStops = expectedStopLocationsRaw.equalsIgnoreCase("Nonstop") ?
              new ArrayList<>() :
              Arrays.asList(expectedStopLocationsRaw.split(",\\s*"));

          // Fetch actual price from booking page
          String actualPrice = driver.findElement(By.xpath("//span[text()='Grand Total']//parent::div/h6")).getText().trim();

          // --- VALIDATIONS ---

          if (!firstOrigin.contains(expectedOrigin) || !lastDestination.contains(expectedDestination) ||
              !depDate.equals(expectedDate) || !firstDepTime.equals(expectedDepartureTime) || !lastArrTime.equals(expectedArrivalTime)) {

              Log.ReportEvent("FAIL", "Booking page mismatch! Expected: " + Arrays.toString(expectedDetails));
              Log.ReportEvent("INFO", "Extracted: Origin=" + firstOrigin + ", Destination=" + lastDestination +
                      ", Date=" + depDate + ", Times=" + firstDepTime + " - " + lastArrTime);
              ScreenShots.takeScreenShot1();
              Assert.fail("Mismatch in origin/destination/dates/times on booking page.");
          }

          if (!normalizedActualStops.equals(expectedStops)) {
              Log.ReportEvent("FAIL", "Stop locations mismatch! Expected: " + expectedStops + ", Found: " + normalizedActualStops);
              ScreenShots.takeScreenShot1();
              Assert.fail("Stopover locations do not match.");
          }

          if (!actualPrice.equals(expectedPrice)) {
              Log.ReportEvent("FAIL", "Price mismatch! Expected: " + expectedPrice + ", Found: " + actualPrice);
              ScreenShots.takeScreenShot1();
              Assert.fail("Price on booking page does not match expected price.");
          }

          if (!actualFlightName.toLowerCase().contains(expectedFlightName.toLowerCase())) {
              Log.ReportEvent("FAIL", "Flight name mismatch! Expected: " + expectedFlightName + ", Found: " + actualFlightName);
              ScreenShots.takeScreenShot1();
              Assert.fail("Flight name on booking page does not match expected flight name.");
          }

          // ✅ SUCCESS
          Log.ReportEvent("PASS", "✅ Booking page matches result page:\n" +
                  "From: " + firstOrigin + " → " + lastDestination +
                  ", Stops: " + normalizedActualStops +
                  ", Times: " + firstDepTime + " - " + lastArrTime +
                  ", Flight Code: " + fullFlightCode +
                  ", Price: " + actualPrice +
                  ", Flight Name: " + actualFlightName);
          ScreenShots.takeScreenShot1();

      } catch (Exception e) {
          Log.ReportEvent("FAIL", "❌ Exception while validating booking page: " + e.getMessage());
          e.printStackTrace();
          ScreenShots.takeScreenShot1();
          Assert.fail("Exception in booking page validation.");
      }
  }

  private String getAttribute(WebElement element, String xpath, String attrName) {
      try {
          return element.findElement(By.xpath(xpath)).getAttribute(attrName).trim();
      } catch (Exception e) {
          return "";
      }
  }

  private String getText(WebElement element, String xpath) {
      try {
          return element.findElement(By.xpath(xpath)).getText().trim();
      } catch (Exception e) {
          return "";
      }
  }
  
 //Method to click back to search button 
  
  public void goBackToSearchResults() throws InterruptedException {
	        // Scroll to top
	        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
	        Thread.sleep(2000); 

	        // Click the "Back To Search Results" button
	        driver.findElement(By.xpath("//button[text()='Back To Search Results']")).click();
	    
  }
  
 //==========================================================================================================
  
  public void validateBookingpgFirstNameOneWay(Log Log, ScreenShots ScreenShots, WebDriver driver, String firstNameValue) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        WebElement firstName = wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.xpath("//h6[@data-tgflguesttype='Adult 1']//following::input[@placeholder='First Name']")));

	        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", firstName);
	        Thread.sleep(1000);

	        if (firstName.isDisplayed() && firstName.isEnabled()) {
	            firstName.click();
	            firstName.sendKeys(Keys.CONTROL + "a");
	            firstName.sendKeys(Keys.DELETE);
	            Thread.sleep(500);

	            firstName.sendKeys(firstNameValue);
	            ScreenShots.takeScreenShot1();

	            Thread.sleep(500);

	            String enteredText = firstName.getAttribute("value");

	            Log.ReportEvent("PASS", "First Name: " + enteredText);
	        } else {
	            Log.ReportEvent("FAIL", "First Name field not interactable.");
	            ScreenShots.takeScreenShot1();
	        }

	    } catch (Exception e) {
	        Log.ReportEvent("FAIL", "Exception while entering First Name: " + e.getMessage());
	        ScreenShots.takeScreenShot1();
	    }
	}

  public void validateBookingpgLastNameOneWay(Log Log, ScreenShots ScreenShots, WebDriver driver, String lastNameValue) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        WebElement lastname = wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.xpath("//h6[@data-tgflguesttype='Adult 1']//following::input[@placeholder='Last Name']")));

	        // Scroll into view
	        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", lastname);
	        Thread.sleep(1000);

	        if (lastname.isDisplayed() && lastname.isEnabled()) {
	            lastname.click();
	            lastname.sendKeys(Keys.CONTROL + "a");
	            lastname.sendKeys(Keys.DELETE);
	            Thread.sleep(500); // Allow time for clear

	            lastname.sendKeys(lastNameValue);
	            ScreenShots.takeScreenShot1();

	            Thread.sleep(500); // Let the input settle

	            String enteredText = lastname.getAttribute("value");
	            Log.ReportEvent("PASS", "Last Name: " + enteredText);
	        } else {
	            Log.ReportEvent("FAIL", "Last Name field not interactable.");
	            ScreenShots.takeScreenShot1();
	        }

	    } catch (Exception e) {
	        Log.ReportEvent("FAIL", "Exception while entering Last Name: " + e.getMessage());
	        ScreenShots.takeScreenShot1();
	    }
	}

  
  public void validateBookingpgEmailOneWay(Log Log, ScreenShots ScreenShots, WebDriver driver, String email) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        WebElement Email = wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.xpath("//input[@name='email']")));

	        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", Email);
	        Thread.sleep(1000); 

	        if (Email.isDisplayed() && Email.isEnabled()) {
	            Email.click();
	            Email.sendKeys(Keys.CONTROL + "a");
	            Email.sendKeys(Keys.DELETE);
	            Thread.sleep(500); 

	         
	            Email.sendKeys(email);
	            ScreenShots.takeScreenShot1();

	            Thread.sleep(500); 

	            String enteredText = Email.getAttribute("value");
	            Log.ReportEvent("PASS", "Email: " + enteredText);
	        } else {
	            Log.ReportEvent("FAIL", "Email field not interactable.");
	            ScreenShots.takeScreenShot1();
	        }

	    } catch (Exception e) {
	        Log.ReportEvent("FAIL", "Exception while entering Email: " + e.getMessage());
	        ScreenShots.takeScreenShot1();
	    }
	}


  public void validateBookingpageTitleOneway(Log log, ScreenShots screenshots, WebDriver driver) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		JavascriptExecutor js = (JavascriptExecutor) driver;


	    try {
	        // Scroll to and click "Send for Approval" button
	        WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//span[text()='Send for Approval']/ancestor::button")));

	        js.executeScript("arguments[0].scrollIntoView(true);", sendButton);
	        Thread.sleep(500);  // Short pause for smooth interaction
	        sendButton.click();

	        System.out.println("Clicked 'Send for Approval' button.");
	        Thread.sleep(2000);  // Wait for potential error popup

	        // Check for error popup
	        WebElement errorPopup = driver.findElement(By.xpath("//div[@role='presentation']"));
	        if (errorPopup.isDisplayed()) {
	            log.ReportEvent("PASS", "Error message is displayed.");
	        } else {
	            log.ReportEvent("FAIL", "Error message is not displayed.");
	        }

	        screenshots.takeScreenShot1();

	    } catch (Exception e) {
	        log.ReportEvent("FAIL", "Exception occurred: " + e.getMessage());
	        screenshots.takeScreenShot1();
	        e.printStackTrace();
	    }
	}
  

  public void selectSeatsBasedOnStops(Log log, ScreenShots screenShots, String... seatsToPick) throws InterruptedException {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    try {
	        // Get stop count
	        WebElement fromStopElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//*[contains(@class,'tg-stops')]//button[contains(text(),'Stop')]")));
	        
	        String fromStopCountText = fromStopElement.getText().trim();
	        String numericCount = fromStopCountText.replaceAll("[^0-9]", "");
	        int stopCount = Integer.parseInt(numericCount.isEmpty() ? "0" : numericCount);
	        
	        log.ReportEvent("INFO", "Detected " + stopCount + " stop(s) for this flight");

	        // Calculate how many 'Pick Seat' buttons we expect (1 + stop count)
	        int expectedPickSeatButtons = stopCount + 1;
	        
	        // Find all Pick Seat buttons
	        List<WebElement> pickSeatButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
	            By.xpath("//button[contains(normalize-space(), 'Pick Seat')]")));
	        
	        if (pickSeatButtons.size() != expectedPickSeatButtons) {
	            log.ReportEvent("FAIL", "Expected " + expectedPickSeatButtons + " Pick Seat buttons but found " + pickSeatButtons.size());
	            screenShots.takeScreenShot1();
	            Assert.fail("Mismatch in Pick Seat buttons count");
	        }

	        // Process each Pick Seat button
	        for (int i = 0; i < pickSeatButtons.size(); i++) {
	            // Click the Pick Seat button
	            WebElement pickSeatBtn = pickSeatButtons.get(i);
	            js.executeScript("arguments[0].scrollIntoView(true);", pickSeatBtn);
	            pickSeatBtn.click();
	            log.ReportEvent("INFO", "Clicked Pick Seat button #" + (i+1));
	            
	            // Select the seat (if seatsToPick array has enough entries)
	            if (i < seatsToPick.length && seatsToPick[i] != null && !seatsToPick[i].isEmpty()) {
	                selectAvailableSeat(seatsToPick[i], log, screenShots);
	            } else {
	                // If no specific seat provided, pick first available
	                selectFirstAvailableSeat(log, screenShots);
	            }
	            
	            // Close seat selection if needed (add logic if your app requires this)
	        }
	    } catch (Exception e) {
	        log.ReportEvent("FAIL", "Error in seat selection: " + e.getMessage());
	        screenShots.takeScreenShot1();
	        throw e;
	    }
	}

	private void selectAvailableSeat(String seatNumber, Log log, ScreenShots screenShots) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    
	    try {
	        // Try to find the exact requested seat if available
	        String xpath = String.format(
	            "//*[contains(@class,'seat-open') and contains(@aria-label,'%s available')]",
	            seatNumber);
	        
	        List<WebElement> availableSeats = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
	            By.xpath(xpath)));
	        
	        if (!availableSeats.isEmpty()) {
	            availableSeats.get(0).click();
	            log.ReportEvent("PASS", "Successfully selected requested seat: " + seatNumber);
	            return;
	        }
	        
	        // If exact seat not available, find alternative
	        log.ReportEvent("WARNING", "Requested seat " + seatNumber + " not available, finding alternative");
	        selectFirstAvailableSeat(log, screenShots);
	        
	    } catch (Exception e) {
	        log.ReportEvent("FAIL", "Error selecting seat " + seatNumber + ": " + e.getMessage());
	        screenShots.takeScreenShot1();
	        throw e;
	    }
	}

	private void selectFirstAvailableSeat(Log log, ScreenShots screenShots) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    
	    try {
	        // Find first available seat
	        WebElement firstAvailableSeat = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//*[contains(@class,'seat-open') and contains(@aria-label,'available')][1]")));
	        
	        // Extract seat number from aria-label for reporting
	        String seatLabel = firstAvailableSeat.getAttribute("aria-label");
	        String seatNumber = seatLabel.replace(" available", "").replace("Seat ", "");
	        
	        firstAvailableSeat.click();
	        log.ReportEvent("INFO", "Selected available seat: " + seatNumber);
	        
	    } catch (Exception e) {
	        log.ReportEvent("FAIL", "No available seats found: " + e.getMessage());
	        screenShots.takeScreenShot1();
	        throw e;
	    }
	}
	
//------------------------------------------------------------------------------------------------------
	//Method to validate seats in one way
	
/*	public int pickSeats(Log log, ScreenShots screenShots,String... selectedSeats) throws InterruptedException { 
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    List<String> seatPrices = new ArrayList<>();
	    int totalSeatPrice = 0;
Thread.sleep(3000);
	    List<WebElement> pickSeatButtons = wait.until(
	        ExpectedConditions.presenceOfAllElementsLocatedBy(
	            By.xpath("//button[contains(normalize-space(), 'Pick Seat')]")
	        )
	    );
	    System.out.println("Found " + pickSeatButtons.size() + " Pick Seat buttons");
	    log.ReportEvent("INFO", "Found " + pickSeatButtons.size() + " Pick Seat buttons");

	    for (int i = 0; i < pickSeatButtons.size(); i++) {
	        clickWithRetry(pickSeatButtons.get(i));
	        System.out.println("Opened seat map " + (i + 1));
	        log.ReportEvent("INFO", "Opened seat map " + (i + 1));
	        Thread.sleep(2000);

	        String selectedSeat = null;

	        if (i < selectedSeats.length && selectedSeats[i] != null) {
	            if (!clickExactSeat(selectedSeats[i])) {
	                System.out.println("Seat " + selectedSeats[i] + " not available");
	                log.ReportEvent("WARN", "Seat " + selectedSeats[i] + " not available");
	                screenShots.takeScreenShot1();
	                selectedSeat = clickFirstAvailableSeat();
	            } else {
	                selectedSeat = selectedSeats[i];
	            }
	        } else {
	            selectedSeat = clickFirstAvailableSeat();
	        }

	        Thread.sleep(1000);

	        try {
	            String seatPriceXPath = "//div[contains(@class,'seat') and contains(@class,'non-empty-seat') and .//span[normalize-space(text())='" + selectedSeat + "']]//small";
	            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(seatPriceXPath)));
	            String priceText = priceElement.getText();

	            seatPrices.add(priceText);

	            String cleaned = priceText.replaceAll("[^\\d]", "");
	            int numericPrice = Integer.parseInt(cleaned);
	            totalSeatPrice += numericPrice;

	            System.out.println("Segment " + (i + 1) + " - Seat: " + selectedSeat + ", Price: " + priceText);
	            log.ReportEvent("INFO", "Segment " + (i + 1) + " - Seat: " + selectedSeat + ", Price: " + priceText);
	        } catch (Exception e) {
	            System.out.println("Failed to get seat price: " + e.getMessage());
	            log.ReportEvent("FAIL", "Failed to get seat price for seat " + selectedSeat + ": " + e.getMessage());
	            screenShots.takeScreenShot1();
	            throw e;
	        }

	        try {
	            WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//button[normalize-space()='Continue']")
	            ));
	            js.executeScript("arguments[0].scrollIntoView(true);", continueBtn);
	            Thread.sleep(500); 
	            continueBtn.click();
	        } catch (Exception e) {
	            System.out.println("Failed to click Continue: " + e.getMessage());
	            log.ReportEvent("FAIL", "Failed to click Continue: " + e.getMessage());
	            screenShots.takeScreenShot1();
	            throw e;
	        }

	        System.out.println("Clicked Continue");
	        log.ReportEvent("INFO", "Clicked Continue");
	        Thread.sleep(1500);
	    }

	    System.out.println("Final Seat Prices: " + seatPrices);
	    System.out.println("Total Seat Price: ₹" + totalSeatPrice);
	    log.ReportEvent("INFO", "Final Seat Prices: " + seatPrices);
	    log.ReportEvent("INFO", "Total Seat Price: ₹" + totalSeatPrice);

	    return totalSeatPrice;
	}
*/
	
	public int pickSeats(Log log, ScreenShots screenShots, String... selectedSeats) throws InterruptedException {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    List<String> seatPrices = new ArrayList<>();
	    int totalSeatPrice = 0;
	    Set<String> alreadySelectedSeats = new HashSet<>();

	    int totalSegments = selectedSeats.length;

	    for (int i = 0; i < totalSegments; i++) {
	        // Re-fetch pick seat buttons each loop
	        List<WebElement> pickSeatButtons = wait.until(
	            ExpectedConditions.presenceOfAllElementsLocatedBy(
	                By.xpath("//button[contains(normalize-space(), 'Pick Seat')]")
	            )
	        );

	        if (i >= pickSeatButtons.size()) {
	            System.out.println("No more Pick Seat buttons at index " + i);
	            log.ReportEvent("WARN", "No more Pick Seat buttons at index " + i);
	            break;
	        }

	        clickWithRetry(pickSeatButtons.get(i));
	        System.out.println("Opened seat map " + (i + 1));
	        log.ReportEvent("INFO", "Opened seat map " + (i + 1));
	        Thread.sleep(2000);

	        String selectedSeat = null;

	        if (selectedSeats[i] != null && clickExactSeat(selectedSeats[i]) && !alreadySelectedSeats.contains(selectedSeats[i])) {
	            selectedSeat = selectedSeats[i];
	        } else {
	            selectedSeat = clickFirstAvailableSeatExcluding(alreadySelectedSeats);
	        }

	        // Extract just seat number (remove price or line breaks)
	        String cleanedSeatNumber = selectedSeat.split("\\R")[0].trim();
	        alreadySelectedSeats.add(cleanedSeatNumber);
	        Thread.sleep(1000);

	        try {
	            String seatPriceXPath = "//div[contains(@class,'seat') and contains(@class,'non-empty-seat') and .//span[normalize-space(text())='" + cleanedSeatNumber + "']]//small";
	            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(seatPriceXPath)));
	            String priceText = priceElement.getText();

	            seatPrices.add(priceText);

	            String cleaned = priceText.replaceAll("[^\\d]", "");
	            int numericPrice = Integer.parseInt(cleaned);
	            totalSeatPrice += numericPrice;

	            System.out.println("Segment " + (i + 1) + " - Seat: " + cleanedSeatNumber + ", Price: " + priceText);
	            log.ReportEvent("INFO", "Segment " + (i + 1) + " - Seat: " + cleanedSeatNumber + ", Price: " + priceText);
	        } catch (Exception e) {
	            System.out.println("Failed to get seat price: " + e.getMessage());
	            log.ReportEvent("FAIL", "Failed to get seat price for seat " + selectedSeat + ": " + e.getMessage());
	            screenShots.takeScreenShot1();
	            throw e;
	        }

	        try {
	            WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//button[normalize-space()='Continue']")
	            ));
	            js.executeScript("arguments[0].scrollIntoView(true);", continueBtn);
	            Thread.sleep(500);
	            continueBtn.click();
	        } catch (Exception e) {
	            System.out.println("Failed to click Continue: " + e.getMessage());
	            log.ReportEvent("FAIL", "Failed to click Continue: " + e.getMessage());
	            screenShots.takeScreenShot1();
	            throw e;
	        }

	        System.out.println("Clicked Continue");
	        log.ReportEvent("INFO", "Clicked Continue");
	        Thread.sleep(1500);
	    }

	    System.out.println("Final Seat Prices: " + seatPrices);
	    System.out.println("Total Seat Price: ₹" + totalSeatPrice);
	    log.ReportEvent("INFO", "Final Seat Prices: " + seatPrices);
	    log.ReportEvent("INFO", "Total Seat Price: ₹" + totalSeatPrice);

	    return totalSeatPrice;
	}

	private void clickWithRetry(WebElement element) {
	    try {
	        element.click();
	    } catch (Exception e) {
	        ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView(true); arguments[0].click();", element
	        );
	    }
	}


	private boolean clickExactSeat(String seatNumber) {
	    try {
	        WebElement seat = driver.findElement(By.xpath(
	            "//div[contains(@class,'seat') and contains(@class,'seat-open') and contains(@class,'non-empty-seat')]//span[normalize-space(text())='" + seatNumber + "']/.."
	        ));
	        seat.click();
	        return true;
	    } catch (Exception e1) {
	        try {
	            Boolean clicked = (Boolean) ((JavascriptExecutor) driver).executeScript(
	                "var seats = document.getElementsByClassName('seat');" +
	                "for (var i = 0; i < seats.length; i++) {" +
	                "  if (seats[i].textContent.trim() === '" + seatNumber + "') {" +
	                "    seats[i].click(); return true;" +
	                "  }" +
	                "}" +
	                "return false;"
	            );
	            return clicked;
	        } catch (Exception e2) {
	            return false;
	        }
	    }
	}

		
	private String clickFirstAvailableSeatExcluding(Set<String> alreadySelectedSeats) {
	    List<WebElement> seats = driver.findElements(By.cssSelector(".seat.seat-open"));

	    for (WebElement seat : seats) {
	        String seatText = seat.getText().trim();
	        String seatNumber = seatText.split("\\R")[0].trim(); // take only the seat number (first line)

	        if (!alreadySelectedSeats.contains(seatNumber) && !seatNumber.isEmpty()) {
	            try {
	                seat.click();
	            } catch (Exception e) {
	                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", seat);
	            }
	            System.out.println("Selected new available seat: " + seatNumber);
	            return seatNumber;
	        }
	    }

	    System.out.println("No unselected available seats found");
	    return "none";
	}
	    	
	
	public int[] validateSeatPricesToBookingPg() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    
	    // Get price elements from booking page
	    WebElement totalPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("//*[contains(@class, ' tg-fbtotal ')]")
	    ));
	    WebElement seatPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("//*[contains(@class, ' tg-fbseatprice')]")
	    ));

	    // Extract and clean prices
	    int bookingTotalPrice = Integer.parseInt(totalPriceElement.getText().replaceAll("[^0-9]", ""));
	    int seatPrice = Integer.parseInt(seatPriceElement.getText().replaceAll("[^0-9]", ""));
	    int actualCombinedPrice = bookingTotalPrice + seatPrice;

	    // Compare with expected price
	   // System.out.println("Expected Seat Price Total: ₹" + expectedTotalSeatPrice);
	    System.out.println("Actual Combined Price: ₹" + actualCombinedPrice);
	    
	    return new int[] {bookingTotalPrice,seatPrice};

	
}
	
	public void ValidateSumOfPrices(Log log, ScreenShots screenShots,int totalPrice, int bookingPrice[]) throws InterruptedException {
	    int bookingTotalprice = bookingPrice[0];
	    int seatPrice = bookingPrice[1];

	    int grandTotal = bookingTotalprice + seatPrice;

	    System.out.println("Total Price: " + totalPrice);
	    System.out.println("Seat Price: " + seatPrice);
	    log.ReportEvent("INFO", "Total Price: " + totalPrice);
	    log.ReportEvent("INFO", "Seat Price: " + seatPrice);

	    if (totalPrice == seatPrice) {
	        System.out.println("pass: " + totalPrice + " " + seatPrice);
	        log.ReportEvent("PASS", "Total price matches seat price: " + totalPrice + " == " + seatPrice);

	        try {
	            WebElement grandtotalprice = driver.findElement(By.xpath("//*[contains(@class, ' tg-fbgrandtotal')]"));
	            int bookingTotalPrice = Integer.parseInt(grandtotalprice.getText().replaceAll("[^0-9]", ""));

	            if (bookingTotalPrice == grandTotal) {
	                System.out.println("pass: " + bookingTotalPrice + " Matches with " + grandTotal);
	                log.ReportEvent("PASS", "Grand total matches expected: " + bookingTotalPrice + " == " + grandTotal);
	            } else {
	                System.out.println("FAIL: Grand total mismatch. Found: " + bookingTotalPrice + ", Expected: " + grandTotal);
	                log.ReportEvent("FAIL", "Grand total mismatch. Found: " + bookingTotalPrice + ", Expected: " + grandTotal);
	                screenShots.takeScreenShot1();
	            }
	        } catch (Exception e) {
	            System.out.println("FAIL: Unable to validate grand total: " + e.getMessage());
	            log.ReportEvent("FAIL", "Unable to validate grand total: " + e.getMessage());
	            screenShots.takeScreenShot1();
	        }

	    } else {
	        System.out.println("FAIL: Total price does not match seat price");
	        log.ReportEvent("FAIL", "Total price does not match seat price: " + totalPrice + " != " + seatPrice);
	        screenShots.takeScreenShot1();
	    }
	}

	//-----------------------------------------------------------------------------------------------------------
	//Method to Get Layover Airline Names
    public ArrayList getLayoverAirlines()
    {
            ArrayList layoverAirportNames = new ArrayList();
            List<WebElement>names=driver.findElements(By.xpath("//*[contains(@class,'tg-airport-name')]//span"));
          for(WebElement name:names)
          {
              String layoverNames=name.getText();
              layoverAirportNames.add(layoverNames);
          }
         return layoverAirportNames;
        }

    //Method to Validate Layover AirLines
    public void validateLayoverAirlines(Log Log, ScreenShots ScreenShots, String... layoverNames)
    {
        try{
            for(String name:layoverNames)
            {
                int start = name.indexOf('(');
                int end = name.indexOf(')');
                String code = "";
                if (start != -1 && end != -1 && start < end) {
                    code = name.substring(start + 1, end);
                }
                System.out.println("Airport code: " + code);
                ArrayList data=new ArrayList<>();
                List<WebElement>layoverAirlineNames=driver.findElements(By.className("tg-layovercity"));
                for(WebElement names:layoverAirlineNames)
                {
                    String layoverData=names.getText();
                    int start1 = layoverData.indexOf('(');
                    int end1 = layoverData.indexOf(')');
                    String code1 = "";
                    if (start1 != -1 && end1 != -1 && start1 < end1) {
                        code1 = layoverData.substring(start1 + 1, end1);
                    }
                    System.out.println("Airport code: " + code1);
                    data.add(code1);
                    if (data.contains(code)) {
                        Log.ReportEvent("PASS", "LayOver AirLine Data is Displayed"+ code);
                        // Proceed with your script logic
                    } else {
                        Log.ReportEvent("FAIL", "LayOver AirLine Data is Not Displayed");
                        ScreenShots.takeScreenShot1();
                        Assert.fail();
                        // You can throw an error or handle accordingly
                    }
                }

            }
        }catch(Exception e)
        {
            Log.ReportEvent("FAIL", "LayOver AirLine Data is Not Displayed");
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
            Assert.fail();
        }
    }

    //Method to Select LayOver Airlines on Result Screen
    public void clickLayOverAirlineCheckboxes(String... airlineNames) {
        for (String airlineName : airlineNames) {
            try {
                WebElement airline = driver.findElement(By.xpath("//*[text()='LAYOVER AIRPORTS']"));
                Actions move = new Actions(driver);
                move.moveToElement(airline).perform();
                Thread.sleep(1000);
                String name=airlineName.trim();
                driver.findElement(By.xpath("//*[normalize-space(text())='"+name+"']/parent::div/parent::li//input")).click();
                System.out.println("Clicked checkbox for: " + airlineName);
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Could not find/click checkbox for: " + airlineName);
                e.printStackTrace();
                Assert.fail();

            }
        }
    }
  //Method to getStops Text Based On Index
    public String getStopsText(int index) {
        String stopsText = driver.findElement(By.xpath("(//*[@class='tg-stops'])[" + index + "]")).getText();
        return stopsText;
    }
    //Method to getClasses Text Based On Index
    public String getClassText() {
        String classText = driver.findElement(By.xpath("//*[contains(@class,'tg-fromcabinclass')]")).getText();
        return classText;
    }
  //Method to Validate Flight details
    public String[] getFlightDetails(String stops, Log Log, ScreenShots ScreenShots) {
        String Origin = null;
        String Destination = null;
        String departingTime = null;
        String arrivalTime = null;
        String departDate = null;
        String arrivalDate = null;
        TestExecutionNotifier.showExecutionPopup();
        try {
            if (stops.contentEquals("Nonstop")) {
                Origin = driver.findElement(By.className("tg-fromorigin")).getAttribute("data-tgfloriginairport");
                Destination = driver.findElement(By.className("tg-fromdestination")).getAttribute("data-tgfldestinationairport");
                departingTime = driver.findElement(By.className("tg-fromdeptime")).getText();
                arrivalTime = driver.findElement(By.className("tg-fromarrtime")).getText();
                departDate = driver.findElement(By.className("tg-fromdepdate")).getText();
                arrivalDate = driver.findElement(By.className("tg-fromarrdate")).getText();
            } else if (stops.contentEquals("1 stops")) {
                Origin = driver.findElement(By.xpath("(//*[contains(@class,'tg-fromorigin')])[1]")).getAttribute("data-tgfloriginairport");
                Destination = driver.findElement(By.xpath("(//*[contains(@class,'tg-fromdestination')])[2]")).getAttribute("data-tgfldestinationairport");
                departingTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fromdeptime')])[1]")).getText();
                arrivalTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fromarrtime')])[2]")).getText();
                departDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-fromdepdate')])[1]")).getText();
                arrivalDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-fromarrdate')])[2]")).getText();
            } else if (stops.contentEquals("2 stops")) {
                Origin = driver.findElement(By.xpath("(//*[contains(@class,'tg-fromorigin')])[1]")).getAttribute("data-tgfloriginairport");
                Destination = driver.findElement(By.xpath("(//*[contains(@class,'tg-fromdestination')])[last()]")).getAttribute("data-tgfldestinationairport");
                departingTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fromdeptime')])[1]")).getText();
                arrivalTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fromarrtime')])[last()]")).getText();
                departDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-fromdepdate')])[1]")).getText();
                arrivalDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-fromarrdate')])[last()]")).getText();
            } else {
                Log.ReportEvent("FAIL", "Unable to get data from Search Screen");
                ScreenShots.takeScreenShot1();
                Assert.fail();
            }
        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Unable to get data from Search Screen");
            e.printStackTrace();
            Assert.fail();

        }
        return new String[]{Origin, Destination, departingTime, arrivalTime, departDate, arrivalDate};
    }
    //Method to click on Continue Button based on Fare
    public String[] clickOnContinueBasedOnFareType(String fareType, String reason) throws InterruptedException {
        Thread.sleep(2000);
        TestExecutionNotifier.showExecutionPopup();
        String price = null;
        String fare=null;
        try {
            price = null;
            fare=null;
            List<WebElement> fareElements = driver.findElements(By.xpath("//div[@data-tgflfaretype='" + fareType + "']"));

            if (!fareElements.isEmpty() && fareElements.get(0).isDisplayed()) {
                Thread.sleep(2000);
                WebElement continueButton = driver.findElement(By.xpath(
                        "//div[@data-tgflfaretype='" + fareType + "']/parent::div/following-sibling::div//button[text()='Continue']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
                ((JavascriptExecutor) driver).executeScript(
                        "window.scrollTo({ top: arguments[0].getBoundingClientRect().top + window.scrollY - 100, behavior: 'smooth' });",
                        continueButton);
                Thread.sleep(2000);
                price = driver.findElement(By.xpath("//div[@data-tgflfaretype='" + fareType + "']/parent::div//div[@data-tgflfare]")).getText();
                continueButton.click();
                fare=fareType;
                if (isElementPresent(By.xpath("//h2[text()='Airport Change']"))) {
                    clickOnYesContinue();
                }

                if (isElementPresent(By.xpath("//h2[text()='Reason for Selection']"))) {
                    clickOnSelectRegionPopup(reason);
                    clickOnProceedBooking();
                }
            } else {
                Thread.sleep(3000);
                WebElement continueButton=driver.findElement(By.xpath("(//button[text()='Continue'])[1]"));
                ((JavascriptExecutor) driver).executeScript(
                        "window.scrollTo({ top: arguments[0].getBoundingClientRect().top + window.scrollY - 100, behavior: 'smooth' });",
                        continueButton);
                Thread.sleep(1000);
                fare = driver.findElement(By.xpath("(//*[@data-tgflfaretype])[1]")).getText();
                price = driver.findElement(By.xpath("(//div[@data-tgflfare])[1]")).getText();
                continueButton.click();
                if (isElementPresent(By.xpath("//h2[text()='Airport Change']"))) {
                    clickOnYesContinue();
                }

                if (isElementPresent(By.xpath("//h2[text()='Reason for Selection']"))) {
                    clickOnSelectRegionPopup(reason);
                    clickOnProceedBooking();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[]{price, fare};
    }
  //Method to Select Reason for Selection
    public void clickOnProceedBooking() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()='Proceed to Booking']")).click();
    }

    //Method to click on Yes Continue Button
    public void clickOnYesContinue() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[text()='Yes, Continue']")).click();

    }

    public boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty() &&
                driver.findElements(locator).get(0).isDisplayed();
    }

    //Method to Click on Select Reasons Popup
    public void clickOnSelectRegionPopup(String reason) throws InterruptedException {
        driver.findElement(By.xpath("//span[text()='" + reason + "']")).click();
        Thread.sleep(1000);
    }	
    
    
  //Method to Validate Flights Details in Booking Screen
//    public void validateFlightDetailsInBookingScreen(Log Log, ScreenShots ScreenShots, String price,String fare, String stops,String classes,String Origin,String Destination,String departingTime,String arrivalTime,String departDate,String arrivalDate) {
//        try {
//            String fareType=fare+" "+"Fare";
//            TestExecutionNotifier.showExecutionPopup();
//            Thread.sleep(6000);
//            WebElement data=driver.findElement(By.xpath("//*[@data-tgflight]"));
//            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", data);
//            Thread.sleep(2000);
//            if (stops.contentEquals("Nonstop")) {
//                Thread.sleep(2000);
//                Origin = driver.findElement(By.className("tg-fborigin")).getAttribute("data-tgdepartfloriginairport");
//                Destination = driver.findElement(By.className("tg-fbdestination")).getAttribute("data-tgdepartfldestinationairport");
//                departingTime = driver.findElement(By.className("tg-fb-deptime")).getText();
//                arrivalTime = driver.findElement(By.className("tg-fbarrtime")).getText();
//                departDate = driver.findElement(By.className("tg-fbdepdate")).getText();
//                arrivalDate = driver.findElement(By.className("tg-fbarrdate")).getText();
//                classes = driver.findElement(By.className("tg-fbcabinclass")).getText();
//                fare = driver.findElement(By.className("flt-booking-faretype")).getText();
//
//                price = driver.findElement(By.xpath("//span[text()='Grand Total']/parent::div//h6")).getText();
//
//                ValidateActualAndExpectedValuesForFlights(Origin, Origin, "Origin Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(Destination, Destination, "Destination Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(departingTime, departingTime, "Departing Time Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(arrivalTime, arrivalTime, "Arrival Time Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(departDate, departDate, "Depart Date Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(arrivalDate, arrivalDate, "Arrival Date Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(price, price, "Price Details in Search Screen and Booking Screen", Log);
////                ValidateActualAndExpectedValuesForFlights(classes, classes, "Cabin Class Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(fare, fareType, "Fare Details in Search Screen and Booking Screen", Log);
//                ScreenShots.takeScreenShot1();
//
//
//            } else if (stops.contentEquals("1 stops")) {
//                Thread.sleep(2000);
//                Origin = driver.findElement(By.xpath("(//*[contains(@class,'tg-fborigin')])[1]")).getAttribute("data-tgdepartfloriginairport");
//                Destination = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbdestination')])[2]")).getAttribute("data-tgdepartfldestinationairport");
//                departingTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fb-deptime')])[1]")).getText();
//                arrivalTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbarrtime')])[2]")).getText();
//                departDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbdepdate')])[1]")).getText();
//                arrivalDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbarrdate')])[2]")).getText();
//                fare = driver.findElement(By.className("flt-booking-faretype")).getText();
//
//                price = driver.findElement(By.xpath("//span[text()='Grand Total']/parent::div//h6")).getText();
//
//                ValidateActualAndExpectedValuesForFlights(Origin, Origin, "Origin Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(Destination, Destination, "Destination Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(departingTime, departingTime, "Departing Time Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(arrivalTime, arrivalTime, "Arrival Time Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(departDate, departDate, "Depart Date Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(arrivalDate, arrivalDate, "Arrival Date Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(price, price, "Price Details in Search Screen and Booking Screen", Log);
////                validateCabinClassesOnBookingScreen(classes,Log,ScreenShots);
//                ValidateActualAndExpectedValuesForFlights(fare, fareType, "Fare Details in Search Screen and Booking Screen", Log);
//                ScreenShots.takeScreenShot1();
//
//
//            }  else if (stops.contentEquals("2 stops")) {
//                Thread.sleep(2000);
//                Origin = driver.findElement(By.xpath("(//*[contains(@class,'tg-fborigin')])[1]")).getAttribute("data-tgdepartfloriginairport");
//                Destination = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbdestination')])[last()]")).getAttribute("data-tgdepartfldestinationairport");
//                departingTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fb-deptime')])[1]")).getText();
//                arrivalTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbarrtime')])[last()]")).getText();
//                departDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbdepdate')])[1]")).getText();
//                arrivalDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbarrdate')])[last()]")).getText();
//                fare = driver.findElement(By.className("flt-booking-faretype")).getText();
//
//                price = driver.findElement(By.xpath("//span[text()='Grand Total']/parent::div//h6")).getText();
//
//                ValidateActualAndExpectedValuesForFlights(Origin, Origin, "Origin Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(Destination, Destination, "Destination Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(departingTime, departingTime, "Departing Time Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(arrivalTime, arrivalTime, "Arrival Time Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(departDate, departDate, "Depart Date Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(arrivalDate, arrivalDate, "Arrival Date Details in Search Screen and Booking Screen", Log);
//                ValidateActualAndExpectedValuesForFlights(price, price, "Price Details in Search Screen and Booking Screen", Log);
////                validateCabinClassesOnBookingScreen(classes,Log,ScreenShots);
//                ValidateActualAndExpectedValuesForFlights(fare, fareType, "Fare Details in Search Screen and Booking Screen", Log);
//                ScreenShots.takeScreenShot1();
//            }else{
//
//                Log.ReportEvent("FAIL", "Validation is Mismatching");
//                ScreenShots.takeScreenShot1();
//                Assert.fail();
//            }
//        } catch (Exception e) {
//            Log.ReportEvent("FAIL", "An error occurred during date validation: " + e.getMessage());
//            ScreenShots.takeScreenShot1();
//            e.printStackTrace();
//            Assert.fail();
//        }
//    }
    
    public void validateFlightDetailsInBookingScreen(Log Log, ScreenShots ScreenShots, String price, String fare, String stops, String classes, String Origin, String Destination, String departingTime, String arrivalTime, String departDate, String arrivalDate) {
        try {
            String fareType = fare + " Fare";
            TestExecutionNotifier.showExecutionPopup();

            // Optional: If booking is in an iframe, switch to it
            // driver.switchTo().frame("frameNameOrId");

            // Wait until the data-tgflight element is present
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(95));
           // WebElement data = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class,'tg-fbDeparting')]")));
            WebElement data = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class,'flight-title tg-fbDepartflight')]")));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", data);
            Thread.sleep(2000);  // small buffer wait

            if (stops.contentEquals("Nonstop")) {
                Origin = driver.findElement(By.className("tg-fbDepartorigin")).getAttribute("data-tgdepartfloriginairport");
                Destination = driver.findElement(By.className("tg-fbDepartdestination")).getAttribute("data-tgdepartfldestinationairport");
                departingTime = driver.findElement(By.className("tg-fbDepartdeptime")).getText();
                arrivalTime = driver.findElement(By.className("tg-fbDepartarrtime")).getText();
                departDate = driver.findElement(By.className("tg-fbDepartdepdate")).getText();
                arrivalDate = driver.findElement(By.className("tg-fbDepartarrdate")).getText();
                classes = driver.findElement(By.className("fbDepartcabinclass")).getText();
                fare = driver.findElement(By.className("flt-booking-faretype")).getText();
                price = driver.findElement(By.xpath("//span[text()='Grand Total']/parent::div//h6")).getText();

                ValidateActualAndExpectedValuesForFlights(Origin, Origin, "Origin Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(Destination, Destination, "Destination Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(departingTime, departingTime, "Departing Time Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(arrivalTime, arrivalTime, "Arrival Time Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(departDate, departDate, "Depart Date Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(arrivalDate, arrivalDate, "Arrival Date Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(price, price, "Price Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(fare, fareType, "Fare Details in Search Screen and Booking Screen", Log);
                ScreenShots.takeScreenShot1();

            } else if (stops.contentEquals("1 stops")) {
                Origin = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartorigin')])[1]")).getAttribute("data-tgdepartfloriginairport");
                Destination = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartdestination')])[2]")).getAttribute("data-tgdepartfldestinationairport");
                departingTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartdeptime')])[1]")).getText();
                arrivalTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartarrtime')])[2]")).getText();
                departDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartdepdate')])[1]")).getText();
                arrivalDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartarrdate')])[2]")).getText();
                fare = driver.findElement(By.className("flt-booking-faretype")).getText();
                price = driver.findElement(By.xpath("//span[text()='Grand Total']/parent::div//h6")).getText();

                ValidateActualAndExpectedValuesForFlights(Origin, Origin, "Origin Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(Destination, Destination, "Destination Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(departingTime, departingTime, "Departing Time Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(arrivalTime, arrivalTime, "Arrival Time Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(departDate, departDate, "Depart Date Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(arrivalDate, arrivalDate, "Arrival Date Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(price, price, "Price Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(fare, fareType, "Fare Details in Search Screen and Booking Screen", Log);
                ScreenShots.takeScreenShot1();

            } else if (stops.contentEquals("2 stops")) {
                Origin = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartorigin')])[1]")).getAttribute("data-tgdepartfloriginairport");
                Destination = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartdestination')])[last()]")).getAttribute("data-tgdepartfldestinationairport");
                departingTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartdeptime')])[1]")).getText();
                arrivalTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartarrtime')])[last()]")).getText();
                departDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartdepdate')])[1]")).getText();
                arrivalDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartarrdate')])[last()]")).getText();
                fare = driver.findElement(By.className("flt-booking-faretype")).getText();
                price = driver.findElement(By.xpath("//span[text()='Grand Total']/parent::div//h6")).getText();

                ValidateActualAndExpectedValuesForFlights(Origin, Origin, "Origin Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(Destination, Destination, "Destination Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(departingTime, departingTime, "Departing Time Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(arrivalTime, arrivalTime, "Arrival Time Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(departDate, departDate, "Depart Date Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(arrivalDate, arrivalDate, "Arrival Date Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(price, price, "Price Details in Search Screen and Booking Screen", Log);
                ValidateActualAndExpectedValuesForFlights(fare, fareType, "Fare Details in Search Screen and Booking Screen", Log);
                ScreenShots.takeScreenShot1();

            } else {
                Log.ReportEvent("FAIL", "Validation is Mismatching: Unexpected stops value - " + stops);
                ScreenShots.takeScreenShot1();
                Assert.fail();
            }

        } catch (TimeoutException te) {
            Log.ReportEvent("FAIL", "Timeout waiting for flight data element: " + te.getMessage());
            ScreenShots.takeScreenShot1();
            Assert.fail();
        } catch (Exception e) {
            Log.ReportEvent("FAIL", "An error occurred during date validation: " + e.getMessage());
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
            Assert.fail();
        }
    }

    
    
    // Method to Validate Actual and Expected Data with Messages for Both Pass and Fail
    public void ValidateActualAndExpectedValuesForFlights(String actual, String expected, String message, Log log) {
        try {
            if (actual.contentEquals(expected)) {
                log.ReportEvent("PASS", String.format("%s | Actual: '%s', Expected: '%s' - Values match.", message, actual, expected));
            } else {
                log.ReportEvent("FAIL", String.format("%s | Actual: '%s', Expected: '%s' - Values do not match.", message, actual, expected));
                Assert.fail("Validation Failed: " + message);
            }
        } catch (Exception e) {
            log.ReportEvent("FAIL", String.format("%s | Actual: '%s', Expected: '%s' - Exception during comparison.", message, actual, expected));
            e.printStackTrace();
            Assert.fail("Exception during validation: " + message);
        }
    }

    // Method to Validate Actual and Expected Data with Messages for Both Pass and Fail
    public void ValidateActualAndExpectedValuesForFlightFairs(String actual, String expected,String expected1,String message, Log log) {
        try {
            if (actual.contentEquals(expected)||actual.contentEquals(expected1)) {
                log.ReportEvent("PASS", String.format("%s | Actual: '%s', Expected: '%s' - Values match.", message, actual, expected));
            } else {
                log.ReportEvent("FAIL", String.format("%s | Actual: '%s', Expected: '%s' - Values do not match.", message, actual, expected));
                Assert.fail("Validation Failed: " + message);
            }
        } catch (Exception e) {
            log.ReportEvent("FAIL", String.format("%s | Actual: '%s', Expected: '%s' - Exception during comparison.", message, actual, expected));
            e.printStackTrace();
            Assert.fail("Exception during validation: " + message);
        }
    }

   
    //Method to click ONWARD DEPART TIME
    public void selectOnWardDepartTimeZeroToSix() throws InterruptedException {
        driver.findElement(By.xpath("//*[contains(@class,'tg-dep-time-06')]")).click();
        Thread.sleep(5000);
    }
    //Method to click ONWARD ARRIVAL TIME
    public void selectOnWardArrivalTimeZeroToSix() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[contains(@class,'tg-arr-time-06')]")).click();
        Thread.sleep(5000);
    }
	
  //Method to generate random numbers
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rng = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(rng.nextInt(characters.length())));
        }
        return sb.toString();
    }
    
  //Method to Enter Adult Details.
    public void enterAdultDetailsForDomestic(String[] title,int adults,Log Log, ScreenShots ScreenShots) throws InterruptedException {
    try{
        if(adults>1)
        {
            for (int i = 0; i < title.length; i++) {
                String firstName = generateRandomString(5);
                String lastName = generateRandomString(5);
                String first="Appu"+firstName;
                String last="Kumar"+lastName;

                String titleNames = title[i];
                int xpathIndex = i + 2;

                WebElement titleDropDown = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbpaxtitile')])["+xpathIndex+"]"));
                titleDropDown.click();
                driver.findElement(By.xpath("//li[@data-value='"+titleNames+"']")).click();
                Thread.sleep(1000);

                // Assuming your input fields have ids like firstname1, lastname1, firstname2, lastname2, etc.
                WebElement firstNameField = driver.findElement(By.xpath("(//input[@name='firstname'])["+xpathIndex+"]"));
                firstNameField.clear();
                firstNameField.sendKeys(first);
                WebElement lastNameField = driver.findElement(By.xpath("(//input[@name='lastname'])["+xpathIndex+"]"));
                lastNameField.clear();
                lastNameField.sendKeys(last);
            }
            ScreenShots.takeScreenShot1();

        }else {
            System.out.println("One Adult had been Selected");
        }
    }catch(Exception e)
    {
        Log.ReportEvent("FAIL", "Enter Adult Details is UnSuccessful");
        e.printStackTrace();
        ScreenShots.takeScreenShot1();
        Assert.fail();
      }
        }
        public static String generateRandomDigits(int length) {
            String digits = "0123456789";
            Random rng = new Random();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < length; i++) {
                sb.append(digits.charAt(rng.nextInt(digits.length())));
            }
            return sb.toString();
        }

        //Method to Enter Adult Details.
        public void enterAdultDetailsForInterNational(String[] title,int adults,Log Log, ScreenShots ScreenShots) throws InterruptedException {
            try{
                if(adults>1)
                {
                    for (int i = 0; i < title.length; i++) {
                        String firstName = generateRandomString(5);
                        String lastName = generateRandomString(5);
                        String first="Appu"+firstName;
                        String last="Kumar"+lastName;

                        String titleNames = title[i];
                        int xpathIndex = i + 2;

                        WebElement titleDropDown = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbpaxtitile')])["+xpathIndex+"]"));
                        titleDropDown.click();
                        driver.findElement(By.xpath("//li[@data-value='"+titleNames+"']")).click();
                        Thread.sleep(1000);

                        // Assuming your input fields have ids like firstname1, lastname1, firstname2, lastname2, etc.
                        WebElement firstNameField = driver.findElement(By.xpath("(//input[@name='firstname'])["+xpathIndex+"]"));
                        firstNameField.clear();
                        firstNameField.sendKeys(first);
                        WebElement lastNameField = driver.findElement(By.xpath("(//input[@name='lastname'])["+xpathIndex+"]"));
                        lastNameField.clear();
                        lastNameField.sendKeys(last);

                        WebElement dateInput = driver.findElement(By.xpath("(//input[@id='date'])["+xpathIndex+"]"));
                        dateInput.sendKeys("12-12-2000");

                        WebElement passportNumber = driver.findElement(By.xpath("(//input[@name='passportnumber'])["+xpathIndex+"]"));
                        passportNumber.clear();
                        String randomNumber = generateRandomDigits(6);  // e.g., "483920"
                        System.out.println("Random Number: " + randomNumber);
                        passportNumber.sendKeys(randomNumber);

                        WebElement expireDateInput = driver.findElement(By.xpath("(//input[@name='passportexpirydate'])["+xpathIndex+"]"));
                        expireDateInput.sendKeys("12-12-2040");

                        WebElement IssuedDateInput = driver.findElement(By.xpath("(//input[@name='passportissuedate'])["+xpathIndex+"]"));
                        IssuedDateInput.sendKeys("12-06-2025");
                    }
                }else{
                    System.out.println("One Adult had been Selected");
                }
                ScreenShots.takeScreenShot1();
            }catch(Exception e)
            {
                Log.ReportEvent("FAIL", "Enter Adult Details is UnSuccessful");
                e.printStackTrace();
                ScreenShots.takeScreenShot1();
                Assert.fail();
            }
        }

        //Method to Select the Pick Seat
        public int selectSeatFormPickSeat(Log log, ScreenShots screenShots) throws InterruptedException {
            Set<String> selectedSeats = new HashSet<>();
            int totalPrice = 0; // To accumulate seat prices
Thread.sleep(4000);
            // Get all "Pick Seat" buttons
            List<WebElement> pickSeatButtons = driver.findElements(By.xpath("//button[contains(text(), 'Pick Seat')]"));

            System.out.println("Total Pick Seat buttons found: " + pickSeatButtons.size());

            for (int i = 0; i < pickSeatButtons.size(); i++) {
                // Re-locate buttons each time to avoid stale elements
                pickSeatButtons = driver.findElements(By.xpath("//button[contains(text(), 'Pick Seat')]"));

                WebElement button = pickSeatButtons.get(i);
                button.click();

                // Wait for seat UI to load
                Thread.sleep(4000);

                // Get all available, open, non-selected seats
                List<WebElement> availableSeats = driver.findElements(By.cssSelector(".seat.seat-open.non-empty-seat"));

                boolean seatPicked = false;

                for (WebElement seat : availableSeats) {
                    String seatLabel = seat.getAttribute("aria-label").trim();

                    if (!selectedSeats.contains(seatLabel)) {
                        seat.click();
                        selectedSeats.add(seatLabel);
                        System.out.println("Passenger " + (i + 1) + " selected seat: " + seatLabel);

                        try {
                            // Find the element that holds the tg-selected-seat-price attribute
                            WebElement priceElement = driver.findElement(By.xpath(" //*[@tg-selected-seat-price]"));

                            // Extract the value from the attribute
                            String priceValue = priceElement.getAttribute("tg-selected-seat-price"); // e.g., "1838.0"
                            System.out.println("Seat price from attribute: " + priceValue);

                            // Optional: compare visible price vs attribute value
                            String visiblePriceText = priceElement.getText().replaceAll("[^0-9.]", ""); // e.g., "1838.0"
                            System.out.println("Seat price from visible text: " + visiblePriceText);

                            // Parse both values
                            double attrPrice = Double.parseDouble(priceValue);
                            double visiblePrice = visiblePriceText.isEmpty() ? 0 : Double.parseDouble(visiblePriceText);

                            // Log mismatch if any
                            if (Math.round(attrPrice) != Math.round(visiblePrice)) {
                                System.out.println(" Price mismatch: Attribute = " + attrPrice + ", Visible = " + visiblePrice);
                            }

                            // Add to total (rounded to int rupees)
                            totalPrice += (int) Math.round(attrPrice);

                            log.ReportEvent("INFO", "Select Pick Seat is "+ seatLabel);
                            screenShots.takeScreenShot1();

                        } catch (Exception e) {
                            System.out.println(" Failed to extract seat price: " + e.getMessage());
                            log.ReportEvent("FAIL", "Failed to extract seat price:  "+ e.getMessage());
                            Assert.fail();
                        }

                        seatPicked = true;
                        break;
                    }
                }

                if (!seatPicked) {
                	Thread.sleep(3000);
                	System.out.println("No available unique seat found for passenger #" + (i + 1));
                }

                Thread.sleep(4000); // Wait after selecting

                // Attempt to close the seat dialog
                try {
                	Thread.sleep(4000);
                    WebElement closeButton = driver.findElement(By.xpath("//button[text()='Continue']")); // Adjust if needed
                    closeButton.click();
                } catch (Exception e) {
                    System.out.println(" Close button not found.");
                    log.ReportEvent("FAIL", "Close button not found. "+ e.getMessage());
                    Assert.fail();
                }

                Thread.sleep(4000); // delay before next iteration
            }

            System.out.println(" Total price for all selected seats: ₹" + totalPrice);
            return totalPrice;
        }
        
        
        
        
        public void selectMealAndBaggageForEachPassengerOnBookingScreen(Log log, ScreenShots screenShots) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            try {
                // Get dropdowns for meals and baggage (assume same size/order)
                List<WebElement> mealDropdowns = driver.findElements(By.xpath("//*[contains(@class,'tg-fbmeal')]"));
                List<WebElement> baggageDropdowns = driver.findElements(By.cssSelector(".tg-fbbaggage"));

                int passengerCount = Math.min(mealDropdowns.size(), baggageDropdowns.size());

                System.out.println("Found " + passengerCount + " passengers with meal & baggage options");

                for (int i = 0; i < passengerCount; i++) {
                    System.out.println("Passenger #" + (i + 1));

                    // --- MEAL SELECTION ---
                    try {
                        mealDropdowns = driver.findElements(By.xpath("//*[contains(@class,'tg-fbmeal')]")); // Re-fetch
                        WebElement mealDropdown = mealDropdowns.get(i);
                        wait.until(ExpectedConditions.elementToBeClickable(mealDropdown)).click();

                        // Wait for meal popover and options
                        WebElement mealContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[contains(@class,'MuiPopover-paper')]")
                        ));
                        List<WebElement> mealOptions = mealContainer.findElements(By.xpath(".//li[@role='option']"));

                        if (!mealOptions.isEmpty() && mealOptions.size() > 1) {
                            Thread.sleep(1000);
                            mealOptions.get(1).click(); // Select 2nd option
                            System.out.println("  Selected meal option: " + mealOptions.get(1).getText());
                            log.ReportEvent("PASS", "Meal option selected: " + mealOptions.get(1).getText());
                            screenShots.takeScreenShot1();
                        } else {
                            log.ReportEvent("FAIL", "No meal options found.");
                            screenShots.takeScreenShot1();
                            Assert.fail("Meal options not available for passenger #" + (i + 1));
                        }

                    } catch (Exception e) {
                        log.ReportEvent("FAIL", "Error during meal selection for passenger #" + (i + 1) + ": " + e.getMessage());
                        screenShots.takeScreenShot1();
                        Assert.fail();
                    }

                    Thread.sleep(1500);

                    // --- BAGGAGE SELECTION ---
                    try {
                        baggageDropdowns = driver.findElements(By.cssSelector(".tg-fbbaggage")); // Re-fetch
                        WebElement baggageDropdown = baggageDropdowns.get(i);
                        wait.until(ExpectedConditions.elementToBeClickable(baggageDropdown)).click();

                        // Wait for latest baggage popover
                        List<WebElement> baggageContainers = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                By.xpath("//div[contains(@class,'MuiPopover-paper')]")
                        ));
                        WebElement latestContainer = baggageContainers.get(baggageContainers.size() - 1);

                        List<WebElement> baggageOptions = latestContainer.findElements(By.xpath(".//li[@role='option']"));

                        if (!baggageOptions.isEmpty() && baggageOptions.size() > 1) {
                            Thread.sleep(1000);
                            baggageOptions.get(1).click(); // Select 2nd option
                            System.out.println("  Selected baggage option: " + baggageOptions.get(1).getText());
                            log.ReportEvent("PASS", "Baggage option selected: " + baggageOptions.get(1).getText());
                            screenShots.takeScreenShot1();
                        } else {
                            log.ReportEvent("FAIL", "No baggage options found.");
                            screenShots.takeScreenShot1();
                            Assert.fail("Baggage options not available for passenger #" + (i + 1));
                        }

                    } catch (StaleElementReferenceException staleEx) {
                        log.ReportEvent("FAIL", "StaleElementReferenceException during baggage selection: " + staleEx.getMessage());
                        screenShots.takeScreenShot1();
                        Assert.fail();
                    } catch (Exception e) {
                        log.ReportEvent("FAIL", "Error during baggage selection for passenger #" + (i + 1) + ": " + e.getMessage());
                        screenShots.takeScreenShot1();
                        Assert.fail();
                    }

                    // Short delay before next passenger (optional, now mostly replaced with WebDriverWait)
                    Thread.sleep(1000);
                }

            } catch (Exception e) {
                log.ReportEvent("FAIL", "Unexpected error during meal and baggage selection: " + e.getMessage());
                screenShots.takeScreenShot1();
                Assert.fail();
            }
        }

        public int getPriceSum(String xpath) {
            List<WebElement> priceElements = driver.findElements(By.xpath(xpath));
            int sum = 0;

            for (WebElement price : priceElements) {
                String priceText = price.getText(); // Example: ₹ 850
                try {
                    int value = Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
                    sum += value;
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid price text: " + priceText);
                }
            }
            return sum;
        }
        
        
      //Method to get Meal and Baggage Deatils
        public void getAndValidateMealBaggagePriceDetails(int seatPrice,Log log, ScreenShots screenShots)
        {
            try{
                int totalMealPrice = getPriceSum("//*[contains(@class,'tg-fbmeal')]//span[@class='price']");
                int totalBaggagePrice = getPriceSum("//*[contains(@class, 'tg-fbbaggage')]//span[@class='price']");
                int grandTotal = totalMealPrice + totalBaggagePrice + seatPrice;
                String priceText = driver.findElement(By.xpath("//span[text()='Total']/parent::div//h6")).getText();
                int price = Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
                int fullPrice=grandTotal+price;
                log.ReportEvent("Info", "Total price :"+" "+price);
                log.ReportEvent("Info", "Meal price :"+" "+totalMealPrice);
                log.ReportEvent("Info", "Baggage price :"+" "+totalBaggagePrice);
                log.ReportEvent("Info", "Seat price :"+" "+seatPrice);
                log.ReportEvent("Info", "Grand Total price :"+" "+fullPrice);
                String totalPrice = driver.findElement(By.xpath("//span[text()='Grand Total']/parent::div//h6")).getText();
                int extractedPrice=extractInrAmount(totalPrice);
                ValidateActualAndExpectedValuesForFlightsDetails(fullPrice,extractedPrice,"Peck Seat,Meal,Baggage Price and Total Price is",log);
                screenShots.takeScreenShot1();
            }catch(Exception e)
            {
                log.ReportEvent("FAIL", "Price Details are Mismatching"+ e.getMessage());
                screenShots.takeScreenShot1();
                Assert.fail();
            }
        }

        public int extractInrAmount(String input) {
            // Pattern to match the INR amount (e.g., ₹ 25,998)
            Pattern pattern = Pattern.compile("₹\\s?([\\d,]+)");
            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                String inrValue = matcher.group(1); // Get only the numeric part
                String numeric = inrValue.replaceAll(",", ""); // Remove commas
                return Integer.parseInt(numeric);
            } else {
                throw new IllegalArgumentException("No INR value found in input: " + input);
            }
        }


        // Method to Validate Actual and Expected Integer Data with Messages for Both Pass and Fail
        public void ValidateActualAndExpectedValuesForFlightsDetails(int actual, int expected, String message, Log log) {
            try {
                if (actual == expected) {
                    log.ReportEvent("PASS", String.format("%s | Actual: '%d', Expected: '%d' - Values match.", message, actual, expected));
                } else {
                    log.ReportEvent("FAIL", String.format("%s | Actual: '%d', Expected: '%d' - Values do not match.", message, actual, expected));
                    Assert.fail("Validation Failed: " + message);
                }
            } catch (Exception e) {
                log.ReportEvent("FAIL", String.format("%s | Actual: '%d', Expected: '%d' - Exception during comparison.", message, actual, expected));
                e.printStackTrace();
                Assert.fail("Exception during validation: " + message);
            }
        }

        //Method to select Department dropdown
        public void selectDepartment()
        {
            driver.findElement(By.xpath("//input[@id='react-select-7-input']")).click();
            driver.findElement(By.xpath("//div[@class='tg-select__option tg-select__option--is-focused css-d7l1ni-option']")).click();
        }

        //Method to select Project dropdown
        public void selectProject()
        {
            driver.findElement(By.xpath("//input[@id='react-select-9-input']")).click();
            driver.findElement(By.xpath("//div[@class='tg-select__option tg-select__option--is-focused css-d7l1ni-option']")).click();
        }

        //Method to select CostCenter dropdown
        public void selectCostcenter()
        {
            driver.findElement(By.xpath("//input[@id='react-select-8-input']")).click();
            driver.findElement(By.xpath("//div[@class='tg-select__option tg-select__option--is-focused css-d7l1ni-option']")).click();
        }
    
      //Method for wait For Progress To Complete
    	public void waitForProgressToComplete(WebDriver driver) {
    	    int timeoutInSeconds = 120;
    	    int pollIntervalInMillis = 500; // Poll every 500ms
    	    int elapsedTime = 0;

    	    while (elapsedTime < timeoutInSeconds * 1000) {
    	        try {
    	            List<WebElement> progressElements = driver.findElements(By.cssSelector("progress"));

    	            // If progress element is no longer in the DOM, assume it is complete
    	            if (progressElements.isEmpty()) {
    	                break;
    	            }

    	            WebElement progress = progressElements.get(0);
    	            String valueStr = progress.getAttribute("value");
    	            //System.out.println(valueStr);

    	            if (valueStr != null) {
    	                try {
    	                    int value = Integer.parseInt(valueStr);
    	                 //   System.out.println(value);
    	                    if (value >= 100) {
    	                        // Wait a bit to ensure DOM update (if tag disappears after hitting 100%)
    	                        Thread.sleep(300);
    	                        // Re-check if it's removed now
    	                        if (driver.findElements(By.cssSelector("progress")).isEmpty()) {
    	                            break;
    	                        }
    	                    }
    	                } catch (NumberFormatException e) {
    	                    // Value was not a number, continue polling
    	                }
    	            }
                     
    	        } catch (StaleElementReferenceException e) {
    	            // Element was removed after we accessed it — consider it done
    	            break;
    	        } catch (InterruptedException e) {
    	            Thread.currentThread().interrupt(); // Restore the interrupt status
    	            break;
    	        }

    	        try {
    	            Thread.sleep(pollIntervalInMillis);
    	        } catch (InterruptedException e) {
    	            Thread.currentThread().interrupt();
    	            break;
    	        }

    	        elapsedTime += pollIntervalInMillis;
    	    }
    	    System.out.println("Progress bar is completed");
    	}
    	
    	//Method to Validate data after selection of Flight
    	public void validateDataAfterSelectingFlight()
    	{
    		String departureTime=driver.findElement(By.xpath("(//div[@class='owf-deptime']/h6)[1]")).getText();
    		String timeOnly=departureTime.split(" ")[0];
    		String arrivalTime=driver.findElement(By.xpath("(//div[@class='owf-arrtime']/h6)[1]")).getText();
    		String arrivalOnly=arrivalTime.split(" ")[0];

    		// Concatenate with dash
    		String finalTime = timeOnly.substring(0, 5) + "-" + arrivalOnly.substring(0, 5);
    		System.out.println("----------------------------------------------------------"); // Output: 16:00-02:15
    		System.out.println("Final Time Range: " + finalTime);
    		System.out.println("----------------------------------------------------------"); // Output: 16:00-02:15

    		String price=driver.findElement(By.xpath("(//div[@class='owf-price']/h6)[1]")).getText();
    		String priceOnly=price.split(" ")[1];
    		String bookingPrice=driver.findElement(By.xpath("//button[text()='Other Fares']/parent::div")).getText();
    		String bookingPriceOnly=bookingPrice.split(" ")[1];

    		String timingsOnly=driver.findElement(By.xpath("//button[text()='Flight Details']/preceding-sibling::p")).getText();
    		// Remove all whitespace characters (spaces, tabs, etc.)
    		String arrivalAndDeparture = timingsOnly.replaceAll("\\s+", "");


    		System.out.println("Arrival and Departure time "+arrivalAndDeparture); // Output: 16:00-02:15
    		System.out.println("----------------------------------------------------------"); // Output: 16:00-02:15      


    		Assert.assertEquals(priceOnly, bookingPriceOnly);
    		Assert.assertEquals(finalTime,arrivalAndDeparture);

    	}

    	
    	public void selectFlightUntilFareTypeFound(String fareTypeArg, Log Log, ScreenShots ScreenShots) throws InterruptedException {
    	    int maxFlights = driver.findElements(By.xpath("//div[@class='oneway-header']/following-sibling::div/div")).size();
    	    int maxCheckLimit = Math.min(10, maxFlights); // Only check up to 10 flights
    	    boolean fareTypeFound = false;

    	    for (int index = 1; index <= maxCheckLimit; index++) {
    	        try {
    	            Log.ReportEvent("INFO", "🔎 Trying flight at index: " + index);
    	            selectFlightBasedOnIndex1(index);
    	            Thread.sleep(3000);

    	            fareTypeFound = selectFaretypeInternal(fareTypeArg, Log, ScreenShots);

    	            if (fareTypeFound) {
    	                Log.ReportEvent("PASS", "✅ Fare type '" + fareTypeArg + "' found for flight at index: " + index);
    	                return;
    	            }

    	        } catch (Exception e) {
    	            Log.ReportEvent("ERROR", "⚠️ Error at index " + index + ": " + e.getMessage());
    	            System.out.println("ERROR ⚠️ Error at index " + index + ": " + e.getMessage());
    	        }
    	    }

    	    // ❗ Fare type not found in first 10 flights
    	    String failMessage = "❌ Fare type '" + fareTypeArg + "' not found for this route after checking first " + maxCheckLimit + " flights.";
    	    Log.ReportEvent("FAIL", failMessage);
    	    ScreenShots.takeScreenShot1();
    	    Assert.fail(failMessage);
    	}

    	private boolean selectFaretypeInternal(String fareTypeArg, Log Log, ScreenShots ScreenShots) throws InterruptedException {
    	    List<WebElement> allFareType = driver.findElements(By.xpath("//div[@data-tgflfaretype]"));
    	    for (WebElement fareType : allFareType) {
    	        String fareTypeText = fareType.getText().trim();
    	        if (fareTypeText.contains(fareTypeArg)) {
    	        	System.out.println(fareTypeText);
    	            JavascriptExecutor js = (JavascriptExecutor) driver;
    	            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fareType);

    	            Log.ReportEvent("PASS", "FareType Found: " + fareTypeText);

    	            WebElement fareTypeButton = driver.findElement(By.xpath("//div[@data-tgflfaretype][normalize-space()='" + fareTypeText + "']/parent::div/parent::div//button[2]"));
    	            js.executeScript("arguments[0].scrollIntoView(true);", fareTypeButton);

    	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	            wait.until(ExpectedConditions.elementToBeClickable(fareTypeButton));

    	            js.executeScript("arguments[0].click();", fareTypeButton);

    	            Log.ReportEvent("PASS", "Clicked On FareType: " + fareTypeText);
    	            reasonForSelectionPopUp1(); // post click logic

    	            return true;
    	        }
    	    }
    	    selectFlightBasedOnIndex1(1);
    	    return false;
    	}
    	
    	public void selectFlightBasedOnIndex1(int index) throws InterruptedException {
		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		    // Optionally scroll to top (you had this before, it's not always needed)
		    js.executeScript("window.scrollBy(0, -document.body.scrollHeight)");
		    Thread.sleep(2000);

		    // Find the target flight element using XPath
		    WebElement flightCard = driver.findElement(By.xpath("(//div[@class='oneway-header']/following-sibling::div/div)[" + index + "]//button[text()='Select']"));

		    // Scroll it into view
		    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", flightCard);
		    Thread.sleep(1000); // Wait for scroll animation to finish (optional)

		    // Click the button
		    flightCard.click();

		    Thread.sleep(2000); // Optional wait for page update
		}

    	
    	//Method to close reason For Selection PopUp
    			public void reasonForSelectionPopUp1() throws InterruptedException {
    			    String value = "Personal Preference";

    			    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    			    try {
    			    	Thread.sleep(8000);
    			    //	 WebElement popup=driver.findElement(By.xpath("//h2[@id='alert-dialog-title']"));
    			        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(
    			            By.xpath("//h2[@id='alert-dialog-title']")
    		        ));

    			        if (popup.isDisplayed()) {
    			            WebElement reasonOption = driver.findElement(
    			                By.xpath("//span[text()='" + value + "']//parent::label")
    			            );
    			            reasonOption.click();
    			            //click on Proceed to Booking
    			            driver.findElement(By.xpath("//button[text()='Proceed to Booking']")).click();
    			          //  Thread.sleep(3000);
    			            //click on Continue button
//    			            driver.findElement(By.xpath("//div[@class='bottom-container-1']//button[text()='Continue']")).click();
    			        }

    			    } catch (TimeoutException e) {
    			        System.out.println("Popup did not appear in time.");
    			    }
    			}

    			public String[] selectFlightUntilFareTypeFound1(String fareTypeArg, String Fromlocation, String Tolocation, String journeyDate, Log Log, ScreenShots ScreenShots) throws InterruptedException {
    			    int maxFlights = driver.findElements(By.xpath("//div[@class='oneway-header']/following-sibling::div/div")).size();
    			    int maxCheckLimit = Math.min(10, maxFlights); // Only check up to 10 flights

    			    for (int index = 1; index <= maxCheckLimit; index++) {
    			        try {
    			            Log.ReportEvent("INFO", "🔎 Trying flight at index: " + index);
    			            selectFlightBasedOnIndex1(index);

    			            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    			            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-tgflfaretype]")));

    			            // ✨ Try to get fare and result
    			            String[] combinedResult = trySelectFareAndReturnData(fareTypeArg, Fromlocation, Tolocation, journeyDate, Log, ScreenShots);
    			            if (combinedResult != null) {
    			                Log.ReportEvent("PASS", "✅ Fare type '" + fareTypeArg + "' found at flight index: " + index);
    			                return combinedResult;
    			            }

    			        } catch (Exception e) {
    			            Log.ReportEvent("ERROR", "⚠️ Error at index " + index + ": " + e.getMessage());
    			            e.printStackTrace();
    			        }
    			    }

    			    String failMessage = "❌ Fare type '" + fareTypeArg + "' not found in first " + maxCheckLimit + " flights.";
    			    Log.ReportEvent("FAIL", failMessage);
    			    ScreenShots.takeScreenShot1();
    			    Assert.fail(failMessage);
    			    return new String[0]; // fallback to keep compiler happy
    			}	
    	
    			private String[] trySelectFareAndReturnData(String fareTypeArg, String Fromlocation, String Tolocation, String journeyDate, Log Log, ScreenShots ScreenShots) throws InterruptedException {
    			    List<WebElement> allFareType = driver.findElements(By.xpath("//*[@data-tgflfaretype]"));

    			    for (WebElement fareType : allFareType) {
    			        String fareTypeText = fareType.getText().trim();
    			        
    			        if (fareTypeText.contains(fareTypeArg)) {
    			        	String fareAttributeValueBtn=fareType.getAttribute("data-tgflfaretype");
    			        	
    			        	 WebElement priceElement = fareType.findElement(By.xpath("./following-sibling::*[@data-tgflfare]"));
    			        	    String priceValue = priceElement.getAttribute("data-tgflfare");

    			        	    System.out.println("Price: " + priceValue);
    			        	
    			            JavascriptExecutor js = (JavascriptExecutor) driver;
    			            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fareType);
    			            Log.ReportEvent("PASS", "FareType Found: " + fareTypeText);
    			            
    			            WebElement fareTypeButton = fareType.findElement(By.xpath("//button[@data-tgflbutton='"+fareAttributeValueBtn+"']"));
    			        //    WebElement fareTypeButton = fareType.findElement(By.xpath("./ancestor::div[contains(@class,'fare-container')]//button[2]"));
    			            js.executeScript("arguments[0].scrollIntoView(true);", fareTypeButton);

    			            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    			            wait.until(ExpectedConditions.elementToBeClickable(fareTypeButton));

    			            String[] resultData = validateFlightResultInResultPage(Fromlocation, Tolocation, journeyDate, Log, ScreenShots);
    			            Log.ReportEvent("INFO", "🛫 Validated Flight Info: " + Arrays.toString(resultData));

    			            js.executeScript("arguments[0].click();", fareTypeButton);
    			            Log.ReportEvent("PASS", "Clicked On FareType: " + fareTypeText);
    			            reasonForSelectionPopUp1();

    			        //    return resultData;
    			            
    			             // Add price to result array  .so This appends the priceValue to the end of the result array.
//    		    String[] combinedResult = Arrays.copyOf(resultData, resultData.length + 1);
//    		    combinedResult[resultData.length] = priceValue;
    		//
//    		    return combinedResult; 
    			         // Add price and fareTypeText to result array
    			            String[] combinedResult = Arrays.copyOf(resultData, resultData.length + 2);
    			            combinedResult[resultData.length] = priceValue;
    			            combinedResult[resultData.length + 1] = fareTypeText;

    			            return combinedResult;

    			             
    			             
    			        }
    			    }

    			    return null;
    			}

    			//---------------------------------------------
    			// Method to click on Meals Dropdown
    		    public double selectMealsOnwardOneWay(Log Log, ScreenShots ScreenShots, String OnwardBaggageSelectSplit[]) throws InterruptedException {

    		        double totalPrice1 = 0.0;
    		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    		        List<WebElement> sectors = driver.findElements(By.xpath("//label[text()='Meal Preference']/parent::div//div[@aria-labelledby='onward-label onward']"));
    		        int value = sectors.size();

    		        for (int i = 0; i < value; i++) {
    		            WebElement sector = sectors.get(i);
    		            sector.click();

    		            String travMeal = OnwardBaggageSelectSplit[i];
    		            List<WebElement> mealsList = driver.findElements(By.xpath("//ul/li[position() > 1]"));
    		            boolean isMealMatched = false;

    		            for (WebElement mealsListGet : mealsList) {
    		                String getmealsName = mealsListGet.getText();
    		                String[] getmealsName1 = getmealsName.split("₹");
    		                String getmealsName2 = getmealsName1[0].trim();
    		                String finalmealsText = getmealsName2.replace("-", "").trim();

    		                if (finalmealsText.equalsIgnoreCase(travMeal)) {
    		                    System.out.println("Matching meal found: " + travMeal);

    		                    WebElement userNeededDate = wait.until(ExpectedConditions.elementToBeClickable(
    		                            By.xpath("//ul[@aria-labelledby='onward-label']//li[normalize-space(text())='" + travMeal + "']")));
    		                    Thread.sleep(2000);
    		                    userNeededDate.click();

    		                    isMealMatched = true;
    		                    break;
    		                }
    		            }

    		            if (!isMealMatched && !mealsList.isEmpty()) {
    		                WebElement selectedmeal = wait.until(ExpectedConditions.elementToBeClickable(mealsList.get(0)));
    		                selectedmeal.click();
    		            }
    		        }

    		        // ✅ Now handle prices AFTER all dropdowns are selected
    		        List<WebElement> priceElements = driver.findElements(By.xpath(
    		                "//[@id='onward']/ancestor::div//[text()='Meal Preference']/parent::div//*[@class='price']"));

    		        if (priceElements.isEmpty()) {
    		            Log.ReportEvent("FAIL", "No price elements found for onward meal selections.");
    		        } else {
    		            for (WebElement priceElement : priceElements) {
    		                String price = priceElement.getText();
    		                System.out.println("Price getting: " + price);
    		                double priceValue = Double.parseDouble(price.replaceAll("[^\\d.]", ""));
    		                totalPrice1 += priceValue;
    		            }
    		            System.out.println(totalPrice1);
    		            Log.ReportEvent("PASS", "Total price after all onward meal selections: " + totalPrice1);
    		        }

    		        return totalPrice1;
    		    }
    		  //select meal return 
    		    public double selectMealsReturn(Log Log, ScreenShots ScreenShots,String ReturnBaggageSelectSplit[]) throws InterruptedException {


    		        double totalPrice2 = 0.0;
    		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    		        List<WebElement> sectors = driver.findElements(By.xpath("//label[text()='Meal Preference']/parent::div//div[@aria-labelledby='return-label return']"));
    		        int value = sectors.size();

    		        for (int i = 0; i < value; i++) {
    		            WebElement sector = sectors.get(i);
    		            sector.click();

    		            String travMeal = ReturnBaggageSelectSplit[i];
    		            List<WebElement> mealsList = driver.findElements(By.xpath("//ul/li[position() > 1]"));
    		            boolean isMealMatched = false;

    		            for (WebElement mealsListGet : mealsList) {
    		                String getmealsName = mealsListGet.getText();
    		                String[] getmealsName1 = getmealsName.split("₹");
    		                String getmealsName2 = getmealsName1[0].trim();
    		                String finalmealsText = getmealsName2.replace("-", "").trim();

    		                if (finalmealsText.equalsIgnoreCase(travMeal)) {
    		                    System.out.println("Matching meal found: " + travMeal);

    		                    WebElement userNeededDate = wait.until(ExpectedConditions.elementToBeClickable(
    		                            By.xpath("//ul[@aria-labelledby='return-label']//li[normalize-space(text())='"+ travMeal +"']")));
    		                    Thread.sleep(2000);
    		                    userNeededDate.click();

    		                    isMealMatched = true;
    		                    break;
    		                }
    		            }

    		            if (!isMealMatched && !mealsList.isEmpty()) {
    		                WebElement selectedmeal = wait.until(ExpectedConditions.elementToBeClickable(mealsList.get(0)));
    		                selectedmeal.click();
    		            }
    		        }

    		        // ✅ Now handle prices AFTER all dropdowns are selected
    		        List<WebElement> priceElementsreturn = driver.findElements(By.xpath(
    		                "//*[@id='return']//parent::div//span[@class='price']"));

    		        if (priceElementsreturn.isEmpty()) {
    		            Log.ReportEvent("FAIL", "No price elements found for onward meal selections.");
    		        } else {
    		            for (WebElement priceElementreturnsector : priceElementsreturn) {
    		                String price = priceElementreturnsector.getText();
    		                System.out.println("Price getting: " + price);
    		                double priceValue = Double.parseDouble(price.replaceAll("[^\\d.]", ""));
    		                totalPrice2 += priceValue;
    		            }
    		            System.out.println(totalPrice2);
    		            Log.ReportEvent("PASS", "Total price after all return meal selections: " + totalPrice2);
    		        }

    		        return totalPrice2;
    		    }
    		    public double addTotalMealsPrice(Log log, ScreenShots screenShots, double totalPrice1, double totalPrice2) {
    		        {
    		            System.out.println("Onward Price: " + totalPrice1);
    		            System.out.println("Return Price: " + totalPrice2);

    		            double total = totalPrice1 + totalPrice2;
    		            System.out.println("Total Baggage Price: " + total);

    		            log.ReportEvent("PASS", "Combined price of meals: " + total);
    		            return total;
    		        }
    		    }
    		    public Object validateMealss(Log Log,ScreenShots ScreenShots,double total) throws InterruptedException
    		    {
    		        String FareSummary = driver.findElement(By.xpath("//*[text()='Meal Price']/parent::div//h6")).getText();
    		        double FareSummary1 = Double.parseDouble(FareSummary.replaceAll("[^\\d.]", ""));

    		        System.out.println(total);
    		        System.out.println(FareSummary1);

    		        if(total == FareSummary1) {
    		            System.out.println("Meals Price validated: " +"Total meals selected count: "+ total + " equals " +"Total count displayed in FareSummary section: "+ FareSummary1);
    		            Log.ReportEvent("PASS", "Meals Price validated is: " +"Total meals selected count: "+ total + "which is equals to" +"Total count displayed in FareSummary section: "+ FareSummary1);
    		        } else {
    		            System.out.println("Meals Price mismatch: calculated " + total + " but found " + FareSummary1 + " in fare summary.");
    		            Log.ReportEvent("FAIL", "Meals Price mismatch: calculated " +"Total meals selected count is: "+ total + " but found " +"Total count displayed in FareSummary section: "+ FareSummary1 + " in fare summary.");
    		        }
    		        return null;

    		    }
    		    
  //Get the flights count 
    	
      public int getFlightsBeforeCount(Log Log) {
    	  WebElement flightCountElement = driver.findElement(By.xpath("//*[contains(@class,'tg-flCount')]"));
    	  String text = flightCountElement.getText(); // Example: "56 flights"
    	  String number = text.split(" ")[0]; // Gets "56" from "56 flight        
    	  int count = Integer.parseInt(number);
          Log.ReportEvent("PASS", "Flights Before count validated: Total flights found: " + count);
    	  return count;


      }
		
      public int getFlightsAfterCount(Log Log) {
    	  WebElement flightCountElement = driver.findElement(By.xpath("//*[contains(@class,'tg-flCount')]"));
    	  String text = flightCountElement.getText(); // Example: "56 flights"
    	  String number = text.split(" ")[0]; // Gets "56" from "56 flight        
    	  int count = Integer.parseInt(number);
          Log.ReportEvent("PASS", "Flights Before count validated: Total flights found: " + count);
    	  return count;


      }   
    		    
    //method for to validate policy filter 
      public void validatePolicyFilterOneWay(int Index, Log Log, ScreenShots ScreenShots) {
    	    try {
    	    	Thread.sleep(3000);
    	    	WebElement flightCard = driver.findElement(By.xpath("(//*[contains(@class,'tg-flight-card')]//button[text()='Select'])[" + Index + "]"));
    	    	Thread.sleep(500);
    	    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	    	js.executeScript("arguments[0].click();", flightCard);

    	        // Get the policy text element
    	        WebElement containerPolicyText = driver.findElement(By.xpath("//*[contains(@class,'fare-card')]//*[contains(@class,'inpolicy tg-policy')]"));
    	        String policyText = containerPolicyText.getText().trim();

    	        // Check if policy text contains "In Policy"
    	        if (policyText.contains("In Policy")) {
    	            Log.ReportEvent("PASS", "Policy filter validation passed: Policy text contains 'In Policy'.");
    	            ScreenShots.takeScreenShot1();
    	        } else {
    	            Log.ReportEvent("FAIL", "Policy filter validation failed: Policy text does not contain 'In Policy'. Text found: " + policyText);
    	            ScreenShots.takeScreenShot1();
    	            Assert.fail("Policy text validation failed.");
    	        }
    	    } catch (Exception e) {
    	        Log.ReportEvent("FAIL", "Exception in validatePolicyFilterOneWay: " + e.getMessage());
    	        ScreenShots.takeScreenShot1();
    	        e.printStackTrace();
    	        Assert.fail();
    	    }
    	}
 
      //method for to validate Out of policy filter 
      public void validateOutOfPolicyFilterOneWay(int Index, Log Log, ScreenShots ScreenShots) {
    	    try {
    	    	Thread.sleep(3000);
    	    	WebElement flightCard = driver.findElement(By.xpath("(//*[contains(@class,'tg-flight-card')]//button[text()='Select'])[" + Index + "]"));
    	    	Thread.sleep(500);
    	    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	    	js.executeScript("arguments[0].click();", flightCard);

    	        // Get the Out of Policy text element
    	        WebElement containerPolicyText = driver.findElement(By.xpath("//*[contains(@class,'fare-options-container')]//*[contains(@class,'outofpolicy tg-policy')]"));
    	        String policyText = containerPolicyText.getText().trim();

    	        // Check if policy text contains "Out of Policy"
    	        if (policyText.contains("Out of Policy")) {
    	            Log.ReportEvent("PASS", "Policy filter validation passed: Policy text contains 'Out of Policy'.");
    	            ScreenShots.takeScreenShot1();
    	        } else {
    	            Log.ReportEvent("FAIL", "Policy filter validation failed: Policy text does not contain 'Out of Policy'. Text found: " + policyText);
    	            ScreenShots.takeScreenShot1();
    	            Assert.fail("Out of Policy text validation failed.");
    	        }
    	    } catch (Exception e) {
    	        Log.ReportEvent("FAIL", "Exception in validateOutOfPolicyFilterOneWay: " + e.getMessage());
    	        ScreenShots.takeScreenShot1();
    	        e.printStackTrace();
    	        Assert.fail();
    	    }
    	}
     		   
      
    //Method to get user entered data
  	public String[] userEnterData() {
  	    try {
  	        // Get "From" location text and extract code
  	        String fromText = driver.findElement(By.xpath("(//div[contains(@class, 'tg-select__single-value')])[1]")).getText().trim();
  	        String fromLocationCode = extractAirportCode(fromText);
   
  	        // Get "To" location text and extract code
  	        String toText = driver.findElement(By.xpath("(//div[contains(@class, 'tg-select__single-value')])[2]")).getText().trim();
  	        String toLocationCode = extractAirportCode(toText);
   
  	        // Get journey date
  	        WebElement journeyDateInput = driver.findElement(By.xpath("//input[@placeholder='Journey Date']"));
  	        String journeyDateValue = journeyDateInput.getAttribute("value").trim();
   
  	        // Get return date (optional)
  	        WebElement returnDateInput = driver.findElement(By.xpath("//input[@placeholder='Return Date (Optional)']"));
  	        String returnDateValue = returnDateInput.getAttribute("value").trim();
   
  	        // Get flight class
  	        String flightClass = driver.findElement(By.xpath("//span[@class='capitalize']")).getText().trim();
   
  	        // Get adult count
  	        String adultCount = driver.findElement(By.xpath("//span[@class='capitalize']/ancestor::button")).getText().trim();
   
  	        // Fixed travel type for now
  	        String travel = "flight";
   
  	        // Debug log
  	        System.out.println("From Text: " + fromText);
  	        System.out.println("From Code: " + fromLocationCode);
  	        System.out.println("To Text: " + toText);
  	      //  System.out.println("To Code: " + toLocationCode);
  	      //  System.out.println("Journey Date: " + journeyDateValue);
  	      //  System.out.println("Return Date: " + returnDateValue);
  	      //  System.out.println("Flight Class: " + flightClass);
  	      //  System.out.println("Adult Count: " + adultCount);
   
  	        return new String[] {
  	            fromLocationCode,
  	            toLocationCode,
  	            journeyDateValue,
  	            returnDateValue,
  	            flightClass,
  	            travel,
  	            adultCount
  	        };
   
  	    } catch (Exception e) {
  	        e.printStackTrace();
  	        // Return default values in case of failure (7 items to avoid index errors)
  	        return new String[] { "", "", "", "", "", "", "" };
  	    }
  	}
  	
 // Helper method to extract airport code from location string
 	private String extractAirportCode(String locationText) {
 		Matcher matcher = Pattern.compile("\\((.*?)\\)").matcher(locationText);
 		return matcher.find() ? matcher.group(1) : locationText;
 	}
  	
  	//------------------------------------------------------------------
  	
  //Method to validate Search Results In Result page
  		public String[] validateResultsInResultPage(String From, String to, String journeyDate, int xpathIndex, Log Log, ScreenShots ScreenShots) throws ParseException {
  		    try {
  		        System.out.println(From);
  		        System.out.println(to);
   
  		        // Format date
  		        journeyDate = journeyDate.replaceAll("(st|nd|rd|th)", ""); // e.g. "14th-Jul-2025" -> "14-Jul-2025"
  		        SimpleDateFormat inputFormat = new SimpleDateFormat("d-MMM-yyyy");
  		        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
  		        Date parsedDate = inputFormat.parse(journeyDate);
  		        String formattedJourneyDate = outputFormat.format(parsedDate);
   
  		        // Click on flight card
  		        WebElement flightCard = driver.findElement(By.xpath("(//*[contains(@class,'tg-flight-card')])[" + xpathIndex + "]"));
  		        flightCard.click();
   
  		        // Extract flight info
  		        String stops = driver.findElement(By.xpath("(//*[@class='tg-stops'])[" + xpathIndex + "]")).getText();
  		        String departTimeText = driver.findElement(By.xpath("(//*[contains(@class,'tg-deptime')])[" + xpathIndex + "]")).getText().trim();
  		        String departTime = departTimeText.split("\\n")[0].trim();
  		        String departDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-depdate')])[" + xpathIndex + "]")).getText();
  		        String arrivalTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-arrtime')])[" + xpathIndex + "]")).getText();
  		        String arrivalDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-arrdate')])[" + xpathIndex + "]")).getText();
  		        String price = driver.findElement(By.xpath("(//*[@class='tg-price'])[" + xpathIndex + "]")).getText();
   
  		        // Origin and destination
  		        String origin = driver.findElement(By.xpath("//*[@data-tgfloriginairport]")).getAttribute("data-tgfloriginairport");
  		        String destination = driver.findElement(By.xpath("(//*[@data-tgfldestinationairport])[last()]")).getAttribute("data-tgfldestinationairport");
                   String fareType=driver.findElement(By.xpath("(//*[contains(@class,'tg-farename')])['"+xpathIndex+"']")).getText();
                   String policy=driver.findElement(By.xpath("(//*[contains(@class,'tg-policy')])['"+xpathIndex+"']")).getText();
                   String arrivalDates=driver.findElement(By.xpath("(//*[@class='tg-fromarrdate'])[last()]")).getText();
  		        String flightCodeDetails=driver.findElement(By.xpath("(//*[contains(@class,'tg-fromflightnumber')])[1]")).getText();
  		        
  		        System.out.println("Origin: " + origin);
  		        System.out.println("Destination: " + destination);
  		        System.out.println("Stops: " + stops);
  		        System.out.println("Departure time: " + departTime);
  		        System.out.println("Departure date: " + departDate);
  		        System.out.println("Arrival time: " + arrivalTime);
  		        System.out.println("Arrival date: " + arrivalDate);
  		        System.out.println("Price: " + price);
   
  		        // Validation
  		        if (From.equals(origin) && to.equals(destination) && formattedJourneyDate.equals(outputFormat.format(parsedDate))) {
  		            String matchedDetails = String.format(
  		                "Flight result matched:\n" +
  		                "- From: %s\n" +
  		                "- To: %s\n" +
  		                "- Journey Date: %s\n" +
  		                "- Departure Time: %s\n" +
  		                "- Arrival Time: %s\n" +
  		                "- Stops: %s\n" +
  		                "- Price: %s",
  		                origin, destination, formattedJourneyDate, departTime, arrivalTime, stops, price
  		            );
   
  		            // Validate airline name/code
  		            String[] FlightDetails = validateFlightIsDisplayed(xpathIndex, Log, ScreenShots);
  		            String airlineName = FlightDetails[0];
  		            String airlineCode = FlightDetails[1];
   
  		            Log.ReportEvent("PASS", matchedDetails);
  		            System.out.println(matchedDetails);
   
  		            return new String[] {
  		                origin, destination, formattedJourneyDate, departTime,
  		                arrivalTime, stops, price, airlineName, airlineCode,flightCodeDetails,fareType,policy,arrivalDates
  		            };
  		        } else {
  		            String mismatchDetails = String.format(
  		                "Flight result mismatch:\n" +
  		                "- From: expected [%s], actual [%s]\n" +
  		                "- To: expected [%s], actual [%s]\n" +
  		                "- Journey Date: expected [%s], actual [%s]",
  		                From, origin, to, destination, formattedJourneyDate, outputFormat.format(parsedDate)
  		            );
   
  		            Log.ReportEvent("FAIL", mismatchDetails);
  		            System.out.println(mismatchDetails);
  		        }
   
  		    } catch (Exception e) {
  		        Log.ReportEvent("FAIL", "Exception while validating flight results: " + e.getMessage());
  		        System.out.println("Exception: " + e.getMessage());
  		    } finally {
  		        ScreenShots.takeScreenShot1();
  		    }
   
  		    // Default return if validation fails or exception occurs
  		    return new String[] {"", "", "", "", "", "", "", "", ""};
  		}
   
            //Helper Method Validate Flight
  		public String[] validateFlightIsDisplayed(int index, Log Log, ScreenShots ScreenShots) {
  		    try {
  		        // Corrected XPath: indexes in XPath start from 1
  		        WebElement fligtName = driver.findElement(By.xpath("(//*[contains(@class,'tg-flightcarrier')])[" + index + "]"));
  		        WebElement fligtCode = driver.findElement(By.xpath("(//*[contains(@class,'tg-flightnumber')])[" + index + "]"));
   
  		        if (fligtName.isDisplayed() && fligtCode.isDisplayed()) {
  		            String flightNames = fligtName.getText();
  		            String flightCodes = fligtCode.getText();
  		            Log.ReportEvent("PASS", "Flight Details are Displayed: Flight Name: " + flightNames + ", Flight Code: " + flightCodes);
  		            return new String[]{flightNames, flightCodes};
  		        } else {
  		            Log.ReportEvent("FAIL", "Flight details are not displayed at index: " + index);
  		            Assert.fail();
  		        }
  		    } catch (Exception e) {
  		        Log.ReportEvent("FAIL", "Exception while validating flight details: " + e.getMessage());
  		        Assert.fail();
  		    } finally {
  		        ScreenShots.takeScreenShot1();
  		    }
  		    return new String[]{"", ""};  // Return empty strings if validation fails
  		}
   
    //------------------------------------------------------------------------
  	//Method to Validate Footer Div
  		public void validateFooterDiv(String[] resultScreenValidationResults, Log Log, ScreenShots ScreenShots) {
  		    String fromLocation = resultScreenValidationResults[0];
  		    String toLocation = resultScreenValidationResults[1];
  		    String departTime = resultScreenValidationResults[3];
  		    String arrivalTime = resultScreenValidationResults[4];
  		    String expectedPrice = resultScreenValidationResults[6];
  		    String expectedFlightCode = resultScreenValidationResults[8];
  	 
  		    try {
  		        // Extract origin and destination from footer (e.g., "BLR-CJB")
  		        WebElement locations = driver.findElement(By.xpath("//*[@data-tgfullsector]"));
  		        String location = locations.getAttribute("data-tgfullsector");
  		        String[] parts = location.split("-");
  		        String origin = parts[0].trim();
  		        String destination = parts[1].trim();
  	 
  		        // Extract departure and arrival times (e.g., "05:30-07:20")
  		        String times = driver.findElement(By.xpath("//*[@data-tgfulltime]")).getText();
  		        String[] timeParts = times.split("-");
  		        String actualDepartTime = timeParts[0].trim();
  		        String actualArrivalTime = timeParts[1].trim();
  	 
  		        // Extract price
  		        String actualPrice = driver.findElement(By.xpath("//*[@data-tgfullfare]")).getText().trim();
  	 
  		        // Extract flight code from footer
  		        WebElement flightElement = driver.findElement(By.xpath("//*[contains(@class,'tg-owbar-flightnum')]"));
  		        String flightText = flightElement.getText().trim(); // e.g., "(EK 567)"
  		        String actualFlightCode = flightText.replaceAll("[()]", "").trim();
  	 
  		        // Logging actual vs expected
  		        System.out.println("===== Actual Footer Details =====");
  		        System.out.println("From: " + origin);
  		        System.out.println("To: " + destination);
  		        System.out.println("Departure: " + actualDepartTime);
  		        System.out.println("Arrival: " + actualArrivalTime);
  		        System.out.println("Price: " + actualPrice);
  		        System.out.println("Flight Code: " + actualFlightCode);
  	 
  		        System.out.println("===== Expected Details =====");
  		        System.out.println("From: " + fromLocation);
  		        System.out.println("To: " + toLocation);
  		        System.out.println("Departure: " + departTime);
  		        System.out.println("Arrival: " + arrivalTime);
  		        System.out.println("Price: " + expectedPrice);
  		        System.out.println("Flight Code: " + expectedFlightCode);
  	 
  		        // Validation
  		        if (
  		        	    origin.equalsIgnoreCase(fromLocation) &&
  		        	    destination.equalsIgnoreCase(toLocation) &&
  		        	    actualDepartTime.equalsIgnoreCase(departTime) &&
  		        	    actualArrivalTime.equalsIgnoreCase(arrivalTime) &&
  		        	    actualPrice.equalsIgnoreCase(expectedPrice) &&
  		        	    actualFlightCode.replaceAll("[^A-Za-z0-9]", "")
  		        	        .equalsIgnoreCase(expectedFlightCode.replaceAll("[^A-Za-z0-9]", ""))
  		        	)
  	                 {
  		            Log.ReportEvent("PASS", "✅ Flights matched in footer div:\n" +
  		                    "From: " + origin + " -> To: " + destination +
  		                    ", Depart: " + actualDepartTime + ", Arrival: " + actualArrivalTime +
  		                    ", Price: " + actualPrice + ", Flight Code: " + actualFlightCode);
  	 
  		            ScreenShots.takeScreenShot1();
  	 
  		            // Proceed to next step
  		            driver.findElement(By.xpath("//div[@class='bottom-container-1']//button[text()='Continue']")).click();
  		            reasonForSelectionPopUp();
  		        } else {
  		            Log.ReportEvent("FAIL", "❌ Flights did not match. Expected vs Actual:\n" +
  		                    "From: " + fromLocation + " vs " + origin + "\n" +
  		                    "To: " + toLocation + " vs " + destination + "\n" +
  		                    "Depart: " + departTime + " vs " + actualDepartTime + "\n" +
  		                    "Arrival: " + arrivalTime + " vs " + actualArrivalTime + "\n" +
  		                    "Price: " + expectedPrice + " vs " + actualPrice + "\n" +
  		                    "Flight Code: " + expectedFlightCode + " vs " + actualFlightCode);
  	 
  		            ScreenShots.takeScreenShot1();
  		            Assert.fail("Flight footer details mismatch.");
  		        }
  	 
  		    } catch (Exception e) {
  		        Log.ReportEvent("FAIL", "❌ Exception in footer validation: " + e.getMessage());
  		        ScreenShots.takeScreenShot1();
  		        e.printStackTrace();
  		        Assert.fail("Exception during footer validation.");
  		    }
  		}
  		
  		//----------------------------------------------------------------------
  	 
  	//Method to Validate Flight Details Booking Page
  			    public void validateFlightDetailsInBookingPage(String[] resultScreenValidationResults, String[] userInput, Log log, ScreenShots screenShots) {
  			        try {
  			            String expectedClass = userInput[4];
  	 
  			            String expectedFrom = resultScreenValidationResults[0];
  			            String expectedTo = resultScreenValidationResults[1];
  			            String expectedJourneyDate = resultScreenValidationResults[2];
  			            String expectedDepartTime = resultScreenValidationResults[3];
  			            String expectedArrivalTime = resultScreenValidationResults[4];
  			            String expectedPrice = resultScreenValidationResults[6];
  			            String expectedAirlineName = resultScreenValidationResults[7];
  			            String expectedFlightCode = resultScreenValidationResults[9];
  			            String expectedFareType = resultScreenValidationResults[10];
  			            String expectedPolicy = resultScreenValidationResults[11];
  			            String expectedArrivalDate = resultScreenValidationResults[12];
  	 
  			            // Actual values from booking page
  			            String actualFrom = driver.findElement(By.xpath("//*[@data-tgdepartfloriginairport]")).getAttribute("data-tgdepartfloriginairport");
  			            String actualTo = driver.findElement(By.xpath("(//*[@data-tgdepartfldestinationairport])[last()]")).getAttribute("data-tgdepartfldestinationairport");
  	 
  			            String flightDetails = driver.findElement(By.xpath("(//*[@class='tg-fbDepartcarriername'])[1]")).getText().trim();
  	 
  			            String actualAirlineName = "";
  			            String actualFlightCode = "";
  			            String actualClass = "";
  	 
  			            // Split airline info
  			            String[] parts = flightDetails.split(" - ");
  			            if (parts.length == 3) {
  			                actualAirlineName = parts[0].trim();
  			                actualFlightCode = parts[1].trim();
  			                actualClass = parts[2].trim();
  			            } else {
  			                log.ReportEvent("FAIL", "Unexpected flight details format: " + flightDetails);
  			                screenShots.takeScreenShot1();
  			                Assert.fail("Flight details format mismatch.");
  			                return;
  			            }
  	 
  			            String actualDepartDate = driver.findElement(By.xpath("(//*[@data-tgdepartfldepdate])[1]")).getAttribute("data-tgdepartfldepdate");
  			            String actualDepartTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartdeptime')])[1]")).getText().trim();
  			            String actualArrivalDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartarrdate')])[last()]")).getText().trim();
  			            String actualArrivalTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartarrtime')])[last()]")).getText().trim();
  			            String actualPolicy = driver.findElement(By.xpath("//*[contains(@class,'tg-policy')]")).getText().trim();
  			            String actualFareType = driver.findElement(By.xpath("//*[contains(@class,'tg-fb-Departfaretype')]")).getText().trim();
  			            String actualTotalPrice = driver.findElement(By.xpath("//*[contains(@class,'tg-fbgrandtotal')]")).getText().trim();
  	 
  			            // Normalize flight code for comparison
  			            String normExpectedCode = expectedFlightCode.replaceAll("[^A-Za-z0-9]", "");
  			            String normActualCode = actualFlightCode.replaceAll("[^A-Za-z0-9]", "");
  	 
  			            // Normalize fare type by removing spaces and lowercase for comparison
  			            String normExpectedFareType = expectedFareType.replaceAll("\\s", "").toLowerCase();
  			            String normActualFareType = actualFareType.replaceAll("\\s", "").toLowerCase();
  	 
  			            // Trim arrival dates
  			            String normExpectedArrivalDate = expectedArrivalDate.trim();
  			            String normActualArrivalDate = actualArrivalDate.trim();
  	 
  			            if (
  			                actualFrom.equalsIgnoreCase(expectedFrom) &&
  			                actualTo.equalsIgnoreCase(expectedTo) &&
  			                actualAirlineName.equalsIgnoreCase(expectedAirlineName) &&
  			                normActualCode.equalsIgnoreCase(normExpectedCode) &&
  			                actualClass.equalsIgnoreCase(expectedClass) &&
  			                actualDepartDate.equals(expectedJourneyDate) &&
  			                actualDepartTime.equals(expectedDepartTime) &&
  			                normActualArrivalDate.equals(normExpectedArrivalDate) &&
  			                actualArrivalTime.equals(expectedArrivalTime) &&
  			                actualPolicy.equalsIgnoreCase(expectedPolicy) &&
  			                normActualFareType.equals(normExpectedFareType) &&
  			                actualTotalPrice.equals(expectedPrice)
  			            ) {
  			               // log.ReportEvent("PASS", "✅ Booking page flight details matched expected values.");
  			                log.ReportEvent("PASS",
  			                	    "✅ Booking page flight details matched expected values:\n" +
  			                	    "From: expected [" + expectedFrom + "], actual [" + actualFrom + "]\n" +
  			                	    "To: expected [" + expectedTo + "], actual [" + actualTo + "]\n" +
  			                	    "Airline: expected [" + expectedAirlineName + "], actual [" + actualAirlineName + "]\n" +
  			                	    "Flight Code: expected [" + expectedFlightCode + "], actual [" + actualFlightCode + "]\n" +
  			                	    "Class: expected [" + expectedClass + "], actual [" + actualClass + "]\n" +
  			                	    "Depart Date: expected [" + expectedJourneyDate + "], actual [" + actualDepartDate + "]\n" +
  			                	    "Depart Time: expected [" + expectedDepartTime + "], actual [" + actualDepartTime + "]\n" +
  			                	    "Arrival Date: expected [" + expectedArrivalDate + "], actual [" + actualArrivalDate + "]\n" +
  			                	    "Arrival Time: expected [" + expectedArrivalTime + "], actual [" + actualArrivalTime + "]\n" +
  			                	    "Policy: expected [" + expectedPolicy + "], actual [" + actualPolicy + "]\n" +
  			                	    "Fare Type: expected [" + expectedFareType + "], actual [" + actualFareType + "]\n" +
  			                	    "Total Price: expected [" + expectedPrice + "], actual [" + actualTotalPrice + "]"
  			                	);
  	 
  			            } else {
  			                log.ReportEvent("FAIL", "❌ Booking page flight details mismatch:\n" +
  			                    "From: expected [" + expectedFrom + "], actual [" + actualFrom + "]\n" +
  			                    "To: expected [" + expectedTo + "], actual [" + actualTo + "]\n" +
  			                    "Airline: expected [" + expectedAirlineName + "], actual [" + actualAirlineName + "]\n" +
  			                    "Flight Code: expected [" + expectedFlightCode + "], actual [" + actualFlightCode + "]\n" +
  			                    "Class: expected [" + expectedClass + "], actual [" + actualClass + "]\n" +
  			                    "Depart Date: expected [" + expectedJourneyDate + "], actual [" + actualDepartDate + "]\n" +
  			                    "Depart Time: expected [" + expectedDepartTime + "], actual [" + actualDepartTime + "]\n" +
  			                    "Arrival Date: expected [" + expectedArrivalDate + "], actual [" + actualArrivalDate + "]\n" +
  			                    "Arrival Time: expected [" + expectedArrivalTime + "], actual [" + actualArrivalTime + "]\n" +
  			                    "Policy: expected [" + expectedPolicy + "], actual [" + actualPolicy + "]\n" +
  			                    "Fare Type: expected [" + expectedFareType + "], actual [" + actualFareType + "]\n"+
  			                    "Total Price : expected [" + actualTotalPrice + "], actual [" + expectedPrice + "]")
  			                ;
  			                
  			                screenShots.takeScreenShot1();
  			                Assert.fail("Booking page flight details mismatch.");
  			            }
  			            
  			            
  			        } catch (Exception e) {
  			            log.ReportEvent("FAIL", "❌ Exception during booking page flight details validation: " + e.getMessage());
  			            screenShots.takeScreenShot1();
  			            e.printStackTrace();
  			            Assert.fail("Exception occurred during validation.");
  			        }
  			    }
  	 
  	 
   //------------------------------------------------------------------
  			    
  			//Method to Validate approval page
    			  public void approvalPageValidation(String origin, String destination, String travels, Log Log, ScreenShots ScreenShots, String profile[]) {
    			      try {
    			          travels = travels.replaceAll("s$", ""); // Remove trailing 's' if present
    			   
    			          String employeeCode = profile[0];
    			          String approvalManager = profile[1];
    			          String traveller = profile[2];
    			          String travellerWithCode = traveller + " (" + employeeCode + ")";
    			          String routeToFind = origin + " - " + destination;
    			   
    			          System.out.println("Looking for route: " + routeToFind);
    			   
    			          // Wait for approval cards to be present
    			          new WebDriverWait(driver, Duration.ofSeconds(5))
    			              .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'tg-approval-requests')]")));
    			   
    			          List<WebElement> cards = driver.findElements(By.xpath("//div[contains(@class,'tg-approval-requests')]"));
    			   
    			          boolean matchFound = false;
    			   
    			          for (WebElement card : cards) {
    			              try {
    			                  String route = card.findElement(By.xpath(".//span[text()='Origin - Destination']/following-sibling::h6")).getText().trim();
    			                  System.out.println("Found route: " + route);
    			   
    			                  if (route.equalsIgnoreCase(routeToFind)) {
    			                      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", card);
    			                      new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(card));
    			   
    			                      String travelType = card.findElement(By.xpath(".//*[text()='Request Type']/following-sibling::h6")).getText().trim();
    			                      String approverName = card.findElement(By.xpath(".//*[text()='Approver Name']/following-sibling::h6")).getText().trim();
    			                      String requestedBy = card.findElement(By.xpath(".//*[text()='Requested By']/following-sibling::h6")).getText().trim();
    			   
    			                      System.out.println("Validating travelType: " + travelType + " == " + travels);
    			                      System.out.println("Approver: " + approverName + " == " + approvalManager);
    			                      System.out.println("Requested By: " + requestedBy + " == " + travellerWithCode);
    			   
    			                      if (travelType.equalsIgnoreCase(travels)
    			                              && approverName.equalsIgnoreCase(approvalManager)
    			                              && requestedBy.equalsIgnoreCase(travellerWithCode)) {
    			   
    			                          Log.ReportEvent("PASS", "✅ Sent approval details are correct:\n"
    			                                  + "Flight Type: " + travelType + "\n"
    			                                  + "Approver Name: " + approverName + "\n"
    			                                  + "Requested By: " + requestedBy);
    			   
    			                          ScreenShots.takeScreenShot1();
    			                      }
    			   
    			                      WebElement detailsBtn = card.findElement(By.xpath(".//button[text()='Details']"));
    			                      detailsBtn.click();
    			                      System.out.println("✅ Clicked Details for: " + routeToFind);
    			   
    			                      matchFound = true;
    			                      break;
    			                  }
    			              } catch (NoSuchElementException e) {
    			                  System.err.println("⚠️ Element missing in card, skipping. Details: " + e.getMessage());
    			              } catch (Exception e) {
    			                  System.err.println("⚠️ Unexpected error in card loop: " + e.getMessage());
    			                  e.printStackTrace();
    			              }
    			          }
    			   
    			          if (!matchFound) {
    			              Log.ReportEvent("FAIL", "❌ No matching approval card found for route: " + routeToFind);
    			              ScreenShots.takeScreenShot1();
    			          }
    			   
    			      } catch (Exception e) {
    			          Log.ReportEvent("FAIL", "❌ Exception occurred during approval page validation: " + e.getMessage());
    			          ScreenShots.takeScreenShot1();
    			          e.printStackTrace();
    			      }
    			  }	    
  //--------------------------------------------------------------------
    	  
	}
	

	
	
	
	
	

       

