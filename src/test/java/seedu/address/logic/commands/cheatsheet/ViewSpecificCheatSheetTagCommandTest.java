package seedu.address.logic.commands.cheatsheet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHEATSHEET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.ModelManager;
import seedu.address.testutil.CheatSheetBuilder;
import seedu.address.ui.CheatsheetTabWindowController;

public class ViewSpecificCheatSheetTagCommandTest {
    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ViewSpecificCheatSheetTagCommand(null));
    }

    @Test
    public void execute_cheatSheetAcceptedByModel_addSuccessful() throws Exception {
        ModelManager model = new ModelManager();
        Index validIndex = new Index(1);
        CheatsheetTabWindowController.setCurrCheatSheet(
                new CheatSheetBuilder().withTags(VALID_TAG_CHEATSHEET, VALID_TAG_IMPORTANT).build());

        CommandResult commandResult = new ViewSpecificCheatSheetTagCommand(validIndex).execute(model);
        assertEquals(
                String.format(ViewSpecificCheatSheetTagCommand.VIEW_TAG_CONTENT_SUCCESS, validIndex.getOneBased()),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Index validIndex1 = new Index(1);
        Index validIndex2 = new Index(2);
        ViewSpecificCheatSheetTagCommand viewCommand1 = new ViewSpecificCheatSheetTagCommand(validIndex1);
        ViewSpecificCheatSheetTagCommand viewCommand2 = new ViewSpecificCheatSheetTagCommand(validIndex2);

        // same object -> returns true
        assertTrue(viewCommand1.equals(viewCommand1));

        // same values -> returns true
        ViewSpecificCheatSheetTagCommand copy = new ViewSpecificCheatSheetTagCommand(validIndex1);
        assertTrue(viewCommand1.equals(copy));

        // different types -> returns false
        assertFalse(viewCommand1.equals(1));

        // null -> returns false
        assertFalse(viewCommand1.equals(null));

        // different person -> returns false
        assertFalse(viewCommand1.equals(viewCommand2));
    }
}
