package Akku;

import Locators.Customer;
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

import static Locators.Customer.CustomerCreation.*;

public class TenantandMsspCreation extends Base {

    @BeforeSuite
    public void beforeSuite() throws IOException {
        initialiseExtentReportsSuite();
    }

    @BeforeMethod
    public void beforeMethod(ITestResult result, ITestContext context, Object[] testData) throws Exception {
        String tenantUrl = (String) testData[0];
        TestContext.setTenant(tenantUrl);

        launchUrl("chrome", tenantUrl);

        // Assign tenant-specific ExtentReports to class-level variable

        extentReports = ExtentManager.getExtent(tenantUrl);

        // Create ExtentTest entry for this test
        initialiseExtentReportsMethodNew(result, context);
    }




    @AfterMethod
    public void afterMethod(ITestResult result) {

        String tenantUrl = TestContext.getTenant();
        boolean pass = result.getStatus() == ITestResult.SUCCESS;

        if (tenantUrl != null) {
            TenantResult.results.merge(tenantUrl, pass, (oldVal, newVal) -> oldVal && newVal);
        }

        // Flush ExtentTest logs to tenant-specific report
        extentReportAfterMethod(result);

        tearDown();
        TestContext.clearTenant();
    }

    @AfterSuite
    public void writeTenantSummary() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("target/tenant-summary.json"), TenantResult.results);

        // Flush all tenant reports just in case
        ExtentManager.flushAll();
    }

    @Test(priority = 0)
    public void createTenantWithBasicPlan() throws AWTException, InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(2000);
        click(Login.signIn);
        staticWait(8000);
        scrollByElement(AddIcon);
        jClick(AddIcon);
        enter(Customer.CustomerCreation.AddCustomerId, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddCustomerId"));
        enter(Customer.CustomerCreation.AddDisplayName, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddDisplayName"));
        enter(Customer.CustomerCreation.AddCustomerEmailId, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddCustomerEmailId"));
        enter(Customer.CustomerCreation.AddNoofLicense, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddNoofLicense"));
        click(AddLoginTheme);
        EnterButton(selectLoginTheme);
        deleteAll(Customer.CustomerCreation.AddMobileNumber);
        enter(Customer.CustomerCreation.AddMobileNumber, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddMobileNumber"));
        //click(AddNextButton);
        click(Customer.CustomerCreation.region);
        click(restOfTheWorld);
        click(AddTestButton);
        click(AddPromptAlertAccept);
        // click(AddTestButton);
        click(AddNextButtonClick);
        Thread.sleep(3000);
        enter(Customer.CustomerCreation.AddSenderEmailAddress, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddSenderEmail"));
        enter(AddEmailSettingdisplayname, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddEmailSettingsDisplayName"));
        enter(AddReplytoEmail, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddReplytoEmailAddress"));
        enter(AddReplytoDisplayName, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddReplytoDisplayName"));
        click(AddNextButtonClick);

//      enter(SSOSessionIdle, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "SSOSessionIdle"));
//      enter(SSOSessionTimeout, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "ssoSessionIdleTimeoutRememberMe"));
//      enter(Customer.CustomerCreation.SSOSessionMaxLifeSpan, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "SSOSessionMaxRememberme"));
//      enter(SSOSessionMaxRememberme, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "ssoSessionMaxLifespan"));
//      enter(SSOLoginTimeOut, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "SSOSessionMaxRememberme"));
//      enter(SSOLogingActionTimeout, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "SSOLogingActionTimeout"));

        click(AddNextButtonClick);
        Thread.sleep(3000);
        click(SelectBasicplanCheckBox);
        click(PlanSaveButton);
        assertEqualsnew(Customer.CustomerCreation.tenantCreationMessage, "Customer creation process initiated. You will receive an email notification upon completion.");
        staticWait(70000);

        enter(SearchPlaceholder, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddDisplayName"));
        staticWait(2000);
        assertTextFromMultipleElements(retreiveTenantList, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddDisplayName"));

    }

    @Test(priority = 1)
    public void createTenantWithProfessionalPlan() throws AWTException, InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(2000);
        click(Login.signIn);

        scrollByElement(AddIcon);
        jClick(AddIcon);
        enter(Customer.CustomerCreation.AddCustomerId, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "AddCustomerId"));
        enter(Customer.CustomerCreation.AddDisplayName, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "AddDisplayName"));
        enter(Customer.CustomerCreation.AddCustomerEmailId, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "AddCustomerEmailId"));
        enter(Customer.CustomerCreation.AddNoofLicense, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "AddNoofLicense"));
        click(AddLoginTheme);
        EnterButton(selectLoginTheme);
        deleteAll(Customer.CustomerCreation.AddMobileNumber);
        enter(Customer.CustomerCreation.AddMobileNumber, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "AddMobileNumber"));

        click(Customer.CustomerCreation.region);
        click(restOfTheWorld);
        click(AddTestButton);
        click(AddPromptAlertAccept);
        click(AddNextButtonClick);
        Thread.sleep(3000);
        enter(Customer.CustomerCreation.AddSenderEmailAddress, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "AddSenderEmail"));
        enter(AddEmailSettingdisplayname, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "AddEmailSettingsDisplayName"));
        enter(AddReplytoEmail, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "AddReplytoEmailAddress"));
        enter(AddReplytoDisplayName, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "AddReplytoDisplayName"));
        click(AddNextButtonClick);
//        enter(SSOSessionIdle, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "SSOSessionIdle"));
//        enter(SSOSessionTimeout, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "ssoSessionIdleTimeoutRememberMe"));
//        enter(Customer.CustomerCreation.SSOSessionMaxLifeSpan, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "SSOSessionMaxRememberme"));
//        enter(SSOSessionMaxRememberme, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "ssoSessionMaxLifespan"));
//        enter(SSOLoginTimeOut, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "SSOSessionMaxRememberme"));
//        enter(SSOLogingActionTimeout, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "SSOLogingActionTimeout"));
        staticWait(3000);
        click(AddNextButtonClick);
        Thread.sleep(3000);
        click(SelectProfessionalplanCheckbox);
        Thread.sleep(3000);
        click(PlanSaveButton);
        assertEqualsnew(Customer.CustomerCreation.tenantCreationMessage, "Customer creation process initiated. You will receive an email notification upon completion.");
        staticWait(70000);

        enter(SearchPlaceholder, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "AddDisplayName"));
        staticWait(2000);
        assertTextFromMultipleElements(retreiveTenantList, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_002", "AddDisplayName"));

    }

    @Test(priority = 2)
    public void createTenantWithAdvancedPlan() throws AWTException, InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(2000);
        click(Login.signIn);
        scrollByElement(AddIcon);
        jClick(AddIcon);
        enter(Customer.CustomerCreation.AddCustomerId, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddCustomerId"));
        enter(Customer.CustomerCreation.AddDisplayName, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddDisplayName"));
        enter(Customer.CustomerCreation.AddCustomerEmailId, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddCustomerEmailId"));
        enter(Customer.CustomerCreation.AddNoofLicense, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddNoofLicense"));
        click(AddLoginTheme);
        EnterButton(selectLoginTheme);
        deleteAll(Customer.CustomerCreation.AddMobileNumber);
        enter(Customer.CustomerCreation.AddMobileNumber, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddMobileNumber"));
        staticWait(3000);
        click(Customer.CustomerCreation.region);
        click(restOfTheWorld);
        click(AddTestButton);
        click(AddPromptAlertAccept);
        click(AddNextButtonClick);
        Thread.sleep(3000);
        enter(Customer.CustomerCreation.AddSenderEmailAddress, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddSenderEmail"));
        enter(AddEmailSettingdisplayname, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddEmailSettingsDisplayName"));
        enter(AddReplytoEmail, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddReplytoEmailAddress"));
        enter(AddReplytoDisplayName, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddReplytoDisplayName"));
        click(AddNextButtonClick);
//        enter(SSOSessionIdle, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "SSOSessionIdle"));
//        enter(SSOSessionTimeout, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "ssoSessionIdleTimeoutRememberMe"));
//        enter(Customer.CustomerCreation.SSOSessionMaxLifeSpan, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "SSOSessionMaxRememberme"));
//        enter(SSOSessionMaxRememberme, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "ssoSessionMaxLifespan"));
//        enter(SSOLoginTimeOut, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "SSOSessionMaxRememberme"));
//        enter(SSOLogingActionTimeout, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "SSOLogingActionTimeout"));
        staticWait(3000);
        click(AddNextButtonClick);
        Thread.sleep(3000);
        jClick(SelectAdvancedplanCheckbox);
        Thread.sleep(5000);
        click(PlanSaveButton);
        assertEqualsnew(Customer.CustomerCreation.tenantCreationMessage, "Customer creation process initiated. You will receive an email notification upon completion.");
        staticWait(70000);

        enter(SearchPlaceholder, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddDisplayName"));
        staticWait(2000);
        assertTextFromMultipleElements(retreiveTenantList, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddDisplayName"));


    }

    @Test(priority = 3)
    public static void updateTenantEmailSettings() throws InterruptedException {


        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(2000);
        click(Login.signIn);
        scrollByElement(AddIcon);
        staticWait(3000);
        click(SearchPlaceholder);
        enter(SearchPlaceholder, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddDisplayName"));
        staticWait(2000);
        findElementsAndClick(retreiveTenantList, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddDisplayName"));
        staticWait(3000);
        click(details);

        scrollByElement(AddReplytoEmail);
        staticWait(3000);
        deleteAll(AddReplytoEmail);
        enter(AddReplytoEmail, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_006", "AddReplytoEmailAddress"));
        staticWait(3000);
        deleteAll(AddReplytoDisplayName);
        staticWait(3000);
        enter(AddReplytoDisplayName, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_006", "AddReplytoDisplayName"));
        click(UserManagementPom.groups.update);
        assertEqualsnew(tenantUpdatedMessage, "Tenant updated successfully.");

    }

    @Test(priority = 4)
    public static void updateTenantSsoTimingSettings() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(2000);
        click(Login.signIn);
        scrollByElement(AddIcon);
        staticWait(3000);
        click(SearchPlaceholder);
        enter(SearchPlaceholder, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddDisplayName"));
        staticWait(2000);
        findElementsAndClick(retreiveTenantList, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddDisplayName"));
        staticWait(3000);
        click(details);
        staticWait(3000);

        deleteAll(SSOSessionIdle);
        staticWait(3000);
        enter(SSOSessionIdle, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "SSOSessionIdle"));
        staticWait(3000);
        deleteAll(SSOLogingActionTimeout);
        staticWait(3000);
        enter(SSOLogingActionTimeout, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "SSOLogingActionTimeout"));

        click(UserManagementPom.groups.update);
        assertEqualsnew(tenantUpdatedMessage, "Tenant updated successfully.");

    }

    @Test(priority = 5)
    public static void updateTenantPlan() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(2000);
        click(Login.signIn);
        scrollByElement(AddIcon);
        staticWait(3000);
        click(SearchPlaceholder);
        enter(SearchPlaceholder, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddDisplayName"));
        staticWait(2000);
        findElementsAndClick(retreiveTenantList, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddDisplayName"));
        staticWait(3000);
        click(details);
        staticWait(3000);

        click(SelectProfessionalplanCheckbox);
        click(UserManagementPom.groups.update);
        assertEqualsnew(tenantUpdatedMessage, "Tenant updated successfully.");

    }

    @Test(priority = 6)
    public static void verifyDomainExistsMessage() throws AWTException, InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(2000);
        click(Login.signIn);

        scrollByElement(AddIcon);
        staticWait(4000);
        click(AddIcon);
        enter(Customer.CustomerCreation.AddCustomerId, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddCustomerId"));
        enter(Customer.CustomerCreation.AddDisplayName, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddDisplayName"));
        enter(Customer.CustomerCreation.AddCustomerEmailId, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddCustomerEmailId"));
        enter(Customer.CustomerCreation.AddNoofLicense, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddNoofLicense"));
        click(AddLoginTheme);
        EnterButton(selectLoginTheme);
        deleteAll(Customer.CustomerCreation.AddMobileNumber);
        enter(Customer.CustomerCreation.AddMobileNumber, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_001", "AddMobileNumber"));
        staticWait(3000);
        click(SelectRegion);
        click(restOfTheWorld);
        staticWait(3000);
        click(AddTestButton);
        assertEqualsnew(DomainexistsPopUp, "Domain exists.");

    }

    @Test(priority = 7)
    public static void updateCustomerasMSSP() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(2000);
        click(Login.signIn);

        scrollByElement(AddIcon);
        staticWait(3000);
        click(SearchPlaceholder);
        enter(SearchPlaceholder, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddDisplayName"));
        staticWait(2000);
        findElementsAndClick(retreiveTenantList, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddDisplayName"));
        staticWait(3000);
        click(details);
        staticWait(5000);
        click(msspOrClientToggle);
        click(UserManagementPom.groups.update);
        assertEqualsnew(tenantUpdatedMessage, "Tenant updated successfully.");

    }

    @Test(priority = 8)
    public static void updateTenantGeneralDetails() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(2000);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "masterPassword"));
        staticWait(2000);
        click(Login.signIn);

        scrollByElement(AddIcon);
        staticWait(3000);
        click(SearchPlaceholder);
        enter(SearchPlaceholder, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddDisplayName"));
        staticWait(2000);
        findElementsAndClick(retreiveTenantList, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_003", "AddDisplayName"));
        staticWait(3000);
        click(details);
        staticWait(5000);
        deleteAll(AddNoofLicense);
        enter(AddNoofLicense, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_006", "AddNoofLicense"));
        deleteAll(Customer.CustomerCreation.AddMobileNumber);
        enter(Customer.CustomerCreation.AddMobileNumber, ExcelDataReader.inputData("CustomerCreation", "Fields", "TC_006", "AddMobileNumber"));
        staticWait(5000);
        click(AddLoginTheme);
        EnterButton(selectLoginTheme);
        click(UserManagementPom.groups.update);
        assertEqualsnew(tenantUpdatedMessage, "Tenant updated successfully.");

    }

}
