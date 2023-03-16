package dataproviders.properties;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class PropertyFileReader {

	private Properties properties;
	private FileInputStream fis;
	public String filePath;

	public PropertyFileReader(String filePath) {
		this.filePath = filePath;
	}

	public String getValue(String key) {
		String value = null;
		try {
			properties = new Properties();
			fis = new FileInputStream(filePath);
			properties.load(fis);
			fis.close();
			value = properties.getProperty(key);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return value;
	}

	public void setValue(String key, String keyValue) {
		try {
			FileOutputStream fos;
			if (fis == null) {
				properties = new Properties();
				fis = new FileInputStream(filePath);
				properties.load(fis);
				fis.close();
			}
			fos = new FileOutputStream(filePath);
			properties.setProperty(key, keyValue);
			properties.store(fos, null);
			fos.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
