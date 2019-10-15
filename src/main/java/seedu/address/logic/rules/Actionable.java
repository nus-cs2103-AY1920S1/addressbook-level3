package seedu.address.logic.rules;

import seedu.address.model.Model;

/**
 * Represents an action with hidden internal logic and the ability to be performed.
 */
public interface Actionable {
    void perform(Model model);
}
