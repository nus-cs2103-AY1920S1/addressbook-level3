package seedu.address.statistics;

import java.util.List;

import seedu.address.model.card.Card;

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

    public boolean allCorrect() {
        return getWrongCards().isEmpty();
    }
}
