package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ElisaCommandHistory;
import seedu.address.model.ItemModel;

/**
 * Undoes last entered command
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    private ElisaCommandHistory elisaCommandHistory;

    public UndoCommand(ElisaCommandHistory elisaCommandHistory) {
        this.elisaCommandHistory = elisaCommandHistory;
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        if (elisaCommandHistory.size() <= 0) {
            throw new CommandException(Messages.MESSAGE_NOTHING_TO_UNDO);
        } else {
            Command lastDone = elisaCommandHistory.popCommand();
            lastDone.reverse(model);
            return new CommandResult("Undo successful!", true);
        }
    }

    @Override
    public void reverse(ItemModel model) throws CommandException { }
}
