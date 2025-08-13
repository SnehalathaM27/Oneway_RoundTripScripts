package com.tripgain.collectionofpages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;


public class TripPlanner {
	WebDriver driver;

	public TripPlanner(WebDriver driver) {

		PageFactory.initElements(driver, this);
		this.driver=driver;
	}
	
	//method to click on trip planner dropdown
	public void clickTripPlannerDropdown() {
		driver.findElement(By.xpath("//button[text()='Trip Planner']")).click();
	}

	
	//method to click dropdown values
	public void clickDropdownValue(String dropdownText) {
	    String dropdown = "//a[text()='" + dropdownText + "']";
	    driver.findElement(By.xpath(dropdown)).click();
	}
	
	//Method to click on create trip
	public void createTrip() {
		driver.findElement(By.xpath("//button[text()='Create A Trip']")).click();
	}
	
	                            //Method to clcik and enter craete trip values
	
	//Method to enter name the trip
	public void enterNameThisTrip(String tripName) {
	    WebElement inputField = driver.findElement(By.xpath("//label[text()='Name this Trip']/following-sibling::div//input"));
	    inputField.clear(); 
	    inputField.sendKeys(tripName);
	}
	
	//method to click on select origin
	
	 
	 @FindBy(xpath = "//*[contains(@id,'origin')]")
	   private WebElement enterLocation;

	 public String enterfrom(String location) throws TimeoutException {
		    enterLocation.clear();
		    enterLocation.sendKeys(location);

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='option']")));

		    selectCityto(location);

		    return location;  // return the input location
		}


	 public void selectlocations(String location) throws TimeoutException {
		    try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		        wait.until(ExpectedConditions.visibilityOfElementLocated(
		            By.xpath("//*[@role='listbox']/parent::div")));

		        wait.until(driver -> driver.findElements(By.xpath("//div[@role='option']")).size() > 0);

		        List<WebElement> options = driver.findElements(By.xpath("//div[@role='option']"));
		        int bestScore = Integer.MAX_VALUE;
		        String bestMatchText = null;

		        String input = location.trim().toLowerCase();

		        for (int i = 0; i < options.size(); i++) {
		            try {
		                WebElement option = options.get(i);
		                String suggestion = option.getText().trim().toLowerCase();
		                int score = levenshteinDistance(input, suggestion);

		                if (score < bestScore) {
		                    bestScore = score;
		                    bestMatchText = option.getText().trim();
		                }
		            } catch (StaleElementReferenceException e) {
		                System.out.println("Stale element at index " + i + ", skipping.");
		            }
		        }

		        if (bestMatchText != null) {
		            int attempts = 0;
		            boolean clicked = false;
		            while (attempts < 3 && !clicked) {
		                try {
		                    // Re-fetch the element each attempt to avoid stale references
		                    WebElement bestMatch = wait.until(ExpectedConditions.elementToBeClickable(
		                        By.xpath("//div[@role='option' and normalize-space(text())='" + bestMatchText + "']")));
		                    bestMatch.click();
		                    System.out.println("Selected best match: " + bestMatchText);
		                    clicked = true;
		                } catch (StaleElementReferenceException e) {
		                    System.out.println("Stale element on click attempt " + (attempts + 1) + ", retrying...");
		                }
		                attempts++;
		            }

		            if (!clicked) {
		                System.out.println("Failed to click the best match after retries.");
		            }

		        } else {
		            System.out.println("No suitable match found for input: " + location);
		        }

		    } catch (NoSuchElementException e) {
		        System.out.println("Input or dropdown not found: " + e.getMessage());
		    } catch (Exception e) {
		        System.out.println("Unexpected error while selecting city or hotel: " + e.getMessage());
		    }
		}

	 public int levenshteinDistance(String a, String b) {
	     int[][] dp = new int[a.length() + 1][b.length() + 1];

	     for (int i = 0; i <= a.length(); i++) {
	         for (int j = 0; j <= b.length(); j++) {
	             if (i == 0) {
	                 dp[i][j] = j;
	             } else if (j == 0) {
	                 dp[i][j] = i;
	             } else {
	                 int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
	                 dp[i][j] = Math.min(Math.min(
	                     dp[i - 1][j] + 1,       // deletion
	                     dp[i][j - 1] + 1),      // insertion
	                     dp[i - 1][j - 1] + cost // substitution
	                 );
	             }
	         }
	     }
	     return dp[a.length()][b.length()];
	 }



	 @FindBy(xpath = "(//input[@class='tg-select__input'])[2]")
	   private WebElement entertoLocation;

	 public String enterTo(String location) throws TimeoutException {
		    entertoLocation.clear();
		    entertoLocation.sendKeys(location);

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='option']")));

		    selectCityto(location);

		    return location;  // returning the input location
		}


		 public void selectCityto(String location) throws TimeoutException {
		     try {
		         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		         // Wait for dropdown container to appear
		         wait.until(ExpectedConditions.visibilityOfElementLocated(
		             By.xpath("//*[@role='listbox']/parent::div")));

		         // Wait until options are loaded
		         wait.until(driver -> driver.findElements(By.xpath("//div[@role='option']")).size() > 0);

		         List<WebElement> initialOptions = driver.findElements(By.xpath("//div[@role='option']"));
		         int bestScore = Integer.MAX_VALUE;
		         String bestMatchText = null;

		         String input = location.trim().toLowerCase();

		         for (int i = 0; i < initialOptions.size(); i++) {
		             try {
		                 WebElement option = driver.findElements(By.xpath("//div[@role='option']")).get(i);
		                 String suggestion = option.getText().trim().toLowerCase();
		                 int score = levenshteinDistance(input, suggestion);

		                 if (score < bestScore) {
		                     bestScore = score;
		                     bestMatchText = option.getText().trim();
		                 }
		             } catch (StaleElementReferenceException e) {
		                 System.out.println("Stale element at index " + i + ", skipping.");
		             }
		         }

		         if (bestMatchText != null) {
		             // Retry clicking best match up to 3 times
		             int attempts = 0;
		             boolean clicked = false;
		             while (attempts < 3 && !clicked) {
		                 try {
		                     WebElement bestMatch = wait.until(ExpectedConditions.elementToBeClickable(
		                         By.xpath("//div[@role='option' and normalize-space(text())='" + bestMatchText + "']")));
		                     bestMatch.click();
		                     System.out.println("Selected best match: " + bestMatchText);
		                     clicked = true;
		                 } catch (StaleElementReferenceException e) {
		                     System.out.println("Stale element on click attempt " + (attempts + 1) + ", retrying...");
		                 }
		                 attempts++;
		             }

		             if (!clicked) {
		                 System.out.println("Failed to click the best match after retries.");
		             }

		         } else {
		             System.out.println("No suitable match found for input: " + location);
		         }

		     } catch (NoSuchElementException e) {
		         System.out.println("Input or dropdown not found: " + e.getMessage());
		     } catch (Exception e) {
		         System.out.println("Unexpected error while selecting city or hotel: " + e.getMessage());
		     }
		 }
	 
	
	    
	    
	
	 //Method to select journey date
	 @FindBy(xpath = "//label[text()='Journey Date']/following-sibling::div")
	    WebElement selectjourdate;
	 
	 public String selectJourneyDate(String day, String MonthandYear) {
		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		    js.executeScript("document.body.style.zoom='80%'");
		    wait.until(ExpectedConditions.elementToBeClickable(selectjourdate)).click();

		    By monthYearHeader = By.xpath("//h2[@class='react-datepicker__current-month']");
		    wait.until(ExpectedConditions.visibilityOfElementLocated(monthYearHeader));

		    String currentMonthYear = driver.findElement(monthYearHeader).getText();

		    if (currentMonthYear.equals(MonthandYear)) {
		        By dayLocator = By.xpath("(//div[@class='react-datepicker__month-container'])[1]//div[text()='" + day + "' and @aria-disabled='false']");
		        wait.until(ExpectedConditions.elementToBeClickable(dayLocator)).click();
		    } else {
		        while (!currentMonthYear.equals(MonthandYear)) {
		            driver.findElement(By.xpath("//button[@aria-label='Next Month']")).click();
		            wait.until(ExpectedConditions.textToBe(monthYearHeader, MonthandYear));
		            currentMonthYear = driver.findElement(monthYearHeader).getText();
		        }
		        By dayLocator = By.xpath("//*[@class='react-datepicker__month-container']//*[text()='" + day + "' and @aria-disabled='false']");
		        wait.until(ExpectedConditions.elementToBeClickable(dayLocator)).click();
		    }

		    js.executeScript("document.body.style.zoom='100%'");

		    String rawDate = day + " " + MonthandYear;  // e.g., "13 August 2025"
		    return normalizeDate(rawDate);    	
		    }

	            
	            //Method to Click on Check-Out  Date
	            public void clickOnReturnDate()
	            {
	                driver.findElement(By.xpath("//label[text()='Return Date']/following-sibling::div")).click();
	            }
	            
	//Method to Select Return Date By Passing Two Paramenters(Date and MounthYear)
	            public String selectReturnDate(String returnDate, String returnMonthAndYear) {
	                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	                clickOnReturnDate();

	                By monthYearHeader = By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]");
	                wait.until(ExpectedConditions.visibilityOfElementLocated(monthYearHeader));

	                String currentMonthYear = driver.findElement(monthYearHeader).getText();

	                while (!currentMonthYear.equals(returnMonthAndYear)) {
	                    driver.findElement(By.xpath("//button[@aria-label='Next Month']")).click();
	                    wait.until(ExpectedConditions.textToBe(monthYearHeader, returnMonthAndYear));
	                    currentMonthYear = driver.findElement(monthYearHeader).getText();
	                }

	                By dayLocator = By.xpath("(//div[@class='react-datepicker__month-container'])[1]//div[text()='" + returnDate + "' and @aria-disabled='false']");
	                WebElement dayElement = wait.until(ExpectedConditions.elementToBeClickable(dayLocator));
	                dayElement.click();

	                String rawDate = returnDate + " " + returnMonthAndYear;  // e.g., "18 August 2025"
	                return normalizeDate(rawDate);       	            }
	            

	            
	  //Method to click on select department
	       public void selectDepartment() throws InterruptedException {
	    	   driver.findElement(By.xpath("//label[text()='Select Department']/following-sibling::div")).click();
	    	   Thread.sleep(1000);
	    	   driver.findElement(By.xpath("//div[@role='option']")).click();
	       }
	       
	          
	 	  //Method to click on select cost center
	 	       public void selectCostCenter() throws InterruptedException {
	 	    	   driver.findElement(By.xpath("//label[text()='Select CostCenter']/following-sibling::div")).click();
	 	    	   Thread.sleep(1000);
	 	    	   driver.findElement(By.xpath("//div[@role='option']")).click();
	 	       }    
	 	       
	 	      //Method to click on select project
	 	       public void selectProject() throws InterruptedException {
	 	    	   driver.findElement(By.xpath("//label[text()='Select Project ']/following-sibling::div")).click();
	 	    	   Thread.sleep(1000);
	 	    	   driver.findElement(By.xpath("//div[@role='option']")).click();
	 	       }
	 	       
	 	       //Method to selct services
	 	      public List<String> selectServices(String... services) {
	 	    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	 	    	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	 	    	        By.xpath("//label[text()='Select Services']/following-sibling::div")
	 	    	    ));

	 	    	    List<String> selectedServices = new ArrayList<>();

	 	    	    for (String service : services) {
	 	    	        String serviceText = "//*[@class='service']//span[text()='" + service + "']";
	 	    	        WebElement serviceOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(serviceText)));
	 	    	        serviceOption.click();

	 	    	        selectedServices.add(service);  // Add to the list of selected services
	 	    	    }

	 	    	    return selectedServices;
	 	    	}


	 	       
