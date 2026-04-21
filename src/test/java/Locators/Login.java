package Locators;

import org.openqa.selenium.By;

public class Login {
    public static By username = By.xpath("//input[@id='username-normal']");
    public static By password = By.xpath("//input[@id='password']");
    public static By submit = By.xpath("//input[@value='Next']");
    public static By signIn = By.xpath("//input[@value='Sign In']");
    public static By invalidUsername = By.xpath("//span[@id='input-error-username']");
    public static By invalidPassword = By.xpath("//span[@id='input-error-password']");
    public static By forgotpasswordUsername = By.xpath("//input[@NAME='userName']");
    public static By userNotFoundmessage = By.xpath("//div[text()='User not found.']");
    public static By normalflowPassword = By.xpath("(//input[@class='pf-c-form-control'])[2]");

    public static By suspiciousLogin = By.xpath("//button[@class = 'Button-module_button__18Bed Button-module_outlined__f4ewH Button-module_secondary__j-3rj Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold single-card-btn'][1]");
    public static By suspiciousLogin1 = By.xpath("//p[@class='text-lg-semibold' and text()='Suspicious Logins']");

    public static By FailedLogin = By.xpath("//button[@class = 'Button-module_button__18Bed Button-module_outlined__f4ewH Button-module_secondary__j-3rj Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold single-card-btn'][3]");
    public static By FailedLogin1 = By.xpath("//p[@class='text-lg-semibold' and text()='Failed Logins']");

    public static By LockedAccounts = By.xpath("//button[@class = 'Button-module_button__18Bed Button-module_outlined__f4ewH Button-module_secondary__j-3rj Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold single-card-btn'][4]");
    public static By LockedAccounts1 = By.xpath("//p[@class='text-lg-semibold' and text()='Locked Accounts']");

    public static By QuarantinedLogins = By.xpath("//button[@class = 'Button-module_button__18Bed Button-module_outlined__f4ewH Button-module_secondary__j-3rj Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold single-card-btn'][2]");
    public static By QuarantinedLogins1 = By.xpath("//p[@class='text-lg-semibold' and text()='Quarantined Logins']");

    public static By AddMoreLicenses = By.xpath("//span[@class='material-symbols-outlined']");
    public static By UpgradePlan1 = By.xpath("//p[text()='Pricing']");
    public static By SuccessfulAttempts = By.xpath("//button[@class='Button-module_button__18Bed Button-module_outlined__f4ewH Button-module_secondary__j-3rj Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold single-card cursor-pointer'][1]");
    public static By SuccessfulAttempts1 = By.xpath("//p[@class='text-lg-semibold' and text()='Successful Recovery']");
    public static By SuccessfulAttemptsData = By.xpath("//td[@class='whitespace-nowrap border-b border-gray-200 px-4 py-3 text-sm']");

    public static By FailedAttempts = By.xpath("//button[@class='Button-module_button__18Bed Button-module_outlined__f4ewH Button-module_secondary__j-3rj Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold single-card cursor-pointer'][2]");
    public static By FailedAttempts1 = By.xpath("//p[@class='text-lg-semibold' and text()='Failed Recovery']");
    public static By FailedAttemptsData = By.xpath("//td[@class='whitespace-nowrap border-b border-gray-200 px-4 py-3 text-sm']");

    public static By CloudDirectoryProductText = By.xpath("//p[text()='Cloud Directory']");
    public static By SSOProductText = By.xpath("//p[text()='SSO & IDP']");
    public static By AdaptiveMFAProductText = By.xpath("//p[text()='Adaptive MFA']");
    public static By PasswordManagerProductText = By.xpath("//p[text()='Password Manager']");
    public static By AccessManagerProductText = By.xpath("//p[text()='Access Manager']");
    public static By SupportProductText = By.xpath("//p[text()='Support']");
    public static By MDMProductText = By.xpath("//p[text()='MDM']");
    public static By UserLifecycleProductText = By.xpath("//p[text()='User Lifecycle Manager']");

    public static By suspiciousLoginData = By.xpath("//tbody[@class='bg-white']/tr/td/p");
    public static By enterLicenses = By.xpath("//input[@type='number']");
    public static By requestLicenses = By.xpath("//button[text()='+ Request License']");
    public static By licenseConfirm = By.xpath("//button[text()='Confirm']");
    public static By thankYouAddingLicense = By.xpath("//p[text()='Thank you for Adding more licenses']");
    public static By defaultFlow = By.xpath("//label[@class='pf-c-form__label pf-c-form__label-text']");

    public static By defaultFlowInvalidUsernameorPassword = By.xpath("//span[contains(text(),'Invalid username or password.')]");
    public static By defaultFlowInvalidUser = By.xpath("//span[contains(text(),'Invalid user')]");


}