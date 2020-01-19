package seedu.mark.logic.commands.results;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ExitCommandResultTest {

    @Test
    public void isExit() {
        ExitCommandResult commandResult = new ExitCommandResult("feedback");

        assertTrue(commandResult.isExit());
        assertFalse(commandResult.isShowHelp());
        assertNull(commandResult.getTab());
        assertEquals(commandResult.getLevelsToExpand(), 0);
    }
}
