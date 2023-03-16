package web.actions;

import com.aventstack.extentreports.Status;

import reporting.ExtentReportManager;
import reporting.ExtentTestManager;
import utilities.logger.Log;
import web.drivers.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.*;
import static constants.FrameworkConstants.*;

public class WebUI {

	private static SoftAssert softAssert = new SoftAssert();

	/**
	 * Stop Soft Assert All
	 */
	public static void stopSoftAssertAll() {
		softAssert.assertAll();
	}

	/**
	 * 
	 */
	public static void hardWait() {
		if (ACTIVE_PAGE_LOADED.trim().toLowerCase().equals("true")) {
			waitForPageLoaded();
		}
		sleep(WAIT_SLEEP_STEP);
	}

	/**
	 * 
	 * @param timeOut
	 */
	public static void hardWait(int timeOut) {
		sleep(timeOut);
	}

	/**
	 * Get Path of the Download Directory
	 * 
	 * @return
	 */
	public static String getPathDownloadDirectory() {
		String path = "";
		String machine_name = System.getProperty("user.home");
		path = machine_name + File.separator + "Downloads";
		return path;
	}

	/**
	 * Get the count of files in the Download Directory
	 * 
	 * @return
	 */
	public static int countFilesInDownloadDirectory() {
		String pathFolderDownload = getPathDownloadDirectory();
		File file = new File(pathFolderDownload);
		int i = 0;
		for (File listOfFiles : file.listFiles()) {
			if (listOfFiles.isFile()) {
				i++;
			}
		}
		return i;
	}

	/**
	 * Verify File Contains in Download Directory
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean verifyFileContainsInDownloadDirectory(String fileName) {
		boolean flag = false;
		try {
			String pathFolderDownload = getPathDownloadDirectory();
			File dir = new File(pathFolderDownload);
			File[] files = dir.listFiles();
			if (files == null || files.length == 0) {
				flag = false;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().contains(fileName)) {
					flag = true;
				}
			}
			return flag;
		} catch (Exception e) {
			e.getMessage();
			return flag;
		}
	}

	/**
	 * Verify File Equals in Download Directory
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean verifyFileEqualsInDownloadDirectory(String fileName) {
		boolean flag = false;
		try {
			String pathFolderDownload = getPathDownloadDirectory();
			File dir = new File(pathFolderDownload);
			File[] files = dir.listFiles();
			if (files == null || files.length == 0) {
				flag = false;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().equals(fileName)) {
					flag = true;
				}
			}
			return flag;
		} catch (Exception e) {
			e.getMessage();
			return flag;
		}
	}

	/**
	 * Verify File Contains in Download Directory
	 * 
	 * @param fileName
	 * @param timeoutSeconds
	 * @return
	 */
	public static Boolean verifyDownloadFileContainsNameCompletedWaitTimeout(String fileName, int timeoutSeconds) {
		boolean check = false;
		int i = 0;
		while (i < timeoutSeconds) {
			boolean exist = verifyFileContainsInDownloadDirectory(fileName);
			if (exist == true) {
				i = timeoutSeconds;
				return check = true;
			}
			sleep(1);
			i++;
		}
		return check;
	}

	/**
	 * Verify File Equals in Download Directory
	 * 
	 * @param fileName
	 * @param timeoutSeconds
	 * @return
	 */
	public static Boolean verifyDownloadFileEqualsNameCompletedWaitTimeout(String fileName, int timeoutSeconds) {
		boolean check = false;
		int i = 0;
		while (i < timeoutSeconds) {
			boolean exist = verifyFileEqualsInDownloadDirectory(fileName);
			if (exist == true) {
				i = timeoutSeconds;
				return check = true;
			}
			sleep(1);
			i++;
		}
		return check;
	}

