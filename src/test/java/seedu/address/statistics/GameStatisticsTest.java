package seedu.address.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void constructor() {
        assertThrows(NullPointerException.class, () -> new GameStatisticsBuilder(null));
    }
    @Test
    public void getScore_valid_success() {
        GameStatisticsBuilder gameStats = new GameStatisticsBuilder("pokemon");
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("abra"), 101), ABRA);
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("butterfree"), 101),
                BUTTERFREE);
        assertEquals(100, gameStats.build().getScore());

        GameStatisticsBuilder gameStats1 = new GameStatisticsBuilder("pokemon");
        gameStats1.addDataPoint(GameDataPoint.createSkipData(10), ABRA);
        gameStats1.addDataPoint(GameDataPoint.createSkipData(10), BUTTERFREE);
        assertEquals(0, gameStats1.build().getScore());


        GameStatisticsBuilder gameStats2 = new GameStatisticsBuilder("pokemon");
        gameStats2.addDataPoint(GameDataPoint.createGuessData(new Guess("butterfree"), 10), ABRA);
        gameStats2.addDataPoint(GameDataPoint.createGuessData(new Guess("butterfree"), 10),
                BUTTERFREE);
        assertEquals(50, gameStats2.build().getScore());
    }

    @Test
    public void getTimeTakenSec_valid_success() {
        GameStatisticsBuilder gameStats = new GameStatisticsBuilder("pokemon");
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("abra"), 10), ABRA);
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("butterfree"), 10),
                BUTTERFREE);
        assertEquals(0.02, gameStats.build().getSecTaken(), 1e-9);

        gameStats.addDataPoint(GameDataPoint.createSkipData(11), CHARIZARD);
        gameStats.addDataPoint(GameDataPoint.createSkipData(12), DITTO);
        assertEquals(0.043, gameStats.build().getSecTaken(), 1e-9);


        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("butterfree"), 21),
                EEVEE);
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("butterfree"), 31),
                FLAREON);
        assertEquals(0.095, gameStats.build().getSecTaken(), 1e-9);
    }

    @Test
    public void isAllCorrect_valid_success() {
        GameStatisticsBuilder gameStats = new GameStatisticsBuilder("pokemon");
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("abra"), 10), ABRA);
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("butterfree"), 10),
                BUTTERFREE);
        assertTrue(gameStats.build().isAllCorrect());

        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("abra"), 10), BUTTERFREE);
        assertFalse(gameStats.build().isAllCorrect());

        GameStatisticsBuilder gameStats1 = new GameStatisticsBuilder("pokemon");
        gameStats1.addDataPoint(GameDataPoint.createGuessData(new Guess("abra"), 10), ABRA);
        gameStats1.addDataPoint(GameDataPoint.createGuessData(new Guess("butterfree"), 10),
                BUTTERFREE);
        gameStats1.addDataPoint(GameDataPoint.createSkipData(10), ABRA);
        assertFalse(gameStats1.build().isAllCorrect());
    }

    @Test
    public void equals() {
        GameStatisticsBuilder gameStats = new GameStatisticsBuilder("pokemon");
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("abra"), 10), ABRA);
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("butterfree"), 10),
                BUTTERFREE);
        assertEquals(gameStats, gameStats);
        assertNotEquals(gameStats, 1);
    }
}