//Method to click on add trip button
	 	     public void clickAddTripButton() {
	 	        WebElement addTripButton = driver.findElement(By.xpath("//button[text()='Add Trip']"));
	 	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addTripButton);
	 	        addTripButton.click();
	 	    }

 	 //Method to get details of trip view page 
//	 	    public String[] getTripViewPageDetails() {
//	 	    	
//	 	       String viewpageFromCode = driver.findElement(By.xpath("//span[text()='From']/following-sibling::h6")).getText();
//	 	       String viewpageToCode = driver.findElement(By.xpath("//span[text()='To']/following-sibling::h6")).getText();
//	 	      String viewpageStartDateText = driver.findElement(By.xpath("//span[text()='Start Date']/following-sibling::h6")).getText();
//	 	     String viewpageReturnDateText = driver.findElement(By.xpath("//span[text()='End Date']/following-sibling::h6")).getText();
//
//	 	  String viewpageFrom = normalizeLocation(viewpageFromCode);
//	 	     String viewpageto = normalizeLocation(viewpageToCode);
//	 	     String viewpageStartDate = normalizeDate(viewpageStartDateText);
//	 	     String viewpageReturnDate = normalizeDate(viewpageReturnDateText);
//
//	 	    List<WebElement> services = driver.findElements(By.xpath("//h6[contains(text(), 'Services')]/span"));
//	 	       StringBuilder allServices = new StringBuilder();
//	 	       for (WebElement service : services) {
//	 	           allServices.append(service.getText()).append(" ");
//	 	       }
//	 	       String servicesText = allServices.toString().trim();
//System.out.println(servicesText);
//
// String TripIdText = driver.findElement(By.xpath("(//*[contains(@class,'subtitle1')])[1]")).getText();
// String viewpageTripId = TripIdText.replace("Trip ID: ", "").trim();
// System.out.println("viewpageTripId"+viewpageTripId);
// System.out.println("viewpageTripFrom"+viewpageFrom);
// System.out.println("viewpageTripTo"+viewpageto);
//
// 
//
//	 	       // Return values as a String array
//	 	       return new String[] {
//	 	    		  viewpageFrom,  //0
//	 	    		 viewpageto,     //1
//	 	    		viewpageStartDate,   //2   
//	 	    		viewpageReturnDate,     //3
//	 	           servicesText,            //4
//	 	          viewpageTripId                 //5
//	 	       };
//	 	   }
//	 	    
	 	     
