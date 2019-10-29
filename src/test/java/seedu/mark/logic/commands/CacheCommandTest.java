package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;

import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.ModelStub;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.util.BookmarkBuilder;
import seedu.mark.storage.StorageStub;

class CacheCommandTest {

    // only testing for invalid, as trying to test a valid URL depends on internet connection.
    // future: mock the URL class so that we can test it without internet
    @Test
    public void execute_invalidUrl_addFailure() throws Exception {
        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://INVALID").build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        CommandResult commandResult = new CacheCommand(INDEX_FIRST_BOOKMARK)
                .execute(modelStub, new StorageStub());

        assertEquals(CacheCommand.MESSAGE_FAILURE, commandResult.getFeedbackToUser());
    }

    @Test
    public void testEquals() {
        CacheCommand command = new CacheCommand(INDEX_FIRST_BOOKMARK);
        CacheCommand other = new CacheCommand(INDEX_FIRST_BOOKMARK);
        assertEquals(command, other);
    }

    private class ModelStubAcceptingBookmarkAdded extends ModelStub {
        private Bookmark setBookmark = null;

        ModelStubAcceptingBookmarkAdded(Bookmark bookmark) {
            setBookmark = bookmark;
        }

        @Override
        public void setBookmark(Bookmark target, Bookmark editedBookmark) {
            setBookmark = editedBookmark;
        }

        @Override
        public void saveMark(String message) {
            // phantom save just for testing
        }

        @Override
        public ObservableList<Bookmark> getFilteredBookmarkList() {
            return FXCollections.observableList(List.of(setBookmark));
        }

        @Override
        public void updateCurrentDisplayedCache(Bookmark bookmarkToDisplayCache) {
            // phantom update just for testing
        }
    }
}
