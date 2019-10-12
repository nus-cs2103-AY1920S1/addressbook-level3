package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalDiaries.getTypicalDukeCooks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.diary.Diary;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalDukeCooks(), new UserPrefs());
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Diary diaryInList = model.getDukeCooks().getDiaryList().get(0);
        assertCommandFailure(new AddCommand(diaryInList), model, AddCommand.MESSAGE_DUPLICATE_DIARY);
    }

}