//	 	    public String[] getTripViewPageDetails(Log Log, ScreenShots ScreenShots) {
//	 	       try {
//	 	           String viewpageFromCode = "", viewpageToCode = "";
//	 	           String viewpageStartDateText = "", viewpageReturnDateText = "";
//	 	           String viewpageTripId = "", servicesText = "";
//
//	 	           // Extract values from Trip View Page
//	 	           try {
//	 	               viewpageFromCode = driver.findElement(By.xpath("//span[text()='From']/following-sibling::h6")).getText();
//	 	           } catch (NoSuchElementException e) {
//	 	               Log.ReportEvent("FAIL", "'From' location not found on Trip View Page.");
//	 	               ScreenShots.takeScreenShot1();
//	 	           }
//
//	 	           try {
//	 	               viewpageToCode = driver.findElement(By.xpath("//span[text()='To']/following-sibling::h6")).getText();
//	 	           } catch (NoSuchElementException e) {
//	 	               Log.ReportEvent("FAIL", "'To' location not found on Trip View Page.");
//	 	               ScreenShots.takeScreenShot1();
//	 	           }
//
//	 	           try {
//	 	               viewpageStartDateText = driver.findElement(By.xpath("//span[text()='Start Date']/following-sibling::h6")).getText();
//	 	           } catch (NoSuchElementException e) {
//	 	               Log.ReportEvent("FAIL", "'Start Date' not found on Trip View Page.");
//	 	               ScreenShots.takeScreenShot1();
//	 	           }
//
//	 	           try {
//	 	               viewpageReturnDateText = driver.findElement(By.xpath("//span[text()='End Date']/following-sibling::h6")).getText();
//	 	           } catch (NoSuchElementException e) {
//	 	               Log.ReportEvent("FAIL", "'End Date' not found on Trip View Page.");
//	 	               ScreenShots.takeScreenShot1();
//	 	           }
//
//	 	           // Normalize values
//	 	           String viewpageFrom = normalizeLocation(viewpageFromCode);
//	 	           String viewpageto = normalizeLocation(viewpageToCode);
//	 	           String viewpageStartDate = normalizeDate(viewpageStartDateText);
//	 	           String viewpageReturnDate = normalizeDate(viewpageReturnDateText);
//
//	 	           // Extract services
//	 	           try {
//	 	               List<WebElement> services = driver.findElements(By.xpath("//h6[contains(text(), 'Services')]/span"));
//	 	               StringBuilder allServices = new StringBuilder();
//	 	               for (WebElement service : services) {
//	 	                   allServices.append(service.getText()).append(" ");
//	 	               }
//	 	               servicesText = allServices.toString().trim();
//	 	           } catch (Exception e) {
//	 	               Log.ReportEvent("FAIL", "Could not extract services from Trip View Page.");
//	 	               ScreenShots.takeScreenShot1();
//	 	           }
//
//	 	           // Extract Trip ID
//	 	           try {
//	 	               String tripIdText = driver.findElement(By.xpath("(//*[contains(@class,'subtitle1')])[1]")).getText();
//	 	               viewpageTripId = tripIdText.replace("Trip ID: ", "").trim();
//	 	           } catch (NoSuchElementException e) {
//	 	               Log.ReportEvent("FAIL", "Trip ID not found on Trip View Page.");
//	 	               ScreenShots.takeScreenShot1();
//	 	           }
//
//	 	           // Check critical fields
//	 	           if (viewpageFrom.isEmpty() || viewpageto.isEmpty() || viewpageTripId.isEmpty()) {
//	 	               Log.ReportEvent("FAIL", "Missing critical data in Trip View Page. Required fields: From = '" + viewpageFrom + "', To = '" + viewpageto + "', Trip ID = '" + viewpageTripId + "'");
//	 	               ScreenShots.takeScreenShot1();
//	 	               return null;
//	 	           }
//
//	 	           // Log final extracted values
//	 	           Log.ReportEvent("INFO", "Trip View Page Data");
//	 	           Log.ReportEvent("INFO", " Trip View Origin: " + viewpageFrom);
//	 	           Log.ReportEvent("INFO", "Trip View Destination: " + viewpageto);
//	 	           Log.ReportEvent("INFO", "Trip View Start Date: " + viewpageStartDate);
//	 	           Log.ReportEvent("INFO", "Trip View Return Date: " + viewpageReturnDate);
//	 	           Log.ReportEvent("INFO", "Trip View Services: " + servicesText);
//	 	           Log.ReportEvent("INFO", "Trip View Trip ID: " + viewpageTripId);
//
//	 	           return new String[]{
//	 	               viewpageFrom,
//	 	               viewpageto,
//	 	               viewpageStartDate,
//	 	               viewpageReturnDate,
//	 	               servicesText,
//	 	               viewpageTripId
//	 	           };
//
//	 	       } catch (Exception e) {
//	 	           Log.ReportEvent("FAIL", "Unexpected error in getTripViewPageDetails(): " + e.getMessage());
//	 	           ScreenShots.takeScreenShot1();
//	 	           return null;
//	 	       }
//	 	   }
//
//	 	   
//
	 	   public String normalizeDate(String rawDate) {
	 		    // Remove ordinal suffixes: st, nd, rd, th
	 		    rawDate = rawDate.replaceAll("(?<=\\d)(st|nd|rd|th)", "");
	 		    rawDate = rawDate.replaceAll(",", "").trim(); // Remove commas if any

	 		    String[] possibleFormats = {
	 		        "dd MMMM yyyy",       // 13 August 2025
	 		        "MMM dd yyyy",        // Aug 13 2025
	 		        "yyyy-MM-dd",         // 2025-08-13
	 		        "dd-MM-yyyy",         // 13-08-2025
	 		        "dd/MM/yyyy",         // 13/08/2025
	 		        "dd MMM yyyy"         // 13 Aug 2025
	 		    };

	 		    for (String format : possibleFormats) {
	 		        try {
	 		            SimpleDateFormat inputFormat = new SimpleDateFormat(format, Locale.ENGLISH);
	 		            Date date = inputFormat.parse(rawDate);

	 		            // Desired output format
	 		            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
	 		            return outputFormat.format(date);

	 		        } catch (ParseException e) {
	 		            // Try next format
	 		        }
	 		    }

	 		    System.err.println("⚠ Could not normalize date: " + rawDate);
	 		    return rawDate;
	 		}

	 	// Normalize location string by removing ", ..." suffix
	 	  public String normalizeLocation(String location) {
	 	      return location.split(",")[0].trim();
	 	  }
	 	  
	 	     
	 	     
	 	 public String[] getTripViewPageDetails(Log Log, ScreenShots ScreenShots) throws TimeoutException {
	 	    try {
	 	        String viewpageFromCode = "", viewpageToCode = "";
	 	        String viewpageStartDateText = "", viewpageReturnDateText = "";
	 	        String viewpageTripId = "", servicesText = "";

	 	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

	 	        // From Location
	 	        try {
	 	        	WebElement fromElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	 	        		    By.xpath("//span[normalize-space()='From']/following-sibling::h6")
	 	            ));
	 	            viewpageFromCode = fromElement.getText();
	 	        } catch (NoSuchElementException e) {
	 	            Log.ReportEvent("FAIL", "'From' location not visible on Trip View Page: " + e.getMessage());
	 	            ScreenShots.takeScreenShot1();
	 	        }

	 	        // To Location
	 	        try {
	 	            WebElement toElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	 	                By.xpath("//span[normalize-space()='To']/following-sibling::h6")
	 	            ));
	 	            viewpageToCode = toElement.getText();
	 	        } catch (NoSuchElementException e) {
	 	            Log.ReportEvent("FAIL", "'To' location not visible on Trip View Page: " + e.getMessage());
	 	            ScreenShots.takeScreenShot1();
	 	        }

	 	        // Start Date
	 	        try {
	 	            WebElement startDateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	 	                By.xpath("//span[normalize-space()='Start Date']/following-sibling::h6")
	 	            ));
	 	            viewpageStartDateText = startDateElement.getText();
	 	        } catch (NoSuchElementException e) {
	 	            Log.ReportEvent("FAIL", "'Start Date' not found on Trip View Page: " + e.getMessage());
	 	            ScreenShots.takeScreenShot1();
	 	        }

	 	        // End Date
	 	        try {
	 	            WebElement endDateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	 	                By.xpath("//span[normalize-space()='End Date']/following-sibling::h6")
	 	            ));
	 	            viewpageReturnDateText = endDateElement.getText();
	 	        } catch (NoSuchElementException e) {
	 	            Log.ReportEvent("FAIL", "'End Date' not found on Trip View Page: " + e.getMessage());
	 	            ScreenShots.takeScreenShot1();
	 	        }

	 	        // Normalize values
	 	        String viewpageFrom = safeNormalizeLocation(viewpageFromCode);
	 	        String viewpageTo = safeNormalizeLocation(viewpageToCode);
	 	        String viewpageStartDate = normalizeDate(viewpageStartDateText);
	 	        String viewpageReturnDate = normalizeDate(viewpageReturnDateText);

	 	        // Services
 	        try {
	 	            List<WebElement> services = driver.findElements(By.xpath("//h6[contains(text(), 'Services')]/span"));
	 	            StringBuilder allServices = new StringBuilder();
	 	            for (WebElement service : services) {
	 	                allServices.append(service.getText()).append(" ");
	 	            }
	 	            servicesText = allServices.toString().trim();
	 	        } catch (Exception e) {
	 	            Log.ReportEvent("FAIL", "Could not extract services from Trip View Page: " + e.getMessage());
	 	            ScreenShots.takeScreenShot1();
	 	        }

	 	        // Trip ID
	 	        try {
	 	            WebElement tripIdElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	 	                By.xpath("(//*[contains(@class,'subtitle1')])[1]")
	 	            ));
	 	            String tripIdText = tripIdElement.getText();
	 	            viewpageTripId = tripIdText.replace("Trip ID: ", "").trim();
	 	        } catch (NoSuchElementException e) {
	 	            Log.ReportEvent("FAIL", "Trip ID not found on Trip View Page: " + e.getMessage());
	 	            ScreenShots.takeScreenShot1();
	 	        }

	 	        // Debug logs before critical check
	 	        Log.ReportEvent("DEBUG", "Raw From: [" + viewpageFromCode + "], Normalized: [" + viewpageFrom + "]");
	 	        Log.ReportEvent("DEBUG", "Raw To: [" + viewpageToCode + "], Normalized: [" + viewpageTo + "]");
	 	        Log.ReportEvent("DEBUG", "Trip ID: [" + viewpageTripId + "]");

	 	        System.out.println("🛠 DEBUG: viewpageFrom = [" + viewpageFrom + "]");
	 	        System.out.println("🛠 DEBUG: viewpageTo = [" + viewpageTo + "]");
	 	        System.out.println("🛠 DEBUG: viewpageTripId = [" + viewpageTripId + "]");
	        // Critical fields validation
	 	        if (viewpageFrom.isEmpty() || viewpageTo.isEmpty() || viewpageTripId.isEmpty()) {
	 	            Log.ReportEvent("FAIL", "Missing critical data in Trip View Page. Required fields: From = '" + viewpageFrom + "', To = '" + viewpageTo + "', Trip ID = '" + viewpageTripId + "'");
	 	            ScreenShots.takeScreenShot1();
	 	            return null;
	 	        }

	 	        // Final info logs
	 	        Log.ReportEvent("INFO", "Trip View Page Data");
	 	        Log.ReportEvent("INFO", "Trip View Origin: " + viewpageFrom);
	 	        Log.ReportEvent("INFO", "Trip View Destination: " + viewpageTo);
	 	        Log.ReportEvent("INFO", "Trip View Start Date: " + viewpageStartDate);
	 	        Log.ReportEvent("INFO", "Trip View Return Date: " + viewpageReturnDate);
	 	        Log.ReportEvent("INFO", "Trip View Services: " + servicesText);
 	        Log.ReportEvent("INFO", "Trip View Trip ID: " + viewpageTripId);

	 	        return new String[] {
	 	            viewpageFrom,
	 	            viewpageTo,
	 	            viewpageStartDate,
	 	            viewpageReturnDate,
	 	            servicesText,
	 	            viewpageTripId
	 	        };

	 	    } catch (Exception e) {
	 	        Log.ReportEvent("FAIL", "Unexpected error in getTripViewPageDetails(): " + e.getMessage());
	 	        ScreenShots.takeScreenShot1();
	 	        return null;
	 	    }
	 	}

	 	private String safeNormalizeLocation(String location) {
	 	    try {
	 	        if (location == null || location.trim().isEmpty()) return "";
	 	        return normalizeLocation(location);
	 	    } catch (Exception e) {
	 	        System.out.println("Warning: normalizeLocation failed for input: " + location);
	 	        return location.trim();
	 	    }
	 	}

	 	
	 	   //validation for to check from create trip to view trip details 
		 	   
	 	 public void validateTripDetailsFromCreateTripToViewTrip(String origindetails,
                 String destinationdetails,
                 String journeydatedetails,
                 String returndatedetails,
                 List<String> servicesdetails,Log Log, ScreenShots ScreenShots) throws TimeoutException {

String[] tripViewDetails = getTripViewPageDetails(Log, ScreenShots); // 0=from, 1=to, 2=startDate, 3=returnDate, 4=servicesText
Log.ReportEvent("INFO", "Validating Details From Create Trip Page To View Trip Page");

boolean allPass = true;

// Compare From Location
if (tripViewDetails[0].equalsIgnoreCase(origindetails)) {
Log.ReportEvent("PASS", "Origin location matches: Expected & Found = " + origindetails);
} else {
Log.ReportEvent("FAIL", "Origin location mismatch! Expected: " + origindetails + ", Found: " + tripViewDetails[0]);
ScreenShots.takeScreenShot1();
allPass = false;
}

// Compare To Location
if (tripViewDetails[1].equalsIgnoreCase(destinationdetails)) {
Log.ReportEvent("PASS", "Destination location matches: Expected & Found = " + destinationdetails);
} else {
Log.ReportEvent("FAIL", "Destination location mismatch! Expected: " + destinationdetails + ", Found: " + tripViewDetails[1]);
ScreenShots.takeScreenShot1();
allPass = false;
}

// Compare Start Date
if (tripViewDetails[2].equalsIgnoreCase(journeydatedetails)) {
Log.ReportEvent("PASS", "Start date matches: Expected & Found = " + journeydatedetails);
} else {
Log.ReportEvent("FAIL", "Start date mismatch! Expected: " + journeydatedetails + ", Found: " + tripViewDetails[2]);
ScreenShots.takeScreenShot1();
allPass = false;
}

// Compare Return Date
if (tripViewDetails[3].equalsIgnoreCase(returndatedetails)) {
Log.ReportEvent("PASS", "Return date matches: Expected & Found = " + returndatedetails);
} else {
Log.ReportEvent("FAIL", "Return date mismatch! Expected: " + returndatedetails + ", Found: " + tripViewDetails[3]);
ScreenShots.takeScreenShot1();
allPass = false;
}

// Compare Services (order insensitive)
Set<String> selectedServicesSet = new HashSet<>(servicesdetails);
Set<String> viewPageServicesSet = new HashSet<>(Arrays.asList(tripViewDetails[4].split("\\s+")));

if (selectedServicesSet.equals(viewPageServicesSet)) {
Log.ReportEvent("PASS", "Services match: Expected & Found = " + selectedServicesSet);
} else {
Log.ReportEvent("FAIL", "Services mismatch! Expected: " + selectedServicesSet + ", Found: " + viewPageServicesSet);
ScreenShots.takeScreenShot1();
allPass = false;
}

// Overall result log
if (allPass) {
Log.ReportEvent("PASS", "Overall Result: All trip details match successfully.");
} else {
Log.ReportEvent("FAIL", "Overall Result: One or more trip details did not match.");
ScreenShots.takeScreenShot1();
}
}

