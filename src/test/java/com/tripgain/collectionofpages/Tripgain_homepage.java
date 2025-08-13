package com.tripgain.collectionofpages;
import org.openqa.selenium.JavascriptExecutor;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;
import com.tripgain.testscripts.BaseClass;

public class Tripgain_homepage {

	WebDriver driver;


	public Tripgain_homepage(WebDriver driver) {

		PageFactory.initElements(driver, this);
		this.driver=driver;
	}



	/*@FindBy(xpath = "(//input[@class='tg-select__input'])[1]")
	WebElement fromLocation;

	@FindBy(xpath = "//div[@class='airport-focused airport-option']")
	WebElement enterFromLocation;

	@FindBy(xpath = "(//input[@class='tg-select__input'])[2]")
	WebElement toLocation;

	@FindBy(xpath = "//div[@id='react-select-3-listbox']//div[@class='airport-focused airport-option']")
	WebElement enterToLocation;*/

	@FindBy(xpath = "//input[@id='TGFl-origin']")
	WebElement fromLocation;

	@FindBy(xpath = "//div[@class='airport-focused airport-option']")
	WebElement enterFromLocation;

	@FindBy(xpath = "//input[@id='TGFl-destination']")
	WebElement toLocation;

	@FindBy(xpath = "//div[@class='airport-focused airport-option']")
	WebElement enterToLocation;

	@FindBy(xpath = "//div[@class='MuiGrid2-root MuiGrid2-direction-xs-row css-uzfmmu']")
	WebElement searchButton;

	@FindBy(xpath = "//*[contains(normalize-space(@class), 'tg-fsonwarddate')]")
	WebElement datePickerInput;

	@FindBy(xpath = "//div[@class='react-datepicker-wrapper']//input[contains(@class ,'DayPickerInput input react')]")
	WebElement monthAndYearCaption;

	@FindBy(xpath = "//button[@class='react-datepicker__navigation react-datepicker__navigation--next']")
	WebElement nextButton;

//	@FindBy(xpath = "//input[@id='react-select-4-input']")
//	WebElement resultPagefromLocation;

	
	@FindBy(id = "TGFl-origin")
	WebElement resultPagefromLocation;
	
	@FindBy(xpath = "//div[@class='airport-focused airport-option']")
	WebElement resultPageenterFromLocation;


	@FindBy(xpath = "//div[@class='airport-focused airport-option']")
	WebElement resultPageentertoLocation;


	@FindBy(xpath = "//input[@id='TGFl-destination']")
	WebElement resultPagetoLocation;

	// Method to Set From location
	public void setFromLocation(String fromLocations) throws InterruptedException {
		Thread.sleep(3000);
		fromLocation.sendKeys(fromLocations); 
	}

	// Method to Click On From Suggestion

	public void clickOnFromSuggestion() throws AWTException, InterruptedException {
		Thread.sleep(3000);

		enterFromLocation.click();
		/*Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		 */
	}

	// Method to Set Result page  From location
	public void resultpagesetFromLocation(String fromLocations) throws InterruptedException {
		Thread.sleep(3000);
		resultPagefromLocation.sendKeys(fromLocations); 
	}

	// Method to Click On Result page From Suggestion

	public void resultpageclickOnFromSuggestion() throws AWTException, InterruptedException {
		Thread.sleep(3000);

		resultPageenterFromLocation.click();
		/*Robot r = new Robot();
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
		 */
	}


	public void resultpageclickOnToSuggestion() throws AWTException, InterruptedException {
		Thread.sleep(3000);

		resultPageentertoLocation.click();
		/*Robot r = new Robot();
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
		 */
	}

	public void setFromLocationOnResultPage(String fromLocation) throws InterruptedException, AWTException {
		Thread.sleep(3000); // Wait for field to be ready

		resultPagefromLocation.clear();            
		setFromLocation(fromLocation);
		clickOnFromSuggestion();
		Thread.sleep(2000);                        
		resultPageenterFromLocation.click();       // Click the first suggestion
	}


	//Method to modify Search
	// public void modifySearch(String from,String to,String targetMonth, String targetYear, String targetDay,String classes,int Adults) throws InterruptedException

