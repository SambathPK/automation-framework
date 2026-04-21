package Locators;

import org.openqa.selenium.By;

public class PasswordManagerPom {

    public static By passwordManager = By.xpath("//a[normalize-space()='Password Manager']");
    public static By passwordLength = By.xpath("//span[@data-testid='password-length-increment-btn']");
    public static By uppercase = By.xpath("(//span[@class='ant-switch-inner'])[1]");
    public static By lowercase = By.xpath("(//span[@class='ant-switch-inner'])[2]");
    public static By specialCharacters = By.xpath("(//span[@class='ant-switch-inner'])[3]");
    public static By digits = By.xpath("(//span[@class='ant-switch-inner'])[4]");
    public static By notRecentlyUsed = By.xpath("(//span[@class='ant-switch-inner'])[6]");
    public static By restoreDefault = By.xpath("//button[contains(@class,'ant-btn css-z548ic ant-btn-primary btn btn-secondary')]");
    public static By save = By.xpath("//span[normalize-space()='Save']");

    public static By oldPassword = By.xpath("//input[@name='oldPassword']");
    public static By newPassword = By.xpath("//input[@name='password']");
    public static By confirmPassword = By.xpath("//input[@name='confirmPassword']");
    public static By update = By.xpath("//button[text()='Save Password']");

    public static By resetPsswdBtn = By.xpath("//button[text()='Reset Password']");
    public static By TempPsswd = By.name("temporaryPassword");
    public static By NewPsswd = By.xpath("(//input[@autocomplete='new-password'])[1]");
    public static By ConfirmPsswd = By.xpath("(//input[@autocomplete='new-password'])[2]");
    public static By Submit = By.xpath("//input[@value='Submit']");
    public static By temporaryPasswordMessage = By.xpath("//p[text()='User password updated successfully.']");

    public static By profileClick = By.xpath("(//p[@class='text-sm-semibold  max-w-[150px] user-name truncate max-w-[150px] whitespace-nowrap overflow-hidden'])[2]");
    public static By changePassword = By.xpath("//button[text()='Change Password']");
    public static  By passwordManagerToastMessage = By.xpath("//div[text()='Password policy updated successfully.']");
    public static By cancelButton = By.xpath("//button[text()='Cancel']");

}
