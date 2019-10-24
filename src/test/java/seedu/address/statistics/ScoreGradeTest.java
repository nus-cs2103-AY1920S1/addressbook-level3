package seedu.address.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ScoreGradeTest {

    @Test
    public void getGrade() {
        assertEquals(ScoreGrade.getGrade(ScoreData.MAX_SCORE), ScoreGrade.HIGH);
        assertEquals(ScoreGrade.getGrade(ScoreData.MIN_SCORE), ScoreGrade.LOW);
        assertEquals(ScoreGrade.getGrade(ScoreGrade.MEDIUM.getMinScore()), ScoreGrade.MEDIUM);
    }
}
