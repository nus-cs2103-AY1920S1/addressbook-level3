package seedu.savenus.model.recommend;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Tag;
import seedu.savenus.model.purchase.Purchase;

//@@author jon-chua
/**
 * Represents the Recommendation System of the menu.
 */
public class RecommendationSystem implements Recommender {

    // Recommendation system weights
    public static final double LIKED_TAG_WEIGHT = 0.03;
    public static final double LIKED_LOCATION_WEIGHT = 0.10;
    public static final double LIKED_CATEGORY_WEIGHT = 0.15;

    public static final double LIKED_TAG_BONUS_LOW = 0.05;
    public static final double LIKED_TAG_BONUS_MED = 0.10;
    public static final double LIKED_TAG_BONUS_HIGH = 0.25;

    public static final int LIKED_TAG_BONUS_LOW_NUM = 1;
    public static final int LIKED_TAG_BONUS_MED_NUM = 3;
    public static final int LIKED_TAG_BONUS_HIGH_NUM = 5;

    public static final double IDENTICAL_FOOD_BONUS_LOW = 0.10;
    public static final double IDENTICAL_FOOD_BONUS_MED = 0.30;
    public static final double IDENTICAL_FOOD_BONUS_HIGH = 0.50;

    public static final int IDENTICAL_FOOD_BONUS_LOW_NUM = 2;
    public static final int IDENTICAL_FOOD_BONUS_MED_NUM = 5;
    public static final int IDENTICAL_FOOD_BONUS_HIGH_NUM = 10;

    public static final double HISTORY_TAG_WEIGHT = 0.01;
    public static final double HISTORY_LOCATION_WEIGHT = 0.03;
    public static final double HISTORY_CATEGORY_WEIGHT = 0.02;

    public static final double DISLIKED_TAG_WEIGHT = -0.10;
    public static final double DISLIKED_LOCATION_WEIGHT = -0.30;
    public static final double DISLIKED_CATEGORY_WEIGHT = -0.40;

    public static final double DISLIKED_TAG_PENALTY_LOW = -0.10;
    public static final double DISLIKED_TAG_PENALTY_MED = -0.30;
    public static final double DISLIKED_TAG_PENALTY_HIGH = -0.50;

    public static final int DISLIKED_TAG_PENALTY_LOW_NUM = 1;
    public static final int DISLIKED_TAG_PENALTY_MED_NUM = 2;
    public static final int DISLIKED_TAG_PENALTY_HIGH_NUM = 3;

    // Negative multiplier starting from -10 to 0, valid for 2 days (172800000ms)
    public static final int JUST_BOUGHT_FOOD_VALIDITY = 172800000;

    private static final Comparator<Food> DEFAULT_COMPARATOR = (x, y) -> 0;
    private static final Predicate<Food> DEFAULT_PREDICATE = x -> true;

    private static RecommendationSystem recommendationSystem;
    private static Comparator<Food> comparator =
            Comparator.comparingDouble(getInstance()::calculateRecommendation).reversed()
                    .thenComparingDouble(x -> Double.parseDouble(x.getPrice().value));

    private UserRecommendations userRecommendations;
    private ObservableList<Purchase> purchaseHistory;
    private BigDecimal budget = new BigDecimal("10000");
    private BigDecimal dailyBudget = new BigDecimal("10000");
    private Predicate<Food> predicate = f -> dailyBudget.compareTo(new BigDecimal(f.getPrice().value)) >= 0;
    private int daysToExpire = 100;

    private boolean isInUse;

    private RecommendationSystem() {
    }

    public static RecommendationSystem getInstance() {
        if (recommendationSystem == null) {
            recommendationSystem = new RecommendationSystem();
        }
        return recommendationSystem;
    }

    /**
     * Calculates the recommendation value for each Food provided
     */
    @Override
    public double calculateRecommendation(Food food) {
        requireNonNull(food);

        double weight = 0;

        // Bonuses for liked tags, categories and locations
        weight += getLikedTagBonus(food);
        weight += getLikedCategoryBonus(food);
        weight += getLikedLocationBonus(food);

        // Penalties for disliked tags, categories and locations
        weight += getDislikedTagPenalty(food);
        weight += getDislikedCategoryPenalty(food);
        weight += getDislikedLocationPenalty(food);

        // Bonuses for matching tags, categories and locations in purchase history
        weight += getPurchaseHistoryTagBonus(food);
        weight += getPurchaseHistoryLocationBonus(food);
        weight += getPurchaseHistoryCategoryBonus(food);

        // Bonus for purchase of the same food in purchase history
        weight += getPurchaseHistorySameFoodBonus(food);

        // Penalty for recent (<2 days) purchase of the same food in purchase history
        weight += getPurchaseHistorySameFoodTimePenalty(food);

        return weight;
    }

