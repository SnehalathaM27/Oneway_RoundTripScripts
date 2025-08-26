package com.tripgain.collectionofpages;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;

public class SkyTravelers_Hotels_DescriptionPage {
	WebDriver driver;

	public SkyTravelers_Hotels_DescriptionPage (WebDriver driver)
	{
		PageFactory.initElements(driver, this);
		this.driver=driver;
	}
	
	public String getHotelNameFromDescPage() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

	        // Step 1: Switch to the newly opened tab
	        String originalWindow = driver.getWindowHandle();
	        wait.until(driver -> driver.getWindowHandles().size() > 1);
	        for (String windowHandle : driver.getWindowHandles()) {
	            if (!windowHandle.equals(originalWindow)) {
	                driver.switchTo().window(windowHandle);
	                break;
	            }
	        }

	        // Step 2: Wait for the element and get the text
	        By hotelNameLocator = By.xpath("//span[contains(@class,'hotel-details-page__body_summary_hotel-name')]");
	        WebElement hotelNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(hotelNameLocator));
	        Thread.sleep(2000);
	        String hotelName = hotelNameElement.getText().trim();

	        System.out.println(" Hotel Name from Description Page: " + hotelName);
	        return hotelName;

	    } catch (Exception e) {
	        System.out.println(" Error fetching hotel name from description page:");
	        e.printStackTrace();

	      

	        return null;
	    }
	}



	
	public String[] getAmountFromDescPage() {
	    String amount = driver.findElement(By.xpath("(//span[contains(@class,'hotel-details-page__body_summary_hotel-price')])[2]")).getText();
        System.out.println("Amount from Description Page: " + amount);

	    return new String[]{amount};
	}	
	
	public String[] getstarRatingFromDescPage() {
	    String starRating = driver.findElement(By.xpath("//span[@class='details-star-rating']")).getText();
        System.out.println(" star Rating from Description Page: " + starRating);

	    return new String[]{starRating};
	}
	


	public String[] getCheckInDateFromDescPage() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
	        
	        // Wait for the element to be present in the DOM
	        WebElement checkInDatelement = wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.xpath("//span[contains(@class,'check-in-out-card_check-in-date')]")));
	        
	        // Scroll to the element
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkInDatelement);
	        
	        // Optionally wait until visible after scroll
	        wait.until(ExpectedConditions.visibilityOf(checkInDatelement));
	        
	        String checkInDateText = checkInDatelement.getText();
	        System.out.println(" checkInDate from Description Page: " + checkInDateText);

	        return new String[]{checkInDateText};
	    } catch (Exception e) {
	        return new String[]{""};
	    }
	}

	public String[] getCheckInMonthFromDescPage() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
	        
	        // Wait for the element to be present in the DOM
	        WebElement checkInMonElement = wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.xpath("//span[contains(@class,'check-in-out-card_check-in-month')]")));
	        
	        // Scroll to the element
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkInMonElement);
	        
	        // Optionally wait until visible after scroll
	        wait.until(ExpectedConditions.visibilityOf(checkInMonElement));
	        
	        String checkInMon = checkInMonElement.getText();
	        System.out.println(" checkInMon from Description Page: " + checkInMon);

	        return new String[]{checkInMon};
	    } catch (Exception e) {
	        return new String[]{""};
	    }
	}


	
	public String[] getCheckoutDateFromDescPage() {
	    String checkOutDate = driver.findElement(By.xpath("//*[contains(@class,'check-in-out-card_check-out-date')]")).getText();
        System.out.println(" checkOutDate from Description Page: " + checkOutDate);

	    return new String[]{checkOutDate};
	}
	
	public String[] getCheckoutMonthFromDescPage() {
	    String checkOutMon = driver.findElement(By.xpath("//*[contains(@class,'check-in-out-card_check-out-month')]")).getText();
        System.out.println(" checkOutMon from Description Page: " + checkOutMon);
	    return new String[]{checkOutMon};
	}
	
	public String[] getRoomCountFromDescPage() {
	    String roomcount = driver.findElement(By.xpath("//*[contains(@class,'check-in-out-card_total-rooms')]")).getText();
        System.out.println("✅ roomcount from Description Page: " + roomcount);

	    return new String[]{roomcount};
	}
	
	public String[] getGuestsCountFromDescPage() {
	    String guestcount = driver.findElement(By.xpath("//*[contains(@class,'check-in-out-card_guests-count')]")).getText();
        System.out.println(" guestcount from Description Page: " + guestcount);

	    return new String[]{guestcount};
	}

	public void validateCheckInDate(
	        String[] descPageDay,
	        String[] descPageMonth,
	        String[] resultPageDate,
	        Log log,
	        ScreenShots screenshots) {

	    boolean allPass = true;

	    try {
	        if (descPageDay == null || descPageDay.length == 0 || descPageMonth == null || descPageMonth.length == 0
	                || resultPageDate == null || resultPageDate.length < 2) {
	            log.ReportEvent("FAIL", "Input date arrays are incomplete or null.");
	            screenshots.takeScreenShot1();
	            return;
	        }

	 //       log.ReportEvent("INFO", "Validating Check-In Date...");
	   //     log.ReportEvent("INFO", "Result Page - Day: " + resultPageDate[0] + ", Month: " + resultPageDate[1]);
	     //   log.ReportEvent("INFO", "Desc Page   - Day: " + descPageDay[0] + ", Month: " + descPageMonth[0]);

	        if (!resultPageDate[0].equals(descPageDay[0])) {
	            log.ReportEvent("FAIL", "Day does not match! ResultPage: " + resultPageDate[0] + ", DescPage: " + descPageDay[0]);
	            screenshots.takeScreenShot1();
	            allPass = false;
	        } else {
	        	System.out.println("Day matches");

	        }

	        if (!resultPageDate[1].equals(descPageMonth[0])) {
	            log.ReportEvent("FAIL", "Month does not match! ResultPage: " + resultPageDate[1] + ", DescPage: " + descPageMonth[0]);
	            screenshots.takeScreenShot1();
	            allPass = false;
	        } else {
	        	System.out.println("Month matches");
	        }

	        if (allPass) {
	            log.ReportEvent("PASS", "Check-In Date validation passed successfully from Result page to description page .");
	        } else {
	            log.ReportEvent("FAIL", "Check-In Date validation failed from Result page to description page .");
	        }

	    } catch (Exception e) {
	        log.ReportEvent("FAIL", "Exception in validateCheckInDate: " + e.getMessage());
	        screenshots.takeScreenShot1();
	        e.printStackTrace();
	    }
	}

	public void validateCheckOutDate(
	        String[] descPageDay,
	        String[] descPageMonth,
	        String[] resultPageDate,
	        Log log,
	        ScreenShots screenshots) {

	    boolean allPass = true;

	    try {
	        if (descPageDay == null || descPageDay.length == 0 || 
	            descPageMonth == null || descPageMonth.length == 0 || 
	            resultPageDate == null || resultPageDate.length < 2) {

	            log.ReportEvent("FAIL", "Check-Out date inputs are null or incomplete.");
	            screenshots.takeScreenShot1();
	            return;
	        }

	     //   log.ReportEvent("INFO", "Validating Check-Out Date...");
	     //   log.ReportEvent("INFO", "Result Page - Day: " + resultPageDate[0] + ", Month: " + resultPageDate[1]);
	       // log.ReportEvent("INFO", "Desc Page   - Day: " + descPageDay[0] + ", Month: " + descPageMonth[0]);

	        // Validate Day
	        if (!resultPageDate[0].equals(descPageDay[0])) {
	            log.ReportEvent("FAIL", "Check-Out day mismatch! ResultPage: " + resultPageDate[0] + ", DescPage: " + descPageDay[0]);
	            screenshots.takeScreenShot1();
	            allPass = false;
	        } else {
	        	System.out.println("Check-Out day matches.");
	        }

	        // Validate Month
	        if (!resultPageDate[1].equals(descPageMonth[0])) {
	            log.ReportEvent("FAIL", "Check-Out month mismatch! ResultPage: " + resultPageDate[1] + ", DescPage: " + descPageMonth[0]);
	            screenshots.takeScreenShot1();
	            allPass = false;
	        } else {
	        	System.out.println("Check-Out month matches.");
	        }

	        // Final Result
	        if (allPass) {
	            log.ReportEvent("PASS", "Check-Out Date validation passed from Result page to description page .");
	        } else {
	            log.ReportEvent("FAIL", "Check-Out Date validation failed from Result page to description page .");
	        }

	    } catch (Exception e) {
	        log.ReportEvent("FAIL", "Exception during Check-Out date validation: " + e.getMessage());
	        screenshots.takeScreenShot1();
	        e.printStackTrace();
	    }
	}
	
	//Method to validate hotel name text with result screeen and with description screeen
	public void validateHotelNameFromResultPageToDescPage(String hotelNameDescPage, List<String> hotelsList, Log log, ScreenShots screenshots) {
	    if (hotelNameDescPage == null || hotelNameDescPage.trim().isEmpty()) {
	        log.ReportEvent("FAIL", "Hotel name from description page is null or empty.");
	        screenshots.takeScreenShot1();
	        return;
	    }

	    if (hotelsList == null || hotelsList.isEmpty()) {
	        log.ReportEvent("FAIL", "Hotel list from results screen is null or empty.");
	        screenshots.takeScreenShot1();
	        return;
	    }

	    String hotelFromDesc = hotelNameDescPage.trim();
	    boolean found = false;

	    for (String hotel : hotelsList) {
	        if (hotel.equalsIgnoreCase(hotelFromDesc)) {
	            found = true;
	            break;
	        }
	    }

	    if (found) {
	        log.ReportEvent("PASS", "Hotel from description page '" + hotelFromDesc + "' found and matches with Result page hotel name .");
	    } else {
	        log.ReportEvent("FAIL", "Hotel from description page '" + hotelFromDesc + "' NOT match with result page hotel name .");
	        screenshots.takeScreenShot1();
	    }
	}


