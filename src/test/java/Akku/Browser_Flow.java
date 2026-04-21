package Akku;


import Locators.Login;
import Locators.UserManagementPom;
import Utils.*;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;

public class Browser_Flow extends Base {

    private final String tenantUrl;

    public Browser_Flow(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new Browser_Flow(tenantUrl)};
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

    @Test(description = "Verify that an admin user can successfully update the browser flow for the tenant by assigning factors.", priority = 0)
    public void verifyAdminCanUpdateBrowserFlowForTenantByAssigningFactors() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(Locators.Adaptive_MFA.adaptive_MFA_Section);
        staticWait(5000);
        findElementByIndexAndClick(Locators.Adaptive_MFA.selectFactorsForAuthentication, 1);
        findElementByIndexAndClick(Locators.Adaptive_MFA.selectFactorsForAuthentication, 2);
        jClick(Locators.Adaptive_MFA.updateButton);
        assertEqualsnew(UserManagementPom.createUser.userEnableAndDisableToastMessage, "Adaptive MFA update is in progress. You'll be notified once it is complete.");

    }

    @Test(description = "Verify that an admin user can successfully revert the adaptive MFA browser flow to the default flow without factors.", priority = 1)
    public void verifyAdminCanRevertToDefaultBrowserFlowWithoutFactors() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(Locators.Adaptive_MFA.adaptive_MFA_Section);
        staticWait(5000);
        findElementByIndexAndClick(Locators.Adaptive_MFA.selectFactorsForAuthentication, 1);
        findElementByIndexAndClick(Locators.Adaptive_MFA.selectFactorsForAuthentication, 2);
        jClick(Locators.Adaptive_MFA.updateButton);
        assertEqualsnew(UserManagementPom.createUser.userEnableAndDisableToastMessage, "Adaptive MFA update is in progress. You'll be notified once it is complete.");

    }

}

