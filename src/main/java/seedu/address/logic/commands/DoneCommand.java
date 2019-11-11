package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EventTime;
import seedu.address.model.Model;
import seedu.address.model.person.Driver;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;
import seedu.address.model.task.exceptions.TaskException;

/**
 * Marks a task as completed.
 * Only works on on-going tasks, not incomplete tasks.
 */
public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a task as COMPLETED.\n"
            + "Parameters: "
            + "[" + PREFIX_TASK + "TASK_ID] "
            + "[" + PREFIX_RATING + "RATING (Must be between 1-5) ]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK + "3 "
            + PREFIX_RATING + "5";

    public static final String MESSAGE_INVALID_ID = "Invalid Task id.";
    public static final String MESSAGE_MARK_TASK_COMPLETED = "Task (TASK ID: %1$s) is marked "
            + TaskStatus.COMPLETED + " successfully.";
    public static final String MESSAGE_TASK_NOT_ONGOING = "Current Task Status: %1$s \n"
            + "Task has to be ONGOING before it can be marked COMPLETED.";
    public static final String MESSAGE_INVALID_RATING = "Invalid Rating. Rating must be in between 1-5";

    private final int taskId;
    private final int rating;

    public DoneCommand(int taskId, int rating) {
        this.taskId = taskId;
        this.rating = rating;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTask(taskId)) {
            throw new CommandException(MESSAGE_INVALID_ID);
        }

        Task taskToMark = model.getTask(taskId);

        if (taskToMark.getStatus() == TaskStatus.INCOMPLETE) {
            throw new CommandException(String.format(MESSAGE_TASK_NOT_ONGOING, taskToMark.getStatus()));
        }

        try {
            taskToMark.markAsDone();

            model.refreshAllFilteredList();
        } catch (TaskException e) {
            //if already completed
            throw new CommandException(e.getMessage());
        }

        //add rating to driver
        taskToMark.getDriver().get().addRating(rating);

        //if task is ONGOING, it must have a driver and eventTime
        freeDriverFromTask(taskToMark.getDriver().get(), taskToMark.getEventTime().get());

        return new CommandResult(String.format(MESSAGE_MARK_TASK_COMPLETED, taskToMark.getId()));
    }

    private void freeDriverFromTask(Driver driver, EventTime eventTime) {
        boolean isDriverFreeFromEventTime = driver.deleteFromSchedule(eventTime);
        assert isDriverFreeFromEventTime;
    }
}
