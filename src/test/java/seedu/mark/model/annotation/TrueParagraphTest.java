package seedu.mark.model.annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.index.Index;

class TrueParagraphTest {

    @Test
    public void equals_test() {
        TrueParagraph p1 = new TrueParagraph(Index.fromOneBased(1), new ParagraphContent("test content"));
        TrueParagraph p2 = new TrueParagraph(Index.fromOneBased(1), new ParagraphContent("test content"));
        TrueParagraph p3 = new TrueParagraph(Index.fromOneBased(1), new ParagraphContent("test 2 content"));

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
    }
}
