package com.tripgain.collectionofpages;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;

public class SkyTravelers_Hotels_BookingPage {
	WebDriver driver;

	public SkyTravelers_Hotels_BookingPage (WebDriver driver)
	{
		PageFactory.initElements(driver, this);
		this.driver=driver;
	}
	
	//Method to get hotel name text from booking p
	public String[] getHotelNameFromBookingPage() {
	    String hotelName = driver.findElement(By.xpath("//*[@class='booking-review-page__body__booking-details_hotel-name']")).getText();
        System.out.println("Hotel name from booking page : " + hotelName);
	    return new String[]{hotelName};
	}	
	
	
	public String[] getHotelLocationFromBookingPage() {
	    String hotelLLoc = driver.findElement(By.xpath("//*[@class='booking-review-page__body__booking-details_hotel-location']")).getText();
        System.out.println("Hotel Location from booking page : " + hotelLLoc);
	    return new String[]{hotelLLoc};
	}	
	
	public String[] getHotelCheckInDateFromBookingPage() {
	    String hotelcheckInDate = driver.findElement(By.xpath("//span[text()='Check In']/following-sibling::span[contains(@class,'booking-review-page__body__booking-details_check-in-card_date')]")).getText();
        System.out.println("Hotel checkInDate from booking page : " + hotelcheckInDate);
	    return new String[]{hotelcheckInDate};
	}	
	
	public String[] gethotelcheckInMonthFromBookingPage() {
	    String hotelcheckInMon = driver.findElement(By.xpath("//span[text()='Check In']/following-sibling::span[@class='booking-review-page__body__booking-details_check-in-card_month']")).getText();
        System.out.println("Hotel CheckIn Month from booking page : " + hotelcheckInMon);
	    return new String[]{hotelcheckInMon};
	}	
	
	public String[] getHotelCheckOutDateFromBookingPage() {
	    String hotelcheckOutDate = driver.findElement(By.xpath("//span[text()='Check-Out']/following-sibling::span[contains(@class,'booking-review-page__body__booking-details_check-in-card_date')]")).getText();
        System.out.println("Hotel checkoutDate from booking page : " + hotelcheckOutDate);
	    return new String[]{hotelcheckOutDate};
	}	
	
	public String[] gethotelcheckOutMonthFromBookingPage() {
	    String hotelcheckOutMon = driver.findElement(By.xpath("//span[text()='Check-Out']/following-sibling::span[@class='booking-review-page__body__booking-details_check-in-card_month']")).getText();
        System.out.println("Hotel check out month from booking page : " + hotelcheckOutMon);
	    return new String[]{hotelcheckOutMon};
	}	
	
	public String[] getRoomTextFromBookingPage() {
	    String roomText = driver.findElement(By.xpath("//*[@class='booking-review-page__body__room-details  booking-review-page__body__room-details_room-type']//div[contains(@class,'fs-16 fw-600')]")).getText();
        System.out.println("Room text from booking page : " + roomText);
	    return new String[]{roomText};
	}	
	
	public String[] getNightsFromBookingPage() {
	    String nightsText = driver.findElement(By.xpath("//*[contains(@class,'fs-16 fw-600 px-2 mb-1')]")).getText();
        System.out.println("nights Textfrom booking page : " + nightsText);
	    return new String[]{nightsText};
	}	
	
	
	public String[] getMealPlanFromBookingPage() {
	    String mealPlanText = driver.findElement(By.xpath("//span[text()='Meal Plan']/following-sibling::span")).getText();
        System.out.println("Meal plan text from booking page : " + mealPlanText);
	    return new String[]{mealPlanText};
	}	
	
	public String[] getCancellationPolicyromBookingPage() {
	    String CancellationPolicyText = driver.findElement(By.xpath("//span[contains(text(),'Cancellation Policy')]/following-sibling::span/div")).getText();
        System.out.println("Cancellation Policy Text from booking page : " + CancellationPolicyText);
	    return new String[]{CancellationPolicyText};
	}	
	
	public String[] getPeopleCountromBookingPage() {
	    String PeopleCountText = driver.findElement(By.xpath("//*[contains(@class,'fs-14 mb-2')]")).getText();
        System.out.println("People Count Text from booking page : " + PeopleCountText);
	    return new String[]{PeopleCountText};
	}	
	
