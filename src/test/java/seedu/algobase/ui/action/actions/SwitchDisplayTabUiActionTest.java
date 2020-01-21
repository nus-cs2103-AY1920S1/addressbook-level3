package seedu.algobase.ui.action.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.DefaultModelStub;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.GuiState;
import seedu.algobase.ui.UiActionException;
import seedu.algobase.ui.action.UiActionResult;

public class SwitchDisplayTabUiActionTest {
    @Test
    public void constructor_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SwitchDetailsTabUiAction(null));
    }

    @Test
    public void execute_displayTabIndexAcceptedByModel_switchSuccessful() throws Exception {
        ModelStubAcceptingDisplayTabIndex modelStub = new ModelStubAcceptingDisplayTabIndex();
        Index index = ModelType.PLAN.getDisplayTabPaneIndex();
        UiActionResult uiActionResult = new SwitchDisplayTabUiAction(index).execute(modelStub);

        assertEquals(
            ModelType.PLAN.getDisplayTabPaneIndex().getZeroBased(),
            modelStub.getGuiState().getTabManager().getDisplayTabPaneIndex().intValue()
        );

        assertEquals(
            Optional.empty(),
            uiActionResult.getFeedbackToUser()
        );
    }

    @Test
    public void execute_displayTabIndexRejectedByModel_throwsUiActionException() throws Exception {
        ModelStubAcceptingDisplayTabIndex modelStub = new ModelStubAcceptingDisplayTabIndex();
        Index index = Index.fromZeroBased(ModelType.values().length);

        assertThrows(UiActionException.class, () -> new SwitchDisplayTabUiAction(index).execute(modelStub));
    }

    /**
     * A Model stub that always accepts the display tab index being added.
     */
    private static class ModelStubAcceptingDisplayTabIndex extends DefaultModelStub {
        private GuiState guiState = new GuiState();

        @Override
        public GuiState getGuiState() {
            return guiState;
        }
    }
}
