package Akku;

import Locators.Audit_Logs_Pom;
import Locators.Login;
import Utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.devtools.v85.backgroundservice.BackgroundService;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

import static org.testng.internal.MethodHelper.isEnabled;

public class Dashboard extends Base {

    private final String tenantUrl;

    public Dashboard(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new Dashboard(tenantUrl)};
    }

    @BeforeSuite
    public void beforeSuite() throws IOException {
        initialiseExtentReportsSuite();
        TenantResult.startSuite();
    }

    @BeforeMethod
    public void beforeMethod(ITestResult result, ITestContext context) throws Exception {

        TestContext.setTenant(tenantUrl);
        result.setAttribute("startTimeMillis", System.currentTimeMillis());

        launchUrl("chrome", tenantUrl);
        extentReports = ExtentManager.getExtent(tenantUrl);

        initialiseExtentReportsMethodNew(result, context);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {

        String tenantUrl = TestContext.getTenant();

        Long startTime = (Long) result.getAttribute("startTimeMillis");
        long endTime = System.currentTimeMillis();

        if (startTime != null) {
            TenantResult.addExecutionTime(tenantUrl, endTime - startTime);
        }

        boolean pass = result.getStatus() == ITestResult.SUCCESS;
        TenantResult.results.merge(tenantUrl, pass, (o, n) -> o && n);

        extentReportAfterMethod(result);
        tearDown();
        TestContext.clearTenant();
    }

    @AfterSuite
    public void afterSuite() throws Exception {
        TenantResult.endSuite();
        generateTenantSummaryHtml();
    }

    @Test(priority = 1, description = "Verify that the Suspicious Login section redirects to the Risk Assessment page")
    public static void SuspiciousLogin() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        click(Login.suspiciousLogin);
        staticWait(3000);
        assertEqualsnew(Login.suspiciousLogin1, "Suspicious Logins");
        assertContainsText(Login.suspiciousLoginData);
    }

    @Test(priority = 2, description = "Verify that the Failed Login section redirects to the Risk Assessment page")
    public static void FailedLogin() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        click(Login.FailedLogin);
        staticWait(3000);
        assertEqualsnew(Login.FailedLogin1, "Failed Logins");
        assertContainsText(Login.suspiciousLoginData);

    }

    @Test(priority = 3, description = "Verify that the Locked Accounts section redirects to the Risk Assessment page")
    public static void LockedAccounts() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        click(Login.LockedAccounts);
        staticWait(3000);
        assertTextContains(Login.LockedAccounts1, "Locked Accounts");
        assertContainsText(Login.suspiciousLoginData);

    }

    @Test(priority = 4, description = "Verify that the Quarantined Logins section redirects to the Risk Assessment page")
    public static void QuarantinedLogins() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        click(Login.QuarantinedLogins);
        staticWait(3000);
        assertTextContains(Login.QuarantinedLogins1, "Quarantined Logins");
//        assertContainsText(Login.suspiciousLoginData);

    }

    @Test(priority = 5, description = "Verify that the Add more licenses in the License Management Section redirects to the Pricing Page (Upgrade Plan page)")
    public static void AddMoreLicenses() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        click(Login.AddMoreLicenses);
        staticWait(3000);
        assertTextContains(Login.UpgradePlan1, "Pricing");
        enter(Login.enterLicenses, "2000");
        click(Login.requestLicenses);
        click(Login.licenseConfirm);
        assertEqualsnew(Login.thankYouAddingLicense, "Thank you for Adding more licenses");

    }

