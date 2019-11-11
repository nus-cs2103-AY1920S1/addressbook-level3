package seedu.address.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.wordbankstats.ScoreData;

public class ScoreDataTest {

    @Test
    public void constructor() {
        assertEquals(new ScoreData(ScoreData.MAX_SCORE + 100), new ScoreData(ScoreData.MAX_SCORE));
        assertEquals(new ScoreData(ScoreData.MIN_SCORE - 100), new ScoreData(ScoreData.MIN_SCORE));
    }

    @Test
    public void equals() {
        ScoreData sd1 = new ScoreData(10);
        ScoreData sd2 = new ScoreData(10);
        ScoreData sd3 = new ScoreData(20);
        assertEquals(sd1, sd2);
        assertNotEquals(sd1, sd3);
        assertNotEquals(sd1, 10);
    }

    @Test
    public void max_valid_success() {
        ScoreData sd1 = new ScoreData(10);
        ScoreData sd2 = new ScoreData(20);
        assertEquals(ScoreData.max(sd1, sd2), sd2);
    }
}
