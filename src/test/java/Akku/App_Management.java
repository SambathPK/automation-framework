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

public class App_Management extends Base {

    private final String tenantUrl;

    public App_Management(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new App_Management(tenantUrl)};
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

    @Test(priority = 0, description = "Verify that the tenant admin can configure a SAML SSO application.")
    public static void configureSamlSsoApplication() throws InterruptedException {

        loginAndNavigateToAppStore();
        staticWait(3000);
        enter(ProvisioningPom.searchApps, Utils.ExcelDataReader.inputData("appstore", "key", "TC_003", "appName"));
        staticWait(1500);
        jClick(setupButton);
        actionClick(sso);
        actionClick(configure);
        staticWait(1500);
        fillSamlSsoFields("TC_002");
        click(App_Management_Pom.addApps.save);
        assertEqualsnew(App_Management_Pom.addApps.AddNotificationSavedSuccessfully, "App saved successfully.");
    }

    @Test(priority = 1, description = "Verify that an error message is displayed if the client ID already exists for a SAML application.")
    public static void clientAlreadyExistsForSamlApplication() throws InterruptedException {

        loginAndNavigateToAppStore();
        staticWait(3000);
        enter(ProvisioningPom.searchApps, Utils.ExcelDataReader.inputData("appstore", "key", "TC_003", "appName"));
        staticWait(1500);
        jClick(setupButton);
        actionClick(sso);
        click(App_Management_Pom.addApps.next);
        staticWait(1500);
        fillSamlSsoFields("TC_002");
        click(App_Management_Pom.addApps.save);
        assertEqualsnew(ClientAlreadyExistPopup, "Client ID already exists.");
    }

    @Test(priority = 2, description = "Verify that the tenant admin can edit an existing SAML SSO application.")
    public static void editSamlSsoApplication() throws InterruptedException {

        loginAndNavigateToAppStore();
        staticWait(3000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, threedot, Utils.ExcelDataReader.inputData("appstore", "key", "TC_003", "appName"));
        staticWait(1500);
        actionClick(sso);
        click(App_Management_Pom.addApps.next);
        staticWait(1500);
        updateSamlSsoFields("TC_003");
        click(save);
        assertEqualsnew(App_Management_Pom.addApps.notificationMessage, "App updated successfully.");
    }

    @Test(priority = 3, description = "Verify that the tenant admin can activate and deactivate a SAML SSO application.")
    public static void activateSamlSsoApp() throws InterruptedException {

        loginAndNavigateToAppStore();
        staticWait(3000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, ssoActiveInactiveToggle1, Utils.ExcelDataReader.inputData("appstore", "key", "TC_003", "appName"));
        click(ProvisioningPom.deleteYesButton);
        assertEqualsnew(EnableNotification, "App display in console status updated successfully.");

        staticWait(5000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, ssoActiveInactiveToggle2, Utils.ExcelDataReader.inputData("appstore", "key", "TC_003", "appName"));
        assertEqualsnew(EnableNotification, "App display in console status updated successfully.");
    }

    // @Test(priority = 7, description = "Verify that the tenant admin can delete a SAML SSO application.")
    public static void deleteSamlSsoApplication() throws InterruptedException {

        loginAndNavigateToAppStore();
        staticWait(5000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, threedot, getAppName("TC_002"));
        click(deleteButton);
        click(yesButton);
        assertEqualsnew(DeletedNotification, "App deleted successfully.");
    }

    @Test(priority = 4, description = "Verify that the tenant admin can configure an OpenID SSO application.")
    public static void configureOpenidSsoApplication() throws InterruptedException {

        loginAndNavigateToAppStore();
        staticWait(3000);
        enter(ProvisioningPom.searchApps, Utils.ExcelDataReader.inputData("appstore", "key", "TC_004", "appName"));
        staticWait(1500);
        jClick(setupButton);
        staticWait(1500);
        actionClick(sso);
        actionClick(configure);
        staticWait(1500);
        click(OpenID);
        fillOpenidSsoFields("TC_004");
        click(save);
        assertEqualsnew(App_Management_Pom.addApps.AddNotificationSavedSuccessfully, "App saved successfully.");
    }

    @Test(priority = 5, description = "Verify that an error message is displayed if the client ID already exists for an OpenID application.")
    public static void clientAlreadyExistsForOpenidApplication() throws InterruptedException {

        loginAndNavigateToAppStore();
        staticWait(3000);
        enter(ProvisioningPom.searchApps, Utils.ExcelDataReader.inputData("appstore", "key", "TC_004", "appName"));
        staticWait(1500);
        jClick(setupButton);
        staticWait(1500);
        actionClick(sso);
        actionClick(configure);
        staticWait(1500);
        click(OpenID);
        fillOpenidSsoFields("TC_004");
        click(save);
        assertEqualsnew(ClientAlreadyExistPopup, "Client ID already exists.");
    }

    @Test(priority = 6, description = "Verify that the tenant admin can edit an existing OpenID SSO application.")
    public static void editOpenidSsoApplication() throws InterruptedException {

        loginAndNavigateToAppStore();
        staticWait(3000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, threedot, Utils.ExcelDataReader.inputData("appstore", "key", "TC_004", "appName"));
        staticWait(1500);
        actionClick(sso);
        click(App_Management_Pom.addApps.next);
        updateOpenidSsoFields("TC_005");
        click(save);
        assertEqualsnew(App_Management_Pom.addApps.notificationMessage, "App updated successfully.");
    }

