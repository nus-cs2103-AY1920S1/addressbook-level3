package io.xpire.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.stream.Collectors;

import io.xpire.commons.core.Messages;
import io.xpire.commons.util.StringUtil;
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

    private final ContainsKeywordsPredicate predicate;

    private final ListType listType;

    public SearchCommand(ListType listType, ContainsKeywordsPredicate predicate) {
        this.listType = listType;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, StateManager stateManager) {
        requireNonNull(model);
        stateManager.saveState(new FilteredState(model));
        // clone the previous list
        ObservableList<? extends Item> prevList = FXCollections.observableArrayList(model.getCurrentList());
        model.filterCurrentList(this.listType, this.predicate);
        ObservableList<? extends Item> currentList = model.getCurrentList();
        StringBuilder sb = new StringBuilder(String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW, currentList.size()));
        model.filterCurrentList(this.listType, this.predicate);
        if (model.getCurrentList().size() == 0) {
            Set<Tag> allTags = prevList
                    .stream()
                    .flatMap(item -> item.getTags().stream())
                    .collect(Collectors.toSet());
            Set<Name> allItemNames = prevList
                    .stream()
                    .map(Item::getName)
                    .collect(Collectors.toSet());

            predicate.getKeywords().forEach(s -> sb.append(s.startsWith("#")
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
