package io.xpire.logic.commands;

import static java.util.Objects.requireNonNull;

import io.xpire.model.Model;
import io.xpire.model.ReplenishList;
import io.xpire.model.Xpire;

/**
 * Clears all items in the list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Item list has been cleared!";
    private String list;

    public ClearCommand(String list) {
        this.list = list;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
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

    public String getList() {
        return this.list;
    }
}
