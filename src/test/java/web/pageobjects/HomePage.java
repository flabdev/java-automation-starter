package web.pageobjects;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import web.actions.WebUI;

public class HomePage {

	public WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public By header_title_message = By.xpath("//div[@class='app_logo']");
	public By menu = By.xpath("//button[@id='react-burger-menu-btn']");
	public By logout = By.xpath("//a[@id='logout_sidebar_link']");

	public HomePage validateHeaderMessageAfterLogin() throws IOException {
		WebUI.assertTextEquals(header_title_message, "Swag Labs", "Validate Header Message After Login", true);
		return this;
	}

	public HomePage clickOnMenu() throws IOException {
		WebUI.click(menu, "Click on Menu", false, false, 2);
		return this;
	}

	public HomePage clickOnLogout() throws IOException {
		WebUI.click(logout, "Click on Logout", false, true, 2);
		return this;
	}

}