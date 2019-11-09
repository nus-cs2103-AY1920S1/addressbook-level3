package io.xpire.logic.commands;

import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.TypicalItems.getTypicalLists;

import org.junit.jupiter.api.Test;

import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.ReplenishList;
import io.xpire.model.UserPrefs;
import io.xpire.model.Xpire;

public class ClearCommandTest {

    @Test
    public void execute_emptyXpire_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandTestUtil.assertCommandSuccess(new ClearCommand(XPIRE), model,
                ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyXpire_success() {
        Model model = new ModelManager(getTypicalLists(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        expectedModel.setXpire(new Xpire());

        CommandTestUtil.assertCommandSuccess(new ClearCommand(XPIRE), model,
                ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyReplenishList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        CommandTestUtil.assertCommandSuccess(new ClearCommand(REPLENISH), model,
                ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyReplenishList_success() {
        Model model = new ModelManager(getTypicalLists(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        expectedModel.setReplenishList(new ReplenishList());
        model.setCurrentList(REPLENISH);
        expectedModel.setCurrentList(REPLENISH);
        CommandTestUtil.assertCommandSuccess(new ClearCommand(REPLENISH), model,
                ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
