package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Driver;
import seedu.address.model.task.Task;

/**
 * Edits the details of an existing person in the address book.
 */
public class FreeCommand extends Command {
    public static final String COMMAND_WORD = "free";
    public static final String MESSAGE_FREE_SUCCESS = "Task #%1$s is no longer assigned to %2$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove a driver from a task, and set the corresponding"
            + "start and end time. "
            + "\n"
            + "Parameters: "
            + "[" + PREFIX_DRIVER + "DRIVER_ID] "
            + "[" + PREFIX_TASK + "TASK_ID] " + "\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_DRIVER + "1 "
            + PREFIX_TASK + "3 ";

    private int driverId;
    private int taskId;

    /**
     * @param driverId driver's ID
     * @param taskId   task's ID
     */
    public FreeCommand(int driverId, int taskId) {
        this.driverId = driverId;
        this.taskId = taskId;
    }

    /**
     * Remove a driver from a task, and set the driver free during the corresponding time in the task.
     * The method will fail if the Task contains no EventTime, or the Driver's Schedule doesn't contain
     * the EventTime.
     *
     * @param driver driver to be freed
     * @param task   task to be freed
     */
    public static void freeDriverFromTask(Driver driver, Task task) {
        assert task.getEventTime().isPresent();

        // remove the task from driver's schedule
        boolean isFreed = driver.deleteFromSchedule(task.getEventTime().get());
        assert isFreed;

        // remove driver from the task
        task.setDriver(Optional.empty());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTask(taskId)) {
            throw new CommandException(Task.MESSAGE_INVALID_ID);
        }
        if (!model.hasDriver(driverId)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Driver driver = model.getDriver(driverId);
        Task task = model.getTask(taskId);

        freeDriverFromTask(driver, task);

        return new CommandResult(String.format(MESSAGE_FREE_SUCCESS, task.getId(), driver.getName().fullName));

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FreeCommand that = (FreeCommand) o;
        return driverId == that.driverId
                && taskId == that.taskId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverId, taskId);
    }
}
