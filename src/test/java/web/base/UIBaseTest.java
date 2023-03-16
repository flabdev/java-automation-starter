package web.base;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import listners.TestListener;
import web.drivers.DriverManager;
import web.drivers.TargetFactory;
import static constants.FrameworkConstants.*;


@Listeners({ TestListener.class })
public class UIBaseTest {
	@BeforeClass(alwaysRun = true)
	public void createDriver() throws IOException {
		WebDriver driver = new TargetFactory().createInstance(BROWSER);
		driver.manage().window().maximize();
		DriverManager.setDriver(driver);	
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		DriverManager.getDriver().quit();
	}

}
