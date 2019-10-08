package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.exercise.model.ExerciseBook;
import seedu.exercise.model.Model;

/**
 * Clears the exercise book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Exercise book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setExerciseBook(new ExerciseBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