	public String[] getHotelChargesBookingPage() {
	    String HotelchargesText = driver.findElement(By.xpath("//*[contains(@class,'hotel-booking-hotel_charges')]")).getText();
        System.out.println("Hotel charges Text from booking page : " + HotelchargesText);
	    return new String[]{HotelchargesText};
	}	
	
	public String[] getTaxAndServicesFromBookingPage() {
	    String taxAndServicesText = driver.findElement(By.xpath("//*[contains(@class,'hotel-booking-taxes_service_charges')]")).getText();
        System.out.println("taxAndServices Text from booking page : " + taxAndServicesText);
	    return new String[]{taxAndServicesText};
	}	
	
	public String[] getTotalAmountFromBookingPage() {
	    String totalAmountText = driver.findElement(By.xpath("//*[contains(@class,'hotel-booking-total_fare')]")).getText();
        System.out.println("total Amount Text from booking page : " + totalAmountText);
	    return new String[]{totalAmountText};
	}	

	//Method to validate hotel name from desc page to booking page 
	public void validateHotelNameFromDescAndBookingPage(String hotelNameFromDesc, String[] hotelNameFromBookingArray, Log Log, ScreenShots ScreenShots) {
	    boolean isPass = true;

	    if (hotelNameFromDesc == null || hotelNameFromDesc.isEmpty()) {
	        Log.ReportEvent("FAIL", "Hotel name from Description Page is null or empty.");
	        ScreenShots.takeScreenShot1();
	        isPass = false;
	    } 
	    else if (hotelNameFromBookingArray == null || hotelNameFromBookingArray.length == 0 || hotelNameFromBookingArray[0].isEmpty()) {
	        Log.ReportEvent("FAIL", "Hotel name from Booking Page is null, empty or missing.");
	        ScreenShots.takeScreenShot1();
	        isPass = false;
	    } 
	    else {
	        String hotelNameFromBooking = hotelNameFromBookingArray[0].trim();
	        if (hotelNameFromDesc.equalsIgnoreCase(hotelNameFromBooking)) {
	           // Log.ReportEvent("PASS", "Hotel names match between Description Page and Booking Page: '" + hotelNameFromDesc + "'");
	        } else {
	            Log.ReportEvent("FAIL", "Hotel names do not match. Description Page: '" + hotelNameFromDesc + "' | Booking Page: '" + hotelNameFromBooking + "'");
	            ScreenShots.takeScreenShot1();
	            isPass = false;
	        }
	    }

	    if (isPass) {
	        Log.ReportEvent("PASS", "Hotel name validation passed between Description and Booking pages.");
	    } else {
	        Log.ReportEvent("FAIL", "Hotel name validation failed between Description and Booking pages.");
	    }
	}

	//Method to validate checkindate from desc page to booking page 
	public void validateCheckInDateFromDescAndBookingPage(String[] descCheckInDate, String[] bookingCheckInDate, Log Log, ScreenShots ScreenShots) {
	    boolean isPass = true;

	    String descDate = (descCheckInDate != null && descCheckInDate.length > 0) ? descCheckInDate[0].trim() : "";
	    String bookingDate = (bookingCheckInDate != null && bookingCheckInDate.length > 0) ? bookingCheckInDate[0].trim() : "";

	    // Check if any date is missing
	    if (descDate.isEmpty()) {
	        Log.ReportEvent("FAIL", "Check-in date from Description Page is empty or not found.");
	        ScreenShots.takeScreenShot1();
	        isPass = false;
	    }

	    if (bookingDate.isEmpty()) {
	        Log.ReportEvent("FAIL", "Check-in date from Booking Page is empty or not found.");
	        ScreenShots.takeScreenShot1();
	        isPass = false;
	    }

	    // Compare dates
	    if (!descDate.isEmpty() && !bookingDate.isEmpty()) {
	        if (descDate.equalsIgnoreCase(bookingDate)) {
	          //  Log.ReportEvent("PASS", "Check-in dates match: '" + descDate + "'");
	        } else {
	            Log.ReportEvent("FAIL", "Check-in dates do not match. Description Page: '" + descDate + "' | Booking Page: '" + bookingDate + "'");
	            ScreenShots.takeScreenShot1();
	            isPass = false;
	        }
	    }

	    // Final log
	    if (isPass) {
	        Log.ReportEvent("PASS", "Check-in date validation passed successfully From Description To Booking page .");
	    } else {
	        Log.ReportEvent("FAIL", "Check-in date validation failed From Description To Booking page.");
	    }
	}

