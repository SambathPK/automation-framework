package Akku;

import Locators.Login;
import Locators.UserManagementPom;
import Utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class UserProfile extends Base {

    private final String tenantUrl;

    public UserProfile(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new UserProfile(tenantUrl)};
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

    @Test(priority = 0, description = "Verify that the user's profile details are updated successfully")
    public static void profile_Details_Update() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "Work_Email_Id"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("Admin_ResetPassword", "Key", "Value", "ConfirmPsswd"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(5000);
        jClick(UserManagementPom.profilePom.userProfileClick);
        staticWait(2000);
        jClick(UserManagementPom.profilePom.view_Profile);
        staticWait(4000);
        jClick(UserManagementPom.profilePom.updateProfile);
        staticWait(3000);
        uploadFile(UserManagementPom.profilePom.photoInputFile, "DataFiles/1631344365998.jpg");
        staticWait(1500);
        deleteAll(UserManagementPom.profilePom.firstName);
        enter(UserManagementPom.profilePom.firstName, Utils.ExcelDataReader.inputData("Profile", "key", "TC_001", "firstName"));
        deleteAll(UserManagementPom.profilePom.lastName);
        enter(UserManagementPom.profilePom.lastName, Utils.ExcelDataReader.inputData("Profile", "key", "TC_001", "lastName"));
        deleteAll(UserManagementPom.profilePom.personalEmail);
        enter(UserManagementPom.profilePom.personalEmail, Utils.ExcelDataReader.inputData("Profile", "key", "TC_001", "personalEmail"));
        deleteAll(UserManagementPom.profilePom.teleNumber);
        enter(UserManagementPom.profilePom.teleNumber, Utils.ExcelDataReader.inputData("Profile", "key", "TC_001", "telephoneNumber"));
        staticWait(3000);
        click(UserManagementPom.profilePom.updateButton);
        assertEqualsnew(UserManagementPom.createUser.userEnableAndDisableToastMessage, "User profile updated successfully");

    }

    @Test(priority = 1, description = "Verify that the user's password is changed successfully")
    public static void change_User_Password() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "Work_Email_Id"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("Admin_ResetPassword", "Key", "Value", "ConfirmPsswd"));
        click(Login.signIn);
        staticWait(5000);
        jClick(UserManagementPom.profilePom.userProfileClick);
        staticWait(4000);
        jClick(UserManagementPom.profilePom.changePassword);
        staticWait(3000);
        enter(UserManagementPom.profilePom.oldPassword, Utils.ExcelDataReader.inputData("Admin_ResetPassword", "Key", "Value", "ConfirmPsswd"));
        enter(UserManagementPom.profilePom.newPassword, Utils.ExcelDataReader.inputData("Profile", "key", "TC_001", "newPassword"));
        enter(UserManagementPom.profilePom.confirmPassword, Utils.ExcelDataReader.inputData("Profile", "key", "TC_001", "confirmPassword"));
        staticWait(3000);
        click(UserManagementPom.profilePom.changePasswordUpdateButton);
        assertEqualsnew(UserManagementPom.createUser.userEnableAndDisableToastMessage, "User Password Updated Successfully");

    }

}
