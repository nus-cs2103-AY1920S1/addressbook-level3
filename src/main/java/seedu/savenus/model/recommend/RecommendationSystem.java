package seedu.savenus.model.recommend;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Tag;
import seedu.savenus.model.purchase.Purchase;

/**
 * Represents the Recommendation System of the menu.
 */
public class RecommendationSystem {
    private static RecommendationSystem recommendationSystem;
    private static Comparator<Food> comparator =
            Comparator.comparingDouble(getInstance()::calculateRecommendation).reversed()
                    .thenComparingDouble(x -> Double.parseDouble(x.getPrice().value));

    private static final double LIKED_TAG_WEIGHT = 0.03;
    private static final double LIKED_LOCATION_WEIGHT = 0.10;
    private static final double LIKED_CATEGORY_WEIGHT = 0.15;

    private static final double LIKED_TAG_BONUS_LOW = 0.05;
    private static final double LIKED_TAG_BONUS_MED = 0.10;
    private static final double LIKED_TAG_BONUS_HIGH = 0.25;

    private static final int LIKED_TAG_BONUS_LOW_NUM = 1;
    private static final int LIKED_TAG_BONUS_MED_NUM = 3;
    private static final int LIKED_TAG_BONUS_HIGH_NUM = 5;

    private static final double IDENTICAL_FOOD_BONUS_LOW = 0.10;
    private static final double IDENTICAL_FOOD_BONUS_MED = 0.30;
    private static final double IDENTICAL_FOOD_BONUS_HIGH = 0.50;

    private static final int IDENTICAL_FOOD_BONUS_LOW_NUM = 2;
    private static final int IDENTICAL_FOOD_BONUS_MED_NUM = 5;
    private static final int IDENTICAL_FOOD_BONUS_HIGH_NUM = 10;

    private static final double HISTORY_TAG_WEIGHT = 0.01;
    private static final double HISTORY_LOCATION_WEIGHT = 0.03;
    private static final double HISTORY_CATEGORY_WEIGHT = 0.02;

    private static final double DISLIKED_TAG_WEIGHT = -0.10;
    private static final double DISLIKED_LOCATION_WEIGHT = -0.30;
    private static final double DISLIKED_CATEGORY_WEIGHT = -0.40;

    private static final double DISLIKED_TAG_PENALTY_LOW = -0.10;
    private static final double DISLIKED_TAG_PENALTY_MED = -0.30;
    private static final double DISLIKED_TAG_PENALTY_HIGH = -0.50;

    private static final int DISLIKED_TAG_PENALTY_LOW_NUM = 1;
    private static final int DISLIKED_TAG_PENALTY_MED_NUM = 2;
    private static final int DISLIKED_TAG_PENALTY_HIGH_NUM = 3;

    // Negative multiplier starting from -10 to 0, valid for 2 days (172800000ms)
    private static final int JUST_BOUGHT_FOOD_VALIDITY = 172800000;

    private UserRecommendations userRecommendations;
    private ObservableList<Purchase> purchaseHistory;
    private BigDecimal budget = new BigDecimal("10000");
    private BigDecimal dailyBudget = new BigDecimal("10000");
    private Predicate<Food> predicate = f -> dailyBudget.compareTo(new BigDecimal(f.getPrice().value)) >= 0;
    private int daysToExpire = 100;

    private boolean inUse;

    private RecommendationSystem() {
    }

    public static RecommendationSystem getInstance() {
        if (recommendationSystem == null) {
            recommendationSystem = new RecommendationSystem();
        }
        return recommendationSystem;
    }

    private static BigDecimal calculateIdenticalFoodNegativeWeight(long timeInMillis) {
        BigDecimal scale = new BigDecimal(1).divide(new BigDecimal(1728000), 20, RoundingMode.HALF_DOWN);
        BigDecimal intercept = new BigDecimal(10);
        return new BigDecimal(timeInMillis).multiply(scale).sqrt(new MathContext(20)).subtract(intercept);
    }

