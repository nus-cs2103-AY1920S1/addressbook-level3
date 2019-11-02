package dukecooks.logic.commands.workout;

import static dukecooks.logic.parser.CliSyntax.PREFIX_WORKOUT_NAME;
import static java.util.Objects.requireNonNull;

import dukecooks.logic.commands.AddCommand;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.workout.Workout;

/**
 *  Initialises a Workout in Duke Cooks
 */

public class AddWorkoutCommand extends AddCommand {

    public static final String VARIANT_WORD = "workout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + VARIANT_WORD + ": Adds a workout to Duke Cooks. "
            + "Parameters: "
            + PREFIX_WORKOUT_NAME + "NAME \n"
            + "Example: " + COMMAND_WORD + " " + VARIANT_WORD
            + PREFIX_WORKOUT_NAME + "5x5 Powerlift";

    public static final String MESSAGE_SUCCESS = "New workout successfully initialised! \n "
            + "Now let's add some exercises!";
    public static final String MESSAGE_DUPLICATE_WORKOUT = "This workout already exists in Duke Cooks";

    private final Workout toAdd;

    /**
     * Creates an AddWorkoutCommand to add the specified {@code Workout}
     */
    public AddWorkoutCommand(Workout workout) {
        requireNonNull(workout);
        toAdd = workout;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasWorkout(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_WORKOUT);
        }

        model.addWorkout(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddWorkoutCommand // instanceof handles nulls
                && toAdd.equals(((AddWorkoutCommand) other).toAdd));
    }
}
