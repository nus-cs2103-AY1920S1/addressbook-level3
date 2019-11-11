package seedu.deliverymans.model.database;

import javafx.collections.ObservableList;
import seedu.deliverymans.model.deliveryman.Deliveryman;

/**
 * Unmodifiable view of the all info regarding deliverymen.
 */
public interface ReadOnlyDeliverymenDatabase {
    /**
     * Returns an unmodifiable view of the deliverymen list.
     * This list will not contain any duplicate deliverymen.
     */
    ObservableList<Deliveryman> getDeliverymenList();
}