    /**
     * Calculates the recommendation value for each Food provided
     */
    public double calculateRecommendation(Food food) {
        double weight = 0;

        // Bonus for liked tags
        long numOfLikedTags = userRecommendations.getLikedTags().stream()
                .filter(food.getTags().stream()
                        .map(t -> new Tag(t.tagName.toLowerCase())).collect(Collectors.toSet())::contains)
                .count();

        if (numOfLikedTags >= LIKED_TAG_BONUS_HIGH_NUM) {
            weight += LIKED_TAG_BONUS_HIGH;
        } else if (numOfLikedTags >= LIKED_TAG_BONUS_MED_NUM) {
            weight += LIKED_TAG_BONUS_MED;
        } else if (numOfLikedTags >= LIKED_TAG_BONUS_LOW_NUM) {
            weight += LIKED_TAG_BONUS_LOW;
        }

        weight += LIKED_TAG_WEIGHT * numOfLikedTags;

        // Bonus for liked categories
        long numOfLikedCategories = userRecommendations.getLikedCategories().stream()
                .filter(new Category(food.getCategory().category.toLowerCase())::equals)
                .count();

        if (numOfLikedCategories > 0) {
            weight += LIKED_CATEGORY_WEIGHT;
        }

        // Bonus for liked locations
        long numOfLikedLocations = userRecommendations.getLikedLocations().stream()
                .filter(new Location(food.getLocation().location.toLowerCase())::equals)
                .count();

        if (numOfLikedLocations > 0) {
            weight += LIKED_LOCATION_WEIGHT;
        }

        // Penalty for disliked tags
        long numOfDislikedTags = userRecommendations.getDislikedTags().stream()
                .filter(food.getTags().stream()
                        .map(t -> new Tag(t.tagName.toLowerCase())).collect(Collectors.toSet())::contains)
                .count();

        if (numOfDislikedTags >= DISLIKED_TAG_PENALTY_HIGH_NUM) {
            weight += DISLIKED_TAG_PENALTY_HIGH;
        } else if (numOfDislikedTags >= DISLIKED_TAG_PENALTY_MED_NUM) {
            weight += DISLIKED_TAG_PENALTY_MED;
        } else if (numOfDislikedTags >= DISLIKED_TAG_PENALTY_LOW_NUM) {
            weight += DISLIKED_TAG_PENALTY_LOW;
        }

        weight += DISLIKED_TAG_WEIGHT * numOfDislikedTags;

        // Penalty for disliked categories
        long numOfDislikedCategories = userRecommendations.getDislikedCategories().stream()
                .filter(new Category(food.getCategory().category.toLowerCase())::equals)
                .count();

        if (numOfDislikedCategories > 0) {
            weight += DISLIKED_CATEGORY_WEIGHT;
        }

        // Penalty for disliked locations
        long numOfDislikedLocations = userRecommendations.getDislikedLocations().stream()
                .filter(new Location(food.getLocation().location.toLowerCase())::equals)
                .count();

        if (numOfDislikedLocations > 0) {
            weight += DISLIKED_LOCATION_WEIGHT;
        }

        // Calculate bonus for purchases of same food
        long numOfIdenticalFoodPurchase = purchaseHistory.stream()
                .map(Purchase::getPurchasedFood).filter(food::equals).count();

        if (numOfIdenticalFoodPurchase >= IDENTICAL_FOOD_BONUS_HIGH_NUM) {
            weight += IDENTICAL_FOOD_BONUS_HIGH;
        } else if (numOfIdenticalFoodPurchase >= IDENTICAL_FOOD_BONUS_MED_NUM) {
            weight += IDENTICAL_FOOD_BONUS_MED;
        } else if (numOfIdenticalFoodPurchase >= IDENTICAL_FOOD_BONUS_LOW_NUM) {
            weight += IDENTICAL_FOOD_BONUS_LOW;
        }

        // Calculate penalty for recent purchase of similar food
        long latestTimePurchased = purchaseHistory.stream().filter(purchase -> purchase.getPurchasedFood().equals(food))
                .map(Purchase::getTimeOfPurchaseInMillisSinceEpoch).max(Comparator.naturalOrder()).orElse(-1L);

        long millisSinceLastPurchase = System.currentTimeMillis() - latestTimePurchased;

        if (millisSinceLastPurchase > 0 && millisSinceLastPurchase < JUST_BOUGHT_FOOD_VALIDITY) {
            weight += calculateIdenticalFoodNegativeWeight(millisSinceLastPurchase).doubleValue();
        }

        return weight;
    }

    public Comparator<Food> getRecommendationComparator() {
        return comparator;
    }

    public void setRecommendationComparator(Comparator<Food> recommendationComparator) {
        comparator = recommendationComparator;
    }

    public Predicate<Food> getRecommendationPredicate() {
        return predicate;
    }

    public void setRecommendationPredicate(Predicate<Food> recommendationPredicate) {
        predicate = recommendationPredicate;
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

    public void removeLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        userRecommendations.removeLikes(categoryList, tagList, locationList);
    }

    public void removeDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        userRecommendations.removeDislikes(categoryList, tagList, locationList);
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

    public void updatePurchaseHistory(ObservableList<Purchase> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    /**
     * Update budget.
     *
     * @param budget The specified budget
     */
    public void updateBudget(BigDecimal budget) {
        this.budget = budget;
        if (budget.equals(BigDecimal.ZERO) || daysToExpire == 0) {
            this.dailyBudget = BigDecimal.ZERO;
        } else {
            this.dailyBudget = budget.divide(new BigDecimal(daysToExpire * 2), 2, RoundingMode.HALF_DOWN);
        }
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public BigDecimal getDailyBudget() {
        return dailyBudget;
    }

    public void updateDaysToExpire(int daysToExpire) {
        this.daysToExpire = daysToExpire;
    }

}
