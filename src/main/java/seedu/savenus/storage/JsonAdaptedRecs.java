package seedu.savenus.storage;

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
    public static final String DUPLICATE_ENTRY_OPPOSING_FOUND =
            "Duplicate entry found in opposing user recommendation list!";

    private Set<String> likedLocations;
    private Set<String> likedTags;
    private Set<String> likedCategories;

    private Set<String> dislikedLocations;
    private Set<String> dislikedTags;
    private Set<String> dislikedCategories;

    /**
     * Constructs a {@code JsonAdaptedRecs} with the given user's recommendations.
     */
    @JsonCreator
    public JsonAdaptedRecs(@JsonProperty("likedCategories") Set<String> likedCategories,
                           @JsonProperty("likedTags") Set<String> likedTags,
                           @JsonProperty("likedLocations") Set<String> likedLocations,
                           @JsonProperty("dislikedCategories") Set<String> dislikedCategories,
                           @JsonProperty("dislikedTags") Set<String> dislikedTags,
                           @JsonProperty("dislikedLocations") Set<String> dislikedLocations) {
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
        likedCategories = source.getLikedCategories().stream().map(c -> c.category).collect(Collectors.toSet());
        likedTags = source.getLikedTags().stream().map(t -> t.tagName).collect(Collectors.toSet());
        likedLocations = source.getLikedLocations().stream().map(l -> l.location).collect(Collectors.toSet());

        dislikedCategories = source.getDislikedCategories().stream().map(c -> c.category).collect(Collectors.toSet());
        dislikedTags = source.getDislikedTags().stream().map(t -> t.tagName).collect(Collectors.toSet());
        dislikedLocations = source.getDislikedLocations().stream().map(l -> l.location).collect(Collectors.toSet());
    }

    /**
     * Converts this Jackson-friendly adapted wallet object into the model's {@code UserRecommendations} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted UserRecommendations.
     */
    public UserRecommendations toModelType() throws IllegalValueException {

        if (likedLocations == null || dislikedLocations == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        } else if (likedTags == null || dislikedTags == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Tag.class.getSimpleName()));
        } else if (likedCategories == null || dislikedCategories == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Category.class.getSimpleName()));
        }

            try {
            // Convert all to lowercase, and parse into new category / tag / location
            // This will throw IllegalArgumentExceptions if there are any problems with the saved data
            Set<Category> newLikedCategories = likedCategories.stream()
                    .map(c -> new Category(c.toLowerCase()))
                    .collect(Collectors.toSet());
            Set<Tag> newLikedTags = likedTags.stream()
                    .map(t -> new Tag(t.toLowerCase()))
                    .collect(Collectors.toSet());
            Set<Location> newLikedLocation = likedLocations.stream()
                    .map(l -> new Location(l.toLowerCase()))
                    .collect(Collectors.toSet());

            Set<Category> newDislikedCategories = dislikedCategories.stream()
                    .map(c -> new Category(c.toLowerCase()))
                    .collect(Collectors.toSet());
            Set<Tag> newDislikedTags = dislikedTags.stream()
                    .map(t -> new Tag(t.toLowerCase()))
                    .collect(Collectors.toSet());
            Set<Location> newDislikedLocation = dislikedLocations.stream()
                    .map(l -> new Location(l.toLowerCase()))
                    .collect(Collectors.toSet());

            // Check for no overlaps
            if (newLikedCategories.stream().anyMatch(newDislikedCategories::contains)
                || newLikedTags.stream().anyMatch(newDislikedTags::contains)
                || newLikedLocation.stream().anyMatch(newDislikedLocation::contains)) {
                throw new IllegalArgumentException(DUPLICATE_ENTRY_OPPOSING_FOUND);
            }

            return new UserRecommendations(newLikedCategories, newLikedTags, newLikedLocation,
                    newDislikedCategories, newDislikedTags, newDislikedLocation);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }
    }
}
