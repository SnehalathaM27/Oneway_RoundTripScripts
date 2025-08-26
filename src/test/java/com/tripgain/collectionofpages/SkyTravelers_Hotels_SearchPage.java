package com.tripgain.collectionofpages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;

public class SkyTravelers_Hotels_SearchPage {
	WebDriver driver;

	public SkyTravelers_Hotels_SearchPage (WebDriver driver)
	{
		PageFactory.initElements(driver, this);
		this.driver=driver;
	}

	//Method to click on hotels 
	
	public void clickOnHotels() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='Hotel']")).click();
	}
	
	public void enterDestinationForHotels(String city) {
		try {
			// Optionally adjust zoom - only if necessary
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.body.style.zoom='80%'");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
			// Wait for the search input to be visible and interactable
			WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Enter Destination / Area / Property']/following-sibling::div//input")));
			// Clear and send keys
			searchField.clear();
			
			searchField.sendKeys(city);
			Thread.sleep(1000);

			// Wait for the dropdown options to appear
			List<WebElement> listOfProperty = wait.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='location-focused location-option']"))
			);

			if (!listOfProperty.isEmpty()) {
				Thread.sleep(2000);
				listOfProperty.get(0).click();
				Thread.sleep(1000);
			} else {
				System.out.println("No options found in the dropdown.");
			}

		} catch (Exception e) {
			System.out.println("Failed to select a location from the list: " + e.getMessage());
		}
	}

	@FindBy(xpath = "(//*[@class='app-landing-datepicker'])[1]")
	WebElement datePickerInput;
	
	//Method to Select Check-In Date By Passing Two Paramenters(Date and MounthYear)
		public void selectDate(String day, String MonthandYear) throws InterruptedException
		{
			TestExecutionNotifier.showExecutionPopup();
			datePickerInput.click();
			String Date=driver.findElement(By.xpath("(//div[@class='react-datepicker__current-month'])[1]")).getText();
			if(Date.contentEquals(MonthandYear))
			{
				Thread.sleep(4000);
				driver.findElement(By.xpath("(//div[@class='react-datepicker__month-container'])[1]//div[text()='"+day+"' and @aria-disabled='false']")).click();
				Thread.sleep(4000);
			}else {
				while(!Date.contentEquals(MonthandYear))
				{
					Thread.sleep(500);
					driver.findElement(By.xpath("//button[@aria-label='Next Month']")).click();
					if(driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText().contentEquals(MonthandYear))
					{
						driver.findElement(By.xpath("(//div[@class='react-datepicker__month-container'])[1]//div[text()='"+day+"' and @aria-disabled='false']")).click();
						break;
					}

				}
			}
		}

		//Method to Click on Check-Out  Date
		public void clickOnReturnDate()
		{
			driver.findElement(By.xpath("(//*[@class='app-landing-datepicker'])[2]")).click();
		}
		
		//Method to Select Return Date By Passing Two Paramenters(Date and MounthYear)
		public void selectReturnDate(String returnDate, String returnMonthAndYear) throws InterruptedException
		{
			clickOnReturnDate();
			String Date=driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::div)[1]")).getText();
			if(Date.contentEquals(returnMonthAndYear))
			{
				driver.findElement(By.xpath("(//div[@class='react-datepicker__month-container'])[1]//div[text()='"+returnDate+"' and @aria-disabled='false']")).click();
				Thread.sleep(4000);
			}else {
				while(!Date.contentEquals(returnMonthAndYear))
				{
					Thread.sleep(500);
					driver.findElement(By.xpath("//button[@aria-label='Next Month']")).click();
					if(driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::div)[1]")).getText().contentEquals(returnMonthAndYear))
					{
						driver.findElement(By.xpath("//div[text()='"+returnDate+"']")).click();
						break;
					}
				}
			}
		}
		
		//Method to add room, adt and child
		public void addRoom(int roomcount, int adultCount , int childCount, int childAge) throws InterruptedException {
			Thread.sleep(2000);
			try {
				System.out.println("Expanding room selection...");
				driver.findElement(By.xpath("//span[text()='Room & Guest']")).click();
			} catch (Exception e) {
				System.out.println("Failed to click expand icon: " + e.getMessage());
			}
			JavascriptExecutor js = (JavascriptExecutor) driver;

			for(int i = 0; i < roomcount - 1; i++) {
				try {
					System.out.println("Adding room: " + (i + 2));
					driver.findElement(By.xpath("//button[text()='Add Room']")).click();
				} catch (Exception e) {
					System.out.println("Failed to click 'Add Room' button at index " + i + ": " + e.getMessage());
				}
			}

			Thread.sleep(3000);

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Room 1:')]")));
			} catch (Exception e) {
				System.out.println("Room elements not visible within timeout: " + e.getMessage());
			}

			List<WebElement> listOfRooms = driver.findElements(By.xpath("//span[contains(text(),'Room 1:')]"));
			System.out.println("Rooms found: " + listOfRooms.size());

			for(int i = 0; i < listOfRooms.size(); i++) {
				WebElement roomElement = listOfRooms.get(i);
				String roomText = roomElement.getText();
				String[] roomTextSplit1 = roomText.split(" ");
				String finalRoomText = roomTextSplit1[1].trim();

				System.out.println("Configuring Room " + finalRoomText);
				System.out.println("Total rooms: " + listOfRooms.size());
				System.out.println("Room index: " + i);
				System.out.println("Adult count: " + adultCount);

				// Add Adults
				String xpath = "//span[text()='Adults(12y+)']/following-sibling::ul/li[text()='" + adultCount + "']";
				try {
				    WebElement addAdult = driver.findElement(By.xpath(xpath));
				    Thread.sleep(1000);
				    addAdult.click();
				    System.out.println("Adult count set to " + adultCount + " in Room " + finalRoomText);
				} catch (Exception e) {
				    System.out.println("Failed to set adult count to " + adultCount + " in Room " + finalRoomText + ": " + e.getMessage());
				}


				// Add Children
				if (childCount >= 1) {
				    // Your original child count xpath
				    String childXpath = "//span[text()='Child']/following-sibling::ul/li[text()='" + childCount + "']";

				    try {
				        WebElement addChild = driver.findElement(By.xpath(childXpath));
				        Thread.sleep(1000);
				        addChild.click();
				        System.out.println("Child count set to " + childCount + " in Room " + finalRoomText);
				    } catch (Exception e) {
				        System.out.println("Failed to set Child count to " + childCount + " in Room " + finalRoomText + ": " + e.getMessage());
				    }

				    try {
				        // Use the xpath you provided exactly for the first child's age dropdown
				        WebElement childAgedropdown = driver.findElement(By.xpath("//select[@name='child0']"));
				        childAgedropdown.click();

				        // ageToSelect should be set dynamically somewhere, for example:
				        String ageToSelect = String.valueOf(childAge); // childAgeValue is an int variable defined elsewhere

				        // Select the age option dynamically, same xpath pattern
				        WebElement ageOption = driver.findElement(By.xpath("//option[text()='" + ageToSelect + "']"));
				        ageOption.click();

				        System.out.println("Selected child age: " + ageToSelect);
				    } catch (Exception e) {
				        System.out.println("Failed to set child age: " + e.getMessage());
				    }
				}

				
				// Click Done Button after final room
				if (i == listOfRooms.size() - 1) {
					try {
						WebElement doneButton = driver.findElement(By.xpath("//button[text()='Done']"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", doneButton);
						Thread.sleep(500);
						doneButton.click();
						System.out.println("Clicked 'Done' after completing Room " + finalRoomText);
					} catch (Exception e) {
						System.out.println("Failed to click 'Done' button: " + e.getMessage());
					}
				}
			}
		}
		
		//Method to Select Room and Adult Details
		public void fillRoomDetails(String roomCountStr, String adultCountStr, String childCountStr, String childAgesStr) {
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    int roomCount = Integer.parseInt(roomCountStr.trim());
		    String[] adultArray = adultCountStr.split(",");
		    String[] childArray = (childCountStr != null && !childCountStr.isEmpty()) ? childCountStr.split(",") : null;
		    String[] roomChildAges = (childAgesStr != null && !childAgesStr.isEmpty()) ? childAgesStr.split(";") : null;
		    // Open Room & Guest popup
		    WebElement roomGuestElement = wait.until(ExpectedConditions.elementToBeClickable(
		          By.xpath("//span[text()='Room & Guest']")));
		    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", roomGuestElement);
		    roomGuestElement.click();
		    int k = 1;
		    for (int i = 0; i < roomCount; i++) {
		       if (i > 0) {
		          WebElement addRoomButton = wait.until(ExpectedConditions.elementToBeClickable(
		                By.xpath("//button[contains(text(), 'Add Room')]")));
		          js.executeScript("arguments[0].scrollIntoView({block: 'center'});", addRoomButton);
		          addRoomButton.click();
		       }
		       int adults = Integer.parseInt(adultArray[i].trim());
		       // Select adults
		       List<WebElement> adultOptions = driver.findElements(By.xpath(
		             "//span[text()='Room " + k + ":']/parent::div//span[text()='Adults(12y+)']/parent::div//li"));
		       for (WebElement option : adultOptions) {
		          if (Integer.parseInt(option.getText().trim()) == adults) {
		             js.executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
		             option.click();
		             break;
		          }
		       }
		       int children = 0;
		       String[] currentRoomChildAges = null;
		       if (childArray != null && childArray.length > i) {
		          children = Integer.parseInt(childArray[i].trim());
		          // Select children count
		          List<WebElement> childOptions = driver.findElements(By.xpath(
		                "//span[text()='Room " + k + ":']/parent::div//span[text()='Child']/parent::div//li"));
		          for (WebElement option : childOptions) {
		             if (Integer.parseInt(option.getText().trim()) == children) {
		                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
		                option.click();
		                break;
		             }
		          }
		          if (roomChildAges != null && roomChildAges.length > i) {
		             currentRoomChildAges = roomChildAges[i].split(",");
		          }
		          // Wait for dropdowns to render
		          if (children > 0 && currentRoomChildAges != null) {
		             try {
		                Thread.sleep(500);
		             } catch (InterruptedException e) {
		                e.printStackTrace();
		             }
		             List<WebElement> childAgeDropdowns = driver.findElements(By.xpath(
		                   "//span[text()='Room " + k + ":']/parent::div//select[starts-with(@name, 'child')]"));
		             for (int j = 0; j < children && j < childAgeDropdowns.size(); j++) {
		                String age = currentRoomChildAges.length > j ? currentRoomChildAges[j].trim() : "5"; // fallback
		                WebElement dropdown = childAgeDropdowns.get(j);
		                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", dropdown);
		                Select select = new Select(dropdown);
		                select.selectByVisibleText(age);
		             }
		          }
		       }
		       k++;
		    }
		    // Click Done
		    WebElement doneButton = wait.until(ExpectedConditions.elementToBeClickable(
		          By.xpath("//button[contains(text(), 'Done')]")));
		    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", doneButton);
		    doneButton.click();
		}
		 


		//method to click on search hotels
		public void clickSearchHotelsAndWaitForResults(Log Log) {
		    try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(45));

		        // Click on 'Search Hotels' button
		        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//button[text()='Search Hotels']")));
		        searchButton.click();
		        Log.ReportEvent("INFO", "Clicked on 'Search Hotels' button.");

		        // Wait for the 'hotels found' count text to be visible
		        WebElement countElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
		            By.xpath("//*[contains(@class,'hotels-found-text')]")));

		        String countText = countElement.getText().trim();
		        Log.ReportEvent("PASS", "Total Hotels Found: " + countText);

		    } catch (TimeoutException te) {
		        Log.ReportEvent("FAIL", "Timeout while waiting for hotels result to load.");
		    } catch (Exception e) {
		        Log.ReportEvent("FAIL", "Exception occurred while searching hotels: " + e.getMessage());
		    }
		}

		
		//Method to get the date from result page 
		public String getCheckInDatefromResultPage() {
		    // XPath to locate the input element by label
		    WebElement checkInInput = driver.findElement(By.xpath("//label[text()='Check in']/following-sibling::div//input[@value]"));

		    String checkInDate = checkInInput.getAttribute("value");

		    // Return it for future use
		    return checkInDate;
		}

		//Method to get the return date from result page 
				public String getCheckOutDatefromResultPage() {
				    // XPath to locate the input element by label
				    WebElement checkOutInput = driver.findElement(By.xpath("//label[text()='Check out']/following-sibling::div//input[@value]"));

				    String checkOutDate = checkOutInput.getAttribute("value");

				    // Return it for future use
				    return checkOutDate;
				}

				//Method to get the room and guests text  from result page 
				public String getRoomAndGuestsTextfromResultPage() {
				    // XPath to locate the input element by label
				    WebElement checkOutInput = driver.findElement(By.xpath("//span[text()='Room and Guest']/following-sibling::span"));

				    String checkOutDate = checkOutInput.getText();

				    // Return it for future use
				    return checkOutDate;
				}

	//Method to get the count of hotels text 
		public void getHotelsCount(Log Log) {
			
			WebElement count = driver.findElement(By.xpath("//*[contains(@class,'hotels-found-text')]"));
			String countText = count.getText();
 	        Log.ReportEvent("PASS", "Total Hotels Found: " + countText);
		}

		//Method to click and search on hotel names
	
		public String clickAndSearchHotelName(Log Log, ScreenShots ScreenShots) {
		    try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

		        // Click on the dropdown to open the list
		        WebElement dropdown = driver.findElement(By.xpath("//label[text()='Hotel Name']/following-sibling::div"));
		        dropdown.click();

		        // Wait for the listbox to appear
		        By listLocator = By.xpath("//*[contains(@id,'react-select-10-listbox')]");
		        WebElement entireList = wait.until(ExpectedConditions.visibilityOfElementLocated(listLocator));

		        Thread.sleep(3000);

		        List<WebElement> allOptions = entireList.findElements(
		            By.xpath("//*[contains(@class,'hotel-name-filter__option')]")
		        );

		        if (allOptions.isEmpty()) {
		            Log.ReportEvent("FAIL", "No options found in 'Hotel Name' dropdown");
		            ScreenShots.takeScreenShot1();
		            return null;
		        }

		        Random random = new Random();
		        int randomIndex = random.nextInt(allOptions.size());

		        WebElement optionToSelect = allOptions.get(randomIndex);

		        wait.until(ExpectedConditions.elementToBeClickable(optionToSelect));
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", optionToSelect);

		        String selectedText = optionToSelect.getText();

		        Actions actions = new Actions(driver);
		        Thread.sleep(2000);
		        actions.moveToElement(optionToSelect).click().perform();

		        Log.ReportEvent("PASS", "Successfully selected hotel From the Dropdown: " + selectedText);
		        return selectedText;

		    } catch (Exception e) {
		        Log.ReportEvent("FAIL", "Error while selecting hotel from dropdown: " + e.getMessage());
		        ScreenShots.takeScreenShot1();
		        return null;
		    }
		}


		
		//Method to click and search on hotel names
		
		public String clickAndSearchHotelLocationName(Log Log, ScreenShots ScreenShots) {
		    try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		        // Click to open the dropdown
		        WebElement dropdown = driver.findElement(By.xpath("//label[text()='Location Name']/following-sibling::div"));
		        dropdown.click();

		        // Wait for the dropdown list to appear
		        WebElement entireList = wait.until(ExpectedConditions.visibilityOfElementLocated(
		            By.xpath("//*[contains(@class,'hotel-location-filter__menu-list')]")
		        ));

		        List<WebElement> allOptions = entireList.findElements(
		            By.xpath("//*[contains(@class,'hotel-location-filter__option')]")
		        );

		        if (allOptions.isEmpty()) {
		            Log.ReportEvent("FAIL", "No options found in 'Hotel Location' dropdown");
		            ScreenShots.takeScreenShot1();
		            return null;
		        }

		        // Pick a random option
		        Random random = new Random();
		        int randomIndex = random.nextInt(allOptions.size());
		        WebElement randomOption = allOptions.get(randomIndex);

		        wait.until(ExpectedConditions.elementToBeClickable(randomOption));
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", randomOption);

		      
		        String selectedText = randomOption.getText();

		        Actions actions = new Actions(driver);
		        Thread.sleep(1000);
		        actions.moveToElement(randomOption).click().perform();

		        Log.ReportEvent("PASS", "Successfully selected hotel Location Name: " + selectedText);
		        Thread.sleep(2000);
		        return selectedText;

		    } catch (Exception e) {
		        Log.ReportEvent("FAIL", "Error while selecting hotel location from dropdown: " + e.getMessage());
		        ScreenShots.takeScreenShot1();
		        return null;
		    }
		}

		//Method to click on star rating
		public String clickStarRating(String userInput,Log Log) {
		    List<WebElement> elements = driver.findElements(By.xpath("//*[@class='star-chip']//span[contains(@class,'fs-14')]"));

		    for (WebElement elem : elements) {
		        String text = elem.getText().trim();

		        if (text.equals(userInput) || text.contains(userInput)) {
		            elem.click();
		            System.out.println("Clicked on star Rating with text: " + text);
			        Log.ReportEvent("INFO", "Clicked on star Rating with text: "+text);
		            return text;  
		        }
		    }

		    System.out.println("No star Rating element found matching: " + userInput);
		    return null;  
		}

