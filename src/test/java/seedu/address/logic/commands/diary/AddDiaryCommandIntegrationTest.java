package seedu.address.logic.commands.diary;

import static seedu.address.logic.commands.CommandTestUtil.assertDiaryCommandFailure;
import static seedu.address.testutil.diary.TypicalDiaries.getTypicalDiaryRecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.diary.components.Diary;

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
        assertDiaryCommandFailure(new AddDiaryCommand(diaryInList), model, AddDiaryCommand.MESSAGE_DUPLICATE_DIARY);
    }

}
