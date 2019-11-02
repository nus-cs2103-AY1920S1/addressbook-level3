package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.algobase.logic.commands.gui.CloseTabCommand;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.GuiState;
import seedu.algobase.model.gui.TabData;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.util.SampleDataUtil;

class CloseTabCommandTest {

    @Test
    public void constructor_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CloseTabCommand(null));
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
            guiState.getTabManager().addDetailsTabData(tabData1);
            guiState.getTabManager().addDetailsTabData(tabData2);
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
