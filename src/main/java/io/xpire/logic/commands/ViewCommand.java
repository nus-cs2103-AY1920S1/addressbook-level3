package io.xpire.logic.commands;
import static java.util.Objects.requireNonNull;

import io.xpire.model.Model;
import io.xpire.model.item.ListToView;

/**
 * Display all items to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

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
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String output = String.format(MESSAGE_SUCCESS, "the");
        if (list != null) {
            output = String.format(MESSAGE_SUCCESS, list);
            model.setCurrentFilteredItemList(list);
        }
        model.updateFilteredItemList(Model.PREDICATE_SHOW_ALL_ITEMS);
        return new CommandResult(output);
    }

    public ListToView getList() {
        return this.list;
    }
}
