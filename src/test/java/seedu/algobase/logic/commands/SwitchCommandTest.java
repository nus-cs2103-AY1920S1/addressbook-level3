package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.model.GuiState;

class SwitchCommandTest {
    @Test
    public void constructor_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SwitchCommand(null));
    }

    @Test
    public void execute_displayTabIndexAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDisplayTabIndex modelStub = new ModelStubAcceptingDisplayTabIndex();
        Index index = Index.fromOneBased(3);
        CommandResult commandResult = new SwitchCommand(index).execute(modelStub);

        assertEquals(
            String.format(SwitchCommand.MESSAGE_SUCCESS, index.getOneBased()),
            commandResult.getFeedbackToUser()
        );
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

