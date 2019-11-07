package seedu.pluswork.logic.commands;

import seedu.pluswork.model.Model;

import static java.util.Objects.requireNonNull;

public class ListInventoryCommand extends Command {
    public static final String COMMAND_WORD = "list-inv";
    public static final String PREFIX_USAGE = "";

    public static final String MESSAGE_SUCCESS = "Listed all inventories";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        //model.updateFilteredInventoriesList(PREDICATE_SHOW_ALL_INVENTORIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
