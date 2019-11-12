package seedu.address.logic.commands.cheatsheet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalFlashcards.getTypicalStudyBuddyPro;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CHEATSHEET;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CHEATSHEET;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ViewCheatSheetCommandTest {

    private Model model = new ModelManager(getTypicalStudyBuddyPro(), new UserPrefs());

    @Test
    public void equals() {
        ViewCheatSheetCommand firstViewCommand = new ViewCheatSheetCommand(INDEX_FIRST_CHEATSHEET);
        ViewCheatSheetCommand secondViewCommand = new ViewCheatSheetCommand(INDEX_SECOND_CHEATSHEET);

        // same object -> returns true
        assertTrue(firstViewCommand.equals(firstViewCommand));

        // same values -> returns true
        ViewCheatSheetCommand firstViewCheatSheetCommandCopy = new ViewCheatSheetCommand(INDEX_FIRST_CHEATSHEET);
        assertTrue(firstViewCommand.equals(firstViewCheatSheetCommandCopy));

        // different types -> returns false
        assertFalse(firstViewCommand.equals(1));

        // null -> returns false
        assertFalse(firstViewCommand.equals(null));

        // different cheatsheet -> returns false
        assertFalse(firstViewCommand.equals(secondViewCommand));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCheatSheetList().size() + 1);
        ViewCheatSheetCommand viewCheatSheetCommand = new ViewCheatSheetCommand(outOfBoundIndex);

        assertCommandFailure(viewCheatSheetCommand, model, Messages.MESSAGE_INVALID_CHEATSHEET_DISPLAYED_INDEX);
    }


}
