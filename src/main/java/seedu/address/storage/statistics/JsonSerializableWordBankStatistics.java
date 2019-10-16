package seedu.address.storage.statistics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.statistics.CardStatistics;
import seedu.address.statistics.ScoreData;
import seedu.address.statistics.WordBankStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A Word Bank Statistics class that is serializable to JSON format.
 */

@JsonRootName(value = "wbstats")
public class JsonSerializableWordBankStatistics {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Card's %s field is missing!";

    private final String wordBankName;
    private final int gamesPlayed;
    private final Double fastestClear;
    private final List<JsonAdaptedCardStatistics> cardStats = new ArrayList<>();
    private final List<Integer> scoreStats = new ArrayList<>();

    @JsonCreator
    public JsonSerializableWordBankStatistics(@JsonProperty("name") String wordBankName,
                                              @JsonProperty("gamesPlayed") int gamesPlayed,
                                              @JsonProperty("fastestClear") Double fastestClear,
                                              @JsonProperty("cardStats") List<JsonAdaptedCardStatistics> cardStats,
                                              @JsonProperty("scoreStats") List<Integer> scoreStats) {
        this.wordBankName = wordBankName;
        this.gamesPlayed = gamesPlayed;
        this.fastestClear = fastestClear;
        this.cardStats.addAll(cardStats);
        this.scoreStats.addAll(scoreStats);
    }

    /**
     * Construct a json serializable version of the parameter {@code wbStats}.
     */
    public JsonSerializableWordBankStatistics(WordBankStatistics wbStats) {
        this.wordBankName = wbStats.getWordBankName();
        this.gamesPlayed = wbStats.getGamesPlayed();
        this.fastestClear = wbStats.getFastestClear().orElse(null);
        List<JsonAdaptedCardStatistics> cardStatsList = wbStats.getCardStats()
                .stream()
                .map(JsonAdaptedCardStatistics::new)
                .collect(Collectors.toList());
        List<Integer> scoreDataList = wbStats.getScoreStats()
                .stream()
                .map(ScoreData::getScore)
                .collect(Collectors.toList());
        this.cardStats.addAll(cardStatsList);
        this.scoreStats.addAll(scoreDataList);

    }

    /**
     * Converts this into the {@code WordBankStatistics} object.
     */
    public WordBankStatistics toModelType() {
        List<CardStatistics> cardStatistics = cardStats
                .stream()
                .map(JsonAdaptedCardStatistics::toModelType)
                .collect(Collectors.toList());
        List<ScoreData> scoreStatistics = scoreStats
                .stream()
                .map(ScoreData::new)
                .collect(Collectors.toList());
        return new WordBankStatistics(wordBankName,
                gamesPlayed,
                Optional.ofNullable(fastestClear),
                cardStatistics,
                scoreStatistics);
    }
}
