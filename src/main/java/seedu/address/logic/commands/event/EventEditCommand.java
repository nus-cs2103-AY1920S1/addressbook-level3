package seedu.address.logic.commands.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.properties.component.descriptive.Categories;
import jfxtras.icalendarfx.properties.component.recurrence.RecurrenceRule;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.EventUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.RecurrenceType;

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

        //map vEvent to event type for ease of referencing and DRY principle
        VEvent vEventObject = model.getVEvent(index);
        Event eventObject = EventUtil.vEventToEventMapper(vEventObject);

        String eventName = (!this.eventName.isBlank())
                ? this.eventName
                : eventObject.getEventName();
        String colorNumberString = (!this.colorNumberString.isBlank())
                ? EventUtil.convertNumberToColorNumber(this.colorNumberString)
                : eventObject.getColorCategory();
        LocalDateTime startDateTime = (!this.startDateTimeString.isBlank())
                ? LocalDateTime.parse(this.startDateTimeString)
                : eventObject.getStartDateTime();
        LocalDateTime endDateTime = (!this.endDateTimeString.isBlank())
                ? LocalDateTime.parse(this.endDateTimeString)
                : eventObject.getEndDateTime();
        String uniqueIdentifier = (!eventObject.getUniqueIdentifier().isBlank())
                ? EventUtil.generateUniqueIdentifier(eventName, startDateTime.toString(), endDateTime.toString())
                : eventObject.getUniqueIdentifier();
        RecurrenceRule recurrenceRule;
        if (this.recurTypeString.isBlank()) {
            recurrenceRule = vEventObject.getRecurrenceRule();
            vEventObject.setRecurrenceRule(recurrenceRule);
        } else {
            try {
                if (!recurTypeString.equalsIgnoreCase(RecurrenceType.NONE.name())) {
                    recurrenceRule = EventUtil.stringToRecurrenceRule(this.recurTypeString);
                    vEventObject.setRecurrenceRule(recurrenceRule);
                }
            } catch (IllegalValueException ex) {
                throw new CommandException(ex.toString());
            }
        }

        if (!EventUtil.validateStartEndDateTime(startDateTime, endDateTime)) {
            throw new CommandException(START_DATE_LATER_END_DATE);
        }

        vEventObject = new VEvent();
        vEventObject.setUniqueIdentifier(uniqueIdentifier);
        vEventObject.setSummary(eventName);
        vEventObject.setDateTimeStart(startDateTime);
        vEventObject.setDateTimeEnd(endDateTime);
        vEventObject.setUniqueIdentifier();

        Categories colorCategory = new Categories(colorNumberString);
        ArrayList<Categories> colorCategoryList = new ArrayList<>();
        colorCategoryList.add(colorCategory);
        vEventObject.setCategories(colorCategoryList);

        model.setVEvent(index, vEventObject);

        return new CommandResult(generateSuccessMessage(vEventObject), CommandResultType.SHOW_SCHEDULE);
    }


    /**
     * Generates a command execution success message.
     *
     * @param vEvent that has been editted.
     */
    private String generateSuccessMessage(VEvent vEvent) {
        return "Edited question: " + vEvent.getSummary().getValue();
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