//Method to click on flights in view page 
	 	 public void clickFlightsOnline() {
	 		 driver.findElement(By.xpath("//div[contains(@class, 'MuiAccordionSummary-content') and contains(text(), 'FLIGHTS (ONLINE)')]")).click();
	 	 }
	
	 	 //Method to click on class dropdown
	 	public void selectClasses(String classes) throws InterruptedException
		{
	 		driver.findElement(By.xpath("//label[text()='Select Travel Class']/following-sibling::div")).click();
	 		Thread.sleep(1000);
			driver.findElement(By.xpath("//div[contains(@class,'tg-select__menu css-a146i9-menu')]//div[text()='"+classes+"']")).click();	
		}
	 	   
	 	public void clickAddToCartButton() {
	 	    try {
	 	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	 	        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
	 	            By.xpath("//button[normalize-space(text())='Add To Cart']")
	 	        ));
	 	        addToCartButton.click();
	 	    } catch (Exception e) {
	 	        e.printStackTrace(); 
	 	        throw e; 
	 	    }
	 	}


	 	
	 	//Method to get the flights online text details 
	 // Method to get the Flights Online input details
	 	public String[] getFlightsOnlineOrigin(Log Log) throws TimeoutException {
	 	    String originText = "";
	 	    String destText = "";
	 	    String journeyDateText = "";
	 	    String returnDateText = "";
	 	    String classText = "";

	 	    try {
	 	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	 	        JavascriptExecutor js = (JavascriptExecutor) driver;

	 	        // Wait and scroll to "From" field
	 	        WebElement originElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	 	            By.xpath("(//*[contains(@class,'tg-select__single-value')])[2]")));
	 	        js.executeScript("arguments[0].scrollIntoView(true);", originElement);
	 	        originText = originElement.getText().trim();

	 	        // Wait and get "To" field
	 	        WebElement destElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	 	            By.xpath("(//*[contains(@class,'tg-select__single-value')])[3]")));
	 	        destText = destElement.getText().trim();

	 	        // Journey date
	 	        WebElement journeyDateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	 	            By.xpath("//label[text()='Select Journey Date']/following-sibling::div/input")));
	 	        journeyDateText = journeyDateElement.getAttribute("value").trim();

	 	        // Return date
	 	        WebElement returnDateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	 	            By.xpath("//label[text()='Select Return Date']/following-sibling::div/input")));
	 	        returnDateText = returnDateElement.getAttribute("value").trim();

	 	        // Travel class
	 	        WebElement classElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	 	            By.xpath("(//*[contains(@class,'tg-select__single-value')])[1]")));
	 	        classText = classElement.getText().trim();

	 	        // Logging extracted values
	 	        Log.ReportEvent("PASS", "Flights Online Origin Text: " + originText);
	 	        Log.ReportEvent("PASS", "Flights Online Destination Text: " + destText);
	 	        Log.ReportEvent("PASS", "Flights Online Journey Date: " + journeyDateText);
	 	        Log.ReportEvent("PASS", "Flights Online Return Date: " + returnDateText);
	 	        Log.ReportEvent("PASS", "Flights Online Class: " + classText);
	 	        
	 	        System.out.println(originText);
	 	        System.out.println(destText);
	 	        
	 	        
	 	        


	 	    } catch (Exception e) {
	 	        Log.ReportEvent("FAIL", "Exception in getFlightsOnlineOrigin: " + e.getMessage());
	 	        e.printStackTrace();
	 	    }

	 	    return new String[]{originText, destText, journeyDateText, returnDateText, classText};
	 	}


	 	
	 //Method to validate getFlightsOnlineOriginData
	 	public String[] getDataForvalidateFlightsOnlineOriginData(Log Log, ScreenShots ScreenShots) {
	 	    String originCode = "", destCode = "", classText = "", startDateFormatted = "", endDateFormatted = "";

	 	    try {
	 	        // Extract Sector text and parse codes
	 	        String routeText = driver.findElement(By.xpath(
	 	            "//div[contains(@class, 'MuiGrid2-root')]/span[text()='Sector']/following-sibling::h6")).getText();

	 	        Pattern pattern = Pattern.compile("\\((.*?)\\)");
	 	        Matcher matcher = pattern.matcher(routeText);

	 	        if (matcher.find()) {
	 	            originCode = matcher.group(1);
	 	        }
	 	        if (matcher.find()) {
	 	            destCode = matcher.group(1);
	 	        }

	 	        // Extract Journey Type / Class
	 	        try {
	 	            classText = driver.findElement(By.xpath(
	 	                "//span[text()='Journey Type / Class']/following-sibling::h6//small")).getText();
	 	        } catch (NoSuchElementException e) {
	 	            Log.ReportEvent("FAIL", "'Journey Type / Class' not found.");
	 	            ScreenShots.takeScreenShot1();
	 	        }

	 	        // Extract and split journey dates
	 	        try {
	 	            String journeyDates = driver.findElement(By.xpath(
	 	                "//span[text()='Journey Dates']/following-sibling::h6")).getText();

	 	            String[] dates = journeyDates.split("->");
	 	            if (dates.length == 2) {
	 	                startDateFormatted = formatDate(dates[0].trim());
	 	                endDateFormatted = formatDate(dates[1].trim());
	 	            } else {
	 	                Log.ReportEvent("FAIL", "Invalid journey dates format: " + journeyDates);
	 	                ScreenShots.takeScreenShot1();
	 	            }
	 	        } catch (NoSuchElementException e) {
	 	            Log.ReportEvent("FAIL", "'Journey Dates' element not found.");
	 	            ScreenShots.takeScreenShot1();
	 	        }

	 	        // Final validation before returning
	 	        if (originCode.isEmpty() || destCode.isEmpty()) {
	 	            Log.ReportEvent("FAIL", "Origin or Destination code is missing. Extracted values - Origin: '" + originCode + "', Destination: '" + destCode + "'");
	 	            ScreenShots.takeScreenShot1();
	 	            return null;
	 	        }

	 	        // Summary log
	 	        Log.ReportEvent("INFO", "Flight Sector Data Extracted:");
	 	        Log.ReportEvent("INFO", "•Sector Origin Code: " + originCode);
	 	        Log.ReportEvent("INFO", "•Sector Destination Code: " + destCode);
	 	        Log.ReportEvent("INFO", "•Sector Class: " + classText);
	 	        Log.ReportEvent("INFO", "•Sector Start Date: " + startDateFormatted);
	 	        Log.ReportEvent("INFO", "•Sector End Date: " + endDateFormatted);

	 	        return new String[] {
	 	            originCode,
	 	            destCode,
	 	            classText,
	 	            startDateFormatted,
	 	            endDateFormatted
	 	        };

	 	    } catch (Exception e) {
	 	        Log.ReportEvent("FAIL", "Unexpected error in getDataForvalidateFlightsOnlineOriginData(): " + e.getMessage());
	 	        ScreenShots.takeScreenShot1();
	 	        return null;
	 	    }
	 	}


	 	private String formatDate(String rawDate) {
	 	    rawDate = rawDate.replace(",", "");
	 	    String[] parts = rawDate.split(" ");
	 	    if (parts.length != 3) return rawDate;

	 	    String day = parts[0];
	 	    String month = parts[1];
	 	    String year = parts[2];

	 	    return day + "-" + month + "-" + year;
	 	}
	 	
	 	//Method to valiadte flights online data and validated data
	 		
	 		public void validateFlightsOnlineDataToSearchFlightsData(Log Log, ScreenShots ScreenShots) throws TimeoutException {
	 		    String[] flightsonlineOriginData = getFlightsOnlineOrigin(Log);           // [originText, destText, journeyDateText, returnDateText, classText]
	 		    String[] validatedData = getDataForvalidateFlightsOnlineOriginData(Log, ScreenShots); // [originCode, destCode, classText, startDateFormatted, endDateFormatted]

	 		    boolean allPass = true;

	 		    // Compare Origin
	 		   if (flightsonlineOriginData[0].toUpperCase().contains(validatedData[0].toUpperCase())) {
	 		        Log.ReportEvent("INFO", "Validating Flights Online Details with Sector Details");

	 		        Log.ReportEvent("PASS", "Origin matches: Code '" + validatedData[0] + "' is found in '" + flightsonlineOriginData[0] + "'");
	 		    } else {
	 		        Log.ReportEvent("FAIL", "Origin mismatch! [Flights Online Page]: " + flightsonlineOriginData[0]
	 		                + "  [Validated Page]: " + validatedData[0]);
	 		        ScreenShots.takeScreenShot1();
	 		        allPass = false;
	 		    }

	 		    // Destination
	 		    if (flightsonlineOriginData[1].toUpperCase().contains(validatedData[1].toUpperCase())) {
	 		        Log.ReportEvent("PASS", "Destination matches: Code '" + validatedData[1] + "' is found in '" + flightsonlineOriginData[1] + "'");
	 		    } else {
	 		        Log.ReportEvent("FAIL", "Destination mismatch! [Flights Online Page]: " + flightsonlineOriginData[1]
	 		                + "  [Validated Page]: " + validatedData[1]);
	 		        ScreenShots.takeScreenShot1();
	 		        allPass = false;
	 		    }

//	 		    // Class
//	 		    if (flightsonlineOriginData[4].equalsIgnoreCase(validatedData[2])) {
//	 		        Log.ReportEvent("PASS", "Class matches: " + flightsonlineOriginData[4]);
//	 		    } else {
//	 		        Log.ReportEvent("FAIL", "Class mismatch! [Flights Online Page]: " + flightsonlineOriginData[4]
//	 		                + "  [Validated Page]: " + validatedData[2]);
//	 		        ScreenShots.takeScreenShot1();
//	 		        allPass = false;
//	 		    }

	 		    // Journey Date
	 		    String formattedJourneyDate = formatDate(flightsonlineOriginData[2]);
	 		    if (formattedJourneyDate.equalsIgnoreCase(validatedData[3])) {
	 		        Log.ReportEvent("PASS", "Journey Date matches: " + formattedJourneyDate);
	 		    } else {
	 		        Log.ReportEvent("FAIL", "Journey Date mismatch! [Flights Online Page]: " + formattedJourneyDate
	 		                + "  [Validated Page]: " + validatedData[3]);
	 		        ScreenShots.takeScreenShot1();
	 		        allPass = false;
	 		    }

	 		    // Return Date
	 		    String formattedReturnDate = formatDate(flightsonlineOriginData[3]);
	 		    if (formattedReturnDate.equalsIgnoreCase(validatedData[4])) {
	 		        Log.ReportEvent("PASS", "Return Date matches: " + formattedReturnDate);
	 		    } else {
	 		        Log.ReportEvent("FAIL", "Return Date mismatch! [Flights Online Page]: " + formattedReturnDate
	 		                + "  [Validated Page]: " + validatedData[4]);
	 		        ScreenShots.takeScreenShot1();
	 		        allPass = false;
	 		    }

	 		    if (allPass) {
	 		        Log.ReportEvent("PASS", " All flight details matched successfully.");
	 		    } else {
	 		        Log.ReportEvent("FAIL", " Some flight details did not match. Please review logs above.");
	 		    }
	 		}

	 		//Method to clcik search flights button
	 		public void clickSearchFlightsButton() {
	 			driver.findElement(By.xpath("//button[text()='Search Flights']")).click();
	 		}
	 	
	 		//Method to get trip id, locations from flights results screen 
	 		public String[] getTripIdAndODLocInFlightsResultsScreen(Log Log) {
	 			 JavascriptExecutor js = (JavascriptExecutor) driver;
	 		   	js.executeScript("window.scrollTo(0, 0);");


	 		    String tripId = "";
	 		    String originCode = "";
	 		    String destinationCode = "";

	 		    try {
	 		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	 		        // Wait for and get Trip ID text
	 		        WebElement headerTripElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header_trip_name")));
	 		        String fullTripText = headerTripElement.getText().trim();

	 		        if (fullTripText.contains("(") && fullTripText.contains(")")) {
	 		            tripId = fullTripText.substring(fullTripText.indexOf('(') + 1, fullTripText.indexOf(')')).trim();
	 		        } else if (fullTripText.contains(":")) {
	 		            // Fallback: "Trip ID: TGTR20250808BT8QFK"
	 		            tripId = fullTripText.split(":")[1].trim();
	 		        } else {
	 		            tripId = fullTripText.trim(); // Last 
	 		        }

	 		        // Wait for and get trip info locations text 
	 		        WebElement tripInfoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("trip_info")));
	 		        String tripInfoText = tripInfoElement.getText().trim();

	 		        // Extract origin and destination
	 		        // Expected format: "Route: BLR -> DEL, Class: Economy"
	 		        if (tripInfoText.contains("->")) {
	 		            String[] parts = tripInfoText.split(":");
	 		            if (parts.length > 1) {
	 		                String routePart = parts[1].split(",")[0].trim(); // "BLR -> DEL"
	 		                String[] codes = routePart.split("->");
	 		                if (codes.length == 2) {
	 		                    originCode = codes[0].trim();
	 		                    destinationCode = codes[1].trim();
	 		                }
	 		            }
	 		        }

	 		        // Logging extracted values
	 		        Log.ReportEvent("PASS", "Result screen Extracted Trip ID: " + tripId);
	 		        Log.ReportEvent("PASS", "Result screen Origin: " + originCode);
	 		        Log.ReportEvent("PASS", "Result screen Destination: " + destinationCode);


	 		    } catch (Exception e) {
	 		        Log.ReportEvent("FAIL", "Exception in getTripIdAndODLocInFlightsResultsScreen: " + e.getMessage());

	 		        e.printStackTrace();
	 		    }

	 		    return new String[]{tripId, originCode, destinationCode};
	 		}

	 		
	 //validate the results like id, loc from view page to results pg
