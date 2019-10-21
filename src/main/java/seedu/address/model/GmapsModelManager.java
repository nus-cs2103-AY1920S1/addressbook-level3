package seedu.address.model;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.logic.internal.gmaps.ClosestLocation;
import seedu.address.logic.internal.gmaps.ProcessLocationGraph;
import seedu.address.logic.internal.gmaps.ProcessVenues;
import seedu.address.model.gmaps.Location;
import seedu.address.model.gmaps.LocationGraph;

/**
 * Represent the in memory of Gmaps related model
 */
public class GmapsModelManager {

    private ArrayList<Location> locations;

    private ArrayList<String> validLocationList;

    private LocationGraph locationGraph;

    public GmapsModelManager() {
        try {
            initProcessVenues();
            initLocationGraph();
        } catch (TimeBookInvalidState | ConnectException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GmapsModelManager gmapsModelManager = new GmapsModelManager();
        System.out.println(gmapsModelManager.closestLocationDataString(new ArrayList<>(Arrays.asList("AS5", "LT17", "LT14"))));
    }

    public Hashtable<String, Object> closestLocationData(ArrayList<String> locationNameList) {
        return new ClosestLocation(locationGraph).closestLocationData(locationNameList);
    }

    public String closestLocationDataString(ArrayList<String> locationNameList) {
        return new ClosestLocation(locationGraph).closesLocationDataString(locationNameList);
    }

    /**
     * Method used to initProcess venues and get location list and validLocationList
     */
    private void initProcessVenues() throws TimeBookInvalidState, ConnectException {
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
    private void initLocationGraph() throws TimeBookInvalidState, ConnectException {
        locationGraph = new LocationGraph(locations, validLocationList);
        new ProcessLocationGraph(locationGraph).populateMatrix();
    }
}
