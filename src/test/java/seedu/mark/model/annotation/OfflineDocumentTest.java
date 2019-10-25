package seedu.mark.model.annotation;

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
    private OfflineDocument cleandoc = new OfflineDocument(OFFLINE_DOC_EXAMPLE);
    private ParagraphIdentifier pid = new ParagraphIdentifier(Index.fromOneBased(1),
            ParagraphIdentifier.ParagraphType.EXIST);
    private ParagraphIdentifier pidInvalid = new ParagraphIdentifier(Index.fromOneBased(3),
            ParagraphIdentifier.ParagraphType.STRAY);
    private HashMap<Annotation, ParagraphIdentifier> ans;

    @BeforeEach
    public void OfflineDocumentTest() throws Exception {
        ans = new HashMap<>();
    }

    @Test
    public void addPhantom_numStrayIncreases() {
        int initNumStray = cleandoc.getNumStrayNotes();
        cleandoc.addPhantom(new Annotation(Highlight.YELLOW, AnnotationNote.SAMPLE_NOTE));
        assertEquals(initNumStray + 1, cleandoc.getNumStrayNotes());
        ParagraphIdentifier tmpid = ParagraphIdentifier.makeStrayId(Index.fromOneBased(initNumStray + 1));
        assertEquals(cleandoc.hasParagraph(tmpid), true);
    }

    @Test
    public void removePhantom_pidExists_paragraphRemoved() throws Exception {
        addPhantom_numStrayIncreases();
        ParagraphIdentifier tmpid = ParagraphIdentifier.makeStrayId(Index.fromOneBased(cleandoc.getNumStrayNotes()));
        assertEquals(cleandoc.hasParagraph(tmpid), true);
        cleandoc.removePhantom(tmpid);
        assertNotEquals(cleandoc.hasParagraph(tmpid), true);
    }

    @Test
    public void removePhantom_pidInvalid_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, OfflineDocument.MESSAGE_INVALID_PID, () ->
                cleandoc.removePhantom(ParagraphIdentifier.makeStrayId(Index.fromOneBased(1))));
    }

    @Test
    public void addAnnotation_pidInvalid_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, OfflineDocument.MESSAGE_INVALID_PID, () ->
                cleandoc.addAnnotation(ParagraphIdentifier.makeStrayId(Index.fromOneBased(1)),
                        new Annotation(Highlight.GREEN, AnnotationNote.SAMPLE_NOTE)));
    }

    @Test
    public void addAnnotation_pidValid_addsAnnotation() {
        //TODO: nth happens tho assertnotthrows have?
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
