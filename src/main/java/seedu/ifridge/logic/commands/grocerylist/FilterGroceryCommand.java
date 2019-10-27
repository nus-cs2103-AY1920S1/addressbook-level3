package seedu.ifridge.logic.commands.grocerylist;

import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.function.Predicate;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.tag.Tag;

/**
 * Changes default sort method settings.
 */
public class FilterGroceryCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filter based on tags provided\n"
            + "Parameters: " + PREFIX_TAG + "TAG\n"
            + "Example: glist " + COMMAND_WORD + " " + PREFIX_TAG + "healthy";

    public static final String MESSAGE_FILTER_SUCCESS = "List shown is filtered by: %1$s";

    public static final String MESSAGE_FILTER_ERROR = "Unknown filter command.\n" + MESSAGE_USAGE;

    private Set<Tag> filterTagList;

    public FilterGroceryCommand(Set<Tag> filterTagList) {
        this.filterTagList = filterTagList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Predicate<GroceryItem> filter = groceryItem -> filterTagList.stream()
                .allMatch(x -> groceryItem.getTags().stream()
                        .anyMatch(y -> y.toString().equalsIgnoreCase(x.toString())));
        model.updateFilteredGroceryItemList(filter);
        return new CommandResult(String.format(MESSAGE_FILTER_SUCCESS, filterTagList.toString()));
    }
}
