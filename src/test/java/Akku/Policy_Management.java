package Akku;

import Locators.Access_Manager;
import Locators.Login;
import Locators.Policy_Pom;
import Utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.devtools.v85.backgroundservice.BackgroundService;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class Policy_Management extends Base {

    private final String tenantUrl;

    public Policy_Management(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new Policy_Management(tenantUrl)};
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

    @Test(description = "Verify that a tenant admin can create a GPO policy configuration and assign it to a group", priority = 0)
    public static void testTenantAdminCanCreateAndAssignGpoPolicy() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(Policy_Pom.policyManager);
        staticWait(2000);
        click(Policy_Pom.addPolicyButton);
        enter(Policy_Pom.policyName, Utils.ExcelDataReader.inputData("PolicyManager", "key", "TC_001", "policyName"));
        enter(Policy_Pom.policyDescription, Utils.ExcelDataReader.inputData("PolicyManager", "key", "TC_001", "policyDescription"));
        enter(Policy_Pom.assignTo, "All");
        findElementsAndClick(Policy_Pom.selectGroups, "All");
        click(Policy_Pom.policyDescription);
        click(Policy_Pom.nextButton);
        staticWait(4000);
        findElementByIndexAndActionClick(Policy_Pom.selectPolicy, 1);
        staticWait(3000);
        findElementByIndexAndActionClick(Policy_Pom.selectPolicy, 2);
        click(Policy_Pom.savePolicy);
        assertEqualsnew(Policy_Pom.alertPopup, "Device policy created successfully.");

    }

    @Test(description = "Verify that the tenant admin can edit and update the policy configuration", priority = 1)
    public static void testTenantAdminCanEditAndUpdatePolicyConfiguration() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(Policy_Pom.policyManager);
        staticWait(2000);
        findElementsAndClickByVisibleText(Policy_Pom.editPolicy, Utils.ExcelDataReader.inputData("PolicyManager", "key", "TC_001", "policyName"), Policy_Pom.editButton);
        staticWait(2000);
        deleteAll(Policy_Pom.policyName);
        staticWait(1500);
        enter(Policy_Pom.policyName, Utils.ExcelDataReader.inputData("PolicyManager", "key", "TC_001", "policyName"));
        deleteAll(Policy_Pom.policyDescription);
        staticWait(1500);
        enter(Policy_Pom.policyDescription, Utils.ExcelDataReader.inputData("PolicyManager", "key", "TC_002", "policyDescription"));
        click(Policy_Pom.nextButton);
        staticWait(4000);
        findElementByIndexAndClick(Policy_Pom.selectPolicy, 3);
        staticWait(2000);
        findElementByIndexAndClick(Policy_Pom.selectPolicy, 4);
        actionClick(Policy_Pom.updatePolicy);
        assertEqualsnew(Policy_Pom.alertPopup, "Device policy updated successfully.");


    }


    @Test(description = "Verify that the tenant admin can assign or unassign groups from a GPO policy configuration", priority = 2)
    public static void testTenantAdminCanAssignOrUnassignGroupsFromGpoPolicy() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(Policy_Pom.policyManager);
        staticWait(2000);
        findElementsAndClickByVisibleText(Policy_Pom.editPolicy, Utils.ExcelDataReader.inputData("PolicyManager", "key", "TC_001", "policyName"), Policy_Pom.editButton);
        click(Policy_Pom.assignTo);
        findElementsAndClick(Policy_Pom.selectGroups, "All");
        findElementsAndClick(Policy_Pom.selectGroups, "Administrators");
        click(Policy_Pom.policyDescription);
        click(Policy_Pom.nextButton);
        staticWait(4000);
        actionClick(Policy_Pom.updatePolicy);
        assertEqualsnew(Policy_Pom.alertPopup, "Device policy updated successfully.");


    }

    @Test(description = "Verify that the tenant admin cannot create another policy with the same policy name", priority = 3)
    public static void testCannotCreateAnotherPolicyWithSamePolicyName() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(Policy_Pom.policyManager);
        staticWait(2000);
        actionClick(Policy_Pom.addnewPolicyButton);
        enter(Policy_Pom.policyName, Utils.ExcelDataReader.inputData("PolicyManager", "key", "TC_001", "policyName"));
        enter(Policy_Pom.policyDescription, Utils.ExcelDataReader.inputData("PolicyManager", "key", "TC_001", "policyDescription"));
        click(Policy_Pom.assignTo);
        findElementsAndClick(Policy_Pom.selectGroups, "All");
        click(Policy_Pom.policyDescription);
        click(Policy_Pom.nextButton);
        staticWait(4000);
        findElementByIndexAndActionClick(Policy_Pom.selectPolicy, 1);
        findElementByIndexAndActionClick(Policy_Pom.selectPolicy, 2);
        click(Policy_Pom.savePolicy);
        assertTextContains(Policy_Pom.alertPopup, "A policy with the same name already exists.");

    }

