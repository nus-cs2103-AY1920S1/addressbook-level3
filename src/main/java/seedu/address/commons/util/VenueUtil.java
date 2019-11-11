package seedu.address.commons.util;

import java.util.ArrayList;

import seedu.address.model.module.Venue;

/**
 * This class is for the utility functions for venues
 */
public class VenueUtil {
    /**
     * This method is used to convert ArrayList of venues to ArrayList of string
     * @param venues
     * @return
     */
    public static ArrayList<String> venueListToString(ArrayList<Venue> venues) {
        ArrayList<String> stringList = new ArrayList<>();
        while (!venues.isEmpty()) {
            stringList.add(venues.remove(0).toString());
        }
        return stringList;
    }
}
