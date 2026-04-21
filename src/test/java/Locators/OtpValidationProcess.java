package Locators;

import org.openqa.selenium.By;

public class OtpValidationProcess {

    public static class forgotUsername {

        public static By forgotusername = By.xpath("//a[text()=' Forgot username?']");
        public static By personalEmail = By.xpath("(//div[@class='item-right'])[1]");
        public static By nextbutton = By.xpath("/html/body/div/div/div/div/div/div/div/div[2]/div/div[2]/div[3]/button/span");
        public static By next2 = By.xpath("//span[text()='Next']");
        public static By enterpersonalemail = By.xpath("//input[@placeholder='Personal email']");
        public static By otp = By.xpath("//input[@aria-label='Please enter OTP character 1']");
        public static By retrieveUsername = By.xpath("//div[@class='copyClip flex items-center float-end']");
        public static By invalidOtp = By.xpath("//div[text()='Invalid OTP']");
        public static By invalidPersonalEmail = By.xpath("//div[text()='Please enter registered email.']");
        public static By OTPmessage = By.xpath("//div[text()='Invalid OTP']");
        public static By EmailMessage = By.xpath("//div[@class='error mt-1']");
        public static By copyButton = By.xpath("/html/body/div/div/div/div/div/div/div/div/div/div[2]/div/div[2]/div[3]/div");


    }

    public static class forgotpassword {

        public static By enterUserName = By.xpath("//input[@id='username']");
        public static By signIn = By.name("login");
        public static By forgotPassword = By.xpath("//a[text()=' Forgot password?']");
        public static By submit = By.xpath("//span[text()='Submit']");
        public static By password = By.xpath("//input[@tabindex='2']");
        public static By login = By.xpath("//input[@name='login']");
        public static By nextButton = By.xpath("//span[text()='Next']");

    }

    public static class setpassword {

        public static By password1 = By.xpath("//input[@name='password']");
        public static By reEnterPassword = By.xpath("//input[@name='confirmPassword']");
        public static By InputError2 = By.xpath("//div[@class='error']");
        public static By displayNameAssertion = By.xpath("//div[@class='userInfo-text']/p");
    }
}
