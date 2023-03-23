package web.pageobjects;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import web.actions.WebUI;

public class LoginPage {

	public WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public By username_field = By.xpath("//input[@id='user-name']");
	public By password_field = By.xpath("//input[@id='password']");
	public By login_button = By.xpath("//input[@id='login-button']");

	public LoginPage navigateURL(String url) throws IOException, InterruptedException {
		WebUI.navigateURL(url);
		WebUI.waitForPageLoaded();
		return this;
	}

	public LoginPage enterUsername(String userName) throws IOException {
		WebUI.sendKeys(username_field, userName, "Enter Email Address");
		return this;
	}

	public LoginPage enterPassword(String password) throws IOException {
		WebUI.sendKeys(password_field, password, "Enter Password", false, false);
		return this;
	}

	public LoginPage clickOnLoginButton() throws IOException {
		WebUI.click(login_button, "Click on Login Button", true, false, 2);
		return this;
	}
}