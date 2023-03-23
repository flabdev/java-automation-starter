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

}
