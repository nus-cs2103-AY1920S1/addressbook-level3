package seedu.address.logic.commands.historycommand;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.entity.CommandType;
import seedu.address.stub.ModelManagerStub;

class UndoCommandTest {
    private ModelManagerStub modelStub;

    @BeforeEach
    void setUp() {
        modelStub = new ModelManagerStub();
    }

    @Test
    void execute_success() throws AlfredException {
        CommandResult commandResult = new UndoCommand().execute(modelStub);

        assertEquals(UndoCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertEquals(CommandType.H, commandResult.getCommandType());
    }
}
