package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.borrower.Borrower;

public interface ReadOnlyBorrowerRecords {
    ObservableList<Borrower> getBorrowerList();
}
