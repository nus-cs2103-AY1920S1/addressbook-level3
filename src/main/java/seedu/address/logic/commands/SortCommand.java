package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.ItemModel;

/**
 * Sort the current list.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Item list has been sorted!";


    @Override
    public CommandResult execute(ItemModel model) {
        requireNonNull(model);
        model.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}