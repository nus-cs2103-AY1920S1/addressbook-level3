package io.xpire.logic.commands;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;
import java.util.stream.Collectors;

import io.xpire.commons.util.StringUtil;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.ListType;
import io.xpire.model.Model;
import io.xpire.model.item.ContainsKeywordsPredicate;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.state.FilteredState;
import io.xpire.model.state.StateManager;
import io.xpire.model.tag.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//@@author JermyTan
/**
 * Searches and displays all items whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";
    public static final String COMMAND_SHORTHAND = "se";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches and displays all items whose names "
            + "or tag(s) contain any of the specified keywords (case-insensitive).\n"
            + "Searching for names allows partial match while searching for tags requires exact match.\n"
            + "Format: search|<keyword>[|<other keywords>]... (keyword(s) for tags must be prefixed with a '#')\n"
            + "Example: " + COMMAND_WORD + "|apple|#fridge|banana";

    public static final String MESSAGE_ITEMS_LISTED_OVERVIEW = "%d items listed!";

    /** A predicate that tests if an item contains a set of keywords. */
    private final ContainsKeywordsPredicate predicate;

    /** The current list type. */
    private final ListType listType;

    /**
     * Public constructor for class.
     *
     * @param listType Current list type.
     * @param predicate Predicate to test the {@code Item} object.
     */
    public SearchCommand(ListType listType, ContainsKeywordsPredicate predicate) {
        this.listType = listType;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, StateManager stateManager) throws CommandException {
        requireAllNonNull(model, stateManager);
        this.requireNonEmptyCurrentList(model);

        //Saves the current copy of the list view
        ObservableList<? extends Item> previousList = FXCollections.observableArrayList(model.getCurrentList());
        stateManager.saveState(new FilteredState(model));
        //Updates the list view
        model.filterCurrentList(this.listType, this.predicate);
        //Retrieves the updated list view
        ObservableList<? extends Item> updatedList = model.getCurrentList();
        StringBuilder sb = new StringBuilder(String.format(MESSAGE_ITEMS_LISTED_OVERVIEW, updatedList.size()));

        //If the list view is empty
        if (updatedList.size() == 0) {
            Set<Tag> allTags = previousList
                    .stream()
                    .flatMap(item -> item.getTags().stream())
                    .collect(Collectors.toSet());
            Set<Name> allItemNames = previousList
                    .stream()
                    .map(Item::getName)
                    .collect(Collectors.toSet());

            //Includes any similar tags or item names found
            this.predicate.getKeywords().forEach(s -> sb.append(s.startsWith("#")
                    ? StringUtil.findSimilarItemTags(s, allTags)
                    : StringUtil.findSimilarItemNames(s, allItemNames)));
        }
        setShowInHistory(true);
        return new CommandResult(sb.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof SearchCommand)) {
            return false;
        } else {
            SearchCommand other = (SearchCommand) obj;
            return this.predicate.equals(other.predicate);
        }
    }

    @Override
    public int hashCode() {
        return this.predicate.hashCode();
    }

    @Override
    public String toString() {
        return "Search command";
    }
}
