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

public class MasterUserAccountProvisioningConfiguration extends Base {

    private final String tenantUrl;

    public MasterUserAccountProvisioningConfiguration(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new MasterUserAccountProvisioningConfiguration(tenantUrl)};
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

    @Test(priority = 0)
    public void createGwsMasterProvisioningConnector() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(5000);
        click(Login.signIn);
        staticWait(10000);
        jClick(App_Management_Pom.addApps.appStore);
        staticWait(8000);
        enter(ProvisioningPom.searchApps, Utils.ExcelDataReader.inputData("appstore", "key", "TC_002", "appName"));
        staticWait(5000);
        jClick(setupButton);
        staticWait(3000);
        actionClick(ProvisioningPom.provisioningButton);
        actionClick(configure);
        staticWait(2000);
        enter(ProvisioningPom.Description, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "Description"));
        enter(ProvisioningPom.SpDomain, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "Sp_Domain"));
        click(ProvisioningPom.authorizationType);
        staticWait(2000);
        findElementsAndClick(ProvisioningPom.selectAuthorizationtype, "Bearer Token");
        click(ProvisioningPom.tokenType);
        staticWait(2000);
        findElementsAndClick(ProvisioningPom.selectTokenType, "Service Token");
        staticWait(3000);
        click(ProvisioningPom.nextButton);
        enter(ProvisioningPom.serviceToken, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "Service_Token"));
        enter(ProvisioningPom.apiEndpointUrl, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "token_endpoint_URL"));
        enter(ProvisioningPom.administratorEmail, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "administrator_username"));
        findElementByIndexAndPasteText(ProvisioningPom.scope, 0, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "scope1"));
        click(ProvisioningPom.addScope);
        findElementByIndexAndPasteText(ProvisioningPom.scope, 1, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "scope2"));
        click(ProvisioningPom.addScope);
        findElementByIndexAndPasteText(ProvisioningPom.scope, 2, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "scope3"));
        click(ProvisioningPom.addScope);
        findElementByIndexAndPasteText(ProvisioningPom.scope, 3, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "scope4"));
        click(ProvisioningPom.addScope);
        findElementByIndexAndPasteText(ProvisioningPom.scope, 4, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "scope5"));
        click(ProvisioningPom.addScope);
        findElementByIndexAndPasteText(ProvisioningPom.scope, 5, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "scope6"));
        click(ProvisioningPom.addScope);
        findElementByIndexAndPasteText(ProvisioningPom.scope, 6, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "scope7"));
        click(ProvisioningPom.addScope);
        findElementByIndexAndPasteText(ProvisioningPom.scope, 7, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "scope8"));

        click(ProvisioningPom.testButton);
        click(ProvisioningPom.done);
        staticWait(3000);
        click(ProvisioningPom.nextButton);
        staticWait(4000);
        enter(ProvisioningPom.provisioningApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "provisioningApiEndpoint"));
        click(ProvisioningPom.provisioningMethodType);
        staticWait(3000);
        findElementsAndClick(ProvisioningPom.provisioningSelectMethodType, "Post");
        enter(ProvisioningPom.provisioningRequestPayload, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "provisioningRequestPayload"));
        enter(ProvisioningPom.provisioningResponsePayload, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "provisioningResponsePayload"));
        findElementByIndexAndPasteText(ProvisioningPom.provisionResponseAttrbuteMapping, 0, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "responseAttributeMapping1"));
        click(ProvisioningPom.nextButton);
        staticWait(2000);
        enter(ProvisioningPom.getRoleApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "getRoleApiEndpoint"));
        click(ProvisioningPom.getroleMethodType);
        staticWait(3000);
        findElementsAndActionClick(ProvisioningPom.getroleSelectMethodType, "Get");
        enter(ProvisioningPom.getroleResponsePayload, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "getRoleResponsePayload"));
        findElementByIndexAndPasteText(ProvisioningPom.getroleResponseAttributeMapping, 0, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "getRoleresponseAttributeMapping1"));
        findElementByIndexAndPasteText(ProvisioningPom.getroleResponseAttributeMapping, 1, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "getRoleresponseAttributeMapping2"));
        scrollByElement(ProvisioningPom.assignRoleApiEndpoint);
        enter(ProvisioningPom.assignRoleApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "assignRoleApiEndpoint"));
        click(ProvisioningPom.assignroleMethodType);
        staticWait(3000);
        findElementsAndClick(ProvisioningPom.assignroleSelectMethodType, "Post");
        enter(ProvisioningPom.assignroleRequestPayload, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "assignRoleRequestPayload"));
        enter(ProvisioningPom.assignroleResponsePayload, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "assignRoleResponsePayload"));
        findElementByIndexAndPasteText(ProvisioningPom.assignroleResponseAttributeMapping, 0, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "assignRoleresponseAttributeMapping1"));
        click(ProvisioningPom.assignroleaddButtonResponseAttribute);
        findElementByIndexAndPasteText(ProvisioningPom.assignroleResponseAttributeMapping, 1, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "assignRoleresponseAttributeMapping2"));
        click(ProvisioningPom.nextButton);
        staticWait(2000);
        enter(ProvisioningPom.getGroupApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "getGroupApiEndpoint"));
        click(ProvisioningPom.getGroupMethodType);
        staticWait(3000);
        findElementsAndActionClick(ProvisioningPom.getGroupSelectMethodType, "Get");
        enter(ProvisioningPom.getGroupResponsePayload, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "getGroupResponsePayload"));
        findElementByIndexAndPasteText(ProvisioningPom.getGroupResponseAttributeMapping, 0, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "getGroupresponseAttributeMapping1"));
        findElementByIndexAndPasteText(ProvisioningPom.getGroupResponseAttributeMapping, 1, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "getGroupresponseAttributeMapping2"));
        scrollByElement(ProvisioningPom.assignGroupApiEndpoint);
        enter(ProvisioningPom.assignGroupApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "assignGroupApiEndpoint"));
        click(ProvisioningPom.assignGroupMethodType);
        staticWait(3000);
        findElementsAndClick(ProvisioningPom.assignGroupSelectMethodType, "Post");
        enter(ProvisioningPom.assignGroupRequestPayload, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "assignGroupRequestPayload"));
        enter(ProvisioningPom.assignGroupResponsePayload, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "assignGroupResponsePayload"));
        findElementByIndexAndPasteText(ProvisioningPom.assignGroupResponseAttributeMapping, 0, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "assignGroupresponseAttributeMapping"));
        enter(ProvisioningPom.assignGroupPathVariable, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "assignGroupPathVariable"));
        click(ProvisioningPom.nextButton);
        staticWait(5000);
        safeClick(ProvisioningPom.skipButton);
        staticWait(5000);
        safeClick(ProvisioningPom.skipButton);
        staticWait(5000);
        enter(ProvisioningPom.provisioningApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "userDeprovisionEndPoint"));
        click(ProvisioningPom.provisioningMethodType);
        staticWait(3000);
        findElementsAndClick(ProvisioningPom.provisioningSelectMethodType, "DELETE");
        findElementByIndexAndPasteText(ProvisioningPom.DeprovisionPathVariable, 2, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "userPathVariable"));
        click(ProvisioningPom.nextButton);
        staticWait(5000);
        enter(ProvisioningPom.provisioningApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "roleDeprovisionEndpoint"));
        click(ProvisioningPom.provisioningMethodType);
        staticWait(3000);
        findElementsAndClick(ProvisioningPom.provisioningSelectMethodType, "DELETE");
        findElementByIndexAndPasteText(ProvisioningPom.DeprovisionPathVariable, 2, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "rolepathVariable1"));
        findElementByIndexAndPasteText(ProvisioningPom.DeprovisionPathVariable, 3, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "rolepathVariable2"));
        click(ProvisioningPom.nextButton);
        staticWait(2000);
        enter(ProvisioningPom.provisioningApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "groupDeprovisionEndpoint"));
        click(ProvisioningPom.provisioningMethodType);
        staticWait(3000);
        findElementsAndClick(ProvisioningPom.provisioningSelectMethodType, "DELETE");
        findElementByIndexAndPasteText(ProvisioningPom.DeprovisionPathVariable, 2, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "grouppathVariable1"));
        findElementByIndexAndPasteText(ProvisioningPom.DeprovisionPathVariable, 3, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "grouppathVariable2"));
        click(ProvisioningPom.groupdeprovisionPathVariableAdd);
        findElementByIndexAndPasteText(ProvisioningPom.DeprovisionPathVariable, 4, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "grouppathVariable3"));
        findElementByIndexAndPasteText(ProvisioningPom.DeprovisionPathVariable, 5, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "grouppathVariable4"));
        click(ProvisioningPom.nextButton);
        staticWait(2000);
        click(ProvisioningPom.browseFromConnector);
        click(ProvisioningPom.akkuConnector);
        click(ProvisioningPom.connectorAddButton);
        staticWait(1000);
        click(ProvisioningPom.nextButton);
        staticWait(2000);

        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 0);
        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 0, "first_name");
        findElementsAndClick(ProvisioningPom.selectAttribute, "first_name");
        staticWait(2000);

        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 1);
        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 1, "givenName");
        findElementsAndClick(ProvisioningPom.selectAttribute, "givenName");
        findElementByIndexAndClick(ProvisioningPom.attributeaddButton, 0);
        staticWait(2000);

        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 1);
        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 1, "last_name");
        findElementsAndClick(ProvisioningPom.selectAttribute, "last_name");
        staticWait(2000);
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 3);
        findElementsAndClick(ProvisioningPom.selectAttribute, "familyName");
        findElementByIndexAndClick(ProvisioningPom.attributeaddButton, 2);
        staticWait(2000);

        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 2);
        findElementsAndClick(ProvisioningPom.selectAttribute, "email");
        staticWait(2000);
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 5);
        findElementsAndClick(ProvisioningPom.selectAttribute, "primaryEmail");
        staticWait(2000);

        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 6);
        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 6, "sp_user_id");
        findElementsAndClick(ProvisioningPom.selectAttribute, "sp_user_id");
        staticWait(2000);
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 7);
        findElementsAndClick(ProvisioningPom.selectAttribute, "userKey");
        staticWait(2000);

        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 8);
        findElementsAndClick(ProvisioningPom.selectAttribute, "email");
        staticWait(2000);
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 9);
        findElementsAndClick(ProvisioningPom.selectAttribute, "email");
        findElementByIndexAndClick(ProvisioningPom.attributeaddButton, 5);
        staticWait(2000);

        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 9);
        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 9, "Sp_group_id");
        findElementsAndClick(ProvisioningPom.selectAttribute, "Sp_group_id");
        staticWait(2000);
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 11);
        findElementsAndClick(ProvisioningPom.selectAttribute, "group_id");
        staticWait(2000);

        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 16);
        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 16, "sp_role_id");
        findElementsAndClick(ProvisioningPom.selectAttribute, "sp_role_id");
        staticWait(2000);
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 17);
        findElementsAndClick(ProvisioningPom.selectAttribute, "roleId");
        findElementByIndexAndClick(ProvisioningPom.attributeaddButton, 11);
        staticWait(2000);

        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 17);
        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 17, "sp_user_id");
        findElementsAndClick(ProvisioningPom.selectAttribute, "sp_user_id");
        staticWait(2000);
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 19);
        findElementsAndClick(ProvisioningPom.selectAttribute, "assignedTo");
        staticWait(2000);

