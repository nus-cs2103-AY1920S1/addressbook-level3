package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.borrower.Borrower;

/**
 * Wraps all data at the catalog level
 * Duplicates are not allowed (by .isSameBook comparison)
 */
public class BorrowerRecords implements ReadOnlyBorrowerRecords {

    // Placeholder for UniqueBorrowerList
    private ObservableList<Borrower> listOfBorrowers = FXCollections.observableArrayList();

    public BorrowerRecords(ReadOnlyBorrowerRecords toBeCopied) {
    }
    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public BorrowerRecords() {

    }

    public boolean hasBorrower(Borrower book) {
        return false;
    }

    public void addBorrower(Borrower book) {
        listOfBorrowers.add(book);
    }

    /**
     * Populate sample borrowers into the system.
     */
    public void populateBorrowers() {
        for (int i = 0; i < 10; i++) {
            listOfBorrowers.add(new Borrower("Lim Ah Meng" + i));
        }
    }

    @Override
    public ObservableList<Borrower> getBorrowerList() {
        return FXCollections.unmodifiableObservableList(listOfBorrowers);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BorrowerRecords // instanceof handles nulls
                && listOfBorrowers.equals(((BorrowerRecords) other).listOfBorrowers));
    }
}
