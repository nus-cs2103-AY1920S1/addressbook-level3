package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AssignCommand.checkAssignPreconditions;
import static seedu.address.logic.commands.AssignCommand.forceAssign;
import static seedu.address.logic.commands.AssignCommand.getTaskIfPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.time.Duration;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.optimizer.Candidate;
import seedu.address.logic.optimizer.ScheduleOptimizer;
import seedu.address.model.Model;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * Suggests a most optimum driver for an incoming task.
 */
public class SuggestCommand extends Command {
    public static final String COMMAND_WORD = "suggest";
    public static final String MESSAGE_NO_DRIVER_AVAILABLE = "No driver is available for this duration. ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Suggest and assign the most optimum driver for the "
            + "task"
            + "\n"
            + "Parameters: [NUMBER_OF_HOURS] "
            + "[" + PREFIX_TASK + "TASK_ID] " + "\n"
            + "Example: " + COMMAND_WORD + " "
            + "1.5" + " "
            + PREFIX_TASK + "3 ";

    private Duration duration;
    private int taskId;

    /**
     * @param taskId   task's ID
     * @param duration duration
     */
    public SuggestCommand(int taskId, Duration duration) {
        requireNonNull(duration);

        this.taskId = taskId;
        this.duration = duration;
    }

    /**
     * Builds a response when the command is succesfully executed.
     * @param result the chosen candidate to assign task
     * @param task the task assigned
     * @return the response string
     */
    public static String buildSuccessfulResponse(Candidate result, Task task) {
        return String.format(Messages.MESSAGE_ASSIGN_SUCCESS,
                task.getId(), result.getKey().getName().fullName, result.getValue().get().toString());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Task task = getTaskIfPresent(model, taskId);
        checkAssignPreconditions(task);

        // check whether the task is already scheduled
        assertTaskIncomplete(task);

        ScheduleOptimizer optimizer = new ScheduleOptimizer(model, task, duration);

        Candidate result = optimizer.start()
                .orElseThrow(() -> new CommandException(MESSAGE_NO_DRIVER_AVAILABLE));

        // optimizer always return an available driver and time slot
        forceAssign(result.getKey(), task, result.getValue().get());

        model.refreshAllFilteredList();

        return new CommandResult(buildSuccessfulResponse(result, task));
    }

    /**
     * Checks that the task is incomplete, throws an exception otherwise.
     * @param task the task to check
     * @throws CommandException with error message
     */
    public void assertTaskIncomplete(Task task) throws CommandException {
        if (task.getStatus() != TaskStatus.INCOMPLETE || task.getDriver().isPresent()
                || task.getEventTime().isPresent()) {
            throw new CommandException(Messages.MESSAGE_ALREADY_ASSIGNED);
        }
    }

}
