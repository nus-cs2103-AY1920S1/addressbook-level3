package seedu.ifridge.logic.commands.grocerylist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_GROCERY_ITEMS;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.model.Model;

/**
 * Lists all grocery items in the grocery list to the user.
 */
public class ListGroceryCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all grocery items";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGroceryItemList(PREDICATE_SHOW_ALL_GROCERY_ITEMS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
