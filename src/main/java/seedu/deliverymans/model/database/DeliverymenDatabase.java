package seedu.deliverymans.model.database;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.model.deliveryman.UniqueDeliverymanList;
import seedu.deliverymans.model.deliveryman.DeliverymanStatusList;

/**
 * To be added
 */
public class DeliverymenDatabase implements ReadOnlyDeliverymenDatabase {

    private final UniqueDeliverymanList deliverymen;
    private DeliverymanStatusList statusList; // to be implemented

    {
        deliverymen = new UniqueDeliverymanList();
    }

    public DeliverymenDatabase() {}

    /**
     * Creates a DeliverymenDatabase using the Deliverymen in the {@code toBeCopied}
     */
    public DeliverymenDatabase(ReadOnlyDeliverymenDatabase toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the deliverymen list with {@code deliverymen}.
     * {@code deliverymen} must not contain duplicate deliverymen.
     */
    public void setDeliverymen(List<Deliveryman> deliverymen) {
        this.deliverymen.setDeliverymen(deliverymen);
    }

    /**
     * Resets the existing data of this {@code DeliverymenDatabase} with {@code newData}.
     */
    public void resetData(ReadOnlyDeliverymenDatabase newData) {
        requireNonNull(newData);

        setDeliverymen(newData.getDeliverymenList());
    }

    //// deliverymen-level operations

    /**
     * Returns true if a deliveryman with the same identity as {@code deliveryman} exists in the deliverymen database.
     */
    public boolean hasDeliveryman(Deliveryman man) {
        requireNonNull(man);
        return deliverymen.contains(man);
    }

    /**
     * Adds a deliveryman to the deliverymen database.
     * The deliveryman must not already exist in the deliverymen database.
     */
    public void addDeliveryman(Deliveryman man) {
        deliverymen.add(man);
    }

    /**
     * Replaces the given deliveryman {@code target} in the list with {@code editedDeliveryman}.
     * {@code target} must exist in the deliveryman database.
     * The customer identity of {@code editedDeliveryman} must not be the same as another existing deliveryman in the
     * delvierymen database.
     */
    public void setDeliveryman(Deliveryman target, Deliveryman editedDeliveryman) {
        requireNonNull(editedDeliveryman);
        deliverymen.setDeliveryman(target, editedDeliveryman);
    }

    /**
     * Removes {@code key} from this {@code DeliverymenDatabase}.
     * {@code key} must exist in the deliverymen database.
     */
    public void removeDeliveryman(Deliveryman key) {
        deliverymen.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return deliverymen.asUnmodifiableObservableList().size() + " deliverymen";
    }

    @Override
    public ObservableList<Deliveryman> getDeliverymenList() {
        return deliverymen.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeliverymenDatabase
                && deliverymen.equals(((DeliverymenDatabase) other).deliverymen));
    }

    @Override
    public int hashCode() {
        return deliverymen.hashCode();
    }
}
