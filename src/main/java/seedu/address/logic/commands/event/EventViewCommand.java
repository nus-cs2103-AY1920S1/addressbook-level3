package seedu.address.logic.commands.event;

import static seedu.address.commons.util.EventUtil.dateToLocalDateTimeFormatter;
import static seedu.address.commons.util.EventUtil.stringToEventScheduleViewMode;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.EventScheduleViewMode;

/**
 * Represents a command to view events in the schedule pane
 */
public class EventViewCommand extends EventCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " [view]: view your events\n"
            + "Parameters(All are optional):\n"
            + "scheduleMode/ [weekly / daily]\n"
            + "targetDate/ [yyyy-mm-dd]\n"
            + "Example: event view scheduleMode/weekly targetDate/2019-11-06";
    private static final String GENERAL_MESSAGE_SUCCESS = "Showing your schedule";

    private final String viewMode;
    private final String viewDateString;

    public EventViewCommand(HashMap<String, String> fields) {
        this.viewMode = fields.get("viewMode");
        this.viewDateString = fields.get("viewDate");
    }

    /**
     * Executes this command which returns a commandResult for UI to act on
     * @param model {@code Model} which the command should operate on.
     * @return returns a commandResult of type SHOW_SCHEDULE
     * @throws CommandException for invalid view modes or invalid date format
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!this.viewMode.isBlank()) {
            // user wants to change the viewMode
            try {
                EventScheduleViewMode desiredViewMode = stringToEventScheduleViewMode(viewMode);
                model.setEventScheduleViewMode(desiredViewMode);
            } catch (IllegalValueException ex) {
                throw new CommandException("Invalid view mode entered", ex);
            }
        }

        if (!this.viewDateString.isBlank()) {
            // user wants to change the reference date
            try {
                LocalDateTime targetDateTime = dateToLocalDateTimeFormatter(viewDateString);
                model.setEventScheduleTargetDateTime(targetDateTime);
            } catch (DateTimeParseException ex) {
                throw new CommandException("Invalid date format entered", ex);
            }
        }

        return new CommandResult(generateSuccessMessage(model.getEventScheduleViewMode(),
                model.getEventScheduleTargetDateTime()), CommandResultType.SHOW_SCHEDULE);
    }

    private String generateSuccessMessage(EventScheduleViewMode eventScheduleViewMode, LocalDateTime targetViewDate) {
        StringBuilder sb = new StringBuilder(GENERAL_MESSAGE_SUCCESS);
        sb.append(String.format(" in %s format", eventScheduleViewMode.name().toLowerCase()));
        sb.append(String.format(" with reference date: %s", targetViewDate.toLocalDate().toString()));
        return sb.toString();
    }
}


