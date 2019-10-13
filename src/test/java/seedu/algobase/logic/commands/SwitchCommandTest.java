package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.GuiState;
import seedu.algobase.model.ModelEnum;

class SwitchCommandTest {
    @Test
    public void constructor_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SwitchCommand(null));
    }

    @Test
    public void execute_displayTabIndexAcceptedByModel_switchSuccessful() throws Exception {
        ModelStubAcceptingDisplayTabIndex modelStub = new ModelStubAcceptingDisplayTabIndex();
        Index index = Index.fromOneBased(ModelEnum.PLAN.getDisplayTabPaneIndex());
        CommandResult commandResult = new SwitchCommand(index).execute(modelStub);

        assertEquals(
            String.format(SwitchCommand.MESSAGE_SUCCESS, index.getOneBased()),
            commandResult.getFeedbackToUser()
        );
    }

    @Test
    public void execute_displayTabIndexRejectedByModel_throwsCommandException() throws Exception {
        ModelStubAcceptingDisplayTabIndex modelStub = new ModelStubAcceptingDisplayTabIndex();
        Index index = Index.fromZeroBased(ModelEnum.values().length);

        assertThrows(CommandException.class, () -> new SwitchCommand(index).execute(modelStub));
    }

    /**
     * A Model stub that always accept the problem being added.
     */
    private class ModelStubAcceptingDisplayTabIndex extends DefaultModelStub {

        @Override
        public GuiState getGuiState() {
            return new GuiState();
        }
    }
}

