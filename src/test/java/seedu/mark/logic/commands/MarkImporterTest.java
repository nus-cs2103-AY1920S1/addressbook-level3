package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.testutil.TypicalBookmarks.ALICE;
import static seedu.mark.testutil.TypicalBookmarks.BENSON;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalBookmarks;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.ImportCommand.MarkImporter;
import seedu.mark.model.Mark;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.UserPrefs;

public class MarkImporterTest {

    @Test
    public void equals() {
        Model model = new ModelManager();
        ReadOnlyMark mark = new Mark();
        MarkImporter markImporter = new MarkImporter(model, mark);

        // same values -> returns true
        assertTrue(markImporter.equals(new MarkImporter(model, mark)));

        // same object -> returns true
        assertTrue(markImporter.equals(markImporter));

        // null -> returns false
        assertFalse(markImporter.equals(null));

        // different types -> returns false
        assertFalse(markImporter.equals(5));

        // different model -> returns false
        Model typicalMarkModel = new ModelManager(getTypicalMark(), new UserPrefs());
        assertFalse(markImporter.equals(new MarkImporter(typicalMarkModel, mark)));

        // different existing bookmarks -> returns false
        Mark markWithOneDuplicate = new Mark();
        markWithOneDuplicate.setBookmarks(Arrays.asList(ALICE));
        Mark markWithTwoDuplicates = new Mark();
        markWithTwoDuplicates.setBookmarks(Arrays.asList(ALICE, BENSON));
        assertFalse(new MarkImporter(typicalMarkModel, markWithOneDuplicate).equals(
                new MarkImporter(typicalMarkModel, markWithTwoDuplicates)));

        // different bookmarks to import -> returns false
        Mark differentMark = new Mark();
        differentMark.setBookmarks(getTypicalBookmarks());
        assertFalse(markImporter.equals(new MarkImporter(model, differentMark)));
    }
}
