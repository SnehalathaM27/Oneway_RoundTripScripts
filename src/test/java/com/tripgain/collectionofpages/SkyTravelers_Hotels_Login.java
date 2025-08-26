package com.tripgain.collectionofpages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.tripgain.common.Log;
import com.tripgain.common.ScreenShots;

public class SkyTravelers_Hotels_Login {
	WebDriver driver;

	public SkyTravelers_Hotels_Login (WebDriver driver)
	{
		PageFactory.initElements(driver, this);
		this.driver=driver;
	}

	@FindBy(xpath = "//label[text()='User ID']/following-sibling::input")
	public WebElement SkyTravelersUserName;

	@FindBy(xpath = "//input[@name='agent_password']") 
	public WebElement SkyTravellersPassword;

	@FindBy(xpath = "//button[text()='Sign In']")
	public WebElement SignInbutton;	


	//Method to Enter UserName
	public void enterUserName(String userName)
	{
		SkyTravelersUserName.sendKeys(userName);
	}

	//Method to Enter Password
	public void enterPasswordName(String password)
	{
		SkyTravellersPassword.sendKeys(password);
	}

	//Method to Click on Login Button
	public void clickButton() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
		wait.until(ExpectedConditions.elementToBeClickable(SignInbutton));
		SignInbutton.click();
     

	}
	
	
	//Method to Verify Home Page is Displayed
    public void verifyHomePageIsDisplayed(Log Log,ScreenShots ScreenShots) throws InterruptedException {
        try {
            WebElement homePageLogo=driver.findElement(By.xpath("//span[text()='Flight']"));
            if(homePageLogo.isDisplayed())
            {
                Log.ReportEvent("PASS", "Home Page is displayed Successful");
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
    
   
 }
