package io.xpire.model.state;

import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.model.CloneModel;
import io.xpire.model.ListType;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;

public class FilteredStateTest {

    private Model model;

    @BeforeEach
    public void initialise() {
        model = new ModelManager(getTypicalLists(), new UserPrefs());
    }

    @Test
    public void pointToSameLists() {
        FilteredState filteredState = new FilteredState(model);
        CloneModel cloneModel = filteredState.getCloneModel();
        assertSame(cloneModel.getXpire(), model.getXpire());
        assertSame(cloneModel.getReplenishList(), model.getReplenishList());
        assertSame(cloneModel.getUserPrefs(), model.getUserPrefs());
    }

    @Test
    public void pointToSameVariables() {
        FilteredState filteredState = new FilteredState(model);
        assertSame(filteredState.getListType(), ListType.XPIRE);
        assertSame(filteredState.getMethod(), model.getXpire().getMethodOfSorting());
        assertEquals(filteredState.getPredicate(), model.getCurrentList().getPredicate());
    }

}
