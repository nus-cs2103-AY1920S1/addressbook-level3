package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_1;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BookBuilder;

public class BookPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        BookPredicate firstPredicate = new BookPredicate().addTitle(firstPredicateKeyword);
        BookPredicate secondPredicate = new BookPredicate().addTitle(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BookPredicate firstPredicateCopy = new BookPredicate().addTitle(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        BookPredicate predicate = new BookPredicate().addTitle("Harry");
        assertTrue(predicate.test(new BookBuilder().withTitle("Harry Bob").build()));

        // Multiple keywords
        predicate = new BookPredicate().addTitle("Harry Bob");
        assertTrue(predicate.test(new BookBuilder().withTitle("Harry Bob").build()));

        // Only one matching keyword
        predicate = new BookPredicate().addTitle("Harry Carol");
        assertTrue(predicate.test(new BookBuilder().withTitle("Harry Carol").build()));

        // Mixed-case keywords
        predicate = new BookPredicate().addTitle("haRry bOB");
        assertTrue(predicate.test(new BookBuilder().withTitle("Harry Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        assertThrows(IllegalArgumentException.class, () -> new BookPredicate().addTitle(""));

        // white space keywords
        assertThrows(IllegalArgumentException.class, () -> new BookPredicate().addTitle("  "));

        // Non-matching keyword
        BookPredicate predicate = new BookPredicate().addTitle("Carol");
        assertFalse(predicate.test(new BookBuilder().withTitle("Harry Bob").build()));

        // Keywords match serial number, author and genre, but does not match title
        predicate = new BookPredicate().addTitle("12345")
            .addSerialNumber(VALID_SERIAL_NUMBER_BOOK_1)
            .addAuthor("J K Rowling")
            .addGenres("Fiction", "Action");

        Book b = new BookBuilder().withTitle("Harry").withSerialNumber(VALID_SERIAL_NUMBER_BOOK_1)
                .withAuthor("J K Rowling").withGenres("Fiction", "Action").build();
        assertFalse(predicate.test(b));
    }
}