	public void validateCheckInMonthFromDescAndBookingPage(String[] descCheckInMonthArray, String[] bookingCheckInMonthArray, Log Log, ScreenShots ScreenShots) {
	    boolean isPass = true;

	    String descMonth = (descCheckInMonthArray != null && descCheckInMonthArray.length > 0) ? descCheckInMonthArray[0].trim() : "";
	    String bookingMonth = (bookingCheckInMonthArray != null && bookingCheckInMonthArray.length > 0) ? bookingCheckInMonthArray[0].trim() : "";

	    // Check if either is empty
	    if (descMonth.isEmpty()) {
	        Log.ReportEvent("FAIL", "Check-in month from Description Page is empty or not found.");
	        ScreenShots.takeScreenShot1();
	        isPass = false;
	    }

	    if (bookingMonth.isEmpty()) {
	        Log.ReportEvent("FAIL", "Check-in month from Booking Page is empty or not found.");
	        ScreenShots.takeScreenShot1();
	        isPass = false;
	    }

	    // Compare if both values are present
	    if (!descMonth.isEmpty() && !bookingMonth.isEmpty()) {
	        if (descMonth.equalsIgnoreCase(bookingMonth)) {
	         //   Log.ReportEvent("PASS", "Check-in months match: '" + descMonth + "'");
	        } else {
	            Log.ReportEvent("FAIL", "Check-in months do not match. Description Page: '" + descMonth + "' | Booking Page: '" + bookingMonth + "'");
	            ScreenShots.takeScreenShot1();
	            isPass = false;
	        }
	    }

	    // Final result
	    if (isPass) {
	        Log.ReportEvent("PASS", "Check-in month validation passed successfully From Description To Booking page.");
	    } else {
	        Log.ReportEvent("FAIL", "Check-in month validation failed From Description To Booking page.");
	    }
	}

	public void validateCheckOutMonthFromDescAndBookingPage(String[] descCheckOutMonthArray, String[] bookingCheckOutMonthArray, Log Log, ScreenShots ScreenShots) {
	    if (descCheckOutMonthArray != null && descCheckOutMonthArray.length > 0
	        && bookingCheckOutMonthArray != null && bookingCheckOutMonthArray.length > 0) {

	        String descMonth = descCheckOutMonthArray[0].trim();
	        String bookingMonth = bookingCheckOutMonthArray[0].trim();

	        if (descMonth.equalsIgnoreCase(bookingMonth)) {
	            Log.ReportEvent("PASS", "Check-out months match From Description To Booking page: '" + descMonth + "'");
	        } else {
	            Log.ReportEvent("FAIL", "Check-out months do not match. Description Page: '" + descMonth + "' | Booking Page: '" + bookingMonth + "'");
	            ScreenShots.takeScreenShot1();
	        }
	    } else {
	        Log.ReportEvent("FAIL", "Check-out month data missing on one or both pages.");
	        ScreenShots.takeScreenShot1();
	    }
	}

	public void validateCheckOutDateFromDescAndBookingPage(String[] descCheckOutDateArray, String[] bookingCheckOutDateArray, Log Log, ScreenShots ScreenShots) {
	    if (descCheckOutDateArray != null && descCheckOutDateArray.length > 0
	        && bookingCheckOutDateArray != null && bookingCheckOutDateArray.length > 0) {

	        String descDate = descCheckOutDateArray[0].trim();
	        String bookingDate = bookingCheckOutDateArray[0].trim();

	        if (descDate.equalsIgnoreCase(bookingDate)) {
	            Log.ReportEvent("PASS", "Check-out dates match From Description To Booking page: '" + descDate + "'");
	        } else {
	            Log.ReportEvent("FAIL", "Check-out dates do not match. Description Page: '" + descDate + "' | Booking Page: '" + bookingDate + "'");
	            ScreenShots.takeScreenShot1();
	        }
	    } else {
	        Log.ReportEvent("FAIL", "Check-out date data missing on one or both pages.");
	        ScreenShots.takeScreenShot1();
	    }
	}

