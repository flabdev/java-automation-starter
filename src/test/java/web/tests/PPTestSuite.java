package web.tests;

import java.io.IOException;

import org.testng.annotations.Test;
import enums.CategoryType;
import web.annotations.FrameworkAnnotation;
import web.base.UIBaseTest;
import web.dataproviders.DataProviders;
import web.drivers.DriverManager;
import web.pageobjects.HomePage;
import web.pageobjects.LoginPage;
import web.pageobjects.MyGroupsPage;
import config.EnvionmentFactory;

public class PPTestSuite extends UIBaseTest {

	@FrameworkAnnotation(category = { CategoryType.SANITY })
	@Test(priority = 1, dataProvider = "LoginDataProvider", dataProviderClass = DataProviders.class)
	public void LoginTest(String email_address, String password) throws IOException, InterruptedException {
		LoginPage loginPage = new LoginPage(DriverManager.getDriver());
		HomePage homePage = new HomePage(DriverManager.getDriver());
		loginPage.navigateURL(EnvionmentFactory.WebURL()).enterEmailAddress(email_address)
				.enterPassword(password).clickOnLoginButton();
		homePage.validateDashboard();
		
	}
	
	@FrameworkAnnotation(category = { CategoryType.REGRESSION })
	@Test(priority = 2, dataProvider = "GroupsEmailsDataProvider", dataProviderClass = DataProviders.class)
	public void AddGroupTest(String email1, String email2) throws IOException, InterruptedException {
		HomePage homePage = new HomePage(DriverManager.getDriver());
		homePage.clickOnProfileDropdown().clickOnMyGroups();
		MyGroupsPage myGroupsPage = new MyGroupsPage(DriverManager.getDriver());
		myGroupsPage.clickOnCreateGroup();
		myGroupsPage.enterGroupName("TestDemo");
		myGroupsPage.enterGroupMembers(email1);
		myGroupsPage.clickOnAddEmailsButton();
		myGroupsPage.clickOnCreateButton();
	}
	
	@FrameworkAnnotation(category = { CategoryType.REGRESSION })
	@Test(priority = 3, dataProvider = "PathPresenterGroupsEmailsDataProvider", dataProviderClass = DataProviders.class)
	public void UpdateGroupTest(String email1, String email2) throws IOException, InterruptedException {
		MyGroupsPage myGroupsPage = new MyGroupsPage(DriverManager.getDriver());
		myGroupsPage.ClickOnEditButton();
		myGroupsPage.enterGroupName("PathPresenterDemo");
		myGroupsPage.enterGroupMembers(email2);
		myGroupsPage.clickOnAddEmailsButton();
		myGroupsPage.clickOnCreateButton();
		myGroupsPage.clickOnOkButton();
	}
	
	@FrameworkAnnotation(category = { CategoryType.REGRESSION })
	@Test(priority = 4)
	public void DeleteGroupTest() throws IOException, InterruptedException {
		MyGroupsPage myGroupsPage = new MyGroupsPage(DriverManager.getDriver());
		myGroupsPage.clickOnDeleteButton();
		myGroupsPage.clickOnOkButton();
	}


}