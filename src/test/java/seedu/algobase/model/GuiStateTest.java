package seedu.algobase.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.model.gui.GuiState;

public class GuiStateTest {

    private static final Index OLD_VALUE = Index.fromZeroBased(ModelEnum.PROBLEM.getDisplayTabPaneIndex());
    private static final Index NEW_VALUE = Index.fromZeroBased(ModelEnum.TAG.getDisplayTabPaneIndex());
    private static final Index ILLEGAL_VALUE = Index.fromZeroBased(ModelEnum.values().length);

    @Test
    public void setDisplayTabPaneIndex_indexWithinRange_success() {
        GuiState guiState = new GuiState();
        Index[] testValues = new Index[] { OLD_VALUE };

        guiState.getDisplayTabPaneIndex().addListener((observable, oldValue, newValue) -> {
            testValues[0] = Index.fromZeroBased((newValue.intValue()));
        });
        guiState.setDisplayTabPaneIndex(NEW_VALUE);

        assertEquals(NEW_VALUE, testValues[0], "Listener does not update value correctly");
    }

    @Test
    public void setDisplayTabPaneIndex_indexOutsideRange_throwsIndexOutOfBoundsException() {
        GuiState guiState = new GuiState();
        Index[] testValues = new Index[] { OLD_VALUE };

        guiState.getDisplayTabPaneIndex().addListener((observable, oldValue, newValue) -> {
            testValues[0] = Index.fromZeroBased((newValue.intValue()));
        });

        assertThrows(IndexOutOfBoundsException.class, () -> guiState.setDisplayTabPaneIndex(ILLEGAL_VALUE));
    }
}