	public void validateGuestCountFromDescAndBookingPage(
		    String[] descGuestCountArray, 
		    String[] bookingPeopleCountArray, 
		    Log Log, 
		    ScreenShots ScreenShots) {

		    if (descGuestCountArray != null && descGuestCountArray.length > 0
		        && bookingPeopleCountArray != null && bookingPeopleCountArray.length > 0) {

		        String descGuestCount = descGuestCountArray[0].trim();
		        String bookingPeopleCountRaw = bookingPeopleCountArray[0].trim();

		        // Extract number from booking page string (e.g., "For 2 People" → "2")
		        String bookingGuestCount = bookingPeopleCountRaw.replaceAll("\\D+", ""); // Keep only digits

		        if (descGuestCount.equals(bookingGuestCount)) {
		            Log.ReportEvent("PASS", "Guest count matches From Description To Booking page: '" + descGuestCount + "'");
		        } else {
		            Log.ReportEvent("FAIL", "Guest count does not match. Description Page: '" + descGuestCount + "' | Booking Page: '" + bookingPeopleCountRaw + "'");
		            ScreenShots.takeScreenShot1();
		        }
		    } else {
		        Log.ReportEvent("FAIL", "Guest count data missing on one or both pages.");
		        ScreenShots.takeScreenShot1();
		    }
		}

	
	public void validateRoomTypeFromDescAndBookingPage(String[] descRoomDetails, String[] bookingRoomTextArray, Log Log, ScreenShots ScreenShots) {
	    if (descRoomDetails != null && descRoomDetails.length > 0
	        && bookingRoomTextArray != null && bookingRoomTextArray.length > 0) {

	        String descRoomType = descRoomDetails[0].trim();   // Room Type is first element in descRoomDetails array
	        String bookingRoomText = bookingRoomTextArray[0].trim();

	        if (descRoomType.equalsIgnoreCase(bookingRoomText)) {
	            Log.ReportEvent("PASS", "Room type matches From Description To Booking page: '" + descRoomType + "'");
	        } else {
	            Log.ReportEvent("FAIL", "Room type does not match. Desc Page: '" + descRoomType + "' | Booking Page: '" + bookingRoomText + "'");
	            ScreenShots.takeScreenShot1();
	        }
	    } else {
	        Log.ReportEvent("FAIL", "Room type data missing on one or both pages.");
	        ScreenShots.takeScreenShot1();
	    }
	}
	
//	public void validateNightsFromDescAndBookingPage(String[] descNightDetails, String[] bookingNightsArray, Log Log, ScreenShots ScreenShots) {
//	    if (descNightDetails != null && descNightDetails.length > 3  // nights is 4th element (index 3)
//	        && bookingNightsArray != null && bookingNightsArray.length > 0) {
//
//	        String descNights = descNightDetails[3].trim(); // nightsText is 4th element
//	        String bookingNights = bookingNightsArray[0].trim();
//
//	        if (descNights.equalsIgnoreCase(bookingNights)) {
//	            Log.ReportEvent("PASS", "Nights count matches From Description To Booking page: '" + descNights + "'");
//	        } else {
//	            Log.ReportEvent("FAIL", "Nights count does not match. Desc Page: '" + descNights + "' | Booking Page: '" + bookingNights + "'");
//	            ScreenShots.takeScreenShot1();
//	        }
//	    } else {
//	        Log.ReportEvent("FAIL", "Nights count data missing on one or both pages.");
//	        ScreenShots.takeScreenShot1();
//	    }
//	}
//
	public void validateNightsFromDescAndBookingPage(
		    String[] descNightDetails, 
		    String[] bookingNightsArray, 
		    Log Log, 
		    ScreenShots ScreenShots) {

		    if (descNightDetails != null && descNightDetails.length > 3
		        && bookingNightsArray != null && bookingNightsArray.length > 0) {

		        String descNightsText = descNightDetails[3].trim();       // e.g., "for 5 Night(s)"
		        String bookingNightsText = bookingNightsArray[0].trim();  // e.g., "5 Days & 5 Night(s)"

		        // Extract the first number from both strings (assumes it's the night count)
		        String descNights = extractNightsCount(descNightsText);
		        String bookingNights = extractNightsCount(bookingNightsText);

		        if (descNights.equals(bookingNights)) {
		            Log.ReportEvent("PASS", "Nights count matches: " + descNights);
		        } else {
		            Log.ReportEvent("FAIL", "Nights count does not match. Desc Page: '" + descNightsText + "' | Booking Page: '" + bookingNightsText + "'");
		            ScreenShots.takeScreenShot1();
		        }
		    } else {
		        Log.ReportEvent("FAIL", "Nights count data missing on one or both pages.");
		        ScreenShots.takeScreenShot1();
		    }
		}

