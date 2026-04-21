package Locators;
import org.openqa.selenium.By;
public class MSP_Dashboard_Pom {
    //LICENSE MANAGEMENT
    public static By LicenseManagementText = By.xpath("(//p[@class='text-lg-semibold'])[1]");
    //Assign Licenses to a Client(Tenant)
    public static By AssignUnassignLicenseButton = By.xpath("//button[@class ='Button-module_button__18Bed Button-module_outlined__f4ewH Button-module_secondary__j-3rj Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold custom-class-name']");
    public static By SearchTenantInLicenseManagement = By.xpath("//input[@class ='TenantsSelectField_custom-select-input__bq8ey w-full']");
    public static By SearchTenantInLicenseManagement1 = By.xpath("//span[text()='Demo Provision']");
    public static By AssignUnassignSlider = By.xpath("//div[@class='w-6 h-6 bg-white border-2 border-[#7F56D9] rounded-full shadow-md cursor-pointer']");
    public static By AssignLicenseButton = By.xpath("//button[@class='Button-module_button__18Bed Button-module_contained__B-47X Button-module_primary__st6yY Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold']");
    public static By ConfirmButton = By.xpath("(//button[@class='Button-module_button__18Bed Button-module_contained__B-47X Button-module_primary__st6yY Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold'])[1]");
    public static By RequestText = By.xpath("//p[normalize-space()='Request for Additional Licenses']");
    //Buy Starter Licenses for MSP themselves
    public static By BuyStarterLicenses = By.xpath("(//button[@class = 'Button-module_button__18Bed Button-module_outlined__f4ewH Button-module_secondary__j-3rj Button-module_iconOnly__FNrSu Button-module_responsive__wGjrI text-sm-semibold !p-[2px] plus-icon'])[1]");
    public static By LicenseCount = By.xpath("(//input[@class = 'p-2 pl-9 h-[40px] border rounded-lg w-[175px] license-input'])");
    public static By BuyLicenseButton = By.xpath("(//button[@class = 'Button-module_button__18Bed Button-module_contained__B-47X Button-module_warning__1voOb Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold'])");
    public static By CloseInModalAlert = By.xpath("(//button[@class = 'Button-module_button__18Bed Button-module_contained__B-47X Button-module_primary__st6yY Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold'])[1]");
    public static By AddMoreLicenseButton = By.xpath("(//button[@class = 'Button-module_button__18Bed Button-module_contained__B-47X Button-module_primary__st6yY Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold custom-class-name'])");
    public static By StarterPlan = By.xpath("(//div[@class = 'plan-label-box '])[1]");
    public static By ThankYouMsg = By.xpath(("//p[normalize-space()='Thank you for Adding more licenses']"));
    //Buy Standard Licenses for MSP themselves
    public static By BuyStandardLicenses = By.xpath("(//button[@class = 'Button-module_button__18Bed Button-module_outlined__f4ewH Button-module_secondary__j-3rj Button-module_iconOnly__FNrSu Button-module_responsive__wGjrI text-sm-semibold !p-[2px] plus-icon'])[2]");
    public static By StandardPlan = By.xpath("(//div[@class = 'plan-label-box '])[2]");
    //Buy Advanced Licenses for MSP themselves
    public static By BuyAdvancedLicenses = By.xpath("(//button[@class = 'Button-module_button__18Bed Button-module_outlined__f4ewH Button-module_secondary__j-3rj Button-module_iconOnly__FNrSu Button-module_responsive__wGjrI text-sm-semibold !p-[2px] plus-icon'])[3]");
    public static By AdvancedPlan = By.xpath("(//div[@class = 'plan-label-box '])[3]");
    //Tenant Table and Update Tenant
    public static By TenantsTableData = By.xpath("(//table//tr/td[3]/p)[1]");
    public static By TenantsTableData2 = By.xpath("(//table//tr/td[5]/p)[1]");
    public static By SearchField = By.xpath("//input[@class='Search_searchInput__RxQKx text-md-regular']");
    public static By TenantInRow1 = By.xpath("//p[@class='text-sm-medium'][1]");
    public static By NoOfLicenses = By.xpath("//input[@name='noOfLicense']");
    public static By AkkuTheme = By.xpath("(//div[@class='theme-selector__card  '])[1]");
    public static By NextButton = By.xpath("//button[contains(text(),'Next')]");
    public static By EditButton = By.xpath("(//button[@class='Button-module_button__18Bed Button-module_outlined__f4ewH Button-module_secondary__j-3rj Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold actions-btn'])[1]");
    public static By displayReplyEmail = By.xpath("//input[@name='displayReplyEmail']");
    public static By UpdateInUpdateTenant = By.xpath("//button[contains(text(),'Update')]");
    public static By EmailSettings = By.xpath("(//div[@class='ant-segmented-item-label'])[2]");
    public static By SSOSettings = By.xpath("(//div[@class='ant-segmented-item-label'])[3]");
    public static By ProductPlanSettings = By.xpath("(//div[@class='ant-segmented-item-label'])[4]");
    public static By LoginTimeOut = By.xpath("//input[@name='loginTimeout']");
    public static By AdvancedPlaninEdit = By.xpath("(//input[@type='checkbox'])[3]");
    public static By AIThreatDetection = By.xpath("//p[normalize-space()='AI Threat Detection']");
    //Get Pricing (Proposal Generation)
    public static By GetPricingButton = By.xpath("//button[@class = 'Button-module_button__18Bed Button-module_outlined__f4ewH Button-module_secondary__j-3rj Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold']");
    public static By CompanyName = By.xpath("//input[@id = 'companyName']");
    public static By CompanyDomain = By.xpath("//input[@name = 'companyDomain']");
    public static By CustomerFirstName = By.xpath("//input[@name = 'customerFirstName']");
    public static By CustomerLastName = By.xpath("//input[@name = 'customerLastName']");
    public static By CompanyEmail = By.xpath("//input[@name = 'companyEmail']");
    public static By NoOfLicensesInput = By.xpath("//input[@id = 'input-licenses-*']");
    public static By StandardPlanInPlanDetails = By.xpath("(//input[@type = 'checkbox'])[2]");
    public static By EnterPrice = By.xpath("(//input[@name = 'akkuPlanDetails.plans.standard.customerPrice'])");
    public static By AddressLineOne = By.xpath("//input[@name='address[0].value']");
    public static By GSTNumber = By.xpath("//input[@name='gstInNumber']");
    public static By PhoneNumber = By.xpath("//input[@name='mobileNumber']");
    public static By PinCode = By.xpath("//input[@name='pincode']");
    public static By CountryDropDown = By.xpath("//select[@name='country']");
    public static By SelectACountry = By.xpath("//option[@value='India']");
    public static By StateDropDown = By.xpath("//select[@name='state']");
    public static By SelectAState = By.xpath("//option[@value='Tamil Nadu']");
    public static By Remarks = By.xpath("//textarea[@name='termsAndConditions']");
    public static By GenerateProposalButton = By.xpath("//button[@type = 'submit']");
    public static By DownloadProposalButton = By.xpath("//button[@class = 'ant-btn css-z548ic ant-btn-default ant-btn-lg flex items-center justify-evenly font-semibold text-[20px] font-Inter  min-w-[186px] w-auto h-[56px]  bg-[#5441DA] text-white']");
    public static By SendProposalButton = By.xpath("//button[@class = 'ant-btn css-z548ic ant-btn-default ant-btn-lg back-btn !text-[#545263] border-[#D0D5DD]']");
    public static By CloseInModalAlert2 = By.xpath("//button[@class = 'ant-modal-close']");
    //Tenant Creation
    public static By AddTenantButton = By.xpath("//button[@class='Button-module_button__18Bed Button-module_contained__B-47X Button-module_primary__st6yY Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold']");
    public static By ClientName = By.xpath("//h3[normalize-space()='GPS'] ");
    public static By CreateTenantButton = By.xpath("//button[text()='Create Tenant']");
    public static By RegionDropDown = By.xpath("//select[@name = 'smsServiceProvider']");
    public static By SelectARegion = By.xpath("//option[@value='REST_OF_THE_WORLD']");
    public static By TestButton = By.xpath("//button[text()='Test']");
    public static By TestDoneButton = By.xpath("//button[@class='ant-btn css-z548ic ant-btn-default ant-btn-lg flex items-center justify-evenly font-semibold text-[20px] font-Inter  min-w-[186px] w-auto h-[56px]  bg-[#5441DA] text-white']");
    public static By displaySenderEmail= By.xpath("//input[@name='displaySenderEmail']");
    public static By senderEmail = By.xpath("//input[@name='senderEmail']");
    public static By replyEmail = By.xpath("//input[@name='replyEmail']");
    public static By Plan = By.xpath("//div[normalize-space()='Standard']");
}