package seedu.address.logic.internal.gmaps;

import java.net.ConnectException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import seedu.address.commons.exceptions.TimeBookInvalidLocation;
import seedu.address.model.gmaps.Location;
import seedu.address.websocket.Cache;

/**
 * This call is used to find the valid location name
 */
public class SanitizeLocation {
    private ArrayList<Location> validLocationList = new ArrayList<>();
    /**
     * Takes in gmapsApi so that it could be replaced by a gmapsApi stub
     */
    public SanitizeLocation() {
    }

    /**
     * This method is used to get the list of sanitized location
     * @return
     */
    public ArrayList<Location> getValidLocationList() {
        return this.validLocationList;
    }

    /**
     * This method is used to santize the location name on nus mods to a google maps identifiable location
     * @param locationName
     * @return
     * @throws ConnectException
     */
    public Location sanitize(String locationName) throws TimeBookInvalidLocation {
        Location location = new Location(locationName);
        String validLocationString = locationName;
        validLocationString = validLocationString.split("-")[0];
        validLocationString = validLocationString.split("/")[0];
        validLocationString = validLocationString.split("_")[0];
        validLocationString = "NUS_" + validLocationString;
        Location validLocation = new Location(validLocationString);
        validLocation.setValidLocation(validLocationString);
        if (!validLocationList.contains(validLocation)) {
            JSONObject apiResponse = Cache.loadPlaces(validLocationString);
            String status = GmapsJsonUtils.getStatus(apiResponse);
            if (status.equals("OK")) {
                validLocation.setPlaceId(GmapsJsonUtils.getPlaceId(apiResponse));
                validLocationList.add(validLocation);

                location.setValidLocation(validLocation.getValidLocation());
                location.setPlaceId(GmapsJsonUtils.getPlaceId(apiResponse));
            } else {
                throw new TimeBookInvalidLocation("Cannot identify " + validLocation);
            }
        } else {
            String placeId = validLocationList.get(validLocationList.indexOf(validLocation)).getPlaceId();
            location.setPlaceId(placeId);
            location.setValidLocation(validLocationString);
        }

        return location;
    }
}
