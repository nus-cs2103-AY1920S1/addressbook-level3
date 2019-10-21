package seedu.address.model.gmaps;

import java.net.ConnectException;
import java.util.ArrayList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.TimeBookInvalidState;
import seedu.address.logic.internal.gmaps.LocationArrayListUtils;


/**
 * This is the graph object that contains the information for location vertex
 */
public class LocationGraph implements java.io.Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private ArrayList<Location> locations;

    private ArrayList<String> validLocationList;

    private ArrayList<ArrayList<Long>> distanceMatrix = new ArrayList<>();

    public LocationGraph(ArrayList<Location> locations, ArrayList<String> validLocationList) {
        this.locations = locations;
        this.validLocationList = validLocationList;
        int validLocationListSize = validLocationList.size();
        for (int i = 0; i < validLocationListSize; i++) {
            distanceMatrix.add(new ArrayList<>());
        }
    }

    private LocationGraph(ArrayList<Location> locations, ArrayList<String> validLocationList,
             ArrayList<ArrayList<Long>> distanceMatrix)
            throws ConnectException, TimeBookInvalidState {
        this.locations = locations;
        this.validLocationList = validLocationList;
        this.distanceMatrix = distanceMatrix;
    }

    public ArrayList<String> getValidLocationList() {
        return validLocationList;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public int getLocationIndex(String locationName) {
        return LocationArrayListUtils.getIndex(locations, locationName);
    }

    public ArrayList<Long> getLocationRow(int index) throws IllegalValueException {
        if (distanceMatrix.size() <= index) {
            throw new IllegalValueException("Index " + index + "exceeds the size of the matrix");
        } else {
            System.out.println(distanceMatrix.size());
            return distanceMatrix.get(index);
        }
    }

    public ArrayList<ArrayList<Long>> getDistanceMatrix() {
        return distanceMatrix;
    }
    //TODO refactor out the return statement
    public LocationGraph setMatrixRow(int rowNum, ArrayList<Long> row) throws ConnectException, TimeBookInvalidState {
        distanceMatrix.get(rowNum).addAll(row);
        return new LocationGraph(locations, validLocationList, distanceMatrix);
    }

}
