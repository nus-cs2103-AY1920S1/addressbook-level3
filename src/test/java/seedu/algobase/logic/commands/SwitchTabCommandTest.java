package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.GuiState;
import seedu.algobase.model.gui.TabData;
import seedu.algobase.model.gui.TabType;

class SwitchTabCommandTest {

    @Test
    public void constructor_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SwitchTabCommand(null, null));
    }

    @Test
    public void execute_displayTabIndexAcceptedByModel_switchSuccessful() throws Exception {
        ModelStubAcceptingDisplayTabIndex modelStub = new ModelStubAcceptingDisplayTabIndex();
        Index index = Index.fromOneBased(ModelType.PLAN.getDisplayTabPaneIndex());
        CommandResult commandResult = new SwitchTabCommand(TabType.DISPLAY, index).execute(modelStub);

        assertEquals(
            String.format(SwitchTabCommand.MESSAGE_SUCCESS, TabType.DISPLAY.getName(), index.getOneBased()),
            commandResult.getFeedbackToUser()
        );
    }

    @Test
    public void execute_displayTabIndexRejectedByModel_throwsCommandException() throws Exception {
        ModelStubAcceptingDisplayTabIndex modelStub = new ModelStubAcceptingDisplayTabIndex();
        Index index = Index.fromZeroBased(ModelType.values().length);

        assertThrows(CommandException.class, () -> new SwitchTabCommand(TabType.DISPLAY, index)
            .execute(modelStub));
    }

    @Test
    public void execute_detailsTabIndexAcceptedByModel_switchSuccessful() throws Exception {
        ModelStubAcceptingDetailsTabIndex modelStub = new ModelStubAcceptingDetailsTabIndex();
        Index index = Index.fromOneBased(2);
        CommandResult commandResult = new SwitchTabCommand(TabType.DETAILS, index).execute(modelStub);

        assertEquals(
            String.format(SwitchTabCommand.MESSAGE_SUCCESS, TabType.DETAILS.getName(), index.getOneBased()),
            commandResult.getFeedbackToUser()
        );
    }

    @Test
    public void execute_detailsTabIndexRejectedByModel_throwsCommandException() throws Exception {
        ModelStubAcceptingDetailsTabIndex modelStub = new ModelStubAcceptingDetailsTabIndex();
        Index index = Index.fromOneBased(3);

        assertThrows(CommandException.class, () -> new SwitchTabCommand(TabType.DETAILS, index)
            .execute(modelStub));
    }

    /**
     * A Model stub that always accepts the display tab index being added.
     */
    private class ModelStubAcceptingDisplayTabIndex extends DefaultModelStub {

        @Override
        public GuiState getGuiState() {
            return new GuiState();
        }
    }

    /**
     * A Model stub that always accepts the problem being added.
     */
    private class ModelStubAcceptingDetailsTabIndex extends DefaultModelStub {

        @Override
        public GuiState getGuiState() {
            GuiState guiState = new GuiState();
            TabData tabData1 = new TabData(ModelType.PROBLEM, Index.fromOneBased(1));
            TabData tabData2 = new TabData(ModelType.PROBLEM, Index.fromOneBased(2));
            guiState.getTabManager().addTab(tabData1, tabData2);
            return guiState;
        }
    }
}
