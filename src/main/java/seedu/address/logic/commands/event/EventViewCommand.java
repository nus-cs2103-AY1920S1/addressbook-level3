package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

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
    public static final String MESSAGE_VIEW_SUCCESS = "Showing your schedule in %s format with reference date %s";

    private LocalDateTime targetViewDateTime;
    private EventScheduleViewMode desiredViewMode;

    public EventViewCommand() {

    }

    public void setDesiredViewMode(EventScheduleViewMode eventScheduleViewMode) {
        requireNonNull(eventScheduleViewMode);

        this.desiredViewMode = eventScheduleViewMode;
    }

    public void setTargetViewDate(LocalDateTime targetViewDateTime) {
        requireNonNull(targetViewDateTime);

        this.targetViewDateTime = targetViewDateTime;
    }

    /**
     * Executes this command which returns a commandResult for UI to act on
     * @param model {@code Model} which the command should operate on.
     * @return returns a commandResult of type SHOW_SCHEDULE
     * @throws CommandException for invalid view modes or invalid date format
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (desiredViewMode != null) {
            // user wants to change the viewMode
            model.setEventScheduleViewMode(desiredViewMode);
        }

        if (targetViewDateTime != null) {
            // user wants to change the reference date
            model.setEventScheduleTargetDateTime(targetViewDateTime);
        }

        return new CommandResult(generateSuccessMessage(model.getEventScheduleViewMode(),
                model.getEventScheduleTargetDateTime()), CommandResultType.SHOW_SCHEDULE);
    }

    /**
     * Generates the success message viewing event schedule
     * @param eventScheduleViewMode the type of view mode the schedule is in
     * @param targetViewDate the target date the schedule is referencing
     * @return a success message string to be shown to the user
     */
    private String generateSuccessMessage(EventScheduleViewMode eventScheduleViewMode, LocalDateTime targetViewDate) {
        return String.format(MESSAGE_VIEW_SUCCESS, eventScheduleViewMode.name().toLowerCase(),
                targetViewDate.toLocalDate().toString());
    }
}


