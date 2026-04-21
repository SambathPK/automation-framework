package Locators;

import org.openqa.selenium.By;

public class UserManagementPom {


    public static class createUser {

        public static By userManagementSection = By.xpath("(//div[@class='icon-wrapper'])[2]");
        public static By add = By.xpath("//button[text()='Add Users']");
        public static By addUsersBtn = By.xpath("//button[@class=' add-directory']");
        public static By addUserManually = By.xpath("//p[text()='Manually']");
        public static By userType = By.xpath("(//div[@id='select-select-field'])[1]");
        public static By userTypeDropdownOptions = By.xpath("//div[@class='Select-module_custom-select-option__4BfXL ']");
        public static By firstName = By.xpath("//input[@name='firstName']");
        public static By lastName = By.xpath("//input[@name='lastName']");
        public static By workEmailId = By.xpath("//input[@name='email']");
        public static By nextButton = By.xpath("//button[@type='submit']");
        public static By createdUserToaster = By.xpath("//div[@class='toast-title']");
        public static By particularUserDetailsDelete = By.xpath("/html/body/div/div/div/div/div[2]/div[2]/div/section/div/div[2]/div[2]/table/tbody/tr");
        public static By nextPagination = By.xpath("//button[text()='Next']");
        public static By editUserDetails = By.xpath("//button[text()='Edit User Details']");
        public static By addAppsButton = By.xpath("//button[text()='Add apps (SSO/ Provisioning)']");
        public static By appSelectOptions = By.xpath("//p[@class='text-sm-medium text-left line-clamp-2']");
        public static By appSubmit = By.xpath("//button[text()='Submit']");
        public static By saveUserDetails = By.xpath("//button[text()='Save Changes']");
        public static By editSuccessMessage = By.xpath("//div[@class='toast-content']");
        public static By userDeleteOption = By.xpath("//div[@class='flex gap-3 justify-end pr-1']/button[2]");
        public static By userDeleteConfirmation = By.xpath("//div[@class='w-full flex justify-end gap-4']/button[2]");

        public static By telephoneNumber = By.xpath("//input[@id='mobileNumber']");
        public static By personalEmail = By.xpath("//input[@name='personalEmail']");
        public static By editPersonalEmail = By.name("personalEmail");
        public static By userManagementToastMessage = By.xpath("//div[@class='toast-title']");
        public static By userEnableAndDisableToastMessage = By.xpath("//div[@class='toast-message text-md-regular']");
        public static By groupFilterBtn = By.xpath("//input[contains(@placeholder,\"Select Groups\")]");
        public static By filterList = By.xpath("//div[@class='multi-select__option-left']/span");
        public static By checkBox = By.xpath("(//div[@class='w-[20px] h-[20px] border rounded-md flex items-center justify-center transition border-[#D5D7DA]'])");
        public static By statusFilter = By.xpath("//input[contains(@placeholder,\"Select status\")]");
        public static By delete = By.xpath("(//span[@class='material-icons-outlined text-[#667085]'])[2]");
        public static By AddIndividualApp = By.xpath("(//span[text()='add'])[1]");

        public static By addButton = By.xpath("(//span[text()='Add'])[1]");
        public static By UpdateBasicDetails = By.xpath("//button[@type='submit']");
        public static By UserUpdatePopUp = By.xpath("//div[text()='User details updated successfully.']");

        public static By UserDeleteIcon = By.xpath("//button[text()='Delete']");
        public static By UsersConfirmDeleteIcon = By.xpath("(//button[text()='Delete'])[2]");
        public static By UserDeletedPopUp = By.xpath("//div[text()='User deleted successfully.']");

        public static By tableId = By.xpath("//table[@class='w-full divide-y divide-gray-200 table-fixed']");
        public static By pTextAssertionMessage = By.xpath("//p[@class='text-[20px] pt-4 text-center font-medium']");
        public static By enableAndDisableUser = By.cssSelector("#default-switch");
        public static By clickDropdownOfSelectApp = By.xpath("//div[@class='ant-select ant-select-outlined h-14 w-full css-z548ic ant-select-multiple ant-select-show-arrow ant-select-show-search']");
        public static By selectApp = By.xpath("//div[@class='ant-select-item-option-content']/label");
        public static By unAssignApp = By.xpath("//span[@class='ant-checkbox ant-wave-target css-z548ic ant-checkbox-checked']");
        public static By yesButton = By.xpath("//span[text()='Yes']");
        public static By ssoEditBasicDetails = By.xpath("//div[@id='rc-tabs-0-tab-1']");
        public static By addApp = By.xpath("(//span[text()='Add'])[2]");