//    @Test(priority = 6, description = "Verify that the Upgrade Plan in the License Management Section redirects to the Pricing Page (Upgrade Plan page) if in Starter & Standard Plans and is disabled if in Advanced Plan")
//    public static void UpgradePlan() throws InterruptedException {
//
//        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
//        staticWait(1500);
//        click(Login.submit);
//        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
//        staticWait(1500);
//        click(Login.signIn);
//        staticWait(8000);
//        if (isEnabled((ITestOrConfiguration) Login.UpgradePlan)) {
//            click(Login.UpgradePlan);
//            staticWait(3000);
//            assertTextContains(Login.UpgradePlan1, "Pricing");
//
//        }
//        //click(Login.UpgradePlan);
//        //staticWait(3000);
//        //assertTextContains(Login.UpgradePlan1, "Pricing");
//    }

    @Test(priority = 7, description = "Verify that the Successful Attempts in Recovery Attempts redirects to the required Recovery Attempts Page")
    public static void SuccessfulAttempts() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        click(Login.SuccessfulAttempts);
        staticWait(3000);
        assertTextContains(Login.SuccessfulAttempts1, "Successful Recovery");
        staticWait(3000);
        assertContainsText(Login.SuccessfulAttemptsData);

    }

    @Test(priority = 8, description = "Verify that the Failure in Recovery Attempts redirects to the required Recovery Attempts Page")
    public static void FailedAttempts() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        click(Login.FailedAttempts);
        staticWait(3000);
        assertTextContains(Login.FailedAttempts1, "Failed Recovery");
        staticWait(3000);
        assertContainsText(Login.FailedAttemptsData);

    }

    @Test(priority = 9, description = "Verify that Products Section has Cloud Directory Product displayed in it")
    public static void CloudDirectoryProduct() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        assertTextContains(Login.CloudDirectoryProductText, "Cloud Directory");

    }

    @Test(priority = 10, description = " Verify that Products Section has SSO & IDP Product displayed in it")
    public static void SSOProduct() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        assertTextContains(Login.SSOProductText, "SSO & IDP");

    }

    @Test(priority = 11, description = "Verify that Products Section has Adaptive MFA Product displayed in it")
    public static void AdaptiveMFAProduct() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        assertTextContains(Login.AdaptiveMFAProductText, "Adaptive MFA");

    }

    @Test(priority = 12, description = "Verify that Products Section has Password Manager Product displayed in it")
    public static void PasswordManagerProduct() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        assertTextContains(Login.PasswordManagerProductText, "Password Manager");

    }

    @Test(priority = 13, description = "Verify that Products Section has Access Manager Product displayed in it")
    public static void AccessManagerProduct() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        scrollByElement(Login.AccessManagerProductText);
        assertTextContains(Login.AccessManagerProductText, "Access Manager");
    }

    @Test(priority = 14, description = "Verify that Products Section has Support Product displayed in it")
    public static void SupportProduct() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        scrollByElement(Login.SupportProductText);
        assertTextContains(Login.SupportProductText, "Support");
    }

    @Test(priority = 15, description = "Verify that Products Section has MDM Product displayed in it")
    public static void MDMProduct() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        scrollByElement(Login.MDMProductText);
        assertTextContains(Login.MDMProductText, "MDM");
    }

    @Test(priority = 16, description = "Verify that Products Section has User LifeCycle Manager")
    public static void UserLifecycleProduct() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        scrollByElement(Login.UserLifecycleProductText);
        assertTextContains(Login.UserLifecycleProductText, "User Lifecycle Manager");
    }

