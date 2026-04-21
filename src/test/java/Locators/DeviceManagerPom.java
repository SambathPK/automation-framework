package Locators;

import org.openqa.selenium.By;

public class DeviceManagerPom {

    public static By deviceManager = By.xpath("//span[text()='Device Management']");
    public static By add = By.xpath("(//span[text()='add '])[1]");
    public static By addDeviceButton = By.xpath("//span[text()='Add Device']");
    public static By csvOption = By.xpath("//span[text()='CSV']");
    public static By serialNumber = By.xpath("//input[@name='serialNumber']");
    public static By deviceUuid = By.xpath("//input[@name='deviceUUId']");
    public static By deviceType = By.xpath("//div[@class='ant-select ant-select-outlined w-full h-[48px] mr-8 text-[20px] input-field css-z548ic ant-select-single ant-select-show-arrow']");
    public static By selectDeviceType = By.xpath("//div[@class='ant-select-item-option-content']");
    public static By deviceAddedMessage = By.xpath("//p[text()='Device added successfully.']");
    public static By serialNumberList = By.xpath("//span[@class='text-[#5441da] hover:text-[#7a6bff] cursor-pointer text-[18px] font-normal font-Inter table-data']");
    public static By deviceUpdatedMessage = By.xpath("//p[text()='Device updated successfully.']");
    public static By updateButton = By.xpath("//span[text()='Update']");
    public static By deviceManagerTableStyle = By.xpath("//table[@style='table-layout: auto;']");
    public static By deviceDeleteddMessage = By.xpath("//p[text()='Device deleted successfully.']");
    public static By assignedUsersField = By.xpath("//div[@class='ant-select-selection-overflow']");
    public static By enterAssignedUsersField = By.xpath("//div[@class='ant-select-selection-overflow']/div/div/input");
    public static By userList = By.xpath("//span[@class='block text-[#747577] text-[14px] truncate']");
    public static By inputFile = By.xpath("//input[@type='file']");
    public static By selectAllCheckBox = By.xpath("//span[@class='ant-checkbox ant-wave-target css-z548ic']");
    public static By proceedButton = By.xpath("//span[text()='Proceed']");
    public static By cancelButton = By.xpath("//span[text()='Cancel']");
    public static By selectCheckBox = By.xpath("(//span[@class='ant-checkbox-inner'])[position() != 1]");
    public static By deleteIcon = By.xpath("//span[text()='delete']");
    public static By deleteConfirm = By.xpath("//span[text()='Delete']");

}

