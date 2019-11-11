package seedu.moolah.logic.commands;

import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.modelhistory.ModelChanges;

public class UndoCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    @Test
    public void run_noPastChanges_throwsCommandException() {
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_NO_CHANGES);
    }

    @Test
    public void run_hasPastChanges_success() {
        String changeMessage = "test";
        ModelChanges changes = new ModelChanges(changeMessage).setMooLah(getTypicalMooLah());
        ModelChanges revert = new ModelChanges(changeMessage).setMooLah(model.getMooLah());

        model.addToPastChanges(changes);
        expectedModel.addToFutureChanges(revert);
        expectedModel.setMooLah(getTypicalMooLah());

        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS, changeMessage);
        assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);
    }
}
