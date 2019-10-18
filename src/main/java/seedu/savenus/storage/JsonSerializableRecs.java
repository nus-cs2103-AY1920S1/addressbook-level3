package seedu.savenus.storage;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.recommend.UserRecommendations;

/**
 * An Immutable Recommendation Set that is serializable to JSON format.
 */
@JsonRootName(value = "savenus")
class JsonSerializableRecs {

    private JsonAdaptedRecs userRecommendations;

    /**
     * Constructs a {@code JsonSerializableRecs} with the given recommendations.
     */
    @JsonCreator
    public JsonSerializableRecs(@JsonProperty("userRecs") JsonAdaptedRecs userRecs) {
        this.userRecommendations = userRecs;
    }

    /**
     * Converts a given {@code UserRecommendations} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRecs}.
     */
    public JsonSerializableRecs(UserRecommendations source) {
        Set<String> likedLocations = source.getLikedLocations().stream()
                .map(l -> l.location).collect(Collectors.toSet());
        Set<String> likedTags = source.getLikedTags().stream()
                .map(t -> t.tagName).collect(Collectors.toSet());
        Set<String> likedCategories = source.getLikedCategories().stream()
                .map(c -> c.category).collect(Collectors.toSet());

        Set<String> dislikedLocations = source.getDislikedLocations().stream()
                .map(l -> l.location).collect(Collectors.toSet());
        Set<String> dislikedTags = source.getDislikedTags().stream()
                .map(t -> t.tagName).collect(Collectors.toSet());
        Set<String> dislikedCategories = source.getDislikedCategories().stream()
                .map(c -> c.category).collect(Collectors.toSet());

        userRecommendations = new JsonAdaptedRecs(likedCategories, likedTags, likedLocations,
                dislikedCategories, dislikedTags, dislikedLocations);
    }

    /**
     * Converts this UserRecommendations into the model's {@code UserRecommendations} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public UserRecommendations toModelType() throws IllegalValueException {
        return userRecommendations.toModelType();
    }

}
