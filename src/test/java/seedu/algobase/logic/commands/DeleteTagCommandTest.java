package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.algobase.logic.commands.DeleteTagCommand.COMMAND_WORD;

import org.junit.jupiter.api.Test;

public class DeleteTagCommandTest {
    @Test
    public void testcommandword() {
        assertEquals("deletetag", COMMAND_WORD);
    }
}
