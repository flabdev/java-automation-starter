package web.dataproviders;

import org.testng.annotations.DataProvider;
import dataproviders.properties.PropertyFileReader;

public class DataProviders {

	@DataProvider(name = "LoginDataProvider")
	public Object[][] Login() {
		PropertyFileReader propertyFileReader = new PropertyFileReader(
				System.getProperty("user.dir") + "/src/test/resources/Data.properties");
		String login_username = propertyFileReader.getValue("USERNAME");
		String login_password = propertyFileReader.getValue("PASSWORD");
		return new Object[][] { { login_username, login_password } };
	}
	
	@DataProvider(name = "GroupsEmailsDataProvider")
	public Object[][] GroupEmails() {
		PropertyFileReader propertyFileReader = new PropertyFileReader(
				System.getProperty("user.dir") + "/src/test/resources/Data.properties");
		String email1 = propertyFileReader.getValue("EmailAddress_1");
		String email2 = propertyFileReader.getValue("EmailAddress_2");
		return new Object[][] { { email1, email2 } };
	}

}
