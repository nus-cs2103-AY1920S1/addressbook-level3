package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.borrower.Borrower;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyBorrowerRecords {

    ObservableList<Borrower> getBorrowerList();

}
