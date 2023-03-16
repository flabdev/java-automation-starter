package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import enums.CategoryType;
import utilities.date.DateUtils;
import utilities.logger.Log;
import utilities.system.IconUtils;
import web.drivers.DriverManager;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import static constants.FrameworkConstants.*;
import java.util.Objects;

public final class ExtentReportManager {

	private static ExtentReports extentReports;
	private static String link = "";

	public static void initReports() {
		if (Objects.isNull(extentReports)) {
			extentReports = new ExtentReports();

			if (OVERRIDE_REPORTS.trim().equals(NO)) {
				System.out.println("OVERRIDE_REPORTS = " + OVERRIDE_REPORTS);
				link = EXTENT_REPORT_FOLDER_PATH + "/" + DateUtils.getCurrentDateTimeCustom("_") + "_"
						+ EXTENT_REPORT_FILE_NAME;
				Log.info("Report Location:" + link);
			} else {
				link = EXTENT_REPORT_FILE_PATH;
				Log.info("Report Location:" + link);
			}
			ExtentSparkReporter spark = new ExtentSparkReporter(link);
			extentReports.attachReporter(spark);
			spark.config().setTheme(Theme.STANDARD);
			spark.config().setDocumentTitle(REPORT_TITLE);
			spark.config().setReportName(REPORT_TITLE);
			extentReports.setSystemInfo("Project Name", REPORT_TITLE);

			Log.info("Extent Reports is installed.");
		}
	}

	public static void flushReports() {
		if (Objects.nonNull(extentReports)) {
			extentReports.flush();
		}
		ExtentTestManager.unload();
		ReportUtils.openReports(link);
	}

	public static void createTest(String testCaseName) {
		// ExtentManager.setExtentTest(extent.createTest(testCaseName));
		ExtentTestManager.setExtentTest(extentReports.createTest(IconUtils.getBrowserIcon() + " : " + testCaseName));
	}

	public static void createTest(String testCaseName, String description) {
		// ExtentManager.setExtentTest(extent.createTest(testCaseName));
		ExtentTestManager.setExtentTest(extentReports.createTest(testCaseName, description));
	}

	public static void removeTest(String testCaseName) {
		// ExtentManager.setExtentTest(extent.createTest(testCaseName));
		extentReports.removeTest(testCaseName);
	}

	/**
	 * Adds the screenshot.
	 *
	 * @param message the message
	 */
	public static void addScreenShot(String message) {
		String base64Image = "data:image/png;base64,"
				+ ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
		ExtentTestManager.getExtentTest().log(Status.INFO, message,
				MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
	}

	/**
	 * Adds the screenshot.
	 *
	 * @param status  the status
	 * @param message the message
	 */
	public static void addScreenShot(Status status, String message) {
		String base64Image = "data:image/png;base64,"
				+ ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
		ExtentTestManager.getExtentTest().log(status, message,
				MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
	}

	/**
	 * Adds the screenshot.
	 * 
	 * @param status
	 */
	public static void addScreenShot(Status status) {
		String base64Image = "data:image/png;base64,"
				+ ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
		ExtentTestManager.getExtentTest().log(status,
				MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
	}

	// public static void addCategories(String[] categories) {
	synchronized public static void addCategories(CategoryType[] categories) {
		if (categories == null) {
			ExtentTestManager.getExtentTest().assignCategory("REGRESSION");
		} else {
			// for (String category : categories) {
			for (CategoryType category : categories) {
				ExtentTestManager.getExtentTest().assignCategory(category.toString());
			}
		}
	}

	public static void logMessage(String message) {
		ExtentTestManager.getExtentTest().log(Status.INFO, message);
	}

	public static void logMessage(Status status, String message) {
		ExtentTestManager.getExtentTest().log(status, message);
	}

	public static void logMessage(Status status, Markup message) {
		ExtentTestManager.getExtentTest().log(status, message);
	}

	public static void logMessage(Status status, Object message) {
		ExtentTestManager.getExtentTest().log(status, (Throwable) message);
	}

	public static void pass(String message) {
		// System.out.println("ExtentReportManager class: " +
		// ExtentTestManager.getExtentTest());
		ExtentTestManager.getExtentTest().pass(message);
	}

	public static void pass(Markup message) {
		ExtentTestManager.getExtentTest().pass(message);
	}

	public static void fail(String message) {
		ExtentTestManager.getExtentTest().fail("<b><font color=red>" + message + "</font></b>");
	}

	public static void fail(Object message) {
		ExtentTestManager.getExtentTest().fail((String) message);
	}

	public static void fail(Markup message) {
		ExtentTestManager.getExtentTest().fail(message);
	}

	public static void skip(String message) {
		ExtentTestManager.getExtentTest().skip(message);
	}

	public static void skip(Markup message) {
		ExtentTestManager.getExtentTest().skip(message);
	}

	public static void info(Markup message) {
		ExtentTestManager.getExtentTest().info(message);
	}

	public static void info(String message) {
		ExtentTestManager.getExtentTest().info(message);
	}

	public static void warning(String message) {
		ExtentTestManager.getExtentTest().log(Status.WARNING, message);
	}

	public static synchronized void log(Logger logger, Status status, Object logObject) {
		ExtentTest test = ExtentTestManager.getExtentTest();

		if (logObject instanceof String) {
			test.log(status, (String) logObject);
			logger.info((String) logObject);
		} else if (logObject instanceof Throwable) {
			test.log(status, MarkupHelper.createCodeBlock(((Throwable) logObject).getMessage()));
			logger.info(((Throwable) logObject).getStackTrace().toString());
		} else if (logObject instanceof Media) {
			test.log(status, (Media) logObject);
		}

	}

	public static synchronized void step(Logger logger, String log) {
		ExtentTest test = ExtentTestManager.getExtentTest();
		test.log(Status.INFO, MarkupHelper.createLabel("Performing -> " + log, ExtentColor.INDIGO));
	}

	public static synchronized void label(String message) {
		Log.info(message);
		ExtentTest test = ExtentTestManager.getExtentTest();
		test.log(Status.INFO, MarkupHelper.createLabel(message, ExtentColor.INDIGO));
	}

}
