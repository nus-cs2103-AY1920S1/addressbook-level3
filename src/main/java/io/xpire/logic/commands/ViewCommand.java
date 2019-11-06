package io.xpire.logic.commands;

import static java.util.Objects.requireNonNull;

import io.xpire.model.Model;
import io.xpire.model.item.ListToView;
import io.xpire.model.state.FilteredState;
import io.xpire.model.state.StateManager;

//@@author febee99
/**
 * Display all items to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String COMMAND_SHORTHAND = "v";

    public static final String MESSAGE_SUCCESS = "Displayed all items in %s list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views all items in tracking or toReplenish list.\n"
            + "If key is 'replenish', items in the toReplenish list will be displayed.\n"
            + "If key is 'main', items in the main list will be displayed.\n"
            + "Format: view|<key> (where key is 'main' or 'replenish')\n"
            + "Example: " + COMMAND_WORD + "|main";

    private ListToView list = null;

    public ViewCommand() {
    }

    public ViewCommand(ListToView list) {
        this.list = list;
    }

    @Override
    public CommandResult execute(Model model, StateManager stateManager) {
        requireNonNull(model);
        stateManager.saveState(new FilteredState(model));
        String output = String.format(MESSAGE_SUCCESS, "the");
        if (list != null) {
            output = String.format(MESSAGE_SUCCESS, list);
            model.setCurrentFilteredItemList(list);
        }
        model.updateFilteredItemList(Model.PREDICATE_SHOW_ALL_ITEMS);
        setShowInHistory(true);
        return new CommandResult(output);
    }

    public ListToView getList() {
        return this.list;
    }

    @Override
    public String toString() {
        return "View command";
    }
}
