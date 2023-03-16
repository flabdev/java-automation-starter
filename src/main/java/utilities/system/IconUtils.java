package utilities.system;


import enums.BrowserType;
import static constants.FrameworkConstants.*;

public final class IconUtils {

    private IconUtils() {
    }

    public static String getBrowserIcon() {
        if (BrowserInfoUtils.getBrowserInfo().contains(BrowserType.CHROME.toString())) {
            return ICON_BROWSER_CHROME;
        } else if (BrowserInfoUtils.getBrowserInfo().contains(BrowserType.EDGE.toString())) {
            return ICON_BROWSER_EDGE;
        } else if (BrowserInfoUtils.getBrowserInfo().contains(BrowserType.FIREFOX.toString())) {
            return ICON_BROWSER_FIREFOX;
        } else {
            return BrowserInfoUtils.getBrowserInfo();
        }
    }

    public static String getOSIcon() {

        String operationSystem = BrowserInfoUtils.getOSInfo().toLowerCase();
        if (operationSystem.contains("win")) {
            return ICON_OS_WINDOWS;
        } else if (operationSystem.contains("nix") || operationSystem.contains("nux") || operationSystem.contains("aix")) {
            return ICON_OS_LINUX;
        } else if (operationSystem.contains("mac")) {
            return ICON_OS_MAC;
        }
        return operationSystem;
    }
}
