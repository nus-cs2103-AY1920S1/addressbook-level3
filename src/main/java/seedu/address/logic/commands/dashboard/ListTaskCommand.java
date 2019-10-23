package seedu.address.logic.commands.dashboard;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DASHBOARD;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;

/**
 * Lists all tasks in DukeCooks to the user.
 */
public class ListTaskCommand extends ListCommand {

    public static final String VARIANT_WORD = "task";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDashboardList(PREDICATE_SHOW_ALL_DASHBOARD);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
