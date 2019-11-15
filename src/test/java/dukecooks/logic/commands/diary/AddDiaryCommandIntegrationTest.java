package dukecooks.logic.commands.diary;

import static dukecooks.testutil.diary.TypicalDiaries.getTypicalDiaryRecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.diary.components.Diary;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddDiaryCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalDiaryRecords(), new UserPrefs());
    }

    @Test
    public void execute_duplicateDiaries_throwsCommandException() {
        Diary diaryInList = model.getDiaryRecords().getDiaryList().get(0);
        CommandTestUtil.assertDiaryCommandFailure(new AddDiaryCommand(diaryInList), model,
                AddDiaryCommand.MESSAGE_DUPLICATE_DIARY);
    }

}
