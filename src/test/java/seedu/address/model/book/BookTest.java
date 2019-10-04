package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BORROWER_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOOK_2;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalBooks.BOOK_2;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DateUtil;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.loan.Loan;
import seedu.address.testutil.BookBuilder;

public class BookTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Book book = new BookBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> book.getGenres().remove(0));
    }

    @Test
    public void noGenres_toString_noTagFieldDisplayed() {
        Book book = new BookBuilder().withGenres().build();
        boolean containsTag = book.toString().contains("Genres");
        assertFalse(containsTag);
    }

    @Test
    public void haveGenres_toString_haveTagFieldDisplayed() {
        Book book = new BookBuilder().withGenres("Action").build();
        boolean containsTag = book.toString().contains("Genres");
        assertTrue(containsTag);
    }

    @Test
    public void isSameBook() {
        // same object -> returns true
        assertTrue(BOOK_1.equals(BOOK_1));

        // null -> returns false
        assertFalse(BOOK_1.equals(null));

        // different serial number and author -> returns false
        Book editedBook1 = new BookBuilder(BOOK_1).withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2)
                .withAuthor(VALID_AUTHOR_BOOK_2).build();
        assertFalse(BOOK_1.equals(editedBook1));

        // different title -> returns false
        editedBook1 = new BookBuilder(BOOK_1).withTitle(VALID_TITLE_BOOK_2)
                .withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2).build();
        assertFalse(BOOK_1.equals(editedBook1));

        // same name, same phone, different attributes -> returns true
        editedBook1 = new BookBuilder(BOOK_1).withAuthor(VALID_AUTHOR_BOOK_2)
                .withGenres(VALID_GENRE_ACTION).build();
        assertTrue(BOOK_1.equals(editedBook1));

        // same name, same email, different attributes -> returns true
        editedBook1 = new BookBuilder(BOOK_1).withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2)
                .withGenres(VALID_GENRE_ACTION).build();
        assertTrue(BOOK_2.equals(editedBook1));

        // same title, same serial number, same author, different attributes -> returns true
        editedBook1 = new BookBuilder(BOOK_1).withGenres(VALID_GENRE_ACTION).build();
        assertTrue(BOOK_1.equals(editedBook1));
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

        // different name and serial number -> returns false
        Book editedA = new BookBuilder(BOOK_1).withTitle(VALID_TITLE_BOOK_2)
                .withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2).build();
        assertFalse(BOOK_1.equals(editedA));

        // different serial number -> returns false
        editedA = new BookBuilder(BOOK_1).withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2).build();
        assertFalse(BOOK_1.equals(editedA));

        // different author -> returns false
        editedA = new BookBuilder(BOOK_1).withAuthor(VALID_AUTHOR_BOOK_2)
                .withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2).build();
        assertFalse(BOOK_1.equals(editedA));

        // different genres -> returns false
        editedA = new BookBuilder(BOOK_1).withGenres(VALID_GENRE_ACTION)
                .withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2).build();
        assertFalse(BOOK_1.equals(editedA));
    }

    @Test
    public void loanTo_bookIsAvailable_loanSuccess() {
        Book toBeLoaned = new BookBuilder(BOOK_1).build();
        assertFalse(toBeLoaned.isCurrentlyLoanedOut());

        BorrowerId currentBorrowerId = new BorrowerId(VALID_BORROWER_ID);
        Loan expectedLoan = new Loan(
                toBeLoaned.getSerialNumber(),
                currentBorrowerId,
                DateUtil.getTodayDate(),
                DateUtil.getTodayPlusDays(30));

        Book expectedBook = new BookBuilder(BOOK_1).withLoan(expectedLoan).build();
        toBeLoaned.loanTo(currentBorrowerId, DateUtil.getTodayDate(), DateUtil.getTodayPlusDays(30));
        assertTrue(toBeLoaned.equals(expectedBook));
    }

    @Test
    public void hashCode_sameBookSameHashCode_assertTrue() {
        Book book1 = new BookBuilder(BOOK_1).build();
        Book book2 = new BookBuilder(BOOK_1).build();
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    public void getSerialNumber_sameBook_assertTrue() {
        Book book1 = new BookBuilder(BOOK_1).build();
        assertTrue(book1.getSerialNumber().equals(new SerialNumber(VALID_SERIAL_NUMBER_BOOK_1)));
    }

    @Test
    public void toString_book() {
        Book book1 = new BookBuilder(BOOK_1).build();
        StringBuilder genres = new StringBuilder();
        book1.getGenres().forEach(genre -> genres.append(genre + " "));
        String stringRep = book1.getTitle()
                + " Serial Number: " + book1.getSerialNumber()
                + " Author: " + book1.getAuthor()
                + " Genres: " + genres.toString();

        String temp = book1.toString();
        assertTrue(book1.toString().equals(stringRep));
    }
}
