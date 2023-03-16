package utilities.system;


import java.io.*;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

public final class SystemHelpers {

	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

	public static String makeSlug(String input) {
		if (input == null)
			throw new IllegalArgumentException();

		String noWhiteSpace = WHITESPACE.matcher(input).replaceAll("_");
		String normalized = Normalizer.normalize(noWhiteSpace, Normalizer.Form.NFD);
		String slug = NONLATIN.matcher(normalized).replaceAll("");
		return slug.toLowerCase(Locale.ENGLISH);
	}

	/**
	 * Read File
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String file) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(file);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = ((BufferedReader) reader).read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}

	/**
	 * Get Current Directory
	 * @return
	 */
	public static String getCurrentDir() {
		String current = System.getProperty("user.dir") + File.separator;
		return current;
	}

	/**
	 * Create a Folder
	 * @param path
	 */
	public static void createFolder(String path) {
		// File is a class inside java.io package
		File file = new File(path);
		int lengthSum = path.length();
		int lengthSub = path.substring(0, path.lastIndexOf('/')).length();
		String result = path.substring(lengthSub, lengthSum);
		System.out.println(result);
		if (!file.exists()) {
			file.mkdir(); // mkdir is used to create folder
			System.out.println("Folder " + file.getName() + " created: " + path);
		} else {
			System.out.println("Folder already created");
		}
	}

	/**
	 * Split String
	 * @param str
	 * @param valueSplit
	 * @return
	 */
	public static ArrayList<String> splitString(String str, String valueSplit) {
		ArrayList<String> arrayListString = new ArrayList<>();
		for (String s : str.split(valueSplit, 0)) {
			arrayListString.add(s);
		}
		return arrayListString;
	}

}