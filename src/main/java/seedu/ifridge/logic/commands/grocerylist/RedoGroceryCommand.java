package seedu.ifridge.logic.commands.grocerylist;

import static java.util.Objects.requireNonNull;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ReadOnlyGroceryList;
import seedu.ifridge.model.ReadOnlyWasteList;

/**
 * Redo grocery list state.
 */
public class RedoGroceryCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redo grocery list";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoGroceryList()) {
            throw new CommandException("Cannot redo.");
        }

        ReadOnlyGroceryList curr = model.redoGroceryList();
        model.setGroceryList(curr);
        ReadOnlyWasteList currWasteList = model.redoWasteList();
        model.setWasteList(currWasteList);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
