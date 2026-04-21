package Akku;

import Locators.Login;
import Locators.OtpValidationProcess;
import Utils.Base;
import Utils.ExtentManager;
import Utils.TenantResult;
import Utils.TestContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class ForgotPassword extends Base {

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

    @Test(description = "Ensure that the user is able change the password with invalid username", groups = "Regression")
    public void resetPasswordWithInvalidUsername() throws InterruptedException {

        enter(OtpValidationProcess.forgotpassword.enterUserName, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "workEmailId"));
        staticWait(3000);
        click(OtpValidationProcess.forgotpassword.signIn);
        click(OtpValidationProcess.forgotpassword.forgotPassword);
        staticWait(2000);
        deleteAll(Login.forgotpasswordUsername);
        enter(Login.forgotpasswordUsername, Utils.ExcelDataReader.inputData("otpValidationProcess", "key", "Valid", "InvalidUsername"));
        click(OtpValidationProcess.forgotpassword.nextButton);
        String expected = "User not found.";
        staticWait(5000);
        assertEqualsnew(Login.userNotFoundmessage, expected);
    }

    @Test(description = "Ensure that the user is able to reset their password with invalid email id and OTP", groups = "Regression")
    public void resetPasswordWithInvalidPersonalEmailAndOTP() throws InterruptedException {
        enter(OtpValidationProcess.forgotpassword.enterUserName, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "workEmailId"));
        click(OtpValidationProcess.forgotpassword.signIn);
        click(OtpValidationProcess.forgotpassword.forgotPassword);
        jClick(OtpValidationProcess.forgotUsername.next2);
        jClick(OtpValidationProcess.forgotUsername.personalEmail);
        jClick(OtpValidationProcess.forgotUsername.next2);
        staticWait(2000);
        enter(OtpValidationProcess.forgotUsername.otp, Utils.ExcelDataReader.inputData("otpValidationProcess", "key", "Invalid", "OTP"));
        click(OtpValidationProcess.forgotpassword.submit);
        String expected1 = "Invalid OTP";
        staticWait(2000);
        assertEquals(getText(OtpValidationProcess.forgotUsername.OTPmessage), expected1);
        String data1 = getText(OtpValidationProcess.forgotUsername.OTPmessage);
        System.out.println("Entered invalid Personal email & OTP to change the password" + " : " + data1);

    }


    @Test(description = "Ensure that the user is able to reset their password successfully, meeting the policy requirements")
    public void resetPasswordMeetingPolicyRequirements() throws InterruptedException {

        enter(OtpValidationProcess.forgotpassword.enterUserName, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        click(OtpValidationProcess.forgotpassword.signIn);
        click(OtpValidationProcess.forgotpassword.forgotPassword);
        jClick(OtpValidationProcess.forgotUsername.next2);
        jClick(OtpValidationProcess.forgotUsername.personalEmail);
        jClick(OtpValidationProcess.forgotUsername.next2);
        staticWait(2000);
        enterValidOTP();
        click(OtpValidationProcess.forgotpassword.submit);
        staticWait(2000);
        enter(OtpValidationProcess.setpassword.password1, Utils.ExcelDataReader.inputData("otpValidationProcess", "key", "Valid", "Password"));
        enter(OtpValidationProcess.setpassword.reEnterPassword, Utils.ExcelDataReader.inputData("otpValidationProcess", "key", "Valid", "ConfirmPassword"));
        staticWait(3000);
        click(OtpValidationProcess.forgotpassword.submit);
        enter(OtpValidationProcess.forgotpassword.enterUserName, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "workEmailId"));
        click(OtpValidationProcess.forgotpassword.signIn);
        enter(OtpValidationProcess.setpassword.password1, Utils.ExcelDataReader.inputData("OtpValidationProcess", "key", "Valid", "Password"));
        click(OtpValidationProcess.forgotpassword.login);

    }

    private void enterValidOTP() {
        String validPersonalEmail = Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "personalEmail");
        String otp = getOTPFromDatabase(validPersonalEmail);
        enter(OtpValidationProcess.forgotUsername.otp, otp);
    }

    private void enterInvalidOTP() {
        String validPersonalEmail = Utils.ExcelDataReader.inputData("otpValidationProcess", "key", "Invalid", "PersonalEmail");
        String otp = getOTPFromDatabase(validPersonalEmail);
        enter(OtpValidationProcess.forgotUsername.otp, otp);
    }

}