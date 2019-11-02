package seedu.elisa.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;
import seedu.elisa.model.ItemStorage;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Item list has been cleared! (About time, isn't it?)";
    private ItemStorage beforeClear;


    @Override
    public CommandResult execute(ItemModel model) {
        requireNonNull(model);
        beforeClear = model.getItemStorage().deepCopy();
        model.clear();
        if (!isExecuted()) {
            model.getElisaCommandHistory().clearRedo();
            setExecuted(true);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void reverse(ItemModel model) throws CommandException {
        model.setItemStorage(beforeClear);
        model.updateLists();
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
