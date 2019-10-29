package seedu.address.logic.internal.gmaps;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ArrayListUtil;
import seedu.address.commons.util.LocationArrayListUtils;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.display.detailwindow.ClosestCommonLocationData;
import seedu.address.model.gmaps.Location;
import seedu.address.model.gmaps.LocationGraph;

/**
 * Class is used to get the closest location
 */
public class ClosestLocation {

    private LocationGraph locationGraph;
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    public ClosestLocation(LocationGraph locationGraph) {
        this.locationGraph = locationGraph;
    }

    /**
     * This is a wrapper function to use internally to get closest location from an ArrayList of venues
     * @param locationNameList
     * @return
     */
    public String closestLocationDataString(ArrayList<String> locationNameList) {
        ClosestCommonLocationData data = closestLocationData(locationNameList);
        String result = "";
        if (data.isOk()) {
            result = result + "\nFirst closest location: " + data.getFirstClosest()
                    + " | Average travelling distance/meters " + data.getFirstAvg() + "\n";
            result = result + "Second closest location: " + data.getSecondClosest()
                    + " | Average travelling distance/meters " + data.getSecondAvg() + "\n";
            result = result + "Third closest location: " + data.getThirdClosest()
                    + " | Average travelling distance/meters " + data.getThirdAvg() + "\n";
            if (!data.getInvalidLocation().isEmpty()) {
                result = result + "Could not recognise these locations:\n"
                        + ArrayListUtil
                        .toStringCommaSpaced(data.getInvalidLocation()) + "\n";
            }
        } else {
            result = "Internal error for " + locationNameList;
        }

        return result;
    }

    /**
     * This method is used to find the closes location from the location graph
     * @return
     */
    public ClosestCommonLocationData closestLocationData(ArrayList<String> locationNameList) {
        ClosestCommonLocationData closestCommonLocationData = new ClosestCommonLocationData();
        String firstClosest = null;
        String secondClosest = null;
        String thirdClosest = null;
        ArrayList<String> invalidLocation = new ArrayList<>();
        int groupSize = locationNameList.size();
        try {
            if (locationNameList.isEmpty()) {
                throw new IllegalValueException("No location entered");
            }
            ArrayList<ArrayList<Long>> currMatrix = new ArrayList<ArrayList<Long>>();
            ArrayList<String> validLocationList = locationGraph.getValidLocationList();
            ArrayList<Location> locations = locationGraph.getLocations();
            for (int i = locationNameList.size() - 1; i >= 0; i--) {
                String currLocationName = locationNameList.get(i);
                int indexLocation = LocationArrayListUtils.getIndex(locations, currLocationName);
                String validLocation = null;
                if (indexLocation >= 0) {
                    Location currLocation = locations.get(indexLocation);
                    validLocation = currLocation.getValidLocation();
                } else {
                    if (validLocationList.contains("NUS_" + locationNameList.get(i))) {
                        validLocation = "NUS_" + locationNameList.get(i);
                    }
                }
                if (validLocation != null) {
                    int indexValidLocation = validLocationList.indexOf(validLocation);
                    if (indexValidLocation == -1) {
                        indexValidLocation = validLocationList.indexOf("NUS_" + validLocation);
                    }
                    ArrayList<Long> currRow = locationGraph.getLocationRow(indexValidLocation);
                    currMatrix.add(currRow);
                } else {
                    invalidLocation.add(locationNameList.get(i));
                    locationNameList.remove(i);
                }
            }

            if (currMatrix.isEmpty()) {
                throw new IllegalValueException("All location entered cannot be identified by TimeBook");
            }

            ArrayList<Long> totalDistance = new ArrayList<>();

            for (int j = 0; j < currMatrix.get(0).size(); j++) {
                totalDistance.add((long) 0);
                boolean isAllNull = true;
                for (int i = 0; i < locationNameList.size(); i++) {
                    Long currDistance = (long) 0;
                    if (currMatrix.get(i).get(j) != null) {
                        isAllNull = false;
                        currDistance = currMatrix.get(i).get(j);
                    }
                    Long newDistance = totalDistance.get(j) + currDistance;
                    totalDistance.set(j, newDistance);
                }
                if (totalDistance.get(j) == 0 && isAllNull) {
                    totalDistance.set(j, Long.MAX_VALUE);
                }
            }
            int firstClosestIndex = 0;
            int secondClosestIndex = 0;
            int thirdClosestIndex = 0;
            ArrayList<String> closestLocations = new ArrayList<>();
            Long firstClosestTime = Long.MAX_VALUE;
            Long secondClosestTime = Long.MAX_VALUE;
            Long thirdClosestTime = Long.MAX_VALUE;
            for (int i = 0; i < totalDistance.size(); i++) {
                String currValidLocation = validLocationList.get(i);
                if (closestLocations.contains(currValidLocation)) {
                    continue;
                }
                if (totalDistance.get(i) < firstClosestTime) {
                    firstClosestIndex = i;
                    firstClosestTime = totalDistance.get(i);
                    closestLocations.add(currValidLocation);
                } else if (totalDistance.get(i) < secondClosestTime) {
                    secondClosestIndex = i;
                    secondClosestTime = totalDistance.get(i);
                    closestLocations.add(currValidLocation);
                } else if (totalDistance.get(i) < thirdClosestTime) {
                    secondClosestIndex = i;
                    thirdClosestTime = totalDistance.get(i);
                    closestLocations.add(currValidLocation);
                }
            }

            firstClosest = StringUtil.removeNusPrefix(validLocationList.get(firstClosestIndex));
            secondClosest = StringUtil.removeNusPrefix(validLocationList.get(secondClosestIndex));
            thirdClosest = StringUtil.removeNusPrefix(validLocationList.get(thirdClosestIndex));

            long firstClosestAvgTime = firstClosestTime / groupSize;
            long secondClosestAvgTime = secondClosestTime / groupSize;
            long thirdClosestAvgTime = thirdClosestTime / groupSize;

            closestCommonLocationData.setImagePath("NUS_" + firstClosest);
            closestCommonLocationData.setFirstClosest(firstClosest);
            closestCommonLocationData.setSecondClosest(secondClosest);
            closestCommonLocationData.setThirdClosest(thirdClosest);
            closestCommonLocationData.setFirstAvg(firstClosestAvgTime);
            closestCommonLocationData.setSecondAvg(secondClosestAvgTime);
            closestCommonLocationData.setThirdAvg(thirdClosestAvgTime);
            closestCommonLocationData.setInvalidLocation(invalidLocation);
            closestCommonLocationData.setOk(true);
        } catch (IllegalValueException e) {
            logger.info(e.getMessage());
        }
        return closestCommonLocationData;
    }
}
