package dukecooks.logic.commands.dashboard;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.ListCommand;
import dukecooks.model.Model;

/**
 * Lists all tasks in DukeCooks to the user.
 */
public class ListTaskNotDoneCommand extends ListCommand {

    public static final String VARIANT_WORD = "taskincomplete";

    public static final String MESSAGE_SHOW_COMPLETE_SUCCESS = "Listed all incomplete tasks";
    public static final String MESSAGE_SHOW_COMPLETE_SUCCESS_NOTASK = "Wow you are productive!";

    private static Event event;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDashboardList(Model.PREDICATE_SHOW_NOT_DONE_DASHBOARD);

        event = Event.getInstance();
        event.set("dashboard", "all");

        if (model.getFilteredDashboardList().size() == 0) {
            return new CommandResult(MESSAGE_SHOW_COMPLETE_SUCCESS_NOTASK);
        } else {
            return new CommandResult(MESSAGE_SHOW_COMPLETE_SUCCESS);
        }
    }
}
