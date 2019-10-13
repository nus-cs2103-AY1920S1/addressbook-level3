package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalBooks.BOOK_2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.testutil.BookBuilder;

public class UniqueBookListTest {

    private final UniqueBookList uniqueBookList = new UniqueBookList();

    @Test
    public void contains_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookList.contains(null));
    }

    @Test
    public void contains_bookNotInList_returnsFalse() {
        assertFalse(uniqueBookList.contains(BOOK_1));
    }

    @Test
    public void contains_bookInList_returnsTrue() {
        uniqueBookList.add(BOOK_1);
        assertTrue(uniqueBookList.contains(BOOK_1));
    }

    @Test
    public void contains_bookWithSameIdentityFieldsInList_returnsTrue() {
        uniqueBookList.add(BOOK_1);
        Book editedBook1 = new BookBuilder(BOOK_1).withGenres(VALID_GENRE_ACTION)
                .build();
        assertTrue(uniqueBookList.contains(editedBook1));
    }

    @Test
    public void add_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookList.add(null));
    }

    @Test
    public void add_duplicateBook_throwsDuplicateBookException() {
        uniqueBookList.add(BOOK_1);
        assertThrows(DuplicateBookException.class, () -> uniqueBookList.add(BOOK_1));
    }

    @Test
    public void setBook_nullTargetBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookList.setBook(null, BOOK_1));
    }

    @Test
    public void setBook_nullEditedBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookList.setBook(BOOK_1, null));
    }

    @Test
    public void setBook_targetBookNotInList_throwsBookNotFoundException() {
        assertThrows(BookNotFoundException.class, () -> uniqueBookList.setBook(BOOK_1, BOOK_1));
    }

    @Test
    public void setBook_editedBookIsSameBook_success() {
        uniqueBookList.add(BOOK_1);
        uniqueBookList.setBook(BOOK_1, BOOK_1);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(BOOK_1);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setBook_editedBookHasSameIdentity_success() {
        uniqueBookList.add(BOOK_1);
        Book editedAlice = new BookBuilder(BOOK_1).withGenres(VALID_GENRE_ACTION)
                .build();
        uniqueBookList.setBook(BOOK_1, editedAlice);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(editedAlice);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setBook_editedBookHasDifferentIdentity_success() {
        uniqueBookList.add(BOOK_1);
        uniqueBookList.setBook(BOOK_1, BOOK_2);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(BOOK_2);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setBook_editedBookHasNonUniqueIdentity_throwsDuplicateBookException() {
        uniqueBookList.add(BOOK_1);
        uniqueBookList.add(BOOK_2);
        assertThrows(DuplicateBookException.class, () -> uniqueBookList.setBook(BOOK_1, BOOK_2));
    }

    @Test
    public void remove_nullBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookList.remove(null));
    }

    @Test
    public void remove_bookDoesNotExist_throwsBookNotFoundException() {
        assertThrows(BookNotFoundException.class, () -> uniqueBookList.remove(BOOK_1));
    }

    @Test
    public void remove_existingBook_removesBook() {
        uniqueBookList.add(BOOK_1);
        uniqueBookList.remove(BOOK_1);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setBooks_nullUniqueBookList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookList.setBooks((UniqueBookList) null));
    }

    @Test
    public void setBooks_uniqueBookList_replacesOwnListWithProvidedUniqueBookList() {
        uniqueBookList.add(BOOK_1);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(BOOK_2);
        uniqueBookList.setBooks(expectedUniqueBookList);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setBooks_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBookList.setBooks((List<Book>) null));
    }

    @Test
    public void setBooks_list_replacesOwnListWithProvidedList() {
        uniqueBookList.add(BOOK_1);
        List<Book> bookList = Collections.singletonList(BOOK_2);
        uniqueBookList.setBooks(bookList);
        UniqueBookList expectedUniqueBookList = new UniqueBookList();
        expectedUniqueBookList.add(BOOK_2);
        assertEquals(expectedUniqueBookList, uniqueBookList);
    }

    @Test
    public void setBooks_listWithDuplicateBooks_throwsDuplicateBookException() {
        List<Book> listWithDuplicateBooks = Arrays.asList(BOOK_1, BOOK_1);
        assertThrows(DuplicateBookException.class, () -> uniqueBookList.setBooks(listWithDuplicateBooks));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueBookList.asUnmodifiableObservableList().remove(0));
    }

}
