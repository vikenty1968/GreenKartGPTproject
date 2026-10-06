package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportsManager {
    private static ExtentReports extent;
    //Test are run  in parallel
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    public void initReport() {
        extent = new ExtentReports();
        ExtentSparkReporter reporter =
                new ExtentSparkReporter("reports/ExtentReport.html");

        extent.attachReporter(reporter);
    }
    // to add test name to the report
    public void createTest(String testName) {
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
    }
    public void testPassed() {
        test.get().pass("Test passed");
    }
    public void testFailed(Throwable error) {
        test.get().fail(error);
    }
    public void testSkipped(Throwable error) {
        if (error != null) {
            test.get().skip(error);
        } else {
            test.get().skip("Test skipped");
        }
    }
    //to check behavior of the skipped test - if this test record is in the report
    public boolean hasCurrentTest() {
        return test.get() != null;
    }
    //To Write down all in report
    public void flushReport() {
        extent.flush();
    }
    public void removeTest() {
        test.remove();
    }

}
