package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ElisaStateHistory;
import seedu.address.model.ItemModel;

/**
 * Undoes last entered command
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    private ElisaStateHistory elisaStateHistory;

    public UndoCommand(ElisaStateHistory elisaStateHistory) {
        this.elisaStateHistory = elisaStateHistory;
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        if (elisaStateHistory.size() <= 1) {
            throw new CommandException(Messages.MESSAGE_NOTHING_TO_UNDO);
        } else {
            elisaStateHistory.popCommand();
            return new CommandResult("Undo successful!", true);
        }
    }
}