//Method to validate guests and room count from result page to description page 
	
	
	public void validateRoomAndGuestCountsFromResultToDescPage(
	        String[] roomGuestFromResultPage,
	        String[] roomCountFromDescPage,
	        String[] guestCountFromDescPage,
	        Log log,
	        ScreenShots screenshots) {

	    // Validate input arrays
	    if (roomGuestFromResultPage == null || roomGuestFromResultPage.length < 2) {
	        log.ReportEvent("FAIL", "Room and Guest data from result page is incomplete or null.");
	        screenshots.takeScreenShot1();
	        return;
	    }

	    if (roomCountFromDescPage == null || roomCountFromDescPage.length == 0 || roomCountFromDescPage[0].isEmpty()) {
	        log.ReportEvent("FAIL", "Room count from description page is null or empty.");
	        screenshots.takeScreenShot1();
	        return;
	    }

	    if (guestCountFromDescPage == null || guestCountFromDescPage.length == 0 || guestCountFromDescPage[0].isEmpty()) {
	        log.ReportEvent("FAIL", "Guest count from description page is null or empty.");
	        screenshots.takeScreenShot1();
	        return;
	    }

	    // Extract and normalize values
	    String roomsResult = roomGuestFromResultPage[0].trim();   // e.g., "1"
	    String guestsResult = roomGuestFromResultPage[1].trim();  // e.g., "2"

	    String roomsDescRaw = roomCountFromDescPage[0].trim();    // e.g., "Room 1"
	    String guestsDescRaw = guestCountFromDescPage[0].trim();  // e.g., "2"

	    // Normalize description page values
	    String roomsDesc = roomsDescRaw.replaceAll("[^0-9]", "");     // "Room 1" → "1"
	    String guestsDesc = guestsDescRaw.replaceAll("[^0-9]", "");   // "2 Guests" → "2" (if needed)

	    // Logging
	  //  log.ReportEvent("INFO", "Result Page - Rooms: " + roomsResult + ", Guests: " + guestsResult);
	   // log.ReportEvent("INFO", "Desc Page   - Rooms: " + roomsDesc + " ,Guests: " + guestsDesc);

	    // Validate room count
	    if (!roomsResult.equals(roomsDesc)) {
	        log.ReportEvent("FAIL", "Room count mismatch! ResultPage: " + roomsResult + ", DescPage: " + roomsDesc);
	        screenshots.takeScreenShot1();
	    } else {
	        log.ReportEvent("PASS", "Room count matches from hotels result page to description page .");
	    }

	    // Validate guest count
	    if (!guestsResult.equals(guestsDesc)) {
	        log.ReportEvent("FAIL", "Guest count mismatch! ResultPage: " + guestsResult + ", DescPage: " + guestsDesc);
	        screenshots.takeScreenShot1();
	    } else {
	        log.ReportEvent("PASS", "Guest count matches from hotels result page to description page.");
	    }
	}


	//Method to validate amount from result page to description page 
	public void validateHotelAmountFromResultToDescPage(
	        String[] amountFromDescPage,
	        String[] amountFromResultsPage,
	        Log log,
	        ScreenShots screenshots) {

	    if (amountFromDescPage == null || amountFromDescPage.length == 0 || amountFromDescPage[0].isEmpty()) {
	        log.ReportEvent("FAIL", "Amount from description page is null or empty.");
	        screenshots.takeScreenShot1();
	        return;
	    }

	    if (amountFromResultsPage == null || amountFromResultsPage.length == 0 || amountFromResultsPage[0].isEmpty()) {
	        log.ReportEvent("FAIL", "Amount from results page is null or empty.");
	        screenshots.takeScreenShot1();
	        return;
	    }

	    String descAmount = amountFromDescPage[0].trim();
	    String resultAmount = amountFromResultsPage[0].trim();

	  //  log.ReportEvent("INFO", "Amount from Description Page: " + descAmount);
	    //log.ReportEvent("INFO", "Amount from Results Page: " + resultAmount);

	    if (!descAmount.equals(resultAmount)) {
	        log.ReportEvent("FAIL", "Hotel amount mismatch! DescPage: " + descAmount + ", ResultsPage: " + resultAmount);
	        screenshots.takeScreenShot1();
	    } else {
	        log.ReportEvent("PASS", "Hotel amount matches on Hotel Result as well as Description Page");
	    }
	}

	//Method to validate star rating from result page to desc page
	
	public void validateStarRatingFromResultToDescpage(
	        String selectedStarRating,
	        String[] descPageStarRating,
	        Log log,
	        ScreenShots screenshots) {

	    if (selectedStarRating == null || selectedStarRating.isEmpty()) {
	        log.ReportEvent("FAIL", "Selected star rating from results page is null or empty.");
	        screenshots.takeScreenShot1();
	        return;
	    }

	    if (descPageStarRating == null || descPageStarRating.length == 0 || descPageStarRating[0].isEmpty()) {
	        log.ReportEvent("FAIL", "Star rating from description page is null or empty.");
	        screenshots.takeScreenShot1();
	        return;
	    }

	    String descStar = descPageStarRating[0].trim();

	    log.ReportEvent("INFO", "Selected Star Rating on Results Page: " + selectedStarRating);
	    log.ReportEvent("INFO", "Star Rating on Description Page: " + descStar);

	    if (!selectedStarRating.equalsIgnoreCase(descStar)) {
	        log.ReportEvent("FAIL", "Star rating mismatch! Results Page: " + selectedStarRating + ", Desc Page: " + descStar);
	        screenshots.takeScreenShot1();
	    } else {
	        log.ReportEvent("PASS", "Star rating matches on both pages.");
	    }
	}
	
	//Method to click on amenities on description page 
			public void clcikAmenitiesOnDescPage() {
				WebElement button = driver.findElement(By.xpath("//button[text()='Amenities']"));
				button.click();
			}
			
			//Method to get amenities text from description page
			public String getAmenitiesFromDescPage() {
			    try {
			        List<WebElement> amenitiesElements = driver.findElements(By.xpath("//div[@id='amenities']//span"));
			        
			        StringBuilder amenitiesBuilder = new StringBuilder();

			        // Loop through each amenity element
			        for (WebElement element : amenitiesElements) {
			            // Get the text of the element and remove extra spaces
			            String text = element.getText().trim();
			            
			            // If text is not empty, add it to the builder followed by a comma and space
			            if (!text.isEmpty()) {
			                amenitiesBuilder.append(text).append(", ");
			            }
			        }

			        // If the builder has any text, remove the last comma and space
			        if (amenitiesBuilder.length() > 2) {
			            amenitiesBuilder.setLength(amenitiesBuilder.length() - 2);
			        }

			        return amenitiesBuilder.toString();

			    } catch (Exception e) {
			        System.out.println("Error fetching amenities: " + e.getMessage());
			        return "";
			    }
			}


	//Method to click on rooms and book 
			public String[] clickBookButtonForRoomsOnDescPage() throws InterruptedException {
			    // Click on Rooms tab
			    driver.findElement(By.xpath("//button[text()='Rooms']")).click();
			    Thread.sleep(2000);
			    
			    // Find all available room options
			    List<WebElement> roomOptions = driver.findElements(By.xpath("//div[@class='room-options-section__room-card card']//span[@class='room-options-section__room-card_room-type']"));
			    
			    int roomCount = roomOptions.size();
			    
			    // Click the appropriate book button based on room count
			    if (roomCount > 1) {
			        // Multiple rooms available - click 2nd book button
			        List<WebElement> bookButtons = driver.findElements(By.xpath("//button[text()='Book']"));
			        if (bookButtons.size() >= 2) {
			        	Thread.sleep(2000);
			            bookButtons.get(1).click(); // Click 2nd book button
			        } else {
			        	Thread.sleep(1000);
			            bookButtons.get(0).click(); // Fallback to 1st if only one exists
			        }
			    } else if (roomCount == 1) {
			        // Single room available - click 1st book button
			        WebElement bookButton = driver.findElement(By.xpath("//button[text()='Book']"));
			        bookButton.click();
			    } else {
			        // No specific rooms found - click Book Now button
			        WebElement bookNowButton = driver.findElement(By.xpath("//div[text()='Book Now']"));
			        bookNowButton.click();
			    }
			    
			    Thread.sleep(1000); // Wait for page to load
			    
			    // Extract room details - handle multiple rooms by using index
			    List<WebElement> roomTypes = driver.findElements(By.xpath("//span[@class='room-options-section__room-card_room-type']"));
			    List<WebElement> fareTypes = driver.findElements(By.xpath("//div[./span[text()='Highlights']]//span[contains(@class,'font-weight-bold')]"));
			    List<WebElement> prices = driver.findElements(By.xpath("//span[@class='room-options-section__room-card_actual-price']"));
			    List<WebElement> nights = driver.findElements(By.xpath("//span[@class='room-options-section__room-card_nights']"));
			    List<WebElement> roomOnly = driver.findElements(By.xpath("//span[@class='room-options-selection__room-card_meal-plan']"));
			    
			    // Determine which room index to use for extraction
			    int roomIndex = (roomCount > 1) ? 1 : 0; // Use 2nd room if multiple, 1st if single
			    
			    String roomType = (roomTypes.size() > roomIndex) ? roomTypes.get(roomIndex).getText() : "Not found";
			    String fareType = (fareTypes.size() > roomIndex) ? fareTypes.get(roomIndex).getText() : "Not found";
			    String price = (prices.size() > roomIndex) ? prices.get(roomIndex).getText() : "Not found";
			    String nightsText = (nights.size() > roomIndex) ? nights.get(roomIndex).getText() : "Not found";
			    String roomOnlyText = (roomOnly.size() > roomIndex) ? roomOnly.get(roomIndex).getText() : "Not found";
			    
			    // Click terms and conditions for the selected room
			    List<WebElement> termsButtons = driver.findElements(By.xpath("//a[text()='Terms & Conditions']"));
			    if (termsButtons.size() > roomIndex) {
			        termsButtons.get(roomIndex).click();
			    } else if (termsButtons.size() > 0) {
			        termsButtons.get(0).click(); // Fallback to first button
			    }
			    
			    Thread.sleep(1000); // Wait for cancellation details to load
			    
			    // Extract cancellation details
			    String cancellationOnAfter = driver.findElement(By.xpath("//*[contains(@class,'cancellation-policy-from-date')]")).getText();
			    String cancellationOnBefore = driver.findElement(By.xpath("//*[contains(@class,'cancellation-policy-to-date')]")).getText();
			    String cancellationCharges = driver.findElement(By.xpath("//*[contains(@class,'cancellation-policy-penalty')]")).getText();
			    
			    // Return all details
			    return new String[] {
			        roomType,
			        fareType,
			        price,
			        nightsText,
			        roomOnlyText,
			        cancellationOnAfter,
			        cancellationOnBefore,
			        cancellationCharges
			    };
			}
			
