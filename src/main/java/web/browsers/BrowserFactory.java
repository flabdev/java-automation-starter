package web.browsers;

import exceptions.HeadlessNotSupportedException;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import static java.lang.Boolean.TRUE;
import static constants.FrameworkConstants.*;

public enum BrowserFactory {

	CHROME {
		@Override
		public WebDriver createDriver() {
			WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
			return new ChromeDriver(getOptions());
		}

		@Override
		public ChromeOptions getOptions() {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--disable-infobars");
			chromeOptions.addArguments("--disable-notifications");
			chromeOptions.addArguments("--remote-allow-origins=*");
			chromeOptions.setHeadless(Boolean.parseBoolean(HEADLESS));
			return chromeOptions;
		}
	},
	FIREFOX {
		@Override
		public WebDriver createDriver() {
			WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
			return new FirefoxDriver(getOptions());
		}

		@Override
		public FirefoxOptions getOptions() {
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setHeadless(Boolean.parseBoolean(HEADLESS));
			return firefoxOptions;
		}
	},
	EDGE {
		@Override
		public WebDriver createDriver() {
			WebDriverManager.getInstance(DriverManagerType.EDGE).setup();
			return new EdgeDriver(getOptions());
		}

		@Override
		public EdgeOptions getOptions() {
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.setHeadless(Boolean.parseBoolean(HEADLESS));
			return edgeOptions;
		}
	},
	SAFARI {
		@Override
		public WebDriver createDriver() {
			WebDriverManager.getInstance(DriverManagerType.SAFARI).setup();
			return new SafariDriver(getOptions());
		}

		@Override
		public SafariOptions getOptions() {
			SafariOptions safariOptions = new SafariOptions();
			safariOptions.setAutomaticInspection(false);

			if (TRUE.equals(Boolean.valueOf(HEADLESS)))
				throw new HeadlessNotSupportedException(safariOptions.getBrowserName());
			return safariOptions;
		}
	};

	public abstract WebDriver createDriver();

	public abstract MutableCapabilities getOptions();
}
