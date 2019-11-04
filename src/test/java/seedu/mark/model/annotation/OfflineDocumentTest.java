package seedu.mark.model.annotation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.OfflineUtil.OFFLINE_DOC_EXAMPLE;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.index.Index;
import seedu.mark.commons.exceptions.IllegalValueException;

class OfflineDocumentTest {
    private OfflineDocument cleandoc;
    private ParagraphIdentifier pid = new ParagraphIdentifier(Index.fromOneBased(1),
            ParagraphIdentifier.ParagraphType.EXIST);
    private ParagraphIdentifier pidInvalid = new ParagraphIdentifier(Index.fromOneBased(3),
            ParagraphIdentifier.ParagraphType.STRAY);
    private HashMap<Annotation, ParagraphIdentifier> ans;

    @BeforeEach
    public void constructOfflineDocumentTest() throws Exception {
        cleandoc = new OfflineDocument(OFFLINE_DOC_EXAMPLE);
        assertEquals(true, cleandoc.hasParagraph(pid));
        assertEquals(false, cleandoc.hasParagraph(pidInvalid));

        ans = new HashMap<>();
    }

    @Test
    public void copy_equals() {
        assertNotEquals(cleandoc, cleandoc.copy());
        assertEquals(cleandoc.getCollection(), cleandoc.copy().getCollection());
    }

    @Test
    public void getParagraph_validPid_noExceptionThrown() {
        assertDoesNotThrow(() -> cleandoc.getParagraph(pid));
    }

    @Test
    public void getParagraph_invalidPid_throwsIllegalValueException() {
        int impossiblePidInt = cleandoc.getCollection().size();
        assertThrows(IllegalValueException.class, OfflineDocument.MESSAGE_INVALID_PID, () ->
                cleandoc.getParagraph(ParagraphIdentifier.makeExistId(Index.fromZeroBased(impossiblePidInt))));
        assertThrows(IllegalValueException.class, OfflineDocument.MESSAGE_INVALID_PID, () ->
                cleandoc.getParagraph(pidInvalid));
    }

    @Test
    public void addPhantom_numStrayIncreases() {
        int initNumStray = cleandoc.getNumStrayNotes();
        cleandoc.addPhantom(new Annotation(Highlight.YELLOW, AnnotationNote.SAMPLE_NOTE));
        assertEquals(initNumStray + 1, cleandoc.getNumStrayNotes());
        ParagraphIdentifier tmpid = ParagraphIdentifier.makeStrayId(Index.fromOneBased(initNumStray + 1));
        assertEquals(true, cleandoc.hasParagraph(tmpid));
    }

