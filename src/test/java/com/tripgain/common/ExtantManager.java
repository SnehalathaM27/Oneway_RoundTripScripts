package com.tripgain.common;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtantManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final Object lock = new Object(); // For thread safety
    private static boolean isInitialized = false;
    private static final Set<String> browserNamesSet = new HashSet<>();




    public void setUpExtentReporter(String browserName) {
        synchronized (lock) {
            if (!isInitialized) {
                LocalDate today = LocalDate.now();
                LocalTime time = LocalTime.now();

                String reportPath = System.getProperty("user.dir") + File.separator +
                        "src" + File.separator + "test" + File.separator + "resources" +
                        File.separator + "Reports" + File.separator +
                        "myReport_" + today + "_" + time.toString().replace(".", "").replace(":", "-") + ".html";

                ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
                sparkReporter.config().setReportName("TripGain Automation Report");
                sparkReporter.config().setTheme(Theme.STANDARD);

                extent = new ExtentReports();
                extent.attachReporter(sparkReporter);
                extent.setSystemInfo("Environment", "QA");
                extent.setSystemInfo("Tester Name", "Snehalatha M");
                extent.setSystemInfo("Test URL", "https://v3.tripgain.com/login");

                try {
                    InetAddress localHost = InetAddress.getLocalHost();
                    extent.setSystemInfo("Machine Name", localHost.getHostName());
                } catch (UnknownHostException e) {
                    System.err.println("Unable to retrieve host name: " + e.getMessage());
                }

                extent.setSystemInfo("OS", System.getProperty("os.name"));
                isInitialized = true;
            }
        //   browserNamesSet.add(browserName);

        }
        

//     // Check if the new browser name is already in the browserNames string
//	    if (!browserNames.contains(browserName)) {
//	        // If browserNames is not empty, append a comma
//	        if (!browserNames.isEmpty()) {
//	            browserNames += ", "; 
//	        }
//	        // Add the new browser name
//	        browserNames += browserName; 
//	    }
//
//	    // Update the "Browser Name" in the system info
//	    extent.setSystemInfo("Browser Name", browserNames);


	}
    // This should be called ONCE after all tests are done (e.g., in @AfterSuite)
    public void finalizeExtentReport() {
        synchronized (lock) {
            if (extent != null && !browserNamesSet.isEmpty()) {
                String combinedBrowserNames = String.join(", ", browserNamesSet);
                extent.setSystemInfo("Browser Name", combinedBrowserNames);
            }
        }
    }
 // Call this to flush the report at the very end
    public void flushReport() {
        synchronized (lock) {
            if (extent != null) {
                extent.flush();
            }
        }
    }
    

    public void createTest(String testName) {
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public ExtentReports getReport() {
        return extent;
    }
}
