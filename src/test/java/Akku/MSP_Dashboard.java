package Akku;

import Locators.Login;
import Locators.MSP_Dashboard_Pom;
import Utils.Base;
import org.openqa.selenium.devtools.v85.backgroundservice.BackgroundService;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.io.IOException;

public class MSP_Dashboard extends Base {

    @Test(priority = 0, description = "Verify that the MSP Admin can Login Successfully and Views the MSP Dashboard")
    public static void loginIntoMSP() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        assertTextContains(MSP_Dashboard_Pom.LicenseManagementText, "License Management");
        staticWait(8000);

    }

    @Test(priority = 1, description = "Verify that the MSP Admin can buy Starter Licenses for themselves")
    public static void buyStarterLicense() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        click(MSP_Dashboard_Pom.BuyStarterLicenses);
        staticWait(3000);
        enter(MSP_Dashboard_Pom.LicenseCount, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "NoOfLicensesInput"));
        staticWait(3000);
        click(MSP_Dashboard_Pom.BuyLicenseButton);
        staticWait(3000);
        click(MSP_Dashboard_Pom.ConfirmButton);
        staticWait(8000);
        assertTextContains(MSP_Dashboard_Pom.ThankYouMsg, "Thank you for Adding more licenses");
        staticWait(3000);
        click(MSP_Dashboard_Pom.CloseInModalAlert);
        staticWait(5000);

        //script for the same but through the Add More Licenses Button
        click(MSP_Dashboard_Pom.AddMoreLicenseButton);
        staticWait(3000);
        click(MSP_Dashboard_Pom.StarterPlan);
        staticWait(3000);
        enter(MSP_Dashboard_Pom.LicenseCount, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "NoOfLicensesInput"));
        staticWait(3000);
        click(MSP_Dashboard_Pom.BuyLicenseButton);
        staticWait(3000);
        click(MSP_Dashboard_Pom.ConfirmButton);
        staticWait(8000);
        assertTextContains(MSP_Dashboard_Pom.ThankYouMsg, "Thank you for Adding more licenses");
        staticWait(3000);
        click(MSP_Dashboard_Pom.CloseInModalAlert);
        staticWait(5000);

    }

    @Test(priority = 2, description = "Verify that the MSP Admin can buy Standard Licenses for themselves")
    public static void buyStandardLicense() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        click(MSP_Dashboard_Pom.BuyStandardLicenses);
        staticWait(3000);
        enter(MSP_Dashboard_Pom.LicenseCount, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "NoOfLicensesInput"));
        staticWait(3000);
        click(MSP_Dashboard_Pom.BuyLicenseButton);
        staticWait(3000);
        click(MSP_Dashboard_Pom.ConfirmButton);
        staticWait(8000);
        assertTextContains(MSP_Dashboard_Pom.ThankYouMsg, "Thank you for Adding more licenses");
        staticWait(3000);
        click(MSP_Dashboard_Pom.CloseInModalAlert);
        staticWait(5000);

        //script for the same but through the Add More Licenses Button
        click(MSP_Dashboard_Pom.AddMoreLicenseButton);
        staticWait(3000);
        click(MSP_Dashboard_Pom.StandardPlan);
        staticWait(3000);
        enter(MSP_Dashboard_Pom.LicenseCount, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "NoOfLicensesInput"));
        staticWait(3000);
        click(MSP_Dashboard_Pom.BuyLicenseButton);
        staticWait(3000);
        click(MSP_Dashboard_Pom.ConfirmButton);
        staticWait(8000);
        assertTextContains(MSP_Dashboard_Pom.ThankYouMsg, "Thank you for Adding more licenses");
        staticWait(3000);
        click(MSP_Dashboard_Pom.CloseInModalAlert);
        staticWait(5000);

    }


    @Test(priority = 3, description = "Verify that the MSP Admin can buy Advanced Licenses for themselves")
    public static void buyAdvancedLicense() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        click(MSP_Dashboard_Pom.BuyAdvancedLicenses);
        staticWait(3000);
        enter(MSP_Dashboard_Pom.LicenseCount, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "NoOfLicensesInput"));
        staticWait(3000);
        click(MSP_Dashboard_Pom.BuyLicenseButton);
        staticWait(3000);
        click(MSP_Dashboard_Pom.ConfirmButton);
        staticWait(8000);
        assertTextContains(MSP_Dashboard_Pom.ThankYouMsg, "Thank you for Adding more licenses");
        staticWait(3000);
        click(MSP_Dashboard_Pom.CloseInModalAlert);
        staticWait(5000);

        //script for the same but through the Add More Licenses Button
        click(MSP_Dashboard_Pom.AddMoreLicenseButton);
        staticWait(3000);
        //click(MSP_Dashboard_Pom.AdvancedPlan);
        //staticWait(3000);
        enter(MSP_Dashboard_Pom.LicenseCount, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "NoOfLicensesInput"));
        staticWait(3000);
        click(MSP_Dashboard_Pom.BuyLicenseButton);
        staticWait(3000);
        click(MSP_Dashboard_Pom.ConfirmButton);
        staticWait(8000);
        assertTextContains(MSP_Dashboard_Pom.ThankYouMsg, "Thank you for Adding more licenses");
        staticWait(3000);
        click(MSP_Dashboard_Pom.CloseInModalAlert);
        staticWait(5000);

    }


    @Test(priority = 4, description = "Verify that the MSP Admin can add Licenses to a tenant in the License Management's Assign/Unassign Licenses button using the slider")
    public static void AddLicenseToTenant() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        click(MSP_Dashboard_Pom.AssignUnassignLicenseButton);
        staticWait(3000);
        click(MSP_Dashboard_Pom.SearchTenantInLicenseManagement);
        staticWait(3000);
        click(MSP_Dashboard_Pom.SearchTenantInLicenseManagement1);
        staticWait(3000);
        moveSlider(MSP_Dashboard_Pom.AssignUnassignSlider, 100);
        staticWait(5000);
        click(MSP_Dashboard_Pom.AssignLicenseButton);
        staticWait(5000);
        assertTextContains(MSP_Dashboard_Pom.RequestText, "Request for Additional Licenses");
        staticWait(3000);
        click(MSP_Dashboard_Pom.ConfirmButton);
        staticWait(3000);

    }

    @Test(priority = 5, description = "Verify that the MSP Tenant table displays Data")
    public static void TenantTable() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        assertContainsText(MSP_Dashboard_Pom.TenantsTableData);
        staticWait(3000);
        assertContainsText(MSP_Dashboard_Pom.TenantsTableData2);
        staticWait(3000);
    }

    @Test(priority = 6, description = "Verify that the MSP Tenant table Search functions as expected")
    public static void SearchTenantTable() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        enter(MSP_Dashboard_Pom.SearchField,Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "SearchTenantTable1"));
        staticWait(8000);
        assertTextContains(MSP_Dashboard_Pom.TenantInRow1,Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "SearchTenantTable1"));
        staticWait(3000);
    }

    @Test(priority = 7, description = "Verify that the MSP Admin is directed to the Tenant Dashboard when clicked on it")
    public static void TenantDashboard() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        click(MSP_Dashboard_Pom.TenantInRow1);
        assertTextContains(MSP_Dashboard_Pom.AIThreatDetection,"AI Threat Detection");
        staticWait(3000);
    }


    @Test(priority = 8, description = "Verify whether the MSP Admin is able to edit Tenant Details (General details) and Update it; through the Edit button in the table.")
    public static void EditTenant() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        enter(MSP_Dashboard_Pom.SearchField, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "SearchTenantTable2"));
        staticWait(3000);
        click(MSP_Dashboard_Pom.EditButton);
        staticWait(3000);
        click(MSP_Dashboard_Pom.NoOfLicenses);
        clearText(MSP_Dashboard_Pom.NoOfLicenses);
        staticWait(3000);
        click(MSP_Dashboard_Pom.NoOfLicenses);
        deleteAll(MSP_Dashboard_Pom.NoOfLicenses);
        staticWait(2000);
        enter(MSP_Dashboard_Pom.NoOfLicenses,Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "NoOfLicensesInput"));
        scrollToBottom();
        assertTextContains(MSP_Dashboard_Pom.UpdateInUpdateTenant,"Update");
        staticWait(3000);
        click(MSP_Dashboard_Pom.UpdateInUpdateTenant);
        staticWait(4000);

    }

    @Test(priority = 9, description = "Verify whether the MSP Admin is able to edit Tenant Details (Email Configurations) and Update it; through the Edit button in the table.")
    public static void EditTenantTwo() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        enter(MSP_Dashboard_Pom.SearchField, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "SearchTenantTable2"));
        staticWait(3000);
        click(MSP_Dashboard_Pom.EditButton);
        staticWait(4000);
        click(MSP_Dashboard_Pom.EmailSettings);
        staticWait(5000);
        deleteAll(MSP_Dashboard_Pom.displayReplyEmail);
        staticWait(2000);
        enter(MSP_Dashboard_Pom.displayReplyEmail,Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "EditDisplayReplyEmail"));
        staticWait(2000);
        assertTextContains(MSP_Dashboard_Pom.UpdateInUpdateTenant,"Update");
        staticWait(3000);
        click(MSP_Dashboard_Pom.UpdateInUpdateTenant);
        staticWait(4000);

    }

    @Test(priority = 10, description = "Verify whether the MSP Admin is able to edit Tenant Details (SSO Configurations) and Update it; through the Edit button in the table.")
    public static void EditTenantThree() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        enter(MSP_Dashboard_Pom.SearchField, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "SearchTenantTable2"));
        staticWait(3000);
        click(MSP_Dashboard_Pom.EditButton);
        staticWait(8000);
        click(MSP_Dashboard_Pom.SSOSettings);
        staticWait(5000);
        deleteAll(MSP_Dashboard_Pom.LoginTimeOut);
        staticWait(2000);
        enter(MSP_Dashboard_Pom.LoginTimeOut,"9");
        scrollToBottom();
        assertTextContains(MSP_Dashboard_Pom.UpdateInUpdateTenant,"Update");
        staticWait(3000);
        click(MSP_Dashboard_Pom.UpdateInUpdateTenant);
        staticWait(4000);
    }

    @Test(priority = 11, description = "Verify whether the MSP Admin is able to edit Tenant Details (Product Plan Details) and Update it; through the Edit button in the table.")
    public static void EditTenantFour() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(8000);
        enter(MSP_Dashboard_Pom.SearchField, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "SearchTenantTable2"));
        staticWait(3000);
        click(MSP_Dashboard_Pom.EditButton);
        staticWait(8000);
        click(MSP_Dashboard_Pom.ProductPlanSettings);
        staticWait(5000);
        click(MSP_Dashboard_Pom.AdvancedPlaninEdit);
        scrollToBottom();
        staticWait(3000);
        assertTextContains(MSP_Dashboard_Pom.UpdateInUpdateTenant,"Update");
        staticWait(3000);
        click(MSP_Dashboard_Pom.UpdateInUpdateTenant);
        staticWait(3000);
    }

    @Test(priority = 12, description = "Verify that the MSP Admin can create and Send a Proposal to a Client")
    public static void GetPricing() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(6000);
        click(MSP_Dashboard_Pom.GetPricingButton);
        staticWait(3000);
        enter(MSP_Dashboard_Pom.CompanyName, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "CompanyName"));
        staticWait(3000);
        enter(MSP_Dashboard_Pom.CompanyDomain, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "CompanyDomain"));
        staticWait(3000);
        enter(MSP_Dashboard_Pom.CustomerFirstName, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "CustomerFirstName"));
        staticWait(3000);
        enter(MSP_Dashboard_Pom.CustomerLastName, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "CustomerLastName"));
        staticWait(3000);
        enter(MSP_Dashboard_Pom.CompanyEmail, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "CompanyEmail"));
        staticWait(3000);
        enter(MSP_Dashboard_Pom.NoOfLicensesInput, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "NoOfLicensesInput"));
        staticWait(3000);
        click(MSP_Dashboard_Pom.StandardPlanInPlanDetails);
        staticWait(3000);
        enter(MSP_Dashboard_Pom.EnterPrice, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "EnterPrice"));
        staticWait(3000);
        enter(MSP_Dashboard_Pom.AddressLineOne, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "AddressLineOne"));
        staticWait(3000);
        enter(MSP_Dashboard_Pom.GSTNumber, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "GSTNumber"));
        staticWait(3000);
        enter(MSP_Dashboard_Pom.PhoneNumber, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "PhoneNumber"));
        staticWait(3000);
        enter(MSP_Dashboard_Pom.PinCode, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "PinCode"));
        staticWait(3000);
        click(MSP_Dashboard_Pom.CountryDropDown);
        staticWait(3000);
        click(MSP_Dashboard_Pom.SelectACountry);
        staticWait(3000);
        click(MSP_Dashboard_Pom.StateDropDown);
        staticWait(3000);
        click(MSP_Dashboard_Pom.SelectAState);
        staticWait(3000);
        //enter(MSP_Dashboard_Pom.EnterCity, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "EnterCity"));
        //staticWait(3000);
        enter(MSP_Dashboard_Pom.Remarks, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "Remarks"));
        staticWait(3000);
        assertTextContains(MSP_Dashboard_Pom.GenerateProposalButton,"Generate");
        staticWait(3000);
        click(MSP_Dashboard_Pom.GenerateProposalButton);
        staticWait(12000);
        click(MSP_Dashboard_Pom.DownloadProposalButton);
        staticWait(8000);
        click(MSP_Dashboard_Pom.SendProposalButton);
        staticWait(6000);
        click(MSP_Dashboard_Pom.CloseInModalAlert2);
        staticWait(5000);

    }


    @Test(priority = 13, description = "Verify that the MSP Admin can create a Tenant through 'Add Tenant' after Proposal Generation")
    public static void AddTenant() throws InterruptedException {

        enter(Login.username, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "workEmailId"));
        staticWait(1500);
        click(Login.submit);
        enter(Login.password, Utils.ExcelDataReader.inputData("loginPage", "key", "TC_001", "password"));
        staticWait(1500);
        click(Login.signIn);
        staticWait(6000);
        click(MSP_Dashboard_Pom.AddTenantButton);
        staticWait(3000);
        clickOnSpecificElementByTextNew(MSP_Dashboard_Pom.ClientName, MSP_Dashboard_Pom.CreateTenantButton, Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "CustomerFirstName"));
        staticWait(7000);
        click(MSP_Dashboard_Pom.RegionDropDown);
        staticWait(3000);
        click(MSP_Dashboard_Pom.SelectARegion);
        staticWait(3000);
        click(MSP_Dashboard_Pom.AkkuTheme);
        staticWait(4000);
        click(MSP_Dashboard_Pom.TestButton);
        staticWait(12000);
        click(MSP_Dashboard_Pom.TestDoneButton);
        staticWait(4000);
        click(MSP_Dashboard_Pom.NextButton);
        staticWait(4000);
        enter(MSP_Dashboard_Pom.displaySenderEmail,Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "CustomerFirstName"));
        staticWait(4000);
        enter(MSP_Dashboard_Pom.senderEmail,Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "senderEmail"));
        staticWait(3000);
        enter(MSP_Dashboard_Pom.displayReplyEmail,Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "CustomerFirstName"));
        staticWait(3000);
        enter(MSP_Dashboard_Pom.replyEmail,Utils.ExcelDataReader.inputData("MSP_TenantCreation", "key", "TC_001", "replyEmail"));
        staticWait(3000);
        click(MSP_Dashboard_Pom.NextButton);
        staticWait(4000);
        click(MSP_Dashboard_Pom.NextButton);
        staticWait(8000);
        assertTextContains(MSP_Dashboard_Pom.Plan,"Standard");
        staticWait(3000);
        //click(MSP_Dashboard_Pom.SubmitButton);
        //staticWait(8000);
    }
}
