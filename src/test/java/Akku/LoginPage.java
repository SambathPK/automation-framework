package Akku;

import Locators.Login;
import Utils.*;
import com.aventstack.extentreports.ExtentReports;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class LoginPage extends Base {

    private final String tenantUrl;

    public LoginPage(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public static Object[] factory(String tenantUrl) {
        return new Object[]{new LoginPage(tenantUrl)};
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

    // ================= TESTS =================

    @Test(priority = 0, description = "Login with valid credentials")
    public void Login() throws InterruptedException {
        loginUser("TC_001");
    }

    @Test(priority = 1, description = "Login attempt with invalid username")
    public void loginWithInvalidUsername() throws InterruptedException {
        attemptLoginWithInvalidUsername("TC_002");
    }

    @Test(priority = 2, description = "Login attempt with invalid password")
    public void loginWithInvalidPassword() throws InterruptedException {
        attemptLoginWithInvalidPassword("TC_001", "TC_002");
    }

    // ================= HELPERS =================

    private void loginUser(String testCase) throws InterruptedException {
        enter(Login.username, ExcelDataReader.inputData("loginPage", "key", testCase, "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, ExcelDataReader.inputData("loginPage", "key", testCase, "password"));
        staticWait(1500);
        click(Login.signIn);
    }

    private void attemptLoginWithInvalidUsername(String testCase) throws InterruptedException {
        enter(Login.username, ExcelDataReader.inputData("loginPage", "key", testCase, "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        assertEqualsnew(Login.invalidUsername, "Invalid user");
    }

    private void attemptLoginWithInvalidPassword(String validUser, String invalidPwd) throws InterruptedException {
        enter(Login.username, ExcelDataReader.inputData("loginPage", "key", validUser, "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, ExcelDataReader.inputData("loginPage", "key", invalidPwd, "password"));
        staticWait(1500);
        click(Login.signIn);
        assertEqualsnew(Login.invalidPassword, "Invalid password.");
    }


    @Test(enabled = false)
    public static void loginnormalbrowserflow() throws InterruptedException {


        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));

        enter(Login.normalflowPassword, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        click(Login.signIn);


    }


}
