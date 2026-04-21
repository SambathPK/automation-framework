package Akku;

import Locators.*;
import Utils.*;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;

public class Admin_ResetPassword extends Base {

    private final String tenantUrl;

    public Admin_ResetPassword(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new Admin_ResetPassword(tenantUrl)};
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

    @Test(priority = 0, description = "Verify that the admin can set a temporary password for a user")
    public static void verifyAdminCanSetTemporaryPassword() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(UserManagementPom.createUser.userManagementSection);
        staticWait(3000);
        findElementsAndClick(UserManagementPom.groups.usermanagementtablewords, ExcelDataReader.inputData("createUser", "key", "TC_002", "DisplayUsername"));
        staticWait(2000);
        jClick(PasswordManagerPom.resetPsswdBtn);
        staticWait(1500);
        enter(PasswordManagerPom.TempPsswd, ExcelDataReader.inputData("Admin_ResetPassword", "Key", "Value", "TemporaryPsswd"));
        click(RoleManagementPom.Update);
        assertEqualsnew(PasswordManagerPom.temporaryPasswordMessage, "User password updated successfully.");
    }

    @Test(priority = 1, description = "Verify that the user can reset their password after receiving a temporary password")
    public static void verifyUserCanResetPassword() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "Work_Email_Id"));
        staticWait(1500);
        click(RoleManagementPom.submit);
        enter(Login.password, ExcelDataReader.inputData("Admin_ResetPassword", "Key", "Value", "TemporaryPsswd"));
        staticWait(1500);
        click(RoleManagementPom.submit);
        staticWait(4000);
        enter(PasswordManagerPom.NewPsswd, ExcelDataReader.inputData("Admin_ResetPassword", "Key", "Value", "NewPsswd"));
        enter(PasswordManagerPom.ConfirmPsswd, ExcelDataReader.inputData("Admin_ResetPassword", "Key", "Value", "ConfirmPsswd"));
        click(PasswordManagerPom.Submit);
        staticWait(4000);
        jClick(UserManagementPom.Consent.Text);
        staticWait(1500);
        jClick(UserManagementPom.Consent.Proceed);
        staticWait(3000);
        assertTextContains(OtpValidationProcess.setpassword.displayNameAssertion, "Welcome");
    }

}