    @Test(priority = 7, description = "Verify that the tenant admin can activate and deactivate an OpenID SSO application.")
    public static void activateOpenidSsoApp() throws InterruptedException {

        loginAndNavigateToAppStore();
        staticWait(3000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, ssoActiveInactiveToggle1, Utils.ExcelDataReader.inputData("appstore", "key", "TC_004", "appName"));
        click(ProvisioningPom.deleteYesButton);
        assertEqualsnew(EnableNotification, "App display in console status updated successfully.");

        staticWait(6000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, ssoActiveInactiveToggle2, Utils.ExcelDataReader.inputData("appstore", "key", "TC_004", "appName"));
        assertEqualsnew(EnableNotification, "App display in console status updated successfully.");
    }

    @Test(priority = 8, description = "Verify that the tenant admin can delete an OpenID SSO application.")
    public static void deleteOpenidSsoApplication() throws InterruptedException {

        loginAndNavigateToAppStore();
        staticWait(3000);
        clickOnSpecificElementByTextNew(App_Management_Pom.addApps.configureApps, deleteButton, Utils.ExcelDataReader.inputData("appstore", "key", "TC_004", "appName"));
        click(ProvisioningPom.deleteYesButton);
        assertEqualsnew(DeletedNotification, "App deleted successfully.");
    }

// Helper Methods

    private static void loginAndNavigateToAppStore() throws InterruptedException {
        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(3000); // Static wait after login
        jClick(App_Management_Pom.addApps.appStore);
    }

    private static String getAppName(String testCase) {
        return Utils.ExcelDataReader.inputData("appstore", "key", testCase, "appName");
    }

    private static void fillSamlSsoFields(String testCase) {
        enter(App_Management_Pom.addApps.clientId, Utils.ExcelDataReader.inputData("appstore", "key", testCase, "clientId"));
        enter(App_Management_Pom.addApps.homeURL, Utils.ExcelDataReader.inputData("appstore", "key", testCase, "homeURL"));
        enter(App_Management_Pom.addApps.SamlValidRedirectURL, Utils.ExcelDataReader.inputData("appstore", "key", testCase, "validRedirectURL"));
        enter(App_Management_Pom.addApps.masterSAMLProcessingURL, Utils.ExcelDataReader.inputData("appstore", "key", testCase, "masterSAML"));
        enter(App_Management_Pom.addApps.postBindingURL, Utils.ExcelDataReader.inputData("appstore", "key", testCase, "postBindingURL"));
        enter(App_Management_Pom.addApps.consumerRedirectURL, Utils.ExcelDataReader.inputData("appstore", "key", testCase, "redirectBindingURL"));
    }

    private static void updateSamlSsoFields(String testCase) {
        deleteAll(App_Management_Pom.addApps.clientId);
        enter(App_Management_Pom.addApps.clientId, Utils.ExcelDataReader.inputData("appstore", "key", testCase, "clientId"));
        deleteAll(App_Management_Pom.addApps.homeURL);
        enter(App_Management_Pom.addApps.homeURL, Utils.ExcelDataReader.inputData("appstore", "key", testCase, "homeURL"));
        deleteAll(App_Management_Pom.addApps.validRedirectURL);
        enter(App_Management_Pom.addApps.validRedirectURL, Utils.ExcelDataReader.inputData("appstore", "key", testCase, "validRedirectURL"));
        deleteAll(App_Management_Pom.addApps.masterSAMLProcessingURL);
        enter(App_Management_Pom.addApps.masterSAMLProcessingURL, Utils.ExcelDataReader.inputData("appstore", "key", testCase, "masterSAML"));
        deleteAll(App_Management_Pom.addApps.postBindingURL);
        enter(App_Management_Pom.addApps.postBindingURL, Utils.ExcelDataReader.inputData("appstore", "key", testCase, "postBindingURL"));
        deleteAll(App_Management_Pom.addApps.consumerRedirectURL);
        enter(App_Management_Pom.addApps.consumerRedirectURL, Utils.ExcelDataReader.inputData("appstore", "key", testCase, "redirectBindingURL"));
    }

    private static void fillOpenidSsoFields(String testCase) {
        enter(App_Management_Pom.addApps.OpenIDClientID, ExcelDataReader.inputData("appstore", "key", testCase, "clientId"));
        enter(HomeLoginURL, ExcelDataReader.inputData("appStore", "key", testCase, "homeURL"));
        enter(OpenIdValidredirectURL, ExcelDataReader.inputData("appStore", "key", testCase, "validRedirectURL"));
        scrollByElement(FrontChannellogout);
        click(FrontChannellogout);
        enter(FrontChannellogout, ExcelDataReader.inputData("appstore", "key", testCase, "FrontChannellogout"));
    }

    private static void updateOpenidSsoFields(String testCase) {
        deleteAll(OpenIDClientID);
        enter(OpenIDClientID, ExcelDataReader.inputData("appStore", "key", testCase, "clientId"));
    }

    private static void toggleSsoActivation(String appName, String status) throws InterruptedException {
        clickOnButtonByNameAndText(App_Management_Pom.addApps.configureApps, ssoActiveInactiveToggle1, Utils.ExcelDataReader.inputData("appstore", "key", appName, "appName"), status);
    }

    private static void toggleSsoActivationSecond(String appName, String status) throws InterruptedException {
        clickOnButtonByNameAndText(App_Management_Pom.addApps.configureApps, ssoActiveInactiveToggle2, Utils.ExcelDataReader.inputData("appstore", "key", appName, "appName"), status);
    }


}

