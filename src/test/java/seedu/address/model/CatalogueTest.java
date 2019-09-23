package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.testutil.BookBuilder;

public class CatalogueTest {

    private final Catalogue catalogue = new Catalogue();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), catalogue.getBookList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> catalogue.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        Catalogue newData = getTypicalCatalogue();
        catalogue.resetData(newData);
        assertEquals(newData, catalogue);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Book editedAlice = new BookBuilder(BOOK_1).withGenres(VALID_GENRE_ACTION)
                .build();
        List<Book> newBooks = Arrays.asList(BOOK_1, editedAlice);
        CatalogueStub newData = new CatalogueStub(newBooks);

        assertThrows(DuplicateBookException.class, () -> catalogue.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> catalogue.hasBook(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(catalogue.hasBook(BOOK_1));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        catalogue.addBook(BOOK_1);
        assertTrue(catalogue.hasBook(BOOK_1));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        catalogue.addBook(BOOK_1);
        Book editedAlice = new BookBuilder(BOOK_1).withGenres(VALID_GENRE_ACTION)
                .build();
        assertTrue(catalogue.hasBook(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> catalogue.getBookList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class CatalogueStub implements ReadOnlyCatalogue {
        private final ObservableList<Book> books = FXCollections.observableArrayList();

        CatalogueStub(Collection<Book> books) {
            this.books.setAll(books);
        }

        @Override
        public ObservableList<Book> getBookList() {
            return books;
        }
    }

}
