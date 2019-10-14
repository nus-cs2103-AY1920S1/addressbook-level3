package seedu.address.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.ABRA;
import static seedu.address.testutil.TypicalCards.BUTTERFREE;
import static seedu.address.testutil.TypicalCards.CHARIZARD;
import static seedu.address.testutil.TypicalCards.DITTO;
import static seedu.address.testutil.TypicalCards.EEVEE;
import static seedu.address.testutil.TypicalCards.FLAREON;

import org.junit.jupiter.api.Test;

import seedu.address.model.game.Guess;

public class GameStatisticsTest {

    @Test
    public void constructor_nullTitle_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GameStatistics(null));
    }

    @Test
    public void constructor_validString_success() {
        new GameStatistics("abc");
    }

    @Test
    public void addDataPoint() {
        GameStatistics gameStats = new GameStatistics("pokemon");
        gameStats.addDataPoint(GameDataPoint.createSkipData(100), ABRA);
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("abra"), 101), ABRA);
        assertEquals(1, gameStats.getData().size()); // 2 data points in 1 card.
        gameStats.addDataPoint(GameDataPoint.createSkipData(100), BUTTERFREE);
        assertEquals(2, gameStats.getData().size()); // 2 different cards
    }

    @Test
    public void getScore() {
        GameStatistics gameStats = new GameStatistics("pokemon");
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("abra"), 101), ABRA);
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("butterfree"), 101),
                BUTTERFREE);
        assertEquals(100, gameStats.getScore());

        GameStatistics gameStats1 = new GameStatistics("pokemon");
        gameStats1.addDataPoint(GameDataPoint.createSkipData(10), ABRA);
        gameStats1.addDataPoint(GameDataPoint.createSkipData(10), BUTTERFREE);
        assertEquals(0, gameStats1.getScore());


        GameStatistics gameStats2 = new GameStatistics("pokemon");
        gameStats2.addDataPoint(GameDataPoint.createGuessData(new Guess("butterfree"), 10), ABRA);
        gameStats2.addDataPoint(GameDataPoint.createGuessData(new Guess("butterfree"), 10),
                BUTTERFREE);
        assertEquals(50, gameStats2.getScore());
    }

    @Test
    public void getTimeTakenSec() {
        GameStatistics gameStats = new GameStatistics("pokemon");
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("abra"), 10), ABRA);
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("butterfree"), 10),
                BUTTERFREE);
        assertEquals(0.02, gameStats.getTimeTakenSec(), 1e-9);

        gameStats.addDataPoint(GameDataPoint.createSkipData(11), CHARIZARD);
        gameStats.addDataPoint(GameDataPoint.createSkipData(12), DITTO);
        assertEquals(0.043, gameStats.getTimeTakenSec(), 1e-9);


        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("butterfree"), 21),
                EEVEE);
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("butterfree"), 31),
                FLAREON);
        assertEquals(0.095, gameStats.getTimeTakenSec(), 1e-9);
    }
}
