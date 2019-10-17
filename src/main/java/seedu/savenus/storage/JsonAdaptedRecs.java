package seedu.savenus.storage;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.savenus.commons.exceptions.IllegalValueException;

import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.tag.Tag;

/**
 * Jackson-friendly version of {@link UserRecommendations}.
 */
class JsonAdaptedRecs {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "User Recommendation's %s field is missing!";

    private Set<Location> likedLocations;
    private Set<Tag> likedTags;
    private Set<Category> likedCategories;

    private Set<Location> dislikedLocations;
    private Set<Tag> dislikedTags;
    private Set<Category> dislikedCategories;

    /**
     * Constructs a {@code JsonAdaptedRecs} with the given user's recommendations.
     */
    @JsonCreator
    public JsonAdaptedRecs(@JsonProperty("likedCategories") HashSet<Category> likedCategories,
                           @JsonProperty("likedTags") HashSet<Tag> likedTags,
                           @JsonProperty("likedLocations") HashSet<Location> likedLocations,
                           @JsonProperty("dislikedCategories") HashSet<Category> dislikedCategories,
                           @JsonProperty("dislikedTags") HashSet<Tag> dislikedTags,
                           @JsonProperty("dislikedLocations") HashSet<Location> dislikedLocations) {
        this.likedCategories = likedCategories;
        this.likedTags = likedTags;
        this.likedLocations = likedLocations;

        this.dislikedCategories = dislikedCategories;
        this.dislikedTags = dislikedTags;
        this.dislikedLocations = dislikedLocations;
    }

    /**
     * Converts a given {@code UserRecommendations} into this class for Jackson use.
     */
    public JsonAdaptedRecs(UserRecommendations source) {
        likedCategories = source.getLikedCategories();
        likedTags = source.getLikedTags();
        likedLocations = source.getLikedLocations();

        dislikedCategories = source.getDislikedCategories();
        dislikedTags = source.getDislikedTags();
        dislikedLocations = source.getDislikedLocations();
    }

    /**
     * Converts this Jackson-friendly adapted wallet object into the model's {@code UserRecommendations} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted UserRecommendations.
     */
    public UserRecommendations toModelType() throws IllegalValueException {

        if (likedLocations == null || likedTags == null || likedCategories == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    UserRecommendations.class.getSimpleName()));
        }

        try {
            // Convert all to lowercase, and parse into new category / tag / location
            // This will throw IllegalArgumentExceptions if there are any problems with the saved data
            Set<Category> newLikedCategories = likedCategories.stream()
                    .map(c -> new Category(c.category.toLowerCase()))
                    .collect(Collectors.toSet());
            Set<Tag> newLikedTags = likedTags.stream()
                    .map(t -> new Tag(t.tagName.toLowerCase()))
                    .collect(Collectors.toSet());
            Set<Location> newLikedLocation = likedLocations.stream()
                    .map(l -> new Location(l.location.toLowerCase()))
                    .collect(Collectors.toSet());

            Set<Category> newDislikedCategories = dislikedCategories.stream()
                    .map(c -> new Category(c.category.toLowerCase()))
                    .collect(Collectors.toSet());
            Set<Tag> newDislikedTags = dislikedTags.stream()
                    .map(t -> new Tag(t.tagName.toLowerCase()))
                    .collect(Collectors.toSet());
            Set<Location> newDislikedLocation = dislikedLocations.stream()
                    .map(l -> new Location(l.location.toLowerCase()))
                    .collect(Collectors.toSet());

            // Check for no overlaps
            if (newLikedCategories.stream().anyMatch(newDislikedCategories::contains)
                || newLikedTags.stream().anyMatch(newDislikedTags::contains)
                || newLikedLocation.stream().anyMatch(newDislikedLocation::contains)) {
                throw new IllegalArgumentException("Duplicate entry found in opposing user recommendation list!");
            }

            return new UserRecommendations(newLikedCategories, newLikedTags, newLikedLocation,
                    newDislikedCategories, newDislikedTags, newDislikedLocation);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }
    }
}