        public static By filterButton = By.xpath("(//button[contains(@class, 'toggle-box')])[1]");
        public static By filterSearch = By.xpath("//button[text()='Search']");
        public static By confirmEnableAndDisableUser = By.xpath("(//button[@class='Button-module_button__18Bed Button-module_contained__B-47X Button-module_primary__st6yY Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold'])[2]");

    }

    public static class LDAP {

        public static By directoryBtn = By.xpath("//button[text()='Directory']");
        public static By chooseDirectory = By.xpath("//span[@class='ant-select-selection-item']");
        public static By selectDirectory = By.xpath("//div[@class='ant-select-item-option-content']");
        public static By LDAPDirectory = By.xpath("(//div[@class='modal-component']//div[2]//div)[1]");
        public static By ldapName = By.xpath("//input[@name='ldapName']");
        public static By connectionURL = By.xpath("//input[@name='connectionUrl']");
        public static By bindDN = By.xpath("//input[@name='bindDn']");
        public static By bindCredentials = By.xpath("//input[@name='bindCredentials']");
        public static By submit = By.xpath("//button[@type='submit']");
        public static By InvalidCredentials = By.xpath("//div[text()='Please enter valid LDAP credentials.']");
        public static By InvalidCertificate = By.xpath("//div[text()='Please enter valid users DN/Certificate.']");
        public static By Done = By.xpath("(//button[@type='button'])[5]");
        public static By reviewMappingBtn = By.xpath("//button[@type='submit']");
        public static By done = By.xpath("//span[text()='Done']");
        public static By next = By.xpath("//button[@type='button']");
        public static By nextBtn = By.xpath("//span[text()='Next']");
        public static By syncBtn = By.xpath("//span[text()='Sync']");

        public static By userDN = By.xpath("//input[@name='usersDn']");
        public static By ldapAttribute = By.xpath("//input[@name='usernameLDAPAttribute']");
        public static By RDNldapAttribute = By.xpath("//input[@name='rdnLdapAttribute']");
        public static By UUIDLdapAttribute = By.xpath("//input[@name='uuidLdapAttribute']");
        public static By userObjectClasses = By.xpath("//input[@name='userObjectClasses']");
        public static By syncUpdateInterval = By.xpath("//input[@name='syncUpdateInterval']");
        public static By certificate = By.xpath("//textarea[@name='certificate']");

        public static By notificationMessage2 = By.xpath("//div[text()='Directory updated successfully.']");
        public static By deleteDirectory = By.xpath("//div[@class='ant-notification-notice-description']");
        public static By getLDAPName = By.cssSelector("div.directory.pt-2.flex-wrap > div > div > p");
        public static By LDAPBtn = By.cssSelector("input.block.w-full.h-full.absolute.opacity-0.cursor-pointer.top-0.left-0");
        public static By editLDAPButton = By.xpath("//span[@class='material-symbols-outlined pencil']");
        public static By deleteLDAP = By.xpath("//p[@class='delete cursor-pointer ']");
        public static By toggleBtn = By.xpath("//div[@class='ant-switch-handle']");
        public static By yesBtn = By.xpath("//span[text()='Yes']");
        public static By SyncGroup = By.xpath("(//img[@class='ant-image-img css-z548ic'])[2]");
        public static By SyncGroupMessage = By.xpath("//p[text()='User group synchronization process started. This may take some time.']");

    }

    public static class groups {

        public static By groupView = By.xpath("//button[text()='Manage Groups']");
        public static By addGroup = By.xpath("//button[text()='Add New Group']");
        public static By groupName = By.xpath("//input[@placeholder='Enter group name']");
        public static By groupDescription = By.xpath("//textarea[@placeholder='Enter description']");
        public static By nextButton = By.xpath("//button[text()='Next']");
        public static By next2 = By.xpath("(//button[text()='Next'])[2]");
        public static By addApps = By.xpath("(//button[@class='icon-box hover:!bg-[#fdf9f9] cursor-pointer'])[1]");
        public static By groupNameSearch = By.xpath("//p[@class='text-sm-medium cursor-pointer']");

