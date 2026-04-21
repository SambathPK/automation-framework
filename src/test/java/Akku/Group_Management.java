package Akku;

import Locators.DeviceManagerPom;
import Locators.Login;
import Locators.UserManagementPom;
import Utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static Locators.UserManagementPom.createUser.*;
import static Locators.UserManagementPom.groups.*;

public class Group_Management extends Base {


    private final String tenantUrl;

    public Group_Management(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new Group_Management(tenantUrl)};
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

    @Test(priority = 0, description = "Verify that a new group is created successfully")
    public static void testCreateNewGroup() throws InterruptedException, AWTException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(UserManagementPom.createUser.userManagementSection);
        staticWait(3000);
        click(UserManagementPom.groups.groupView);
        actionClick(UserManagementPom.groups.addGroup);
        enter(UserManagementPom.groups.groupName, Utils.ExcelDataReader.inputData("groups", "key", "TC_001", "groupName"));
        enter(UserManagementPom.groups.groupDescription, Utils.ExcelDataReader.inputData("groups", "key", "TC_001", "description"));
        click(UserManagementPom.groups.nextButton);
        staticWait(2000);
        getTableDetails(UserManagementPom.groups.GetGroupTableId, groupCheckbox2, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "Work_Email_Id"));
        click(UserManagementPom.groups.next2);
        staticWait(2000);
        click(UserManagementPom.groups.addApps);
        findElementsAndClick(selectApps, ExcelDataReader.inputData("appstore", "key", "TC_003", "appName"));
        click(appSubmit);
        click(saveGroup);
        assertEqualsnew(GroupCreatedPopUp, "Group created successfully. If users are assigned to the group, you will be notified once all assigned users have been added.");
    }

    @Test(priority = 1, description = "Verify that tenant admin cannot delete a group when users are assigned to it")
    public static void testDeleteGroupWithUsersAssigned() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(UserManagementPom.createUser.userManagementSection);
        staticWait(3000);
        click(UserManagementPom.groups.groupView);
        staticWait(2000);
        getTableDetailsAndJsClick(groupTable, UserManagementPom.groups.GroupCheckboxClick, Utils.ExcelDataReader.inputData("groups", "key", "TC_001", "groupName"));
        click(deleteIcon);
        click(UserManagementPom.groups.delete);
        assertEqualsnew(userEnableAndDisableToastMessage, "You cannot delete a group with assigned users. Please remove users from the group before deleting it.");
    }

    @Test(priority = 2, description = "Verify that an error message is shown when a group name already exists")
    public static void testGroupNameAlreadyExistsError() throws InterruptedException, AWTException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(UserManagementPom.createUser.userManagementSection);
        staticWait(3000);
        click(UserManagementPom.groups.groupView);
        actionClick(UserManagementPom.groups.addGroup);
        enter(UserManagementPom.groups.groupName, Utils.ExcelDataReader.inputData("groups", "key", "TC_001", "groupName"));
        enter(UserManagementPom.groups.groupDescription, Utils.ExcelDataReader.inputData("groups", "key", "TC_001", "description"));
        click(UserManagementPom.groups.nextButton);
        staticWait(2000);
        getTableDetails(UserManagementPom.groups.GetGroupTableId, groupCheckbox2, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "Work_Email_Id"));
        click(UserManagementPom.groups.next2);
        staticWait(2000);
        click(UserManagementPom.groups.addApps);
        findElementsAndClick(selectApps, ExcelDataReader.inputData("appstore", "key", "TC_003", "appName"));
        click(appSubmit);
        click(saveGroup);
        assertEqualsnew(groupsToastMessage, "Group name already exists.");
    }

    @Test(priority = 3, description = "Verify that a user can be successfully unassigned from a group")
    public static void testUnassignUserFromGroup() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(UserManagementPom.createUser.userManagementSection);
        staticWait(3000);
        click(UserManagementPom.groups.groupView);
        staticWait(3000);
        findElementsAndClick(groupNameSearch, Utils.ExcelDataReader.inputData("groups", "key", "TC_001", "groupName"));
        click(UserManagementPom.groups.nextButton);
        staticWait(1500);
        getTableDetails(UserManagementPom.groups.GetGroupTableId, groupCheckbox2, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "Work_Email_Id"));
        click(UserManagementPom.groups.next2);
        click(saveGroup);
        assertEqualsnew(deallocateUserMessage, "You will be notified once user(s) de-allocated from the group.");
    }

    @Test(priority = 4, description = "Verify that the group name and description are updated successfully")
    public static void testUpdateGroupNameAndDescription() throws InterruptedException, AWTException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(UserManagementPom.createUser.userManagementSection);
        staticWait(3000);
        click(UserManagementPom.groups.groupView);
        staticWait(3000);
        findElementsAndClick(groupNameSearch, Utils.ExcelDataReader.inputData("groups", "key", "TC_001", "groupName"));
        deleteAll(UserManagementPom.groups.groupName);
        enter(UserManagementPom.groups.groupName, Utils.ExcelDataReader.inputData("groups", "key", "TC_005", "groupName"));
        deleteAll(UserManagementPom.groups.groupDescription);
        enter(UserManagementPom.groups.groupDescription, Utils.ExcelDataReader.inputData("groups", "key", "TC_005", "description"));
        click(UserManagementPom.groups.nextButton);
        click(UserManagementPom.groups.next2);
        click(saveGroup);
        assertEqualsnew(GroupupdatedPopUp, "Successfully updated group details.");
    }

    @Test(priority = 5, description = "Verify that a group can be deleted successfully")
    public static void testDeleteGroup() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(UserManagementPom.createUser.userManagementSection);
        staticWait(3000);
        click(UserManagementPom.groups.groupView);
        staticWait(2000);
        getTableDetailsAndJsClick(groupTable, UserManagementPom.groups.GroupCheckboxClick, Utils.ExcelDataReader.inputData("groups", "key", "TC_005", "groupName"));
        click(deleteIcon);
        click(UserManagementPom.groups.delete);
        assertEqualsnew(UserManagementPom.groups.notificationMessage, "Successfully deleted selected groups.");
    }


}