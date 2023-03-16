package constants;

import dataproviders.properties.PropertyFileReader;
import utilities.system.SystemHelpers;

public class FrameworkConstants {

	//Configuration Path
	public static final String PATH = System.getProperty("user.dir") + "/config/config.properties";
	public static final PropertyFileReader PropertyFileUtil = new PropertyFileReader(PATH);
	// Run Constants
	public static final String RUN_MODE = PropertyFileUtil.getValue("RUN_MODE");
	public static final String TARGET = PropertyFileUtil.getValue("TARGET");
	public static final String ENVIRONMENT = PropertyFileUtil.getValue("ENVIRONMENT");
	
	//	Environment Path
	public static final String ENV_PATH = System.getProperty("user.dir") + "/config/" + ENVIRONMENT.toLowerCase() + ".properties";

	// Browser Constants
	public static final String BROWSER = PropertyFileUtil.getValue("BROWSER");
	public static final String HEADLESS = PropertyFileUtil.getValue("HEADLESS");

	public static final String ICON_OS_WINDOWS = "<i class='fa fa-windows' ></i>";
	public static final String ICON_OS_MAC = "<i class='fa fa-apple' ></i>";
	public static final String ICON_OS_LINUX = "<i class='fa fa-linux' ></i>";
	public static final String ICON_BROWSER_EDGE = "<i class=\"fa fa-edge\" aria-hidden=\"true\"></i>";
	public static final String ICON_BROWSER_CHROME = "<i class=\"fa fa-chrome\" aria-hidden=\"true\"></i>";
	public static final String ICON_BROWSER_FIREFOX = "<i class=\"fa fa-firefox\" aria-hidden=\"true\"></i>";
	public static final String ICON_BROWSER_SAFARI = "<i class=\"fa fa-safari\" aria-hidden=\"true\"></i>";
	public static final String ICON_Navigate_Right = "<i class='fa fa-arrow-circle-right' ></i>";
	public static final String ICON_LAPTOP = "<i class='fa fa-laptop' style='font-size:18px'></i>";
	public static final String ICON_BUG = "<i class='fa fa-bug' ></i>";
	public static final String ICON_CAMERA = "<i class=\"fa fa-camera\" aria-hidden=\"true\"></i>";
	public static final String ICON_BROWSER_PREFIX = "<i class=\"fa fa-";
	public static final String ICON_BROWSER_SUFFIX = "\" aria-hidden=\"true\"></i>";