//        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 18);
//        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 18, "Sp_group_id");
//        findElementsAndClick(ProvisioningPom.selectAttribute, "Sp_group_id");
//        staticWait(2000);
//        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 19);
//        findElementsAndClick(ProvisioningPom.selectAttribute, "group_id");
//        staticWait(2000);
//        findElementByIndexAndClick(ProvisioningPom.attributeaddButton, 5);
//
//        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 20);
//        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 20, "sp_user_id");
//        findElementsAndClick(ProvisioningPom.selectAttribute, "sp_user_id");
//        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 21);
//        findElementsAndClick(ProvisioningPom.selectAttribute, "userKey");
        click(ProvisioningPom.nextButton);
        // assertEqualsnew(ProvisioningPom.attributesUpdatedMessage, "Attributes updated successfully.");

        staticWait(8000);
        actionClick(ProvisioningPom.skipButton);
        staticWait(8000);
        safeClick(ProvisioningPom.skipButton);
        staticWait(8000);
        safeClick(ProvisioningPom.skipButton);
        staticWait(6000);
        click(ProvisioningPom.confirmButton);
        assertEqualsnew(ProvisioningPom.provisioningConnectorCompleteMessage, "Provisioning configuration completed successfully.");

    }

    @Test(priority = 1)
    public static void editGwsMasterProvisioningConnector() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(5000);
        click(Login.signIn);
        staticWait(10000);
        jClick(App_Management_Pom.addApps.appStore);
        staticWait(8000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, threedot, Utils.ExcelDataReader.inputData("appstore", "key", "TC_003", "appName"));
        staticWait(3000);
        actionClick(ProvisioningPom.provisioningButton);
        click(App_Management_Pom.addApps.next);
        staticWait(6000);
        deleteAll(ProvisioningPom.Description);
        enter(ProvisioningPom.Description, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_002", "Description"));
        deleteAll(ProvisioningPom.SpDomain);
        enter(ProvisioningPom.SpDomain, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "Sp_Domain"));
        click(ProvisioningPom.nextButton);
        deleteAll(ProvisioningPom.serviceToken);
        enter(ProvisioningPom.serviceToken, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_002", "Service_Token"));
        deleteAll(ProvisioningPom.administratorEmail);
        enter(ProvisioningPom.administratorEmail, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_002", "administrator_username"));
        click(ProvisioningPom.testButton);
        click(ProvisioningPom.done);
        staticWait(3000);
        click(ProvisioningPom.nextButton);
        staticWait(4000);

        deleteAll(ProvisioningPom.provisioningApiEndpoint);
        enter(ProvisioningPom.provisioningApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "provisioningApiEndpoint"));
        click(ProvisioningPom.nextButton);
        staticWait(2000);

        deleteAll(ProvisioningPom.getRoleApiEndpoint);
        enter(ProvisioningPom.getRoleApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "getRoleApiEndpoint"));
        click(ProvisioningPom.nextButton);
        staticWait(2000);

        deleteAll(ProvisioningPom.getGroupApiEndpoint);
        enter(ProvisioningPom.getGroupApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "getGroupApiEndpoint"));
        click(ProvisioningPom.nextButton);
        staticWait(5000);
        safeClick(ProvisioningPom.skipButton);
        staticWait(5000);
        safeClick(ProvisioningPom.skipButton);
        staticWait(5000);

        deleteAll(ProvisioningPom.provisioningApiEndpoint);
        enter(ProvisioningPom.provisioningApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "userDeprovisionEndPoint"));
        click(ProvisioningPom.nextButton);
        staticWait(2000);

        deleteAll(ProvisioningPom.provisioningApiEndpoint);
        enter(ProvisioningPom.provisioningApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "roleDeprovisionEndpoint"));
        click(ProvisioningPom.nextButton);
        staticWait(2000);

        deleteAll(ProvisioningPom.provisioningApiEndpoint);
        enter(ProvisioningPom.provisioningApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_001", "groupDeprovisionEndpoint"));
        click(ProvisioningPom.nextButton);
        staticWait(2000);
        click(ProvisioningPom.nextButton);
        staticWait(2000);
        click(ProvisioningPom.nextButton);
        staticWait(6000);
        actionClick(ProvisioningPom.skipButton);
        staticWait(5000);
        safeClick(ProvisioningPom.skipButton);
        staticWait(5000);
        safeClick(ProvisioningPom.skipButton);
        staticWait(5000);
        click(ProvisioningPom.confirmButton);
        assertEqualsnew(ProvisioningPom.provisioningConnectorCompleteMessage, "Provisioning configuration completed successfully.");

    }

    @Test(priority = 2)
    public static void publishGwsMasterProvisioningConnector() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(5000);
        click(Login.signIn);
        staticWait(10000);
        jClick(App_Management_Pom.addApps.appStore);
        staticWait(8000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, publish, Utils.ExcelDataReader.inputData("appstore", "key", "TC_003", "appName"));
        assertEqualsnew(ProvisioningPom.provisionPublishMessage, "Provisioning engine published successfully.");
    }

    //@Test(priority = 3)
    public static void deleteGwsMasterProvisioningConnector() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(5000);
        click(Login.signIn);
        staticWait(10000);
        actionClick(App_Management_Pom.addApps.appStore);
        staticWait(5000);
        clickOnButtonByNameAndText(App_Management_Pom.addApps.configureApps, ProvisioningPom.provisionActiveInactiveToggle1, Utils.ExcelDataReader.inputData("appstore", "key", "TC_002", "appName"), "Active");
        staticWait(3000);
        clickOnButtonByNameAndText(App_Management_Pom.addApps.configureApps, ProvisioningPom.provisionActiveInactiveToggle2, Utils.ExcelDataReader.inputData("appstore", "key", "TC_002", "appName"), "Inactive");
        assertEqualsnew(ProvisioningPom.provisionDeactivateMessage, "Provisioning engine deactivated successfully.");
        staticWait(5000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, threedot, Utils.ExcelDataReader.inputData("appstore", "key", "TC_002", "appName"));
        click(ProvisioningPom.deleteButton);
        click(ProvisioningPom.deleteYesButton);
        assertEqualsnew(ProvisioningPom.provisionDeleteMessage, "Provisioning engine deleted successfully.");
    }

    @Test(priority = 4)
    public void createJiraMasterConnector() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(5000);
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

        staticWait(2000);
        enter(ProvisioningPom.Description, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "Description"));
        enter(ProvisioningPom.SpDomain, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "Sp_Domain"));
        click(ProvisioningPom.authorizationType);
        staticWait(2000);
        findElementsAndClick(ProvisioningPom.selectAuthorizationtype, "Basic Auth");
        click(ProvisioningPom.tokenType);
        staticWait(2000);
        findElementsAndClick(ProvisioningPom.selectTokenType, "API Token");
        staticWait(3000);
        click(ProvisioningPom.nextButton);
        enter(ProvisioningPom.apiToken, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "Api_Token"));
        enter(ProvisioningPom.apiEndpointUrl, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "token_endpoint_URL"));
        click(ProvisioningPom.tokenMethodType);
        staticWait(3000);
        findElementsAndClick(ProvisioningPom.provisioningSelectMethodType, "Post");
        enter(ProvisioningPom.apiTokenUsername, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "administrator_username"));

        click(ProvisioningPom.testButton);
        click(ProvisioningPom.done);
        staticWait(3000);
        click(ProvisioningPom.nextButton);
        staticWait(4000);

        enter(ProvisioningPom.provisioningApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "provisioningApiEndpoint"));
        click(ProvisioningPom.provisioningMethodType);
        staticWait(3000);
        findElementsAndClick(ProvisioningPom.provisioningSelectMethodType, "Post");
        enter(ProvisioningPom.provisioningRequestPayload, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "provisioningRequestPayload"));
        enter(ProvisioningPom.provisioningResponsePayload, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "provisioningResponsePayload"));
        findElementByIndexAndPasteText(ProvisioningPom.provisionResponseAttrbuteMapping, 0, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "responseAttributeMapping1"));
        click(ProvisioningPom.nextButton);
        staticWait(2000);
        click(ProvisioningPom.skipButton);
        staticWait(3000);
        enter(ProvisioningPom.getGroupApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "getGroupApiEndpoint"));
        click(ProvisioningPom.getGroupMethodType);
        staticWait(3000);
        findElementsAndActionClick(ProvisioningPom.getGroupSelectMethodType, "Get");
        enter(ProvisioningPom.getGroupResponsePayload, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "getGroupResponsePayload"));
        findElementByIndexAndPasteText(ProvisioningPom.getGroupResponseAttributeMapping, 0, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "getGroupresponseAttributeMapping2"));
        findElementByIndexAndPasteText(ProvisioningPom.getGroupResponseAttributeMapping, 1, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "getGroupresponseAttributeMapping1"));
        scrollByElement(ProvisioningPom.assignGroupApiEndpoint);
        enter(ProvisioningPom.assignGroupApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "assignGroupApiEndpoint"));
        click(ProvisioningPom.assignGroupMethodType);
        staticWait(3000);
        findElementsAndClick(ProvisioningPom.assignGroupSelectMethodType, "Post");
        enter(ProvisioningPom.assignGroupRequestPayload, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "assignGroupRequestPayload"));
        enter(ProvisioningPom.assignGroupResponsePayload, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "assignGroupResponsePayload"));
        findElementByIndexAndPasteText(ProvisioningPom.assignGroupResponseAttributeMapping, 0, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "assignGroupresponseAttributeMapping"));
        findElementByIndexAndPasteText(ProvisioningPom.assignGroupRequestVariable, 0, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "assignGroupRequestVariable1"));
        findElementByIndexAndPasteText(ProvisioningPom.assignGroupRequestVariable, 1, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "assignGroupRequestVariable2"));
        click(ProvisioningPom.nextButton);
        staticWait(5000);
        safeClick(ProvisioningPom.skipButton);
        staticWait(5000);
        safeClick(ProvisioningPom.skipButton);
        staticWait(5000);
        enter(ProvisioningPom.provisioningApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "userDeprovisionEndPoint"));
        click(ProvisioningPom.provisioningMethodType);
        staticWait(3000);
        findElementsAndClick(ProvisioningPom.provisioningSelectMethodType, "DELETE");
        findElementByIndexAndPasteText(ProvisioningPom.deprovisionRequestVariable, 0, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "userRequestVariable"));
        click(ProvisioningPom.nextButton);
        staticWait(5000);
        safeClick(ProvisioningPom.skipButton);
        staticWait(2000);
        enter(ProvisioningPom.provisioningApiEndpoint, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "groupDeprovisionEndpoint"));
        click(ProvisioningPom.provisioningMethodType);
        staticWait(3000);
        findElementsAndClick(ProvisioningPom.provisioningSelectMethodType, "DELETE");
        findElementByIndexAndPasteText(ProvisioningPom.DeprovisionPathVariable, 0, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "groupRequestVariable1"));
        findElementByIndexAndPasteText(ProvisioningPom.DeprovisionPathVariable, 1, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "groupRequestVariable2"));
        click(ProvisioningPom.groupdeprovisionRequestVariableAdd);
        findElementByIndexAndPasteText(ProvisioningPom.DeprovisionPathVariable, 2, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "groupRequestVariable3"));
        findElementByIndexAndPasteText(ProvisioningPom.DeprovisionPathVariable, 3, Utils.ExcelDataReader.inputData("Provisioning", "key", "TC_003", "groupRequestVariable4"));
        click(ProvisioningPom.nextButton);
        staticWait(2000);
        click(ProvisioningPom.browseFromConnector);
        click(ProvisioningPom.akkuConnector);
        click(ProvisioningPom.connectorAddButton);
        staticWait(1000);
        click(ProvisioningPom.nextButton);
        staticWait(2000);
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 0);
        findElementsAndClick(ProvisioningPom.selectAttribute, "email");
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 1);
        findElementsAndClick(ProvisioningPom.selectAttribute, "emailAddress");

        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 2);
        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 2, "sp_user_id");
        findElementsAndClick(ProvisioningPom.selectAttribute, "sp_user_id");
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 3);
        findElementsAndClick(ProvisioningPom.selectAttribute, "accountId");


        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 4);
        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 4, "sp_user_id");
        findElementsAndClick(ProvisioningPom.selectAttribute, "sp_user_id");
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 5);
        findElementsAndClick(ProvisioningPom.selectAttribute, "accountId");
        findElementByIndexAndClick(ProvisioningPom.attributeaddButton, 2);

        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 5);
        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 5, "sp_group_id");
        findElementsAndClick(ProvisioningPom.selectAttribute, "sp_group_id");
        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 7);
        findElementsAndClick(ProvisioningPom.selectAttribute, "groupId");

