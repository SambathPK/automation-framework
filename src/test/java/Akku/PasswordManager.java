package Akku;

import Locators.Login;
import Locators.PasswordManagerPom;
import Locators.RoleManagementPom;
import Locators.UserManagementPom;
import Utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static Locators.UserManagementPom.groups.groupsToastMessage;

public class PasswordManager extends Base {

    private final String tenantUrl;

    public PasswordManager(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new PasswordManager(tenantUrl)};
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

    @Test(priority = 0, description = "Verify that default password policy is restored and updated successfully")
    public void verifyPasswordPolicyRestorationAndUpdate() throws InterruptedException {

        // Login and navigate to password manager
        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(PasswordManagerPom.passwordManager);
        staticWait(3000);
        // Restore default password policy
        safeClick(PasswordManagerPom.restoreDefault);
        assertEqualsnew(PasswordManagerPom.passwordManagerToastMessage, "Password policy updated successfully.");
        staticWait(3000);
        // Update password policy settings (turn off uppercase and special characters)
        click(PasswordManagerPom.passwordLength);
        click(PasswordManagerPom.uppercase);  // off
        click(PasswordManagerPom.specialCharacters);
        staticWait(1000);
        click(PasswordManagerPom.save);
        assertEqualsnew(PasswordManagerPom.passwordManagerToastMessage, "Password policy updated successfully.");
    }

    @Test(priority = 1, description = "Verify that password update fails with non-compliant data (First attempt with invalid new password)")
    public void verifyPasswordUpdateWithNonCompliantData_FirstAttempt() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(PasswordManagerPom.passwordManager);
        staticWait(5000);
        jClick(UserManagementPom.profilePom.adminprofileClick);
        staticWait(3000);
        jClick(PasswordManagerPom.profileClick);
        staticWait(2000);
        jClick(PasswordManagerPom.changePassword);
        // Enter old and new password (invalid)
        enter(PasswordManagerPom.oldPassword, Utils.ExcelDataReader.inputData("PasswordManager", "key", "TC_001", "password"));
        enter(PasswordManagerPom.newPassword, Utils.ExcelDataReader.inputData("PasswordManager", "key", "TC_001", "newPassword1"));
        enter(PasswordManagerPom.confirmPassword, Utils.ExcelDataReader.inputData("PasswordManager", "key", "TC_001", "confirmPassword1"));
        staticWait(3000);
        WebElement enabled1 = findElement(PasswordManagerPom.update);
        boolean isButtonDisabled1 = enabled1.isEnabled();

