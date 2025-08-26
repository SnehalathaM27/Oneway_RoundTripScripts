package com.tripgain.collectionofpages;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;

public class SkyTravelers_Hotels_confirmBookingPage {
	WebDriver driver;

	public SkyTravelers_Hotels_confirmBookingPage (WebDriver driver)
	{
		PageFactory.initElements(driver, this);
		this.driver=driver;
	}
	
	//Method to get the hotel name text
	public String[] getHotelNameTextFromConfirmPage() throws InterruptedException {
		Thread.sleep(3000);
		String hotelname = driver.findElement(By.xpath("//*[contains(@class,'modal-body')]//*[@class='booking-review-page__body__booking-details_hotel-name']")).getText();
        System.out.println("Hotel name from confirm Confirm page : " + hotelname);

	    return new String[]{hotelname};
	}	
	
	//Method to get the hotel location text
		public String[] getHotelLocationTextFromConfirmPage() throws InterruptedException {
			Thread.sleep(2000);
		    String Loc = driver.findElement(By.xpath("//*[contains(@class,'modal-body')]//*[@class='booking-review-page__body__booking-details_hotel-location']")).getText();
	        System.out.println("Hotel name from confirm Confirm page : " + Loc);

		    return new String[]{Loc};
		}	
		
		public String[] getHotelCheckInDateFromConfirmPage() {
		    String hotelcheckInDate = driver.findElement(By.xpath("//*[contains(@class,'modal-body')]//span[text()='Check In']/following-sibling::span[contains(@class,'booking-review-page__body__booking-details_check-in-card_date')]")).getText();
	        System.out.println("Hotel checkInDate from Confirm page : " + hotelcheckInDate);
		    return new String[]{hotelcheckInDate};
		}	
		
		public String[] gethotelcheckInMonthFromConfirmPage() {
		    String hotelcheckInMon = driver.findElement(By.xpath("//*[contains(@class,'modal-body')]//span[text()='Check In']/following-sibling::span[contains(@class,'booking-review-page__body__booking-details_check-in-card_month')]")).getText();
	        System.out.println("Hotel CheckIn Month from Confirm page : " + hotelcheckInMon);
		    return new String[]{hotelcheckInMon};
		}	
		
		public String[] getHotelCheckOutDateFromConfirmPage() {
		    String hotelcheckOutDate = driver.findElement(By.xpath("//*[contains(@class,'modal-body')]//span[text()='Check-Out']/following-sibling::span[contains(@class,'booking-review-page__body__booking-details_check-in-card_date')]")).getText();
	        System.out.println("Hotel checkoutDate from Confirm page : " + hotelcheckOutDate);
		    return new String[]{hotelcheckOutDate};
		}	
		
		public String[] gethotelcheckOutMonthFromConfirmPage() {
		    String hotelcheckOutMon = driver.findElement(By.xpath("//*[contains(@class,'modal-body')]//span[text()='Check-Out']/following-sibling::span[contains(@class,'booking-review-page__body__booking-details_check-in-card_month')]")).getText();
	        System.out.println("Hotel check out month from Confirm page : " + hotelcheckOutMon);
		    return new String[]{hotelcheckOutMon};
		}	
		
		public String[] getRoomTextFromConfirmPage() {
		    String roomText = driver.findElement(By.xpath("//*[contains(@class,'modal-body')]//*[contains(@class,'booking-review-page__body__room-details_room-type')]")).getText();
	        System.out.println("Room text from Confirm page : " + roomText);
		    return new String[]{roomText};
		}	
		
		public String[] getPeopleCountFromConfirmPage() {
		    String PeopleCountText = driver.findElement(By.xpath("//*[contains(@class,'modal-body')]//*[contains(@class,'fs-14 mb-2')]")).getText();
	        System.out.println("People Count Text from Confirm page : " + PeopleCountText);
		    return new String[]{PeopleCountText};
		}	
		
		public String[] getMealPlanFromConfirmPage() {
		    String mealPlanText = driver.findElement(By.xpath("//*[contains(@class,'modal-body')]//span[text()='Meal Plan']/following-sibling::span")).getText();
	        System.out.println("Meal plan text from confirm page : " + mealPlanText);
		    return new String[]{mealPlanText};
		}	
		
		public String[] getAmountFromConfirmPage() {
		    String amount = driver.findElement(By.xpath("//span[text()='Total Amount']/following-sibling::span")).getText();
	        System.out.println("Meal plan text from confirm page : " + amount);
		    return new String[]{amount};
		}	

