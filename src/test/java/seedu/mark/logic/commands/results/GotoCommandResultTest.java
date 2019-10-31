package seedu.mark.logic.commands.results;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.TabCommand;

public class GotoCommandResultTest {

    @Test
    public void getTab() {
        GotoCommandResult commandResult = new GotoCommandResult("feedback");

        assertEquals(TabCommand.Tab.ONLINE, commandResult.getTab());
        assertFalse(commandResult.isShowHelp());
        assertFalse(commandResult.isExit());
        assertEquals(commandResult.getLevelsToExpand(), 0);
    }
}
