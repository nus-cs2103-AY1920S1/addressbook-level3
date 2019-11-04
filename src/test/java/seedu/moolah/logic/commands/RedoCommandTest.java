package seedu.moolah.logic.commands;

import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;

public class RedoCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager();
        expectedModel = new ModelManager(model);
    }

    @Test
    public void run_noFutureModels_throwsCommandException() {
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_NO_MODEL);
    }

    @Test
    public void run_hasFutureModels_success() {
        Model other = new ModelManager();
        model.addToFutureHistory(other);
        expectedModel.addToPastHistory(other);
        assertCommandSuccess(new RedoCommand(), model, String.format(RedoCommand.MESSAGE_SUCCESS, ""), expectedModel);
    }
}
