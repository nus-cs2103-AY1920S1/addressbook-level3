package seedu.exercise.logic.commands.events;

import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Exercise;

/**
 * Represents a particular add exercise event that can be redone or undone.
 */
public class AddExerciseEvent implements Event {

    public static final String KEY_EXERCISE_TO_ADD = "exerciseToAdd";
    private static final String EVENT_DESCRIPTION = "Add exercise: %1$s";

    /**
     * The exercise that has been added during the event.
     */
    private final Exercise exercise;

    /**
     * Creates an AddExerciseEvent to store the particular event of an exercise being added to the exercise book.
     *
     * @param eventPayload a wrapper class that stores the essential information for undo and redo
     */
    public AddExerciseEvent(EventPayload<? super Exercise> eventPayload) {
        this.exercise = (Exercise) eventPayload.get(KEY_EXERCISE_TO_ADD);
    }

    @Override
    public void undo(Model model) {
        model.deleteExercise(exercise);
    }

    @Override
    public void redo(Model model) {
        model.addExercise(exercise);
    }

    /**
     * Returns the exercise that was added.
     *
     * @return exercise that is passed into constructor of AddExerciseEvent
     */
    public Exercise getExercise() {
        return exercise;
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION, exercise);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddExerciseEvent // instanceof handles nulls
                && exercise.equals(((AddExerciseEvent) other).getExercise()));
    }

}