//Method to click on facilities
		public String clickFacilities(Log Log, ScreenShots ScreenShots) {
		    try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		        JavascriptExecutor js = (JavascriptExecutor) driver;

		        // Wait and find all facility labels directly
		        List<WebElement> facilityLabels = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
		            By.xpath("//*[contains(@class,'hotel-filters_facility-label')]")
		        ));

		        if (facilityLabels.isEmpty()) {
		            Log.ReportEvent("FAIL", "No facilities found.");
		            ScreenShots.takeScreenShot1();
		            return null;
		        }

		        // Pick a random facility
		        Random random = new Random();
		        int randomIndex = random.nextInt(facilityLabels.size());
		        WebElement randomFacility = facilityLabels.get(randomIndex);

		        wait.until(ExpectedConditions.elementToBeClickable(randomFacility));
		        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", randomFacility);

		        String facilityText = randomFacility.getText().trim();

		        // Try JavaScript click first (more reliable)
		        js.executeScript("arguments[0].click();", randomFacility);

		        Log.ReportEvent("PASS", "Successfully clicked facility: " + facilityText);
		        Thread.sleep(2000); 
		        return facilityText;

		    } catch (Exception e) {
		        Log.ReportEvent("FAIL", "Error while selecting facility: " + e.getMessage());
		        ScreenShots.takeScreenShot1();
		        return null;
		    }
		}


		//Method to get all the hotels text from result screen
		
		public List<String> getHotelsNameTextFromresultScreen(Log Log, ScreenShots ScreenShots) {
		    try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		        // Wait until the first hotel card name is visible
		        wait.until(ExpectedConditions.visibilityOfElementLocated(
		            By.xpath("//*[@class='hotel-card__body__header_hotel-name']")
		        ));

		        // Get all hotel name elements
		        List<WebElement> hotelElements = driver.findElements(
		            By.xpath("//*[@class='hotel-card__body__header_hotel-name']")
		        );

		        List<String> hotelNames = new ArrayList<>();

		        for (WebElement hotel : hotelElements) {
		            hotelNames.add(hotel.getText().trim());
		        }

		        if (hotelNames.isEmpty()) {
		            Log.ReportEvent("FAIL", "No hotels found on result screen.");
		            ScreenShots.takeScreenShot1();
		            return Collections.emptyList();
		        }

		        Log.ReportEvent("PASS", "Total hotels found: " + hotelNames.size());

		        // Print hotel names
		        for (String name : hotelNames) {
		            System.out.println("Hotel: " + name);
		        }

		        return hotelNames;

		    } catch (Exception e) {
		        Log.ReportEvent("FAIL", "Error while getting hotel names: " + e.getMessage());
		        ScreenShots.takeScreenShot1();
		        return Collections.emptyList();
		    }
		}

		
		//Method to get the location text from hotels 
		public List<String> getHotelsLocationTextFromResultpage(Log Log, ScreenShots ScreenShots) {
		    try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		        // Wait for hotel location elements to appear
		        wait.until(ExpectedConditions.visibilityOfElementLocated(
		            By.xpath("//span[@class='hotel-card__body__header_hotel-location']")
		        ));

		        // Find all location elements
		        List<WebElement> locationElements = driver.findElements(
		            By.xpath("//span[@class='hotel-card__body__header_hotel-location']")
		        );

		        List<String> locationNames = new ArrayList<>();
		        for (WebElement location : locationElements) {
		            locationNames.add(location.getText().trim());
		        }

		        if (locationNames.isEmpty()) {
		            Log.ReportEvent("FAIL", "No hotel locations found on the result page.");
		            ScreenShots.takeScreenShot1();
		            return Collections.emptyList();
		        }

		        Log.ReportEvent("PASS", "Total hotel locations found: " + locationNames.size());

		        // Print each location (optional)
		        for (String loc : locationNames) {
		            System.out.println("Hotel Location: " + loc);
		        }

		        return locationNames;

		    } catch (Exception e) {
		        Log.ReportEvent("FAIL", "Error while fetching hotel locations: " + e.getMessage());
		        ScreenShots.takeScreenShot1();
		        return Collections.emptyList();
		    }
		}
		
		//Method to get the amenities from result page
	
		public String getFacilitiesTitleTextFromResultpage(Log Log) {
		    try {
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		        WebElement facilitiesDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
		            By.xpath("//div[contains(@class, 'd-flex justify-content-start flex-column') and @title]")));
		        

		        // Get the title attribute value
		        String facilitiesTitle = facilitiesDiv.getAttribute("title").trim();

		        
		        System.out.println("Facilities Title Text: " + facilitiesTitle);
		        Log.ReportEvent("INFO", "Facilities Title Text: "+facilitiesTitle);


		        return facilitiesTitle;

		    } catch (Exception e) {
		        System.out.println("Error retrieving facilities title text: " + e.getMessage());
		        return "";
		    }
		}
		
		
		public void validateFacilitiesTitleWithClickedFacility(Log Log, ScreenShots ScreenShots) {
		    String facilitiesTitleText = getFacilitiesTitleTextFromResultpage(Log);
		    String clickedFacility = clickFacilities(Log, ScreenShots);

		    boolean isPass = true;

		    if (clickedFacility == null || clickedFacility.isEmpty()) {
		        Log.ReportEvent("FAIL", "No facility was clicked or clicked facility text is null/empty.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else if (facilitiesTitleText == null || facilitiesTitleText.isEmpty()) {
		        Log.ReportEvent("FAIL", "Facilities title text from result page is null or empty.");
		        ScreenShots.takeScreenShot1();
		        isPass = false;
		    } else {
		        // Case-insensitive contains check
		        if (facilitiesTitleText.toLowerCase().contains(clickedFacility.toLowerCase())) {
		            Log.ReportEvent("PASS", "Clicked facility '" + clickedFacility + "' is present in result title text.");
		        } else {
		            Log.ReportEvent("FAIL", "Clicked facility '" + clickedFacility + "' NOT found in result title text.");
		            ScreenShots.takeScreenShot1();
		            isPass = false;
		        }
		    }

		    if (isPass) {
		        Log.ReportEvent("PASS", "Facilities validation passed successfully.");
		    } else {
		        Log.ReportEvent("FAIL", "Facilities validation failed. Please check screenshots and logs.");
		    }
		}

		//Method to click on select room button on result page
		public void clickOnSelectRoomButtonOnResultpage() {
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    JavascriptExecutor js = (JavascriptExecutor) driver;

		    try {
		        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Select Room']")));

		        // Scroll into view
		        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", button);

		        // Try normal click
		        button.click();

		    } catch (Exception e) {
		        System.out.println("Normal click failed: " + e.getMessage());

		        // Try JS click as fallback
		        try {
		            WebElement button = driver.findElement(By.xpath("//button[text()='Select Room']"));
		            js.executeScript("arguments[0].click();", button);
		        } catch (Exception ex) {
		            System.out.println("JS click also failed: " + ex.getMessage());
		            throw ex; // Rethrow or handle error accordingly
		        }
		    }
		}

		
		// Method to get hotel name
		public String[] getHotelNameFromCardFromResultsPage() {
		    String name = driver.findElement(By.xpath(".//*[@class='hotel-card__body__header_hotel-name']")).getText();
		    return new String[]{name};
		}

		// Method to get hotel location
		public String[] getHotelLocationFromCardFromResultsPage() {
		    String location = driver.findElement(By.xpath(".//span[@class='hotel-card__body__header_hotel-location']")).getText();
		    return new String[]{location};
		}

		// Method to get hotel amount
		public String[] getHotelAmountFromCardFromResultsPage() {
		    String amount = driver.findElement(By.xpath(".//span[contains(@class,'hotel-results-watermarked')]")).getText();
		    return new String[]{amount};
		}

		// Method to get hotel facilities
		public String[] getHotelFacilitiesFromCardFromResultsPage() {
		    String facilities = driver.findElement(By.xpath(".//div[contains(@class, 'd-flex justify-content-start flex-column') and @title]")).getText();
		    return new String[]{facilities};
		}

	
		
		
		//Method to get the check in date text from result page 
		public String[] getCheckInDateTextFromResultPage() {
		    WebElement dateInput = driver.findElement(By.xpath("(//div[@class='react-datepicker__input-container'])[1]//input"));
		    		    String fullDate = dateInput.getAttribute("value");
		    		    String[] parts = fullDate.split(" "); // parts[0] = "19", parts[1] = "Aug", parts[2] = "2025"
		    
		    // Return the parts (day, month, year)
		    return parts;
		}
		
		//Method to get the check out date text from result page 
				public String[] getCheckOutDateTextFromResultPage() {
				    WebElement dateInput = driver.findElement(By.xpath("(//div[@class='react-datepicker__input-container'])[2]//input"));
				    		    String fullDate = dateInput.getAttribute("value");
				    		    String[] parts = fullDate.split(" "); // parts[0] = "19", parts[1] = "Aug", parts[2] = "2025"
				    
				    // Return the parts (day, month, year)
				    return parts;
				}
				
				//Method to get the room and guests text from result page 
				public String[] getRoomAndGuestTextFromResultPage() {
				    WebElement element = driver.findElement(By.xpath("//span[text()='Room and Guest']/following-sibling::span"));
				    String fullText = element.getText();  // e.g. "1 Room, 2 Guests"
				    
				    // Split by comma first: ["1 Room", " 2 Guests"]
				    String[] parts = fullText.split(",");
				    
				    // Extract number of rooms (trim to remove spaces)
				    String roomsPart = parts[0].trim();    // "1 Room"
				    // Extract just the number from roomsPart (everything before space)
				    String rooms = roomsPart.split(" ")[0];  // "1"
				    
				    // Extract number of guests
				    String guestsPart = parts[1].trim();   // "2 Guests"
				    String guests = guestsPart.split(" ")[0]; // "2"
				    
				    // Return array with rooms and guests numbers as strings
				    return new String[] { rooms, guests };
				}

				public void switchToNewTab() {
				    try {
				        String originalWindow = driver.getWindowHandle();
				        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

				        // Wait for second tab to open
				        wait.until(driver -> driver.getWindowHandles().size() > 1);

				        // Switch to the new tab
				        for (String windowHandle : driver.getWindowHandles()) {
				            if (!windowHandle.equals(originalWindow)) {
				                driver.switchTo().window(windowHandle);
				                System.out.println(" Switched to new tab: " + driver.getTitle());
				                break;
				            }
				        }

				    } catch (Exception e) {
				        System.out.println(" Failed to switch to new tab: " + e.getMessage());
				        e.printStackTrace();
				    }
				}

				
				
							
				

				//Method to validate hotel text on result page 
				public void validateHotelNameSelectionOnResultpage(String selectedHotel, List<String> hotelsList, Log log, ScreenShots screenshots) {
				    if (selectedHotel == null || selectedHotel.isEmpty()) {
				        log.ReportEvent("FAIL", "Selected hotel name is null or empty.");
				        screenshots.takeScreenShot1();
				        return;
				    }

				    if (hotelsList == null || hotelsList.isEmpty()) {
				        log.ReportEvent("FAIL", "Hotel list from result screen is null or empty.");
				        screenshots.takeScreenShot1();
				        return;
				    }

				    // remove extras  like  (4 star)")
				    String cleanedSelectedHotel = selectedHotel.replaceAll("\\s*\\(.*?\\)\\s*", "").trim().toLowerCase();

				    boolean found = false;
				    for (String hotel : hotelsList) {
				        String cleanedHotel = hotel.trim().toLowerCase();

				        // Check if cleaned hotel names match (case-insensitive, bracket info removed from selectedHotel)
				        if (cleanedHotel.equalsIgnoreCase(cleanedSelectedHotel)) {
				            found = true;
				            break;
				        }
				    }

				    if (found) {
				        log.ReportEvent("PASS", "Match Found -- Selected hotel '" + selectedHotel + "' found same in selected dropdown and results list.");
				    } else {
				        log.ReportEvent("FAIL", "Selected hotel '" + selectedHotel + "' NOT found in selected dropdown and results list.");
				        screenshots.takeScreenShot1();
				    }
				}

	

	//validate facilities from clicked check box to result page title facility
				
				public void validateFacilitiesTitleWithClickedFacility(String clickedFacility, String facilitiesTitleText, Log Log, ScreenShots ScreenShots) {
				    boolean isPass = true;

				    if (clickedFacility == null || clickedFacility.isEmpty()) {
				        Log.ReportEvent("FAIL", "No facility was clicked or clicked facility text is null/empty.");
				        ScreenShots.takeScreenShot1();
				        isPass = false;
				    } 
				    else if (facilitiesTitleText == null || facilitiesTitleText.isEmpty()) {
				        Log.ReportEvent("FAIL", "Facilities title text from result page is null or empty.");
				        ScreenShots.takeScreenShot1();
				        isPass = false;
				    } 
				    else {
				        if (facilitiesTitleText.toLowerCase().contains(clickedFacility.toLowerCase())) {
				            Log.ReportEvent("PASS", "Clicked facility '" + clickedFacility + "' is present in result title text.");
				        } else {
				            Log.ReportEvent("FAIL", "Clicked facility '" + clickedFacility + "' NOT found in result title text.");
				            ScreenShots.takeScreenShot1();
				            isPass = false;
				        }
				    }

				   if (isPass) {
				        Log.ReportEvent("PASS", "Facilities validation passed successfully from clicked facility checkbox to hotel facilities.");
				    } else {
				        Log.ReportEvent("FAIL", "Facilities validation failed from clicked facility checkbox to hotel facilities.");
				    }
				}

				//Method to validate location from selected and in hotel card 
				public void validateHotelLocationFromSelectedToHotelCard(String selectedLocation, String[] locationFromResultsPage, Log Log, ScreenShots ScreenShots) throws InterruptedException {
				    boolean isPass = true;
				    Thread.sleep(1000);

				    if (selectedLocation == null || selectedLocation.isEmpty()) {
				        Log.ReportEvent("FAIL", "Selected hotel location is null or empty.");
				        ScreenShots.takeScreenShot1();
				        isPass = false;
				    } else if (locationFromResultsPage == null || locationFromResultsPage.length == 0 || locationFromResultsPage[0].isEmpty()) {
				        Log.ReportEvent("FAIL", "Hotel location from results page is null, empty or missing.");
				        ScreenShots.takeScreenShot1();
				        isPass = false;
				    } else {
				        String locationFromCard = locationFromResultsPage[0].trim();
				        if (selectedLocation.equalsIgnoreCase(locationFromCard)) {
				            Log.ReportEvent("PASS", "Hotel location matches between dropdown and results page: '" + selectedLocation + "'");
				        } else {
				            Log.ReportEvent("FAIL", "Hotel location does NOT match. Dropdown: '" + selectedLocation + "' | Results page: '" + locationFromCard + "'");
				            ScreenShots.takeScreenShot1();
				            isPass = false;
				        }
				    }

				    if (isPass) {
				        Log.ReportEvent("PASS", "Hotel location validation passed from selected location dropdown to hotel card .");
				    } else {
				        Log.ReportEvent("FAIL", "Hotel location validation failed from selected location dropdown to hotel card.");
				    }
				}
				
				//Method to click on hotel price low to high
				public void clickPriceLowToHigh() {
					driver.findElement(By.xpath("//div[text()='Price low to high']")).click();
				}
				
				public void clickPriceHighToLow() {
					driver.findElement(By.xpath("//div[text()='Price high to low']")).click();
				}
				
				public void clickStarsLowToHigh() {
					driver.findElement(By.xpath("//div[text()='Stars low to high']")).click();
				}
				
				public void clickStarsHighToLow() {
					driver.findElement(By.xpath("//div[text()='Stars high to low']")).click();
				}

	//Method to validate price are from low to high 
				public boolean verifyPricesLowToHigh(Log Log,ScreenShots ScreenShots) {
				    try {
				        // Find all price elements
				        List<WebElement> priceElements = driver.findElements(By.xpath("//*[contains(@class,'hotel-card__body__header_hotel-price')]"));

				        if (priceElements.isEmpty()) {
				            Log.ReportEvent("FAIL", "No price elements found.");
				            return false;
				        }

				        // Extract prices as numbers 
				        List<Double> prices = new ArrayList<>();
				        for (WebElement priceElem : priceElements) {
				            String priceText = priceElem.getText().replaceAll("[^\\d.]", ""); // Remove unwanted things 
				            if (!priceText.isEmpty()) {
				                prices.add(Double.parseDouble(priceText));
				            } else {
				                Log.ReportEvent("FAIL", "Price text empty or invalid for element: " + priceElem.getText());
				                return false;
				            }
				        }

				        // Check if the prices list is sorted low to high
				        for (int i = 0; i < prices.size() - 1; i++) {
				            if (prices.get(i) > prices.get(i + 1)) {
				                Log.ReportEvent("FAIL", "Prices are NOT sorted low to high: " + prices);
						        ScreenShots.takeScreenShot1();

				                return false;
				            }
				        }

				        Log.ReportEvent("PASS", "Prices are sorted low to high: " );
				        return true;

				    } catch (Exception e) {
				        Log.ReportEvent("FAIL", "Exception in verifying prices: " + e.getMessage());
				        ScreenShots.takeScreenShot1();
				        return false;
				    }
				}
			
				public boolean verifyPricesHighToLow(Log Log) {
				    try {
				        // Find all price elements
				        List<WebElement> priceElements = driver.findElements(By.xpath("//*[contains(@class,'hotel-card__body__header_hotel-price')]"));

				        if (priceElements.isEmpty()) {
				            Log.ReportEvent("FAIL", "No price elements found.");
				            return false;
				        }

				        // Extract prices as numbers (assuming price text like "$1,200" or "₹1,200")
				        List<Double> prices = new ArrayList<>();
				        for (WebElement priceElem : priceElements) {
				            String priceText = priceElem.getText().replaceAll("[^\\d.]", ""); // Remove everything except digits and dot
				            if (!priceText.isEmpty()) {
				                prices.add(Double.parseDouble(priceText));
				            } else {
				                Log.ReportEvent("FAIL", "Price text empty or invalid for element: " + priceElem.getText());
				                return false;
				            }
				        }

				        // Check if the prices list is sorted high to low
				        for (int i = 0; i < prices.size() - 1; i++) {
				            if (prices.get(i) < prices.get(i + 1)) {   //this is logic
				                Log.ReportEvent("FAIL", "Prices are NOT sorted high to low: " + prices);
				                return false;
				            }
				        }

				        Log.ReportEvent("PASS", "Prices are sorted high to low: " );
				        return true;

				    } catch (Exception e) {
				        Log.ReportEvent("FAIL", "Exception in verifying prices: " + e.getMessage());
				        return false;
				    }
				}

				public boolean validateStarRatingsLowToHigh(Log Log) {
				    try {
				        //  Find all star-rating containers
				        List<WebElement> ratingBoxes = driver.findElements(By.xpath("//*[contains(@class,'hotel-star-rating')]"));

				        if (ratingBoxes.isEmpty()) {
				            Log.ReportEvent("FAIL", "No hotel star-rating containers found.");
				            return false;
				        }

				        List<Integer> starCounts = new ArrayList<>();

				        //  For each box, count the filled stars
				        for (WebElement box : ratingBoxes) {
				            List<WebElement> filledStars = box.findElements(By.xpath(".//*[contains(@class,'star-fil')]")); 
				            starCounts.add(filledStars.size());
				        }

				        // Create a sorted copy in ascending order
				        List<Integer> sorted = new ArrayList<>(starCounts);
				        Collections.sort(sorted); // ascending

				        if (starCounts.equals(sorted)) {
				            Log.ReportEvent("PASS", "Star ratings are sorted from low to high: ");
				            return true;
				        } else {
				            Log.ReportEvent("FAIL", "Star ratings are NOT sorted from low to high: " + starCounts);
				            return false;
				        }

				    } catch (Exception e) {
				        Log.ReportEvent("FAIL", "Exception while validating star ratings: " + e.getMessage());
				        return false;
				    }
				}

	
				public boolean validateStarRatingsHighToLow(Log Log) {
				    try {
				        // Find all star-rating containers
				        List<WebElement> ratingBoxes = driver.findElements(By.xpath("//*[contains(@class,'hotel-star-rating')]"));

				        if (ratingBoxes.isEmpty()) {
				            Log.ReportEvent("FAIL", "No hotel star-rating containers found.");
				            return false;
				        }

				        List<Integer> starCounts = new ArrayList<>();

				        for (WebElement box : ratingBoxes) {
				            // Count filled stars within this box
				            List<WebElement> filledStars = box.findElements(By.xpath(".//*[contains(@class,'star-fil')]")); 
				            starCounts.add(filledStars.size());
				        }

				        // Create a copy of list and sort it in descending order
				        List<Integer> sorted = new ArrayList<>(starCounts);
				        sorted.sort(Collections.reverseOrder());

				        if (starCounts.equals(sorted)) {
				            Log.ReportEvent("PASS", "Star ratings are sorted from high to low: " );
				            return true;
				        } else {
				            Log.ReportEvent("FAIL", "Star ratings are NOT sorted from high to low: " + starCounts);
				            return false;
				        }

				    } catch (Exception e) {
				        Log.ReportEvent("FAIL", "Exception while validating star ratings: " + e.getMessage());
				        return false;
				    }
				}

//				//Method to Scroll the Slider from min
//				public double[] adjustMinimumSliderValueByPercentage(double percentageValue) throws InterruptedException {
//
//				    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//				    // Wait for the min thumb to be visible
//				    WebElement minThumb = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".thumb.thumb-0")));
//
//				    Thread.sleep(2000); // Optional: for page stability
//
//				    // Get the min and max values from aria attributes
//				    double minValue = Double.parseDouble(minThumb.getAttribute("aria-valuemin"));
//				    double maxValue = Double.parseDouble(minThumb.getAttribute("aria-valuemax"));
//
//				    // Calculate the target value based on percentage
//				    double targetValue = minValue + ((maxValue - minValue) * percentageValue / 100.0);
//				    targetValue = Math.max(minValue, Math.min(maxValue, targetValue));
//
//				    System.out.println("Min Value: " + minValue);
//				    System.out.println("Max Value: " + maxValue);
//				    System.out.println("Percentage Passed: " + percentageValue);
//				    System.out.println("Calculated Target Value: " + targetValue);
//
//				    // Find the entire slider container
//				    WebElement slider = driver.findElement(By.cssSelector("div.slider"));
//				    int sliderWidth = slider.getSize().getWidth();
//				    System.out.println("Slider Width: " + sliderWidth);
//
//				    // Calculate target offset in pixels
//				    double percentageOffset = (targetValue - minValue) / (maxValue - minValue);
//				    int targetPixelOffset = (int) (sliderWidth * percentageOffset);
//				    System.out.println("Target Pixel Offset: " + targetPixelOffset);
//
//				    // Get current position of thumb
//				    int currentThumbLeft = minThumb.getLocation().getX();
//				    int sliderLeft = slider.getLocation().getX();
//
//				    int currentOffset = currentThumbLeft - sliderLeft;
//				    System.out.println("Current Offset: " + currentOffset);
//
//				    int moveBy = targetPixelOffset - currentOffset;
//				    System.out.println("Calculated Move By (pixels): " + moveBy);
//
//				    // Perform the drag
//				    new Actions(driver)
//				          .clickAndHold(minThumb)
//				          .moveByOffset(moveBy, 0)
//				          .release()
//				          .perform();
//
//				    System.out.println("Slider moved to value corresponding to " + percentageValue + "%");
//				    System.out.println(minValue);
//				    System.out.println(maxValue);
//				    return new double[]{minValue, maxValue};
//				}
				
				// Method to Scroll the Slider from min and return the text after slider movement
				public String adjustMinimumSliderValueByPercentage(double percentageValue) throws InterruptedException {

				    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

				    // Wait for the min thumb to be visible
				    WebElement minThumb = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".thumb.thumb-0")));

				    Thread.sleep(2000); // Optional: for page stability

				    // Get the min and max values from aria attributes
				    double minValue = Double.parseDouble(minThumb.getAttribute("aria-valuemin"));
				    double maxValue = Double.parseDouble(minThumb.getAttribute("aria-valuemax"));

				    // Calculate the target value based on percentage
				    double targetValue = minValue + ((maxValue - minValue) * percentageValue / 100.0);
				    targetValue = Math.max(minValue, Math.min(maxValue, targetValue));

				    System.out.println("Min Value: " + minValue);
				    System.out.println("Max Value: " + maxValue);
				    System.out.println("Percentage Passed: " + percentageValue);
				    System.out.println("Calculated Target Value: " + targetValue);

				    // Find the entire slider container
				    WebElement slider = driver.findElement(By.cssSelector("div.slider"));
				    int sliderWidth = slider.getSize().getWidth();
				    System.out.println("Slider Width: " + sliderWidth);

				    // Calculate target offset in pixels
				    double percentageOffset = (targetValue - minValue) / (maxValue - minValue);
				    int targetPixelOffset = (int) (sliderWidth * percentageOffset);
				    System.out.println("Target Pixel Offset: " + targetPixelOffset);

				    // Get current position of thumb
				    int currentThumbLeft = minThumb.getLocation().getX();
				    int sliderLeft = slider.getLocation().getX();

				    int currentOffset = currentThumbLeft - sliderLeft;
				    System.out.println("Current Offset: " + currentOffset);

				    int moveBy = targetPixelOffset - currentOffset;
				    System.out.println("Calculated Move By (pixels): " + moveBy);

				    // Perform the drag
				    new Actions(driver)
				        .clickAndHold(minThumb)
				        .moveByOffset(moveBy, 0)
				        .release()
				        .perform();

				    System.out.println("Slider moved to value corresponding to " + percentageValue + "%");

				    // Small wait for UI to reflect changes
				    Thread.sleep(2000);

				    // Get the updated text after slider move
				    List<WebElement> spanElements = driver.findElements(By.xpath("//*[@class='one-way-flight-filters_section_content']//span"));
				    
				    String finalText = "";

				    // Combine visible texts if needed (or get first non-empty one)
				    for (WebElement el : spanElements) {
				        String text = el.getText().trim();
				        if (!text.isEmpty()) {
				            finalText = text;
				            break; // Assuming you only need the first relevant text
				        }
				    }

				    System.out.println("Final Text after Slider Move: " + finalText);
				    return finalText;
				}

				
				//Method to Scroll the Slider from max
				public String adjustMaximumSliderValueByPercentage(double percentageValue) throws InterruptedException {
				    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

				    // Wait for both thumbs to be visible
				    WebElement minThumb = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".thumb.thumb-0")));
				    WebElement maxThumb = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".thumb.thumb-1")));

				    // Wait for the slider container
				    WebElement slider = driver.findElement(By.cssSelector("div.slider"));

				    Thread.sleep(1000);

				    // Get min and max range values from thumbs
				    double minValue = Double.parseDouble(minThumb.getAttribute("aria-valuemin").trim());
				    double maxValue = Double.parseDouble(maxThumb.getAttribute("aria-valuemax").trim());

				    // Calculate the new max value based on the percentage (reduce from max)
				    double targetValue = maxValue - ((maxValue - minValue) * (percentageValue / 100.0));
				    targetValue = Math.max(minValue, Math.min(maxValue, targetValue));

				    System.out.println("Min: " + minValue + ", Max: " + maxValue);
				    System.out.println("Target Max Value After Reducing " + percentageValue + "%: " + targetValue);

				    // Calculate slider pixel position
				    int sliderLeft = slider.getLocation().getX();
				    int sliderWidth = slider.getSize().getWidth();
				    int thumbWidth = maxThumb.getSize().getWidth();
				    int effectiveRange = sliderWidth - thumbWidth;

				    double targetPercentage = (targetValue - minValue) / (maxValue - minValue);
				    int targetPixelOffset = (int) (targetPercentage * effectiveRange);

				    int currentThumbLeft = maxThumb.getLocation().getX();
				    int currentOffset = currentThumbLeft - sliderLeft;

				    int moveBy = targetPixelOffset - currentOffset;

				    System.out.println("Slider Left X: " + sliderLeft);
				    System.out.println("Slider Width: " + sliderWidth);
				    System.out.println("Thumb Width: " + thumbWidth);
				    System.out.println("Effective Range (pixels): " + effectiveRange);
				    System.out.println("Current Thumb Offset: " + currentOffset);
				    System.out.println("Target Pixel Offset: " + targetPixelOffset);
				    System.out.println("Move By (pixels): " + moveBy);

				    // Perform drag on max thumb
				    new Actions(driver)
				        .clickAndHold(maxThumb)
				        .moveByOffset(moveBy, 0)
				        .release()
				        .perform();

				    System.out.println("Slider max thumb moved to value corresponding to " + percentageValue + "%");

				    Thread.sleep(2000); // Wait for UI update

				    // Get the updated range text (similar to min method)
				    List<WebElement> spanElements = driver.findElements(By.xpath("//*[@class='one-way-flight-filters_section_content']//span"));

				    String finalText = "";

				    for (WebElement el : spanElements) {
				        String text = el.getText().trim();
				        if (!text.isEmpty()) {
				            finalText = text;
				            break; // Assuming only one relevant span is needed
				        }
				    }

				    System.out.println("Final Text after Max Slider Move: " + finalText);
				    return finalText;
				}

				//Method for validate minimum slider values with result page amount
				public void validateHotelPricesFromMinimumSlider(double percentageValue,Log Log) throws InterruptedException {
				    // Get the slider range text like "145.12-AED 1,282.12"
				    String sliderText = adjustMinimumSliderValueByPercentage(percentageValue);
				    System.out.println("Slider Text Returned: " + sliderText);

				    // Extract the min and max values
				    String cleanedText = sliderText.replaceAll("[^0-9.,\\-]", "").replace(",", "");
				    String[] parts = cleanedText.split("-");

				    if (parts.length != 2) {
				        throw new RuntimeException(" Failed to parse slider range: " + sliderText);
				    }

				    double minValue = Double.parseDouble(parts[0].trim());
				    double maxValue = Double.parseDouble(parts[1].trim());

				    System.out.println("Parsed Min Value: " + minValue);
				    System.out.println("Parsed Max Value: " + maxValue);

				    // Locate the hotel prices
				    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				    List<WebElement> priceElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
				        By.xpath("//*[contains(@class,'hotel-card__body__header_hotel-price')]")
				    ));

				    boolean allPricesValid = true;

				    for (WebElement priceElement : priceElements) {
				        String priceText = priceElement.getText().replaceAll("[^0-9.]", "");
				        
				        if (!priceText.isEmpty()) {
				            double price = Double.parseDouble(priceText);
				            System.out.println("Checking Hotel Price: " + price);

				            // Check if price is in range or matches min/max exactly
				            if (price < minValue || price > maxValue) {
				                System.out.println(" Price " + price + " is outside of allowed range: " + minValue + " - " + maxValue);
				                allPricesValid = false;
				            } else {
				                System.out.println(" Price " + price + " is valid.");
				            }
				        }
				    }

				    if (!allPricesValid) {
				        throw new AssertionError(" Validation failed: Some prices are not within or equal to the slider range.");
				    } else {
				        System.out.println(" All hotel prices are within or equal to the slider range.");
			            Log.ReportEvent("PASS", "All hotel prices are valid and within the adjusted min slider range.");

				    }
				}
				
				
				public void validateMaximumSliderValues(double percentageValue,Log Log) throws InterruptedException {
				    // Get the slider text after moving the max thumb
				    String sliderText = adjustMaximumSliderValueByPercentage(percentageValue);
				    System.out.println("Slider Text Returned: " + sliderText);

				    // Clean and extract numeric min and max values from the string
				    String cleanedText = sliderText.replaceAll("[^0-9.,\\-]", "").replace(",", "");
				    String[] parts = cleanedText.split("-");

				    if (parts.length != 2) {
				        throw new RuntimeException("Failed to parse slider range: " + sliderText);
				    }

				    double minValue = Double.parseDouble(parts[0].trim());
				    double maxValue = Double.parseDouble(parts[1].trim());

				    System.out.println("Parsed Min Value: " + minValue);
				    System.out.println("Parsed Max Value: " + maxValue);

				    // Wait for hotel prices to appear
				    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				    List<WebElement> priceElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
				        By.xpath("//*[contains(@class,'hotel-card__body__header_hotel-price')]")
				    ));

				    boolean allPricesValid = true;

				    for (WebElement priceElement : priceElements) {
				        String priceText = priceElement.getText().replaceAll("[^0-9.]", ""); // Strip currency and commas

				        if (!priceText.isEmpty()) {
				            double price = Double.parseDouble(priceText);
				            System.out.println("Hotel Price Found: " + price);

				            if (price >= minValue && price <= maxValue) {
				                System.out.println("Price " + price + " is within or equal to the range.");
				            } else {
				                System.out.println("Price " + price + " is outside the allowed range: " + minValue + " - " + maxValue);
				                allPricesValid = false;
				            }
				        }
				    }

				    if (!allPricesValid) {
				        throw new AssertionError("Validation failed: One or more prices are outside the slider range.");
				    } else {
				        System.out.println(" All hotel prices are valid and within the adjusted max slider range.");
			            Log.ReportEvent("PASS", "All hotel prices are valid and within the adjusted max slider range.");

				    }
				}


}