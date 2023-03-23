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
import config.EnvionmentFactory;

public class SauceDemoSuite extends UIBaseTest {

	@FrameworkAnnotation(category = { CategoryType.SANITY })
	@Test(priority = 1, dataProvider = "LoginDataProvider", dataProviderClass = DataProviders.class)
	public void LoginTest(String username, String password) throws IOException, InterruptedException {
		LoginPage loginPage = new LoginPage(DriverManager.getDriver());
		HomePage homePage = new HomePage(DriverManager.getDriver());
		loginPage.navigateURL(EnvionmentFactory.WebURL()).enterUsername(username).enterPassword(password)
				.clickOnLoginButton();
		homePage.validateHeaderMessageAfterLogin().clickOnMenu().clickOnLogout();
	}

}