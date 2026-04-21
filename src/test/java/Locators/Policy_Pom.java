package Locators;

import org.openqa.selenium.By;

public class Policy_Pom {

    public static By policyManager = By.xpath("(//div[@class='icon-wrapper'])[6]");
    public static By addPolicyButton = By.xpath("//button[text()='Add Policy']");
    public static By addnewPolicyButton = By.xpath("//button[text()='Add New Policy']");
    public static By policyName = By.xpath("//input[@name='name']");
    public static By policyDescription = By.xpath("//textarea[@name='description']");
    public static By assignTo = By.xpath("//input[@id='multi-users']");
    public static By selectGroups = By.xpath("//div[@class='multi-select__option-content']");
    public static By nextButton = By.xpath("//button[text()='Next']");
    public static By selectPolicy = By.xpath("(//span[@class='Switch-module_switch-handle__4UO-n'])");
    public static By savePolicy = By.xpath("//button[text()='Save Policy']");
    public static By alertPopup = By.xpath("//div[@class='toast-message text-md-regular']");
    public static By editPolicy = By.xpath("//h1[@class='display-xs-semibold text-primary title']");
    public static By editButton = By.xpath("//button[text()='Edit']");
    public static By updatePolicy = By.xpath("//button[text()='Update Policy']");
    public static By enableDisablePolicy = By.xpath("//input[@id='switch-Policy Automation three']");
    public static By confirmButton = By.xpath("//button[text()='Confirm']");
    public static By deleteButton = By.xpath("//button[text()='Delete']");


}
