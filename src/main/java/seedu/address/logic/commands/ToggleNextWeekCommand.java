package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplay;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;

/**
 * Command to toggle schedule view to show the subsequent week.
 */
public class ToggleNextWeekCommand extends Command {

    public static final String COMMAND_WORD = "nw";
    public static final String MESSAGE_SUCCESS = "Showing subsequent weeks' schedule! You can only view schedules up "
            + "to 4 weeks ahead.";
    public static final String MESSAGE_USAGE = "To view next week's schedule, type nw!";

    public ToggleNextWeekCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateScheduleWindowDisplay(new ScheduleWindowDisplay(ScheduleWindowDisplayType.NONE));
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, false, false, true);
    }

    @Override
    public boolean equals(Command command) {
        return command instanceof ToggleNextWeekCommand;
    }
}
