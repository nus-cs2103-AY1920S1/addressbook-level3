package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.note.exceptions.InvalidUndoException;

/**
 * Terminates the program.
 */
public class UndoNoteCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_ACKNOWLEDGEMENT = "Successfully undone command: %1$s";
    public static final String MESSAGE_UNDO_FAILED = "No commands to undo!";


    @Override
    public CommandResult execute(Model model) {
        try {
            String undoneCommand = model.undoNote();
            return new CommandResult(String.format(MESSAGE_UNDO_ACKNOWLEDGEMENT, undoneCommand));
        } catch (InvalidUndoException e) {
            return new CommandResult(MESSAGE_UNDO_FAILED);
        }
    }

}
