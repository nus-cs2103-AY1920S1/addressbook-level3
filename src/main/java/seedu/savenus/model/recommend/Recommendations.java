package seedu.savenus.model.recommend;

import java.util.Set;

import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Tag;

//@@author jon-chua
/**
 * The API of the Recommendations component.
 */
public interface Recommendations {
    /**
     * Add likes to the user's liked list.
     *
     * @param categoryList The list of categories
     * @param tagList      The list of tags
     * @param locationList The list of locations
     */
    void addLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList);

    /**
     * Add dislikes to the user's liked list.
     *
     * @param categoryList The list of categories
     * @param tagList      The list of tags
     * @param locationList The list of locations
     */
    void addDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList);

    /**
     * Removes likes from the user's liked list.
     *
     * @param categoryList The list of categories
     * @param tagList      The list of tags
     * @param locationList The list of locations
     */
    void removeLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList);

    /**
     * Removes dislikes from the user's disliked list.
     *
     * @param categoryList The list of categories
     * @param tagList      The list of tags
     * @param locationList The list of locations
     */
    void removeDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList);

    /**
     * Clears the user's like list.
     */
    void clearLikes();

    /**
     * Clears the user's dislike list.
     */
    void clearDislikes();

    /**
     * Gets the user's liked tags.
     */
    Set<Tag> getLikedTags();

    /**
     * Gets the user's liked locations.
     */
    Set<Location> getLikedLocations();

    /**
     * Gets the user's liked categories.
     */
    Set<Category> getLikedCategories();

    /**
     * Gets the user's disliked tags.
     */
    Set<Tag> getDislikedTags();

    /**
     * Gets the user's disliked locations.
     */
    Set<Location> getDislikedLocations();

    /**
     * Gets the user's disliked categories.
     */
    Set<Category> getDislikedCategories();
}
