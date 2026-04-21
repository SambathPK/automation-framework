package Akku;

import Locators.OtpValidationProcess;
import Utils.Base;
import Utils.ExtentManager;
import Utils.TenantResult;
import Utils.TestContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;

public class ForgotUsername extends Base {

    @BeforeSuite
    public void beforeSuite() throws IOException {
        initialiseExtentReportsSuite();
    }

    @BeforeMethod
    public void beforeMethod(ITestResult result, ITestContext context, Object[] testData) throws Exception {
        String tenantUrl = (String) testData[0];
        TestContext.setTenant(tenantUrl);

        launchUrl("chrome", tenantUrl);

        // Assign tenant-specific ExtentReports to class-level variable

        extentReports = ExtentManager.getExtent(tenantUrl);

        // Create ExtentTest entry for this test
        initialiseExtentReportsMethodNew(result, context);
    }




    @AfterMethod
    public void afterMethod(ITestResult result) {

        String tenantUrl = TestContext.getTenant();
        boolean pass = result.getStatus() == ITestResult.SUCCESS;

        if (tenantUrl != null) {
            TenantResult.results.merge(tenantUrl, pass, (oldVal, newVal) -> oldVal && newVal);
        }

        // Flush ExtentTest logs to tenant-specific report
        extentReportAfterMethod(result);

        tearDown();
        TestContext.clearTenant();
    }

    @AfterSuite
    public void writeTenantSummary() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("target/tenant-summary.json"), TenantResult.results);

        // Flush all tenant reports just in case
        ExtentManager.flushAll();
    }

    @Test(description = "Ensure that the user is able to retrieve their username with a valid OTP (One-Time Password)")
    public void RetrieveUsernameWithValidOTP() throws InterruptedException, IOException, UnsupportedFlavorException {
        navigateToForgotUsername();
        enterValidPersonalEmail();
        Thread.sleep(3000);
        enterValidOTP();
        staticWait(2000);
        click(OtpValidationProcess.forgotpassword.submit);
        assertTextContains(OtpValidationProcess.forgotUsername.copyButton, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "workEmailId"));
    }

    @Test(description = "Ensure that the user is unable to retrieve their username with an invalid OTP (One-Time Password)", groups = "Regression")
    public void RetrieveUsernameWithInvalidOTP() throws InterruptedException {
        navigateToForgotUsername();
        enterValidPersonalEmail();
        enterInvalidOTP();
        click(OtpValidationProcess.forgotpassword.submit);
        validateInvalidOTPErrorMessage();

        String expected1 = "Invalid OTP";
        staticWait(3000);
        assertEqualsnew(OtpValidationProcess.forgotUsername.OTPmessage, expected1);

    }

    @Test(description = "Ensure that the user is unable to retrieve their username with an invalid personal email address", groups = "Regression")
    public void RetrieveUsernameWithInvalidPersonalEmailID() throws InterruptedException {
        navigateToForgotUsername();
        enterInvalidPersonalEmail();
        staticWait(3000);
        validateInvalidPersonalEmailErrorMessage();


        String expected2 = "Please enter registered email.";
        //staticWait(3000);
        assertEqualsnew(OtpValidationProcess.forgotUsername.EmailMessage, expected2);

    }

    // Reusable methods

    private void navigateToForgotUsername() {
        ImplicitlyWait();
        click(OtpValidationProcess.forgotUsername.forgotusername);
        jClick(OtpValidationProcess.forgotUsername.personalEmail);
        jClick(OtpValidationProcess.forgotUsername.next2);
    }

    private void enterValidPersonalEmail() {
        String validPersonalEmail = Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "personalEmail");
        enter(OtpValidationProcess.forgotUsername.enterpersonalemail, validPersonalEmail);
        jClick(OtpValidationProcess.forgotUsername.next2);
    }

    private void enterInvalidPersonalEmail() {
        String invalidPersonalEmail = Utils.ExcelDataReader.inputData("otpValidationProcess", "key", "Invalid", "PersonalEmail");
        enter(OtpValidationProcess.forgotUsername.enterpersonalemail, invalidPersonalEmail);
        jClick(OtpValidationProcess.forgotUsername.next2);
    }

    private void enterValidOTP() {
        String validPersonalEmail = Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "personalEmail");
        String otp = getOTPFromDatabase(validPersonalEmail);
        enter(OtpValidationProcess.forgotUsername.otp, otp);
    }

    private void enterInvalidOTP() {
        String invalidOTP = Utils.ExcelDataReader.inputData("otpValidationProcess", "key", "Invalid", "OTP");
        enter(OtpValidationProcess.forgotUsername.otp, invalidOTP);
    }

    private void validateRetrievedUsername() {
        String expectedUsername = Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "workEmailId");
        validateText(OtpValidationProcess.forgotUsername.retrieveUsername, expectedUsername);
    }

    private void validateInvalidOTPErrorMessage() {
        String expectedErrorMessage = "Invalid OTP";
        assertEquals(getText(OtpValidationProcess.forgotUsername.invalidOtp), expectedErrorMessage);
    }

    private void validateInvalidPersonalEmailErrorMessage() {
        ImplicitlyWait();
        String expectedErrorMessage = "Please enter registered email.";
        validateText(OtpValidationProcess.forgotUsername.invalidPersonalEmail, expectedErrorMessage);
    }


}
