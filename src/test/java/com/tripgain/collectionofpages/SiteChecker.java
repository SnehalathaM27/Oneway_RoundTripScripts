package com.tripgain.collectionofpages;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class SiteChecker {
	WebDriver driver;

	public SiteChecker (WebDriver driver)
	{
		PageFactory.initElements(driver, this);
		this.driver=driver;
	}

    /**
     * Checks if the site is up by sending a HEAD request.
     * Retries every waitSeconds for maxAttempts times.
     * After the first wait, if still down, refreshes the page using the provided WebDriver.
     * Throws RuntimeException if site is down after all retries.
     *
     * @param driver       Selenium WebDriver to refresh the page
     * @param urlToCheck   URL to test
     * @param maxAttempts  Max retry attempts
     * @param waitSeconds  Seconds to wait between retries
     */
	public static void waitForSiteToBeUp(WebDriver driver, String urlToCheck, int maxAttempts, int waitSeconds) {
	    int attempts = 0;

	    while (attempts < maxAttempts) {
	        if (isSiteUp(urlToCheck)) {
	            System.out.println("Site is UP: " + urlToCheck);
	            return;
	        } else {
	            System.out.println(" Site is DOWN. Retry " + (attempts + 1) + " of " + maxAttempts +
	                               ". Waiting " + waitSeconds + " seconds...");

	            try {
	                Thread.sleep(waitSeconds * 1000L);
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	                throw new RuntimeException("Interrupted while waiting for site to be up.");
	            }

	            // Refresh the page before the next retry
	            System.out.println("Refreshing the page before retry " + (attempts + 2));
	            try {
	                driver.navigate().refresh();
	                System.out.println("Refresh Done");
	            } catch (Exception e) {
	                System.out.println("Failed to refresh the page: " + e.getMessage());
	                // You could choose to continue or fail here
	            }

	            attempts++;
	        }
	    }

	    throw new RuntimeException("Site is still down after " + maxAttempts + " retries.");
	}

    

    /**
     * Helper method to check if site is up by sending HEAD request.
     */
    private static boolean isSiteUp(String urlToCheck) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urlToCheck).openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int responseCode = connection.getResponseCode();
            return responseCode >= 200 && responseCode < 500;
        } catch (IOException e) {
            return false;
        }
    }
}
