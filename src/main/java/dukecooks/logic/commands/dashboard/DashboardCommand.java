package dukecooks.logic.commands.dashboard;

import static java.util.Objects.requireNonNull;

import dukecooks.logic.commands.Command;
import dukecooks.logic.commands.CommandResult;
import dukecooks.model.Model;

/**
 * Lists all tasks in DukeCooks to the user.
 */
public class DashboardCommand extends Command {

    public static final String COMMAND_WORD = "dashboard";

    public static final String MESSAGE_SUCCESS = "You are now at the dashboard!";

    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.getDashboardFilePath();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
