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

	public By dashboard_dropdown = By
			.xpath("//li[@class='nav-item b-nav-dropdown dropdown' and @variant='primary']//span");
	public By profile_dropdown = By.xpath("(//li[@class='nav-item b-nav-dropdown dropdown'])[2]");
	public By my_groups = By.xpath("//ul[@class='dropdown-menu dropdown-menu-right show']//i[@class='fa fa-users']");

	public HomePage validateDashboard() throws IOException {
		WebUI.assertElement(dashboard_dropdown, "Validating Dashboard droddown is displaying", true);
		return this;
	}

	public HomePage clickOnProfileDropdown() throws IOException {
		WebUI.click(profile_dropdown, "Click on Profile Dropdown", false, false, 2);
		return this;
	}

	public HomePage clickOnMyGroups() throws IOException {
		WebUI.click(my_groups, "Click on My Groups", false, false, 2);
		return this;
	}

}