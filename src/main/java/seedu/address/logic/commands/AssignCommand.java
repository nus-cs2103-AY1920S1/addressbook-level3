package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_ALREADY_ASSIGNED;
import static seedu.address.commons.core.Messages.MESSAGE_ALREADY_COMPLETED;
import static seedu.address.commons.core.Messages.MESSAGE_ASSIGN_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_TODAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.GlobalClock;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EventTime;
import seedu.address.model.Model;
import seedu.address.model.person.Driver;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.SchedulingSuggestion;
import seedu.address.model.person.exceptions.SchedulingException;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * Assigns a task with a Driver and a valid EventTime.
 */
public class AssignCommand extends Command {
    public static final String COMMAND_WORD = "assign";
    public static final String MESSAGE_PROMPT_FORCE = "Use 'assign force' to overwrite the current assignment.";
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
    public static void forceAssign(Driver driver, Task task, EventTime eventTime) throws CommandException {
        try {
            driver.assign(eventTime);
        } catch (SchedulingException e) {
            throw new CommandException(e.getMessage());
        }

        task.setDriverAndEventTime(Optional.of(driver), Optional.of(eventTime));
    }

    /**
     * Builds a String when a command is successful.
     *
     * @param suggestion the suggestion given by Schedule
     * @param task       the assigned task
     * @param driver     the driver assigned
     * @param eventTime  the time to happen
     * @return the string that used to return CommandResult
     */
    public static String buildSuccessfulResponse(SchedulingSuggestion suggestion, Task task, Driver driver,
                                                 EventTime eventTime) {
        String additionalSuggestion = suggestion.isEmpty() ? "" : "\n" + suggestion;
        String succeedResponse = String.format(MESSAGE_ASSIGN_SUCCESS,
                task.getId(),
                driver.getName().fullName,
                eventTime.toString()) + additionalSuggestion;
        return succeedResponse;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // check existence of task and driver
        if (!model.hasTask(taskId)) {
            throw new CommandException(Task.MESSAGE_INVALID_ID);
        }
        if (!model.hasDriver(driverId)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Driver driver = model.getDriver(driverId);
        Task task = model.getTask(taskId);
        Optional<EventTime> existingEventTime = task.getEventTime();

        // check whether the task is scheduled for today
        if (!task.getDate().equals(GlobalClock.dateToday())) {
            throw new CommandException(MESSAGE_NOT_TODAY);
        }

        // check if the task is already assigned
        if (task.getStatus().equals(TaskStatus.COMPLETED)) {
            throw new CommandException(MESSAGE_ALREADY_COMPLETED);
        }

        boolean isAlreadyAssigned = task.getStatus() != TaskStatus.INCOMPLETE || task.getDriver().isPresent()
                || task.getEventTime().isPresent();

        if (isAlreadyAssigned) {
            if (isForceAssign) {
                FreeCommand.freeDriverFromTask(task.getDriver().get(), task);
            } else {
                throw new CommandException(MESSAGE_ALREADY_ASSIGNED + MESSAGE_PROMPT_FORCE);
            }
        }

        // check current time against system time
        if (eventTime.getStart().compareTo(GlobalClock.timeNow()) < 0) {
            throw new CommandException(String.format(Schedule.MESSAGE_EVENT_START_BEFORE_NOW_FORMAT,
                    GlobalClock.timeNow().format(EventTime.DISPLAY_TIME_FORMAT)));
        }


        SchedulingSuggestion suggestion = driver.suggestTime(eventTime, GlobalClock.timeNow());
        if (suggestion.isFatal()) {
            if (isAlreadyAssigned && isForceAssign) {
                // restore task
                forceAssign(driver, task, existingEventTime.get());
            }
            throw new CommandException(suggestion.toString());
        }

        forceAssign(driver, task, eventTime);
        model.refreshAllFilteredList();

        return new CommandResult(buildSuccessfulResponse(suggestion, task, driver, eventTime));
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
