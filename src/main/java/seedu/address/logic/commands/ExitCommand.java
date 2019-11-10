package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Seller Manager as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, UiChange.EXIT);
    }

}
