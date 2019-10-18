package seedu.address.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARKING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TIME;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;


/**
 * command to add tasks.
 */
public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "Add Task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the calendar. "
            + "Parameters: "
            + PREFIX_TASK_DESCRIPTION + "DESCRIPTION "
            + PREFIX_MARKING + "MARKING_STATUS "
            + PREFIX_TASK_TIME + "START_TIME, END_TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_DESCRIPTION + "CS2103T Lecture "
            + PREFIX_MARKING + "Y "
            + PREFIX_TASK_TIME + "13/10/2019 13:00, 13/10/2019 15:00 ";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Task toAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code Task}
     */
    public AddTaskCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }


}
