package Locators;

import org.openqa.selenium.By;

public class Customer {

    public static class CustomerCreation {

        public static By Password = By.id("password");
        public static By AddIcon = By.xpath("//span[@class='material-symbols-outlined']");
        public static By AddCustomerId = By.name("customerId");
        public static By AddDisplayName = By.name("displayName");
        public static By AddCustomerEmailId = By.xpath("//input[@name='customerEmailId']");
        public static By AddNoofLicense = By.xpath("//input[@name='noOfLicense']");
        public static By AddLoginTheme = By.xpath("//div[@name='loginTheme']//div[@class='ant-select-selector']");
        public static By SelectRegion = By.xpath("(//div[@class='ant-select-selector'])[7]");
        public static By selectLoginTheme = By.xpath("(//div[@class='ant-select-item-option-content'])[1]");
        public static By AddMobileNumber = By.xpath(" //input[@placeholder='1 (702) 123-4567']");
        public static By region = By.xpath("//div[@name='smsServiceProvider']");
        public static By restOfTheWorld = By.xpath("//span[text()='Rest of the world']");
        public static By AddTestButton = By.xpath("//span[normalize-space()='Test']");
        public static By AddPromptAlertAccept = By.xpath("//span[text()='Done']");
        public static By AddNextButtonClick = By.xpath("(//button[@class='ant-btn css-z548ic ant-btn-primary flex items-center justify-evenly font-semibold text-[20px] font-Inter w-[186px] h-[56px] bg-[#5441DA] text-white'])[1]");
        public static By AddSenderEmailAddress = By.xpath("//input[@placeholder='Sender email address']");
        public static By AddEmailSettingdisplayname = By.name("displaySenderEmail");
        public static By AddReplytoEmail = By.name("replyEmail");
        public static By AddReplytoDisplayName = By.xpath("//input[@placeholder='Display name for reply email address']");
        public static By SSOSessionIdle = By.xpath("//input[@name='ssoSessionIdleTimeout']");
        public static By SSOLogingActionTimeout = By.name("loginActionTimeout");
        public static By SelectBasicplanCheckBox = By.xpath("(//span[@class='ant-checkbox ant-wave-target css-z548ic'])[1]");
        public static By PlanSaveButton = By.xpath("//button[@class='ant-btn css-z548ic ant-btn-primary flex items-center justify-evenly font-semibold text-[20px] font-Inter w-[186px] h-[56px] bg-[#5441DA] text-white']");
        public static By SelectProfessionalplanCheckbox = By.xpath("(//span[@class='ant-checkbox ant-wave-target css-z548ic'])[2]");
        public static By SelectAdvancedplanCheckbox = By.xpath("(//span[@class='ant-checkbox ant-wave-target css-z548ic'])[3]");
        public static By SearchPlaceholder = By.xpath("(//input[@type='text'])[2]");
        public static By tenantCreationMessage = By.xpath("//div[text()='Customer creation process initiated. You will receive an email notification upon completion.']");
        public static By DomainexistsPopUp = By.xpath("//div[text()='Domain exists.']");
        public static By tenantUpdatedMessage = By.xpath("//div[text()='Tenant updated successfully.']");
        public static By retreiveTenantList = By.xpath("//p[@class='text-[#5441DA] cursor-pointer font-Inter font-normal text-[16px]']");
        public static By details = By.xpath("//span[text()='Details']");
        public static By msspOrClientToggle = By.xpath("(//span[@class='ant-switch-inner'])[1]");

    }
}
