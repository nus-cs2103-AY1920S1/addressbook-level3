package seedu.exercise.logic.commands.events;

import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Regime;

/**
 * Represents a particular add regime event that can be redone or undone.
 */
public class AddRegimeEvent implements Event {

    public static final String KEY_REGIME_TO_ADD = "regimeToAdd";
    private static final String EVENT_DESCRIPTION = "Add regime: %1$s\n%2$s";

    private final Regime regimeToAdd;

    /**
     * Creates an AddRegimeEvent to store the particular event of a regime being added to
     * the regime book.
     *
     * @param eventPayload a wrapper class that stores the essential information for undo and redo
     */
    public AddRegimeEvent(EventPayload<? super Regime> eventPayload) {
        this.regimeToAdd = (Regime) eventPayload.get(KEY_REGIME_TO_ADD);
    }

    @Override
    public void undo(Model model) {
        model.deleteRegime(regimeToAdd);
    }

    @Override
    public void redo(Model model) {
        model.addRegime(regimeToAdd);
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION,
                regimeToAdd.getRegimeName(),
                regimeToAdd);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRegimeEvent // instanceof handles nulls
                && regimeToAdd.equals(((AddRegimeEvent) other).regimeToAdd));
    }
}