//			public String[] clickOnBookButtonForRoomsOnDescPage() throws InterruptedException {
//				
//			    System.out.println("=== Starting Room Booking Process ===");
//
//			    // Step 1: Click on Rooms tab
//			    WebElement roomsTab = driver.findElement(By.xpath("//button[text()='Rooms']"));
//			    Thread.sleep(1000);
//			    roomsTab.click();
//			    System.out.println("Clicked on Rooms tab");
//			    Thread.sleep(4000);
//
//			    // Step 2: Get room cards
//			    List<WebElement> roomCards = driver.findElements(By.xpath("//div[@class='room-options-section__room-card card']"));
//			    Thread.sleep(1000);
//			    int roomCount = roomCards.size();
//			    System.out.println("Room cards found: " + roomCount);
//
//			    int targetIndex = 0;
//
//			    // Step 3: Click Terms & Conditions FIRST (before Book)
//			    if (roomCount > 1) {
//			        targetIndex = 1;
//
//			        List<WebElement> termsLinks = driver.findElements(By.xpath("//a[text()='Terms & Conditions']"));
//			        if (termsLinks.size() > 1) {
//			        	Thread.sleep(1000);
//			            termsLinks.get(1).click();
//			            System.out.println("Clicked 2nd Terms & Conditions");
//			        } else if (!termsLinks.isEmpty()) {
//			            termsLinks.get(0).click();
//			            System.out.println("Fallback: Clicked 1st Terms & Conditions");
//			        }
//
//			    } else if (roomCount == 1) {
//			        targetIndex = 0;
//
//			        List<WebElement> termsLinks = driver.findElements(By.xpath("//a[text()='Terms & Conditions']"));
//			        if (!termsLinks.isEmpty()) {
//			        	Thread.sleep(1000);
//
//			            termsLinks.get(0).click();
//			            System.out.println("Clicked 1st Terms & Conditions");
//			        } else {
//			            System.out.println("No Terms & Conditions found.");
//			        }
//			    } else {
//			        // No room cards found → Click Book Now button
//			        WebElement bookNowBtn = driver.findElement(By.xpath("//div[text()='Book Now']"));
//			        bookNowBtn.click();
//			        System.out.println("Clicked Book Now button");
//			        Thread.sleep(1000);
//			        return new String[] {
//			            "Not found", "Not found", "Not found", "Not found", "Not found",
//			            "Not found", "Not found", "Not found"
//			        };
//			    }
//
//			    Thread.sleep(1000); // Wait for Terms modal or section to load
//
//			    // Step 4: Extract Cancellation Details (after Terms clicked)
//			    List<WebElement> cancelAfterElements = driver.findElements(By.xpath("//*[contains(@class,'cancellation-policy-from-date')]"));
//			    String cancelAfter = cancelAfterElements.isEmpty() ? "Not found" :
//			        cancelAfterElements.stream().map(WebElement::getText).map(String::trim).collect(Collectors.joining(" | "));
//
//			    List<WebElement> cancelBeforeElements = driver.findElements(By.xpath("//*[contains(@class,'cancellation-policy-to-date')]"));
//			    String cancelBefore = cancelBeforeElements.isEmpty() ? "Not found" :
//			        cancelBeforeElements.stream().map(WebElement::getText).map(String::trim).collect(Collectors.joining(" | "));
//
//			    List<WebElement> cancelChargesElements = driver.findElements(By.xpath("//*[contains(@class,'cancellation-policy-penalty')]"));
//			    String cancelCharges = cancelChargesElements.isEmpty() ? "Not found" :
//			        cancelChargesElements.stream().map(WebElement::getText).map(String::trim).collect(Collectors.joining(" | "));
//
//			    System.out.println("=== Cancellation Info ===");
//			    System.out.println("After: " + cancelAfter);
//			    System.out.println("Before: " + cancelBefore);
//			    System.out.println("Charges: " + cancelCharges);
//
//			    Thread.sleep(1000);
//
//			    // Step 5: Now Click Book Button
//			    
//			    
//			    List<WebElement> bookButtons = driver.findElements(By.xpath("//button[text()='Book']"));
//			    if (roomCount > 1 && bookButtons.size() > 1) {
//		        	Thread.sleep(1000);
//
//			        bookButtons.get(1).click();
//			        System.out.println("Clicked 2nd Book button");
//			    } else if (roomCount == 1 && !bookButtons.isEmpty()) {
//		        	Thread.sleep(1000);
//
//			        bookButtons.get(0).click();
//			        System.out.println("Clicked 1st Book button");
//			    }
//
//			    Thread.sleep(1000);
//
//			    // Step 6: Extract Room Details
//			    List<WebElement> roomTypeElements = driver.findElements(By.xpath("//span[@class='room-options-section__room-card_room-type']"));
//			    String roomType = (roomTypeElements.size() > targetIndex) ? roomTypeElements.get(targetIndex).getText().trim() : "Not found";
//
//			    List<WebElement> fareTypeElements = driver.findElements(By.xpath("//div[./span[text()='Highlights']]//span[contains(@class,'font-weight-bold')]"));
//			    String fareType = (fareTypeElements.size() > targetIndex) ? fareTypeElements.get(targetIndex).getText().trim() : "Not found";
//
//			    List<WebElement> priceElements = driver.findElements(By.xpath("//span[@class='room-options-section__room-card_actual-price']"));
//			    String price = (priceElements.size() > targetIndex) ? priceElements.get(targetIndex).getText().trim() : "Not found";
//
//			    List<WebElement> nightsElements = driver.findElements(By.xpath("//span[@class='room-options-section__room-card_nights']"));
//			    String nightsText = (nightsElements.size() > targetIndex) ? nightsElements.get(targetIndex).getText().trim() : "Not found";
//
//			    List<WebElement> mealPlanElements = driver.findElements(By.xpath("//span[@class='room-options-selection__room-card_meal-plan']"));
//			    String mealPlan = (mealPlanElements.size() > targetIndex) ? mealPlanElements.get(targetIndex).getText().trim() : "Not found";
//
//			    System.out.println("Room Details => Type: " + roomType + ", Fare: " + fareType + ", Price: " + price + ", Nights: " + nightsText + ", Meal Plan: " + mealPlan);
//
//			    return new String[] {
//			        roomType, fareType, price, nightsText, mealPlan,
//			        cancelAfter, cancelBefore, cancelCharges
//			    };
//			}
//			
			
			public String[] clickOnBookButtonForRoomsOnDescPage(int roomIndex) throws InterruptedException {
			    System.out.println("=== Starting Room Booking Process ===");

			    // Step 1: Click Rooms Tab
			    WebElement roomsTab = driver.findElement(By.xpath("//button[text()='Rooms']"));
			    Thread.sleep(1000);
			    roomsTab.click();
			    System.out.println("Clicked on Rooms tab");
			    Thread.sleep(4000);

			    // Step 2: Get all room cards
			    List<WebElement> roomCards = driver.findElements(By.xpath("//div[@class='room-options-section__room-card card']"));
			    int roomCount = roomCards.size();
			    System.out.println("Room cards found: " + roomCount);

			    WebElement selectedCard = null;

			    // Step 3: Check if no rooms are available
			    if (roomCount == 0) {
			        WebElement bookNowBtn = driver.findElement(By.xpath("//div[text()='Book Now']"));
			        bookNowBtn.click();
			        System.out.println("Clicked Book Now button");
			        Thread.sleep(1000);
			        return new String[] {
			            "Not found", "Not found", "Not found", "Not found", "Not found",
			            "Not found", "Not found", "Not found"
			        };
			    }

			    // Step 4: Adjust for 1-based input
			    int adjustedIndex = roomIndex - 1;

			    // Step 5: Validate room index
			    if (adjustedIndex < 0 || adjustedIndex >= roomCount) {
			        System.out.println("Invalid room index provided: " + roomIndex + ". Defaulting to first room.");
			        adjustedIndex = 0;
			    }

			    selectedCard = roomCards.get(adjustedIndex);
			    System.out.println("Selected room card at index: " + roomIndex + " (zero-based: " + adjustedIndex + ")");

			    // Step 6: Click Terms & Conditions inside selected room card
			    try {
			        WebElement termsLink = selectedCard.findElement(By.xpath(".//a[text()='Terms & Conditions']"));
			        Thread.sleep(1000);
			        termsLink.click();
			        System.out.println("Clicked Terms & Conditions inside selected card");
			    } catch (NoSuchElementException e) {
			        System.out.println("No Terms & Conditions found in selected card.");
			    }

			    Thread.sleep(1000); // Wait for modal to load

			    // Step 7: Get cancellation info globally
			    String cancelAfter = getTextFromElements("//*[contains(@class,'cancellation-policy-from-date')]");
			    String cancelBefore = getTextFromElements("//*[contains(@class,'cancellation-policy-to-date')]");
			    String cancelCharges = getTextFromElements("//*[contains(@class,'cancellation-policy-penalty')]");

//			    System.out.println("=== Cancellation Info ===");
//			    System.out.println("After: " + cancelAfter);
//			    System.out.println("Before: " + cancelBefore);
//			    System.out.println("Charges: " + cancelCharges);

			    Thread.sleep(1000);
			    
			    // Step 8: Click Book button inside selected card
			    try {
			        WebElement bookButton = selectedCard.findElement(By.xpath(".//button[text()='Book']"));

			        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bookButton);

			        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			        wait.until(ExpectedConditions.elementToBeClickable(bookButton));

			        bookButton.click();
			        System.out.println("Clicked Book button inside selected card");
			    } catch (Exception e) {
			        System.out.println("Failed to click Book button: " + e.getMessage());
			    }

			    // Step 9: Extract room details
			    String roomType = getTextFromChild(selectedCard, ".//span[contains(@class,'room-card_room-type')]");
			    String fareType = getTextFromChild(selectedCard, ".//div[./span[text()='Highlights']]//span[contains(@class,'font-weight-bold')]");
			    String price = getTextFromChild(selectedCard, ".//span[contains(@class,'room-card_actual-price')]");
			    String nightsText = getTextFromChild(selectedCard, ".//span[contains(@class,'room-card_nights')]");
			    String mealPlan = getTextFromChild(selectedCard, ".//span[contains(@class,'room-card_meal-plan')]");

			    System.out.println("Room Details => Type: " + roomType + ", Fare: " + fareType + ", Price: " + price + ", Nights: " + nightsText + ", Meal Plan: " + mealPlan);

