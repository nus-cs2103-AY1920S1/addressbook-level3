package seedu.mark.model.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.util.BookmarkBuilder;

public class BookmarkPredicateTest {

    private static final List<String> KEYWORD_LIST_MULTIPLE = Arrays.asList("first", "second");
    private static final List<String> KEYWORD_LIST_SINGLE = Collections.singletonList("second");

    private static final String FIRST_NAME = "First Website";
    private static final String FIRST_URL = "https://first-website.com";
    private static final String SECOND_NAME = "Second Website";
    private static final String SECOND_URL = "https://second-website.com";
    private static final String STANDARD_NAME = "Website";
    private static final String STANDARD_URL = "https://website.com";

    @Test
    public void equals() {
        BookmarkPredicate firstPredicate = new BookmarkPredicate().withNameKeywords(KEYWORD_LIST_MULTIPLE);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BookmarkPredicate firstPredicateCopy = new BookmarkPredicate().withNameKeywords(KEYWORD_LIST_MULTIPLE);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(new BookmarkPredicate())); // different name keywords
        assertFalse(firstPredicate.equals(new BookmarkPredicate().withNameKeywords(KEYWORD_LIST_MULTIPLE)
                        .withoutNameKeywords(KEYWORD_LIST_SINGLE))); // different not-name keywords
        assertFalse(firstPredicate.equals(new BookmarkPredicate().withNameKeywords(KEYWORD_LIST_MULTIPLE)
                        .withUrlKeywords(KEYWORD_LIST_SINGLE))); // different url keywords
        assertFalse(firstPredicate.equals(new BookmarkPredicate().withNameKeywords(KEYWORD_LIST_MULTIPLE)
                        .withoutUrlKeywords(KEYWORD_LIST_MULTIPLE))); // different not-url keywords
        assertFalse(firstPredicate.equals(new BookmarkPredicate().withNameKeywords(KEYWORD_LIST_MULTIPLE)
                .withFolder(KEYWORD_LIST_SINGLE))); // different folder names
        assertFalse(firstPredicate.equals(new BookmarkPredicate().withNameKeywords(KEYWORD_LIST_MULTIPLE)
                .withoutFolder(KEYWORD_LIST_SINGLE))); // different not-folder names
    }

    @Test
    public void test() { // TODO: check equivalence partitions
        Bookmark firstBookmark =
                new BookmarkBuilder().withName(FIRST_NAME).withUrl(FIRST_URL).build();
        Bookmark secondBookmark =
                new BookmarkBuilder().withName(SECOND_NAME).withUrl(SECOND_URL).build();

        // empty predicate
        BookmarkPredicate predicate = new BookmarkPredicate();
        assertTrue(predicate.test(firstBookmark));
        assertTrue(predicate.test(secondBookmark));

        // some fields specified
        predicate = new BookmarkPredicate().withNameKeywords(KEYWORD_LIST_MULTIPLE)
                .withUrlKeywords(KEYWORD_LIST_SINGLE);
        assertTrue(predicate.test(secondBookmark));
        assertFalse(predicate.test(firstBookmark)); // url doesn't contain "second"

        // all fields specified
        predicate = new BookmarkPredicate().withNameKeywords(KEYWORD_LIST_MULTIPLE)
                .withoutNameKeywords(KEYWORD_LIST_SINGLE).withUrlKeywords(KEYWORD_LIST_MULTIPLE)
                .withoutUrlKeywords(KEYWORD_LIST_SINGLE);
        assertTrue(predicate.test(firstBookmark));
        assertFalse(predicate.test(secondBookmark));

        // contradictory conditions
        predicate = new BookmarkPredicate().withNameKeywords(KEYWORD_LIST_MULTIPLE)
                .withoutNameKeywords(KEYWORD_LIST_MULTIPLE);
        assertFalse(predicate.test(firstBookmark));
        assertFalse(predicate.test(secondBookmark));
    }

    @Test
    public void isEmpty() {
        assertTrue(new BookmarkPredicate().isEmpty());
        assertFalse(new BookmarkPredicate().withNameKeywords(KEYWORD_LIST_MULTIPLE).isEmpty());
        assertFalse(new BookmarkPredicate().withUrlKeywords(KEYWORD_LIST_MULTIPLE).isEmpty());
        assertFalse(new BookmarkPredicate().withoutNameKeywords(KEYWORD_LIST_MULTIPLE).isEmpty());
        assertFalse(new BookmarkPredicate().withoutUrlKeywords(KEYWORD_LIST_MULTIPLE).isEmpty());
    }

    // TODO: test for invalid inputs / duplicate keywords

    @Test
    public void withNameKeywords_noDuplicateKeywords_success() {
        BookmarkPredicate predicate = new BookmarkPredicate().withNameKeywords(KEYWORD_LIST_MULTIPLE);

        // state check
        assertEquals(predicate.getNameKeywords(), new HashSet<>(KEYWORD_LIST_MULTIPLE));
        assertEquals(predicate.getNotNameKeywords(), new HashSet<>());
        assertEquals(predicate.getUrlKeywords(), new HashSet<>());
        assertEquals(predicate.getNotUrlKeywords(), new HashSet<>());

        // behaviour check
        // assumption: BookmarkBuilder.DEFAULT_URL doesn't affect predicate result
        assertTrue(predicate.test(new BookmarkBuilder().withName(FIRST_NAME).build()));
        assertTrue(predicate.test(new BookmarkBuilder().withName(SECOND_NAME).build()));
        assertFalse(predicate.test(new BookmarkBuilder().withName(STANDARD_NAME).build()));
    }

    @Test
    public void withoutNameKeywords_noDuplicateKeywords_success() {
        BookmarkPredicate predicate = new BookmarkPredicate().withoutNameKeywords(KEYWORD_LIST_MULTIPLE);

        // state check
        assertEquals(predicate.getNameKeywords(), new HashSet<>());
        assertEquals(predicate.getNotNameKeywords(), new HashSet<>(KEYWORD_LIST_MULTIPLE));
        assertEquals(predicate.getUrlKeywords(), new HashSet<>());
        assertEquals(predicate.getNotUrlKeywords(), new HashSet<>());

        // behaviour check
        // assumption: BookmarkBuilder.DEFAULT_URL doesn't affect predicate result
        assertTrue(predicate.test(new BookmarkBuilder().withName(STANDARD_NAME).build()));
        assertFalse(predicate.test(new BookmarkBuilder().withName(FIRST_NAME).build()));
        assertFalse(predicate.test(new BookmarkBuilder().withName(SECOND_NAME).build()));
    }

    @Test
    public void withUrlKeywords_noDuplicateKeywords_success() {
        BookmarkPredicate predicate = new BookmarkPredicate().withUrlKeywords(KEYWORD_LIST_MULTIPLE);

        // state check
        assertEquals(predicate.getNameKeywords(), new HashSet<>());
        assertEquals(predicate.getNotNameKeywords(), new HashSet<>());
        assertEquals(predicate.getUrlKeywords(), new HashSet<>(KEYWORD_LIST_MULTIPLE));
        assertEquals(predicate.getNotUrlKeywords(), new HashSet<>());

        // behaviour check
        // assumption: BookmarkBuilder.DEFAULT_NAME doesn't affect predicate result
        assertTrue(predicate.test(new BookmarkBuilder().withUrl(FIRST_URL).build()));
        assertTrue(predicate.test(new BookmarkBuilder().withUrl(SECOND_URL).build()));
        assertFalse(predicate.test(new BookmarkBuilder().withUrl(STANDARD_URL).build()));
    }

    @Test
    public void withoutUrlKeywords_noDuplicateKeywords_success() {
        BookmarkPredicate predicate = new BookmarkPredicate().withoutUrlKeywords(KEYWORD_LIST_MULTIPLE);

        // state check
        assertEquals(predicate.getNameKeywords(), new HashSet<>());
        assertEquals(predicate.getNotNameKeywords(), new HashSet<>());
        assertEquals(predicate.getUrlKeywords(), new HashSet<>());
        assertEquals(predicate.getNotUrlKeywords(), new HashSet<>(KEYWORD_LIST_MULTIPLE));

        // behaviour check
        // assumption: BookmarkBuilder.DEFAULT_NAME doesn't affect predicate result
        assertTrue(predicate.test(new BookmarkBuilder().withUrl(STANDARD_URL).build()));
        assertFalse(predicate.test(new BookmarkBuilder().withUrl(FIRST_URL).build()));
        assertFalse(predicate.test(new BookmarkBuilder().withUrl(SECOND_URL).build()));
    }
}
