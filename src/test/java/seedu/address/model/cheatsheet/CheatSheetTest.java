package seedu.address.model.cheatsheet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_GEM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_MATH;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCheatSheets.CS6;
import static seedu.address.testutil.TypicalCheatSheets.CS7;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CheatSheetBuilder;

public class CheatSheetTest {
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        CheatSheet cheatSheet = new CheatSheetBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> cheatSheet.getTags().remove(0));
    }

    @Test
    public void isSameCheatSheet() {
        // same object -> returns true
        assertTrue(CS6.isSameCheatSheet(CS6));

        // null -> returns false
        assertFalse(CS6.isSameCheatSheet(null));

        // different title and tags -> returns false
        CheatSheet editedCs6 = new CheatSheetBuilder(CS6)
                .withTitle(VALID_TITLE_GEM).withTags(VALID_TAG_IMPORTANT).build();
        assertFalse(CS6.isSameCheatSheet(editedCs6));

        // different title -> returns false
        editedCs6 = new CheatSheetBuilder(CS6).withTitle(VALID_TITLE_GEM).build();
        assertFalse(CS6.isSameCheatSheet(editedCs6));

        // same title, different tags -> returns true
        editedCs6 = new CheatSheetBuilder(CS6)
                .withTitle(VALID_TITLE_MATH)
                .withTags(VALID_TAG_IMPORTANT).build();
        assertTrue(CS6.isSameCheatSheet(editedCs6));
    }

    @Test
    public void equals() {
        // same values -> returns true
        CheatSheet cs6Copy = new CheatSheetBuilder(CS6).build();
        assertTrue(CS6.equals(cs6Copy));

        // same object -> returns true
        assertTrue(CS6.equals(CS6));

        // null -> returns false
        assertFalse(CS6.equals(null));

        // different type -> returns false
        assertFalse(CS6.equals(5));

        // different cheatsheet -> returns false
        assertFalse(CS6.equals(CS7));

        // different title -> returns false
        CheatSheet editedCs6 = new CheatSheetBuilder(CS6).withTitle(VALID_TITLE_GEM).build();
        assertFalse(CS6.equals(editedCs6));

        // different tags -> returns false
        editedCs6 = new CheatSheetBuilder(CS6).withTags(VALID_TAG_IMPORTANT).build();
        assertFalse(CS6.equals(editedCs6));
    }
}
