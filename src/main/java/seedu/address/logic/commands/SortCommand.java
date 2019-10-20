package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ItemModel;
import seedu.address.model.item.VisualizeList;

/**
 * Sort the current list.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Item list has been sorted!";
    private VisualizeList beforeSort;


    @Override
    public CommandResult execute(ItemModel model) {
        requireNonNull(model);
        beforeSort = model.getVisualList().deepCopy();
        model.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void reverse(ItemModel model) throws CommandException {
        model.setVisualizeList(beforeSort);
    }
}
