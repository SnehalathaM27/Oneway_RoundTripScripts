package com.tripgain.collectionofpages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Tripgain_FutureDates {
	WebDriver driver;

    // Constructor to initialize WebDriver
    public Tripgain_FutureDates(WebDriver driver) {
        this.driver = driver;
    }

    public static class DateResult {
        public String day;
        public String month;
        public String year;

        public DateResult(String day, String month, String year) {
            this.day = day;
            this.month = month;
            this.year = year;
        }

        @Override
        public String toString() {
            return day + "-" + month + "-" + year;
        }
    }

    public Map<String, DateResult> furtherDate() {
        WebElement dateElement = driver.findElement(By.xpath("//div[@class='react-datepicker__input-container']//input[@placeholder='Journey Date']"));
        String currentDateStr = dateElement.getAttribute("value"); // e.g., "30th-May-2025"

        // Clean suffixes: "th", "st", "nd", "rd"
        String cleanedDateStr = currentDateStr.replaceAll("(st|nd|rd|th)", "");
        String[] dateParts = cleanedDateStr.split("-");

        int day = Integer.parseInt(dateParts[0]);       // e.g., 30
        String monthStr = dateParts[1];                 // e.g., May
        int year = Integer.parseInt(dateParts[2]);      // e.g., 2025

        // Convert to LocalDate
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH);
        Month month = Month.from(monthFormatter.parse(monthStr));
        LocalDate currentDate = LocalDate.of(year, month, day);

        // Add 5 and 15 days
        LocalDate datePlus4 = currentDate.plusDays(4);
        LocalDate datePlus5 = currentDate.plusDays(5);
        LocalDate datePlus15 = currentDate.plusDays(15);
        

        // Prepare results as strings
        DateResult result4 = new DateResult(
                String.valueOf(datePlus4.getDayOfMonth()),
                datePlus4.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                String.valueOf(datePlus4.getYear())
            );

        
        
        DateResult result5 = new DateResult(
            String.valueOf(datePlus5.getDayOfMonth()),
            datePlus5.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
            String.valueOf(datePlus5.getYear())
        );

        DateResult result15 = new DateResult(
            String.valueOf(datePlus15.getDayOfMonth()),
            datePlus15.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
            String.valueOf(datePlus15.getYear())
        );
        

        Map<String, DateResult> resultMap = new HashMap<>();
        resultMap.put("datePlus4", result4);
        resultMap.put("datePlus5", result5);
        resultMap.put("datePlus15", result15);

        return resultMap;
    }


    
    
}
