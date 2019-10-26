package seedu.deliverymans.model.deliveryman;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// import javafx.collections.transformation.SortedList;
import seedu.deliverymans.model.deliveryman.deliverymanstatus.UniqueStatusList;

/**
 * A list that primarily focuses on the status of the deliverymen.
 * Handles the statuses of the deliverymen.
 * Issues related to the current statuses of the deliverymen are directed here.
 * Not allowed to edit information regarding personal info of deliverymen.
 */
public class StatusManager {

    private final UniqueDeliverymanList deliverymen;
    private final UniqueStatusList statuses;

    private ObservableList<Deliveryman> availableMen = FXCollections.observableArrayList();
    private ObservableList<Deliveryman> unavailableMen = FXCollections.observableArrayList();
    private ObservableList<Deliveryman> deliveringMen = FXCollections.observableArrayList();

    public StatusManager(UniqueDeliverymanList deliverymenList) {
        deliverymen = deliverymenList;
        statuses = new UniqueStatusList();
        initDeliverymenList(deliverymenList);
        initStatusLists();
    }

    // ====== Methods to initialise all the lists when app is opened ==============================================
    /**
     * Initialise the deliverymen list when app is re-opened.
     */
    public void initDeliverymenList(UniqueDeliverymanList deliverymenList) {
        for (Deliveryman man: deliverymenList) {
            deliverymen.add(man);
        }
    }

    /**
     * Initialise all the statuses of every deliveryman when app is re-opened.
     */
    public void initStatusLists() {
        for (Deliveryman man: deliverymen) {
            switch (man.getStatus().getDescription()) {
            case "AVAILABLE":
                availableMen.add(man);
                break;
            case "UNAVAILABLE":
                availableMen.add(man);
                break;
            case "DELIVERING":
                deliveringMen.add(man);
                break;
            default:
                return;
            }
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

    // ========== Methods for Order assignment ===================================================================

    /**
     * Returns an available deliveryman to the DeliverymenDatabase.
     */
    public Deliveryman getAvailableDeliveryman() {
        Deliveryman removed = availableMen.remove(0);
        updateStatusOf(removed, "DELIVERING");
        return removed;
    }

    /**
     * Updates the status of a deliveryman by removing the previous status and assigning the new status.
     */
    public void updateStatusOf(Deliveryman deliveryman, String strStatus) {
        removePreviousStatus(deliveryman);
        assignStatusTagTo(deliveryman, strStatus);
    }

    /**
     * Removes original status from a deliveryman.
     */
    public void removePreviousStatus(Deliveryman deliveryman) {
        switch (deliveryman.getStatus().getDescription()) {
        case "AVAILABLE":
            availableMen.remove(deliveryman);
            break;
        case "UNAVAILABLE":
            unavailableMen.remove(deliveryman);
            break;
        case "DELIVERING":
            deliveringMen.remove(deliveryman);
            break;
        default:
            return;
        }
    }

    /**
     * Assigns new status tag to a deliveryman.
     */
    public void assignStatusTagTo(Deliveryman deliveryman, String strNewStatus) {
        switch (strNewStatus) {
        case "AVAILABLE":
            deliveryman.setStatusTo(statuses.getAvailableTag());
            availableMen.add(deliveryman);
            break;
        case "UNAVAILABLE":
            deliveryman.setStatusTo(statuses.getUnavailableTag());
            unavailableMen.add(deliveryman);
            break;
        case "DELIVERING":
            deliveryman.setStatusTo(statuses.getDeliveringTag());
            deliveringMen.add(deliveryman);
            break;
        default:
            return;
        }
    }

}
