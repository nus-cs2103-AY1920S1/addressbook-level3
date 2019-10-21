package seedu.savenus.model.recommend;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
     * Removes likes from the recommendation system.
     *
     * @param categoryList The list of categories
     * @param tagList      The list of tags
     * @param locationList The list of locations
     */
    public void removeLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        likedCategories.removeAll(categoryList);
        likedTags.removeAll(tagList);
        likedLocations.removeAll(locationList);
    }

    /**
     * Removes dislikes from the recommendation system.
     *
     * @param categoryList The list of categories
     * @param tagList      The list of tags
     * @param locationList The list of locations
     */
    public void removeDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        dislikedCategories.removeAll(categoryList);
        dislikedTags.removeAll(tagList);
        dislikedLocations.removeAll(locationList);
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UserRecommendations)) {
            return false;
        }

        UserRecommendations otherRec = (UserRecommendations) other;
        return getLikedCategories().equals(otherRec.getLikedCategories())
                && getLikedTags().equals(otherRec.getLikedTags())
                && getLikedLocations().equals(otherRec.getLikedLocations())
                && getDislikedCategories().equals(otherRec.getDislikedCategories())
                && getDislikedTags().equals(otherRec.getDislikedTags())
                && getDislikedLocations().equals(otherRec.getDislikedLocations());
    }

    @Override
    public String toString() {
        return "Current likes: Categories: " + getLikedCategories()
                .stream().map(c -> c.category)
                .collect(Collectors.joining(", "))
                + " | Tags: " + getLikedTags()
                .stream().map(t -> t.tagName)
                .collect(Collectors.joining(", "))
                + " | Locations: " + getLikedLocations()
                .stream().map(l -> l.location)
                .collect(Collectors.joining(", "))
                + "\nCurrent dislikes:"
                + " Categories: " + getDislikedCategories()
                .stream().map(c -> c.category)
                .collect(Collectors.joining(", "))
                + " | Tags: " + getDislikedTags()
                .stream().map(t -> t.tagName)
                .collect(Collectors.joining(", "))
                + " | Locations: " + getDislikedLocations()
                .stream().map(l -> l.location)
                .collect(Collectors.joining(", "));
    }
}
