package Locators;

import org.openqa.selenium.By;

public class App_Management_Pom {


    public static class addApps {

        public static By appStore = By.xpath("//a[@href='/app-store']");
        public static By configureApps = By.xpath("//p[@class='text-md-semibold']");
        public static By setupButton = By.xpath("//button[text()='Setup']");
        public static By sso = By.xpath("//p[text()='SSO']");
        public static By configure = By.xpath("//button[text()='Configure']");
        public static By threedot = By.xpath("//div[@class='flex gap-2 items-start min-w-[230px] self-start justify-end status-action-box']/div[1]");
        public static By publish = By.xpath("//div[@class='flex gap-2 items-start min-w-[230px] self-start justify-end status-action-box']/div[2]");
        public static By deleteButton = By.xpath("//div[@class='flex gap-2 items-start min-w-[230px] self-start justify-end status-action-box']/div[2]");
        public static By yesButton = By.xpath("//Span[text()='Yes']");
        public static By next = By.xpath("//button[text()='Configure']");
        public static By clientId = By.xpath("//input[@name='saml.clientId']");
        public static By homeURL = By.xpath("//input[@name='saml.baseUrl']");
        public static By validRedirectURL = By.xpath("//input[@name='saml.validRedirectUrl[0]']");
        public static By SamlValidRedirectURL = By.xpath("//input[@name='saml.validRedirectUrl[0]']");
        public static By masterSAMLProcessingURL = By.xpath("//input[@name='saml.masterSamlProcessingUrl']");
        public static By postBindingURL = By.xpath("//input[@name='saml.assertionConsumerUrlPost']");
        public static By consumerRedirectURL = By.xpath("//input[@name='saml.assertionConsumerUrlRedirect']");
        public static By save = By.xpath("//button[text()='Submit']");
        public static By update = By.xpath("//button[text()='Update']");
        public static By notificationMessage = By.xpath("//div[text()='App updated successfully.']");
        public static By AddNotificationSavedSuccessfully = By.xpath("//div[text()='App saved successfully.']");
        public static By ClientAlreadyExistPopup = By.xpath("//div[text()='Client ID already exists.']");
        public static By EnableNotification = By.xpath("//div[text()='App display in console status updated successfully.']");
        public static By DeletedNotification = By.xpath("//div[text()='App deleted successfully.']");
        public static By OpenID = By.xpath("//p[text()='OpenID']");
        public static By OpenIDClientID = By.xpath("//input[@name='openId.clientId']");
        public static By HomeLoginURL = By.xpath("//input[@name='openId.baseUrl']");
        public static By OpenIdValidredirectURL = By.xpath("//input[@name='openId.validRedirectUrl[0]']");
        public static By FrontChannellogout = By.xpath("//input[@name='openId.frontChannelLogoutUrl']");
        public static By ssoActiveInactiveToggle1 = By.xpath("//button[@class='Button-module_button__18Bed Button-module_text__dcWb- Button-module_error__1zBsf Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold max-h-[36px] !p-0']");
        public static By ssoActiveInactiveToggle2 = By.xpath("//button[@class='Button-module_button__18Bed Button-module_contained__B-47X Button-module_primary__st6yY Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold max-h-[36px]']");

    }
}
