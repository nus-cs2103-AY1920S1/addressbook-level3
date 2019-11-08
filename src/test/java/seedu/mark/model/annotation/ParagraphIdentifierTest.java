package seedu.mark.model.annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.index.Index;

class ParagraphIdentifierTest {
    private Index index = Index.fromOneBased(1);
    private Index index2 = Index.fromOneBased(2);

    @Test
    public void equals_check() {
        assertEquals(new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.EXIST),
                new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.EXIST));
    }

    @Test
    public void hashCode_check() {
        assertEquals(new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.EXIST).hashCode(),
                new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.EXIST).hashCode());
    }

    @Test
    public void compareTo_check() {
        ParagraphIdentifier p1 = new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.EXIST);
        ParagraphIdentifier p2 = new ParagraphIdentifier(index2, ParagraphIdentifier.ParagraphType.EXIST);
        ParagraphIdentifier p3 = new ParagraphIdentifier(index2, ParagraphIdentifier.ParagraphType.STRAY);
        ParagraphIdentifier p4 = new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.STRAY);

        assertEquals(-1, p1.compareTo(p2));
        assertEquals(-1, p1.compareTo(p4));
        assertEquals(1, p2.compareTo(p1));
        assertEquals(-1, p2.compareTo(p3));

        assertEquals(1, p3.compareTo(p4));
        assertEquals(1, p3.compareTo(p2));
        assertEquals(-1, p4.compareTo(p3));
    }

    @Test
    public void toString_check() {
        assertEquals("P1", new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.EXIST).toString());
        assertEquals("G1", new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.STRAY).toString());
    }

}
