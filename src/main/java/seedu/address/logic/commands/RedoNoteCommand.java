package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.note.exceptions.InvalidRedoException;

/**
 * Terminates the program.
 */
public class RedoNoteCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_REDO_ACKNOWLEDGEMENT = "Redone last action on Note Book as requested";
    public static final String MESSAGE_REDO_FAILED = "No commands to redo!";

    @Override
    public CommandResult execute(Model model) {
        try {
            model.redoNote();
        } catch (InvalidRedoException e) {
            return new CommandResult(MESSAGE_REDO_FAILED);
        }
        return new CommandResult(MESSAGE_REDO_ACKNOWLEDGEMENT);
    }

}
