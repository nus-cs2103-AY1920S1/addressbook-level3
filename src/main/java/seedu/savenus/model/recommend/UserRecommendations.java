package seedu.savenus.model.recommend;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Tag;

//@@author jon-chua
/**
 * Stores the user's recommendations
 */
public class UserRecommendations implements Recommendations {
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

    @Override
    public void addLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        likedCategories.addAll(categoryList);
        likedTags.addAll(tagList);
        likedLocations.addAll(locationList);
    }

    @Override
    public void addDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        dislikedCategories.addAll(categoryList);
        dislikedTags.addAll(tagList);
        dislikedLocations.addAll(locationList);
    }

    @Override
    public void removeLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        likedCategories.removeAll(categoryList);
        likedTags.removeAll(tagList);
        likedLocations.removeAll(locationList);
    }

    @Override
    public void removeDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        dislikedCategories.removeAll(categoryList);
        dislikedTags.removeAll(tagList);
        dislikedLocations.removeAll(locationList);
    }

    @Override
    public void clearLikes() {
        likedTags.clear();
        likedLocations.clear();
        likedCategories.clear();
    }

    @Override
    public void clearDislikes() {
        dislikedTags.clear();
        dislikedLocations.clear();
        dislikedCategories.clear();
    }

    @Override
    public Set<Tag> getLikedTags() {
        return likedTags;
    }

    @Override
    public Set<Location> getLikedLocations() {
        return likedLocations;
    }

    @Override
    public Set<Category> getLikedCategories() {
        return likedCategories;
    }

    @Override
    public Set<Tag> getDislikedTags() {
        return dislikedTags;
    }

    @Override
    public Set<Location> getDislikedLocations() {
        return dislikedLocations;
    }

    @Override
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
                .stream().parallel().map(c -> c.category)
                .collect(Collectors.joining(", "))
                + " | Tags: " + getLikedTags()
                .stream().parallel().map(t -> t.tagName)
                .collect(Collectors.joining(", "))
                + " | Locations: " + getLikedLocations()
                .stream().parallel().map(l -> l.location)
                .collect(Collectors.joining(", "))
                + "\nCurrent dislikes:"
                + " Categories: " + getDislikedCategories()
                .stream().parallel().map(c -> c.category)
                .collect(Collectors.joining(", "))
                + " | Tags: " + getDislikedTags()
                .stream().parallel().map(t -> t.tagName)
                .collect(Collectors.joining(", "))
                + " | Locations: " + getDislikedLocations()
                .stream().parallel().map(l -> l.location)
                .collect(Collectors.joining(", "));
    }
}
