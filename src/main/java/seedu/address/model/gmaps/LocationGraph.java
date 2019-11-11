package seedu.address.model.gmaps;

import java.util.ArrayList;

import seedu.address.commons.exceptions.IllegalValueException;


/**
 * This is the graph object that contains the information for location vertex
 */
public class LocationGraph {
    private ArrayList<Location> locations;

    private ArrayList<Location> validLocationList;

    private ArrayList<ArrayList<Long>> distanceMatrix;

    public LocationGraph(ArrayList<Location> locations, ArrayList<Location> validLocationList,
                         ArrayList<ArrayList<Long>> distanceMatrix) {
        this.locations = locations;
        this.validLocationList = validLocationList;
        this.distanceMatrix = distanceMatrix;
    }

    public ArrayList<Location> getValidLocationList() {
        return validLocationList;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }


    public ArrayList<Long> getLocationRow(int index) throws IllegalValueException {
        if (distanceMatrix.size() <= index) {
            throw new IllegalValueException("Index " + index + "exceeds the size of the matrix");
        } else {
            return distanceMatrix.get(index);
        }
    }

    public ArrayList<ArrayList<Long>> getDistanceMatrix() {
        return distanceMatrix;
    }
}
