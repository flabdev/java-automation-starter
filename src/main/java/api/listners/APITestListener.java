package api.listners;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import config.RunConfig;
import reporting.ExtentReportManager;
import org.testng.*;
import java.util.Arrays;
import utilities.logger.Log;


public class APITestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

	static int count_totalTCs;
	static int count_passedTCs;
	static int count_skippedTCs;
	static int count_failedTCs;
	

	public APITestListener() {
		RunConfig.LoadAPIConfiguration();
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
	}

	@Override
	public void onFinish(ISuite iSuite) {
		Log.info("End suite testing " + iSuite.getName());
		ExtentReportManager.flushReports();

	}


	@Override
	public void onTestStart(ITestResult iTestResult) {
		Log.info("Test case: " + getTestName(iTestResult) + " test is starting...");
		count_totalTCs = count_totalTCs + 1;
		ExtentReportManager.createTest(iTestResult.getName());
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		Log.info("Test case: " + getTestName(iTestResult) + " is passed.");
		count_passedTCs = count_passedTCs + 1;
		// ExtentReports log operation for passed tests.
		String logText = "<b>Test Method " + iTestResult.getMethod().getMethodName() + " Successful.</b>";
		Markup markup = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		ExtentReportManager.logMessage(Status.PASS, markup);

	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		Log.error("Test case: " + getTestName(iTestResult) + " is failed.");
		count_failedTCs = count_failedTCs + 1;
		String methodName = iTestResult.getMethod().getMethodName();
		String exception_msg = iTestResult.getThrowable().getMessage()
				+ Arrays.toString(iTestResult.getThrowable().getStackTrace());
		StringBuilder failMsgFormat = new StringBuilder();
		failMsgFormat.append("<details><summary><b><font color=red>" + "Error Occured, Click to see logs : "
				+ "</font></b></summary>");
		failMsgFormat.append(exception_msg.replaceAll(",", "<br>")).append("</details> \n");
		ExtentReportManager.fail(failMsgFormat.toString());
		String logText = "<b>Test Method " + methodName + " Failed.</b>";
		Markup markup = MarkupHelper.createLabel(logText, ExtentColor.RED);
		ExtentReportManager.logMessage(Status.FAIL, markup);
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		Log.warn("Test case: " + getTestName(iTestResult) + " is skipped.");
		count_skippedTCs = count_skippedTCs + 1;
		ExtentReportManager.logMessage(Status.SKIP, "Test case: " + getTestName(iTestResult) + " is skipped.");


	}

	
}
