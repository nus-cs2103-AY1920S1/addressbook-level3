package seedu.address.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.ABRA;
import static seedu.address.testutil.TypicalCards.BUTTERFREE;

import org.junit.jupiter.api.Test;

import seedu.address.model.game.Guess;

public class GameStatisticsBuilderTest {

    @Test
    public void constructor_nullTitle_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GameStatisticsBuilder(null));
    }

    @Test
    public void constructor_validString_success() {
        new GameStatisticsBuilder("abc");
    }

    @Test
    public void addDataPoint() {
        GameStatisticsBuilder gameStats = new GameStatisticsBuilder("pokemon");
        gameStats.addDataPoint(GameDataPoint.createSkipData(100), ABRA);
        gameStats.addDataPoint(GameDataPoint.createGuessData(new Guess("abra"), 101), ABRA);
        assertEquals(1, gameStats.getData().size()); // 2 data points in 1 card.
        gameStats.addDataPoint(GameDataPoint.createSkipData(100), BUTTERFREE);
        assertEquals(2, gameStats.getData().size()); // 2 different cards
    }
}
