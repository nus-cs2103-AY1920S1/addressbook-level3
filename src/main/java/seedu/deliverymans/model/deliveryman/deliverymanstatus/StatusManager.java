package seedu.deliverymans.model.deliveryman.deliverymanstatus;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.model.deliveryman.deliverymanstatus.UniqueStatusList.AVAILABLE_STATUS;
import static seedu.deliverymans.model.deliveryman.deliverymanstatus.UniqueStatusList.DELIVERING_STATUS;
import static seedu.deliverymans.model.deliveryman.deliverymanstatus.UniqueStatusList.UNAVAILABLE_STATUS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.model.deliveryman.UniqueDeliverymanList;
import seedu.deliverymans.model.deliveryman.exceptions.InvalidStatusChangeException;
import seedu.deliverymans.model.deliveryman.exceptions.NoMoreAvailableDeliverymanException;

/**
 * Manages and handles the statuses of the deliverymen.
 * Issues related to the current statuses of the deliverymen are directed here.
 * Not allowed to edit identity and data field of deliverymen other than their statuses.
 */
public class StatusManager {

    private final ObservableList<Deliveryman> availableMen = FXCollections.observableArrayList();
    private final ObservableList<Deliveryman> unavailableMen = FXCollections.observableArrayList();
    private final ObservableList<Deliveryman> deliveringMen = FXCollections.observableArrayList();

    /**
     * Initialise all the status lists of every deliveryman when app is re-opened.
     * Can only be called by higher-level classes that uses this class (ie. DeliverymenDatabase)
     */
    public void initStatusLists(UniqueDeliverymanList deliverymenList) {
        resetStatusLists();
        for (Deliveryman man : deliverymenList) {
            switch (man.getStatus().getDescription()) {
            case AVAILABLE_STATUS:
                availableMen.add(man);
                break;
            case UNAVAILABLE_STATUS:
                unavailableMen.add(man);
                break;
            case DELIVERING_STATUS:
                deliveringMen.add(man);
                break;
            default:
                return;
            }
        }
    }

    /**
     * Changes a status of a deliveryman from AVAILABLE to UNAVAILABLE, or vice versa.
     *
     * @throws InvalidStatusChangeException if deliveryman status is DELIVERING.
     */
    public Deliveryman switchDeliverymanStatus(Deliveryman deliveryman) throws InvalidStatusChangeException {
        String status = deliveryman.getStatus().getDescription();
        if (status.equals(AVAILABLE_STATUS)) {
            return updateStatusOf(deliveryman, UNAVAILABLE_STATUS);
        } else if (status.equals(UNAVAILABLE_STATUS)) {
            return updateStatusOf(deliveryman, AVAILABLE_STATUS);
        } else {
            throw new InvalidStatusChangeException();
        }
    }

    public void addAvailableMan(Deliveryman deliveryman) {
        availableMen.add(deliveryman);
    }

    public void addUnavailableMan(Deliveryman deliveryman) {
        unavailableMen.add(deliveryman);
    }

    public void addDeliveringMan(Deliveryman deliveryman) {
        deliveringMen.add(deliveryman);
    }

    /**
     * Removes a deliveryman from the relevant status list.
     * Guarantees: Deliveryman exists in one of the 3 status lists.
     */
    public void removeDeliveryman(Deliveryman target) {
        requireNonNull(target);
        assert (target.getStatus().getDescription().equals(AVAILABLE_STATUS)
                || target.getStatus().getDescription().equals(UNAVAILABLE_STATUS)
                || target.getStatus().getDescription().equals(DELIVERING_STATUS));

        for (Deliveryman man: availableMen) {
            if (target.isSameDeliveryman(man)) {
                availableMen.remove(man);
                return;
            }
        }

        for (Deliveryman man: unavailableMen) {
            if (target.isSameDeliveryman(man)) {
                unavailableMen.remove(man);
                return;
            }
        }

        for (Deliveryman man: deliveringMen) {
            if (target.isSameDeliveryman(man)) {
                deliveringMen.remove(man);
                return;
            }
        }
    }

    /**
     * Returns a list of all available deliverymen.
     */
    public ObservableList<Deliveryman> listAvailableMen() {
        return availableMen;
    }

    /**
     * Returns a list of all unavailable deliverymen.
     */
    public ObservableList<Deliveryman> listUnavailableMen() {
        return unavailableMen;
    }

    /**
     * Returns a list of all delivering deliverymen.
     */
    public ObservableList<Deliveryman> listDeliveringMen() {
        return deliveringMen;
    }

    /**
     * Returns true if the list contains an equivalent deliveryman as the given argument.
     */
    public boolean contains(Deliveryman toCheck) {
        requireNonNull(toCheck);
        return availableMen.stream().anyMatch(toCheck::isSameDeliveryman);
    }

    /**
     * Clears all the status lists.
     */
    public void resetStatusLists() {
        availableMen.clear();
        unavailableMen.clear();
        deliveringMen.clear();
    }

    // ========== Methods related to order ===================================================================

    /**
     * Returns an available deliveryman to the DeliverymenDatabase.
     * @return an available Deliveryman
     * @throws NoMoreAvailableDeliverymanException if availableMen is empty
     */
    public Deliveryman getAvailableDeliveryman() throws NoMoreAvailableDeliverymanException {
        if (availableMen.size() == 0) {
            throw new NoMoreAvailableDeliverymanException();
        }
        Deliveryman removed = availableMen.remove(0);
        return updateStatusOf(removed, DELIVERING_STATUS);
    }

    /**
     * Resets the status of the deliveryman after an order has become inactive (ie. deleted or completed).
     */
    public Deliveryman updateDeliverymanStatusAfterChangesToOrder(Deliveryman deliveryman) {
        return updateStatusOf(deliveryman, AVAILABLE_STATUS);
    }

    /**
     * Updates the status of a deliveryman by removing the previous status and assigning the new status.
     */
    public Deliveryman updateStatusOf(Deliveryman deliveryman, String strStatus) {
        removePreviousStatus(deliveryman);
        return assignStatusTagTo(deliveryman, strStatus);
    }

    /**
     * Removes original status from a deliveryman.
     */
    public void removePreviousStatus(Deliveryman deliveryman) {
        switch (deliveryman.getStatus().getDescription()) {
        case AVAILABLE_STATUS:
            availableMen.remove(deliveryman);
            break;
        case UNAVAILABLE_STATUS:
            unavailableMen.remove(deliveryman);
            break;
        case DELIVERING_STATUS:
            deliveringMen.remove(deliveryman);
            break;
        default:
            return;
        }
    }

    /**
     * Assigns new status tag to a deliveryman.
     */
    public Deliveryman assignStatusTagTo(Deliveryman deliveryman, String strNewStatus) {
        Deliveryman edited;
        switch (strNewStatus) {
        case AVAILABLE_STATUS:
            edited = deliveryman.setStatusTo(UniqueStatusList.getAvailableTag());
            availableMen.add(edited);
            return edited;
        case UNAVAILABLE_STATUS:
            edited = deliveryman.setStatusTo(UniqueStatusList.getAvailableTag());
            unavailableMen.add(edited);
            return edited;
        case DELIVERING_STATUS:
            edited = deliveryman.setStatusTo(UniqueStatusList.getDeliveringTag());
            deliveringMen.add(edited);
            return edited;
        default:
            return deliveryman;
        }
    }

}
