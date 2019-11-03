package seedu.address.logic.commands.event;

import static seedu.address.commons.util.EventUtil.convertNumberToColorCategoryList;
import static seedu.address.commons.util.EventUtil.vEventToString;
import static seedu.address.commons.util.EventUtil.validateStartEndDateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.properties.component.descriptive.Categories;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.EventUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Edits the events details in the events record.
 */
public class EventEditCommand extends EventCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " [index]: Edits a event\n"
        + "Parameters(All are optional):\n"
        + "eventName/ [EVENTNAME]\n"
        + "startDateTime/ [STARTDATETIME]\n"
        + "endDateTime/ [ENDDATETIME]\n"
        + "recur/ [DAILY/WEEKLY/NONE]\n"
        + "color/ [0 - 23]\n"
        + "Example: event 6 eventName/cs2100 lecture startDateTime/2019-10-21T14:00 "
        + "endDateTime/2019-10-21T15:00 recur/none color/1";
    private static final String START_DATE_LATER_END_DATE = "start date time provided for the event is later "
            + "than or equal to end date time provided";
    private static final String BAD_DATE_FORMAT = "Invalid DateTime Format. "
            + "Please follow the format: yyyy-MM-ddTHH:mm, "
            + "e.g. 28 October 2019, 2PM should be input as 2019-10-28T14:00";
    private static final String INVALID_RECURRENCE_TYPE = "Invalid Recurrence Type";
    private static final String NO_FIELDS_CHANGED = "At least one field to edit must be provided.";

    private final Index index;
    private final String eventName;
    private final String startDateTimeString;
    private final String endDateTimeString;
    private final String recurTypeString;
    private final String colorNumberString;

    /**
     * Creates a EventEditCommand object.
     *
     * @param index  of question in the list.
     * @param fields to edit.
     */
    public EventEditCommand(Index index, HashMap<String, String> fields) {
        this.index = index;
        this.eventName = fields.get("eventName");
        this.startDateTimeString = fields.get("startDateTime");
        this.endDateTimeString = fields.get("endDateTime");
        this.recurTypeString = fields.get("recurType");
        this.colorNumberString = fields.get("colorString");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (index.getZeroBased() >= model.getVEventList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        if (!isAnyFieldEdited()) {
            throw new CommandException(NO_FIELDS_CHANGED);
        }

        //map vEvent to event type for ease of referencing and DRY principle
        VEvent vEventObject = model.getVEvent(index);

        //event name
        if (!this.eventName.isBlank()) {
            //changes made to event name
            vEventObject.setSummary(this.eventName);
        }

        //color number
        if (!this.colorNumberString.isBlank()) {
            try {
                ArrayList<Categories> categoriesToBeSet = convertNumberToColorCategoryList(colorNumberString);
                vEventObject.setCategories(categoriesToBeSet);
            } catch (IllegalValueException ex) {
                throw new CommandException(ex.getMessage(), ex);
            }
        }

        //start and end date time
        LocalDateTime startDateTime = LocalDateTime.from(vEventObject.getDateTimeStart().getValue());
        LocalDateTime endDateTime = LocalDateTime.from(vEventObject.getDateTimeEnd().getValue());
        if (!this.startDateTimeString.isBlank()) {
            try {
                startDateTime = LocalDateTime.parse(startDateTimeString);
            } catch (DateTimeParseException dtpEx) {
                throw new CommandException(BAD_DATE_FORMAT, dtpEx);
            }
        }
        if (!this.endDateTimeString.isBlank()) {
            try {
                endDateTime = LocalDateTime.parse(endDateTimeString);
            } catch (DateTimeParseException dtpEx) {
                throw new CommandException(BAD_DATE_FORMAT, dtpEx);
            }
        }
        //validate start and end date time
        if (!validateStartEndDateTime(startDateTime, endDateTime)) {
            throw new CommandException(START_DATE_LATER_END_DATE);
        }
        //set start and end date time
        vEventObject.setDateTimeEnd(endDateTime);
        vEventObject.setDateTimeStart(startDateTime);

        //Recurrence Rule
        if (!this.recurTypeString.isBlank()) {
            if (!EventUtil.validateRecurTypeString(recurTypeString)) {
                throw new CommandException(INVALID_RECURRENCE_TYPE);
            }
            try {
                vEventObject.setRecurrenceRule(EventUtil.stringToRecurrenceRule(recurTypeString));

            } catch (IllegalValueException ex) {
                throw new CommandException(ex.getMessage(), ex);
            }
        }

        model.setVEvent(index, vEventObject);

        return new CommandResult(generateSuccessMessage(vEventObject), CommandResultType.SHOW_SCHEDULE);
    }

    /**
     * Returns true if at least one field is edited.
     */
    private boolean isAnyFieldEdited() {
        return !eventName.isBlank() || !startDateTimeString.isBlank() || !endDateTimeString.isBlank()
                || !recurTypeString.isBlank() || !colorNumberString.isBlank();
    }

    /**
     * Generates a command execution success message.
     *
     * @param vEvent that has been editted.
     */
    private String generateSuccessMessage(VEvent vEvent) {
        return "Edited event: \n" + vEventToString(vEvent);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventEditCommand)) {
            return false;
        }

        //state check
        EventEditCommand e = (EventEditCommand) other;
        return index.equals(e.index)
            && eventName.equals(e.eventName)
            && startDateTimeString.equals(e.startDateTimeString)
            && endDateTimeString.equals(e.endDateTimeString)
            && recurTypeString.equals(e.recurTypeString)
            && colorNumberString.equals(e.colorNumberString);
    }
}
