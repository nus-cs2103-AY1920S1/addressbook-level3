package seedu.algobase.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GuiStateTest {

    private static final int OLD_VALUE = 0;
    private static final int NEW_VALUE = 1;

    @Test
    public void test_setDisplayTabPaneIndex_success() {
        GuiState guiState = new GuiState();
        int[] testValues = new int[] { OLD_VALUE };

        guiState.getDisplayTabPaneIndex().addListener((observable, oldValue, newValue) -> {
            testValues[0] = (int) newValue;
        });
        guiState.setDisplayTabPaneIndex(NEW_VALUE);

        assertEquals(NEW_VALUE, testValues[0], "Listener does not update value correctly");
    }
}
