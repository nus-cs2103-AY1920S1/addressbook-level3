package seedu.address.statistics;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import seedu.address.model.card.Card;
import seedu.address.model.game.Guess;

/**
 * A class that manages all statistics to be shown at the end of the game.
 */
public class GameStatistics {

    /** The data that maps each card to the actions that happen when that card is shown **/
    private final Map<Card, List<GameDataPoint>> data;

    private final String title;

    public GameStatistics(String title) {
        requireNonNull(title);
        this.title = title;
        data = new LinkedHashMap<>();
    }

    /**
     * Add a data point to the statistics.
     * @param gameDataPoint The data point that represents the user action.
     * @param card The card displayed when the action happens
     */
    public void addDataPoint(GameDataPoint gameDataPoint, Card card) {
        if (data.containsKey(card)) {
            data.get(card).add(gameDataPoint);
        } else {
            List<GameDataPoint> gameDataPointList = new ArrayList<>();
            gameDataPointList.add(gameDataPoint);
            data.put(card, gameDataPointList);
        }
    }

    public String getTitle() {
        return title;
    }

    public Map<Card, List<GameDataPoint>> getData() {
        return data;
    }

    /**
     * Returns the score of the game. To be shown in the result page.
     * The scoring system can be flexible.
     */
    public int getScore() {
        // todo implement this method properly
        int correctAnswer = (int) data.keySet()
                .stream()
                .filter(x -> {
                    Optional<Guess> guess = data.get(x).get(0).getGuess();
                    return guess.isPresent() && guess.get().matches(x.getWord());
                })
                .count();
        int score = (int) Math.round(((double) correctAnswer) / data.size() * 100);
        return score;
    }

    public ScoreGrade getScoreGrade() {
        return ScoreGrade.getGrade(getScore());
    }

    /**
     * Returns the time taken for the entire game. To be shown on the result page.
     */
    public double getTimeTakenSec() {
        return data.keySet()
                .stream()
                // sum up the last guess for every card
                .mapToLong(x -> {
                    List<GameDataPoint> dataPoints = data.get(x);
                    return dataPoints.get(dataPoints.size() - 1).getMillisTaken();
                }).sum() / 1000.0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Card, List<GameDataPoint>> entry : data.entrySet()) {
            sb.append("-------").append(entry.getKey()).append("-------\n");
            for (GameDataPoint gameDataPoint : entry.getValue()) {
                sb.append("   ").append(gameDataPoint).append("\n");
            }
        }
        return sb.toString();
    }

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
}
