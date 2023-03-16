package listners;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config.RunConfig;
import enums.CategoryType;
import reporting.ExtentReportManager;
import org.testng.*;
import static constants.FrameworkConstants.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import utilities.logger.Log;
import utilities.recording.CaptureHelpers;
import utilities.recording.ScreenRecoderHelpers;
import web.actions.WebUI;
import web.annotations.FrameworkAnnotation;
import web.drivers.DriverManager;

public class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

	static int count_totalTCs;
	static int count_passedTCs;
	static int count_skippedTCs;
	static int count_failedTCs;
	private ScreenRecoderHelpers screenRecorder;
	

	public TestListener() {
		try {
			RunConfig.LoadWebConfiguration();
			screenRecorder = new ScreenRecoderHelpers();
		} catch (IOException | AWTException e) {
			System.out.println(e.getMessage());
		}
	}

	public String getTestName(ITestResult result) {
		return result.getTestName() != null ? result.getTestName()
				: result.getMethod().getConstructorOrMethod().getName();
	}

	public String getTestDescription(ITestResult result) {
		return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
	}

	@Override
	public void onStart(ISuite iSuite) {
		ExtentReportManager.initReports();
		Log.info("Start suite: " + iSuite.getName());
		iSuite.setAttribute("WebDriver", DriverManager.getDriver());
		// if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
		// CptureHelpers.startRecord(iSuite.getName());
		// }
	}

	@Override
	public void onFinish(ISuite iSuite) {
		Log.info("End suite testing " + iSuite.getName());
		WebUI.stopSoftAssertAll();
		// mail
		ExtentReportManager.flushReports();
		// ZipUtils.zip();
		// EmailSendUtils.sendEmail(count_totalTCs, count_passedTCs, count_failedTCs,
		// count_skippedTCs);

		// if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
		// CaptureHelpers.stopRecord();
		// }
	}

	public CategoryType[] getCategoryType(ITestResult iTestResult) {
		if (iTestResult.getMethod().getConstructorOrMethod().getMethod()
				.getAnnotation(FrameworkAnnotation.class) == null) {
			return null;
		}
		CategoryType categoryType[] = iTestResult.getMethod().getConstructorOrMethod().getMethod()
				.getAnnotation(FrameworkAnnotation.class).category();
		return categoryType;
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		Log.info("Test case: " + getTestName(iTestResult) + " test is starting...");
		count_totalTCs = count_totalTCs + 1;
		ExtentReportManager.createTest(iTestResult.getName());
		ExtentReportManager.addCategories(getCategoryType(iTestResult));
		// ExtentReportManager.addDevices();
		// ExtentReportManager.info(BrowserInfoUtils.getOSInfo());

		if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
			screenRecorder.startRecording(getTestName(iTestResult));
		}

	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		Log.info("Test case: " + getTestName(iTestResult) + " is passed.");
		count_passedTCs = count_passedTCs + 1;

		if (SCREENSHOT_PASSED_STEPS.equals(YES)) {
			CaptureHelpers.captureScreenshot(DriverManager.getDriver(), getTestName(iTestResult));
		}

		// ExtentReports log operation for passed tests.
		String logText = "<b>Test Method " + iTestResult.getMethod().getMethodName() + " Successful.</b>";
		Markup markup = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		ExtentReportManager.logMessage(Status.PASS, markup);

		if (VIDEO_RECORD.trim().toLowerCase().equals(YES)) {
			screenRecorder.stopRecording(true);
		}
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		Log.error("Test case: " + getTestName(iTestResult) + " is failed.");
		count_failedTCs = count_failedTCs + 1;

		if (SCREENSHOT_FAILED_STEPS.equals(YES)) {
			CaptureHelpers.captureScreenshot(DriverManager.getDriver(), getTestName(iTestResult));
		}
		String methodName = iTestResult.getMethod().getMethodName();
		String exception_msg = iTestResult.getThrowable().getMessage()
				+ Arrays.toString(iTestResult.getThrowable().getStackTrace());
		StringBuilder failMsgFormat = new StringBuilder();
		ExtentReportManager.addScreenShot(Status.FAIL, "<b><font color=red>" + "Failure Screenshot" + "</font></b>");
		failMsgFormat.append("<details><summary><b><font color=red>" + "Error Occured, Click to see logs : "
				+ "</font></b></summary>");
		failMsgFormat.append(exception_msg.replaceAll(",", "<br>")).append("</details> \n");
		ExtentReportManager.fail(failMsgFormat.toString());
		String logText = "<b>Test Method " + methodName + " Failed.</b>";
		Markup markup = MarkupHelper.createLabel(logText, ExtentColor.RED);
		ExtentReportManager.logMessage(Status.FAIL, markup);

		if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
			screenRecorder.stopRecording(true);
		}
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		Log.warn("Test case: " + getTestName(iTestResult) + " is skipped.");
		count_skippedTCs = count_skippedTCs + 1;

		if (SCREENSHOT_SKIPPED_STEPS.equals(YES)) {
			CaptureHelpers.captureScreenshot(DriverManager.getDriver(), getTestName(iTestResult));
		}

		ExtentReportManager.logMessage(Status.SKIP, "Test case: " + getTestName(iTestResult) + " is skipped.");

		if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
			screenRecorder.stopRecording(true);
		}

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		Log.error("Test failed but it is in defined success ratio " + getTestName(iTestResult));
		ExtentReportManager.logMessage("Test failed but it is in defined success ratio " + getTestName(iTestResult));
	}
}
