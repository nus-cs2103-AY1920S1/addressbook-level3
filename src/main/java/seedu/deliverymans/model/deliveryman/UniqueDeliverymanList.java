package seedu.deliverymans.model.deliveryman;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * A unique list of deliverymen that does not allow for duplicate deliverymen.
 */
public class UniqueDeliverymanList {
    private ObservableList<Deliveryman> list;

    public ObservableList<Deliveryman> asUnmodifiableObservableList() {
        return list;
    }

    public void setDeliverymen(List<Deliveryman> deliverymen) {
    }

    public boolean contains(Deliveryman man) {
        return list.contains(man);
    }

    public void add(Deliveryman man) {
        list.add(man);
    }

    public ObservableList<Deliveryman> getDeliverymenList() {
        return list;
    }

    public void setDeliveryman(Deliveryman target, Deliveryman editedDeliveryman) {
    }

    public void remove(Deliveryman key) {
    }
}
