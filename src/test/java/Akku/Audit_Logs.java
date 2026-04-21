package Akku;

import Locators.Audit_Logs_Pom;
import Locators.Login;
import Locators.UserManagementPom;
import Utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openxmlformats.schemas.drawingml.x2006.main.STAdjCoordinate;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Audit_Logs extends Base {

    private final String tenantUrl;

    public Audit_Logs(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new Audit_Logs(tenantUrl)};
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

    @AfterSuite
    public void writeTenantSummary() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("target/tenant-summary.json"), TenantResult.results);

        // Flush all tenant reports just in case
        ExtentManager.flushAll();
    }

    @Test(priority = 0, description = "Verify that user-specific audit logs are displayed correctly")
    public static void testDisplayUserAuditLogs() throws InterruptedException {
        // Log in and navigate to the Audit Logs section

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(Audit_Logs_Pom.auditlogs);
        staticWait(6000);

        // Submit filter to view user audit logs and verify their display
        click(Audit_Logs_Pom.auditlogssubmitbutton);
        staticWait(3000);
        assertContainsText(Audit_Logs_Pom.auditLogTableText);
    }

    @Test(priority = 1, description = "Verify that admin-specific audit logs are displayed correctly")
    public static void testDisplayAdminAuditLogs() throws InterruptedException, AWTException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(Audit_Logs_Pom.auditlogs);
        staticWait(2000);

        // Filter by User Type as 'Admin' and verify display of admin audit logs
        click(Audit_Logs_Pom.UserType);
        staticWait(2000);
        click(Audit_Logs_Pom.UserTypeadmin);
        click(Audit_Logs_Pom.auditlogssubmitbutton);
        staticWait(3000);
        assertContainsText(Audit_Logs_Pom.auditLogTableText);
    }

    @Test(priority = 2, description = "Verify that user audit logs can be downloaded successfully in CSV format")
    public static void testDownloadUserAuditLogsCSV() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(Audit_Logs_Pom.auditlogs);
        staticWait(3000);

        // Submit filter and initiate CSV download for user audit logs
        click(Audit_Logs_Pom.auditlogssubmitbutton);
        staticWait(3000);
        click(Audit_Logs_Pom.DownloadCSVButton);

        // Verify the CSV download initiation message
        assertEqualsnew(UserManagementPom.createUser.userEnableAndDisableToastMessage,
                "Audit logs download process initiated. You will receive the audit logs CSV via email");
    }

    @Test(priority = 3, description = "Verify that admin audit logs can be downloaded successfully in CSV format")
    public static void testDownloadAdminAuditLogsCSV() throws InterruptedException {
        // Log in and navigate to the Audit Logs section
        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(Audit_Logs_Pom.auditlogs);
        staticWait(3000);

        // Filter by User Type as 'Admin' and initiate CSV download for admin audit logs
        click(Audit_Logs_Pom.UserType);
        staticWait(2000);
        click(Audit_Logs_Pom.UserTypeadmin);
        click(Audit_Logs_Pom.auditlogssubmitbutton);
        staticWait(3000);
        click(Audit_Logs_Pom.DownloadCSVButton);

        // Verify the CSV download initiation message
        assertEqualsnew(UserManagementPom.createUser.userEnableAndDisableToastMessage,
                "Audit logs download process initiated. You will receive the audit logs CSV via email");
    }

    @Test(priority = 4, description = "Verify that login events are accurately recorded in the audit logs")
    public static void testAuditLogsForLoginEvents() throws InterruptedException {
        // Log in and navigate to Audit Logs
        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        // Open the Audit Logs section
        jClick(Audit_Logs_Pom.auditlogs);
        staticWait(3000);

        jClick(Audit_Logs_Pom.actionType);
        staticWait(4000);
        findElementsAndClickNew(Audit_Logs_Pom.selectActionType, "Login");

        // Apply filter and verify the  presence of login records in the logs
        click(Audit_Logs_Pom.auditlogssubmitbutton);
        staticWait(3000);
        assertTextFromMultipleElements(Audit_Logs_Pom.auditLogTableText, "Login");
    }

    @Test(priority = 6, description = "Verify that audit logs can be filtered and searched by specific user data")
    public static void testSearchAuditLogsForSpecificUser() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);

        jClick(Audit_Logs_Pom.auditlogs);
        staticWait(3000);

        // Search for logs related to the user "Sampath"
        jClick(Audit_Logs_Pom.entersearchData);
        jsEnter(Audit_Logs_Pom.entersearchData, "Sampath");
        click(Audit_Logs_Pom.auditlogssubmitbutton);
        staticWait(3000);
        assertTextContainsInMultipleElements(Audit_Logs_Pom.auditLogTableText, "Sampath");
    }
}
