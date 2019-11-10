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
    public static final String MESSAGE_INVALID_LIKE = "Like data contains out of bound like.";
    public static final String MESSAGE_INVALID_DISLIKE = "Like data contains out of bound dislike.";
    public static final String MESSAGE_DUPLICATE_LIKE = "Like data contains duplicate like.";
    public static final String MESSAGE_DUPLICATE_DISLIKE = "Like data contains duplicate dislike.";

    private final Map<String, Integer> likeMap = new HashMap<>();
    private final Map<String, Integer> dislikeMap = new HashMap<>();

    /**
     * Constructs a {@code JsonSerializableWeme} with the given memes.
     */
    @JsonCreator
    public JsonSerializableStats(@JsonProperty("likeMap") Map<String, Integer> likeData,
                                 @JsonProperty("dislikeMap") Map<String, Integer> dislikeData) {
        likeMap.putAll(likeData);
        dislikeMap.putAll(dislikeData);
    }

    /**
     * Converts a given {@code ReadOnlyWeme} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableWeme}.
     */
    public JsonSerializableStats(Stats source) {
        Map<String, SimpleIntegerProperty> likeData = source.getObservableLikeData();
        Map<String, SimpleIntegerProperty> dislikeData = source.getObservableDislikeData();
        for (Map.Entry<String, SimpleIntegerProperty> entry : likeData.entrySet()) {
            likeMap.put(entry.getKey(), entry.getValue().get());
        }
        for (Map.Entry<String, SimpleIntegerProperty> entry : dislikeData.entrySet()) {
            dislikeMap.put(entry.getKey(), entry.getValue().get());
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
        Map<String, SimpleIntegerProperty> dislikeData = new HashMap<>();
        for (Map.Entry<String, Integer> entry : likeMap.entrySet()) {
            if (entry.getValue() < 0) {
                throw new IllegalValueException(MESSAGE_INVALID_LIKE);
            } else if (likeData.containsKey(entry.getKey())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LIKE);
            }
            likeData.put(entry.getKey(), new SimpleIntegerProperty(entry.getValue()));
        }
        for (Map.Entry<String, Integer> entry : dislikeMap.entrySet()) {
            if (entry.getValue() < 0) {
                throw new IllegalValueException(MESSAGE_INVALID_DISLIKE);
            } else if (dislikeData.containsKey(entry.getKey())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DISLIKE);
            }
            dislikeData.put(entry.getKey(), new SimpleIntegerProperty(entry.getValue()));
        }
        stats.setLikeData(likeData);
        stats.setDislikeData(dislikeData);
        return stats;
    }

}
