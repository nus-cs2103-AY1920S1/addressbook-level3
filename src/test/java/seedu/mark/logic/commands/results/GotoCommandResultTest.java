package seedu.mark.logic.commands.results;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_URL_AMY;
import static seedu.mark.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.Url;

public class GotoCommandResultTest {

    @Test
    public void isOpen() {
        GotoCommandResult commandResult = new GotoCommandResult("feedback", new Url(VALID_URL_AMY));

        assertTrue(commandResult.isGoto());
        assertFalse(commandResult.isShowHelp());
        assertFalse(commandResult.isExit());
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GotoCommandResult("feedback", null));
    }
}
