package io.xpire.model.state;

import static io.xpire.model.ListType.XPIRE;
import static io.xpire.testutil.TypicalItems.CORIANDER;
import static io.xpire.testutil.TypicalItems.getTypicalLists;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.sort.XpireMethodOfSorting;
import io.xpire.testutil.XpireItemBuilder;

public class StackManagerTest {

    private Model model;
    private ModifiedState modifiedState;
    private FilteredState filteredState;
    private final StateManager stackManager = new StackManager();

    @BeforeEach
    public void initialise() {
        model = new ModelManager(getTypicalLists(), new UserPrefs());
        model.sortXpire(new XpireMethodOfSorting("date"));
        filteredState = new FilteredState(model);
        model.addItem(XPIRE, new XpireItemBuilder(CORIANDER).build());
        modifiedState = new ModifiedState(model);

    }

    @Test
    public void saveCorrectStates() {
        stackManager.saveState(filteredState);

        assertSame(filteredState, stackManager.undo(modifiedState));
        assertSame(modifiedState, stackManager.redo());
        //repeated to ensure that the undo/redo still holds
        assertSame(filteredState, stackManager.undo(modifiedState));
        assertSame(modifiedState, stackManager.redo());
    }

}
