package seedu.elisa.logic.commands;


import seedu.elisa.commons.core.Messages;
import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ElisaCommandHistory;
import seedu.elisa.model.ItemModel;

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
