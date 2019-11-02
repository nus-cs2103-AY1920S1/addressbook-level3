package seedu.address.logic.internal.gmaps;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * This class conatains static method for the processing of Gmaps API repsonse
 */
public class GmapsJsonUtils {

    /**
     * This method is used to get the arraylist of arraylist of value for the calculation of the matrix
     * @param apiResponse
     * @return
     */
    public static ArrayList<ArrayList<Long>> getArrayListMatrix(JSONObject apiResponse) {
        ArrayList<ArrayList<Long>> distanceMatrixArrayList = new ArrayList<>();
        JSONArray rows = (JSONArray) apiResponse.get("rows");
        for (int i = 0; i < rows.size(); i++) {
            ArrayList<Long> currVertex = new ArrayList<>();
            JSONObject currRow = (JSONObject) rows.get(i);
            JSONArray elements = (JSONArray) currRow.get("elements");
            for (int j = 0; j < elements.size(); j++) {
                Long value;
                JSONObject currElement = (JSONObject) elements.get(j);
                if (((String) currElement.get("status")).equals("OK")) {
                    JSONObject duration = (JSONObject) currElement.get("distance");
                    value = (Long) duration.get("value");
                } else {
                    value = null;
                }
                currVertex.add(value);
            }
            distanceMatrixArrayList.add(currVertex);
        }
        return distanceMatrixArrayList;
    }

    public static String getPlaceId(JSONObject apiResponse) {
        JSONArray candidates = (JSONArray) apiResponse.get("candidates");
        JSONObject firstCandidate = (JSONObject) candidates.get(0);
        return (String) firstCandidate.get("place_id");
    }

    public static double getLat(JSONObject apiResponse) {
        JSONObject result = (JSONObject) apiResponse.get("result");
        JSONObject geometry = (JSONObject) result.get("geometry");
        JSONObject location = (JSONObject) geometry.get("location");
        return (double) location.get("lat");
    }

    public static double getLng(JSONObject apiResponse) {
        JSONObject result = (JSONObject) apiResponse.get("result");
        JSONObject geometry = (JSONObject) result.get("geometry");
        JSONObject location = (JSONObject) geometry.get("location");
        return (double) location.get("lng");
    }

    public static String getAlias(JSONObject apiResponse) {
        JSONObject result = (JSONObject) apiResponse.get("result");
        return (String) result.get("name");
    }


    /**
     * This method is used to get the status of the api response
     * @param apiResponse
     * @return
     */
    public static String getStatus(JSONObject apiResponse) {
        String status = (String) apiResponse.get("status");
        return status;
    }
}
