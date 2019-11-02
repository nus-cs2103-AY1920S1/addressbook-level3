package seedu.address.logic.internal.gmaps;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.model.GmapsModelManager;
import seedu.address.model.gmaps.Location;
import seedu.address.websocket.Cache;
import seedu.address.websocket.util.ImageQuery;
import seedu.address.websocket.util.UrlUtil;

/**
 * This class is used as a functional class to generate images for the various locations
 */
public class GenerateImage {

    private ArrayList<Location> validLocationList;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public GenerateImage(ArrayList<Location> validLocationList) {
        this.validLocationList = validLocationList;
    }

    /**
     * This command is used to execute the generation of images
     * @throws TimeBookInvalidState
     */
    public void execute() throws TimeBookInvalidState {
        if (!UrlUtil.isGmapsKeyPresent()) {
            throw new TimeBookInvalidState("Enter API key to execute the command.");
        } else if (validLocationList.isEmpty()) {
            throw new TimeBookInvalidState("Process Venues first before generating images.");
        }
        populateCoordinates();
        retrieveImage();
    }

    /**
     * This method is used to assign the coordinates to the valid locations
     */
    private void populateCoordinates() {
        for (int i = 0; i < validLocationList.size(); i++) {
            Location currValidLocation = validLocationList.get(i);
            String placeId = currValidLocation.getPlaceId();
            JSONObject response = Cache.loadPlaceDetails(placeId);
            String lat = Double.toString(GmapsJsonUtils.getLat(response));
            String lng = Double.toString(GmapsJsonUtils.getLng(response));
            currValidLocation.setLat(lat);
            currValidLocation.setLng(lng);
        }
    }

    /**
     * This method is used to get image for the valid location
     */
    private void retrieveImage() {
        for (int i = 0; i < validLocationList.size(); i++) {
            Location currValidLocation = validLocationList.get(i);
            String url = UrlUtil.generateGmapsStaticImage(currValidLocation.getLatLng());
            String fullPath = Cache.writeImagePath(currValidLocation.getValidLocation());
            logger.info("getting image for " + currValidLocation.getLocationName());
            ImageQuery.execute(url, fullPath);
        }
    }

    public static void main(String[] args) throws TimeBookInvalidState {
        new GmapsModelManager().generateImage();
    }
}
