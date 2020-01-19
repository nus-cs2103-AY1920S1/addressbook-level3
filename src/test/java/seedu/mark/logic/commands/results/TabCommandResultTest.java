package seedu.mark.logic.commands.results;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.TabCommand;

public class TabCommandResultTest {

    @Test
    public void getTab() {
        TabCommandResult commandResult = new TabCommandResult("feedback", TabCommand.Tab.DASHBOARD);

        assertEquals(TabCommand.Tab.DASHBOARD, commandResult.getTab());
        assertFalse(commandResult.isShowHelp());
        assertFalse(commandResult.isExit());
        assertEquals(commandResult.getLevelsToExpand(), 0);
    }
}