		// Helper method to extract the number of nights
		private String extractNightsCount(String text) {
		    Matcher matcher = Pattern.compile("(\\d+)\\s*Night").matcher(text);
		    if (matcher.find()) {
		        return matcher.group(1); // Return the numeric night count
		    }
		    return "0"; // fallback if no match found
		}


	public void validateMealPlanFromDescAndBookingPage(String[] descRoomDetails, String[] bookingMealPlanArray, Log Log, ScreenShots ScreenShots) {
	    if (descRoomDetails != null && descRoomDetails.length > 4  // mealPlan is 5th element (index 4)
	        && bookingMealPlanArray != null && bookingMealPlanArray.length > 0) {

	        String descMealPlan = descRoomDetails[4].trim(); // mealPlan from desc page
	        String bookingMealPlan = bookingMealPlanArray[0].trim();

	        if (descMealPlan.equalsIgnoreCase(bookingMealPlan)) {
	            Log.ReportEvent("PASS", "Meal Plan matches From Description To Booking page: '" + descMealPlan + "'");
	        } else {
	            Log.ReportEvent("FAIL", "Meal Plan does not match. Desc Page: '" + descMealPlan + "' | Booking Page: '" + bookingMealPlan + "'");
	            ScreenShots.takeScreenShot1();
	        }
	    } else {
	        Log.ReportEvent("FAIL", "Meal Plan data missing on one or both pages.");
	        ScreenShots.takeScreenShot1();
	    }
	}

//	public void validateCancellationPolicyFromDescAndBookingPage(String[] descDetails, String[] bookingCancellationPolicyArray, Log Log, ScreenShots ScreenShots) {
//	    if (descDetails == null || descDetails.length < 8) {
//	        Log.ReportEvent("FAIL", "Description page cancellation details are missing or incomplete.");
//	        ScreenShots.takeScreenShot1();
//	        return;
//	    }
//
//	    if (bookingCancellationPolicyArray == null || bookingCancellationPolicyArray.length == 0) {
//	        Log.ReportEvent("FAIL", "Booking page cancellation policy text is missing.");
//	        ScreenShots.takeScreenShot1();
//	        return;
//	    }
//
//	    String cancelAfter = descDetails[5].trim();
//	    String cancelBefore = descDetails[6].trim();
//	    String cancelCharges = descDetails[7].trim();
//
//	    String bookingCancellationPolicy = bookingCancellationPolicyArray[0].trim();
//
//	    // Simple checks: Verify if cancelAfter, cancelBefore, and cancelCharges appear somewhere in booking cancellation policy text
//	    boolean afterMatch = bookingCancellationPolicy.contains(cancelAfter);
//	    boolean beforeMatch = bookingCancellationPolicy.contains(cancelBefore);
//	    boolean chargesMatch = bookingCancellationPolicy.contains(cancelCharges);
//
//	    if (afterMatch && beforeMatch && chargesMatch) {
//	        Log.ReportEvent("PASS", "Cancellation policy matches between description and booking page.");
//	    } else {
//	        Log.ReportEvent("FAIL", "Cancellation policy mismatch.\n" +
//	            "Desc Page => After: " + cancelAfter + ", Before: " + cancelBefore + ", Charges: " + cancelCharges + "\n" +
//	            "Booking Page => " + bookingCancellationPolicy);
//	        ScreenShots.takeScreenShot1();
//	    }
//	}
	
	public void validateHotelLocationFromResultsAndBookingPage(
		    String[] resultsPageLocationArray, 
		    String[] bookingPageLocationArray, 
		    Log Log, 
		    ScreenShots ScreenShots) {

		    if (resultsPageLocationArray != null && resultsPageLocationArray.length > 0
		        && bookingPageLocationArray != null && bookingPageLocationArray.length > 0) {

		        String resultsLocation = resultsPageLocationArray[0].trim();
		        String bookingLocation = bookingPageLocationArray[0].trim();

		        if (resultsLocation.equalsIgnoreCase(bookingLocation)) {
		            Log.ReportEvent("PASS", "Hotel locations match From Result To Booking page: '" + resultsLocation + "'");
		        } else {
		            Log.ReportEvent("FAIL", "Hotel locations do not match. Results Page: '" + resultsLocation + "' | Booking Page: '" + bookingLocation + "'");
		            ScreenShots.takeScreenShot1();
		        }
		    } else {
		        Log.ReportEvent("FAIL", "Hotel location data missing on one or both pages.");
		        ScreenShots.takeScreenShot1();
		    }
		}

