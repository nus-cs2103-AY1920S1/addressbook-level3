package seedu.address.storage.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.catalog.JsonAdaptedBook.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalLoans.LOAN_1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Author;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.book.Title;

public class JsonAdaptedBookTest {
    private static final String INVALID_TITLE = "&HarryBotter";
    private static final String INVALID_SERIAL_NUMBER = "+651234a";
    private static final String INVALID_GENRE = "#friend";
    private static final String NO_LOAN = null;

    private static final String VALID_TITLE = BOOK_1.getTitle().toString();
    private static final String VALID_SERIAL_NUMBER = BOOK_1.getSerialNumber().toString();
    private static final String VALID_AUTHOR = BOOK_1.getAuthor().toString();
    private static final List<JsonAdaptedGenre> VALID_GENRES = BOOK_1.getGenres().stream()
            .map(JsonAdaptedGenre::new)
            .collect(Collectors.toList());
    private static final List<String> VALID_LOAN_HISTORY = new ArrayList<>();

    @BeforeAll
    public static void setUp() {
        VALID_LOAN_HISTORY.add(LOAN_1.getLoanId().toString());
    }

    @Test
    public void toModelType_validBookDetails_returnsBook() throws Exception {
        JsonAdaptedBook book = new JsonAdaptedBook(BOOK_1);
        assertEquals(BOOK_1, book.toModelType());
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        JsonAdaptedBook book =
                new JsonAdaptedBook(
                        INVALID_TITLE, VALID_SERIAL_NUMBER, VALID_AUTHOR, NO_LOAN, VALID_GENRES, VALID_LOAN_HISTORY);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        JsonAdaptedBook book = new JsonAdaptedBook(
                null, VALID_SERIAL_NUMBER, VALID_AUTHOR, NO_LOAN, VALID_GENRES, VALID_LOAN_HISTORY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidSerialNumber_throwsIllegalValueException() {
        JsonAdaptedBook book =
                new JsonAdaptedBook(
                        VALID_TITLE, INVALID_SERIAL_NUMBER, VALID_AUTHOR, NO_LOAN, VALID_GENRES, VALID_LOAN_HISTORY);
        String expectedMessage = SerialNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullSerialNumber_throwsIllegalValueException() {
        JsonAdaptedBook book = new JsonAdaptedBook(
                VALID_TITLE, null, VALID_AUTHOR, NO_LOAN, VALID_GENRES, VALID_LOAN_HISTORY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, SerialNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_nullAuthor_throwsIllegalValueException() {
        JsonAdaptedBook book = new JsonAdaptedBook(
                VALID_TITLE, VALID_SERIAL_NUMBER, null, NO_LOAN, VALID_GENRES, VALID_LOAN_HISTORY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Author.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, book::toModelType);
    }

    @Test
    public void toModelType_invalidGenres_throwsIllegalValueException() {
        List<JsonAdaptedGenre> invalidGenres = new ArrayList<>(VALID_GENRES);
        invalidGenres.add(new JsonAdaptedGenre(INVALID_GENRE));
        JsonAdaptedBook book =
                new JsonAdaptedBook(
                        VALID_TITLE, VALID_SERIAL_NUMBER, VALID_AUTHOR, NO_LOAN, invalidGenres, VALID_LOAN_HISTORY);
        assertThrows(IllegalValueException.class, book::toModelType);
    }

}
