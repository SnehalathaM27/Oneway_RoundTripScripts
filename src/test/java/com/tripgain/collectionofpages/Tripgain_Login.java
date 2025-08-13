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

public class Tripgain_Login {
	WebDriver driver;

	public Tripgain_Login (WebDriver driver)
	{
		PageFactory.initElements(driver, this);
		this.driver=driver;
	}

	@FindBy(name="username")
	public WebElement tripGainUserName;

	@FindBy(xpath = "//input[@name='password']") 
	public WebElement tripGainPassword;

	@FindBy(xpath = "//button[@type='submit']")
	public WebElement button;	


	//Method to Enter UserName
	public void enterUserName(String userName)
	{
		tripGainUserName.sendKeys(userName);
	}

	//Method to Enter Password
	public void enterPasswordName(String password)
	{
		tripGainPassword.sendKeys(password);
	}

	//Method to Click on Login Button
	public void clickButton() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
		wait.until(ExpectedConditions.elementToBeClickable(button));
		button.click();
     

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
  //Method to Validate Password Error Message
  	public void verifyInvalidPasswordErrorMessage(Log Log,ScreenShots ScreenShots) throws InterruptedException {
  		try {
  			WebElement invalidPasswordErrorMessage =driver.findElement(By.xpath("//div[@aria-describedby='client-snackbar']"));
  			if(invalidPasswordErrorMessage.isDisplayed())
  			{
  				Log.ReportEvent("PASS", "Error message is displayed for invalid password is Successful");
  				ScreenShots.takeScreenShot1();
  			}
  			else
  			{
  				Log.ReportEvent("FAIL", "Error message is not displayed for invalid password");
  				ScreenShots.takeScreenShot1();
  				Assert.fail();

  			}
  		}
  		catch(Exception e)
  		{
  			Log.ReportEvent("FAIL", "Error message is not displayed for invalid password");
  			ScreenShots.takeScreenShot1();
  			e.printStackTrace();
  			Assert.fail();

  		}

}
}
