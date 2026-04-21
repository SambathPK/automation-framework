package Akku;

import Locators.Login;
import Locators.OtpValidationProcess;
import Locators.UserManagementPom;
import Utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

import static Locators.UserManagementPom.createUser.createdUserToaster;
import static Locators.UserManagementPom.createUser.userEnableAndDisableToastMessage;

public class Access_Manager extends Base {


    private final String tenantUrl;

    public Access_Manager(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new Access_Manager(tenantUrl)};
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

    public void navigateToAccessManagersection() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(Locators.Access_Manager.IP_Restriction.accessManagerSection);
    }

    @Test(description = "Verify that an admin user can successfully configure IP restrictions at the organization level.", priority = 0)
    public void verifyAdminCanConfigureIpRestrictionsForOrganization() throws InterruptedException {

        navigateToAccessManagersection();
        staticWait(6000);
        actionClick(Locators.Access_Manager.IP_Restriction.createNew);
        click(Locators.Access_Manager.IP_Restriction.ipOption);
        enter(Locators.Access_Manager.IP_Restriction.name, Utils.ExcelDataReader.inputData("accessManager", "key", "TC_001", "name"));
        enter(Locators.Access_Manager.IP_Restriction.description, Utils.ExcelDataReader.inputData("accessManager", "key", "TC_001", "description"));
        click(UserManagementPom.groups.nextButton);
        click(Locators.Access_Manager.IP_Restriction.ipRange);
        click(Locators.Access_Manager.IP_Restriction.exactIp);
        enter(Locators.Access_Manager.IP_Restriction.ipv4, Utils.ExcelDataReader.inputData("accessManager", "key", "TC_001", "ipv4"));
        click(UserManagementPom.groups.nextButton);
        click(Locators.Access_Manager.IP_Restriction.submit);
        assertEqualsnew(userEnableAndDisableToastMessage, "Successfully created IP restriction details.");
    }

    @Test(description = "Verify that a user is able to access the application when connecting from a valid IP address.", priority = 1)
    public void verifyUserCanAccessApplicationWithValidIp() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "Work_Email_Id"));
        staticWait(1500);
        click(Login.submit);
        enter(UserManagementPom.profilePom.newPassword, Utils.ExcelDataReader.inputData("Profile", "key", "TC_001", "newPassword"));
        staticWait(1500);
        click(Login.signIn);
        assertTextContains(OtpValidationProcess.setpassword.displayNameAssertion, "Welcome");

    }

    @Test(description = "Verify that an admin user can successfully update IP restriction details.", priority = 2)
    public void verifyAdminCanUpdateIpRestrictionDetails() throws InterruptedException {

        navigateToAccessManagersection();
        staticWait(5000);
        actionClick(Locators.Access_Manager.IP_Restriction.manageButton);
        click(Locators.Access_Manager.IP_Restriction.selectSavedIp);
        deleteAll(Locators.Access_Manager.IP_Restriction.name);
        enter(Locators.Access_Manager.IP_Restriction.name, Utils.ExcelDataReader.inputData("accessManager", "key", "TC_004", "name"));
        deleteAll(Locators.Access_Manager.IP_Restriction.description);
        enter(Locators.Access_Manager.IP_Restriction.description, Utils.ExcelDataReader.inputData("accessManager", "key", "TC_004", "description"));
        click(UserManagementPom.groups.nextButton);
        deleteAll(Locators.Access_Manager.IP_Restriction.ipv4);
        enter(Locators.Access_Manager.IP_Restriction.ipv4, Utils.ExcelDataReader.inputData("accessManager", "key", "TC_004", "ipv4"));
        click(UserManagementPom.groups.nextButton);
        click(Locators.Access_Manager.IP_Restriction.update);
        assertEqualsnew(userEnableAndDisableToastMessage, "IP restriction updated successfully.");

    }

    @Test(description = "Verify that a user is prevented from accessing the application when connecting from an invalid IP address.", priority = 3)
    public void verifyUserCannotAccessApplicationWithInvalidIp() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "Work_Email_Id"));
        staticWait(1500);
        click(Login.submit);
        assertEqualsnew(Locators.Access_Manager.IP_Restriction.accessDeniedMsg, "Please contact your IT support to know how you can access your work environment,close the browser window to initiate the login process again.");

    }

    @Test(description = "Verify that an admin user can successfully enable and disable IP restrictions.", priority = 4)
    public void verifyAdminCanEnableAndDisableIpRestrictions() throws InterruptedException {

        navigateToAccessManagersection();
        staticWait(5000);
        jClick(Locators.Access_Manager.IP_Restriction.enableDisableToggle);
        click(Locators.Access_Manager.IP_Restriction.deleteYesBtn);
        assertEqualsnew(createdUserToaster, "IP restriction disabled successfully");
        staticWait(5000);
        jClick(Locators.Access_Manager.IP_Restriction.enableDisableToggle);
        assertEqualsnew(createdUserToaster, "IP restriction enabled successfully");

    }

    @Test(description = "Verify that an admin user can successfully delete an IP restriction.", priority = 5)
    public void verifyAdminCanDeleteIpRestriction() throws InterruptedException {

        navigateToAccessManagersection();
        staticWait(5000);
        actionClick(Locators.Access_Manager.IP_Restriction.manageButton);
        click(Locators.Access_Manager.IP_Restriction.deleteIcon);
        click(Locators.Access_Manager.IP_Restriction.deleteYesBtn);
        assertEqualsnew(userEnableAndDisableToastMessage, "IP restriction deleted successfully.");
    }


    @Test(description = "Verify that an admin user can successfully configure IP restrictions for users.", priority = 6)
    public void verifyAdminCanConfigureIpRestrictionsForUsers() throws InterruptedException {

        navigateToAccessManagersection();
        staticWait(6000);
        actionClick(Locators.Access_Manager.IP_Restriction.createNew);
        click(Locators.Access_Manager.IP_Restriction.ipOption);
        enter(Locators.Access_Manager.IP_Restriction.name, Utils.ExcelDataReader.inputData("accessManager", "key", "TC_001", "name"));
        enter(Locators.Access_Manager.IP_Restriction.description, Utils.ExcelDataReader.inputData("accessManager", "key", "TC_001", "description"));
        click(UserManagementPom.groups.nextButton);
        click(Locators.Access_Manager.IP_Restriction.ipRange);
        click(Locators.Access_Manager.IP_Restriction.exactIp);
        enter(Locators.Access_Manager.IP_Restriction.ipv4, Utils.ExcelDataReader.inputData("accessManager", "key", "TC_001", "ipv4"));
        click(UserManagementPom.groups.nextButton);
        click(Locators.Access_Manager.IP_Restriction.assignToUsers);
        staticWait(3000);
        getTableDetailsAndJsClick(Locators.Access_Manager.IP_Restriction.userTable,Locators.Access_Manager.IP_Restriction.ipGroupSelect,ExcelDataReader.inputData("createUser", "key", "TC_001", "Work_Email_Id"));
        click(Locators.Access_Manager.IP_Restriction.submit);
        assertEqualsnew(userEnableAndDisableToastMessage, "Successfully created IP restriction details.");
    }

    @Test(description = "Verify that an admin user can successfully delete configured IP restrictions for users.", priority = 7)
    public void verifyAdminCanDeleteIpRestrictionsForUsers() throws InterruptedException {

        navigateToAccessManagersection();
        staticWait(5000);
        actionClick(Locators.Access_Manager.IP_Restriction.manageButton);
        click(Locators.Access_Manager.IP_Restriction.deleteIcon);
        click(Locators.Access_Manager.IP_Restriction.deleteYesBtn);
        assertEqualsnew(userEnableAndDisableToastMessage, "IP restriction deleted successfully.");

    }
}


