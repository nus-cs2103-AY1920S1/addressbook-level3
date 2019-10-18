package seedu.deliverymans.model.deliveryman;

import java.util.List;

/**
 * A list that primarily focuses on the status of the deliverymen.
 * Issues related to the current statuses of the deliverymen are directed here.
 * Not allowed to edit information regarding personal info of deliverymen.
 */
public class DeliverymanStatusList extends UniqueDeliverymanList {
    private UniqueDeliverymanList internalList;

    // List is not viable option. But used for now.
    private List<Deliveryman> availableMen;
    private List<Deliveryman> unavailableMen;
    private List<Deliveryman> deliveringMen;

    public DeliverymanStatusList(UniqueDeliverymanList unmodifiableList) {
        internalList = unmodifiableList;
    }

    /**
     * Lists all the deliverymen with their respective statuses.
     */
    public void listAll() {}

    /**
     * Lists all the available deliverymen (ie. available for orders to be assigned to them)
     * @return List of deliverymen who are ready to be assigned any food order.
     */
    public List<Deliveryman> listAvailableMen() {
        return availableMen;
    }

    /**
     * Lists all the unavailable deliverymen.
     */
    public List<Deliveryman> listUnavailableMen() {
        return unavailableMen;
    }

}
