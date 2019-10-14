package seedu.address.statistics;

import org.junit.jupiter.api.Test;
import seedu.address.model.game.Guess;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

public class GameDataPointTest {

    @Test
    public void createSkipData() {
        assertThrows(AssertionError.class, () -> GameDataPoint.createSkipData(-1)); // invalid millis
    }

    @Test
    public void createGuessData() {
        assertThrows(NullPointerException.class,
                () -> GameDataPoint.createGuessData(null, 1)); // null guess
        assertThrows(AssertionError.class,
                () -> GameDataPoint.createGuessData(new Guess("abc"), -1)); // invalid millis
    }

    @Test
    public void getMillisTaken() {
        assertEquals(GameDataPoint.createSkipData(1).getMillisTaken(), 1);
        assertEquals(GameDataPoint.createGuessData(new Guess("abc"), 1).getMillisTaken(),
                1);
    }

    @Test
    public void getGuess() {
        assertEquals(GameDataPoint.createSkipData(1).getGuess(), Optional.empty()); // skip data does not have a guess.
        Guess g1 = new Guess("abc");
        Guess g2 = new Guess("abc");
        assertEquals(GameDataPoint.createGuessData(g1, 1).getGuess().get(),
                g1);
        assertEquals(GameDataPoint.createGuessData(g1, 1).getGuess().get(),
                g2);
    }
}
