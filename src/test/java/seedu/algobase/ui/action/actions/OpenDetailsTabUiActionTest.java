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
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.util.SampleDataUtil;
import seedu.algobase.ui.UiActionException;
import seedu.algobase.ui.action.UiActionResult;

public class OpenDetailsTabUiActionTest {
    @Test
    public void constructor_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OpenDetailsTabUiAction(null, null));
    }

    @Test
    public void execute_openProblemTabAcceptedByModel_openSuccessful() throws Exception {
        ModelStub modelStub = new ModelStub();
        Index index = Index.fromZeroBased(0);

        ModelType modelType = ModelType.PROBLEM;
        Id id = modelStub.getFilteredProblemList().get(index.getZeroBased()).getId();

        UiActionResult uiActionResult = new OpenDetailsTabUiAction(modelType, id).execute(modelStub);

        int detailsTabIndex = modelStub.getGuiState().getTabManager().getDetailsTabPaneIndex().getValue().intValue();
        TabData lastTabData = modelStub.getGuiState().getTabManager().getTabsDataList().get(detailsTabIndex);

        assertEquals(
            lastTabData,
            new TabData(ModelType.PROBLEM, id)
        );

        assertEquals(
            Optional.empty(),
            uiActionResult.getFeedbackToUser()
        );
    }

    @Test
    public void execute_openProblemTabIndexOutOfRange_throwsUiActionException() throws Exception {
        ModelStub modelStub = new ModelStub();

        ModelType modelType = ModelType.PROBLEM;

        assertThrows(UiActionException.class, () -> new OpenDetailsTabUiAction(modelType, Id.generateId("123"))
            .execute(modelStub));
    }

    @Test
    public void execute_openExistingProblemTabAcceptedByModel_switchSuccessful() throws Exception {
        ModelStub modelStub = new ModelStub();

        Index index1 = Index.fromZeroBased(0);
        Index index2 = Index.fromZeroBased(1);

        ModelType modelType = ModelType.PROBLEM;
        Id id1 = modelStub.getFilteredProblemList().get(index1.getZeroBased()).getId();
        Id id2 = modelStub.getFilteredProblemList().get(index2.getZeroBased()).getId();

        int detailsTabIndex;
        TabData currentTabData;

        // Open tab for model at index1
        UiActionResult uiActionResult1 = new OpenDetailsTabUiAction(modelType, id1).execute(modelStub);

        detailsTabIndex = modelStub.getGuiState().getTabManager().getDetailsTabPaneIndex().getValue().intValue();
        currentTabData = modelStub.getGuiState().getTabManager().getTabsDataList().get(detailsTabIndex);

        assertEquals(
            currentTabData,
            new TabData(ModelType.PROBLEM, id1)
        );

        assertEquals(
            Optional.empty(),
            uiActionResult1.getFeedbackToUser()
        );

        // Open tab for model at index2
        UiActionResult uiActionResult2 = new OpenDetailsTabUiAction(modelType, id2).execute(modelStub);

        detailsTabIndex = modelStub.getGuiState().getTabManager().getDetailsTabPaneIndex().getValue().intValue();
        currentTabData = modelStub.getGuiState().getTabManager().getTabsDataList().get(detailsTabIndex);

        assertEquals(
            currentTabData,
            new TabData(ModelType.PROBLEM, id2)
        );

        assertEquals(
            Optional.empty(),
            uiActionResult2.getFeedbackToUser()
        );

        // Open tab for model at index1 again
        UiActionResult uiActionResult1Copy = new OpenDetailsTabUiAction(modelType, id1).execute(modelStub);

        detailsTabIndex = modelStub.getGuiState().getTabManager().getDetailsTabPaneIndex().getValue().intValue();
        currentTabData = modelStub.getGuiState().getTabManager().getTabsDataList().get(detailsTabIndex);

        assertEquals(
            currentTabData,
            new TabData(ModelType.PROBLEM, id1)
        );

        assertEquals(
            Optional.empty(),
            uiActionResult1Copy.getFeedbackToUser()
        );
    }

    /**
     * A Model stub that has a problem list with sample data and a GuiState with 2 tabs in its tab manager.
     */
    private class ModelStub extends DefaultModelStub {

        private GuiState guiState;
        private ObservableList<Problem> internalList;

        ModelStub() {
            guiState = new GuiState();
            TabData tabData1 = new TabData(ModelType.PROBLEM, Id.generateId());
            TabData tabData2 = new TabData(ModelType.PROBLEM, Id.generateId());
            guiState.getTabManager().openDetailsTab(tabData1);
            guiState.getTabManager().openDetailsTab(tabData2);
            Problem[] problems = SampleDataUtil.getSampleProblems();
            internalList = FXCollections.observableList(Arrays.asList(problems));
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
