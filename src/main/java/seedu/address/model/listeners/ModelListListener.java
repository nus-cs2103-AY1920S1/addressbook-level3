package seedu.address.model.listeners;

import seedu.address.model.ModelData;

/**
 * Represents a listener that will be notified whenever the EventList or TaskList in ModelManager changes.
 */
public interface ModelListListener {

    void onModelListChange(ModelData lists);
}
