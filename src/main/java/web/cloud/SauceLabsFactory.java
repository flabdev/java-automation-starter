package web.cloud;

import java.net.MalformedURLException;
import java.net.URL;
import static constants.FrameworkConstants.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utilities.system.OSUtil;

public class SauceLabsFactory {

	public WebDriver initSauceDriver(String browserName) throws MalformedURLException {
		final String URL = "https://" + SAUCE_LABS_USERNAME + ":" + SAUCE_LABS_PASSWORD
				+ "@ondemand.eu-central-1.saucelabs.com:443/wd/hub";
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", browserName);
		// capabilities.setCapability("version", Version);
		capabilities.setCapability("platform", OSUtil.getOS());
		WebDriver driver = new RemoteWebDriver(new URL(URL), capabilities);
		return driver;
	}

}
