package config;


import static constants.FrameworkConstants.*;
import dataproviders.properties.PropertyFileReader;

public class EnvionmentFactory {

	public static PropertyFileReader PropertyFileUtil = new PropertyFileReader(ENV_PATH);;


	public static String WebURL() {
		return PropertyFileUtil.getValue("WEB_URL");
	}

	public static String APIURL() {
		return PropertyFileUtil.getValue("API_URL");
	}

	public static String DBURL() {
		return PropertyFileUtil.getValue("DB_URL");
	}

}
