package seedu.algobase.ui.action.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.DefaultModelStub;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.GuiState;
import seedu.algobase.model.gui.TabData;
import seedu.algobase.ui.UiActionException;
import seedu.algobase.ui.action.UiActionResult;

public class SwitchDetailsTabUiActionTest {
    @Test
    public void constructor_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SwitchDetailsTabUiAction(null));
    }

    @Test
    public void execute_detailsTabIndexAcceptedByModel_switchSuccessful() throws Exception {
        ModelStubAcceptingDetailsTabIndex modelStub = new ModelStubAcceptingDetailsTabIndex();
        Index index = Index.fromZeroBased(1);
        UiActionResult uiActionResult = new SwitchDetailsTabUiAction(index).execute(modelStub);

        assertEquals(
            1,
            modelStub.getGuiState().getTabManager().getDetailsTabPaneIndex().intValue()
        );

        assertEquals(
            Optional.empty(),
            uiActionResult.getFeedbackToUser()
        );
    }

    @Test
    public void execute_detailsTabIndexRejectedByModel_throwsUiActionException() throws Exception {
        ModelStubAcceptingDetailsTabIndex modelStub = new ModelStubAcceptingDetailsTabIndex();
        Index index = Index.fromZeroBased(2);

        assertThrows(UiActionException.class, () -> new SwitchDetailsTabUiAction(index).execute(modelStub));
    }

    /**
     * A Model stub that always accepts the problem being added.
     */
    private static class ModelStubAcceptingDetailsTabIndex extends DefaultModelStub {

        @Override
        public GuiState getGuiState() {
            GuiState guiState = new GuiState();
            TabData tabData1 = new TabData(ModelType.PROBLEM, Id.generateId());
            TabData tabData2 = new TabData(ModelType.PROBLEM, Id.generateId());
            guiState.getTabManager().openDetailsTab(tabData1);
            guiState.getTabManager().openDetailsTab(tabData2);
            return guiState;
        }
    }
}
