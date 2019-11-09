package io.xpire.logic.commands;

import static io.xpire.commons.core.Messages.MESSAGE_EMPTY_LIST;
import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.TypicalItems.getTypicalLists;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.ReplenishList;
import io.xpire.model.UserPrefs;
import io.xpire.model.Xpire;
import io.xpire.model.item.ContainsKeywordsPredicate;

//@@author JermyTan
public class ClearCommandTest {

    @Test
    public void execute_emptyXpire_failure() {
        Model model = new ModelManager();

        CommandTestUtil.assertCommandFailure(new ClearCommand(XPIRE), model, MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_emptyReplenishList_failure() {
        Model model = new ModelManager();

        CommandTestUtil.assertCommandFailure(new ClearCommand(REPLENISH), model, MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_emptyXpireFiltered_failure() {
        Model model = new ModelManager();
    }

    @Test
    public void execute_nonEmptyXpireViewAll_success() {
        Model model = new ModelManager(getTypicalLists(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        expectedModel.setXpire(new Xpire());

        CommandTestUtil.assertCommandSuccess(new ClearCommand(XPIRE), model,
                ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyReplenishListViewAll_success() {
        Model model = new ModelManager(getTypicalLists(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        expectedModel.setReplenishList(new ReplenishList());
        model.setCurrentList(REPLENISH);
        expectedModel.setCurrentList(REPLENISH);
        CommandTestUtil.assertCommandSuccess(new ClearCommand(REPLENISH), model,
                ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
/*
    @Test
    protected void execute_NonEmptyXpireFilter_success() {
        Model model = new ModelManager(getTypicalLists(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        model.filterCurrentList(XPIRE, new ContainsKeywordsPredicate(List.of(new String[] {"i"})));

        CommandTestUtil.assertCommandSuccess(new ClearCommand(XPIRE), model,
                ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
 */
}
