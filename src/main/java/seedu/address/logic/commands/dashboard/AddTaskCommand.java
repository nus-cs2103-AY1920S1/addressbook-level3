package seedu.address.logic.commands.dashboard;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.dashboard.components.Dashboard;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASKDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASKNAME;

public class AddTaskCommand extends AddCommand {

    public static final String COMMAND_WORD = "taskadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to Duke Cooks. \n"
            + "Parameters: "
            + PREFIX_TASKNAME + "TASKNAME "
            + PREFIX_TASKDATE + "TASKDATE ";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task is already exists in Duke Cooks";

    public final Dashboard toAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code Dashboard}
     */
    public AddTaskCommand(Dashboard dashboard) {
        requireNonNull(dashboard);
        toAdd =  dashboard;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if(model.hasDashboard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.addDashboard(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand //instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
