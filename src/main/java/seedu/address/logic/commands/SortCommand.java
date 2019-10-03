package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import seedu.address.model.Model;
import seedu.address.model.item.sort.MethodOfSorting;

/**
 * Sorts the items in the displayed list.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all items according to the specified key.\n"
            + "If key is 'name', items will be sorted according to their names in alphabetical order.\n"
            + "If key is 'date', items will be sorted according to their expiry dates in chronological order.\n"
            + "Format: sort|<key> (where key is 'name' or 'date')\n"
            + "Example: " + COMMAND_WORD + "|name";

    public static final String MESSAGE_SUCCESS = "Sorted all items";

    private final MethodOfSorting method;

    public SortCommand(MethodOfSorting method) {
        this.method = method;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortItemList(this.method);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(MESSAGE_SUCCESS + " by " + method.getValue());
    }

}
