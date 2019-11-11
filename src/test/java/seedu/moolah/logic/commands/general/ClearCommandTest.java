package seedu.moolah.logic.commands.general;

import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ModelHistory;

public class ClearCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        expectedModel = new ModelManager();
    }

    @Test
    public void run_emptyMooLah_success() {
        ClearCommand clearCommand = new ClearCommand();

        model = new ModelManager();
        expectedModel.addToPastChanges(new ModelChanges(clearCommand.getDescription()));

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void run_nonEmptyMooLah_success() {
        ClearCommand clearCommand = new ClearCommand();

        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel.addToPastChanges(new ModelChanges(clearCommand.getDescription()).setMooLah(model.getMooLah()));

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
