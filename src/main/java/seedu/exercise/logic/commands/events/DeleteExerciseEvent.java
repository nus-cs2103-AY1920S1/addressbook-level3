package seedu.exercise.logic.commands.events;

import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Exercise;

/**
 * Represents a particular delete exercise event that can be redone or undone.
 */
public class DeleteExerciseEvent implements Event {

    public static final String KEY_EXERCISE_TO_DELETE = "exerciseToDelete";
    private static final String EVENT_DESCRIPTION = "Delete exercise: %1$s";

    /**
     * The exercise that has been deleted during the event.
     */
    private final Exercise exerciseToDelete;

    /**
     * Creates a DeleteExerciseEvent to store the particular event of an exercise being deleted from the exercise book.
     *
     * @param eventPayload a wrapper class that stores the essential information for undo and redo
     */
    public DeleteExerciseEvent(EventPayload<Exercise> eventPayload) {
        this.exerciseToDelete = (Exercise) eventPayload.get(KEY_EXERCISE_TO_DELETE);
    }

    @Override
    public void undo(Model model) {
        model.addExercise(exerciseToDelete);
    }

    @Override
    public void redo(Model model) {
        model.deleteExercise(exerciseToDelete);
    }

    /**
     * Returns the exercise that was deleted.
     *
     * @return exercise that is passed into constructor of DeleteExerciseEvent
     */
    public Exercise getExerciseToDelete() {
        return exerciseToDelete;
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION, exerciseToDelete);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteExerciseEvent // instanceof handles nulls
                && exerciseToDelete.equals(((DeleteExerciseEvent) other).getExerciseToDelete()));
    }

}