//			    return new String[] {
//			        roomType, fareType, price, nightsText, mealPlan,
//			        cancelAfter, cancelBefore, cancelCharges
//			    };
			    
			    return new String[] {
				        roomType, fareType, price, nightsText, mealPlan
				       
				    };
			}

			private String getTextFromChild(WebElement parent, String xpath) {
			    try {
			        WebElement el = parent.findElement(By.xpath(xpath));
			        return el.getText().trim();
			    } catch (NoSuchElementException e) {
			        return "Not found";
			    }
			}

			private String getTextFromElements(String xpath) {
			    List<WebElement> elements = driver.findElements(By.xpath(xpath));
			    if (elements.isEmpty()) return "Not found";
			    return elements.stream().map(WebElement::getText).map(String::trim).collect(Collectors.joining(" | "));
			}

		
			
//Method to validate facilities text from result page hotel card to description page 
			public void validateFacilitiesTitleWithDescPageAmenities(String facilitiesTitleText, String amenitiesText, Log Log, ScreenShots ScreenShots) {
			    boolean isPass = true;

			    if (facilitiesTitleText == null || facilitiesTitleText.isEmpty()) {
			        Log.ReportEvent("FAIL", "Facilities title text from result page is null or empty.");
			        ScreenShots.takeScreenShot1();
			        isPass = false;
			    } else if (amenitiesText == null || amenitiesText.isEmpty()) {
			        Log.ReportEvent("FAIL", "Amenities text from description page is null or empty.");
			        ScreenShots.takeScreenShot1();
			        isPass = false;
			    } else {
			        // Split both strings into arrays by comma
			        String[] facilitiesArr = facilitiesTitleText.toLowerCase().split(",");
			        String[] amenitiesArr = amenitiesText.toLowerCase().split(",");

			        // Trim spaces from each item
			        for (int i = 0; i < facilitiesArr.length; i++) {
			            facilitiesArr[i] = facilitiesArr[i].trim();
			        }
			        for (int i = 0; i < amenitiesArr.length; i++) {
			            amenitiesArr[i] = amenitiesArr[i].trim();
			        }

			        // Check if every facility in facilitiesArr is present in amenitiesArr
			        for (String facility : facilitiesArr) {
			            boolean found = false;
			            for (String amenity : amenitiesArr) {
			                if (amenity.equals(facility)) {
			                    found = true;
			                    break;
			                }
			            }
			            if (!found) {
			                Log.ReportEvent("FAIL", "Facility '" + facility.toUpperCase() + "' NOT found in description page amenities.");
			                ScreenShots.takeScreenShot1();
			                isPass = false;
			            }
			        }

			        if (isPass) {
			            Log.ReportEvent("PASS", "Facilities title from result page is present in description page amenities.");
			        }
			    }

			    if (isPass) {
			        Log.ReportEvent("PASS", "Facilities validation passed successfully from result to description page.");
			    } else {
			        Log.ReportEvent("FAIL", "Facilities validation failed from result to description page.");
	                ScreenShots.takeScreenShot1();

			    }
			}


			//Method to get the policy from Hotel Details Page
			public List<String> getFormattedCancellationPoliciesByIndex(int index) {
			    List<String> formattedPolicies = new ArrayList<>();
			    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			    try {
			       // 1. Check Refundability
			       boolean isNonRefundable = driver.findElements(By.xpath("(//span[text()='Non-Refundable'])[" + index + "]")).size() > 0;
			       boolean isRefundable = driver.findElements(By.xpath("(//span[text()='Refundable'])[" + index + "]")).size() > 0;
			       if (!isNonRefundable && !isRefundable) {
			          System.out.println("❌ Refundability status not found.");
			          return formattedPolicies;
			       }
			       // 2. Click "Terms & Conditions"
			       WebElement termsLink = driver.findElement(By.xpath("(//a[text()='Terms & Conditions'])[" + index + "]"));
			       ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", termsLink);
			       Thread.sleep(500);
			       termsLink.click();
			       // 3. Wait for cancellation policy section
			       wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cancellation-policy-from-date")));
			       // 4. Extract data
			       List<WebElement> fromDates = driver.findElements(By.className("cancellation-policy-from-date"));
			       List<WebElement> toDates = driver.findElements(By.className("cancellation-policy-to-date"));
			       List<WebElement> penalties = driver.findElements(By.className("cancellation-policy-penalty"));
			       int count = Math.min(fromDates.size(), Math.min(toDates.size(), penalties.size()));
			       DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			       DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
			       for (int i = 0; i < count; i++) {
			          String fromText = fromDates.get(i).getText().trim();
			          String toText = toDates.get(i).getText().trim();
			          String penaltyText = penalties.get(i).getText().trim(); // e.g., "51.78 (percentage)" or "0 (amount)"
			          LocalDateTime fromDate = LocalDateTime.parse(fromText, inputFormat);
			          LocalDateTime toDate = LocalDateTime.parse(toText, inputFormat);
			          String penaltyValue = "0.0";
			          String penaltyType = "";
			          int openParen = penaltyText.indexOf("(");
			          int closeParen = penaltyText.indexOf(")");
			          if (openParen > 0 && closeParen > openParen) {
			             String value = penaltyText.substring(0, openParen).trim();
			             try {
			                penaltyValue = String.format("%.2f", Double.parseDouble(value));
			             } catch (NumberFormatException e) {
			                penaltyValue = "0.0";
			             }
			             penaltyType = penaltyText.substring(openParen + 1, closeParen).trim();
			          }
			          // Build final format based on penalty type
			          String formatted;
			          if (penaltyType.equalsIgnoreCase("amount")) {
			             formatted = String.format(
			                   "From %s , To: %s , Penalty will be amount of %s",
			                   fromDate.format(outputFormat),
			                   toDate.format(outputFormat),
			                   penaltyValue
			             );
			          } else if (penaltyType.equalsIgnoreCase("percentage")) {
			             formatted = String.format(
			                   "From %s , To: %s , Penalty will be %s percentage",
			                   fromDate.format(outputFormat),
			                   toDate.format(outputFormat),
			                   penaltyValue
			             );
			          } else {
			             formatted = String.format(
			                   "From %s , To: %s , Penalty will be %s of %s",
			                   fromDate.format(outputFormat),
			                   toDate.format(outputFormat),
			                   penaltyType,
			                   penaltyValue
			             );
			          }
			          formattedPolicies.add(formatted);
			       }
			       // 5. Handle special case: Non-refundable with no rows
			       if (isNonRefundable && formattedPolicies.isEmpty()) {
			          if (!fromDates.isEmpty() && !toDates.isEmpty()) {
			             LocalDateTime fromDate = LocalDateTime.parse(fromDates.get(0).getText().trim(), inputFormat);
			             LocalDateTime toDate = LocalDateTime.parse(toDates.get(0).getText().trim(), inputFormat);
			             String formatted = String.format(
			                   "From %s , To: %s , Penalty will be 100.0 percentage",
			                   fromDate.format(outputFormat),
			                   toDate.format(outputFormat)
			             );
			             formattedPolicies.add(formatted);
			          } else {
			             formattedPolicies.add("From <start-date> , To: <end-date> , Penalty will be 100.0 percentage");
			          }
			       }
			    } catch (Exception e) {
			       System.out.println("❌ Error extracting cancellation policies: " + e.getMessage());
			    }
			    return formattedPolicies;
			}
			 	
			


}
