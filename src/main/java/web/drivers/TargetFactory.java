package web.drivers;

import enums.RunMode;
import exceptions.TargetNotValidException;
import utilities.logger.Log;
import web.browsers.BrowserFactory;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import static constants.FrameworkConstants.*;
import java.net.URL;

public class TargetFactory {

	public WebDriver createInstance(String browser) {
		RunMode runMode = RunMode.valueOf(RUN_MODE.toUpperCase());
		WebDriver webdriver;
		switch (runMode) {
		case LOCAL:
			// Create new driver from Enum setup in BrowserFactory class
			webdriver = BrowserFactory.valueOf(browser.toUpperCase()).createDriver();
			break;
		case GRID:
			// Create new driver on Cloud (Selenium Grid, Docker) from method below
			webdriver = createRemoteInstance(BrowserFactory.valueOf(browser.toUpperCase()).getOptions());
			break;
		default:
			throw new TargetNotValidException(runMode.toString());
		}
		return webdriver;
	}

	private RemoteWebDriver createRemoteInstance(MutableCapabilities capability) {
		RemoteWebDriver remoteWebDriver = null;
		try {
			String gridURL = String.format("http://%s:%s", REMOTE_URL, REMOTE_PORT);

			remoteWebDriver = new RemoteWebDriver(new URL(gridURL), capability);
		} catch (java.net.MalformedURLException e) {
			Log.error("Grid URL is invalid or Grid Port is not available");
			Log.error(String.format("Browser: %s", capability.getBrowserName()), e);
		} catch (IllegalArgumentException e) {
			Log.error(String.format("Browser %s is not valid or recognized", capability.getBrowserName()), e);
		}

		return remoteWebDriver;
	}

}