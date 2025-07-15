package com.tripgain.collectionofpages;

import java.time.Duration;
import java.util.List;

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

public class Tripgain_Bookingpage {

WebDriver driver;


public Tripgain_Bookingpage(WebDriver driver) {

PageFactory.initElements(driver, this);
this.driver=driver;
}


public void clicktravelers(String text1, String text2) {
   String dynamicXPath = "//p[text()='" + text1 + "' or text()='" + text2 + "']";
   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
   WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXPath)));
driver.findElement(By.xpath("(//button[contains(text(), 'Pick Seat')])[1]")).click();

}
public void clickSeatByNumber(String seatNumber) {
   String dynamicXPath = "//div[@aria-label='Seat " + seatNumber + "']";
   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
   WebElement seatElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXPath)));
   seatElement.click();
   driver.findElement(By.xpath("//button[text()='Continue']")).click();
}

@FindBy(xpath="//label[text()='Purpose of Travel']/following::div[1]")
WebElement pot;

public void clickpot() {
   pot.click();
}

public void clickpurposeoftravel(String value) {
   String dynamicXPath = "//li[@data-value='" + value + "']";
   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
   WebElement listItem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dynamicXPath)));
   JavascriptExecutor js = (JavascriptExecutor) driver;
   js.executeScript("arguments[0].scrollIntoView(true);", listItem);
   wait.until(ExpectedConditions.elementToBeClickable(listItem)).click();
   driver.findElement(By.xpath("//span[text()='Send for Approval']")).click();
   }

//adult2 title

@FindBy(xpath="//div[contains(@id,\":r3v:\")]")
WebElement adult2title;

public void clickadult2title() {
   WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(80));
   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id,\":r3v:\")]")));
   adult2title.click();
}

public void bookingpageadults2(String title) {
   String dynamicXPath = "//li[@data-value='" + title + "']";
   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
   WebElement titleElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXPath)));
   titleElement.click();
   //adult2 firstname
   driver.findElement(By.xpath("//input[contains(@id,\":r41:\")]")).sendKeys("ex");
   //adult2 lastname
   driver.findElement(By.xpath("//input[contains(@id,\":r42:\")]")).sendKeys("user1");


}
//adult3 title

@FindBy(xpath = "//div[contains(@id,\":r43:\")]")
WebElement adult3title;

public void clickadult3title() {
   WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(80));
   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id,\":r43:\")]")));
   adult3title.click();
   
}

public void bookingpageadults3(String userInput) {
   String dynamicXPath ="//li[@data-value='" + userInput + "']";
   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
   WebElement titleElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dynamicXPath)));
   titleElement.click();
//adult3 firstname
driver.findElement(By.xpath("//input[contains(@id,\":r45:\")]")).sendKeys("ex");
//adult3 lastname
driver.findElement(By.xpath("//input[contains(@id,\":r46:\")]")).sendKeys("user2");


}


//Method to Click on Send Approval
public void clickOnSendApprovalButton() throws InterruptedException
{
	Thread.sleep(3000);
    driver.findElement(By.xpath("//span[text()='Send for Approval']")).click();
    Thread.sleep(1000);
}

//Method to Validate Send Approval Toast 
public void validateSendApprovalToastMessage(Log Log,ScreenShots ScreenShots)
{
    try {
        String approvalToastMessage=driver.findElement(By.xpath("//span[@id='client-snackbar']")).getText();
        if(approvalToastMessage.contentEquals("Your request has been successfully submitted."))
        {
            Log.ReportEvent("PASS", "Send Approval is Successful");
            ScreenShots.takeScreenShot1();
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
    

public void sendApproval(Log Log, ScreenShots ScreenShots) throws InterruptedException
{
	driver.findElement(By.xpath("//span[text()='Send for Approval']")).click();
	
	WebElement confirmationPopup=driver.findElement(By.xpath("//span[@id='client-snackbar']"));
	if(confirmationPopup.isDisplayed())
	{
		String message=confirmationPopup.getText();

		if(message.equals("Your request has been successfully submitted."))
		{
		System.out.println(message);
		Log.ReportEvent("PASS", "Request Is Sent: " + message );
		ScreenShots.takeScreenShot1();	
		}else
		{
			System.out.println(message);
    		Log.ReportEvent("FAIL", "Request Is Sent: " + message );
    		ScreenShots.takeScreenShot1();
		}
	}
}
public void selectDepartment()
{
    driver.findElement(By.xpath("//input[@id='react-select-7-input']")).click();
    driver.findElement(By.xpath("//div[@id='react-select-7-option-0']")).click();
}

public void selectProject()
{
    driver.findElement(By.xpath("//input[@id='react-select-9-input']")).click();
    driver.findElement(By.xpath("//div[@id='react-select-9-option-0']")).click();
}

public void selectCostcenter()
{
    driver.findElement(By.xpath("//input[@id='react-select-8-input']")).click();
    driver.findElement(By.xpath("//div[@id='react-select-8-option-0']")).click();
}

public double selectBaggageOnward()
{
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


}
