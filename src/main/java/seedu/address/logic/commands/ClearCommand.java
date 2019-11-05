package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "SML data has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        requireNonNull(model);
        // do something here
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