        // If the button is disabled (false), it means the password does not match the policy
        if (!isButtonDisabled1) {
            System.out.println("The entered password is not matching as per the required password policy");
        } else {
            // If the button is enabled (true), it means the password matches the policy
            System.out.println("The entered password is matching the system's password policy");
        }

    }

    @Test(priority = 2, description = "Verify that password update fails with non-compliant data (Second attempt with invalid new password)")
    public void verifyPasswordUpdateWithNonCompliantData_SecondAttempt() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(PasswordManagerPom.passwordManager);
        staticWait(3000);
        safeClick(PasswordManagerPom.restoreDefault);
        assertEqualsnew(PasswordManagerPom.passwordManagerToastMessage, "Password policy updated successfully.");
        staticWait(3000);

        // Update password policy settings (turn on lowercase and digits)
        click(PasswordManagerPom.passwordLength);
        click(PasswordManagerPom.lowercase);
        click(PasswordManagerPom.digits);
        staticWait(1000);
        click(PasswordManagerPom.save);
        assertEqualsnew(PasswordManagerPom.passwordManagerToastMessage, "Password policy updated successfully.");
        staticWait(3000);

        // Navigate to the admin profile and attempt to reset the password
        jClick(UserManagementPom.profilePom.adminprofileClick);
        staticWait(3000);
        jClick(PasswordManagerPom.profileClick);
        staticWait(2000);
        jClick(PasswordManagerPom.changePassword);
        // Enter old and new password (invalid)
        enter(PasswordManagerPom.oldPassword, Utils.ExcelDataReader.inputData("PasswordManager", "key", "TC_001", "password"));
        enter(PasswordManagerPom.newPassword, Utils.ExcelDataReader.inputData("PasswordManager", "key", "TC_001", "newPassword2"));
        enter(PasswordManagerPom.confirmPassword, Utils.ExcelDataReader.inputData("PasswordManager", "key", "TC_001", "confirmPassword2"));
        staticWait(2000);
        WebElement enabled1 = findElement(PasswordManagerPom.update);
        boolean isButtonDisabled1 = enabled1.isEnabled();

        // If the button is disabled (false), it means the password does not match the policy
        if (!isButtonDisabled1) {
            System.out.println("The entered password is not matching as per the required password policy");
        } else {
            // If the button is enabled (true), it means the password matches the policy
            System.out.println("The entered password is matching the system's password policy");
        }
        staticWait(2000);
        jClick(PasswordManagerPom.cancelButton);
        staticWait(3000);
        // Close the dialog and wait
        jClick(PasswordManagerPom.passwordManager);
        staticWait(4000);
        // Restore the default password policy
        jClick(PasswordManagerPom.restoreDefault);
        assertEqualsnew(PasswordManagerPom.passwordManagerToastMessage, "Password policy updated successfully.");
    }

    @Test(priority = 3, description = "Verify that password policy is updated with valid data")
    public void verifyPasswordPolicyUpdateWithValidData() throws InterruptedException {

        // Login and navigate to password manager
        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(PasswordManagerPom.passwordManager);
        staticWait(3000);

        // Update password policy settings (turn on lowercase and not recently used)
        click(PasswordManagerPom.passwordLength);
        click(PasswordManagerPom.lowercase);
        click(PasswordManagerPom.notRecentlyUsed);
        staticWait(2000);

        // Save updated password policy
        click(PasswordManagerPom.save);
        assertEqualsnew(PasswordManagerPom.passwordManagerToastMessage, "Password policy updated successfully.");
    }

    @Test(priority = 4, description = "Verify that password update with valid data is successful")
    public void verifyPasswordUpdateWithValidData() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(PasswordManagerPom.passwordManager);
        staticWait(3000);
        jClick(UserManagementPom.profilePom.adminprofileClick);
        staticWait(3000);
        jClick(PasswordManagerPom.profileClick);
        staticWait(2000);
        jClick(PasswordManagerPom.changePassword);
        staticWait(3000);
        // Enter old password and new valid password
        enter(PasswordManagerPom.oldPassword, Utils.ExcelDataReader.inputData("PasswordManager", "key", "TC_001", "password"));
        enter(PasswordManagerPom.newPassword, Utils.ExcelDataReader.inputData("PasswordManager", "key", "TC_001", "newPassword3"));
        enter(PasswordManagerPom.confirmPassword, Utils.ExcelDataReader.inputData("PasswordManager", "key", "TC_001", "confirmPassword3"));
        staticWait(3000);
        jClick(PasswordManagerPom.update);
        assertEqualsnew(UserManagementPom.createUser.userEnableAndDisableToastMessage, "User password updated successfully.");
    }

    @Test(priority = 5, description = "Verify login with updated password and restore default password policy")
    public void verifyLoginWithUpdatedPassword() throws InterruptedException {

        // Log back in with updated password
        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(RoleManagementPom.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("PasswordManager", "key", "TC_001", "confirmPassword3"));
        staticWait(1500);
        click(RoleManagementPom.submit);
        // Navigate to password manager and restore default settings
        jClick(PasswordManagerPom.passwordManager);
        staticWait(3000);
        jClick(PasswordManagerPom.restoreDefault);
        assertEqualsnew(PasswordManagerPom.passwordManagerToastMessage, "Password policy updated successfully.");
        staticWait(5000);
        // Update password policy settings again
        click(PasswordManagerPom.uppercase);
        click(PasswordManagerPom.lowercase);
        click(PasswordManagerPom.specialCharacters);
        click(PasswordManagerPom.digits);
        staticWait(2000);
        // Save updated password policy
        click(PasswordManagerPom.save);
        assertEqualsnew(PasswordManagerPom.passwordManagerToastMessage, "Password policy updated successfully.");
    }

    @Test(priority = 6, description = "Verify that password update fails due to reuse of recent password")
    public void verifyPasswordUpdateFailsDueToReuseOfRecentPassword() throws InterruptedException {
        // Login and navigate to password manager
        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(PasswordManagerPom.passwordManager);
        staticWait(3000);
        // Navigate to the admin profile and attempt to reset password
        jClick(UserManagementPom.profilePom.adminprofileClick);
        staticWait(3000);
        jClick(PasswordManagerPom.profileClick);
        staticWait(2000);
        jClick(PasswordManagerPom.changePassword);
        staticWait(2000);
        // Enter old password and new password (recently used password)
        enter(PasswordManagerPom.oldPassword, Utils.ExcelDataReader.inputData("PasswordManager", "key", "TC_001", "password"));
        enter(PasswordManagerPom.newPassword, Utils.ExcelDataReader.inputData("PasswordManager", "key", "TC_001", "newPassword3"));
        enter(PasswordManagerPom.confirmPassword, Utils.ExcelDataReader.inputData("PasswordManager", "key", "TC_001", "confirmPassword3"));
        staticWait(1500);
        // Attempt to update password and check for recent password reuse error message
        jClick(PasswordManagerPom.update);
        assertEqualsnew(UserManagementPom.createUser.userEnableAndDisableToastMessage, "The password was recently used, please enter a different password.");
    }

    @Test(priority = 7, description = "Verify that password policy is updated when 'Not Recently Used' toggle is disabled")
    public void verifyPasswordPolicyUpdateWhenNotRecentlyUsedIsDisabled() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(PasswordManagerPom.passwordManager);
        staticWait(3000);
        // Enable the "not recently used" option for the password policy
        click(PasswordManagerPom.notRecentlyUsed);
        staticWait(3000);
        // Save updated password policy settings
        click(PasswordManagerPom.save);
        assertEqualsnew(PasswordManagerPom.passwordManagerToastMessage, "Password policy updated successfully.");

    }

}