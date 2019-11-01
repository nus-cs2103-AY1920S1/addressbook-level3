package seedu.savenus.model.recommend;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Tag;
import seedu.savenus.model.purchase.Purchase;

//@@author jon-chua

/**
 * The API of the Recommender component.
 */
public interface Recommender {
    /**
     * Calculate the recommendation value of a given {@code Food}.
     *
     * @param food The food
     * @return The recommendation value of the food
     * @throws NullPointerException if {@code food} is null.
     */
    double calculateRecommendation(Food food);

    /**
     * Gets the recommendation comparator.
     */
    Comparator<Food> getRecommendationComparator();

    /**
     * Gets the recommendation predicate.
     */
    Predicate<Food> getRecommendationPredicate();

    /**
     * Returns if the recommendation system is in use.
     */
    boolean isInUse();

    /**
     * Sets the in use value of the recommendation system.
     */
    void setInUse(boolean isInUse);

    /**
     * Add likes to the recommendation system.
     *
     * @param categoryList The list of categories to add
     * @param tagList      The list of tags to add
     * @param locationList The list of locations to add
     */
    void addLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList);

    /**
     * Add dislikes to the recommendation system.
     *
     * @param categoryList The list of categories to add
     * @param tagList      The list of tags to add
     * @param locationList The list of locations to add
     */
    void addDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList);

    /**
     * Remove likes from the recommendation system.
     *
     * @param categoryList The list of categories to remove
     * @param tagList      The list of tags to remove
     * @param locationList The list of locations to remove
     */
    void removeLikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList);

    /**
     * Remove dislikes from the recommendation system.
     *
     * @param categoryList The list of categories to remove
     * @param tagList      The list of tags to remove
     * @param locationList The list of locations to remove
     */
    void removeDislikes(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList);

    /**
     * Clear likes from the recommendation system.
     */
    void clearLikes();

    /**
     * Clear dislikes from the recommendation system.
     */
    void clearDislikes();

    /**
     * Gets the current user recommendations.
     *
     * @return The user's recommendations
     */
    UserRecommendations getUserRecommendations();

    /**
     * Sets the current user recommendations.
     *
     * @param userRecommendations The user's recommendations
     * @throws NullPointerException if {@code userRecommendations} is null.
     */
    void setUserRecommendations(UserRecommendations userRecommendations);

    /**
     * Updates the purchase history.
     *
     * @param purchaseHistory The user's purchase history
     * @throws NullPointerException if {@code purchaseHistory} is null.
     */
    void updatePurchaseHistory(ObservableList<Purchase> purchaseHistory);

    /**
     * Update the user's budget.
     *
     * @param budget The user's budget
     * @throws NullPointerException if {@code budget} is null.
     */
    void updateBudget(BigDecimal budget);

    /**
     * Gets the user's budget.
     *
     * @return The user's budget
     */
    BigDecimal getBudget();

    /**
     * Gets the user's daily budget.
     *
     * @return The user's daily budget
     */
    BigDecimal getDailyBudget();

    /**
     * Gets the days to expiry of user's budget.
     *
     * @return The number of days to expiry of user's budget.
     */
    int getDaysToExpire();

    /**
     * Update the user's days to expiry of budget.
     *
     * @param daysToExpire The days to expiry of budget
     */
    void updateDaysToExpire(int daysToExpire);
}
