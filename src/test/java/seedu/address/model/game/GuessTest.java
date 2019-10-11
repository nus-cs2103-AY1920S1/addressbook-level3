package seedu.address.model.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.card.Word;

public class GuessTest {

    @Test
    public void nullStringPassedIntoConstructor_throwsNullPointerException() {
        String userString = null;
        assertThrows(NullPointerException.class, () -> new Guess(userString));
    }

    @Test
    public void matches() {
        Word lowercaseWord = new Word("abra");
        Guess lowercaseGuess = new Guess("abra");
        assertTrue(lowercaseGuess.matches(lowercaseWord));

        Word uppercaseWord = new Word("ABRA");
        Guess uppercaseGuess = new Guess("ABRA");
        assertTrue(uppercaseGuess.matches(uppercaseWord));

        Word someWord = new Word("Ditto");
        Guess wrongGuess = new Guess("Charizard");
        assertFalse(wrongGuess.matches(someWord));

        Word mixedCaseWord = new Word("chariZARD");
        Guess mixedCaseGuess = new Guess("CHARIzard");
        assertTrue(mixedCaseGuess.matches(mixedCaseWord));
    }

}
