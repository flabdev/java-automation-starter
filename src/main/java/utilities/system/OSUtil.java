package utilities.system;


import java.io.File;

public class OSUtil {
	public enum OS {
		WINDOWS, LINUX, MAC, SOLARIS
	};// Operating systems.

	private static OS os = null;

	public static OS getOS() {
		if (os == null) {
			String operSys = System.getProperty("os.name").toLowerCase();
			if (operSys.contains("win")) {
				os = OS.WINDOWS;
			} else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
				os = OS.LINUX;
			} else if (operSys.contains("mac")) {
				os = OS.MAC;
			} else if (operSys.contains("sunos")) {
				os = OS.SOLARIS;
			}
		}
		return os;
	}

	public static String getOSInfo() {
		return System.getProperty("os.name");
	}

	public static boolean isWindows() {
		return (getOSInfo().contains("win"));
	}

	public static boolean isMac() {
		return (getOSInfo().contains("mac"));
	}

	public static boolean isUnix() {
		return (getOSInfo().contains("nix") || getOSInfo().contains("nux") || getOSInfo().contains("aix"));
	}

	public static boolean isLinux() {
		return (getOSInfo().contains("nix") || getOSInfo().contains("nux") || getOSInfo().contains("aix"));
	}
	
	public static String getCurrentDir() {
        String current = System.getProperty("user.dir") + File.separator;
        return current;
    }

	

}