//    @Test(priority = 17, description = "Making an User to fail login, to verify that an User in Failed Login can be blocked")
//    public static void BlockFailedLoginUser() throws InterruptedException {
//
//        enter(Login.username, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "workEmailId"));
//        staticWait(1500);
//        click(Login.submit);
//        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_002", "password"));
//        staticWait(1500);
//        click(Login.signIn);
//        assertEqualsnew(Login.InvalidPasswordText, "Invalid password.");
//
//    }
//
//    @Test(priority = 18, description = "Verify that an User in Failed Login can be blocked")
//    public static void BlockFailedLoginUserOne() throws InterruptedException {
//
//        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
//        staticWait(1500);
//        click(Login.submit);
//        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
//        staticWait(1500);
//        click(Login.signIn);
//        click(Login.FailedLogin);
//        staticWait(4000);
//        assertEqualsnew(Login.FailedLogin1, "Failed Logins");
//        staticWait(5000);
//        // getTableDetails(Login.FailedLoginTable, Login.BlockUserInFailedLogin, "pending");
//        jClick(Login.BlockUserInFailedLogin);
//        staticWait(2000);
//        click(Login.BlockConfirmation);
//        assertEqualsnew(Login.blockedMessage, "User blocked successfully.");
//    }
//
//    @Test(
//            priority = 20,
//            description = "Make a user fail login to verify that a failed-login user can be triggered for password reset"
//    )
//    public static void blockFailedLoginUserForResetPassword() throws InterruptedException {
//
//        enter(Login.username, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "workEmailId"));
//        staticWait(1500);
//        click(Login.submit);
//        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_002", "password"));
//        staticWait(1500);
//        click(Login.signIn);
//        assertEqualsnew(Login.InvalidPasswordText, "Invalid password.");
//
//    }
//
//    @Test(priority = 21, description = "Verify that the admin can Reset Password for an User in Failed Login")
//    public static void ResetPasswordForFailedLoginUser() throws InterruptedException {
//
//        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
//        staticWait(1500);
//        click(Login.submit);
//        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
//        staticWait(1500);
//        click(Login.signIn);
//        click(Login.FailedLogin);
//        staticWait(3000);
//        assertEqualsnew(Login.FailedLogin1, "Failed Logins");
//        staticWait(5000);
//        //getTableDetails(Login.FailedLoginTable, Login.ResetPasswordInFailedLogin, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "workEmailId"));
//        jClick(Login.ResetPasswordInFailedLogin);
//        staticWait(3000);
//        enter(Login.ResetPasswordField, ExcelDataReader.inputData("Admin_ResetPassword", "Key", "Value", "TemporaryPsswd"));
//        staticWait(2000);
//        click(Login.UpdateResetPassword);
//        assertEqualsnew(Login.resetPasswordMessage, "Temporary password set successfully.");
//    }
//
//    @Test(priority = 19, description = "Verify that an admin can Unblock an User in Locked Accounts")
//    public static void UnblockLockedUser() throws InterruptedException {
//
//        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
//        staticWait(1500);
//        click(Login.submit);
//        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
//        staticWait(1500);
//        click(Login.signIn);
//        click(Login.LockedAccounts);
//        staticWait(3000);
//        assertTextContains(Login.LockedAccounts1, "Locked Accounts");
//        staticWait(3000);
//        getTableDetailsAndJsClick(Login.FailedLoginTable, Login.UnblockInLockedAccounts, "0");
//        staticWait(3000);
//        click(Login.BlockConfirmation);
//        assertEqualsnew(Login.unblockedMessage, "User status updated successfully.");
//    }
//
//    @Test(priority = 22, description = "Verify the User can Reset their Password after receiving the Temporary Password")
//    public static void UserResetPassword() throws InterruptedException {
//
//        enter(Login.UsernameField, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "workEmailId"));
//        staticWait(1500);
//        click(Login.NextButton);
//        staticWait(1500);
//        enter(Login.PasswordField, ExcelDataReader.inputData("Admin_ResetPassword", "Key", "Value", "TemporaryPsswd"));
//        staticWait(1500);
//        click(Login.SignInButton);
//        staticWait(4000);
//        enter(Login.NewPsswd, ExcelDataReader.inputData("Admin_ResetPassword", "Key", "Value", "ConfirmPsswd"));
//        enter(Login.ConfirmPsswd, ExcelDataReader.inputData("Admin_ResetPassword", "Key", "Value", "ConfirmPsswd"));
//        click(Login.ResetSubmitButton);
//        staticWait(6000);
//        assertContainsText(Login.welcomeBack);
//    }
}
