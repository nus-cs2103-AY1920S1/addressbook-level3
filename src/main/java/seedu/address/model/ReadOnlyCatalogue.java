package seedu.address.model;

import java.util.Arrays;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;

public interface ReadOnlyCatalogue {
    ObservableList<Book> getBookList();
}
