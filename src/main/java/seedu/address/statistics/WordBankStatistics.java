package seedu.address.statistics;

import seedu.address.model.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Represents the overall statistics of a word bank.
 */
public class WordBankStatistics {

    private final String wordBankName;
    private int gamesPlayed;
    private Optional<Double> fastestClear; // empty if never cleared
    private final List<CardStatistics> cardStats = new ArrayList<>();
    private final List<ScoreData> scoreStats = new ArrayList<>();

    public WordBankStatistics(String wordBankName,
                              int gamesPlayed,
                              Optional<Double> fastestClear,
                              List<CardStatistics> cardStats,
                              List<ScoreData> scoreStats) {
        this.wordBankName = wordBankName;
        this.gamesPlayed = gamesPlayed;
        this.fastestClear = fastestClear;
        this.cardStats.addAll(cardStats);
        this.scoreStats.addAll(scoreStats);
    }

    /**
     * Updates this word bank statistics to include {@code gameStats}.
     */
    public void update(GameStatistics gameStats) {
        ++gamesPlayed;
        if (gameStats.allCorrect()) {
            // update fastestClear if necessary
            fastestClear = fastestClear.map(aDouble -> Math.min(aDouble, gameStats.getSecTaken()))
                    .or(() -> Optional.of(gameStats.getSecTaken()));
        }
        List<Card> correctCards = gameStats.getCorrectCards();
        List<Card> wrongCards = gameStats.getWrongCards();
        for (Card card : wrongCards) {
            Optional<CardStatistics> stat = getRespectiveCardStats(card.getId());
            if (stat.isPresent()) {
                stat.get().addWrong();
            } else {
                cardStats.add(new CardStatistics(card.getId(), 1, 0));
            }
        }
        for (Card card : correctCards) {
            Optional<CardStatistics> stat = getRespectiveCardStats(card.getId());
            if (stat.isPresent()) {
                stat.get().addCorrect();
            } else {
                cardStats.add(new CardStatistics(card.getId(), 1, 1));
            }
        }
        scoreStats.add(new ScoreData(gameStats.getScore()));
    }

    /**
     * Get the {@code CardStatistics} given the {@code cardId}.
     */
    private Optional<CardStatistics> getRespectiveCardStats(String cardId) {
        return cardStats.stream()
                .filter(x -> x.getCardId().equals(cardId))
                .findFirst();
    }

    public String getWordBankName() {
        return wordBankName;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public Optional<Double> getFastestClear() {
        return fastestClear;
    }

    public List<CardStatistics> getCardStats() {
        return cardStats;
    }

    public List<ScoreData> getScoreStats() {
        return scoreStats;
    }

    public ScoreData getHighestScore() {
        return scoreStats.stream()
                .reduce(new ScoreData(0), ScoreData::max);
    }

    public List<Card> getMostMissedCards(int num) {
        return null; //todo
    }

    /**
     * Create a new black statistics with the name {@code wbName}.
     */
    public static WordBankStatistics getEmpty(String wbName) {
        return new WordBankStatistics(wbName,
                0,
                Optional.empty(),
                Collections.emptyList(),
                Collections.emptyList());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WordBankStatistics)) {
            return false;
        }
        return this.wordBankName.equals(((WordBankStatistics) obj).wordBankName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append(wordBankName)
                .append("\n")
                .append("played:")
                .append(gamesPlayed)
                .append("\n")
                .append("fastest perfect:")
                .append(fastestClear)
                .append("\n")
                .append(cardStats)
                .append("\n")
                .append(scoreStats)
                .append("\n").toString();
    }
}
