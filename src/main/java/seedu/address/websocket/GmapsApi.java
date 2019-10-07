package seedu.address.websocket;

import java.net.ConnectException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import seedu.address.commons.core.LogsCenter;
import seedu.address.websocket.util.ApiQuery;
import seedu.address.websocket.util.ParserUtil;
import seedu.address.websocket.util.QueryResult;

/**
 * This class is used for making API request to Google Maps API
 */
public class GmapsApi {

    private final String apiKey;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public GmapsApi(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * This method is used to execute the Google Maps distance matrix API. It will return
     * a complete matrix mapping of all the String in the locations
     * @param locations
     * @return JSONObject of api call
     * @throws InvalidParameterException
     * @throws ConnectException
     */
    public JSONObject getDistanceMatrix(ArrayList<String> locations)
            throws InvalidParameterException, ConnectException {
        if (locations.size() > 10) {
            throw new InvalidParameterException("GMAPS API Only can make request to 10 locations.");
        }
        String baseUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&";
        String apiKeyQueryParams = "key=" + apiKey;
        String originQueryParams = "origins=";
        String destinationQueryParams = "destinations=";
        String venuesQueryParams = "";
        for (int i = 0; i < locations.size(); i++) {
            venuesQueryParams = venuesQueryParams + locations.get(i) + "|";
        }
        originQueryParams = originQueryParams + venuesQueryParams + "&";
        destinationQueryParams = destinationQueryParams + venuesQueryParams + "&";
        String fullUrl = baseUrl + originQueryParams + destinationQueryParams + apiKeyQueryParams;
        ApiQuery query = new ApiQuery(fullUrl);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONObject obj = ParserUtil.parseStringToJsonObject(queryResult.getResponseResult());
            return obj;
        } else {
            throw new ConnectException(ParserUtil.parseStringToJsonObject(queryResult.getResponseResult()).toString());
        }
    }
}