	public void modifySearch(String from,String day,String MonthandYear,int Adults) throws InterruptedException
	{
		try {
			resultPagefromLocation.sendKeys(from);
			resultpageclickOnFromSuggestion();
			//  resultPagetoLocation.sendKeys(to);
			// resultpageclickOnToSuggestion();
			Thread.sleep(2000);
			selectDate(day,MonthandYear);
			Thread.sleep(2000);
			clickOnClassesDropDown();
			Thread.sleep(2000);
			ClickMinusButton();
			selectAdults(Adults);
			clickOnDone();
			clickOnSearchButton();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Assert.fail();
		}

	}

	
//	public void modifySearch(String fromLocations, String targetMonth, String targetYear, String targetDay, int Adults) throws InterruptedException {
//        try {
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
//            setFromLocation(fromLocations);
//            wait.until(ExpectedConditions.elementToBeClickable(enterFromLocation)).click();
//
//            selectDate(targetMonth, targetYear, targetDay);
//
//            modifyclickOnClassesDropDown();
//            
//            selectAdults(Adults);
//            clickOnDone();
//            clickOnSearchButton(); // Replace with actual locator
//            
//            
//
//         
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assert.fail("Search modification failed due to: " + e.getMessage());
//        }
//    }
//
//
//	public void modifyclickOnClassesDropDown()
//    {
//        driver.findElement(By.xpath("(//*[@data-testid='ChevronDownIcon'])[3]")).click();
//    }
	
	
	public void modifySearchTo(String to) throws InterruptedException
	{
		try {
			resultPagetoLocation.sendKeys(to);
			resultpageclickOnToSuggestion();
			Thread.sleep(2000);
			closeReturnButton();
			Thread.sleep(2000);

			clickOnSearchButton();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Assert.fail();
		}

	}


	// Method to Set To location
	public void setToLocation(String toLocations) throws InterruptedException {
		Thread.sleep(3000);

		toLocation.sendKeys(toLocations);
	}

	public void closeReturnButton() {
		driver.findElement(By.xpath("//button[@class='react-datepicker__close-icon']")).click();
	}



	// Method to Click on To Suggestion
	public void clickOnToSuggestion() throws AWTException, InterruptedException {
		Thread.sleep(1000);

		enterToLocation.click();
		/*	Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		 */
	}

	/*
	//Method to Select Date
	public void selectDatePicker(String day, String targetMonthAndYear) throws InterruptedException {
		// Click the input to open the calendar (if needed)
		datePickerInput.click();
		Thread.sleep(500); // Allow time for calendar popup to appear

		String displayedDate = monthAndYearCaption.getText();

		while (!monthAndYearCaption.getText().equals(targetMonthAndYear)) {
			nextButton.click();
			Thread.sleep(300); // Wait for the calendar to update
		}

		WebElement targetDay = driver.findElement(By.xpath("//div[text()='" + day + "']"));
		targetDay.click();
		Thread.sleep(500); // Optional: allow time for any post-click effects
	}
	 */
	// Method to Click on Search Button
	public void clickOnSearchButton() throws InterruptedException
	{
		Thread.sleep(5000);

		searchButton.click();
	}


	//Method to Clear From Date
	public void clearFromDate() throws InterruptedException
	{
		Thread.sleep(2000);
		WebElement datePicker=driver.findElement(By.xpath("//*[contains(normalize-space(@class), 'tg-fsonwarddate')]"));
		Thread.sleep(1000);
		datePicker.sendKeys(Keys.CONTROL + "a") ;
		datePicker.sendKeys(Keys.BACK_SPACE);

	}
	// Method to Click on Search Button
	public void logOutFromApplication(Log Log,ScreenShots ScreenShots) throws InterruptedException
	{
		driver.findElement(By.xpath("//button[text()='Mr Test Traveller ID']")).click();
		driver.findElement(By.xpath("//li[text()='Log out']")).click();
		Thread.sleep(6000);			
		WebElement tripGainLogo=driver.findElement(By.xpath("//img[@alt='TripGain']"));
		if(tripGainLogo.isDisplayed())
		{
			Log.ReportEvent("PASS", "User LogOut was Successful");
			ScreenShots.takeScreenShot1();

		}
		else {
			Log.ReportEvent("FAIL", "User LogOut was UnSuccessful");	
			ScreenShots.takeScreenShot1();

		}

	}

