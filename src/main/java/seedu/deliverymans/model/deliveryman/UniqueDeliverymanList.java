package seedu.deliverymans.model.deliveryman;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.deliverymans.model.deliveryman.exceptions.DeliverymanNotFoundException;
import seedu.deliverymans.model.deliveryman.exceptions.DuplicateDeliverymanException;

/**
 * A unique list of deliverymen that does not allow for duplicate deliverymen.
 * A deliveryman is considered unique by comparing using {@code Deliveryman#isSameDeliveryman(Deliveryman)}. As such,
 * adding and updating of deliverymen uses Deliveryman#isSameDeliveryman(Deliveryman) for equality so as to ensure that
 * the deliveryman being added or updated is unique in terms of identity in the UniqueDeliverymenList. However, the
 * removal of a deliveryman uses Deliveryman#equals(Object) so as to ensure that the deliveryman with exactly the same
 * fields will be removed.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueDeliverymanList implements Iterable<Deliveryman> {

    private final ObservableList<Deliveryman> internalList = FXCollections.observableArrayList();
    private final ObservableList<Deliveryman> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Deliveryman> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns true if the list contains an equivalent deliveryman as the given argument.
     */
    public boolean contains(Deliveryman toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDeliveryman);
    }

    /**
     * Adds a deliveryman to the list.
     * The deliveryman must not already exist in the list.
     */
    public void add(Deliveryman toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDeliverymanException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the deliveryman {@code target} in the list with {@code editedDeliveryman}.
     * {@code target} must exist in the list.
     * The deliveryman identity of {@code editedDeliveryman} must not be the same as another existing deliveryman in the
     * list.
     */
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

    /**
     * Removes the equivalent deliveryman from the list.
     * The deliveryman must exist in the list.
     */
    public void remove(Deliveryman toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DeliverymanNotFoundException();
        }
    }

    public void setDeliverymen(UniqueDeliverymanList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code deliverymen}.
     * {@code deliverymen} must not contain duplicate deliverymen.
     */
    public void setDeliverymen(List<Deliveryman> deliverymen) {
        requireAllNonNull(deliverymen);
        if (!deliverymenAreUnique(deliverymen)) {
            throw new DuplicateDeliverymanException();
        }

        internalList.setAll(deliverymen);
    }

    @Override
    public Iterator<Deliveryman> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDeliverymanList // instanceof handles nulls
                && internalList.equals(((UniqueDeliverymanList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code deliverymen} contains only unique deliverymen.
     */
    private boolean deliverymenAreUnique(List<Deliveryman> deliverymen) {
        for (int i = 0; i < deliverymen.size() - 1; i++) {
            for (int j = i + 1; j < deliverymen.size(); j++) {
                if (deliverymen.get(i).isSameDeliveryman(deliverymen.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
