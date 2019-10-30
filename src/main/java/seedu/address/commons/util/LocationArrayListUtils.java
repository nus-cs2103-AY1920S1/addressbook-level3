package seedu.address.commons.util;

import java.util.ArrayList;

import seedu.address.model.gmaps.Location;

/**
 * This class is used for process location object arraylist
 */
public class LocationArrayListUtils {
    /**
     * This method is used to get the index of the location with the same name
     * @param arrayList
     * @param locationName
     * @return
     */
    public static int getIndex(ArrayList<Location> arrayList, String locationName) {
        int index = -1;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) == null) {
                continue;
            }
            if (arrayList.get(i).getLocationName().equals(locationName)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * This method is used to check if the locationName is in the list of Location
     * @param arrayList
     * @param locationName
     * @return
     */
    public static boolean containLocationName(ArrayList<Location> arrayList, String locationName) {
        boolean result = false;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getLocationName().equals(locationName)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
