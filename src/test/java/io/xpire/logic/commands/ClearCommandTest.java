package io.xpire.logic.commands;

import static io.xpire.testutil.TypicalItems.getTypicalLists;

import org.junit.jupiter.api.Test;

import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.Xpire;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandTestUtil.assertCommandSuccess(new ClearCommand("main"), model,
                ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalLists(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        expectedModel.setXpire(new Xpire());

        CommandTestUtil.assertCommandSuccess(new ClearCommand("main"), model,
                ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
