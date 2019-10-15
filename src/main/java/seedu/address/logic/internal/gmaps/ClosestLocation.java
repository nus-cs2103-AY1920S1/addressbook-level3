package seedu.address.logic.internal.gmaps;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.TimeBookInvalidLocation;
import seedu.address.commons.util.VenueUtil;
import seedu.address.model.gmaps.Location;
import seedu.address.model.gmaps.LocationGraph;
import seedu.address.model.module.Venue;

/**
 * Class is used to get the closest location
 */
public class ClosestLocation {

    private LocationGraph locationGraph;

    public ClosestLocation() {
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream("data/LocationGraphSerialization");
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            locationGraph = (LocationGraph) in.readObject();

            in.close();
            file.close();

            System.out.println("LocationGraph has been deserialized");
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
    }

    /**
     * This is a wrapper function to use internally to get closest location from an ArrayList of venues
     * @param venues
     * @return
     */
    public String closestLocationVenues(ArrayList<Venue> venues) {
        ArrayList<String> locationNameList = VenueUtil.venueListToString(venues);
        String result = null;
        try {
            result = closestLocationString(locationNameList);
        } catch (IllegalValueException e) {
            System.out.println(e.getMessage());
        } catch (TimeBookInvalidLocation e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


    /**
     * This method is used to find the closes location from the location graph
     * @return
     */
    public String closestLocationString(ArrayList<String> locationNameList) throws IllegalValueException, TimeBookInvalidLocation {
        if (locationNameList.isEmpty()) {
            throw new TimeBookInvalidLocation("No location entered");
        }
        ArrayList<ArrayList<Long>> currMatrix = new ArrayList<ArrayList<Long>>();
        ArrayList<String> gmapsRecognisedLocationList = locationGraph.getGmapsRecognisedLocationList();
        ArrayList<Location> locations = locationGraph.getLocations();
        for (int i = 0; i < locationNameList.size(); i++) {
            int indexLocations = LocationArrayListUtils.getIndex(locations, locationNameList.get(i));
            Location currLocation = locations.get(indexLocations);
            String gmapsRecognisedLocation = currLocation.getGoogleRecognisedLocation();
            if (gmapsRecognisedLocation != null) {
                int indexGmapsRecognisedLocation = gmapsRecognisedLocationList.indexOf(gmapsRecognisedLocation);
                System.out.println("Index of " + gmapsRecognisedLocation + " is " + indexGmapsRecognisedLocation);
                ArrayList<Long> currRow = locationGraph.getLocationRow(indexGmapsRecognisedLocation);
                currMatrix.add(currRow);
            } else {
                locationNameList.remove(i);
            }
        }
        ArrayList<Long> totalDistance = new ArrayList<>();
        System.out.println("-" + currMatrix.get(0).size() + "|" + locationNameList.size());
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
        System.out.println(totalDistance);
        int minIndex = 0;
        Long min = Long.MAX_VALUE;
        for (int i = 0; i < totalDistance.size(); i++) {
            if (totalDistance.get(i) < min) {
                minIndex = i;
                min = totalDistance.get(i);
            }
        }
        return gmapsRecognisedLocationList.get(minIndex);
    }
}
