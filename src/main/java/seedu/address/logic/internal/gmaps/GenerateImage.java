package seedu.address.logic.internal.gmaps;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.model.GmapsModelManager;
import seedu.address.model.gmaps.Location;
import seedu.address.websocket.Cache;

public class GenerateImage {

    private ArrayList<Location> validLocationList;

    public GenerateImage(ArrayList<Location> validLocationList) {
        this.validLocationList = validLocationList;
    }

    public void execute() throws TimeBookInvalidState {
        for (int i = 0; i < validLocationList.size(); i++) {
            if (validLocationList.get(i).getPlaceId() == null) {
                throw new TimeBookInvalidState("No place ID for " + validLocationList.get(i));
            }
        }
    }

    /**
     * This method is used to assign the coordinates to the valid locations
     */
    private void populateCoordinates() {
        for (int i = 0; i < validLocationList.size(); i++) {
            Location currValidLocation = validLocationList.get(i);
            String placeId = currValidLocation.getPlaceId();
            JSONObject response = Cache.loadPlaceDetails(placeId);

        }
    }

    public static void main(String[] args) throws TimeBookInvalidState {
        new GmapsModelManager().generateImage();
    }
}
