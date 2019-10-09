package seedu.address.logic.internal.gmaps;

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
        System.out.println(arrayList);
        int index = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getLocationName().equals(locationName)) {
                index = i;
                break;
            }
        }
        return index;
    }

}