	//Method to Select Date By Passing Three Paramenters(Mounth,Year,Date)
	public void selectdate(String targetMonth, String targetYear, String targetDate) throws InterruptedException {
		// Click to open the calendar
		datePickerInput.click();

		Thread.sleep(5000); // Allow calendar to open

		// Get the displayed month and year
		String monthYearText = driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText();
		String currentMonth = monthYearText.split(" ")[0].trim();
		String currentYear = monthYearText.split(" ")[1].trim();

		// Loop until the correct month and year are displayed
		while (!(currentMonth.equalsIgnoreCase(targetMonth) && currentYear.equals(targetYear))) {
			driver.findElement(By.xpath("//button[@aria-label='Next Month']")).click();
			Thread.sleep(1000); // Wait for the calendar to update

			monthYearText = driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText();
			currentMonth = monthYearText.split(" ")[0].trim();
			currentYear = monthYearText.split(" ")[1].trim();

			System.out.println("Navigated to: " + currentMonth + " " + currentYear);
		}

		// Construct a reliable XPath that selects the correct date in the current month
		String dateXpath = "//div[contains(@class,'react-datepicker__day') and text()='" + targetDate + "' and not(contains(@class,'outside-month'))]";

		// Use WebDriverWait to ensure the date element is clickable
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dateXpath)));

		if (!dateElement.getAttribute("class").contains("disabled")) {
			System.out.println("Clicking on date: " + targetDate);
			dateElement.click();
		} else {
			System.out.println("The selected date is disabled: " + targetDate);
		}
	}


	///

	//Method to Click On Date
	public void clickdate()
	{
		datePickerInput.click();
	}

	//Method to Select Date By Passing Two Paramenters(Date and MounthYear)
	public void selectDate(String day, String MonthandYear) throws InterruptedException
	{
		datePickerInput.click();
		//String Date=driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText();
		 String Date = driver.findElement(By.cssSelector(".react-datepicker__current-month")).getText();
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

	//Method to Select Classes 
	public void selectClasses(String classes)
	{
		driver.findElement(By.xpath("//div[@class='traveller-popup']//span[text()='"+classes+"']")).click();	
	}

	//Method to Select Adults
	//	public void selectAdults(int Adults) throws InterruptedException
	//	{
	//		if(Adults==1)
	//		{
	//			System.out.println("Adults had been Selected");
	//		}
	//		else {
	//			for(int i=2;i<=Adults;i++)
	//			{
	//				Thread.sleep(1000);
	//				driver.findElement(By.xpath("//*[@data-testid='PlusIcon']")).click();
	//
	//			}
	//		}
	//	}
	//	
	public int selectAdults(int Adults) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		int currentAdults = 1; // fallback default

		try {
			WebElement adultsDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@data-tgfladults]")));  

			String currentAdultsText = adultsDiv.getText().trim();

			if (!currentAdultsText.isEmpty() && currentAdultsText.matches("\\d+")) {
				currentAdults = Integer.parseInt(currentAdultsText);
			} else {
			}
		} catch (Exception e) {
			System.out.println("Error fetching adults count: " + e.getMessage());
		}

		System.out.println("Current adults count on page: " + currentAdults);

		if (Adults <= currentAdults) {
			System.out.println("No increment needed. Requested: " + Adults + ", Current: " + currentAdults);
		} else {
			for (int i = currentAdults + 1; i <= Adults; i++) {
				WebElement plusButton = wait.until(ExpectedConditions.elementToBeClickable(
						By.xpath("//div[@data-tgfladults]//*[@data-testid='PlusIcon']")));

				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", plusButton);
				Thread.sleep(500); // pause for UI to settle
				plusButton.click();

				System.out.println("Clicked PlusIcon for adult number " + i);
				Thread.sleep(1000); // wait for count update
			}
		}

		return Adults;
	}




	//Method to click on Minus Icon
	public void ClickMinusButton() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//*[@data-testid='MinusIcon'])[1]")).click();
	}

	//Method to Click On ClassDropDown
	public void clickOnClassesDropDown() throws InterruptedException
	{
		Thread.sleep(3000);

		driver.findElement(By.xpath("//*[contains(@class,'tg-fstravelpopup')]")).click();
	}

	//Method Click ON Done Button
	public void clickOnDone() throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//WebElement doneBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='app_header.done']")));
		WebElement doneBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Done')]")));
		doneBtn.click();
	}


	public void adjustSliderToValue(int targetValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));

		// Locate the first thumb input element (for the first slider)
		WebElement sliderInput = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//input[@aria-orientation='horizontal' and @data-index='0']")
				));

		// Get min, max, and current values of the slider
		double minValue = Double.parseDouble(sliderInput.getAttribute("min"));
		double maxValue = Double.parseDouble(sliderInput.getAttribute("max"));
		double currentValue = Double.parseDouble(sliderInput.getAttribute("value"));

		System.out.println("Min Value: " + minValue);
		System.out.println("Max Value: " + maxValue);
		System.out.println("Current Value: " + currentValue);
		System.out.println("Target Value: " + targetValue);

		// Make sure the target value is within the min-max range
		if (targetValue < minValue) targetValue = (int) minValue;
		if (targetValue > maxValue) targetValue = (int) maxValue;

		// If the target value is already set, no need to adjust
		if (targetValue == currentValue) {
			System.out.println("Target value is the same as the current value. No adjustment needed.");
			return;
		}

		// Calculate the percentage of the slider to adjust based on target value
		double percentage = (targetValue - minValue) / (maxValue - minValue);

		// Locate the slider thumb's position (the thumb's left style value is what we need)
		WebElement thumb = driver.findElement(By.xpath("//span[@data-index='0']"));

		// Get the width of the slider
		int trackWidth = driver.findElement(By.xpath("//span[@class='MuiSlider-track']")).getSize().getWidth();
		int targetOffset = (int) (trackWidth * percentage);

		// Use JavaScriptExecutor to adjust the thumb's position (move it to the target value)
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.left = arguments[1] + '%';", thumb, targetOffset);

		// Optionally: simulate an input event to trigger any associated UI updates
		js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", 
				sliderInput, String.valueOf(targetValue));
	}

	//Method to Search Flights on Home Page
	public void searchFlightsOnHomePage(String fromLocations,String toLocations,String day, String MonthandYear,String classes,int Adults)
	{
		try {
			driver.findElement(By.xpath("//button[text()='Close']")).click();

			Thread.sleep(3000);
			setFromLocation(fromLocations);
			clickOnFromSuggestion();
			Thread.sleep(2000);
			setToLocation(toLocations);
			clickOnToSuggestion();
			Thread.sleep(2000);
			selectDate(day,MonthandYear);
			Thread.sleep(3000);
			clickOnClassesDropDown();
			Thread.sleep(3000);
			selectClasses(classes);
			selectAdults(Adults);
			clickOnDone();
			clickOnSearchButton();            
			Thread.sleep(10000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}  

	//Method to Search Flights on Home Page
	public void searchFlightsOnHomePage(String fromLocations,String toLocations,String day, String MonthandYear,String classes,int Adults,Log Log, ScreenShots ScreenShots)
	{
		try {
			//driver.findElement(By.xpath("//button[text()='Close']")).click();
			Thread.sleep(2000);
			setFromLocation(fromLocations);
			clickOnFromSuggestion();
			Thread.sleep(1000);
			setToLocation(toLocations);
			clickOnToSuggestion();
			Thread.sleep(1000);
			selectDate(day,MonthandYear);
			Thread.sleep(1000);
			clickOnClassesDropDown();
			Thread.sleep(1000);
			selectClasses(classes);
			selectAdults(Adults);
			clickOnDone();
			clickOnSearchButton();            
			Thread.sleep(10000);
		}
		catch(Exception e)
		{
			Log.ReportEvent("FAIL", "User Search was UnSuccessful");
			ScreenShots.takeScreenShot1();
			e.printStackTrace();
			Assert.fail();

		}
	}
	//Method to Verify Home Page is Displayed
	public void verifyHomePageIsDisplayed(Log Log,ScreenShots ScreenShots) throws InterruptedException {
		try {
			WebElement homePageLogo=driver.findElement(By.xpath("//strong[text()='Book Flights']"));
			if(homePageLogo.isDisplayed())
			{
				Log.ReportEvent("PASS", "Home Page is displayed Successful");
				ScreenShots.takeScreenShot1();
			}
			else
			{
				Log.ReportEvent("FAIL", "Home Page is not displayed");
				ScreenShots.takeScreenShot1();                
			}        
		}
		catch(Exception e)
		{
			Log.ReportEvent("FAIL", "Home Page is not displayed");
			ScreenShots.takeScreenShot1();
			e.printStackTrace();
		}

	}  


	//Round Trip

	@FindBy(xpath="//input[@value='roundtrip']")
	WebElement Roundtrip;

	public void Clickroundtrip() {
		Roundtrip.click();
	}


		
	

	//Return date picker --RoundTrip
	@FindBy(xpath = "//input[@placeholder='Return Date (Optional)']")
	WebElement returndatePickerInput;

	//Method to Click On Date
	public void clickreturndateRoundTrip()
	{
		returndatePickerInput.click();
	}

	//Method to Select Return Date By Passing Two Parameters(Date and MounthYear)
	
  	public void selectReturnDateRoundTrip(String returnday, String returnMonthandYear) throws InterruptedException
  	{
  		returndatePickerInput.click();
  		String Date=driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText();
  		if(Date.contentEquals(returnMonthandYear))
  		{
  			driver.findElement(By.xpath("//div[text()='"+returnday+"']")).click();
  			Thread.sleep(4000);
  		}else {
  			while(!Date.contentEquals(returnMonthandYear))
  			{
  				driver.findElement(By.xpath("//button[@aria-label='Next Month']")).click();
  				if(driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText().contentEquals(returnMonthandYear))
  				{
  					driver.findElement(By.xpath("//div[text()='"+returnday+"']")).click();
  					break;
  				}

  			}
  		}
  	}
	 

	//Method to Select Return Date By Passing Two Paramenters(Date and MounthYear)
//	public void selectReturnDate(String returnDate, String returnMonthAndYear) throws InterruptedException
//	{
//		clickOnReturnDate();
//		//String Date=driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText();
//		 String Date = driver.findElement(By.cssSelector(".react-datepicker__current-month")).getText();
//		if(Date.contentEquals(returnMonthAndYear))
//		{
//			driver.findElement(By.xpath("(//div[@class='react-datepicker__month-container'])[1]//div[text()='"+returnDate+"' and @aria-disabled='false']")).click();
//			Thread.sleep(4000);
//		}else {
//			while(!Date.contentEquals(returnMonthAndYear))
//			{
//				Thread.sleep(500);
//				driver.findElement(By.xpath("//button[@aria-label='Next Month']")).click();
//				if(driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText().contentEquals(returnMonthAndYear))
//				{
//					driver.findElement(By.xpath("//div[text()='"+returnDate+"']")).click();
//					break;
//				}
//
//			}
//		}
//	}
//	//Method to Select Date By Passing Two Paramenters(Date and MounthYear) for calender2
//    
//        public void selectDatecalender2(String returnDay, String returnMonthYear) throws InterruptedException
//
//        {
//        datePickerInput2.click();
//        String Date=driver.findElement(By.xpath("//div[@class='react-datepicker__month-container']//div/child::h2")).getText();
//        if(Date.contentEquals(returnMonthYear))
//        {
//            driver.findElement(By.xpath("//div[text()='"+returnDay+"']")).click();
//            Thread.sleep(4000);
//        }else {
//            while(!Date.contentEquals(returnMonthYear))
//            {
//                Thread.sleep(500);
//                driver.findElement(By.xpath("//button[@aria-label='Next Month']")).click();
//                if(driver.findElement(By.xpath("//div[@class='react-datepicker__month-container']//div/child::h2")).getText().contentEquals(MonthandYearcalender2))
//                {
//                    driver.findElement(By.xpath("//div[text()='"+returnDay+"']")).click();
//                    break;
//                }
//
//            }
//       }
//    }
//  
//	
	//Method to Click Return Date
	public void clickOnReturnDate()
	{
		driver.findElement(By.xpath("//*[contains(normalize-space(@class), 'tg-fsreturndate')]")).click();
	}

	 //   Method to Search Flights on Home Page --roundtrip
	    public void searchFlightsOnHomePage(Log Log,ScreenShots ScreenShots,String fromLocations,String toLocations,String day, String MonthandYear,String returnDay,String returnMonthAndYear,String classes,int Adults)
	    {
	        try {
	        	//driver.findElement(By.xpath("//button[text()='Close']")).click();
	
	        	Thread.sleep(1500);
	            setFromLocation(fromLocations);
	            clickOnFromSuggestion();
	        	Thread.sleep(1500);
	            setToLocation(toLocations);
	              clickOnToSuggestion();
	          	Thread.sleep(1500);
	              selectDate(day,MonthandYear);
	          	Thread.sleep(1500);
	          	selectReturnDateRoundTrip(returnDay, returnMonthAndYear);
	          	clickOnClassesDropDown();
	          	Thread.sleep(1500);
	              selectClasses(classes);
	          	Thread.sleep(1500);
	              selectAdults(Adults);
	                clickOnDone();
	              clickOnSearchButton();
	            Thread.sleep(9000);
	            
	        }
	        catch(Exception e)
	        {
	        	Log.ReportEvent("FAIL", "User Search was UnSuccessful");
	            ScreenShots.takeScreenShot1();
	            e.printStackTrace();
	            Assert.fail();
	        }
	    }

	@FindBy(xpath="//*[contains(normalize-space(@class), 'tg-fsreturndate')]")
    WebElement datePickerInput2;
	
	

	public static String[] GenerateDatesToSelectFlights() {
		LocalDate today = LocalDate.now();
		LocalDate futureDate = today.plusDays(5);
		LocalDate returnFutureDate = today.plusDays(10); // Add 5 days

		int day = futureDate.getDayOfMonth();// e.g., 13
		String fromDate = String.valueOf(day);
		int returnDay = returnFutureDate.getDayOfMonth();// e.g., 13
		String returnDate = String.valueOf(returnDay);


		String month = futureDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH); // e.g., "May"
		int year = futureDate.getYear();
		String fromYear = String.valueOf(year);

		String returnMonth = returnFutureDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH); // e.g., "May"
		int returnYear = returnFutureDate.getYear();
		String returnYears = String.valueOf(returnYear);

		String fromMonthYear = month + " " + year;
		String returnMonthYear = returnMonth + " " + returnYear;

		return new String[] {fromDate,returnDate,fromMonthYear, returnMonthYear,month,fromYear,returnMonth,returnYears};

	}


	//method to select Adult, child,Infant
	public void selectpax(String... Pax) throws InterruptedException 
	{
		driver.findElement(By.xpath("//*[@data-testid='ChevronDownIcon']")).click();

		List<WebElement> getAllPaxs = driver.findElements(By.xpath("//div[@class='stepper-buttons']"));

		for (String paxEntry : Pax) {
			String paxTypeFromInput = paxEntry.split(":")[0].trim(); //Adult:2
			int desiredCount = Integer.parseInt(paxEntry.split(":")[1].trim());

			for (WebElement getAllPax : getAllPaxs) {
				String getAllPaxText = getAllPax.getText(); 
				System.out.println(getAllPax);
				System.out.println(getAllPaxText);
				String paxType = getAllPaxText.split(" ")[1];  // e.g., Adults, Children, etc.
				System.out.println("Found: " + paxType + ", Target: " + paxTypeFromInput);

				if (paxType.toLowerCase().contains(paxTypeFromInput.toLowerCase())) {
					int currentCount = Integer.parseInt(getAllPax.findElement(By.xpath(".//span")).getText());
					System.out.println(currentCount);
					WebElement plusButton = getAllPax.findElement(By.xpath(".//*[@data-testid='PlusIcon']"));

					for (int i = currentCount; i < desiredCount; i++) {
						plusButton.click();
						Thread.sleep(300);
					}

					System.out.println("Set " + paxType + " to " + desiredCount);
					break;
				}
			}
		}
	}


	//==============================================================================================================

	// L1-L1 GRADE


	public void kebabMenu()
	{
		driver.findElement(By.xpath("//button[text()='Mr test traveller98']")).click();
	}

	public void Menu(String options) {
		try {
			kebabMenu();  // Method that opens the dropdown menu
			WebElement dropDownList = driver.findElement(By.xpath("//ul[@role='menu']"));

			if (dropDownList.isDisplayed()) {
				WebElement menuItem = driver.findElement(By.xpath("//a[text()='" + options + "']"));
				menuItem.click();
			}
		} catch (NoSuchElementException e) {
			System.out.println("Element not found: " + e.getMessage());
		} catch (ElementNotInteractableException e) {
			System.out.println("Element not interactable: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Unexpected exception: " + e.getMessage());
		}
	}
	public void validatePolicyGrade(String expectedGrade,Log Log, ScreenShots ScreenShots) {
		try {

			WebElement gradeElement = driver.findElement(By.xpath("//h6[text()='L1 - L1 Test Grade']"));
			String gradeText = gradeElement.getText();

			if (gradeText.equals(expectedGrade)) {
				Log.ReportEvent("PASS", "Selected grade is: " + gradeText);

			} else {
				Log.ReportEvent("FAIL", "Selected grade does not match expected. Found: " + gradeText);
			}
			ScreenShots.takeScreenShot1();
		} catch (NoSuchElementException e) {
			Log.ReportEvent("FAIL", "Grade element not found: " + e.getMessage());
			ScreenShots.takeScreenShot1();
		} catch (Exception e) {
			Log.ReportEvent("FAIL", "Unexpected error while validating grade: " + e.getMessage());
			ScreenShots.takeScreenShot1();
		}
	}

	public String travelMenu(String travelSelection,Log Log, ScreenShots ScreenShots) {
		try {
			// Click on the Travel menu
			driver.findElement(By.xpath("//button[text()='Travel']")).click();

			// Locate the dropdown menu
			WebElement travelDropdown = driver.findElement(By.xpath("//div[@data-testid='sentinelStart']//parent::div//ul[@role='menu']"));

			WebElement oneWayRadioButton;

			// If dropdown is displayed, select the desired option
			if (travelDropdown.isDisplayed()) {
				WebElement option = driver.findElement(By.xpath("//a[text()='" + travelSelection + "']"));
				option.click();
				Thread.sleep(2000);
				oneWayRadioButton=driver.findElement(By.xpath("//input[@value='oneway']"));
				if (oneWayRadioButton.isSelected()) {
					Log.ReportEvent("PASS", "One-Way Radio Button is selected by default.");
				} else {
					Log.ReportEvent("INFO", "One-Way Radio Button is not selected by default. Selecting it now.");
					oneWayRadioButton.click();
				}
				return travelSelection;

			} else {
				System.out.println("Dropdown menu not displayed.");
			}

		} catch (NoSuchElementException e) {
			System.out.println("Element not found: " + e.getMessage());
		} catch (ElementNotInteractableException e) {
			System.out.println("Element not interactable: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
		}
		return travelSelection;
	}

	//Method to Verify The From Field Error Message
	public void verifyErrorMsgForFromField(Log Log,ScreenShots ScreenShots) throws InterruptedException
	{
		try {
			WebElement errorMsgForFromField =driver.findElement(By.xpath("//span[@id='client-snackbar']"));
			if(errorMsgForFromField.isDisplayed())
			{
				Log.ReportEvent("PASS", "Error Message is Displayed for From field (Select Origin field) is Successful");
			}
			else
			{
				Log.ReportEvent("FAIL", "Error Message is not Displayed for From field (Select Origin field)");
				ScreenShots.takeScreenShot1();
				Assert.fail();

			}
		}
		catch(Exception e)
		{
			Log.ReportEvent("FAIL", "Error Message is not Displayed for From field (Select Origin field)");
			ScreenShots.takeScreenShot1();
			e.printStackTrace();
			Assert.fail();

		}

	}
	/////////////////////////////////////

	//Method to Check Error Message for From Field
	public void validateErrorMsgForFromField(String toLocations,String targetMonth, String targetYear,String targetDay,String classes,int Adults,Log Log,ScreenShots ScreenShots)
	{
		try {
			setToLocation(toLocations);
			clickOnToSuggestion();
			//	selectDate(day,MonthandYear);
			selectDate(targetMonth, targetYear, targetDay);
			clickOnClassesDropDown();
			selectClasses(classes);
			Thread.sleep(3000);
			selectAdults(Adults);
			Thread.sleep(3000);
			clickOnDone();
			Thread.sleep(3000);
			clickOnSearchButton();
			Thread.sleep(2000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Assert.fail();

		}
	}

	//Method to Select Date By Passing Three Paramenters(Mounth,Year,Date)
	public void selectDate(String targetMonth, String targetYear, String targetDay) throws InterruptedException {
		// Open the calendar
		datePickerInput.click();
		Thread.sleep(3000); // Ideally replaced with explicit waits

		// Get current displayed month and year
		String displayedMonthYear = driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText();
		String currentMonth = displayedMonthYear.split(" ")[0].trim();
		String currentYear = displayedMonthYear.split(" ")[1].trim();

		// Loop until desired month and year are displayed
		while (!(currentMonth.equalsIgnoreCase(targetMonth) && currentYear.equals(targetYear))) {
			driver.findElement(By.xpath("//button[@aria-label='Next Month']")).click();
			Thread.sleep(500); // Small delay to allow calendar to update

			displayedMonthYear = driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText();
			currentMonth = displayedMonthYear.split(" ")[0].trim();
			currentYear = displayedMonthYear.split(" ")[1].trim();
		}

		// Build a safe XPath that avoids days from other months
		String dayXPath = "//div[contains(@class,'react-datepicker__day') " +
				"and not(contains(@class,'outside-month')) " +
				"and text()='" + targetDay + "']";

		WebElement dayElement = driver.findElement(By.xpath(dayXPath));

		// Check if the day is enabled
		if (dayElement.getAttribute("aria-disabled") == null || !dayElement.getAttribute("aria-disabled").equals("true")) {
			dayElement.click();
		} else {
			System.out.println("The selected date is disabled: " + targetDay);
			Assert.fail();
		}
	}

	///

	public void clearcalenderdefaulttext(String fromLocations,String toLocations,String classes,int Adults,Log Log,ScreenShots ScreenShots)
	{
		try {
			setFromLocation(fromLocations);
			clickOnFromSuggestion();
			setToLocation(toLocations);
			clickOnToSuggestion();
			Thread.sleep(3000);

			clearFromDate();
			Thread.sleep(3000);

			//							selectDate(day,MonthandYear);
			clickOnClassesDropDown();
			selectClasses(classes);
			Thread.sleep(3000);
			selectAdults(Adults);
			Thread.sleep(3000);
			clickOnDone();
			Thread.sleep(3000);
			clickOnSearchButton();
			Thread.sleep(2000);
			//							verifyerrormsgofemptyTofield(Log, ScreenShots);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Assert.fail();

		}
	}
	//Method to Validate Calendar Error Message
	public void verifyErrorMsgForCalenderField(Log Log,ScreenShots ScreenShots) throws InterruptedException
	{
		try {
			WebElement errorMsgForCalenderField =driver.findElement(By.xpath("//*[text()='Select journey date.']"));
			if(errorMsgForCalenderField.isDisplayed())
			{
				Log.ReportEvent("PASS", "Error Message is Displayed for Calender Field is Successful");
			}
			else
			{
				Log.ReportEvent("FAIL", "Error Message is Not Displayed for Calender Field");
				ScreenShots.takeScreenShot1();
				Assert.fail();

			}
		}
		catch(Exception e)
		{
			Log.ReportEvent("FAIL", "Error Message is Not Displayed for Calender Field");
			ScreenShots.takeScreenShot1();
			e.printStackTrace();
			Assert.fail();

		}

	}		

	//method for oneway
	public void searchFlightsOnHomePage(String fromLocations, String toLocations,
			String targetMonth, String targetYear, String targetDay,
			String classes, int Adults) {
		try {
			Thread.sleep(3000);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

			setFromLocation(fromLocations);
			wait.until(ExpectedConditions.elementToBeClickable(enterFromLocation)).click();

			setToLocation(toLocations);
			wait.until(ExpectedConditions.elementToBeClickable(enterToLocation)).click();

			selectDate(targetMonth, targetYear, targetDay);


			clickOnClassesDropDown();
			selectClasses(classes);
			selectAdults(Adults);
			clickOnDone();

			wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click(); // Replace with correct locator

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Flight search failed: " + e.getMessage());
		}
	}

	public String[] MenuOption(String options) {
		try {
			kebabMenu();  // Opens the kebab menu

			WebElement dropDownList = driver.findElement(By.xpath("//ul[@role='menu']"));
			if (dropDownList.isDisplayed()) {

				WebElement menuItem = driver.findElement(By.xpath("//a[text()='" + options + "']"));
				menuItem.click();

				//				            String employeeCode = driver.findElement(By.xpath("(//h6[contains(@class, 'MuiTypography-subtitle1')])[2]")).getText().trim();
				//				            String approvalManager = driver.findElement(By.xpath("(//h6[contains(@class, 'MuiTypography-subtitle1')])[12]")).getText().trim();
				//
				//				            String traveller = driver.findElement(By.xpath("//h5[contains(@class,'MuiTypography-h5')]")).getText().trim();

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

				WebElement employeeCodeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h6[contains(@class, 'MuiTypography-subtitle1')])[2]")));
				String employeeCode = employeeCodeElement.getText().trim();

				WebElement approvalManagerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h6[contains(@class, 'MuiTypography-subtitle1')])[12]")));
				String approvalManager = approvalManagerElement.getText().trim();

				WebElement travellerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//h5[contains(@class,'MuiTypography-h5')])[2]")));
				String traveller = travellerElement.getText().trim();


				// Safely remove title like "Mr", "Ms", etc.
				if (traveller.matches("^(Mr|Ms|Mrs)\\s+.*")) {
					traveller = traveller.replaceFirst("^(Mr|Ms|Mrs)\\s+", "").trim();
				}
				System.out.println("Raw Employee Code text: '" + employeeCode + "'");
				System.out.println("Raw Approval Manager text: '" + approvalManager + "'");
				System.out.println("Raw Traveller text: '" + traveller + "'");


				return new String[] {employeeCode, approvalManager, traveller};
			}

		} catch (NoSuchElementException e) {
			System.out.println("Element not found: " + e.getMessage());
		} catch (ElementNotInteractableException e) {
			System.out.println("Element not interactable: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Unexpected exception: " + e.getMessage());
		}

		// If something fails, return default values for all 3
		return new String[] {"", "", ""};
	}

	public void validatePolicyGradeL1(String expectedGrade,Log Log, ScreenShots ScreenShots) {
		try {

			WebElement gradeElement = driver.findElement(By.xpath("//h6[text()='L1 - L1 Test Grade']"));
			String gradeText = gradeElement.getText();

			if (gradeText.equals(expectedGrade)) {
				Log.ReportEvent("PASS", "Selected grade is: " + gradeText);

			} else {
				Log.ReportEvent("FAIL", "Selected grade does not match expected. Found: " + gradeText);
			}
			ScreenShots.takeScreenShot1();
		} catch (NoSuchElementException e) {
			Log.ReportEvent("FAIL", "Grade element not found: " + e.getMessage());
			ScreenShots.takeScreenShot1();
		} catch (Exception e) {
			Log.ReportEvent("FAIL", "Unexpected error while validating grade: " + e.getMessage());
			ScreenShots.takeScreenShot1();
		}
	}
	public void validatePolicyGradeL2(String expectedGrade,Log Log, ScreenShots ScreenShots) {
		try {

			WebElement gradeElement = driver.findElement(By.xpath("//h6[text()='L2 - L2 Test Grade']"));
			String gradeText = gradeElement.getText();

			if (gradeText.equals(expectedGrade)) {
				Log.ReportEvent("PASS", "Selected grade is: " + gradeText);

			} else {
				Log.ReportEvent("FAIL", "Selected grade does not match expected. Found: " + gradeText);
			}
			ScreenShots.takeScreenShot1();
		} catch (NoSuchElementException e) {
			Log.ReportEvent("FAIL", "Grade element not found: " + e.getMessage());
			ScreenShots.takeScreenShot1();
		} catch (Exception e) {
			Log.ReportEvent("FAIL", "Unexpected error while validating grade: " + e.getMessage());
			ScreenShots.takeScreenShot1();
		}
	}

	
	//---------------------------------------  hotels----------------------------------------------
	
	//Method to Click On Travel Drop Down
		public void clickOnTravelDropDown() throws InterruptedException {
			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[@data-testid='KeyboardArrowDownIcon']/parent::button[text()='Travel']")).click();
		}

		//Method to Select Travel Options
		public void selectTravelOptionsOnHomeScreen(String travelOption) throws InterruptedException {
			Thread.sleep(1000);
			driver.findElement(By.xpath("//a[text()='"+travelOption+"']")).click();
			Thread.sleep(1000);

		}


	




}

