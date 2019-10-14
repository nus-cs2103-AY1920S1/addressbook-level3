package seedu.jarvis.logic.commands.planner;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_FREQ;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_TASK_DES;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_TASK_TYPE;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.planner.tasks.Task;

/**
 * Adds a task to JARVIS
 */
public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the address book. "
            + "Parameters: "
            + PREFIX_TASK_TYPE + "TASK TYPE "
            + PREFIX_TASK_DES + "TASK DES "
            + PREFIX_DATE + "DATE "
            + PREFIX_PRIORITY + "PRIORITY LEVEL "
            + PREFIX_FREQ + "FREQUENCY "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_TYPE + "todo "
            + PREFIX_TASK_DES + "borrow book "
            + PREFIX_DATE + "3/10/2019 "
            + PREFIX_PRIORITY + "MED "
            + PREFIX_FREQ + "weekly "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";

    public static final String MESSAGE_INVERSE_SUCCESS_DELETE = "Deleted task: %1$s";
    public static final String MESSAGE_INVERSE_PERSON_NOT_FOUND = "Task already deleted: %1$s";

    public static final boolean HAS_INVERSE = false;

    private final Task toAdd;

    /**
     * Creates an {@code AddAddressCommand} to add the specified {@code Person}.
     */
    public AddTaskCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
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
     * Adds {@code Task} to the planner, if task is not already inside address book.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that person was added successfully.
     * @throws CommandException If there already is a {@code Person} matching the person
     * to be added in the address book.
     */
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
    public CommandResult executeInverse(Model model) throws CommandException {
        return null;
    }
}
