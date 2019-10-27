package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplay;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;

public class HomeCommand extends Command {
    public static final String COMMAND_WORD = "home";
    public static final String MESSAGE_SUCCESS = "Returning back to home page.";
    public static final String MESSAGE_USAGE = "Home command does not require any flags! Type home will do!";

    public HomeCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.updateDetailWindowDisplay(new ScheduleWindowDisplay(ScheduleWindowDisplayType.HOME));
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, false,
                false, false, true);
    }

    @Override
    public boolean equals(Command command) {
        return command instanceof HomeCommand;
    }
}
