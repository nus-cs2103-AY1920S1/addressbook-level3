package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalBooks.BOOK_2;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BookBuilder;

public class BookTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Book book = new BookBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> book.getGenres().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(BOOK_1.isSameBook(BOOK_1));

        // null -> returns false
        assertFalse(BOOK_1.isSameBook(null));

        // different serial number and author -> returns false
        Book editedBook1 = new BookBuilder(BOOK_1).withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2).withAuthor(VALID_AUTHOR_BOOK_2).build();
        assertFalse(BOOK_1.isSameBook(editedBook1));

        // different title -> returns false
        editedBook1 = new BookBuilder(BOOK_1).withTitle(VALID_TITLE_BOOK_2).build();
        assertFalse(BOOK_1.isSameBook(editedBook1));

        // same name, same phone, different attributes -> returns true
        editedBook1 = new BookBuilder(BOOK_1).withAuthor(VALID_AUTHOR_BOOK_2)
                .withGenres(VALID_GENRE_ACTION).build();
        assertTrue(BOOK_1.isSameBook(editedBook1));

        // same name, same email, different attributes -> returns true
        editedBook1 = new BookBuilder(BOOK_1).withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2)
                .withGenres(VALID_GENRE_ACTION).build();
        assertTrue(BOOK_1.isSameBook(editedBook1));

        // same title, same serial number, same author, different attributes -> returns true
        editedBook1 = new BookBuilder(BOOK_1).withGenres(VALID_GENRE_ACTION).build();
        assertTrue(BOOK_1.isSameBook(editedBook1));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Book aliceCopy = new BookBuilder(BOOK_1).build();
        assertTrue(BOOK_1.equals(aliceCopy));

        // same object -> returns true
        assertTrue(BOOK_1.equals(BOOK_1));

        // null -> returns false
        assertFalse(BOOK_1.equals(null));

        // different type -> returns false
        assertFalse(BOOK_1.equals(5));

        // different person -> returns false
        assertFalse(BOOK_1.equals(BOOK_2));

        // different name -> returns false
        Book editedAlice = new BookBuilder(BOOK_1).withTitle(VALID_TITLE_BOOK_2).build();
        assertFalse(BOOK_1.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new BookBuilder(BOOK_1).withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2).build();
        assertFalse(BOOK_1.equals(editedAlice));

        // different email -> returns false
        editedAlice = new BookBuilder(BOOK_1).withAuthor(VALID_AUTHOR_BOOK_2).build();
        assertFalse(BOOK_1.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new BookBuilder(BOOK_1).withGenres(VALID_GENRE_ACTION).build();
        assertFalse(BOOK_1.equals(editedAlice));
    }
}
