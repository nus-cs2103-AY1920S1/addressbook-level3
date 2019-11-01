package cs.f10.t1.nursetraverse.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CommandWordTest {
    private CommandWord testCommandWord = new CommandWord("test1", "test2",
            true, true);

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
}
