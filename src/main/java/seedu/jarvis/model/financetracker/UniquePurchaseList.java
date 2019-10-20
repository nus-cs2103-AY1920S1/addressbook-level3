package seedu.jarvis.model.financetracker;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.financetracker.exceptions.PurchaseNotFoundException;
import seedu.jarvis.model.financetracker.purchase.Purchase;

/**
 * A list of purchases that does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniquePurchaseList {

    private ObservableList<Purchase> internalPurchaseList = FXCollections.observableArrayList();
    private final ObservableList<Purchase> internalUnmodifiablePurchaseList =
            FXCollections.unmodifiableObservableList(internalPurchaseList);

    /**
     * Default constructor to be used when JARVIS starts up.
     */
    public UniquePurchaseList() {

    }

    /**
     * Constructor to be used if there is an existing list of purchases.
     */
    public UniquePurchaseList(ObservableList<Purchase> internalPurchaseList) {
        requireNonNull(internalPurchaseList);
        this.internalPurchaseList = internalPurchaseList;
    }

    public void setInternalPurchaseList(List<Purchase> purchaseList) {
        requireNonNull(purchaseList);
        this.internalPurchaseList.setAll(purchaseList);
    }

    public ObservableList<Purchase> getInternalPurchaseList() {
        return internalPurchaseList;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Purchase> asUnmodifiableObservableList() {
        return internalUnmodifiablePurchaseList;
    }

    /**
     * Returns the {@Purchase} based on its {@code Index}.
     */
    public Purchase getPurchase(Index index) throws CommandException {
        requireNonNull(index);
        if (index.getZeroBased() >= internalPurchaseList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PURCHASE_DISPLAYED_INDEX);
        }

        return internalPurchaseList.get(index.getZeroBased());
    }

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Purchase toCheck) {
        requireNonNull(toCheck);
        return internalPurchaseList.stream().anyMatch(toCheck::isSamePurchase);
    }

    /**
     * Returns the number of purchases in the current list.
     */
    public int size() {
        return internalPurchaseList.size();
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Purchase toAdd) {
        requireNonNull(toAdd);
        internalPurchaseList.add(toAdd);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Purchase toRemove) {
        requireNonNull(toRemove);
        if (!internalPurchaseList.remove(toRemove)) {
            throw new PurchaseNotFoundException();
        }

        internalPurchaseList.remove(toRemove);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePurchaseList // instanceof handles nulls
                && internalPurchaseList.equals(((UniquePurchaseList) other).internalPurchaseList));
    }

    @Override
    public int hashCode() {
        return internalPurchaseList.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are your purchases for the month!");
        for (Purchase purchase : internalPurchaseList) {
            sb.append(purchase);
            sb.append("\n");
        }
        return sb.toString();
    }
}
