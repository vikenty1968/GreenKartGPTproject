package listeners;

import driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentReportsManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TestListeners implements ITestListener, ISuiteListener {
    private ExtentReportsManager reportManager = new ExtentReportsManager();
    @Override
    public void onTestFailure(ITestResult result) {
     try {
         reportManager.testFailed(result.getThrowable());
         WebDriver driver = DriverManager.getDriver();

         if (driver != null) {
             try {
                 File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                 System.out.println("Screen Shot");
                 Path screenShotDir = Paths.get("screenshots");
                 Files.createDirectories(screenShotDir);
                 // New screen everytime
//            Path destination = screenShotDir.resolve(
//                    result.getName() + "_" + System.currentTimeMillis() + ".png");
                 //   Files.copy(screenShot.toPath(),destination);
                 // Rewrite screenshot
                 Path destination = screenShotDir.resolve(result.getName() + ".png");

                 Files.copy(
                         screenShot.toPath(),
                         destination,
                         StandardCopyOption.REPLACE_EXISTING
                 );
             } catch (IOException e) {
                 System.out.println("Unable to take screenshot: " + e.getMessage());
             }
         }
     } finally {
         reportManager.removeTest();
     }
    }

    @Override

    public void onTestSkipped(ITestResult result) {
        try {
            if (!reportManager.hasCurrentTest()) {
                reportManager.createTest(result.getName());
            }

            reportManager.testSkipped(result.getThrowable());
            System.out.println("Test skipped " + result.getName());
        } finally {
            reportManager.removeTest();
        }
    }

//Before suite
    @Override
    public void onStart(ISuite suite) {
        reportManager.initReport();//create report
    }
    //after suite
    @Override
    public void onFinish(ISuite suite) {
        System.out.println("Suite finished  saving report");
        reportManager.flushReport();
        System.out.println(
                "Report path: " +
                        Paths.get("reports/ExtentReport.html").toAbsolutePath()
        );
    }
    //Before test
    @Override
    public void onTestStart(ITestResult result) {
        reportManager.createTest(result.getName());
    }
    @Override
    public void onTestSuccess(ITestResult result) {
      try{  reportManager.testPassed();
        System.out.println("Test passed: " + result.getName());
    }    finally {
          reportManager.removeTest();//to clean flow
      }
    }

}
