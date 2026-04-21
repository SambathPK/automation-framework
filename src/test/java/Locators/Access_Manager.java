package Locators;

import org.openqa.selenium.By;

public class Access_Manager {

    public static class IP_Restriction{

        public static By accessManagerSection = By.xpath("//span[text()='Access Manager']");
        public static By createNew = By.xpath("//button[text()='Add restrictions']");
        public static By add = By.xpath("//div[@id='root']/div/div/div/div/div[2]/div/div/div/div[2]/button/span");
        public static By ipOption = By.xpath("(//button[text()='Configure'])[1]");
        public static By name = By.xpath("//input[@name='name']");
        public static By description = By.xpath("//input[@name='description']");
        public static By ipRange = By.xpath("//div[@id='select-']");
        public static By exactIp = By.xpath("//span[text()='Exact IP']");
        public static By ipv4 = By.xpath("//input[@name='ipDetails[0].IPv4']");
        public static By submit = By.xpath("//button[text()='Save']");
        public static By update = By.xpath("//button[text()='Update']");
        public static By searchUser = By.xpath("//input[@placeholder='Search']");
        public static By ipGroupSelect = By.xpath("(//label[@class='relative inline-flex items-center cursor-pointer'])[position() !=1]");
        public static By save = By.xpath("(//button[@type='button'])[4]");
        public static By assignToUsers = By.xpath("//p[text()='Users']");
        public static By selectSavedIp = By.xpath("//button[text()='Edit']");
        public static By deleteIcon = By.xpath("//button[text()='Delete']");
        public static By enableDisableToggle = By.xpath("(//input[@id='default-switch'])[1]");
        public static By deleteYesBtn = By.xpath("//button[text()='Confirm']");
        public static By manageButton= By.xpath("(//button[text()='Manage'])[1]");
        public static By accessDeniedMsg = By.xpath("//p[@class='instruction']");
        public static By userTable = By.xpath("//table[@class='w-full divide-y divide-gray-200 table-fixed']");

    }

}
