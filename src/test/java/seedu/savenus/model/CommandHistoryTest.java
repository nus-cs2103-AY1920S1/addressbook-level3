package seedu.savenus.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class CommandHistoryTest {

    private CommandHistory commandHistory = CommandHistory.getInstance();

    @Test
    public void storeInvalidCommand_success() {
        commandHistory.storeInvalidCommand("asdasfgas");
        assertEquals(commandHistory.getCurrentCommandIndex(),
                commandHistory.getCommandHistory().size() - 1);
    }

    @Test
    public void storeValidCommand_success() {
        commandHistory.storeValidCommand("edit");
        assertEquals(commandHistory.getCurrentCommandIndex(),
                commandHistory.getCommandHistory().size());
    }

    @Test
    public void getPrev_correctReturnType() {
        assertEquals(null,
                commandHistory.getPrev());
        commandHistory.storeValidCommand("edit");
        commandHistory.storeValidCommand("add");
        commandHistory.storeValidCommand("info");
        assertEquals(
                commandHistory.getCommandHistory().get(commandHistory.getCurrentCommandIndex() - 1),
                commandHistory.getPrev());
    }

    @Test
    public void getNext_correctReturnType() {
        assertEquals(null, commandHistory.getNext());
    }

    @Test
    public void toString_correctReturnType() {
        assertEquals(true, commandHistory.toString() instanceof String);
    }
}
