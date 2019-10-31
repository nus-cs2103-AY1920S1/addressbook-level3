package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.note.exceptions.InvalidUndoException;

/**
 * Terminates the program.
 */
public class UndoNoteCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_ACKNOWLEDGEMENT = "Undone last action on Note Book as requested";
    public static final String MESSAGE_UNDO_FAILED = "No commands to undo!";


    @Override
    public CommandResult execute(Model model) {
        try {
            model.undoNote();
        } catch (InvalidUndoException e) {
            return new CommandResult(MESSAGE_UNDO_FAILED);
        }
        return new CommandResult(MESSAGE_UNDO_ACKNOWLEDGEMENT);
    }

}
