package seedu.deliverymans.model.deliveryman;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.deliverymans.model.deliveryman.deliverymanstatus.UniqueStatusList;

/**
 * A list that primarily focuses on the status of the deliverymen.
 * Handles the statuses of the deliverymen.
 * Issues related to the current statuses of the deliverymen are directed here.
 * Not allowed to edit information regarding personal info of deliverymen.
 */
public class StatusManager {

    //private ObservableList<Deliveryman> statusList = SortedList<Deliveryman>(null);

    private final UniqueDeliverymanList deliverymen;
    private final UniqueStatusList statuses;

    private ObservableList<Deliveryman> availableMen;
    private ObservableList<Deliveryman> unavailableMen;
    private ObservableList<Deliveryman> deliveringMen;

    public StatusManager(UniqueDeliverymanList deliverymenList) {
        this.deliverymen = deliverymenList;
        this.statuses = new UniqueStatusList();
    }

    public void updateStatusOf(Deliveryman deliveryman, String strStatus) {
        removePreviousStatus(deliveryman);
        assignStatusTagTo(deliveryman, strStatus);
    }

    /**
     * Replaces the status tag of a deliveryman with another one.
     */
    public void assignStatusTagTo(Deliveryman deliveryman, String strStatus ) {
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
        }
    }

    /**
     * Lists all the deliverymen with their respective statuses.
     */
    public void listAll() {}

    /**
     * Returns a list of all available deliverymen.
     */
    public List<Deliveryman> listAvailableMen() {
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
