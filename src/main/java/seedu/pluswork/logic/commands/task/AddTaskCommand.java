package seedu.pluswork.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_STATUS;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_TAG;

import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.task.Task;


/**
 * Adds a task to the project.
 */
public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "add-task";
    public static final String PREFIX_USAGE = PREFIX_TASK_NAME + "  " + PREFIX_TASK_STATUS + "  " + PREFIX_TASK_TAG;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the dashboard. "
            + "Parameters: "
            + PREFIX_TASK_NAME + "NAME "
            + PREFIX_TASK_STATUS + "STATUS "
            + PREFIX_TASK_TAG + "TAG "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_NAME + "Complete Assignment "
            + PREFIX_TASK_STATUS + "unbegun "
            + PREFIX_TASK_TAG + "urgent";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in your Dashboard";

    private final Task toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public AddTaskCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
//        return new CommandResult("Type-3 /" + String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
