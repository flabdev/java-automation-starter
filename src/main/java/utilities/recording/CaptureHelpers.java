package utilities.recording;

import org.apache.commons.io.FileUtils;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import utilities.logger.Log;
import utilities.system.SystemHelpers;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.monte.media.AudioFormatKeys.EncodingKey;
import static org.monte.media.AudioFormatKeys.FrameRateKey;
import static org.monte.media.AudioFormatKeys.KeyFrameIntervalKey;
import static org.monte.media.AudioFormatKeys.MediaTypeKey;
import static org.monte.media.AudioFormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.MIME_AVI;
import static org.monte.media.VideoFormatKeys.*;
import static constants.FrameworkConstants.*;

public class CaptureHelpers extends ScreenRecorder {

	// ------Record with Monte Media library---------
	private static ScreenRecorder screenRecorder;
	private final String name;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

	public CaptureHelpers(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat,
			Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
		super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
		this.name = name;
	}

	/**
	 * Method to Create a Movie File
	 */
	@Override
	protected File createMovieFile(Format fileFormat) throws IOException {

		if (!movieFolder.exists()) {
			movieFolder.mkdirs();
		} else if (!movieFolder.isDirectory()) {
			throw new IOException(movieFolder + " is not a directory.");
		}
		return new File(movieFolder,
				name + "_" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
	}

	/**
	 * Start Video Record
	 * 
	 * @param methodName
	 */
	public static void startRecord(String methodName) {
		File file = new File("./" + EXPORT_VIDEO_PATH + "/" + methodName + "/");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;

		System.out.println("width" + width);
		System.out.println("height" + height);

		Rectangle captureSize = new Rectangle(0, 0, width, height);
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		try {
			screenRecorder = new CaptureHelpers(gc, null,
					new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
							CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
							Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
					null, file, methodName);

			screenRecorder.start();
		} catch (IOException | AWTException e) {
			e.printStackTrace();
		}
	}

	// Stop record video
	/**
	 * Stop Video Record
	 */
	public static void stopRecord() {
		try {
			screenRecorder.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Capture Screenshot
	 * 
	 * @param driver
	 * @param screenName
	 */
	public static void captureScreenshot(WebDriver driver, String screenName) {
		try {
			String path = SystemHelpers.getCurrentDir() + EXPORT_CAPTURE_PATH;
			File file = new File(path);
			if (!file.exists()) {
				Log.info("No Folder: " + path);
				file.mkdir();
				Log.info("Folder created: " + file);
			}

			Log.info("Driver for Screenshot: " + driver);
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source,
					new File(path + "/" + screenName + "_" + dateFormat.format(new Date()) + ".png"));
			Log.info("Screenshot taken: " + screenName);
			Log.info("Screenshot taken current URL: " + driver.getCurrentUrl());
		} catch (Exception e) {
			System.out.println("Exception while taking screenshot: " + e.getMessage());
		}
	}

}
