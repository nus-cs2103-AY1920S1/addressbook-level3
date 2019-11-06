package seedu.mark.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.util.BookmarkBuilder;
import seedu.mark.model.predicates.BookmarkPredicate;

class JsonAdaptedBookmarkPredicateTest {

    private static final List<String> KEYWORD_LIST_MULTIPLE = Arrays.asList("first", "second");
    private static final List<String> KEYWORD_LIST_SINGLE = Collections.singletonList("second");

    private static final String MATCHING_NAME = "First Bookmark";
    private static final String MATCHING_URL = "https://first-website.com";
    private static final String MATCHING_FOLDER = "first";
    private static final String NON_MATCHING_NAME = "Second Bookmark";
    private static final String NON_MATCHING_URL = "https://second-website.com";
    private static final String NON_MATCHING_FOLDER = "second";

    @Test
    public void toModelType_validBookmarkPredicateDetails_returnsBookmarkPredicate() {
        BookmarkPredicate predicate = new BookmarkPredicate().withNameKeywords(KEYWORD_LIST_MULTIPLE)
                .withoutNameKeywords(KEYWORD_LIST_SINGLE).withUrlKeywords(KEYWORD_LIST_MULTIPLE)
                .withoutUrlKeywords(KEYWORD_LIST_SINGLE);
        JsonAdaptedBookmarkPredicate jsonPredicate = new JsonAdaptedBookmarkPredicate(predicate);

        // state check
        assertEquals(predicate, jsonPredicate.toModelType());

        // TODO: behaviour check
    }

    // TODO: test invalid input?

    @Test
    public void toModelType_validKeywords_success() {
        BookmarkPredicate bookmarkPredicate = new JsonAdaptedBookmarkPredicate(
                KEYWORD_LIST_MULTIPLE, KEYWORD_LIST_SINGLE,
                KEYWORD_LIST_MULTIPLE, KEYWORD_LIST_SINGLE,
                KEYWORD_LIST_MULTIPLE, KEYWORD_LIST_SINGLE).toModelType();

        assertEquals(bookmarkPredicate,
                new BookmarkPredicate().withNameKeywords(KEYWORD_LIST_MULTIPLE)
                        .withoutNameKeywords(KEYWORD_LIST_SINGLE).withUrlKeywords(KEYWORD_LIST_MULTIPLE)
                        .withoutUrlKeywords(KEYWORD_LIST_SINGLE).withFolder(KEYWORD_LIST_MULTIPLE)
                        .withoutFolder(KEYWORD_LIST_SINGLE));

        // check whether predicate works
        assertTrue(bookmarkPredicate.test(new BookmarkBuilder().withName(MATCHING_NAME)
                .withUrl(MATCHING_URL).withFolder(MATCHING_FOLDER).build()));
        assertFalse(bookmarkPredicate.test(new BookmarkBuilder().withName(NON_MATCHING_NAME)
                .withUrl(MATCHING_URL).withFolder(MATCHING_FOLDER).build()));
        assertFalse(bookmarkPredicate.test(new BookmarkBuilder().withName(MATCHING_NAME)
                .withUrl(NON_MATCHING_URL).withFolder(MATCHING_FOLDER).build()));
        assertFalse(bookmarkPredicate.test(new BookmarkBuilder().withName(MATCHING_NAME)
                .withUrl(MATCHING_URL).withFolder(NON_MATCHING_FOLDER).build()));
        assertFalse(bookmarkPredicate.test(new BookmarkBuilder().withName(NON_MATCHING_NAME)
                .withUrl(NON_MATCHING_URL).withFolder(NON_MATCHING_FOLDER).build()));
        assertFalse(bookmarkPredicate.test(new BookmarkBuilder().withName("Random Name")
                .withUrl("http://random").withFolder("Random").build()));
    }
}
