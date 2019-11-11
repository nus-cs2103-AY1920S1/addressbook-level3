package seedu.address.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.ABRA;
import static seedu.address.testutil.TypicalCards.BUTTERFREE;
import static seedu.address.testutil.TypicalCards.CHARIZARD;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.game.Guess;

public class GameStatisticsBuilderTest {

    @Test
    public void constructor() {
        assertThrows(NullPointerException.class, () -> new GameStatisticsBuilder(null));
    }

    @Test
    public void addDataPoint_valid_success() {
        GameStatisticsBuilder gameStats = new GameStatisticsBuilder("pokemon");
        gameStats.addDataPoint(GameDataPoint.createSkipData(100), ABRA);
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("abra"), 101), ABRA);
        assertEquals(1, gameStats.size()); // 2 data points in 1 card.
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("guess"),
                100), BUTTERFREE);
        assertEquals(2, gameStats.size()); // 2 different cards
    }

    @Test
    public void addDataPoint_millisHasPassed_throwsIllegalArgumentException() {
        GameStatisticsBuilder gameStats = new GameStatisticsBuilder("pokemon");
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("guess"),
                100), BUTTERFREE);
        assertThrows(IllegalArgumentException.class, () -> gameStats.addDataPoint(GameDataPoint.createSkipData(90),
                BUTTERFREE));
    }

    @Test
    public void build_validInput_success() {
        GameStatisticsBuilder gameStats = new GameStatisticsBuilder("pokemon");
        gameStats.addDataPoint(GameDataPoint.createSkipData(100), ABRA);
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("abra"), 101), ABRA);
        gameStats.addDataPoint(GameDataPoint.createSkipData(100), BUTTERFREE);
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("ccc"), 10), CHARIZARD);
        assertEquals(3, gameStats.size());
        assertEquals(gameStats.build().getScore(), 33);
        assertEquals(gameStats.build().getCorrectCards(), List.of(ABRA));
        assertEquals(gameStats.build().getWrongCards(), List.of(BUTTERFREE, CHARIZARD));
        assertEquals(gameStats.build().getSecTaken(), 0.211);
        assertEquals(gameStats.build().getTitle(), "pokemon");
    }
}
