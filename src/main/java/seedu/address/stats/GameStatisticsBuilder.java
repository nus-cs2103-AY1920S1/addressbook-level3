package seedu.address.stats;

import seedu.address.model.card.Card;
import seedu.address.model.game.Guess;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameStatisticsBuilder {
    private Map<Card, List<GuessData>> data;

    public GameStatisticsBuilder() {
        data = new LinkedHashMap<>();
    }

    public void addGuessData(Card card, GuessData guessData) {
        if (data.containsKey(card)) {
            data.get(card).add(guessData);
        } else {
            List<GuessData> guessDataList = new ArrayList<>();
            guessDataList.add(guessData);
            data.put(card, guessDataList);
        }
    }

    public class GuessData {
        private final Guess guess;
        private final long millisTaken;

        public GuessData(Guess guess, long millisTaken) {
            this.guess = guess;
            this.millisTaken = millisTaken;
        }
    }
}
