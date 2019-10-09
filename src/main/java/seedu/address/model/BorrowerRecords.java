package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;

/**
 * Wraps all data at the catalog level
 * Duplicates are not allowed (by .equals comparison)
 */
public class BorrowerRecords implements ReadOnlyBorrowerRecords {

    // Placeholder for UniqueBorrowerList
    private ObservableList<Borrower> listOfBorrowers = FXCollections.observableArrayList();

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public BorrowerRecords() {
    }

    /**
     * Creates BorrowerRecords
     * @param toBeCopied is the records to be copied
     */
    public BorrowerRecords(ReadOnlyBorrowerRecords toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public boolean hasBorrower(Borrower borrower) {
        return listOfBorrowers.stream().anyMatch(item -> item.isSameBorrower(borrower));
    }

    public void addBorrower(Borrower borrower) {
        listOfBorrowers.add(borrower);
    }

    private void resetData(ReadOnlyBorrowerRecords newData) {
        requireNonNull(newData);

        setBorrowers(newData.getBorrowerList());
    }

    private void setBorrowers(ObservableList<Borrower> borrowerList) {
        this.listOfBorrowers = borrowerList;
    }

    /**
     * to check if borrower id exists.
     * @param id is the borrower id.
     * @return a boolean true or false of whether the id exists.
     */
    boolean checkIfBorrowerIdExists(BorrowerId id) {
        requireNonNull(id);
        return listOfBorrowers.stream().map(Borrower::getBorrowerId).anyMatch(value -> value.equals(id));
    }

    /**
     * Returns a borrower based on its BorrowerId.
     *
     * @param id the <code>BorrowerId</code> of the <code>Borrower</code>
     * @return <code>Borrower</code> which corresponds to the given <code>BorrowerId</code>
     * @throws NullPointerException if borrower is not present in the borrower records.
     */
    public Borrower getBorrowerFromId(BorrowerId id) throws NullPointerException {
        if (!checkIfBorrowerIdExists(id)) {
            throw new NullPointerException();
        }
        long numberOfBorrowers = listOfBorrowers
                .stream()
                .filter(borrower -> borrower.getBorrowerId().equals(id))
                .count();
        assert (numberOfBorrowers == 1) : "Duplicate borrowers";
        return listOfBorrowers.stream()
                .filter(borrower -> borrower.getBorrowerId().equals(id))
                .collect(Collectors.toList())
                .get(0);
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
