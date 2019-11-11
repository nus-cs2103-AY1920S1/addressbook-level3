package seedu.address.model.classid;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.commands.CommandWord;

public class CommandWordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandWord(null));
    }

    @Test
    public void constructor_invalidCommandWord_throwsIllegalArgumentException() {
        String invalidCommandWord = "";
        assertThrows(IllegalArgumentException.class, () -> new CommandWord(invalidCommandWord));
    }

    @Test
    public void isValidCommandWord() {

        assertThrows(NullPointerException.class, () -> CommandWord.isValidWord(null));

        assertFalse(CommandWord.isValidWord("")); // empty string
        assertFalse(CommandWord.isValidWord(" ")); // spaces only

        assertTrue(CommandWord.isValidWord("test"));
        assertTrue(CommandWord.isValidWord("test test"));
        assertTrue(CommandWord.isValidWord("accepted!"));
        assertTrue(CommandWord.isValidWord("okay123"));
        assertTrue(CommandWord.isValidWord("random"));
    }
}
