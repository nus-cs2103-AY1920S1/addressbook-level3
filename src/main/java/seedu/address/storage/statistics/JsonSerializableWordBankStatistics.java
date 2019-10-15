package seedu.address.storage.statistics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.statistics.CardStatistics;
import seedu.address.statistics.ScoreData;
import seedu.address.statistics.WordBankStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable Word Bank Statistics that is serializable to JSON format.
 */

@JsonRootName(value = "wbstats")
public class JsonSerializableWordBankStatistics {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Card's %s field is missing!";

    private final String wordBankName;
    private final int gamesPlayed;
    private final double fastestPerfect;
    private final List<JsonAdaptedCardStatistics> cardStats = new ArrayList<>();
    private final List<JsonAdaptedScoreData> scoreStats = new ArrayList<>();

    @JsonCreator
    public JsonSerializableWordBankStatistics(@JsonProperty("name") String wordBankName,
                                              @JsonProperty("gamesPlayed") int gamesPlayed,
                                              @JsonProperty("fastestPerfect") double fastestPerfect,
                                              @JsonProperty("cardStats") List<JsonAdaptedCardStatistics> cardStats,
                                              @JsonProperty("scoreStats") List<JsonAdaptedScoreData> scoreStats) {
        this.wordBankName = wordBankName;
        this.gamesPlayed = gamesPlayed;
        this.fastestPerfect = fastestPerfect;
        this.cardStats.addAll(cardStats);
        this.scoreStats.addAll(scoreStats);
    }

    public JsonSerializableWordBankStatistics(WordBankStatistics wbStats) {
        this.wordBankName = wbStats.getWordBankName();
        this.gamesPlayed = wbStats.getGamesPlayed();
        this.fastestPerfect = wbStats.getFastestPerfect();
        List<JsonAdaptedCardStatistics> cardStatsList = wbStats.getCardStats()
                .stream()
                .map(JsonAdaptedCardStatistics::new)
                .collect(Collectors.toList());
        List<JsonAdaptedScoreData> scoreDataList = wbStats.getScoreStats()
                .stream()
                .map(JsonAdaptedScoreData::new)
                .collect(Collectors.toList());
        this.cardStats.addAll(cardStatsList);
        this.scoreStats.addAll(scoreDataList);

    }

    public WordBankStatistics toModelType() {
        List<CardStatistics> cardStatistics = cardStats
                .stream()
                .map(JsonAdaptedCardStatistics::toModelType)
                .collect(Collectors.toList());
        List<ScoreData> scoreStatistics = scoreStats
                .stream()
                .map(JsonAdaptedScoreData::toModelType)
                .collect(Collectors.toList());
        return new WordBankStatistics(wordBankName,
                gamesPlayed,
                fastestPerfect,
                cardStatistics,
                scoreStatistics);
    }
}
