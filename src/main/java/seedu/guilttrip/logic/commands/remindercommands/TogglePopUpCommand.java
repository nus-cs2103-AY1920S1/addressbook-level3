package seedu.guilttrip.logic.commands.remindercommands;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;

/**
 * Toggles pop up message of generalReminder.
 */
public class TogglePopUpCommand extends Command {

    public static final String COMMAND_WORD = "showPopUp";
    public final boolean willShowPopUp;

    public TogglePopUpCommand(boolean willShowPopUp) {
        this.willShowPopUp = willShowPopUp;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        model.getReminderSelected().togglePopUpDisplay(willShowPopUp);
        if (willShowPopUp) {
            return new CommandResult("GeneralReminder will show pop up message");
        } else {
            return new CommandResult("GeneralReminder will not show pop up message");
        }
    }
}
