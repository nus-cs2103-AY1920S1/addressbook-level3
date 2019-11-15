package dukecooks.logic.commands.dashboard;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.GotoCommand;
import dukecooks.model.Model;

/**
 * Directs to Dashboard in DukeCooks to the user.
 */
public class GotoTaskCommand extends GotoCommand {

    public static final String VARIANT_WORD = "dashboard";

    public static final String MESSAGE_SUCCESS = "You are now at the dashboard";

    private static Event event;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDashboardList(Model.PREDICATE_SHOW_ALL_DASHBOARD);
        event = Event.getInstance();
        event.set("dashboard", "all");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
