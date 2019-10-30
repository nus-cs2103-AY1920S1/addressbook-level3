package seedu.address.logic.commands.clearcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.phone.Phone;

/**
 * Clears the phone book.
 */
public class ClearPhoneCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear-p";
    public static final String MESSAGE_SUCCESS = "Phone book has been cleared!";


    @Override
    public CommandResult executeUndoableCommand(Model model, CommandHistory commandHistory,
                                                UndoRedoStack undoRedoStack) {
        requireNonNull(model);
        model.setPhoneBook(new DataBook<Phone>());
        return new CommandResult(MESSAGE_SUCCESS, UiChange.PHONE);
    }
}
