package seedu.deliverymans.model.deliveryman;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.deliverymans.model.deliveryman.exceptions.DeliverymanNotFoundException;
import seedu.deliverymans.model.deliveryman.exceptions.DuplicateDeliverymanException;

/**
 * A unique list of deliverymen that does not allow for duplicate deliverymen.
 */
public class UniqueDeliverymanList {
    private final ObservableList<Deliveryman> internalList = FXCollections.observableArrayList();
    private final ObservableList<Deliveryman> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);


    public ObservableList<Deliveryman> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public void setDeliverymen(List<Deliveryman> deliverymen) {

    }

    public boolean contains(Deliveryman man) {
        return internalList.contains(man);
    }

    public void add(Deliveryman man) {
        internalList.add(man);
    }

    public void setDeliveryman(Deliveryman target, Deliveryman editedDeliveryman) {
        requireNonNull(editedDeliveryman);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DeliverymanNotFoundException();
        }

        if (!target.isSameDeliveryman(editedDeliveryman) && contains(editedDeliveryman)) {
            throw new DuplicateDeliverymanException();
        }

        internalList.set(index, editedDeliveryman);
    }

    public void remove(Deliveryman key) {
    }
}
