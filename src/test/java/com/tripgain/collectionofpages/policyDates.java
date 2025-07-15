package com.tripgain.collectionofpages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;

public class policyDates {
	WebDriver driver;

	public policyDates(WebDriver driver) {

		PageFactory.initElements(driver, this);
		this.driver=driver;
	}
	
    
    //round trip
    
    @FindBy(xpath = "//input[@id='TGFl-origin']")
    WebElement fromLocation;

//    @FindBy(xpath = "//*[@class='tg-select__menu css-zgmpxx-menu']")
//    WebElement enterFromLocation;
    
    @FindBy(xpath = "//div[@class='airport-focused airport-option']")
    WebElement enterFromLocation;

    @FindBy(xpath = "//input[@id='TGFl-destination']")
    WebElement toLocation;

//    @FindBy(xpath = "//*[@class='tg-select__menu css-zgmpxx-menu']")
//    WebElement enterToLocation;
    @FindBy(xpath = "//div[@class='airport-focused airport-option']")
    WebElement enterToLocation;

    //@FindBy(xpath = "//div[@class='MuiGrid2-root MuiGrid2-direction-xs-row css-uzfmmu']")
    //WebElement searchButton;

    @FindBy(xpath = "//*[@data-testid='MagnifyIcon']/ancestor::button")
    WebElement searchButton;

//    @FindBy(xpath = "//input[@class='DayPickerInput input' and not(contains(@placeholder, 'Return Date (Optional)'))]")
//    WebElement datePickerInput;
    @FindBy(xpath = "//input[@placeholder='Journey Date']")
    WebElement datePickerInput;
    

    @FindBy(xpath = "//input[@placeholder='Journey Date']")
    WebElement monthAndYearCaption;
//    @FindBy(xpath = "//div[@class='react-datepicker-wrapper']//input[contains(@class ,'DayPickerInput input react')]")
//    WebElement monthAndYearCaption;

    //@FindBy(xpath = "//button[@class='react-datepicker__navigation react-datepicker__navigation--next']")
    //WebElement nextButton;

    @FindBy(xpath = "//button[@aria-label='Next Month']")
    WebElement nextButton;

//    @FindBy(xpath="//input[@class='DayPickerInput input' and (contains(@placeholder, 'Return Date (Optional)'))]")
//    WebElement datePickerInputReturnDate;
    @FindBy(xpath="//input[@placeholder='Return Date (Optional)']")
    WebElement datePickerInputReturnDate;

    @FindBy(xpath="//input[@value='roundtrip']")
    WebElement roundTripRadioButton;

  //Method to Click On ClassDropDown
    public void clickOnClassesDropDown()
    {
        driver.findElement(By.xpath("//button[text()='1']")).click();
    }
    
  //Method to Select Adults
    public void selectAdults(int Adults) throws InterruptedException
    {
        if(Adults==1)
        {
            System.out.println("Adults had been Selected");
        }
        else {
            for(int i=2;i<=Adults;i++)
            {
                Thread.sleep(1000);
                driver.findElement(By.xpath("//*[@data-testid='PlusIcon']")).click();

            }
        }
    }
    

    //Method Click ON Done Button
    public void clickOnDone()
    {
        driver.findElement(By.xpath("//button[text()='Done']")).click();
    }
  //Method to Select Classes 
    public void selectClasses(String classes) throws InterruptedException
    {
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@class='traveller-popup']//span[text()='"+classes+"']")).click();
    }

 // Method to Set From location
    public void setFromLocation(String fromLocations) throws InterruptedException {

        Thread.sleep(3000);
        fromLocation.sendKeys(fromLocations); 
    }
    
 // Method to Set To location
    public void setToLocation(String toLocations) throws InterruptedException {


        toLocation.sendKeys(toLocations);
    }
    
//  //Method to Select Date By Passing Three Paramenters(Mounth,Year,Date)
//    public void selectDate(String targetMonth, String targetYear, String targetDay) throws InterruptedException {
//        // Open the calendar
//        datePickerInput.click();
//        Thread.sleep(3000); // Ideally replaced with explicit waits
//
//        // Get current displayed month and year
//        String displayedMonthYear = driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText();
//        String currentMonth = displayedMonthYear.split(" ")[0].trim();
//        String currentYear = displayedMonthYear.split(" ")[1].trim();
//        System.out.println(displayedMonthYear);
//        System.out.println(currentMonth);
//        System.out.println(currentMonth);
//
//        // Loop until desired month and year are displayed
//        while (!(currentMonth.equalsIgnoreCase(targetMonth) && currentYear.equals(targetYear))) {
//            driver.findElement(By.xpath("//button[@aria-label='Next Month']")).click();
//            Thread.sleep(500); // Small delay to allow calendar to update
//
//            displayedMonthYear = driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText();
//            currentMonth = displayedMonthYear.split(" ")[0].trim();
//            currentYear = displayedMonthYear.split(" ")[1].trim();
//        }
//
//        // Build a safe XPath that avoids days from other months
//        String dayXPath = "//div[contains(@class,'react-datepicker__day') " +
//                "and not(contains(@class,'outside-month')) " +
//                "and text()='" + targetDay + "']";
//        System.out.println("hello");
//        System.out.println(dayXPath+"  hello");
//
//        WebElement dayElement = driver.findElement(By.xpath(dayXPath));
//
//        // Check if the day is enabled
//        if (dayElement.getAttribute("aria-disabled") == null || !dayElement.getAttribute("aria-disabled").equals("true")) {
//            dayElement.click();
//        } else {
//            System.out.println("The selected date is disabled: " + targetDay);
//            Assert.fail();
//        }
//    }
//    
 // Method to Select Date By Passing Three Parameters (Month, Year, Date)
