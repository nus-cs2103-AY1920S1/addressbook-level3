package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BIN_ITEMS;

import seedu.address.model.Model;

/**
 * Lists the items in the bin
 */
public class ListBinCommand extends Command {

    public static final String COMMAND_WORD = "listbin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a list of all items in the bin.";

    public static final String MESSAGE_SUCCESS = "Listed all items in the bin";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBinItemList(PREDICATE_SHOW_ALL_BIN_ITEMS);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true,
            false, false, false, false, false, false, true);
    }

}
