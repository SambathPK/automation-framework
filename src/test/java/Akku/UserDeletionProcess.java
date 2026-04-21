package Akku;

import Locators.App_Management_Pom;
import Locators.Login;
import Locators.ProvisioningPom;
import Locators.UserManagementPom;
import Utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

import static Locators.App_Management_Pom.addApps.*;
import static Locators.UserManagementPom.createUser.*;

public class UserDeletionProcess extends Base {

    private final String tenantUrl;

    public UserDeletionProcess(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new UserDeletionProcess(tenantUrl)};
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

    @Test(priority = 0, description = "Verify that a user can be deleted successfully")
    public void testDeleteUser() throws InterruptedException {
        loginAndNavigateToUserManagement();
        deleteUser("TC_001");
        assertEqualsnew(userEnableAndDisableToastMessage, "User deleted successfully.");
    }

    @Test(priority = 1, description = "Verify that the GWS Tenant Provisioning Connector and SSO configuration is deleted successfully")
    public static void deleteGwsTenantProvisioningConnector() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(App_Management_Pom.addApps.appStore);
        staticWait(6000);

        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, deleteButton, Utils.ExcelDataReader.inputData("appstore", "key", "TC_003", "appName"));
        click(ProvisioningPom.deleteYesButton);
        assertEqualsnew(DeletedNotification, "App deleted successfully.");
    }


    private static void loginAndNavigateToUserManagement() throws InterruptedException {
        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(10000);
        jClick(UserManagementPom.createUser.userManagementSection);
        staticWait(10000);
    }

    private static void deleteUser(String testCase) throws InterruptedException {

        getTableDetails(tableId, checkBox, ExcelDataReader.inputData("createUser", "key", testCase, "Work_Email_Id"));
        getTableDetails(tableId, checkBox, "csvone@cloudnowtech.com");
        staticWait(2000);
        click(UserDeleteIcon);
        staticWait(1000);
        click(UsersConfirmDeleteIcon);
    }
}
