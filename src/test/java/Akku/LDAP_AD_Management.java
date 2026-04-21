package Akku;

import Locators.Login;
import Locators.RoleManagementPom;
import Locators.UserManagementPom;
import Utils.Base;
import Utils.ExtentManager;
import Utils.TenantResult;
import Utils.TestContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class LDAP_AD_Management extends Base {

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


    @Test(description = "Verify that an LDAP directory is successfully added and synchronized",priority = 0)
    public void testAddLdapDirectory() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(UserManagementPom.createUser.userManagementSection);
        staticWait(6000);
        click(UserManagementPom.createUser.add);
        click(UserManagementPom.LDAP.directoryBtn);
        staticWait(4000);
        click(UserManagementPom.LDAP.LDAPDirectory);
        click(UserManagementPom.LDAP.submit);
        staticWait(5000);
        click(UserManagementPom.LDAP.chooseDirectory);
        staticWait(3000);
        findElementsAndClick(UserManagementPom.LDAP.selectDirectory, "Active Directory");
        enter(UserManagementPom.LDAP.ldapName, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "ldapName"));
        enter(UserManagementPom.LDAP.connectionURL, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "connectionURL"));
        enter(UserManagementPom.LDAP.bindDN, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "bindDN"));
        enter(UserManagementPom.LDAP.bindCredentials, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "bindCredentials"));
        staticWait(2000);
        click(UserManagementPom.LDAP.submit);
        staticWait(3000);
        click(UserManagementPom.LDAP.done);
        staticWait(2000);
        click(UserManagementPom.LDAP.next);
        enter(UserManagementPom.LDAP.userDN, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "userDN"));
        enter(UserManagementPom.LDAP.ldapAttribute, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "ldapAttribute"));
        enter(UserManagementPom.LDAP.RDNldapAttribute, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "RDNldapAttribute"));
        enter(UserManagementPom.LDAP.UUIDLdapAttribute, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "UUIDLdapAttribute"));
        enter(UserManagementPom.LDAP.userObjectClasses, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "userObjectClasses"));
        enter(UserManagementPom.LDAP.syncUpdateInterval, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "syncUpdateInterval"));
        enter(UserManagementPom.LDAP.certificate, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "certificate"));
        click(UserManagementPom.LDAP.submit);
        staticWait(8000);
//
//        String CreateLdap = getText(UserManagement.createUser.notificationMessage);
//        System.out.println(CreateLdap);
//        String Cl = "Directory created successfully";
//        Assert.assertEquals(CreateLdap,Cl);
//        staticWait(3000);

        click(UserManagementPom.LDAP.reviewMappingBtn);
        click(UserManagementPom.LDAP.nextBtn);
        staticWait(6000);
        click(UserManagementPom.LDAP.syncBtn);
        //assertEqualsnew(UserManagementPom.createUser.AddUserCreatedSuccessMsg, "User sync successful");
    }

    // @Test()
    public void editLdapDirectory() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(UserManagementPom.createUser.userManagementSection);
        staticWait(4000);
        jClick(UserManagementPom.LDAP.LDAPBtn);
        staticWait(3000);
        click(UserManagementPom.LDAP.editLDAPButton);
