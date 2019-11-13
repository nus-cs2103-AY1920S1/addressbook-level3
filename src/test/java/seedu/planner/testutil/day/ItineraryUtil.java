package seedu.planner.testutil.day;

import seedu.planner.logic.commands.addcommand.AddDayCommand;
import seedu.planner.model.Itinerary;

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
