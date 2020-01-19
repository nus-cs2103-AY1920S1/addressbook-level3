package seedu.mark.model.autotag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalBookmarks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.util.BookmarkBuilder;
import seedu.mark.model.predicates.BookmarkPredicate;
import seedu.mark.model.tag.Tag;

class AutotagControllerTest {

    private static final SelectiveBookmarkTagger TAGGER_HELLO = new SelectiveBookmarkTagger(
            new Tag("Hello"), new BookmarkPredicate().withNameKeywords(Collections.singletonList("hello")));
    private static final SelectiveBookmarkTagger TAGGER_WORLD = new SelectiveBookmarkTagger(
            new Tag("World"), new BookmarkPredicate().withNameKeywords(Collections.singletonList("world")));
    private static final SelectiveBookmarkTagger TAGGER_URL_EXAMPLE = new SelectiveBookmarkTagger(
            new Tag("exampleTag"),
            new BookmarkPredicate().withUrlKeywords(Collections.singletonList("example")));

    private static final Bookmark BOOKMARK_HELLO = new BookmarkBuilder().withName("Hello World")
            .withUrl("https://hello-world.org").build();

    @Test
    public void addTagger_validValue_success() {
        AutotagController controller = new AutotagController();
        controller.addTagger(TAGGER_HELLO);
        assertEquals(controller, new AutotagController(FXCollections.observableArrayList(TAGGER_HELLO)));
    }

    @Test
    public void applyTaggers_noTaggersPresent_notTagged() {
        AutotagController controller = new AutotagController();
        assertEquals(controller.applyTaggers(BOOKMARK_HELLO), BOOKMARK_HELLO);
    }

    @Test
    public void applyTaggers_noTaggersMatch_notTagged() {
        AutotagController controller = new AutotagController(FXCollections.observableArrayList(TAGGER_URL_EXAMPLE));
        assertEquals(controller.applyTaggers(BOOKMARK_HELLO), BOOKMARK_HELLO);
    }

    @Test
    public void applyTaggers_oneTaggerMatches_tagged() {
        AutotagController controller = new AutotagController(FXCollections.observableArrayList(TAGGER_HELLO));
        Bookmark expectedBookmark = new BookmarkBuilder(BOOKMARK_HELLO).withTags("Hello").build();
        assertEquals(controller.applyTaggers(BOOKMARK_HELLO), expectedBookmark);
    }

    @Test
    public void applyTaggers_multipleTaggersMatch_tagged() {
        AutotagController controller = new AutotagController(
                FXCollections.observableArrayList(TAGGER_HELLO, TAGGER_WORLD));
        Bookmark expectedBookmark = new BookmarkBuilder(BOOKMARK_HELLO).withTags("Hello", "World").build();
        assertEquals(controller.applyTaggers(BOOKMARK_HELLO), expectedBookmark);
    }

    @Test
    public void applyTaggersToList_taggersMatchAllBookmarks_allBookmarksTagged() {
        AutotagController controller = new AutotagController(FXCollections.observableArrayList(TAGGER_URL_EXAMPLE));
        List<Bookmark> expectedBookmarks = tagBookmarks(getTypicalBookmarks(), TAGGER_URL_EXAMPLE);
        assertEquals(controller.applyTaggersToList(getTypicalBookmarks()), expectedBookmarks);
    }

    /**
     * Returns a copy of the given bookmark list in which all bookmarks have been
     * tagged by {@code tagger}. Similar to {@code AutotagController#applyTaggersToList()}
     * if the {@code AutotagController} contains only a single {@code tagger}.
     */
    private List<Bookmark> tagBookmarks(List<Bookmark> bookmarks, SelectiveBookmarkTagger tagger) {
        List<Bookmark> taggedBookmarks = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            taggedBookmarks.add(tagger.applyTag(bookmark));
        }
        return taggedBookmarks;
    }

    @Test
    public void removeTagger_taggerExists_returnsTagger() {
        AutotagController controller = new AutotagController(FXCollections.observableArrayList(TAGGER_HELLO));
        assertEquals(controller.removeTagger("Hello"), Optional.of(TAGGER_HELLO));
    }

    @Test
    public void removeTagger_taggerDoesNotExist_returnsEmptyOptional() {
        AutotagController controller = new AutotagController(FXCollections.observableArrayList(TAGGER_URL_EXAMPLE));
        assertEquals(controller.removeTagger("WORLD"), Optional.empty());
    }

    @Test
    public void removeAllTaggers() {
        AutotagController controller = new AutotagController(FXCollections.observableArrayList(TAGGER_URL_EXAMPLE));
        controller.removeAllTaggers();
        assertEquals(controller, new AutotagController());
    }

    @Test
    public void equals() {
        // same object -> returns true
        AutotagController controller = new AutotagController(FXCollections.observableArrayList(TAGGER_HELLO));
        assertTrue(controller.equals(controller));

        // same values -> returns true
        assertTrue(controller.equals(new AutotagController(FXCollections.observableArrayList(TAGGER_HELLO))));

        // null -> returns false
        assertFalse(controller.equals(null));

        // different taggers -> returns false
        assertFalse(controller.equals(new AutotagController(FXCollections.observableArrayList(TAGGER_URL_EXAMPLE))));
    }
}
