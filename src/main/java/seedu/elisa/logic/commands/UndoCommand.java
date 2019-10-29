package seedu.elisa.logic.commands;

import seedu.elisa.commons.core.Messages;
import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ElisaCommandHistory;
import seedu.elisa.model.ItemModel;

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
        if (elisaCommandHistory.sizeUndo() == 0) {
            throw new CommandException(Messages.MESSAGE_NOTHING_TO_UNDO);
        } else {
            UndoableCommand lastDone = elisaCommandHistory.popUndo();
            lastDone.reverse(model);
            return new CommandResult("Undo [" + lastDone.getCommandWord() + "] command successful!"
                    + " Try to do it right this time..");
        }
    }

}
