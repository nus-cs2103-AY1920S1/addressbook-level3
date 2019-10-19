package seedu.exercise.logic.commands.events;

import seedu.exercise.model.Model;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;

/**
 * Represents a particular add event that can be redone or undone.
 */
public class ClearEvent implements Event {

    private static final String EVENT_DESCRIPTION = "Clear Exercise Book: %1$s";

    /**
     * The exercise book that exists before the clear event.
     */
    private final ReadOnlyResourceBook<Exercise> exerciseBookCleared;

    /**
     * Creates a ClearEvent to store the particular event of the exercise book being cleared.
     *
     * @param exerciseBookCleared an exercise book in the state before the ClearEvent.
     */
    ClearEvent(ReadOnlyResourceBook<Exercise> exerciseBookCleared) {
        this.exerciseBookCleared = exerciseBookCleared;
    }

    @Override
    public void undo(Model model) {
        model.setExerciseBook(exerciseBookCleared);
    }

    @Override
    public void redo(Model model) {
        model.setExerciseBook(new ReadOnlyResourceBook<>());
    }

    /**
     * Returns the exercise book that exists before the clear event.
     *
     * @return an exercise book in the state before the ClearEvent.
     */
    public ReadOnlyResourceBook<Exercise> getExerciseBookCleared() {
        return exerciseBookCleared;
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION, exerciseBookCleared);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ClearEvent // instanceof handles nulls
            && exerciseBookCleared.equals(((ClearEvent) other).getExerciseBookCleared()));
    }

}
