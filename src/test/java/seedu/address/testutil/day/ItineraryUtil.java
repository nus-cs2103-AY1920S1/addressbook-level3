package seedu.address.testutil.day;

import seedu.address.logic.commands.AddDayCommand;
import seedu.address.model.Itinerary;

/**
 * A utility class for Contact.
 */
public class ItineraryUtil {

    /**
     * Returns an add command string for adding the {@code itineraryies}.
     */
    public static String getAddDayCommand(Itinerary itinerary) {
        return AddDayCommand.COMMAND_WORD + " " + AddDayCommand.SECOND_COMMAND_WORD + " "
                + getItineraryDetails(itinerary);
    }

    /**
     * Returns the part of command string for the given {@code itinerary}'s details.
     */
    public static String getItineraryDetails(Itinerary itinerary) {
        return itinerary.getItinerary().size() + "";
    }
}
