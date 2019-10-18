package seedu.savenus.model.recommend;

import java.util.Comparator;
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

    private UserRecommendations userRecommendations;

    private Comparator<Food> recommendationComparator;
    private Predicate<Food> recommendationPredicate;
    private boolean inUse;

    public RecommendationSystem() {
        this.inUse = false;

        // Calculate by recommendation value, using price to break ties
        recommendationComparator = Comparator.comparingInt(this::calculateRecommendation).reversed()
                .thenComparingDouble(x -> Double.parseDouble(x.getPrice().value));

        // TODO: Dummy predicate
        recommendationPredicate = f -> Double.parseDouble(f.getPrice().value) < 50;
        userRecommendations = new UserRecommendations();
    }

    /**
     * Calculates the recommendation value for each Food provided
     */
    public int calculateRecommendation(Food food) {
        int weight = 0;

        weight += LIKED_TAG_WEIGHT * userRecommendations.getLikedTags().stream()
                .filter(food.getTags().stream()
                        .map(t -> new Tag(t.tagName.toLowerCase())).collect(Collectors.toSet())::contains)
                .count();
        weight -= DISLIKED_TAG_WEIGHT * userRecommendations.getDislikedTags().stream()
                .filter(food.getTags().stream()
                        .map(t -> new Tag(t.tagName.toLowerCase())).collect(Collectors.toSet())::contains)
                .count();

        weight += LIKED_CATEGORY_WEIGHT * userRecommendations.getLikedCategories().stream()
                .filter(new Category(food.getCategory().category.toLowerCase())::equals)
                .count();
        weight -= DISLIKED_CATEGORY_WEIGHT * userRecommendations.getDislikedCategories().stream()
                .filter(new Category(food.getCategory().category.toLowerCase())::equals)
                .count();

        weight += LIKED_LOCATION_WEIGHT * userRecommendations.getLikedLocations().stream()
                .filter(new Location(food.getLocation().location.toLowerCase())::equals)
                .count();
        weight -= DISLIKED_LOCATION_WEIGHT * userRecommendations.getDislikedLocations().stream()
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

    public void addLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        userRecommendations.addLikes(categoryList, tagList, locationList);
    }

    public void addDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        userRecommendations.addDislikes(categoryList, tagList, locationList);
    }

    public void clearLikes() {
        userRecommendations.clearLikes();
    }

    public void clearDislikes() {
        userRecommendations.clearDislikes();
    }

    public UserRecommendations getUserRecommendations() {
        return userRecommendations;
    }

    public void setUserRecommendations(UserRecommendations userRecommendations) {
        this.userRecommendations = userRecommendations;
    }
}
