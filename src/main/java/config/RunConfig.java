package config;

import static constants.FrameworkConstants.ENVIRONMENT;
import static constants.FrameworkConstants.ENV_PATH;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import dataproviders.properties.PropertyFileReader;
import enums.EnvType;
import utilities.logger.Log;

public class RunConfig {

	private static String target;
	private static String runMode;
	private static String browser;
	private static String headless;
	private static String environment;
	static String PATH;

	public static void LoadWebConfiguration() {
		try {
			PATH = System.getProperty("user.dir") + "/config/config.properties";
			PropertyFileReader PropertyFileUtil = new PropertyFileReader(PATH);
			Log.info("Loading Configuration from the File : " + PATH);

			// Target
			if (!(System.getProperty("target") == null)) {
				PropertyFileUtil.setValue("TARGET", target);
			}
			target = PropertyFileUtil.getValue("TARGET");
			if (Objects.equals(target, "")) {
				throw new RuntimeException("TARGET is not configured in the file :" + PATH);
			}
			Log.info("Target Type : " + target);

			// Run Mode
			if (!(System.getProperty("runMode") == null)) {
				PropertyFileUtil.setValue("RUN_MODE", runMode);
			}
			runMode = PropertyFileUtil.getValue("RUN_MODE");
			if (Objects.equals(runMode, "")) {
				throw new RuntimeException("RUN_MODE is not configured in the file :" + PATH);
			}
			Log.info("Run Mode : " + runMode);

			// Browser
			if (!(System.getProperty("browser") == null)) {
				PropertyFileUtil.setValue("BROWSER", browser);
			}
			browser = PropertyFileUtil.getValue("BROWSER");
			if (Objects.equals(browser, "")) {
				throw new RuntimeException("BROWSER is not configured in the file :" + PATH);
			}
			Log.info("Browser Type : " + browser);

			// Headless
			if (!(System.getProperty("headless") == null)) {
				PropertyFileUtil.setValue("HEADLESS", headless);
			}
			headless = PropertyFileUtil.getValue("HEADLESS");
			if (Objects.equals(headless, "")) {
				throw new RuntimeException("HEADLESS is not configured in the file :" + PATH);
			}
			Log.info("Headless Mode : " + headless);

			// Environment
			if (!(System.getProperty("environment") == null)) {
				PropertyFileUtil.setValue("ENVIRONMENT", environment);
			}
			environment = PropertyFileUtil.getValue("ENVIRONMENT");
			if (Objects.equals(environment, "")) {
				throw new RuntimeException("ENVIRONMENT is not configured in the file :" + PATH);
			}
			Log.info("Environment : " + environment);
			
			Log.info("Chosen Environment is :" + ENVIRONMENT);
			Log.info("Loading Environment Configuration from the File : " + ENV_PATH);
			Set<String> enivironmentList = Arrays.stream(EnvType.values()).map(Enum::name).collect(Collectors.toSet());
			if (!enivironmentList.contains(ENVIRONMENT.toUpperCase())) {
				Log.error("Invalid Environment Provided. Supported Environments are  :" + enivironmentList);
				throw new RuntimeException(
						"Invalid Environment Provided. Supported Environments are  :" + enivironmentList);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void LoadAPIConfiguration() {
		try {
			PATH = System.getProperty("user.dir") + "/config/config.properties";
			PropertyFileReader PropertyFileUtil = new PropertyFileReader(PATH);
			Log.info("Loading Configuration from the File : " + PATH);

			// Target
			if (!(System.getProperty("target") == null)) {
				PropertyFileUtil.setValue("TARGET", target);
			}
			target = PropertyFileUtil.getValue("TARGET");
			if (Objects.equals(target, "")) {
				throw new RuntimeException("TARGET is not configured in the file :" + PATH);
			}
			Log.info("Target Type : " + target);

			// Environment
			if (!(System.getProperty("environment") == null)) {
				PropertyFileUtil.setValue("ENVIRONMENT", environment);
			}
			environment = PropertyFileUtil.getValue("ENVIRONMENT");
			if (Objects.equals(environment, "")) {
				throw new RuntimeException("ENVIRONMENT is not configured in the file :" + PATH);
			}
			Log.info("Environment : " + environment);
			
			Log.info("Chosen Environment is :" + ENVIRONMENT);
			Log.info("Loading Environment Configuration from the File : " + ENV_PATH);
			Set<String> enivironmentList = Arrays.stream(EnvType.values()).map(Enum::name).collect(Collectors.toSet());
			if (!enivironmentList.contains(ENVIRONMENT.toUpperCase())) {
				Log.error("Invalid Environment Provided. Supported Environments are  :" + enivironmentList);
				throw new RuntimeException(
						"Invalid Environment Provided. Supported Environments are  :" + enivironmentList);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
