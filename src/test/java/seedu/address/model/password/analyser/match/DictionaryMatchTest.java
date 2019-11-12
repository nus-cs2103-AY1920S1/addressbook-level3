package seedu.address.model.password.analyser.match;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DictionaryMatchTest {

    @Test
    void compareTo_equalRank_returnZero() {
        DictionaryMatch instanceOne = new DictionaryMatch(0, 4, "token", 1);
        DictionaryMatch instanceTwo = new DictionaryMatch(0, 4, "nekot", 1);
        assertTrue(instanceOne.compareTo(instanceTwo) == 0);
    }

    @Test
    void compareTo_unequalRank_returnNegative() {
        DictionaryMatch instanceOne = new DictionaryMatch(0, 4, "lowerRank", 1);
        DictionaryMatch instanceTwo = new DictionaryMatch(0, 4, "higherRank", 5);
        assertTrue(instanceOne.compareTo(instanceTwo) < 0);
    }
}