	/**
	 * Delete All Files in Download Directory
	 */
	public static void deleteAllFileInDownloadDirectory() {
		try {
			String pathFolderDownload = getPathDownloadDirectory();
			File file = new File(pathFolderDownload);
			File[] listOfFiles = file.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					new File(listOfFiles[i].toString()).delete();
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * Delete All Files in Specific Directory
	 * 
	 * @param pathDirectory
	 */
	public static void deleteAllFileInDirectory(String pathDirectory) {
		try {
			File file = new File(pathDirectory);
			File[] listOfFiles = file.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					new File(listOfFiles[i].toString()).delete();
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * Download the file using JS
	 * 
	 * @param fileName
	 * @return
	 */
	public static Boolean verifyFileDownloadedWithJS(String fileName) {
		getURL("chrome://downloads");
		sleep(3);
		JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
		String element = (String) js.executeScript(
				"return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('#show').getAttribute('title')");
		File file = new File(element);
		Log.info(element);
		Log.info(file.getName());
		if (file.exists() && file.getName().trim().equals(fileName)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Set Windows Size
	 * 
	 * @param widthPixel
	 * @param heightPixel
	 */
	public static void setWindowSize(int widthPixel, int heightPixel) {
		DriverManager.getDriver().manage().window().setSize(new Dimension(widthPixel, heightPixel));
	}

	/**
	 * Set Window Position
	 * 
	 * @param X
	 * @param Y
	 */
	public static void setWindowPosition(int X, int Y) {
		DriverManager.getDriver().manage().window().setPosition(new Point(X, Y));
	}

	/**
	 * Maximize Window
	 */
	public static void maximizeWindow() {
		DriverManager.getDriver().manage().window().maximize();
	}

	/**
	 * Minimize Window
	 */
	public static void minimizeWindow() {
		DriverManager.getDriver().manage().window().minimize();
	}

	/**
	 * Screenshot on Element
	 * 
	 * @param by
	 * @param elementName
	 */
	public static void screenshotElement(By by, String elementName) {
		File scrFile = getWebElement(by).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File("./" + elementName + ".png"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Javascript Executor
	 * 
	 * @return
	 */
	public static JavascriptExecutor getJsExecutor() {
		JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
		return js;
	}

	/**
	 * Get WebElement
	 * 
	 * @param by
	 * @return
	 */
	public static WebElement getWebElement(By by) {
		return DriverManager.getDriver().findElement(by);
	}

	/**
	 * Get WebElements
	 * 
	 * @param by
	 * @return
	 */
	public static List<WebElement> getWebElements(By by) {
		return DriverManager.getDriver().findElements(by);
	}

	/**
	 * Sleep
	 * 
	 * @param second
	 */
	public static void sleep(double second) {
		try {
			Thread.sleep((long) (second * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Chrome Notification Allow
	 * 
	 * @return
	 */
	public static ChromeOptions notificationsAllow() {
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 1);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		return options;
	}

	/**
	 * Chrome Notification Block
	 * 
	 * @return
	 */
	public static ChromeOptions notificationsBlock() {
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		return options;
	}

	/**
	 * Upload File using SendKeys
	 * 
	 * @param by
	 * @param filePath
	 */
	public static void uploadFile(By by, String filePath, String stepDescription) {
		hardWait();
		try {
			waitForElementVisible(by);
			DriverManager.getDriver().findElement(by).sendKeys(filePath);
			Log.info(stepDescription + " : " + filePath.toString());
			ExtentReportManager.pass(stepDescription + " : " + filePath.toString());
		} catch (Exception e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Upload File using SendKeys
	 * 
	 * @param by
	 * @param filePath
	 */
	public static void uploadFile(By by, String filePath, String stepDescription, boolean screenshot) {
		hardWait();
		try {
			waitForElementVisible(by);
			DriverManager.getDriver().findElement(by).sendKeys(filePath);
			Log.info(stepDescription + " : " + filePath.toString());
			ExtentReportManager.pass(stepDescription + " : " + filePath.toString());
			if (screenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
		} catch (Exception e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Get Current URL
	 * 
	 * @return
	 */
	public static String getCurrentUrl() {
		hardWait();
		Log.info("Current Page Url: " + DriverManager.getDriver().getCurrentUrl());
		return DriverManager.getDriver().getCurrentUrl();
	}

	/**
	 * Get Page Title
	 * 
	 * @return
	 */
	public static String getPageTitle() {
		hardWait();
		String title = DriverManager.getDriver().getTitle();
		Log.info("Current Page Title: " + DriverManager.getDriver().getTitle());
		return title;
	}

	/**
	 * Verify Page Title
	 * 
	 * @param pageTitle
	 * @return
	 */
	public static boolean verifyPageTitle(String pageTitle) {
		hardWait();
		return getPageTitle().equals(pageTitle);
	}

	/**
	 * Verify Page Contains Text
	 * 
	 * @param text
	 * @return
	 */
	public static boolean verifyPageContainsText(String text) {
		hardWait();
		return DriverManager.getDriver().getPageSource().contains(text);
	}

	/**
	 * Verify Page URL
	 * 
	 * @param pageUrl
	 * @return
	 */
	public static boolean verifyPageUrl(String pageUrl) {
		hardWait();
		Log.info("Current URL: " + DriverManager.getDriver().getCurrentUrl());
		return DriverManager.getDriver().getCurrentUrl().contains(pageUrl.trim());
	}

	// Handle checkbox and radio button

	/**
	 * Verify Element Checked
	 * 
	 * @param by
	 * @return
	 */
	public static boolean verifyElementChecked(By by) {
		hardWait();
		boolean checked = getWebElement(by).isSelected();
		if (checked) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Verify Element Checked with message
	 * 
	 * @param by
	 * @param message
	 * @return
	 */
	public static boolean verifyElementChecked(By by, String message) {
		hardWait();
		waitForElementVisible(by);
		boolean checked = getWebElement(by).isSelected();
		if (checked) {
			return true;
		} else {
			Assert.fail(message);
			return false;
		}
	}

	// Handle dropdown

	/**
	 * Select Option Dynamic
	 * 
	 * @param by
	 * @param text
	 * @return
	 */
	public static boolean selectOptionDynamic(By by, String text) {
		hardWait();
		try {
			List<WebElement> elements = getWebElements(by);

			for (WebElement element : elements) {
				Log.info(element.getText());
				if (element.getText().toLowerCase().trim().contains(text.toLowerCase().trim())) {
					element.click();
					return true;
				}
			}
		} catch (Exception e) {
			Log.info(e.getMessage());
			e.getMessage();
		}
		return false;
	}

	/**
	 * Verify Dropdown Option exist
	 * 
	 * @param by
	 * @param text
	 * @return
	 */
	public static boolean verifyDropdownOptionExist(By by, String text) {
		hardWait();
		try {
			List<WebElement> elements = getWebElements(by);

			for (WebElement element : elements) {
				Log.info(element.getText());
				if (element.getText().toLowerCase().trim().contains(text.toLowerCase().trim())) {
					return true;
				}
			}
		} catch (Exception e) {
			Log.info(e.getMessage());
			e.getMessage();
		}
		return false;
	}

	/**
	 * Get total number of options
	 * 
	 * @param by
	 * @return
	 */
	public static int getOptionTotal(By by) {
		hardWait();
		try {
			List<WebElement> elements = getWebElements(by);
			return elements.size();
		} catch (Exception e) {
			Log.info(e.getMessage());
			e.getMessage();
		}
		return 0;
	}

	// Dropdown (Select Option)

	/**
	 * Verify total number of options
	 * 
	 * @param element
	 * @param total
	 */
	public static void verifyOptionTotal(By element, int total) {
		hardWait();
		Select select = new Select(getWebElement(element));
		Assert.assertEquals(total, select.getOptions().size());
	}

	/**
	 * Verify Selected Text
	 * 
	 * @param by
	 * @param text
	 * @return
	 */
	public static boolean verifySelectedByText(By by, String text) {
		sleep(WAIT_SLEEP_STEP);
		Select select = new Select(getWebElement(by));
		Log.info("Option Selected by text: " + select.getFirstSelectedOption().getText());
		return select.getFirstSelectedOption().getText().equals(text);
	}

	/**
	 * Verify Selected Value
	 * 
	 * @param by
	 * @param optionValue
	 * @return
	 */
	public static boolean verifySelectedByValue(By by, String optionValue) {
		sleep(WAIT_SLEEP_STEP);
		Select select = new Select(getWebElement(by));
		Log.info("Option Selected by value: " + select.getFirstSelectedOption().getAttribute("value"));
		return select.getFirstSelectedOption().getAttribute("value").equals(optionValue);
	}

	/**
	 * Verify Selected Index
	 * 
	 * @param by
	 * @param index
	 * @return
	 */
	public static boolean verifySelectedByIndex(By by, int index) {
		sleep(WAIT_SLEEP_STEP);
		boolean res = false;
		Select select = new Select(getWebElement(by));
		int indexFirstOption = select.getOptions().indexOf(select.getFirstSelectedOption());
		Log.info("The First Option selected by index: " + indexFirstOption);
		Log.info("Expected index: " + index);
		if (indexFirstOption == index) {
			res = true;
		} else {
			res = false;
		}
		return res;
	}

	// Handle frame iframe
	/**
	 * Switch To Frame By Index
	 * 
	 * @param index
	 */
	public static void switchToFrameByIndex(int index) {
		sleep(WAIT_SLEEP_STEP);
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_EXPLICIT),
				Duration.ofMillis(500));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
		// DriverManager.getDriver().switchTo().frame(Index);
	}

	/**
	 * Switch To Frame By ID or Name
	 * 
	 * @param IdOrName
	 */
	public static void switchToFrameByIdOrName(String IdOrName) {
		sleep(WAIT_SLEEP_STEP);

		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_EXPLICIT),
				Duration.ofMillis(500));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(IdOrName));
	}

	/**
	 * Switch To Frame By Element
	 * 
	 * @param by
	 */
	public static void switchToFrameByElement(By by) {
		sleep(WAIT_SLEEP_STEP);

		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_EXPLICIT),
				Duration.ofMillis(500));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
	}

	/**
	 * Switch To Frame Default Content
	 */
	public static void switchToDefaultContent() {
		sleep(WAIT_SLEEP_STEP);

		DriverManager.getDriver().switchTo().defaultContent();
	}

	/**
	 * Switch To Window
	 * 
	 * @param position
	 */
	public static void switchToWindow(int position) {
		sleep(WAIT_SLEEP_STEP);

		DriverManager.getDriver().switchTo()
				.window(DriverManager.getDriver().getWindowHandles().toArray()[position].toString());
	}

	/**
	 * Switch To Window or Tab
	 * 
	 * @param position
	 */
	public static void switchToTab(int position) {
		sleep(WAIT_SLEEP_STEP);

		DriverManager.getDriver().switchTo()
				.window(DriverManager.getDriver().getWindowHandles().toArray()[position].toString());
	}

	/**
	 * Verify Number of Tabs
	 * 
	 * @param number
	 * @return
	 */
	public static boolean verifyNumberOfTabs(int number) {
		return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_EXPLICIT))
				.until(ExpectedConditions.numberOfWindowsToBe(number));
	}

	/**
	 * Verify Number of Windows
	 * 
	 * @param number
	 * @return
	 */
	public static boolean verifyNumberOfWindows(int number) {
		return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_EXPLICIT))
				.until(ExpectedConditions.numberOfWindowsToBe(number));
	}

	/**
	 * Open New Tab
	 */
	public static void openNewTab() {
		sleep(WAIT_SLEEP_STEP);
		// Opens a new tab and switches to new tab
		DriverManager.getDriver().switchTo().newWindow(WindowType.TAB);
	}

	/**
	 * Open New Window
	 */
	public static void openNewWindow() {
		sleep(WAIT_SLEEP_STEP);
		// Opens a new window and switches to new window
		DriverManager.getDriver().switchTo().newWindow(WindowType.WINDOW);
	}

	/**
	 * Switch to Parent or Main Window
	 */
	public static void switchToMainWindow() {
		sleep(WAIT_SLEEP_STEP);
		DriverManager.getDriver().switchTo()
				.window(DriverManager.getDriver().getWindowHandles().toArray()[0].toString());
	}

	/**
	 * Switch to Parent or Main Window
	 * 
	 * @param originalWindow
	 */
	public static void switchToMainWindow(String originalWindow) {
		sleep(WAIT_SLEEP_STEP);
		DriverManager.getDriver().switchTo().window(originalWindow);
	}

	/**
	 * Switch to Last Child Window
	 */
	public static void switchToLastWindow() {
		hardWait();
		Set<String> windowHandles = DriverManager.getDriver().getWindowHandles();
		DriverManager.getDriver().switchTo()
				.window(DriverManager.getDriver().getWindowHandles().toArray()[windowHandles.size() - 1].toString());
	}

	/*
	 * ========== Handle Alert ==================
	 */

	/**
	 * Alert Accept
	 */
	public static void alertAccept() {
		hardWait();
		DriverManager.getDriver().switchTo().alert().accept();
	}

	/**
	 * Alert Dismiss
	 */
	public static void alertDismiss() {
		hardWait();
		DriverManager.getDriver().switchTo().alert().dismiss();
	}

	/**
	 * Get Alert Text
	 */
	public static void alertGetText() {
		hardWait();
		DriverManager.getDriver().switchTo().alert().getText();
	}

	/**
	 * Set Alert Text
	 * 
	 * @param text
	 */
	public static void alertSetText(String text) {
		hardWait();
		DriverManager.getDriver().switchTo().alert().sendKeys(text);
	}

	/**
	 * Verify Alert Present
	 * 
	 * @param timeOut
	 * @return
	 */
	public static boolean isAlertPresent(int timeOut) {
		hardWait();
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut),
					Duration.ofMillis(500));
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (Throwable error) {
			return false;
		}
	}

	// Handle Elements

	/**
	 * Get Elements Exist
	 */
	public static List<String> getListElementsText(By by) {
		hardWait();
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_EXPLICIT),
				Duration.ofMillis(500));
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		List<WebElement> listElement = getWebElements(by);
		List<String> listText = new ArrayList<>();

		for (WebElement e : listElement) {
			listText.add(e.getText());
		}

		return listText;
	}

	/**
	 * Verify Element To Be Clickable
	 * 
	 * @param by
	 * @return
	 */
	public static boolean verifyElementToBeClickable(By by) {
		hardWait();
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_EXPLICIT),
					Duration.ofMillis(500));
			wait.until(ExpectedConditions.elementToBeClickable(by));
			return true;
		} catch (Exception e) {
			Log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * Verify Element Present
	 * 
	 * @param by
	 * @return
	 */
	public static boolean verifyElementPresent(By by) {
		hardWait();
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_EXPLICIT),
					Duration.ofMillis(500));
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		} catch (Exception e) {
			Log.info("The element does NOT present. " + e.getMessage());
			Assert.assertTrue(false, "The element does NOT present. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Verify Element Present
	 * 
	 * @param by
	 * @param timeout
	 * @return
	 */
	public static boolean verifyElementPresent(By by, int timeout) {
		hardWait();
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		} catch (Exception e) {
			Log.info("The element does NOT present. " + e.getMessage());
			Assert.assertTrue(false, "The element does NOT present. " + e.getMessage());
			return false;
		}
	}

	/**
	 * verify Element Present with message
	 * 
	 * @param by
	 * @param message
	 * @return
	 */
	public static boolean verifyElementPresent(By by, String message) {
		hardWait();

		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_EXPLICIT),
					Duration.ofMillis(500));
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		} catch (Exception e) {
			if (message.isEmpty() || message == null) {
				Log.error("The element does NOT present. " + e.getMessage());
				Assert.assertTrue(false, "The element does NOT present. " + e.getMessage());
			} else {
				Log.error(message + e.getMessage());
				Assert.assertTrue(false, message + e.getMessage());
			}

			return false;
		}
	}

	/**
	 * Verify Element Present with message and timeout
	 * 
	 * @param by
	 * @param timeout
	 * @param message
	 * @return
	 */
	public static boolean verifyElementPresent(By by, int timeout, String message) {
		hardWait();

		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		} catch (Exception e) {
			if (message.isEmpty() || message == null) {
				Log.error("The element does NOT present. " + e.getMessage());
				Assert.assertTrue(false, "The element does NOT present. " + e.getMessage());
			} else {
				Log.error(message + e.getMessage());
				Assert.assertTrue(false, message + e.getMessage());
			}

			return false;
		}
	}

	/**
	 * Verify Element not Present
	 * 
	 * @param by
	 * @return
	 */
	public static boolean verifyElementNotPresent(By by) {
		hardWait();
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_EXPLICIT),
					Duration.ofMillis(500));
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			Log.error("The element presents. " + by);
			Assert.assertTrue(false, "The element presents. " + by);
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * Verify Element not Present with timeout
	 * 
	 * @param by
	 * @param timeout
	 * @return
	 */
	public static boolean verifyElementNotPresent(By by, int timeout) {
		hardWait();
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			Log.error("Element is present " + by);
			Assert.assertTrue(false, "The element presents. " + by);
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * Verify Element not Present with Message
	 * 
	 * @param by
	 * @param message
	 * @return
	 */
	public static boolean verifyElementNotPresent(By by, String message) {
		hardWait();
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_EXPLICIT),
					Duration.ofMillis(500));
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			if (message.isEmpty() || message == null) {
				Log.error("The element presents. " + by);
				Assert.assertTrue(false, "The element presents. " + by);
			} else {
				Log.error(message + by);
				Assert.assertTrue(false, message + by);
			}
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * Verify Element not Present with Message and timeout
	 * 
	 * @param by
	 * @param timeout
	 * @param message
	 * @return
	 */
	public static boolean verifyElementNotPresent(By by, int timeout, String message) {
		hardWait();
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			if (message.isEmpty() || message == null) {
				Log.error("The element presents. " + by);
				Assert.assertTrue(false, "The element presents. " + by);
			} else {
				Log.error(message + by);
				Assert.assertTrue(false, message + by);
			}
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * is Element Visible with timeout
	 * 
	 * @param by
	 * @param timeout
	 * @return
	 */
	public static boolean isElementVisible(By by, long timeout) {
		hardWait();
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * is Element Visible
	 * 
	 * @param by
	 * @return
	 */
	public static boolean isElementVisible(By by) {
		hardWait();
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_EXPLICIT),
					Duration.ofMillis(500));
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * is Element Visible with message
	 * 
	 * @param by
	 * @param message
	 * @return
	 */
	public static boolean isElementVisible(By by, String message) {
		hardWait();

		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_EXPLICIT),
					Duration.ofMillis(500));
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		} catch (Exception e) {
			if (message.isEmpty() || message == null) {
				Log.error("The element is not visible. " + by);
				Assert.assertTrue(false, "The element is not visible. " + by);
			} else {
				Log.error(message + by);
				Assert.assertTrue(false, message + by);
			}
			return false;
		}
	}

	/**
	 * is Element Visible with message and timeout
	 * 
	 * @param by
	 * @param timeout
	 * @param message
	 * @return
	 */
	public static boolean isElementVisible(By by, int timeout, String message) {
		hardWait();
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		} catch (Exception e) {
			if (message.isEmpty() || message == null) {
				Log.error("The element is not visible. " + by);
				Assert.assertTrue(false, "The element is not visible. " + by);
			} else {
				Log.error(message + by);
				Assert.assertTrue(false, message + by);
			}
			return false;
		}
	}

	/**
	 * Scroll To Element
	 * 
	 * @param element
	 */
	public static void scrollToElement(By element) {
		hardWait();
		JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("arguments[0].scrollIntoView(true);", getWebElement(element));
	}

	/**
	 * Scroll To Element
	 * 
	 * @param element
	 */
	public static void scrollToElement(WebElement element) {
		hardWait();
		JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/**
	 * Scroll To Position
	 * 
	 * @param X
	 * @param Y
	 */
	public static void scrollToPosition(int X, int Y) {
		hardWait();
		JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("window.scrollTo(" + X + "," + Y + ");");
	}

	/**
	 * Hover on Element
	 * 
	 * @param by
	 * @return
	 */
	public static boolean hoverElement(By by) {
		hardWait();
		try {
			Actions action = new Actions(DriverManager.getDriver());
			action.moveToElement(getWebElement(by)).perform();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Drag and Drop using JS
	 * 
	 * @param from
	 * @param to
	 */
	public static void dragAndDropJS(WebElement from, WebElement to) {
		hardWait();
		JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("function createEvent(typeOfEvent) {\n" + "var event =document.createEvent(\"CustomEvent\");\n"
				+ "event.initCustomEvent(typeOfEvent,true, true, null);\n" + "event.dataTransfer = {\n" + "data: {},\n"
				+ "setData: function (key, value) {\n" + "this.data[key] = value;\n" + "},\n"
				+ "getData: function (key) {\n" + "return this.data[key];\n" + "}\n" + "};\n" + "return event;\n"
				+ "}\n" + "\n" + "function dispatchEvent(element, event,transferData) {\n"
				+ "if (transferData !== undefined) {\n" + "event.dataTransfer = transferData;\n" + "}\n"
				+ "if (element.dispatchEvent) {\n" + "element.dispatchEvent(event);\n"
				+ "} else if (element.fireEvent) {\n" + "element.fireEvent(\"on\" + event.type, event);\n" + "}\n"
				+ "}\n" + "\n" + "function simulateHTML5DragAndDrop(element, destination) {\n"
				+ "var dragStartEvent =createEvent('dragstart');\n" + "dispatchEvent(element, dragStartEvent);\n"
				+ "var dropEvent = createEvent('drop');\n"
				+ "dispatchEvent(destination, dropEvent,dragStartEvent.dataTransfer);\n"
				+ "var dragEndEvent = createEvent('dragend');\n"
				+ "dispatchEvent(element, dragEndEvent,dropEvent.dataTransfer);\n" + "}\n" + "\n"
				+ "var source = arguments[0];\n" + "var destination = arguments[1];\n"
				+ "simulateHTML5DragAndDrop(source,destination);", from, to);
	}

	/**
	 * Drag and Drop
	 * 
	 * @param fromElement
	 * @param toElement
	 * @return
	 */
	public static boolean dragAndDrop(By fromElement, By toElement) {
		hardWait();
		try {
			Actions action = new Actions(DriverManager.getDriver());
			action.dragAndDrop(getWebElement(fromElement), getWebElement(toElement)).perform();
			// action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
			return true;
		} catch (Exception e) {
			Log.info(e.getMessage());
			return false;
		}
	}

	/**
	 * Drag and Drop of HTML5
	 * 
	 * @param fromElement
	 * @param toElement
	 * @return
	 */
	public static boolean dragAndDropHTML5(By fromElement, By toElement) {
		hardWait();
		try {
			Robot robot = new Robot();
			robot.mouseMove(0, 0);

			int X1 = getWebElement(fromElement).getLocation().getX()
					+ (getWebElement(fromElement).getSize().getWidth() / 2);
			int Y1 = getWebElement(fromElement).getLocation().getY()
					+ (getWebElement(fromElement).getSize().getHeight() / 2);
			System.out.println(X1 + " , " + Y1);

			int X2 = getWebElement(toElement).getLocation().getX()
					+ (getWebElement(toElement).getSize().getWidth() / 2);
			int Y2 = getWebElement(toElement).getLocation().getY()
					+ (getWebElement(toElement).getSize().getHeight() / 2);
			System.out.println(X2 + " , " + Y2);

			sleep(1);
			robot.mouseMove(X1, Y1 + 120);
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

			sleep(1);
			robot.mouseMove(X2, Y2 + 120);
			sleep(1);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

			return true;
		} catch (Exception e) {
			Log.info(e.getMessage());
			return false;
		}
	}

	/**
	 * Drag and Drop using off set values
	 * 
	 * @param fromElement
	 * @param X
	 * @param Y
	 * @return
	 */
	public static boolean dragAndDropToOffset(By fromElement, int X, int Y) {
		hardWait();
		try {
			Robot robot = new Robot();
			robot.mouseMove(0, 0);
			int X1 = getWebElement(fromElement).getLocation().getX()
					+ (getWebElement(fromElement).getSize().getWidth() / 2);
			int Y1 = getWebElement(fromElement).getLocation().getY()
					+ (getWebElement(fromElement).getSize().getHeight() / 2);
			System.out.println(X1 + " , " + Y1);
			sleep(1);
			// Header: chrome is being controlled by automated test software
			robot.mouseMove(X1, Y1 + 120);
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

			sleep(1);
			robot.mouseMove(X, Y + 120);
			sleep(1);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			return true;
		} catch (Exception e) {
			Log.info(e.getMessage());
			return false;
		}
	}

	/**
	 * Move to Element
	 * 
	 * @param toElement
	 * @return
	 */
	public static boolean moveToElement(By toElement) {
		hardWait();
		try {
			Actions action = new Actions(DriverManager.getDriver());
			action.moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
			return true;
		} catch (Exception e) {
			Log.info(e.getMessage());
			return false;
		}
	}

	/**
	 * Move to element using off set values
	 * 
	 * @param X
	 * @param Y
	 * @return
	 */
	public static boolean moveToOffset(int X, int Y) {
		hardWait();
		try {
			Actions action = new Actions(DriverManager.getDriver());
			action.moveByOffset(X, Y).build().perform();
			return true;
		} catch (Exception e) {
			Log.info(e.getMessage());
			return false;
		}
	}

	/**
	 * Enter Key
	 * 
	 * @return
	 */
	public static boolean pressENTER() {
		hardWait();

		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Enter Escape
	 * 
	 * @return
	 */
	public static boolean pressESC() {
		hardWait();

		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ESCAPE);
			robot.keyRelease(KeyEvent.VK_ESCAPE);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Press F5
	 * 
	 * @return
	 */
	public static boolean pressF5() {
		hardWait();

		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_F5);
			robot.keyRelease(KeyEvent.VK_F5);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Press F11
	 * 
	 * @return
	 */
	public static boolean pressF11() {
		hardWait();
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_F11);
			robot.keyRelease(KeyEvent.VK_F11);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Refresh the page
	 */
	public static void refresh() {
		hardWait();
		DriverManager.getDriver().navigate().refresh();
		waitForPageLoaded();
		Log.info("Refresh the Page");
	}

	/**
	 * Highlight on Element
	 * 
	 * @param by
	 * @return
	 */

	public static WebElement highLightElement(By by) {
		hardWait();
		// draw a border around the found element
		if (DriverManager.getDriver() instanceof JavascriptExecutor) {
			((JavascriptExecutor) DriverManager.getDriver()).executeScript(
					"arguments[0].style.border='4px solid yellow'", DriverManager.getDriver().findElement(by));
			sleep(1);
		}
		return getWebElement(by);
	}

	/**
	 * Open website with URL
	 *
	 * @param URL
	 */
	public static void getURL(String URL) {
		sleep(WAIT_SLEEP_STEP);
		DriverManager.getDriver().get(URL);
		waitForPageLoaded();
		Log.info("Open URL: " + URL);
		if (ExtentTestManager.getExtentTest() != null) {
			ExtentReportManager.pass("Navigate to URL: " + URL);
		}
	}

	/**
	 * Open website with navigate to URL
	 *
	 * @param URL
	 */
	public static void navigateURL(String URL) {
		DriverManager.getDriver().navigate().to(URL);
		waitForPageLoaded();
		if (ExtentTestManager.getExtentTest() != null) {
			ExtentReportManager.pass("Navigate to URL: " + URL);
		}
	}

	/**
	 * Get Text
	 * 
	 * @param by
	 * @return
	 */
	public static String getText(By by) {
		String text = null;
		try {
			if (ACTIVE_PAGE_LOADED.trim().toLowerCase().equals("true")) {
				waitForPageLoaded();
			}
			sleep(WAIT_SLEEP_STEP);
			text = DriverManager.getDriver().findElement(by).getText().trim();
			Log.info("Getting the Text : " + text);
			return text;
		} catch (Throwable e) {
			Log.info("Failed to get the Text : " + by.toString());
			throw e;
		}
	}

	/**
	 * Get Attribute Value
	 * 
	 * @param by
	 * @param attributeName
	 * @return
	 */
	public static String getAttributeElement(By by, String attributeName) {
		if (ACTIVE_PAGE_LOADED.trim().toLowerCase().equals("true")) {
			waitForPageLoaded();
		}
		sleep(WAIT_SLEEP_STEP);
		return DriverManager.getDriver().findElement(by).getAttribute(attributeName);
	}

	/**
	 * Get CSS Value
	 * 
	 * @param by
	 * @param cssName
	 * @return
	 */
	public static String getCssValueElement(By by, String cssName) {
		if (ACTIVE_PAGE_LOADED.trim().toLowerCase().equals("true")) {
			waitForPageLoaded();
		}
		sleep(WAIT_SLEEP_STEP);
		return DriverManager.getDriver().findElement(by).getCssValue(cssName);
	}

	/**
	 * Get Size Element
	 * 
	 * @param by
	 * @return
	 */
	public static Dimension getSizeElement(By by) {
		return DriverManager.getDriver().findElement(by).getSize();
	}

	/**
	 * get Location Element
	 * 
	 * @param by
	 * @return
	 */
	public static Point getLocationElement(By by) {
		return DriverManager.getDriver().findElement(by).getLocation();
	}

	/**
	 * get TagName Element
	 * 
	 * @param by
	 * @return
	 */
	public static String getTagNameElement(By by) {
		return DriverManager.getDriver().findElement(by).getTagName();
	}

	/**
	 * Wait For Element Visible
	 * 
	 * @param by
	 * @return
	 */
	public static void waitForElementVisible(By by) {
		hardWait();
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_EXPLICIT),
				Duration.ofMillis(500));
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	// Wait Page loaded

	/**
	 * Wait for Page Load
	 */
	public static void waitForPageLoaded() {
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_PAGE_LOADED),
				Duration.ofMillis(500));
		JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

		// wait for Javascript to loaded
		ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver)
				.executeScript("return document.readyState").toString().equals("complete");

		// Get JS is Ready
		boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

		// Wait Javascript until it is Ready!
		if (!jsReady) {
			Log.info("Javascript in NOT Ready!");
			// Wait for Javascript to load
			try {
				wait.until(jsLoad);
			} catch (Throwable error) {
				error.printStackTrace();
				Assert.fail("Timeout waiting for page load (Javascript). (" + WAIT_PAGE_LOADED + "s)");
			}
		}
	}

	/**
	 * Wait for jQuery Load
	 */
	public static void waitForJQueryLoad() {
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_PAGE_LOADED),
				Duration.ofMillis(500));
		JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

		// Wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = driver -> {
			assert driver != null;
			return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
		};

		// Get JQuery is Ready
		boolean jqueryReady = (Boolean) js.executeScript("return jQuery.active==0");

		// Wait JQuery until it is Ready!
		if (!jqueryReady) {
			Log.info("JQuery is NOT Ready!");
			try {
				// Wait for jQuery to load
				wait.until(jQueryLoad);
			} catch (Throwable error) {
				Assert.fail("Timeout waiting for JQuery load. (" + WAIT_PAGE_LOADED + "s)");
			}
		}
	}

	/**
	 * Wait for Angular Load
	 */
	public static void waitForAngularLoad() {
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(WAIT_PAGE_LOADED),
				Duration.ofMillis(500));
		JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
		final String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";

		// Wait for ANGULAR to load
		ExpectedCondition<Boolean> angularLoad = driver -> {
			assert driver != null;
			return Boolean.valueOf(((JavascriptExecutor) driver).executeScript(angularReadyScript).toString());
		};

		// Get Angular is Ready
		boolean angularReady = Boolean.parseBoolean(js.executeScript(angularReadyScript).toString());

		// Wait ANGULAR until it is Ready!
		if (!angularReady) {
			Log.warn("Angular is NOT Ready!");
			// Wait for Angular to load
			try {
				// Wait for jQuery to load
				wait.until(angularLoad);
			} catch (Throwable error) {
				Assert.fail("Timeout waiting for Angular load. (" + WAIT_PAGE_LOADED + "s)");
			}
		}

	}

	// Selenium Actions

	/**
	 * Clicking on WebElement
	 * 
	 * @param by
	 */
	public static void click(By by, String stepDescription, boolean beforeScreenshot, boolean afterScreenshot,
			int ScreenshotTimeout) {
		try {
			if (beforeScreenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
			if (DEMO_MODE) {
				highLightElement(by);
			}
			DriverManager.getDriver().findElement(by).click();
			WebUI.hardWait(ScreenshotTimeout);
			Log.info(stepDescription);
			ExtentReportManager.pass(stepDescription);
			if (afterScreenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Clicking on WebElement
	 * 
	 * @param by
	 */
	public static void click(By by, String stepDescription) {
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			DriverManager.getDriver().findElement(by).click();
			Log.info(stepDescription);
			ExtentReportManager.pass(stepDescription);
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Clicking on WebElement
	 * 
	 * @param by
	 */
	public static void click(By by, String stepDescription, boolean beforeScreenshot, boolean afterScreenshot) {
		try {
			waitForElementVisible(by);
			if (beforeScreenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
			if (DEMO_MODE) {
				highLightElement(by);
			}
			DriverManager.getDriver().findElement(by).click();
			Log.info(stepDescription);
			ExtentReportManager.pass(stepDescription);
			if (afterScreenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Click uisng JS
	 * 
	 * @param by
	 */
	public static void clickJS(By by, String stepDescription) {
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			// Scroll to element với Js
			JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
			js.executeScript("arguments[0].scrollIntoView(true);", getWebElement(by));
			js.executeScript("arguments[0].click();", getWebElement(by));
			Log.info(stepDescription);
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Click uisng JS
	 * 
	 * @param by
	 */
	public static void clickJS(By by, String stepDescription, boolean screenshot) {
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
			js.executeScript("arguments[0].scrollIntoView(true);", getWebElement(by));
			js.executeScript("arguments[0].click();", getWebElement(by));
			Log.info(stepDescription);
			if (screenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Click using LinkText
	 *
	 * @param linkText is the visible text of a link
	 */
	public static void clickLinkText(String linkText, String stepDescription) {
		try {
			waitForElementVisible(By.linkText(linkText));
			if (DEMO_MODE) {
				highLightElement(By.linkText(linkText));
			}
			DriverManager.getDriver().findElement(By.linkText(linkText)).click();
			Log.info(stepDescription);
			ExtentReportManager.pass(stepDescription);

		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Click using LinkText
	 *
	 * @param linkText is the visible text of a link
	 */
	public static void clickLinkText(String linkText, String stepDescription, boolean beforeScreenshot,
			boolean afterScreenshot) {
		try {
			waitForElementVisible(By.linkText(linkText));
			if (beforeScreenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
			if (DEMO_MODE) {
				highLightElement(By.linkText(linkText));
			}
			DriverManager.getDriver().findElement(By.linkText(linkText)).click();
			Log.info(stepDescription);
			ExtentReportManager.pass(stepDescription);
			if (afterScreenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Right Click
	 * 
	 * @param by
	 */
	public static void rightClick(By by) {
		Actions action = new Actions(DriverManager.getDriver());
		action.contextClick(DriverManager.getDriver().findElement(by)).build().perform();
	}

	/**
	 * Passing Data to the Input Field
	 * 
	 * @param by
	 * @param value
	 */
	public static void sendKeys(By by, String value, String stepDescription, boolean beforeScreenshot,
			boolean afterScreenshot) {
		try {
			waitForElementVisible(by);
			if (beforeScreenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
			if (DEMO_MODE) {
				highLightElement(by);
			}
			DriverManager.getDriver().findElement(by).sendKeys(value);
			Log.info(stepDescription + " : " + value.toString());
			ExtentReportManager.pass(stepDescription + " : " + value.toString());
			if (afterScreenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Passing Data to the Input Field
	 * 
	 * @param by
	 * @param value
	 */
	public static void sendKeys(By by, String value, String stepDescription) {
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			DriverManager.getDriver().findElement(by).sendKeys(value);
			Log.info(stepDescription + " : " + value.toString());
			ExtentReportManager.pass(stepDescription + " : " + value.toString());
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Passing Data to the Input Field
	 * 
	 * @param by
	 *
	 */
	public static void sendKeys(By by, Keys key) {
		try {
			waitForElementVisible(by);
			DriverManager.getDriver().findElement(by).sendKeys(key);
		} catch (Throwable e) {
			Log.error("Failed!! to Pass the Key");
			ExtentReportManager.fail("Failed!! to Pass the Key");
			throw e;
		}
	}

	/**
	 * Passing Data to the Input Field
	 * 
	 * @param key
	 */
	public static void sendKeys(Keys key) {
		try {
			Actions action = new Actions(DriverManager.getDriver());
			action.sendKeys(key).build().perform();
		} catch (Throwable e) {
			Log.error("Failed!! to Pass the Key");
			ExtentReportManager.fail("Failed!! to Pass the Key");
			throw e;
		}
	}

	/**
	 * Clear the Textbox Field
	 * 
	 * @param by
	 */
	public static void clear(By by, String stepDescription) {
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			DriverManager.getDriver().findElement(by).clear();
			Log.info(stepDescription);
			ExtentReportManager.pass(stepDescription);
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Clear the Textbox Field
	 * 
	 * @param by
	 */
	public static void clear(By by, String stepDescription, boolean beforeScreenshot, boolean afterScreenshot) {
		try {
			waitForElementVisible(by);
			if (beforeScreenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
			if (DEMO_MODE) {
				highLightElement(by);
			}
			DriverManager.getDriver().findElement(by).clear();
			Log.info(stepDescription);
			ExtentReportManager.pass(stepDescription);
			if (afterScreenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}

	}

	// Select

	/**
	 * Select Option By Text
	 * 
	 * @param by
	 * @param text
	 */
	public static void selectOptionByText(By by, String text, String stepDescription) {
		hardWait();
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			Select select = new Select(getWebElement(by));
			select.selectByVisibleText(text);
			Log.info(stepDescription + " : " + text.toString());
			ExtentReportManager.pass(stepDescription);
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Select Option By Value
	 * 
	 * @param by
	 * @param value
	 */
	public static void selectOptionByValue(By by, String value, String stepDescription) {
		hardWait();
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			Select select = new Select(getWebElement(by));
			select.selectByValue(value);
			Log.info(stepDescription + " : " + value.toString());
			ExtentReportManager.pass(stepDescription + " : " + value.toString());
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Select Option By Index
	 * 
	 * @param by
	 * @param index
	 */
	public static void selectOptionByIndex(By by, int index, String stepDescription) {
		hardWait();
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			Select select = new Select(getWebElement(by));
			select.selectByIndex(index);
			Log.info(stepDescription);
			ExtentReportManager.pass(stepDescription);
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Select Option By Text
	 * 
	 * @param by
	 * @param text
	 */
	public static void selectOptionByText(By by, String text, String stepDescription, boolean screenshot) {
		hardWait();
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			Select select = new Select(getWebElement(by));
			select.selectByVisibleText(text);
			Log.info(stepDescription + " : " + text.toString());
			ExtentReportManager.pass(stepDescription);
			if (screenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Select Option By Value
	 * 
	 * @param by
	 * @param value
	 */
	public static void selectOptionByValue(By by, String value, String stepDescription, boolean screenshot) {
		hardWait();
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			Select select = new Select(getWebElement(by));
			select.selectByValue(value);
			Log.info(stepDescription + " : " + value.toString());
			ExtentReportManager.pass(stepDescription + " : " + value.toString());
			if (screenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	/**
	 * Select Option By Index
	 * 
	 * @param by
	 * @param index
	 */
	public static void selectOptionByIndex(By by, int index, String stepDescription, boolean screenshot) {
		hardWait();
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			Select select = new Select(getWebElement(by));
			select.selectByIndex(index);
			Log.info(stepDescription);
			ExtentReportManager.pass(stepDescription);
			if (screenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
		} catch (Throwable e) {
			Log.error("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			throw e;
		}
	}

	// Assertions

	/**
	 * Assert Text Equals
	 * 
	 * @param by
	 * @param expected_text
	 * @param stepDescription
	 * @param screenshot
	 */
	public static void assertTextEquals(By by, String expected_text, String stepDescription, boolean screenshot) {
		String actual_text = null;
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			actual_text = DriverManager.getDriver().findElement(by).getText();
			Log.info("Actual Text :: " + actual_text);
			Log.info("Expected Text : " + expected_text);
			Assert.assertEquals(actual_text, expected_text);
			Log.info("Actual Text :: " + actual_text + " is matching with the " + expected_text);
			ExtentReportManager.pass(stepDescription);
			ExtentReportManager.pass("Actual Text :: " + actual_text + " is not matching with the " + expected_text);
			if (screenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
		} catch (Throwable e) {
			Log.error("Actual Text :: " + actual_text + " is matching with the " + expected_text);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Actual Text :: " + actual_text + " is not matching with the " + expected_text);
			throw e;
		}
	}

	/**
	 * Assert Text Equals
	 * 
	 * @param by
	 * @param expected_text
	 * @param stepDescription
	 */
	public static void assertTextEquals(By by, String expected_text, String stepDescription) {
		String actual_text = null;
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			actual_text = DriverManager.getDriver().findElement(by).getText();
			Log.info("Actual Text :: " + actual_text);
			Log.info("Expected Text : " + expected_text);
			Assert.assertEquals(actual_text, expected_text);
			Log.info("Actual Text :: " + actual_text + " is matching with the " + expected_text);
			ExtentReportManager.pass(stepDescription);
			ExtentReportManager.pass("Actual Text :: " + actual_text + " is matching with the " + expected_text);
		} catch (Throwable e) {
			Log.error("Actual Text :: " + actual_text + " is not matching with the " + expected_text);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Actual Text :: " + actual_text + " is not matching with the " + expected_text);
			throw e;
		}
	}

	/**
	 * Assert Text Contains
	 * 
	 * @param by
	 * @param expected_text
	 * @param stepDescription
	 * @param screenshot
	 */
	public static void assertTextContains(By by, String expected_text, String stepDescription, boolean screenshot) {
		String actual_text = null;
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			actual_text = DriverManager.getDriver().findElement(by).getText();
			Log.info("Actual Text :: " + actual_text);
			Log.info("Expected Text : " + expected_text);
			Assert.assertTrue(actual_text.contains(expected_text));
			Log.info("Expected Text :: " + expected_text + " contains in the " + actual_text);
			ExtentReportManager.pass(stepDescription);
			ExtentReportManager.pass("Expected Text :: " + expected_text + " contains in the " + actual_text);
			if (screenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
		} catch (Throwable e) {
			Log.error("Expected Text :: " + expected_text + " not contains in the " + actual_text);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Expected Text :: " + expected_text + " not contains in the " + actual_text);
			throw e;
		}
	}

	/**
	 * Assert Text Contains
	 * 
	 * @param by
	 * @param expected_text
	 * @param stepDescription
	 */
	public static void assertTextContains(By by, String expected_text, String stepDescription) {
		String actual_text = null;
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			actual_text = DriverManager.getDriver().findElement(by).getText();
			Log.info("Actual Text :: " + actual_text);
			Log.info("Expected Text : " + expected_text);
			Assert.assertTrue(actual_text.contains(expected_text));
			Log.info("Expected Text :: " + expected_text + " contains in the " + actual_text);
			ExtentReportManager.pass(stepDescription);
			ExtentReportManager.pass("Expected Text :: " + expected_text + " contains in the " + actual_text);
		} catch (Throwable e) {
			Log.error("Expected Text :: " + expected_text + " not contains in the " + actual_text);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Expected Text :: " + expected_text + " not contains in the " + actual_text);
			throw e;
		}
	}

	/**
	 * Assert Element
	 * 
	 * @param by
	 * @param stepDescription
	 * @param screenshot
	 */
	public static void assertElement(By by, String stepDescription, boolean screenshot) {
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			Assert.assertTrue(isElementVisible(by));
			Log.info("Element is displaying:: " + by.toString());
			Log.info(stepDescription.toString());
			ExtentReportManager.pass(stepDescription);
			if (screenshot) {
				ExtentReportManager.addScreenShot(Status.INFO);
			}
		} catch (Throwable e) {
			Log.error("Element is not displaying:: " + by.toString());
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Element is not displaying:: " + by.toString());
			throw e;
		}
	}

	/**
	 * Assert Element
	 * 
	 * @param by
	 * @param stepDescription
	 */
	public static void assertElement(By by, String stepDescription) {
		try {
			waitForElementVisible(by);
			if (DEMO_MODE) {
				highLightElement(by);
			}
			Assert.assertTrue(isElementVisible(by));
			Log.info("Element is displaying:: " + by.toString());
			Log.info(stepDescription.toString());
			ExtentReportManager.pass(stepDescription);
		} catch (Throwable e) {
			Log.error("Element is not displaying:: " + by.toString());
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Element is not displaying:: " + by.toString());
			throw e;
		}
	}

	/**
	 * Assert Title Title
	 * 
	 * @param expected_title
	 * @param stepDescription
	 */
	public static void assertTitle(String expected_title, String stepDescription) {
		String actual_title = null;
		try {
			actual_title = DriverManager.getDriver().getTitle();
			Log.info("Actual Title :: " + actual_title);
			Log.info("Expected Title : " + expected_title);
			Assert.assertEquals(actual_title, expected_title);
			Log.info(stepDescription.toString());
			Log.info("Actual Title :: " + actual_title + " is matching with the " + expected_title);
			ExtentReportManager.pass(stepDescription);
			ExtentReportManager.pass("Actual Title :: " + actual_title + " is matching with the " + expected_title);
		} catch (Throwable e) {
			Log.error("Actual Title :: " + actual_title + " is not matching with the " + expected_title);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Actual Title :: " + actual_title + " is not matching with the " + expected_title);
			throw e;
		}
	}

	/**
	 * Assert Title Contains
	 * 
	 * @param expected_title
	 * @param stepDescription
	 */
	public static void assertTitleContains(String expected_title, String stepDescription) {
		String actual_title = null;
		try {
			actual_title = DriverManager.getDriver().getTitle();
			Log.info("Actual Title :: " + actual_title);
			Log.info("Expected Title : " + expected_title);
			Log.info(stepDescription.toString());
			Assert.assertTrue(actual_title.contains(expected_title));
			Log.info("Actual Title :: " + actual_title + " is matching with the " + expected_title);
			ExtentReportManager.pass(stepDescription);
			ExtentReportManager.pass("Actual Title :: " + actual_title + " is matching with the " + expected_title);
		} catch (Throwable e) {
			Log.error("Actual Title :: " + actual_title + " is not matching with the " + expected_title);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Actual Title :: " + actual_title + " is not matching with the " + expected_title);
			throw e;
		}
	}

	/**
	 * Assert URL Equals
	 * 
	 * @param expected_url
	 * @param stepDescription
	 */
	public static void assertURLEquals(String expected_url, String stepDescription) {
		String actual_url = null;
		try {
			actual_url = DriverManager.getDriver().getCurrentUrl();
			Log.info("Actual URL :: " + actual_url);
			Log.info("Expected URL : " + expected_url);
			Log.info(stepDescription.toString());
			Assert.assertEquals(actual_url, expected_url);
			Log.info("Actual URL :: " + actual_url + " is matching with the " + expected_url);
			ExtentReportManager.pass(stepDescription);
			ExtentReportManager.pass("Actual URL :: " + actual_url + " is matching with the " + expected_url);
		} catch (Throwable e) {
			Log.error("Actual URL :: " + actual_url + " is not matching with the " + expected_url);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Actual URL :: " + actual_url + " is not matching with the " + expected_url);
			throw e;
		}
	}

	/**
	 * Assert URL Contains
	 * 
	 * @param expected_url
	 * @param stepDescription
	 */
	public static void assertURLContains(String expected_url, String stepDescription) {
		String actual_url = null;
		try {
			actual_url = DriverManager.getDriver().getCurrentUrl();
			Log.info("Actual URL :: " + actual_url);
			Log.info("Expected URL : " + expected_url);
			Log.info(stepDescription.toString());
			Assert.assertTrue(actual_url.contains(expected_url));
			Log.info("Actual URL :: " + actual_url + " is matching with the " + expected_url);
			ExtentReportManager.pass(stepDescription);
			ExtentReportManager.pass("Actual URL :: " + actual_url + " is matching with the " + expected_url);
		} catch (Throwable e) {
			Log.error("Actual URL :: " + actual_url + " is not matching with the " + expected_url);
			ExtentReportManager.fail("Failed!! : " + stepDescription);
			ExtentReportManager.fail("Actual URL :: " + actual_url + " is not matching with the " + expected_url);
			throw e;
		}
	}

}
