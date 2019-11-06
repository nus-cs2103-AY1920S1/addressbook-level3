package io.xpire.logic.commands;

import static java.util.Objects.requireNonNull;

import io.xpire.commons.core.Messages;
import io.xpire.commons.util.StringUtil;
import io.xpire.model.Model;
import io.xpire.model.item.ContainsKeywordsPredicate;
import io.xpire.model.state.FilteredState;
import io.xpire.model.state.StateManager;

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

    public SearchCommand(ContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, StateManager stateManager) {
        requireNonNull(model);
        stateManager.saveState(new FilteredState(model));
        model.updateFilteredItemList(this.predicate);
        StringBuilder sb = new StringBuilder(String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW,
                model.getCurrentFilteredItemList().size()));
        //@@author febee99
        if (model.getCurrentFilteredItemList().size() == 0) {
            predicate.getKeywords().forEach(s -> {
                if (s.startsWith("#")) {
                    sb.append(StringUtil.findSimilarItemTags(s, model.getAllItemTags()));
                } else {
                    sb.append(StringUtil.findSimilarItemNames(s, model.getAllItemNames()));
                }
            });
        }
        //@@author
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
