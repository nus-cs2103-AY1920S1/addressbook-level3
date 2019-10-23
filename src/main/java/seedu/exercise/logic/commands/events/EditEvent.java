package seedu.exercise.logic.commands.events;

import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Exercise;

/**
 * Represents a particular edit event that can be redone or undone.
 */
public class EditEvent implements Event {

    public static final String KEY_EXERCISE_TO_EDIT = "exerciseToEdit";
    public static final String KEY_EDITED_EXERCISE = "editedExercise";
    private static final String EVENT_DESCRIPTION = "Edit\t: %1$s\nTo\t: %2$s";

    /**
     * The exercise that has been edited during the event.
     */
    private final Exercise exerciseOld;

    /**
     * The newly edited exercise after the edit event.
     */
    private final Exercise exerciseNew;

    /**
     * Creates a EditEvent to store the particular event of an exercise being edited
     * in the exercise book.
     *
     * @param eventPayload a wrapper class that stores the essential information for undo and redo
     */
    public EditEvent(EventPayload<? super Exercise> eventPayload) {
        this.exerciseOld = (Exercise) eventPayload.get(KEY_EXERCISE_TO_EDIT);
        this.exerciseNew = (Exercise) eventPayload.get(KEY_EDITED_EXERCISE);
    }

    @Override
    public void undo(Model model) {
        model.setExercise(exerciseNew, exerciseOld);
    }

    @Override
    public void redo(Model model) {
        model.setExercise(exerciseOld, exerciseNew);
    }

    /**
     * Returns the exercise that has been edited.
     *
     * @return exercise before the edit event happens
     */
    public Exercise getExerciseOld() {
        return exerciseOld;
    }

    /**
     * Returns the newly edited exercise after the edit event.
     *
     * @return exercise after the edit event happens
     */
    public Exercise getExerciseNew() {
        return exerciseNew;
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION, exerciseOld, exerciseNew);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditEvent // instanceof handles nulls
                && exerciseOld.equals(((EditEvent) other).getExerciseOld())
                && exerciseNew.equals(((EditEvent) other).getExerciseNew()));
    }

}
