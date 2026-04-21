package Locators;

import Utils.Base;
import org.openqa.selenium.By;

public class Audit_Logs_Pom extends Base {

    public static By auditlogs = By.xpath("//span[text()='Audit Logs']");
    public static By auditlogssubmitbutton = By.xpath("//button[text()='Search']");
    public static By UserTypeadmin = By.xpath("//span[text()='Admin']");
    public static By UserType = By.xpath("//div[@id='select-select-field']");
    public static By DownloadCSVButton = By.xpath("//button[text()='Download']");
    public static By auditLogTableText = By.xpath("//td[@class='whitespace-nowrap border-b border-gray-200 px-4 py-3 text-sm']/p");
    public static By actionType = By.xpath("//div[@class='multi-select__input-wrapper']");
    public static By selectActionType = By.xpath("//span[@class='multi-select__option-label']");
    public static By entersearchData = By.xpath("//input[@class='Search_searchInput__hei4M text-md-regular']");

}