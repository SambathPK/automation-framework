package Akku;

import Locators.Login;
import Utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Admin_Dashboard extends Base {

    private final String tenantUrl;
    private final String username;
    private final String password;

    public Admin_Dashboard(String tenantUrl, String username, String password) {
        this.tenantUrl = tenantUrl;
        this.username = username;
        this.password = password;
    }

    @Factory(dataProvider = "SmokeTest", dataProviderClass = TenantDataProvider.class)
    public static Object[] factory(String tenantUrl, String username, String password) {
        return new Object[]{new Admin_Dashboard(tenantUrl, username, password)};
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
    public void SuspiciousLogin() throws InterruptedException {

        if (isTextPresentInMultipleElements(Login.defaultFlow, "Password")) {

            enter(Login.username, username);
            enter(Login.normalflowPassword, password);
            click(Login.signIn);

        } else {

            enter(Login.username, username);
            staticWait(1500);
            click(Login.submit);
            enter(Login.password, password);
            staticWait(1500);
            click(Login.signIn);
        }
        click(Login.suspiciousLogin);
        staticWait(3000);
        assertEqualsnew(Login.suspiciousLogin1, "Suspicious Logins");
    }

    @Test(priority = 2, description = "Verify that the Failed Login section redirects to the Risk Assessment page")
    public void FailedLogin() throws InterruptedException {

        if (isTextPresentInMultipleElements(Login.defaultFlow, "Password")) {

            enter(Login.username, username);
            enter(Login.normalflowPassword, password);
            click(Login.signIn);

        } else {

            enter(Login.username, username);
            staticWait(1500);
            click(Login.submit);
            enter(Login.password, password);
            staticWait(1500);
            click(Login.signIn);
        }
        click(Login.FailedLogin);
        staticWait(3000);
        assertEqualsnew(Login.FailedLogin1, "Failed Logins");
        assertContainsText(Login.suspiciousLoginData);

    }

//    @Test(priority = 3, description = "Verify that the Locked Accounts section redirects to the Risk Assessment page")
//    public void LockedAccounts() throws InterruptedException {
//
//        if (isTextPresentInMultipleElements(Login.defaultFlow, "Password")) {
//
//            enter(Login.username, username);
//            enter(Login.normalflowPassword, password);
//            click(Login.signIn);
//
//        } else {
//
//            enter(Login.username, username);
//            staticWait(1500);
//            click(Login.submit);
//            enter(Login.password, password);
//            staticWait(1500);
//            click(Login.signIn);
//        }
//        click(Login.LockedAccounts);
//        staticWait(3000);
//        assertTextContains(Login.LockedAccounts1, "Locked Accounts");
//
//    }
//
//    @Test(priority = 4, description = "Verify that the Quarantined Logins section redirects to the Risk Assessment page")
//    public void QuarantinedLogins() throws InterruptedException {
//
//        if (isTextPresentInMultipleElements(Login.defaultFlow, "Password")) {
//
//            enter(Login.username, username);
//            enter(Login.normalflowPassword, password);
//            click(Login.signIn);
//
//        } else {
//
//            enter(Login.username, username);
//            staticWait(1500);
//            click(Login.submit);
//            enter(Login.password, password);
//            staticWait(1500);
//            click(Login.signIn);
//        }
//        click(Login.QuarantinedLogins);
//        staticWait(3000);
//        assertTextContains(Login.QuarantinedLogins1, "Quarantined Logins");
//
//    }

//    @Test(priority = 5, description = "Verify that the Successful Attempts in Recovery Attempts redirects to the required Recovery Attempts Page")
//    public static void SuccessfulAttempts() throws InterruptedException {
//
//        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
//        staticWait(1500);
//        click(Login.submit);
//        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
//        staticWait(1500);
//        click(Login.signIn);
//        click(Login.SuccessfulAttempts);
//        staticWait(3000);
//        assertTextContains(Login.SuccessfulAttempts1, "Successful Recovery");
//
//    }
//
//    @Test(priority = 6, description = "Verify that the Failure in Recovery Attempts redirects to the required Recovery Attempts Page")
//    public static void FailedAttempts() throws InterruptedException {
//
//        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
//        staticWait(1500);
//        click(Login.submit);
//        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
//        staticWait(1500);
//        click(Login.signIn);
//        click(Login.FailedAttempts);
//        staticWait(3000);
//        assertTextContains(Login.FailedAttempts1, "Failed Recovery");
//
//    }

//    @Test( priority = 7, description = "Verify that Products Section has Cloud Directory Product displayed in it")
//    public static void CloudDirectoryProduct(String tenantUrl) throws InterruptedException {
//
//        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
//        staticWait(1500);
//        click(Login.submit);
//        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
//        staticWait(1500);
//        click(Login.signIn);
//        assertTextContains(Login.CloudDirectoryProductText, "Cloud Directory");
//
//    }
//
//    @Test( priority = 8, description = " Verify that Products Section has SSO & IDP Product displayed in it")
//    public static void SSOProduct(String tenantUrl) throws InterruptedException {
//
//        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
//        staticWait(1500);
//        click(Login.submit);
//        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
//        staticWait(1500);
//        click(Login.signIn);
//        assertTextContains(Login.SSOProductText, "SSO & IDP");
//
//    }
//
//    @Test( priority = 9, description = "Verify that Products Section has Support Product displayed in it")
//    public static void SupportProduct(String tenantUrl) throws InterruptedException {
//
//        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
//        staticWait(1500);
//        click(Login.submit);
//        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
//        staticWait(1500);
//        click(Login.signIn);
//        scrollByElement(Login.SupportProductText);
//        assertTextContains(Login.SupportProductText, "Support");
//    }
}
