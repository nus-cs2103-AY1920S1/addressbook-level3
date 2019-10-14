package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;

/**
 * Adds a task to the address book.
 */
public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "add-task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the dashboard. "
            + "Parameters: "
            + PREFIX_TASK_NAME + "NAME "
            + PREFIX_MEMBER_NAME + "MEMBER_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_NAME + "finish user guide "
            + PREFIX_MEMBER_NAME + "elsa koh";

    private static final String MESSAGE_SUCCESS = "New task added: %1$s";
    private static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";

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
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
