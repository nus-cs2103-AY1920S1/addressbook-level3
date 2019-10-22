package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Optional;

import seedu.address.commons.core.item.Item;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ItemModel;
import seedu.address.model.item.VisualizeList;

/**
 * Sort the current list.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Item list has been sorted!";
    private VisualizeList beforeSort;
    private Optional<Comparator<Item>> comparator;

    public SortCommand (Optional<Comparator<Item>> comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(ItemModel model) {
        requireNonNull(model);
        beforeSort = model.getVisualList().deepCopy();
        if (comparator.isPresent()) {
            model.sort(comparator.get());
        } else {
            model.sort();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void reverse(ItemModel model) throws CommandException {
        model.setVisualizeList(beforeSort);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
