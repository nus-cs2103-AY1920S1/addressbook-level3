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

    private static final Comparator<Food> DEFAULT_COMPARATOR = (x, y) -> 0;
    private static final Predicate<Food> DEFAULT_PREDICATE = x -> true;

    // TODO
    private static final int LIKED_TAG_WEIGHT = 1;
    private static final int LIKED_LOCATION_WEIGHT = 2;
    private static final int LIKED_CATEGORY_WEIGHT = 3;

    private static final int DISLIKED_TAG_WEIGHT = 3;
    private static final int DISLIKED_LOCATION_WEIGHT = 4;
    private static final int DISLIKED_CATEGORY_WEIGHT = 5;


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

        // Calculate by recommendation value, using price to break ties
        recommendationComparator = Comparator.comparingInt(this::calculateRecommendation).reversed()
                .thenComparingDouble(x -> Double.parseDouble(x.getPrice().value));

        // TODO: Dummy predicate
        recommendationPredicate = f -> Double.parseDouble(f.getPrice().value) < 50;

        likedTags = new HashSet<>();
        likedLocations = new HashSet<>();
        likedCategories = new HashSet<>();

        dislikedTags = new HashSet<>();
        dislikedLocations = new HashSet<>();
        dislikedCategories = new HashSet<>();

        // Some dummy data
        likedTags.add(new Tag("Cheese"));
        likedTags.add(new Tag("Healthy"));
        likedTags.add(new Tag("Pasta"));

        dislikedLocations.add(new Location("Some ulu location"));

        likedCategories.add(new Category("Chinese"));
    }

    /**
     * Calculates the recommendation value for each Food provided
     */
    private int calculateRecommendation(Food food) {
        int weight = 0;

        weight += LIKED_TAG_WEIGHT * likedTags.stream().filter(food.getTags()::contains).count();
        weight -= DISLIKED_TAG_WEIGHT * dislikedTags.stream().filter(food.getTags()::contains).count();

        weight += LIKED_CATEGORY_WEIGHT * likedCategories.stream().filter(food.getCategory()::equals).count();
        weight -= DISLIKED_CATEGORY_WEIGHT * dislikedCategories.stream().filter(food.getCategory()::equals).count();

        weight += LIKED_LOCATION_WEIGHT * likedLocations.stream().filter(food.getLocation()::equals).count();
        weight -= DISLIKED_LOCATION_WEIGHT * dislikedLocations.stream().filter(food.getLocation()::equals).count();

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
}
