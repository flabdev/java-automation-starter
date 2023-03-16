package web.cloud;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utilities.system.OSUtil;

import static constants.FrameworkConstants.*;
public class BrowserStackFactory {

	public WebDriver initBrowserStackDriver(String browserName) throws MalformedURLException {
		final String URL = "https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_PASSWORD
				+ "@hub-cloud.browserstack.com/wd/hub";
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", browserName);
		// capabilities.setCapability("browserVersion", version);
		HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
		browserstackOptions.put("os", OSUtil.getOS());
		browserstackOptions.put("osVersion", "10");
		browserstackOptions.put("projectName", "PROJECT_NAME");
		browserstackOptions.put("buildName", "BUILD_NAME");
		browserstackOptions.put("sessionName", "SESSION_NAME");
		browserstackOptions.put("local", "true");
		browserstackOptions.put("debug", "true");
		browserstackOptions.put("consoleLogs", "info");
		browserstackOptions.put("networkLogs", "true");
		browserstackOptions.put("browserstack", browserstackOptions);
		capabilities.setCapability("bstack:options", browserstackOptions);
		WebDriver driver = new RemoteWebDriver(new URL(URL), capabilities);
		return driver;

	}

}
