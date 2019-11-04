package seedu.moolah.logic.commands;

import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;

public class UndoCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager();
        expectedModel = new ModelManager(model);
    }

    @Test
    public void run_noPastModels_throwsCommandException() {
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_NO_MODEL);
    }

    @Test
    public void run_hasPastModels_success() {
        Model other = new ModelManager();
        model.addToPastHistory(other);
        expectedModel.addToFutureHistory(other);
        assertCommandSuccess(new UndoCommand(), model, String.format(UndoCommand.MESSAGE_SUCCESS, ""), expectedModel);
    }
}
