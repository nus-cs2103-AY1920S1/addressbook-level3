package dukecooks.logic.commands.diary;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.TypicalIndexes.INDEX_FAILURE_DIARY;
import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_DIARY;
import static dukecooks.testutil.TypicalIndexes.INDEX_SECOND_DIARY;
import static dukecooks.testutil.diary.TypicalDiaries.getTypicalDiaryRecords;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.diary.components.Diary;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewDiaryCommand.
 */
public class ViewDiaryCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalDiaryRecords(), new UserPrefs());
        expectedModel = new ModelManager(model.getDiaryRecords(), new UserPrefs());
    }

    @Test
    public void execute_viewDiary_throwsCommandException() {
        CommandTestUtil.assertDiaryCommandFailure(new ViewDiaryCommand(INDEX_FAILURE_DIARY), model,
                Messages.MESSAGE_INVALID_DIARY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_viewDiary_success() {
        Diary viewedDiary = model.getFilteredDiaryList().get(INDEX_FIRST_DIARY.getZeroBased());
        assertCommandSuccess(new ViewDiaryCommand(INDEX_FIRST_DIARY), expectedModel,
                String.format(ViewDiaryCommand.MESSAGE_SUCCESS, viewedDiary.getDiaryName()),
                expectedModel);
    }

    @Test
    public void equals() {
        ViewDiaryCommand firstViewDiaryCommand = new ViewDiaryCommand(INDEX_FIRST_DIARY);
        ViewDiaryCommand secondViewDiaryCommand = new ViewDiaryCommand(INDEX_FIRST_DIARY);
        ViewDiaryCommand thirdViewDiaryCommand = new ViewDiaryCommand(INDEX_SECOND_DIARY);

        // same object -> return true
        assertTrue(firstViewDiaryCommand.equals(firstViewDiaryCommand));

        // same values -> returns true
        assertTrue(firstViewDiaryCommand.equals(secondViewDiaryCommand));

        // different types -> returns false
        assertFalse(firstViewDiaryCommand.equals(1));

        // null -> returns false
        assertFalse(firstViewDiaryCommand.equals(null));

        // different view diary commands -> returns false
        assertFalse(firstViewDiaryCommand.equals(thirdViewDiaryCommand));
    }
}
