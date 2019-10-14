package seedu.address.statistics;

import seedu.address.model.card.Card;
import seedu.address.model.game.Guess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameStatistics {
    private final Map<Card, List<GameDataPoint>> data;
    private final String title;

    public GameStatistics(String title) {
        this.title = title;
        data = new LinkedHashMap<>();
    }

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
     * Returns the time taken for the entire game.
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
