package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.properties.component.descriptive.Categories;
import seedu.address.commons.util.EventUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Creates a new event to be added to the event list.
 */
public class EventAddCommand extends EventCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new event\n"
            + "Parameters:\n"
            + "eventName/ [EVENTNAME]\n"
            + "startDateTime/ [STARTDATETIME]\n"
            + "endDateTime/ [ENDDATETIME]\n"
            + "recur/ [DAILY/WEEKLY/NONE]\n"
            + "color/ [0 - 23]\n"
            + "Example: event eventName/cs2100 lecture startDateTime/2019-10-21T14:00 "
            + "endDateTime/2019-10-21T15:00 recur/none color/1";

    private static final String WEEKLY_RECURRENCE_RULE = "FREQ=WEEKLY;INTERVAL=1";
    private static final String DAILY_RECURRENCE_RULE = "FREQ=DAILY;INTERVAL=1";

    private static final String BAD_DATE_FORMAT = "Invalid DateTime Format. "
            + "Please follow the format: yyyy-MM-ddTHH:mm, "
            + "e.g. 28 October 2019, 2PM should be input as 2019-10-28T14:00";
    private static final String INVALID_RECURRENCE_TYPE = "Invalid Recurrence Type";
    private static final String INVALID_EVENT_RANGE = "Invalid date range between start and end dateTime";
    private static final String DEFAULT_COLOR_STRING = "group00";

    private final String eventName;
    private final String startDateTimeString;
    private final String endDateTimeString;
    private final String recurTypeString;
    private final String colorNumberString;

    /**
     * Creates a EventAddCommand object.
     *
     * @param eventName to set.
     * @param startDateTimeString   string representation of eventStartDateTime.
     * @param endDateTimeString   string representation of eventEndDateTime.
     * @param recurTypeString     of event e.g weekly, daily, or none.
     */
    public EventAddCommand(String eventName, String startDateTimeString, String endDateTimeString,
                           String recurTypeString) {
        requireNonNull(eventName);
        requireNonNull(startDateTimeString);
        requireNonNull(endDateTimeString);
        requireNonNull(recurTypeString);

        this.eventName = eventName;
        this.startDateTimeString = startDateTimeString;
        this.endDateTimeString = endDateTimeString;
        this.recurTypeString = recurTypeString.toLowerCase();
        this.colorNumberString = DEFAULT_COLOR_STRING;
    }

    /**
     * Overloaded constructor to create a EventAddCommand object with color type
     *
     * @param eventName to set.
     * @param startDateTimeString   string representation of eventStartDateTime.
     * @param endDateTimeString   string representation of eventEndDateTime.
     * @param recurTypeString     of event e.g weekly, daily, or none.
     */
    public EventAddCommand(String eventName, String startDateTimeString, String endDateTimeString,
                           String recurTypeString, String colorNumberString) {
        requireNonNull(eventName);
        requireNonNull(startDateTimeString);
        requireNonNull(endDateTimeString);
        requireNonNull(recurTypeString);

        this.eventName = eventName;
        this.startDateTimeString = startDateTimeString;
        this.endDateTimeString = endDateTimeString;
        this.recurTypeString = recurTypeString.toLowerCase();
        this.colorNumberString = EventUtil.convertNumberToColorNumber(colorNumberString);
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        VEvent vEvent = new VEvent();
        vEvent.setSummary(eventName);

        LocalDateTime startDateTime;
        LocalDateTime endDateTime;

        try {
            startDateTime = LocalDateTime.parse(startDateTimeString);
            endDateTime = LocalDateTime.parse(endDateTimeString);
        } catch (DateTimeParseException dtpEx) {
            throw new CommandException(BAD_DATE_FORMAT, dtpEx);
        }

        if (startDateTime.compareTo(endDateTime) >= 1) {
            throw new CommandException(INVALID_EVENT_RANGE);
        }

        vEvent.setDateTimeStart(startDateTime);
        vEvent.setDateTimeEnd(endDateTime);

        String uniqueIdentifier = EventUtil.generateUniqueIdentifier(eventName,
                startDateTimeString, endDateTimeString);
        vEvent.setUniqueIdentifier(uniqueIdentifier);

        if (recurTypeString.equals("weekly")) {
            vEvent.setRecurrenceRule(WEEKLY_RECURRENCE_RULE);
        } else if (recurTypeString.equals("daily")) {
            vEvent.setRecurrenceRule(DAILY_RECURRENCE_RULE);
        } else if (!recurTypeString.equals("none")) {
            throw new CommandException(INVALID_RECURRENCE_TYPE);
        }

        //validate colorNumberString is in range

        Categories colorCategory = new Categories(colorNumberString);
        ArrayList<Categories> colorCategoryList = new ArrayList<>();
        colorCategoryList.add(colorCategory);
        vEvent.setCategories(colorCategoryList);

        if (model.hasVEvent(vEvent)) {
            return new CommandResult("Will result in duplicate Event being created");
        } else {
            model.addVEvent(vEvent);
            return new CommandResult(generateSuccessMessage(vEvent), CommandResultType.SHOW_SCHEDULE);
        }
    }

    /**
     * Generates a command execution success message.
     *
     * @param vEvent that has been added.
     */
    private String generateSuccessMessage(VEvent vEvent) {
        return "Added event: " + vEvent.getSummary().getValue();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventAddCommand)) {
            return false;
        }

        // state check
        EventAddCommand e = (EventAddCommand) other;
        return eventName.equals(e.eventName);
    }
}
