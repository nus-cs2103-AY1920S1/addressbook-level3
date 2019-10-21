package seedu.address.statistics;

import java.util.Arrays;

/**
 * Represents how good a score is. Used by the UI to set the text color for the score.
 * @see seedu.address.ui.modules.GameResultPanel
 */
public enum ScoreGrade {
    HIGH(80), MEDIUM(50), LOW(0);

    private int minScore;

    ScoreGrade(int minScore) {
        this.minScore = minScore;
    }

    static ScoreGrade getGrade(int grade) {
        return Arrays.stream(ScoreGrade.values())
                .filter(x -> grade >= x.minScore)
                .findFirst()
                .orElse(LOW);
    }
}
