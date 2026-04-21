package Akku;

import Locators.Login;
import Locators.UserManagementPom;
import Utils.*;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.awt.*;
import java.io.IOException;

import static Locators.UserManagementPom.createUser.*;

public class User_Management extends Base {

    private final String tenantUrl;

    public User_Management(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new User_Management(tenantUrl)};
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

        scriptTimeout();

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


    @Test(priority = 0, description = "Verify that a user is created successfully")
    public void testAddUser() throws InterruptedException {

        loginAndNavigateToUserManagement1();
        fillUserDetailsAndCreate1("TC_001");
        assertEqualsnew(createdUserToaster, ExcelDataReader.inputData("createUser", "key", "TC_001", "Success_Message"));
    }


    @Test(priority = 1, description = "Verify that user details are updated and apps are assigned successfully")
    public void testEditUserDetailsAndAssignApps() throws InterruptedException, AWTException {

        loginAndNavigateToUserManagement1();
        staticWait(2000);
        findAndEditUser("TC_001");
        updateUserDetails("TC_002");
        assignAppsToUser("TC_003");
        assertEqualsnew(userEnableAndDisableToastMessage, "User details updated successfully.");


    }

    @Test(priority = 2, description = "Verify that a user cannot be created with an existing username")
    public void testCreateUserWithExistingUsername() throws InterruptedException {

        loginAndNavigateToUserManagement1();
        fillUserDetailsAndCreate1("TC_001");
        assertEqualsnew(createdUserToaster, "User already exist.");

    }

    @Test(priority = 3, description = "Verify that tenant admin cannot create a user with an existing personal email ID")
    public void testCreateUserWithExistingPersonalEmail() throws InterruptedException {

        loginAndNavigateToUserManagement1();
        fillUserDetailsAndCreate1("TC_005");
        assertEqualsnew(createdUserToaster, "User personal email id already exists.");
    }

    @Test(priority = 4, description = "Verify that tenant admin cannot create a user with an existing mobile number")
    public void testCreateUserWithExistingMobileNumber() throws InterruptedException {

        loginAndNavigateToUserManagement1();
        fillUserDetailsAndCreate1("TC_006");
        assertEqualsnew(createdUserToaster, "User phone number already exists.");
    }

    //@Test(priority = 5, description = "Verify that a user can be deleted successfully")
    public void testDeleteUser() throws InterruptedException {
        loginAndNavigateToUserManagement1();
        deleteUser("TC_001");
        assertEqualsnew(UserDeletedPopUp, "User deleted successfully.");
    }

    @Test(priority = 6, description = "Verify that the group filter is applied correctly")
    public void testApplyGroupFilter() throws InterruptedException {

        loginAndNavigateToUserManagement1();
        applyFilter("All");
        assertTextFromMultipleElements(UserManagementPom.groups.statusAndGroupsWords, "All");
    }

    @Test(priority = 7, description = "Verify that active and inactive user filters are applied correctly")
    public void testFilterActiveAndInactiveUsers() throws InterruptedException {

        loginAndNavigateToUserManagement1();
        filterUsersByStatus("Active");
        assertTextFromMultipleElements(UserManagementPom.groups.statusAndGroupsWords, "Active");
    }

//    @Test(priority = 8, description = "Verify that maximum character limit validation works for user details")
//    public void testVerifyMaximumCharacterLimit() throws InterruptedException {
//        loginAndNavigateToUserManagement();
//        fillUserDetailsAndCreateWithMaxCharacterLimit("TC_003");
//        assertEqualsnew(Maximumcharacters, "Maximum length is 16 characters for the first name");
//    }

    @Test(priority = 9, description = "Verify that a user can be disabled and re-enabled successfully")
    public void testDisableAndEnableUser() throws InterruptedException {

        loginAndNavigateToUserManagement1();
        findAndEditUser("TC_002");

        staticWait(3000);
        jClick(enableAndDisableUser);
        staticWait(3000);
        jClick(confirmEnableAndDisableUser);
        assertEqualsnew(userEnableAndDisableToastMessage, "User status updated successfully.");

        staticWait(3000);

        jClick(enableAndDisableUser);
        staticWait(3000);
        jClick(confirmEnableAndDisableUser);
        assertEqualsnew(userEnableAndDisableToastMessage, "User status updated successfully.");
    }

