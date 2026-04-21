package Akku;

import Locators.*;
import Utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.model.Device;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class DeviceManagement extends Base {

    private final String tenantUrl;

    public DeviceManagement(String tenantUrl) {
        this.tenantUrl = tenantUrl;
        System.out.println("CREATED INSTANCE FOR TENANT: " + tenantUrl);
    }

    @Factory(dataProvider = "RegressionTest", dataProviderClass = TenantDataProvider.class)
    public Object[] factory(String tenantUrl) {
        return new Object[]{new DeviceManagement(tenantUrl)};
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

    @Test(priority = 0, description = "Verify that the admin can manage device restrictions for all users")
    public static void manageDeviceRestrictionsForAllUsers() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(DeviceManagerPom.deviceManager);
        staticWait(3000);
        click(DeviceManagerPom.add);
        click(DeviceManagerPom.addDeviceButton);
        enter(DeviceManagerPom.serialNumber, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "serialNumber"));
        enter(DeviceManagerPom.deviceUuid, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "DeviceUUID"));
        staticWait(1500);
        click(DeviceManagerPom.deviceType);
        findElementsAndClick(DeviceManagerPom.selectDeviceType, "Laptop");
        click(RoleManagementPom.Save);
        assertEqualsnew(DeviceManagerPom.deviceAddedMessage, "Device added successfully.");

        staticWait(5000);
        findElementsAndClick(DeviceManagerPom.serialNumberList, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "serialNumber"));
        staticWait(3000);
        click(DeviceManagerPom.serialNumber);
        deleteAll(DeviceManagerPom.serialNumber);
        enter(DeviceManagerPom.serialNumber, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_002", "serialNumber"));
        click(DeviceManagerPom.deviceUuid);
        deleteAll(DeviceManagerPom.deviceUuid);
        enter(DeviceManagerPom.deviceUuid, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_002", "DeviceUUID"));
        click(DeviceManagerPom.updateButton);
        assertEqualsnew(DeviceManagerPom.deviceUpdatedMessage, "Device updated successfully.");

        staticWait(5000);
        getTableDetailsAndJsClick(DeviceManagerPom.deviceManagerTableStyle, DeviceManagerPom.selectCheckBox, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_002", "serialNumber"));
        click(DeviceManagerPom.deleteIcon);
        click(DeviceManagerPom.deleteConfirm);
        assertEqualsnew(DeviceManagerPom.deviceDeleteddMessage, "Device deleted successfully.");

    }

    @Test(priority = 1, description = "Verify that the admin can manage device restrictions for selected users")
    public static void manageDeviceRestrictionsForSelectedUsers() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(DeviceManagerPom.deviceManager);
        staticWait(3000);
        click(DeviceManagerPom.add);
        click(DeviceManagerPom.addDeviceButton);
        enter(DeviceManagerPom.serialNumber, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "serialNumber"));
        enter(DeviceManagerPom.deviceUuid, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "DeviceUUID"));
        staticWait(1500);
        click(DeviceManagerPom.deviceType);
        findElementsAndClick(DeviceManagerPom.selectDeviceType, "Laptop");
        click(DeviceManagerPom.assignedUsersField);
        staticWait(2000);
        enter(DeviceManagerPom.enterAssignedUsersField, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "user1"));
        staticWait(5000);
        findElementsAndActionClick(DeviceManagerPom.userList, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "user1"));
        click(RoleManagementPom.Save);
        assertEqualsnew(DeviceManagerPom.deviceAddedMessage, "Device added successfully.");

        staticWait(5000);
        findElementsAndClick(DeviceManagerPom.serialNumberList, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "serialNumber"));
        staticWait(2500);
        click(DeviceManagerPom.assignedUsersField);
        staticWait(3000);
        enter(DeviceManagerPom.enterAssignedUsersField, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "user1"));
        staticWait(3000);
        findElementsAndActionClick(DeviceManagerPom.userList, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "user1"));
        staticWait(3000);
        click(DeviceManagerPom.updateButton);
        assertEqualsnew(DeviceManagerPom.deviceUpdatedMessage, "Device updated successfully.");

        staticWait(5000);
        findElementsAndClick(DeviceManagerPom.serialNumberList, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "serialNumber"));
        deleteAll(DeviceManagerPom.serialNumber);
        enter(DeviceManagerPom.serialNumber, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_002", "serialNumber"));
        deleteAll(DeviceManagerPom.deviceUuid);
        enter(DeviceManagerPom.deviceUuid, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_002", "DeviceUUID"));
        click(DeviceManagerPom.updateButton);
        assertEqualsnew(DeviceManagerPom.deviceUpdatedMessage, "Device updated successfully.");

        staticWait(5000);
        getTableDetailsAndJsClick(DeviceManagerPom.deviceManagerTableStyle, DeviceManagerPom.selectCheckBox, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_002", "serialNumber"));
        click(DeviceManagerPom.deleteIcon);
        click(DeviceManagerPom.deleteConfirm);
        assertEqualsnew(DeviceManagerPom.deviceDeleteddMessage, "Device deleted successfully.");

    }

    @Test(priority = 2, description = "Verify that the admin can manage device restrictions with CSV upload")
    public static void manageDeviceRestrictionsWithCSVUpload() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(DeviceManagerPom.deviceManager);
        staticWait(3000);
        click(DeviceManagerPom.add);
        click(DeviceManagerPom.csvOption);
        staticWait(3000);
        uploadFile(DeviceManagerPom.inputFile, "DataFiles/User-Device-Import.csv");
        assertEqualsnew(UserManagementPom.createUser.userEnableAndDisableToastMessage, "User data import process has started. You will receive an email notification once it's completed.");
        staticWait(5000);
        findElementByIndexAndClick(DeviceManagerPom.selectAllCheckBox, 0);
        staticWait(2000);
        click(DeviceManagerPom.deleteIcon);
        click(DeviceManagerPom.deleteConfirm);
        assertEqualsnew(DeviceManagerPom.deviceDeleteddMessage, "Device deleted successfully.");

    }

    @Test(priority = 3, description = "Verify the error handling is done for the device management admin operations")
    public static void handleDeviceManagementErrors() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        jClick(DeviceManagerPom.deviceManager);
        staticWait(3000);
        click(DeviceManagerPom.add);
        click(DeviceManagerPom.addDeviceButton);
        enter(DeviceManagerPom.serialNumber, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "serialNumber"));
        enter(DeviceManagerPom.deviceUuid, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "DeviceUUID"));
        staticWait(2000);
        click(DeviceManagerPom.deviceType);
        staticWait(2000);
        findElementsAndClick(DeviceManagerPom.selectDeviceType, "Laptop");
        click(DeviceManagerPom.assignedUsersField);
        staticWait(3000);
        enter(DeviceManagerPom.enterAssignedUsersField, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "user1"));
        staticWait(3000);
        findElementsAndActionClick(DeviceManagerPom.userList, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "user1"));
        click(RoleManagementPom.Save);
        assertEqualsnew(DeviceManagerPom.deviceAddedMessage, "Device added successfully.");

        staticWait(5000);
        click(DeviceManagerPom.add);
        click(DeviceManagerPom.addDeviceButton);
        staticWait(3000);
        enter(DeviceManagerPom.serialNumber, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "serialNumber"));
        enter(DeviceManagerPom.deviceUuid, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_002", "DeviceUUID"));
        staticWait(2000);
        click(DeviceManagerPom.deviceType);
        staticWait(2000);
        findElementsAndClick(DeviceManagerPom.selectDeviceType, "Laptop");
        staticWait(2000);
        click(RoleManagementPom.Save);
        assertEqualsnew(UserManagementPom.createUser.userEnableAndDisableToastMessage, "Device serial number already exists.");
        click(DeviceManagerPom.cancelButton);
        click(DeviceManagerPom.proceedButton);

        staticWait(5000);
        click(DeviceManagerPom.add);
        click(DeviceManagerPom.addDeviceButton);
        staticWait(3000);
        enter(DeviceManagerPom.serialNumber, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_002", "serialNumber"));
        enter(DeviceManagerPom.deviceUuid, Utils.ExcelDataReader.inputData("DeviceManager", "key", "TC_001", "DeviceUUID"));
        staticWait(2000);
        click(DeviceManagerPom.deviceType);
        findElementsAndClick(DeviceManagerPom.selectDeviceType, "Laptop");
        staticWait(2000);
        click(RoleManagementPom.Save);
        assertEqualsnew(UserManagementPom.createUser.userEnableAndDisableToastMessage, "Device UUID already exists.");
        click(DeviceManagerPom.cancelButton);
        click(DeviceManagerPom.proceedButton);

        staticWait(5000);
        findElementByIndexAndClick(DeviceManagerPom.selectAllCheckBox, 0);
        click(DeviceManagerPom.deleteIcon);
        click(DeviceManagerPom.deleteConfirm);
        assertEqualsnew(DeviceManagerPom.deviceDeleteddMessage, "Device deleted successfully.");

    }

}


