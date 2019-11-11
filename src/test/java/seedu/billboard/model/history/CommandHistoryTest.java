package seedu.billboard.model.history;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandHistoryTest {
    private CommandHistory ch = new CommandHistory();

    @BeforeEach
    void setUp() {
        CommandHistory.clearHistory();
    }

    @Test
    void peekNextCmd() {
        //Empty command history
        assertEquals("", CommandHistory.peekNextCmd());
        CommandHistory.addCmdHistory("first");
        assertEquals("first", CommandHistory.peekNextCmd());
        assertEquals("first", CommandHistory.peekNextCmd());
    }

    @Test
    void peekPreviousCmd() {
        //Empty command history
        assertEquals(CommandHistory.peekPreviousCmd(), "");
        CommandHistory.addCmdHistory("first");
        assertEquals("", CommandHistory.peekPreviousCmd());
        CommandHistory.peekNextCmd();
        assertEquals("", CommandHistory.peekPreviousCmd());
        CommandHistory.addCmdHistory("second");
        CommandHistory.peekNextCmd();
        CommandHistory.peekNextCmd();
        assertEquals("second", CommandHistory.peekPreviousCmd());
    }
}
