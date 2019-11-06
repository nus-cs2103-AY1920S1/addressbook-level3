package io.xpire.logic.commands;

import static java.util.Objects.requireNonNull;

import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.item.XpireItem;
import io.xpire.model.state.ModifiedState;
import io.xpire.model.state.StateManager;

/**
 * Adds an xpireItem to the list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_SHORTHAND = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item to the tracking list.\n"
            + "Format: add|<item name>|<expiry date>[|<quantity>]\n"
            + "Example: " + COMMAND_WORD + "|Strawberry|11/12/1999|2";

    public static final String MESSAGE_SUCCESS = "New item added to tracking list: %s";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists";

    private final XpireItem toAdd;
    private String result = "";

    /**
     * Creates an AddCommand to add the specified {@code XpireItem}
     */
    public AddCommand(XpireItem xpireItem) {
        requireNonNull(xpireItem);
        this.toAdd = xpireItem;
    }

    /**
     * Executes {@code AddCommand}.
     *
     * @param model {@code Model} which the command should operate on.
     * @param stateManager
     * @return success message from {@code CommandResult} if xpireItem is successfully added.
     * @throws CommandException if xpireItem added is a duplicate.
     */
    @Override
    public CommandResult execute(Model model, StateManager stateManager) throws CommandException {
        requireNonNull(model);
        stateManager.saveState(new ModifiedState(model));

        if (model.hasItem(this.toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        }
        model.addItem(this.toAdd);
        this.result = String.format(MESSAGE_SUCCESS, this.toAdd);
        setShowInHistory(true);
        return new CommandResult(result);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof AddCommand)) {
            return false;
        } else {
            AddCommand other = (AddCommand) obj;
            return this.toAdd.equals(other.toAdd);
        }
    }

    @Override
    public int hashCode() {
        return this.toAdd.hashCode();
    }

    @Override
    public String toString() {
        return "the following Add command:\n" + result;
    }
}
