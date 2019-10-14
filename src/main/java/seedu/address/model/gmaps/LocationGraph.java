package seedu.address.model.gmaps;

import java.net.ConnectException;
import java.util.ArrayList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.logic.internal.gmaps.LocationArrayListUtils;
import seedu.address.logic.internal.gmaps.ProcessVenues;


/**
 * This is the graph object that contains the information for location vertex
 */
public class LocationGraph implements java.io.Serializable {

    private ArrayList<Location> locations;

    private ArrayList<String> gmapsRecognisedLocationList;

    private ArrayList<ArrayList<Long>> distanceMatrix = new ArrayList<>();

    private ProcessVenues processVenues;

    public LocationGraph(ProcessVenues processVenues) throws ConnectException, TimeBookInvalidState {
        this.processVenues = processVenues;
        this.locations = processVenues.getLocations();
        this.gmapsRecognisedLocationList = processVenues.getGmapsRecognisedLocationList();
        int gmapsRecognisedLocationListSize = gmapsRecognisedLocationList.size();
        for (int i = 0; i < gmapsRecognisedLocationListSize; i++) {
            distanceMatrix.add(new ArrayList<>());
        }
    }

    private LocationGraph(ProcessVenues processVenues, ArrayList<ArrayList<Long>> distanceMatrix)
            throws ConnectException, TimeBookInvalidState {
        this.locations = processVenues.getLocations();
        this.gmapsRecognisedLocationList = processVenues.getGmapsRecognisedLocationList();
        this.distanceMatrix = distanceMatrix;
    }

    public ArrayList<String> getGmapsRecognisedLocationList() {
        return gmapsRecognisedLocationList;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public int getLocationIndex(String locationName) {
        return LocationArrayListUtils.getIndex(locations, locationName);
    }

    public ArrayList<Long> getLocationRow(int index) throws IllegalValueException {
        if (distanceMatrix.size() <= index) {
            throw new IllegalValueException("Index " + index + "exceeds the size of the matix");
        } else {
            System.out.println(distanceMatrix.size());
            return distanceMatrix.get(index);
        }
    }

    public ArrayList<ArrayList<Long>> getDistanceMatrix() {
        return distanceMatrix;
    }

    public LocationGraph setMatrixRow(int rowNum, ArrayList<Long> row) throws ConnectException, TimeBookInvalidState {
        distanceMatrix.get(rowNum).addAll(row);
        return new LocationGraph(processVenues, distanceMatrix);
    }

}
