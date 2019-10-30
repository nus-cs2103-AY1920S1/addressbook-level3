package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplay;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;

/**
 * Command to handle scrolling events using CLI.
 */
public class ScrollCommand extends Command {

    public static final String COMMAND_WORD = "n";
    public static final String MESSAGE_SUCCESS = "Showing next view of schedule";

    public ScrollCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateScheduleWindowDisplay(new ScheduleWindowDisplay(ScheduleWindowDisplayType.NONE));
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, true);
    }

    @Override
    public boolean equals(Command command) {
        return (command instanceof ScrollCommand);
    }
}
