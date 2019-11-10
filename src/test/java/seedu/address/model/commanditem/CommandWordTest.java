package seedu.address.model.commanditem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CommandWordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandWord(null));
    }

    @Test
    public void constructor_invalidCommandWord_throwsIllegalArgumentException() {
        String invalidCommand = "";
        assertThrows(IllegalArgumentException.class, () -> new CommandWord(invalidCommand));
    }

    @Test
    public void isValidCommandWord() {
        // null command word
        assertThrows(NullPointerException.class, () -> CommandWord.isValidWord(null));

        // invalid command word
        assertFalse(CommandWord.isValidWord("")); // empty string
        assertFalse(CommandWord.isValidWord(" ")); // spaces only

        // valid command word
        assertTrue(CommandWord.isValidWord("peter jack")); // alphabets only
        assertTrue(CommandWord.isValidWord("12345")); // numbers only
        assertTrue(CommandWord.isValidWord("add 23")); // alphanumeric characters
        assertTrue(CommandWord.isValidWord("Exit now")); // with capital letters
        assertTrue(CommandWord.isValidWord("Adding income only")); // long commands
    }

    @Test
    public void checkHashCode() {
        CommandWord commandWord = new CommandWord("12345");
        assertEquals(commandWord.hashCode(), commandWord.word.hashCode());
    }

    @Test
    public void checkString() {
        CommandWord commandWord = new CommandWord("12345");
        assertEquals(commandWord.toString(), "12345");
    }
}
