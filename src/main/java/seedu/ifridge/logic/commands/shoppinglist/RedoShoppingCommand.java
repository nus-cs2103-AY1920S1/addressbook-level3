package seedu.ifridge.logic.commands.shoppinglist;

import static java.util.Objects.requireNonNull;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ReadOnlyShoppingList;

/**
 * Redo shopping list state.
 */
public class RedoShoppingCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redo shopping list";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoShoppingList()) {
            throw new CommandException("Cannot redo.");
        }

        ReadOnlyShoppingList currShoppingList = model.redoShoppingList();
        model.setShoppingList(currShoppingList);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
