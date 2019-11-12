package io.xpire.logic.commands;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;

import io.xpire.model.ListType;
import io.xpire.model.Model;
import io.xpire.model.state.FilteredState;
import io.xpire.model.state.StateManager;

//@@author febee99
/**
 * Display all items to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String COMMAND_SHORTHAND = "v";

    public static final String MESSAGE_VIEW_OPTIONS = "Only two lists (main and replenish) are available to view.";
    public static final String MESSAGE_SUCCESS = "Displayed all items in %s list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views all items in tracking or toReplenish list.\n"
            + "If key is 'replenish', items in the to-buy list will be displayed.\n"
            + "If key is 'main', items in the main list will be displayed.\n"
            + "Format: view|<key> (where key is 'main' or 'replenish')\n"
            + "Example: " + COMMAND_WORD + "|main";

    private final ListType listType;

    public ViewCommand(ListType listType) {
        this.listType = listType;
    }

    @Override
    public CommandResult execute(Model model, StateManager stateManager) {
        requireAllNonNull(model, stateManager);
        stateManager.saveState(new FilteredState(model));
        model.setCurrentList(this.listType);
        setShowInHistory(true);
        return new CommandResult(String.format(MESSAGE_SUCCESS, listType.toString()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof ViewCommand)) {
            return false;
        } else {
            ViewCommand other = (ViewCommand) obj;
            return this.listType.equals(other.listType);
        }
    }

    @Override
    public String toString() {
        return "View command";
    }
}
