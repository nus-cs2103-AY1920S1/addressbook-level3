package seedu.mark.logic.commands.results;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.TabCommand.Tab;

public class ExpandCommandResultTest {
    @Test
    public void isExpand() {
        int levelsToExpand = 1;
        ExpandCommandResult result =
                new ExpandCommandResult("feedback", levelsToExpand);
        assertEquals(result.getLevelsToExpand(), levelsToExpand);
        assertFalse(result.isExit());
        assertFalse(result.isShowHelp());
        assertEquals(result.getTab(), Tab.DASHBOARD);
    }
}
