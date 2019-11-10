package seedu.deliverymans.model.database;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.model.deliveryman.deliverymanstatus.UniqueStatusList.DELIVERING_STATUS;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.model.deliveryman.UniqueDeliverymanList;
import seedu.deliverymans.model.deliveryman.deliverymanstatistics.StatisticsManager;
import seedu.deliverymans.model.deliveryman.deliverymanstatistics.StatisticsRecordCard;
import seedu.deliverymans.model.deliveryman.deliverymanstatus.StatusManager;
import seedu.deliverymans.model.deliveryman.exceptions.InvalidStatusChangeException;
import seedu.deliverymans.model.deliveryman.exceptions.NoMoreAvailableDeliverymanException;
import seedu.deliverymans.model.deliveryman.exceptions.UnableToDeleteDeliveringDeliverymanException;

/**
 * Wraps all Deliverymen data at the deliverymen-database level
 * Duplicates are not allowed (by .isSameDeliveryman comparison)
 */
public class DeliverymenDatabase implements ReadOnlyDeliverymenDatabase {

    private final UniqueDeliverymanList deliverymen;
    private final StatusManager statusManager;
    private final StatisticsManager statisticsManager;
    private ObservableList<Deliveryman> statusSortedList = FXCollections.observableArrayList();

    {
        deliverymen = new UniqueDeliverymanList();
        statusManager = new StatusManager();
        statisticsManager = new StatisticsManager();
    }

    public DeliverymenDatabase() {
    }

    /**
     * Creates a DeliverymenDatabase using the Deliverymen in the {@code toBeCopied}
     */
    public DeliverymenDatabase(ReadOnlyDeliverymenDatabase toBeCopied) {
        this();
        resetData(toBeCopied);
    }

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
        statusManager.initStatusLists(deliverymen);
    }

    // ========== Basic functions related to deliverymen ==========================================================

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
        statusManager.addUnavailableMan(man);
    }

    /**
     * Replaces the given deliveryman {@code target} in the list with {@code editedDeliveryman}.
     * {@code target} must exist in the deliveryman database.
     * The deliveryman identity of {@code editedDeliveryman} must not be the same as another existing deliveryman in the
     * deliverymen database.
     */
    public void setDeliveryman(Deliveryman target, Deliveryman editedDeliveryman) {
        requireNonNull(editedDeliveryman);
        deliverymen.setDeliveryman(target, editedDeliveryman);
        statusManager.removeDeliveryman(target);
        statusManager.addUnavailableMan(editedDeliveryman);
        statusManager.updateStatusOf(editedDeliveryman, target.getStatus().getDescription());
    }

    /**
     * Removes {@code key} from this {@code DeliverymenDatabase}.
     * {@code key} must exist in the deliverymen database.
     */
    public void removeDeliveryman(Deliveryman key) throws UnableToDeleteDeliveringDeliverymanException {
        if (key.getStatus().getDescription().equals(DELIVERING_STATUS)) {
            throw new UnableToDeleteDeliveringDeliverymanException();
        }
        deliverymen.remove(key);
        statusManager.removeDeliveryman(key);
    }

    // ========= Methods related to list command =================================================================

    /**
     * Lists all the available deliverymen;
     */
    public ObservableList<Deliveryman> getAvailableDeliverymenList() {
        return statusManager.listAvailableMen();
    }

    /**
     * Lists all the delivering deliverymen.
     */
    public ObservableList<Deliveryman> getDeliveringDeliverymenList() {
        return statusManager.listDeliveringMen();
    }

    /**
     * Lists all the unavailable deliverymen.
     */
    public ObservableList<Deliveryman> getUnavailableDeliverymenList() {
        return statusManager.listUnavailableMen();
    }

    /**
     * Returns true if the list contains an equivalent deliveryman as the given argument.
     */
    public boolean contains(Deliveryman toCheck) {
        requireNonNull(toCheck);
        return statusSortedList.stream().anyMatch(toCheck::isSameDeliveryman);
    }

    // ========== Methods related to Order ====================================================================

    /**
     * Retrieves the name of an available deliveryman for OrderManager for the purpose of delivering an order.
     */
    public Name getAvailableDeliveryman() throws NoMoreAvailableDeliverymanException {
        return statusManager.getAvailableDeliveryman().getName();
    }

    /**
     * Updates the deliveryman status after he has completed an order.
     */
    public void updateDeliverymanStatusAfterChangesToOrder(Name name) {
        getDeliverymenList().stream()
                .filter(d -> d.getName().equals(name))
                .findAny()
                .ifPresent(deliveryman -> deliverymen.setDeliveryman(deliveryman,
                        statusManager.updateDeliverymanStatusAfterChangesToOrder(deliveryman)));
    }

    /**
     * Switches the deliveryman status from AVAILABLE to UNAVAILABLE, or vice versa.
     */
    public void switchDeliverymanStatus(Deliveryman target) throws InvalidStatusChangeException {
        deliverymen.setDeliveryman(target, statusManager.switchDeliverymanStatus(target));
    }

    // ========== Methods related to Statistics ================================================================

    /**
     *
     * @return
     */
    public StatisticsRecordCard analyzeDeliverymenStatus() {
        return statisticsManager.analyzeStatusLists(statusManager.listAvailableMen(),
                statusManager.listUnavailableMen(), statusManager.listDeliveringMen());
    }

    // ========== util methods =================================================================================

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
