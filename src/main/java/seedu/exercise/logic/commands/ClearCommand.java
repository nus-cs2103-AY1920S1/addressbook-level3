package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.exercise.logic.commands.history.EventHistory;
import seedu.exercise.model.ExerciseBook;
import seedu.exercise.model.Model;
import seedu.exercise.model.ReadOnlyExerciseBook;

/**
 * Clears the exercise book.
 */
public class ClearCommand extends Command implements UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Exercise book has been cleared!";

    /**
     * The exercise book that this instance of ClearCommand has cleared.
     */
    private ReadOnlyExerciseBook exerciseBookCleared;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        exerciseBookCleared = model.getExerciseBookData();
        EventHistory.getInstance().addCommandToUndoStack(this);
        model.setExerciseBook(new ExerciseBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Returns the ExerciseBook to be cleared from the ModelManager.
     *
     * @return exercise book to be cleared
     */
    public ReadOnlyExerciseBook getExerciseBookCleared() {
        return exerciseBookCleared;
    }
}
