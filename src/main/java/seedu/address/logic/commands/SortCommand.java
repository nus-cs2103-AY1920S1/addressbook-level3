package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.item.sort.MethodOfSorting;

/**
 * Sorts all items in the expiry date tracker and displays them for the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all items according the the method specified"
            + "and displays them as a list with index numbers.\n"
            + "Parameters: METHOD\n"
            + "Example: " + COMMAND_WORD + "|name";
    public static final String MESSAGE_SUCCESS = "Sorted all items";
    private final MethodOfSorting method;

    public SortCommand(MethodOfSorting method) {
        this.method = method;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortItemList(method);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(String.format(
                Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, model.getSortedItemList().size()));
    }

}
