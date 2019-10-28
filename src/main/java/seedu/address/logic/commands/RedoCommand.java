package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ElisaCommandHistory;
import seedu.address.model.ItemModel;

public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    private ElisaCommandHistory elisaCommandHistory;

    public RedoCommand(ElisaCommandHistory elisaCommandHistory) {
        this.elisaCommandHistory = elisaCommandHistory;
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        if (elisaCommandHistory.sizeRedo() == 0) {
            throw new CommandException(Messages.MESSAGE_NOTHING_TO_REDO);
        } else {
            UndoableCommand lastDone = elisaCommandHistory.popRedo();
            lastDone.execute(model);
            return new CommandResult("Redo [" + lastDone.getCommandWord() + "] command successful!");
        }
    }
}
