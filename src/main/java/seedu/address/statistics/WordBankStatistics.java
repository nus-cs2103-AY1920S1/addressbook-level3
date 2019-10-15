package seedu.address.statistics;

import java.util.ArrayList;
import java.util.List;

public class WordBankStatistics {
    private final String wordBankName;
    private final int gamesPlayed;
    private final double fastestPerfect;
    private final List<CardStatistics> cardStats = new ArrayList<>();
    private final List<ScoreData> scoreStats = new ArrayList<>();

    public WordBankStatistics(String wordBankName,
                              int gamesPlayed,
                              double fastestPerfect,
                              List<CardStatistics> cardStats,
                              List<ScoreData> scoreStats) {
        this.wordBankName = wordBankName;
        this.gamesPlayed = gamesPlayed;
        this.fastestPerfect = fastestPerfect;
        this.cardStats.addAll(cardStats);
        this.scoreStats.addAll(scoreStats);
    }

    public String getWordBankName() {
        return wordBankName;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public double getFastestPerfect() {
        return fastestPerfect;
    }

    public List<CardStatistics> getCardStats() {
        return cardStats;
    }

    public List<ScoreData> getScoreStats() {
        return scoreStats;
    }
}
