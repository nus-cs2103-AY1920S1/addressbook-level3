package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_FICTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_NONFICTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOOK_2;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BookBuilder;

public class BookPredicateTest {

    @Test
    public void isValid() {
        // has no conditions -> false
        BookPredicate predicate = new BookPredicate();
        assertFalse(predicate.isValid());

        // has title condition -> true
        predicate.setTitle(VALID_TITLE_BOOK_1);
        assertTrue(predicate.isValid());

        // has author condition -> true
        predicate = new BookPredicate().setAuthor(VALID_AUTHOR_BOOK_1);
        assertTrue(predicate.isValid());

        // has serialNumber condition -> true
        predicate = new BookPredicate().setSerialNumber(VALID_SERIAL_NUMBER_BOOK_1);
        assertTrue(predicate.isValid());

        // has genres condition -> true
        predicate = new BookPredicate().setGenres(VALID_GENRE_FICTION);
        assertTrue(predicate.isValid());
    }

    @Test
    public void equals() {

        BookPredicate predicate1 = new BookPredicate().setTitle(VALID_TITLE_BOOK_1);
        BookPredicate predicate2 = new BookPredicate().setTitle(VALID_TITLE_BOOK_2);

        // same object -> returns true
        assertTrue(predicate1.equals(predicate1));

        // null -> returns false
        assertFalse(predicate1.equals(null));

        // same title condition -> returns true
        BookPredicate predicate1Copy = new BookPredicate().setTitle(VALID_TITLE_BOOK_1);
        assertTrue(predicate1.equals(predicate1Copy));

        // different title condition -> returns false
        assertFalse(predicate1.equals(predicate2));

        predicate1.setAuthor(VALID_AUTHOR_BOOK_1);
        predicate1Copy.setAuthor(VALID_AUTHOR_BOOK_1);
        predicate2.setAuthor(VALID_AUTHOR_BOOK_2);

        // same author condition -> returns true
        assertTrue(predicate1.equals(predicate1Copy));

        // different author condition -> returns false
        assertFalse(predicate1.equals(predicate2));

        predicate1.setSerialNumber(VALID_SERIAL_NUMBER_BOOK_1);
        predicate1Copy.setSerialNumber(VALID_SERIAL_NUMBER_BOOK_1);
        predicate2.setSerialNumber(VALID_SERIAL_NUMBER_BOOK_2);

        // same serialNumber condition -> returns true
        assertTrue(predicate1.equals(predicate1Copy));

        // different serialNumber condition -> returns false
        assertFalse(predicate1.equals(predicate2));

        predicate1.setGenres(VALID_GENRE_FICTION, VALID_GENRE_ACTION);
        predicate1Copy.setGenres(VALID_GENRE_ACTION, VALID_GENRE_FICTION);
        predicate2.setGenres(VALID_GENRE_ACTION, VALID_GENRE_NONFICTION);

        // same genres condition, different order -> returns true
        assertTrue(predicate1.equals(predicate1Copy));

        // different genres condition -> returns false
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void test_titleCondition_returnsTrue() {
        // One keyword
        BookPredicate predicate = new BookPredicate().setTitle("Harry");
        assertTrue(predicate.test(new BookBuilder().withTitle("Harry Bob").build()));

        // Multiple keywords
        predicate = new BookPredicate().setTitle("Harry Bob");
        assertTrue(predicate.test(new BookBuilder().withTitle("Harry Bob").build()));

        // Mixed-case keywords
        predicate = new BookPredicate().setTitle("haRry bOB");
        assertTrue(predicate.test(new BookBuilder().withTitle("Harry Bob").build()));
    }

    @Test
    public void test_titleCondition_returnsFalse() {
        // Zero keywords
        assertThrows(IllegalArgumentException.class, () -> new BookPredicate().setTitle(""));

        // white space keywords
        assertThrows(IllegalArgumentException.class, () -> new BookPredicate().setTitle("  "));

        // Non-matching keyword
        BookPredicate predicate = new BookPredicate().setTitle("Carol");
        assertFalse(predicate.test(new BookBuilder().withTitle("Harry Bob").build()));

        // Only one matching keyword
        predicate = new BookPredicate().setTitle("Harry Drew");
        assertFalse(predicate.test(new BookBuilder().withTitle("Harry Carol").build()));

        // Keywords match serial number, author and genre, but does not match title
        predicate = new BookPredicate().setTitle("12345")
                .setSerialNumber(VALID_SERIAL_NUMBER_BOOK_1)
                .setAuthor(VALID_AUTHOR_BOOK_1)
                .setGenres(VALID_GENRE_FICTION);

        Book b = new BookBuilder().withTitle("Harry")
                .withSerialNumber(VALID_SERIAL_NUMBER_BOOK_1)
                .withAuthor(VALID_AUTHOR_BOOK_1)
                .withGenres(VALID_GENRE_FICTION)
                .build();
        assertFalse(predicate.test(b));
    }

    @Test
    public void test_authorCondition_returnsTrue() {
        // One keyword
        BookPredicate predicate = new BookPredicate().setAuthor("Harry");
        assertTrue(predicate.test(new BookBuilder().withAuthor("Harry Bob").build()));

        // Multiple keywords
        predicate = new BookPredicate().setAuthor("Harry Bob");
        assertTrue(predicate.test(new BookBuilder().withAuthor("Harry Bob").build()));

        // Mixed-case keywords
        predicate = new BookPredicate().setAuthor("haRry bOB");
        assertTrue(predicate.test(new BookBuilder().withAuthor("Harry Bob").build()));
    }

    @Test
    public void test_authorCondition_returnsFalse() {
        // Zero keywords
        assertThrows(IllegalArgumentException.class, () -> new BookPredicate().setAuthor(""));

        // white space keywords
        assertThrows(IllegalArgumentException.class, () -> new BookPredicate().setAuthor("  "));

        // Non-matching keyword
        BookPredicate predicate = new BookPredicate().setAuthor("Carol");
        assertFalse(predicate.test(new BookBuilder().withAuthor("Harry Bob").build()));

        // Only one matching keyword
        predicate = new BookPredicate().setAuthor("Harry Drew");
        assertFalse(predicate.test(new BookBuilder().withAuthor("Harry Carol").build()));

        // Keywords match title, serial number and genre, but does not match author
        predicate = new BookPredicate().setTitle(VALID_TITLE_BOOK_1)
                .setSerialNumber(VALID_SERIAL_NUMBER_BOOK_1)
                .setAuthor("Orange")
                .setGenres(VALID_GENRE_FICTION);

        Book b = new BookBuilder().withTitle(VALID_TITLE_BOOK_1)
                .withSerialNumber(VALID_SERIAL_NUMBER_BOOK_1)
                .withAuthor("Apple")
                .withGenres(VALID_GENRE_FICTION)
                .build();
        assertFalse(predicate.test(b));
    }

    @Test
    public void test_serialNumberCondition_returnsTrue() {
        // One keyword
        BookPredicate predicate = new BookPredicate().setSerialNumber(VALID_SERIAL_NUMBER_BOOK_1);
        assertTrue(predicate.test(new BookBuilder().withSerialNumber(VALID_SERIAL_NUMBER_BOOK_1).build()));
    }

    @Test
    public void test_serialNumberCondition_returnsFalse() {
        // Zero keywords
        assertThrows(IllegalArgumentException.class, () -> new BookPredicate().setSerialNumber(""));

        // white space keywords
        assertThrows(IllegalArgumentException.class, () -> new BookPredicate().setSerialNumber("  "));

        // Non-matching keyword
        BookPredicate predicate = new BookPredicate().setSerialNumber(VALID_SERIAL_NUMBER_BOOK_1);
        assertFalse(predicate.test(new BookBuilder().withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2).build()));

        // Keywords match title, author and genre, but does not match serial number
        predicate = new BookPredicate().setTitle(VALID_TITLE_BOOK_1)
                .setSerialNumber(VALID_SERIAL_NUMBER_BOOK_1)
                .setAuthor(VALID_AUTHOR_BOOK_1)
                .setGenres(VALID_GENRE_FICTION);

        Book b = new BookBuilder().withTitle(VALID_TITLE_BOOK_1)
                .withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2)
                .withAuthor(VALID_AUTHOR_BOOK_1)
                .withGenres(VALID_GENRE_FICTION)
                .build();
        assertFalse(predicate.test(b));
    }

    @Test
    public void test_genresCondition_returnsTrue() {
        // One keyword
        BookPredicate predicate = new BookPredicate().setGenres(VALID_GENRE_FICTION);
        assertTrue(predicate.test(new BookBuilder().withGenres(VALID_GENRE_FICTION, VALID_GENRE_ACTION).build()));

        // Multiple keywords, different order
        predicate = new BookPredicate().setGenres(VALID_GENRE_ACTION, VALID_GENRE_FICTION);
        assertTrue(predicate.test(new BookBuilder().withGenres(VALID_GENRE_FICTION, VALID_GENRE_ACTION).build()));
    }

    @Test
    public void test_genresCondition_returnsFalse() {
        // Zero keywords
        assertThrows(IllegalArgumentException.class, () -> new BookPredicate().setGenres(""));

        // white space keywords
        assertThrows(IllegalArgumentException.class, () -> new BookPredicate().setGenres("  "));

        // Non-matching keyword will not pass
        BookPredicate predicate = new BookPredicate().setGenres(VALID_GENRE_NONFICTION);
        assertFalse(predicate.test(new BookBuilder().withGenres(VALID_GENRE_FICTION, VALID_GENRE_ACTION).build()));

        // Only one matching keyword will not pass predicate
        predicate = new BookPredicate().setGenres(VALID_GENRE_ACTION, VALID_GENRE_NONFICTION);
        assertFalse(predicate.test(new BookBuilder().withGenres(VALID_GENRE_FICTION, VALID_GENRE_ACTION).build()));

        // Mixed-case keywords allowed
        predicate = new BookPredicate().setGenres("ficTion", "Action");
        assertTrue(predicate.test(new BookBuilder().withGenres(VALID_GENRE_FICTION, VALID_GENRE_ACTION).build()));

        // Keywords match title, author and serial number, but does not match genre
        predicate = new BookPredicate().setTitle(VALID_TITLE_BOOK_1)
                .setSerialNumber(VALID_SERIAL_NUMBER_BOOK_1)
                .setAuthor(VALID_AUTHOR_BOOK_1)
                .setGenres(VALID_GENRE_NONFICTION);

        Book b = new BookBuilder().withTitle(VALID_TITLE_BOOK_1)
                .withSerialNumber(VALID_SERIAL_NUMBER_BOOK_1)
                .withAuthor(VALID_AUTHOR_BOOK_1)
                .withGenres(VALID_GENRE_FICTION)
                .build();
        assertFalse(predicate.test(b));
    }
}
