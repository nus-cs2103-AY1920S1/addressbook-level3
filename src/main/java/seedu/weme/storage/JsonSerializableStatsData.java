package seedu.weme.storage;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.statistics.StatsEngine;
import seedu.weme.statistics.StatsManager;

/**
 * An Immutable StatsData that is serializable to JSON format.
 */
@JsonRootName(value = "statsData")
class JsonSerializableStatsData {

    private final JsonAdaptedLikeData jsonLikeData;

    /**
     * Constructs a {@code JsonSerializableMemeBook} with the given memes.
     */
    @JsonCreator
    public JsonSerializableStatsData(@JsonProperty("likes") Map<String, Integer> likeData) {
        jsonLikeData = new JsonAdaptedLikeData(likeData);
    }

    /**
     * Converts a given {@code ReadOnlyMemeBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMemeBook}.
     */
    public JsonSerializableStatsData(StatsEngine source) {
        jsonLikeData = new JsonAdaptedLikeData(source.getLikeData());
    }

    /**
     * Converts this meme book into the model's {@code MemeBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public StatsEngine toModelType() throws IllegalValueException {
        StatsEngine statsEngine = new StatsManager();
        statsEngine.setLikeData(jsonLikeData.toModelType());
        return statsEngine;
    }

}
