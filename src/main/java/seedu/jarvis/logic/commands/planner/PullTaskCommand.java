package seedu.jarvis.logic.commands.planner;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Pulls a list of tasks based on certain specified attributes
 * Attributes are priority, tags and date.
 */
public class PullTaskCommand extends Command {

    public static final String COMMAND_WORD = "pull-task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks"
             + "with the specified attribute.\n"
             + "Parameters: {p/PRIORITY_LEVEL | d/DATE | #TAG }\n"
             + "Example: " + COMMAND_WORD + " #school";

    public static final String MESSAGE_NO_INVERSE = COMMAND_WORD + " command cannot be undone.";

    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";

    public static final boolean HAS_INVERSE = false;

    private final Predicate<Task> predicate;

    public PullTaskCommand(Predicate<Task> predicate) {
        this.predicate = predicate;
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
     * @return whether the command has an inverse execution
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    /**
     * Pulls all {@code Task} in the planner that pass the {@code Predicate} of the command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code Model} of the number of {@code Task} matching the {@code Predicate}.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredTaskList(predicate);
        model.setViewStatus(ViewType.LIST_PLANNER_PULL);

        return new CommandResult(String.format(MESSAGE_TASKS_LISTED_OVERVIEW, model.getFilteredTaskList().size()),
                true);
    }

    /**
     * There is no available inverse execution available, always throws a {@code CommandException}.
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @throws CommandException Always thrown.
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NO_INVERSE);
    }

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        throw new InvalidCommandToJsonException();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PullTaskCommand)
                && predicate.equals(((PullTaskCommand) other).predicate);
    }
}
