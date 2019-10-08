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

    public GmapsApi() {
        this.apiKey = "AIzaSyDTTisjkJJnVDyKVTuNL5POQis87wtA5D8";
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
        String apiKeyQueryParams = "key=" + apiKey;
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
        ApiQuery query = new ApiQuery(fullUrl);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONObject obj = ParserUtil.parseStringToJsonObject(queryResult.getResponseResult());
            return obj;
        } else {
            throw new ConnectException(ParserUtil.parseStringToJsonObject(queryResult.getResponseResult()).toString());
        }
    }

    public JSONObject getLocation(String locationName) throws ConnectException {
        String baseUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?location=.sg&";
        String apiKeyQueryParams = "key=" + apiKey + "&";
        String queryParams = "query=" + locationName + "&";
        String fullUrl = baseUrl + queryParams + apiKeyQueryParams;
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
