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

import seedu.address.commons.util.DateUtil;
import seedu.address.logic.parser.Flag;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.LoanBuilder;

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

        // has loan state -> true
        predicate = new BookPredicate().setLoanState(Flag.LOANED);
        assertTrue(predicate.isValid());

        // has display limit -> true
        predicate = new BookPredicate().setDisplayLimit(1);
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

        predicate1 = new BookPredicate().setAuthor(VALID_AUTHOR_BOOK_1);
        predicate1Copy = new BookPredicate().setAuthor(VALID_AUTHOR_BOOK_1);
        predicate2 = new BookPredicate().setAuthor(VALID_AUTHOR_BOOK_2);

        // same author condition -> returns true
        assertTrue(predicate1.equals(predicate1Copy));

        // different author condition -> returns false
        assertFalse(predicate1.equals(predicate2));

        predicate1 = new BookPredicate().setSerialNumber(VALID_SERIAL_NUMBER_BOOK_1);
        predicate1Copy = new BookPredicate().setSerialNumber(VALID_SERIAL_NUMBER_BOOK_1);
        predicate2 = new BookPredicate().setSerialNumber(VALID_SERIAL_NUMBER_BOOK_2);

        // same serialNumber condition -> returns true
        assertTrue(predicate1.equals(predicate1Copy));

        // different serialNumber condition -> returns false
        assertFalse(predicate1.equals(predicate2));

        predicate1 = new BookPredicate().setGenres(VALID_GENRE_FICTION, VALID_GENRE_ACTION);
        predicate1Copy = new BookPredicate().setGenres(VALID_GENRE_ACTION, VALID_GENRE_FICTION);
        predicate2 = new BookPredicate().setGenres(VALID_GENRE_ACTION, VALID_GENRE_NONFICTION);

        // same genres condition, different order -> returns true
        assertTrue(predicate1.equals(predicate1Copy));

        // different genres condition -> returns false
        assertFalse(predicate1.equals(predicate2));

        predicate1 = new BookPredicate().setLoanState(Flag.AVAILABLE);
        predicate1Copy = new BookPredicate().setLoanState(Flag.AVAILABLE);
        predicate2 = new BookPredicate().setLoanState(Flag.OVERDUE);

        // same loan state -> true
        assertTrue(predicate1.equals(predicate1Copy));

        // different loan state -> false
        assertFalse(predicate1.equals(predicate2));

        predicate1 = new BookPredicate().setDisplayLimit(1);
        predicate1Copy = new BookPredicate().setDisplayLimit(1);
        predicate2 = new BookPredicate().setDisplayLimit(2);

        // same display limit -> true
        assertTrue(predicate1.equals(predicate1Copy));

        // different display limit -> false
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

        // partial keywords
        predicate = new BookPredicate().setTitle("har");
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

        // partial keywords
        predicate = new BookPredicate().setTitle("har");
        assertTrue(predicate.test(new BookBuilder().withTitle("Harry Bob").build()));
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

        // Mixed-case keywords allowed
        predicate = new BookPredicate().setGenres("ficTion", "Action");
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

        // partial keywords -> false
        predicate = new BookPredicate().setGenres(VALID_GENRE_FICTION);
        assertFalse(predicate.test(new BookBuilder().withGenres(VALID_GENRE_ACTION, VALID_GENRE_NONFICTION).build()));
    }

    @Test
    public void test_loanStateCondition_returnsTrue() {

        // available
        BookPredicate predicate = new BookPredicate().setLoanState(Flag.AVAILABLE);
        assertTrue(predicate.test(new BookBuilder().build()));

        // loaned
        predicate = new BookPredicate().setLoanState(Flag.LOANED);
        assertTrue(predicate.test(new BookBuilder().withLoan(new LoanBuilder()
                .withDueDate(DateUtil.getTodayPlusDays(30)).build()).build()));
        assertTrue(predicate.test(new BookBuilder().withLoan(new LoanBuilder()
                .withDueDate(DateUtil.getTodayMinusDays(1)).build()).build()));

        // overdue
        predicate = new BookPredicate().setLoanState(Flag.OVERDUE);
        assertTrue(predicate.test(new BookBuilder().withLoan(new LoanBuilder()
                .withStartDate(DateUtil.getTodayMinusDays(30))
                .withDueDate(DateUtil.getTodayMinusDays(1)).build()).build()));
    }

    @Test
    public void test_loanStateCondition_returnsFalse() {

        // predicate flagged available but book not available
        BookPredicate predicate = new BookPredicate().setLoanState(Flag.AVAILABLE);
        assertFalse(predicate.test(new BookBuilder().withLoan(new LoanBuilder().build()).build()));

        // predicate flagged loaned but book available
        predicate = new BookPredicate().setLoanState(Flag.LOANED);
        assertFalse(predicate.test(new BookBuilder().build()));

        // predicate flagged overdue but book not overdue
        predicate = new BookPredicate().setLoanState(Flag.OVERDUE);
        assertFalse(predicate.test(new BookBuilder().withLoan(new LoanBuilder()
                .withDueDate(DateUtil.getTodayMinusDays(0)).build()).build()));
    }

    @Test
    public void test_displayLimit_returnsTrue() {
        BookPredicate predicate = new BookPredicate().setDisplayLimit(2);
        // display limit = 2
        assertTrue(predicate.test(new BookBuilder().build()));
        // display limit = 1
        assertTrue(predicate.test(new BookBuilder().build()));
    }

    @Test
    public void test_displayLimit_returnsFalse() {
        BookPredicate predicate = new BookPredicate().setDisplayLimit(1);
        // display limit = 1
        predicate.test(new BookBuilder().build());
        // display limit = 0
        assertFalse(predicate.test(new BookBuilder().build()));
    }
}
