package seedu.address.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.wordbankstats.CardStatistics;

public class CardStatisticsTest {

    @Test
    public void constructor() {
        assertThrows(NullPointerException.class, () -> new CardStatistics(null, 1, 1));
        assertThrows(IllegalArgumentException.class, ()
            -> new CardStatistics("a", 1, 2)); // numShown < numCorrect
        assertThrows(IllegalArgumentException.class, ()
            -> new CardStatistics("a", -1, 1));
        assertThrows(IllegalArgumentException.class, ()
            -> new CardStatistics("a", 1, -1));
    }

    @Test
    public void addWrong_correct_success() {
        CardStatistics cardStatistics = new CardStatistics("abc", 3, 2);
        CardStatistics cardStatisticsWrong = new CardStatistics("abc", 4, 2);
        cardStatistics.addWrong();
        assertEquals(cardStatistics, cardStatisticsWrong);
    }

    @Test
    public void addCorrect_correct_success() {
        CardStatistics cardStatistics = new CardStatistics("abc", 3, 2);
        CardStatistics cardStatisticsCorrect = new CardStatistics("abc", 4, 3);
        cardStatistics.addCorrect();
        assertEquals(cardStatistics, cardStatisticsCorrect);
    }

    @Test
    public void equals() {
        CardStatistics cardStatistics = new CardStatistics("abc", 3, 2);
        assertEquals(cardStatistics, cardStatistics);
        Integer i = 1;
        assertNotEquals(cardStatistics, i);
    }
}
