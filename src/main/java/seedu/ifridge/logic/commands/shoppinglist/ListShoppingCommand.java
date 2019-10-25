package seedu.ifridge.logic.commands.shoppinglist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_SHOPPING_ITEMS;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListShoppingCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all food items";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredShoppingList(PREDICATE_SHOW_ALL_SHOPPING_ITEMS);
        CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS);
        commandResult.setShoppingListCommand();
        return commandResult;
    }
}
