package seedu.ifridge.logic.commands.shoppinglist;

import static java.util.Objects.requireNonNull;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ReadOnlyShoppingList;

/**
 * Undo shopping list state.
 */
public class UndoShoppingCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo shopping list";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoShoppingList()) {
            throw new CommandException("Cannot undo.");
        }

        ReadOnlyShoppingList currShoppingList = model.undoShoppingList();
        model.setShoppingList(currShoppingList);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
