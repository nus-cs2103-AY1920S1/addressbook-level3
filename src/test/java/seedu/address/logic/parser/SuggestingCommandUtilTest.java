package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class SuggestingCommandUtilTest {
    boolean testFuzzyMatcher(final String sequence, final String match) {
        return SuggestingCommandUtil.createFuzzyMatcher(sequence).test(match);
    }

    void assertFuzzyPassingMatch(final String sequence, final String passingMatch) {
        assertTrue(testFuzzyMatcher(sequence, passingMatch));
    }

    void assertFuzzyFailingMatch(final String sequence, final String failingMatch) {
        assertFalse(testFuzzyMatcher(sequence, failingMatch));
    }

    @Test
    void createFuzzyMatcher_exactMatch_success() {
        assertFuzzyPassingMatch("mdm", "mdm");
    }

    @Test
    void createFuzzyMatcher_oneCharacterBetweenNoTrailing_success() {
        assertFuzzyPassingMatch("mdm", "madam");
    }

    @Test
    void createFuzzyMatcher_trailingCharacter_success() {
        final String sequence = "mdm";
        final String passingMatch = sequence + "a";
        assertFuzzyPassingMatch(sequence, passingMatch);
    }

    @Test
    void createFuzzyMatcher_oneCharacterBetweenWithTrailing_success() {
        assertFuzzyPassingMatch("mdm", "madame");
    }

    @Test
    void createFuzzyMatcher_manyCharactersBetweenNoTrailing_success() {
        assertFuzzyPassingMatch("mdm", "medium");
    }

    @Test
    void createFuzzyMatcher_manyCharactersBetweenWithTrailing_success() {
        assertFuzzyPassingMatch("mdm", "madman");
    }

    @Test
    void createFuzzyMatcher_matchUnicodeSurrogatePair_success() {
        assertFuzzyPassingMatch("😁😁", "😁smile😁");
    }

    @Test
    void createFuzzyMatcher_trailingNonLatinCharacters_success() {
        assertFuzzyPassingMatch("mdm", "mdm你好");
    }

    @Test
    void createFuzzyMatcher_incompleteMatch_fail() {
        assertFuzzyFailingMatch("mdm", "md");
    }

    @Test
    void createFuzzyMatcher_leadingCharacter_fail() {
        final String sequence = "mdm";
        final String failingMatch = "a" + sequence;
        assertFuzzyFailingMatch(sequence, failingMatch);
    }

    @Test
    void createFuzzyMatcher_spaceInBetween_fail() {
        assertFuzzyFailingMatch("mdm", "m dm");
    }

    @Test
    void createFuzzyMatcher_trailingSpace_fail() {
        final String sequence = "mdm";
        final String failingMatch = sequence + " ";
        assertFuzzyFailingMatch(sequence, failingMatch);
    }

    @Test
    void createFuzzyMatcher_dashCharacter_success() {
        assertFuzzyPassingMatch("tsg", "test-string");
    }
}
