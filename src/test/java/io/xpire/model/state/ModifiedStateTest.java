package io.xpire.model.state;

import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.model.CloneModel;
import io.xpire.model.ListType;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;

public class ModifiedStateTest {

    private Model model;

    @BeforeEach
    public void initialise() {
        model = new ModelManager(getTypicalLists(), new UserPrefs());
    }

    @Test
    public void pointToDifferentObject() {
        ModifiedState modifiedState = new ModifiedState(model);
        CloneModel cloneModel = modifiedState.getCloneModel();
        assertNotSame(cloneModel.getXpire(), model.getXpire());
        assertNotSame(cloneModel.getReplenishList(), model.getReplenishList());
        assertNotSame(cloneModel.getUserPrefs(), model.getUserPrefs());
    }

    @Test
    public void pointToSameVariables() {
        ModifiedState modifiedState = new ModifiedState(model);
        assertSame(modifiedState.getListType(), ListType.XPIRE);
        assertSame(modifiedState.getMethod(), model.getXpire().getMethodOfSorting());
        assertEquals(modifiedState.getPredicate(), model.getCurrentList().getPredicate());
    }

}
