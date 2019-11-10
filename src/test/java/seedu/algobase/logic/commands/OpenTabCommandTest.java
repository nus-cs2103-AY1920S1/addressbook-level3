package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.logic.commands.gui.OpenTabCommand;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.GuiState;
import seedu.algobase.model.gui.TabData;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.util.SampleDataUtil;

class OpenTabCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OpenTabCommand(null, null));
    }

    @Test
    public void execute_openProblemTabAcceptedByModel_openSuccessful() throws Exception {
        ModelStub modelStub = new ModelStub();
        Index index = Index.fromZeroBased(0);

        ModelType modelType = ModelType.PROBLEM;
        Id id = modelStub.getFilteredProblemList().get(index.getZeroBased()).getId();

        CommandResult commandResult = new OpenTabCommand(modelType, index).execute(modelStub, commandHistory);

        int detailsTabIndex = modelStub.getGuiState().getTabManager().getDetailsTabPaneIndex().getValue().intValue();
        TabData lastTabData = modelStub.getGuiState().getTabManager().getTabsDataList().get(detailsTabIndex);

        assertEquals(
            lastTabData,
            new TabData(ModelType.PROBLEM, id)
        );

        assertEquals(
            String.format(OpenTabCommand.MESSAGE_SUCCESS, index.getOneBased()),
            commandResult.getFeedbackToUser()
        );
    }

    @Test
    public void execute_openProblemTabIndexOutOfRange_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStub();

        ModelType modelType = ModelType.PROBLEM;
        Index index = Index.fromZeroBased(modelStub.getFilteredProblemList().size());

        assertThrows(CommandException.class, () -> new OpenTabCommand(modelType, index)
            .execute(modelStub, commandHistory));
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
        CommandResult commandResult1 = new OpenTabCommand(modelType, index1).execute(modelStub, commandHistory);

        detailsTabIndex = modelStub.getGuiState().getTabManager().getDetailsTabPaneIndex().getValue().intValue();
        currentTabData = modelStub.getGuiState().getTabManager().getTabsDataList().get(detailsTabIndex);

        assertEquals(
            currentTabData,
            new TabData(ModelType.PROBLEM, id1)
        );

        assertEquals(
            String.format(OpenTabCommand.MESSAGE_SUCCESS, index1.getOneBased()),
            commandResult1.getFeedbackToUser()
        );

        // Open tab for model at index2
        CommandResult commandResult2 = new OpenTabCommand(modelType, index2).execute(modelStub, commandHistory);

        detailsTabIndex = modelStub.getGuiState().getTabManager().getDetailsTabPaneIndex().getValue().intValue();
        currentTabData = modelStub.getGuiState().getTabManager().getTabsDataList().get(detailsTabIndex);

        assertEquals(
            currentTabData,
            new TabData(ModelType.PROBLEM, id2)
        );

        assertEquals(
            String.format(OpenTabCommand.MESSAGE_SUCCESS, index2.getOneBased()),
            commandResult2.getFeedbackToUser()
        );

        // Open tab for model at index1 again
        CommandResult commandResult1Copy = new OpenTabCommand(modelType, index1).execute(modelStub, commandHistory);

        detailsTabIndex = modelStub.getGuiState().getTabManager().getDetailsTabPaneIndex().getValue().intValue();
        currentTabData = modelStub.getGuiState().getTabManager().getTabsDataList().get(detailsTabIndex);

        assertEquals(
            currentTabData,
            new TabData(ModelType.PROBLEM, id1)
        );

        assertEquals(
            String.format(OpenTabCommand.MESSAGE_SWITCH_SUCCESS, index1.getOneBased()),
            commandResult1Copy.getFeedbackToUser()
        );
    }

    /**
     * A Model stub that has a problem list with sample data and a GuiState with 2 tabs in its tab manager.
     */
    private static class ModelStub extends DefaultModelStub {

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
