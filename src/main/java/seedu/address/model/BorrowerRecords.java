package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.borrower.Borrower;

public class BorrowerRecords implements ReadOnlyBorrowerRecords {

    // Placeholder for UniqueBorrowerList
    ObservableList<Borrower> listOfBorrowers = FXCollections.observableArrayList();

    public BorrowerRecords(ReadOnlyBorrowerRecords toBeCopied) {
    }

    public BorrowerRecords() {

    }

    public boolean hasBorrower(Borrower book) {
        return false;
    }

    public void addBorrower(Borrower book) {
        listOfBorrowers.add(book);
    }

    public void populateBorrowers() {
        for (int i = 0; i < 10; i++) {
            listOfBorrowers.add(new Borrower("Lim Ah Meng" + i));
        }
    }

    @Override
    public ObservableList<Borrower> getBorrowerList() {
        return FXCollections.unmodifiableObservableList(listOfBorrowers);
    }
}
