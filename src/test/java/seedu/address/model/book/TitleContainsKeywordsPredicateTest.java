package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BookBuilder;

public class TitleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TitleContainsKeywordPredicate firstPredicate = new TitleContainsKeywordPredicate(firstPredicateKeywordList);
        TitleContainsKeywordPredicate secondPredicate = new TitleContainsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TitleContainsKeywordPredicate firstPredicateCopy = new TitleContainsKeywordPredicate(firstPredicateKeywordList);
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
        TitleContainsKeywordPredicate predicate = new TitleContainsKeywordPredicate(Collections.singletonList("Harry"));
        assertTrue(predicate.test(new BookBuilder().withTitle("Harry Bob").build()));

        // Multiple keywords
        predicate = new TitleContainsKeywordPredicate(Arrays.asList("Harry", "Bob"));
        assertTrue(predicate.test(new BookBuilder().withTitle("Harry Bob").build()));

        // Only one matching keyword
        predicate = new TitleContainsKeywordPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new BookBuilder().withTitle("Harry Carol").build()));

        // Mixed-case keywords
        predicate = new TitleContainsKeywordPredicate(Arrays.asList("haRry", "bOB"));
        assertTrue(predicate.test(new BookBuilder().withTitle("Harry Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TitleContainsKeywordPredicate predicate = new TitleContainsKeywordPredicate(Collections.emptyList());
        assertFalse(predicate.test(new BookBuilder().withTitle("Harry").build()));

        // Non-matching keyword
        predicate = new TitleContainsKeywordPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new BookBuilder().withTitle("Harry Bob").build()));

        // Keywords match serial number, author and genre, but does not match title
        predicate = new TitleContainsKeywordPredicate(Arrays.asList("12345", "JKRowling", "Fiction", "Action"));
        Book b = new BookBuilder().withTitle("Harry").withSerialNumber(VALID_SERIAL_NUMBER_BOOK_1)
                .withAuthor("J K Rowling").withGenres("Fiction", "Action").build();
        assertFalse(predicate.test(new BookBuilder().withTitle("Harry").withSerialNumber(VALID_SERIAL_NUMBER_BOOK_1)
                .withAuthor("JKRowling").withGenres("Fiction", "Action").build()));
    }
}