    @Test(priority = 10, description = "Verify that an individual app can be unassigned from a user successfully")
    public void testUnassignIndividualAppFromUser() throws InterruptedException {

        loginAndNavigateToUserManagement1();
        findAndEditUser("TC_002");

        staticWait(4000);
        assignAppsToUser("TC_003");

        // Verify successful update
        assertEqualsnew(UserUpdatePopUp, "User details updated successfully.");
    }


    private void loginAndNavigateToUserManagement1() throws InterruptedException {

        enter(Login.username, ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(3000);
        jClick(UserManagementPom.createUser.userManagementSection);
        staticWait(6000);
    }

    private void fillUserDetailsAndCreate1(String testCase) throws InterruptedException {

        actionClick(UserManagementPom.createUser.add);
        click(UserManagementPom.createUser.addUserManually);
        enter(UserManagementPom.createUser.firstName, ExcelDataReader.inputData("createUser", "key", testCase, "First_Name"));
        enter(UserManagementPom.createUser.lastName, ExcelDataReader.inputData("createUser", "key", testCase, "Last_Name"));
        enter(UserManagementPom.createUser.workEmailId, ExcelDataReader.inputData("createUser", "key", testCase, "Work_Email_Id"));
        click(UserManagementPom.createUser.userType);
        staticWait(3000);
        selectFromScrollableDropdown(userTypeDropdownOptions, ExcelDataReader.inputData("createUser", "key", testCase, "User_Type"));
        click(UserManagementPom.createUser.nextButton);
        enter(UserManagementPom.createUser.telephoneNumber, ExcelDataReader.inputData("createUser", "key", testCase, "Mobile_Number"));
        enter(UserManagementPom.createUser.personalEmail, ExcelDataReader.inputData("createUser", "key", testCase, "Personal_Email"));
        staticWait(4000);
        actionClick(UserManagementPom.createUser.nextButton);
    }

    private static void findAndEditUser(String testCase) throws InterruptedException {

        scrollToBottom();
        staticWait(2000);
        findElementsAndClick(UserManagementPom.groups.usermanagementtablewords, ExcelDataReader.inputData("createUser", "key", testCase, "DisplayUsername"));
    }

    private static void updateUserDetails(String testCase) throws InterruptedException {

        staticWait(3000);
        jClick(editUserDetails);
        clearAndEnterText(firstName, ExcelDataReader.inputData("createUser", "key", testCase, "First_Name"));
        clearAndEnterText(lastName, ExcelDataReader.inputData("createUser", "key", testCase, "Last_Name"));
        staticWait(3000);
        clearAndEnterText(editPersonalEmail, ExcelDataReader.inputData("createUser", "key", testCase, "Personal_Email"));
        deleteAll(telephoneNumber);
        staticWait(3000);
        enter(telephoneNumber, ExcelDataReader.inputData("createUser", "key", testCase, "Mobile_Number"));
        // clearAndEnterText(telephoneNumber, ExcelDataReader.inputData("createUser", "key", "TC_002", "Mobile_Number"));


    }

    private static void assignAppsToUser(String testCase) throws InterruptedException {

        jClick(addAppsButton);
        staticWait(2000);
        findElementsAndClick(appSelectOptions, ExcelDataReader.inputData("appstore", "key", testCase, "appName"));
        click(appSubmit);
        jClick(saveUserDetails);

    }

    private static void deleteUser(String testCase) throws InterruptedException {

        // getTableDetails(particularUserDetailsDelete, userDeleteOption, ExcelDataReader.inputData("createUser", "key", "TC_001", "Edit_User"));

        getTableDetailsWithPagination(particularUserDetailsDelete, userDeleteOption, nextPagination, ExcelDataReader.inputData("createUser", "key", "TC_001", "Edit_User"));
        jClick(userDeleteConfirmation);

    }

    private static void applyFilter(String filterValue) throws InterruptedException {

        click(filterButton);
        click(UserManagementPom.createUser.groupFilterBtn);
        staticWait(2500);
        findElementsAndClick(UserManagementPom.createUser.filterList, filterValue);
        jClick(filterSearch);

    }

    private static void filterUsersByStatus(String status) throws InterruptedException {

        click(filterButton);
        click(UserManagementPom.createUser.statusFilter);
        staticWait(2500);  // Wait for the dropdown to load properly

        // Apply the status filter (either Active or Inactive)
        findElementsAndClick(UserManagementPom.createUser.filterList, status);
        jClick(filterSearch);
        // Ensure enough time for the filtered results to appear

    }
}
