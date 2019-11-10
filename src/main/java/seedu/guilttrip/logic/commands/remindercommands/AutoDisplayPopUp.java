package seedu.guilttrip.logic.commands.remindercommands;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.reminders.messages.Message;

/**
 * automatically displays message popup.
 */
public class AutoDisplayPopUp extends Command {
    private Message message;
    public AutoDisplayPopUp(Message message) {
        this.message = message;
    }

    @Override
    public CommandResult execute(final Model model, final CommandHistory history) throws CommandException {
        return new CommandResult("display popup", message);
    }
}
