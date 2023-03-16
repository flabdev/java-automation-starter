package web.pageobjects;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import web.actions.WebUI;

public class MyGroupsPage {

	public WebDriver driver;

	public MyGroupsPage(WebDriver driver) {
		this.driver = driver;
	}

	public By my_groups_button = By.xpath("//div[@class='btn-group']");
	public By group_name_field = By.xpath("//input[@name='Group Name']");
	public By group_members_field = By.xpath("//*[@name='comma-email']");
	public By add_emails_button = By.xpath("//button[@aria-label='add emails']");
	public By create_button = By.xpath("//button[@aria-label='save']");
	public By edit_button = By.xpath("//button[@title='Edit']");
	public By delete_button = By.xpath("//button[@title='Delete']");
	public By ok_button = By.xpath("//button[text()='OK']");

	public MyGroupsPage clickOnCreateGroup() throws IOException {
		WebUI.click(my_groups_button, "Click on My Group Button", false, false, 2);
		return this;
	}

	public MyGroupsPage enterGroupName(String group_name) throws IOException {
		WebUI.clear(group_name_field, "Clear Group Name");
		WebUI.sendKeys(group_name_field, group_name, "Enter Group Name");
		return this;
	}

	public MyGroupsPage enterGroupMembers(String group_members) throws IOException {
		WebUI.hardWait(2);
		WebUI.sendKeys(group_members_field, group_members, "Enter Group Members");
		return this;
	}

	public MyGroupsPage clickOnAddEmailsButton() throws IOException {
		WebUI.click(add_emails_button, "Click on Emails Button", false, false, 2);
		return this;
	}

	public MyGroupsPage clickOnCreateButton() throws IOException {
		WebUI.click(create_button, "Click on Create Button", false, true, 2);
		return this;
	}

	public MyGroupsPage ClickOnEditButton() throws IOException {
		WebUI.click(edit_button, "Click on Edit Button", false, false, 2);
		return this;
	}

	public MyGroupsPage clickOnDeleteButton() throws IOException {
		WebUI.click(delete_button, "Click on Delete Button", false, true, 2);
		return this;
	}

	public MyGroupsPage clickOnOkButton() throws IOException {
		WebUI.click(ok_button, "Click on OK Button", false, true, 2);
		return this;
	}

}