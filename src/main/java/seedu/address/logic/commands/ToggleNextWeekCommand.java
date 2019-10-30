package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;

/**
 * Command to toggle schedule view to show the subsequent week.
 */
public class ToggleNextWeekCommand extends Command {

    public static final String COMMAND_WORD = "nw";
    public static final String MESSAGE_SUCCESS = "Showing subsequent weeks' schedule! You can only view schedules up "
            + "to 4 weeks ahead.";
    public static final String MESSAGE_USAGE = "To view next week's schedule, type nw!";
    public static final String MESSAGE_FAILURE = "Nothing to toggle";

    public ToggleNextWeekCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        ScheduleWindowDisplayType status = model.getState();
        if (status == ScheduleWindowDisplayType.PERSON
                || status == ScheduleWindowDisplayType.GROUP
                || status == ScheduleWindowDisplayType.NONE) {
            //model.updateScheduleWindowDisplay(new ScheduleWindowDisplay(ScheduleWindowDisplayType.NONE));
            return new CommandResult(MESSAGE_SUCCESS, false, false, false, false, false, true);
        } else {
            return new CommandResult(MESSAGE_FAILURE, false, false);
        }
    }

    @Override
    public boolean equals(Command command) {
        return command instanceof ToggleNextWeekCommand;
    }
}
