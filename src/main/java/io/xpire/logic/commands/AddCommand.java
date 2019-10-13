package io.xpire.logic.commands;

import static java.util.Objects.requireNonNull;

import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.item.Item;

/**
 * Adds an item to the list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item to the list.\n"
            + "Format: add|<item name>|<expiry date>|[<quantity>]\n"
            + "Example: " + COMMAND_WORD + "|Strawberry|11/12/1999|2";

    public static final String MESSAGE_SUCCESS = "New item added: %s";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists";

    private final Item toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Item}
     */
    public AddCommand(Item item) {
        requireNonNull(item);
        this.toAdd = item;
    }

    /**
     * Executes {@code AddCommand}.
     *
     * @param model {@code Model} which the command should operate on.
     * @return success message from {@code CommandResult} if item is successfully added.
     * @throws CommandException if item added is a duplicate.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasItem(this.toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        }

        model.addItem(this.toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.toAdd));
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
}
