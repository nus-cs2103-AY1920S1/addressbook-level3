package dukecooks.logic.commands.dashboard;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.AddCommand;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.model.Model;
import dukecooks.model.dashboard.components.Dashboard;

/**
 * Adds a task into DukeCooks
 */
public class AddTaskCommand extends AddCommand {

    public static final String VARIANT_WORD = "task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to DukeCooks. \n"
            + "Parameters: "
            + CliSyntax.PREFIX_TASKNAME + "TASKNAME "
            + CliSyntax.PREFIX_TASKDATE + "TASKDATE ";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task is already exists in DukeCooks";

    public final Dashboard toAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code Dashboard}
     */
    public AddTaskCommand(Dashboard dashboard) {
        requireNonNull(dashboard);
        toAdd = dashboard;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasDashboard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        // Navigate to dashboard tab
        Event event = Event.getInstance();
        event.set("dashboard", "all");

        model.addDashboard(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand //instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }

    @Override
    public String toString() {
        return toAdd.toString();
    }
}
