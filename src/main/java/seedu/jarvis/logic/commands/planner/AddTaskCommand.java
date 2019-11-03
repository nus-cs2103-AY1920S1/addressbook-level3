package seedu.jarvis.logic.commands.planner;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_DATE;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_FREQ;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_PRIORITY;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TAG;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TASK_DES;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TASK_TYPE;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.planner.PlannerModel;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;
import seedu.jarvis.storage.history.commands.planner.JsonAdaptedAddTaskCommand;

/**
 * Adds a task to JARVIS
 */
public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "add-task";

    public static final String MESSAGE_USAGE_TODO = COMMAND_WORD + " t/todo"
            + ": Adds a Todo task to the planner. "
            + "Parameters: "
            + PREFIX_TASK_DES + "TASK DES "
            + PREFIX_PRIORITY + "[PRIORITY LEVEL] "
            + PREFIX_FREQ + "[FREQUENCY] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_TYPE + "todo "
            + PREFIX_TASK_DES + "borrow book "
            + PREFIX_PRIORITY + "medium "
            + PREFIX_FREQ + "weekly "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_USAGE_EVENT = COMMAND_WORD + " t/event"
            + ": Adds an Event task to the planner. "
            + "Parameters: "
            + PREFIX_TASK_DES + "TASK DES "
            + PREFIX_DATE + "START DATE//END DATE "
            + PREFIX_PRIORITY + "[PRIORITY LEVEL] "
            + PREFIX_FREQ + "[FREQUENCY] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_TYPE + "event "
            + PREFIX_TASK_DES + "workshop "
            + PREFIX_DATE + "20/12/2019//20/12/2019 "
            + PREFIX_PRIORITY + "medium "
            + PREFIX_FREQ + "weekly "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "school";

    public static final String MESSAGE_USAGE_DEADLINE = COMMAND_WORD + " t/deadline"
            + ": Adds a Deadline task to the planner. "
            + "Parameters: "
            + PREFIX_TASK_TYPE + "TASK TYPE "
            + PREFIX_TASK_DES + "TASK DES "
            + PREFIX_DATE + "DUE DATE "
            + PREFIX_PRIORITY + "[PRIORITY LEVEL] "
            + PREFIX_FREQ + "[FREQUENCY] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_TYPE + "deadline "
            + PREFIX_TASK_DES + "assignment "
            + PREFIX_DATE + "20/12/2019 "
            + PREFIX_PRIORITY + "medium "
            + PREFIX_FREQ + "weekly "
            + PREFIX_TAG + "cs "
            + PREFIX_TAG + "school";

    public static final String MESSAGE_SUCCESS = "New task added:\n%1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the planner";

    public static final String MESSAGE_INVERSE_SUCCESS_DELETE = "Deleted task: %1$s";
    public static final String MESSAGE_INVERSE_TASK_NOT_FOUND = "Task already deleted: %1$s";

    public static final boolean HAS_INVERSE = true;

    private final Task toAdd;

    /**
     * Creates an {@code AddAddressCommand} to add the specified {@code Person}.
     */
    public AddTaskCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    /**
     * Gets the {@code Task} to be added.
     *
     * @return {@code Task} to be added.
     */
    public Task getAddedTask() {
        return toAdd;
    }

    /**
     * Gets the command word of the command.
     *
     * @return {@code String} representation of the command word.
     */
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * Returns whether the command has an inverse execution.
     * If the command has no inverse execution, then calling {@code executeInverse}
     * will be guaranteed to always throw a {@code CommandException}.
     *
     * @return Whether the command has an inverse execution.
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    /**
     * Adds {@code Task} to the planner, if task is not already inside planner.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that task was added successfully.
     * @throws CommandException If there already is a {@code Task} matching the task
     * to be added in the planner.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.addTask(toAdd);
        model.updateFilteredTaskList(PlannerModel.PREDICATE_SHOW_ALL_TASKS);
        model.setViewStatus(ViewType.LIST_PLANNER);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), true);
    }

    /**
     * Deletes {@code Task} from the planner that was added by this command's execution
     * if the task is still in the planner
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return {@code CommandResult} that task was removed if task was in the planner,
     * else {@code CommandResult} that the task was already not in the planner
     * @throws CommandException If task to be removed is not found in the planner
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTask(toAdd)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_TASK_NOT_FOUND, toAdd));
        }

        model.deleteTask(toAdd);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_DELETE, toAdd));
    }

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        return new JsonAdaptedAddTaskCommand(this);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
