package seedu.address.websocket.util;

import java.net.MalformedURLException;
import java.net.URL;

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
}
