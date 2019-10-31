package seedu.mark.logic.commands.results;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HelpCommandResultTest {

    @Test
    public void isShowHelp() {
        HelpCommandResult commandResult = new HelpCommandResult("feedback");

        assertTrue(commandResult.isShowHelp());
        assertFalse(commandResult.isExit());
        assertNull(commandResult.getTab());
        assertEquals(commandResult.getLevelsToExpand(), 0);
    }
}
