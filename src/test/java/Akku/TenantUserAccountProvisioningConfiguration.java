package Akku;

import Locators.App_Management_Pom;
import Locators.Login;
import Locators.ProvisioningPom;
import Utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

import static Locators.App_Management_Pom.addApps.*;

public class TenantUserAccountProvisioningConfiguration extends Base {

    private final String tenantUrl;

    public TenantUserAccountProvisioningConfiguration(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new TenantUserAccountProvisioningConfiguration(tenantUrl)};
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

    @Test(priority = 0, description = "Verify that GWS tenant provisioning connector is configured successfully")
    public void configureGwsTenantProvisioningConnector() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(App_Management_Pom.addApps.appStore);
        staticWait(4000);
        enter(ProvisioningPom.searchApps, Utils.ExcelDataReader.inputData("appstore", "key", "TC_003", "appName"));
        staticWait(5000);
        jClick(setupButton);
        staticWait(3000);
        actionClick(ProvisioningPom.provisioningButton);
        actionClick(configure);
        staticWait(4000);
        click(ProvisioningPom.nextButton);
        enter(ProvisioningPom.serviceToken, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_002", "Service_Token"));
        enter(ProvisioningPom.administratorEmail, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_002", "administrator_username"));
        click(ProvisioningPom.testButton);
        staticWait(4000);
        jClick(ProvisioningPom.done);
        staticWait(6000);
        click(ProvisioningPom.nextButton);
        staticWait(6000);
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 0);
        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 0, "All");
        staticWait(2000);
        findElementsAndClick(ProvisioningPom.selectAttribute, "All");
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 1);
        staticWait(2000);
        findElementsAndActionClick(ProvisioningPom.selectAttribute, "QA Group");
        click(ProvisioningPom.nextButton);
        staticWait(6000);

//        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 0);
//        staticWait(1000);
//        findElementsAndClick(ProvisioningPom.selectAttribute, "admin");
//        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 1);
//        staticWait(1000);
//        findElementsAndActionClick(ProvisioningPom.selectAttribute, "_SEED_ADMIN_ROLE");
//        click(ProvisioningPom.nextButton);

        safeClick(ProvisioningPom.skipButton);
        staticWait(5000);
        safeClick(ProvisioningPom.skipButton);
        staticWait(5000);
        safeClick(ProvisioningPom.confirmButton);
        assertEqualsnew(ProvisioningPom.provisioningConnectorCompleteMessage, "Provisioning configuration completed successfully.");

        staticWait(6000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, ProvisioningPom.provisionActiveInactiveToggle1, Utils.ExcelDataReader.inputData("appstore", "key", "TC_003", "appName"));
        assertEqualsnew(ProvisioningPom.provisionActivateMessage, "Provisioning engine activated successfully.");


    }

    @Test(priority = 1, description = "Verify that the GWS Tenant Provisioning Connector is edited successfully")
    public void editGwsTenantProvisioningConnector() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(6000);
        jClick(App_Management_Pom.addApps.appStore);
        staticWait(4000);
        enter(ProvisioningPom.searchApps, Utils.ExcelDataReader.inputData("appstore", "key", "TC_003", "appName"));
        staticWait(5000);

        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, ProvisioningPom.provisionActiveInactiveToggle2, Utils.ExcelDataReader.inputData("appstore", "key", "TC_003", "appName"));
        click(ProvisioningPom.deleteYesButton);
        assertEqualsnew(ProvisioningPom.provisionDeactivateMessage, "Provisioning engine deactivated successfully.");

        staticWait(5000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, threedot, Utils.ExcelDataReader.inputData("appstore", "key", "TC_003", "appName"));
        staticWait(3000);
        actionClick(ProvisioningPom.provisioningButton);
        click(App_Management_Pom.addApps.next);
        staticWait(4000);
        click(ProvisioningPom.nextButton);
        deleteAll(ProvisioningPom.serviceToken);
        enter(ProvisioningPom.serviceToken, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "Service_Token"));
        deleteAll(ProvisioningPom.administratorEmail);
        enter(ProvisioningPom.administratorEmail, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "administrator_username"));
        click(ProvisioningPom.testButton);
        staticWait(4000);
        click(ProvisioningPom.done);
        staticWait(3000);
        click(ProvisioningPom.nextButton);
        staticWait(10000);

        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 0);
        staticWait(2000);
        findElementsAndClick(ProvisioningPom.selectAttribute, "All");
        staticWait(2000);
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 1);
        staticWait(2000);
        findElementsAndActionClick(ProvisioningPom.selectAttribute, "QA Group");
        click(ProvisioningPom.nextButton);
        staticWait(4000);

