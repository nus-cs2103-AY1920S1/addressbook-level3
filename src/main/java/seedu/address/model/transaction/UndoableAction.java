package seedu.address.model.transaction;

import seedu.address.model.util.Date;

/**
 * Abstraction of different user actions
 */
public interface UndoableAction {
    public Amount getAmount();
    public Date getDate();
}
