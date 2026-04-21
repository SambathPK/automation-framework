package Locators;

import org.openqa.selenium.By;

public class RoleManagementPom {

    public static By RoleManagement = By.xpath("//span[text()='Role Management']");
    public static By AddRole = By.xpath("//button[text()='Add New Role']");
    public static By RoleName = By.xpath("(//input[@type='text'])[1]");
    public static By RoleDescription = By.xpath("//textarea[@name='description']");
    public static By selectRolePermission = By.xpath("//div[@class='Switch-module_switch-wrapper__-oU-w Switch-module_color-primary__HnpaQ Switch-module_size-small__de4E1']");
    public static By Save = By.xpath("//button[@type='submit']");
    public static By Update = By.xpath("//button[text()='Update']");
    public static By submit = By.xpath("//input[@id='kc-login']");
    public static By UserManagement_Table2 = By.xpath("//table[@class='w-full divide-y divide-gray-200 table-fixed']");
    public static By roleEdit = By.xpath("//div[@class='flex items-center gap-4 justify-end pr-2']/div[1]/button");
    public static By roleDelete = By.xpath("//div[@class='flex items-center gap-4 justify-end pr-2']/div[2]/button");
    public static By Popup_Confirm = By.xpath("//button[text()='Confirm']");

}