	public void validateHotelPriceFromDescAndBookingPage(
		    String[] descPagePriceArray, 
		    String[] bookingPagePriceArray, 
		    Log Log, 
		    ScreenShots ScreenShots) {

		    if (descPagePriceArray != null && descPagePriceArray.length > 2
		        && bookingPagePriceArray != null && bookingPagePriceArray.length > 0) {

		        String descPrice = descPagePriceArray[2].trim(); // Price from Description page 
		        String bookingPrice = bookingPagePriceArray[0].trim(); // Price from Booking page

		        if (descPrice.equalsIgnoreCase(bookingPrice)) {
		            Log.ReportEvent("PASS", "Hotel prices match From Description To Booking page: '" + descPrice + "'");
		        } else {
		            Log.ReportEvent("FAIL", "Hotel prices do not match. Description Page: '" + descPrice + "' | Booking Page: '" + bookingPrice + "'");
		            ScreenShots.takeScreenShot1();
		        }
		    } else {
		        Log.ReportEvent("FAIL", "Hotel price data missing on one or both pages.");
		        ScreenShots.takeScreenShot1();
		    }
		}
	
	//Method to get final amount from booking page 
	public String[] getFinalBottomAmountFromBookingPage() {
	    String bottomAmountText = driver.findElement(By.xpath("//*[contains(@class,'hotel-booking-final-total-fare')]")).getText();
        System.out.println("Final Bottom amount text from booking page : " + bottomAmountText);
	    return new String[]{bottomAmountText};
	}	
	
	public void validateTotalAndFinalBottomAmountFromBookingPage(
		    String[] totalAmountArray,
		    String[] finalBottomAmountArray,
		    Log Log,
		    ScreenShots ScreenShots) {

		    if (totalAmountArray != null && totalAmountArray.length > 0 &&
		        finalBottomAmountArray != null && finalBottomAmountArray.length > 0) {

		        String totalAmount = totalAmountArray[0].trim().replaceAll("[^\\d.]", "");
		        String finalAmount = finalBottomAmountArray[0].trim().replaceAll("[^\\d.]", "");

		        if (totalAmount.equals(finalAmount)) {
		            Log.ReportEvent("PASS", "Total amount and final bottom amount match: " + totalAmount);
		        } else {
		            Log.ReportEvent("FAIL", "Amount mismatch. Total Amount: '" + totalAmount + "' | Final Bottom Amount: '" + finalAmount + "'");
		            ScreenShots.takeScreenShot1();
		        }

		    } else {
		        Log.ReportEvent("FAIL", "One or both amount values are missing.");
		        ScreenShots.takeScreenShot1();
		    }
		}

//Method to enetr email data 
	public void enterEmail(String email) {
	    try {
	        WebElement emailInput = driver.findElement(By.xpath("//label[text()='Email']/following-sibling::input"));
	        emailInput.clear();
	        emailInput.sendKeys(email);
	        System.out.println("Entered email: " + email);
	    } catch (NoSuchElementException e) {
	        System.out.println("Email input field not found.");
	        e.printStackTrace();
	    }
	}

//Method to enter phone number 
	public void enterPhoneNumber(String phoneNumber) {
	    try {
	        WebElement phoneInput = driver.findElement(By.xpath("//label[text()='Phone Number']/following-sibling::input"));
	        phoneInput.clear(); 
	        phoneInput.sendKeys(phoneNumber);
	        System.out.println("Entered phone number: " + phoneNumber);
	    } catch (NoSuchElementException e) {
	        System.out.println("Phone Number input field not found.");
	        e.printStackTrace();
	    }
	}
	
