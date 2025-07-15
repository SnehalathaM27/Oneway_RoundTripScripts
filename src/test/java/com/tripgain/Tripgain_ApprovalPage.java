package com.tripgain;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;

public class Tripgain_ApprovalPage {
WebDriver driver;
	

	public Tripgain_ApprovalPage(WebDriver driver) {

		PageFactory.initElements(driver, this);
		this.driver=driver;
	}
	
	
//	public void approvalPageValidation(String origin, String destination,String travel,String profile[],Log Log, ScreenShots ScreenShots) {
//		
//		String employeeCode =profile[0];
//		String approvalManager =profile[1];
//		String traveller=profile[2];
//		//concat
//		String travellerWithCode = profile[2] + " (" + profile[0] + ")";
//		System.out.println(travellerWithCode);  // Output: test traveller98 (1234)
//
//	    String routeToFind = origin + " - " + destination;
//
//	    List<WebElement> cards = driver.findElements(By.cssSelector("div.MuiPaper-root"));
//
//	    for (WebElement card : cards) {
//	        try {
//	            String route = card.findElement(By.xpath(".//span[text()='Origin - Destination']/following-sibling::h6")).getText().trim();
//
//	            if (route.equalsIgnoreCase(routeToFind)) {
//	                // Scroll into view
//	                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", card);
//
//	                // Optional: wait until visible
//	                new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(card));
//	                
//	                //-----------
//	                String traveltype=driver.findElement(By.xpath("(.//h6[@class='MuiTypography-root MuiTypography-subtitle1 capitalize css-17jtg62'])[4]")).getText();
//	        		String approverName=driver.findElement(By.xpath("(.//h6[@class='MuiTypography-root MuiTypography-subtitle1 css-17jtg62'])[7]")).getText();
//	        		String requestedBy=driver.findElement(By.xpath("(.//h6[@class='MuiTypography-root MuiTypography-subtitle1 css-17jtg62'])[8]")).getText();
//	                if(traveltype.equals(travel) && approverName.equals(approvalManager) && requestedBy.equals(travellerWithCode) )
//	                {
//	                	Log.ReportEvent("PASS", "Sent approval details are correct: " +
//	                "flight type :"+ traveltype +"Approver Name :"+ approverName +"RequestedBy :" +requestedBy
//	                			);
//	                	
//						ScreenShots.takeScreenShot1();	
//	                }
//	                
//	                ///////////////////////
//
//	                // Click on "Details" button (or change the button text as needed)
//	                WebElement detailsBtn = card.findElement(By.xpath(".//button[text()='Details']"));
//	                detailsBtn.click();
//
//	                System.out.println("✅ Clicked Details for: " + routeToFind);
//	                return;
//	            }
//	        } catch (NoSuchElementException e) {
//	            // Skip this card if structure is invalid
//	        }
//	    }
//
//	    System.out.println("❌ Route not found: " + routeToFind);
//	}
//	public void validateApproval()
//	{
//		String traveltype=driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 capitalize css-17jtg62'])[4]")).getText();
//		String approverName=driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 css-17jtg62'])[7]")).getText();
//		String requestedBy=driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 css-17jtg62'])[8]")).getText();
//	}
	public void approvalPageValidation(String origin, String destination, String travels, Log Log, ScreenShots ScreenShots,String profile[]) throws InterruptedException {
		Thread.sleep(3000);
          //If the string ends with s, it gets removed; otherwise, it stays unchanged.
		travels = travels.replaceAll("s$", "");

		System.out.println(origin);
		System.out.println(destination);
		
	    String employeeCode = profile[0];
	    String approvalManager = profile[1];
	    String traveller = profile[2];
	    String travellerWithCode = traveller + " (" + employeeCode + ")";
	    String routeToFind = origin + " - " + destination;
	    System.out.println(routeToFind);

	    List<WebElement> cards = driver.findElements(By.cssSelector("div.MuiPaper-root"));

	    for (WebElement card : cards) {
	        try {
	            String route = card.findElement(By.xpath(".//span[text()='Origin - Destination']/following-sibling::h6")).getText().trim();
System.out.println(route);
	            if (route.equalsIgnoreCase(routeToFind)) {

	                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", card);
	                new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(card));

	                // 🔄 Index-based element access within card
	                String travelType = card.findElement(By.xpath("(.//h6[contains(@class,'MuiTypography-subtitle1')])[3]")).getText().trim();
	                String approverName = card.findElement(By.xpath("(.//h6[contains(@class,'MuiTypography-subtitle1')])[4]")).getText().trim();
	                String requestedBy = card.findElement(By.xpath("(.//h6[contains(@class,'MuiTypography-subtitle1')])[5]")).getText().trim();

	                System.out.println(travelType+" "+travels);
	                
	                System.out.println(approverName+" "+approvalManager);
	                System.out.println(requestedBy+" "+travellerWithCode);
	                
	                if (travelType.equalsIgnoreCase(travels)
	                        && approverName.equalsIgnoreCase(approvalManager)
	                        && requestedBy.equalsIgnoreCase(travellerWithCode)) {

	                    Log.ReportEvent("PASS", "✅ Sent approval details are correct:\n"
	                            + "Flight Type: " + travelType   + "\n"
	                            + "Approver Name: " + approverName + "\n"
	                            + "Requested By: " + requestedBy);

	                    ScreenShots.takeScreenShot1();
	                }
Thread.sleep(2000);
	                WebElement detailsBtn = card.findElement(By.xpath(".//button[text()='Details']"));
	                detailsBtn.click();

	                System.out.println("✅ Clicked Details for: " + routeToFind);
	                return;
	            }
	        } catch (NoSuchElementException e) {
	            // Handle missing data gracefully
	        }
	    }

	}
