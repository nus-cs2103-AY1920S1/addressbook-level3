package seedu.address.logic.commands.calendar;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * command to add tasks.
 */
public class AddTaskCommand extends Command {
    public static final String COMMAND_WORD = "Add Task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the calendar. "
            + "Parameters: "
            + PREFIX__TASK_DESCRIPTION + "DESCRIPTION "
            + PREFIX_MARKING + "MARKING_STATUS "
            + PREFIX_TASK_TIME + "START_TIME, END_TIME"
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX__TASK_DESCRIPTION + "CS2103T Lecture "
            + PREFIX_MARKING + "Y "
            + PREFIX_TASK_TIME + "13/10/2019 13:00, 13/10/2019 15:00 "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

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