	public void clickAcceptanceAndConditionsCheckBox() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    try {
	        WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("acceptTerms")));

	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);

	        wait.until(ExpectedConditions.elementToBeClickable(checkbox));

	        checkbox.click();
	    } catch (Exception e) {
	        System.out.println("Error clicking the checkbox: " + e.getMessage());
	    }
	}

	
	//Method to clcik on payment options on booking page 
	public void clickPaymentOptions(String paymentType) {
	    String xpath = "//*[contains(@class,'hotel-pg') and contains(., '" + paymentType + "')]";
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    try {
	        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	        Thread.sleep(500); 

	        wait.until(ExpectedConditions.elementToBeClickable(element));

	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

	    } catch (Exception e) {
	        System.out.println("Error clicking payment option: " + e.getMessage());
	    }
	}

	//Method to clcik on conform booking button on booking page 
	public void confirmBookingButton() {
		driver.findElement(By.xpath("//button[text()='Confirm Booking']")).click();
		
	}
	
	
	public void enterTravellerDetailsOnBookingPageAndReturnTheText() throws InterruptedException {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    Random random = new Random();

	    // Titles excluding Mstr
	    String[] titlesWithoutMstr = {"Mr", "Ms", "Mrs"};
	    // Sample first and last names (replace with your own arrays)
	    String[] firstNames = {"John", "Nina", "Alex", "Linda"};
	    String[] lastNames = {"Davis", "Brown", "Smith", "Johnson"};

	    // Find all title input elements for all travelers
	    List<WebElement> titleInputs = driver.findElements(By.cssSelector("input.hotel-booking-title__input"));
	    // First name inputs (assuming same order as title inputs)
	    List<WebElement> firstNameInputs = driver.findElements(By.cssSelector("input[name='firstName']"));
	    // Last name inputs
	    List<WebElement> lastNameInputs = driver.findElements(By.cssSelector("input[name='lastName']"));

	    List<String[]> enteredDetails = new ArrayList<>();

	    int travelerCount = titleInputs.size();

	    for (int i = 0; i < travelerCount; i++) {
	        try {
	            Thread.sleep(1000);

	            WebElement titleInput = titleInputs.get(i);
	            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", titleInput);
	            Thread.sleep(1000);
	            wait.until(ExpectedConditions.elementToBeClickable(titleInput));
	            Thread.sleep(1000);
	            titleInput.click();
	            Thread.sleep(1000);

	            // Find the closest .spacing-16 container for this traveler
	            WebElement travelerBlock = (WebElement) js.executeScript(
	                "return arguments[0].closest('.spacing-16')", titleInput);

	            // Check if Age field exists inside this traveler block
	            List<WebElement> ageLabels = travelerBlock.findElements(By.xpath(".//label[text()='Age']"));

	            String selectedTitle;
	            if (!ageLabels.isEmpty()) {
	                // Age field found -> select "Mstr"
	                selectedTitle = "Mstr";
	            } else {
	                // No Age field -> pick random title excluding "Mstr"
	                selectedTitle = titlesWithoutMstr[random.nextInt(titlesWithoutMstr.length)];
	            }

	            // Enter the selected title
	            titleInput.sendKeys(selectedTitle);
	            Thread.sleep(1000);
	            titleInput.sendKeys(Keys.ENTER);
	            Thread.sleep(1000);

	            // Fill First Name
	            WebElement firstNameInput = firstNameInputs.get(i);
	            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstNameInput);
	            wait.until(ExpectedConditions.visibilityOf(firstNameInput));
	            firstNameInput.clear();
	            String randomFirstName = firstNames[random.nextInt(firstNames.length)];
	            firstNameInput.sendKeys(randomFirstName);

	            // Fill Last Name
	            WebElement lastNameInput = lastNameInputs.get(i);
	            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", lastNameInput);
	            wait.until(ExpectedConditions.visibilityOf(lastNameInput));
	            lastNameInput.clear();
	            String randomLastName = lastNames[random.nextInt(lastNames.length)];
	            lastNameInput.sendKeys(randomLastName);

	            System.out.println("Filled traveler " + (i + 1) + ": " + selectedTitle + " " + randomFirstName + " " + randomLastName);

	            enteredDetails.add(new String[]{selectedTitle, randomFirstName, randomLastName});

	        } catch (Exception e) {
	            System.out.println("Failed to fill traveler " + (i + 1) + ": " + e.getMessage());
	        }
	    }
	}

	//Method to Validate policy on Booking Screen
	public boolean validateCancellationPolicies(List<String> expectedPolicies,Log log, ScreenShots screenShots) {
	    try {
	       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	       // Locate the section that contains "Cancellation Policy"
	       WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(
	             By.xpath("//div[contains(@class, 'p-2') and contains(., 'Cancellation Policy')]")
	       ));
	       // Scroll to the container
	       ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", container);
	       // Locate the actual inner <div> that holds the cancellation policy lines
	       WebElement innerDiv = container.findElement(By.xpath(".//span[contains(@class,'fs-14')]/div"));
	       // Extract inner HTML and split by <br>
	       String innerHTML = innerDiv.getAttribute("innerHTML");
	       String[] actualPoliciesArray = innerHTML.split("<br>");
	       List<String> actualPolicies = Arrays.stream(actualPoliciesArray)
	             .map(String::trim)
	             .collect(Collectors.toList());
	       // Log actual policies for debugging
	       System.out.println("📋 Actual Cancellation Policies:");
	       actualPolicies.forEach(System.out::println);
	       // Check if sizes match
	       if (expectedPolicies.size() != actualPolicies.size()) {
	          System.out.println("❌ Mismatch in number of policies. Expected: " + expectedPolicies.size() + ", Found: " + actualPolicies.size());
	          log.ReportEvent("FAIL", "❌ Mismatch in number of policies. Expected: " + expectedPolicies.size() + ", Found: " + actualPolicies.size());
	          screenShots.takeScreenShot();
	          Assert.fail();
	          return false;
	       }
	       // Compare content
	       for (int i = 0; i < expectedPolicies.size(); i++) {
	          String expected = expectedPolicies.get(i);
	          String actual = actualPolicies.get(i);
	          if (!arePoliciesEqualIgnoringDecimal(expected, actual)) {
	             System.out.println("❌ Policy mismatch at index " + i);
	             System.out.println("Expected: " + expected);
	             System.out.println("Actual  : " + actual);
	             return false;
	          }
	       }
	       System.out.println("✅ Cancellation policies validation passed.");
	       log.ReportEvent("PASS", "✅ Cancellation policies validation passed. ");
	       return true;
	    } catch (NoSuchElementException | TimeoutException e) {
	       System.out.println("❌ Cancellation policy section not found: " + e.getMessage());
	       log.ReportEvent("FAIL", "❌ Cancellation policy section not found: " + e.getMessage());
	       e.printStackTrace();
	       screenShots.takeScreenShot();
	       Assert.fail("Traveller entry failed: " + e.getMessage());
	       return false;
	    } catch (Exception e) {
	       System.out.println("❌ Unexpected error while validating cancellation policies: " + e.getMessage());
	       log.ReportEvent("FAIL", "❌ Unexpected error while validating cancellation policies");
	       e.printStackTrace();
	       screenShots.takeScreenShot();
	       Assert.fail("Traveller entry failed: " + e.getMessage());
	       return false;
	    }
	}
	// Helper function to compare integer part of penalty values
	private boolean arePoliciesEqualIgnoringDecimal(String expected, String actual) {
	    // Regex to match: From <date> , To: <date> , Penalty will be (amount of|) <number> (percentage|)
	    String pattern = "From (.+?) , To: (.+?) , Penalty will be (amount of )?(\\d+(\\.\\d+)?) ?(percentage)?";
	    Pattern regex = Pattern.compile(pattern);
	    Matcher expectedMatcher = regex.matcher(expected);
	    Matcher actualMatcher = regex.matcher(actual);
	    if (expectedMatcher.matches() && actualMatcher.matches()) {
	       String expectedFrom = expectedMatcher.group(1).trim();
	       String expectedTo = expectedMatcher.group(2).trim();
	       String expectedType = expectedMatcher.group(3) != null ? expectedMatcher.group(3).trim() : "";
	       String expectedValueStr = expectedMatcher.group(4);
	       String expectedIsPercentage = expectedMatcher.group(6) != null ? "percentage" : "";
	       String actualFrom = actualMatcher.group(1).trim();
	       String actualTo = actualMatcher.group(2).trim();
	       String actualType = actualMatcher.group(3) != null ? actualMatcher.group(3).trim() : "";
	       String actualValueStr = actualMatcher.group(4);
	       String actualIsPercentage = actualMatcher.group(6) != null ? "percentage" : "";
	       // Compare fixed parts
	       if (!expectedFrom.equals(actualFrom)) return false;
	       if (!expectedTo.equals(actualTo)) return false;
	       if (!expectedType.equals(actualType)) return false;
	       if (!expectedIsPercentage.equals(actualIsPercentage)) return false;
	       // Compare integer part of numeric values only
	       try {
	          double expectedValue = Double.parseDouble(expectedValueStr);
	          double actualValue = Double.parseDouble(actualValueStr);
	          int expectedInt = (int) expectedValue;
	          int actualInt = (int) actualValue;
	          return expectedInt == actualInt;
	       } catch (NumberFormatException e) {
	          return false;
	       }
	    }
	    // If regex does not match, fallback to strict equality
	    return expected.equals(actual);
	}

	



}
