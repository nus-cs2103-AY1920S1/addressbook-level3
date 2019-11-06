package seedu.address.logic.internal.gmaps;

import java.net.ConnectException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import seedu.address.model.gmaps.Location;
import seedu.address.websocket.Cache;

/**
 * This method is used to initialise the location graph method
 */
public class ProcessLocationGraph {
    private ArrayList<Location> validLocationList;

    private ArrayList<ArrayList<Long>> distanceMatrix = new ArrayList<>();

    public ProcessLocationGraph(ArrayList<Location> validLocationList) {
        this.validLocationList = validLocationList;
        for (int i = 0; i < validLocationList.size(); i++) {
            distanceMatrix.add(new ArrayList<>());
        }
        process();
    }

    public ArrayList<ArrayList<Long>> getDistanceMatrix() {
        return distanceMatrix;
    }

    private void setMatrixRows(ArrayList<ArrayList<Long>> distanceMatrix, int start, int end) {
        if (distanceMatrix.size() != end - start + 1) {
            throw new InvalidParameterException("distanceMatrix size must equal to start - end + 1");
        } else {
            for (int i = 0; i < distanceMatrix.size(); i++) {
                ArrayList<Long> currRow = distanceMatrix.get(i);
                setMatrixRow(i + start, currRow);
            }
        }
    }

    /**
     * This method is used to populate the distance matrix.
     * @throws ConnectException
     */
    private void process() {
        System.out.println("Start populating");
        for (int i = 0; i <= validLocationList.size() / 10; i++) {
            ArrayList<Location> locationRowString = new ArrayList<Location>(validLocationList
                    .subList(i * 10 , Math.min((i + 1) * 10, validLocationList.size())));
            for (int j = 0; j < validLocationList.size() / 10; j++) {
                ArrayList<Location> locationColumnString = new ArrayList<Location>(validLocationList
                        .subList(j * 10 , Math.min((j + 1) * 10, validLocationList.size())));
                JSONObject apiResponse = Cache.loadDistanceMatrix(locationRowString, locationColumnString);
                ArrayList<ArrayList<Long>> currMatrix = GmapsJsonUtils.getArrayListMatrix(apiResponse);
                setMatrixRows(currMatrix, i * 10, Math.min(i * 10 + 9, validLocationList.size() - 1));
            }
        }
        System.out.println("Finish populating");
    }

    private void setMatrixRow(int rowNum, ArrayList<Long> row) {
        distanceMatrix.get(rowNum).addAll(row);
    }

}
