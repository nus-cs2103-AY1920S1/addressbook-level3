package cs.f10.t1.nursetraverse.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs.f10.t1.nursetraverse.model.autocomplete.CommandWord;
import org.junit.jupiter.api.Test;

class CommandWordTest {
    private CommandWord testCommandWord = new CommandWord("test1", "test2",
            "test description 1", true, true);

    @Test
    public void getSuggestedWord() {
        assertEquals("test2", testCommandWord.getSuggestedWord());
    }

    @Test
    public void getAssociatedWordList() {
        assertEquals(1, testCommandWord.getAssociatedWordList().size());
        assertEquals("test1", testCommandWord.getAssociatedWordList().get(0));
    }

    @Test
    public void hasIndex() {
        assertTrue(testCommandWord.hasIndex());
    }

    @Test
    public void hasPrefix() {
        assertTrue(testCommandWord.hasPrefix());
    }

    @Test
    public void getConnectorChar() {
        assertEquals(" ", testCommandWord.getConnectorChar());
    }

    @Test
    public void getDescription() {
        assertEquals("test description 1", testCommandWord.getDescription());
    }
}
