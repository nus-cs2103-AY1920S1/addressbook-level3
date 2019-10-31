package dukecooks.logic.commands.dashboard;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.ViewCommand;
import dukecooks.model.Model;

/**
 * Lists all tasks in DukeCooks to the user.
 */
public class DashboardCommand extends ViewCommand {

    public static final String COMMAND_WORD = "dashboard";

    public static final String MESSAGE_SUCCESS = "You are now at the dashboard!";

    private static Event event;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        event = Event.getInstance();
        event.set("dashboard");
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
