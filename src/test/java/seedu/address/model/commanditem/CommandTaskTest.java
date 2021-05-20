package seedu.address.model.commanditem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CommandTaskTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandTask(null));
    }

    @Test
    public void constructor_invalidCommandTask_throwsIllegalArgumentException() {
        String invalidTask = "";
        assertThrows(IllegalArgumentException.class, () -> new CommandTask(invalidTask));
    }

    @Test
    public void isValidTask() {
        // null task
        assertThrows(NullPointerException.class, () -> CommandTask.isValidTask(null));

        // invalid task
        assertFalse(CommandTask.isValidTask("")); // empty string
        assertFalse(CommandTask.isValidTask(" ")); // spaces only

        // valid task
        assertTrue(CommandTask.isValidTask("add_contact"));
        assertTrue(CommandTask.isValidTask("exit"));

    }

    @Test
    public void checkHashCode() {
        CommandTask commandTask = new CommandTask("exit");
        assertEquals(commandTask.hashCode(), commandTask.task.hashCode());
    }

    @Test
    public void checkString() {
        CommandTask commandTask = new CommandTask("exit");
        assertEquals(commandTask.toString(), "exit");
    }

}
