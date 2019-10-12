package seedu.mark.model.annotation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import seedu.mark.commons.core.index.Index;

class ParagraphIdentifierTest {
    Index index = Index.fromOneBased(1);

    @Test
    public void equals() {
        assertEquals(new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.EXIST),
                new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.EXIST));
    }

}