//public void validateDetailsNavBar(String from,String to,String deptTime,String arrTime,String FlightCode,String Price,String deptDate)
//{
//	System.out.println(from);
//	System.out.println(to);
//	System.out.println(deptTime);
//	System.out.println(arrTime);
//	System.out.println(FlightCode);
//	System.out.println(Price);
//	System.out.println(deptDate);
//
//	WebElement details=driver.findElement(By.xpath("//h6[text()='Selected Flight Details']"));
//	if(details.isDisplayed())
//	{
//		String orgin=driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[1]")).getText();
//		String destination=driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[4]")).getText();
//		String departTime=driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[2]")).getText();
//		String arrivalTime=driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[5]")).getText();
//		String flightCode=driver.findElement(By.xpath("//p[@class='MuiTypography-root MuiTypography-body1 d-flex justify-content-center css-hhjtk']")).getText();
//		String price=driver.findElement(By.xpath("//strong[@class='price']")).getText();
//		String departDate=driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[1]/following-sibling::small")).getText();
//		System.out.println(orgin);
//		System.out.println(destination);
//		System.out.println(departTime);
//		System.out.println(arrivalTime);
//		System.out.println(flightCode);
//		System.out.println(price);
//		System.out.println(departDate);
//		if(orgin.equals(from) && destination.equals(to) && departTime.equals(deptTime) && arrivalTime.equals(arrTime) && flightCode.equals(FlightCode) && price.equals(Price) && departDate.equals(deptDate))
//		{
//			
//		}
//		
//		
//}
//
//}
//	public void validateDetailsNavBar(String from, String to, String deptTime, String arrTime, String FlightCode, String Price, String deptDate) {
//	    System.out.println("Expected Values:");
//	    System.out.println(from);
//	    System.out.println(to);
//	    System.out.println(deptTime);
//	    System.out.println(arrTime);
//	    System.out.println(FlightCode);
//	    System.out.println(Price);
//	    System.out.println(deptDate);
//
//	    WebElement details = driver.findElement(By.xpath("//h6[text()='Selected Flight Details']"));
//	    if (details.isDisplayed()) {
//	        String origin = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[1]")).getText();
//	        String destination = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[4]")).getText();
//	        String departTime = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[2]")).getText();
//	        String arrivalTime = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[5]")).getText();
//	        String flightCode = driver.findElement(By.xpath("//p[@class='MuiTypography-root MuiTypography-body1 d-flex justify-content-center css-hhjtk']")).getText();
//	        String price = driver.findElement(By.xpath("//strong[@class='price']")).getText();
//	        String departDate = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[1]/following-sibling::small")).getText();
//
//	        System.out.println("Actual Values:");
//	        System.out.println(origin);
//	        System.out.println(destination);
//	        System.out.println(departTime);
//	        System.out.println(arrivalTime);
//	        System.out.println(flightCode);
//	        System.out.println(price);
//	        System.out.println(departDate);
//
//	        // Normalize values
//	        origin = origin.replaceAll(".*\\((.*?)\\)", "$1").trim();                    // e.g. "Bengaluru, India (BLR)" -> "BLR"
//	        destination = destination.replaceAll(".*\\((.*?)\\)", "$1").trim();          // e.g. "New Delhi, India (DEL)" -> "DEL"
//	        flightCode = flightCode.replaceAll("[()]", "").trim();                       // e.g. "(IX 2936)" -> "IX 2936"
//	        price = price.replaceAll("[^0-9]", "");                                       // e.g. "₹ 4,556" -> "4556"
//
//	        // Convert departDate like "on 4th Jul ," to "2025-07-04"
//	        departDate = departDate.replaceAll("on\\s*", "").replaceAll(",", "").trim();
//	        String[] parts = departDate.split(" ");
//	        String day = parts[0].replaceAll("\\D+", ""); // "4th" → "4"
//	        String month = parts[1];
//	        String monthNum = "";
//
//	        switch (month) {
//	            case "Jan": monthNum = "01"; break;
//	            case "Feb": monthNum = "02"; break;
//	            case "Mar": monthNum = "03"; break;
//	            case "Apr": monthNum = "04"; break;
//	            case "May": monthNum = "05"; break;
//	            case "Jun": monthNum = "06"; break;
//	            case "Jul": monthNum = "07"; break;
//	            case "Aug": monthNum = "08"; break;
//	            case "Sep": monthNum = "09"; break;
//	            case "Oct": monthNum = "10"; break;
//	            case "Nov": monthNum = "11"; break;
//	            case "Dec": monthNum = "12"; break;
//	        }
//
//	        if (day.length() == 1) {
//	            day = "0" + day;
//	        }
//
//	        departDate = "2025-" + monthNum + "-" + day;
//
//	        // Final validation
//	        if (origin.equals(from) &&
//	            destination.equals(to) &&
//	            departTime.equals(deptTime) &&
//	            arrivalTime.equals(arrTime) &&
//	            flightCode.equals(FlightCode) &&
//	            price.equals(Price) &&
//	            departDate.equals(deptDate)) {
//	            System.out.println("✅ All flight details match!");
//	        } else {
//	            System.out.println("❌ Flight details mismatch!");
//	        }
//	    } else {
//	        System.out.println("❌ Flight details section not displayed.");
//	    }
//	}
  