//    @Test(description = "Verify that the tenant admin cannot assign an already configured policy to the same group",priority = 4)
//    public static void testCannotReassignAlreadyConfiguredPolicyToSameGroup() throws InterruptedException {
//
//        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
//        staticWait(1500);
//        click(Login.submit);
//        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
//        staticWait(1500);
//        click(Login.signIn);
//        jClick(Policy_Pom.policyManager);
//        staticWait(2000);
//        click(Policy_Pom.addnewPolicyButton);
//        enter(Policy_Pom.policyName, Utils.ExcelDataReader.inputData("PolicyManager", "key", "TC_003", "policyName"));
//        enter(Policy_Pom.policyDescription, Utils.ExcelDataReader.inputData("PolicyManager", "key", "TC_003", "policyDescription"));
//        click(Policy_Pom.assignTo);
//        findElementsAndClick(Policy_Pom.selectGroups, "All");
//        click(Policy_Pom.policyDescription);
//        click(Policy_Pom.nextButton);
//        staticWait(4000);
//        findElementByIndexAndActionClick(Policy_Pom.selectPolicy, 5);
//        findElementByIndexAndActionClick(Policy_Pom.selectPolicy, 6);
//        click(Policy_Pom.savePolicy);
//        assertTextContains(Access_Manager.GeoLocationDeletePopup, "The group(s) All already have these policies configured");
//
//    }

    @Test(description = "Verify that the tenant admin can enable and disable a GPO policy", priority = 5)
    public static void testTenantAdminCanEnableAndDisableGPOPolicy() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(Policy_Pom.policyManager);
        staticWait(4000);
        jClickOnButtonByName(Policy_Pom.editPolicy, Policy_Pom.enableDisablePolicy, Utils.ExcelDataReader.inputData("PolicyManager", "key", "TC_001", "policyName"));
        click(Policy_Pom.confirmButton);
        assertEqualsnew(Policy_Pom.alertPopup, "Policy disabled successfully");
        staticWait(5000);
        jClickOnButtonByName(Policy_Pom.editPolicy, Policy_Pom.enableDisablePolicy, Utils.ExcelDataReader.inputData("PolicyManager", "key", "TC_001", "policyName"));
        assertEqualsnew(Policy_Pom.alertPopup, "Policy enabled successfully");

    }

    @Test(description = "Verify tenant admin can delete an existing GPO policy successfully", priority = 6)
    public static void verifyTenantAdminDeletesGpoPolicy() throws InterruptedException {


        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(Policy_Pom.policyManager);
        staticWait(4000);
        jClickOnButtonByName(Policy_Pom.editPolicy, Policy_Pom.deleteButton, Utils.ExcelDataReader.inputData("PolicyManager", "key", "TC_001", "policyName"));
        click(Policy_Pom.confirmButton);
        assertEqualsnew(Policy_Pom.alertPopup, "Device policy deleted successfully.");


    }


}
