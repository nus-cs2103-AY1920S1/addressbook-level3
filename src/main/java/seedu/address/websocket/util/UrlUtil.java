package seedu.address.websocket.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.gmaps.Location;

/**
 * Url Constructor Util
 */
public class UrlUtil {

    private static String gmapsApiKey = "";

    private static final Logger logger = LogsCenter.getLogger(UrlUtil.class);

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
     * THis method is used to remove the API key from the url
     * @param url that contains API key
     * @return url without API key
     */
    public static String sanitizeApiKey(String url) {
        if (url.contains("key=")) {
            return url.split("key=")[0];
        } else {
            return url;
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

    /**
     * This is a utility function to get the full API endpoint for Gmaps Distance Matrix Api from 2 arraylist of string
     * @param locationsRow
     * @param locationsColumn
     * @return
     */
    public static String generateGmapsDistanceMatrixUrl(
            ArrayList<Location> locationsRow, ArrayList<Location> locationsColumn) throws InvalidParameterException {
        if (locationsColumn.size() > 10 || locationsRow.size() > 10) {
            throw new InvalidParameterException("GMAPS API Only can make request to 10 locations.");
        }
        String baseUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&";
        String apiKeyQueryParams = "key=" + gmapsApiKey;
        String originQueryParams = "origins=";
        String destinationQueryParams = "destinations=";
        for (int i = 0; i < locationsRow.size(); i++) {
            originQueryParams = originQueryParams + "place_id:" + locationsRow.get(i).getPlaceId() + "|";
        }
        for (int i = 0; i < locationsColumn.size(); i++) {
            destinationQueryParams = destinationQueryParams + "place_id:" + locationsColumn.get(i).getPlaceId() + "|";
        }
        originQueryParams = originQueryParams + "&";
        destinationQueryParams = destinationQueryParams + "&";
        String fullUrl = baseUrl + originQueryParams + destinationQueryParams + apiKeyQueryParams;
        return fullUrl;
    }

    /**
     * This is a utility function to get the full API endpoint for Gmaps places Api from a string
     * @param locationName
     * @return
     */
    public static String generateGmapsPlacesUrl(String locationName) {
        String baseUrl = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?location=.sg&"
                + "inputtype=textquery&fields=name,place_id,geometry&locationbias=circle:4000@1.2966426,103.7763939&";
        String apiKeyQueryParams = "key=" + gmapsApiKey + "&";
        String queryParams = "input=" + conditionalLocationName(locationName) + "&";
        String fullUrl = baseUrl + queryParams + apiKeyQueryParams;
        return fullUrl;
    }

    /**
     * This method is used to get the details of a place from the place id
     * @param placeId
     * @return
     */
    public static String generateGmapsPlaceDetailsUrl(String placeId) {
        String baseUrl = "https://maps.googleapis.com/maps/api/place/details/json?";
        String placeIdQueryParams = "place_id=" + placeId + "&";
        String apiKeyQueryParams = "key=" + gmapsApiKey + "&";
        String fullUrl = baseUrl + placeIdQueryParams + apiKeyQueryParams;
        return fullUrl;
    }

    /**
     * This method is used to get image from GMAPS and save to local directory
     * @param coordinates
     * @return
     */
    public static String generateGmapsStaticImage(String coordinates) {
        String baseUrl = "https://maps.googleapis.com/maps/api/staticmap?size=500x500&zoom=17";
        String markerQueryParam = "&markers=color:red|size:large|label:L|" + coordinates;
        String centerQueryParam = "&center=" + coordinates;
        String apiKeyQueryParams = "&key=" + gmapsApiKey + "&";
        String fullUrl = baseUrl + markerQueryParam + centerQueryParam + apiKeyQueryParams;
        return fullUrl;
    }

    /**
     * This method is used to check if the key is present
     */
    public static boolean isGmapsKeyPresent() {
        return gmapsApiKey.length() != 0;
    }

    /**
     * This method is used to set the gmaps API key
     * @param gmapsApiKey
     */
    public static void setGmapsApiKey(String gmapsApiKey) {
        UrlUtil.gmapsApiKey = gmapsApiKey;
    }

    /**
     * This method is used to handle edge cases on Google Api so that it would support all the locations in NUS.
     * @param locationName
     * @return
     */
    private static String conditionalLocationName(String locationName) {
        if (!locationName.contains("NUS_")) {
            logger.warning("Internal logic error for conditional name " + locationName);
        }
        if (locationName.contains("AS") || locationName.contains("E2")) {
            return locationName.split("NUS_")[1];
        } else if (locationName.contains("EH")) {
            return "NUS_Eusoff_Hall";
        } else if (locationName.contains("GBT")) {
            return "NUS_SDE";
        } else if (locationName.contains("LT11")) {
            return "AS2";
        } else if (locationName.contains("MD7")) {
            return "MD7";
        } else if (locationName.contains("RH")) {
            return "NUS_Raffles_Hall";
        } else if (locationName.contains("S2")) {
            return "NUS_S1";
        } else if (locationName.contains("S5")) {
            return "NUS_S4";
        } else if (locationName.contains("S7") || locationName.contains("S8")) {
            return "S7";
        } else if (locationName.contains("SDE1")) {
            return "NUS_SDE";
        } else if (locationName.contains("SH")) {
            return "NUS_Sheares_Hall";
        } else if (locationName.contains("TB")) {
            return "NUS_CLB";
        } else if (locationName.contains("UT22")) {
            return "NUS_UT";
        } else if (locationName.contains("WT")) {
            return "NUS_SDE2";
        } else if (locationName.contains("YSTCM")) {
            return "NUS%20Yong%20Siew%20Toh%20Conservatory%20of%20Music";
        }

        return locationName;
    }
}
