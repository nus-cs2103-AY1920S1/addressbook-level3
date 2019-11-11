package seedu.address.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.game.Guess;

public class GameDataPointTest {

    @Test
    public void createSkipData_negativeMillis_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> GameDataPoint.createSkipData(-1)); // invalid millis
    }

    @Test
    public void createGuessData_negativeMillis_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, ()
            -> GameDataPoint.createGuessData(new Guess("abc"), -1)); // invalid millis
    }

    @Test
    public void createGuessData_nullGuess_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> GameDataPoint.createGuessData(null, 1)); // null guess
    }

    @Test
    public void getMillisTaken_validInput_success() {
        assertEquals(GameDataPoint.createSkipData(1).getMillisTaken(), 1);
        assertEquals(GameDataPoint.createGuessData(new Guess("abc"), 1).getMillisTaken(),
                1);
    }

    @Test
    public void getGuess_validInput_success() {
        assertEquals(GameDataPoint.createSkipData(1).getGuess(), Optional.empty()); // skip data does not have a guess.
        Guess g1 = new Guess("abc");
        Guess g2 = new Guess("abc");
        assertEquals(GameDataPoint.createGuessData(g1, 1).getGuess().get(), g1);
        assertEquals(GameDataPoint.createGuessData(g1, 1).getGuess().get(), g2);
    }

    @Test
    public void equals() {
        GameDataPoint g1 = GameDataPoint.createGuessData(new Guess("abc"), 1);
        GameDataPoint g2 = GameDataPoint.createGuessData(new Guess("abc"), 1);
        GameDataPoint g3 = GameDataPoint.createGuessData(new Guess("abcd"), 1);
        GameDataPoint g4 = GameDataPoint.createGuessData(new Guess("abc"), 2);

        GameDataPoint g5 = GameDataPoint.createSkipData(1);
        GameDataPoint g6 = GameDataPoint.createSkipData(1);
        GameDataPoint g7 = GameDataPoint.createSkipData(2);
        Integer i = 1;
        assertNotEquals(g1, i);
        assertEquals(g1, g1);
        assertEquals(g1, g2);
        assertNotEquals(g1, g3);
        assertNotEquals(g1, g4);
        assertEquals(g5, g6);
        assertNotEquals(g5, g7);
        assertNotEquals(g1, g5);
    }
}
