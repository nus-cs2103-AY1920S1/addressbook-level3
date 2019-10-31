package seedu.address.model.listeners;

import seedu.address.model.ModelLists;

/**
 * Represents a listener that will be notified whenever the Model needs to be reset.
 */
public interface ModelResetListener {

    void onModelReset(ModelLists state, Object caller);
}
