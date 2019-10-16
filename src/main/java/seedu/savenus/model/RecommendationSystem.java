package seedu.savenus.model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.tag.Tag;

/**
 * Represents the Recommendation System of the menu.
 */
public class RecommendationSystem {

    private static final Comparator<Food> DEFAULT_COMPARATOR = (x, y) -> 0;
    private static final Predicate<Food> DEFAULT_PREDICATE = x -> true;

    // TODO
    private static final int LIKED_TAG_WEIGHT = 1;
    private static final int LIKED_LOCATION_WEIGHT = 2;
    private static final int LIKED_CATEGORY_WEIGHT = 3;

    private static final int DISLIKED_TAG_WEIGHT = 3;
    private static final int DISLIKED_LOCATION_WEIGHT = 4;
    private static final int DISLIKED_CATEGORY_WEIGHT = 5;

    // TODO
    private static final Set<Tag> likedTags = new HashSet<>();
    private static final Set<Location> likedLocations = new HashSet<>();
    private static final Set<Category> likedCategories = new HashSet<>();

    private static final Set<Tag> dislikedTags = new HashSet<>();
    private static final Set<Location> dislikedLocations = new HashSet<>();
    private static final Set<Category> dislikedCategories = new HashSet<>();

    private Comparator<Food> recommendationComparator;
    private Predicate<Food> recommendationPredicate;
    private boolean inUse;

    public RecommendationSystem() {
        this.inUse = false;

        // Calculate by recommendation value, using price to break ties
        recommendationComparator = Comparator.comparingInt(RecommendationSystem::calculateRecommendation).reversed()
                .thenComparingDouble(x -> Double.parseDouble(x.getPrice().value));

        // TODO: Dummy predicate
        recommendationPredicate = f -> Double.parseDouble(f.getPrice().value) < 50;
    }

    /**
     * Calculates the recommendation value for each Food provided
     */
    public static int calculateRecommendation(Food food) {
        int weight = 0;

        weight += LIKED_TAG_WEIGHT * likedTags.stream()
                .filter(food.getTags().stream()
                        .map(t -> new Tag(t.tagName.toLowerCase())).collect(Collectors.toSet())::contains)
                .count();
        weight -= DISLIKED_TAG_WEIGHT * dislikedTags.stream()
                .filter(food.getTags().stream()
                        .map(t -> new Tag(t.tagName.toLowerCase())).collect(Collectors.toSet())::contains)
                .count();

        weight += LIKED_CATEGORY_WEIGHT * likedCategories.stream()
                .filter(new Category(food.getCategory().category.toLowerCase())::equals)
                .count();
        weight -= DISLIKED_CATEGORY_WEIGHT * dislikedCategories.stream()
                .filter(new Category(food.getCategory().category.toLowerCase())::equals)
                .count();

        weight += LIKED_LOCATION_WEIGHT * likedLocations.stream()
                .filter(new Location(food.getLocation().location.toLowerCase())::equals)
                .count();
        weight -= DISLIKED_LOCATION_WEIGHT * dislikedLocations.stream()
                .filter(new Location(food.getLocation().location.toLowerCase())::equals)
                .count();

        return weight;
    }

    public Comparator<Food> getRecommendationComparator() {
        if (inUse) {
            return recommendationComparator;
        } else {
            return DEFAULT_COMPARATOR;
        }
    }

    public void setRecommendationComparator(Comparator<Food> recommendationComparator) {
        this.recommendationComparator = recommendationComparator;
    }

    public Predicate<Food> getRecommendationPredicate() {
        if (inUse) {
            return recommendationPredicate;
        } else {
            return DEFAULT_PREDICATE;
        }
    }

    public void setRecommendationPredicate(Predicate<Food> recommendationPredicate) {
        this.recommendationPredicate = recommendationPredicate;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
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
}
