package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplay;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;

/**
 * Command to show popup of the locations suggested.
 */
public class PopupCommand extends Command {

    public static final String COMMAND_WORD = "popup";
    public static final String MESSAGE_SUCCESS = "Showing locations";

    public PopupCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        //Do nothing to model
        model.updateDetailWindowDisplay(new ScheduleWindowDisplay(ScheduleWindowDisplayType.NONE));
        return new CommandResult(MESSAGE_SUCCESS, false, false , false , false, true);
    }

    @Override
    public boolean equals(Command command) {
        return (command instanceof PopupCommand);
    }
}
