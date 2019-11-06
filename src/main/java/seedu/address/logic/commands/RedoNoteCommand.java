package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.note.exceptions.InvalidRedoException;

/**
 * Terminates the program.
 */
public class RedoNoteCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_REDO_ACKNOWLEDGEMENT = "Successfully redone command: %1$s";
    public static final String MESSAGE_REDO_FAILED = "No commands to redo!";

    @Override
    public CommandResult execute(Model model) {
        try {
            String redoneCommand = model.redoNote();
            return new CommandResult(String.format(MESSAGE_REDO_ACKNOWLEDGEMENT, redoneCommand));
        } catch (InvalidRedoException e) {
            return new CommandResult(MESSAGE_REDO_FAILED);
        }
    }

}
