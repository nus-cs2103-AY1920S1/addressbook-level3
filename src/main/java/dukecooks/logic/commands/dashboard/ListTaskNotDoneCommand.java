package dukecooks.logic.commands.dashboard;

import static java.util.Objects.requireNonNull;

import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.ListCommand;
import dukecooks.model.Model;

/**
 * Lists all tasks in DukeCooks to the user.
 */
public class ListTaskNotDoneCommand extends ListCommand {

    public static final String VARIANT_WORD = "taskincomplete";

    public static final String MESSAGE_SHOW_COMPLETE_SUCCESS = "Listed all incomplete tasks";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDashboardList(Model.PREDICATE_SHOW_NOT_DONE_DASHBOARD);
        return new CommandResult(MESSAGE_SHOW_COMPLETE_SUCCESS);
    }
}
