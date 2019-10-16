package seedu.address.statistics;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.model.card.Card;
import seedu.address.model.game.Guess;

/**
 * A class that manages all statistics to be added during the game.
 */
public class GameStatisticsBuilder {

    /** The data that maps each card to the actions that happen when that card is shown **/
    private final Map<Card, List<GameDataPoint>> data;

    private final String title;

    public GameStatisticsBuilder(String title) {
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

    public GameStatistics build() {
        return new GameStatistics(title,
                getScore(),
                getTimeTakenSec(),
                getAllCorrectCards(),
                getAllWrongCards());
    }

    // The following are helper methods to construct the game statistics.

    /**
     * Returns the score of the game. To be shown in the result page.
     * The scoring system can be flexible.
     */
    private int getScore() {
        // todo implement this method properly
        int correctAnswer = (int) data.keySet()
                .stream()
                .filter(x -> {
                    Optional<Guess> guess = getLastDataPoint(x).getGuess();
                    return guess.isPresent() && guess.get().matches(x.getWord());
                })
                .count();
        int score = (int) Math.round(((double) correctAnswer) / data.size() * 100);
        return score;
    }

    private ScoreGrade getScoreGrade() {
        return ScoreGrade.getGrade(getScore());
    }

    /**
     * Returns the time taken for the entire game. To be shown on the result page.
     */
    private double getTimeTakenSec() {
        return data.keySet()
                .stream()
                // sum up the last guess for every card
                .mapToLong(x -> getLastDataPoint(x).getMillisTaken())
                .sum() / 1000.0;
    }

    private List<Card> getAllWrongCards() {
        return data.keySet()
                .stream()
                .filter(x -> {
                    Optional<Guess> guess = getLastDataPoint(x).getGuess();
                    return guess.isEmpty() || !guess.get().matches(x.getWord());
                })
                .collect(Collectors.toList());
    }

    private List<Card> getAllCorrectCards() {
        return data.keySet()
                .stream()
                .filter(x -> {
                    Optional<Guess> guess = getLastDataPoint(x).getGuess();
                    return guess.isPresent() && guess.get().matches(x.getWord());
                })
                .collect(Collectors.toList());
    }

    private GameDataPoint getLastDataPoint(Card card) {
        List<GameDataPoint> dataPoints = data.get(card);
        return dataPoints.get(dataPoints.size() - 1);
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
}
