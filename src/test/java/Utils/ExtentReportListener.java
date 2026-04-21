package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

public class ExtentReportListener implements ITestListener {

    // Define ExtentReports and ExtentTest instances
    private ExtentReports extent;
    private ExtentTest test;

    public ExtentReportListener() {
        // Specify the directory path where the HTML report should be generated
        String reportPath = "test-output/ExtentReport.html";
        // Create an ExtentHtmlReporter instance with the specified path
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(new File(reportPath));
        // Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    // This method executes before any test method starts
    public void onTestStart(ITestResult result) {
        // Initialize ExtentTest for this test method
        test = extent.createTest(result.getMethod().getMethodName());
    }

    // This method executes when any test method fails
    public void onTestFailure(ITestResult result) {
        // Log failure status in ExtentReports
        test.log(Status.FAIL, "Test Case Failed: " + result.getName());
    }

    // This method executes when any test method passes
    public void onTestSuccess(ITestResult result) {
        // Log success status in ExtentReports
        test.log(Status.PASS, "Test Case Passed: " + result.getName());
    }

    // This method executes when any test method is skipped
    public void onTestSkipped(ITestResult result) {
        // Log skipped status in ExtentReports
        test.log(Status.SKIP, "Test Case Skipped: " + result.getName());
    }

    // This method executes after all tests have finished
    public void onFinish(ITestContext context) {
        // Flush the ExtentReports instance
        extent.flush();
    }
}
