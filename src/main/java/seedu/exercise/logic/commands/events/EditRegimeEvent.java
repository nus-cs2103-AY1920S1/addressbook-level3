package seedu.exercise.logic.commands.events;

import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Regime;

/**
 * Represents a particular edit regime event that can be redone or undone. Edit regime events
 * are induced using AddRegimeCommand or DeleteRegimeCommand on an existing command.
 */
public class EditRegimeEvent implements Event {

    public static final String KEY_IS_REGIME_EDITED = "isRegimeEdited";
    public static final String KEY_ORIGINAL_REGIME = "originalRegime";
    public static final String KEY_EDITED_REGIME = "editedRegime";
    private static final String EVENT_DESCRIPTION = "Edit regime: %1$s\n%2$s";

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
                editedRegime);
    }
}