//        staticWait(4000);
//        deleteAll(UserManagementPom.LDAP.ldapName);
//        enter(UserManagementPom.LDAP.ldapName, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "ldapName"));
//        deleteAll(UserManagementPom.LDAP.connectionURL);
//        enter(UserManagementPom.LDAP.connectionURL, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "connectionURL"));
//        deleteAll(UserManagementPom.LDAP.bindDN);
//        enter(UserManagementPom.LDAP.bindDN, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "bindDN"));
//        enter(UserManagementPom.LDAP.bindCredentials, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "bindCredentials"));
//        click(UserManagementPom.LDAP.submit);
//        click(UserManagementPom.LDAP.done);
//        click(UserManagementPom.LDAP.nextBtn);
//        staticWait(7000);
//        deleteAll(UserManagementPom.LDAP.userDN);
//        enter(UserManagementPom.LDAP.userDN, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "userDN"));
//        deleteAll(UserManagementPom.LDAP.ldapAttribute);
//        enter(UserManagementPom.LDAP.ldapAttribute, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "ldapAttribute"));
//        deleteAll(UserManagementPom.LDAP.RDNldapAttribute);
//        enter(UserManagementPom.LDAP.RDNldapAttribute, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "RDNldapAttribute"));
//        deleteAll(UserManagementPom.LDAP.UUIDLdapAttribute);
//        enter(UserManagementPom.LDAP.UUIDLdapAttribute, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "UUIDLdapAttribute"));
//        deleteAll(UserManagementPom.LDAP.userObjectClasses);
//        enter(UserManagementPom.LDAP.userObjectClasses, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "userObjectClasses"));
//        deleteAll(UserManagementPom.LDAP.syncUpdateInterval);
//        enter(UserManagementPom.LDAP.syncUpdateInterval, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "syncUpdateInterval"));
//        deleteAll(UserManagementPom.LDAP.certificate);
//        staticWait(3000);
//        enter(UserManagementPom.LDAP.certificate, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "certificate"));
//        click(UserManagementPom.LDAP.submit);
//        click(UserManagementPom.LDAP.EditNext);
//
//        String expected2 = "Directory updated successfully";
//        staticWait(3000);
//        assertEquals(getText(UserManagementPom.LDAP.notificationMessage2), expected2);
//        String data2 = getText(UserManagementPom.LDAP.notificationMessage2);
//        System.out.println("Directory is updated" + " : " + data2);
//        staticWait(5000);
//
//        scrollByElement(UserManagementPom.LDAP.lastAttribute);
//        click(UserManagementPom.LDAP.reviewMappingBtn);
//        click(UserManagementPom.LDAP.nextBtn);
//        click(UserManagementPom.LDAP.syncBtn);
//        staticWait(3000);
//        String expected3 = "User sync successful";
//        staticWait(3000);
//        assertEquals(getText(UserManagementPom.LDAP.notificationMessage), expected3);
//        String data3 = getText(UserManagementPom.LDAP.notificationMessage);
//        System.out.println("Sync user for LDAP/AD" + " : " + data3);
    }


    //@Test()
    public void groupSync() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        click(UserManagementPom.createUser.userManagementSection);
        staticWait(3000);
        clickOnSpecificElementByText(UserManagementPom.LDAP.getLDAPName, UserManagementPom.LDAP.LDAPBtn, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_001", "ldapName"));
        staticWait(3000);
        click(UserManagementPom.LDAP.SyncGroup);

        String expected7 = "User group synchronization process started. This may take some time.";
        staticWait(2000);
        assertEquals(getText(UserManagementPom.LDAP.SyncGroupMessage), expected7);
        String data7 = getText(UserManagementPom.LDAP.SyncGroupMessage);
        System.out.println("Synced the ldap" + " : " + data7);

    }

    @Test(description = "Verify that the LDAP directory is successfully disabled and updated in the system",priority = 1)
    public void testDisableLdapDirectory() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(UserManagementPom.createUser.userManagementSection);
        staticWait(3000);
        jClick(UserManagementPom.LDAP.LDAPBtn);
        staticWait(3000);
        click(UserManagementPom.LDAP.editLDAPButton);
        enter(UserManagementPom.LDAP.bindCredentials, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "bindCredentials"));
        click(UserManagementPom.LDAP.submit);
        //click(UserManagement.LDAP.EditNext);
        click(UserManagementPom.LDAP.Done);
        click(UserManagementPom.LDAP.toggleBtn);
        click(UserManagementPom.LDAP.yesBtn);
        assertEqualsnew(UserManagementPom.LDAP.notificationMessage2, "Directory updated successfully.");
    }

    @Test(description = "Verify that an LDAP directory is successfully deleted and removed from the system",priority = 2)
    public void testDeleteLdapDirectory() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        click(UserManagementPom.createUser.userManagementSection);
        staticWait(3000);
        jClick(UserManagementPom.LDAP.LDAPBtn);
        staticWait(3000);
        click(UserManagementPom.LDAP.editLDAPButton);
        staticWait(5000);
        click(UserManagementPom.LDAP.deleteLDAP);
        click(UserManagementPom.LDAP.yesBtn);
        assertEqualsnew(UserManagementPom.LDAP.deleteDirectory, "Directory deleted successfully.");

    }

    @Test(description = "Verify that adding an LDAP directory with invalid credentials displays the appropriate error message",priority = 3)
    public void testAddLdapWithInvalidCredentials() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        actionClick(UserManagementPom.createUser.userManagementSection);
        staticWait(6000);
        click(UserManagementPom.createUser.add);
        click(UserManagementPom.LDAP.directoryBtn);
        staticWait(4000);
        click(UserManagementPom.LDAP.LDAPDirectory);
        click(UserManagementPom.LDAP.submit);
        staticWait(5000);
