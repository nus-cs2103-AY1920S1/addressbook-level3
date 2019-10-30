package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PHONES;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.model.Model;

/**
 * Lists all phones in the phone book to the user.
 */
public class ListPhoneCommand extends Command {

    //to be discussed
    public static final String COMMAND_WORD = "list-p";

    public static final String MESSAGE_SUCCESS = "Listed all current phones";


    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        requireNonNull(model);
        model.updateFilteredPhoneList(PREDICATE_SHOW_ALL_PHONES);
        return new CommandResult(MESSAGE_SUCCESS, UiChange.PHONE);
    }
}