        public static By selectApps = By.xpath("//p[@class='text-md-semibold text-primary']");
        public static By submit = By.xpath("//button[@type='submit']");
        public static By notificationMessage = By.xpath("//div[text()='Successfully deleted selected groups.']");
        public static By add = By.xpath("//span[text()='Add']");
        public static By delete = By.xpath("(//button[text()='Delete'])[2]");
        public static By deleteIcon = By.xpath("//button[text()='Delete']");
        public static By GroupCreatedPopUp = By.xpath("//div[text()='Group created successfully. If users are assigned to the group, you will be notified once all assigned users have been added.']");
        public static By GroupupdatedPopUp = By.xpath("//div[text()='Successfully updated group details.']");
        public static By GetGroupTableId = By.xpath("//table[@class='w-full divide-y divide-gray-200 table-fixed']");
        public static By GroupCheckboxClick = By.xpath("//tbody[@class='bg-white']/tr/td/div/label");

        public static By groupTable = By.xpath("//table[@class='w-full divide-y divide-gray-200 table-fixed']");
        public static By groupCheckbox2 = By.xpath("(//label[@class='inline-flex items-center cursor-pointer'])[position() > 1]");
        public static By usermanagementtablewords = By.xpath("//p[@class='text-sm-medium line-clamp-1 max-w-[200px] text-ellipsis text-left']");
        public static By statusAndGroupsWords = By.xpath("//table[@class='w-full divide-y divide-gray-200 table-fixed']/tbody/tr/td/div/span");
        public static By update = By.xpath("//span[text()='Update']");
        public static By deallocateUserMessage = By.xpath("//div[text()='You will be notified once user(s) de-allocated from the group.']");
        public static By groupsToastMessage = By.xpath("//div[@class='toast-message text-md-regular']");
        public static By saveGroup = By.xpath("//button[@class='Button-module_button__18Bed Button-module_contained__B-47X Button-module_primary__st6yY Button-module_medium__HYxwi Button-module_responsive__wGjrI text-sm-semibold']");


    }

    public static class BulkUpload {

        public static By csvOption = By.xpath("//p[text()='CSV Upload']");//button[text()='CSV']
        public static By fileInputLocator = By.xpath("//input[@type='file']");

    }

    public static class profilePom {

        public static By adminprofileClick = By.xpath("//div[@class='relative cursor-pointer notification-icon']");
        public static By userProfileClick = By.xpath("(//button[@type='button'])[1]");
        public static By firstName = By.xpath("//input[@name='firstName']");
        public static By lastName = By.xpath("//input[@name='lastName']");
        public static By personalEmail = By.xpath("//input[@name='email']");
        public static By teleNumber = By.id("phoneNumber");
        public static By updateButton = By.xpath("//button[text()='Update']");
        public static By changePassword = By.xpath("//span[text()='Change Password']");
        public static By oldPassword = By.name("oldPassword");
        public static By newPassword = By.name("password");
        public static By confirmPassword = By.name("confirmPassword");
        public static By changePasswordUpdateButton = By.xpath("//button[@type='submit']");
        public static By photoInputFile = By.xpath("//input[@type='file']");
        public static By view_Profile = By.xpath("//span[text()='View Profile']");
        public static By updateProfile = By.xpath("//span[text()='Update Profile']");
    }

    public static class Consent {

        public static By Terms = By.xpath("//p[text()='Terms & Conditions']");
        public static By Terms_popup = By.xpath("//span[text()='Close']");
        public static By Text = By.xpath("//span [text()='I accept the terms and conditions']");
        public static By Proceed = By.xpath("//span[text()='Proceed']");
        public static By Profile = By.xpath("//p[@class='text-[#fff] font-semibold font-Inter capitalize initial']");
        public static By Profile_Conditions = By.xpath("//span[text()='Terms & Conditions']");

    }

}

