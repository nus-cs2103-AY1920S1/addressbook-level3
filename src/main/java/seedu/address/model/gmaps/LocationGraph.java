package seedu.address.model.gmaps;

import java.util.ArrayList;

import seedu.address.logic.internal.gmaps.LocationArrayListUtils;

/**
 * This is the graph object that contains the information for location vertex
 */
public class LocationGraph {

    private ArrayList<Location> venues;

    private ArrayList<ArrayList<Long>> distanceMatrix = new ArrayList<>();

    public LocationGraph(ArrayList<Location> venues) {
        this.venues = venues;
        int venuesSize = venues.size();
        for (int i = 0; i < venuesSize ; i++) {
            distanceMatrix.add(new ArrayList<>());
        }
    }

    public LocationGraph(ArrayList<Location> venues, ArrayList<ArrayList<Long>> distanceMatrix) {
        this.venues = venues;
        this.distanceMatrix = distanceMatrix;
    }

    public ArrayList<Location> getVenues() {
        return venues;
    }

    public int getLocationIndex(String locationName) {
        return LocationArrayListUtils.getIndex(venues, locationName);
    }

    public ArrayList<Long> getLocationRow(int index) {
        return distanceMatrix.get(index);
    }

    public ArrayList<ArrayList<Long>> getDistanceMatrix() {
        return distanceMatrix;
    }

    public LocationGraph setMatrixRow(int rowNum, ArrayList<Long> row) {
            distanceMatrix.get(rowNum).addAll(row);
            return new LocationGraph(venues, distanceMatrix);
    }

}
