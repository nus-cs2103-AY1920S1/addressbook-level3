package cs.f10.t1.nursetraverse.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CommandWordTest {
    private CommandWord testCommandWord = new CommandWord("test1", "test2",
            true, true);

    @Test
    void getSuggestedWord() {
        assertEquals("test2", testCommandWord.getSuggestedWord());
    }

    @Test
    void getAssociatedWordList() {
        assertEquals(1, testCommandWord.getAssociatedWordList().size());
        assertEquals("test1", testCommandWord.getAssociatedWordList().get(0));
    }

    @Test
    void hasIndex() {
        assertTrue(testCommandWord.hasIndex());
    }

    @Test
    void hasPrefix() {
        assertTrue(testCommandWord.hasPrefix());
    }
}
