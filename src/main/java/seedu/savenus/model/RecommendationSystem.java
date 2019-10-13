package seedu.savenus.model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.tag.Tag;

/**
 * Represents the Recommendation System of the menu.
 */
public class RecommendationSystem {

    private Comparator<Food> recommendationComparator;
    private Predicate<Food> recommendationPredicate;
    private boolean inUse;

    // TODO
    private final Set<Tag> likedTags;
    private final Set<Location> likedLocations;
    private final Set<Category> likedCategories;

    private final Set<Tag> dislikedTags;
    private final Set<Location> dislikedLocations;
    private final Set<Category> dislikedCategories;

    public RecommendationSystem() {
        this.inUse = false;

        // TODO: Dummy comparators and predicates
        recommendationComparator = Comparator.comparingDouble(x -> Double.parseDouble(x.getPrice().value));
        recommendationPredicate = f -> Double.parseDouble(f.getPrice().value) < 50;

        likedTags = new HashSet<>();
        likedLocations = new HashSet<>();
        likedCategories = new HashSet<>();

        dislikedTags = new HashSet<>();
        dislikedLocations = new HashSet<>();
        dislikedCategories = new HashSet<>();
    }

    public Comparator<Food> getRecommendationComparator() {
        if (inUse) {
            return recommendationComparator;
        } else {
            return (x, y) -> 0;
        }
    }

    public void setRecommendationComparator(Comparator<Food> recommendationComparator) {
        this.recommendationComparator = recommendationComparator;
    }

    public Predicate<Food> getRecommendationPredicate() {
        if (inUse) {
            return recommendationPredicate;
        } else {
            return x -> true;
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
}
