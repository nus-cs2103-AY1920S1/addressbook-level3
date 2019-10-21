package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import org.junit.jupiter.api.Test;
import seedu.mark.commons.core.index.Index;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;
import seedu.mark.model.annotation.Highlight;
import seedu.mark.model.annotation.ParagraphIdentifier;
import seedu.mark.storage.Storage;
import seedu.mark.storage.StorageStub;

class AddAnnotationCommandTest {
    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());
    private Storage storage = new StorageStub();

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

}