//	 		public void validateTripIdAndODLocInFlightsResultsScreen(Log Log, ScreenShots ScreenShots) throws TimeoutException {
//	 		    // Get Trip ID and other data from Trip View Page
//	 		    String[] viewPageData = getTripViewPageDetails(Log, ScreenShots);
//	 		    
//	 		   System.out.println("DEBUG: viewPageData = " + Arrays.toString(viewPageData));
//	 		  System.out.println("DEBUG: viewPageData.length = " + (viewPageData != null ? viewPageData.length : "null"));
//
//	 		    if (viewPageData == null || viewPageData.length < 6) {
//	 		        Log.ReportEvent("FAIL", "Failed to retrieve complete data from Trip View Page. Trip ID might be missing.");
//	 		        ScreenShots.takeScreenShot1();
//	 		        return;
//	 		    }
//
//	 		    String viewPageTripId = viewPageData[5].trim();      // Trip ID
//	 		    String viewPageFromCode = viewPageData[0].trim();    // From Location
//	 		    String viewPageToCode = viewPageData[1].trim();      // To Location
//
//	 		    // Get Origin and Destination codes from validated sector data
//	 		    String[] validatedFlightsOnlineSectorData = getDataForvalidateFlightsOnlineOriginData(Log, ScreenShots);
//	 		    if (validatedFlightsOnlineSectorData == null || validatedFlightsOnlineSectorData.length < 2) {
//	 		        Log.ReportEvent("FAIL", "Failed to retrieve validated origin/destination sector data.");
//	 		        ScreenShots.takeScreenShot1();
//	 		        return;
//	 		    }
//
//	 		    String sectorOriginCode = validatedFlightsOnlineSectorData[0].trim();
//	 		    String sectorDestCode = validatedFlightsOnlineSectorData[1].trim();
//
//	 		    // Get Trip ID, Origin, Destination from Flights Results Screen
//	 		    String[] flightsResultsData = getTripIdAndODLocInFlightsResultsScreen(Log);
//	 		    if (flightsResultsData == null || flightsResultsData.length < 3) {
//	 		        Log.ReportEvent("FAIL", "Failed to retrieve data from Flights Results screen.");
//	 		        ScreenShots.takeScreenShot1();
//	 		        return;
//	 		    }
//
//	 		    String resultsTripId = flightsResultsData[0].trim();
//	 		    String resultsOriginCode = flightsResultsData[1].trim();
//	 		    String resultsDestCode = flightsResultsData[2].trim();
//
//	 		    // Log raw values for debugging
//	 		    System.out.println("🔎 View Page Trip ID         : [" + viewPageTripId + "]");
//	 		    System.out.println("🔎 Flights Results Trip ID   : [" + resultsTripId + "]");
//	 		    System.out.println("🔎 Sector Origin Code        : [" + sectorOriginCode + "]");
//	 		    System.out.println("🔎 Results Origin Code       : [" + resultsOriginCode + "]");
//	 		    System.out.println("🔎 Sector Destination Code   : [" + sectorDestCode + "]");
//	 		    System.out.println("🔎 Results Destination Code  : [" + resultsDestCode + "]");
//
//	 		    boolean allPass = true;
//
//	 		    // 🔹 Validate Trip ID
//	 		    if (resultsTripId.equalsIgnoreCase(viewPageTripId)) {
//	 		        Log.ReportEvent("PASS", "Trip ID matches: '" + resultsTripId + "'");
//	 		    } else {
//	 		        Log.ReportEvent("FAIL", "Trip ID mismatch! Expected: '" + viewPageTripId + "', Found: '" + resultsTripId + "'");
//	 		        ScreenShots.takeScreenShot1();
//	 		        allPass = false;
//	 		    }
//
//	 		    // 🔹 Validate Origin Code
//	 		    if (resultsOriginCode.equalsIgnoreCase(sectorOriginCode)) {
//	 		        Log.ReportEvent("PASS", "Origin code matches: '" + resultsOriginCode + "'");
//	 		    } else {
//	 		        Log.ReportEvent("FAIL", "Origin code mismatch! Expected: '" + sectorOriginCode + "', Found: '" + resultsOriginCode + "'");
//	 		        ScreenShots.takeScreenShot1();
//	 		        allPass = false;
//	 		    }
//
//	 		    // 🔹 Validate Destination Code
//	 		    if (resultsDestCode.equalsIgnoreCase(sectorDestCode)) {
//	 		        Log.ReportEvent("PASS", "Destination code matches: '" + resultsDestCode + "'");
//	 		    } else {
//	 		        Log.ReportEvent("FAIL", "Destination code mismatch! Expected: '" + sectorDestCode + "', Found: '" + resultsDestCode + "'");
//	 		        ScreenShots.takeScreenShot1();
//	 		        allPass = false;
//	 		    }
//
//	 		    if (allPass) {
//	 		        Log.ReportEvent("PASS", "✅ All validations passed: Trip ID, Origin and Destination codes match successfully.");
//	 		    } else {
//	 		        Log.ReportEvent("FAIL", "❌ One or more validations failed. See individual failure logs for details.");
//	 		    }
//	 		}
	 		
	 	// Method to extract Trip ID from the Trip View Page
	 		public String getViewPageId(Log Log, ScreenShots ScreenShots) throws TimeoutException {
	 		    String viewPageTripId = "";

	 		    try {
	 		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

	 		        WebElement tripIdElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	 		            By.xpath("(//*[contains(@class,'subtitle1')])[1]")));

	 		        String tripIdText = tripIdElement.getText().trim(); // Example: "Trip ID: TGTR20251108RDARUI"
	 		        viewPageTripId = tripIdText.replace("Trip ID:", "").trim();
	 		        System.out.println(viewPageTripId);

	 		        Log.ReportEvent("PASS", "Trip ID extracted from View Page: " + viewPageTripId);
	 		    } catch (NoSuchElementException e) {
	 		        Log.ReportEvent("FAIL", "Trip ID element not found on Trip View Page: " + e.getMessage());
	 		        ScreenShots.takeScreenShot1();
	 		        e.printStackTrace();
	 		    } catch (Exception e) {
	 		        Log.ReportEvent("FAIL", "Unexpected error in getViewPageId: " + e.getMessage());
	 		        ScreenShots.takeScreenShot1();
	 		        e.printStackTrace();
	 		    }

	 		    return viewPageTripId;
	 		}


	 		//Method for to validate loc details and id from view page screen to flights result page screen 
	 		public void validateTripIdAndODLocInFlightsResultsScreen(
	 			    String[] flightInputData,
	 			    String viewPageTripId,
	 			    String[] resultsData,
	 			    Log log,
	 			    ScreenShots screenshots) {

	 			    boolean allPass = true;

	 			    try {
	 			        if (flightInputData == null || flightInputData.length < 5) {
	 			            log.ReportEvent("FAIL", "Flight input data is incomplete or null.");
	 			            screenshots.takeScreenShot1();
	 			            return;
	 			        }

	 			        if (resultsData == null || resultsData.length < 3) {
	 			            log.ReportEvent("FAIL", "Results screen data is incomplete or null.");
	 			            screenshots.takeScreenShot1();
	 			            return;
	 			        }

	 			        String inputOrigin = flightInputData[0];
	 			        String inputDestination = flightInputData[1];
	 			        String inputJourneyDate = flightInputData[2];
	 			        String inputReturnDate = flightInputData[3];
	 			        String inputClass = flightInputData[4];

	 			        String resultTripId = resultsData[0];
	 			        String resultOrigin = resultsData[1];
	 			        String resultDestination = resultsData[2];

	 			        // --------------------------------------------
	 			        // VALIDATIONS
	 			        // --------------------------------------------

	 			        log.ReportEvent("INFO", "Validating Trip ID and O&D Codes from view page to flights results page");

	 			        // ORIGIN
	 			        if (inputOrigin.toUpperCase().contains(resultOrigin.toUpperCase())) {
	 			            log.ReportEvent("PASS", "Origin matches: Result Screen = " + resultOrigin + ", Flights Online = " + inputOrigin);
	 			        } else {
	 			            log.ReportEvent("FAIL", "Origin mismatch! Result Screen = " + resultOrigin + ", Flights Online = " + inputOrigin);
	 			            screenshots.takeScreenShot1();
	 			            allPass = false;
	 			        }

	 			        // DESTINATION
	 			        if (inputDestination.toUpperCase().contains(resultDestination.toUpperCase())) {
	 			            log.ReportEvent("PASS", "Destination matches: Result Screen = " + resultDestination + ", Flights Online = " + inputDestination);
	 			        } else {
	 			            log.ReportEvent("FAIL", "Destination mismatch! Result Screen = " + resultDestination + ", Flights Online = " + inputDestination);
	 			            screenshots.takeScreenShot1();
	 			            allPass = false;
	 			        }

	 			        // TRIP ID
	 			        if (resultTripId != null && viewPageTripId != null && resultTripId.equalsIgnoreCase(viewPageTripId)) {
	 			            log.ReportEvent("PASS", "Trip ID matches: Result Screen = " + resultTripId + ", View Page = " + viewPageTripId);
	 			        } else {
	 			            log.ReportEvent("FAIL", "Trip ID mismatch! Result Screen = " + resultTripId + ", View Page = " + viewPageTripId);
	 			            screenshots.takeScreenShot1();
	 			            allPass = false;
	 			        }

	 			       

	 			        // Final Pass/Fail
	 			        if (allPass) {
	 			            log.ReportEvent("PASS", "All validations passed: Trip ID and O&D Codes are consistent across screens.");
	 			        } else {
	 			            log.ReportEvent("FAIL", "Some validations failed: See above for details.");
	 			        }

	 			    } catch (Exception e) {
	 			        log.ReportEvent("FAIL", "Exception in validateTripIdAndODLocInFlightsResultsScreen: " + e.getMessage());
	 			        screenshots.takeScreenShot1();
	 			        e.printStackTrace();
	 			    }
	 			}
	 		
	 		//Method to click on add trip and continue button
	 		public void clickAddTripAndContinueButton() {
	 			driver.findElement(By.xpath("//button[text()='Add to Trip and Continue']")).click();
	 		}
	 		
	 		//Method to click on submit trip button
	 		public void clickSubmitTripButton() {
	 			driver.findElement(By.xpath("//button[text()='Submit Trip']")).click();
	 		}
	 		
	 		//Method to click on yes submit button
	 		public void clickYesSubmitButton() {
	 			driver.findElement(By.xpath("//button[text()='Yes, Submit']")).click();
	 		}

	 		                //My Requests Awaiting Approval page details  (Request screen)
	 		
	 	
	 		public void getApprovalIdFromRequestScreen(String viewPageTripId, Log log, ScreenShots screenshots) {
	 		    try {
	 		    	//viewPageTripId -- getting id from view page
	 		        if (viewPageTripId == null || viewPageTripId.isEmpty()) {
	 		            log.ReportEvent("FAIL", "Provided Trip ID is null or empty.");
	 		            return;
	 		        }

	 		        // Get all approval ID from the Request screen
	 		        List<WebElement> tripId = driver.findElements(
	 		            By.xpath("//button[contains(@class,'tg-approval-requests-trip-id')]"));

	 		        boolean matchFound = false;

	 		        for (WebElement tripIds : tripId) {
	 		            String approvalIdText = tripIds.getText().trim();

	 		            if (approvalIdText.equalsIgnoreCase(viewPageTripId)) {
	 		                log.ReportEvent("PASS", "Matching approval ID found from view page to requests page : " + approvalIdText);

	 		                // Scroll to element for visibility
	 		                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tripIds);

	 		                // Click the corresponding "Details" button in the same row
	 		                WebElement detailsButton = tripIds
	 		                    .findElement(By.xpath("//button[text()='Details']"));
	 		                detailsButton.click();

	 		                matchFound = true;
	 		                break;
	 		            }
	 		        }

	 		        if (!matchFound) {
	 		            log.ReportEvent("FAIL", "No matching approval ID found for Trip ID from view page to requests page : " + viewPageTripId);
	 		            screenshots.takeScreenShot1();
	 		        }

	 		    } catch (Exception e) {
	 		        log.ReportEvent("FAIL", "Exception in getApprovalIdFromRequestScreen from view page to requests page : " + e.getMessage());
	 		        screenshots.takeScreenShot1();
	 		        e.printStackTrace();
	 		    }
	 		}

	 	//Method to get price from request screen view
	 		public String[] getPriceFromRequestScreenView(Log log,ScreenShots screenshots) {
	 		    String tripCostPriceText = "";
	 		    String flightsOnlinePriceText = "";

	 		    try {
	 		        // Get "Trip Cost" from Request screen
	 		        WebElement tripCostPrice = driver.findElement(
	 		            By.xpath("(//*[text()='Trip Cost']/following-sibling::h6)[1]"));
	 		        tripCostPriceText = tripCostPrice.getText().trim();
	 		        System.out.println("Trip Cost: " + tripCostPriceText);
	 		        log.ReportEvent("INFO", "Trip Cost: " + tripCostPriceText);


	 		        // Click Flights Online button
	 		        clickFlightsOnline();

	 		        // Get "Flights Online" price
	 		       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	 		        WebElement flightsOnlinePrice = wait.until(ExpectedConditions.visibilityOfElementLocated(
	 		            By.xpath("//div[contains(@class,'price-container-flight') and contains(@class,'mb-16')]//strong[@class='price']")
	 		        ));

	 		        flightsOnlinePriceText = flightsOnlinePrice.getText().trim();
	 		        System.out.println("Flights Online Price: " + flightsOnlinePriceText);
	 		        log.ReportEvent("INFO", "Flights Online Price: " + flightsOnlinePriceText);

	 		    } catch (Exception e) {
	 		        System.out.println("Error in getPriceFromRequestScreenView: " + e.getMessage());
	 		        e.printStackTrace();
	 		    }

	 		    return new String[] { tripCostPriceText, flightsOnlinePriceText };
	 		}
	 		
	 		//Method to validate price from flights booking page to request view page price
	 		public void validatePriceFromFlightsBookingPageToRequestViewPage(
	 			    String[] reuestScreenViewPrice,
	 			    String bookingGrandTotal,
	 			    Log log,
	 			    ScreenShots screenshots) {

	 			    try {
	 			        if (reuestScreenViewPrice == null || reuestScreenViewPrice.length < 2 || bookingGrandTotal == null) {
	 			            log.ReportEvent("FAIL", "Missing required price data for validation.");
	 			            screenshots.takeScreenShot1();
	 			            return;
	 			        }

	 			        // Clean all values to extract only numeric parts
	 			        String tripCost = reuestScreenViewPrice[0].replaceAll("[^0-9]", "");
	 			        String flightsOnlinePrice = reuestScreenViewPrice[1].replaceAll("[^0-9]", "");
	 			        String grandTotal = bookingGrandTotal.replaceAll("[^0-9]", "");

	 			        log.ReportEvent("INFO", "Validating prices from flights booking screen to request view page...");
	 			        log.ReportEvent("INFO", "Trip Cost from Request Screen: " + tripCost);
	 			        log.ReportEvent("INFO", "Flights Online Price from Request Screen: " + flightsOnlinePrice);
	 			        log.ReportEvent("INFO", "Grand Total from Booking Page: " + grandTotal);

	 			        boolean allMatch = true;

	 			        if (grandTotal.equals(tripCost)) {
	 			            log.ReportEvent("PASS", "Grand Total matches Trip Cost.");
	 			        } else {
	 			            log.ReportEvent("FAIL", "Mismatch: Grand Total does not match Trip Cost.");
	 			            screenshots.takeScreenShot1();
	 			            allMatch = false;
	 			        }

	 			        if (grandTotal.equals(flightsOnlinePrice)) {
	 			            log.ReportEvent("PASS", "Grand Total matches Flights Online Price.");
	 			        } else {
	 			            log.ReportEvent("FAIL", "Mismatch: Grand Total does not match Flights Online Price.");
	 			            screenshots.takeScreenShot1();
	 			            allMatch = false;
	 			        }

	 			        if (allMatch) {
	 			            log.ReportEvent("PASS", "All price values are consistent across screens.");
	 			        } else {
	 			            log.ReportEvent("FAIL", "Price validation failed. Inconsistencies found.");
	 			        }

	 			    } catch (Exception e) {
	 			        log.ReportEvent("FAIL", "Exception in validatePriceFromFlightsBookingPageToRequestViewPage: " + e.getMessage());
	 			        screenshots.takeScreenShot1();
	 			        e.printStackTrace();
	 			    }
	 			}

	 		//Method to get details from request view page
	 			
