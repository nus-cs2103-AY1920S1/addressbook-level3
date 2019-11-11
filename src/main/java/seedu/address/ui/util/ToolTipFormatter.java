package seedu.address.ui.util;

import seedu.address.model.display.timeslots.PersonTimeslot;

/**
 * Utility class to format messages for Tooltips.
 */
public class ToolTipFormatter {
    /**
     * Formats the tool tip message of a PersonTimeslot object.
     * @param timeslot
     * @return
     */
    public static String formatTooltipMessage(PersonTimeslot timeslot) {
        String locationText = timeslot.getVenue().getVenue().trim().equals("") ? "" : " at " + timeslot.getVenue();
        return timeslot.getEventName() + " " + timeslot.getStartTime() + " - "
                + timeslot.getEndTime() + locationText;
    }
}
