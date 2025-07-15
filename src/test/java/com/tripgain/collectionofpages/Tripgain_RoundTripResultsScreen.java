package com.tripgain.collectionofpages;

import java.awt.AWTException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import org.openqa.selenium.NoSuchElementException;
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
import static com.tripgain.collectionofpages.BaggageDetails.getBaggageDetailsManual;


public class Tripgain_RoundTripResultsScreen {
	WebDriver driver;

	public Tripgain_RoundTripResultsScreen(WebDriver driver) {

		PageFactory.initElements(driver, this);
		this.driver=driver;
	}
	
	public void clickOnClearFilter()
	{
		driver.findElement(By.xpath("//button[text()='Clear Filters']")).click();
	}
	//Method To Get Default Price Range Of Slider
	 public double[] defaultPriceRangeOfSlider(WebDriver driver, Log Log, ScreenShots ScreenShots) {
	        try {
	            Thread.sleep(8000);
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	            WebElement minSliderInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//input[@data-index='0']")));

	            double minValue = Double.parseDouble(minSliderInput.getAttribute("aria-valuemin"));
	            double maxValue = Double.parseDouble(minSliderInput.getAttribute("aria-valuemax"));

	            System.out.println("Min value: " + minValue);
	            System.out.println("Max value: " + maxValue);

	            if (minValue >= 0 && maxValue > minValue) {
	                String message = "PASS: Default price range displayed is: Min = " + minValue + ", Max = " + maxValue;
	                Log.ReportEvent("PASS", message);
	            } else {
	                String message = "FAIL: Default price range is invalid. Min = " + minValue + ", Max = " + maxValue;
	                Log.ReportEvent("FAIL", message);
	                Assert.fail(message);
	            }

	            ScreenShots.takeScreenShot1();
	            return new double[]{minValue, maxValue}; // ✅ return values here

	        } catch (Exception e) {
	            Log.ReportEvent("ERROR", "Exception while checking default price range: " + e.getMessage());
	            e.printStackTrace();
	            ScreenShots.takeScreenShot1();
	            Assert.fail("Exception occurred during price range validation");
	            return new double[0]; // Required fallback for compilation
	        }
	    }
    
    
    //---------------------------------------------------------------------------------------------------------
    
//    public void verifyroundtripPriceRangeValuesOnResultScreen(Log Log, ScreenShots ScreenShots, int index) {
//        try {
//            Thread.sleep(3000);
//
//            String minPriceValue = driver.findElement(By.xpath("//input[@data-index='0']")).getAttribute("aria-valuenow");
//            String maxPriceValue = driver.findElement(By.xpath("//input[@data-index='1']")).getAttribute("aria-valuenow");
//
//            String priceRangeFrom = driver.findElement(By.xpath("(//div[contains(@class, 'round-trip-from-results')]//h3[contains(@class, 'price')])[1]")).getText();
//            String priceRangeTo = driver.findElement(By.xpath("(//div[contains(@class, 'round-trip-to-results')]//h3[contains(@class, 'price')])[1]")).getText();
//
//            int min = Integer.parseInt(minPriceValue);
//            int max = Integer.parseInt(maxPriceValue);
//            int priceFrom = extractPrice1(priceRangeFrom, "FROM");
//            int priceTo = extractPrice1(priceRangeTo, "TO");
//
//            System.out.println("Slider Min: " + min);
//            System.out.println("Slider Max: " + max);
//            System.out.println("Flight Price FROM (first): " + priceFrom);
//            System.out.println("Flight Price TO (first): " + priceTo);
//
//            List<WebElement> FromFlights = driver.findElements(By.xpath("//div[@class='round-trip-from-results']//div[@class='frc-price']//h3"));
//            List<WebElement> ToFlights = driver.findElements(By.xpath("//div[@class='round-trip-to-results']//div[@class='frc-price']//h3"));
//
//            // ✅ Loop through all FROM prices and print validation result
//            for (int i = 0; i < FromFlights.size(); i++) {
//                String priceText = FromFlights.get(i).getText();
//                int price = extractPrice1(priceText, "FROM");
//
//                boolean isWithinRange = price >= min && price <= max;
//
//                System.out.println("Flight " + (i + 1) + " FROM price: ₹" + price + " — " + (isWithinRange ? "WITHIN" : "OUT OF") + " range (" + min + " - " + max + ")");
//            }
//
//            // ✅ Loop through all TO prices and print validation result
//            for (int i = 0; i < ToFlights.size(); i++) {
//                String priceText = ToFlights.get(i).getText();
//                int price = extractPrice1(priceText, "TO");
//
//                boolean isWithinRange = price >= min && price <= max;
//
//                System.out.println("Flight " + (i + 1) + " TO price: ₹" + price + " — " + (isWithinRange ? "WITHIN" : "OUT OF") + " range (" + min + " - " + max + ")");
//            }
//
//            // ✅ Validate and log the first FROM and TO prices
//            boolean fromValid = priceFrom >= min && priceFrom <= max;
//            boolean toValid = priceTo >= min && priceTo <= max;
//
//            if (fromValid) {
//                Log.ReportEvent("PASS", "FROM price ₹" + priceFrom + " is within range ₹" + min + " - ₹" + max);
//            } else {
//                Log.ReportEvent("FAIL", "FROM price ₹" + priceFrom + " is NOT within range ₹" + min + " - ₹" + max);
//            }
//
//            if (toValid) {
//                Log.ReportEvent("PASS", "TO price ₹" + priceTo + " is within range ₹" + min + " - ₹" + max);
//            } else {
//                Log.ReportEvent("FAIL", "TO price ₹" + priceTo + " is NOT within range ₹" + min + " - ₹" + max);
//            }
//
//            if (!fromValid || !toValid) {
//                Assert.fail("Flight price is not within selected slider range.");
//            }
//
//            ScreenShots.takeScreenShot1();
//
//        } catch (Exception e) {
//            Log.ReportEvent("FAIL", "Exception during price validation: " + e.getMessage());
//            ScreenShots.takeScreenShot1();
//            e.printStackTrace();
//            Assert.fail("Exception occurred during price validation");
//        }
//    }
//    // ✅ Move this outside the method, anywhere inside the class
//    private int extractPrice1(String text, String label) throws Exception {
//        Pattern pattern = Pattern.compile("(₹|INR)\\s?\\d{1,3}(,\\d{3})*");
//        Matcher matcher = pattern.matcher(text);
//        if (matcher.find()) {
//            String rawPrice = matcher.group();
//            return Integer.parseInt(rawPrice.replaceAll("[^0-9]", ""));
//        } else {
//            throw new Exception("Could not extract " + label + " price from text: " + text);
//        }
//    }
    
    //----------------------------------------------------------------------------------------------------------
    
    //Method to click check In baggage round trip
    public void clickroundtripcheckinbaggage() {
   	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
   	    JavascriptExecutor js = (JavascriptExecutor) driver;

   	    try {
   	        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(
   	            By.xpath("//legend[text()='CHECK-IN BAGGAGE']/parent::div//input[@type='checkbox']")));

   	        // Scroll into view (helpful if covered by sticky headers)
   	        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);

   	        // Use JS to click (bypasses invisible/overlay issues)
   	        js.executeScript("arguments[0].click();", checkbox);

   	        System.out.println("Clicked CHECK-IN BAGGAGE checkbox");
   	    } catch (Exception e) {
   	        System.out.println("Failed to click CHECK-IN BAGGAGE checkbox");
   	        e.printStackTrace();
   	    }
   	}

    
    //method to validate check in baggage round trip
   /* public void validatecheckinbaggageroundtrip(Log log, ScreenShots screenshots, int index) {
   	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
   	    
   	    try {
   	    	Thread.sleep(4000);
   	        // Wait for and click flight card
   	        String xpath = "(//div[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]";

   	        
   	        // Wait for and click View Flight button
   	        WebElement viewflightbutton = wait.until(ExpectedConditions.elementToBeClickable(
   	            By.xpath("//button[text()='View Flight']")));
   	       
   	        viewflightbutton.click();
   	        
   	        // Wait for and validate baggage text
   	        WebElement baggageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
   	            By.xpath("//span[text()='Check-in baggage:']//strong"))); 
       	        //screenshots.takeScreenShot1();

   	      
   	        String baggageText = baggageElement.getText();
   	        log.ReportEvent("PASS", "Selected flight " + "Index: "+index + " with baggage: " + baggageText);
   	        screenshots.takeScreenShot1();

   	        // Wait for and click Close button
   	        WebElement closeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
   	            By.xpath("//button[text()='Close']")
   	        ));
   	        closeButton.click();
   	        
   	    } catch (Exception e) {
   	        log.ReportEvent("FAIL", "Error selecting flight " + index + ": " + e.getMessage());
   	        screenshots.takeScreenShot1();
   	    }
   	}
    
    
    public void validatecheckinbaggageroundtrip(Log log, ScreenShots screenshots, int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));

        try {
            Thread.sleep(4000);

            // Wait for and click flight card
            String xpath = "(//div[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]";

            // Wait for and click View Flight button
            WebElement viewflightbutton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(xpath)));
            viewflightbutton.click();

            // Wait for and validate baggage text
            WebElement baggageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='Check-in baggage:']//strong")));

            List<WebElement> checkInBaggage = driver.findElements(By.xpath("//span[text()='Check-in baggage:']//strong"));

            for (WebElement element : checkInBaggage) {
                String checkInBaggageText = element.getText();
                if (checkInBaggageText.contains("0PC") || checkInBaggageText.contains("0KG")) {
                    System.out.println("Fail,Check-in baggage is not available");
                } else {
                    System.out.println("Pass,Check-in baggage is available");
                }
            }

            log.ReportEvent("PASS", "Selected flight Index: " + index + " with baggage: " + baggageElement.getText());
            screenshots.takeScreenShot1();

            // Wait for and click Close button
            WebElement closeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[text()='Close']")));
            closeButton.click();

        } catch (Exception e) {
            log.ReportEvent("FAIL", "Check In Baggage flights are Not displayed on Result Screen");
            screenshots.takeScreenShot1();
            e.printStackTrace();
            Assert.fail();
        }
    }*/

    //or
    
  //Method to validate Check In Baggage Functionality
  
 
 //method to validate return checkin baggage 
    public void validatereturnCheckInBaggageFlightsOnResultScreenRoundTrip(Log Log, ScreenShots ScreenShots, int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Click the correct flight using index
            String xpath = "(//div[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]";
        	
            WebElement viewflightbutton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

            // Scroll into view before clicking
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewflightbutton);
            Thread.sleep(500); // Optional wait for scroll to settle

            try {
                viewflightbutton.click(); // Try normal click first
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", viewflightbutton);
            }

            // Wait for baggage
            List<WebElement> checkInBaggage = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//span[text()='Check-in baggage:']//strong")));

            // Scroll and validate each baggage element
            for (WebElement element : checkInBaggage) {
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
                Thread.sleep(500); 
                String checkInBaggageText = element.getText();

                if (checkInBaggageText.contains("0PC") || checkInBaggageText.contains("0KG")) {
                    System.out.println("Fail,  Flights Check-in baggage is not available");
                    Log.ReportEvent("FAIL", " Flights Check In Baggage flights are Not displayed on Result Screen");
                    ScreenShots.takeScreenShot1();

                } else {
                    System.out.println("Pass,  Flights Check-in baggage is available");
                    Log.ReportEvent("Pass", " Flights Check In Baggage flights are displayed on Result Screen");

                }
            }

        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Check In Baggage flights are Not displayed on Result Screen");
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
            Assert.fail();
        }
        closeButtononresultpage();
    }
    
    //or
    
    public void validatereturncheckinbaggageroundtrip(Log log, ScreenShots screenshots, int index) {
   	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
   	    
   	    try {
   	        Thread.sleep(4000);

   	        String xpath = "//div[@class='round-trip-to-results']//button[text()='View Flight'])[" + index + "]";

   	        WebElement viewflightbutton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

   	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", viewflightbutton);
   	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewflightbutton);

   	        // Validate baggage info
   	        WebElement baggageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
   	            By.xpath("//span[text()='Check-in baggage:']")));

   	        String baggageText = baggageElement.getText();
   	        log.ReportEvent("PASS", "Selected return flight Index: " + index + " with baggage: " + baggageText);

   	        // Close modal
   	        WebElement closeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
   	            By.xpath("//button[text()='Close']")));
   	        closeButton.click();

   	    } catch (Exception e) {
   	        log.ReportEvent("FAIL", "Error selecting return flight " + index + ": " + e.getMessage());
   	        screenshots.takeScreenShot1();
   	    }
        closeButtononresultpage();

   	}
    
    
 
 //-----------------------------------------------------------------------------------------------------
    
    //Method to click Refundable Fare round trip
    public void clickRefundableFareRoundTrip() {
   	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
   	    JavascriptExecutor js = (JavascriptExecutor) driver;

   	    try {
   	        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(
   	            By.xpath("//*[contains(@class,'tg-flRefundablefare')]//input[@type='checkbox']")));
   	        // Scroll into view (helpful if covered by sticky headers)
   	        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);

   	        // Use JS to click (bypasses invisible/overlay issues)
   	        js.executeScript("arguments[0].click();", checkbox);

   	        System.out.println("Clicked Refundable Fare Checkbox");
   	    } catch (Exception e) {
   	        System.out.println("Failed to click Refundable Fare checkbox");
   	        e.printStackTrace();
   	    }
   	    
    }
    
   	    //Method to validate Refundable fare roundtrip
   	    
    public void validateRefundableFare(int index,Log Log, ScreenShots ScreenShots) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Step 1: Click "View Flight" button
            String xpathExpression = "(//*[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]";
            WebElement viewFlightBtn = driver.findElement(By.xpath(xpathExpression));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewFlightBtn);
            Thread.sleep(1000);
            viewFlightBtn.click();
            Thread.sleep(3000); // Wait for fare options to appear

            // Logging initial click
            Log.ReportEvent("INFO", "Clicked on 'View Flight' for index: " + index);

            // Step 2: Get list of refundable fare texts
            List<WebElement> refundableFares = driver.findElements(By.xpath("//*[contains(@class,'tg-fare-refundable')]"));
            Log.ReportEvent("INFO", "Refundable fares found: " + refundableFares.size());

            boolean isRefundableFound = false;

            for (WebElement fare : refundableFares) {
                String fareText = fare.getText().trim();
                Log.ReportEvent("INFO", "Fare Text: " + fareText);

                if (fareText.contains("Refundable")) {
                    isRefundableFound = true;
                    break;
                }
            }

            // Step 3: Close the modal
            driver.findElement(By.xpath("//button[text()='Close']")).click();
            Thread.sleep(1000);
            Log.ReportEvent("INFO", "Closed fare details.");

            // Step 4: Validation
            if (isRefundableFound) {
                Log.ReportEvent("PASS", "Refundable fares found.");
            } else {
                Log.ReportEvent("FAIL", "No refundable fares found.");
            }

        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Exception occurred: " + e.getMessage());
            ScreenShots.takeScreenShot1();
            throw e; // Optional: rethrow if you want the test to fail
        }
    }

 
 //Method to validate UncheckIn refundable fare round trip
	    
   	public void validateUnCheckInRefundablefarefilterRoundtrip(Log Log, ScreenShots ScreenShots) {
   	    try {
   	        // Wait for page to load
   	        Thread.sleep(5000);
   	        
   	        // 1. Get and display INITIAL count
   	        String initialCount = driver.findElement(By.xpath("//span[@id='onward_flight_count']")).getText();
   	        System.out.println("Initial Flight Count: " + initialCount);
   	        Log.ReportEvent("INFO", "Initial Flight Count: " + initialCount);
   	        
   	        // 2. Click the checkbox
   	        WebElement checkbox = driver.findElement(By.xpath("//legend[text()='REFUNDABLE FARE']/parent::div//input[@type='checkbox']"));
   	        checkbox.click();
   	        Thread.sleep(3000);
   	        
   	        // 3. Get and display count AFTER CHECKING
   	        String checkedCount = driver.findElement(By.xpath("//span[@id='onward_flight_count']")).getText();
   	        System.out.println("Count After Checking: " + checkedCount);
   	        Log.ReportEvent("INFO", "Count After Checking: " + checkedCount);
   	        
   	        // 4. Uncheck the checkbox
   	        checkbox.click();
   	        Thread.sleep(3000);
   	        
   	        // 5. Get and display count AFTER UNCHECKING
   	        String uncheckedCount = driver.findElement(By.xpath("//span[@id='onward_flight_count']")).getText();
   	        System.out.println("Count After Unchecking: " + uncheckedCount);
   	        Log.ReportEvent("INFO", "Count After Unchecking: " + uncheckedCount);
   	        
   	    } catch (Exception e) {
   	        System.out.println("Error displaying flight counts: " + e.getMessage());
   	        Log.ReportEvent("FAIL", "Error displaying flight counts: " + e.getMessage());
   	        ScreenShots.takeScreenShot1();
   	    }
   	}
 
   	//----------------------------------------------------------------------------------------
   	 //method to validate check in baggage round trip for International
       public void validatecheckinbaggageroundtripInternational(Log log, ScreenShots screenshots, int index) {
      	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
      	    
      	    try {
      	    	Thread.sleep(4000);
      	    	driver.findElement(By.xpath("(//div[@role='button']//button[text()='Select'])[" + index + "]")).click();
      	    	
      	    }catch(Exception e) {
      	    }
      	    }
       
       
     //---------------------------------------------------------------------------------------------------------
  
       //Method to select airlines roundtrip
      
       public void ValidateAirlineSelectionRoundtrip(Log log, ScreenShots screenshots, String airlineToSelect) {
           try {
               WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

               // Step 1: Scroll to AIRLINES section
               WebElement airlineSection = wait.until(ExpectedConditions.visibilityOfElementLocated(
                   By.xpath("//legend[text()='AIRLINES']/parent::div")));
               ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", airlineSection);
               log.ReportEvent("PASS", "Scrolled to AIRLINES section");
Thread.sleep(3000);        
// Step 2: Get all airline names from filter section
               List<WebElement> airlineItems = driver.findElements(
                   By.xpath("//legend[text()='AIRLINES']/parent::div//ul/li"));

               List<String> airlineNames = new ArrayList<>();  // empty list to store the airline names.
               for (WebElement item : airlineItems) {
                   String name = item.findElement(By.className("tg-airline-name")).getText().trim();
                   airlineNames.add(name);
               }

               log.ReportEvent("INFO", "Available Airlines: " + airlineNames);

               // Step 3: Split input into array if multiple airlines are provided
             String[] airlinesToSelectArray = airlineToSelect.split("\\s*,\\s*");

               // Step 4: Select each airline
               for (String airline : airlinesToSelectArray) {    //Iterates over each airline name provided by the user
                   if (!airlineNames.contains(airline)) {
                       log.ReportEvent("FAIL", "Airline not found in filter list: " + airline);
                       continue; // Skip to next airline instead of returning
                   }

                   WebElement airlineItem = wait.until(ExpectedConditions.presenceOfElementLocated(
                       By.xpath("//legend[text()='AIRLINES']/parent::div//ul/li[.//span[text()='" + airline + "']]")));

                   WebElement checkbox = airlineItem.findElement(By.xpath(".//input[@type='checkbox']"));
Thread.sleep(2000);
                   ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
                   Thread.sleep(3000);
                   ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
                   Thread.sleep(2000); 
                   boolean isSelected = (Boolean) ((JavascriptExecutor) driver)
                       .executeScript("return arguments[0].checked;", checkbox);

                   if (isSelected) {
                	   Thread.sleep(3000);
                       log.ReportEvent("PASS", "Successfully selected airline: " + airline);
                   } else {
                       log.ReportEvent("FAIL", "Failed to select airline: " + airline);
                   }
                   screenshots.takeScreenShot1();
               }

               // Step 5: Validate the results based on the user input
               boolean isValid = true;
            //   String[] airlinesToValidateArray = airlineToSelect.split("\\s*,\\s*");

               // Validate that each airline appears in the results
               for (String airline : airlinesToSelectArray) {
                   // Use the XPath to check if the airline appears in the results
               	List<WebElement> result = driver.findElements(
               		    By.xpath("//small[text()='" + airline + "']"));
                   // Check if result element is found
                   if (result != null) {
                	   Thread.sleep(2000);
                       log.ReportEvent("PASS", "Airline " + airline + " is present in the results.");
                   } else {
                       log.ReportEvent("FAIL", "Airline " + airline + " is NOT present in the results.");
                       isValid = false;
                   }
               }

               if (isValid) {
                   log.ReportEvent("PASS", "All selected airlines are correctly displayed in the results.");
               } else {
                   log.ReportEvent("FAIL", "Some selected airlines are missing from the results.");
               }
               screenshots.takeScreenShot1();

           } catch (Exception e) {
               log.ReportEvent("FAIL", "Error in selecting or validating airlines: " + e.getMessage());
               screenshots.takeScreenShot1();
               e.printStackTrace();
           }
       }
       
       //or
       
       //Method to validate Airlines based on index for RoundTrip
       public void validateAirLines(int index, String expectedAirline, Log Log, ScreenShots ScreenShots) {
           try {
               // Get all airline elements
               List<WebElement> airlines = driver.findElements(By.xpath("//span[@data-tgflcarriername]"));
               boolean found = false;

               // Loop through each element
               for (WebElement airlineElement : airlines) {
                   String rawText = airlineElement.getText().trim(); // e.g., "Indigo - (6E 6562) - economy"

                   // Extract only the airline name before " - "
                   String actualAirline = rawText.split(" - ")[0].trim();

                   System.out.println("Expected: " + expectedAirline);
                   System.out.println("Found: " + actualAirline);

                   // Compare with expected
                   if (actualAirline.equalsIgnoreCase(expectedAirline.trim())) {
                       System.out.println("✅ Expected airline is shown: " + actualAirline);
                       found = true;
                       break;
                   }
               }

               // Report result
               if (found) {
                   Log.ReportEvent("PASS", "✅ Expected airline is showing: " + expectedAirline);
               } else {
                   Log.ReportEvent("FAIL", "❌ Expected airline NOT found: " + expectedAirline);
                   ScreenShots.takeScreenShot1();
                   Assert.fail("Airline not found in the results.");
               }

           } catch (Exception e) {
               Log.ReportEvent("FAIL", "❌ Exception during airline validation: " + e.getMessage());
               ScreenShots.takeScreenShot1();
               e.printStackTrace();
               Assert.fail("Exception occurred during airline validation.");
           }
       }
       
       //-----------------------------------------------------------
       
     //Method for Round Trip Select AirLines
       public void selectAirLines(String... airlines) {
           WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
           JavascriptExecutor js = (JavascriptExecutor) driver;

           for (String airline : airlines) {
               String trimmedAirline = airline.trim();

               WebElement airlineOption = wait.until(ExpectedConditions.presenceOfElementLocated(
                       By.xpath("//legend[text()='AIRLINES']//parent::div//span[text()='" + trimmedAirline + "']//parent::div/parent::li//input")));

               // Scroll into view and click via JavaScript
               js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", airlineOption);
               js.executeScript("arguments[0].click();", airlineOption);
           }
       }
       
       
       

       //-----------------------------------------------------------------------
  
  	 
  	                       //Method to validate flight count
  	 
public void validateflightsInitialcount(Log Log, ScreenShots ScreenShots) {
   try {
	        // 1. Get and display INITIAL count
       String initialCount = driver.findElement(By.xpath("//span[@id='onward_flight_count']")).getText();
     String returncount=driver.findElement(By.xpath("//span[@id='return_flight_count']")).getText();
       System.out.println("Initial Flight Count: " + initialCount);
       System.out.println("Return Flight Count: " + returncount);

       Log.ReportEvent("INFO", "Initial Flight Count: " + initialCount);
       Log.ReportEvent("INFO", "Return Flight Count: " + returncount);

   } catch (Exception e) {
       System.out.println("Error displaying flight counts: " + e.getMessage());
       Log.ReportEvent("FAIL", "Error displaying flight counts: " + e.getMessage());
       ScreenShots.takeScreenShot1();
   }
}


public void validateflightsaftercount(Log Log, ScreenShots ScreenShots) {
       //Get and display count AFTER CHECKING
	 try {
    String initialCount = driver.findElement(By.xpath("//span[@id='onward_flight_count']")).getText();
    String returncount=driver.findElement(By.xpath("//span[@id='return_flight_count']")).getText();

    System.out.println("Initial Flight Count: " + initialCount);
    System.out.println("Return Flight Count: " + returncount);

    Log.ReportEvent("INFO", "Initial Flight Count: " + initialCount);
    Log.ReportEvent("INFO", "Return Flight Count: " + returncount);

} catch (Exception e) {
    System.out.println("Error displaying flight counts: " + e.getMessage());
    Log.ReportEvent("FAIL", "Error displaying flight counts: " + e.getMessage());
    ScreenShots.takeScreenShot1();
}
}

public void validateflightsInitialcountInternational(Log Log, ScreenShots ScreenShots) {
   try {
	        // 1. Get and display INITIAL count
       String initialCount = driver.findElement(By.xpath("//*[@id='flight_count']")).getText();
       System.out.println("Initial Flight Count: " + initialCount);

       Log.ReportEvent("INFO", "Initial Flight Count: " + initialCount);

   } catch (Exception e) {
       System.out.println("Error displaying flight counts: " + e.getMessage());
       Log.ReportEvent("FAIL", "Error displaying flight counts: " + e.getMessage());
       ScreenShots.takeScreenShot1();
   }
}


	 

public void validateflightsaftercountInternational(Log Log, ScreenShots ScreenShots) {
       //Get and display count AFTER CHECKING
	 try {
    String initialCount = driver.findElement(By.xpath("//*[@id='flight_count']")).getText();

    System.out.println("Initial Flight Count: " + initialCount);

    Log.ReportEvent("INFO", "Initial Flight Count: " + initialCount);

} catch (Exception e) {
    System.out.println("Error displaying flight counts: " + e.getMessage());
    Log.ReportEvent("FAIL", "Error displaying flight counts: " + e.getMessage());
    ScreenShots.takeScreenShot1();
}
}


//---------------------------------------------------------------------------------------------------

//Method for policy filter roundtrip

public void clickpolicyfilterRoundtrip() {
	driver.findElement(By.xpath("//*[contains(@class,'tg-inpolicy')]//input[@name='in']")).click();
	
}

//Method to validate InPolicy Fliter roundtrip

public void validateInPolicyFilterRoundtrip(Log log, ScreenShots screenshots) {
   try {
       // Find all elements with text "In Policy" or "Out of Policy"
       List<WebElement> policyElements = driver.findElements(By.xpath("//div[text()='In Policy' or text()='Out of Policy']"));

       //Loop through each element and print its text
       boolean onlyInPolicy = true;   //all are In policy--true

       for (WebElement policy : policyElements) {
           String text = policy.getText();
           System.out.println("Policy found: " + text);

           //If any policy is not "In Policy", set to false
           if (!text.equals("In Policy")) {
               onlyInPolicy = false;  
           }
       }

       //validation 
       if (policyElements.isEmpty()) {
           log.ReportEvent("FAIL", "No policy labels found on the page");
       } else if (onlyInPolicy) {
           log.ReportEvent("PASS", "All flights are In Policy");
       } else {
           log.ReportEvent("PASS", "Some flights are Out of Policy");
       }


   } catch (Exception e) {
       log.ReportEvent("FAIL", "Exception occurred: " + e.getMessage());
       screenshots.takeScreenShot1();
       e.printStackTrace();
   }
   
}


public void validatePolicy(Log Log, ScreenShots ScreenShots, String expectedValue) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollTo(0, 0);");

    try {
        String lowerExpected = expectedValue.trim().toLowerCase();

        // Try to match InPolicy first
        List<WebElement> inPolicyElements = driver.findElements(By.xpath("//div[@class='inpolicy']"));
        List<WebElement> outOfPolicyElements = driver.findElements(By.xpath("//div[@class='outofpolicy']"));

        List<WebElement> matchingElements = new ArrayList<>();

        if (!inPolicyElements.isEmpty()) {
            for (WebElement el : inPolicyElements) {
                if (el.getText().trim().equalsIgnoreCase(expectedValue)) {
                    matchingElements.add(el);
                }
            }
        }

        if (matchingElements.isEmpty() && !outOfPolicyElements.isEmpty()) {
            for (WebElement el : outOfPolicyElements) {
                if (el.getText().trim().equalsIgnoreCase(expectedValue)) {
                    matchingElements.add(el);
                }
            }
        }

        if (matchingElements.isEmpty()) {
            Log.ReportEvent("FAIL", "❌ No matching policy elements found with the expected value: " + expectedValue);
            ScreenShots.takeScreenShot1();
            Assert.fail("No matching policies found.");
            return;
        }

        // Verify all matched elements are correct
        boolean allMatch = true;
        List<String> mismatched = new ArrayList<>();

        for (WebElement policy : matchingElements) {
            String text = policy.getText().trim();
            System.out.println("Policy Text: " + text);
            if (!text.equalsIgnoreCase(expectedValue)) {
                mismatched.add(text);
                allMatch = false;
            }
        }

        if (allMatch) {
            Log.ReportEvent("PASS", "✅ All displayed policies are '" + expectedValue + "'.");
        } else {
            Log.ReportEvent("FAIL", "❌ Some policies are not '" + expectedValue + "'. Mismatched: " + mismatched);
            ScreenShots.takeScreenShot1();
            Assert.fail("One or more policies do not match expected value.");
        }


    } catch (Exception e) {
        Log.ReportEvent("FAIL", "❌ Exception while checking policies: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
        Assert.fail("Exception occurred during policy validation.");
    }
}


//----------------------------------RoundTrip------------------------

//Method to validate Airline Sorting starting
public void ValidateAirlineSortingStarting(String Name,Log Log,ScreenShots ScreenShots) throws InterruptedException 
{
	 try {
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(100));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='"+Name+"']")));
         driver.findElement(By.xpath("//small[text()='"+Name+"']")).click();
         Thread.sleep(5000);
         boolean value=driver.findElement(By.xpath("//small[contains(text(), 'AIRLINE')]/preceding::*[@data-testid='SortDescendingIcon']")).isDisplayed();
         Thread.sleep(5000);
         if(Name.contentEquals("AIRLINE ")&& value==true)
         {
             System.out.println("--------------------------Airlines List in Descending Order----------------------------------");
             List<WebElement>numberOfAirlines=driver.findElements(By.xpath("//div[@class='carreir-info']"));
             int numberOfRecords=numberOfAirlines.size();            
             System.out.println("Number of Records present in an Homepage=="+numberOfRecords);
             for(WebElement airlineNames:numberOfAirlines)
             {
                 System.out.println(airlineNames.getText());
             }
             Log.ReportEvent("PASS", "Airline data is Sorted Descending Order is Successful");
         }
         System.out.println("--------------------------Airlines List in Ascending Order-------------------------------------");              
         Thread.sleep(5000);
         driver.findElement(By.xpath("//small[text()='"+Name+"']")).click();
         driver.findElement(By.xpath("//small[contains(text(), 'AIRLINE')]/preceding::*[@data-testid='SortAscendingIcon']")).isDisplayed();
         List<WebElement>numberOfAirlines=driver.findElements(By.xpath("//div[@class='carreir-info']"));
         for(WebElement airlineNames:numberOfAirlines)
         {
             System.out.println(airlineNames.getText());
         }    
         Log.ReportEvent("PASS", "Airline data is Sorted Descending Order is Successful");
     }
     catch(Exception e)
     {
         Log.ReportEvent("FAIL", "Airline data is not Sorted Successful");
         ScreenShots.takeScreenShot1();
         e.printStackTrace();
     }
      	    }
//-- ----------------------------------------------------------------
public void ValidateAirlineSorting(String name, Log log, ScreenShots screenShots) {
    try {
    	new WebDriverWait(driver, Duration.ofSeconds(90)).until(
    		    webDriver -> ((JavascriptExecutor) webDriver)
    		        .executeScript("return document.readyState").equals("complete")
    		);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(150));
        
        // 1. Find and click the airline header to trigger initial sort
        WebElement airlineHeader = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//small[normalize-space(text()='"+name+"')]")));
        
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", airlineHeader);
        airlineHeader.click();
        
        // 2. Verify descending sort
        verifySortOrder("descending", log, screenShots);
        
        // 3. Click again for ascending sort
        airlineHeader.click();
        
        // 4. Verify ascending sort
        verifySortOrder("ascending", log, screenShots);
        
    } catch (Exception e) {
        log.ReportEvent("FAIL", "Airline sorting validation failed: " + e.getMessage());
        screenShots.takeScreenShot1();
        e.printStackTrace();
    }
}

private void verifySortOrder(String orderType, Log log, ScreenShots screenShots) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        // Wait for appropriate sort icon
        String iconXpath = orderType.equals("ascending") 
            ? "//*[@data-testid='SortAscendingIcon']" 
            : "//*[@data-testid='SortDescendingIcon']";
            
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(iconXpath)));
        
        // Get and print airlines
        List<WebElement> airlines = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
            By.xpath("//div[@class='carreir-info']")));
            
        System.out.println("----------- Airlines List in " + orderType.toUpperCase() + " Order -----------");
        System.out.println("Number of Records: " + airlines.size());
        
        for(WebElement airline : airlines) {
            System.out.println(airline.getText());
        }
        
        log.ReportEvent("PASS", "Airline data sorted in " + orderType + " order successfully");
        
    } catch (Exception e) {
        throw new RuntimeException("Failed to verify " + orderType + " sort: " + e.getMessage());
    }
}

//Method to validate Airline Sorting returning
public void ValidateAirlineSortingreturn(String Name,Log Log,ScreenShots ScreenShots) throws InterruptedException 
{
	 try {
		 WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(200));
		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[normalize-space(.)='AIRLINE']")));
		 WebElement airlineElement = driver.findElement(By.xpath("//small[normalize-space(.)='AIRLINE']"));
		 ((JavascriptExecutor) driver).executeScript("arguments[0].click();", airlineElement);

         driver.findElement(By.xpath("//small[normalize-space(text())='"+Name+"']")).click();
         Thread.sleep(5000);
         boolean value=driver.findElement(By.xpath("//small[normalize-space()='AIRLINE']/following-sibling::*[@data-testid=\"SortDescendingIcon\"]")).isDisplayed();
         Thread.sleep(5000);
         if(Name.contentEquals(" AIRLINE ")&& value==true)
         {
             System.out.println("--------------------------Airlines List in Descending Order----------------------------------");
             List<WebElement>numberOfAirlines=driver.findElements(By.xpath("//div[@class='carreir-info']"));
             int numberOfRecords=numberOfAirlines.size();            
             System.out.println("Number of Records present in an Homepage=="+numberOfRecords);
             for(WebElement airlineNames:numberOfAirlines)
             {
                 System.out.println(airlineNames.getText());
             }
             Log.ReportEvent("PASS", "Airline data is Sorted Descending Order is Successful");
         }
         System.out.println("--------------------------Airlines List in Ascending Order-------------------------------------");              
         Thread.sleep(5000);
         driver.findElement(By.xpath("//small[normalize-space(text())='"+Name+"']")).click();
         driver.findElement(By.xpath("//small[normalize-space()='AIRLINE']/following-sibling::*[@data-testid=\"SortAscendingIcon\"]")).isDisplayed();
         List<WebElement>numberOfAirlines=driver.findElements(By.xpath("//div[@class='carreir-info']"));
         for(WebElement airlineNames:numberOfAirlines)
         {
             System.out.println(airlineNames.getText());
         }    
         Log.ReportEvent("PASS", "Airline data is Sorted Descending Order is Successful");
     }
     catch(Exception e)
     {
         Log.ReportEvent("FAIL", "Airline data is not Sorted Successful");
         ScreenShots.takeScreenShot1();
         e.printStackTrace();
     }
     

 }

 

//Method to click on Depart ascending filter
public void DepartAscendingFilter() throws InterruptedException
{
	new WebDriverWait(driver, Duration.ofSeconds(90)).until(
		    webDriver -> ((JavascriptExecutor) webDriver)
		        .executeScript("return document.readyState").equals("complete")
		);

	Thread.sleep(6000);
	driver.findElement(By.xpath(""
            + "(//small[text()='DEPART']/parent::button//*[@class='MuiSvgIcon-root MuiSvgIcon-fontSizeMedium sort-default css-9ei8wr'])[1]")).click();
}

//Method to click on depart descending filter
public void DepartDescendingFilter() throws InterruptedException
{
	new WebDriverWait(driver, Duration.ofSeconds(90)).until(
		    webDriver -> ((JavascriptExecutor) webDriver)
		        .executeScript("return document.readyState").equals("complete")
		);

	Thread.sleep(3000);

    driver.findElement(By.xpath("//small[text()='DEPART']/parent::button//*[@class='MuiSvgIcon-root MuiSvgIcon-fontSizeMedium sort-icon css-9ei8wr']")).click();
}

//Method to validate departure

public void checkresultsfordepature(Log Log, ScreenShots ScreenShots, String orderType) throws InterruptedException {
	
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
        
        // Using only the exact XPath you provided
        String xpath = "//div[contains(@class,'MuiGrid2-root MuiGrid2-direction-xs-row MuiGrid2-grid-lg-2 MuiGrid2-grid-md-2 MuiGrid2-grid-sm-2 MuiGrid2-grid-xs-6 css-fsa438')]";
        
        // Wait for at least one element to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        
        // Find all matching elements
        List<WebElement> flightContainers = driver.findElements(By.xpath(xpath));

        if (flightContainers.isEmpty()) {
            Log.ReportEvent("FAIL", "No flight containers found using the provided XPath");
            ScreenShots.takeScreenShot1();
            return;
        }

        // Extract time strings from h3 elements inside each container
        List<String> timeStrings = new ArrayList<>();
        for (WebElement container : flightContainers) {
            try {
                WebElement timeElement = container.findElement(By.xpath(".//h3"));
                timeStrings.add(timeElement.getText().trim());
            } catch (NoSuchElementException e) {
                Log.ReportEvent("FAIL", "Time element not found within flight container");
                ScreenShots.takeScreenShot1();
                return;
            }
        }

        // Convert time strings to minutes
        List<Integer> timesInMinutes = new ArrayList<>();
        for (String time : timeStrings) {
            try {
                String[] parts = time.split(":");
                int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
                timesInMinutes.add(minutes);
            } catch (Exception e) {
                Log.ReportEvent("FAIL", "Invalid time format: " + time);
                ScreenShots.takeScreenShot1();
                return;
            }
        }

        // Validate order
        boolean isValidOrder = true;
        String orderName = orderType.toLowerCase();
        
        if (orderName.equals("ascending")) {
            for (int i = 0; i < timesInMinutes.size() - 1; i++) {
                if (timesInMinutes.get(i) > timesInMinutes.get(i + 1)) {
                    isValidOrder = false;
                    break;
                }
            }
        } 
        else if (orderName.equals("descending")) {
            for (int i = 0; i < timesInMinutes.size() - 1; i++) {
                if (timesInMinutes.get(i) < timesInMinutes.get(i + 1)) {
                    isValidOrder = false;
                    break;
                }
            }
        } 
        else {
            Log.ReportEvent("FAIL", "Invalid order type: " + orderType);
            ScreenShots.takeScreenShot1();
            return;
        }

        // Report results
        String status = isValidOrder ? "PASS" : "FAIL";
        String message = isValidOrder 
            ? "Flights are in correct " + orderName + " order" 
            : "Flights are NOT in " + orderName + " order. Times: " + timeStrings;
        
        Log.ReportEvent(status, message);
        ScreenShots.takeScreenShot1();

    } catch (TimeoutException e) {
    	Thread.sleep(9000);
        Log.ReportEvent("FAIL", "Timed out waiting for flight elements using the provided XPath");
        ScreenShots.takeScreenShot1();
    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Unexpected error: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
    }
}

//Method to validate flights stops on Result Screen
public void validateFlightsStopsInternationalOnRoundtripResultScreen(String numberOfStops,Log Log,ScreenShots ScreenShots) {
	try {
        Thread.sleep(5000);

        // Locate the stops element
        WebElement onwardLocalStops = driver.findElement(By.xpath(
            "//span[./text()[1][normalize-space()='2'] and ./text()[2][contains(., 'stops')]]"
        ));

        // Extract and trim both values
        String actualStops = onwardLocalStops.getText().trim();
        String expectedStops = numberOfStops.trim();

        System.out.println("Actual Stops Text: '" + actualStops + "'");
        System.out.println("Expected Stops Text: '" + expectedStops + "'");

        // Compare
        if (actualStops.equalsIgnoreCase(expectedStops)) {
            Log.ReportEvent("PASS", "Flight stops match the expected value: '" + expectedStops + "'");
        } else {
            Log.ReportEvent("FAIL", "Flight stops do not match. Expected: '" + expectedStops + "', Found: '" + actualStops + "'");
            ScreenShots.takeScreenShot1();
            Assert.fail("Mismatch in number of stops");
        }

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception occurred while validating flight stops: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
        Assert.fail("Exception during flight stops validation");
    }

}

//Method to validate flights stops on Result Screen
public void validateFlightsStopsOnResultScreenInternational(String numberOfStops,Log Log,ScreenShots ScreenShots)
{
  try {
     Thread.sleep(5000);
     List<WebElement> flightStops=driver.findElements(By.xpath("//span[@data-tgstops]"));
     boolean stops=true;
     if(flightStops.size()>0)
     {
        for(WebElement flightStop:flightStops)
        {
           String stop=flightStop.getText();
           if(stop.contentEquals(numberOfStops))
           {
              System.out.println(stop);

           }
           else {
              stops=false;
              Log.ReportEvent("FAIL", "Flights are Not displayed based on User Searched");
              ScreenShots.takeScreenShot1();
              Assert.fail();
           }
        }
        if(stops==true)
        {
           Log.ReportEvent("PASS", "Flights displayed based on User Searched is Successful");
        }
     }else{
        Log.ReportEvent("FAIL", "No Flights are displayed based on User Search");
        ScreenShots.takeScreenShot1();
        Assert.fail();
     }
  }
  catch(Exception e)
  {
     Log.ReportEvent("FAIL", "Flights are Not displayed based on User Searched");
     ScreenShots.takeScreenShot1();
     Assert.fail();
     e.printStackTrace();
  }

}


// Method to  round Trip click On Ward  Airline Stops
public void roundTripClickOnWardStops(String... stops) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    for (String stop : stops) {
        String trimmedStop = stop.trim();
     //   String xpath = "//div[@class='filter-stops']//button[text()='" + trimmedStop + "']";
        String xpath = "//button[contains(@class,'tg-stops')][normalize-space()='"+trimmedStop+"']";
        WebElement stopButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

        // Scroll into view
        js.executeScript("arguments[0].scrollIntoView(true);", stopButton);

        // Wait until clickable
        wait.until(ExpectedConditions.elementToBeClickable(stopButton));

        // JavaScript click to bypass overlay issues
        js.executeScript("arguments[0].click();", stopButton);
    }
}

// Method to validate flight stops on result screen for local destinations
public void validateFlightsStopsOnResultScreen(String numberOfStops, Log Log, ScreenShots ScreenShots) {
    try {
        Thread.sleep(9000);

        // Locate the stops element
        WebElement onwardLocalStops = driver.findElement(By.xpath(
            "//*[contains(@class,'tg-fromstops')]"
        ));

        // Extract and trim both values
        String actualStops = onwardLocalStops.getText().trim();
        String expectedStops = numberOfStops.trim();

        System.out.println("Actual Stops Text: '" + actualStops + "'");
        System.out.println("Expected Stops Text: '" + expectedStops + "'");

        // Compare
        if (actualStops.equalsIgnoreCase(expectedStops)) {
            Log.ReportEvent("PASS", "Flight stops match the expected value: '" + expectedStops + "'");
        } else {
            Log.ReportEvent("FAIL", "Flight stops do not match. Expected: '" + expectedStops + "', Found: '" + actualStops + "'");
            ScreenShots.takeScreenShot1();
            Assert.fail("Mismatch in number of stops");
        }

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception occurred while validating flight stops: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
        Assert.fail("Exception during flight stops validation");
    }
}


//Method to validate flight stops on result screen for local destinations
public void validateFlightsStopsOnResultScreenforInternational(String numberOfStops, Log Log, ScreenShots ScreenShots) {
 try {
     Thread.sleep(9000);

     // Locate the stops element
     WebElement onwardLocalStops = driver.findElement(By.xpath(
         "//*[contains(@class,'tg-intlonward-stops')]"
     ));

     // Extract and trim both values
     String actualStops = onwardLocalStops.getText().trim();
     String expectedStops = numberOfStops.trim();

     System.out.println("Actual Stops Text: '" + actualStops + "'");
     System.out.println("Expected Stops Text: '" + expectedStops + "'");

     // Compare
     if (actualStops.equalsIgnoreCase(expectedStops)) {
         Log.ReportEvent("PASS", "Flight stops match the expected value: '" + expectedStops + "'");
     } else {
         Log.ReportEvent("FAIL", "Flight stops do not match. Expected: '" + expectedStops + "', Found: '" + actualStops + "'");
         ScreenShots.takeScreenShot1();
         Assert.fail("Mismatch in number of stops");
     }

 } catch (Exception e) {
     Log.ReportEvent("FAIL", "Exception occurred while validating flight stops: " + e.getMessage());
     ScreenShots.takeScreenShot1();
     e.printStackTrace();
     Assert.fail("Exception during flight stops validation");
 }
}


//Method to Validate Flights Details Displayed On Result Screen for International
//Method to Validate Flights Details Displayed On Result Screen for International
public void verifyFlightsDetailsOnResultScreenForInternational(Log Log, ScreenShots ScreenShots) throws InterruptedException {
try {
   TestExecutionNotifier.showExecutionPopup();
   WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[contains(@class,'tg-intlonward-flightcarrier')])[1]")));
   Thread.sleep(3000);
   WebElement priceDetails = driver.findElement(By.xpath("(//*[contains(@class,'tg-intlonward-price')])[1]"));
   WebElement FlightTitleDetails = driver.findElement(By.xpath("(//*[contains(@class,'tg-intlonward-flightcarrier')])[1]"));
   WebElement departureDetails = driver.findElement(By.xpath("(//*[contains(@class,'tg-intlonward-deptime')])[1]"));
   WebElement arrivalDetails = driver.findElement(By.xpath("(//*[contains(@class,'tg-intlonward-arrtime')])[1]"));
   WebElement durationDetails = driver.findElement(By.xpath("(//*[contains(@class,'tg-intonwardl-duration')])[1]"));
   Thread.sleep(3000);
   if (priceDetails.isDisplayed() && FlightTitleDetails.isDisplayed() && departureDetails.isDisplayed() && arrivalDetails.isDisplayed() && durationDetails.isDisplayed()) {
      Log.ReportEvent("PASS", "Flights Details are displayed in Result Screen Successful");
   } else {
      Log.ReportEvent("FAIL", "Flights Details Not displayed in Result Screen");
      ScreenShots.takeScreenShot1();
      Assert.fail();
   }

} catch (Exception e) {
   e.printStackTrace();
   if (driver.findElement(By.xpath("//*[@data-testid='AirplaneIcon']")).isDisplayed()) {
      Log.ReportEvent("FAIL", "Flights are Not displayed in Result Screen");
      ScreenShots.takeScreenShot1();
      Assert.fail();
   }
}

}
//Method to Select flight based on Index
public void clickOnSelectFlightBasedOnIndex(int index) throws InterruptedException
{
    Thread.sleep(2000);
    driver.findElement(By.xpath("(//button[text()='Select'])["+index+"]")).click();
    Thread.sleep(2000);

}

//Method to round Trip click Return Stops
public void roundTripClickReturnStops(String... stops) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    for (String stop : stops) {
        String trimmedStop = stop.trim();
        String xpath = "//button[contains(@class,'tg-return-stops')][normalize-space()='"+trimmedStop+"']";

        WebElement stopButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

        // Scroll into view
        js.executeScript("arguments[0].scrollIntoView(true);", stopButton);

        // Wait until clickable
        wait.until(ExpectedConditions.elementToBeClickable(stopButton));

        // Click via JavaScript to handle overlays
        js.executeScript("arguments[0].click();", stopButton);
    }

        }

// Method to validate flight stops on result screen
public void validateFlightsreturnStopsOnResultScreen(String numberOfStops, Log Log, ScreenShots ScreenShots) {
    try {
        Thread.sleep(5000);

        // Locate the stops element
        WebElement onwardLocalStops = driver.findElement(By.xpath(
            "//*[contains(@class,'tg-tostops')]"
        ));

        // Extract and trim both values
        String actualStops = onwardLocalStops.getText().trim();
        String expectedStops = numberOfStops.trim();

        System.out.println("Actual Return Stops Text: '" + actualStops + "'");
        System.out.println("Expected Return Stops Text: '" + expectedStops + "'");

        // Compare
        if (actualStops.equalsIgnoreCase(expectedStops)) {
            Log.ReportEvent("PASS", "Flight Return stops match the expected value: '" + expectedStops + "'");
        } else {
            Log.ReportEvent("FAIL", "Flight Return stops do not match. Expected: '" + expectedStops + "', Found: '" + actualStops + "'");
            ScreenShots.takeScreenShot1();
            Assert.fail("Mismatch in number of stops");
        }

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception occurred while validating flight Return stops: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
        Assert.fail("Exception during flight Return stops validation");
    }
}


//-------------------------------------------------------------------------------------------------------------

//Method to Validate flights on Result Page In RoundTRip
public void validateFlightsResultsForRoundTrip(Log Log,ScreenShots ScreenShots)
{
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.elementToBeClickable(By.className("round-trip-from-results")));
        WebElement flights=driver.findElement(By.className("round-trip-from-results"));    

        Thread.sleep(4000);
        if(flights.isDisplayed())
        {
            Log.ReportEvent("PASS", "Flights are displayed based on User Search is Successful");
        }    
        else {
            Log.ReportEvent("FAIL", "Flights are Not displayed based on User Search Please Change Filter");
            ScreenShots.takeScreenShot1();
        }
        
    }
    catch(Exception e){
        Log.ReportEvent("FAIL", "Flights are Not displayed based on User Search Please Change Filter");
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
    }       
}

//--------------------------------------------------------------------------------------------------------


//-------------------------------------------------------------------------------------------------------------

//Method to click ONWARD DEPART TIME roundtrip
public void selectOnWardDepartTimeroundtrip(String... times) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(75));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    for (String time : times) {
        String trimmedTime = time.trim();
        String xpath = "//*[contains(@class,'depart-time tg-onward-dep-time')]//small[text()='" + trimmedTime + "']";

        WebElement departTime = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", departTime);
        wait.until(ExpectedConditions.elementToBeClickable(departTime));
        js.executeScript("arguments[0].click();", departTime);
    }
}
//method to validate onward depart time round trip

public void validateRoundTripOnwardDepartureTimeIsSelected(Log log, ScreenShots screenshots, String... timeRanges) {
    System.out.println("=== Departure Time Validation ===");
    
    for (String range : timeRanges) {
        System.out.println("\nValidating against range: " + range);
        
        try {
            // Wait a moment for page to load
            Thread.sleep(2000);
            
            // Get the start and end hours from input (e.g., "00-06" → 0 and 6)
            String[] hours = range.split("-");
            int startHour = Integer.parseInt(hours[0].trim());
            int endHour = Integer.parseInt(hours[1].trim());
            
            // Find all departure time elements on page
            List<WebElement> timeElements = driver.findElements(
                By.xpath("//div[@class='round-trip-from-results']//div[@ class='frc-deptime']"));
            
            boolean allValid = true;
            int validCount = 0; //counts how many times validation is passed
            int invalidCount = 0;   //counts how many times validation is failed
            
            //  Detect overnight ranges
            boolean isOvernight = endHour <= startHour;            //end hr=6   && srt hr=3
            
            for (WebElement element : timeElements) {
                String timeText = element.getText().trim(); // e.g., "02:30"
                String[] parts = timeText.split(":");        // Splits into ["02", "30"]
                int hour = Integer.parseInt(parts[0]);  // Converts "02" → 2 (hour)
                
                // Check if hour is between start and end (with overnight support)
                boolean isValid;
                if (isOvernight) {
                    // For ranges like 18-00, valid if hour >= 18 OR hour < 0 (but 0 would be 00:00)
                    isValid = hour >= startHour || hour < endHour;
                } else {
                    //  logic for normal ranges
                    isValid = hour >= startHour && hour < endHour;
                }
                
                if (!isValid) {
                    System.out.println(timeText + " → INVALID");
                    log.ReportEvent("FAIL", timeText + " not in range " + range);
                    allValid = false;
                    invalidCount++;
                } else {
                    System.out.println(timeText + " → VALID");
                    validCount++;
                }
            }
            
            System.out.println("Summary - Valid: " + validCount + ", Invalid: " + invalidCount);
            
            if (allValid) {
                System.out.println("RESULT: PASS - All times in range " + range);
                log.ReportEvent("PASS", "All times in range " + range);

            } else {
                System.out.println("RESULT: FAIL - Some times outside range " + range);
                screenshots.takeScreenShot1();
            }
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            log.ReportEvent("FAIL", "Error checking times: " + e.getMessage());
            screenshots.takeScreenShot1();
        }
    }
    System.out.println("=== Validation Complete ===");
}

  //or
//Method to validate flights departure time on result screen
public void validateFlightsDepartureTimeOnResultScreen(int flightStartHour, int flightStartMinute, int flightEndHour, int flightEndMinute, Log Log, ScreenShots ScreenShots) {
  try {
      Thread.sleep(5000);

      List<String> flightsDepartureData = new ArrayList<>();
      List<WebElement> airlineDepartureCount = driver.findElements(By.xpath("//*[contains(@class,'tg-fromdeptime')]"));

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
          System.out.println(departureText);
          flightsDepartureData.add(departureText);

          LocalTime timeToCheck = LocalTime.parse(departureText, formatter);

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

  } catch (Exception e) {
      Log.ReportEvent("FAIL", "Exception while validating flight times: " + e.getMessage());
      ScreenShots.takeScreenShot1();
      e.printStackTrace();
      Assert.fail();
  }
}
//-------------------------------------------------------------------------------------------------------------
		
//Method to click ONWARD ARRIVAL TIME roundtrip
public void selectOnWardArrivalTimeroundtrip(String... times) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(85));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    for (String time : times) {
        String trimmedTime = time.trim();
        String xpath = "//*[contains(@class,'depart-time tg-onward-arr-time')]//small[(text()='" + trimmedTime + "')]";
        WebElement arrivalTime = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", arrivalTime);
        wait.until(ExpectedConditions.elementToBeClickable(arrivalTime));
        js.executeScript("arguments[0].click();", arrivalTime);
    }
}



     //method to validate onward arrival time round trip
	 public void validateroundtripOnwardArrivalTimeIsSelected(Log log, ScreenShots screenshots, String... timeRanges) {
            System.out.println("=== Arrival Time Validation ===");
            
            for (String range : timeRanges) {
                System.out.println("\nValidating against range: " + range);
                
                try {
                    // Wait a moment for page to load
                    Thread.sleep(2000);
                    
                    // Get the start and end hours from input (e.g., "00-06" → 0 and 6)
                    String[] hours = range.split("-");
                    int startHour = Integer.parseInt(hours[0].trim());
                    int endHour = Integer.parseInt(hours[1].trim());
                    
                    // Find all departure time elements on page
                    List<WebElement> timeElements = driver.findElements(
                        By.xpath("//div[@class='round-trip-from-results']//div[@class='frc-arrtime']"));
          
                    
                    boolean allValid = true;
                    int validCount = 0; //counts how many times validation is passed
                    int invalidCount = 0;   //counts how many times validation is failed
                    
                    //Detect overnight ranges
                    boolean isOvernight = endHour <= startHour;
                    
                    for (WebElement element : timeElements) {
                        String timeText = element.getText().trim(); // e.g., "02:30"
                        String[] parts = timeText.split(":");        // Splits into ["02", "30"]
                        int hour = Integer.parseInt(parts[0]);  // Converts "02" → 2 (hour)
                        
                        //  Check if hour is between start and end (with overnight support)
                        boolean isValid;
                        if (isOvernight) {
                            // For ranges like 18-00, valid if hour >= 18 OR hour < 0 (but 0 would be 00:00)
                            isValid = hour >= startHour || hour < endHour;
                        } else {
                            // logic for normal ranges
                            isValid = hour >= startHour && hour < endHour;
                        }
                        
                        if (!isValid) {
                            System.out.println(timeText + " → INVALID");
                            log.ReportEvent("FAIL", timeText + " not in range " + range);
                            allValid = false;
                            invalidCount++;
                        } else {
                            System.out.println(timeText + " → VALID");
                            validCount++;
                        }
                    }
                    
                    System.out.println("Summary - Valid: " + validCount + ", Invalid: " + invalidCount);
                    
                    if (allValid) {
                        System.out.println("RESULT: PASS - All times in range " + range);
                        log.ReportEvent("PASS", "All times in range " + range);

                        
                    } else {
                        System.out.println("RESULT: FAIL - Some times outside range " + range);
                        screenshots.takeScreenShot1();
                    }
                    
                } catch (Exception e) {
                    System.out.println("ERROR: " + e.getMessage());
                    log.ReportEvent("FAIL", "Error checking times: " + e.getMessage());
                    screenshots.takeScreenShot1();
                }
            }
            System.out.println("=== Validation Complete ===");
        }
	 
	 //or
	 
	//Method to validate flights Arrival time on result screen
	    public void validateFlightsArrivalTimeroundtrip(int flightStartHour, int flightStartMinute, int flightEndHour, int flightEndMinute, Log Log, ScreenShots ScreenShots) {
	        try {
	            Thread.sleep(5000);

	            List<String> flightsArrivalData = new ArrayList<>();
	            List<WebElement> airlineArrivalCount = driver.findElements(By.xpath("//*[contains(@class,'tg-fromarrtime')]"));

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
	                flightsArrivalData.add(arrivalText);

	                LocalTime timeToCheck = LocalTime.parse(arrivalText, formatter);

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

	        } catch (Exception e) {
	            Log.ReportEvent("FAIL", "Exception while validating flight times: " + e.getMessage());
	            ScreenShots.takeScreenShot1();
	            e.printStackTrace();
	            Assert.fail();
	        }
	    }

	 
//-------------------------------------------------------------------------------------------------------------

// Method to click Return return ARRIVAL TIME
	 public void selectReturnArrivalTimeRoundtrip(String... times) {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        for (String time : times) {
	            String trimmedTime = time.trim();
	            String xpath = "//*[contains(@class,'depart-time tg-return-arr-time')]//small[text()='" + trimmedTime + "']";

	            WebElement arrivalTime = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

	            // Scroll into view and click via JavaScript
	            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", arrivalTime);
	            wait.until(ExpectedConditions.elementToBeClickable(arrivalTime));
	            js.executeScript("arguments[0].click();", arrivalTime);
	        }
	    }

//method to validate return arrival time round trip
  
public void validateroundtripreturnArrivalTimeIsSelected(Log log, ScreenShots screenshots, String... timeRanges) {
 System.out.println("===Return Arrival Time Validation ===");
 
 for (String range : timeRanges) {
     System.out.println("\nValidating against range: " + range);
     
     try {
         // Wait a moment for page to load
         Thread.sleep(2000);
         
         // Get the start and end hours from input (e.g., "00-06" → 0 and 6)
         String[] hours = range.split("-");
         int startHour = Integer.parseInt(hours[0].trim());
         int endHour = Integer.parseInt(hours[1].trim());
         
         // Find all departure time elements on page
         List<WebElement> timeElements = driver.findElements(
             By.xpath("//div[@class='round-trip-to-results']//div[contains(@class, 'frc-arrtime')]"));
         
         boolean allValid = true;
         int validCount = 0; //counts how many times validation is passed
         int invalidCount = 0;   //counts how many times validation is failed
         
         //  Detect overnight ranges
         boolean isOvernight = endHour <= startHour;
         
         for (WebElement element : timeElements) {
             String timeText = element.getText().trim(); // e.g., "02:30"
             String[] parts = timeText.split(":");        // Splits into ["02", "30"]
             int hour = Integer.parseInt(parts[0]);  // Converts "02" → 2 (hour)
             
             // Check if hour is between start and end (with overnight support)
             boolean isValid;
             if (isOvernight) {
                 // For ranges like 18-00, valid if hour >= 18 OR hour < 0 (but 0 would be 00:00)
                 isValid = hour >= startHour || hour < endHour;
             } else {
                 //  logic for normal ranges
                 isValid = hour >= startHour && hour < endHour;
             }
             
             if (!isValid) {
                 System.out.println(timeText + " → INVALID");
                 log.ReportEvent("FAIL", timeText + " not in range " + range);
                 allValid = false;
                 invalidCount++;
             } else {
                 System.out.println(timeText + " → VALID");
                 validCount++;
             }
         }
         
         System.out.println("Summary - Valid: " + validCount + ", Invalid: " + invalidCount);
         
         if (allValid) {
             System.out.println("RESULT: PASS - All times in range " + range);
             log.ReportEvent("PASS", "All times in range " + range);

         } else {
             System.out.println("RESULT: FAIL - Some times outside range " + range);
             screenshots.takeScreenShot1();
         }
         
     } catch (Exception e) {
         System.out.println("ERROR: " + e.getMessage());
         log.ReportEvent("FAIL", "Error checking times: " + e.getMessage());
         screenshots.takeScreenShot1();
     }
 }
 System.out.println("=== Validation Complete ===");
}


//or

public void validatereturnFlightsArrivalTimeroundtrip(int flightStartHour, int flightStartMinute, int flightEndHour, int flightEndMinute, Log Log, ScreenShots ScreenShots) {
    try {
        Thread.sleep(5000);

        List<String> flightsArrivalData = new ArrayList<>();
        List<WebElement> airlineArrivalCount = driver.findElements(By.xpath("//*[contains(@class,'tg-toarrtime')]"));

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
            System.out.println(arrivalText);
            flightsArrivalData.add(arrivalText);

            LocalTime timeToCheck = LocalTime.parse(arrivalText, formatter);

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

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception while validating flight times: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
        Assert.fail();
    }
}

//-------------------------------------------------------------------------------------------------------------

public void selectReturnDepartTimeroundtrip(String... timeElements) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    for (String timeElement : timeElements) {
        String xpath = "//*[contains(@class,'depart-time tg-return-dep-time')]//small[text()='" + timeElement + "']";
        try {
            WebElement timeElement2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", timeElement2);
            js.executeScript("arguments[0].click();", timeElement2);
            return; // Clicked successfully
        } catch (Exception e) {
            System.out.println("Could not click element with time: " + timeElement + " — " + e.getMessage());
        }
    }
    System.out.println("Failed to click on any RETURN DEPART TIME elements.");
}




//method to validate onward return depart time round trip

public void validateroundtripreturnDepartureTimeIsSelected(Log log, ScreenShots screenshots, String... timeRanges) {
 System.out.println("===Return Departure Time Validation ===");
 
 for (String range : timeRanges) {
     System.out.println("\nValidating against range: " + range);
     
     try {
         // Wait a moment for page to load
         Thread.sleep(2000);
         
         // Get the start and end hours from input (e.g., "00-06" → 0 and 6)
         String[] hours = range.split("-");
         int startHour = Integer.parseInt(hours[0].trim());
         int endHour = Integer.parseInt(hours[1].trim());
         
         // Find all departure time elements on page
         List<WebElement> timeElements = driver.findElements(
             By.xpath("//div[@class='round-trip-to-results']//div[contains(@class,'frc-deptime')]"));
         
         boolean allValid = true;
         int validCount = 0; //counts how many times validation is passed
         int invalidCount = 0;   //counts how many times validation is failed
         
         //  Detect overnight ranges
         boolean isOvernight = endHour <= startHour;
         
         for (WebElement element : timeElements) {
             String timeText = element.getText().trim(); // e.g., "02:30"
             String[] parts = timeText.split(":");        // Splits into ["02", "30"]
             int hour = Integer.parseInt(parts[0]);  // Converts "02" → 2 (hour)
             
             // Check if hour is between start and end (with overnight support)
             boolean isValid;
             if (isOvernight) {
                 // For ranges like 18-00, valid if hour >= 18 OR hour < 0 (but 0 would be 00:00)
                 isValid = hour >= startHour || hour < endHour;
             } else {
                 //  logic for normal ranges
                 isValid = hour >= startHour && hour < endHour;
             }
             
             if (!isValid) {
                 System.out.println(timeText + " → INVALID");
                 log.ReportEvent("FAIL", timeText + " not in range " + range);
                 allValid = false;
                 invalidCount++;
             } else {
                 System.out.println(timeText + " → VALID");
                 validCount++;
             }
         }
         
         System.out.println("Summary - Valid: " + validCount + ", Invalid: " + invalidCount);
         
         if (allValid) {
             System.out.println("RESULT: PASS - All times in range " + range);
             log.ReportEvent("PASS", "All times in range " + range);

         } else {
             System.out.println("RESULT: FAIL - Some times outside range " + range);
             screenshots.takeScreenShot1();
         }
         
     } catch (Exception e) {
         System.out.println("ERROR: " + e.getMessage());
         log.ReportEvent("FAIL", "Error checking times: " + e.getMessage());
         screenshots.takeScreenShot1();
     }
 }
 System.out.println("=== Validation Complete ===");
}

               //or

//Method to validate return flights departure time on result screen
public void validatereturnFlightsDepartureTimeOnResultScreen(int flightStartHour, int flightStartMinute, int flightEndHour, int flightEndMinute, Log Log, ScreenShots ScreenShots) {
    try {
        Thread.sleep(5000);

        List<String> flightsDepartureData = new ArrayList<>();
        List<WebElement> airlineDepartureCount = driver.findElements(By.xpath("//*[contains(@class,'tg-todeptime')]"));

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
            System.out.println(departureText);
            flightsDepartureData.add(departureText);

            LocalTime timeToCheck = LocalTime.parse(departureText, formatter);

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

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception while validating flight times: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
        Assert.fail();
    }
}

//-------------------------------------------------------------------------------------------------------------

//Method to Click on Filters on Result Screen
public void clickOnFiltersOnResultScreenroundtrip(String filterName)
{
driver.findElement(By.xpath("//small[text()='"+filterName+"']")).click();

}

//-------------------------------------------------------------------------------------------------------------
//Method to Validate AirLine Filter for Local to International and International to International
public void validateAirlineFilterForInternationalToLocalAndInternationalToInternationalFlights(Log Log, ScreenShots ScreenShots,String order)
{
try{
   int index=1;
   ArrayList flightsData=new ArrayList();
   int airlineCount=driver.findElements(By.xpath("//div[contains(@class,'flight-image')]/following-sibling::small")).size();
   for(int i=0;i<airlineCount;i++ )
   {
      WebElement airlineList=driver.findElement(By.xpath("(//div[contains(@class,'flight-image')]/following-sibling::small)["+index+"]"));
      index=index+2;
      String airlineText=airlineList.getText();
      System.out.println(airlineText);
      flightsData.add(airlineText);
      if(index>airlineCount)
      {
         break;
      }
   }
   System.out.println(flightsData);
   boolean isSorted = isSortedAlphabeticallyAirlines(flightsData,order);
   System.out.println("Is the list sorted in ascending order? " + isSorted);
   if(isSorted==true)
   {
      Log.ReportEvent("PASS", "Airlines are Sorted in Order is Successful");
   }
   else{
      Log.ReportEvent("FAIL", "Airlines are Not Sorted in order is Successful");
      ScreenShots.takeScreenShot1();
      Assert.fail();
   }
}
catch(Exception e)
{
   Log.ReportEvent("FAIL", "Airlines are Not Sorted in order is Successful");
   ScreenShots.takeScreenShot1();
   Assert.fail();
}


}
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
    }
    else
    {
       Log.ReportEvent("PASS", "Flights Departure Time is Not Sorted");
       Assert.fail();
    }

 } catch (Exception e) {
    e.printStackTrace();
    Log.ReportEvent("FAIL", "Flights Departure Time is Not Sorted");
    ScreenShots.takeScreenShot1();
    Assert.fail();
 }
}
//----------------------------------------------------------------------------------------------------------

//Method to get Other Country Price Value
public String getOtherCountryPriceValue(int index)
{
 String priceValue=driver.findElement(By.xpath("(//span[@class='other-currency-price bold'])["+index+"]")).getText();
 System.out.println(priceValue);
 return priceValue;
}
//----------------------------------------------------------------------------------------------------------

//Method to get Indian Country Price Value
public String getIndianCountryPriceValue(int index)
{
 String priceValue=driver.findElement(By.xpath("(//h6[contains(@class,'price bold')])["+index+"]")).getText();
 System.out.println(priceValue);
 return priceValue;
}
//----------------------------------------------------------------------------------------------------------

//Method to Click on Continue Button on Continue Flight Booking Popup
public void clickOnContinueBookingFlightPopup() throws InterruptedException
{
 driver.findElement(By.xpath("//div[contains(@class,'bottom-container')]//button[text()='Continue']")).click();
 Thread.sleep(1000);
}
//----------------------------------------------------------------------------------------------------------

//Method to Click on Select Reasons Popup
public void clickOnSelectRegionPopup(String reason) throws InterruptedException {
 driver.findElement(By.xpath("//span[text()='"+reason+"']")).click();
 Thread.sleep(1000);
}
//----------------------------------------------------------------------------------------------------------

//Method to Click on Proceed Booking
public void clickOnProceedBooking() throws InterruptedException {
 driver.findElement(By.xpath("//button[text()='Proceed to Booking']")).click();
 Thread.sleep(1000);
}
//----------------------------------------------------------------------------------------------------------

//Method to get the Price From Continue Popup
public String getPriceValueFromContinuePopup()
{
 String priceValue=driver.findElement(By.xpath("//div[contains(@class,'bottom-container')]//button[contains(@class,'btn-link')]/parent::div//h6")).getText();
 return priceValue;
}
//----------------------------------------------------------------------------------------------------------

//Method to Validate data after selection of Flight
public void validateDataAfterSelectingFlight(Log Log,ScreenShots ScreenShots,String priceValue)
{
 try {
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

        String fromCode = driver.findElement(By.xpath("((//div[@class='bb-flight-details'])[1]//h6)[1]//small")).getText();
        String toCode = driver.findElement(By.xpath("((//div[@class='bb-flight-details'])[1]//h6)[3]//small")).getText();

        String returnFromCode = driver.findElement(By.xpath("((//div[@class='bb-flight-details'])[2]//h6)[1]//small")).getText();
        String returnToCode = driver.findElement(By.xpath("((//div[@class='bb-flight-details'])[2]//h6)[3]//small")).getText();

    String priceValueOnPopup=getPriceValueFromContinuePopup();
    Assert.assertEquals(priceValue,priceValueOnPopup);
        if (fromCode.contentEquals(fromLocationCode) && toCode.contentEquals(toLocationCode)&&returnFromCode.contentEquals(toLocationCode)&&returnToCode.contentEquals(fromLocationCode)) {
            Log.ReportEvent("PASS", "Flights From and To Locations is displaying Same");
        } else {
            Log.ReportEvent("FAIL", "Flights From and To Locations are Not displaying Same");
            ScreenShots.takeScreenShot1();
            Assert.fail();

        }

    }catch(Exception e)
 {
    Log.ReportEvent("FAIL", "Flights From and To Locations are Not displaying Same"+ e.getMessage());
    ScreenShots.takeScreenShot1();
    Assert.fail();


 }

}
//----------------------------------------------------------------------------------------------------------

//Method to validate Currency 
public void validateCurrencyRoundTrip(String expectedCurrency, Log log, ScreenShots screenShots) {
  try {
      // Get footer currency values
      Thread.sleep(5000);
      String footerDivSelectedCurrencyFrom = driver.findElement(By.xpath("//div[@data-tgflotherprice]")).getText().split(" ")[0].trim();
      String footerDivSelectedCurrencyTo = driver.findElement(By.xpath("//div[@data-tgfltootherprice]")).getText().split(" ")[0].trim();
      String footerDivSelectedCurrencyTotal = driver.findElement(By.xpath("//div[@data-tgothertotal]")).getText().split(" ")[0].trim();

      // Wait for currency list to be visible using explicit wait
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
      List<WebElement> currencyElements = wait.until(
          ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@data-tgothercheapestprice]"))
      );

      // Validate each currency in the list
      boolean allMatch = true;
      for (WebElement value : currencyElements) {
          String currency = value.getText().split(" ")[0].trim();; // Assume the currency symbol/code is first
          System.out.println("Currency found: " + currency);
          if (!currency.equals(expectedCurrency)) {
              allMatch = false;
          }
      }

      // Footer check
      boolean isFooterMatch = footerDivSelectedCurrencyFrom.equals(expectedCurrency) &&
                              footerDivSelectedCurrencyTo.equals(expectedCurrency) &&
                              footerDivSelectedCurrencyTotal.equals(expectedCurrency);

      if (isFooterMatch && allMatch) {
          log.ReportEvent("PASS", "All currency values match expected: " + expectedCurrency);
      } else {
          log.ReportEvent("FAIL", String.format("Expected: '%s'. Found Footer -> From: '%s', To: '%s', Total: '%s'.",
                  expectedCurrency, footerDivSelectedCurrencyFrom, footerDivSelectedCurrencyTo, footerDivSelectedCurrencyTotal));
          if (!allMatch) {
              log.ReportEvent("FAIL", "One or more list currency values do not match expected: " + expectedCurrency);
          }
          Assert.fail("Currency mismatch");
      }

      screenShots.takeScreenShot1(); // Screenshot after validation

  } catch (Exception e) {
      log.ReportEvent("ERROR", "An error occurred during currency validation: " + e.getMessage());
      screenShots.takeScreenShot1();
      Assert.fail("Exception occurred: " + e.getMessage());
  }
}


//Method to Verify Currency Persistence Across Pages
public void VerifyCurrencyPersistenceAcrossPages(String expectedCurrency, Log log, ScreenShots screenShots) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

    try {
        // Wait for the currency element to be visible
        WebElement currency = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("other-currency-price bold")));

        // Scroll the currency element into view
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", currency);

        // Extract and compare the currency value
        String value = currency.getText().split(" ")[0].trim();
        if (value.equals(expectedCurrency)) {
            log.ReportEvent("PASS", "Currency value matches expected: " + expectedCurrency);

            // Scroll to the top before clicking 'Back To Search Results'
            js.executeScript("window.scrollBy(0, -document.body.scrollHeight)");

            // Click on 'Back To Search Results'
            driver.findElement(By.xpath("//button[text()='Back To Search Results']")).click();

            // Validate currency on the result page
            Thread.sleep(2000);
            log.ReportEvent("INFO", "Currency value matches Back To Search Results page");
            System.out.println("Currency value matches Back To Search Results page");
            
            validateCurrencyRoundTrip(expectedCurrency, log, screenShots);
        } else {
            log.ReportEvent("FAIL", "Currency value does not match expected. Found: " + value + ", Expected: " + expectedCurrency);
            screenShots.takeScreenShot1();
        }
    } catch (NoSuchElementException e) {
        log.ReportEvent("ERROR", "Currency element not found: " + e.getMessage());
        screenShots.takeScreenShot1();
    } catch (ElementNotInteractableException e) {
        log.ReportEvent("ERROR", "Currency element not interactable: " + e.getMessage());
        screenShots.takeScreenShot1();
    } catch (Exception e) {
        log.ReportEvent("ERROR", "An unexpected error occurred: " + e.getMessage());
        screenShots.takeScreenShot1();
    }
}


public void selectFromAndToFlightsBasedOnIndex(int Fromindex, int Toindex) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    try {
    	Thread.sleep(4000);
    	// Scroll to top
        js.executeScript("window.scrollTo(0, 0);");

        // Select 'From' flight
        String fromXPath = "(//*[@class='round-trip-from-results']//button[text()='View Flight'])[" + Fromindex + "]";
        WebElement fromDiv = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(fromXPath)));

        try {
            wait.until(ExpectedConditions.elementToBeClickable(fromDiv)).click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Standard click failed. Trying JS click.");
            js.executeScript("arguments[0].click();", fromDiv);
        }

        System.out.println(" Clicked 'From' flight at index: " + Fromindex);

        // Short wait to let UI load properly
        Thread.sleep(1000);

        // Select 'To' flight
        String returnXPath = "(//*[@class='round-trip-to-results']//button[text()='View Flight'])[" + Toindex + "]";
        WebElement returnDiv = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(returnXPath)));

        // Scroll to 'To' flight element and click
        try {
            wait.until(ExpectedConditions.elementToBeClickable(returnDiv)).click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Standard click failed. Trying JS click.");
            js.executeScript("arguments[0].click();", returnDiv);
        }

        System.out.println(" Clicked 'To' flight at index: " + Toindex);

    } catch (Exception e) {
        System.err.println(" Exception during flight selection: " + e.getMessage());
        e.printStackTrace();
        Assert.fail("Flight selection failed due to exception: " + e.getMessage());
    }
}

//Method to select Currency DropDown Values

public void selectCurrencyDropDownValues(WebDriver driver,String value) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    WebElement CurrencyValue = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[text()='"+value+"']")
            ));

    JavascriptExecutor js = (JavascriptExecutor) driver;
    // 🛠️ FIXED: "argument" → "arguments"
    js.executeScript("arguments[0].scrollIntoView(true);", CurrencyValue);

    wait.until(ExpectedConditions.elementToBeClickable(CurrencyValue)).click();
}





//---------------------------------------------------------------------------------------------------------
//Method to Validate default Currency Value
public void defaultCurrencyValue(Log Log, ScreenShots screenShots) {
    try {
    	Thread.sleep(4000);
        // Step 1: Get the default currency from the dropdown
        String defaultCurrency = driver.findElement(
            By.xpath("//*[contains(@class, 'tg-currency-change')]//*[contains(@class, 'tg-select__single-value')]")
        ).getText().trim();

        String expectedSymbol = getCurrencySymbol(defaultCurrency); // Helper method below

        if (expectedSymbol != null) {
            Log.ReportEvent("PASS", "Default currency value displayed: " + defaultCurrency);
            // Step 2: Validate all 'FROM' prices
            List<WebElement> fromPrices = driver.findElements(By.xpath("//*[contains(@class, 'tg-fromprice')]"));
            boolean allFromValid = validateCurrencyPrices(fromPrices, expectedSymbol, "FROM", Log);

            // Step 3: Validate all 'TO' prices
            List<WebElement> toPrices = driver.findElements(By.xpath("//*[contains(@class, 'tg-toprice')]"));
            boolean allToValid = validateCurrencyPrices(toPrices, expectedSymbol, "TO", Log);

            // Step 4: Final check
            if (!allFromValid || !allToValid) {
                Assert.fail("One or more prices are not in the expected currency format.");
            }

        } else {
            Log.ReportEvent("FAIL", "Unsupported or unexpected default currency: " + defaultCurrency);
            screenShots.takeScreenShot1();
            Assert.fail("No symbol found for default currency: " + defaultCurrency);
            
        }

        screenShots.takeScreenShot1(); // Always take screenshot

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception while validating currency: " + e.getMessage());
        screenShots.takeScreenShot1();
        e.printStackTrace();
        Assert.fail("Exception occurred during currency validation");
    }
}
private String getCurrencySymbol(String currencyCode) {
    switch (currencyCode) {
        case "INR": return "₹";
        case "USD": return "$";
        case "EUR": return "€";
        // Add more currencies as needed
        default: return null;
    }
}
private boolean validateCurrencyPrices(List<WebElement> prices, String expectedSymbol, String label, Log Log) {
    boolean allValid = true;

    for (WebElement priceElement : prices) {
        String value = priceElement.getText().trim();
        System.out.println(label + " Price Displayed: " + value);

        if (value.startsWith(expectedSymbol)) {
            System.out.println("✅ Valid " + label + " price: " + value);
        } else {
            System.out.println("❌ Invalid " + label + " price: " + value);
            Log.ReportEvent("FAIL", "Invalid " + label + " currency value: " + value);
            allValid = false;
        }
    }

    return allValid;
}


//--------------------------------------------------------------------------------------------------------------

//method to check whether depart time of all flights are displaying in ascending order from div
public void timeOrderCheckInAscendingForDepartForFromDiv(Log Log,ScreenShots ScreenShots) {
       

    try {
        
        // Find all matching elements
        List<WebElement> departTime = driver.findElements(By.xpath("//div[@class='round-trip-from-results']//h3[@data-tgdeptime]"));

        // Extract time strings from h3 elements inside each div
        List<String> timeStrings = new ArrayList<>();
        for (WebElement departTime1 : departTime) {
            String h3 = departTime1.getText();
            timeStrings.add(h3.trim());
            System.out.println(timeStrings);
        }

        // Convert time strings to minutes
        List<Integer> timesInMinutes = new ArrayList<>();
        for (String time : timeStrings) {
            String[] parts = time.split(":");
            int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
            timesInMinutes.add(minutes);
            System.out.println(timesInMinutes);
        }

        // Check if times are in ascending order
        boolean isAscending = true;
        for (int i = 0; i < timesInMinutes.size() - 1; i++) {
            if (timesInMinutes.get(i) > timesInMinutes.get(i + 1)) {
                isAscending = false;
                break;
            }
        }
        if(isAscending)
        {

            Log.ReportEvent("PASS","Flights are displaying in ascending order");

            System.out.println("Flights are displaying in ascending order");

        }
        else
        {

            Log.ReportEvent("FAIL", "Flights are Not displaying in ascending order");

            System.out.println("Flights are Not displaying in ascending order");
        }
       

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
       
    }
}



//Method to check whether depart flights result are appearing in descending order from div
public void timeOrderCheckInDescendingForDepartForFromDiv(Log Log,ScreenShots ScreenShots) {
       

    try {
        
        // Find all matching elements
        List<WebElement> divs = driver.findElements(By.xpath("//div[@class='round-trip-from-results']//h3[@data-tgdeptime]"));

        // Extract time strings from h3 elements inside each div
        List<String> timeStrings = new ArrayList<>();
        for (WebElement div : divs) {
            String h3 = div.getText();
            timeStrings.add(h3.trim());
            System.out.println(timeStrings);
        }

        // Convert time strings to minutes
        List<Integer> timesInMinutes = new ArrayList<>();
        for (String time : timeStrings) {
            String[] parts = time.split(":");
            int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
            timesInMinutes.add(minutes);
            System.out.println(timesInMinutes);
        }

        // Check if times are in ascending order
        boolean isDescending = true;
        for (int i = 0; i < timesInMinutes.size() - 1; i++) {
            if (timesInMinutes.get(i) < timesInMinutes.get(i + 1)) {
                isDescending = false;
                break;
            }
        }
        if(isDescending)
        {

            Log.ReportEvent("PASS", "Flights are displaying in Descending order");

            System.out.println("Flights are displaying in Descending order");

        }
        else
        {

            Log.ReportEvent("FAIL", "Flights are Not displaying in Descending order");

            System.out.println("Flights are Not displaying in Descending order");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
       
    }
}




//method to check whether depart time of all flights in ascending order To div
public void timeOrderCheckInAscendingForDepartForToDiv(Log Log,ScreenShots ScreenShots) {
       

    try {
        
        // Find all matching elements
        List<WebElement> departTime = driver.findElements(By.xpath("//div[@class='round-trip-to-results']//h3[@data-tgdeptime]"));

        // Extract time strings from h3 elements inside each div
        List<String> timeStrings = new ArrayList<>();
        for (WebElement departTime1 : departTime) {
            String h3 = departTime1.getText();
            timeStrings.add(h3.trim());
            System.out.println(timeStrings);
        }

        // Convert time strings to minutes
        List<Integer> timesInMinutes = new ArrayList<>();
        for (String time : timeStrings) {
            String[] parts = time.split(":");
            int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
            timesInMinutes.add(minutes);
            System.out.println(timesInMinutes);
        }

        // Check if times are in ascending order
        boolean isAscending = true;
        for (int i = 0; i < timesInMinutes.size() - 1; i++) {
            if (timesInMinutes.get(i) > timesInMinutes.get(i + 1)) {
                isAscending = false;
                break;
            }
        }
        if(isAscending)
        {

            Log.ReportEvent("PASS", "Flights are displaying in ascending order");

            System.out.println("Flights are displaying in ascending order");

        }
        else
        {

            Log.ReportEvent("FAIL", "Flights are Not displaying in ascending order");

            System.out.println("Flights are Not displaying in ascending order");
        }
       

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
       
    }
 }


//Method to check whether depart flights result are appearing in descending order To div
public void timeOrderCheckInDescendingForDepartForToDiv(Log Log,ScreenShots ScreenShots) {
       

    try {
        
        // Find all matching elements
        List<WebElement> departTime = driver.findElements(By.xpath("//div[@class='round-trip-to-results']//h3[@data-tgdeptime]"));

        // Extract time strings from h3 elements inside each div
        List<String> timeStrings = new ArrayList<>();
        for (WebElement departTime1 : departTime) {
            String h3 = departTime1.getText();
            timeStrings.add(h3.trim());
            System.out.println(timeStrings);
        }

        // Convert time strings to minutes
        List<Integer> timesInMinutes = new ArrayList<>();
        for (String time : timeStrings) {
            String[] parts = time.split(":");
            int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
            timesInMinutes.add(minutes);
            System.err.println(timesInMinutes);
        }

        // Check if times are in ascending order
        boolean isDescending = true;
        for (int i = 0; i < timesInMinutes.size() - 1; i++) {
            if (timesInMinutes.get(i) < timesInMinutes.get(i + 1)) {
                isDescending = false;
                break;
            }
        }
        if(isDescending)
        {
            Log.ReportEvent("PASS", "Flights are displaying in Descending order");

            System.out.println("Flights are displaying in Descending order");

        }
        else
        {
            Log.ReportEvent("PASS", "Flights are Not displaying in Descending order");

            System.out.println("Flights are Not displaying in Descending order");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
       
    }
 }

//method to check whether depart time of all flights are displaying in ascending order from div
public void TimeOrderCheckInAscendingForDepartForFromDiv(Log Log,ScreenShots ScreenShots) {
     
    try {
        
        // Find all matching elements
        List<WebElement> departTime = driver.findElements(By.xpath("//div[@class='round-trip-from-results']//h3[@data-tgdeptime]"));

        // Extract time strings from h3 elements inside each div
        List<String> timeStrings = new ArrayList<>();
        for (WebElement departTime1 : departTime) {
            String h3 = departTime1.getText();
            timeStrings.add(h3.trim());
            System.out.println(timeStrings);
        }

        // Convert time strings to minutes
        List<Integer> timesInMinutes = new ArrayList<>();
        for (String time : timeStrings) {
            String[] parts = time.split(":");
            int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
            timesInMinutes.add(minutes);
            System.out.println(timesInMinutes);
        }

        // Check if times are in ascending order
        boolean isAscending = true;
        for (int i = 0; i < timesInMinutes.size() - 1; i++) {
            if (timesInMinutes.get(i) > timesInMinutes.get(i + 1)) {
                isAscending = false;
                break;
            }
        }
        if(isAscending)
        {

            Log.ReportEvent("PASS","Flights are displaying in ascending order");

            System.out.println("Flights are displaying in ascending order");

        }
        else
        {

            Log.ReportEvent("FAIL", "Flights are Not displaying in ascending order");

            System.out.println("Flights are Not displaying in ascending order");
        }
       

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
       
    }
 }


//Method to check whether depart flights result are appearing in descending order from div
public void TimeOrderCheckInDescendingForDepartForFromDiv(Log Log,ScreenShots ScreenShots) {
       

    try {
        
        // Find all matching elements
        List<WebElement> divs = driver.findElements(By.xpath("//div[@class='round-trip-from-results']//h3[@data-tgdeptime]"));

        // Extract time strings from h3 elements inside each div
        List<String> timeStrings = new ArrayList<>();
        for (WebElement div : divs) {
            String h3 = div.getText();
            timeStrings.add(h3.trim());
            System.out.println(timeStrings);
        }

        // Convert time strings to minutes
        List<Integer> timesInMinutes = new ArrayList<>();
        for (String time : timeStrings) {
            String[] parts = time.split(":");
            int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
            timesInMinutes.add(minutes);
            System.out.println(timesInMinutes);
        }

        // Check if times are in ascending order
        boolean isDescending = true;
        for (int i = 0; i < timesInMinutes.size() - 1; i++) {
            if (timesInMinutes.get(i) < timesInMinutes.get(i + 1)) {
                isDescending = false;
                break;
            }
        }
        if(isDescending)
        {

            Log.ReportEvent("PASS", "Flights are displaying in Descending order");

            System.out.println("Flights are displaying in Descending order");

        }
        else
        {

            Log.ReportEvent("FAIL", "Flights are Not displaying in Descending order");

            System.out.println("Flights are Not displaying in Descending order");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
       
    }
 }



//method to check whether depart time of all flights in ascending order To div
public void TimeOrderCheckInAscendingForDepartForToDiv(Log Log,ScreenShots ScreenShots) {
       

    try {
        
        // Find all matching elements
        List<WebElement> departTime = driver.findElements(By.xpath("//div[@class='round-trip-to-results']//h3[@data-tgdeptime]"));

        // Extract time strings from h3 elements inside each div
        List<String> timeStrings = new ArrayList<>();
        for (WebElement departTime1 : departTime) {
            String h3 = departTime1.getText();
            timeStrings.add(h3.trim());
            System.out.println(timeStrings);
        }

        // Convert time strings to minutes
        List<Integer> timesInMinutes = new ArrayList<>();
        for (String time : timeStrings) {
            String[] parts = time.split(":");
            int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
            timesInMinutes.add(minutes);
            System.out.println(timesInMinutes);
        }

        // Check if times are in ascending order
        boolean isAscending = true;
        for (int i = 0; i < timesInMinutes.size() - 1; i++) {
            if (timesInMinutes.get(i) > timesInMinutes.get(i + 1)) {
                isAscending = false;
                break;
            }
        }
        if(isAscending)
        {

            Log.ReportEvent("PASS", "Flights are displaying in ascending order");

            System.out.println("Flights are displaying in ascending order");

        }
        else
        {

            Log.ReportEvent("FAIL", "Flights are Not displaying in ascending order");

            System.out.println("Flights are Not displaying in ascending order");
        }
       

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
       
    }
 }

//Method to check whether depart flights result are appearing in descending order To div
public void TimeOrderCheckInDescendingForDepartForToDiv(Log Log,ScreenShots ScreenShots) {
       

    try {
        
        // Find all matching elements
        List<WebElement> departTime = driver.findElements(By.xpath("//div[@class='round-trip-to-results']//h3[@data-tgdeptime]"));

        // Extract time strings from h3 elements inside each div
        List<String> timeStrings = new ArrayList<>();
        for (WebElement departTime1 : departTime) {
            String h3 = departTime1.getText();
            timeStrings.add(h3.trim());
            System.out.println(timeStrings);
        }

        // Convert time strings to minutes
        List<Integer> timesInMinutes = new ArrayList<>();
        for (String time : timeStrings) {
            String[] parts = time.split(":");
            int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
            timesInMinutes.add(minutes);
            System.err.println(timesInMinutes);
        }

        // Check if times are in ascending order
        boolean isDescending = true;
        for (int i = 0; i < timesInMinutes.size() - 1; i++) {
            if (timesInMinutes.get(i) < timesInMinutes.get(i + 1)) {
                isDescending = false;
                break;
            }
        }
        if(isDescending)
        {
            Log.ReportEvent("PASS", "Flights are displaying in Descending order");

            System.out.println("Flights are displaying in Descending order");

        }
        else
        {
            Log.ReportEvent("PASS", "Flights are Not displaying in Descending order");

            System.out.println("Flights are Not displaying in Descending order");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
       
    }
 }


//Method to click on Depart ascending filter To div
public void DepartAscendingFilterForToDiv()
{
driver.findElement(By.xpath("(//small[text()='DEPART'])[2]")).click();
}
//Method to click on depart descending filter To div
public void DepartDescendingFilterForToDiv()
{

driver.findElement(By.xpath("(//small[text()='DEPART'])[2]")).click();
}
//Method to click on Depart ascending filter
public void DepartAscendingFilterForFromDiv()
{
    driver.findElement(By.xpath("(//small[text()='DEPART'])[1]")).click();
}
//Method to click on depart descending filter From div
public void DepartDescendingFilterForFromDiv()
{

    driver.findElement(By.xpath("(//small[text()='DEPART'])[1]")).click();
}
	
//--------------------------------------------------------------------------------------------------------------------

//Method to adjust Minimum Slider To Value

public void  adjustMinimumSliderToValue(WebDriver driver, double targetMinValue) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(0, -document.body.scrollHeight);");



    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement minSliderInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@data-index='0']")));

        double minValue = Double.parseDouble(minSliderInput.getAttribute("aria-valuemin"));
        double maxValue = Double.parseDouble(minSliderInput.getAttribute("aria-valuemax"));

        double clampedValue = Math.max(minValue, Math.min(maxValue, targetMinValue));
        double percentage = (clampedValue - minValue) / (maxValue - minValue);

        WebElement sliderTrack = driver.findElement(By.xpath("//span[contains(@class, 'MuiSlider-track')]"));
        int trackWidth = sliderTrack.getSize().getWidth();

        int targetOffset = (int) (trackWidth * percentage);

        WebElement thumbHandle = driver.findElement(By.xpath("//input[@data-index='0']"));
        int thumbX = thumbHandle.getLocation().getX();
        int trackX = sliderTrack.getLocation().getX();
        int currentOffset = thumbX - trackX;

        int moveBy = targetOffset - currentOffset;

        new Actions(driver)
        .moveToElement(thumbHandle)
        .pause(Duration.ofMillis(300))
        .clickAndHold()
        .moveByOffset(moveBy, 0)
        .pause(Duration.ofMillis(200))
        .release()
        .perform();

        // Tolerance wait
        wait.until(driver1 -> {
            String actualValueStr = minSliderInput.getAttribute("aria-valuenow");
            try {
                double actualValue = Double.parseDouble(actualValueStr);
                return Math.abs(actualValue - clampedValue) <= 300; // allow 300 units tolerance
            } catch (NumberFormatException e) {
                return false;
            }
        });

        System.out.println("✅ Min slider moved to: " + minSliderInput.getAttribute("aria-valuenow"));

    } catch (Exception e) {
        System.err.println("❌ Failed to move min slider: " + e.getMessage());
        e.printStackTrace();
    }

}





//----------------------------------------------------------------------------------------

//Method to clear filter
public void clearfilter(Log Log, ScreenShots ScreenShots)
{
    try {
        clickOnClearFilter();
        Thread.sleep(4000);
        Log.ReportEvent("PASS", "The applied Filter got cleared");
    }
    catch (Exception e) {
        Log.ReportEvent("FAIL", "The applied Filter got cleared: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
        Assert.fail();
    }
}



//Method to validate Default And Latest Price Range Values On SlideBar For RoundWay
public void validateDefaultAndLatestPriceRangeValuesOnSlideBarForRoundWay(
        Log Log, ScreenShots ScreenShots,
        double minValue, double maxValue,
        double newMinValue, double newMaxValue) {

    try {
        if (Double.compare(minValue, newMinValue) == 0 && Double.compare(maxValue, newMaxValue) == 0) {
            Log.ReportEvent("PASS", "Default price range and Latest price range are similar after clearing filter.");
        } else {
            Log.ReportEvent("FAIL", "Default price range changed after applying 'Clear Filter'. "
                    + "Expected Min: " + minValue + ", Actual Min: " + newMinValue + " | "
                    + "Expected Max: " + maxValue + ", Actual Max: " + newMaxValue);
            Assert.fail();
        }

        ScreenShots.takeScreenShot1();
    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception during validation: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
        Assert.fail();
    }
}


//--------------------------------------------------------------------------------------------------------

//Method to validate Sme Filter
public void validateSmeFilter(int index, Log log, ScreenShots screenshots) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    List<String> validFares = Arrays.asList("sme fare", "corporate fare");

    String fromFlightCardXpath = "//div[@class='round-trip-from-results']//div[contains(@class, 'round-trip-card')]";
    String returnFlightCardXpath = "//div[@class='round-trip-to-results']//div[contains(@class, 'round-trip-card')]";

    boolean fromResult = validateFlightCardFares(fromFlightCardXpath, index, validFares, log, screenshots, wait, "From Flight Card");
    boolean returnResult = validateFlightCardFares(returnFlightCardXpath, index, validFares, log, screenshots, wait, "Return Flight Card");

    if (fromResult && returnResult) {
        log.ReportEvent("PASS", "SME filter validation passed for both From and Return flight cards.");
    } else {
        log.ReportEvent("FAIL", "SME filter validation failed.");
    }
}

private boolean validateFlightCardFares(String flightCardXpath, int index, List<String> validFares,
        Log log, ScreenShots screenshots, WebDriverWait wait, String cardName) {
    try {
        // Wait and click "View Flight" button for specified card and index
        WebElement viewFlightBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(" + flightCardXpath + "//button[text()='View Flight'])[" + index + "]")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", viewFlightBtn);

        try {
            viewFlightBtn.click();
        } catch (Exception e) {
            // Fallback to JS click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewFlightBtn);
        }

        // Wait for fare elements to appear
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h6[@data-tgfaretype]")));

        List<WebElement> fares = driver.findElements(By.xpath("//h6[@data-tgfaretype]"));

        // Validate each fare text
        for (WebElement fare : fares) {
            String fareText = fare.getText().trim().toLowerCase();
            if (!validFares.contains(fareText)) {
                log.ReportEvent("FAIL", cardName + ": Unexpected fare found: '" + fareText + "'");
                screenshots.takeScreenShot1();
                closePopup();
                return false;
            }
        }

        log.ReportEvent("PASS", cardName + ": Only SME/Corporate fares are displayed.");
        closePopup();
        return true;

    } catch (Exception e) {
        log.ReportEvent("ERROR", cardName + ": Error during validation - " + e.getMessage());
        screenshots.takeScreenShot1();
        return false;
    }
}

//private void closePopup() {
//    try {
//        WebElement closeBtn = driver.findElement(By.xpath("//button[text()='Close']"));
//        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
//    } catch (Exception e) {
//        // Log or ignore if close button not found; popup might already be closed
//    }
//}

//---------------------------------------------------------------------------------------------
//Method to validate Sme Fare Filter Unchecked
public void validateSmeFareFilterUnchecked(int index, Log log, ScreenShots screenshots, boolean isSmeFilterChecked) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(0, -document.body.scrollHeight)");

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    // Define expected fare types
    List<String> smeFares = Arrays.asList("sme fare", "corporate fare");
    List<String> allFares = Arrays.asList(
            "normal fare",
            "sme fare",
            "flexi fare",
            "super 6e fare",
            "corporate fare",
            "special fare",
            "corporate select fare"
            );

    Set<String> validFares = (isSmeFilterChecked ? smeFares : allFares)
            .stream()
            .map(String::toLowerCase)
            .map(String::trim)
            .collect(Collectors.toSet());

    // Locate and scroll to SME checkbox
    WebElement smeCheckBox = driver.findElement(By.xpath("//legend[text()='SME / Corporate Fare']//parent::div//input"));
    js.executeScript("arguments[0].scrollIntoView(true);", smeCheckBox);

    // Verify checkbox state
    if (smeCheckBox.isSelected() == isSmeFilterChecked) {
        log.ReportEvent("PASS", "SME checkbox is " + (isSmeFilterChecked ? "selected." : "unselected."));
    } else {
        log.ReportEvent("FAIL", "SME checkbox state is incorrect. Expected: " + (isSmeFilterChecked ? "selected" : "unselected"));
        screenshots.takeScreenShot1();
        Assert.fail("Expected SME checkbox to be " + (isSmeFilterChecked ? "selected" : "unselected"));
    }

    js.executeScript("window.scrollBy(0, -document.body.scrollHeight)");

    // Define flight card XPaths
    String fromFlightCardXpath = "//div[@class='round-trip-from-results']//div[contains(@class, 'round-trip-card')]";
    String returnFlightCardXpath = "//div[@class='round-trip-to-results']//div[contains(@class, 'round-trip-card')]";

    boolean fromResult = validateFlightCardFares(fromFlightCardXpath, index, validFares, log, screenshots, wait, "From Flight Card");
    boolean returnResult = validateFlightCardFares(returnFlightCardXpath, index, validFares, log, screenshots, wait, "Return Flight Card");

    if (fromResult && returnResult) {
        log.ReportEvent("PASS", "Fare validation passed for both From and Return flight cards with SME filter " + (isSmeFilterChecked ? "checked." : "unchecked."));
    } else {
        log.ReportEvent("FAIL", "Fare validation failed with SME filter " + (isSmeFilterChecked ? "checked." : "unchecked."));
    }
}

private boolean validateFlightCardFares(String flightCardXpath, int index, Set<String> validFares,
        Log log, ScreenShots screenshots, WebDriverWait wait, String cardName) {
    try {
        WebElement viewFlightBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(" + flightCardXpath + "//button[text()='View Flight'])[" + index + "]")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", viewFlightBtn);

        try {
            viewFlightBtn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewFlightBtn);
        }

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h6[@data-tgfaretype]")));
        List<WebElement> fares = driver.findElements(By.xpath("//h6[@data-tgfaretype]"));

        List<String> foundFares = new ArrayList<>();
        boolean allValid = true;

        for (WebElement fare : fares) {
            String fareText = fare.getText().trim().toLowerCase();
            foundFares.add(fareText);
            if (!validFares.contains(fareText)) {
                log.ReportEvent("FAIL", cardName + ": Unexpected fare found: '" + fareText + "'");
                allValid = false;
            }
        }

        // Print all detected fare types to console
        System.out.println("----- " + cardName + " Fare Types -----");
        for (String fare : foundFares) {
            System.out.println(" - " + fare);
        }

        if (allValid) {
            log.ReportEvent("PASS", cardName + ": All fares are valid.");
        } else {
            log.ReportEvent("FAIL", cardName + ": One or more fares are invalid.");
        }

        screenshots.takeScreenShot1();
        closePopup();
        return allValid;

    } catch (Exception e) {
        log.ReportEvent("ERROR", cardName + ": Error during validation - " + e.getMessage());
        screenshots.takeScreenShot1();
        return false;
    }
}
//------------------------------------------------------------------------------------------------------------------

//Method to get total Flight Count From And To Before Applying Filter
public void totalFlightCountFromAndToBeforeApplyingFilter(Log log,ScreenShots screenShots) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(0, -document.body.scrollHeight)");
    // Corrected XPath expressions
    String totalCountFrom = driver.findElement(By.xpath("//span[@id='onward_flight_count']")).getText();
    String totalCountTo = driver.findElement(By.xpath("//span[@id='return_flight_count']")).getText();

    // Improved log message formatting
    log.ReportEvent("INFO", "Total Flights Found Before Applying Filter: From = " 
            + totalCountFrom + ", To = " + totalCountTo);
    screenShots.takeScreenShot1();
}

//Method to get total Flight Count From And To After Applying Filter
public void totalFlightCountFromAndToAfterApplyingFilter(Log log,ScreenShots screenShots) throws InterruptedException {
    Thread.sleep(4000);
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(0, -document.body.scrollHeight)");
    // Corrected XPath expressions
    String totalCountFrom = driver.findElement(By.xpath("//span[@id='onward_flight_count']")).getText();
    String totalCountTo = driver.findElement(By.xpath("//span[@id='return_flight_count']")).getText();

    // Improved log message formatting
    log.ReportEvent("INFO", "Total Flights Found After Applying Filter: From = " 
            + totalCountFrom + ", To = " + totalCountTo);
    screenShots.takeScreenShot1();
}

//------------------------------------------------------------------------------------------------

@FindBy(xpath = "//div[@class='MuiGrid2-root MuiGrid2-direction-xs-row css-uzfmmu']")
WebElement searchButton;
//Method to Click on Search Button
	public void clickOnSearchButton() throws InterruptedException
	{
		Thread.sleep(5000);

		searchButton.click();
	}

//Function for to click and validate swap roundtrip
public void swapRoundtrip() throws InterruptedException {
	JavascriptExecutor js = (JavascriptExecutor) driver;
	js.executeScript("window.scrollTo(0, 0);");
	driver.findElement(By.xpath("//button[@title='swap airports']")).click();
	WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
	WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//*[contains(@class,'tg-fssearch-bt')]")));
        searchButton.click();
        Thread.sleep(3000);

}


//Method to validate BeforeSwap
public void validateBeforeSwap(Log Log, ScreenShots ScreenShots, int index) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    try {
        // Get 'From' and 'To' search codes first
        WebElement fromSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@class,'tg-fsorigin')]//*[contains(@class,'tg-select__single-value')]")));
        WebElement toSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@class,'tg-fsdestination')]//*[contains(@class,'tg-select__single-value')]")));

        String fromSearchText = fromSearch.getText(); // e.g. "Bengaluru, India (BLR)"
        String toSearchText = toSearch.getText();     // e.g. "Hyderabad, India (HYD)"

        String fromSearchCode = fromSearchText.replaceAll(".*\\((.*?)\\)", "$1").trim().toUpperCase();
        String toSearchCode = toSearchText.replaceAll(".*\\((.*?)\\)", "$1").trim().toUpperCase();
//        
//        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
//            By.xpath("//*[contains(@class,'tg-fssearch-bt')]")));
//        searchButton.click();
//        Thread.sleep(3000);
        
        //  Click 'View Flight' button to open details
        String xpath = "(//*[contains(@class,'round-trip-from-results')]//button[text()='View Flight'])[" + index + "]";
        WebElement viewflightbutton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewflightbutton);
        Thread.sleep(500);
        try {
            viewflightbutton.click(); 
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", viewflightbutton);

        }

//        //  Wait for origin and destination elements in flight popup
//        WebElement origin = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.id("undefined-Origin")));
//        WebElement destination = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.id("undefined-destination")));
//
//        String originText = origin.getAttribute("data-tgfloriginairport");         // e.g. "BLR, India"
//        String destinationText = destination.getAttribute("data-tgfldestinationairport"); // e.g. "HYD, India"
//
//        String originCode = originText.split(",")[0].trim().toUpperCase();         // BLR
//        String destinationCode = destinationText.split(",")[0].trim().toUpperCase(); // HYD


        WebElement origin = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("undefined-Origin")));
        WebElement destination = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("undefined-destination")));

        // Extract the visible text directly from the <h6> element
        String originText = origin.getText();         // e.g., "Hyderabad, India (HYD)"
        String destinationText = destination.getText(); // e.g., "Bengaluru, India (BLR)"

        // Extract airport codes from the visible text (between parentheses)
        String originCode = originText.substring(originText.indexOf("(") + 1, originText.indexOf(")")).trim();
        String destinationCode = destinationText.substring(destinationText.indexOf("(") + 1, destinationText.indexOf(")")).trim();

        // Print for verification
        System.out.println("Origin Code: " + originCode);       // HYD
        System.out.println("Destination Code: " + destinationCode); // BLR

        // Validation
        if (fromSearchCode.equals(originCode) && toSearchCode.equals(destinationCode)) {
            Log.ReportEvent("PASS", "Before Swap Airport codes match: From : Origin[" + fromSearchCode + " = " + originCode + "], To : Destination[" + toSearchCode + " = " + destinationCode + "]");

        } else {
            Log.ReportEvent("FAIL", "Before Swap Mismatch in airport codes: From : Origin[" + fromSearchCode + " ≠ " + originCode + "], To : Destination[" + toSearchCode + " ≠ " + destinationCode + "]");
            ScreenShots.takeScreenShot1();
            Assert.fail("Airport codes do not match with origin/destination.");
        }

        //Close result popup after validation
        closeButtononresultpage();
    	js.executeScript("window.scrollTo(0, 0);");

        

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception occurred while checking flights on Result Screen");
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
        Assert.fail();
    }
}

//Method to validate AfterSwap
public void validateAfterSwap(Log Log, ScreenShots ScreenShots, int index) {
  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
  JavascriptExecutor js = (JavascriptExecutor) driver;

  try {
      //  Get 'From' and 'To' search codes first
      WebElement fromSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(
              By.xpath("(//div[contains(@class,'tg-select__single-value css-1dimb5e-singleValue')])[1]")));
      WebElement toSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(
              By.xpath("(//div[contains(@class,'tg-select__single-value css-1dimb5e-singleValue')])[2]")));

      String fromSearchText = fromSearch.getText(); // e.g. "Bengaluru, India (BLR)"
      String toSearchText = toSearch.getText();     // e.g. "Hyderabad, India (HYD)"

      String fromSearchCode = fromSearchText.replaceAll(".*\\((.*?)\\)", "$1").trim().toUpperCase();
      String toSearchCode = toSearchText.replaceAll(".*\\((.*?)\\)", "$1").trim().toUpperCase();
      
      
      //  Click 'View Flight' button to open details
      String xpath = "(//*[contains(@class,'round-trip-to-results')]//button[text()='View Flight'])[" + index + "]";
      WebElement viewflightbutton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
      js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewflightbutton);
      Thread.sleep(500);
      try {
          viewflightbutton.click(); 
      } catch (ElementClickInterceptedException e) {
          js.executeScript("arguments[0].click();", viewflightbutton);

      }

//      //  Wait for origin and destination elements in flight popup
//      WebElement origin = wait.until(ExpectedConditions.visibilityOfElementLocated(
//              By.id("undefined-Origin")));
//      WebElement destination = wait.until(ExpectedConditions.visibilityOfElementLocated(
//              By.id("undefined-destination")));
//
//      String originText = origin.getAttribute("data-tgfloriginairport");         // e.g. "BLR, India"
//      String destinationText = destination.getAttribute("data-tgfldestinationairport"); // e.g. "HYD, India"
//
//      String originCode = originText.split(",")[0].trim().toUpperCase();         // BLR
//      String destinationCode = destinationText.split(",")[0].trim().toUpperCase(); // HYD
//
      

      WebElement origin = wait.until(ExpectedConditions.visibilityOfElementLocated(
              By.id("undefined-Origin")));
      WebElement destination = wait.until(ExpectedConditions.visibilityOfElementLocated(
              By.id("undefined-destination")));

      // Extract the visible text (e.g., "Hyderabad, India (HYD)")
      String originText = origin.getText();
      String destinationText = destination.getText();

      // Extract the airport codes from inside parentheses
      String originCode = originText.substring(originText.indexOf("(") + 1, originText.indexOf(")")).trim();         // HYD
      String destinationCode = destinationText.substring(destinationText.indexOf("(") + 1, destinationText.indexOf(")")).trim(); // BLR

      // Validation (example)
      System.out.println("Origin Code: " + originCode);
      System.out.println("Destination Code: " + destinationCode);

      //  Validation
      if (fromSearchCode.equals(originCode) && toSearchCode.equals(destinationCode)) {
          Log.ReportEvent("PASS", "After Swap Airport codes match: From : Origin[" + fromSearchCode + " = " + originCode + "], To : Destination[" + toSearchCode + " = " + destinationCode + "]");

      } else {
          Log.ReportEvent("FAIL", "After Swap Mismatch in airport codes: From : Origin[" + fromSearchCode + " ≠ " + originCode + "], To : Destination[" + toSearchCode + " ≠ " + destinationCode + "]");
          ScreenShots.takeScreenShot1();
          Assert.fail("Airport codes do not match with origin/destination.");
      }

      //  Close result popup after validation
      closeButtononresultpage();

  } catch (Exception e) {
      Log.ReportEvent("FAIL", "Exception occurred while checking flights on Result Screen");
      ScreenShots.takeScreenShot1();
      e.printStackTrace();
      Assert.fail();
  }
}




//--------------------------------------------------------------------------------------------------------------

//Method to validate Policy Filter UnChecked InPolicy and OutOfPolicy
public void validatePolicyFilterUnChecked(Log Log, ScreenShots ScreenShots) {
    try {
        // Step 0: Verify that the Policy Filter is unchecked
        WebElement policyFilterCheckbox = driver.findElement(By.xpath("//*[contains(@class,'tg-inpolicy')]//input[@name='in']"));
        boolean isChecked = policyFilterCheckbox.isSelected();

        if (isChecked) {
            Log.ReportEvent("FAIL", " Policy filter is still checked. Uncheck it before running this test.");
            ScreenShots.takeScreenShot1();
            Assert.fail("Policy filter is not unchecked.");
            return;
        }

        Log.ReportEvent("INFO", " Policy filter is confirmed to be unchecked.");

        // Step 1: Wait for at least one in-policy or out-of-policy element to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='inpolicy tg-policy']")),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='outofpolicy tg-policy']"))
        ));

        // Step 2: Collect policy status elements
        List<WebElement> inPolicyElements = driver.findElements(By.xpath("//*[@class='inpolicy tg-policy']"));
        List<WebElement> outOfPolicyElements = driver.findElements(By.xpath("//*[@class='outofpolicy tg-policy']"));

        boolean foundInPolicy = !inPolicyElements.isEmpty();
        boolean foundOutOfPolicy = !outOfPolicyElements.isEmpty();

        // Log what was found
        if (foundInPolicy && foundOutOfPolicy) {
            Log.ReportEvent("PASS", " Both 'In Policy' and 'Out of Policy' flights are displayed when the filter is unchecked.");
        } else if (foundInPolicy) {
            Log.ReportEvent("PASS", " Only 'In Policy' flights are displayed. Filter is unchecked but only some results are visible.");
        } else if (foundOutOfPolicy) {
            Log.ReportEvent("PASS", " Only 'Out of Policy' flights are displayed. Filter is unchecked but only some results are visible.");
        } else {
            Log.ReportEvent("FAIL", " No flights displayed with either 'In Policy' or 'Out of Policy' status.");
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
        Log.ReportEvent("FAIL", " Timed out waiting for policy status elements.");
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
        Assert.fail();
    } catch (Exception e) {
        Log.ReportEvent("FAIL", " Exception during policy filter unchecked validation: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
        Assert.fail();
    }
}


//---------------------------------------------------------------------------------------------

//Method to validate Resultpage DatePicker

public void validateResultpageDatePicker(Log Log, ScreenShots ScreenShots) throws Exception {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));

    try {
        // 1. Get date from Result Page input
        WebElement datePickerResultPage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Journey Date']")));
        String resultPageDateRaw = datePickerResultPage.getAttribute("value").trim();  // e.g., "14th-Jun-2025"

        // Format to "14 Jun"
        String formattedResultDate = resultPageDateRaw
                .replaceAll("(st|nd|rd|th)", "")   // remove ordinal suffix
                .replaceAll("-", " ")              // change format to "14 Jun"
                .split(" ")[0] + " " + resultPageDateRaw.split("-")[1];

        // 2. Select flight and proceed to booking page
        selectFromAndToFlightsBasedOnIndex(2,2);
        Thread.sleep(2000);
        WebElement continueButton = wait.until(ExpectedConditions.presenceOfElementLocated(
        	    By.xpath("//*[contains(@class,'tg-bar-continue-btn')]")));

        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", continueButton);

        	((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueButton);

        	Thread.sleep(3000); 

        // 3. Get date from Booking Page
       // WebElement dateElementBookingPage = driver.findElement(
             //   By.xpath("//small[@data-tgfldepdate]"));
        
        WebElement dateElementBookingPage = driver.findElement(
                By.xpath("//small[@data-tgdepartfldepdate]"));
       
        String bookingPageDateRaw = dateElementBookingPage.getText().trim();  // e.g., "on 14th Jun ,"

        // Format to "14 Jun"
        String formattedBookingDate = bookingPageDateRaw
                .replace("on", "")
                .replaceAll("(st|nd|rd|th)", "")
                .replaceAll("[^0-9a-zA-Z ]", "")
                .trim();
        System.out.println(formattedBookingDate);

        // 4. Compare dates
        if (formattedResultDate.equalsIgnoreCase(formattedBookingDate)) {
            Log.ReportEvent("PASS", " Date validation PASSED: " +
                    formattedResultDate + " matches " + formattedBookingDate);
        } else {
            Log.ReportEvent("FAIL", " Date validation FAILED: Expected '" +
                    formattedResultDate + "' but found '" + formattedBookingDate + "'");
            ScreenShots.takeScreenShot1();
            throw new AssertionError("Date validation failed");
        }

    } catch (Exception e) {
        Log.ReportEvent("FAIL", " Test failed with exception: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        throw e;
    }
}

public void ValidateBookingPageFlightsDetails(Log Log, ScreenShots ScreenShots) throws Exception {
	validateResultpageDatePicker(Log, ScreenShots);
	
}

//validate locations from result page to booking page roundtrip --- domestic

/*  working good 
 public void validateLocationsFromResultToBookingPage(Log Log, ScreenShots ScreenShots, int index) throws InterruptedException {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    try {
        // Click View Flight
        WebElement viewFlightButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//div[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewFlightButton);
        Thread.sleep(500);
        viewFlightButton.click();
        Thread.sleep(2000); // Allow modal to open

        // Determine stop type using data-tgnumstops attribute
        String stopType = "";
        int originIndex = 1, destinationIndex = 1;
        WebElement stopElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//div[@class='round-trip-from-results']//span[@data-tgnumstops])[" + index + "]")));
        String stopCount = stopElement.getAttribute("data-tgnumstops").trim();

        switch (stopCount) {
            case "0":
                stopType = "Nonstop";
                break;
            case "1":
                stopType = "Onestop";
                destinationIndex = 2;
                break;
            case "2":
                stopType = "Twostop";
                destinationIndex = 3;
                break;
            default:
                Log.ReportEvent("FAIL", "Stop type not detected: " + stopCount);
                ScreenShots.takeScreenShot1();
                Assert.fail("Stop type not recognized");
        }

        // Extract from result popup
        WebElement origin = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//h6[@data-tgfloriginairport])[" + originIndex + "]")));
        WebElement destination = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//h6[@data-tgfldestinationairport])[" + destinationIndex + "]")));
        String resultOrigin = origin.getAttribute("data-tgfloriginairport").split(",")[0].trim().toUpperCase();
        String resultDestination = destination.getAttribute("data-tgfldestinationairport").split(",")[0].trim().toUpperCase();
        Log.ReportEvent("INFO", "Result page Floight Location Details");
System.out.println(resultOrigin);
System.out.println(resultDestination);
        ScreenShots.takeScreenShot1();

        // Close the modal (try normal click, fallback to JS)
        try {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Close']")));
            try {
                closeBtn.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", closeBtn);
            }
        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Unable to click Close: " + e.getMessage());
            ScreenShots.takeScreenShot1();
            Assert.fail("Close button click failed");
        }

        // Proceed to Booking page
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Continue']")));
        continueBtn.click();

     // Scroll slightly down to ensure booking locations are in view
        js.executeScript("window.scrollBy(0, 200);");

        // Ensure booking section is loaded before extracting details
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h6[@data-tgdepartflorigin]")));

        // Try finding booking page airport elements
        WebElement bookingOriginElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//h6[@data-tgdepartflorigin])[" + originIndex + "]")));
        WebElement bookingDestElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//h6[@data-tgdepartfldestination])[" + destinationIndex + "]")));

        // Debug raw text for verification
        String rawBookingOrigin = bookingOriginElem.getText();
        String rawBookingDestination = bookingDestElem.getText();
        System.out.println("Booking Page Raw Origin: " + rawBookingOrigin);
        System.out.println("Booking Page Raw Destination: " + rawBookingDestination);

        // Extract airport codes safely
        String bookingOrigin = rawBookingOrigin.matches(".*\\(.*\\).*") ?
            rawBookingOrigin.replaceAll(".*\\((.*?)\\).*", "$1").trim().toUpperCase() :
            rawBookingOrigin.trim().toUpperCase();

        String bookingDestination = rawBookingDestination.matches(".*\\(.*\\).*") ?
            rawBookingDestination.replaceAll(".*\\((.*?)\\).*", "$1").trim().toUpperCase() :
            rawBookingDestination.trim().toUpperCase();

        ScreenShots.takeScreenShot1();
        Log.ReportEvent("INFO", "Booking page Details: Origin: " + bookingOrigin + ", Destination: " + bookingDestination);

        // Compare and validate
        if (resultOrigin.equalsIgnoreCase(bookingOrigin) && resultDestination.equalsIgnoreCase(bookingDestination)) {
            Log.ReportEvent("PASS", "Flights for " + stopType + ": Result Page [" + resultOrigin + " -> " + resultDestination +
                    "] vs Booking Page [" + bookingOrigin + " -> " + bookingDestination + "]");
            ScreenShots.takeScreenShot1();
        } else {
            Log.ReportEvent("FAIL", "Mismatch for " + stopType + ": Result Page [" + resultOrigin + " -> " + resultDestination +
                    "] vs Booking Page [" + bookingOrigin + " -> " + bookingDestination + "]");
            ScreenShots.takeScreenShot1();
            Assert.fail("Origin/Destination mismatch.");
        }


    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception in location validation: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        Assert.fail("Validation failed due to exception");
    }
}*/ 



//
//    public String validateLocationsFromResultToBookingPage(Log Log, ScreenShots ScreenShots, int index) throws InterruptedException {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//
//        try {
//            //FROM: View Flight Button 
//            WebElement fromViewFlightButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("(//*[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]")));
//            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fromViewFlightButton);
//            Thread.sleep(5000);
//            fromViewFlightButton.click();
//            Thread.sleep(2000); 
//
//            // FROM - Determine Stop Type 
//            String fromStopType = "";
//            int fromOriginIndex = 1, fromDestIndex = 1;
//            WebElement fromStopElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("(//*[contains(@class, 'round-trip-from-results')]//*[contains(@class, 'tg-fromstops')])[" + index + "]")));
//            String fromStopCount = fromStopElement.getAttribute("data-tgnumstops").trim();
//
//            switch (fromStopCount) {
//                case "0":
//                    fromStopType = "Nonstop";
//                    break;
//                case "1":
//                    fromStopType = "Onestop";
//                    fromDestIndex = 2;
//                    break;
//                case "2":
//                    fromStopType = "Twostop";
//                    fromDestIndex = 3;
//                    break;
//                default:
//                    Log.ReportEvent("FAIL", "FROM stop type not recognized: " + fromStopCount);
//                    ScreenShots.takeScreenShot1();
//                    Assert.fail("FROM stop type not recognized");
//            }
//
//
//            //  Extract FROM Result Popup Details 
//            WebElement resultFromOrigin = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("(//*[@data-tgfloriginairport])[" + fromOriginIndex + "]")));
//            WebElement resultFromDest = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("(//*[@data-tgfldestinationairport])[" + fromDestIndex + "]")));
//            String resultFromOriginCode = resultFromOrigin.getAttribute("data-tgfloriginairport").split(",")[0].trim().toUpperCase();
//            String resultFromDestCode = resultFromDest.getAttribute("data-tgfldestinationairport").split(",")[0].trim().toUpperCase();
//            
//            Thread.sleep(4000);
//            viewFlightDetailsClosePopup();
//            
//
//            //  TO: View Flight Button 
//            WebElement toViewFlightButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("(//*[@class='round-trip-to-results']//button[text()='View Flight'])[" + index + "]")));
//            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", toViewFlightButton);
//            Thread.sleep(500);
//            toViewFlightButton.click();
//            Thread.sleep(2000); 
//            
//            //  TO: Determine Stop Type 
//            String toStopType = "";
//            int toOriginIndex = 1, toDestIndex = 1;
//            WebElement toStopElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("(//*[contains(@class, 'round-trip-to-results')]//*[contains(@class, 'tg-tostops')])[" + index + "]")));
//            String toStopCount = toStopElement.getAttribute("data-tgnumstops").trim();
//
//            switch (toStopCount) {
//                case "0":
//                    toStopType = "Nonstop";
//                    break;
//                case "1":
//                    toStopType = "Onestop";
//                    toDestIndex = 2;
//                    break;
//                case "2":
//                    toStopType = "Twostop";
//                    toDestIndex = 3;
//                    break;
//                default:
//                    Log.ReportEvent("FAIL", "TO stop type not recognized: " + toStopCount);
//                    ScreenShots.takeScreenShot1();
//                    Assert.fail("TO stop type not recognized");
//            }
//            
//            //  Extract TO Result Popup Details 
//            WebElement resultToOrigin = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("(//*[@data-tgfloriginairport])[" + toOriginIndex + "]")));
//            WebElement resultToDest = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("(//*[@data-tgfldestinationairport])[" + toDestIndex + "]")));
//            String resultToOriginCode = resultToOrigin.getAttribute("data-tgfloriginairport").split(",")[0].trim().toUpperCase();
//            String resultToDestCode = resultToDest.getAttribute("data-tgfldestinationairport").split(",")[0].trim().toUpperCase();
//
//            Log.ReportEvent("INFO", "Result Page FROM " + fromStopType + ": " + resultFromOriginCode + " → " + resultFromDestCode);
//            Log.ReportEvent("INFO", "Result Page TO " + toStopType + ": " + resultToOriginCode + " → " + resultToDestCode);
//            ScreenShots.takeScreenShot1();
//
//            
//            Thread.sleep(4000);
//            viewFlightDetailsClosePopup();
// 
//            //  Continue to Booking 
//            WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Continue']")));
//            continueBtn.click();
//            Thread.sleep(2000);
//            js.executeScript("window.scrollBy(0, 200);");
//
//            // === Extract FROM Booking Page Details ===
//            WebElement bookingFromOriginElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("(//*[@data-tgdepartflorigin])[" + fromOriginIndex + "]")));
//            WebElement bookingFromDestElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("(//*[@data-tgdepartfldestination])[" + fromDestIndex + "]")));
//            String bookingFromOrigin = extractAirportCode(bookingFromOriginElem.getText());
//            String bookingFromDest = extractAirportCode(bookingFromDestElem.getText());
//
//            // === Extract TO Booking Page Details ===
//            WebElement bookingToOriginElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("(//*[@data-tgreturnflorigin])[" + toOriginIndex + "]")));
//            WebElement bookingToDestElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("(//*[@data-tgreturnfldestination])[" + toDestIndex + "]")));
//            String bookingToOrigin = extractAirportCode(bookingToOriginElem.getText());
//            String bookingToDest = extractAirportCode(bookingToDestElem.getText());
//
//            Log.ReportEvent("INFO", "Booking Page FROM: " + bookingFromOrigin + " → " + bookingFromDest);
//            Log.ReportEvent("INFO", "Booking Page TO: " + bookingToOrigin + " → " + bookingToDest);
//            ScreenShots.takeScreenShot1();
//
//            // === Validation ===
//            boolean fromMatch = resultFromOriginCode.equalsIgnoreCase(bookingFromOrigin) &&
//                                resultFromDestCode.equalsIgnoreCase(bookingFromDest);
//            boolean toMatch = resultToOriginCode.equalsIgnoreCase(bookingToOrigin) &&
//                              resultToDestCode.equalsIgnoreCase(bookingToDest);
//
//            if (fromMatch && toMatch) {
//                Log.ReportEvent("PASS", "Flight locations match for both FROM (" + fromStopType + ") and TO (" + toStopType + ")");
//            } else {
//                Log.ReportEvent("FAIL", "Mismatch Detected:\nFROM: " + resultFromOriginCode + " → " + resultFromDestCode + " vs " +
//                                bookingFromOrigin + " → " + bookingFromDest +
//                                "\nTO: " + resultToOriginCode + " → " + resultToDestCode + " vs " +
//                                bookingToOrigin + " → " + bookingToDest);
//                ScreenShots.takeScreenShot1();
//                Assert.fail("Mismatch in FROM/TO location validation.");
//            }
//
//        } catch (Exception e) {
//            Log.ReportEvent("FAIL", "Exception occurred: " + e.getMessage());
//            ScreenShots.takeScreenShot1();
//            Assert.fail("Validation failed due to exception.");
//        }
//
//        return "Validation completed";
//    }
//
//    // This helper method must be outside the main method
//    private String extractAirportCode1(String rawText) {
//        if (rawText == null) return "";
//        if (rawText.matches(".*\\(.*\\).*")) {
//            return rawText.replaceAll(".*\\((.*?)\\).*", "$1").trim().toUpperCase();
//        }
//        return rawText.trim().toUpperCase();
//    }


public String validateLocationsFromResultToBookingPage(Log Log, ScreenShots ScreenShots, int index) throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
    JavascriptExecutor js = (JavascriptExecutor) driver;


        // FROM - Determine Stop Type with attribute and fallback
        String fromStopType = "";
        int fromOriginIndex = 1, fromDestIndex = 1;
        WebElement fromStopElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//*[contains(@class, 'round-trip-from-results')]//*[contains(@class, 'tg-fromstops')])[" + index + "]")));

        // Retry to get 'data-tgnumstops' using JS executor
        String fromStopCountAttr = null;
        int retries = 0;
        while (fromStopCountAttr == null && retries < 10) {
            fromStopCountAttr = (String) js.executeScript("return arguments[0].getAttribute('data-tgnumstops');", fromStopElement);
            if (fromStopCountAttr != null && !fromStopCountAttr.trim().isEmpty()) break;
            Thread.sleep(500);
            retries++;
        }

        // Fallback to visible text if attribute still null
        if (fromStopCountAttr == null || fromStopCountAttr.trim().isEmpty()) {
            String stopText = fromStopElement.getText().trim().toLowerCase();
            if (stopText.contains("non")) fromStopCountAttr = "0";
            else if (stopText.contains("1")) fromStopCountAttr = "1";
            else if (stopText.contains("2")) fromStopCountAttr = "2";
            else {
                Log.ReportEvent("FAIL", "FROM stop count not found by attribute or text.");
                ScreenShots.takeScreenShot1();
                Assert.fail("FROM stop count not found.");
            }
        }

        fromStopCountAttr = fromStopCountAttr.trim();
        switch (fromStopCountAttr) {
            case "0":
                fromStopType = "Nonstop";
                break;
            case "1":
                fromStopType = "Onestop";
                fromDestIndex = 2;
                break;
            case "2":
                fromStopType = "Twostop";
                fromDestIndex = 3;
                break;
            default:
                Log.ReportEvent("FAIL", "FROM stop type not recognized: " + fromStopCountAttr);
                ScreenShots.takeScreenShot1();
                Assert.fail("FROM stop type not recognized");
        }

        try {
            // FROM: View Flight Button
            WebElement fromViewFlightButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//*[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]")));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fromViewFlightButton);
            Thread.sleep(1000);

            // Try JS click to avoid click intercepted
            try {
                fromViewFlightButton.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", fromViewFlightButton);
            }
            Thread.sleep(3000);

        // Extract FROM Result Popup Details
        WebElement resultFromOrigin = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//*[@data-tgfloriginairport])[" + fromOriginIndex + "]")));
        WebElement resultFromDest = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//*[@data-tgfldestinationairport])[" + fromDestIndex + "]")));

        String resultFromOriginCode = safeGetAttributeTrimmed(resultFromOrigin, "data-tgfloriginairport", "FROM Origin", Log, ScreenShots).split(",")[0].toUpperCase();
        String resultFromDestCode = safeGetAttributeTrimmed(resultFromDest, "data-tgfldestinationairport", "FROM Destination", Log, ScreenShots).split(",")[0].toUpperCase();

        Thread.sleep(2000);
        viewFlightDetailsClosePopup();


        // TO - Determine Stop Type with attribute and fallback
        String toStopType = "";
        int toOriginIndex = 1, toDestIndex = 1;
        WebElement toStopElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//*[contains(@class, 'round-trip-to-results')]//*[contains(@class, 'tg-tostops')])[" + index + "]")));

        // Retry to get 'data-tgnumstops' using JS executor
        String toStopCountAttr = null;
        retries = 0;
        while (toStopCountAttr == null && retries < 10) {
            toStopCountAttr = (String) js.executeScript("return arguments[0].getAttribute('data-tgnumstops');", toStopElement);
            if (toStopCountAttr != null && !toStopCountAttr.trim().isEmpty()) break;
            Thread.sleep(500);
            retries++;
        }

        // Fallback to visible text if attribute still null
        if (toStopCountAttr == null || toStopCountAttr.trim().isEmpty()) {
            String stopText = toStopElement.getText().trim().toLowerCase();
            if (stopText.contains("non")) toStopCountAttr = "0";
            else if (stopText.contains("1")) toStopCountAttr = "1";
            else if (stopText.contains("2")) toStopCountAttr = "2";
            else {
                Log.ReportEvent("FAIL", "TO stop count not found by attribute or text.");
                ScreenShots.takeScreenShot1();
                Assert.fail("TO stop count not found.");
            }
        }

        toStopCountAttr = toStopCountAttr.trim();
        switch (toStopCountAttr) {
            case "0":
                toStopType = "Nonstop";
                break;
            case "1":
                toStopType = "Onestop";
                toDestIndex = 2;
                break;
            case "2":
                toStopType = "Twostop";
                toDestIndex = 3;
                break;
            default:
                Log.ReportEvent("FAIL", "TO stop type not recognized: " + toStopCountAttr);
                ScreenShots.takeScreenShot1();
                Assert.fail("TO stop type not recognized");
        }

        // TO: View Flight Button
        WebElement toViewFlightButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//*[@class='round-trip-to-results']//button[text()='View Flight'])[" + index + "]")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", toViewFlightButton);
        Thread.sleep(500);

        try {
            toViewFlightButton.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", toViewFlightButton);
        }
        Thread.sleep(3000);
        
        // Extract TO Result Popup Details
        WebElement resultToOrigin = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//*[@data-tgfloriginairport])[" + toOriginIndex + "]")));
        WebElement resultToDest = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//*[@data-tgfldestinationairport])[" + toDestIndex + "]")));

        String resultToOriginCode = safeGetAttributeTrimmed(resultToOrigin, "data-tgfloriginairport", "TO Origin", Log, ScreenShots).split(",")[0].toUpperCase();
        String resultToDestCode = safeGetAttributeTrimmed(resultToDest, "data-tgfldestinationairport", "TO Destination", Log, ScreenShots).split(",")[0].toUpperCase();

        Log.ReportEvent("INFO", "Result Page FROM " + fromStopType + ": " + resultFromOriginCode + " → " + resultFromDestCode);
        Log.ReportEvent("INFO", "Result Page TO " + toStopType + ": " + resultToOriginCode + " → " + resultToDestCode);
        ScreenShots.takeScreenShot1();

        Thread.sleep(2000);
        viewFlightDetailsClosePopup();

        // Continue to Booking
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Continue']")));
        js.executeScript("arguments[0].scrollIntoView(true);", continueBtn);
        Thread.sleep(500);

        try {
            continueBtn.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", continueBtn);
        }
        Thread.sleep(3000);
        js.executeScript("window.scrollBy(0, 200);");

        // Extract FROM Booking Page Details
        WebElement bookingFromOriginElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//*[@data-tgdepartflorigin])[" + fromOriginIndex + "]")));
        WebElement bookingFromDestElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//*[@data-tgdepartfldestination])[" + fromDestIndex + "]")));
        String bookingFromOrigin = extractAirportCode1(bookingFromOriginElem.getText());
        String bookingFromDest = extractAirportCode1(bookingFromDestElem.getText());

        // Extract TO Booking Page Details
        WebElement bookingToOriginElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//*[@data-tgreturnflorigin])[" + toOriginIndex + "]")));
        WebElement bookingToDestElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("(//*[@data-tgreturnfldestination])[" + toDestIndex + "]")));
        String bookingToOrigin = extractAirportCode1(bookingToOriginElem.getText());
        String bookingToDest = extractAirportCode1(bookingToDestElem.getText());

        Log.ReportEvent("INFO", "Booking Page FROM: " + bookingFromOrigin + " → " + bookingFromDest);
        Log.ReportEvent("INFO", "Booking Page TO: " + bookingToOrigin + " → " + bookingToDest);
        ScreenShots.takeScreenShot1();

        // Validation
        boolean fromMatch = resultFromOriginCode.equalsIgnoreCase(bookingFromOrigin) &&
                            resultFromDestCode.equalsIgnoreCase(bookingFromDest);
        boolean toMatch = resultToOriginCode.equalsIgnoreCase(bookingToOrigin) &&
                          resultToDestCode.equalsIgnoreCase(bookingToDest);

        if (fromMatch && toMatch) {
            Log.ReportEvent("PASS", "Flight locations match for both FROM (" + fromStopType + ") and TO (" + toStopType + ")");
        } else {
            Log.ReportEvent("FAIL", "Mismatch Detected:\nFROM: " + resultFromOriginCode + " → " + resultFromDestCode + " vs " +
                            bookingFromOrigin + " → " + bookingFromDest +
                            "\nTO: " + resultToOriginCode + " → " + resultToDestCode + " vs " +
                            bookingToOrigin + " → " + bookingToDest);
            ScreenShots.takeScreenShot1();
            Assert.fail("Mismatch in FROM/TO location validation.");
        }

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception occurred: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        Assert.fail("Validation failed due to exception.");
    }

    return "Validation completed";
}

// Helper method to safely get attribute, trim and fail if null
private String safeGetAttributeTrimmed(WebElement element, String attributeName, String label, Log Log, ScreenShots ScreenShots) {
    String value = element.getAttribute(attributeName);
    if (value == null) {
        Log.ReportEvent("FAIL", label + " attribute '" + attributeName + "' is null.");
        ScreenShots.takeScreenShot1();
        Assert.fail(label + " attribute '" + attributeName + "' is null.");
    }
    return value.trim();
}

// Extract airport code from text like "City Name (ABC)"
private String extractAirportCode1(String rawText) {
    if (rawText == null) return "";
    if (rawText.matches(".*\\(.*\\).*")) {
        return rawText.replaceAll(".*\\((.*?)\\).*", "$1").trim().toUpperCase();
    }
    return rawText.trim().toUpperCase();
}

// Close flight details popup (you need to implement this method as per your page)
private void viewFlightDetailsClosePopup1() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    try {
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Close']")));
        closeButton.click();
        Thread.sleep(2000);
    } catch (TimeoutException e) {
        // Popup might not appear, safe to ignore
    }
}



//-----------------------------------------------------------------------------------------------------------------

//Method to validate price from result page to booking page

	public void validatePricesFromResultToBooking(Log Log, ScreenShots ScreenShots, int Index) {
     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));

	    try {
	    	Thread.sleep(4000);
	        // Step 1: Get origin and destination cards by user index
	        WebElement originDiv = driver.findElement(By.xpath("(//*[contains(@class,'round-trip-card-from')])[" + Index + "]"));
	        WebElement destDiv = driver.findElement(By.xpath("(//*[contains(@class,'round-trip-card-to')])[" + Index + "]"));

	        // Step 2: get prices from result page
	        WebElement resultPageOriginPriceElem = driver.findElement(By.xpath("//*[contains(@class,'tg-fromprice')]"));
	        WebElement resultPageDestPriceElem = driver.findElement(By.xpath("//*[contains(@class,'tg-toprice')]"));

	        int resultPageOriginPrice = Integer.parseInt(resultPageOriginPriceElem.getText().replaceAll("[^\\d]", ""));
	        int resultPageDestPrice = Integer.parseInt(resultPageDestPriceElem.getText().replaceAll("[^\\d]", ""));

	        // Step 3: get prices from bottom bar
	        WebElement bottomBarOriginPriceElem = driver.findElement(By.xpath("//*[contains(@class,'tg-bar-owprice')]"));
	        WebElement bottomBarDestPriceElem = driver.findElement(By.xpath("//*[contains(@class,'tg-bar-rtprice')]"));
	        WebElement bottomBarTotalPriceElem = driver.findElement(By.xpath("//*[contains(@class,'tg-bar-totalprice')]"));

	        int bottomBarOriginPrice = Integer.parseInt(bottomBarOriginPriceElem.getText().replaceAll("[^\\d]", ""));
	        int bottomBarDestPrice = Integer.parseInt(bottomBarDestPriceElem.getText().replaceAll("[^\\d]", ""));
	        int bottomBarTotalPrice = Integer.parseInt(bottomBarTotalPriceElem.getText().replaceAll("[^\\d]", ""));

	        // Validation 1: Origin price
	        if (resultPageOriginPrice != bottomBarOriginPrice) {
	            Log.ReportEvent("FAIL", "Origin price mismatch. Result Page: " + resultPageOriginPrice + ", Bottom Bar: " + bottomBarOriginPrice);
	            ScreenShots.takeScreenShot1();
	            return;
	        } else {
	            Log.ReportEvent("PASS", "Origin price matched: " + resultPageOriginPrice);
	        }

	        // Validation 2: Destination price
	        if (resultPageDestPrice != bottomBarDestPrice) {
	            Log.ReportEvent("FAIL", "Destination price mismatch. Result Page: " + resultPageDestPrice + ", Bottom Bar: " + bottomBarDestPrice);
	            ScreenShots.takeScreenShot1();
	            return;
	        } else {
	            Log.ReportEvent("PASS", "Destination price matched: " + resultPageDestPrice);
	        }

	        // Validation 3: Total = Origin + Destination
	        int calculatedTotal = bottomBarOriginPrice + bottomBarDestPrice;
	        if (calculatedTotal != bottomBarTotalPrice) {
	            Log.ReportEvent("FAIL", "Total price mismatch. Calculated: " + calculatedTotal + ", Bottom Bar Total: " + bottomBarTotalPrice);
	            ScreenShots.takeScreenShot1();
	            return;
	        } else {
	            Log.ReportEvent("PASS", "Bottom bar total price matched: " + calculatedTotal);
	        }

	        // Step 4: Click Continue
	        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Continue']")));
	        continueBtn.click();

	        // Step 5: Booking page total
	        WebElement bookingPageGrandTotalElem = driver.findElement(By.xpath("//*[contains(@class,'tg-fbgrandtotal')]"));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bookingPageGrandTotalElem);
	        int bookingPageGrandTotal = Integer.parseInt(bookingPageGrandTotalElem.getText().replaceAll("[^\\d]", ""));

	        // Validation 4: Booking Page Total == Bottom Bar Total
	        if (bookingPageGrandTotal != bottomBarTotalPrice) {
	            Log.ReportEvent("FAIL", "Grand total mismatch. Booking Page: " + bookingPageGrandTotal + ", Bottom Bar Total: " + bottomBarTotalPrice);
	            ScreenShots.takeScreenShot1();
	            return;
	        } else {
	            Log.ReportEvent("PASS", "Booking page grand total matched: " + bookingPageGrandTotal);

	        }

	        // Final success
	        Log.ReportEvent("PASS", "All price validations passed successfully.");

	    } catch (Exception e) {
	        Log.ReportEvent("FAIL", "Exception in price validation: " + e.getMessage());
	        ScreenShots.takeScreenShot1();
	    }
	}



//--------------------------------------------------------------------------------------------------------------

//Method to validate Radio button is changing from Round trip to One way

public void validateRoundtripRadioButton() {
	WebElement roundtripradiobutton = driver.findElement(By.xpath("//input[@value='roundtrip']"));
	WebElement onewayradiobutton = driver.findElement(By.xpath("//input[@value='oneway']"));
	
	 if (roundtripradiobutton.isSelected()) {
	        System.out.println("Round trip radio button is selected.");
	    } else {
	        System.out.println("Round trip radio button is NOT selected.");
	    }	
}

public void validateOnewayRadioButton() {
	WebElement onewayradiobutton = driver.findElement(By.xpath("//input[@value='oneway']"));
	
	 if (onewayradiobutton.isSelected()) {
	        System.out.println("Round trip radio button is selected.");
	    } else {
	        System.out.println("Round trip radio button is NOT selected.");
	    }	
}



public void validateRadioButtonIsChangedToOneWay(Log Log, ScreenShots ScreenShots) {
    WebElement roundtripRadioButton = driver.findElement(By.xpath("//input[@value='roundtrip']"));
    WebElement onewayRadioButton = driver.findElement(By.xpath("//input[@value='oneway']"));

    // Check if selection changed
    if (onewayRadioButton.isSelected()) {
        System.out.println("Radio button changed to One Way successfully.");
        Log.ReportEvent("PASS", "Radio button changed to One Way successfully");
    } else {
        System.out.println("Radio button did NOT change to One Way.");
        Log.ReportEvent("FAIL", "Radio button Did Not changed to One Way");
        ScreenShots.takeScreenShot1();


    }
}
//------------------------------------------------------------------------------------------------------

//getting from div details roundtrip
public Object[] selectFlightInRoundTripFromLocation(int index) {
    WebElement flightButton = null;
    try {
        // Locate the flight selection button using XPath
        flightButton = driver.findElement(By.xpath(
                "(//div[@data-tgflightcard='from']//button[text()='View Flight'])[" + index + "]"
                ));

        // Scroll the button into view using JavaScriptExecutor
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", flightButton);

        // Wait for the element to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(flightButton));

        WebElement stops = driver.findElement(By.xpath("(//span[@data-tgnumstops])[" + index + "]"));

        // Retrieve the value of the 'data-tgnumstops' attribute
        String stop = stops.getText();

        // Print the attribute value
        System.out.println("stop Value: " + stop);
        //--price
        WebElement priceElement = driver.findElement(By.xpath("(//h3[@data-tgcheapestprice])[" + index + "]"));

        // Retrieve the value of the data-tgcheapestprice attribute
        String priceValue = priceElement.getAttribute("data-tgcheapestprice");
        double priceFrom=Double.parseDouble(priceValue);
        System.out.println(priceFrom);

        //--
        String departTime=driver.findElement(By.xpath("(//h3[@data-tgdeptime])[" + index + "]")).getText();
        System.out.println("departTime:"+departTime);
        String arrivalTime=driver.findElement(By.xpath("(//h3[@data-tgarrtime])[" + index + "]")).getText();
        System.out.println("arrivalTime :"+arrivalTime);
        String duration=driver.findElement(By.xpath("(//h5[@data-tgtotalduration])[" + index + "]")).getText();
        System.out.println(duration);
        // Click the button using JavaScriptExecutor
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", flightButton);
        return new Object[] {stop,priceFrom,departTime,arrivalTime,duration};
    } catch (Exception e) {
        System.err.println("An unexpected error occurred.");
        e.printStackTrace();
    }
    return null;
}


public String[] validateFlightDetailsFromLocation(String fromLocation, String toLocation, String fromdate, String stops,String departTimeing,String arrivalTimeing, Log log, ScreenShots screenshots) throws ParseException, InterruptedException {

    System.out.println(fromLocation);
    System.out.println(toLocation);

    String stop = stops;
    System.out.println(stop);

    String originalDate = fromdate;
    String cleanedDate = originalDate.replaceAll("(\\d+)(st|nd|rd|th)", "$1");

    // Define the input and output date formats
    SimpleDateFormat inputFormat = new SimpleDateFormat("d-MMM-yyyy");
    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Parse the cleaned date string into a Date object
    Date date = inputFormat.parse(cleanedDate);

    // Format the Date object into the desired output format
    String formattedFromDate = outputFormat.format(date);
    System.out.println(formattedFromDate);
    ///////////////--------------
    // Date from flight card
    String fromdates = driver.findElement(By.xpath("(//small[@data-tgfldepdate])[1]")).getAttribute("data-tgfldepdate");
    System.out.println(fromdates);

    ////------
    String flightName=driver.findElement(By.xpath("(//p[@data-tgflcarriername])[1]")).getAttribute("data-tgflcarriername");
    System.out.println(flightName);
    
    String departTime=driver.findElement(By.xpath("(//h6[@data-tgflorigindeptime])[1]")).getText();
    //getting last arrival time dynamical from view flight details
    List<WebElement> arrivalTimeElements = driver.findElements(By.xpath("//h6[@data-tgfloriginarrtime]"));
    WebElement lastArrivalTimeElement = arrivalTimeElements.get(arrivalTimeElements.size() - 1);
    String arrivalTime=lastArrivalTimeElement.getText();
    System.out.println("arrivalTime:"+arrivalTime);

    ///----
    String initialOriginAirportData = driver.findElement(By.xpath("(//h6[@data-tgfloriginairport])[1]"))
            .getAttribute("data-tgfloriginairport");
    String finalDestinationAirportData=" ";
    String firstDestinationAirportData;
    String firstOriginAirportData;
    String secondDestinationAirportData;
    String secondOriginAirportData;

    // Report actual vs expected for From Location
    if (!initialOriginAirportData.equals(fromLocation)) {
        log.ReportEvent("FAIL", "Expected From Location: " + fromLocation + ", but found: " + initialOriginAirportData);
        System.out.println("initialOriginAirportData :"+initialOriginAirportData);
        System.out.println("fromLocation :"+fromLocation);
    } else {
        log.ReportEvent("INFO", "Expected From Location: " + fromLocation + ", Actual: " + initialOriginAirportData);
        System.out.println("initialOriginAirportData :"+initialOriginAirportData);
        System.out.println("fromLocation :"+fromLocation);
    }

    //        // Report actual vs expected for To Location
    //        if (!finalDestinationAirportData.equals(toLocation)) {
    //            log.ReportEvent("FAIL", "Expected To Location: " + toLocation + ", but found: " + finalDestinationAirportData);
    //        } else {
    //            log.ReportEvent("INFO", "Expected To Location: " + toLocation + ", Actual: " + finalDestinationAirportData);
    //        }

    // Report actual vs expected for Date
    if (!fromdates.equals(formattedFromDate)) {
        log.ReportEvent("FAIL", "Expected Date: " + fromdate + ", but found: " + formattedFromDate);
        System.out.println("Expected:"+fromdate);
        System.out.println("but found:"+formattedFromDate);
    } else {
        log.ReportEvent("INFO", "Expected Date: " + fromdate + ", Actual: " + formattedFromDate);
        System.out.println("Expected:"+fromdate);
        System.out.println("but found:"+formattedFromDate);
    }
    // Report actual vs expected for DepartTime
    if (!departTime.equals(departTimeing)) {
        log.ReportEvent("FAIL", "Expected depart Time In flight Result: " + departTime + ", but found: " + departTimeing);
        System.out.println("departTime:"+departTime);
        System.out.println(" but found:"+departTimeing);
    } else {
        log.ReportEvent("INFO", "Expected depart Time In flight Result: " + departTime + ", Actual depart Time In flight Result In View Flight : " + departTimeing);
        System.out.println("departTime:"+departTime);
        System.out.println(" but found:"+departTimeing);
    }

    // Report actual vs expected for ArrivalTime
    if (!arrivalTime.equals(arrivalTimeing)) {
        log.ReportEvent("FAIL", "Expected arrival Time In flight Result: " + arrivalTime + ", but found: " + arrivalTimeing);
        System.out.println("arrivalTime :"+arrivalTime);
        System.out.println("arrivalTimeing :"+arrivalTimeing);
    } else {
        log.ReportEvent("INFO", "Expected arrival Time In flight Result: " + arrivalTime + ", Actual depart Time In flight Result In View Flight : " + arrivalTimeing);
        System.out.println("arrivalTime :"+arrivalTime);
        System.out.println("arrivalTimeing :"+arrivalTimeing);
    }


    if (stop.equals("Nonstop")) {
        Thread.sleep(2000);
        finalDestinationAirportData = driver.findElement(By.xpath("(//h6[@data-tgfldestinationairport])[1]"))
                .getAttribute("data-tgfldestinationairport");

        if (initialOriginAirportData.equals(fromLocation) && finalDestinationAirportData.equals(toLocation) && fromdates.equals(formattedFromDate)) {
            System.out.println(initialOriginAirportData + ""+fromLocation);
            System.out.println(finalDestinationAirportData+ " "+toLocation);
            System.out.println(fromdates+ " "+formattedFromDate);
            // Report actual vs expected for To Location
            if (!finalDestinationAirportData.equals(toLocation)) {
                log.ReportEvent("FAIL", "Expected To Location: " + toLocation + ", but found: " + finalDestinationAirportData);
            } else {
                log.ReportEvent("INFO", "Expected To Location: " + toLocation + ", Actual: " + finalDestinationAirportData);
            }

            log.ReportEvent("PASS", "Matching details for nonstop flight");
            return new String[]{ stop,flightName,finalDestinationAirportData};
        } else {
            log.ReportEvent("FAIL", "Mismatch in flight details");
            return null;
        }
    } else if (stop.equals("1stops")) {
        firstDestinationAirportData = driver.findElement(By.xpath("(//h6[@data-tgfldestinationairport])[1]"))
                .getAttribute("data-tgfldestinationairport");
        firstOriginAirportData = driver.findElement(By.xpath("(//h6[@data-tgfloriginairport])[2]"))
                .getAttribute("data-tgfloriginairport");
        Thread.sleep(2000);
        finalDestinationAirportData = driver.findElement(By.xpath("(//h6[@data-tgfldestinationairport])[2]"))
                .getAttribute("data-tgfldestinationairport");

        System.out.println(initialOriginAirportData);
        System.out.println(fromLocation);
        System.out.println(finalDestinationAirportData);
        System.out.println(toLocation);
        System.out.println(fromdates);
        System.out.println(formattedFromDate);

        if (initialOriginAirportData.equals(fromLocation) && finalDestinationAirportData.equals(toLocation) && fromdates.equals(formattedFromDate)) {
            // Report actual vs expected for To Location
            if (!finalDestinationAirportData.equals(toLocation)) {
                log.ReportEvent("FAIL", "Expected To Location: " + toLocation + ", but found: " + finalDestinationAirportData);
            } else {
                log.ReportEvent("INFO", "Expected To Location: " + toLocation + ", Actual: " + finalDestinationAirportData);
            }

            log.ReportEvent("PASS", "Matching details for 1 stop flight");
            return new String[]{ stop,flightName,firstDestinationAirportData, firstOriginAirportData, finalDestinationAirportData};
        } else {
            log.ReportEvent("FAIL", "Mismatch in flight details");
            return null;
        }
    } else if (stop.equals("2stops")) {
        firstDestinationAirportData = driver.findElement(By.xpath("(//h6[@data-tgfldestinationairport])[1]"))
                .getAttribute("data-tgfldestinationairport");
        firstOriginAirportData = driver.findElement(By.xpath("(//h6[@data-tgfloriginairport])[2]"))
                .getAttribute("data-tgfloriginairport");
        secondDestinationAirportData = driver.findElement(By.xpath("(//h6[@data-tgfldestinationairport])[2]"))
                .getAttribute("data-tgfldestinationairport");
        secondOriginAirportData = driver.findElement(By.xpath("(//h6[@data-tgfloriginairport])[3]"))
                .getAttribute("data-tgfloriginairport");
        Thread.sleep(2000);
        finalDestinationAirportData = driver.findElement(By.xpath("(//h6[@data-tgfldestinationairport])[3]"))
                .getAttribute("data-tgfldestinationairport");
        //debug purpose
        System.out.println(initialOriginAirportData + ""+fromLocation);
        System.out.println(finalDestinationAirportData+ " "+toLocation);
        System.out.println(fromdates+ " "+formattedFromDate); 

        if (initialOriginAirportData.equals(fromLocation) && finalDestinationAirportData.equals(toLocation) && fromdates.equals(formattedFromDate)) {
            // Report actual vs expected for To Location
            if (!finalDestinationAirportData.equals(toLocation)) {
                log.ReportEvent("FAIL", "Expected To Location: " + toLocation + ", but found: " + finalDestinationAirportData);
            } else {
                log.ReportEvent("INFO", "Expected To Location: " + toLocation + ", Actual: " + finalDestinationAirportData);
            }

            log.ReportEvent("PASS", "Matching details for 2 stops flight");
            return new String[]{ stop,flightName,firstDestinationAirportData, firstOriginAirportData, secondDestinationAirportData, secondOriginAirportData, finalDestinationAirportData};
        } else {
            log.ReportEvent("FAIL", "Mismatch in flight details");
            return null;
        }
    }
    return null;
}
//fetching details from return div 
public Object[] selectFlightInRoundTripToLocation(int index) {
    try {
        // Locate the flight selection button using XPath
        WebElement flightButton = driver.findElement(By.xpath(
                "(//div[@data-tgflightcard='to']//button[text()='View Flight'])[" + index + "]"
                ));

        // Scroll the button into view using JavaScriptExecutor
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", flightButton);

        // Wait for the element to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(flightButton));

        Thread.sleep(3000);
        // Retrieve the number of stops
        WebElement stops = driver.findElement(By.xpath(
                "(//span[@data-tgnumstops])[" + index + "]"
                ));
        String stop = stops.getText();
        System.out.println("Number of stops: " + stop);


        WebElement priceElement = driver.findElement(By.xpath("(//div[@class='round-trip-to-results']//h3[@data-tgcheapestprice])[" + index + "]"));

        // Retrieve the value of the data-tgcheapestprice attribute
        String priceValue = priceElement.getAttribute("data-tgcheapestprice");
        System.out.println(priceValue);
        double priceFrom=Double.parseDouble(priceValue);
        System.out.println(priceFrom);

        //--
        String departTime=driver.findElement(By.xpath("(//div[@class='round-trip-to-results']//h3[@data-tgdeptime])[" + index + "]")).getText();
        System.out.println("departTime:"+departTime);
        String arrivalTime=driver.findElement(By.xpath("(//div[@class='round-trip-to-results']//h3[@data-tgarrtime])[" + index + "]")).getText();
        System.out.println("arrivalTime:"+arrivalTime);
        String duration=driver.findElement(By.xpath("(//div[@class='round-trip-to-results']//h5[@data-tgtotalduration])[" + index + "]")).getText();
        System.out.println(duration);

        // Click the button using JavaScriptExecutor
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", flightButton);
        return new Object[] {stop,priceFrom,departTime,arrivalTime,duration};


    } catch (ElementClickInterceptedException e) {
        System.err.println("Error: The element is not clickable.");
        e.printStackTrace();
    } catch (Exception e) {
        System.err.println("An unexpected error occurred.");
        e.printStackTrace();
    }
    return null;
}

//===============================================================================================

//validate return flight loc and all details
public String[] validateFlightDetailsReturnLocation(String fromLocation, String toLocation, String fromdate, String stops,String departTimeing,String arrivalTimeing, Log log, ScreenShots screenshots) throws ParseException{

    System.out.println(fromLocation);
    System.out.println(toLocation);

    String stop = stops;
    System.out.println(stop);

    String originalDate = fromdate;
    String cleanedDate = originalDate.replaceAll("(\\d+)(st|nd|rd|th)", "$1");

    // Define the input and output date formats
    SimpleDateFormat inputFormat = new SimpleDateFormat("d-MMM-yyyy");
    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Parse the cleaned date string into a Date object
    Date date = inputFormat.parse(cleanedDate);

    // Format the Date object into the desired output format
    String formattedFromDate = outputFormat.format(date);
    System.out.println(formattedFromDate);
    ///////////////--------------
    // Date from flight card
    String fromdates = driver.findElement(By.xpath("(//small[@data-tgfldepdate])[1]")).getAttribute("data-tgfldepdate");
    System.out.println(fromdates);

    ////------
    String flightName=driver.findElement(By.xpath("(//p[@data-tgflcarriername])[1]")).getAttribute("data-tgflcarriername");
    String departTime=driver.findElement(By.xpath("(//h6[@data-tgflorigindeptime])[1]")).getText();
    //getting last arrival time dynamical from view flight details
    List<WebElement> arrivalTimeElements = driver.findElements(By.xpath("//h6[@data-tgfloriginarrtime]"));
    WebElement lastArrivalTimeElement = arrivalTimeElements.get(arrivalTimeElements.size() - 1);
    String arrivalTime=lastArrivalTimeElement.getText();
    System.out.println("arrivalTime:"+arrivalTime);

    ///----
    String initialOriginAirportData = driver.findElement(By.xpath("(//h6[@data-tgfloriginairport])[1]"))
            .getAttribute("data-tgfloriginairport");
    System.out.println("initialOriginAirportData :"+initialOriginAirportData);
    String finalDestinationAirportData=" ";
    String firstDestinationAirportData;
    String firstOriginAirportData;
    String secondDestinationAirportData;
    String secondOriginAirportData;

    // Report actual vs expected for From Location
    if (!initialOriginAirportData.equals(toLocation)) {
        log.ReportEvent("FAIL", "Expected From Location: " + toLocation + ", but found: " + initialOriginAirportData);
        System.out.println("initialOriginAirportData :"+initialOriginAirportData);
        System.out.println("fromLocation :"+toLocation);
    } else {
        log.ReportEvent("INFO", "Expected From Location: " + toLocation + ", Actual: " + initialOriginAirportData);
        System.out.println("initialOriginAirportData :"+initialOriginAirportData);
        System.out.println("fromLocation :"+toLocation);
    }

    //        // Report actual vs expected for To Location
    //        if (!finalDestinationAirportData.equals(toLocation)) {
    //            log.ReportEvent("FAIL", "Expected To Location: " + toLocation + ", but found: " + finalDestinationAirportData);
    //        } else {
    //            log.ReportEvent("INFO", "Expected To Location: " + toLocation + ", Actual: " + finalDestinationAirportData);
    //        }

    // Report actual vs expected for Date
    if (!fromdates.equals(formattedFromDate)) {
        log.ReportEvent("FAIL", "Expected Date: " + fromdate + ", but found: " + formattedFromDate);
        System.out.println(fromdates);
        System.out.println(formattedFromDate);
    } else {
        log.ReportEvent("INFO", "Expected Date: " + fromdate + ", Actual: " + formattedFromDate);
        System.out.println(fromdates);
        System.out.println(formattedFromDate);
    }
    // Report actual vs expected for DepartTime
    if (!departTime.equals(departTimeing)) {
        log.ReportEvent("FAIL", "Expected depart Time In flight Result: " + departTime + ", but found: " + departTimeing);
        System.out.println(departTime);
        System.out.println(departTimeing);
    } else {
        log.ReportEvent("INFO", "Expected depart Time In flight Result: " + departTime + ", Actual depart Time In flight Result In View Flight : " + departTimeing);
        System.out.println(departTime);
        System.out.println(departTimeing);
    }

    // Report actual vs expected for ArrivalTime
    if (!arrivalTime.equals(arrivalTimeing)) {
        log.ReportEvent("FAIL", "Expected arrival Time In flight Result: " + arrivalTime + ", but found: " + arrivalTimeing);
        System.out.println(arrivalTime);
        System.out.println(arrivalTimeing);
    } else {
        log.ReportEvent("INFO", "Expected arrival Time In flight Result: " + arrivalTime + ", Actual depart Time In flight Result In View Flight : " + arrivalTimeing);
        System.out.println(arrivalTime);
        System.out.println(arrivalTimeing);
    }


    if (stop.equals("Nonstop")) {
        finalDestinationAirportData = driver.findElement(By.xpath("(//h6[@data-tgfldestinationairport])[1]"))
                .getAttribute("data-tgfldestinationairport");

        if (initialOriginAirportData.equals(toLocation) && finalDestinationAirportData.equals(fromLocation) && fromdates.equals(formattedFromDate)) {
            System.out.println(initialOriginAirportData+ ""+toLocation);
            System.out.println(finalDestinationAirportData+""+fromLocation);
            System.out.println(fromdates+""+formattedFromDate);
            // Report actual vs expected for To Location
            if (!finalDestinationAirportData.equals(fromLocation)) {
                log.ReportEvent("FAIL", "Expected To Location: " + fromLocation + ", but found: " + finalDestinationAirportData);
            } else {
                log.ReportEvent("INFO", "Expected To Location: " + fromLocation + ", Actual: " + finalDestinationAirportData);
            }

            log.ReportEvent("PASS", "Matching details for nonstop flight");
            return new String[]{finalDestinationAirportData, stop};
        } else {
            log.ReportEvent("FAIL", "Mismatch in flight details");
            return null;
        }
    } else if (stop.equals("1stops")) {
        firstDestinationAirportData = driver.findElement(By.xpath("(//h6[@data-tgfldestinationairport])[1]"))
                .getAttribute("data-tgfldestinationairport");
        firstOriginAirportData = driver.findElement(By.xpath("(//h6[@data-tgfloriginairport])[2]"))
                .getAttribute("data-tgfloriginairport");
        finalDestinationAirportData = driver.findElement(By.xpath("(//h6[@data-tgfldestinationairport])[2]"))
                .getAttribute("data-tgfldestinationairport");

        System.out.println(initialOriginAirportData+ ""+toLocation);
        System.out.println(finalDestinationAirportData+""+fromLocation);
        System.out.println(fromdates+""+formattedFromDate);

        if (initialOriginAirportData.equals(toLocation) && finalDestinationAirportData.equals(fromLocation) && fromdates.equals(formattedFromDate)) {
            // Report actual vs expected for To Location
            if (!finalDestinationAirportData.equals(fromLocation)) {
                log.ReportEvent("FAIL", "Expected To Location: " + fromLocation + ", but found: " + finalDestinationAirportData);
            } else {
                log.ReportEvent("INFO", "Expected To Location: " + fromLocation + ", Actual: " + finalDestinationAirportData);
            }

            log.ReportEvent("PASS", "Matching details for 1 stop flight");
            return new String[]{firstDestinationAirportData, firstOriginAirportData, finalDestinationAirportData, stop};
        } else {
            log.ReportEvent("FAIL", "Mismatch in flight details");
            return null;
        }
    } else if (stop.equals("2stops")) {
        firstDestinationAirportData = driver.findElement(By.xpath("(//h6[@data-tgfldestinationairport])[1]"))
                .getAttribute("data-tgfldestinationairport");
        firstOriginAirportData = driver.findElement(By.xpath("(//h6[@data-tgfloriginairport])[2]"))
                .getAttribute("data-tgfloriginairport");
        secondDestinationAirportData = driver.findElement(By.xpath("(//h6[@data-tgfldestinationairport])[2]"))
                .getAttribute("data-tgfldestinationairport");
        secondOriginAirportData = driver.findElement(By.xpath("(//h6[@data-tgfloriginairport])[3]"))
                .getAttribute("data-tgfloriginairport");
        finalDestinationAirportData = driver.findElement(By.xpath("(//h6[@data-tgfldestinationairport])[3]"))
                .getAttribute("data-tgfldestinationairport");
        System.out.println(initialOriginAirportData+ ""+toLocation);
        System.out.println(finalDestinationAirportData+""+fromLocation);
        System.out.println(fromdates+""+formattedFromDate);

        if (initialOriginAirportData.equals(toLocation) && finalDestinationAirportData.equals(fromLocation) && fromdates.equals(formattedFromDate)) {
            // Report actual vs expected for To Location
            if (!finalDestinationAirportData.equals(toLocation)) {
                log.ReportEvent("FAIL", "Expected To Location: " + fromLocation + ", but found: " + finalDestinationAirportData);
            } else {
                log.ReportEvent("INFO", "Expected To Location: " + fromLocation + ", Actual: " + finalDestinationAirportData);
            }

            log.ReportEvent("PASS", "Matching details for 2 stops flight");
            return new String[]{firstDestinationAirportData, firstOriginAirportData, secondDestinationAirportData, secondOriginAirportData, finalDestinationAirportData, stop};
        } else {
            log.ReportEvent("FAIL", "Mismatch in flight details");
            return null;
        }
    }
    return null;
}

//================================================

public void viewFlightDetailsClosePopup()
{
    driver.findElement(By.xpath("//button[text()='Close']")).click();
}
//============================================================================

public void clickFromViewFlights(int index) throws InterruptedException {
	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
	  JavascriptExecutor js = (JavascriptExecutor) driver;

	 //  Click 'View Flight' button to open details
    String xpath = "(//div[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]";
    WebElement viewflightbutton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewflightbutton);
    Thread.sleep(500);
    try {
        viewflightbutton.click(); 
    } catch (ElementClickInterceptedException e) {
        js.executeScript("arguments[0].click();", viewflightbutton);

    }
}

public void clickToViewFlights(int index) throws InterruptedException {
	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
	  JavascriptExecutor js = (JavascriptExecutor) driver;

	 //  Click 'View Flight' button to open details
   String xpath = "(//div[@class='round-trip-to-results']//button[text()='View Flight'])[" + index + "]";
   WebElement viewflightbutton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
   js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewflightbutton);
   Thread.sleep(500);
   try {
       viewflightbutton.click(); 
   } catch (ElementClickInterceptedException e) {
       js.executeScript("arguments[0].click();", viewflightbutton);

   }
}

//==================================================================================

//method to validate Fares on view flight
public void validateFares(Log log, ScreenShots screenshots) {
	
	String viewFlightsFarePrice = driver.findElement(By.xpath("//div[@data-tgflfare]")).getText();
	driver.findElement(By.xpath("//button[text()='Select']")).click();
	String bottomBarFarePrice = driver.findElement(By.xpath("//p[@data-tgflprice]")).getText();
	if(viewFlightsFarePrice.equals(bottomBarFarePrice)) {
	    System.out.println("Both are having same prices");
	    log.ReportEvent("PASS", "Both are having same prices");
	} else {
	    System.out.println("Prices are not same");
	    log.ReportEvent("FAIL", "Prices are not same");
        screenshots.takeScreenShot1();
	}

	
}

//===========================================================================================

public void selectdepartFaretype(String fareTypeArg)
{
    System.out.println(fareTypeArg);
    List<WebElement> allFareType = driver.findElements(By.xpath("//div[@data-tgflfaretype]"));
    for(WebElement fareType:allFareType)
    {
        String fareTypeText = fareType.getText();
        System.out.println(fareTypeText);
//        if(!fareTypeText.contains(fareTypeArg))
//        {
//            
//             JavascriptExecutor js = (JavascriptExecutor) driver;
//                js.executeScript("window.scrollBy(0, 1500);");
//        }
        System.out.println(fareTypeText+" "+fareTypeArg);
        if(fareTypeText.contains(fareTypeArg))
        {
            JavascriptExecutor jss = (JavascriptExecutor) driver;
             jss.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fareType);
            
        System.out.println("Found: " +fareTypeText);
        //driver.findElement(By.xpath("//div[@data-tgflfaretype='"+fareTypeArg+"']/parent::div/parent::div//button[2]")).click();
        
        // Locate the button associated with the specified fare type
        //WebElement fareTypeButton = driver.findElement(By.xpath("//div[@data-tgflfaretype='" + fareTypeText + "']/parent::div/parent::div//button[2]"));
        WebElement fareTypeButton = driver.findElement(By.xpath("//div[@data-tgflfaretype][normalize-space()='"+fareTypeText+"']/parent::div/parent::div//button[2]"));

        // Scroll the element into view
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", fareTypeButton);

        // Wait for the element to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(fareTypeButton));

        // Click the element using JavaScriptExecutor
        js.executeScript("arguments[0].click();", fareTypeButton);
        break;
        
        
       
        
        
        

        }
    }
}


//============================================================================

public void validateDepatureFaretypeToBookingPg(int index, String fareTypeArg, Log Log, ScreenShots ScreenShots) throws InterruptedException {
    boolean fareTypeFound = false;
    String actualSelectedFare = ""; // <-- Track selected fare here

    System.out.println(index);
    Thread.sleep(3000);

    String xpathExpression = "(//div[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]";
    WebElement button = driver.findElement(By.xpath(xpathExpression));
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
    Thread.sleep(1000);
    button.click();

    System.out.println(fareTypeArg);
    List<WebElement> allFareType = driver.findElements(By.xpath("//*[@data-tgflfaretype]"));
    for (WebElement fareType : allFareType) {
        String fareTypeText = fareType.getText().trim();
        System.out.println(fareTypeText);
        System.out.println(fareTypeText + " " + fareTypeArg);

        if (fareTypeText.contains(fareTypeArg)) {
            // Scroll and click the matched fare type
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fareType);
            System.out.println("Found: " + fareTypeText);
            Log.ReportEvent("PASS", "FareType Found: " + fareTypeText);

            WebElement fareTypeButton = driver.findElement(By.xpath("//*[@data-tgflfaretype][normalize-space()='" + fareTypeText + "']/parent::div/parent::div//button[2]"));
            js.executeScript("arguments[0].scrollIntoView(true);", fareTypeButton);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(fareTypeButton));
            js.executeScript("arguments[0].click();", fareTypeButton);
            Log.ReportEvent("PASS", "Clicked On FareType: " + fareTypeText);

            actualSelectedFare = fareTypeText;  // save the fare we actually selected
            fareTypeFound = true;
            break;
        }
    }

    if (!fareTypeFound) {
        // fallback: click first fare type
        if (!allFareType.isEmpty()) {
            WebElement firstFare = allFareType.get(0);
            actualSelectedFare = firstFare.getText().trim();
            firstFare.click();
            System.out.println("User expected FareType not found, clicked on first fare: " + actualSelectedFare);
            Log.ReportEvent("PASS", "User expected FareType not found, clicked on first fare: " + actualSelectedFare);
        } else {
            Log.ReportEvent("FAIL", "No fare types available to select.");
            return; // nothing to validate
        }
    }

    // Click Continue button
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
    WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Continue']")));
    try {
        continueBtn.click();
    } catch (Exception e) {
        System.out.println("Normal click failed, trying JS click");
        js.executeScript("arguments[0].click();", continueBtn);
    }

    // Wait for booking depart fare to be visible
    Thread.sleep(3000);
    WebElement BookingdepartFare = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'tg-fbdepartfaretype')]")));

    // Scroll to top of Booking depart fare
    js.executeScript("arguments[0].scrollIntoView({block: 'start', behavior: 'smooth'});", BookingdepartFare);
    ScreenShots.takeScreenShot1();

    String bookingFareText = BookingdepartFare.getText().trim();
    System.out.println("Booking Dsepart Fare (full): " + bookingFareText);

    // Validate actual selected fare with booking depart fare (case insensitive, substring match)
    if (bookingFareText.toLowerCase().contains(actualSelectedFare.toLowerCase())) {
        Log.ReportEvent("PASS", "Booking depart fare '" + bookingFareText + "' matches selected fare type: " + actualSelectedFare);
        System.out.println("Fare type validation passed.");
    } else {
        Log.ReportEvent("FAIL", "Booking depart fare '" + bookingFareText + "' does NOT match selected fare type '" + actualSelectedFare + "'");
        ScreenShots.takeScreenShot1();
        System.out.println("Fare type validation failed.");
    }
}


public void validateReturnFaretypeToBookingPg(int index, String fareTypeArg, Log Log, ScreenShots ScreenShots) throws InterruptedException {
    boolean fareTypeFound = false;
    String actualSelectedFare = ""; // <-- Track selected fare here

    System.out.println(index);
    Thread.sleep(3000);

    String xpathExpression = "(//*[@class='round-trip-to-results']//button[text()='View Flight'])[" + index + "]";
    WebElement button = driver.findElement(By.xpath(xpathExpression));
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
    Thread.sleep(1000);
    button.click();

    System.out.println(fareTypeArg);
    List<WebElement> allFareType = driver.findElements(By.xpath("//*[@data-tgflfaretype]"));
    for (WebElement fareType : allFareType) {
        String fareTypeText = fareType.getText().trim();
        System.out.println(fareTypeText);
        System.out.println(fareTypeText + " " + fareTypeArg);

        if (fareTypeText.contains(fareTypeArg)) {
            // Scroll and click the matched fare type
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fareType);
            System.out.println("Found: " + fareTypeText);
            Log.ReportEvent("PASS", "Return FareType Found: " + fareTypeText);

            WebElement fareTypeButton = driver.findElement(By.xpath("//*[@data-tgflfaretype][normalize-space()='" + fareTypeText + "']/parent::div/parent::div//button[2]"));
            js.executeScript("arguments[0].scrollIntoView(true);", fareTypeButton);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(fareTypeButton));
            js.executeScript("arguments[0].click();", fareTypeButton);
            Log.ReportEvent("PASS", "Clicked On Return FareType: " + fareTypeText);

            actualSelectedFare = fareTypeText;  // save the fare we actually selected
            fareTypeFound = true;
            break;
        }
    }

    if (!fareTypeFound) {
        // fallback: click first fare type
        if (!allFareType.isEmpty()) {
            WebElement firstFare = allFareType.get(0);
            actualSelectedFare = firstFare.getText().trim();
            firstFare.click();
            System.out.println("User expected FareType not found, clicked on first fare: " + actualSelectedFare);
            Log.ReportEvent("PASS", "User expected FareType not found, clicked on first fare: " + actualSelectedFare);
        } else {
            Log.ReportEvent("FAIL", "No Return fare types available to select.");
            return; // nothing to validate
        }
    }

    // Click Continue button
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
    WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Continue']")));
    try {
        continueBtn.click();
    } catch (Exception e) {
        System.out.println("Normal click failed, trying JS click");
        js.executeScript("arguments[0].click();", continueBtn);
    }

    // Wait for booking depart fare to be visible
    Thread.sleep(3000);
    WebElement BookingdepartFare = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'tg-fbreturnfaretype')]")));

    // Scroll to top of Booking depart fare
    js.executeScript("arguments[0].scrollIntoView({block: 'start', behavior: 'smooth'});", BookingdepartFare);
    ScreenShots.takeScreenShot1();

    String bookingFareText = BookingdepartFare.getText().trim();
    System.out.println("Booking Return Fare (full): " + bookingFareText);

    // Validate actual selected fare with booking depart fare (case insensitive, substring match)
    if (bookingFareText.toLowerCase().contains(actualSelectedFare.toLowerCase())) {
        Log.ReportEvent("PASS", "Booking Return fare '" + bookingFareText + "' matches selected fare type: " + actualSelectedFare);
        System.out.println("Return Fare type validation passed.");
    } else {
        Log.ReportEvent("FAIL", "Booking Return fare '" + bookingFareText + "' does NOT match selected fare type '" + actualSelectedFare + "'");
        ScreenShots.takeScreenShot1();
        System.out.println("Return Fare type validation failed.");
    }
}

//----------------------------------------------------------------------------


//--------------------------------------------------------------------------------------------------

public void selectFromFaretypePrices(int index, String fareTypeArg, Log log, ScreenShots screenshots) throws InterruptedException {
    boolean fareTypeFound = false;
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    System.out.println("Flight index: " + index);
    Thread.sleep(3000);

    // Step 1: Click "View Flight" button
    String xpathExpression = "(//div[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]";
    WebElement viewFlightBtn = driver.findElement(By.xpath(xpathExpression));
    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewFlightBtn);
    Thread.sleep(1000);
    viewFlightBtn.click();
    Thread.sleep(3000); // Wait for fare options to appear

    // Step 2: Find all fare type blocks
    List<WebElement> allFareTypes = driver.findElements(By.xpath("//*[@data-tgflfaretype]"));
    WebElement selectedFareBlock = null;
    String fareTypeText = ""; // Correct variable for fare type
    String popupFareText = "";

    for (WebElement fareType : allFareTypes) {
        String currentFareTypeText = fareType.getText().trim();
        System.out.println("Checking Fare Type: " + currentFareTypeText);

        if (currentFareTypeText.contains(fareTypeArg)) {
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fareType);
            log.ReportEvent("PASS", "FareType Found: " + currentFareTypeText);
            fareTypeFound = true;
            selectedFareBlock = fareType;
            fareTypeText = currentFareTypeText;
            break;
        }
    }

    // Step 3: Fallback if not found
    if (!fareTypeFound && !allFareTypes.isEmpty()) {
        log.ReportEvent("INFO", "FareType not found. Defaulting to first fare type.");
        selectedFareBlock = allFareTypes.get(0);
        fareTypeText = selectedFareBlock.getText().trim();
    }

    if (selectedFareBlock == null) {
        log.ReportEvent("FAIL", "No fare types found.");
        screenshots.takeScreenShot1();
        return;
    }

    // Step 4: Get fare price
    wait.until(ExpectedConditions.visibilityOf(selectedFareBlock));
    WebElement parent = selectedFareBlock.findElement(By.xpath(".."));
    List<WebElement> priceElements = parent.findElements(By.xpath(".//*[contains(@class, 'fare-price')]"));
    if (priceElements.isEmpty()) {
        log.ReportEvent("FAIL", "No fare price elements found inside selected fare block.");
        System.out.println("Selected block HTML: " + selectedFareBlock.getAttribute("outerHTML"));
        screenshots.takeScreenShot1();
        return;
    }

    popupFareText = priceElements.get(0).getText().trim();
    System.out.println("Popup Fare Price: " + popupFareText);
    log.ReportEvent("INFO", "Popup Fare Price: " + popupFareText);

    // Step 5: Scroll to and click "Select" button using fareTypeText
    try {
        String selectButtonXPath = "//*[@data-tgflfaretype][normalize-space()='" + fareTypeText + "']/parent::div/parent::div//button[2]";
        WebElement selectButton = driver.findElement(By.xpath(selectButtonXPath));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", selectButton);
        wait.until(ExpectedConditions.elementToBeClickable(selectButton));
        js.executeScript("arguments[0].click();", selectButton);
        log.ReportEvent("PASS", "Clicked on Select for FareType");
    } catch (Exception e) {
        log.ReportEvent("FAIL", "Could not find or click Select button: " + e.getMessage());
        screenshots.takeScreenShot1();
        return;
    }

    // Step 6: Get bottom bar fare price
    Thread.sleep(3000); // Wait for price update
    WebElement bottomBarPriceEl = driver.findElement(By.xpath("//*[@data-tgflprice]"));
    String bottomBarText = bottomBarPriceEl.getText().trim();
    System.out.println("Bottom Bar From Fare Price: " + bottomBarText);
    log.ReportEvent("INFO", "Bottom Bar From Fare Price: " + bottomBarText);

    // Step 7: Compare prices
    String cleanPopupPrice = popupFareText.replaceAll("[^0-9.]", "");
    String cleanBottomPrice = bottomBarText.replaceAll("[^0-9.]", "");

    if (cleanPopupPrice.equals(cleanBottomPrice)) {
        log.ReportEvent("PASS", "From Fare price matched: " + popupFareText);
        System.out.println("From Fare price matches.");
    } else {
        log.ReportEvent("FAIL", "From Price mismatch. Popup: " + popupFareText + ", Bottom Bar: " + bottomBarText);
        System.out.println("From Fare price mismatch.");
    }

    screenshots.takeScreenShot1();
}

public void selectReturnFaretypePrices(int index, String fareTypeArg, Log log, ScreenShots screenshots) throws InterruptedException {
    boolean fareTypeFound = false;
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    System.out.println("Flight index: " + index);
    Thread.sleep(3000);

    // Step 1: Click "View Flight" button
    String xpathExpression = "(//div[@class='round-trip-to-results']//button[text()='View Flight'])[" + index + "]";
    WebElement viewFlightBtn = driver.findElement(By.xpath(xpathExpression));
    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewFlightBtn);
    Thread.sleep(1000);
    viewFlightBtn.click();
    Thread.sleep(3000); // Wait for fare options to appear

    // Step 2: Find all fare type blocks
    List<WebElement> allFareTypes = driver.findElements(By.xpath("//*[@data-tgflfaretype]"));
    WebElement selectedFareBlock = null;
    String fareTypeText = ""; // Correct variable for fare type
    String popupFareText = "";

    for (WebElement fareType : allFareTypes) {
        String currentFareTypeText = fareType.getText().trim();
        System.out.println("Checking Fare Type: " + currentFareTypeText);

        if (currentFareTypeText.contains(fareTypeArg)) {
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fareType);
            log.ReportEvent("PASS", "FareType Found: " + currentFareTypeText);
            fareTypeFound = true;
            selectedFareBlock = fareType;
            fareTypeText = currentFareTypeText;
            break;
        }
    }

    // Step 3: Fallback if not found
    if (!fareTypeFound && !allFareTypes.isEmpty()) {
        log.ReportEvent("INFO", "FareType not found. Defaulting to first fare type.");
        selectedFareBlock = allFareTypes.get(0);
        fareTypeText = selectedFareBlock.getText().trim();
    }

    if (selectedFareBlock == null) {
        log.ReportEvent("FAIL", "No fare types found.");
        screenshots.takeScreenShot1();
        return;
    }

    // Step 4: Get fare price
    wait.until(ExpectedConditions.visibilityOf(selectedFareBlock));
    WebElement parent = selectedFareBlock.findElement(By.xpath(".."));
    List<WebElement> priceElements = parent.findElements(By.xpath(".//*[contains(@class, 'fare-price')]"));
    if (priceElements.isEmpty()) {
        log.ReportEvent("FAIL", "No fare price elements found inside selected fare block.");
        System.out.println("Selected block HTML: " + selectedFareBlock.getAttribute("outerHTML"));
        screenshots.takeScreenShot1();
        return;
    }

    popupFareText = priceElements.get(0).getText().trim();
    System.out.println("Popup Fare Price: " + popupFareText);
    log.ReportEvent("INFO", "Popup Fare Price: " + popupFareText);

    // Step 5: Scroll to and click "Select" button using fareTypeText
    try {
        String selectButtonXPath = "//*[@data-tgflfaretype][normalize-space()='" + fareTypeText + "']/parent::div/parent::div//button[2]";
        WebElement selectButton = driver.findElement(By.xpath(selectButtonXPath));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", selectButton);
        wait.until(ExpectedConditions.elementToBeClickable(selectButton));
        js.executeScript("arguments[0].click();", selectButton);
        log.ReportEvent("PASS", "Clicked on Select for FareType");
    } catch (Exception e) {
        log.ReportEvent("FAIL", "Could not find or click Select button: " + e.getMessage());
        screenshots.takeScreenShot1();
        return;
    }

    // Step 6: Get bottom bar fare price
    Thread.sleep(3000); // Wait for price update
    WebElement bottomBarPriceEl = driver.findElement(By.xpath("//*[@data-tgfltoprice]"));
    String bottomBarText = bottomBarPriceEl.getText().trim();
    System.out.println("Bottom Bar To Fare Price: " + bottomBarText);
    log.ReportEvent("INFO", "Bottom Bar To Fare Price: " + bottomBarText);

    // Step 7: Compare prices
    String cleanPopupPrice = popupFareText.replaceAll("[^0-9.]", "");
    String cleanBottomPrice = bottomBarText.replaceAll("[^0-9.]", "");

    if (cleanPopupPrice.equals(cleanBottomPrice)) {
        log.ReportEvent("PASS", "To Fare price matched: " + popupFareText);
        System.out.println("To Fare price matches.");
    } else {
        log.ReportEvent("FAIL", "To Price mismatch. Popup: " + popupFareText + ", Bottom Bar: " + bottomBarText);
        System.out.println("To Fare price mismatch.");
    }

    screenshots.takeScreenShot1();
}


//------------------------------------------------------------------------------------
public void popupClose()
{
    WebElement btn=driver.findElement(By.xpath("//button[.='Close']"));
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollIntoView(true);",btn );

    // Wait for the element to be clickable
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.elementToBeClickable(btn));

    // Click the element using JavaScriptExecutor
    js.executeScript("arguments[0].click();", btn);
}

//-------------------------------------------------------------------------------------------

public WebElement FarerulesToBookingPage(WebDriver driver, String fareType) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    
    String xpath = String.format("//h6[@data-tgfaretype='%s']", fareType);
    return driver.findElement(By.xpath(xpath));

}

//=======================


//public void selectFareBasedOnType(Log log, ScreenShots screenshots, WebDriver driver, String fareType) throws InterruptedException {
//    JavascriptExecutor js = (JavascriptExecutor) driver;
//
//    // Scroll to top
//    js.executeScript("window.scrollBy(0, -document.body.scrollHeight)");
//    try {
//        Thread.sleep(3000);
//    } catch (InterruptedException e) {
//        log.ReportEvent("FAIL", "Interrupted while scrolling to top: " + e.getMessage());
//        screenshots.takeScreenShot1();
//    }
//
//    List<WebElement> viewFlightsButtons = driver.findElements(By.xpath("//button[normalize-space()='View Flight']"));
//    log.ReportEvent("INFO", "Found " + viewFlightsButtons.size() + " 'View Flight' buttons");
//
//    for (int i = 0; i < viewFlightsButtons.size(); i++) {
//        WebElement viewBtn = viewFlightsButtons.get(i);
//
//        try {
//            log.ReportEvent("INFO", "Processing 'View Flight' button #" + (i + 1));
//            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewBtn);
//            Thread.sleep(3000);
//            js.executeScript("arguments[0].click();", viewBtn);
//            log.ReportEvent("PASS", "Clicked 'View Flight' button #" + (i + 1));
//            Thread.sleep(2000);
//            screenshots.takeScreenShot1();
//
//            // Wait for fare type elements to appear
//            try {
//                new WebDriverWait(driver, Duration.ofSeconds(70))
//                        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h6[@data-tgfaretype]")));
//                Thread.sleep(500);
//            } catch (Exception e) {
//                log.ReportEvent("INFO", "Fare options did not load for flight #" + (i + 1) + ": " + e.getMessage());
//                Thread.sleep(2000);
//                screenshots.takeScreenShot1();
//                continue;
//            }
//            WebElement scrollableContainer = driver.findElement(
//            	    By.xpath("//div[contains(@class, 'MuiPaper-root') or contains(@class, 'MuiDialogContent-root')]")
//            	);
//
//
//            List<WebElement> fareElements = driver.findElements(By.xpath("//h6[@data-tgfaretype]"));
//
//            List<WebElement> selectButtons = driver.findElements(By.xpath("//button[normalize-space()='Select']"));
//
//            for (int j = 0; j < fareElements.size(); j++) {
//                String currentFare = fareElements.get(j).getAttribute("data-tgfaretype");
//
//                if (currentFare.equalsIgnoreCase(fareType)) {
//                    try {
//                        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", currentFare);
//
//                    	 WebElement selectBtn = selectButtons.get(j);
//                        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", selectBtn);
//                        js.executeScript("arguments[0].click();", selectBtn);
//                        log.ReportEvent("PASS", "Successfully selected fare: " + fareType);
//                        Thread.sleep(2000);
//
//                        screenshots.takeScreenShot1();
//                       
//                        return;
//     } catch (Exception e) {
//                        log.ReportEvent("FAIL", "Failed to click 'Select' button for fare '" + fareType + "': " + e.getMessage());
//                        Thread.sleep(2000);
//
//                        screenshots.takeScreenShot1();
//                        throw new RuntimeException("Failed to click Select button", e);
//                    }
//                }
//            }
//
//            log.ReportEvent("INFO", "Fare type '" + fareType + "' not found for flight #" + (i + 1));
//            Thread.sleep(2000);
//
//            screenshots.takeScreenShot1();
//
//        } catch (Exception e) {
//            
//        	log.ReportEvent("FAIL", "Exception in processing flight option #" + (i + 1) + ": " + e.getMessage());
//                	screenshots.takeScreenShot1();
//        } finally {
//            // Close the modal if open
//            try {
//                WebElement closeBtn = new WebDriverWait(driver, Duration.ofSeconds(70))
//                        .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Close']")));
//                Thread.sleep(3000);
//                closeBtn.click();
//                log.ReportEvent("INFO", "Closed fare selection modal for flight #" + (i + 1));
//                Thread.sleep(300);
//            } catch (Exception e) {
//                log.ReportEvent("INFO", "No modal to close for flight #" + (i + 1));
//            }
//        }
//    }
//   
//
//    log.ReportEvent("FAIL", "Fare type '" + fareType + "' not found in any flight options.");
//    Thread.sleep(2000);
//
//    screenshots.takeScreenShot1();
//    throw new NoSuchElementException("Fare type '" + fareType + "' not found in any flight options.");
//    
//   
//    
//}

public void selectFareBasedOnType(Log log, ScreenShots screenshots, WebDriver driver, String fareType) throws InterruptedException {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    System.out.println("Starting selectFareBasedOnType with fareType: " + fareType);
    log.ReportEvent("INFO", "Starting fare selection for fareType: " + fareType);

    // Scroll to top of the page first
    try {
        js.executeScript("window.scrollTo(0, 0);");
        Thread.sleep(2000);
    } catch (Exception e) {
        log.ReportEvent("FAIL", "Failed to scroll to top: " + e.getMessage());
        screenshots.takeScreenShot1();
    }

    //all view flights buttons
    List<WebElement> viewFlightsButtons = driver.findElements(By.xpath("//button[normalize-space()='View Flight']"));
    log.ReportEvent("INFO", "Found " + viewFlightsButtons.size() + " 'View Flight' buttons");

    for (int i = 0; i < viewFlightsButtons.size(); i++) {
        WebElement viewBtn = viewFlightsButtons.get(i);

        try {
            System.out.println("Processing 'View Flight' button #" + (i + 1));

            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewBtn);
            Thread.sleep(1500);

            js.executeScript("arguments[0].click();", viewBtn);
            log.ReportEvent("PASS", "Clicked 'View Flight' button #" + (i + 1));
            Thread.sleep(2500);
            screenshots.takeScreenShot1();

            // Wait for fare type elements to load inside the modal/dialog
            try {
                new WebDriverWait(driver, Duration.ofSeconds(70))
                        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h6[@data-tgfaretype]")));
                Thread.sleep(500);
            } catch (Exception e) {
                log.ReportEvent("INFO", "Fare options did not load for flight #" + (i + 1) + ": " + e.getMessage());
                screenshots.takeScreenShot1();
                // Close modal and continue with next flight
                closeModalIfPresent(driver, log);
                continue;
            }

            // Get all fare elements and corresponding Select buttons
            List<WebElement> fareElements = driver.findElements(By.xpath("//h6[@data-tgfaretype]"));
            List<WebElement> selectButtons = driver.findElements(By.xpath("//button[normalize-space()='Select']"));


            boolean fareFound = false;

            for (int j = 0; j < fareElements.size(); j++) {
                String currentFare = fareElements.get(j).getAttribute("data-tgfaretype");
                System.out.println("Checking fare element #" + (j + 1) + " with data-tgfaretype='" + currentFare + "'");

                if (currentFare != null && currentFare.equalsIgnoreCase(fareType)) {
                    fareFound = true;
                    try {
                        System.out.println("Matching fare found at index " + j + ", scrolling fare element into view");
                        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fareElements.get(j));
                        Thread.sleep(800);

                        WebElement selectBtn = selectButtons.get(j);
                        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", selectBtn);
                        Thread.sleep(800);
                        js.executeScript("arguments[0].click();", selectBtn);

                        log.ReportEvent("PASS", "Successfully selected fare: " + fareType);
                        System.out.println("[DEBUG] Successfully selected fare: " + fareType);
                        Thread.sleep(2000);
                        screenshots.takeScreenShot1();
                        return; // Exit after successful selection
                    } catch (Exception e) {
                        System.out.println("[ERROR] Failed to click 'Select' button for fare '" + fareType + "': " + e.getMessage());
                        log.ReportEvent("FAIL", "Failed to click 'Select' button for fare '" + fareType + "': " + e.getMessage());
                        screenshots.takeScreenShot1();
                        throw new RuntimeException("Failed to click Select button", e);
                    }
                }
            }

            if (!fareFound) {
                System.out.println("[INFO] Fare type '" + fareType + "' not found for flight #" + (i + 1));
                log.ReportEvent("INFO", "Fare type '" + fareType + "' not found for flight #" + (i + 1));
                screenshots.takeScreenShot1();
            }

        } catch (Exception e) {
            System.out.println("[ERROR] Exception in processing flight option #" + (i + 1) + ": " + e.getMessage());
            log.ReportEvent("FAIL", "Exception in processing flight option #" + (i + 1) + ": " + e.getMessage());
            screenshots.takeScreenShot1();
        } finally {
            closeModalIfPresent(driver, log);
        }
    }

    System.out.println("[FAIL] Fare type '" + fareType + "' not found in any flight options.");
    log.ReportEvent("FAIL", "Fare type '" + fareType + "' not found in any flight options.");
    screenshots.takeScreenShot1();
    throw new NoSuchElementException("Fare type '" + fareType + "' not found in any flight options.");
}

// Helper method to close  if it is open
private void closeModalIfPresent(WebDriver driver, Log log) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Close']")));
        Thread.sleep(1000);
        closeBtn.click();
        log.ReportEvent("INFO", "Closed fare selection modal.");
        Thread.sleep(500);
    } catch (Exception e) {
        log.ReportEvent("INFO", "No modal to close or already closed.");
    }
}



public void ValidateFareToBookingPage(Log log, ScreenShots screenshots) throws InterruptedException {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(75));
        JavascriptExecutor js = (JavascriptExecutor) driver;

    	  // Continue to booking
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Continue']")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", continueBtn);
        Thread.sleep(500);
        js.executeScript("arguments[0].click();", continueBtn);


        System.out.println("clicked on continue button");
        // Wait for booking page fare text near Supplier Name image
       By bookingFareLocator = By.xpath("(//img[@alt='Supplier Name'])[1]/following-sibling::h6");
               WebElement bookingFareElement = wait.until(ExpectedConditions.visibilityOfElementLocated(bookingFareLocator));

                        // Extract and trim the text from the booking fare element
                        String bookingFareTextRaw = bookingFareElement.getText().trim();

                        String bookingFareText = bookingFareTextRaw.split("-")[0].trim();

                        // Log the extracted main part of the fare text
                        log.ReportEvent("INFO", "Booking page fare text: " + bookingFareText);

        // Collect all fare options from flight result page
        List<WebElement> fareElements = driver.findElements(By.xpath("//h6[@data-tgfaretype]"));
System.out.println(bookingFareText);
        boolean matchFound = false;
        for (WebElement fareElement : fareElements) {
            String fareOptionText = fareElement.getText().replaceAll("[^\\p{Print}]", "").trim();
            String bookingCleanText = bookingFareText.replaceAll("[^\\p{Print}]", "").trim();

            System.out.println("Comparing: '" + fareOptionText + "' with '" + bookingCleanText + "'");
            log.ReportEvent("INFO", "Flight result fare option: " + fareOptionText);

            if (fareOptionText.equalsIgnoreCase(bookingCleanText)
                || fareOptionText.toLowerCase().contains(bookingCleanText.toLowerCase())
                || bookingCleanText.toLowerCase().contains(fareOptionText.toLowerCase())) {
                matchFound = true;
                break;
            }
        }
    } catch (TimeoutException e) {
        log.ReportEvent("FAIL", "Booking fare element not found using Supplier Name image reference.");
        screenshots.takeScreenShot1();
        throw new NoSuchElementException("Could not locate booking fare text on booking page", e);
    } catch (Exception e) {
        log.ReportEvent("FAIL", "Unexpected error during fare validation: " + e.getMessage());
        screenshots.takeScreenShot1();
        throw e;
    }
}

//---------------------------------------------------------------------------

//Method to validate flight names from result page to booking page

public void validateBookingAirlineMatchesSelected(Log log, ScreenShots screenshots) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        // Step 1: Get all airline filter
        List<WebElement> airlineItems = driver.findElements(
            By.xpath("//legend[text()='AIRLINES']/parent::div//ul/li")
        );

        List<String> selectedAirlines = new ArrayList<>();

        for (WebElement item : airlineItems) {
            WebElement svgIcon = item.findElement(By.xpath(".//*[name()='svg']"));

            // Get its class attribute
            String svgClass = svgIcon.getAttribute("class");

            // Check if it contains 'Mui-checked' or similar class that indicates selection
            if (svgClass.contains("Mui-checked")) {
                // Get airline name from span
                WebElement span = item.findElement(By.xpath(".//span[contains(@class,'MuiListItemText-primary')]"));
                String airlineName = span.getText().trim();
                selectedAirlines.add(airlineName);
            }
        }

        log.ReportEvent("INFO", "User selected airlines from checkbox: " + selectedAirlines);
        screenshots.takeScreenShot1();

        // Step 2: Get airline name from booking page
        WebElement bookingElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[@data-tgflcarriername]")));

        String fullBookingAirline = bookingElement.getAttribute("data-tgflcarriername").trim();
        String[] parts = fullBookingAirline.split(" ");
        String bookingAirlineName = parts[0];  // Or use fullBookingAirline if you want full name

        log.ReportEvent("INFO", "Booking page airline name: " + bookingAirlineName);

        // Step 3: Compare
        boolean matchFound = selectedAirlines.stream()
            .anyMatch(a -> bookingAirlineName.equalsIgnoreCase(a) || fullBookingAirline.equalsIgnoreCase(a));

        if (matchFound) {
            log.ReportEvent("PASS", "Booking page airline matches a selected checkbox: " + bookingAirlineName);
        } else {
            log.ReportEvent("FAIL", "Booking airline '" + bookingAirlineName + "' does NOT match selected: " + selectedAirlines);
            screenshots.takeScreenShot1();
        }

    } catch (Exception e) {
        log.ReportEvent("FAIL", "Error validating airline name: " + e.getMessage());
        screenshots.takeScreenShot1();
        throw new RuntimeException("Airline validation failed", e);
    }
}

// Continue to booking
public void clickContinueButton() {
// Click Continue button
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
JavascriptExecutor js = (JavascriptExecutor) driver;

WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Continue']")));
try {
    continueBtn.click();
} catch (Exception e) {
    js.executeScript("arguments[0].click();", continueBtn);
    System.out.println("SUCCESSFULLY CLICKED");

}
}

public void navigateBack() throws InterruptedException {
	Thread.sleep(3000); 
driver.navigate().back();

}


public void validateRadioButtonRoundTrip(Log Log, ScreenShots ScreenShots) {
    try {
        Thread.sleep(2000); // Wait for UI transition if needed
        WebElement roundtripRadioButton = driver.findElement(By.xpath("//input[@value='roundtrip']"));

        // Check if roundtrip radio button is selected
        if (roundtripRadioButton.isSelected()) {
            System.out.println("Successfully changed from booking page to result page (Roundtrip selected)");
            Log.ReportEvent("PASS", "Successfully changed from booking page to result page (Roundtrip selected)");
        } else {
            System.out.println("Did not change from booking page to result page (Roundtrip not selected)");
            Log.ReportEvent("FAIL", "Did not change from booking page to result page (Roundtrip not selected)");
        }

        ScreenShots.takeScreenShot1();
    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception while validating radio button: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
    }
}

//---------------------------------------------------------------------------------------



/*public void clickSeats(int index) throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Get the stop count for the selected flight
    WebElement stopElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("(//div[@class='round-trip-from-results']//span[@data-tgnumstops])[" + index + "]")));
    String stopCount = stopElement.getAttribute("data-tgnumstops").trim();

    // Determine seat button index based on stop count
    int seatButtonIndex;
    if (stopCount.equals("0")) {          // Nonstop
        seatButtonIndex = 1;
    } else if (stopCount.equals("1")) { 
    	// Onestop
        seatButtonIndex = 1;
        seatButtonIndex = 2;
    } else if (stopCount.equals("2")) {   // Twostop
    	seatButtonIndex = 1;
        seatButtonIndex = 2;
        seatButtonIndex = 3;
        
    } else {                              // Fallback if unexpected value
        seatButtonIndex = 1;
    }

    // Scroll to and click the appropriate Pick Seat button
    WebElement pickSeatButton = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("(//button[contains(normalize-space(text()), 'Pick Seat')])[" + seatButtonIndex + "]")));
    System.out.println("Clicking on Pick Seat button at index: " + seatButtonIndex);

    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", pickSeatButton);
    Thread.sleep(1000);
    js.executeScript("arguments[0].click();", pickSeatButton);
}*/

public void clickSeats(int index) throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Get the stop count for the selected flight
    WebElement stopElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("(//div[@class='round-trip-from-results']//span[@data-tgnumstops])[" + index + "]")));
    String stopCount = stopElement.getAttribute("data-tgnumstops").trim();

    // Determine how many seat buttons to click based on stop count
    int numberOfStops;
    try {
        numberOfStops = Integer.parseInt(stopCount);
    } catch (NumberFormatException e) {
        numberOfStops = 0; // Default to nonstop if parsing fails
    }

    // Loop through and click required number of Pick Seat buttons
    for (int i = 1; i <= numberOfStops + 1; i++) { // +1 to include the first segment
        WebElement pickSeatButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//button[contains(normalize-space(text()), 'Pick Seat')])[" + i + "]")));

        System.out.println("Clicking on Pick Seat button at index: " + i);

        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", pickSeatButton);
        Thread.sleep(1000);
        js.executeScript("arguments[0].click();", pickSeatButton); 
    }
 
}

public void selectSeatByNumber(String seatNumber) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
    String dynamicXPath = String.format("//div[@tg-seat='%s']", seatNumber);

    try {
        WebElement seat = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXPath)));

        // Scroll into view
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", seat);
        Thread.sleep(300);

        // Click the seat
        seat.click();
        System.out.println("Clicked seat: " + seatNumber);

    } catch (Exception e) {
        System.out.println("Failed to select seat " + seatNumber + ": " + e.getMessage());
        e.printStackTrace();
    }
}

//validate seats in booking page

/*public void validateSeatBooking(String seatNumber) throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Get seat price from seat map
    String seatPriceText = driver.findElement(By.xpath(String.format("//div[@tg-seat='%s']//small", seatNumber))).getText();

    Thread.sleep(3000); 

    // Click Continue
    driver.findElement(By.xpath("//button[text()='Continue']")).click();

    // Wait for booking page price to be visible
    WebElement bookingpgPriceText = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//span[normalize-space()='Seat Price']/following-sibling::h6")
    ));

    // Scroll to booking page price element
    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", bookingpgPriceText);
    Thread.sleep(500);

    String bookingPriceText = bookingpgPriceText.getText();

    // Compare the two price texts
    if (seatPriceText.equals(bookingPriceText)) {
        System.out.println("PASS: Seat price matches on booking page: " + seatPriceText);
    } else {
        System.out.println("FAIL: Seat price mismatch. Seat page: " + seatPriceText + ", Booking page: " + bookingPriceText);
    }
    
    WebElement bookingpgPrice = driver.findElement(By.xpath("//span[normalize-space()='Total']/following-sibling::h6"));
    bookingpgPrice.getText();
    
    WebElement bookingpgSeatPrice = driver.findElement(By.xpath("//span[normalize-space()='Seat Price']/following-sibling::h6"));
    bookingpgSeatPrice.getText();
    
    WebElement bookingpgGrandPrice = driver.findElement(By.xpath("//span[normalize-space()='Grand Total']/following-sibling::h6"));
    bookingpgGrandPrice.getText();
} */

public void validateSeatBooking(String seatNumber) throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Get seat price from seat map
    String seatPriceText = driver.findElement(By.xpath(String.format("//div[@tg-seat='%s']//small", seatNumber))).getText();

    Thread.sleep(3000);

    // Click Continue
    driver.findElement(By.xpath("//button[text()='Continue']")).click();

    // Wait for booking page seat price to be visible
    WebElement bookingpgSeatPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//span[normalize-space()='Seat Price']/following-sibling::h6")
    ));
    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", bookingpgSeatPrice);
    Thread.sleep(500);

    String bookingSeatPriceText = bookingpgSeatPrice.getText();

    // Compare seat price texts
    if (seatPriceText.equals(bookingSeatPriceText)) {
        System.out.println("PASS: Seat price matches on booking page: " + seatPriceText);
    } else {
        System.out.println("FAIL: Seat price mismatch. Seat page: " + seatPriceText + ", Booking page: " + bookingSeatPriceText);
    }

    // Get booking page Total price
    WebElement bookingpgPrice = driver.findElement(By.xpath("//span[normalize-space()='Total']/following-sibling::h6"));
    String bookingPriceText = bookingpgPrice.getText();

    // Get booking page Grand Total price
    WebElement bookingpgGrandPrice = driver.findElement(By.xpath("//span[normalize-space()='Grand Total']/following-sibling::h6"));
    String bookingGrandPriceText = bookingpgGrandPrice.getText();

    // Convert price strings to numbers
    double seatPrice = parsePrice(bookingSeatPriceText);
    double totalPrice = parsePrice(bookingPriceText);
    double grandTotal = parsePrice(bookingGrandPriceText);

    // Sum seat price + total price
    double sum = seatPrice + totalPrice;

    // Compare sum with grand total (allow small decimal tolerance)
    if (Math.abs(sum - grandTotal) < 0.01) {
        System.out.println("PASS: Sum of Seat Price and Total matches Grand Total: " + sum);
    } else {
        System.out.println("FAIL: Sum mismatch. Seat Price + Total = " + sum + ", but Grand Total = " + grandTotal);
    }
}

// Helper method to parse price string like "₹ 12,207" to double 12207.0
private double parsePrice(String priceText) {
    String cleaned = priceText.replaceAll("[₹,\\s]", "");
    return Double.parseDouble(cleaned);
}

//==============================================================================================

//Method to vaklidate FirstName in booking page

public void validateBookingpgFirstName(Log Log, ScreenShots ScreenShots, WebDriver driver) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Wait for the First Name input field to appear
        WebElement firstName = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h6[@data-tgflguesttype='Adult 1']//following::input[@placeholder='First Name']")));

        // Scroll directly to the First Name input field
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", firstName);
        Thread.sleep(1000); // Allow smooth scroll animation to complete

        // Validate visibility and interactability
        if (firstName.isDisplayed() && firstName.isEnabled()) {
            firstName.clear(); // Clear if interactable
            System.out.println("Cleared First Name field.");
        } else {
            Log.ReportEvent("FAIL", "First Name field is not interactable.");
            return;
        }

        // Scroll to and click 'Send for Approval'
        WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()='Send for Approval']/ancestor::button")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", sendButton);
        Thread.sleep(500);
        sendButton.click();
        System.out.println("Clicked 'Send for Approval' button.");

        // Wait for error message popup
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[@role='presentation']")));
        if (error.isDisplayed()) {
            Log.ReportEvent("PASS", "Error message is displayed.");
        } else {
            Log.ReportEvent("FAIL", "Error message is not displayed.");
        }

        ScreenShots.takeScreenShot1();

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception occurred: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
    }
}



//==========================================================================================================

//Method to vaklidate LastName in booking page

public void validateBookingpgLastName(Log Log, ScreenShots ScreenShots, WebDriver driver) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Wait for Last Name field to be present in the DOM
        WebElement lastName = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//h6[@data-tgflguesttype='Adult 1']//following::input[@placeholder='Last Name']")));

        // Scroll to center of the screen
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", lastName);
        Thread.sleep(1000);

        // Ensure element is visible and enabled
        wait.until(ExpectedConditions.visibilityOf(lastName));
        wait.until(ExpectedConditions.elementToBeClickable(lastName));

        if (lastName.isDisplayed() && lastName.isEnabled()) {
            lastName.clear();
            System.out.println("Cleared Last Name field.");
        } else {
            Log.ReportEvent("FAIL", "Last Name field is not interactable.");
            ScreenShots.takeScreenShot1();
            return;
        }

        // Scroll to and click Send for Approval button
        WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()='Send for Approval']/ancestor::button")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", sendButton);
        Thread.sleep(500);
        sendButton.click();
        System.out.println("Clicked 'Send for Approval' button.");

        // Wait and validate error popup
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[@role='presentation']")));
        if (error.isDisplayed()) {
            Log.ReportEvent("PASS", "Error message is displayed.");
        } else {
            Log.ReportEvent("FAIL", "Error message is not displayed.");
        }

        ScreenShots.takeScreenShot1();

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception occurred: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
    }
}


//======================================================================================================

//Method to vaklidate Email in booking page

public void validateBookingpgEmail(Log Log, ScreenShots ScreenShots, WebDriver driver) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Wait for and scroll to Email field
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@name='email']")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", emailField);
        Thread.sleep(1000); // Allow scroll animation to complete

        // Ensure email field is interactable
        if (emailField.isDisplayed() && emailField.isEnabled()) {
            emailField.clear();
            System.out.println("Cleared Email field.");
        } else {
            Log.ReportEvent("FAIL", "Email field is not interactable.");
            return;
        }

        // Scroll to and click 'Send for Approval'
        WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()='Send for Approval']/ancestor::button")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", sendButton);
        Thread.sleep(500);
        sendButton.click();
        System.out.println("Clicked 'Send for Approval' button.");

        // Wait for error popup
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[@role='presentation']")));
        if (error.isDisplayed()) {
            Log.ReportEvent("PASS", "Error message is displayed.");
        } else {
            Log.ReportEvent("FAIL", "Error message is not displayed.");
        }

        ScreenShots.takeScreenShot1();

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception occurred: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
    }
}


//==================================================================================

//Method to vaklidate PhoneNo in booking page

public void validateBookingpgPhoneNo(Log Log, ScreenShots ScreenShots, WebDriver driver) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Wait for and scroll to Phone Number field
        WebElement phoneNo = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//input[@name='mobile']")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", phoneNo);
        Thread.sleep(1000); // Let the scroll animation complete

        // Ensure Phone Number field is interactable
        if (phoneNo.isDisplayed() && phoneNo.isEnabled()) {
            phoneNo.clear();
            System.out.println("Cleared Phone Number field.");
        } else {
            Log.ReportEvent("FAIL", "Phone Number field is not interactable.");
            return;
        }

        // Scroll to and click Send for Approval button
        WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()='Send for Approval']/ancestor::button")));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", sendButton);
        Thread.sleep(500);
        sendButton.click();
        System.out.println("Clicked 'Send for Approval' button.");

        // Wait for error popup
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[@role='presentation']")));
        if (error.isDisplayed()) {
            Log.ReportEvent("PASS", "Error message is displayed.");
        } else {
            Log.ReportEvent("FAIL", "Error message is not displayed.");
        }

        ScreenShots.takeScreenShot1();

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception occurred: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
    }
}


//=============================================================================================

//Method to vaklidate Title in booking page

public void validateBookingpageTitle(Log log, ScreenShots screenshots, WebDriver driver) {
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


//Method to click On Title DropDown

	public void clickOnTitleDropdown() throws InterruptedException {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));

	    // Wait until page is fully loaded
	    wait.until(webDriver -> ((JavascriptExecutor) webDriver)
	        .executeScript("return document.readyState").equals("complete"));

	    // Locate the Title dropdown element
	    WebElement titleDropdown = driver.findElement(
	        By.xpath("//label[text()='Title']/ancestor::*[1]//div[@aria-haspopup='listbox']")
	    );

	    // Scroll to the element
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", titleDropdown);

	    Thread.sleep(500);  // Allow scroll to finish smoothly

	    // Click the dropdown
	    titleDropdown.click();
	}

	//Method to select title DropDown Values
	public void selectTitleDropDownValueByText(Log Log, ScreenShots ScreenShots, WebDriver driver, String valueToSelect) {
	    try {
	        new WebDriverWait(driver, Duration.ofSeconds(90)).until(
	            webDriver -> ((JavascriptExecutor) webDriver)
	                .executeScript("return document.readyState").equals("complete")
	        );

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));

	        String dynamicXPath = String.format("//li[@data-value and normalize-space(text())='%s']", valueToSelect);

	        WebElement titleValue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dynamicXPath)));

	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].scrollIntoView(true);", titleValue);
	        Thread.sleep(500); 

	        wait.until(ExpectedConditions.elementToBeClickable(titleValue)).click();

	        String selectedText = titleValue.getText().trim();
	        Log.ReportEvent("PASS", "Title: " + selectedText);

	    } catch (Exception e) {
	        Log.ReportEvent("FAIL", "Exception while selecting Title: " + e.getMessage());
	        ScreenShots.takeScreenShot1();
	    }
	}

	
	//================================================================================
	
	public void validateRoundtripPolicyInBookingPage(Log log, ScreenShots screenshots, String expectedValue) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
	        JavascriptExecutor js = (JavascriptExecutor) driver;

	        // Scroll to top to ensure visibility
	        js.executeScript("window.scrollTo(0, 0);");

	        // Wait until policy element is visible
	        WebElement policyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//div[@data-tginploicy]")));

	        String bookingpgPolicy = policyElement.getText().trim();
	        System.out.println("Booking Page Policy Text: " + bookingpgPolicy);

	        if (bookingpgPolicy.equalsIgnoreCase(expectedValue)) {
	            log.ReportEvent("PASS", "Booking page policy matches expected value: '" + expectedValue + "'");
	        } else {
	            log.ReportEvent("FAIL", "Booking page policy mismatch. Expected: '" + expectedValue + "', Found: '" + bookingpgPolicy + "'");
	            screenshots.takeScreenShot1();
	            Assert.fail("Booking page policy does not match expected value.");
	        }

	    } catch (Exception e) {
	        log.ReportEvent("FAIL", "Exception occurred while validating booking page policy: " + e.getMessage());
	        screenshots.takeScreenShot1();
	        e.printStackTrace();
	        Assert.fail("Exception occurred during booking policy validation.");
	    }
	}

		
	//==============================================================================================
	
	public void bookingPagePolicy(Log Log, ScreenShots ScreenShots, String expectedValue) {
        try {
            // Wait until at least one policy element is present
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='inpolicy']")),
                    ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='outofpolicy']"))
                    ));

            // Re-locate all policy elements freshly (avoid stale)
            List<WebElement> inPolicyElements = driver.findElements(By.xpath("//div[@class='inpolicy']"));
            List<WebElement> outOfPolicyElements = driver.findElements(By.xpath("//div[@class='outofpolicy']"));

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
            boolean outOfPolicyFliter=false;
            String outOfPolicy="";
            // Re-fetch text inside loop to avoid stale elements
            for (int i = 0; i < allPolicies.size(); i++) {
                try {
                    WebElement freshPolicy = allPolicies.get(i);
                    String policyText = freshPolicy.getText().trim();
                    
                    System.out.println("Policy Text: " + policyText);
                    if(policyText.equals("Out of Policy"))
                    {
                         outOfPolicy=freshPolicy.getAttribute("aria-label");
                        System.out.println(outOfPolicy);
                        outOfPolicyFliter=true;
                    }

                    if (!policyText.equalsIgnoreCase(expectedValue)) {
                        mismatchedPolicies.add(policyText);
                        allMatch = false;
                    }
                } catch (StaleElementReferenceException se) {
                    // Try re-fetching if stale
                    allPolicies = driver.findElements(By.xpath("//div[@class='inpolicy' or @class='outofpolicy']"));
                    i--; // retry the same index
                }
            }

            if (allMatch) {
                Log.ReportEvent("PASS", "✅ All displayed policies match: '" + expectedValue + "'");
            } else {
                Log.ReportEvent("FAIL", "❌ Some policies do not match '" + expectedValue + "'. Mismatched: " + mismatchedPolicies);
                Assert.fail("One or more policies do not match expected value.");
            }
            if(outOfPolicyFliter)
            {
                Log.ReportEvent("PASS", "✅ OutOfPolicy is Displayed: '" + outOfPolicy + "'");
            }

            ScreenShots.takeScreenShot1();

        } catch (Exception e) {
            Log.ReportEvent("FAIL", "❌ Exception while validating policy: " + e.getMessage());
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
            Assert.fail("Exception during policy validation.");
        }
    }

	//===============================================================================
	
	//Method to validate round Trip Footer Validation
    public void roundTripFooterValidation(String fromLocations, String fromDestinationLocations, String fromDepartTiming,
            String fromArrivalTiming, String FromDurationTime, String FromStop,
            double fromFlightPrices,
            String returnLocations, String returnDestinationLocations, String returnDepartTiming,
            String returnArrivalTiming, String returnDurationTime, String returnStopsExpected,
            double returnFlightPrices,
            Log log, ScreenShots screenshots) throws InterruptedException {

        boolean isFromValid = false;
        boolean isReturnValid = false;

        // ----- FROM FLIGHT DETAILS -----
        String fromLocation = driver.findElement(By.xpath("//small[@data-tgflorigin]")).getText();
        String fromDestinationLocation = driver.findElement(By.xpath("//small[@data-tgfldestination]")).getText();
        String fromDepartTime = driver.findElement(By.xpath("//span[@data-tgfldeptime]")).getText();
        String fromArrivalTime = driver.findElement(By.xpath("//span[@data-tgflarrtime]")).getText();
        //String FromDuration = driver.findElement(By.xpath("//p[@data-tgfldurationandstops]")).getText();
        // Locate the element using XPath
        WebElement fromElement = driver.findElement(By.xpath("//p[@data-tgfldurationandstops]"));

        // Get full text content (e.g., "5h 30m , 1 stops")
        String fullText = fromElement.getText();

        // Extract just the "5h 30m" part
        String fromDuration = fullText.split(",")[0].trim();

        System.out.println(fromDuration); // Output: "5h 30m"

        //String FromStops = driver.findElement(By.xpath("//small[@data-tgfloriginstops]")).getText();
        //System.out.println(FromStops);
        // Locate the element
        WebElement fromelement = driver.findElement(By.xpath("//small[@data-tgfloriginstops]"));

        // Get the full text (e.g., "1 stops")
        String fromFullText = fromelement.getText();

        // Remove the space between number and text
        String FromStops = fromFullText.replace(" ", "");

        System.out.println(FromStops); // Output: "1stops"

        
        

        String fromFlightPriceStr = driver.findElement(By.xpath("//p[@data-tgflprice]")).getAttribute("data-tgflprice");
        double fromFlightPrice = Double.parseDouble(fromFlightPriceStr);

        // ----- RETURN FLIGHT DETAILS -----
        String returnLocation = driver.findElement(By.xpath("//small[@data-tgfltoorigin]")).getText();
        String returnDestinationLocation = driver.findElement(By.xpath("//small[@data-tgfltodestination]")).getText();
        Thread.sleep(3000);
        String returnDepartTime = driver.findElement(By.xpath("//span[@data-tgfltodeptime]")).getText();
        String returnArrivalTime = driver.findElement(By.xpath("//span[@data-tgfltoarrtime]")).getText();
        //    String returnDuration = driver.findElement(By.xpath("//p[@data-tgfltodurationandstops]")).getText();
        WebElement returnElement = driver.findElement(By.xpath("//p[@data-tgfltodurationandstops]"));

        // Get full text content (e.g., "5h 30m , 1 stops")
        String  returnElements= returnElement.getText();

        // Extract just the "5h 30m" part
        String returnDuration = returnElements.split(",")[0].trim();

        System.out.println(returnDuration); // Output: "5h 30m"

        Thread.sleep(3000);
        //String returnStops = driver.findElement(By.xpath("//small[@data-tgfldestinationstops]")).getText();
        WebElement returnelement = driver.findElement(By.xpath("//small[@data-tgfldestinationstops]"));

        // Get the full text (e.g., "1 stops")
        String returnFullText = returnelement.getText();

        // Remove the space between number and text
        String returnStops = returnFullText.replace(" ", "");

        System.out.println(returnStops); // Output: "1stops"

        String returnFlightPriceStr = driver.findElement(By.xpath("//p[@data-tgfltoprice]")).getAttribute("data-tgfltoprice");
        double returnFlightPrice = Double.parseDouble(returnFlightPriceStr);

        System.out.println(fromLocation+""+fromLocations);
        System.out.println(fromDestinationLocation+""+fromDestinationLocations);
        System.out.println(fromDepartTime+""+fromDepartTiming);
        System.out.println(fromArrivalTime+""+fromArrivalTiming);
        System.out.println(fromDuration+""+FromDurationTime);
        System.out.println(FromStops+"  "+FromStop);
        System.out.println(fromFlightPrice+""+fromFlightPrices);


        // ----- FROM FLIGHT VALIDATION -----
        if (fromLocation.equals(fromLocations) &&
                fromDestinationLocation.equals(fromDestinationLocations) &&
                fromDepartTime.equals(fromDepartTiming) &&
                fromArrivalTime.equals(fromArrivalTiming) &&
                fromDuration.equals(FromDurationTime) &&
                FromStops.equals(FromStop) &&
                fromFlightPrice == fromFlightPrices) {

            isFromValid = true;

            log.ReportEvent("PASS", "From Flight Details Matched:\n" +
                    "From: " + fromLocation + " | To: " + fromDestinationLocation +
                    " | Dep: " + fromDepartTime + " | Arr: " + fromArrivalTime +
                    "\nDuration: " + fromDuration + " | Stops: " + FromStops +
                    " | Price: " + fromFlightPrice);
            screenshots.takeScreenShot1();
        } else {
            log.ReportEvent("FAIL", "From Flight Details Mismatch:\n" +
                    "From: " + fromLocation + " vs " + fromLocations + "\n" +
                    "To: " + fromDestinationLocation + " vs " + fromDestinationLocations + "\n" +
                    "Departure: " + fromDepartTime + " vs " + fromDepartTiming + "\n" +
                    "Arrival: " + fromArrivalTime + " vs " + fromArrivalTiming + "\n" +
                    "Duration: " + fromDuration + " vs " + FromDurationTime + "\n" +
                    "Stops: " + FromStops + " vs " + FromStop + "\n" +
                    "Price: " + fromFlightPrice + " vs " + fromFlightPrices);
            screenshots.takeScreenShot1();
        }

        // ----- RETURN FLIGHT VALIDATION -----
        System.out.println(returnLocation+" "+returnLocations);
        System.out.println(returnDestinationLocation+" "+returnDestinationLocations);
        System.out.println(returnDepartTiming+" "+returnDepartTiming);
        System.out.println(returnArrivalTime+" "+returnArrivalTiming);
        System.out.println(returnDuration+" "+returnDurationTime);
        System.out.println(returnStops+" "+returnStopsExpected);
        System.out.println(returnFlightPrice+" "+returnFlightPrices);

        if (returnLocation.equals(returnLocations) &&
                returnDestinationLocation.equals(returnDestinationLocations) &&
                returnDepartTime.equals(returnDepartTiming) &&
                returnArrivalTime.equals(returnArrivalTiming) &&
                returnDuration.equals(returnDurationTime) &&
                returnStops.equals(returnStopsExpected) &&
                returnFlightPrice == returnFlightPrices) {

            isReturnValid = true;

            log.ReportEvent("PASS", "Return Flight Details Matched:\n" +
                    "From: " + returnLocation + " | To: " + returnDestinationLocation +
                    " | Dep: " + returnDepartTime + " | Arr: " + returnArrivalTime +
                    "\nDuration: " + returnDuration + " | Stops: " + returnStops +
                    " | Price: " + returnFlightPrice);
        } else {
            log.ReportEvent("FAIL", "Return Flight Details Mismatch:\n" +
                    "From: " + returnLocation + " vs " + returnLocations + "\n" +
                    "To: " + returnDestinationLocation + " vs " + returnDestinationLocations + "\n" +
                    "Departure: " + returnDepartTime + " vs " + returnDepartTiming + "\n" +
                    "Arrival: " + returnArrivalTime + " vs " + returnArrivalTiming + "\n" +
                    "Duration: " + returnDuration + " vs " + returnDurationTime + "\n" +
                    "Stops: " + returnStops + " vs " + returnStopsExpected + "\n" +
                    "Price: " + returnFlightPrice + " vs " + returnFlightPrices);
            screenshots.takeScreenShot1();
        }

        // ----- TOTAL PRICE VALIDATION -----
        if (isFromValid && isReturnValid) {
            validateTotalPrice(fromFlightPrice, returnFlightPrice, log, screenshots);
        }
    }

    //Helper Method For TOTAL PRICE VALIDATION 

    public Object[] validateTotalPrice(double fromPrice, double returnPrice, Log log, ScreenShots screenshots) throws InterruptedException {
        // Calculate total
        double totalPrice = fromPrice + returnPrice;

        // Round normally (e.g. 0.5+ rounds up, below rounds down)
        int expectedTotal = (int) Math.round(totalPrice);

        // Get total price from site
        String totalStr = driver.findElement(By.xpath("//h5[@data-tgtotal]")).getAttribute("data-tgtotal");
        double siteTotalDouble = Double.parseDouble(totalStr);
        int actualTotal = (int) Math.round(siteTotalDouble); // optional, just in case

        // Compare and report
        if (expectedTotal == actualTotal) {
            log.ReportEvent("PASS", "Total Price Matched:\nFrom: " + fromPrice + " + Return: " + returnPrice +
                    " = Expected Total: " + expectedTotal + " | Site Total: " + actualTotal);
            Thread.sleep(3000);
            WebElement button = driver.findElement(By.xpath("//button[text()='Continue']"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", button);

            return new Object[] {siteTotalDouble};
        } else {
            log.ReportEvent("FAIL", "Total Price Mismatch:\nFrom: " + fromPrice + " + Return: " + returnPrice +
                    " = Expected Total: " + expectedTotal + " | Site Total: " + actualTotal);
            screenshots.takeScreenShot1();
        }
        return null;

    }
//===========================================================
    
    public String[] userEnterData() {
        try {
            // Get "From" location code
            String fromText = driver.findElement(By.xpath("(//div[contains(@class, 'tg-select__single-value')])[1]")).getText().trim();
            String fromLocationCode = extractAirportCode(fromText);

            // Get "To" location code
            String toText = driver.findElement(By.xpath("(//div[contains(@class, 'tg-select__single-value')])[2]")).getText().trim();
            String toLocationCode = extractAirportCode(toText);

            // Get journey date
            WebElement journeyDateInput = driver.findElement(By.xpath("//input[@placeholder='Journey Date']"));
            String journeyDateValue = journeyDateInput.getAttribute("value").trim();

            // Get return date
            WebElement returnDateInput = driver.findElement(By.xpath("//input[@placeholder='Return Date (Optional)']"));
            String returnDateValue = returnDateInput.getAttribute("value").trim();

            return new String[] { fromLocationCode, toLocationCode, journeyDateValue, returnDateValue };

        } catch (Exception e) {
            e.printStackTrace();
            return new String[] { "", "", "", "" };
        }
    }
    
    //----------------------------------------------------------------------------------------
    
    //Method to validatde policy filter round trip
    
//    public void validatePolicyFilterRoundTrip(int index) throws InterruptedException {
//    	WebElement fromPolicyText = driver.findElement(By.xpath("//*[contains(@class, 'round-trip-from-results')]//*[contains(@class, 'inpolicy tg-policy')]"));
//    	fromPolicyText.getText();
//    	
//    	//click on from view flight based on index
//    	 String xpathExpression = "(//div[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]";
//    	    WebElement button = driver.findElement(By.xpath(xpathExpression));
//    	    JavascriptExecutor js = (JavascriptExecutor) driver;
//    	    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button);
//    	    Thread.sleep(1000);
//    	    button.click();
//    	    
//    WebElement FarePolicyText = driver.findElement(By.xpath("//*[contains(@class, 'tg-flight-details-dialog')]/parent::div//*[contains(@class,'outofpolicy tg-policy')]"));
//    FarePolicyText.getText();
//    	
//    	//close button
//    driver.findElement(By.xpath("//button[text()='Close']")).click();
//    
//    	
//  //click on to view flight based on index
//	 String toExpression = "(//div[@class='round-trip-to-results']//button[text()='View Flight'])[" + index + "]";
//	    WebElement to = driver.findElement(By.xpath(toExpression));
//	    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", to);
//	    Thread.sleep(1000);
//	    to.click();
//	    
//WebElement ToFarePolicyText = driver.findElement(By.xpath("//*[contains(@class, 'tg-flight-details-dialog')]/parent::div//*[contains(@class,'outofpolicy tg-policy')]"));
//ToFarePolicyText.getText();
//	
//	//close button
//driver.findElement(By.xpath("//button[text()='Close']")).click();
//
////booking page validation
//
//WebElement departBookingPgPolicyText = driver.findElement(By.xpath("//*[@data-tgdepartinpolicy]"));
//departBookingPgPolicyText.getText();
//
//WebElement returnBookingPgPolicyText = driver.findElement(By.xpath("//*[@data-tgreturninpolicy]"));
//returnBookingPgPolicyText.getText();
//
//    }
    
    public void validatePolicyFilterRoundTrip(int index, String expectedPolicyText,Log log, ScreenShots screenshots) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Normalize input
        String expectedNormalized = expectedPolicyText.replaceAll("\\s+", "").toLowerCase();
        System.out.println("Starting validation for policy text: " + expectedPolicyText);
        log.ReportEvent("INFO", "Starting validation for policy text: " + expectedPolicyText);

        // === FROM POLICY TEXT ===
        WebElement fromPolicyText = driver.findElement(By.xpath("//*[contains(@class, 'round-trip-from-results')]//*[@class='inpolicy tg-policy' and @data-tginpolicy='true']"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fromPolicyText);
        Thread.sleep(500);
        String fromText = fromPolicyText.getAttribute("textContent").trim().replaceAll("\\s+", "").toLowerCase();
        System.out.println("From policy text found: " + fromText);
        if (!fromText.contains(expectedNormalized)) {
            log.ReportEvent("FAIL", "From result policy mismatch. Expected: " + expectedPolicyText + ", Found: " + fromText);
            screenshots.takeScreenShot1();
            throw new AssertionError("From result policy mismatch. Expected: " + expectedPolicyText + ", Found: " + fromText);
        }
        log.ReportEvent("PASS", "From policy text matches expected text.");

        // === CLICK VIEW FLIGHT (FROM) ===
        WebElement viewFromButton = driver.findElement(By.xpath("(//*[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewFromButton);
        Thread.sleep(1000);
        System.out.println("Clicking 'View Flight' button (FROM) at index " + index);
        log.ReportEvent("INFO", "Clicking 'View Flight' button (FROM) at index " + index);
        viewFromButton.click();

        // === FARE POLICY (FROM VIEW FLIGHT DIALOG) ===
        WebElement farePolicyText = driver.findElement(By.xpath("//*[contains(@class, 'tg-flight-details-dialog')]/parent::*//*[@class='inpolicy tg-policy' and @data-tginpolicy='true']"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", farePolicyText);
        Thread.sleep(500);
        String fareText = farePolicyText.getAttribute("textContent").trim().replaceAll("\\s+", "").toLowerCase();
        System.out.println("Fare policy (FROM) text found: " + fareText);
        if (!fareText.contains(expectedNormalized)) {
            log.ReportEvent("FAIL", "Fare policy (from) mismatch. Expected: " + expectedPolicyText + ", Found: " + fareText);
            screenshots.takeScreenShot1();
            throw new AssertionError("Fare policy (from) mismatch. Expected: " + expectedPolicyText + ", Found: " + fareText);
        }
        log.ReportEvent("PASS", "Fare policy (FROM) text matches expected text.");

        // === CLOSE ===
        WebElement closeBtn1 = driver.findElement(By.xpath("//button[text()='Close']"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", closeBtn1);
        Thread.sleep(500);
        System.out.println("Closing FROM view flight dialog.");
        log.ReportEvent("INFO", "Closing FROM view flight dialog.");
        closeBtn1.click();

        // === CLICK VIEW FLIGHT (TO) ===
        WebElement viewToButton = driver.findElement(By.xpath("(//*[@class='round-trip-to-results']//button[text()='View Flight'])[" + index + "]"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewToButton);
        Thread.sleep(1000);
        System.out.println("Clicking 'View Flight' button (TO) at index " + index);
        log.ReportEvent("INFO", "Clicking 'View Flight' button (TO) at index " + index);
        viewToButton.click();

        // === FARE POLICY (TO VIEW FLIGHT DIALOG) ===
        WebElement toFarePolicyText = driver.findElement(By.xpath("//*[contains(@class, 'tg-flight-details-dialog')]/parent::*//*[@class='inpolicy tg-policy' and @data-tginpolicy='true']"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", toFarePolicyText);
        Thread.sleep(500);
        String toFareText = toFarePolicyText.getAttribute("textContent").trim().replaceAll("\\s+", "").toLowerCase();
        System.out.println("Fare policy (TO) text found: " + toFareText);
        if (!toFareText.contains(expectedNormalized)) {
            log.ReportEvent("FAIL", "Fare policy (to) mismatch. Expected: " + expectedPolicyText + ", Found: " + toFareText);
            screenshots.takeScreenShot1();
            throw new AssertionError("Fare policy (to) mismatch. Expected: " + expectedPolicyText + ", Found: " + toFareText);
        }
        log.ReportEvent("PASS", "Fare policy (TO) text matches expected text.");

        // === CLOSE ===
        WebElement closeBtn2 = driver.findElement(By.xpath("//button[text()='Close']"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", closeBtn2);
        Thread.sleep(500);
        System.out.println("Closing TO view flight dialog.");
        log.ReportEvent("INFO", "Closing TO view flight dialog.");
        closeBtn2.click();
        // Optionally click Continue button if required
        WebElement continueBtn = driver.findElement(By.xpath("//button[text()='Continue']"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", continueBtn);
        Thread.sleep(500);
        System.out.println("Clicking 'Continue' button.");
        log.ReportEvent("INFO", "Clicking 'Continue' button.");
        continueBtn.click();

        System.out.println("Validation completed successfully for policy text: " + expectedPolicyText);
        log.ReportEvent("PASS", "Completed validation successfully for policy text: " + expectedPolicyText);

        // === DEPART BOOKING PAGE POLICY ===
        WebElement departBookingPolicy = driver.findElement(By.xpath("//*[@class='inpolicy tg-policy' and @data-tgdepartinpolicy='true']"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", departBookingPolicy);
        Thread.sleep(500);
        String departBookingText = departBookingPolicy.getAttribute("textContent").trim().replaceAll("\\s+", "").toLowerCase();
        System.out.println("Booking page depart policy text found: " + departBookingText);
        if (!departBookingText.contains(expectedNormalized)) {
            log.ReportEvent("FAIL", "Booking page depart policy mismatch. Expected: " + expectedPolicyText + ", Found: " + departBookingText);
            screenshots.takeScreenShot1();
            throw new AssertionError("Booking page depart policy mismatch. Expected: " + expectedPolicyText + ", Found: " + departBookingText);
        }
        log.ReportEvent("PASS", "Booking page depart policy text matches expected text.");

        // === RETURN BOOKING PAGE POLICY ===
        WebElement returnBookingPolicy = driver.findElement(By.xpath("//*[@class='inpolicy tg-policy' and @data-tgdepartinpolicy='true'] | //div[@class='inpolicy tg-policy' and @data-tgreturninpolicy='true']\r\n"
        		+ ""));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", returnBookingPolicy);
        Thread.sleep(500);
        String returnBookingText = returnBookingPolicy.getAttribute("textContent").trim().replaceAll("\\s+", "").toLowerCase();
        System.out.println("Booking page return policy text found: " + returnBookingText);
        if (!returnBookingText.contains(expectedNormalized)) {
            log.ReportEvent("FAIL", "Booking page return policy mismatch. Expected: " + expectedPolicyText + ", Found: " + returnBookingText);
            screenshots.takeScreenShot1();
            throw new AssertionError("Booking page return policy mismatch. Expected: " + expectedPolicyText + ", Found: " + returnBookingText);
        }
        log.ReportEvent("PASS", "Booking page return policy text matches expected text.");

      
    }
    
    
    public void validateOutOfPolicyFilterRoundTrip(int index, String expectedPolicyText,Log log, ScreenShots screenshots) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Normalize input
        String expectedNormalized = expectedPolicyText.replaceAll("\\s+", "").toLowerCase();
        System.out.println("Starting validation for Out Of policy text: " + expectedPolicyText);
        log.ReportEvent("INFO", "Starting validation for Out Of policy text: " + expectedPolicyText);

        // === FROM POLICY TEXT ===
        WebElement fromPolicyText = driver.findElement(By.xpath("//*[contains(@class, 'round-trip-from-results')]//div[@class='outofpolicy tg-policy' and @data-tgoutpolicy='true']"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", fromPolicyText);
        Thread.sleep(500);
        String fromText = fromPolicyText.getAttribute("textContent").trim().replaceAll("\\s+", "").toLowerCase();
        System.out.println("From policy text found: " + fromText);
        if (!fromText.contains(expectedNormalized)) {
            log.ReportEvent("FAIL", "From result policy mismatch. Expected: " + expectedPolicyText + ", Found: " + fromText);
            screenshots.takeScreenShot1();
            throw new AssertionError("From result policy mismatch. Expected: " + expectedPolicyText + ", Found: " + fromText);
        }
        log.ReportEvent("PASS", "From policy text matches expected text.");

        // === CLICK VIEW FLIGHT (FROM) ===
        WebElement viewFromButton = driver.findElement(By.xpath("(//div[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewFromButton);
        Thread.sleep(1000);
        System.out.println("Clicking 'View Flight' button (FROM) at index " + index);
        log.ReportEvent("INFO", "Clicking 'View Flight' button (FROM) at index " + index);
        viewFromButton.click();

        // === FARE POLICY (FROM VIEW FLIGHT DIALOG) ===
        WebElement farePolicyText = driver.findElement(By.xpath("//*[contains(@class, 'tg-flight-details-dialog')]/parent::div//div[@class='outofpolicy tg-policy' and @data-tgoutpolicy='true']"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", farePolicyText);
        Thread.sleep(500);
        String fareText = farePolicyText.getAttribute("textContent").trim().replaceAll("\\s+", "").toLowerCase();
        System.out.println("Fare policy (FROM) text found: " + fareText);
        if (!fareText.contains(expectedNormalized)) {
            log.ReportEvent("FAIL", "Fare policy (from) mismatch. Expected: " + expectedPolicyText + ", Found: " + fareText);
            screenshots.takeScreenShot1();
            throw new AssertionError("Fare policy (from) mismatch. Expected: " + expectedPolicyText + ", Found: " + fareText);
        }
        log.ReportEvent("PASS", "Fare policy (FROM) text matches expected text.");

        // === CLOSE ===
        WebElement closeBtn1 = driver.findElement(By.xpath("//button[text()='Close']"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", closeBtn1);
        Thread.sleep(500);
        System.out.println("Closing FROM view flight dialog.");
        log.ReportEvent("INFO", "Closing FROM view flight dialog.");
        closeBtn1.click();

        // === CLICK VIEW FLIGHT (TO) ===
        WebElement viewToButton = driver.findElement(By.xpath("(//div[@class='round-trip-to-results']//button[text()='View Flight'])[" + index + "]"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewToButton);
        Thread.sleep(1000);
        System.out.println("Clicking 'View Flight' button (TO) at index " + index);
        log.ReportEvent("INFO", "Clicking 'View Flight' button (TO) at index " + index);
        viewToButton.click();

        // === FARE POLICY (TO VIEW FLIGHT DIALOG) ===
        WebElement toFarePolicyText = driver.findElement(By.xpath("//*[contains(@class, 'tg-flight-details-dialog')]/parent::div//div[@class='outofpolicy tg-policy' and @data-tgoutpolicy='true']"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", toFarePolicyText);
        Thread.sleep(500);
        String toFareText = toFarePolicyText.getAttribute("textContent").trim().replaceAll("\\s+", "").toLowerCase();
        System.out.println("Fare policy (TO) text found: " + toFareText);
        if (!toFareText.contains(expectedNormalized)) {
            log.ReportEvent("FAIL", "Fare policy (to) mismatch. Expected: " + expectedPolicyText + ", Found: " + toFareText);
            screenshots.takeScreenShot1();
            throw new AssertionError("Fare policy (to) mismatch. Expected: " + expectedPolicyText + ", Found: " + toFareText);
        }
        log.ReportEvent("PASS", "Fare policy (TO) text matches expected text.");

        // === CLOSE ===
        WebElement closeBtn2 = driver.findElement(By.xpath("//button[text()='Close']"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", closeBtn2);
        Thread.sleep(500);
        System.out.println("Closing TO view flight dialog.");
        log.ReportEvent("INFO", "Closing TO view flight dialog.");
        closeBtn2.click();
        // Optionally click Continue button if required
        WebElement continueBtn = driver.findElement(By.xpath("//button[text()='Continue']"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", continueBtn);
        Thread.sleep(500);
        System.out.println("Clicking 'Continue' button.");
        log.ReportEvent("INFO", "Clicking 'Continue' button.");
        continueBtn.click();

        System.out.println("Validation completed successfully for policy text: " + expectedPolicyText);
        log.ReportEvent("PASS", "Completed validation successfully for policy text: " + expectedPolicyText);

        // === DEPART BOOKING PAGE POLICY ===
        WebElement departBookingPolicy = driver.findElement(By.xpath("//div[@class='outofpolicy tg-policy' and @data-tgdepartoutpolicy='true'] | //div[@class='outofpolicy tg-policy' and @data-tgreturnoutpolicy='true']"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", departBookingPolicy);
        Thread.sleep(500);
        String departBookingText = departBookingPolicy.getAttribute("textContent").trim().replaceAll("\\s+", "").toLowerCase();
        System.out.println("Booking page depart policy text found: " + departBookingText);
        if (!departBookingText.contains(expectedNormalized)) {
            log.ReportEvent("FAIL", "Booking page depart policy mismatch. Expected: " + expectedPolicyText + ", Found: " + departBookingText);
            screenshots.takeScreenShot1();
            throw new AssertionError("Booking page depart policy mismatch. Expected: " + expectedPolicyText + ", Found: " + departBookingText);
        }
        log.ReportEvent("PASS", "Booking page depart policy text matches expected text.");

        // === RETURN BOOKING PAGE POLICY ===
        WebElement returnBookingPolicy = driver.findElement(By.xpath("//div[@class='outofpolicy tg-policy' and @data-tgdepartoutpolicy='true']"));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", returnBookingPolicy);
        Thread.sleep(500);
        String returnBookingText = returnBookingPolicy.getAttribute("textContent").trim().replaceAll("\\s+", "").toLowerCase();
        System.out.println("Booking page return policy text found: " + returnBookingText);
        if (!returnBookingText.contains(expectedNormalized)) {
            log.ReportEvent("FAIL", "Booking page return policy mismatch. Expected: " + expectedPolicyText + ", Found: " + returnBookingText);
            screenshots.takeScreenShot1();
            throw new AssertionError("Booking page return policy mismatch. Expected: " + expectedPolicyText + ", Found: " + returnBookingText);
        }
        log.ReportEvent("PASS", "Booking page return policy text matches expected text.");

      
    }
    
    //--------------------------------------------------------------------------------
    
  //Method to verify Flights Details On ResultScreen For Local to Local
    public void verifyFlightsDetailsOnResultScreenForLocal(int index, Log log, ScreenShots screenShots) throws InterruptedException {
        Thread.sleep(5000);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Get user-selected locations
            String selectedFromLocation = driver.findElement(By.xpath("//*[contains(@class,' tg-fsorigin')]//*[contains(@class,'tg-select__single-value')]")).getText();
            String selectedToLocation = driver.findElement(By.xpath("//*[contains(@class,'tg-fsdestination')]//*[contains(@class,'tg-select__single-value')]")).getText();

            // Extract airport codes
            String expectedFromCode = extractAirportCode(selectedFromLocation);
            String expectedToCode = extractAirportCode(selectedToLocation);

            // Wait for fromStops element and get text
            WebElement fromStopsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("(//*[contains(@class,' tg-fromstops')])[1]")));

            String fromStops = fromStopsElement.getText().trim();
            if (fromStops.isEmpty()) {
                fromStops = fromStopsElement.getAttribute("innerText").trim();
                Assert.fail();
            }

            System.out.println("From Stops: " + fromStops);

            // Onward flight card and details
            WebElement onwardFlightCard = driver.findElement(By.xpath("//*[contains(@class, 'round-trip-card-from')][" + index + "]"));
            WebElement onwardDepartureTitle = onwardFlightCard.findElement(By.xpath(".//*[contains(@class,'tg-fromdeptime')]"));
            WebElement onwardArrivalTitle = onwardFlightCard.findElement(By.xpath(".//*[contains(@class,' tg-fromarrtime')]"));
            WebElement onwardJourneyTime = onwardFlightCard.findElement(By.xpath(".//*[contains(@class,'tg-fromduration')]"));
            WebElement onwardPrice = onwardFlightCard.findElement(By.xpath(".//*[contains(@class,'tg-fromprice')]"));
            WebElement onwardCarrierInfo = onwardFlightCard.findElement(By.xpath(".//*[@class='carreir-info']"));

            WebElement viewOnwardButton = onwardFlightCard.findElement(By.xpath(".//button[text()='View Flight']"));
            js.executeScript("arguments[0].scrollIntoView(true);", viewOnwardButton);
            js.executeScript("arguments[0].click();", viewOnwardButton);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']")));

            String fromResult, toResult, actualFromCode, actualToCode;

            if (fromStops.toLowerCase().contains("nonstop")) {
                fromResult = driver.findElement(By.xpath("//*[@data-tgfloriginairport]")).getText();
                toResult = driver.findElement(By.xpath("//*[@data-tgfldestinationairport]")).getText();
            } else if (fromStops.toLowerCase().contains("1stops")) {
                fromResult = driver.findElement(By.xpath("//*[@data-tgfloriginairport]")).getText();
                toResult = driver.findElement(By.xpath("(//*[@data-tgfldestinationairport])[last()]")).getText();
            } else if (fromStops.toLowerCase().contains("2stops")) {
                fromResult = driver.findElement(By.xpath("//*[@data-tgfloriginairport]")).getText();
                toResult = driver.findElement(By.xpath("(//*[@data-tgfldestinationairport])[last()]")).getText();
            } else {
                throw new RuntimeException("Unknown stop type in onward flight: " + fromStops);
            }

            actualFromCode = extractAirportCode(fromResult);
            actualToCode = extractAirportCode(toResult);

            Thread.sleep(3000);
            WebElement closeButton1 = driver.findElement(By.xpath("//button[text()='Close']"));
            js.executeScript("arguments[0].click();", closeButton1);
            Thread.sleep(3000);

            // Return flight
            WebElement returnFlightCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(@class,'round-trip-card-to')][" + index + "]")));
            WebElement returnDepartureTitle = returnFlightCard.findElement(By.xpath(".//*[contains(@class,'tg-todeptime')]"));
            WebElement returnArrivalTitle = returnFlightCard.findElement(By.xpath(".//*[contains(@class,' tg-toarrtime')]"));
            WebElement returnJourneyTime = returnFlightCard.findElement(By.xpath(".//*[contains(@class,'tg-toduration')]"));
            WebElement returnPrice = returnFlightCard.findElement(By.xpath(".//*[contains(@class,'tg-toprice')]"));
            WebElement returnCarrierInfo = returnFlightCard.findElement(By.xpath(".//*[@class='carreir-info']"));

            WebElement viewReturnButton = returnFlightCard.findElement(By.xpath(".//button[text()='View Flight']"));
            js.executeScript("arguments[0].scrollIntoView(true);", viewReturnButton);
            js.executeScript("arguments[0].click();", viewReturnButton);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']")));

            String returnFromResult, returnToResult, returnFromCode, returnToCode;
            String returnStops = driver.findElement(By.xpath("(//*[contains(@class,'tg-tostops')])[1]")).getText();
            if (returnStops.isEmpty()) {
                returnStops = driver.findElement(By.xpath("(//*[contains(@class,'tg-tostops')])[1]")).getAttribute("innerText").trim();
            }
            System.out.println("Return Stops: " + returnStops);

            if (returnStops.toLowerCase().contains("nonstop")) {
                returnFromResult = driver.findElement(By.xpath("//*[@data-tgfloriginairport]")).getText();
                returnToResult = driver.findElement(By.xpath("//*[@data-tgfldestinationairport]")).getText();
            } else if (returnStops.toLowerCase().contains("1stops")) {
                returnFromResult = driver.findElement(By.xpath("//*[@data-tgfloriginairport]")).getText();
                returnToResult = driver.findElement(By.xpath("(//*[@data-tgfldestinationairport])[last()]")).getText();
            } else if (returnStops.toLowerCase().contains("2stops")) {
                returnFromResult = driver.findElement(By.xpath("//*[@data-tgfloriginairport]")).getText();
                returnToResult = driver.findElement(By.xpath("(//*[@data-tgfldestinationairport])[last()]")).getText();
            } else {
                throw new RuntimeException("Unknown stop type in return flight: " + returnStops);
            }

            returnFromCode = extractAirportCode(returnFromResult);
            returnToCode = extractAirportCode(returnToResult);

            Thread.sleep(3000);
            WebElement closeButton2 = driver.findElement(By.xpath("//button[text()='Close']"));
            js.executeScript("arguments[0].click();", closeButton2);

            // Validate visibility and correctness
            boolean onwardVisible = isElementVisible(js, onwardFlightCard, onwardDepartureTitle, onwardArrivalTitle, onwardJourneyTime, onwardPrice, onwardCarrierInfo);
            boolean returnVisible = isElementVisible(js, returnFlightCard, returnDepartureTitle, returnArrivalTitle, returnJourneyTime, returnPrice, returnCarrierInfo);

            if (onwardVisible && returnVisible) {
                boolean locationMatch = actualFromCode.equals(expectedFromCode) &&
                        actualToCode.equals(expectedToCode) &&
                        returnFromCode.equals(expectedToCode) &&
                        returnToCode.equals(expectedFromCode);

                if (locationMatch) {
                    log.ReportEvent("PASS", "Flight details visible and correctly displayed.\n" +
                            "Expected FROM: " + expectedFromCode + " (" + selectedFromLocation + "), TO: " + expectedToCode + " (" + selectedToLocation + ")\n" +
                            "Actual FROM: " + actualFromCode + " (" + fromResult + "), TO: " + actualToCode + " (" + toResult + ")\n" +
                            "Return FROM: " + returnFromCode + " (" + returnFromResult + "), TO: " + returnToCode + " (" + returnToResult + ")");
                } else {
                    log.ReportEvent("FAIL", "Mismatch in flight locations.\n" +
                            "Expected FROM: " + expectedFromCode + " (" + selectedFromLocation + "), TO: " + expectedToCode + " (" + selectedToLocation + ")\n" +
                            "Actual FROM: " + actualFromCode + " (" + fromResult + "), TO: " + actualToCode + " (" + toResult + ")\n" +
                            "Return FROM: " + returnFromCode + " (" + returnFromResult + "), TO: " + returnToCode + " (" + returnToResult + ")");
                    screenShots.takeScreenShot1();
                    Assert.fail("Flight details mismatch.");
                }
            } else {
                log.ReportEvent("FAIL", "One or more flight detail elements are not visible.");
                screenShots.takeScreenShot1();
                Assert.fail("Flight detail elements missing.");
            }

        } catch (Exception e) {
            log.ReportEvent("FAIL", "Exception occurred while verifying flight details: " + e.getMessage());
            screenShots.takeScreenShot1();
            Assert.fail("Exception: " + e.getMessage());
        }
    }

// Helper method to extract airport code from location string
private String extractAirportCode(String locationText) {
    Matcher matcher = Pattern.compile("\\((.*?)\\)").matcher(locationText);
    return matcher.find() ? matcher.group(1) : locationText;
}

// Helper method to check element visibility
private boolean isElementVisible(JavascriptExecutor js, WebElement... elements) {
    for (WebElement element : elements) {
        boolean visible = (Boolean) js.executeScript(
                "return arguments[0]?.offsetWidth > 0 && arguments[0]?.offsetHeight > 0;", element);
        if (!visible) return false;
    }
    return true;
}
 
//--------------------------------------------------------------------------------------------

//Method to verify Price Range Values On Result Screen
public void verifyPriceRangeValuesOnResultScreen(Log Log, ScreenShots ScreenShots) {
    try {
        Thread.sleep(5000);

        String minPriceValue = driver.findElement(By.xpath("//input[@data-index='0']")).getAttribute("aria-valuenow");
        String maxPriceValue = driver.findElement(By.xpath("//input[@data-index='1']")).getAttribute("aria-valuenow");

        List<WebElement> fromPrices = driver.findElements(By.cssSelector(".tg-fromprice"));
        String priceRangeFrom = fromPrices.get(0).getText(); // gets the first match

        List<WebElement> toPrices = driver.findElements(By.cssSelector(".tg-toprice"));
        String priceRangeTo = toPrices.get(0).getText(); // gets the first match

        System.out.println("From Price: " + priceRangeFrom);
        System.out.println("To Price: " + priceRangeTo);


        int min = Integer.parseInt(minPriceValue);
        int max = Integer.parseInt(maxPriceValue);
        int priceFrom = extractPrice1(priceRangeFrom, "FROM");
        int priceTo = extractPrice1(priceRangeTo, "TO");

        System.out.println("Slider Min: " + min);
        System.out.println("Slider Max: " + max);
        System.out.println("Flight Price FROM (first): " + priceFrom);
        System.out.println("Flight Price TO (first): " + priceTo);

        List<WebElement> FromFlights = driver.findElements(By.cssSelector(".tg-fromprice"));
        List<WebElement> ToFlights = driver.findElements(By.cssSelector(".tg-toprice"));

        // ✅ Loop through all FROM prices and print validation result
        for (int i = 0; i < FromFlights.size(); i++) {
            String priceText = FromFlights.get(i).getText();
            int price = extractPrice1(priceText, "FROM");

            boolean isWithinRange = price >= min && price <= max;

            System.out.println("Flight " + (i + 1) + " FROM price: ₹" + price + " — " + (isWithinRange ? "WITHIN" : "OUT OF") + " range (" + min + " - " + max + ")");
        }

        // ✅ Loop through all TO prices and print validation result
        for (int i = 0; i < ToFlights.size(); i++) {
            String priceText = ToFlights.get(i).getText();
            int price = extractPrice1(priceText, "TO");

            boolean isWithinRange = price >= min && price <= max;

            System.out.println("Flight " + (i + 1) + " TO price: ₹" + price + " — " + (isWithinRange ? "WITHIN" : "OUT OF") + " range (" + min + " - " + max + ")");
        }

        // ✅ Validate and log the first FROM and TO prices
        boolean fromValid = priceFrom >= min && priceFrom <= max;
        boolean toValid = priceTo >= min && priceTo <= max;

        if (fromValid) {
            Log.ReportEvent("PASS", "FROM price ₹" + priceFrom + " is within range ₹" + min + " - ₹" + max);
        } else {
            Log.ReportEvent("FAIL", "FROM price ₹" + priceFrom + " is NOT within range ₹" + min + " - ₹" + max);
        }

        if (toValid) {
            Log.ReportEvent("PASS", "TO price ₹" + priceTo + " is within range ₹" + min + " - ₹" + max);
        } else {
            Log.ReportEvent("FAIL", "TO price ₹" + priceTo + " is NOT within range ₹" + min + " - ₹" + max);
        }

        if (!fromValid || !toValid) {
            Assert.fail("Flight price is not within selected slider range.");
        }

        ScreenShots.takeScreenShot1();

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception during price validation: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
        Assert.fail("Exception occurred during price validation");
    }
}



// ✅ Move this outside the method, anywhere inside the class
private int extractPrice1(String text, String label) throws Exception {
Pattern pattern = Pattern.compile("(₹|INR)\\s?\\d{1,3}(,\\d{3})*");
Matcher matcher = pattern.matcher(text);
if (matcher.find()) {
    String rawPrice = matcher.group();
    return Integer.parseInt(rawPrice.replaceAll("[^0-9]", ""));
} else {
    throw new Exception("Could not extract " + label + " price from text: " + text);
}
}
//Method to adjust Maximum Slider To Value
public  void adjustMaximumSliderToValue(WebDriver driver, double targetMaxValue) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the maximum slider input is visible
        WebElement maxSliderInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@data-index='1']")));

        // Get min and max values
        double minValue = Double.parseDouble(maxSliderInput.getAttribute("aria-valuemin"));
        double maxValue = Double.parseDouble(maxSliderInput.getAttribute("aria-valuemax"));

        System.out.println("Min Value: " + minValue + ", Max Value: " + maxValue);

        // Clamp target value within range
        double clampedValue = Math.max(minValue, Math.min(maxValue, targetMaxValue));
        double percentage = (clampedValue - minValue) / (maxValue - minValue);

        System.out.println("Target Value: " + clampedValue + ", Percentage: " + percentage);

        // Get slider track and width
        WebElement sliderTrack = driver.findElement(By.xpath("//span[contains(@class, 'MuiSlider-track')]"));
        int trackWidth = sliderTrack.getSize().getWidth();

        // Calculate target offset
        int targetOffset = (int) (trackWidth * percentage);
        System.out.println("Track Width: " + trackWidth + ", Target Offset: " + targetOffset);

        // Get thumb handle and track's X position
        WebElement thumbHandle = driver.findElement(By.xpath("//input[@data-index='1']"));
        int thumbX = thumbHandle.getLocation().getX();
        int trackX = sliderTrack.getLocation().getX();
        int currentOffset = thumbX - trackX;

        int moveBy = targetOffset - currentOffset;
        System.out.println("Current Offset: " + currentOffset + ", Move By: " + moveBy);

        // Move the slider thumb
        Actions action = new Actions(driver);
        action.moveToElement(thumbHandle)
        .pause(Duration.ofMillis(300))
        .clickAndHold()
        .moveByOffset(moveBy, 0)
        .pause(Duration.ofMillis(200))
        .release()
        .perform();

        // Wait for UI to update (using WebDriverWait)
        wait.until(ExpectedConditions.attributeToBe(maxSliderInput, "aria-valuenow", String.valueOf(clampedValue)));

        // Log updated value
        String newMaxValue = maxSliderInput.getAttribute("aria-valuenow");
        System.out.println("Updated Max Value: " + newMaxValue);

    } catch (Exception e) {
        System.err.println("❌ Failed to move max slider: " + e.getMessage());
        e.printStackTrace();
    }
}

    //----------------------------------------------------------------------------

    
    //---------------------------------------------
public void closePopup() {
    try {
        WebElement closeBtn = driver.findElement(By.xpath("//button[text()='Close']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
    } catch (Exception e) {
        // Log or ignore if close button not found; popup might already be closed
    }
}

    
//----------------------------------------------------------------------------------------------------


//method to close the button on result screen
 public void closeButtononresultpage() {
     driver.findElement(By.xpath("//button[text()='Close']")).click();
 }
 
//method to click clear filters roundtrip
 public void clickclearfiltersRoundtrip() {
     
     JavascriptExecutor js = (JavascriptExecutor) driver;
     js.executeScript("window.scrollTo(0, 0);");

      driver.findElement(By.xpath("//button[text()='Clear Filters']")).click();
  }

   //====================================================================================
 
//Method to click on Duration ascending filter From div
 public void DurationAscendingFilter()
 {
     driver.findElement(By.xpath("(//small[text()='DURATION'])[1]")).click();
 }
 
 
 
//method to check whether Duration time of all flights in ascending order From div
 public void TimeOrderCheckInAscendingForDuration(Log Log,ScreenShots ScreenShots) {
     try {
         List<WebElement> Durations = driver.findElements(By.xpath("//div[@class='round-trip-from-results']//div[@class='frc-journeytime']"));

         List<String> timeStrings = new ArrayList<>();
         for (WebElement Duration : Durations) {
             String h3 = Duration.getText();
             timeStrings.add(h3.trim());
         }

         List<Integer> timesInMinutes = new ArrayList<>();
         for (String time : timeStrings) {
             int totalMinutes = 0;
             time = time.toLowerCase();

             if (time.contains("h")) {
                 String hoursPart = time.substring(0, time.indexOf("h")).trim();
                 totalMinutes += Integer.parseInt(hoursPart) * 60;
                 time = time.substring(time.indexOf("h") + 1).trim();
             }

             if (time.contains("m")) {
                 String minutesPart = time.substring(0, time.indexOf("m")).trim();
                 totalMinutes += Integer.parseInt(minutesPart);
             }

             timesInMinutes.add(totalMinutes);
             System.out.println(timesInMinutes);
         }

         boolean isDescending = true;
         for (int i = 0; i < timesInMinutes.size() - 1; i++) {
             if (timesInMinutes.get(i) > timesInMinutes.get(i + 1)) {
                 isDescending = false;
                 break;
             }
         }

         if (isDescending) {
             System.out.println("Flights are displaying in Ascending order");
             Log.ReportEvent("PASS", "Flights are displaying in Ascending order");
         } else {
             System.out.println("Flights are Not displaying in Ascending order");
             Log.ReportEvent("FAIL", "Flights are Not displaying in Ascending order");
         }

     } catch (Exception e) {
         e.printStackTrace();
     }
}
 
//Method to click on Duration desending filter From div
 public void DurationDescendingFilter()
 {

     driver.findElement(By.xpath("(//small[text()='DURATION'])[1]")).click();
 }
 
//Method to check whether flights result are appearing in descending order for duration From div
 public void TimeOrderCheckInDescendingForDuration(Log Log,ScreenShots ScreenShots) {
     try {
         List<WebElement> Durations = driver.findElements(By.xpath("//div[@class='round-trip-from-results']//div[@class='frc-journeytime']"));

         List<String> timeStrings = new ArrayList<>();
         for (WebElement Duration : Durations) {
             String h3 = Duration.getText();
             timeStrings.add(h3.trim());
         }

         List<Integer> timesInMinutes = new ArrayList<>();
         for (String time : timeStrings) {
             int totalMinutes = 0;
             time = time.toLowerCase();

             if (time.contains("h")) {
                 String hoursPart = time.substring(0, time.indexOf("h")).trim();
                 totalMinutes += Integer.parseInt(hoursPart) * 60;
                 time = time.substring(time.indexOf("h") + 1).trim();
             }

             if (time.contains("m")) {
                 String minutesPart = time.substring(0, time.indexOf("m")).trim();
                 totalMinutes += Integer.parseInt(minutesPart);
             }

             timesInMinutes.add(totalMinutes);
             System.out.println(timesInMinutes);
         }

         boolean isDescending = true;
         for (int i = 0; i < timesInMinutes.size() - 1; i++) {
             if (timesInMinutes.get(i) < timesInMinutes.get(i + 1)) {
                 isDescending = false;
                 break;
             }
         }

         if (isDescending) {
             System.out.println("Flights are displaying in Descending order");
             Log.ReportEvent("PASS", "Flights are displaying in Descending order");
         } else {
             System.out.println("Flights are Not displaying in Descending order");
             Log.ReportEvent("FAIL", "Flights are Not displaying in Descending order");
         }

     } catch (Exception e) {
         e.printStackTrace();
     }
}
 

//Method to click on Duration Ascending filter To div
 public void DurationAscendingFilterForToDiv()
 {
     driver.findElement(By.xpath("(//small[text()='DURATION'])[2]")).click();
 }

//method to check whether Duration time of all flights in ascending order To div
 public void TimeOrderCheckInAscendingForDurationForToDiv(Log Log,ScreenShots ScreenShots) {
     try {
         List<WebElement> Durations = driver.findElements(By.xpath("//div[@class='round-trip-from-results']//div[@class='frc-journeytime']"));

         List<String> timeStrings = new ArrayList<>();
         for (WebElement Duration : Durations) {
             String h3 = Duration.getText();
             timeStrings.add(h3.trim());
         }

         List<Integer> timesInMinutes = new ArrayList<>();
         for (String time : timeStrings) {
             int totalMinutes = 0;
             time = time.toLowerCase();

             if (time.contains("h")) {
                 String hoursPart = time.substring(0, time.indexOf("h")).trim();
                 totalMinutes += Integer.parseInt(hoursPart) * 60;
                 time = time.substring(time.indexOf("h") + 1).trim();
             }

             if (time.contains("m")) {
                 String minutesPart = time.substring(0, time.indexOf("m")).trim();
                 totalMinutes += Integer.parseInt(minutesPart);
             }

             timesInMinutes.add(totalMinutes);
             System.out.println(timesInMinutes);
         }

         boolean isDescending = true;
         for (int i = 0; i < timesInMinutes.size() - 1; i++) {
             if (timesInMinutes.get(i) < timesInMinutes.get(i + 1)) {
                 isDescending = false;
                 break;
             }
         }

         if (isDescending) {
             System.out.println("Flights are displaying in Ascending order");
             Log.ReportEvent("PASS", "Flights are displaying in Ascending order");
         } else {
             System.out.println("Flights are Not displaying in Ascending order");
             Log.ReportEvent("FAIL", "Flights are Not displaying in Ascending order");
         }

     } catch (Exception e) {
         e.printStackTrace();
     }
}
//Method to click on Duration desending filter To div
 public void DurationDescendingFilterForToDiv()
 {
     driver.findElement(By.xpath("(//small[text()='DURATION'])[2]")).click();
 }

//Method to check whether flights result are appearing in descending order for depart To div
 public void TimeOrderCheckInDescendingForDurationForToDiv(Log Log, ScreenShots ScreenShots) {
     try {
         List<WebElement> Durations = driver.findElements(By.xpath("//div[@class='round-trip-from-results']//div[@class='frc-journeytime']"));

         List<String> timeStrings = new ArrayList<>();
         for (WebElement Duration : Durations) {
             String h3 = Duration.getText();
             timeStrings.add(h3.trim());
         }

         List<Integer> timesInMinutes = new ArrayList<>();
         for (String time : timeStrings) {
             int totalMinutes = 0;
             time = time.toLowerCase();

             if (time.contains("h")) {
                 String hoursPart = time.substring(0, time.indexOf("h")).trim();
                 totalMinutes += Integer.parseInt(hoursPart) * 60;
                 time = time.substring(time.indexOf("h") + 1).trim();
             }

             if (time.contains("m")) {
                 String minutesPart = time.substring(0, time.indexOf("m")).trim();
                 totalMinutes += Integer.parseInt(minutesPart);
             }

             timesInMinutes.add(totalMinutes);
             System.out.println(timesInMinutes);
         }

         boolean isDescending = true;
         for (int i = 0; i < timesInMinutes.size() - 1; i++) {
             if (timesInMinutes.get(i) < timesInMinutes.get(i + 1)) {
                 isDescending = false;
                 break;
             }
         }

         if (isDescending) {
             System.out.println("Flights are displaying in Descending order");
             Log.ReportEvent("PASS", "Flights are displaying in Descending order");
         } else {
             System.out.println("Flights are Not displaying in Descending order");
             Log.ReportEvent("FAIL", "Flights are Not displaying in Descending order");
         }

     } catch (Exception e) {
         e.printStackTrace();
     }
 }
     
   //----------------------------------------------------------------------------------------------
 
 //Method to validate airlines for roundtrip
 
// public void validateDepartAirlines(int index) throws InterruptedException {
//	  JavascriptExecutor js = (JavascriptExecutor) driver;
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//	    // Step 1: Click "View Flight" button
//	    String xpathExpression = "(//div[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]";
//	    WebElement viewFlightBtn = driver.findElement(By.xpath(xpathExpression));
//	    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewFlightBtn);
//	    Thread.sleep(1000);
//	    viewFlightBtn.click();
//	    Thread.sleep(3000); // Wait for fare options to appear
//	    
//	    //get airlines text from after clicking on view flight button 
//	    
//	    WebElement DepartairlinesName = driver.findElement(By.xpath("//*[contains(@class,' tg-fromflightcarrier')]"));
//	    DepartairlinesName.getText();
//	    
//	    //click on close button
//	   WebElement closeBtn = driver.findElement(By.xpath("//*[contains(@class,' tg-fromviewclose-btn')]"));
//	   closeBtn.click();
//	    
//	   //get airlines text to after clicking on view flight button 
//	   
//	//   Click "View Flight" button
//	    String xpathExpression1 = "(//div[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]";
//	    WebElement viewFlightBtnTo = driver.findElement(By.xpath(xpathExpression1));
//	    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewFlightBtnTo);
//	    Thread.sleep(1000);
//	    viewFlightBtnTo.click();
//	    Thread.sleep(3000); 
//	   
//	    WebElement ReturnairlinesName = driver.findElement(By.xpath("//*[contains(@class,' tg-toflightcarrier')]"));
//	    ReturnairlinesName.getText();
//	    
//	    //click on close button
//	   WebElement closeBtn1 = driver.findElement(By.xpath("//*[contains(@class,' tg-fromviewclose-btn')]"));
//	   closeBtn1.click();
//	    
//	   //compare the text
//	   
//	   
//	   
//	   
//	   
//	   //click on continue button
//       WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Continue']")));
//       continueBtn.click();
//	   
//       //validation for booking page
//       
//       WebElement bookingPageDepart = driver.findElement(By.className("tg-fbdepartcarriername"));
//       bookingPageDepart.getText();
//       
//       WebElement bookingPageReturn = driver.findElement(By.className("tg-fbreturncarriername"));
//       bookingPageReturn.getText();
//	    
//       //Compare 
//       
// }
 
 
	//----------------------------------------------------------------------------------------------
 public void validateDepartAirlines(int index, String airlineNames, Log Log, ScreenShots ScreenShots) {
	    try {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(95));

	        // Convert comma-separated airlines to list
	        String[] airlineArray = airlineNames.split(",");
	        List<String> userAirlines = new ArrayList<>();
	        for (String a : airlineArray) {
	            if (!a.trim().isEmpty()) {
	                userAirlines.add(a.trim());
	            }
	        }

	        List<String> selectedAirlines = new ArrayList<>();
	        boolean anyAirlineClicked = false;

	        // Try to click user-specified airlines
	        for (String airline : userAirlines) {
	            try {
	                WebElement checkbox = driver.findElement(By.xpath(
	                    "//*[text()='AIRLINES']//parent::div//span[text()='" + airline + "']//parent::div/parent::li//input"));
	                if (!checkbox.isSelected()) {
	                    checkbox.click();
	                    Log.ReportEvent("PASS", "Clicked airline checkbox: " + airline);
	                }
	                selectedAirlines.add(airline);
	                anyAirlineClicked = true;
	                break; // only need to click one
	            } catch (NoSuchElementException e) {
	                // Airline not found, continue to next
	            }
	        }

	        // Fallback: click first available airline if none of the user-passed airlines found
	        if (!anyAirlineClicked) {
	            try {
	                WebElement firstCheckbox = driver.findElement(By.xpath(
	                    "(//*[text()='AIRLINES']//parent::div//li//input)[1]"));
	                WebElement firstLabel = driver.findElement(By.xpath(
	                    "(//*[text()='AIRLINES']//parent::div//li//span)[1]"));
	                String fallbackAirline = firstLabel.getText().trim();

	                if (!firstCheckbox.isSelected()) {
	                    firstCheckbox.click();
	                }

	                selectedAirlines.clear();
	                selectedAirlines.add(fallbackAirline);
	                Log.ReportEvent("INFO", "Fallback: Clicked first available airline: " + fallbackAirline);
	            } catch (Exception e) {
	                Log.ReportEvent("FAIL", "Fallback airline selection failed: " + e.getMessage());
	                ScreenShots.takeScreenShot1();
	                return;
	            }
	        }

	        Log.ReportEvent("DEBUG", "Selected airlines to validate against: " + selectedAirlines);

	        Thread.sleep(2000); // Wait for page refresh

	        // Step 2: Click View Flight button (Departure)
	        WebElement viewFlightBtn = driver.findElement(By.xpath(
	            "(//*[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]"));
	        js.executeScript("arguments[0].scrollIntoView(true);", viewFlightBtn);
	        Thread.sleep(1000);
	        try {
	            viewFlightBtn.click();
	        } catch (Exception e) {
	            js.executeScript("arguments[0].click();", viewFlightBtn);
	        }
	        Thread.sleep(2000);

	        // Step 3: Validate Departure Airline
	        WebElement departElem = driver.findElement(By.xpath("//*[contains(@class,'tg-fromflightcarrier')]"));
	        String departName = extractAirlineName(departElem.getText().trim());

	        if (selectedAirlines.contains(departName)) {
	            Log.ReportEvent("PASS", "Departure airline matched: " + departName);
	        } else {
	            Log.ReportEvent("FAIL", "Departure airline mismatch. Expected one of: " + selectedAirlines + ", found: " + departName);
	            ScreenShots.takeScreenShot1();
	            return;
	        }

	        // Step 4: Close departure popup
	        driver.findElement(By.xpath("//*[contains(@class,'tg-fromviewclose-btn')]")).click();
	        Thread.sleep(1000);

	        // Step 5: Click View Flight button (Return)
	        WebElement viewFlightBtnReturn = driver.findElement(By.xpath(
	            "(//*[@class='round-trip-to-results']//button[text()='View Flight'])[" + index + "]"));
	        js.executeScript("arguments[0].scrollIntoView(true);", viewFlightBtnReturn);
	        Thread.sleep(1000);
	        try {
	            viewFlightBtnReturn.click();
	        } catch (Exception e) {
	            js.executeScript("arguments[0].click();", viewFlightBtnReturn);
	        }
	        Thread.sleep(2000);

	        // Step 6: Validate Return Airline
	        WebElement returnElem = driver.findElement(By.xpath("//*[contains(@class,'tg-toflightcarrier')]"));
	        String returnName = extractAirlineName(returnElem.getText().trim());

	        if (selectedAirlines.contains(returnName)) {
	            Log.ReportEvent("PASS", "Return airline matched: " + returnName);
	        } else {
	            Log.ReportEvent("FAIL", "Return airline mismatch. Expected one of: " + selectedAirlines + ", found: " + returnName);
	            ScreenShots.takeScreenShot1();
	            return;
	        }

	        // Step 7: Close return popup
	        driver.findElement(By.xpath("//*[contains(@class,'tg-toviewclose-btn')]")).click();
	        Thread.sleep(1000);

	        // Step 8: Continue to booking
	        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Continue']")));
	        continueBtn.click();

	        // Step 9: Validate booking page airline names
	        WebElement bookingDepart = driver.findElement(By.className("tg-fbdepartcarriername"));
	        js.executeScript("arguments[0].scrollIntoView(true);", bookingDepart);
	        String bookingDepartName = extractAirlineName(bookingDepart.getText().trim());

	        if (selectedAirlines.contains(bookingDepartName)) {
	            Log.ReportEvent("PASS", "Booking page departure airline matched: " + bookingDepartName);
	        } else {
	            Log.ReportEvent("FAIL", "Booking page departure airline mismatch. Expected one of: " + selectedAirlines + ", found: " + bookingDepartName);
	            ScreenShots.takeScreenShot1();
	            return;
	        }

	        WebElement bookingReturn = driver.findElement(By.className("tg-fbreturncarriername"));
	        js.executeScript("arguments[0].scrollIntoView(true);", bookingReturn);
	        String bookingReturnName = extractAirlineName(bookingReturn.getText().trim());

	        if (selectedAirlines.contains(bookingReturnName)) {
	            Log.ReportEvent("PASS", "Booking page return airline matched: " + bookingReturnName);
	        } else {
	            Log.ReportEvent("FAIL", "Booking page return airline mismatch. Expected one of: " + selectedAirlines + ", found: " + bookingReturnName);
	            ScreenShots.takeScreenShot1();
	            return;
	        }

	        Log.ReportEvent("PASS", "All airline validations passed: " + selectedAirlines);

	    } catch (Exception e) {
	        Log.ReportEvent("FAIL", "Exception during validation: " + e.getMessage());
	        ScreenShots.takeScreenShot1();
	    }
	}

	private String extractAirlineName(String fullName) {
	    if (fullName.contains(" -")) {
	        return fullName.split(" -")[0].trim();
	    }
	    return fullName.trim();
	}

//----------------------------------------------------------------------------------------------------------	
	public void ArriveAscendingFilter()
    {
        driver.findElement(By.xpath("(//small[text()='ARRIVE'])[1]")).click();
    }
//method to check whether arrival time of all flights in ascending order From div
    public void TimeOrderCheckInAscendingForArrival(Log Log,ScreenShots ScreenShots) {


        try {
Thread.sleep(3000);
            // Find all matching elements
            List<WebElement> ArrivalTime = driver.findElements(By.xpath("//*[contains(normalize-space(@class), 'tg-fromarrtime')]"));

            // Extract time strings from h3 elements inside each div
            List<String> timeStrings = new ArrayList<>();
            for (WebElement ArrivalTime1 : ArrivalTime) {
                String h3 = ArrivalTime1.getText();
                timeStrings.add(h3.trim());
                System.out.println(timeStrings);
            }

            // Convert time strings to minutes
            List<Integer> timesInMinutes = new ArrayList<>();
            for (String time : timeStrings) {
                String[] parts = time.split(":");
                int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
                timesInMinutes.add(minutes);
                System.out.println(timesInMinutes);
            }

            // Check if times are in ascending order
            boolean isAscending = true;
            for (int i = 0; i < timesInMinutes.size() - 1; i++) {
                if (timesInMinutes.get(i) > timesInMinutes.get(i + 1)) {
                    isAscending = false;
                    break;
                }
            }
            if(isAscending)
            {
                System.out.println("Flights are displaying in ascending order");
                Log.ReportEvent("PASS", "Flights are displaying in Ascending order");


            }
            else
            {
                System.out.println("Flights are Not displaying in ascending order");
                Log.ReportEvent("FAIL", "Flights are Not displaying in ascending order");

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
//Method to click on Duration desending filter From div
    public void ArriveDescendingFilter()
    {

        driver.findElement(By.xpath("(//small[text()='ARRIVE'])[1]")).click();
    }
//Method to check whether flights result are appearing in descending order for depart
    public void TimeOrderCheckInDescendingForArrival(Log Log,ScreenShots ScreenShots) {


        try {

            // Find all matching elements
            List<WebElement> ArrivalTime = driver.findElements(By.xpath("//*[contains(normalize-space(@class), 'tg-fromarrtime')]"));

            // Extract time strings from h3 elements inside each div
            List<String> timeStrings = new ArrayList<>();
            for (WebElement ArrivalTime1 : ArrivalTime) {
                String h3 = ArrivalTime1.getText();
                timeStrings.add(h3.trim());
                System.out.println(timeStrings);
            }

            // Convert time strings to minutes
            List<Integer> timesInMinutes = new ArrayList<>();
            for (String time : timeStrings) {
                String[] parts = time.split(":");
                int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
                timesInMinutes.add(minutes);
            }

            // Check if times are in ascending order
            boolean isDescending = true;
            for (int i = 0; i < timesInMinutes.size() - 1; i++) {
                if (timesInMinutes.get(i) < timesInMinutes.get(i + 1)) {
                    isDescending = false;
                    break;
                }
            }
            if(isDescending)
            {
                System.out.println("Flights are displaying in Descending order");
                Log.ReportEvent("PASS", "Flights are displaying in Descending order");

            }
            else
            {
                System.out.println("Flights are Not displaying in Descending order");
                Log.ReportEvent("FAIL", "Flights are Not displaying in Descending order");

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
//Method to click on Arrive ascending filter To div
    public void ArriveAscendingFilterToDiv()
    {
        driver.findElement(By.xpath("(//small[text()='ARRIVE'])[2]")).click();
    }
public void TimeOrderCheckInAscendingForArrivalToDiv(Log Log,ScreenShots ScreenShots) {


        try {

            // Find all matching elements
            List<WebElement> divs = driver.findElements(By.xpath("//*[contains(normalize-space(@class), 'tg-toarrtime')]"));

            // Extract time strings from h3 elements inside each div
            List<String> timeStrings = new ArrayList<>();
            for (WebElement div : divs) {
                String h3 = div.getText();
                timeStrings.add(h3.trim());
                System.out.println(timeStrings);
            }

            // Convert time strings to minutes
            List<Integer> timesInMinutes = new ArrayList<>();
            for (String time : timeStrings) {
                String[] parts = time.split(":");
                int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
                timesInMinutes.add(minutes);
                System.out.println(timesInMinutes);
            }

            // Check if times are in ascending order
            boolean isAscending = true;
            for (int i = 0; i < timesInMinutes.size() - 1; i++) {
                if (timesInMinutes.get(i) > timesInMinutes.get(i + 1)) {
                    isAscending = false;
                    break;
                }
            }
            if(isAscending=true)
            {
                System.out.println("Flights are displaying in ascending order");

            }
            else
            {
                System.out.println("Flights are Not displaying in ascending order");
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
//Method to click on Duration desending filter To div
    public void ArriveDescendingFilterToDiv()
    {

        driver.findElement(By.xpath("(//small[text()='ARRIVE'])[2]")).click();
    }
public void TimeOrderCheckInDescendingForArrivalToDiv(Log Log,ScreenShots ScreenShots) {


        try {

            // Find all matching elements
            List<WebElement> divs = driver.findElements(By.xpath("//div[@class='round-trip-to-results']//h3[@data-tgarrtime]"));

            // Extract time strings from h3 elements inside each div
            List<String> timeStrings = new ArrayList<>();
            for (WebElement div : divs) {
                String h3 = div.getText();
                timeStrings.add(h3.trim());
                System.out.println(timeStrings);
            }

            // Convert time strings to minutes
            List<Integer> timesInMinutes = new ArrayList<>();
            for (String time : timeStrings) {
                String[] parts = time.split(":");
                int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
                timesInMinutes.add(minutes);
                System.out.println(timesInMinutes);
            }

            // Check if times are in ascending order
            boolean isDescending = true;
            for (int i = 0; i < timesInMinutes.size() - 1; i++) {
                if (timesInMinutes.get(i) < timesInMinutes.get(i + 1)) {
                    isDescending = false;
                    break;
                }
            }
            if(isDescending=true)
            {
                System.out.println("Flights are displaying in Descending order");

            }
            else
            {
                System.out.println("Flights are Not displaying in Descending order");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

//method to validate Price Filter From Div
    public void PriceOrderCheckInAscending(Log Log,ScreenShots ScreenShots) {
        try {
            // Find price elements using more robust XPath
            List<WebElement> priceElements = driver.findElements(By.xpath(
                    "//*[contains(normalize-space(@class), 'tg-fromprice')]"
                    ));
            System.out.println(priceElements.size());
            // Extract and clean price strings
            List<Integer> prices = new ArrayList<>();
            for (WebElement priceElement : priceElements) {
                String rawPrice = priceElement.getText().trim();
                System.out.println(rawPrice);

                // Remove currency symbols and commas
                String cleanPrice = rawPrice
                        .replace("â‚¹", "")
                        .replace("₹", "")        

                        .replace(",", "")
                        .trim();
System.out.println(cleanPrice);
                try {
                    int priceValue = Integer.parseInt(cleanPrice);
                    System.out.println(priceValue);
                    prices.add(priceValue);
                    System.out.println("Cleaned price: " + priceValue);
                } catch (NumberFormatException e) {
                    System.err.println("Failed to parse: " + rawPrice);
                }
            }

            // Check descending order
            boolean isAscending = true;
            for (int i = 0; i < prices.size() - 1; i++) {
                if (prices.get(i) > prices.get(i + 1)) {
                    isAscending = false;
                    break;
                }
            }
            if(isAscending)
            {
                System.out.println("Prices are getting displayed in Ascending order");
                Log.ReportEvent("PASS", "Prices are getting displayed in Ascending order");

            }
            else
            {
                System.out.println("Prices are Not getting displayed in Ascending order");
                Log.ReportEvent("FAIL", "Prices are Not getting displayed in Ascending order");


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
public void PriceOrderCheckInAscendingToDiv(Log Log,ScreenShots ScreenShots) {
        try {
            // 1. Find price elements using more robust XPath
            List<WebElement> priceElements = driver.findElements(By.xpath(
                    "//*[contains(normalize-space(@class), 'tg-toprice')]"
                    ));

            // 2. Extract and clean price strings
            List<Integer> prices = new ArrayList<>();
            for (WebElement priceElement : priceElements) {
                String rawPrice = priceElement.getText().trim();

                // Remove currency symbols and commas
                String cleanPrice = rawPrice
                        .replace("â‚¹", "")
                        .replace("₹", "")

                        .replace(",", "")
                        .trim();

                try {
                    int priceValue = Integer.parseInt(cleanPrice);
                    prices.add(priceValue);
                    System.out.println("Cleaned price: " + priceValue);
                } catch (NumberFormatException e) {
                    System.err.println("Failed to parse: " + rawPrice);
                }
            }

            // 3. Check descending order
            boolean isAscending = true;
            for (int i = 0; i < prices.size() - 1; i++) {
                if (prices.get(i) > prices.get(i + 1)) {
                    isAscending = false;
                    break;
                }
            }
            if(isAscending)
            {
                System.out.println("Prices are getting displayed in Ascending order");
                Log.ReportEvent("PASS", "Prices are getting displayed in Ascending order");

            }
            else
            {
                System.out.println("Prices are Not getting displayed in Ascending order");
                Log.ReportEvent("FAIL", "Prices are Not getting displayed in Ascending order");


            }
            // System.out.println("Prices in descending order: " + isDescending);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//Method to click on Price Filter From Div
    public void PriceDescendingFilter()
    {
        driver.findElement(By.xpath("(//small[text()='PRICE'])[1]")).click();
    }
//method to validate Price Filter From Div
    public void PriceOrderCheckInDescending(Log Log,ScreenShots ScreenShots) {
        try {
            // 1. Find price elements using more robust XPath
            List<WebElement> priceElements = driver.findElements(By.xpath(
                    "//*[contains(normalize-space(@class), 'tg-fromprice')]"
                    ));

            // 2. Extract and clean price strings
            List<Integer> prices = new ArrayList<>();
            for (WebElement priceElement : priceElements) {
                String rawPrice = priceElement.getText().trim();

                // Remove currency symbols and commas
                String cleanPrice = rawPrice
                        .replace("â‚¹", "")
                        .replace("₹", "")        

                        .replace(",", "")
                        .trim();

                try {
                    int priceValue = Integer.parseInt(cleanPrice);
                    prices.add(priceValue);
                    System.out.println("Cleaned price: " + priceValue);
                } catch (NumberFormatException e) {
                    System.err.println("Failed to parse: " + rawPrice);
                }
            }

            // 3. Check descending order
            boolean isDescending = true;
            for (int i = 0; i < prices.size() - 1; i++) {
                if (prices.get(i) < prices.get(i + 1)) {
                    isDescending = false;
                    break;
                }
            }
            if(isDescending)
            {
                System.out.println("Prices are getting displayed in descending order");
                Log.ReportEvent("PASS", "Prices are getting displayed in descending order");

            }
            else
            {
                System.out.println("Prices are Not getting displayed in descending order");
                Log.ReportEvent("FAIL", "Prices are Not getting displayed in descending order");


            }
            //  System.out.println("Prices in descending order: " + isDescending);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//Method to click on Price Filter From Div
    public void PriceDescendingFilterToDiv()
    {
        driver.findElement(By.xpath("(//small[text()='PRICE'])[2]")).click();
    }
//Method to validate Price Filter To Div
    public void PriceOrderCheckInDescendingToDiv(Log Log,ScreenShots ScreenShots) {
        try {
            // 1. Find price elements using more robust XPath
            List<WebElement> priceElements = driver.findElements(By.xpath(
                    "//*[contains(normalize-space(@class), 'tg-toprice')]"
                    ));

            // 2. Extract and clean price strings
            List<Integer> prices = new ArrayList<>();
            for (WebElement priceElement : priceElements) {
                String rawPrice = priceElement.getText().trim();

                // Remove currency symbols and commas
                String cleanPrice = rawPrice
                        .replace("â‚¹", "")
                        .replace("₹", "")        

                        .replace(",", "")
                        .trim();

                try {
                    int priceValue = Integer.parseInt(cleanPrice);
                    prices.add(priceValue);
                    System.out.println("Cleaned price: " + priceValue);
                } catch (NumberFormatException e) {
                    System.err.println("Failed to parse: " + rawPrice);
                }
            }

            // 3. Check descending order
            boolean isDescending = true;
            for (int i = 0; i < prices.size() - 1; i++) {
                if (prices.get(i) < prices.get(i + 1)) {
                    isDescending = false;
                    break;
                }
            }
            if(isDescending)
            {
                System.out.println("Prices are getting displayed in descending order");
                Log.ReportEvent("PASS", "Prices are getting displayed in descending order");

            }
            else
            {
                System.out.println("Prices are Not getting displayed in descending order");
                Log.ReportEvent("FAIL", "Prices are Not getting displayed in descending order");


            }
            // System.out.println("Prices in descending order: " + isDescending);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



// Method to click on Meals Dropdown
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
    } //select meal return 
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
//Method to validate meals price with FareSummary  

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

// Method to click on Meals Dropdown
        public double selectBaggageOnwardOneWay(Log Log, ScreenShots ScreenShots, String OnwardBaggageSelectSplit[]) throws InterruptedException {

            double totalPrice1 = 0.0;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            List<WebElement> sectors = driver.findElements(By.xpath("//label[text()='Baggage Preference']/parent::div//div[@aria-labelledby='onward-label onward']"));
            int value = sectors.size();

            for (int i = 0; i < value; i++) {
                WebElement sector = sectors.get(i);
                sector.click();

                String baggage = OnwardBaggageSelectSplit[i];
                List<WebElement> baggageList = driver.findElements(By.xpath("//ul/li[position() > 1]"));
                boolean isBaggageMatched = false;

                for (WebElement baggageListGet : baggageList) {
                    String getBaggageName = baggageListGet.getText();
                    String[] getbaggageName1 = getBaggageName.split("₹");
                    String getBaggageName2 = getbaggageName1[0].trim();
                    String finalBaggageText = getBaggageName2.replace("-", "").trim();

                    if (finalBaggageText.equalsIgnoreCase(baggage)) {
                        System.out.println("Matching meal found: " + baggage);

                        WebElement userNeededDate = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//ul[@aria-labelledby='onward-label']//li[normalize-space(text())='"+baggage+"']")));
                        Thread.sleep(2000);
                        userNeededDate.click();

                        isBaggageMatched = true;
                        break;
                    }
                }

                if (!isBaggageMatched && !baggageList.isEmpty()) {
                    WebElement selectedBaggage = wait.until(ExpectedConditions.elementToBeClickable(baggageList.get(0)));
                    selectedBaggage.click();
                }
            }

            // ✅ Now handle prices AFTER all dropdowns are selected
            List<WebElement> priceElements = driver.findElements(By.xpath(
                "//[@id='onward']/ancestor::div//[text()='Baggage Preference']/parent::div//*[@class='price']"));

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
                Log.ReportEvent("PASS", "Total price after all onward Baggage selections: " + totalPrice1);
            }

            return totalPrice1;
        }
 //select meal return 
        public double selectBaggageReturnn(Log Log, ScreenShots ScreenShots,String ReturnBaggageSelectSplit[]) throws InterruptedException {


                 double totalPrice2 = 0.0;
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                    List<WebElement> sectors = driver.findElements(By.xpath("//label[text()='Baggage Preference']/parent::div//div[@aria-labelledby='return-label return']"));
                    int value = sectors.size();

                    for (int i = 0; i < value; i++) {
                        WebElement sector = sectors.get(i);
                        sector.click();

                        String returnBaggage = ReturnBaggageSelectSplit[i];
                        List<WebElement> baggageList = driver.findElements(By.xpath("//ul/li[position() > 1]"));
                        boolean isBaggageMatched = false;

                        for (WebElement baggageListGet : baggageList) {
                            String getbaggageName = baggageListGet.getText();
                            String[] getBaggageName1 = getbaggageName.split("₹");
                            String getBaggageName2 = getBaggageName1[0].trim();
                            String finalBaggageText = getBaggageName2.replace("-", "").trim();

                            if (finalBaggageText.equalsIgnoreCase(returnBaggage)) {
                                System.out.println("Matching meal found: " + returnBaggage);

                                WebElement userNeededDate = wait.until(ExpectedConditions.elementToBeClickable(
                                    By.xpath("//ul[@aria-labelledby='return-label']//li[normalize-space(text())='"+returnBaggage+"']")));
                                Thread.sleep(2000);
                                userNeededDate.click();

                                isBaggageMatched = true;
                                break;
                            }
                        }

                        if (!isBaggageMatched && !baggageList.isEmpty()) {
                            WebElement selectedBaggage = wait.until(ExpectedConditions.elementToBeClickable(baggageList.get(0)));
                            selectedBaggage.click();
                        }
                    }

                    // ✅ Now handle prices AFTER all dropdowns are selected
                    List<WebElement> priceElementsreturn = driver.findElements(By.xpath(
                        "//*[@id='return']//parent::div//span[@class='price']"));

                    if (priceElementsreturn.isEmpty()) {
                        Log.ReportEvent("FAIL", "No price elements found for return baggage selections.");
                    } else {
                        for (WebElement priceElementreturnsector : priceElementsreturn) {
                            String price = priceElementreturnsector.getText();
                            System.out.println("Price getting: " + price);
                            double priceValue = Double.parseDouble(price.replaceAll("[^\\d.]", ""));
                            totalPrice2 += priceValue;
                        }
                        System.out.println(totalPrice2);
                        Log.ReportEvent("PASS", "Total price after all return baggage selections: " + totalPrice2);
                    }

                    return totalPrice2;
                }

public Object validateBaggagee(Log Log,ScreenShots ScreenShots,double total) throws InterruptedException
    {
        String FareSummary = driver.findElement(By.xpath("//*[text()='Baggage Price']/parent::div//h6")).getText();
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

    //---------------------------------------------------------------------------------
    
    public String clickAirlineCheckboxRoundtripDomestic1(String... airlineNames) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Try user-requested airlines first
        for (String airlineName : airlineNames) {
            try {
            	String xpath="//*[text()='AIRLINES']//parent::div//span[text()='" + airlineName + "']//parent::div/parent::li//input";
            
            //	String xpath = "//[contains(@class,'tg-airline-name')]/span[text()='" + airlineName.trim() + "']/parent::/parent::*//input[@type='checkbox']";
                WebElement checkboxInput = driver.findElement(By.xpath(xpath));
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkboxInput);
                if (!checkboxInput.isSelected()) {
                    js.executeScript("arguments[0].click();", checkboxInput);
                    System.out.println("Clicked checkbox for: " + airlineName);
                } else {
                    System.out.println("Checkbox already selected for: " + airlineName);
                }
                return airlineName.trim();
            } catch (NoSuchElementException e) {
                // Not found, continue to next airline
            } catch (Exception e) {
                System.out.println("Error processing airline '" + airlineName + "': " + e.getMessage());
            }
        }

        // None of the requested airlines found, click a random available airline
        List<WebElement> availableAirlines = driver.findElements(By.xpath("//*[contains(@class,'tg-airline-name')]"));

        if (availableAirlines.isEmpty()) {
            System.out.println("No airlines are available on the page.");
            return null;
        } else {
            // Pick a random airline element
            Random rand = new Random();
            WebElement randomAirlineElem = availableAirlines.get(rand.nextInt(availableAirlines.size()));

            // Get airline name text
            String randomAirlineName = randomAirlineElem.getText().trim();

            try {
                // From the span, navigate up to find checkbox input sibling
                WebElement checkboxInput = randomAirlineElem.findElement(By.xpath("./parent::/parent:://input[@type='checkbox']"));
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkboxInput);
                if (!checkboxInput.isSelected()) {
                    js.executeScript("arguments[0].click();", checkboxInput);
                    System.out.println("Clicked RANDOM checkbox for: " + randomAirlineName);
                } else {
                    System.out.println("Random airline checkbox already selected: " + randomAirlineName);
                }
                return randomAirlineName;
            } catch (Exception e) {
                System.out.println("Error clicking random airline checkbox for '" + randomAirlineName + "': " + e.getMessage());
                return null;
            }
        }
    }
public void validateAirlinesDomesticroundtrip(int index, Log Log, ScreenShots ScreenShots, String... airlineCode) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Locate and scroll to the 'View Flight' button
            String xpath = "(//div[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]";
            WebElement viewflightbutton = driver.findElement(By.xpath(xpath));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewflightbutton);
            Thread.sleep(500); // Allow time for scrolling

            // Click using JavaScript
            js.executeScript("arguments[0].click();", viewflightbutton);

            // Manual wait for airline data to appear
            List<WebElement> airlineElements = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                airlineElements = driver.findElements(By.xpath("//*[@data-tgflcarriername]"));
                if (!airlineElements.isEmpty()) break;
                Thread.sleep(500);
            }

            // Collect visible airline names
            List<String> displayedAirlines = new ArrayList<>();
            for (WebElement element : airlineElements) {
                if (element.isDisplayed()) {
                    String airline = element.getText().replaceAll("\\s+", " ").trim();
                    if (!airline.isEmpty()) {
                        displayedAirlines.add(airline);
                    }
                }
            }

            System.out.println("User passed airlines: " + Arrays.toString(airlineCode));
            System.out.println("Displayed Airlines: " + displayedAirlines);

            boolean allMatch = true;
            for (String expectedAirline : airlineCode) {
                if (expectedAirline == null || expectedAirline.trim().isEmpty()) {
                    Log.ReportEvent("WARN", "Skipping null or empty airline code.");
                    continue;
                }

                String expectedTrimmed = expectedAirline.replaceAll("\\s+", " ").trim().toLowerCase();

                boolean found = displayedAirlines.stream()
                    .anyMatch(actual -> actual.replaceAll("\\s+", " ").trim().toLowerCase().contains(expectedTrimmed));

                if (found) {
                    Log.ReportEvent("PASS", "Expected airline is showing: " + expectedAirline);
                } else {
                    Log.ReportEvent("FAIL", "Expected airline NOT found: " + expectedAirline);
                    allMatch = false;
                }
            }

            if (allMatch) {
                Log.ReportEvent("PASS", "All expected airlines are correctly shown in the results.");
            } else {
                Log.ReportEvent("FAIL", "Some expected airlines are missing in the results.");
            }

            ScreenShots.takeScreenShot1();

        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Exception occurred while validating airline codes: " + e.getMessage());
            ScreenShots.takeScreenShot1();
            e.printStackTrace();
        }

        // Close the result popup
        closeButtononresultpage ();
    }

public void clickOnContinue()
{
    driver.findElement(By.xpath("//button[@data-tgbookflights]")).click();

}

public void validateReturnAirlinesDomesticroundtrip(int index, Log Log, ScreenShots ScreenShots, String... airlineCode) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    try {
        // Locate and scroll to the 'View Flight' button
        String xpath = "(//div[@class='round-trip-to-results']//button[text()='View Flight'])[" + index + "]";
        WebElement viewflightbutton = driver.findElement(By.xpath(xpath));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewflightbutton);
        Thread.sleep(500); // Allow time for scrolling

        // Click using JavaScript
        js.executeScript("arguments[0].click();", viewflightbutton);

        // Manual wait for airline data to appear
        List<WebElement> airlineElements = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            airlineElements = driver.findElements(By.xpath("//*[@data-tgflcarriername]"));
            if (!airlineElements.isEmpty()) break;
            Thread.sleep(500);
        }

        // Collect visible airline names
        List<String> displayedAirlines = new ArrayList<>();
        for (WebElement element : airlineElements) {
            if (element.isDisplayed()) {
                String airline = element.getText().replaceAll("\\s+", " ").trim();
                if (!airline.isEmpty()) {
                    displayedAirlines.add(airline);
                }
            }
        }

        System.out.println("User passed airlines: " + Arrays.toString(airlineCode));
        System.out.println("Displayed Airlines: " + displayedAirlines);

        boolean allMatch = true;
        for (String expectedAirline : airlineCode) {
            if (expectedAirline == null || expectedAirline.trim().isEmpty()) {
                Log.ReportEvent("WARN", "Skipping null or empty airline code.");
                continue;
            }

            String expectedTrimmed = expectedAirline.replaceAll("\\s+", " ").trim().toLowerCase();

            boolean found = displayedAirlines.stream()
                .anyMatch(actual -> actual.replaceAll("\\s+", " ").trim().toLowerCase().contains(expectedTrimmed));

            if (found) {
                Log.ReportEvent("PASS", "Expected airline is showing for return: " + expectedAirline);
            } else {
                Log.ReportEvent("FAIL", "Expected airline NOT found for return: " + expectedAirline);
                allMatch = false;
            }
        }

        if (allMatch) {
            Log.ReportEvent("PASS", "All expected airlines are correctly shown in the results for return.");
        } else {
            Log.ReportEvent("FAIL", "Some expected airlines are missing in the results for return.");
        }

        ScreenShots.takeScreenShot1();

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Exception occurred while validating airline codes for return: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
    }

    // Close the result popup
    closeButtononresultpage ();
}


/*     use this if we want all flights to check

public void validateMultipleAirlinesInDepartFlights(Log Log, String... airlineNames) throws InterruptedException {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Step 1: Click checkboxes for user-passed airlines
    List<String> selectedAirlines = new ArrayList<>();
    for (String airline : airlineNames) {
        String clickedAirline = clickAirlineCheckboxRoundtripDomestic1(airline);
        if (clickedAirline != null && !clickedAirline.isEmpty()) {
            selectedAirlines.add(clickedAirline);
            Log.ReportEvent("INFO", "Selected airline checkbox: " + clickedAirline);
        } else {
            Log.ReportEvent("WARN", "Airline checkbox not found or not selected: " + airline);
        }
    }

    if (selectedAirlines.isEmpty()) {
        Log.ReportEvent("FAIL", "No airlines were selected. Exiting validation.");
        return;
    }

    // Step 2: Find all "View Flight" buttons in departure flights section
    List<WebElement> viewFlightButtons = driver.findElements(By.xpath("//div[@class='round-trip-from-results']//button[text()='View Flight']"));

    Log.ReportEvent("INFO", "Found " + viewFlightButtons.size() + " 'View Flight' buttons to validate.");

    Map<String, Integer> airlineCounts = new HashMap<>();
    for (String airline : selectedAirlines) {
        airlineCounts.put(airline, 0);
    }

    // Step 3: Loop through all view flight buttons and count airlines in details
    for (int i = 0; i < viewFlightButtons.size(); i++) {
        WebElement button = viewFlightButtons.get(i);
        try {
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", button);
            js.executeScript("arguments[0].click();", button);
            Log.ReportEvent("INFO", "Clicked 'View Flight' button #" + (i + 1));

            Thread.sleep(1000); // wait for popup to load

            // Get all airline name elements inside popup
            List<WebElement> airlineElements = driver.findElements(By.xpath("//*[@data-tgflcarriername]"));

            for (WebElement el : airlineElements) {
                String airlineText = el.getText().trim();
                for (String selected : selectedAirlines) {
                    if (airlineText.equalsIgnoreCase(selected)) {
                        airlineCounts.put(selected, airlineCounts.get(selected) + 1);
                    }
                }
            }

            // Close the popup
            WebElement closeBtn = driver.findElement(By.xpath("//*[contains(@class,'tg-fromviewclose-btn')]"));
            js.executeScript("arguments[0].click();", closeBtn);
            Thread.sleep(500);
        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Error while validating flight #" + (i + 1) + ": " + e.getMessage());
        }
    }

    // Step 4: Log and print summary counts
    Log.ReportEvent("INFO", "=== Airline counts in departure flight details ===");
    for (String airline : selectedAirlines) {
        int count = airlineCounts.get(airline);
        String msg = airline + ": " + count;
        Log.ReportEvent("INFO", msg);
        System.out.println(msg);
    }
}



public void validateMultipleAirlinesInReturnFlights(Log Log, String... airlineNames) throws InterruptedException {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Step 1: Click checkboxes for user-passed airlines
    List<String> selectedAirlines = new ArrayList<>();
    for (String airline : airlineNames) {
        String clickedAirline = clickAirlineCheckboxRoundtripDomestic1(airline);
        if (clickedAirline != null && !clickedAirline.isEmpty()) {
            selectedAirlines.add(clickedAirline);
            Log.ReportEvent("INFO", "Selected airline checkbox: " + clickedAirline);
        } else {
            Log.ReportEvent("WARN", "Airline checkbox not found or not selected: " + airline);
        }
    }

    if (selectedAirlines.isEmpty()) {
        Log.ReportEvent("FAIL", "No airlines were selected. Exiting validation.");
        return;
    }

    // Step 2: Find all "View Flight" buttons in departure flights section
    List<WebElement> viewFlightButtons = driver.findElements(By.xpath("//div[@class='round-trip-to-results']//button[text()='View Flight']"));

    Log.ReportEvent("INFO", "Found " + viewFlightButtons.size() + " 'View Flight' buttons to validate.");

    Map<String, Integer> airlineCounts = new HashMap<>();
    for (String airline : selectedAirlines) {
        airlineCounts.put(airline, 0);
    }

    // Step 3: Loop through all view flight buttons and count airlines in details
    for (int i = 0; i < viewFlightButtons.size(); i++) {
        WebElement button = viewFlightButtons.get(i);
        try {
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", button);
            js.executeScript("arguments[0].click();", button);
            Log.ReportEvent("INFO", "Clicked 'View Flight' button #" + (i + 1));

            Thread.sleep(1000); // wait for popup to load

            // Get all airline name elements inside popup
            List<WebElement> airlineElements = driver.findElements(By.xpath("//*[@data-tgflcarriername]"));

            for (WebElement el : airlineElements) {
                String airlineText = el.getText().trim();
                for (String selected : selectedAirlines) {
                    if (airlineText.equalsIgnoreCase(selected)) {
                        airlineCounts.put(selected, airlineCounts.get(selected) + 1);
                    }
                }
            }

            // Close the popup
            WebElement closeBtn = driver.findElement(By.xpath("//*[contains(@class,'tg-toviewclose-btn')]"));
            js.executeScript("arguments[0].click();", closeBtn);
            Thread.sleep(500);
        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Error while validating flight #" + (i + 1) + ": " + e.getMessage());
        }
    }

    // Step 4: Log and print summary counts
    Log.ReportEvent("INFO", "=== Airline counts in departure flight details ===");
    for (String airline : selectedAirlines) {
        int count = airlineCounts.get(airline);
        String msg = airline + ": " + count;
        Log.ReportEvent("INFO", msg);
        System.out.println(msg);
    }
}
*/

public List<String> clickAirlineCheckboxRoundtripDomesticRoundtrip(Log Log,String... airlineNames) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    List<String> clickedAirlines = new ArrayList<>();

    // Try user-requested airlines first
    for (String airlineName : airlineNames) {
        try {
            String xpath = "//*[text()='AIRLINES']//parent::div//span[text()='" + airlineName + "']//parent::div/parent::li//input";
            WebElement checkboxInput = driver.findElement(By.xpath(xpath));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkboxInput);

            if (!checkboxInput.isSelected()) {
                js.executeScript("arguments[0].click();", checkboxInput);
                Log.ReportEvent("INFO", "Checkbox selected for: " + airlineName);
                System.out.println("Clicked checkbox for: " + airlineName);
            } else {
                Log.ReportEvent("INFO", "Checkbox already selected for: " + airlineName);
                System.out.println("Checkbox already selected for: " + airlineName);
            }

            clickedAirlines.add(airlineName.trim());

        } catch (NoSuchElementException e) {
            Log.ReportEvent("WARN", "Airline not found on UI: " + airlineName);
            System.out.println("Airline not found on UI: " + airlineName);
        } catch (Exception e) {
            Log.ReportEvent("FAIL", "Error processing airline '" + airlineName + "': " + e.getMessage());
            System.out.println("Error processing airline '" + airlineName + "': " + e.getMessage());
        }
    }

    // If no checkboxes were successfully clicked, click a random available airline
    if (clickedAirlines.isEmpty()) {
        List<WebElement> availableAirlines = driver.findElements(By.xpath("//*[contains(@class,'tg-airline-name')]"));
        if (!availableAirlines.isEmpty()) {
            Random rand = new Random();
            WebElement randomAirlineElem = availableAirlines.get(rand.nextInt(availableAirlines.size()));
            String randomAirlineName = randomAirlineElem.getText().trim();

            try {
                WebElement checkboxInput = randomAirlineElem.findElement(By.xpath("./ancestor::li//input[@type='checkbox']"));
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkboxInput);
                if (!checkboxInput.isSelected()) {
                    js.executeScript("arguments[0].click();", checkboxInput);
                    Log.ReportEvent("INFO", "Fallback: Checkbox selected for random airline: " + randomAirlineName);
                    System.out.println("Clicked RANDOM checkbox for: " + randomAirlineName);
                }

                clickedAirlines.add(randomAirlineName);
            } catch (Exception e) {
                Log.ReportEvent("FAIL", "Error clicking random airline checkbox: " + e.getMessage());
                System.out.println("Error clicking random airline checkbox for '" + randomAirlineName + "': " + e.getMessage());
            }
        } else {
            Log.ReportEvent("FAIL", "No airlines are available on the page.");
            System.out.println("No airlines are available on the page.");
        }
    }

    return clickedAirlines;
}

public void validateReturnAirlinesDomesticroundtrip(Log Log, ScreenShots ScreenShots, String... airlineCode) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    try {
        List<String> clickedAirlines = new ArrayList<>();
        int maxCards = 20;

        for (int index = 1; index <= maxCards; index++) {
            try {
                // Scroll to and click 'View Flight' button for return leg
                String xpath = "(//div[@class='round-trip-to-results']//button[text()='View Flight'])[" + index + "]";
                WebElement viewflightbutton = driver.findElement(By.xpath(xpath));
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewflightbutton);
                Thread.sleep(500);
                js.executeScript("arguments[0].click();", viewflightbutton);

                // Wait for airline info to appear
                List<WebElement> airlineElements = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    airlineElements = driver.findElements(By.xpath("//*[@data-tgflcarriername]"));
                    if (!airlineElements.isEmpty()) break;
                    Thread.sleep(500);
                }

                List<String> displayedAirlines = new ArrayList<>();
                for (WebElement element : airlineElements) {
                    if (element.isDisplayed()) {
                        String airline = element.getText().replaceAll("\\s+", " ").trim();
                        if (!airline.isEmpty()) {
                            displayedAirlines.add(airline);
                        }
                    }
                }

                System.out.println("Flight card index [" + index + "], Airlines found: " + displayedAirlines);

                // Check if any displayed airline matches any of the passed airlineCode
                boolean matched = false;
                for (String expectedAirline : airlineCode) {
                    if (expectedAirline == null || expectedAirline.trim().isEmpty()) continue;
                    String expectedTrimmed = expectedAirline.replaceAll("\\s+", " ").trim().toLowerCase();

                    for (String actual : displayedAirlines) {
                        String actualTrimmed = actual.replaceAll("\\s+", " ").trim().toLowerCase();
                        if (actualTrimmed.contains(expectedTrimmed)) {
                            Log.ReportEvent("PASS", "Return flight [" + index + "] matched airline: " + actual);
                            matched = true;
                            break;
                        }
                    }
                    if (matched) break;
                }

                if (!matched) {
                    Log.ReportEvent("FAIL", "Return flight [" + index + "] has NO matching airlines from expected: " + Arrays.toString(airlineCode));
                }

                ScreenShots.takeScreenShot1();

                // Close popup
                closeButtononresultpage();

            } catch (NoSuchElementException e) {
                Log.ReportEvent("INFO", "No more return flight cards after index " + (index - 1));
                break; // Exit if no more return flight buttons are found
            } catch (Exception e) {
                Log.ReportEvent("WARN", "Exception at return flight index " + index + ": " + e.getMessage());
                closeButtononresultpage();
            }
        }

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Overall error validating return flights: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
    }
}

public void validateDepartAirlinesDomesticroundtrip(Log Log, ScreenShots ScreenShots, String... airlineCode) {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    try {
        List<String> clickedAirlines = new ArrayList<>();
        int maxCards = 20;

        for (int index = 1; index <= maxCards; index++) {
            try {
                // Scroll to and click 'View Flight' button for return leg
                String xpath = "(//div[@class='round-trip-from-results']//button[text()='View Flight'])[" + index + "]";
                WebElement viewflightbutton = driver.findElement(By.xpath(xpath));
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewflightbutton);
                Thread.sleep(500);
                js.executeScript("arguments[0].click();", viewflightbutton);

                // Wait for airline info to appear
                List<WebElement> airlineElements = new ArrayList<>();
                for (int i = 0; i < 10; i++) {               //It will tries upto 10 times
                    airlineElements = driver.findElements(By.xpath("//*[@data-tgflcarriername]"));
                    if (!airlineElements.isEmpty()) break;
                    Thread.sleep(500);
                }

                List<String> displayedAirlines = new ArrayList<>();
                for (WebElement element : airlineElements) {
                    if (element.isDisplayed()) {
                    	//Extracts text (airline name), removes extra spaces, and stores in displayedAirlines
                        String airline = element.getText().replaceAll("\\s+", " ").trim();
                        if (!airline.isEmpty()) {
                            displayedAirlines.add(airline);
                        }
                    }
                }

                System.out.println("Flight card index [" + index + "], Airlines found: " + displayedAirlines);

                // Check if any displayed airline matches any of the passed airlineCode
                boolean matched = false;
                for (String expectedAirline : airlineCode) {
                    if (expectedAirline == null || expectedAirline.trim().isEmpty()) continue;  //This line skips the current iteration of the loop if the airline name is null
                    String expectedTrimmed = expectedAirline.replaceAll("\\s+", " ").trim().toLowerCase();

                    for (String actual : displayedAirlines) {
                        String actualTrimmed = actual.replaceAll("\\s+", " ").trim().toLowerCase();
                        if (actualTrimmed.contains(expectedTrimmed)) {
                            Log.ReportEvent("PASS", "Return flight [" + index + "] matched airline: " + actual);
                            matched = true;
                            break;
                        }
                    }
                    if (matched) break;
                }

                if (!matched) {
                    Log.ReportEvent("FAIL", "Return flight [" + index + "] has NO matching airlines from expected: " + Arrays.toString(airlineCode));
                }

                ScreenShots.takeScreenShot1();

                // Close popup
                closeButtononresultpage();

            } catch (NoSuchElementException e) {
                Log.ReportEvent("INFO", "No more return flight cards after index " + (index - 1));
                break; // Exit if no more return flight buttons are found
            } catch (Exception e) {
                Log.ReportEvent("WARN", "Exception at return flight index " + index + ": " + e.getMessage());
                closeButtononresultpage();
            }
        }

    } catch (Exception e) {
        Log.ReportEvent("FAIL", "Overall error validating return flights: " + e.getMessage());
        ScreenShots.takeScreenShot1();
        e.printStackTrace();
    }
}

//-----------------------------------------------------------------------------------------------

public double selectMealsOnward() throws InterruptedException
{
	TestExecutionNotifier.showExecutionPopup();
	Thread.sleep(2000);
	double totalPrice1 = 0.0;
	List<WebElement> sectors = driver.findElements(By.xpath("//label[text()='Meal Preference']/parent::div//div[@aria-labelledby='onward-label onward']"));

	for(WebElement sector:sectors)

	{
		System.out.println(sector);

		sector.click();
		List<WebElement>  baggageList = driver.findElements(By.xpath("//ul/li[position() > 1]"));
		for(WebElement baggageListGet:baggageList)  
		{
			String getBaggageName = baggageListGet.getText();
			System.out.println(getBaggageName);
			Thread.sleep(2000);

			baggageList.get(0).click();
			String price = driver.findElement(By.xpath("//div[@aria-labelledby='onward-label onward']//span[@class='price']")).getText();
			System.out.println("gettingprice"+price);
			double prices = Double.parseDouble(price.replaceAll("[^\\d.]", ""));
			totalPrice1 += prices; // Add to total price
			System.out.println(totalPrice1);
			break;

		}

	}
	return totalPrice1;

}
public double selectMealsReturn() throws InterruptedException
{
	TestExecutionNotifier.showExecutionPopup();
	Thread.sleep(2000);

	double totalPrice2 = 0.0;

	List<WebElement> sectors = driver.findElements(By.xpath("//label[text()='Meal Preference']/parent::div//div[@aria-labelledby='return-label return']"));

	for(WebElement sector:sectors)

	{
		System.out.println(sector);

		sector.click();
		List<WebElement>  baggageList = driver.findElements(By.xpath("//ul/li[position() > 1]"));
		for(WebElement baggageListGet:baggageList)  
		{
			String getBaggageName = baggageListGet.getText();
			System.out.println(getBaggageName);
			Thread.sleep(2000);

			baggageList.get(0).click();
			String price = driver.findElement(By.xpath("//label[text()='Meal Preference']/parent::div//div[@aria-labelledby='return-label return']//span[@class='price']")).getText();
			System.out.println(price);
			double prices = Double.parseDouble(price.replaceAll("[^\\d.]", ""));
			totalPrice2 += prices; // Add to total price
			System.out.println(totalPrice2);
			break;

		}

	}
	return totalPrice2;
}
public double addTotalMealsPriceOneway(Log Log,ScreenShots ScreenShots,double totalPrice1,double totalPrice2) throws InterruptedException {
//	double totalPrice1 = selectMealsOnward();
//	double totalPrice2 = selectMealsReturn();
	System.out.println(totalPrice1);
	System.out.println(totalPrice2);

	double total = totalPrice1+totalPrice2 ;
	System.out.println("Total Baggage Price: " + total);
	Log.ReportEvent("PASS", "Total Baggage Price: "+ total);

	return total;
}

public void validateMealsPrice(Log log, ScreenShots screenShots, double total) throws InterruptedException
{
	TestExecutionNotifier.showExecutionPopup();
	
	String FareSummary = driver.findElement(By.xpath("//span[text()='Meal Price']/parent::div//h6")).getText();
	double FareSummary1 = Double.parseDouble(FareSummary.replaceAll("[^\\d.]", ""));

	System.out.println(total);
	System.out.println(FareSummary1);

	if(total==FareSummary1)
	{
		System.out.println("Meals price validated");
	}
}

//-------------------------------------------------------------------------------------------------------------

public double selectBaggageOnward() throws InterruptedException
{
	Thread.sleep(2000);

	double totalPrice1 = 0.0;
	List<WebElement> sectors = driver.findElements(By.xpath("//label[text()='Baggage Preference']/parent::div//div[@aria-labelledby='onward-label onward']"));

	for(WebElement sector:sectors)

	{
		System.out.println(sector);

		sector.click();
		List<WebElement>  baggageList = driver.findElements(By.xpath("//ul/li[position() > 1]"));
		for(WebElement baggageListGet:baggageList)  
		{
			String getBaggageName = baggageListGet.getText();
			System.out.println(getBaggageName);
			baggageList.get(1).click();
			String price = driver.findElement(By.xpath("//span[@class='price']")).getText();
			System.out.println(price);
			double prices = Double.parseDouble(price.replaceAll("[^\\d.]", ""));
			totalPrice1 += prices; // Add to total price
			System.out.println(totalPrice1);
			break;

		}

	}
	return totalPrice1;

}

public void validateBaggagePrice(double total) throws InterruptedException
{
	//double total = addTotalBaggagePrice();
	String FareSummary = driver.findElement(By.xpath("//span[text()='Baggage Price']/parent::div//h6")).getText();
	double FareSummary1 = Double.parseDouble(FareSummary.replaceAll("[^\\d.]", ""));

	System.out.println(total);
	System.out.println(FareSummary1);

	if(total==FareSummary1)
	{
		System.out.println("Baggage price validated");
	}
}

public double addTotalBaggagePrice(Log log, ScreenShots screenShots, double totalPrice1, double totalPrice2) {
	{
		System.out.println("Onward Price: " + totalPrice1);
		System.out.println("Return Price: " + totalPrice2);

		double total = totalPrice1 + totalPrice2;
		System.out.println("Total Baggage Price: " + total);

		log.ReportEvent("PASS", "Combined price of meals: " + total);
		return total;
	}
}



public double selectBaggageReturn() throws InterruptedException
{
    Thread.sleep(2000);

    double totalPrice2 = 0.0;

    List<WebElement> sectors = driver.findElements(By.xpath("//label[text()='Baggage Preference']/parent::div//div[@aria-labelledby='return-label return']"));

    for(WebElement sector:sectors)

    {
        System.out.println(sector);

        sector.click();
        List<WebElement>  baggageList = driver.findElements(By.xpath("//ul/li[position() > 1]"));
        for(WebElement baggageListGet:baggageList)  
        {
            String getBaggageName = baggageListGet.getText();
            System.out.println(getBaggageName);
            baggageList.get(1).click();
            String price = driver.findElement(By.xpath("//div[@aria-labelledby='return-label return']//span[@class='price']")).getText();
            System.out.println(price);
            double prices = Double.parseDouble(price.replaceAll("[^\\d.]", ""));
            totalPrice2 += prices; // Add to total price
            System.out.println(totalPrice2);
            break;

        }

    }
    return totalPrice2;
}

//--------------------------------------------------------------------------------------
//Method to select Department dropdown
public void selectDepartmentRoundtrip() {
    TestExecutionNotifier.showExecutionPopup();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));

    WebElement input = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//input[contains(@id,'react-select-21-input') and @type='text']")));
    input.click();

    WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//div[contains(@id,'react-select') and contains(@id,'option-0')]")));
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
}

//Method to select Project dropdown
public void selectProjectRoundtrip()
{
	TestExecutionNotifier.showExecutionPopup();
	driver.findElement(By.xpath("//input[@id='react-select-9-input']")).click();
	driver.findElement(By.xpath("//div[@class='tg-select__option tg-select__option--is-focused css-d7l1ni-option']")).click();
}

//Method to select CostCenter dropdown
public void selectCostcenterRoundtrip()
{
	TestExecutionNotifier.showExecutionPopup();
	driver.findElement(By.xpath("//input[@id='react-select-8-input']")).click();
	driver.findElement(By.xpath("//div[@class='tg-select__option tg-select__option--is-focused css-d7l1ni-option']")).click();
}

//Method to Click on Send Approval
public void clickOnSendApprovalButton() throws InterruptedException
{ TestExecutionNotifier.showExecutionPopup();
    driver.findElement(By.xpath("//span[text()='Send for Approval']")).click();
    Thread.sleep(1000);
}

//Method to Validate Send Approval Toast
public void validateSendApprovalToastMessage(Log Log,ScreenShots ScreenShots)
{
	TestExecutionNotifier.showExecutionPopup();
    try {
        String approvalToastMessage=driver.findElement(By.xpath("//span[@id='client-snackbar']")).getText();
        if(approvalToastMessage.contentEquals("Your request has been successfully submitted."))
        {
            Log.ReportEvent("PASS", "Send Approval is Successful");
        }else {
            Log.ReportEvent("FAIL", "Send Approval is Not Successful");
            ScreenShots.takeScreenShot1();
            Assert.fail();
        }
        Thread.sleep(3000);
    }catch(Exception e)
    {
        Log.ReportEvent("FAIL", "Send Approval is Not Successful"+ e.getMessage());
        ScreenShots.takeScreenShot1();
        Assert.fail();
    }
}
//=========================================================================

public String clickOnParticularAirline(String[] names) {
	TestExecutionNotifier.showExecutionPopup();
    String selectedFlight = null;

    for (String flightName : names) {
        try {
            System.out.println(flightName);
            // XPath tries to locate the checkbox element for the given flight name
            WebElement checkboxLabel = driver.findElement(By.xpath(
                    "//span[contains(@class,'tg-airline')]//following::span[contains(text(),'" + flightName + "')]/preceding::span[contains(@class,'tg-airline')][1]"));

            // Click the checkbox if found
            checkboxLabel.click();
            selectedFlight = flightName;
            System.out.println("✅ Selected flight: " + flightName);
            break;  // Exit after selecting the first available flight
        } catch (NoSuchElementException e) {
            System.out.println("⚠️ Flight not found: " + flightName + ", trying next...");
        } catch (Exception e) {
            System.out.println("❌ Error while trying to click flight: " + flightName + " → " + e.getMessage());
        }
    }
    // If none of the flights were found
    if (selectedFlight == null) {
        System.out.println("❌ None of the given flights were found: " + Arrays.toString(names));
        Assert.fail("No matching airline found from the list: " + Arrays.toString(names));
    }

    return selectedFlight;
}


public String[] selectFromAndToFlightsBasedOnIndex(int Fromindex)  throws InterruptedException {
	TestExecutionNotifier.showExecutionPopup();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

    // Construct XPath for the 'View Flight' button
    String xpath = "(//div[@class='round-trip-from-results']//button[text()='View Flight'])[" + Fromindex + "]";

    // Wait for the 'View Flight' button to be clickable
    WebElement viewFlightButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Scroll the element into view
    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewFlightButton);

    // Wait for the scrolling to complete
    Thread.sleep(500); // Adjust as necessary

    // Click the 'View Flight' button
    viewFlightButton.click();
    
    Thread.sleep(2000);
   closePopup1();
    //Return
    
//    // Construct XPath for the 'View Flight' button
//    String xpath1 = "(//div[@class='round-trip-to-results']//button[text()='View Flight'])[" + Fromindex + "]";
//
//    // Wait for the 'View Flight' button to be clickable
//    WebElement viewFlightButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath1)));
//    JavascriptExecutor jss = (JavascriptExecutor) driver;
//
//    // Scroll the element into view
//    jss.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewFlightButton1);
//
//    // Wait for the scrolling to complete
//    Thread.sleep(500); // Adjust as necessary
//
//    // Click the 'View Flight' button
//    viewFlightButton1.click();
//    Thread.sleep(2000);
//    closePopup1();
    return null;
}



public void closePopup1() {
            try {
                WebElement closeBtn = driver.findElement(By.xpath("//button[text()='Close']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
            } catch (Exception e) {
                // Log or ignore if close button not found; popup might already be closed
            }
        }
     

public String[] selectToFlightsBasedOnIndex(int Toindex)  throws InterruptedException {
	TestExecutionNotifier.showExecutionPopup();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

    // Construct XPath for the 'View Flight' button
    String xpath = "(//div[@class='round-trip-to-results']//button[text()='View Flight'])["+ Toindex +"]";

    // Wait for the 'View Flight' button to be clickable
    WebElement viewFlightButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Scroll the element into view
    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", viewFlightButton);

    // Wait for the scrolling to complete
    Thread.sleep(500); // Adjust as necessary

    // Click the 'View Flight' button
    viewFlightButton.click();
    return null;
}

public void validateAllBaggageDetailsForAllAirline( String flightname, Log log, ScreenShots screenShots) {
TestExecutionNotifier.showExecutionPopup();
List<WebElement> fareElements;
List<WebElement> cabinBags;
List<WebElement> checkinBags;

try {
    fareElements = driver.findElements(By.className("tg-fare-type"));
    cabinBags = driver.findElements(By.className("tg-fare-cabinbag"));
    checkinBags = driver.findElements(By.className("tg-fare-checkinbag"));
} catch (Exception e) {
    log.ReportEvent("FAIL", "Failed to locate UI elements: " + e.getMessage());
    return;
}

if (fareElements.isEmpty() || cabinBags.isEmpty() || checkinBags.isEmpty()) {
    log.ReportEvent("INFO", "No fare or baggage details found on UI.");
    return;
}

int totalFares = Math.min(fareElements.size(), Math.min(cabinBags.size(), checkinBags.size()));

log.ReportEvent("INFO", "Starting baggage validation for " + totalFares + " fares under airline: " + flightname);

for (int i = 0; i < totalFares; i++) {
    try {
        WebElement fareElement = fareElements.get(i);
        String fareName = fareElement.getAttribute("data-tgflfaretype");

        // Fallback to text if attribute is missing
        if (fareName == null || fareName.trim().isEmpty()) {
            fareName = fareElement.getText().trim();
        }

        if (fareName == null || fareName.isEmpty()) {
            log.ReportEvent("WARN", "Skipping fare at index " + i + " due to missing fare name.");
            continue;
        }
        String cabinBaggage = cabinBags.get(i).getText().trim();
        String checkinBaggage = checkinBags.get(i).getText().trim();

       BaggageDetails expected = getBaggageDetailsManual(flightname, fareName);
       
        BaggageDetails actual = new BaggageDetails(cabinBaggage, checkinBaggage);

        log.ReportEvent("INFO", "Fare: " + fareName);
        System.out.println(fareName);
        log.ReportEvent("INFO", "Expected → " + expected);
        System.out.println(expected);

        log.ReportEvent("INFO", "Actual   → " + actual);
        System.out.println(actual);


        if (expected == null || actual == null) {
            log.ReportEvent("FAIL", "Missing baggage data for fare: " + fareName);
            continue;
        }
        boolean isCabinMatch = expected.cabinBaggage.equalsIgnoreCase(actual.cabinBaggage);
        boolean isCheckinMatch = expected.checkinBaggage.equalsIgnoreCase(actual.checkinBaggage);

        if (isCabinMatch && isCheckinMatch) {
            log.ReportEvent("PASS", "✅ Baggage validation passed for fare: " + fareName);
        } else {
            log.ReportEvent("FAIL", "❌ Baggage validation failed for fare: " + fareName);
            if (!isCabinMatch) {
                log.ReportEvent("FAIL", "Cabin mismatch: Expected = " + expected.cabinBaggage + ", Found = " + actual.cabinBaggage);
            }
            if (!isCheckinMatch) {
                log.ReportEvent("FAIL", "Check-in mismatch: Expected = " + expected.checkinBaggage + ", Found = " + actual.checkinBaggage);
            }
        }

    } catch (Exception e) {
        log.ReportEvent("FAIL", "Exception during validation at index " + i + ": " + e.getMessage());
    }
}
}



}