package thrift.logic.commands;

import static thrift.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.Thrift;
import thrift.model.UserPrefs;
import thrift.testutil.TypicalTransactions;

public class ClearCommandTest {

    @Test
    public void execute_emptyThift_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyThrift_success() {
        Model model = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalTransactions.getTypicalThrift(), new UserPrefs());
        expectedModel.setThrift(new Thrift());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
