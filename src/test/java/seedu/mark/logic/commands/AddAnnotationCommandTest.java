package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;
import static seedu.mark.testutil.TypicalIndexes.INDEX_SECOND_BOOKMARK;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.ModelStub;
import seedu.mark.model.annotation.Annotation;
import seedu.mark.model.annotation.AnnotationNote;
import seedu.mark.model.annotation.Highlight;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.Paragraph;
import seedu.mark.model.annotation.ParagraphIdentifier;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.CachedCopy;
import seedu.mark.model.bookmark.util.BookmarkBuilder;
import seedu.mark.storage.StorageStub;

class AddAnnotationCommandTest {
    private Index index = Index.fromOneBased(1);
    private ParagraphIdentifier pid = new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.EXIST);

    @Test
    public void constructor_nullParams_throwsNullPointerExceptions() {
        assertThrows(NullPointerException.class, () ->
                new AddAnnotationCommand(null, pid, null, Highlight.GREEN));
        assertThrows(NullPointerException.class, () ->
                new AddAnnotationCommand(index, null, null, Highlight.GREEN));
        assertThrows(NullPointerException.class, () ->
                new AddAnnotationCommand(index, pid, null, null));
    }

    @Test
    public void equals_success() {
        assertEquals(new AddAnnotationCommand(index, pid, null, Highlight.GREEN),
                new AddAnnotationCommand(index, pid, null, Highlight.GREEN));
        assertNotEquals(new AddAnnotationCommand(index, pid, null, Highlight.YELLOW),
                new AddAnnotationCommand(index, pid, null, Highlight.GREEN));
    }

    @Test
    public void execute_invalidBookmark_throwsCommandException() {
        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://anyurl").build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_BOOKMARK_DISPLAYED_INDEX, () ->
                new AddAnnotationCommand(INDEX_SECOND_BOOKMARK, ParagraphIdentifier.makeStrayId(Index.fromOneBased(1)),
                        AnnotationNote.SAMPLE_NOTE, Highlight.GREEN)
                        .execute(modelStub, new StorageStub()));
    }

    @Test
    public void execute_noCacheAvailable_throwsCommandException() {
        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://anyurl").build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        assertThrows(CommandException.class, AddAnnotationCommand.MESSAGE_NO_CACHE_AVAILABLE, () ->
                new AddAnnotationCommand(INDEX_FIRST_BOOKMARK, ParagraphIdentifier.makeStrayId(Index.fromOneBased(1)),
                AnnotationNote.SAMPLE_NOTE, Highlight.GREEN)
                .execute(modelStub, new StorageStub()));

    }

    @Test
    public void execute_paragraphIsPhantom_throwsCommandException() {
        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub()).build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        assertThrows(CommandException.class, AddAnnotationCommand.MESSAGE_CANNOT_CHOOSE_PHANTOM_TO_ANNOTATE, () ->
                new AddAnnotationCommand(INDEX_FIRST_BOOKMARK, ParagraphIdentifier.makeStrayId(Index.fromOneBased(1)),
                        AnnotationNote.SAMPLE_NOTE, Highlight.GREEN)
                        .execute(modelStub, new StorageStub()));

    }

    @Test
    public void execute_validIdentifiers_success() {
        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub()).build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        assertDoesNotThrow(() -> new AddAnnotationCommand(INDEX_FIRST_BOOKMARK,
                ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                AnnotationNote.SAMPLE_NOTE, Highlight.GREEN)
                .execute(modelStub, new StorageStub()));
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
        public void updateDocument(OfflineDocument doc) {
            // phantom update for testing
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

    private class CachedCopyStub extends CachedCopy {
        CachedCopyStub() {
            super("");
        }

        @Override
        public OfflineDocument getAnnotations() {
            return new OfflineDocumentStub();
        }
    }

    private class OfflineDocumentStub extends OfflineDocument {
        OfflineDocumentStub() {
            super("");
        }

        @Override
        public void addAnnotation(ParagraphIdentifier pid, Annotation an) {
            //phantom add annotation for AddAnnotationCommand to call
        }

        @Override
        public void loadAnnotations(HashMap annotations) {
            assert false : "this method should not be called.";
        }

        @Override
        public void updateStrayIndex() {
            assert false : "this method should not be called.";
        }

        @Override
        public OfflineDocument copy() {
            return this;
        }

        @Override
        public List<Paragraph> getCollection() {
            assert false : "this method should not be called.";
            return null;
        }

        @Override
        public Paragraph getParagraph(ParagraphIdentifier pid) {
            assert false : "this method should not be called.";
            return null;
        }

        @Override
        public boolean hasParagraph(ParagraphIdentifier pid) {
            assert false : "this method should not be called.";
            return false;
        }

        @Override
        public int getNumStrayNotes() {
            assert false : "this method should not be called.";
            return 0;
        }

        @Override
        public void removePhantom(ParagraphIdentifier pid) {
            assert false : "this method should not be called.";
        }

        @Override
        public void addPhantom(Annotation an) {
            assert false : "this method should not be called.";
        }
    }
}
