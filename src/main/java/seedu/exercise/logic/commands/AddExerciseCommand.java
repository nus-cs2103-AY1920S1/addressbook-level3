package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_UNIT;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.commands.history.EventHistory;
import seedu.exercise.model.Model;
import seedu.exercise.model.exercise.Exercise;

/**
 * Adds an exercise to the exercise book.
 */
public class AddExerciseCommand extends AddCommand {

    public static final String MESSAGE_USAGE_EXERCISE = "Parameters: "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_NAME + "EXERCISE NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_CALORIES + "CALORIES "
            + PREFIX_QUANTITY + "QUANTITY "
            + PREFIX_UNIT + "UNITS "
            + "[" + PREFIX_MUSCLE + "MUSCLE]...\n"
            + "\t\tExample: " + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "exercise"
            + PREFIX_NAME + "Run "
            + PREFIX_DATE + "22/09/2019 "
            + PREFIX_CALORIES + "1500 "
            + PREFIX_QUANTITY + "2.4 "
            + PREFIX_UNIT + "km "
            + PREFIX_MUSCLE + "Leg";

    public static final String MESSAGE_SUCCESS = "New exercise added: %1$s";
    public static final String MESSAGE_DUPLICATE_EXERCISE = "This exercise already exists in the exercise book";

    private Exercise toAdd;

    /**
     * Creates an AddExerciseCommand to add the specified {@code Exercise}
     */
    public AddExerciseCommand(Exercise exercise) {
        requireNonNull(exercise);
        toAdd = exercise;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasExercise(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXERCISE);
        }

        model.addExercise(toAdd);
        EventHistory.getInstance().addCommandToUndoStack(this);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    public Exercise getExercise() {
        return toAdd;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddExerciseCommand // instanceof handles nulls
                && toAdd.equals(((AddExerciseCommand) other).toAdd));
    }
}
