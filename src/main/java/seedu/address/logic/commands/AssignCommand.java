package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.time.Clock;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EventTime;
import seedu.address.model.Model;
import seedu.address.model.person.Driver;
import seedu.address.model.person.exceptions.SchedulingException;
import seedu.address.model.task.Task;

/**
 * Edits the details of an existing person in the address book.
 */
public class AssignCommand extends Command {
    public static final String COMMAND_WORD = "assign";
    public static final String MESSAGE_ASSIGN_SUCCESS = "Assigned #%1$d to %2$s at %3$s";
    public static final String MESSAGE_EVENT_START_BEFORE_NOW = "The proposed time is in the past.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assign a driver the specified task, with a proposed "
            + "start and end time. "
            + "\n"
            + "Parameters: [force] "
            + "[" + PREFIX_DRIVER + "DRIVER_ID] "
            + "[" + PREFIX_TASK + "TASK_ID] "
            + "[" + PREFIX_EVENT_TIME + "hMM - hMM] " + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DRIVER + "1 "
            + PREFIX_TASK + "3 "
            + PREFIX_EVENT_TIME + "930 - 1600";

    private Clock clock;
    private EventTime eventTime;
    private boolean isForceAssign;
    private int driverId;
    private int taskId;

    /**
     * @param driverId      driver's ID
     * @param taskId        task's ID
     * @param eventTime     successfully parsed EventTime object
     * @param isForceAssign true if disregard suggestion messages
     */
    public AssignCommand(int driverId, int taskId, EventTime eventTime, boolean isForceAssign) {
        requireNonNull(eventTime);

        this.driverId = driverId;
        this.taskId = taskId;
        this.eventTime = eventTime;
        this.isForceAssign = isForceAssign;
        this.clock = Clock.systemDefaultZone();
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

        // check current time against system time
        if (eventTime.getStart().compareTo(LocalTime.now(this.clock)) < 0) {
            throw new CommandException(MESSAGE_EVENT_START_BEFORE_NOW);
        }


        String suggestion = driver.suggestTime(eventTime);
        if (!suggestion.isEmpty() && !isForceAssign) {
            throw new CommandException(suggestion);
        }

        forceAssign(driver, task, eventTime);

        // TODO: update GUI
        return new CommandResult(String.format(MESSAGE_ASSIGN_SUCCESS,
                task.getId(), driver.getName().fullName, eventTime.toString()));

    }

    /**
     * Assign the task at the given time to the specified driver, without checking the driver's schedule.
     * The operation is atomic.
     *
     * @param driver    driver
     * @param task      task
     * @param eventTime the time which the task is happening
     * @throws SchedulingException when the proposed time conflicts with the driver's schedule
     */
    private void forceAssign(Driver driver, Task task, EventTime eventTime) throws CommandException {
        try {
            driver.assign(eventTime);
        } catch (SchedulingException e) {
            throw new CommandException(e.getMessage());
        }

        task.setDriverAndEventTime(Optional.of(driver), Optional.of(eventTime));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssignCommand that = (AssignCommand) o;
        return isForceAssign == that.isForceAssign
                && driverId == that.driverId
                && taskId == that.taskId
                && Objects.equals(eventTime, that.eventTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventTime, isForceAssign, driverId, taskId);
    }
}
