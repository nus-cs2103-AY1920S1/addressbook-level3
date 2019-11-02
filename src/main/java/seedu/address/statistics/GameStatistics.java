package seedu.address.statistics;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.model.card.Card;
import seedu.address.model.wordbankstats.ScoreData;

/**
 * Represents the statistics of a single game.
 */
public class GameStatistics {

    private final String title;
    private final int score;
    private final ScoreGrade grade;
    private final double secTaken;
    private final List<Card> correctCards;
    private final List<Card> wrongCards;

    public GameStatistics(String title, int score, double secTaken,
                          List<Card> correctCards, List<Card> wrongCards) {
        requireAllNonNull(title, correctCards, wrongCards);
        if (score < ScoreData.MIN_SCORE || score > ScoreData.MAX_SCORE) {
            throw new IllegalArgumentException("Score must be within bound as specified in ScoreData");
        }
        this.title = title;
        this.score = score;
        this.secTaken = secTaken;
        this.correctCards = correctCards;
        this.wrongCards = wrongCards;

        this.grade = ScoreGrade.getGrade(score);
    }

    public String getTitle() {
        return title;
    }

    public int getScore() {
        return score;
    }

    public ScoreGrade getGrade() {
        return grade;
    }

    public double getSecTaken() {
        return secTaken;
    }

    public List<Card> getCorrectCards() {
        return correctCards;
    }

    public List<Card> getWrongCards() {
        return wrongCards;
    }

    public boolean isAllCorrect() {
        return getWrongCards().isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GameStatistics)) {
            return false;
        }

        GameStatistics otherGameStats = (GameStatistics) other;
        return title.equals(otherGameStats.getTitle())
                && score == otherGameStats.getScore()
                && grade.equals(otherGameStats.getGrade())
                && secTaken == otherGameStats.getSecTaken()
                && correctCards.equals(otherGameStats.getCorrectCards())
                && wrongCards.equals(otherGameStats.getWrongCards());
    }
}
