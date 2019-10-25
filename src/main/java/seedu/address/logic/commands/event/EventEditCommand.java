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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.question.Question;

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
        + "Example: event 6 edit eventName/cs2100 lecture startDateTime/2019-10-21T14:00 "
        + "endDateTime/2019-10-21T15:00 recur/none color/1";

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
        this.recurTypeString = fields.get("recur");
        this.colorNumberString = fields.get("color");
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
                ? this.eventName
                : eventObject.getColorCategory();
        LocalDateTime startDateTime = (!this.startDateTimeString.isBlank())
                ? LocalDateTime.parse(this.startDateTimeString)
                : eventObject.getStartDateTime();
        LocalDateTime endDateTime = (!this.endDateTimeString.isBlank())
                ? LocalDateTime.parse(this.endDateTimeString)
                : eventObject.getEndDateTime();
        RecurrenceRule recurrenceRule;
        if (this.recurTypeString.isBlank()) {
            recurrenceRule = vEventObject.getRecurrenceRule();
        } else {
            try {
                recurrenceRule = EventUtil.stringToRecurrenceRule(this.recurTypeString);
            } catch (IllegalValueException ex) {
                throw new CommandException(ex.toString());
            }
        }

        vEventObject = new VEvent();
        vEventObject.setSummary(eventName);
        vEventObject.setDateTimeStart(startDateTime);
        vEventObject.setDateTimeEnd(endDateTime);
        vEventObject.setUniqueIdentifier(EventAddCommand.UNIQUE_IDENTIFIER);
        vEventObject.setRecurrenceRule(recurrenceRule);

        Categories colorCategory = new Categories(colorNumberString);
        ArrayList<Categories> colorCategoryList = new ArrayList<>();
        colorCategoryList.add(colorCategory);
        vEventObject.setCategories(colorCategoryList);

        model.setVEvent(index, vEventObject);

        return new CommandResult("stubbed event edit success message");
    }



    /**
     * Generates a command execution success message.
     *
     * @param question that has been added.
     */
    private String generateSuccessMessage(Question question) {
        return "Edited question: " + question;
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
