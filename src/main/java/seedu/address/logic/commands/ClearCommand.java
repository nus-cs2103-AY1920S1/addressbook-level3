package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ItemModel;
import seedu.address.model.ItemStorage;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Item list has been cleared!";
    private ItemStorage beforeClear;


    @Override
    public CommandResult execute(ItemModel model) {
        requireNonNull(model);
        beforeClear = model.getItemStorage().deepCopy();
        model.clear();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void reverse(ItemModel model) throws CommandException {
        model.setItemStorage(beforeClear);
    }
}
