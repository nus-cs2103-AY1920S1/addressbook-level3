package seedu.address.websocket;

import java.net.ConnectException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import seedu.address.commons.core.LogsCenter;
import seedu.address.websocket.util.ApiQueryFactory;
import seedu.address.websocket.util.ParserUtil;
import seedu.address.websocket.util.QueryResult;

/**
 * This class is used for making API request to Google Maps API
 */
public class GmapsApi {

    private static final String API_KEY = "AIzaSyAbbblr33yEEYFNqFgiqfSckdgBANayVms";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public GmapsApi() {
    }

    /**
     * This method is used to execute the Google Maps distance matrix API. It will return
     * a complete matrix mapping of all the String in the locations
     * @param
     * @return JSONObject of api call
     * @throws InvalidParameterException
     * @throws ConnectException
     */
    public JSONObject getDistanceMatrix(ArrayList<String> locationsRow, ArrayList<String> locationsColumn)
            throws InvalidParameterException, ConnectException {
        if (locationsColumn.size() > 10 || locationsRow.size() > 10) {
            throw new InvalidParameterException("GMAPS API Only can make request to 10 locations.");
        }
        String baseUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&";
        String apiKeyQueryParams = "key=" + API_KEY;
        String originQueryParams = "origins=";
        String destinationQueryParams = "destinations=";
        for (int i = 0; i < locationsRow.size(); i++) {
            originQueryParams = originQueryParams + locationsRow.get(i) + "|";
        }
        for (int i = 0; i < locationsColumn.size(); i++) {
            destinationQueryParams = destinationQueryParams + locationsColumn.get(i) + "|";
        }
        originQueryParams = originQueryParams + "&";
        destinationQueryParams = destinationQueryParams + "&";
        String fullUrl = baseUrl + originQueryParams + destinationQueryParams + apiKeyQueryParams;
        ApiQueryFactory query = new ApiQueryFactory(fullUrl, CacheFileNames.GMAPS_DISTANCE_MATRIX_PATH);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONObject obj = ParserUtil.parseStringToJsonObject(queryResult.getResponseResult());
            return obj;
        } else {
            throw new ConnectException(ParserUtil.parseStringToJsonObject(queryResult.getResponseResult()).toString());
        }
    }

    public JSONObject getLocation(String locationName) throws ConnectException {
        String baseUrl = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?location=.sg&"
                + "inputtype=textquery&fields=name,place_id&";
        String apiKeyQueryParams = "key=" + API_KEY + "&";
        String queryParams = "input=" + locationName + "&";
        String fullUrl = baseUrl + queryParams + apiKeyQueryParams;
        System.out.println(fullUrl);
        ApiQueryFactory query = new ApiQueryFactory(fullUrl, CacheFileNames.GMAPS_PLACES_PATH);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONObject obj = ParserUtil.parseStringToJsonObject(queryResult.getResponseResult());
            return obj;
        } else {
            throw new ConnectException(ParserUtil.parseStringToJsonObject(queryResult.getResponseResult()).toString());
        }
    }
}