//    public void selectDate(String targetMonth, String targetYear, String targetDay) throws InterruptedException {
//        try {
//            // Open the calendar
//            datePickerInput.click();
//            
//            Thread.sleep(3000); // Ideally replaced with explicit waits
//
//            // Get current displayed month and year
//            String displayedMonthYear = driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText();
//            String currentMonth = displayedMonthYear.split(" ")[0].trim();
//            String currentYear = displayedMonthYear.split(" ")[1].trim();
//            System.out.println("Displayed: " + displayedMonthYear);
//      
//
//            // Loop until desired month and year are displayed
//            while (!(currentMonth.equalsIgnoreCase(targetMonth) && currentYear.equals(targetYear))) {
//                driver.findElement(By.xpath("//button[@aria-label='Next Month']")).click();
//                
//                Thread.sleep(500); // Allow calendar to update
//
//                displayedMonthYear = driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText();
//                currentMonth = displayedMonthYear.split(" ")[0].trim();
//                currentYear = displayedMonthYear.split(" ")[1].trim();
//                System.out.println("Updated Display: " + displayedMonthYear);
//            }
//
//            // Build XPath to avoid selecting days from other months
//            String dayXPath = "//div[contains(@class,'react-datepicker__day') " +
//                    "and not(contains(@class,'outside-month')) " +
//                    "and text()='" + targetDay + "']";
//            System.out.println("Generated day XPath: " + dayXPath);
//          System.out.println(targetDay);
//            
//           
//            WebElement dayElement = driver.findElement(By.xpath(dayXPath));
//            
//            // Check if the day is enabled and click
//            if (dayElement.getAttribute("aria-disabled") == null || !dayElement.getAttribute("aria-disabled").equals("true")) {
//                dayElement.click();
//                System.out.println("Clicked date: " + targetDay);
//                
//            } else {
//                System.out.println("The selected date is disabled: " + targetDay);
//               
//                Assert.fail("The selected date is disabled.");
//            }
//
//        } catch (Exception e) {
//            System.out.println("Exception while selecting date: " + e.getMessage());
//           
//            throw e;
//        }
//    }
   

  //Method for roundtrip
    public void searchFlightsOnHomePage(String fromLocations, String toLocations,
            String targetMonth, String targetYear, String targetDay,
            String targetMonthReturn, String targetYearReturn, String targetDayReturn,
            String classes, int Adults) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

            setFromLocation(fromLocations);
            wait.until(ExpectedConditions.elementToBeClickable(enterFromLocation)).click();

            setToLocation(toLocations);
            wait.until(ExpectedConditions.elementToBeClickable(enterToLocation)).click();

            selectDate(targetMonth, targetYear, targetDay);
            selectReturnDate(targetMonthReturn, targetYearReturn, targetDayReturn);

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
 //Method to Select Date By Passing Three Paramenters(Mounth,Year,Date)
   public void selectDate(String targetMonth, String targetYear, String targetDay) throws InterruptedException {
       // Open the calendar
       datePickerInput.click();
       Thread.sleep(3000); // Ideally replaced with explicit waits

       // Get current displayed month and year
       String displayedMonthYear = driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText();
       String currentMonth = displayedMonthYear.split(" ")[0].trim();
       String currentYear = displayedMonthYear.split(" ")[1].trim();

       System.out.println("hi");
       // Loop until desired month and year are displayed
       while (!(currentMonth.equalsIgnoreCase(targetMonth) && currentYear.equals(targetYear))) {
           driver.findElement(By.xpath("//button[@aria-label='Next Month']")).click();
           Thread.sleep(500); // Small delay to allow calendar to update

           displayedMonthYear = driver.findElement(By.xpath("(//div[@class='react-datepicker__header ']/child::h2)[1]")).getText();
           currentMonth = displayedMonthYear.split(" ")[0].trim();
           currentYear = displayedMonthYear.split(" ")[1].trim();
       }
System.out.println("hello");
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
public void selectReturnDate(String targetMonth, String targetYear, String targetDay) throws InterruptedException {
        // Open the calendar
        datePickerInputReturnDate.click();

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


}
