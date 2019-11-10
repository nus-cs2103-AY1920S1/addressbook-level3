package seedu.address.model.commanditem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;

//@@author{lawncegoh}
class CommandItemTest {

    private CommandItem commandItemCopy = new CommandItem(new CommandWord("fly"), new CommandTask("123"));
    private CommandWord commandWord = new CommandWord("123");

    private CommandItem commandItem = new CommandItem(new CommandWord("fly"), new CommandTask("123"));

    @Test
    public void checkSetCommandWord() {
        commandItem.setCommandWord(new CommandWord("walk"));
        assertEquals(commandItem.getCommandWord(), new CommandWord("walk"));
    }

    @Test
    public void checkSetCommandTask() {
        commandItem.setCommandTask(new CommandTask("321"));
        assertEquals(commandItem.getCommandTask(), new CommandTask("321"));
    }

    @Test
    public void equal() {
        //same value return true
        assertTrue(commandItem.equals(commandItemCopy));
        assertTrue(commandItem.isSameCommand(commandItemCopy));
        //not commanditem return false
        assertFalse(commandItem.equals(commandWord));
    }

    @Test
    public void checkHashCode() {
        assertEquals(commandItem.hashCode(), Objects.hash(commandItem.getCommandTask(), commandItem.getCommandWord()));
    }

    @Test
    public void checkString() {
        assertEquals(commandItem.toString(), " Word: fly Task: 123");
    }

}
