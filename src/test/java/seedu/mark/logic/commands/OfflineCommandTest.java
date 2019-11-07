package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_CACHED_HTML;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;

import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.ModelStub;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.CachedCopy;
import seedu.mark.model.bookmark.util.BookmarkBuilder;
import seedu.mark.storage.StorageStub;

class OfflineCommandTest {

    @Test
    public void execute_validIndex_successful() throws CommandException {
        Bookmark validBookmark = new BookmarkBuilder().withCachedCopy(new CachedCopy(VALID_CACHED_HTML)).build();
        ModelAcceptingOfflineCommand modelStub = new ModelAcceptingOfflineCommand(validBookmark);

        CommandResult commandResult = new OfflineCommand(INDEX_FIRST_BOOKMARK)
                .execute(modelStub, new StorageStub());

        assertEquals(String.format(OfflineCommand.MESSAGE_SUCCESS, validBookmark), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws CommandException {
        Bookmark validBookmark = new BookmarkBuilder().build(); // no cached copy
        ModelAcceptingOfflineCommand modelStub = new ModelAcceptingOfflineCommand(validBookmark);

        assertThrows(CommandException.class, OfflineCommand.MESSAGE_NO_CACHED_COPIES, () ->
                new OfflineCommand(INDEX_FIRST_BOOKMARK).execute(modelStub, new StorageStub()));
    }


    @Test
    public void testEquals() {
        OfflineCommand command = new OfflineCommand(INDEX_FIRST_BOOKMARK);
        OfflineCommand other = new OfflineCommand(INDEX_FIRST_BOOKMARK);
        assertEquals(command, other);
    }

    private class ModelAcceptingOfflineCommand extends ModelStub {
        private Bookmark bookmark;

        public ModelAcceptingOfflineCommand(Bookmark bookmark) {
            this.bookmark = bookmark;
        }

        @Override
        public ObservableList<Bookmark> getFilteredBookmarkList() {
            return FXCollections.observableList(List.of(bookmark));
        }

        @Override
        public void updateDocument(OfflineDocument doc) {
            // valid update
        }

        @Override
        public void setOfflineDocNameCurrentlyShowing(String name) {
            // valid set doc title
        }

        @Override
        public void saveMark(String record) {
            // phantom update just for testing
        }
    }
}
