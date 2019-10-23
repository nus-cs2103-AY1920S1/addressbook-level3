package seedu.deliverymans.model.deliveryman;

import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// import javafx.collections.transformation.SortedList;
import javafx.collections.transformation.FilteredList;
import seedu.deliverymans.model.deliveryman.deliverymanstatus.UniqueStatusList;

/**
 * A list that primarily focuses on the status of the deliverymen.
 * Handles the statuses of the deliverymen.
 * Issues related to the current statuses of the deliverymen are directed here.
 * Not allowed to edit information regarding personal info of deliverymen.
 */
public class StatusManager {

    //private ObservableList<Deliveryman> statusList = SortedList<Deliveryman>(null);

    private final UniqueDeliverymanList deliverymen; // used for sorting later
    private final UniqueStatusList statuses;

    private ObservableList<Deliveryman> availableMen = FXCollections.observableArrayList();
    private ObservableList<Deliveryman> unavailableMen = FXCollections.observableArrayList();
    private ObservableList<Deliveryman> deliveringMen = FXCollections.observableArrayList();

    public StatusManager(UniqueDeliverymanList deliverymenList) {
        deliverymen = deliverymenList;
        statuses = new UniqueStatusList();
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
     *
     */
    public void updateStatusOf(Deliveryman deliveryman, String strStatus) {
        removePreviousStatus(deliveryman);
        assignStatusTagTo(deliveryman, strStatus);
    }

    /**
     * Replaces the status tag of a deliveryman with another one.
     */
    public void assignStatusTagTo(Deliveryman deliveryman, String strStatus) {
        switch (strStatus) {
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

    /**
     *
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
     * Lists all the deliverymen with their respective statuses.
     */
    public void listAll() {}

    /**
     * Returns a list of all available deliverymen.
     */
    public ObservableList<Deliveryman> listAvailableMen() {
        return availableMen;
    }

    /**
     * Returns a list of all unavailable deliverymen.
     */
    public List<Deliveryman> listUnavailableMen() {
        return unavailableMen;
    }

    /**
     * Returns a list of all delivering deliverymen.
     */
    public List<Deliveryman> listDeliveringMen() {
        return deliveringMen;
    }

}
