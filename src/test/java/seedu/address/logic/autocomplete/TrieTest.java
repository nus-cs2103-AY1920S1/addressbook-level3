package seedu.address.logic.autocomplete;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class TrieTest extends Trie{

    public TrieTest() {
        this.insert("patents");
        this.insert("patron");
        this.insert("upsize");
    }

    @Test
    public void correctnessTest() {
        ArrayList<String> result = this.find("pat").getPossibilities();
        assertTrue(result.contains("ients"));
        assertTrue(result.contains("ents"));
        assertTrue(result.contains("ron"));
        result = this.find("up").getPossibilities();
        assertTrue(result.contains("date"));
        assertTrue(result.contains("size"));
    }
}