    /**
     * Calculates the bonus for matching tags in the user's liked tag set
     */
    private double getLikedTagBonus(Food food) {
        double weight = 0;

        long numOfLikedTags = userRecommendations.getLikedTags().stream().parallel()
                .filter(food.getTags().stream()
                        .map(t -> new Tag(t.tagName.toLowerCase())).collect(Collectors.toSet())::contains)
                .count();

        if (numOfLikedTags >= LIKED_TAG_BONUS_HIGH_NUM) {
            weight = LIKED_TAG_BONUS_HIGH;
        } else if (numOfLikedTags >= LIKED_TAG_BONUS_MED_NUM) {
            weight = LIKED_TAG_BONUS_MED;
        } else if (numOfLikedTags >= LIKED_TAG_BONUS_LOW_NUM) {
            weight = LIKED_TAG_BONUS_LOW;
        }

        return weight + LIKED_TAG_WEIGHT * numOfLikedTags;
    }

    /**
     * Calculates the bonus for matching categories in the user's liked category set
     */
    private double getLikedCategoryBonus(Food food) {
        long numOfLikedCategories = userRecommendations.getLikedCategories().stream().parallel()
                .filter(new Category(food.getCategory().category.toLowerCase())::equals)
                .count();

        if (numOfLikedCategories > 0) {
            return LIKED_CATEGORY_WEIGHT;
        }
        return 0;
    }

    /**
     * Calculates the bonus for matching locations in the user's liked location set
     */
    private double getLikedLocationBonus(Food food) {
        long numOfLikedLocations = userRecommendations.getLikedLocations().stream().parallel()
                .filter(new Location(food.getLocation().location.toLowerCase())::equals)
                .count();

        if (numOfLikedLocations > 0) {
            return LIKED_LOCATION_WEIGHT;
        }
        return 0;
    }

    /**
     * Calculates the penalty for matching tags in the user's disliked tag set
     */
    private double getDislikedTagPenalty(Food food) {
        double weight = 0;

        long numOfDislikedTags = userRecommendations.getDislikedTags().stream().parallel()
                .filter(food.getTags().stream()
                        .map(t -> new Tag(t.tagName.toLowerCase())).collect(Collectors.toSet())::contains)
                .count();

        if (numOfDislikedTags >= DISLIKED_TAG_PENALTY_HIGH_NUM) {
            weight = DISLIKED_TAG_PENALTY_HIGH;
        } else if (numOfDislikedTags >= DISLIKED_TAG_PENALTY_MED_NUM) {
            weight = DISLIKED_TAG_PENALTY_MED;
        } else if (numOfDislikedTags >= DISLIKED_TAG_PENALTY_LOW_NUM) {
            weight = DISLIKED_TAG_PENALTY_LOW;
        }

        return weight + DISLIKED_TAG_WEIGHT * numOfDislikedTags;
    }

    /**
     * Calculates the penalty for matching categories in the user's disliked category set
     */
    private double getDislikedCategoryPenalty(Food food) {
        long numOfDislikedCategories = userRecommendations.getDislikedCategories().stream().parallel()
                .filter(new Category(food.getCategory().category.toLowerCase())::equals)
                .count();

        if (numOfDislikedCategories > 0) {
            return DISLIKED_CATEGORY_WEIGHT;
        }
        return 0;
    }

    /**
     * Calculates the penalty for matching locations in the user's disliked location set
     */
    private double getDislikedLocationPenalty(Food food) {
        long numOfDislikedLocations = userRecommendations.getDislikedLocations().stream().parallel()
                .filter(new Location(food.getLocation().location.toLowerCase())::equals)
                .count();

        if (numOfDislikedLocations > 0) {
            return DISLIKED_LOCATION_WEIGHT;
        }
        return 0;
    }

    /**
     * Calculates the bonus for matching tags in the user's purchase history
     */
    private double getPurchaseHistoryTagBonus(Food food) {
        long numberOfMatchedTags = purchaseHistory.stream().parallel()
                .map(purchase -> purchase.getPurchasedFood().getTags())
                .map(tagSet -> tagSet.stream()
                        .map(tag -> new Tag(tag.tagName.toLowerCase()))
                        .filter(tag -> food.getTags().stream()
                                .map(foodTag -> new Tag(foodTag.tagName.toLowerCase()))
                                .collect(Collectors.toSet())
                                .contains(tag)))
                .map(Stream::count)
                .mapToLong(Long::longValue).sum();

        return numberOfMatchedTags * HISTORY_TAG_WEIGHT;
    }

    /**
     * Calculates the bonus for matching locations in the user's purchase history
     */
    private double getPurchaseHistoryLocationBonus(Food food) {
        long numberOfMatchedLocations = purchaseHistory.stream().parallel()
                .map(purchase -> purchase.getPurchasedFood().getLocation())
                .map(location -> new Location(location.location.toLowerCase()))
                .filter(location -> new Location(food.getLocation().location.toLowerCase()).equals(location))
                .count();

        return numberOfMatchedLocations * HISTORY_LOCATION_WEIGHT;
    }

