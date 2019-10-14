package seedu.address.statistics;

import seedu.address.model.card.Card;
import seedu.address.model.game.Guess;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameStatistics {
    private Map<Card, List<GuessData>> data;

    public GameStatistics() {
        data = new LinkedHashMap<>();
    }

    public void addGuessData(GuessData guessData, Card card) {
        if (data.containsKey(card)) {
            data.get(card).add(guessData);
        } else {
            List<GuessData> guessDataList = new ArrayList<>();
            guessDataList.add(guessData);
            data.put(card, guessDataList);
        }
    }

    public static class GuessData {
        private final Guess guess;
        private final long millisTaken;

        public GuessData(Guess guess, long millisTaken) {
            this.guess = guess;
            this.millisTaken = millisTaken;
        }

        @Override
        public String toString() {
            return guess + ": " + millisTaken + "ms";
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Card, List<GuessData>> entry : data.entrySet()) {
            sb.append("-------").append(entry.getKey()).append("-------\n");
            for (GuessData guessData : entry.getValue()) {
                sb.append("   ").append(guessData).append("\n");
            }
        }
        return sb.toString();
    }
}
