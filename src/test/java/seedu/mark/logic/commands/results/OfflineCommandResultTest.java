package seedu.mark.logic.commands.results;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.TabCommand;

public class OfflineCommandResultTest {

    @Test
    public void getTab() {
        OfflineCommandResult commandResult = new OfflineCommandResult("feedback");

        assertEquals(TabCommand.Tab.OFFLINE, commandResult.getTab());
        assertFalse(commandResult.isShowHelp());
        assertFalse(commandResult.isExit());
        assertEquals(commandResult.getLevelsToExpand(), 0);
    }
}
