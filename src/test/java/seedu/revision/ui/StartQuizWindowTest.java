package seedu.revision.ui;

import static seedu.revision.testutil.Assert.assertThrows;
import static seedu.revision.testutil.TypicalAnswerables.getTypicalRevisionTool;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.Logic;
import seedu.revision.logic.LogicManager;
import seedu.revision.model.History;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.UserPrefs;
import seedu.revision.model.quiz.Mode;
import seedu.revision.model.quiz.NormalMode;
import seedu.revision.storage.Storage;
import seedu.revision.stubs.StorageStub;

public class StartQuizWindowTest {

    private final Mode normalMode = new NormalMode();
    private Storage storageStub = new StorageStub();
    private Model model = new ModelManager(getTypicalRevisionTool(), new UserPrefs(), new History());
    private Logic logic = new LogicManager(model, storageStub);

    @Test
    public void constructor_nullStage_throwsAssertionError() {
        assertThrows(AssertionError.class, () ->
                new StartQuizWindow(null, logic, normalMode));

    }

}