//	 			//get booking page return details
//				 public String[] getFlightsOnlineDetailsAfterApproval(String departStopsText) throws InterruptedException {
//
//					  String BookingPagedepartFromText = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartorigin')])[1]")).getText().trim();
//					    String bookingFromCode = extractLocationCode(BookingPagedepartFromText);
//	
//					    String BookingPagedepartToText = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartdestination')])[last()]")).getText().trim();
//					    String bookingToCode = extractLocationCode(BookingPagedepartToText);
//	
//					    String BookingdepartDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartdepdate')])[1]")).getText().trim();
//	
//					    if (BookingdepartDate.toLowerCase().startsWith("on ")) {
//					    	BookingdepartDate = BookingdepartDate.substring(3).trim();
//					    }
//					    BookingdepartDate = BookingdepartDate.replaceAll(",$", "").trim();  // Remove trailing comma
//				    // Format dates with suffix (e.g. "8th Aug")
//				    String BookingPagedepartdepartDate = formatDateWithSuffix(BookingdepartDate);
//				    
//
//					    String BookinarrDate = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbReturnarrdate')])[last()]")).getText().trim();
//
//					    if (BookinarrDate.toLowerCase().startsWith("on ")) {
//					    	BookinarrDate = BookinarrDate.substring(3).trim();
//					    }
//					    BookinarrDate = BookinarrDate.replaceAll(",$", "").trim();  // Remove trailing comma
//				    // Format dates with suffix (e.g. "8th Aug")
//				    String BookingdepartArrivalDate = formatDateWithSuffix(BookinarrDate);
//				      
//
//				    String BookingdepartTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartdeptime')])[1]")).getText().trim();
//	
//							    String BookingarrivalTime = driver.findElement(By.xpath("(//*[contains(@class,'tg-fbDepartarrtime')])[last()]")).getText().trim();
//								    
//	
//							    String BookingPagedepartflightClass = driver.findElement(By.xpath("//*[contains(@class,'tg-fbDepartcabinclass')]")).getText().trim();
//		
//
//							    // Get all durations
//							    List<WebElement> bookingdurationElements = driver.findElements(By.xpath("//*[contains(@class,'tg-fbDepartduration')]"));
//							    StringBuilder allDurationsBuilder = new StringBuilder();
//
//							    for (int i = 0; i < bookingdurationElements.size(); i++) {
//							        String dur = bookingdurationElements.get(i).getText().trim();
//							        allDurationsBuilder.append(dur);
//							        if (i != bookingdurationElements.size() - 1) {    //this is for donyt add commas/space to last element 
//							            allDurationsBuilder.append(", ");
//							        }
//							    }
//
//							    String allbookingDurations = allDurationsBuilder.toString();  //convert to string
//
//							    
//							    
//							    
//							    String bookingpageairlineText = driver.findElement(By.xpath("//*[contains(@class,'tg-fbDepartcarriername')]")).getText().trim();
//								    String departairlinetext = bookingpageairlineText.split(" -")[0].trim();
//				
//								    System.out.println("Airline Name: " + departairlinetext);
//								    
//							    String faretype = driver.findElement(By.xpath("//*[contains(@class,'tg-fb-Departfaretype')]")).getText().trim();
//							    String BookingPagedepartFareText = faretype.replace("Fare", "").trim();
//				
//								    String BookingPageCabinBaggageText = driver.findElement(By.xpath("(//span[contains(@class,'caption')]//strong[1])[1]")).getText().trim();
//			
//								    String BookingPageCheckInBaggageText = driver.findElement(By.xpath("(//span[contains(@class,'caption')]//strong[2])[1]")).getText().trim();
//				
//								    // Get all connecting flights text
//								    List<WebElement> bookingconnectingFlightsText = driver.findElements(By.xpath("//h6[contains(@class, 'tg-fbDepartflight')]/ancestor::div[contains(@class,'MuiCardContent-root')]//strong[contains(@class, 'tg-fb-layover-destination')]"));
//								    StringBuilder allConnectingFlightsBuilder = new StringBuilder();
//
//								    for (int i = 0; i < bookingconnectingFlightsText.size(); i++) {
//								        String dur = bookingconnectingFlightsText.get(i).getText().trim();
//								        allConnectingFlightsBuilder.append(dur);
//								        if (i != bookingconnectingFlightsText.size() - 1) {    //this is for donyt add commas/space to last element 
//								        	allConnectingFlightsBuilder.append(", ");
//								        }
//								    }
//
//								    String allbookingConnectingFlightsText = allConnectingFlightsBuilder.toString();  //convert to string
//
//				
//
//					    return new String[]{
//					    		bookingFromCode,   // 0
//					    		bookingToCode,      // 1
//					    		BookingPagedepartdepartDate,       
//					    		BookingdepartArrivalDate,       
//					    		BookingdepartTime,     
//					    		BookingarrivalTime,           
//					    		BookingPagedepartflightClass,         
//					    		allbookingDurations,      
//					    		departairlinetext,
//					    		BookingPagedepartFareText,
//					    		BookingPageCabinBaggageText,
//					    		BookingPageCheckInBaggageText,
//					    		allbookingConnectingFlightsText
//					              
//					    };
//					}
//				 
//	 			
//	 			
//	 			
//	 			
	 		
	 

}