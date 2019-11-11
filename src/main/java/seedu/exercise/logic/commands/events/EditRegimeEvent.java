package seedu.exercise.logic.commands.events;

import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Regime;
//@@author garylyp
/**
 * Represents a particular edit regime event that can be redone or undone. Edit regime events
 * are induced using AddRegimeCommand or DeleteRegimeCommand on an existing command.
 */
public class EditRegimeEvent implements Event {

    public static final String KEY_IS_REGIME_EDITED = "isRegimeEdited";
    public static final String KEY_ORIGINAL_REGIME = "originalRegime";
    public static final String KEY_EDITED_REGIME = "editedRegime";
    private static final String EVENT_DESCRIPTION = "Edit: %1$s from %2$s exercises to %3$s exercises";

    private final Regime originalRegime;
    private final Regime editedRegime;

    /**
     * Creates an EditRegimeEvent to store the particular event of a regime being edited in the regime book.
     *
     * @param eventPayload a data carrier that stores the essential information for undo and redo
     */
    public EditRegimeEvent(EventPayload<Object> eventPayload) {
        this.originalRegime = (Regime) eventPayload.get(KEY_ORIGINAL_REGIME);
        this.editedRegime = (Regime) eventPayload.get(KEY_EDITED_REGIME);
    }

    @Override
    public void undo(Model model) {
        model.setRegime(editedRegime, originalRegime);
    }

    @Override
    public void redo(Model model) {
        model.setRegime(originalRegime, editedRegime);
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION,
                editedRegime.getRegimeName(),
                editedRegime.getRegimeExercises().asUnmodifiableObservableList().size(),
                originalRegime.getRegimeExercises().asUnmodifiableObservableList().size());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditRegimeEvent // instanceof handles nulls
                && originalRegime.equals(((EditRegimeEvent) other).originalRegime)
                && editedRegime.equals(((EditRegimeEvent) other).editedRegime));
    }
}
