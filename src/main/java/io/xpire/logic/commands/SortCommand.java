package io.xpire.logic.commands;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;

import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.item.sort.XpireMethodOfSorting;
import io.xpire.model.state.FilteredState;
import io.xpire.model.state.StateManager;

//@@author febee99
/**
 * Sorts the items in the displayed list.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_SHORTHAND = "so";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all items according to the specified key.\n"
            + "If key is 'name', items will be sorted according to their names in alphabetical order.\n"
            + "If key is 'date', items will be sorted according to their expiry dates in chronological order.\n"
            + "Format: sort|<key> (where key is 'name' or 'date')\n"
            + "Example: " + COMMAND_WORD + "|name";

    public static final String MESSAGE_SUCCESS = "Sorted all items";

    private final XpireMethodOfSorting method;


    public SortCommand(XpireMethodOfSorting method) {
        this.method = method;
    }

    @Override
    public CommandResult execute(Model model, StateManager stateManager)throws CommandException {
        requireAllNonNull(model, stateManager);
        this.requireNonEmptyCurrentList(model);
        stateManager.saveState(new FilteredState(model));
        model.sortXpire(this.method);
        //model.updateFilteredItemList(Model.PREDICATE_SORT_ALL_ITEMS);
        setShowInHistory(true);
        return new CommandResult(MESSAGE_SUCCESS + " by " + method);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof SortCommand)) {
            return false;
        } else {
            SortCommand other = (SortCommand) obj;
            return this.method.equals(other.method);
        }
    }

    @Override
    public int hashCode() {
        return this.method.hashCode();
    }

    @Override
    public String toString() {
        return "Sort command";
    }
}
