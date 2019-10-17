package seedu.savenus.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class CommandHistoryTest {


    @Test
    public void storeInvalidCommand_success() {
        CommandHistory.storeInvalidCommand("asdasfgas");
        assertEquals(CommandHistory.getCurrentCommandIndex(),
                CommandHistory.getCommandHistory().size() - 1);
    }

    @Test
    public void storeValidCommand_success() {
        CommandHistory.storeValidCommand("edit");
        assertEquals(CommandHistory.getCurrentCommandIndex(),
                CommandHistory.getCommandHistory().size());
    }

    @Test
    public void getPrev_correctReturnType() {
        assertEquals(null,
                CommandHistory.getPrev());
        CommandHistory.storeValidCommand("edit");
        CommandHistory.storeValidCommand("add");
        CommandHistory.storeValidCommand("info");
        assertEquals(
                CommandHistory.getCommandHistory().get(CommandHistory.getCurrentCommandIndex() - 1),
                CommandHistory.getPrev());
    }

    @Test
    public void getNext_correctReturnType() {
        assertEquals(null, CommandHistory.getNext());
    }

}
