package io.xpire.logic.commands;

import static io.xpire.logic.commands.CommandTestUtil.assertCommandSuccess;
import static io.xpire.logic.commands.CommandTestUtil.showXpireItemAtIndex;
import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static io.xpire.testutil.TypicalItems.getTypicalLists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;

//@@author febee99
/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewCommand.
 */
public class ViewCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalLists(), new UserPrefs());
        expectedModel = new ModelManager(model.getLists(), new UserPrefs());
    }

    //---------------- Tests for Xpire List --------------------------------------------------------------------------
    @Test
    public void execute_xpireIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ViewCommand(XPIRE), model, String.format(ViewCommand.MESSAGE_SUCCESS, "main"),
            expectedModel);
    }

    @Test
    public void execute_xpireIsFiltered_showsEverything() {
        showXpireItemAtIndex(model, INDEX_FIRST_ITEM);
        assertCommandSuccess(new ViewCommand(XPIRE), model, String.format(ViewCommand.MESSAGE_SUCCESS, "main"),
            expectedModel);
    }

    //---------------- Tests for Replenish List -----------------------------------------------------------------------
    @Test
    public void execute_replenishListIsNotFiltered_showsSameList() {
        expectedModel.setCurrentList(REPLENISH);
        assertCommandSuccess(new ViewCommand(REPLENISH), model, String.format(ViewCommand.MESSAGE_SUCCESS, "replenish"),
                expectedModel);
    }

    @Test
    public void execute_replenishListIsFiltered_showsEverything() {
        showXpireItemAtIndex(model, INDEX_FIRST_ITEM);
        expectedModel.setCurrentList(REPLENISH);
        assertCommandSuccess(new ViewCommand(REPLENISH), model, String.format(ViewCommand.MESSAGE_SUCCESS, "replenish"),
                expectedModel);
    }
}
