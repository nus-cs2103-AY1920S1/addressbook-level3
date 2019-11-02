package seedu.exercise.logic.commands.events;

import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Exercise;

/**
 * Represents a particular edit event that can be redone or undone.
 */
public class EditExerciseEvent implements Event {

    public static final String KEY_ORIGINAL_EXERCISE = "originalExercise";
    public static final String KEY_EDITED_EXERCISE = "editedExercise";
    private static final String EVENT_DESCRIPTION = "Edit\t: %1$s\nTo\t: %2$s";

    /**
     * The exercise that has been edited during the event.
     */
    private final Exercise originalExercise;

    /**
     * The newly edited exercise after the edit event.
     */
    private final Exercise editedExercise;

    /**
     * Creates a EditExerciseEvent to store the particular event of an exercise being edited
     * in the exercise book.
     *
     * @param eventPayload a wrapper class that stores the essential information for undo and redo
     */
    public EditExerciseEvent(EventPayload<Exercise> eventPayload) {
        this.originalExercise = (Exercise) eventPayload.get(KEY_ORIGINAL_EXERCISE);
        this.editedExercise = (Exercise) eventPayload.get(KEY_EDITED_EXERCISE);
    }

    @Override
    public void undo(Model model) {
        model.setExercise(editedExercise, originalExercise);
    }

    @Override
    public void redo(Model model) {
        model.setExercise(originalExercise, editedExercise);
    }

    /**
     * Returns the exercise that has been edited.
     *
     * @return exercise before the edit event happens
     */
    public Exercise getOriginalExercise() {
        return originalExercise;
    }

    /**
     * Returns the newly edited exercise after the edit event.
     *
     * @return exercise after the edit event happens
     */
    public Exercise getEditedExercise() {
        return editedExercise;
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION, originalExercise, editedExercise);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditExerciseEvent // instanceof handles nulls
                && originalExercise.equals(((EditExerciseEvent) other).getOriginalExercise())
                && editedExercise.equals(((EditExerciseEvent) other).getEditedExercise()));
    }

}