//        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 8);
//        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 8, "sp_user_id");
//        findElementsAndClick(ProvisioningPom.selectAttribute, "sp_user_id");
//        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 9);
//        findElementsAndClick(ProvisioningPom.selectAttribute, "accountId");
//        findElementByIndexAndClick(ProvisioningPom.attributeaddButton, 5);
//
//        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 9);
//        findElementByIndexAndPasteText(ProvisioningPom.sourceandtargetAttributedropdown2, 9, "sp_group_id");
//        findElementsAndClick(ProvisioningPom.selectAttribute, "sp_group_id");
//        findElementByIndexAndClick(ProvisioningPom.sourceandtargetAttributedropdown, 11);
//        findElementsAndClick(ProvisioningPom.selectAttribute, "groupId");
        click(ProvisioningPom.nextButton);
        //assertEqualsnew(ProvisioningPom.attributesUpdatedMessage, "Attributes updated successfully.");

        staticWait(8000);
        actionClick(ProvisioningPom.skipButton);
        staticWait(7000);
        safeClick(ProvisioningPom.skipButton);
        staticWait(4000);
        safeClick(ProvisioningPom.skipButton);
        staticWait(4000);
        click(ProvisioningPom.confirmButton);
        assertEqualsnew(ProvisioningPom.provisioningConnectorCompleteMessage, "Provisioning configuration completed successfully.");

    }

    @Test(priority = 5)
    public static void publishJiraMasterProvisioningConnector() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(5000);
        click(Login.signIn);
        staticWait(10000);
        jClick(App_Management_Pom.addApps.appStore);
        staticWait(8000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, publish, Utils.ExcelDataReader.inputData("appstore", "key", "TC_001", "appName"));
        assertEqualsnew(ProvisioningPom.provisionPublishMessage, "Provisioning engine published successfully.");
    }

    //@Test(priority = 6)
    public static void deleteJiraMasterProvisioningConnector() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(5000);
        click(Login.signIn);
        staticWait(10000);
        actionClick(App_Management_Pom.addApps.appStore);
        staticWait(5000);
        clickOnButtonByNameAndText(App_Management_Pom.addApps.configureApps, ProvisioningPom.provisionActiveInactiveToggle1, Utils.ExcelDataReader.inputData("appstore", "key", "TC_001", "appName"), "Active");
        staticWait(3000);
        clickOnButtonByNameAndText(App_Management_Pom.addApps.configureApps, ProvisioningPom.provisionActiveInactiveToggle2, Utils.ExcelDataReader.inputData("appstore", "key", "TC_001", "appName"), "Inactive");
        assertEqualsnew(ProvisioningPom.provisionDeactivateMessage, "Provisioning engine deactivated successfully.");
        staticWait(5000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, threedot, Utils.ExcelDataReader.inputData("appstore", "key", "TC_001", "appName"));
        click(ProvisioningPom.deleteButton);
        click(ProvisioningPom.deleteYesButton);
        assertEqualsnew(ProvisioningPom.provisionDeleteMessage, "Provisioning engine deleted successfully.");
    }
}
