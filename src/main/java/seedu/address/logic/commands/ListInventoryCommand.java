package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INVENTORIES;

import seedu.address.model.Model;

public class ListInventoryCommand extends Command{
    public static final String COMMAND_WORD = "list-inv";

    public static final String MESSAGE_SUCCESS = "Listed all inventories";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredInventoriesList(PREDICATE_SHOW_ALL_INVENTORIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
