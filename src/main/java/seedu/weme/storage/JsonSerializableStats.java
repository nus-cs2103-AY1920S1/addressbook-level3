package seedu.weme.storage;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javafx.beans.property.SimpleIntegerProperty;
import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.model.statistics.Stats;
import seedu.weme.model.statistics.StatsManager;

/**
 * An Immutable Stats that is serializable to JSON format.
 */
@JsonRootName(value = "statsData")
class JsonSerializableStats {

    private final Map<String, Integer> likeMap = new HashMap<>();

    /**
     * Constructs a {@code JsonSerializableWeme} with the given memes.
     */
    @JsonCreator
    public JsonSerializableStats(@JsonProperty("likeMap") Map<String, Integer> likeData) {
        likeMap.putAll(likeData);
    }

    /**
     * Converts a given {@code ReadOnlyWeme} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableWeme}.
     */
    public JsonSerializableStats(Stats source) {
        Map<String, SimpleIntegerProperty> likeData = source.getObservableLikeData();
        for (Map.Entry<String, SimpleIntegerProperty> entry : likeData.entrySet()) {
            likeMap.put(entry.getKey(), entry.getValue().get());
        }
    }

    /**
     * Converts this stats into the model's {@code Stats} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Stats toModelType() throws IllegalValueException {
        Stats stats = new StatsManager();
        Map<String, SimpleIntegerProperty> likeData = new HashMap<>();
        for (Map.Entry<String, Integer> entry : likeMap.entrySet()) {
            likeData.put(entry.getKey(), new SimpleIntegerProperty(entry.getValue()));
        }
        stats.setLikeData(likeData);
        return stats;
    }

}
