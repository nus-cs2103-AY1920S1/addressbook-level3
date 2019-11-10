package seedu.algobase.ui.action.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.DefaultModelStub;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.GuiState;
import seedu.algobase.model.gui.TabData;
import seedu.algobase.model.gui.TabManager;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.util.SampleDataUtil;
import seedu.algobase.ui.UiActionException;
import seedu.algobase.ui.action.UiActionResult;

public class CloseDetailsTabUiActionTest {
    @Test
    public void constructor_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CloseDetailsTabUiAction(null, null));
    }

    @Test
    public void execute_closeLastProblemTabAcceptedByModel_closeSuccessful() throws Exception {
        FiveTabsModelStub modelStub = new FiveTabsModelStub();
        TabManager tabManager = modelStub.getGuiState().getTabManager();

        // Choose the last index to be closed.
        Index toBeClosedIndex = Index.fromZeroBased(4); // Last index
        ModelType modelType = ModelType.PROBLEM;
        Id toBeClosedId = modelStub.getFilteredProblemList().get(toBeClosedIndex.getZeroBased()).getId();

        // Get the expected select details tab
        Index expectedIndex = Index.fromZeroBased(3); // Second Last index
        Id expectedId = modelStub.getFilteredProblemList().get(expectedIndex.getZeroBased()).getId();
        TabData expectedTabData = new TabData(ModelType.PROBLEM, expectedId);

        UiActionResult uiActionResult = new CloseDetailsTabUiAction(modelType.PROBLEM, toBeClosedId).execute(modelStub);

        // Get the currently selected details tab
        int selectedDetailsTabIndex = tabManager.getDetailsTabPaneIndex().intValue();
        TabData selectedTabDataAfterClose = tabManager.getTabsDataList().get(selectedDetailsTabIndex);

        assertEquals(
            selectedTabDataAfterClose,
            expectedTabData
        );

        assertEquals(
            Optional.empty(),
            uiActionResult.getFeedbackToUser()
        );
    }

    @Test
    public void execute_closeTabIndexOutOfUpperRange_throwsUiActionException() throws Exception {
        FiveTabsModelStub modelStub = new FiveTabsModelStub();
        TabManager tabManager = modelStub.getGuiState().getTabManager();
        tabManager.switchDetailsTab(Index.fromZeroBased(0)); // Switch to the first tab

        assertThrows(
            UiActionException.class, () ->
                new CloseDetailsTabUiAction(ModelType.PROBLEM, Id.generateId()).execute(modelStub)
        );
    }

    /**
     * A Model stub that has a problem list with sample data and a GuiState with 5 tabs in its tab manager.
     */
    private static class FiveTabsModelStub extends DefaultModelStub {

        private GuiState guiState;
        private ObservableList<Problem> internalList;

        FiveTabsModelStub() {

            guiState = new GuiState();

            // Set the internal list to contain sample problems
            Problem[] problems = SampleDataUtil.getSampleProblems();
            internalList = FXCollections.observableList(Arrays.asList(problems));

            // Open the first 3 problems as tabs
            TabData tabData1 = new TabData(ModelType.PROBLEM, problems[0].getId());
            TabData tabData2 = new TabData(ModelType.PROBLEM, problems[1].getId());
            TabData tabData3 = new TabData(ModelType.PROBLEM, problems[2].getId());
            TabData tabData4 = new TabData(ModelType.PROBLEM, problems[3].getId());
            TabData tabData5 = new TabData(ModelType.PROBLEM, problems[4].getId());
            guiState.getTabManager().openDetailsTab(tabData1);
            guiState.getTabManager().openDetailsTab(tabData2);
            guiState.getTabManager().openDetailsTab(tabData3);
            guiState.getTabManager().openDetailsTab(tabData4);
            guiState.getTabManager().openDetailsTab(tabData5);
        }

        @Override
        public ObservableList<Problem> getFilteredProblemList() {
            return internalList;
        }

        @Override
        public GuiState getGuiState() {
            return guiState;
        }
    }
}
