package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.exercise.Exercise;

/**
 * Adds an exercise to Duke Cooks.
 */
public class AddExerciseCommand extends Command {

    public static final String COMMAND_WORD = "addExercise";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an exercise to Duke Cooks. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PRIMARY_MUSCLE + "MUSCLE TYPE "
            + PREFIX_INTENSITY + "INTENSITY "
            + "[" + PREFIX_DISTANCE + "DISTANCE]..."
            + "[" + PREFIX_REPETITIONS + "REPS]..."
            + "[" + PREFIX_SETS + "SET]..."
            + "[" + PREFIX_WEIGHT + "WEIGHT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Bench Press "
            + PREFIX_PRIMARY_MUSCLE + "Chest "
            + PREFIX_INTENSITY + "3 "
            + PREFIX_REPETITIONS + "5 "
            + PREFIX_SETS + "5 "
            + PREFIX_WEIGHT + "30 kg ";

    public static final String MESSAGE_SUCCESS = "New exercise added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This exercise already exists in Duke Cooks";

    private final Exercise toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddExerciseCommand(Exercise exercise) {
        requireNonNull(exercise);
        toAdd = exercise;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasExercise(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addExercise(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddExerciseCommand // instanceof handles nulls
                && toAdd.equals(((AddExerciseCommand) other).toAdd));
    }
}
