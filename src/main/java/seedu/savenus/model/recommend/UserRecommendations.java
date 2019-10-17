package seedu.savenus.model.recommend;

import java.util.HashSet;
import java.util.Set;

import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.tag.Tag;

/**
 * Stores the user's recommendations
 */
public class UserRecommendations {
    private Set<Tag> likedTags;
    private Set<Location> likedLocations;
    private Set<Category> likedCategories;

    private Set<Tag> dislikedTags;
    private Set<Location> dislikedLocations;
    private Set<Category> dislikedCategories;

    public UserRecommendations() {
        this(new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    }

    public UserRecommendations(Set<Category> likedCategories, Set<Tag> likedTags, Set<Location> likedLocations,
                               Set<Category> dislikedCategories, Set<Tag> dislikedTags,
                               Set<Location> dislikedLocations) {

        this.likedCategories = likedCategories;
        this.likedTags = likedTags;
        this.likedLocations = likedLocations;

        this.dislikedCategories = dislikedCategories;
        this.dislikedTags = dislikedTags;
        this.dislikedLocations = dislikedLocations;
    }

    /**
     * Add likes to the recommendation system.
     *
     * @param categoryList The list of categories
     * @param tagList      The list of tags
     * @param locationList The list of locations
     */
    public void addLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        likedCategories.addAll(categoryList);
        likedTags.addAll(tagList);
        likedLocations.addAll(locationList);
    }

    /**
     * Add dislikes to the recommendation system.
     *
     * @param categoryList The list of categories
     * @param tagList      The list of tags
     * @param locationList The list of locations
     */
    public void addDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        dislikedCategories.addAll(categoryList);
        dislikedTags.addAll(tagList);
        dislikedLocations.addAll(locationList);
    }

    /**
     * Clear the recommendation system's likes.
     */
    public void clearLikes() {
        likedTags.clear();
        likedLocations.clear();
        likedCategories.clear();
    }

    /**
     * Clear the recommendation system's dislikes.
     */
    public void clearDislikes() {
        dislikedTags.clear();
        dislikedLocations.clear();
        dislikedCategories.clear();
    }

    public Set<Tag> getLikedTags() {
        return likedTags;
    }

    public Set<Location> getLikedLocations() {
        return likedLocations;
    }

    public Set<Category> getLikedCategories() {
        return likedCategories;
    }

    public Set<Tag> getDislikedTags() {
        return dislikedTags;
    }

    public Set<Location> getDislikedLocations() {
        return dislikedLocations;
    }

    public Set<Category> getDislikedCategories() {
        return dislikedCategories;
    }
}
