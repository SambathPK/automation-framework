package Akku;

import Locators.Login;
import Utils.*;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;


public class LoginFlow extends Base {

    private final String tenantUrl;
    private final String username;
    private final String password;

    // ================= CONSTRUCTOR =================

    public LoginFlow(String tenantUrl, String username, String password) {
        this.tenantUrl = tenantUrl;
        this.username = username;
        this.password = password;
        System.out.println("CREATED INSTANCE FOR: " + tenantUrl + " | " + username);
    }

    // ================= FACTORY =================

    @Factory(dataProvider = "SmokeTest", dataProviderClass = TenantDataProvider.class)
    public static Object[] factory(String tenantUrl, String username, String password) {
        return new Object[]{new LoginFlow(tenantUrl, username, password)};
    }


    // ================= CONFIG =================

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
    public void login() throws InterruptedException {
        loginUser();
    }

    @Test(priority = 1, description = "Login attempt with invalid username")
    public void loginWithInvalidUsername() throws InterruptedException {
        attemptLoginWithInvalidUsername("TC_002");
    }

    @Test(priority = 2, description = "Login attempt with invalid password")
    public void loginWithInvalidPassword() throws InterruptedException {
        attemptLoginWithInvalidPassword();
    }

    // ================= HELPERS =================

    private void loginUser() throws InterruptedException {


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

    }

    private void attemptLoginWithInvalidUsername(String testCase) throws InterruptedException {

        enter(Login.username,
                ExcelDataReader.inputData("loginPage", "key", testCase, "workEmailId"));

        if (isTextPresentInMultipleElements(Login.defaultFlow, "Password")) {

            // Direct flow
            click(Login.signIn);
            assertEqualsnew(Login.defaultFlowInvalidUser, "Invalid user");

        } else {

            // Two-step flow
            staticWait(1500);
            click(Login.submit);
            assertEqualsnew(Login.invalidUsername, "Invalid user");
        }
    }

    private void attemptLoginWithInvalidPassword() throws InterruptedException {

        enter(Login.username, username);

        if (isTextPresentInMultipleElements(Login.defaultFlow, "Password")) {

            // Direct flow
            enter(Login.normalflowPassword,
                    ExcelDataReader.inputData("loginPage", "key", "TC_002", "password"));

            click(Login.signIn);
            assertEqualsnew(Login.defaultFlowInvalidUsernameorPassword, "Invalid username or password.");

        } else {

            // Two-step flow
            staticWait(1500);
            click(Login.submit);

            enter(Login.password,
                    ExcelDataReader.inputData("loginPage", "key", "TC_002", "password"));

            staticWait(1500);
            click(Login.signIn);
            assertEqualsnew(Login.invalidPassword, "Invalid password.");
        }
    }


}
