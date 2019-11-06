package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.mark.commons.core.index.Index;
import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.ModelStub;
import seedu.mark.model.annotation.Annotation;
import seedu.mark.model.annotation.AnnotationNote;
import seedu.mark.model.annotation.Highlight;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.Paragraph;
import seedu.mark.model.annotation.ParagraphContent;
import seedu.mark.model.annotation.ParagraphIdentifier;
import seedu.mark.model.annotation.PhantomParagraph;
import seedu.mark.model.annotation.TrueParagraph;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.CachedCopy;
import seedu.mark.model.bookmark.util.BookmarkBuilder;
import seedu.mark.storage.StorageStub;

class EditAnnotationCommandTest {

    @Test
    public void execute_moveToTargetPhantom_throwsCommandException() {
        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(new TrueParagraph(Index.fromOneBased(1),
                        new ParagraphContent("lupus")))).build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        assertThrows(CommandException.class,
                EditAnnotationCommand.MESSAGE_TARGET_NO_PHANTOM, () ->
                        new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                                ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                                ParagraphIdentifier.makeStrayId(Index.fromOneBased(1)),
                                AnnotationNote.SAMPLE_NOTE, Highlight.YELLOW)
                                .execute(modelStub, new StorageStub()));
    }

    @Test
    public void execute_noNoteHighlightGiven_throwsCommandException() {
        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(new TrueParagraph(Index.fromOneBased(1),
                        new ParagraphContent("lupus")))).build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        assertThrows(CommandException.class,
                EditAnnotationCommand.MESSAGE_NO_EDITS_GIVEN, () ->
                        new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                                ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                                null,
                                null, null)
                                .execute(modelStub, new StorageStub()));
    }

    @Test
    public void execute_noNoteToPhantom_throwsCommandException() {
        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(new TrueParagraph(Index.fromOneBased(1),
                        new ParagraphContent("lupus")))).build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        //execute will check note is not null first before even checking that pid is valid
        assertThrows(CommandException.class,
                EditAnnotationCommand.MESSAGE_PHANTOM_CANNOT_HIGHLIGHT, () ->
                        new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                                ParagraphIdentifier.makeStrayId(Index.fromOneBased(1)),
                                null,
                                null, Highlight.YELLOW)
                                .execute(modelStub, new StorageStub()));
    }

    @Test
    public void execute_invalidOriginalPid_throwsCommandException() {
        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(new TrueParagraph(Index.fromOneBased(1),
                        new ParagraphContent("lupus")))).build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        assertThrows(CommandException.class,
                EditAnnotationCommand.COMMAND_WORD + ": condition hit for test case", () ->
                        new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                                ParagraphIdentifier.makeExistId(Index.fromOneBased(10)),
                                null,
                                null, Highlight.YELLOW)
                                .execute(modelStub, new StorageStub()));
    }

    @Test
    public void execute_validOrigPidInvalidTargetPid_throwsCommandException() {
        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(new TrueParagraph(Index.fromOneBased(1),
                        new ParagraphContent("lupus")))).build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        assertThrows(CommandException.class,
                EditAnnotationCommand.COMMAND_WORD + ": condition hit for test case", () ->
                        new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                                ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                                ParagraphIdentifier.makeExistId(Index.fromOneBased(10)),
                                null, null)
                                .execute(modelStub, new StorageStub()));
    }

    @Test
    public void execute_origParaNoAnnotation_throwsCommandException() {
        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(new TrueParagraph(Index.fromOneBased(1),
                        new ParagraphContent("lupus")))).build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        assertThrows(CommandException.class,
                String.format(EditAnnotationCommand.MESSAGE_NOTHING_TO_EDIT, "P1"), () ->
                        new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                                ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                                null,
                                null, Highlight.GREEN)
                                .execute(modelStub, new StorageStub()));
    }

    @Test
    public void execute_origHasAnnotationButTargetSameAsOrigPara_throwsCommandException() {
        Paragraph annotatedP = new TrueParagraph(Index.fromOneBased(1),
                new ParagraphContent("lupus"));
        annotatedP.addAnnotation(new Annotation(Highlight.PINK));
        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(annotatedP)).build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        assertThrows(CommandException.class,
                EditAnnotationCommand.MESSAGE_CANNOT_MOVE_TO_SAME_PARA, () ->
                        new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                                ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                                ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                                null, null)
                                .execute(modelStub, new StorageStub()));
    }

    @Test
    public void execute_noMoveOnTruePara_success() {
        Paragraph annotatedP = new TrueParagraph(Index.fromOneBased(1),
                new ParagraphContent("lupus"));
        annotatedP.addAnnotation(new Annotation(Highlight.PINK));

        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(annotatedP)).build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        assertDoesNotThrow(() -> new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                                ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                                null,
                                AnnotationNote.SAMPLE_NOTE, null)
                                .execute(modelStub, new StorageStub()));
        assertDoesNotThrow(() -> new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                null,
                AnnotationNote.SAMPLE_NOTE, Highlight.GREEN)
                .execute(modelStub, new StorageStub()));
        assertDoesNotThrow(() -> new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                null,
                null, Highlight.ORANGE)
                .execute(modelStub, new StorageStub()));
    }

    @Test
    public void execute_noMoveOnPhantom_success() {
        Paragraph annotatedP = new PhantomParagraph(Index.fromOneBased(1),
                new Annotation(Highlight.PINK, AnnotationNote.SAMPLE_NOTE));

        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(annotatedP)).build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        assertDoesNotThrow(() -> new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                ParagraphIdentifier.makeStrayId(Index.fromOneBased(1)),
                null,
                AnnotationNote.SAMPLE_NOTE, null)
                .execute(modelStub, new StorageStub()));
        assertDoesNotThrow(() -> new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                ParagraphIdentifier.makeStrayId(Index.fromOneBased(1)),
                null,
                AnnotationNote.SAMPLE_NOTE, Highlight.GREEN)
                .execute(modelStub, new StorageStub()));
        //currently does not check if note is equal to previous; no point checking since it's user choice
    }

    @Test
    public void execute_moveFromPhantomToTrue_success() {
        Paragraph annotatedP = new PhantomParagraph(Index.fromOneBased(1),
                new Annotation(Highlight.PINK, AnnotationNote.SAMPLE_NOTE));

        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(annotatedP)).build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        assertDoesNotThrow(() -> new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                ParagraphIdentifier.makeStrayId(Index.fromOneBased(1)),
                ParagraphIdentifier.makeExistId((Index.fromOneBased(1))),
                null, null)
                .execute(modelStub, new StorageStub()));

        validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(annotatedP)).build();
        ModelStubAcceptingBookmarkAdded modelStub2 = new ModelStubAcceptingBookmarkAdded(validBookmark);
        assertDoesNotThrow(() -> new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                ParagraphIdentifier.makeStrayId(Index.fromOneBased(1)),
                ParagraphIdentifier.makeExistId((Index.fromOneBased(1))),
                AnnotationNote.SAMPLE_NOTE, null)
                .execute(modelStub2, new StorageStub()));

        validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(annotatedP)).build();
        ModelStubAcceptingBookmarkAdded modelStub3 = new ModelStubAcceptingBookmarkAdded(validBookmark);
        assertDoesNotThrow(() -> new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                ParagraphIdentifier.makeStrayId(Index.fromOneBased(1)),
                ParagraphIdentifier.makeExistId((Index.fromOneBased(1))),
                AnnotationNote.SAMPLE_NOTE, Highlight.YELLOW)
                .execute(modelStub3, new StorageStub()));
    }

    @Test
    public void execute_moveFromTrueToTrue_success() {
        Paragraph annotatedP = new TrueParagraph(Index.fromOneBased(1),
                new ParagraphContent("sample content"));
        annotatedP.addAnnotation(new Annotation(Highlight.GREEN));
        Paragraph targetP = new TrueParagraph(Index.fromOneBased(2),
                new ParagraphContent("sample content"));

        Bookmark validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(annotatedP, targetP)).build();
        ModelStubAcceptingBookmarkAdded modelStub = new ModelStubAcceptingBookmarkAdded(validBookmark);

        validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(annotatedP.copy(), targetP.copy())).build();
        ModelStubAcceptingBookmarkAdded modelStub2 = new ModelStubAcceptingBookmarkAdded(validBookmark);

        validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(annotatedP.copy(), targetP.copy())).build();
        ModelStubAcceptingBookmarkAdded modelStub3 = new ModelStubAcceptingBookmarkAdded(validBookmark);

        validBookmark = new BookmarkBuilder().withUrl("http://anyurl")
                .withCachedCopy(new CachedCopyStub(annotatedP.copy(), targetP.copy())).build();
        ModelStubAcceptingBookmarkAdded modelStub4 = new ModelStubAcceptingBookmarkAdded(validBookmark);

        assertDoesNotThrow(() -> new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                ParagraphIdentifier.makeExistId(Index.fromOneBased(2)),
                AnnotationNote.SAMPLE_NOTE, null)
                .execute(modelStub, new StorageStub()));
        assertDoesNotThrow(() -> new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                ParagraphIdentifier.makeExistId(Index.fromOneBased(2)),
                AnnotationNote.SAMPLE_NOTE, Highlight.GREEN)
                .execute(modelStub2, new StorageStub()));
        assertDoesNotThrow(() -> new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                ParagraphIdentifier.makeExistId(Index.fromOneBased(2)),
                null, Highlight.ORANGE)
                .execute(modelStub3, new StorageStub()));
        assertDoesNotThrow(() -> new EditAnnotationCommand(INDEX_FIRST_BOOKMARK,
                ParagraphIdentifier.makeExistId(Index.fromOneBased(1)),
                ParagraphIdentifier.makeExistId(Index.fromOneBased(2)),
                null, null)
                .execute(modelStub4, new StorageStub()));
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

        @Override
        public void setOfflineDocNameCurrentlyShowing(String name) {
            // valid set doc title
        }
    }

    private class CachedCopyStub extends CachedCopy {
        private OfflineDocumentStub doc;

        CachedCopyStub(Paragraph p) {
            super("");
            doc = new OfflineDocumentStub(p);
        }

        CachedCopyStub(Paragraph p, Paragraph newP) {
            super("");
            doc = new OfflineDocumentStub(p, newP);
        }

        @Override
        public OfflineDocument getAnnotations() {
            return doc;
        }
    }

    private class OfflineDocumentStub extends OfflineDocument {
        private Paragraph p;
        private Paragraph newP;

        OfflineDocumentStub(Paragraph p) {
            super("");
            this.p = p;
            this.newP = null;
        }

        OfflineDocumentStub(Paragraph p, Paragraph newP) {
            this(p);
            this.newP = newP;
        }

        @Override
        public OfflineDocument copy() {
            return this;
        }

        @Override
        public void addAnnotation(ParagraphIdentifier pid, Annotation an) {
            //phantom add annotation for AddAnnotationCommand to call
        }

        @Override
        public Paragraph getParagraph(ParagraphIdentifier pid) throws IllegalValueException {
            if (p == null) {
                throw new IllegalValueException("no such paragraph anymore");
            }
            if (pid.equals(ParagraphIdentifier.makeExistId(Index.fromOneBased(10)))
                    || pid.equals(ParagraphIdentifier.makeStrayId(Index.fromOneBased(10)))) {
                throw new IllegalValueException("condition hit for test case");
            }
            return this.p;
        }

        @Override
        public void removePhantom(ParagraphIdentifier pid) {
            assert p != null : "phantom (supposedly) already removed.";
            p = null;
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
        public List<Paragraph> getCollection() {
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
        public void addPhantom(Annotation an) {
            assert false : "this method should not be called.";
        }
    }
}
