package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.commands.events.AddExerciseEvent.KEY_EXERCISE_TO_ADD;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_UNIT;

import seedu.exercise.logic.commands.events.EventHistory;
import seedu.exercise.logic.commands.events.EventPayload;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.ui.ListResourceType;

/**
 * Adds an exercise to the exercise book.
 */
public class AddExerciseCommand extends AddCommand implements PayloadCarrierCommand {

    public static final String MESSAGE_USAGE_EXERCISE = "Parameters: "
        + PREFIX_CATEGORY + "CATEGORY "
        + PREFIX_NAME + "EXERCISE NAME "
        + PREFIX_DATE + "DATE "
        + PREFIX_CALORIES + "CALORIES "
        + PREFIX_QUANTITY + "QUANTITY "
        + PREFIX_UNIT + "UNITS "
        + "[" + PREFIX_MUSCLE + "MUSCLE]..."
        + "[CUSTOM_PROPERTY_PREFIX_NAME/VALUE]..."
        + "\nExample: " + COMMAND_WORD + " "
        + PREFIX_CATEGORY + "exercise "
        + PREFIX_NAME + "Run "
        + PREFIX_DATE + "22/09/2019 "
        + PREFIX_CALORIES + "1500 "
        + PREFIX_QUANTITY + "2.4 "
        + PREFIX_UNIT + "km "
        + PREFIX_MUSCLE + "Leg";

    public static final String MESSAGE_SUCCESS = "New exercise added: %1$s";
    public static final String MESSAGE_DUPLICATE_EXERCISE = "This exercise already exists in the exercise book";
    public static final String RESOURCE_TYPE = "exercise";

    private Exercise exerciseToAdd;
    private EventPayload<Exercise> eventPayload;

    /**
     * Creates an AddExerciseCommand to add the specified {@code Exercise}
     */
    public AddExerciseCommand(Exercise exercise) {
        requireNonNull(exercise);
        exerciseToAdd = exercise;
        eventPayload = new EventPayload<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasExercise(exerciseToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXERCISE);
        }

        model.addExercise(exerciseToAdd);
        model.updateStatistic();
        eventPayload.put(KEY_EXERCISE_TO_ADD, exerciseToAdd);
        EventHistory.getInstance().addCommandToUndoStack(this);
        return new CommandResult(String.format(MESSAGE_SUCCESS, exerciseToAdd), ListResourceType.EXERCISE);
    }

    @Override
    public EventPayload<Exercise> getPayload() {
        return eventPayload;
    }

    @Override
    public String getCommandTypeIdentifier() {
        return RESOURCE_TYPE;

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddExerciseCommand // instanceof handles nulls
            && exerciseToAdd.equals(((AddExerciseCommand) other).exerciseToAdd));
    }
}
