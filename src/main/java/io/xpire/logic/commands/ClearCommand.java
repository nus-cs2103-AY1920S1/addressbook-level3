package io.xpire.logic.commands;

import static java.util.Objects.requireNonNull;

import io.xpire.model.Model;
import io.xpire.model.ReplenishList;
import io.xpire.model.StackManager;
import io.xpire.model.Xpire;
import io.xpire.model.state.State;

/**
 * Clears all items in the list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_SHORTHAND = "cl";

    public static final String MESSAGE_SUCCESS = "Item list has been cleared!";
    private String list;

    public ClearCommand(String list) {
        this.list = list;
    }

    //@@author febee99
    @Override
    public CommandResult execute(Model model, StackManager stackManager) {
        requireNonNull(model);
        stackManager.saveState(new State(model));
        switch (list) {
        case "main" :
            model.setXpire(new Xpire());
            break;
        case "replenish":
            model.setReplenishList(new ReplenishList());
            break;
        default:
            break;
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public String toString() {
        return "Clear Command";
    }

    public String getList() {
        return this.list;
    }
}
