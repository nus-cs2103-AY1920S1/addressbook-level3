package seedu.ifridge.logic.commands.grocerylist;

import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_SORT;

import java.text.ParseException;
import java.util.Comparator;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.food.GroceryItem;

/**
 * Changes default sort method settings.
 */
public class SortGroceryCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort based on expiry date or alphabetical.\n"
            + "Parameters: " + PREFIX_SORT + "TYPE \n"
            + "Example: glist " + COMMAND_WORD + " " + PREFIX_SORT + "expiry";

    public static final String MESSAGE_SORT_SUCCESS = "List shown is sorted by: %1$s";

    public static final String MESSAGE_UNKNOWN_SORT_TYPE = "Unknown sort type.\n" + MESSAGE_USAGE;

    private String sortBy;

    public SortGroceryCommand(String sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Comparator<GroceryItem> sortComparator = getComparator(sortBy);

        ObservableList<GroceryItem> internalList = model.getGroceryList().getGroceryList();
        SortedList<GroceryItem> sortedList = internalList.sorted(sortComparator);
        GroceryList groceryList = ((GroceryList) model.getGroceryList());
        groceryList.setGroceryList(sortedList);

        model.commitGroceryList();
        model.commitWasteList();
        return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, sortBy));
    }

    /**
     * Creates a comparator that would compare based on the {@code String} inputted.
     * @param sortBy the String that would determine the type of comparator to be returned.
     * @return Returns a comparator to be used for sorting.
     * @throws CommandException If sorting type is unknown.
     */
    public Comparator<GroceryItem> getComparator(String sortBy) throws CommandException {
        switch (sortBy) {
        case "expiry":
            return getComparatorByExpiry();
        case "alphabetical":
            return getComparatorByAlphabetical();
        default:
            throw new CommandException(MESSAGE_UNKNOWN_SORT_TYPE);
        }
    }

    /**
     * Returns a comparator that compares grocery items based on expiry date.
     * @return Returns a comparator that compares grocery items based on expiry date.
     */
    public Comparator<GroceryItem> getComparatorByExpiry() {
        return Comparator.comparing(groceryItem -> {
            try {
                return groceryItem.getExpiryDate().toDateFormat();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    /**
     * Returns a comparator that compares grocery items based on name's alphabetical order.
     * @return Returns a comparator that compares grocery items based on name's alphabetical order.
     */
    public Comparator<GroceryItem> getComparatorByAlphabetical() {
        return Comparator.comparing(groceryItem -> groceryItem.getName().toString().toLowerCase());
    }
}
