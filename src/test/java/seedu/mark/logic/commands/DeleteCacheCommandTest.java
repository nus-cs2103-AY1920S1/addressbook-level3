package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;

import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.ModelStub;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.CachedCopy;
import seedu.mark.model.bookmark.util.BookmarkBuilder;
import seedu.mark.storage.StorageStub;

class DeleteCacheCommandTest {

    @Test
    public void execute_valid_deleteSuccess() throws Exception {
        Bookmark validBookmark = new BookmarkBuilder().withCachedCopy(new CachedCopy("<html>")).build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        DeleteCacheCommand command = new DeleteCacheCommand(INDEX_FIRST_BOOKMARK);

        CommandResult result = command.execute(modelStub, new StorageStub());

        assertEquals(DeleteCacheCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void execute_noOfflineCopy_deleteFailure() throws Exception {
        Bookmark validBookmark = new BookmarkBuilder().build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        DeleteCacheCommand command = new DeleteCacheCommand(INDEX_FIRST_BOOKMARK);

        assertThrows(CommandException.class, DeleteCacheCommand.MESSAGE_FAILURE, () ->
                command.execute(modelStub, new StorageStub()));
    }

    @Test
    public void testEquals() {
        DeleteCacheCommand command = new DeleteCacheCommand(INDEX_FIRST_BOOKMARK);
        DeleteCacheCommand other = new DeleteCacheCommand(INDEX_FIRST_BOOKMARK);
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

        @Override
        public ObservableValue<String> getObservableOfflineDocNameCurrentlyShowing() {
            return new SimpleStringProperty("");
        }
    }
}
