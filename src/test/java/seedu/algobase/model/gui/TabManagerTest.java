package seedu.algobase.model.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;

public class TabManagerTest {

    private static final Index OLD_VALUE = Index.fromZeroBased(0);
    private static final Index NEW_VALUE = Index.fromZeroBased(1);
    private static final Index ILLEGAL_VALUE = Index.fromZeroBased(10);

    @Test
    public void setDisplayTabPaneIndex_indexWithinRange_success() {
        TabManager tabManager = new TabManager();
        Index[] testValues = new Index[] { OLD_VALUE };

        tabManager.getDisplayTabPaneIndex().addListener((observable, oldValue, newValue) -> {
            testValues[0] = Index.fromZeroBased((newValue.intValue()));
        });
        tabManager.switchDisplayTab(NEW_VALUE);

        assertEquals(NEW_VALUE, testValues[0], "Listener does not update value correctly");
    }

    @Test
    public void setDisplayTabPaneIndex_indexOutsideRange_throwsIndexOutOfBoundsException() {
        TabManager tabManager = new TabManager();
        Index[] testValues = new Index[] { OLD_VALUE };

        tabManager.getDisplayTabPaneIndex().addListener((observable, oldValue, newValue) -> {
            testValues[0] = Index.fromZeroBased((newValue.intValue()));
        });

        assertThrows(IndexOutOfBoundsException.class, () -> tabManager.switchDetailsTab(ILLEGAL_VALUE));
    }

    @Test
    public void setDetailsTabPaneIndex_indexWithinRange_success() {
        TabManager tabManager = new TabManager();
        TabData tabData1 = new TabData(ModelType.PROBLEM, Id.generateId());
        TabData tabData2 = new TabData(ModelType.PROBLEM, Id.generateId());
        tabManager.openDetailsTab(tabData1);
        tabManager.openDetailsTab(tabData2);
        Index[] testValues = new Index[] { OLD_VALUE };

        tabManager.getDetailsTabPaneIndex().addListener((observable, oldValue, newValue) -> {
            testValues[0] = Index.fromZeroBased((newValue.intValue()));
        });
        tabManager.switchDetailsTab(OLD_VALUE);

        assertEquals(OLD_VALUE, testValues[0], "Listener does not update value correctly");
    }

    @Test
    public void setDetailsTabPaneIndex_indexOutsideRange_throwsIndexOutOfBoundsException() {
        TabManager tabManager = new TabManager();
        Index[] testValues = new Index[] { OLD_VALUE };

        tabManager.getDetailsTabPaneIndex().addListener((observable, oldValue, newValue) -> {
            testValues[0] = Index.fromZeroBased((newValue.intValue()));
        });

        assertThrows(IndexOutOfBoundsException.class, () -> tabManager.switchDetailsTab(ILLEGAL_VALUE));
    }
}
