package seedu.address.websocket.util;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtil {
    public static URL generateUrl(String url){
        try{
            URL query = new URL(url);
            return query;
        } catch (MalformedURLException mue){
            return null;
        }
    }
}
