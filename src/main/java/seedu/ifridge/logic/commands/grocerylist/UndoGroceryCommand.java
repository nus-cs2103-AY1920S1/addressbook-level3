package seedu.ifridge.logic.commands.grocerylist;

import static java.util.Objects.requireNonNull;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ReadOnlyGroceryList;
import seedu.ifridge.model.ReadOnlyWasteList;

/**
 * Undo grocery list state.
 */
public class UndoGroceryCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo grocery list";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoGroceryList()) {
            throw new CommandException("Cannot undo.");
        }

        ReadOnlyGroceryList currGroceryList = model.undoGroceryList();
        model.setGroceryList(currGroceryList);
        ReadOnlyWasteList currWasteList = model.undoWasteList();
        model.setWasteList(currWasteList);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
