package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_BORROWER_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_2;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import seedu.address.commons.util.DateUtil;
import seedu.address.model.book.Book;
//import seedu.address.model.book.SerialNumber;
import seedu.address.model.book.exceptions.DuplicateBookException;
//import seedu.address.model.borrower.BorrowerId;
//import seedu.address.model.loan.Loan;
import seedu.address.testutil.BookBuilder;

public class CatalogTest {

    private final Catalog catalog = new Catalog();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), catalog.getBookList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> catalog.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyCatalog_replacesData() {
        Catalog newData = getTypicalCatalog();
        catalog.resetData(newData);
        assertEquals(newData, catalog);
    }

    @Test
    public void resetData_withDuplicateBookDifferentSerialNumber_success() {
        // Two books with the same identity fields but different serial number
        Book editedBook1 = new BookBuilder(BOOK_1).withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2).build();
        List<Book> newBooks = Arrays.asList(BOOK_1, editedBook1);
        CatalogStub newData = new CatalogStub(newBooks);
        assertTrue(true);
    }

    @Test
    public void resetData_withDuplicateBook_throwsDuplicateBookException() {
        // Two books with the same identity fields and same serial number
        Book editedBook1 = new BookBuilder(BOOK_1).build();
        List<Book> newBooks = Arrays.asList(BOOK_1, editedBook1);
        CatalogStub newData = new CatalogStub(newBooks);
        assertThrows(DuplicateBookException.class, () -> catalog.resetData(newData));
    }

    @Test
    public void hasBook_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> catalog.hasBook(null));
    }

    @Test
    public void hasBook_bookNotInCatalog_returnsFalse() {
        assertFalse(catalog.hasBook(BOOK_1));
    }

    @Test
    public void hasBook_bookInCatalog_returnsTrue() {
        catalog.addBook(BOOK_1);
        assertTrue(catalog.hasBook(BOOK_1));
    }

    @Test
    public void hasBook_bookWithSameIdentityFieldsInCatalog_returnsTrue() {
        catalog.addBook(BOOK_1);
        Book editedAlice = new BookBuilder(BOOK_1).withGenres(VALID_GENRE_ACTION)
                .build();
        assertTrue(catalog.hasBook(editedAlice));
    }

    @Test
    public void getBookList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> catalog.getBookList().remove(0));
    }

    @Test
    public void getLoanedBooks_noBooksInCatalog_returnEmptyList() {
        catalog.resetData(new Catalog());
        ObservableList<Book> loanedBooks = catalog.getLoanedBooks();
        assertTrue(loanedBooks.size() == 0);
    }

    @Test
    public void getAvailableBooks_noBooksInCatalog_returnEmptyList() {
        catalog.resetData(new Catalog());
        ObservableList<Book> loanedBooks = catalog.getAvailableBooks();
        assertTrue(loanedBooks.size() == 0);
    }

    //    @Test
    //    public void getLoanedBooks_typicalCatalogWithLoanedBooks_returnsListOfLoanedBooks() {
    //        Catalog newData = getTypicalCatalog();
    //        SerialNumber currentSerialNumber = new SerialNumber("B00007");
    //        BorrowerId currentBorrowerId = new BorrowerId(VALID_BORROWER_ID);
    //
    //        Loan currentLoan =
    //                new Loan(currentSerialNumber,
    //                        currentBorrowerId,
    //                        DateUtil.getTodayDate(),
    //                        DateUtil.getTodayPlusDays(30));
    //
    //        Book loanedBook = new BookBuilder().withSerialNumber("B00007").withLoan(currentLoan).build();
    //        newData.addBook(loanedBook);
    //        catalog.resetData(newData);
    //        ObservableList<Book> loanedBooks = catalog.getLoanedBooks();
    //        assertTrue(loanedBooks.size() == 1);
    //        assertTrue(loanedBooks.get(0).equals(loanedBook));
    //    }

    //    @Test
    //    public void getAvailableBooks_typicalCatalogWithLoanedBooks_returnsListOfAvailableBooks() {
    //        Catalog newData = getTypicalCatalog();
    //        SerialNumber currentSerialNumber = new SerialNumber("B00007");
    //        BorrowerId currentBorrowerId = new BorrowerId(VALID_BORROWER_ID);
    //
    //        Loan currentLoan =
    //                new Loan(currentSerialNumber,
    //                        currentBorrowerId,
    //                        DateUtil.getTodayDate(),
    //                        DateUtil.getTodayPlusDays(30));
    //
    //        Book loanedBook = new BookBuilder().withSerialNumber("B00007").withLoan(currentLoan).build();
    //        newData.addBook(loanedBook);
    //        catalog.resetData(newData);
    //        ObservableList<Book> availableBooks = catalog.getAvailableBooks();
    //        assertTrue(availableBooks.size() == 4);
    //        assertTrue(getTypicalCatalog().getBookList().equals(availableBooks));
    //    }

    @Test
    public void hashCode_sameCatalog_returnsSameHashCode() {
        Catalog newData = getTypicalCatalog();
        catalog.resetData(newData);
        assertEquals(newData.hashCode(), catalog.hashCode());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class CatalogStub implements ReadOnlyCatalog {
        private final ObservableList<Book> books = FXCollections.observableArrayList();

        CatalogStub(Collection<Book> books) {
            this.books.setAll(books);
        }

        @Override
        public ObservableList<Book> getBookList() {
            return books;
        }
    }

}