		//Method to validate hotel text from booking to confirm page 
		public void validateHotelNameFromBookingAndConfirmPage(String[] hotelNameFromBookingArray, String[] hotelNameFromConfirmArray, Log Log, ScreenShots ScreenShots) {
		    boolean isPass = true;

		    if (hotelNameFromBookingArray == null || hotelNameFromBookingArray.length == 0 || hotelNameFromBookingArray[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Hotel name from Booking Page is null, empty or missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } 
		    else if (hotelNameFromConfirmArray == null || hotelNameFromConfirmArray.length == 0 || hotelNameFromConfirmArray[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Hotel name from Confirmation Page is null, empty or missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } 
		    else {
		        String bookingName = hotelNameFromBookingArray[0].trim();
		        String confirmName = hotelNameFromConfirmArray[0].trim();

		        if (bookingName.equalsIgnoreCase(confirmName)) {
		            // Do nothing here, let final PASS log handle it
		        } else {
		            Log.ReportEvent("FAIL", "Hotel names do not match. Booking Page: '" + bookingName + "' | Confirmation Page: '" + confirmName + "'");
		            ScreenShots.takeScreenShot1();
		            isPass = false;
		        }
		    }

		    if (isPass) {
		        Log.ReportEvent("PASS", "Hotel name validation passed between Booking and Confirmation pages.");
		    } else {
		        Log.ReportEvent("FAIL", "Hotel name validation failed between Booking and Confirmation pages.");
		    }
		}

		public void validateHotelLocationFromBookingAndConfirmPage(String[] bookingLocArray, String[] confirmLocArray, Log Log, ScreenShots ScreenShots) {
		    boolean isPass = true;

		    if (bookingLocArray == null || bookingLocArray.length == 0 || bookingLocArray[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Hotel location from Booking Page is null, empty or missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } 
		    else if (confirmLocArray == null || confirmLocArray.length == 0 || confirmLocArray[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Hotel location from Confirmation Page is null, empty or missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } 
		    else {
		        String bookingLoc = bookingLocArray[0].trim();
		        String confirmLoc = confirmLocArray[0].trim();

		        if (bookingLoc.equalsIgnoreCase(confirmLoc)) {
		            // Locations match
		        } else {
		            Log.ReportEvent("FAIL", "Hotel locations do not match. Booking Page: '" + bookingLoc + "' | Confirmation Page: '" + confirmLoc + "'");
		            ScreenShots.takeScreenShot1();
		            isPass = false;
		        }
		    }

		    if (isPass) {
		        Log.ReportEvent("PASS", "Hotel location validation passed between Booking and Confirmation pages.");
		    } else {
		        Log.ReportEvent("FAIL", "Hotel location validation failed between Booking and Confirmation pages.");
		    }
		}

		public void validateHotelCheckInDayFromBookingAndConfirmPage(String[] dayFromBooking, String[] dayFromConfirm, Log Log, ScreenShots ScreenShots) {
		    boolean isPass = true;

		    if (dayFromBooking == null || dayFromBooking.length == 0 || dayFromBooking[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Check-in day from Booking Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else if (dayFromConfirm == null || dayFromConfirm.length == 0 || dayFromConfirm[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Check-in day from Confirmation Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else {
		        String bookingDay = dayFromBooking[0].trim();
		        String confirmDay = dayFromConfirm[0].trim();

		        if (bookingDay.equals(confirmDay)) {
		            Log.ReportEvent("PASS", "Check-in day matches between Booking and Confirmation pages.");
		        } else {
		            Log.ReportEvent("FAIL", "Check-in days do not match. Booking: '" + bookingDay + "' | Confirmation: '" + confirmDay + "'");
		            ScreenShots.takeScreenShot1();
		            isPass = false;
		        }
		    }
		}

		public void validateHotelCheckInMonthFromBookingAndConfirmPage(String[] monthFromBooking, String[] monthFromConfirm, Log Log, ScreenShots ScreenShots) {
		    boolean isPass = true;

		    if (monthFromBooking == null || monthFromBooking.length == 0 || monthFromBooking[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Check-in month from Booking Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else if (monthFromConfirm == null || monthFromConfirm.length == 0 || monthFromConfirm[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Check-in month from Confirmation Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else {
		        String bookingMonth = monthFromBooking[0].trim();
		        String confirmMonth = monthFromConfirm[0].trim();

		        if (bookingMonth.equalsIgnoreCase(confirmMonth)) {
		            Log.ReportEvent("PASS", "Check-in month matches between Booking and Confirmation pages.");
		        } else {
		            Log.ReportEvent("FAIL", "Check-in months do not match. Booking: '" + bookingMonth + "' | Confirmation: '" + confirmMonth + "'");
		            ScreenShots.takeScreenShot1();
		            isPass = false;
		        }
		    }
		}

		public void validateHotelCheckOutDayFromBookingAndConfirmPage(String[] dayFromBooking, String[] dayFromConfirm, Log Log, ScreenShots ScreenShots) {
		    boolean isPass = true;

		    if (dayFromBooking == null || dayFromBooking.length == 0 || dayFromBooking[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Check-out day from Booking Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else if (dayFromConfirm == null || dayFromConfirm.length == 0 || dayFromConfirm[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Check-out day from Confirmation Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else {
		        String bookingDay = dayFromBooking[0].trim();
		        String confirmDay = dayFromConfirm[0].trim();

		        if (bookingDay.equals(confirmDay)) {
		            Log.ReportEvent("PASS", "Check-out day matches between Booking and Confirmation pages.");
		        } else {
		            Log.ReportEvent("FAIL", "Check-out days do not match. Booking: '" + bookingDay + "' | Confirmation: '" + confirmDay + "'");
		            ScreenShots.takeScreenShot1();
		            isPass = false;
		        }
		    }
		}
		
		public void validateHotelCheckOutMonthFromBookingAndConfirmPage(String[] monthFromBooking, String[] monthFromConfirm, Log Log, ScreenShots ScreenShots) {
		    boolean isPass = true;

		    if (monthFromBooking == null || monthFromBooking.length == 0 || monthFromBooking[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Check-out month from Booking Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else if (monthFromConfirm == null || monthFromConfirm.length == 0 || monthFromConfirm[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Check-out month from Confirmation Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else {
		        String bookingMonth = monthFromBooking[0].trim();
		        String confirmMonth = monthFromConfirm[0].trim();

		        if (bookingMonth.equalsIgnoreCase(confirmMonth)) {
		            Log.ReportEvent("PASS", "Check-out month matches between Booking and Confirmation pages.");
		        } else {
		            Log.ReportEvent("FAIL", "Check-out months do not match. Booking: '" + bookingMonth + "' | Confirmation: '" + confirmMonth + "'");
		            ScreenShots.takeScreenShot1();
		            isPass = false;
		        }
		    }
		}

		public void validateRoomTypeFromBookingAndConfirmPage(String[] roomFromBooking, String[] roomFromConfirm, Log Log, ScreenShots ScreenShots) {
		    boolean isPass = true;

		    if (roomFromBooking == null || roomFromBooking.length == 0 || roomFromBooking[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Room type from Booking Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else if (roomFromConfirm == null || roomFromConfirm.length == 0 || roomFromConfirm[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Room type from Confirmation Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else {
		        String bookingRoom = roomFromBooking[0].trim();
		        String confirmRoom = roomFromConfirm[0].trim();

		        if (bookingRoom.equalsIgnoreCase(confirmRoom)) {
		            Log.ReportEvent("PASS", "Room type matches between Booking and Confirmation pages.");
		        } else {
		            Log.ReportEvent("FAIL", "Room types do not match. Booking: '" + bookingRoom + "' | Confirmation: '" + confirmRoom + "'");
		            ScreenShots.takeScreenShot1();
		            isPass = false;
		        }
		    }
		}

		public void validatePeopleCountFromBookingAndConfirmPage(String[] peopleFromBooking, String[] peopleFromConfirm, Log Log, ScreenShots ScreenShots) {
		    boolean isPass = true;

		    if (peopleFromBooking == null || peopleFromBooking.length == 0 || peopleFromBooking[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "People count from Booking Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else if (peopleFromConfirm == null || peopleFromConfirm.length == 0 || peopleFromConfirm[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "People count from Confirmation Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else {
		        String bookingPeople = peopleFromBooking[0].trim();
		        String confirmPeople = peopleFromConfirm[0].trim();

		        if (bookingPeople.equalsIgnoreCase(confirmPeople)) {
		            Log.ReportEvent("PASS", "People count matches between Booking and Confirmation pages.");
		        } else {
		            Log.ReportEvent("FAIL", "People count does not match. Booking: '" + bookingPeople + "' | Confirmation: '" + confirmPeople + "'");
		            ScreenShots.takeScreenShot1();
		            isPass = false;
		        }
		    }
		}

		public void validateMealPlanFromBookingAndConfirmPage(String[] mealPlanFromBooking, String[] mealPlanFromConfirm, Log Log, ScreenShots ScreenShots) {
		    boolean isPass = true;

		    if (mealPlanFromBooking == null || mealPlanFromBooking.length == 0 || mealPlanFromBooking[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Meal plan from Booking Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else if (mealPlanFromConfirm == null || mealPlanFromConfirm.length == 0 || mealPlanFromConfirm[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Meal plan from Confirmation Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else {
		        String bookingMealPlan = mealPlanFromBooking[0].trim();
		        String confirmMealPlan = mealPlanFromConfirm[0].trim();

		        if (bookingMealPlan.equalsIgnoreCase(confirmMealPlan)) {
		            Log.ReportEvent("PASS", "Meal plan matches between Booking and Confirmation pages.");
		        } else {
		            Log.ReportEvent("FAIL", "Meal plans do not match. Booking: '" + bookingMealPlan + "' | Confirmation: '" + confirmMealPlan + "'");
		            ScreenShots.takeScreenShot1();
		            isPass = false;
		        }
		    }
		}
		
		public void validateTotalAmountFromBookingAndConfirmPage(String[] amountFromBooking, String[] amountFromConfirm, Log Log, ScreenShots ScreenShots) {
		    boolean isPass = true;

		    if (amountFromBooking == null || amountFromBooking.length == 0 || amountFromBooking[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Amount from Booking Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else if (amountFromConfirm == null || amountFromConfirm.length == 0 || amountFromConfirm[0].trim().isEmpty()) {
		        Log.ReportEvent("FAIL", "Amount from Confirmation Page is missing.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else {
		        String bookingAmountStr = amountFromBooking[0].replaceAll("[^\\d.]", ""); // removes currency symbol and commas
		        String confirmAmountStr = amountFromConfirm[0].replaceAll("[^\\d.]", "");

		        try {
		            double bookingAmount = Double.parseDouble(bookingAmountStr);
		            double confirmAmount = Double.parseDouble(confirmAmountStr);

		            if (Double.compare(bookingAmount, confirmAmount) == 0) {
		                Log.ReportEvent("PASS", "Total amount matches between Booking and Confirmation pages.");
		            } else {
		                Log.ReportEvent("FAIL", "Total amounts do not match. Booking: '" + bookingAmount + "' | Confirmation: '" + confirmAmount + "'");
		                ScreenShots.takeScreenShot1();
		                isPass = false;
		            }
		        } catch (NumberFormatException e) {
		            Log.ReportEvent("FAIL", "Unable to parse total amounts. Booking: '" + bookingAmountStr + "', Confirm: '" + confirmAmountStr + "'");
		            ScreenShots.takeScreenShot1();
		            isPass = false;
		        }
		    }
		}
		
		public String[] getTravellerDetailsArray() {
		    List<WebElement> elements = driver.findElements(By.xpath("//div[@class='modal-body']//div[contains(@class,'px-2 py-1')]//span[contains(@class,'fs-14 fw-500')]"));

		    List<String> texts = new ArrayList<>();

		    for (WebElement el : elements) {
		        String text = el.getText().trim();
		        if (!text.isEmpty()) {
		            texts.add(text);
		        }
		    }

		    System.out.println("Traveller details array: " + texts);

		    return texts.toArray(new String[0]);
		}
		
		//Method to click on book now button on confirm page
		public void clcikBookNowButtonOnConfirmPage() {
			driver.findElement(By.xpath("//button[text()='Book Now']")).click();
		}
		
		//Method to validate payment page is displayed or not 
		public void validateBillingInformationDisplayed(Log Log) {
		    try {
		        WebElement billingInfoElement = driver.findElement(By.xpath("//span[text()='Billing Information']"));
		        if (billingInfoElement.isDisplayed()) {
		            Log.ReportEvent("PASS", "Payment page is displayed successfully.");
		        } else {
		            Log.ReportEvent("FAIL", "Payment page is NOT displayed.");
		        }
		    } catch (NoSuchElementException e) {
		        Log.ReportEvent("FAIL", "Payment page is NOT displayed - element not found.");
		    }
		}




}