    /**
     * Calculates the bonus for matching categories in the user's purchase history
     */
    private double getPurchaseHistoryCategoryBonus(Food food) {
        long numberOfMatchedCategories = purchaseHistory.stream().parallel()
                .map(purchase -> purchase.getPurchasedFood().getCategory())
                .map(category -> new Category(category.category.toLowerCase()))
                .filter(category -> new Category(food.getCategory().category.toLowerCase()).equals(category))
                .count();

        return numberOfMatchedCategories * HISTORY_CATEGORY_WEIGHT;
    }

    /**
     * Calculates the bonus for purchases of the same food in the user's purchase history
     */
    private double getPurchaseHistorySameFoodBonus(Food food) {
        long numOfIdenticalFoodPurchase = purchaseHistory.stream().parallel()
                .map(Purchase::getPurchasedFood).filter(food::equals).count();

        if (numOfIdenticalFoodPurchase >= IDENTICAL_FOOD_BONUS_HIGH_NUM) {
            return IDENTICAL_FOOD_BONUS_HIGH;
        } else if (numOfIdenticalFoodPurchase >= IDENTICAL_FOOD_BONUS_MED_NUM) {
            return IDENTICAL_FOOD_BONUS_MED;
        } else if (numOfIdenticalFoodPurchase >= IDENTICAL_FOOD_BONUS_LOW_NUM) {
            return IDENTICAL_FOOD_BONUS_LOW;
        }
        return 0;
    }

    /**
     * Calculates the penalty for recent purchases of the same food in the user's purchase history
     * A function that increases from -10 (0ms since last purchase) to 0 (172800000ms = 2 days since last purchase)
     */
    private double getPurchaseHistorySameFoodTimePenalty(Food food) {
        long latestTimePurchased = purchaseHistory.stream().parallel()
                .filter(purchase -> purchase.getPurchasedFood().equals(food))
                .map(Purchase::getTimeOfPurchaseInMillisSinceEpoch).max(Comparator.naturalOrder()).orElse(-1L);

        long millisSinceLastPurchase = System.currentTimeMillis() - latestTimePurchased;

        if (millisSinceLastPurchase > 0 && millisSinceLastPurchase < JUST_BOUGHT_FOOD_VALIDITY) {
            return calculateSameFoodTimePenalty(millisSinceLastPurchase).doubleValue();
        }
        return 0;
    }

    private static BigDecimal calculateSameFoodTimePenalty(long timeInMillis) {
        BigDecimal scale = BigDecimal.ONE.divide(new BigDecimal(1728000), 10, RoundingMode.HALF_DOWN);
        BigDecimal intercept = BigDecimal.TEN;
        return new BigDecimal(timeInMillis).multiply(scale).sqrt(new MathContext(10)).subtract(intercept);
    }

    @Override
    public Comparator<Food> getRecommendationComparator() {
        if (isInUse()) {
            return comparator;
        } else {
            return DEFAULT_COMPARATOR;
        }
    }

    @Override
    public Predicate<Food> getRecommendationPredicate() {
        if (isInUse()) {
            return predicate;
        } else {
            return DEFAULT_PREDICATE;
        }
    }

    @Override
    public boolean isInUse() {
        return isInUse;
    }

    @Override
    public void setInUse(boolean isInUse) {
        this.isInUse = isInUse;
    }

    @Override
    public void addLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        userRecommendations.addLikes(categoryList, tagList, locationList);
    }

    @Override
    public void addDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        userRecommendations.addDislikes(categoryList, tagList, locationList);
    }

    @Override
    public void removeLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        userRecommendations.removeLikes(categoryList, tagList, locationList);
    }

    @Override
    public void removeDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        userRecommendations.removeDislikes(categoryList, tagList, locationList);
    }

    @Override
    public void clearLikes() {
        userRecommendations.clearLikes();
    }

    @Override
    public void clearDislikes() {
        userRecommendations.clearDislikes();
    }

    @Override
    public UserRecommendations getUserRecommendations() {
        return userRecommendations;
    }

    @Override
    public void setUserRecommendations(UserRecommendations userRecommendations) {
        requireNonNull(userRecommendations);
        this.userRecommendations = userRecommendations;
    }

    @Override
    public void updatePurchaseHistory(ObservableList<Purchase> purchaseHistory) {
        requireNonNull(purchaseHistory);
        this.purchaseHistory = purchaseHistory;
    }

    /**
     * Update budget.
     *
     * @param budget The specified budget
     */
    @Override
    public void updateBudget(BigDecimal budget) {
        requireNonNull(budget);

        this.budget = budget;
        if (budget.equals(BigDecimal.ZERO) || daysToExpire == 0) {
            this.dailyBudget = BigDecimal.ZERO;
        } else {
            this.dailyBudget = budget.divide(new BigDecimal(daysToExpire * 2), 2, RoundingMode.HALF_DOWN);
        }
    }

    @Override
    public BigDecimal getBudget() {
        return budget;
    }

    @Override
    public BigDecimal getDailyBudget() {
        return dailyBudget;
    }

    @Override
    public int getDaysToExpire() {
        return daysToExpire;
    }

    @Override
    public void updateDaysToExpire(int daysToExpire) {
        requireNonNull(daysToExpire);
        this.daysToExpire = daysToExpire;
    }

}