    @Test
    public void addPhantom_nullAnnotation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> cleandoc.addPhantom(null));
    }

    @Test
    public void addPhantom_invalidAnnotation_throwsAssertionError() {
        assertThrows(AssertionError.class, OfflineDocument.MESSAGE_ASSERT_PHANTOM_HAS_NOTE, () ->
                cleandoc.addPhantom(new Annotation(Highlight.GREEN)));
    }

    @Test
    public void removePhantom_pidExists_paragraphRemoved() throws Exception {
        addPhantom_numStrayIncreases();
        ParagraphIdentifier tmpid = ParagraphIdentifier.makeStrayId(Index.fromOneBased(cleandoc.getNumStrayNotes()));
        assertEquals(true, cleandoc.hasParagraph(tmpid));
        cleandoc.removePhantom(tmpid);
        assertNotEquals(true, cleandoc.hasParagraph(tmpid));
    }

    @Test
    public void removePhantom_pidInvalid_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, OfflineDocument.MESSAGE_INVALID_PID, () ->
                cleandoc.removePhantom(ParagraphIdentifier.makeStrayId(Index.fromOneBased(1))));
        assertThrows(AssertionError.class, OfflineDocument.MESSAGE_ASSERT_IS_PHANTOM, () ->
                cleandoc.removePhantom(pid));
    }

    @Test
    public void addAnnotation_pidInvalid_throwsIllegalValueException() {
        int impossiblePidInt = cleandoc.getCollection().size();
        assertThrows(IllegalValueException.class, OfflineDocument.MESSAGE_INVALID_PID, () ->
                cleandoc.addAnnotation(ParagraphIdentifier.makeExistId(Index.fromZeroBased(impossiblePidInt)),
                        new Annotation(Highlight.GREEN, AnnotationNote.SAMPLE_NOTE)));
    }

    @Test
    public void addAnnotation_pidStray_throwsAssertionError() {
        assertThrows(AssertionError.class, OfflineDocument.MESSAGE_ASSERT_NOT_PHANTOM, () ->
                cleandoc.addAnnotation(ParagraphIdentifier.makeStrayId(Index.fromOneBased(1)),
                        new Annotation(Highlight.YELLOW)));
    }

    @Test
    public void addAnnotation_pidValid_addsAnnotation() throws Exception {
        assertEquals(null, cleandoc.getParagraph(pid).getAnnotation());

        assertDoesNotThrow(() -> cleandoc.addAnnotation(pid,
                new Annotation(Highlight.GREEN, AnnotationNote.SAMPLE_NOTE)));

        assertEquals(new Annotation(Highlight.GREEN, AnnotationNote.SAMPLE_NOTE),
                cleandoc.getParagraph(pid).getAnnotation());

        cleandoc.addPhantom(new Annotation(Highlight.PINK, AnnotationNote.SAMPLE_NOTE));
        assertDoesNotThrow(() -> cleandoc.addAnnotation(ParagraphIdentifier.makeStrayId(Index.fromOneBased(1)),
                new Annotation(Highlight.ORANGE, AnnotationNote.SAMPLE_NOTE)));
    }

    @Test
    public void updateStrayIndex_checkBeforeAfterStrayParagraphs() throws Exception {
        ParagraphIdentifier s2 = ParagraphIdentifier.makeStrayId(Index.fromOneBased(2));
        ParagraphIdentifier s3 = ParagraphIdentifier.makeStrayId(Index.fromOneBased(3));
        Annotation an1 = new Annotation(Highlight.GREEN, AnnotationNote.SAMPLE_NOTE);
        Annotation an2 = new Annotation(Highlight.PINK, AnnotationNote.SAMPLE_NOTE);
        Annotation an3 = new Annotation(Highlight.ORANGE, AnnotationNote.SAMPLE_NOTE);

        assertEquals(0, cleandoc.getNumStrayNotes());
        cleandoc.addPhantom(an1);
        assertEquals(1, cleandoc.getNumStrayNotes());
        assertNotEquals(true, cleandoc.hasParagraph(s3));
        cleandoc.addPhantom(an2);
        cleandoc.addPhantom(an3);

        assertEquals(3, cleandoc.getNumStrayNotes());
        assertEquals(true, cleandoc.hasParagraph(s3));

        assertDoesNotThrow(() -> cleandoc.removePhantom(s2));
        assertEquals(false, cleandoc.hasParagraph(s2));
        assertEquals(true, cleandoc.hasParagraph(s3));

        cleandoc.updateStrayIndex();
        assertEquals(2, cleandoc.getNumStrayNotes());
        assertEquals(true, cleandoc.hasParagraph(s2));
        assertNotEquals(true, cleandoc.hasParagraph(s3));
        assertEquals(an3, cleandoc.getParagraph(s2).getAnnotation());
    }

    @Test
    public void loadAnnotations_annotationNull_noAnnotationAdded() throws Exception {
        assertEquals(null, cleandoc.getParagraph(pid).getAnnotation());
        ans.put(null, pid);
        cleandoc.loadAnnotations(ans);

        assertEquals(null, cleandoc.getParagraph(pid).getAnnotation());
    }

    @Test
    public void loadAnnotations_noSuchParagraph_addPhantom() {
        ans.put(new Annotation(Highlight.ORANGE, AnnotationNote.SAMPLE_NOTE), pidInvalid);
        int numParagraphsTotal = cleandoc.getCollection().size();
        cleandoc.loadAnnotations(ans);
        assertEquals(numParagraphsTotal + 1, cleandoc.getCollection().size());
    }

    @Test
    public void loadAnnotations_paragraphExists_noNewParagraphs() {
        ans.put(new Annotation(Highlight.YELLOW, AnnotationNote.SAMPLE_NOTE), pid);
        int numParagraphsTotal = cleandoc.getCollection().size();
        cleandoc.loadAnnotations(ans);
        assertEquals(numParagraphsTotal, cleandoc.getCollection().size());
    }


}
