package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;

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
    private HashMap<BorrowerId, Borrower> borrowersMap = new HashMap<>();

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

    /**
     * Checks if a borrower is in the borrower records.
     *
     * @param borrower Borrower to be checked.
     */
    public boolean hasBorrower(Borrower borrower) {
        return listOfBorrowers.stream().anyMatch(current -> current.getPhone().equals(borrower.getPhone())
                || current.getEmail().equals(borrower.getEmail()));
    }

    /**
     * Adds a borrower to the borrower records.
     *
     * @param borrower Borrower to be added.
     */
    public void addBorrower(Borrower borrower) {
        listOfBorrowers.add(borrower);
        borrowersMap.put(borrower.getBorrowerId(), borrower);
    }

    private void resetData(ReadOnlyBorrowerRecords newData) {
        requireNonNull(newData);
        setBorrowers(FXCollections.observableArrayList(newData.getBorrowerList()));
    }

    private void setBorrowers(ObservableList<Borrower> borrowerList) {
        this.listOfBorrowers = borrowerList;
        this.borrowersMap = new HashMap<>();
        borrowerList.stream().forEach(borrower -> borrowersMap.put(borrower.getBorrowerId(), borrower));
        BorrowerIdGenerator.setBorrowers(this);
    }

    /**
     * to check if borrower id exists.
     * @param id is the borrower id.
     * @return a boolean true or false of whether the id exists.
     */
    boolean checkIfBorrowerIdExists(BorrowerId id) {
        requireNonNull(id);
        return borrowersMap.containsKey(id);
    }

    /**
     * Returns a borrower based on its BorrowerId.
     *
     * @param id the <code>BorrowerId</code> of the <code>Borrower</code>
     * @return <code>Borrower</code> which corresponds to the given <code>BorrowerId</code>
     * @throws NullPointerException if borrower is not present in the borrower records.
     */
    public Borrower getBorrowerFromId(BorrowerId id) throws NullPointerException {
        if (!borrowersMap.containsKey(id)) {
            throw new NullPointerException("Borrower does not exists");
        }
        return borrowersMap.get(id);
    }

    @Override
    public ObservableList<Borrower> getBorrowerList() {
        return FXCollections.unmodifiableObservableList(listOfBorrowers);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BorrowerRecords // instanceof handles nulls
                && listOfBorrowers.equals(((BorrowerRecords) other).listOfBorrowers)
                && borrowersMap.equals(((BorrowerRecords) other).borrowersMap));
    }

    int getSize() {
        return listOfBorrowers.size();
    }
}
