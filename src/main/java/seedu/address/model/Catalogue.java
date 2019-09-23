package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.book.Book;

public class Catalogue implements ReadOnlyCatalogue {

    // Placeholder for UniqueBookList
    ObservableList<Book> listOfBooks = FXCollections.observableArrayList();

    public Catalogue(ReadOnlyCatalogue toBeCopied) {
    }

    public Catalogue() {

    }

    public boolean hasBook(Book book) {
        return false;
    }

    public void addBook(Book book) {
        listOfBooks.add(book);
    }

    public void populateBooks() {
        for (int i = 0; i < 10; i++) {
            listOfBooks.add(new Book("Harry Potter" + i));
        }
    }

    @Override
    public ObservableList<Book> getBookList() {
        return FXCollections.unmodifiableObservableList(listOfBooks);
    }
}