//        click(UserManagementPom.LDAP.chooseDirectory);
//        staticWait(3000);
//        findElementsAndClick(UserManagementPom.LDAP.selectDirectory, "Active Directory");
        enter(UserManagementPom.LDAP.ldapName, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_001", "ldapName"));
        enter(UserManagementPom.LDAP.connectionURL, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "connectionURL"));
        enter(UserManagementPom.LDAP.bindDN, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_001", "bindDN"));
        enter(UserManagementPom.LDAP.bindCredentials, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_001", "InvalidBindCredentials"));
        click(UserManagementPom.LDAP.submit);
        assertEqualsnew(UserManagementPom.LDAP.InvalidCredentials, "Please enter valid LDAP credentials.");


    }

    @Test(description = "Verify that adding an LDAP directory with an invalid certificate displays the appropriate error message",priority = 4)
    public void testAddLdapWithInvalidCertificate() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        actionClick(UserManagementPom.createUser.userManagementSection);
        staticWait(6000);
        click(UserManagementPom.createUser.add);
        click(UserManagementPom.LDAP.directoryBtn);
        staticWait(4000);
        click(UserManagementPom.LDAP.LDAPDirectory);
        click(UserManagementPom.LDAP.submit);
        staticWait(5000);
//        click(UserManagementPom.LDAP.chooseDirectory);
//        staticWait(3000);
//        findElementsAndClick(UserManagementPom.LDAP.selectDirectory, "Active Directory");
        enter(UserManagementPom.LDAP.ldapName, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_001", "ldapName"));
        enter(UserManagementPom.LDAP.connectionURL, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "connectionURL"));
        enter(UserManagementPom.LDAP.bindDN, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "bindDN"));
        enter(UserManagementPom.LDAP.bindCredentials, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_002", "bindCredentials"));
        click(UserManagementPom.LDAP.submit);
        click(UserManagementPom.LDAP.done);
        click(UserManagementPom.LDAP.next);
        enter(UserManagementPom.LDAP.userDN, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_001", "userDN"));
        enter(UserManagementPom.LDAP.ldapAttribute, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_001", "ldapAttribute"));
        enter(UserManagementPom.LDAP.RDNldapAttribute, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_001", "RDNldapAttribute"));
        enter(UserManagementPom.LDAP.UUIDLdapAttribute, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_001", "UUIDLdapAttribute"));
        enter(UserManagementPom.LDAP.userObjectClasses, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_001", "userObjectClasses"));
        enter(UserManagementPom.LDAP.syncUpdateInterval, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_001", "syncUpdateInterval"));
        enter(UserManagementPom.LDAP.certificate, Utils.ExcelDataReader.inputData("createLdap", "key", "TC_001", "InvalidCertificate"));
        click(UserManagementPom.LDAP.submit);
        assertEqualsnew(UserManagementPom.LDAP.InvalidCertificate, "Please enter valid users DN/Certificate.");

    }


}