//	public void validateDetailsNavBar1(String expected[], Log Log, ScreenShots ScreenShots) {
//		 
//		
//	        String from=expected[0];
//	        String to=expected[1];
//	        String deptTime=expected[4];
//	        String arrTime=expected[5];
//	        String flightCode =expected[3];
//	       String Price= expected[9];
//	        String deptDate=expected[2];
//	        System.out.println("Flight Details:");
//	        System.out.println("From: " + from );
//	        System.out.println("To: " + to);
//	        System.out.println("Date: " + deptDate );
//	        System.out.println("Flight Code: " + flightCode );
//	        System.out.println("Departure Time: " + deptTime);
//	        System.out.println("Arrival Time: " + arrTime);
//	        System.out.println("Price: " + Price );
//	   
//
//	    WebElement details = driver.findElement(By.xpath("//h6[text()='Selected Flight Details']"));
//	    if (details.isDisplayed()) {
//	        String origin = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[1]")).getText();
//	        String destination = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[4]")).getText();
//	        String departTime = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[2]")).getText();
//	        String arrivalTime = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[5]")).getText();
//	        String flightcode = driver.findElement(By.xpath("//p[@class='MuiTypography-root MuiTypography-body1 d-flex justify-content-center css-hhjtk']")).getText();
//	        String price = driver.findElement(By.xpath("//strong[@class='price']")).getText();
//	        String departDate = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[1]/following-sibling::small")).getText();
//
//	        System.out.println("Actual Values:");
//	        System.out.println(origin);
//	        System.out.println(destination);
//	        System.out.println(departTime);
//	        System.out.println(arrivalTime);
//	        System.out.println(flightCode);
//	        System.out.println(price);
//	        System.out.println(departDate);
//
//	        // Normalize values
//	        origin = origin.replaceAll(".*\\((.*?)\\)", "$1").trim();                    // e.g. "Bengaluru, India (BLR)" -> "BLR"
//	        destination = destination.replaceAll(".*\\((.*?)\\)", "$1").trim();          // e.g. "New Delhi, India (DEL)" -> "DEL"
//	        flightCode = flightCode.replaceAll("[()]", "").trim();                       // e.g. "(IX 2936)" -> "IX 2936"
//	        price = price.replaceAll("[^0-9]", "");                                       // e.g. "₹ 4,556" -> "4556"
//
//	        // Convert departDate like "on 4th Jul ," to "2025-07-04"
//	        departDate = departDate.replaceAll("on\\s*", "").replaceAll(",", "").trim();
//	        String[] parts = departDate.split(" ");
//	        String day = parts[0].replaceAll("\\D+", ""); // "4th" → "4"
//	        String month = parts[1];
//	        String monthNum = "";
//
//	        switch (month) {
//	            case "Jan": monthNum = "01"; break;
//	            case "Feb": monthNum = "02"; break;
//	            case "Mar": monthNum = "03"; break;
//	            case "Apr": monthNum = "04"; break;
//	            case "May": monthNum = "05"; break;
//	            case "Jun": monthNum = "06"; break;
//	            case "Jul": monthNum = "07"; break;
//	            case "Aug": monthNum = "08"; break;
//	            case "Sep": monthNum = "09"; break;
//	            case "Oct": monthNum = "10"; break;
//	            case "Nov": monthNum = "11"; break;
//	            case "Dec": monthNum = "12"; break;
//	        }
//
//	        if (day.length() == 1) {
//	            day = "0" + day;
//	        }
//
//	        departDate = "2025-" + monthNum + "-" + day;
//
//	        // Final validation
//	        if (origin.equals(from) &&
//	            destination.equals(to) &&
//	            departTime.equals(deptTime) &&
//	            arrivalTime.equals(arrTime) &&
//	            flightCode.equals(flightcode) &&
//	            price.equals(Price) &&
//	            departDate.equals(deptDate)) {
//	            System.out.println("✅ All flight details match!");
//	            System.out.println("Flight Details:");
//		        System.out.println("From: " + from );
//		        System.out.println("To: " + to);
//		        System.out.println("Date: " + deptDate );
//		        System.out.println("Flight Code: " + flightCode );
//		        System.out.println("Departure Time: " + deptTime);
//		        System.out.println("Arrival Time: " + arrTime);
//		        System.out.println("Price: " + Price );
//	            Log.ReportEvent("PASS", "✅ Sent approval details are correct:\n"
//                        + "Orgin: " + from + "\n"
//                        + "Destination: " + to + "\n"
//                        + "DepartDate : " + deptDate + "\n"
//                        + "Flight Code: " + flightCode + "\n"
//                        + "Departure Time: " + deptTime + "\n"
//                        + "Arrival Time: " + arrTime + "\n"
//                        + "Price: " + Price +"\n"
//	            		
//	            		);
//
//                ScreenShots.takeScreenShot1();
//	            
//	        } else {
//	            System.out.println("❌ Flight details mismatch!");
//	            Log.ReportEvent("FAIL", "✅ Sent approval details are correct:\n"
//                        + "Orgin: " + from + "\n"
//                        + "Destination: " + to + "\n"
//                        + "DepartDate : " + deptDate + "\n"
//                        + "Flight Code: " + flightCode + "\n"
//                        + "Departure Time: " + deptTime + "\n"
//                        + "Arrival Time: " + arrTime + "\n"
//                        + "Price: " + Price +"\n"
//	            		
//	            		);
//
//                ScreenShots.takeScreenShot1();
//                Assert.fail();
//	        }
//	    } else {
//	        System.out.println("❌ Flight details section not displayed.");
//	        Assert.fail();
//	    }
//	}
//	public void validateDetailsNavBar1(String expected[], Log Log, ScreenShots ScreenShots) {
//	    try {
////	        // ✅ Validate input array
////	        if (expected == null || expected.length < 10) {
////	            throw new IllegalArgumentException("Invalid expected[] array passed. Minimum 10 elements required.");
//////	        }
//
//	        // ✅ Extract expected values
//	        String from = expected[0];
//	        String to = expected[1];
//	        String deptTime = expected[4];
//	        String arrTime = expected[5];
//	        String flightCodeExpected = expected[3];
//	        String priceExpected = expected[9];
//	        String deptDateExpected = expected[2];
//
//	        System.out.println("Expected Flight Details:");
//	        System.out.println("From: " + from);
//	        System.out.println("To: " + to);
//	        System.out.println("Date: " + deptDateExpected);
//	        System.out.println("Flight Code: " + flightCodeExpected);
//	        System.out.println("Departure Time: " + deptTime);
//	        System.out.println("Arrival Time: " + arrTime);
//	        System.out.println("Price: " + priceExpected);
//
//	        // ✅ Check if flight details section is visible
//	        WebElement detailsSection = driver.findElement(By.xpath("//h6[text()='Selected Flight Details']"));
//	        if (!detailsSection.isDisplayed()) {
//	            throw new RuntimeException("Flight details section not displayed.");
//	        }
//
//	        // ✅ Extract actual values from UI
//	        String origin = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[1]")).getText();
//	        String destination = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[4]")).getText();
//	        String departTime = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[2]")).getText();
//	        String arrivalTime = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[5]")).getText();
//	        String flightCodeActual = driver.findElement(By.xpath("//p[@class='MuiTypography-root MuiTypography-body1 d-flex justify-content-center css-hhjtk']")).getText();
//	        String priceActual = driver.findElement(By.xpath("//strong[@class='price']")).getText();
//	        String departDate = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[1]/following-sibling::small")).getText();
//
//	        // ✅ Normalize actual values
//	        origin = origin.replaceAll(".*\\((.*?)\\)", "$1").trim();                  
//	        destination = destination.replaceAll(".*\\((.*?)\\)", "$1").trim();
//	        flightCodeExpected = flightCodeExpected.replaceAll("[()]", "").trim();
//	        flightCodeActual = flightCodeActual.replaceAll("[()]", "").trim();
//	        priceActual = priceActual.replaceAll("[^0-9]", "");
//
//	        // ✅ Format depart date (e.g., "on 4th Jul ,") → "YYYY-MM-DD"
//	        departDate = departDate.replaceAll("on\\s*", "").replaceAll(",", "").trim();
//	        String[] dateParts = departDate.split(" ");
//	        String day = dateParts[0].replaceAll("\\D+", "");
//	        String month = dateParts[1];
//	        String monthNum = switch (month) {
//	            case "Jan" -> "01";
//	            case "Feb" -> "02";
//	            case "Mar" -> "03";
//	            case "Apr" -> "04";
//	            case "May" -> "05";
//	            case "Jun" -> "06";
//	            case "Jul" -> "07";
//	            case "Aug" -> "08";
//	            case "Sep" -> "09";
//	            case "Oct" -> "10";
//	            case "Nov" -> "11";
//	            case "Dec" -> "12";
//	            default -> throw new IllegalArgumentException("Unknown month abbreviation: " + month);
//	        };
//	        if (day.length() == 1) day = "0" + day;
//	        String currentYear = String.valueOf(LocalDate.now().getYear());
//	        String departDateFormatted = currentYear + "-" + monthNum + "-" + day;
//
//	        // ✅ Compare expected vs actual values
//	        boolean allMatch = origin.equals(from) &&
//	                           destination.equals(to) &&
//	                           departTime.equals(deptTime) &&
//	                           arrivalTime.equals(arrTime) &&
//	                           flightCodeExpected.equals(flightCodeActual) &&
//	                           priceActual.equals(priceExpected) &&
//	                           departDateFormatted.equals(deptDateExpected);
//
//	        if (allMatch) {
//	            System.out.println("✅ All flight details match!");
//	            Log.ReportEvent("PASS", "✅ Flight details match:\n"
//	                + "Origin: " + from + "\n"
//	                + "Destination: " + to + "\n"
//	                + "Date: " + deptDateExpected + "\n"
//	                + "Flight Code: " + flightCodeExpected + "\n"
//	                + "Departure Time: " + deptTime + "\n"
//	                + "Arrival Time: " + arrTime + "\n"
//	                + "Price: " + priceExpected);
//	            ScreenShots.takeScreenShot1();
//	        } else {
//	            System.out.println("❌ Flight details mismatch.");
//	            System.out.println("🔍 Expected vs Actual:");
//	            System.out.println("From: " + from + " | " + origin);
//	            System.out.println("To: " + to + " | " + destination);
//	            System.out.println("Departure Time: " + deptTime + " | " + departTime);
//	            System.out.println("Arrival Time: " + arrTime + " | " + arrivalTime);
//	            System.out.println("Flight Code: " + flightCodeExpected + " | " + flightCodeActual);
//	            System.out.println("Price: " + priceExpected + " | " + priceActual);
//	            System.out.println("Date: " + deptDateExpected + " | " + departDateFormatted);
//
//	            Log.ReportEvent("FAIL", "❌ Flight details mismatch.\n"
//	                + "Expected:\n"
//	                + "Origin: " + from + ", Destination: " + to + ", Date: " + deptDateExpected
//	                + ", Flight Code: " + flightCodeExpected + ", Departure: " + deptTime
//	                + ", Arrival: " + arrTime + ", Price: " + priceExpected + "\n"
//	                + "Actual:\n"
//	                + "Origin: " + origin + ", Destination: " + destination + ", Date: " + departDateFormatted
//	                + ", Flight Code: " + flightCodeActual + ", Departure: " + departTime
//	                + ", Arrival: " + arrivalTime + ", Price: " + priceActual);
//
//	            ScreenShots.takeScreenShot1();
//	            Assert.fail("Flight details validation failed.");
//	        }
//
//	    } catch (Exception e) {
//	        System.out.println("❌ Exception during flight detail validation: " + e.getMessage());
//	        e.printStackTrace();
//	        Log.ReportEvent("ERROR", "❗ Exception occurred: " + e.getMessage());
//	        ScreenShots.takeScreenShot1();
//	        Assert.fail("Test failed due to exception.");
//	    }
//	}
	
	
/*
	public void validateDetailsNavBar1(String expected[], Log Log, ScreenShots ScreenShots) {
	    try {
	        String from = expected[0];
	        String to = expected[1];
	        String deptTime = expected[4];
	        String arrTime = expected[5];
	        String flightCode = expected[3];
	        String Price = expected[9];
	        String deptDate = expected[2];

	        System.out.println("Flight Details:");
	        System.out.println("From: " + from);
	        System.out.println("To: " + to);
	        System.out.println("Date: " + deptDate);
	        System.out.println("Flight Code: " + flightCode);
	        System.out.println("Departure Time: " + deptTime);
	        System.out.println("Arrival Time: " + arrTime);
	        System.out.println("Price: " + Price);

	        WebElement details = driver.findElement(By.xpath("//h6[text()='Selected Flight Details']"));
	        if (details.isDisplayed()) {
	            String origin = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[1]")).getText();
	            String destination = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[4]")).getText();
	            String departTime = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[2]")).getText();
	            String arrivalTime = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[5]")).getText();
	            String flightcode = driver.findElement(By.xpath("//p[@class='MuiTypography-root MuiTypography-body1 d-flex justify-content-center css-hhjtk']")).getText();
	            String price = driver.findElement(By.xpath("//strong[@class='price']")).getText();
	            String departDate = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[1]/following-sibling::small")).getText();

	            System.out.println("Actual Values:");
	            System.out.println(origin);
	            System.out.println(destination);
	            System.out.println(departTime);
	            System.out.println(arrivalTime);
	            System.out.println(flightCode);
	            System.out.println(price);
	            System.out.println(departDate);

	            // Normalize values
	            origin = origin.replaceAll(".*\\((.*?)\\)", "$1").trim();
	            destination = destination.replaceAll(".*\\((.*?)\\)", "$1").trim();
	            flightCode = flightCode.replaceAll("[()]", "").trim();
	            price = price.replaceAll("[^0-9]", "");

	            departDate = departDate.replaceAll("on\\s*", "").replaceAll(",", "").trim();
	            String[] parts = departDate.split(" ");
	            String day = parts[0].replaceAll("\\D+", "");
	            String month = parts[1];
	            String monthNum = "";

	            switch (month) {
	                case "Jan": monthNum = "01"; break;
	                case "Feb": monthNum = "02"; break;
	                case "Mar": monthNum = "03"; break;
	                case "Apr": monthNum = "04"; break;
	                case "May": monthNum = "05"; break;
	                case "Jun": monthNum = "06"; break;
	                case "Jul": monthNum = "07"; break;
	                case "Aug": monthNum = "08"; break;
	                case "Sep": monthNum = "09"; break;
	                case "Oct": monthNum = "10"; break;
	                case "Nov": monthNum = "11"; break;
	                case "Dec": monthNum = "12"; break;
	            }

	            if (day.length() == 1) {
	                day = "0" + day;
	            }

	            departDate = "2025-" + monthNum + "-" + day;

	            // Final validation
	            if (origin.equals(from) &&
	                destination.equals(to) &&
	                departTime.equals(deptTime) &&
	                arrivalTime.equals(arrTime) &&
	                flightCode.equals(flightcode) &&
	                price.equals(Price) &&
	                departDate.equals(deptDate)) {

	                System.out.println("✅ All flight details match!");
	                Log.ReportEvent("PASS", "✅ Sent approval details are correct:\n"
	                        + "Orgin: " + from + "\n"
	                        + "Destination: " + to + "\n"
	                        + "DepartDate : " + deptDate + "\n"
	                        + "Flight Code: " + flightCode + "\n"
	                        + "Departure Time: " + deptTime + "\n"
	                        + "Arrival Time: " + arrTime + "\n"
	                        + "Price: " + Price + "\n");

	                ScreenShots.takeScreenShot1();

	            } else {
	                System.out.println("❌ Flight details mismatch!");
	                Log.ReportEvent("FAIL", "❌ Flight details mismatch:\n"
	                        + "Orgin: " + from + "\n"
	                        + "Destination: " + to + "\n"
	                        + "DepartDate : " + deptDate + "\n"
	                        + "Flight Code: " + flightCode + "\n"
	                        + "Departure Time: " + deptTime + "\n"
	                        + "Arrival Time: " + arrTime + "\n"
	                        + "Price: " + Price + "\n");

	                ScreenShots.takeScreenShot1();
	                Assert.fail();
	            }

	        } else {
	            System.out.println("❌ Flight details section not displayed.");
	            Log.ReportEvent("FAIL", "❌ Flight details section not displayed.");
	            ScreenShots.takeScreenShot1();
	            Assert.fail();
	        }

	    } catch (Exception e) {
	        System.out.println("❌ Exception during flight detail validation: " + e.getMessage());
	        e.printStackTrace();
	        Log.ReportEvent("ERROR", "❗ Exception occurred: " + e.getMessage());
	        ScreenShots.takeScreenShot1();
	        Assert.fail("Test failed due to exception.");
	    }
	}
*/
	public void validateDetailsNavBar1(String expected[], Log Log, ScreenShots ScreenShots) {
	    try {
	        String from = expected[0];
	        String to = expected[1];
	        String deptTime = expected[4];
	        String arrTime = expected[5];
	        String flightCodeExpected = expected[3];
	        String priceExpected = expected[9];
	        String deptDateExpected = expected[2];

	        System.out.println("Expected Flight Details:");
	        System.out.println("From: " + from);
	        System.out.println("To: " + to);
	        System.out.println("Date: " + deptDateExpected);
	        System.out.println("Flight Code: " + flightCodeExpected);
	        System.out.println("Departure Time: " + deptTime);
	        System.out.println("Arrival Time: " + arrTime);
	        System.out.println("Price: " + priceExpected);

	        WebElement detailsSection = driver.findElement(By.xpath("//h6[text()='Selected Flight Details']"));
	        if (!detailsSection.isDisplayed()) {
	            throw new RuntimeException("Flight details section not displayed.");
	        }

	        String origin = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[1]")).getText();
	        String destination = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[4]")).getText();
	        String departTime = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[2]")).getText();
	        String arrivalTime = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[5]")).getText();
	        String flightCodeActual = driver.findElement(By.xpath("//p[@class='MuiTypography-root MuiTypography-body1 d-flex justify-content-center css-hhjtk']")).getText();
	        String priceActual = driver.findElement(By.xpath("//strong[@class='price']")).getText();
	        String departDate = driver.findElement(By.xpath("(//h6[@class='MuiTypography-root MuiTypography-subtitle1 bold css-17jtg62'])[1]/following-sibling::small")).getText();

	        origin = origin.replaceAll(".*\\((.*?)\\)", "$1").trim();
	        destination = destination.replaceAll(".*\\((.*?)\\)", "$1").trim();
	        flightCodeExpected = flightCodeExpected.replaceAll("[()]", "").trim();
	        flightCodeActual = flightCodeActual.replaceAll("[()]", "").trim();
	        priceActual = priceActual.replaceAll("[^0-9]", "");

	        departDate = departDate.replaceAll("on\\s*", "").replaceAll(",", "").trim();
	        String[] dateParts = departDate.split(" ");
	        String day = dateParts[0].replaceAll("\\D+", "");
	        String month = dateParts[1];
	        String monthNum = "";

	       
	        switch (month) {
	            case "Jan": monthNum = "01"; break;
	            case "Feb": monthNum = "02"; break;
	            case "Mar": monthNum = "03"; break;
	            case "Apr": monthNum = "04"; break;
	            case "May": monthNum = "05"; break;
	            case "Jun": monthNum = "06"; break;
	            case "Jul": monthNum = "07"; break;
	            case "Aug": monthNum = "08"; break;
	            case "Sep": monthNum = "09"; break;
	            case "Oct": monthNum = "10"; break;
	            case "Nov": monthNum = "11"; break;
	            case "Dec": monthNum = "12"; break;
	            default:
	                throw new IllegalArgumentException("Unknown month abbreviation: " + month);
	        }

	        if (day.length() == 1) day = "0" + day;
	        String currentYear = String.valueOf(java.time.LocalDate.now().getYear());
	        String departDateFormatted = currentYear + "-" + monthNum + "-" + day;

	        boolean allMatch = origin.equals(from) &&
	                           destination.equals(to) &&
	                           departTime.equals(deptTime) &&
	                           arrivalTime.equals(arrTime) &&
	                           flightCodeExpected.equals(flightCodeActual) &&
	                           priceActual.equals(priceExpected) &&
	                           departDateFormatted.equals(deptDateExpected);

	        if (allMatch) {
	            System.out.println("✅ All flight details match!");
	            Log.ReportEvent("PASS", "✅ Flight details match:\n"
	                    + "Origin: " + from + "\n"
	                    + "Destination: " + to + "\n"
	                    + "Date: " + deptDateExpected + "\n"
	                    + "Flight Code: " + flightCodeExpected + "\n"
	                    + "Departure Time: " + deptTime + "\n"
	                    + "Arrival Time: " + arrTime + "\n"
	                    + "Price: " + priceExpected);
	            ScreenShots.takeScreenShot1();
	        } else {
	            System.out.println("❌ Flight details mismatch.");
	            System.out.println("🔍 Expected vs Actual:");
	            System.out.println("From: " + from + " | " + origin);
	            System.out.println("To: " + to + " | " + destination);
	            System.out.println("Departure Time: " + deptTime + " | " + departTime);
	            System.out.println("Arrival Time: " + arrTime + " | " + arrivalTime);
	            System.out.println("Flight Code: " + flightCodeExpected + " | " + flightCodeActual);
	            System.out.println("Price: " + priceExpected + " | " + priceActual);
	            System.out.println("Date: " + deptDateExpected + " | " + departDateFormatted);

	            Log.ReportEvent("FAIL", "❌ Flight details mismatch.\n"
	                    + "Expected:\n"
	                    + "Origin: " + from + ", Destination: " + to + ", Date: " + deptDateExpected
	                    + ", Flight Code: " + flightCodeExpected + ", Departure: " + deptTime
	                    + ", Arrival: " + arrTime + ", Price: " + priceExpected + "\n"
	                    + "Actual:\n"
	                    + "Origin: " + origin + ", Destination: " + destination + ", Date: " + departDateFormatted
	                    + ", Flight Code: " + flightCodeActual + ", Departure: " + departTime
	                    + ", Arrival: " + arrivalTime + ", Price: " + priceActual);

	            ScreenShots.takeScreenShot1();
	            Assert.fail("Flight details validation failed.");
	        }

	    } catch (Exception e) {
	        System.out.println("❌ Exception during flight detail validation: " + e.getMessage());
	        e.printStackTrace();
	        Log.ReportEvent("ERROR", "❗ Exception occurred: " + e.getMessage());
	        ScreenShots.takeScreenShot1();
	        Assert.fail("Test failed due to exception.");
	    }
	}

}
