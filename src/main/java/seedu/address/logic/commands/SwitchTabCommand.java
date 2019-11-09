package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;

/**
 * Command to handle tab switches.
 */
public class SwitchTabCommand extends Command {
    public static final String COMMAND_WORD = "switch-tab";
    public static final String MESSAGE_SUCCESS = "Tabs switched!";
    public static final String MESSAGE_FAILURE = "You can only switch tabs in the home page!";
    public static final String MESSAGE_USAGE = "Switch tab command does not take in any other arguments!";

    public SwitchTabCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        ScheduleWindowDisplayType state = model.getState();
        if (state == ScheduleWindowDisplayType.HOME) {
            return new CommandResultBuilder(MESSAGE_SUCCESS).setSwitchTabs().build();
        } else {
            return new CommandResultBuilder(MESSAGE_FAILURE).setSwitchTabs().build();
        }

    }

    @Override
    public boolean equals(Command command) {
        return (command instanceof SwitchTabCommand);
    }
}
