package dukecooks.logic.commands.diary;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_DIARY;
import static dukecooks.testutil.diary.TypicalDiaries.getTypicalDiaryRecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListDiaryCommand.
 */
public class ListDiaryCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalDiaryRecords(), new UserPrefs());
        expectedModel = new ModelManager(model.getDiaryRecords(), new UserPrefs());
    }

    @Test
    public void execute_diaryListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListDiaryCommand(), model, ListDiaryCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_diaryListIsFiltered_showsEverything() {
        CommandTestUtil.showDiaryAtIndex(model, INDEX_FIRST_DIARY);
        assertCommandSuccess(new ListDiaryCommand(), model, ListDiaryCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
