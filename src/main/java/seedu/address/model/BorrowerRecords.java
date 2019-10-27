package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.borrower.BorrowerIdGenerator;
import seedu.address.model.borrower.exceptions.BorrowerNotFoundException;
import seedu.address.model.borrower.exceptions.DuplicateBorrowerException;

/**
 * Wraps all data at the catalog level
 * Duplicates are not allowed (by .equals comparison)
 */
public class BorrowerRecords implements ReadOnlyBorrowerRecords {

    // Placeholder for UniqueBorrowerList
    private ObservableList<Borrower> listOfBorrowers = FXCollections.observableArrayList();
    private HashMap<BorrowerId, Borrower> borrowersMap = new HashMap<>();

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
    public boolean checkIfBorrowerIdExists(BorrowerId id) {
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
            throw new NullPointerException("Borrower " + id.toString() + " does not exists");
        }
        return borrowersMap.get(id);
    }

    /**
     * Returns true if the list contains an equivalent borrower as the given argument.
     */
    public boolean listContains(Borrower toCheck) {
        requireNonNull(toCheck);
        return listOfBorrowers.stream().anyMatch(toCheck::equals);
    }

    /**
     * Replaces the borrower {@code target} in the list with {@code editedBorrower}.
     * {@code target} must exist in the list.
     * The borrower identity of {@code editedBorrower} must not be the same as another existing borrower in the list.
     */
    public void setBorrower(Borrower target, Borrower editedBorrower) {
        requireAllNonNull(target, editedBorrower);
        int index = listOfBorrowers.indexOf(target);
        if (index == -1) {
            throw new BorrowerNotFoundException();
        }
        if (!target.equals(editedBorrower) && listContains(editedBorrower)) {
            throw new DuplicateBorrowerException();
        }
        listOfBorrowers.set(index, editedBorrower);

        borrowersMap.remove(target.getBorrowerId());
        borrowersMap.put(editedBorrower.getBorrowerId(), editedBorrower);
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

    public int getSize() {
        return listOfBorrowers.size();
    }

    /**
     * Checks if a borrower has duplicated phone or email in the borrower records.
     *
     * @param editedBorrower Borrower to be checked.
     */
    public boolean hasDuplicateBorrower(Borrower editedBorrower) {
        return listOfBorrowers.stream().anyMatch(current ->
                current.getBorrowerId() != editedBorrower.getBorrowerId()
                        && (current.getPhone().equals(editedBorrower.getPhone())
                        || current.getEmail().equals(editedBorrower.getEmail())));
    }

    /**
     * Removes a borrower from the borrower records.
     *
     * @param borrower Borrower to be removed.
     */
    public void removeBorrower(Borrower borrower) {
        listOfBorrowers.remove(borrower);
        borrowersMap.remove(borrower.getBorrowerId());
    }
}