//        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 0);
//        staticWait(2000);
//        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 0, "admin");
//        staticWait(2000);
//        findElementsAndClick(ProvisioningPom.selectAttribute, "admin");
//        staticWait(2000);
//        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 1);
//        staticWait(2000);
//        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 1, "_SEED_ADMIN_ROLE");
//        staticWait(2000);
//        findElementsAndActionClick(ProvisioningPom.selectAttribute, "_SEED_ADMIN_ROLE");
//        click(ProvisioningPom.nextButton);

        safeClick(ProvisioningPom.skipButton);
        staticWait(5000);
        safeClick(ProvisioningPom.skipButton);
        staticWait(5000);
        safeClick(ProvisioningPom.confirmButton);
        assertEqualsnew(ProvisioningPom.provisioningConnectorCompleteMessage, "Provisioning configuration completed successfully.");

    }

    // @Test(priority = 2, description = "Verify that the Jira Tenant Provisioning Connector is configured successfully")
    public void configureJiraTenantProvisioningConnector() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(10000);
        actionClick(App_Management_Pom.addApps.appStore);
        staticWait(8000);
        enter(ProvisioningPom.searchApps, Utils.ExcelDataReader.inputData("appstore", "key", "TC_001", "appName"));
        staticWait(5000);
        jClick(setupButton);
        staticWait(3000);
        actionClick(ProvisioningPom.provisioningButton);
        actionClick(configure);
        staticWait(2000);
        click(ProvisioningPom.nextButton);
        enter(ProvisioningPom.apiToken, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "Api_Token"));
        enter(ProvisioningPom.apiTokenUsername, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "administrator_username"));
        click(ProvisioningPom.testButton);
        staticWait(4000);
        click(ProvisioningPom.done);
        staticWait(6000);
        click(ProvisioningPom.nextButton);
        staticWait(12000);

        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 0);
        staticWait(1000);
        findElementsAndClick(ProvisioningPom.selectAttribute, "All");
        staticWait(1000);
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 1);
        staticWait(1000);
        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 1, "Ticket creation team");
        findElementsAndActionClick(ProvisioningPom.selectAttribute, "Ticket creation team");
        click(ProvisioningPom.nextButton);
        //assertEqualsnew(ProvisioningPom.groupMappingConfigureMessage, "Group mapping updated successfully.");
        staticWait(8000);
        click(ProvisioningPom.confirmButton);
        assertEqualsnew(ProvisioningPom.provisioningConnectorCompleteMessage, "Provisioning configuration completed successfully.");
        staticWait(6000);

        staticWait(6000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, ProvisioningPom.provisionActiveInactiveToggle1, Utils.ExcelDataReader.inputData("appstore", "key", "TC_001", "appName"));
        assertEqualsnew(ProvisioningPom.provisionActivateMessage, "Provisioning engine activated successfully.");

    }

    //@Test(priority = 3, description = "Verify that the Jira Tenant Provisioning Connector is deleted successfully")
    public static void deleteJiraTenantProvisioningConnector() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(10000);
        jClick(App_Management_Pom.addApps.appStore);
        staticWait(8000);

        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, ProvisioningPom.provisionActiveInactiveToggle2, Utils.ExcelDataReader.inputData("appstore", "key", "TC_001", "appName"));
        click(ProvisioningPom.deleteYesButton);
        assertEqualsnew(ProvisioningPom.provisionDeactivateMessage, "Provisioning engine deactivated successfully.");

        staticWait(5000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, deleteButton, Utils.ExcelDataReader.inputData("appstore", "key", "TC_001", "appName"));
        click(ProvisioningPom.deleteYesButton);
        assertEqualsnew(ProvisioningPom.provisionDeleteMessage, "Provisioning engine deleted successfully.");

    }


}
