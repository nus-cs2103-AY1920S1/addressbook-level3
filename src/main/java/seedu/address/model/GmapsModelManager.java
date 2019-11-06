package seedu.address.model;

import java.net.ConnectException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.logic.internal.gmaps.ClosestLocation;
import seedu.address.logic.internal.gmaps.GenerateImage;
import seedu.address.logic.internal.gmaps.GmapsJsonUtils;
import seedu.address.logic.internal.gmaps.ProcessLocationGraph;
import seedu.address.logic.internal.gmaps.ProcessVenues;
import seedu.address.model.display.detailwindow.ClosestCommonLocationData;
import seedu.address.model.gmaps.Location;
import seedu.address.model.gmaps.LocationGraph;
import seedu.address.websocket.Cache;

/**
 * Represent the in memory of Gmaps related model
 */
public class GmapsModelManager {

    private ArrayList<Location> locations;

    private ArrayList<Location> validLocationList;

    private LocationGraph locationGraph;

    public GmapsModelManager() {
        try {
            initProcessVenues();
            initLocationGraph();
        } catch (TimeBookInvalidState e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to get the suggestions for valid locations.
     * @param prefix
     * @return
     */
    public ArrayList<String> validLocationSuggester(String prefix) {
        ArrayList<String> suggestions = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            String name = locations.get(i).getLocationName();
            if (name.startsWith(prefix)) {
                suggestions.add(name);
            }
        }
        return suggestions;
    }

    /**
     * This method is used to assign the coordinates to the valid locations
     */
    public void populateCoordinates() {
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
     * This method is used to re-generate the images for google maps
     * @throws TimeBookInvalidState
     */
    public void generateImage() throws TimeBookInvalidState {
        populateCoordinates();
        new GenerateImage(validLocationList).execute();
    }

    public ArrayList<Location> getValidLocationList() {
        return validLocationList;
    }

    public ClosestCommonLocationData closestLocationData(ArrayList<String> locationNameList) {
        return new ClosestLocation(locationGraph).closestLocationData(locationNameList);
    }

    public String closestLocationDataString(ArrayList<String> locationNameList) {
        return new ClosestLocation(locationGraph).closestLocationDataString(locationNameList);
    }

    /**
     * Method used to initProcess venues and get location list and validLocationList
     */
    private void initProcessVenues() throws TimeBookInvalidState {
        ProcessVenues processVenues;
        processVenues = new ProcessVenues().process();
        locations = processVenues.getLocations();
        validLocationList = processVenues.getValidLocationList();
    }

    /**
     * Method used to initialise the full location graph
     * @throws TimeBookInvalidState
     * @throws ConnectException
     */
    private void initLocationGraph() {
        ArrayList<ArrayList<Long>> distanceMatrix = new ProcessLocationGraph(validLocationList).getDistanceMatrix();
        locationGraph = new LocationGraph(locations, validLocationList, distanceMatrix);
    }
}
