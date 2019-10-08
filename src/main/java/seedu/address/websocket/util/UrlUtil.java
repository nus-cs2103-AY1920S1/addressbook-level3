package seedu.address.websocket.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import seedu.address.commons.util.StringUtil;

/**
 * Url Constructor Util
 */
public class UrlUtil {

    /**
     * Generates a URL object from a String.
     *
     * @param url String to be converted
     * @return URL object or null if URL is malformed
     */
    public static URL generateUrl(String url) {
        try {
            URL query = new URL(url);
            return query;
        } catch (MalformedURLException mue) {
            return null;
        }
    }

    /**
     * Split query string of URL into a map of key-value pairs. It is assumed that keys are unique.
     * @param url URL to split.
     * @return a map of {@code String} key and {@code String} value.
     */
    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        if (StringUtil.isNullOrEmpty(url.getQuery())) {
            return Collections.emptyMap();
        }

        Map<String, String> queryMap = new LinkedHashMap<>();
        String query = url.getQuery();
        String[] querySplits = query.split("&");

        for (String part : querySplits) {
            int equalsIndex = part.indexOf("=");
            String key = equalsIndex > 0 ? URLDecoder.decode(part.substring(0, equalsIndex), "UTF-8") : part;
            String value = equalsIndex > 0 && part.length() > equalsIndex + 1
                    ? URLDecoder.decode(part.substring(equalsIndex + 1), "UTF-8") : null;
            queryMap.put(key, value);
        }
        return queryMap;
    }
}
