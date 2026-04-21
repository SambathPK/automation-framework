package Akku;

import Locators.Login;
import Locators.OtpValidationProcess;
import Locators.UserManagementPom;
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

public class Set_Password extends Base {

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

    @Test(description = "Verify the user is able set the password for newly created user with invalid personal email and OTP", groups = "Regression")
    public void setPasswordWithInvalidPersonalEmailAndOTP() throws InterruptedException {
        jClick(OtpValidationProcess.forgotUsername.next2);
        jClick(OtpValidationProcess.forgotUsername.personalEmail);
        jClick(OtpValidationProcess.forgotUsername.next2);
        staticWait(3000);
        enter(OtpValidationProcess.forgotUsername.otp, Utils.ExcelDataReader.inputData("otpValidationProcess", "setPassword", "Value", "InvalidOTP"));
        staticWait(3000);
        click(OtpValidationProcess.forgotpassword.submit);
        String expected = "Invalid OTP";
        assertEqualsnew(OtpValidationProcess.forgotUsername.OTPmessage, expected);

    }


    @Test(description = "Verify the user is able set the password for newly created user with invalid personal email and OTP", groups = "Regression")
    public void setPasswordWithInvalidCredentials() throws InterruptedException {
        jClick(OtpValidationProcess.forgotUsername.next2);
        jClick(OtpValidationProcess.forgotUsername.personalEmail);
        jClick(OtpValidationProcess.forgotUsername.next2);
        staticWait(2000);
        enterValidOTP();
        staticWait(3000);
        click(OtpValidationProcess.forgotpassword.submit);
        staticWait(2000);
        enter(OtpValidationProcess.setpassword.password1, Utils.ExcelDataReader.inputData("otpValidationProcess", "setPassword", "Value", "Password"));
        enter(OtpValidationProcess.setpassword.reEnterPassword, Utils.ExcelDataReader.inputData("otpValidationProcess", "setPassword", "InvalidValue", "ConfirmPassword"));
        staticWait(8000);
        click(OtpValidationProcess.forgotpassword.submit);
        String expected1 = "Passwords must match";
        assertEqualsnew(OtpValidationProcess.setpassword.InputError2, expected1);
    }


    @Test(description = "Verify the user is able set the password for newly created user with valid credentials")
    public void setPasswordWithValidCredentials() throws InterruptedException {
        jClick(OtpValidationProcess.forgotUsername.next2);
        jClick(OtpValidationProcess.forgotUsername.personalEmail);
        jClick(OtpValidationProcess.forgotUsername.next2);
        staticWait(2000);
        enterValidOTP();
        staticWait(3000);
        click(OtpValidationProcess.forgotpassword.submit);
        staticWait(2000);
        enter(OtpValidationProcess.setpassword.password1, Utils.ExcelDataReader.inputData("otpValidationProcess", "setPassword", "Value", "Password"));
        enter(OtpValidationProcess.setpassword.reEnterPassword, Utils.ExcelDataReader.inputData("otpValidationProcess", "setPassword", "Value", "ConfirmPassword"));
        staticWait(5000);
        click(OtpValidationProcess.forgotpassword.submit);
        staticWait(12000);
        enter(Login.username, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "workEmailId"));
        staticWait(3000);
        click(OtpValidationProcess.forgotpassword.signIn);
        enter(Login.password, Utils.ExcelDataReader.inputData("otpValidationProcess", "setPassword", "Value", "Password"));
        click(OtpValidationProcess.forgotpassword.login);
        staticWait(10000);
        click(UserManagementPom.Consent.Text);
        staticWait(2000);
        click(UserManagementPom.Consent.Proceed);
        staticWait(3000);
        assertEqualsnew(OtpValidationProcess.setpassword.displayNameAssertion, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "DisplayUsername"));
    }

    private void enterValidOTP() {
        String validPersonalEmail = Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "personalEmail");
        String otp = getOTPFromDatabase(validPersonalEmail);
        enter(OtpValidationProcess.forgotUsername.otp, otp);
    }

    private void enterInvalidOTP() {
        String validPersonalEmail = Utils.ExcelDataReader.inputData("otpValidationProcess", "setPassword", "InvalidValue", "SpPersonalEmail");
        String otp = getOTPFromDatabase(validPersonalEmail);
        enter(OtpValidationProcess.forgotUsername.otp, otp);
    }
}
