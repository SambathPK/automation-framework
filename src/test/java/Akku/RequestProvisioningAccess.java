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

public class RequestProvisioningAccess extends Base {

    private final String tenantUrl;

    public RequestProvisioningAccess(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new RequestProvisioningAccess(tenantUrl)};
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

    @Test(priority = 0, description = "Verify that a user can request permanent provisioning access to an application successfully")
    public void testRequestPermanentProvisioningAccess() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "Work_Email_Id"));
        staticWait(3000);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("Profile", "key", "TC_001", "confirmPassword"));
        click(Login.signIn);
        staticWait(6000);
        actionClick(ProvisioningPom.allApps);
        mouseOverAction(ProvisioningPom.requestAccess);
        click(ProvisioningPom.requestAccess);
        assertEqualsnew(ProvisioningPom.requestSubmitted, "Provisioning access request submitted. You’ll be notified via email once reviewed.");
    }

    @Test(priority = 1, description = "Verify that an admin can approve a pending provisioning access request")
    public void testApproveProvisioningRequest() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        actionClick(App_Management_Pom.addApps.appStore);
        staticWait(6000);
        click(ProvisioningPom.pendingRequest);
        staticWait(2000);
        findElementsAndClickwithPaginationnew(ProvisioningPom.approve, "Approve", ProvisioningPom.paginationNew);
        assertEqualsnew(ProvisioningPom.approveRequestMessage, "The provisioning access request has been approved successfully.");
    }

    @Test(priority = 2, description = "Verify that a user can request temporary provisioning access to an application")
    public void testRequestTemporaryProvisioningAccess() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("createUser", "key", "TC_001", "workEmailId"));
        staticWait(3000);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("Profile", "key", "TC_001", "confirmPassword"));
        click(Login.signIn);
        staticWait(6000);
        actionClick(ProvisioningPom.allApps);
        mouseOverAction(ProvisioningPom.requestTemporaryAccess);
        click(ProvisioningPom.requestTemporaryAccess);
        staticWait(3000);
        enter(ProvisioningPom.startDate, "24-04-2026");
        pressEnterActions();
        enter(ProvisioningPom.endDate, "26-04-2026");
        pressEnterActions();
        click(ProvisioningPom.sendButton);
        assertEqualsnew(ProvisioningPom.requestSubmitted, "Provisioning access request submitted. You’ll be notified via email once reviewed.");
    }

    @Test(priority = 3, description = "Verify that an admin can reject a pending provisioning access request")
    public void testRejectProvisioningRequest() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        actionClick(App_Management_Pom.addApps.appStore);
        staticWait(6000);
        click(ProvisioningPom.pendingRequest);
        staticWait(2000);
        findElementsAndClickwithPaginationnew(ProvisioningPom.reject, "Reject", ProvisioningPom.paginationNew);
        click(ProvisioningPom.deleteYesButton);
        assertEqualsnew(ProvisioningPom.rejectRequestMessage, "The provisioning access request has been rejected successfully.");
    }

}
