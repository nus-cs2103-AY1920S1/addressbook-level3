package io.xpire.model;

import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.model.state.State;

public class CloneModelTest {

    private Model model;
    private CloneModel cloneModelFiltered;
    private CloneModel cloneModelModified;

    @BeforeEach
    public void initialise() {
        model = new ModelManager(getTypicalLists(), new UserPrefs());
        cloneModelFiltered = new CloneModel(model.getXpire(), model.getReplenishList(),
                model.getUserPrefs(), State.StateType.FILTERED);
        cloneModelModified = new CloneModel(model.getXpire(), model.getReplenishList(),
                model.getUserPrefs(), State.StateType.MODIFIED);
    }

    @Test
    public void constructor() {
        assertEquals(model.getXpire(), cloneModelFiltered.getXpire());
        assertEquals(model.getXpire(), cloneModelModified.getXpire());
        assertSame(model.getXpire(), cloneModelFiltered.getXpire());
        assertNotSame(model.getXpire(), cloneModelModified.getXpire());

        assertEquals(model.getReplenishList(), cloneModelFiltered.getReplenishList());
        assertEquals(model.getReplenishList(), cloneModelModified.getReplenishList());
        assertSame(model.getReplenishList(), cloneModelFiltered.getReplenishList());
        assertNotSame(model.getReplenishList(), cloneModelModified.getReplenishList());

        assertEquals(model.getUserPrefs(), cloneModelFiltered.getUserPrefs());
        assertEquals(model.getUserPrefs(), cloneModelModified.getUserPrefs());
        assertSame(model.getUserPrefs(), cloneModelFiltered.getUserPrefs());
        assertNotSame(model.getUserPrefs(), cloneModelModified.getUserPrefs());
    }
}
