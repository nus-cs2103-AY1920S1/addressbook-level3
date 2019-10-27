package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.mark.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.mark.commons.core.index.Index;
import seedu.mark.model.annotation.ParagraphIdentifier;

class DeleteAnnotationCommandTest {
    private Index index = Index.fromOneBased(1);
    private ParagraphIdentifier pid = new ParagraphIdentifier(index, ParagraphIdentifier.ParagraphType.EXIST);


    @Test
    public void constructor_nullParams_throwsNullPointerExceptions() {
        assertThrows(NullPointerException.class, () ->
                new DeleteAnnotationCommand(null, pid, false, false));
        assertThrows(NullPointerException.class, () ->
                new DeleteAnnotationCommand(index, null, false, false));
    }

    @Test
    public void equals_success() {
        assertEquals(new DeleteAnnotationCommand(index, pid, false, false),
                new DeleteAnnotationCommand(index, pid, false, false));
        assertNotEquals(new DeleteAnnotationCommand(index, pid, false, false),
                new DeleteAnnotationCommand(index, pid, false, true));
    }
}
