package seedu.savenus.storage;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.tag.Tag;

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

        Set<Location> likedLocations = source.getLikedLocations();
        Set<Tag> likedTags = source.getLikedTags();
        Set<Category> likedCategories = source.getLikedCategories();

        Set<Location> dislikedLocations = source.getDislikedLocations();
        Set<Tag> dislikedTags = source.getDislikedTags();
        Set<Category> dislikedCategories = source.getDislikedCategories();

        userRecommendations = new JsonAdaptedRecs(new UserRecommendations(likedCategories, likedTags, likedLocations,
                dislikedCategories, dislikedTags, dislikedLocations));

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