	// Report Constants
	public static final String PROJECT_PATH = SystemHelpers.getCurrentDir();
	public static final String EXCEL_DATA_FILE_PATH = PropertyFileUtil.getValue("EXCEL_DATA_FILE_PATH");
	public static final String PROJECT_NAME = PropertyFileUtil.getValue("PROJECT_NAME");
	public static final String REPORT_TITLE = PropertyFileUtil.getValue("REPORT_TITLE");
	public static final String EXTENT_REPORT_NAME = PropertyFileUtil.getValue("EXTENT_REPORT_NAME");
	public static final String EXTENT_REPORT_FOLDER = PropertyFileUtil.getValue("EXTENT_REPORT_FOLDER");
	public static final String EXPORT_VIDEO_PATH = PropertyFileUtil.getValue("EXPORT_VIDEO_PATH");
	public static final String EXPORT_CAPTURE_PATH = PropertyFileUtil.getValue("EXPORT_CAPTURE_PATH");
	public static final String OVERRIDE_REPORTS = PropertyFileUtil.getValue("OVERRIDE_REPORTS");
	public static final String OPEN_REPORTS_AFTER_EXECUTION = PropertyFileUtil.getValue("OPEN_REPORTS_AFTER_EXECUTION");
	public static final String SEND_EMAIL_TO_USERS = PropertyFileUtil.getValue("SEND_EMAIL_TO_USERS");
	public static final String SCREENSHOT_PASSED_STEPS = PropertyFileUtil.getValue("SCREENSHOT_PASSED_STEPS");
	public static final String SCREENSHOT_FAILED_STEPS = PropertyFileUtil.getValue("SCREENSHOT_FAILED_STEPS");
	public static final String SCREENSHOT_SKIPPED_STEPS = PropertyFileUtil.getValue("SCREENSHOT_SKIPPED_STEPS");
	public static final String SCREENSHOT_ALL_STEPS = PropertyFileUtil.getValue("SCREENSHOT_ALL_STEPS");
	public static final String ZIP_FOLDER = PropertyFileUtil.getValue("ZIP_FOLDER");
	public static final String ZIP_FOLDER_PATH = PropertyFileUtil.getValue("ZIP_FOLDER_PATH");
	public static final String ZIP_FOLDER_NAME = PropertyFileUtil.getValue("ZIP_FOLDER_NAME");
	public static final String VIDEO_RECORD = PropertyFileUtil.getValue("VIDEO_RECORD");
	public static final boolean DEMO_MODE = Boolean.parseBoolean(PropertyFileUtil.getValue("DEMO_MODE"));
	public static final String EXTENT_REPORT_FOLDER_PATH = PROJECT_PATH + EXTENT_REPORT_FOLDER;
	public static final String EXTENT_REPORT_FILE_NAME = EXTENT_REPORT_NAME + ".html";
	public static String EXTENT_REPORT_FILE_PATH = EXTENT_REPORT_FOLDER_PATH + "/" + EXTENT_REPORT_FILE_NAME;

	// Zip file for Report folder
	public static final String ZIPPED_EXTENT_REPORTS_FOLDER = EXTENT_REPORT_FOLDER + ".zip";

	public static final String YES = "yes";
	public static final String NO = "no";

	// Wait Constants
	public static final int WAIT_DEFAULT = Integer.parseInt(PropertyFileUtil.getValue("WAIT_DEFAULT"));
	public static final int WAIT_IMPLICIT = Integer.parseInt(PropertyFileUtil.getValue("WAIT_IMPLICIT"));
	public static final int WAIT_EXPLICIT = Integer.parseInt(PropertyFileUtil.getValue("WAIT_EXPLICIT"));
	public static final int WAIT_PAGE_LOADED = Integer.parseInt(PropertyFileUtil.getValue("WAIT_PAGE_LOADED"));
	public static final int WAIT_SLEEP_STEP = Integer.parseInt(PropertyFileUtil.getValue("WAIT_SLEEP_STEP"));
	public static final String ACTIVE_PAGE_LOADED = PropertyFileUtil.getValue("ACTIVE_PAGE_LOADED");

	// Email Constants
	public static final String SERVER = PropertyFileUtil.getValue("SERVER");
	public static final String PORT = PropertyFileUtil.getValue("PORT");
	public static final String FROM = PropertyFileUtil.getValue("FROM");
	public static final String PASSWORD = PropertyFileUtil.getValue("PASSWORD");
	public static final String[] TO = PropertyFileUtil.getValue("TO").split(",");
	public static final String SUBJECT = "Automation Report";

	// Cloud Constants
	public static final String REMOTE_URL = PropertyFileUtil.getValue("REMOTE_URL");
	public static final String REMOTE_PORT = PropertyFileUtil.getValue("REMOTE_PORT");
	public static final String SAUCE_LABS_USERNAME = PropertyFileUtil.getValue("SAUCE_LABS_USERNAME");
	public static final String SAUCE_LABS_PASSWORD = PropertyFileUtil.getValue("SAUCE_LABS_PASSWORD");
	public static final String BROWSERSTACK_USERNAME = PropertyFileUtil.getValue("BROWSERSTACK_USERNAME");
	public static final String BROWSERSTACK_PASSWORD = PropertyFileUtil.getValue("BROWSERSTACK_PASSWORD");
	
	
	//API Constants.
	
}
