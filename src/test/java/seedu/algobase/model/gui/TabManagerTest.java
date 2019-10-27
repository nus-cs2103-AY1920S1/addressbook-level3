package seedu.algobase.model.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;

public class TabManagerTest {

    private static final Index OLD_VALUE = ModelType.PROBLEM.getDisplayTabPaneIndex();
    private static final Index NEW_VALUE = ModelType.TAG.getDisplayTabPaneIndex();
    private static final Index ILLEGAL_VALUE = Index.fromZeroBased(ModelType.values().length);

    @Test
    public void setDisplayTabPaneIndex_indexWithinRange_success() {
        TabManager tabManager = new TabManager();
        Index[] testValues = new Index[] { OLD_VALUE };

        tabManager.getDisplayTabPaneIndex().addListener((observable, oldValue, newValue) -> {
            testValues[0] = Index.fromZeroBased((newValue.intValue()));
        });
        tabManager.setDisplayTabPaneIndex(NEW_VALUE);

        assertEquals(NEW_VALUE, testValues[0], "Listener does not update value correctly");
    }

    @Test
    public void setDisplayTabPaneIndex_indexOutsideRange_throwsIndexOutOfBoundsException() {
        TabManager tabManager = new TabManager();
        Index[] testValues = new Index[] { OLD_VALUE };

        tabManager.getDisplayTabPaneIndex().addListener((observable, oldValue, newValue) -> {
            testValues[0] = Index.fromZeroBased((newValue.intValue()));
        });

        assertThrows(IndexOutOfBoundsException.class, () -> tabManager.setDisplayTabPaneIndex(ILLEGAL_VALUE));
    }

    @Test
    public void setDetailsTabPaneIndex_indexWithinRange_success() {
        TabManager tabManager = new TabManager();
        TabData tabData1 = new TabData(ModelType.PROBLEM, Id.generateId(100000000));
        TabData tabData2 = new TabData(ModelType.PROBLEM, Id.generateId(100000001));
        tabManager.addDetailsTabData(tabData1);
        tabManager.addDetailsTabData(tabData2);
        Index[] testValues = new Index[] { OLD_VALUE };

        tabManager.getDetailsTabPaneIndex().addListener((observable, oldValue, newValue) -> {
            testValues[0] = Index.fromZeroBased((newValue.intValue()));
        });
        tabManager.setDetailsTabPaneIndex(NEW_VALUE);

        assertEquals(NEW_VALUE, testValues[0], "Listener does not update value correctly");
    }

    @Test
    public void setDetailsTabPaneIndex_indexOutsideRange_throwsIndexOutOfBoundsException() {
        TabManager tabManager = new TabManager();
        Index[] testValues = new Index[] { OLD_VALUE };

        tabManager.getDetailsTabPaneIndex().addListener((observable, oldValue, newValue) -> {
            testValues[0] = Index.fromZeroBased((newValue.intValue()));
        });

        assertThrows(IndexOutOfBoundsException.class, () -> tabManager.setDetailsTabPaneIndex(ILLEGAL_VALUE));
    }
}
