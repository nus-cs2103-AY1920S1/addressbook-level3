package seedu.address.model.classid;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.commands.CommandAction;

public class CommandActionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandAction(null));
    }

    @Test
    public void constructor_invalidCommandAction_throwsIllegalArgumentException() {
        String invalidCommandAction = "";
        assertThrows(IllegalArgumentException.class, () -> new CommandAction(invalidCommandAction));
    }

    @Test
    public void isValidCommandAction() {

        assertThrows(NullPointerException.class, () -> CommandAction.isValidAction(null));

        assertFalse(CommandAction.isValidAction("")); // empty string
        assertFalse(CommandAction.isValidAction(" ")); // spaces only

        assertTrue(CommandAction.isValidAction("add"));
        assertTrue(CommandAction.isValidAction("delete"));
        assertTrue(CommandAction.isValidAction("plus multiply"));
        assertTrue(CommandAction.isValidAction("randomize"));
        assertTrue(CommandAction.isValidAction("random"));
    }
}
