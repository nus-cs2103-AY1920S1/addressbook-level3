package seedu.address.logic.internal.gmaps;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import seedu.address.model.gmaps.Location;

/**
 * This class is used for process location object arraylist
 */
public class LocationArrayListUtils {
    public static ArrayList<Location> getSubList(ArrayList<Location> arrayList, int start, int end) {
        ArrayList<Location> newArrayList = new ArrayList<>();
        if (start < 0 || start > arrayList.size()) {
            throw new InvalidParameterException("Value for start parameter is out of bound");
        } else if (end < 0 || end > arrayList.size()) {
            throw new InvalidParameterException("Value for end parameter is out of bound");
        } else if (start > end) {
            throw new InvalidParameterException("Start > end is an invalid logic");
        }
        for (int i = start; i <= end; i++) {
            Location currLocation = arrayList.get(i);
            newArrayList.add(currLocation);
        }
        return newArrayList;
    }
    public static ArrayList<String> locationListToStringList(ArrayList<Location> arrayList) {
        ArrayList<String> newArrayList = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            newArrayList.add(arrayList.get(i).getGoogleRecognisedLocation());
        }
        return newArrayList;
    }
    public static int getIndex(ArrayList<Location> arrayList, String locationName) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getLocationName().equals(locationName)) {
                return i;
            }
        }
        return -1;
    }

}
