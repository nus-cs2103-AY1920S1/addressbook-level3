package seedu.tarence.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.ModelStub;

class InputHistoryHandlerTest {

    public static final String LAST_INPUT = "Last input";
    public static final String SECOND_LAST_INPUT = "Second last input";

    private InputHistoryHandler inputHistoryHandler;
    private ModelStubInputHistoryHandler model;

    @BeforeEach
    void setup() {
        model = new ModelStubInputHistoryHandler();
        inputHistoryHandler = new InputHistoryHandler(model);
    }

    @Test
    void getPastCommand_getInputHistory_commandsRetrieved() {
        model.inputHistory.add(LAST_INPUT);
        model.inputHistory.add(SECOND_LAST_INPUT);
        assertEquals(LAST_INPUT, inputHistoryHandler.getPastInput("up"));
        assertEquals(SECOND_LAST_INPUT, inputHistoryHandler.getPastInput("up"));
        assertEquals(SECOND_LAST_INPUT, inputHistoryHandler.getPastInput("up"));
        assertEquals(LAST_INPUT, inputHistoryHandler.getPastInput("down"));
        assertEquals(LAST_INPUT, inputHistoryHandler.getPastInput("down"));
    }

    @Test
    void getPastCommand_getInputHistory_noCommandsInHistory() {
        assertNull(inputHistoryHandler.getPastInput("up"));
    }

    //CHECKSTYLE:OFF: VisibilityModifier
    private static class ModelStubInputHistoryHandler extends ModelStub {

        List<String> inputHistory = new ArrayList<>();
        boolean isInputChanged = true;
        private int inputHistoryIndex = 0;

        @Override
        public void setInputChangedToTrue() {
            isInputChanged = true;
        }

        @Override
        public void setInputChangedToFalse() {
            isInputChanged = false;
        }

        @Override
        public boolean hasInputChanged() {
            return isInputChanged;
        }

        @Override
        public List<String> getInputHistory() {
            return inputHistory;
        }

        @Override
        public int getInputHistoryIndex() {
            return inputHistoryIndex;
        }

        @Override
        public void incrementInputHistoryIndex() {
            inputHistoryIndex += 1;
        }

        @Override
        public void decrementInputHistoryIndex() {
            inputHistoryIndex -= 1;
        }

        @Override
        public void resetInputHistoryIndex() {
            inputHistoryIndex = 0;
        }

    }
}
