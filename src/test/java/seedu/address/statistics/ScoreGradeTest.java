package seedu.address.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.wordbankstats.ScoreData;

public class ScoreGradeTest {

    @Test
    public void getGrade_valid_success() {
        assertEquals(ScoreGrade.getGrade(ScoreData.MAX_SCORE), ScoreGrade.HIGH);
        assertEquals(ScoreGrade.getGrade(ScoreData.MIN_SCORE), ScoreGrade.LOW);
        assertEquals(ScoreGrade.getGrade(ScoreGrade.MEDIUM.getMinScore()), ScoreGrade.MEDIUM);
    }
}
