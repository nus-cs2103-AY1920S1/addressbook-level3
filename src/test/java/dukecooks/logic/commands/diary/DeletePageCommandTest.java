package dukecooks.logic.commands.diary;

import static dukecooks.testutil.diary.TypicalDiaries.ALL_MEAT;
import static dukecooks.testutil.diary.TypicalDiaries.getTypicalDiaryRecords;
import static dukecooks.testutil.diary.TypicalPages.PHO_PAGE;
import static dukecooks.testutil.diary.TypicalPages.SUSHI_PAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.diary.components.Diary;
import dukecooks.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeletePageCommandTest {

    private Model model = new ModelManager(getTypicalDiaryRecords(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        int size = model.getFilteredDiaryList().size();
        Diary targetDiary = model.getFilteredDiaryList().get(size - 1);
        int sizeOfPages = targetDiary.getPages().size();
        Index outOfBoundIndex = Index.fromOneBased(sizeOfPages + 1);
        DeletePageCommand deleteCommand = new DeletePageCommand(outOfBoundIndex, targetDiary.getDiaryName());

        assertFalse(targetDiary.getPages().size() < sizeOfPages, Messages.MESSAGE_INVALID_PAGE_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        Diary diary = ALL_MEAT;
        diary.getPages().add(PHO_PAGE);
        diary.getPages().add(SUSHI_PAGE);
        DeletePageCommand deleteFirstCommand =
                new DeletePageCommand(TypicalIndexes.INDEX_FIRST_PAGE, diary.getDiaryName());
        DeletePageCommand deleteSecondCommand =
                new DeletePageCommand(TypicalIndexes.INDEX_SECOND_PAGE, diary.getDiaryName());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePageCommand deleteFirstCommandCopy =
                new DeletePageCommand(TypicalIndexes.INDEX_FIRST_DIARY, diary.getDiaryName());
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different delete page commands -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
