package Akku;

import Locators.Login;
import Locators.RoleManagementPom;
import Locators.UserManagementPom;
import Utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import javax.management.relation.Role;
import java.io.File;
import java.io.IOException;

public class Role_Management extends Base {

    private final String tenantUrl;

    public Role_Management(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new Role_Management(tenantUrl)};
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

    @Test(priority = 0, description = "Verify that a new role is added successfully with the correct permissions")
    public static void testAddRole() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        jClick(RoleManagementPom.RoleManagement);//Clicked on "Role Management"
        jClick(RoleManagementPom.RoleManagement);//Clicked on "Role Management"
        click(RoleManagementPom.AddRole);        //Clicked on "Add new Role"
        staticWait(3000);
        enter(RoleManagementPom.RoleName, ExcelDataReader.inputData("RoleManagement", "Key", "TC_001", "Rolename"));
        staticWait(2000);
        enter(RoleManagementPom.RoleDescription, ExcelDataReader.inputData("RoleManagement", "Key", "TC_001", "Roledescription"));
        click(UserManagementPom.groups.nextButton);
        staticWait(3000);
        findElementByIndexAndActionClick(RoleManagementPom.selectRolePermission, 0);
        findElementByIndexAndActionClick(RoleManagementPom.selectRolePermission, 1);
        findElementByIndexAndActionClick(RoleManagementPom.selectRolePermission, 11);
        findElementByIndexAndActionClick(RoleManagementPom.selectRolePermission, 12);
        staticWait(2000);
        click(RoleManagementPom.Save);
        assertEqualsnew(UserManagementPom.createUser.userManagementToastMessage, "Role saved successfully.");

    }

    @Test(priority = 1, description = "Verify that an error message is displayed when trying to create a role with an existing name")
    public static void testRoleNameAlreadyExists() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        jClick(RoleManagementPom.RoleManagement);
        jClick(RoleManagementPom.RoleManagement);//Clicked on "Role Management"//Clicked on "Role Management"
        staticWait(2000);
        click(RoleManagementPom.AddRole);        //Clicked on "Add new Role"
        staticWait(1500);
        enter(RoleManagementPom.RoleName, ExcelDataReader.inputData("RoleManagement", "Key", "TC_001", "Rolename"));
        staticWait(2000);
        enter(RoleManagementPom.RoleDescription, ExcelDataReader.inputData("RoleManagement", "Key", "TC_001", "Roledescription"));
        click(UserManagementPom.groups.nextButton);
        staticWait(3000);
        findElementByIndexAndActionClick(RoleManagementPom.selectRolePermission, 3);
        findElementByIndexAndActionClick(RoleManagementPom.selectRolePermission, 10);
        findElementByIndexAndActionClick(RoleManagementPom.selectRolePermission, 13);
        findElementByIndexAndActionClick(RoleManagementPom.selectRolePermission, 13);
        staticWait(2000);
        click(RoleManagementPom.Save);
        assertEqualsnew(UserManagementPom.createUser.userEnableAndDisableToastMessage, "Role name already exists.");
    }

    @Test(priority = 2, description = "Verify that an existing role is updated successfully with new permissions")
    public static void testEditRole() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        jClick(RoleManagementPom.RoleManagement);
        jClick(RoleManagementPom.RoleManagement);//Clicked on "Role Management"
        staticWait(3000);
        getTableDetails(RoleManagementPom.UserManagement_Table2, RoleManagementPom.roleEdit, ExcelDataReader.inputData("RoleManagement", "Key", "TC_001", "Rolename"));
        findElementByIndexAndActionClick(RoleManagementPom.selectRolePermission, 3);
        findElementByIndexAndActionClick(RoleManagementPom.selectRolePermission, 10);
        findElementByIndexAndActionClick(RoleManagementPom.selectRolePermission, 13);
        findElementByIndexAndActionClick(RoleManagementPom.selectRolePermission, 13);
        staticWait(1500);
        click(RoleManagementPom.Save);
        assertEqualsnew(UserManagementPom.createUser.userManagementToastMessage, "Role updated successfully.");

    }

    @Test(priority = 3, description = "Verify that a role is deleted successfully")
    public static void testDeleteRole() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        jClick(RoleManagementPom.RoleManagement);
        staticWait(3000);
        getTableDetails(RoleManagementPom.UserManagement_Table2, RoleManagementPom.roleDelete, ExcelDataReader.inputData("RoleManagement", "Key", "TC_001", "Rolename"));
        click(RoleManagementPom.Popup_Confirm);
        assertEqualsnew(UserManagementPom.createUser.userEnableAndDisableToastMessage, "Role deleted successfully.");

    }
}







