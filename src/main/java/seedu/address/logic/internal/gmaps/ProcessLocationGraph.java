package seedu.address.logic.internal.gmaps;

import java.net.ConnectException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import seedu.address.model.gmaps.Location;
import seedu.address.model.gmaps.LocationGraph;
import seedu.address.websocket.GmapsApi;

public class ProcessLocationGraph {
    private GmapsApi gmapsApi = new GmapsApi();
    private LocationGraph locationGraph;

    public ProcessLocationGraph(LocationGraph locationGraph) {
        this.locationGraph = locationGraph;
    }
    private void setMatrixRows(ArrayList<ArrayList<Long>> distanceMatrix, int start, int end) {
        if (distanceMatrix.size() != end - start + 1) {
            System.out.println((distanceMatrix.size() + "|" + start + "|" + end));
            throw new InvalidParameterException("distanceMatrix size must equal to start - end + 1");
        } else {
            for (int i = 0; i < distanceMatrix.size(); i++) {
                ArrayList<Long> currRow = distanceMatrix.get(i);
                locationGraph.setMatrixRow(i + start, currRow);
            }
        }
    }

    public void populateMatrix() throws ConnectException {
        ArrayList<Location> venues = locationGraph.getVenues();
        for (int i = 0; i < venues.size()/10 ; i++) {
            ArrayList<Location> locationsRowObj = LocationArrayListUtils.getSubList(venues, i*10 , Math.min(i*10 + 9, venues.size()-1));
            ArrayList<String> locationRowString = LocationArrayListUtils.locationListToStringList(locationsRowObj);
            for (int j = 0; j < venues.size()/10; j++) {
                ArrayList<Location> locationsColumnObj = LocationArrayListUtils.getSubList(venues, j*10, Math.min(j*10 + 9, venues.size()-1));
                ArrayList<String> locationColumnString = LocationArrayListUtils.locationListToStringList(locationsColumnObj);
                JSONObject apiResponse = gmapsApi.getDistanceMatrix(locationRowString, locationColumnString);
                ArrayList<ArrayList<Long>> currMatrix = GmapsJsonUtils.getArrayListMatrix(apiResponse);
                setMatrixRows(currMatrix, i*10, i*10 + 9);
                System.out.println("Building matrix for row: " + locationRowString + "column: " + locationColumnString);
            }
        }
    }

    public static void main(String[] args) throws ConnectException {
        GetVenues getVenues = new GetVenues();
        LocationGraph locationGraph = new LocationGraph(getVenues.getVenues());
        System.out.println(locationGraph.getVenues());
        ProcessLocationGraph processLocationGraph = new ProcessLocationGraph(locationGraph);
        processLocationGraph.populateMatrix();
        System.out.println(locationGraph.getDistanceMatrix());
    }
}
