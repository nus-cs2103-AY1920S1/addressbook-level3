package seedu.address.model.wordbankstats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.model.appsettings.DifficultyEnum;
import seedu.address.model.card.Card;
import seedu.address.statistics.GameStatistics;

/**
 * Represents the overall statistics of a word bank.
 */
public class WordBankStatistics {

    private final String wordBankName;
    private int gamesPlayed;
    private Optional<Double> fastestClear; // empty if never cleared
    private final List<CardStatistics> cardStats = new ArrayList<>();
    private final List<ScoreData> scoreStats = new ArrayList<>();
    private boolean receivedBadgeEasy;
    private boolean receivedBadgeNormal;
    private boolean receivedBadgeHard;

    public WordBankStatistics(String wordBankName,
                              int gamesPlayed,
                              Optional<Double> fastestClear,
                              List<CardStatistics> cardStats,
                              List<ScoreData> scoreStats,
                              boolean receivedBadgeEasy,
                              boolean receivedBadgeNormal,
                              boolean receivedBadgeHard) {
        this.wordBankName = wordBankName;
        this.gamesPlayed = gamesPlayed;
        this.fastestClear = fastestClear;
        this.cardStats.addAll(cardStats);
        this.scoreStats.addAll(scoreStats);

        this.receivedBadgeEasy = receivedBadgeEasy;
        this.receivedBadgeNormal = receivedBadgeNormal;
        this.receivedBadgeHard = receivedBadgeHard;
    }

    /**
     * Updates this word bank statistics to include {@code gameStats} of the difficulty {@code difficultyEnum}.
     */
    public void update(GameStatistics gameStats, DifficultyEnum difficultyEnum) {
        assert gameStats.getTitle().equals(getWordBankName());
        ++gamesPlayed;
        if (gameStats.isAllCorrect()) {
            // update fastestClear if necessary
            fastestClear = fastestClear.map(aDouble -> Math.min(aDouble, gameStats.getSecTaken()))
                    .or(() -> Optional.of(gameStats.getSecTaken()));
            // give badges
            switch (difficultyEnum) {
            case EASY:
                receivedBadgeEasy = true;
                break;
            case MEDIUM:
                receivedBadgeNormal = true;
                break;
            case HARD:
                receivedBadgeHard = true;
                break;
            default:
            }
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

    public List<CardStatistics> getMostMissedCardStats() {
        return cardStats;
    }

    public boolean isReceivedBadgeEasy() {
        return receivedBadgeEasy;
    }

    public boolean isReceivedBadgeNormal() {
        return receivedBadgeNormal;
    }

    public boolean isReceivedBadgeHard() {
        return receivedBadgeHard;
    }

    /**
     * Removes the statistics from a chosen card via its cardID.
     *
     * @param cardId The ID of the card to remove statistics from.
     */
    public void removeCardStatistics(String cardId) {
        for (int i = 0; i < cardStats.size(); ++i) {
            if (cardStats.get(i).getCardId().equals(cardId)) {
                cardStats.remove(i);
                return;
            }
        }
    }

    /**
     * Create a new word bank statistics with the name {@code wbName}.
     */
    public static WordBankStatistics getEmpty(String wbName) {
        return new WordBankStatistics(wbName,
                0,
                Optional.empty(),
                Collections.emptyList(),
                Collections.emptyList(),
                false, false, false);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WordBankStatistics)) {
            return false;
        }
        WordBankStatistics other = (WordBankStatistics) obj;
        return getWordBankName().equals(other.getWordBankName())
                && getGamesPlayed() == other.getGamesPlayed()
                && getFastestClear().equals(other.getFastestClear())
                && getCardStats().equals(other.getCardStats())
                && getScoreStats().equals(other.getScoreStats())
                && isReceivedBadgeEasy() == other.isReceivedBadgeEasy()
                && isReceivedBadgeNormal() == other.isReceivedBadgeNormal()
                && isReceivedBadgeHard() == other.isReceivedBadgeHard();
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
                .append("\n")
                .append(isReceivedBadgeEasy())
                .append(isReceivedBadgeNormal())
                .append(isReceivedBadgeHard()).toString();
    }
}
