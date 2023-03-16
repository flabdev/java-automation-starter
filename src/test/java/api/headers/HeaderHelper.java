package api.headers;

import org.apache.commons.collections4.map.HashedMap;

public class HeaderHelper {

	public static HashedMap<String, String> getDefaultHeader() {
		HashedMap<String, String> defaultHeader = new HashedMap<>();
		defaultHeader.put("Content-Type", "application/json");
		defaultHeader.put("Authorization","");
		return defaultHeader;
	}

}
