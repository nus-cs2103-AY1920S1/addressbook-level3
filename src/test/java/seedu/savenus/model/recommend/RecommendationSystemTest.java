package seedu.savenus.model.recommend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_NAME_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_OPENING_HOURS_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_PRICE_CHICKEN_RICE;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_RESTRICTIONS_CHICKEN_RICE;
import static seedu.savenus.model.recommend.RecommendationSystem.DISLIKED_CATEGORY_WEIGHT;
import static seedu.savenus.model.recommend.RecommendationSystem.DISLIKED_LOCATION_WEIGHT;
import static seedu.savenus.model.recommend.RecommendationSystem.DISLIKED_TAG_PENALTY_HIGH;
import static seedu.savenus.model.recommend.RecommendationSystem.DISLIKED_TAG_PENALTY_HIGH_NUM;
import static seedu.savenus.model.recommend.RecommendationSystem.DISLIKED_TAG_PENALTY_LOW;
import static seedu.savenus.model.recommend.RecommendationSystem.DISLIKED_TAG_PENALTY_LOW_NUM;
import static seedu.savenus.model.recommend.RecommendationSystem.DISLIKED_TAG_PENALTY_MED;
import static seedu.savenus.model.recommend.RecommendationSystem.DISLIKED_TAG_PENALTY_MED_NUM;
import static seedu.savenus.model.recommend.RecommendationSystem.DISLIKED_TAG_WEIGHT;
import static seedu.savenus.model.recommend.RecommendationSystem.HISTORY_CATEGORY_WEIGHT;
import static seedu.savenus.model.recommend.RecommendationSystem.HISTORY_LOCATION_WEIGHT;
import static seedu.savenus.model.recommend.RecommendationSystem.HISTORY_TAG_WEIGHT;
import static seedu.savenus.model.recommend.RecommendationSystem.IDENTICAL_FOOD_BONUS_HIGH;
import static seedu.savenus.model.recommend.RecommendationSystem.IDENTICAL_FOOD_BONUS_HIGH_NUM;
import static seedu.savenus.model.recommend.RecommendationSystem.IDENTICAL_FOOD_BONUS_LOW;
import static seedu.savenus.model.recommend.RecommendationSystem.IDENTICAL_FOOD_BONUS_LOW_NUM;
import static seedu.savenus.model.recommend.RecommendationSystem.IDENTICAL_FOOD_BONUS_MED;
import static seedu.savenus.model.recommend.RecommendationSystem.IDENTICAL_FOOD_BONUS_MED_NUM;
import static seedu.savenus.model.recommend.RecommendationSystem.JUST_BOUGHT_FOOD_VALIDITY;
import static seedu.savenus.model.recommend.RecommendationSystem.LIKED_CATEGORY_WEIGHT;
import static seedu.savenus.model.recommend.RecommendationSystem.LIKED_LOCATION_WEIGHT;
import static seedu.savenus.model.recommend.RecommendationSystem.LIKED_TAG_BONUS_HIGH;
import static seedu.savenus.model.recommend.RecommendationSystem.LIKED_TAG_BONUS_HIGH_NUM;
import static seedu.savenus.model.recommend.RecommendationSystem.LIKED_TAG_BONUS_LOW;
import static seedu.savenus.model.recommend.RecommendationSystem.LIKED_TAG_BONUS_LOW_NUM;
import static seedu.savenus.model.recommend.RecommendationSystem.LIKED_TAG_BONUS_MED;
import static seedu.savenus.model.recommend.RecommendationSystem.LIKED_TAG_BONUS_MED_NUM;
import static seedu.savenus.model.recommend.RecommendationSystem.LIKED_TAG_WEIGHT;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;

import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.OpeningHours;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.food.Restrictions;
import seedu.savenus.model.food.Tag;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.purchase.PurchaseHistory;
import seedu.savenus.model.purchase.TimeOfPurchase;

//@@author jon-chua
/**
 * Tests for RecommendationSystem
 */
public class RecommendationSystemTest {

    private static final String NO_RECENT_FOOD_PENALTY = String.valueOf(JUST_BOUGHT_FOOD_VALIDITY + 1000);
    private static final double EPSILON = 1E-6;

    @BeforeEach
    public void setUp() {
        RecommendationSystem.getInstance().setUserRecommendations(new UserRecommendations());
        RecommendationSystem.getInstance().updatePurchaseHistory(FXCollections.observableArrayList());
        RecommendationSystem.getInstance().clearLikes();
        RecommendationSystem.getInstance().clearDislikes();
    }

    @Test
    public void calculateRecommendation_test() {
        double recommendationValue = RecommendationSystem.getInstance().calculateRecommendation(CARBONARA);
        assertEquals(recommendationValue, 0, EPSILON);
    }

    @Test
    public void likedCategoryMatch() {
        RecommendationBuilder rec = new RecommendationBuilder.Builder().matchLikedCategory(true).numOfLikedTags(0)
                .matchLikedLocation(false).matchDislikedCategory(false).numOfDislikedTags(0)
                .matchDislikedLocation(false).purchaseHistoryNum(0).mostRecentPurchaseTime(NO_RECENT_FOOD_PENALTY)
                .isLike(true).build();

        RecommendationSystem.getInstance().setUserRecommendations(rec.getUserRecommendations());
        RecommendationSystem.getInstance().updatePurchaseHistory(rec.getPurchaseHistory().getPurchaseHistoryList());

        double value = RecommendationSystem.getInstance().calculateRecommendation(rec.getFood());
        assertEquals(LIKED_CATEGORY_WEIGHT, value, EPSILON);
    }

    @Test
    public void likedTagsMatch_low() {
        RecommendationBuilder rec = new RecommendationBuilder.Builder().matchLikedCategory(false)
                .numOfLikedTags(LIKED_TAG_BONUS_LOW_NUM).matchLikedLocation(false).matchDislikedCategory(false)
                .numOfDislikedTags(0).matchDislikedLocation(false).purchaseHistoryNum(0)
                .mostRecentPurchaseTime(NO_RECENT_FOOD_PENALTY).isLike(true).build();

        RecommendationSystem.getInstance().setUserRecommendations(rec.getUserRecommendations());
        RecommendationSystem.getInstance().updatePurchaseHistory(rec.getPurchaseHistory().getPurchaseHistoryList());

        double value = RecommendationSystem.getInstance().calculateRecommendation(rec.getFood());
        assertEquals(LIKED_TAG_BONUS_LOW + LIKED_TAG_BONUS_LOW_NUM * LIKED_TAG_WEIGHT, value, EPSILON);
    }

    @Test
    public void likedTagsMatch_med() {
        RecommendationBuilder rec = new RecommendationBuilder.Builder().matchLikedCategory(false)
                .numOfLikedTags(LIKED_TAG_BONUS_MED_NUM).matchLikedLocation(false).matchDislikedCategory(false)
                .numOfDislikedTags(0).matchDislikedLocation(false).purchaseHistoryNum(0)
                .mostRecentPurchaseTime(NO_RECENT_FOOD_PENALTY).isLike(true).build();

        RecommendationSystem.getInstance().setUserRecommendations(rec.getUserRecommendations());
        RecommendationSystem.getInstance().updatePurchaseHistory(rec.getPurchaseHistory().getPurchaseHistoryList());

        double value = RecommendationSystem.getInstance().calculateRecommendation(rec.getFood());
        assertEquals(LIKED_TAG_BONUS_MED + LIKED_TAG_BONUS_MED_NUM * LIKED_TAG_WEIGHT, value, EPSILON);
    }

    @Test
    public void likedTagsMatch_high() {
        RecommendationBuilder rec = new RecommendationBuilder.Builder().matchLikedCategory(false)
                .numOfLikedTags(LIKED_TAG_BONUS_HIGH_NUM).matchLikedLocation(false).matchDislikedCategory(false)
                .numOfDislikedTags(0).matchDislikedLocation(false).purchaseHistoryNum(0)
                .mostRecentPurchaseTime(NO_RECENT_FOOD_PENALTY).isLike(true).build();

        RecommendationSystem.getInstance().setUserRecommendations(rec.getUserRecommendations());
        RecommendationSystem.getInstance().updatePurchaseHistory(rec.getPurchaseHistory().getPurchaseHistoryList());

        double value = RecommendationSystem.getInstance().calculateRecommendation(rec.getFood());
        assertEquals(LIKED_TAG_BONUS_HIGH + LIKED_TAG_BONUS_HIGH_NUM * LIKED_TAG_WEIGHT, value, EPSILON);
    }

    @Test
    public void likedLocationMatch() {
        RecommendationBuilder rec = new RecommendationBuilder.Builder().matchLikedCategory(false).numOfLikedTags(0)
                .matchLikedLocation(true).matchDislikedCategory(false).numOfDislikedTags(0)
                .matchDislikedLocation(false).purchaseHistoryNum(0).mostRecentPurchaseTime(NO_RECENT_FOOD_PENALTY)
                .isLike(true).build();

        RecommendationSystem.getInstance().setUserRecommendations(rec.getUserRecommendations());
        RecommendationSystem.getInstance().updatePurchaseHistory(rec.getPurchaseHistory().getPurchaseHistoryList());

        double value = RecommendationSystem.getInstance().calculateRecommendation(rec.getFood());
        assertEquals(LIKED_LOCATION_WEIGHT, value, EPSILON);
    }

    @Test
    public void dislikedCategoryMatch() {
        RecommendationBuilder rec = new RecommendationBuilder.Builder().matchLikedCategory(false).numOfLikedTags(0)
                .matchLikedLocation(false).matchDislikedCategory(true).numOfDislikedTags(0)
                .matchDislikedLocation(false).purchaseHistoryNum(0).mostRecentPurchaseTime(NO_RECENT_FOOD_PENALTY)
                .isLike(false).build();

        RecommendationSystem.getInstance().setUserRecommendations(rec.getUserRecommendations());
        RecommendationSystem.getInstance().updatePurchaseHistory(rec.getPurchaseHistory().getPurchaseHistoryList());

        double value = RecommendationSystem.getInstance().calculateRecommendation(rec.getFood());
        assertEquals(DISLIKED_CATEGORY_WEIGHT, value, EPSILON);
    }

    @Test
    public void dislikedTagsMatch_low() {
        RecommendationBuilder rec = new RecommendationBuilder.Builder().matchLikedCategory(false)
                .numOfLikedTags(0).matchLikedLocation(false).matchDislikedCategory(false)
                .numOfDislikedTags(DISLIKED_TAG_PENALTY_LOW_NUM).matchDislikedLocation(false).purchaseHistoryNum(0)
                .mostRecentPurchaseTime(NO_RECENT_FOOD_PENALTY).isLike(false).build();

        RecommendationSystem.getInstance().setUserRecommendations(rec.getUserRecommendations());
        RecommendationSystem.getInstance().updatePurchaseHistory(rec.getPurchaseHistory().getPurchaseHistoryList());

        double value = RecommendationSystem.getInstance().calculateRecommendation(rec.getFood());
        assertEquals(DISLIKED_TAG_PENALTY_LOW + DISLIKED_TAG_PENALTY_LOW_NUM * DISLIKED_TAG_WEIGHT, value);
    }

    @Test
    public void dislikedTagsMatch_med() {
        RecommendationBuilder rec = new RecommendationBuilder.Builder().matchLikedCategory(false)
                .numOfLikedTags(0).matchLikedLocation(false).matchDislikedCategory(false)
                .numOfDislikedTags(DISLIKED_TAG_PENALTY_MED_NUM).matchDislikedLocation(false).purchaseHistoryNum(0)
                .mostRecentPurchaseTime(NO_RECENT_FOOD_PENALTY).isLike(false).build();

        RecommendationSystem.getInstance().setUserRecommendations(rec.getUserRecommendations());
        RecommendationSystem.getInstance().updatePurchaseHistory(rec.getPurchaseHistory().getPurchaseHistoryList());

        double value = RecommendationSystem.getInstance().calculateRecommendation(rec.getFood());
        assertEquals(DISLIKED_TAG_PENALTY_MED + DISLIKED_TAG_PENALTY_MED_NUM * DISLIKED_TAG_WEIGHT, value, EPSILON);
    }

    @Test
    public void dislikedTagsMatch_high() {
        RecommendationBuilder rec = new RecommendationBuilder.Builder().matchLikedCategory(false)
                .numOfLikedTags(0).matchLikedLocation(false).matchDislikedCategory(false)
                .numOfDislikedTags(DISLIKED_TAG_PENALTY_HIGH_NUM).matchDislikedLocation(false).purchaseHistoryNum(0)
                .mostRecentPurchaseTime(NO_RECENT_FOOD_PENALTY).isLike(false).build();

        RecommendationSystem.getInstance().setUserRecommendations(rec.getUserRecommendations());
        RecommendationSystem.getInstance().updatePurchaseHistory(rec.getPurchaseHistory().getPurchaseHistoryList());

        double value = RecommendationSystem.getInstance().calculateRecommendation(rec.getFood());
        assertEquals(DISLIKED_TAG_PENALTY_HIGH + DISLIKED_TAG_PENALTY_HIGH_NUM * DISLIKED_TAG_WEIGHT, value, EPSILON);
    }

    @Test
    public void dislikedLocationMatch() {
        RecommendationBuilder rec = new RecommendationBuilder.Builder().matchLikedCategory(false).numOfLikedTags(0)
                .matchLikedLocation(false).matchDislikedCategory(false).numOfDislikedTags(0)
                .matchDislikedLocation(true).purchaseHistoryNum(0).mostRecentPurchaseTime(NO_RECENT_FOOD_PENALTY)
                .isLike(false).build();

        RecommendationSystem.getInstance().setUserRecommendations(rec.getUserRecommendations());
        RecommendationSystem.getInstance().updatePurchaseHistory(rec.getPurchaseHistory().getPurchaseHistoryList());

        double value = RecommendationSystem.getInstance().calculateRecommendation(rec.getFood());
        assertEquals(DISLIKED_LOCATION_WEIGHT, value);
    }

    @Test
    public void numOfSimilarPurchase_low() {
        RecommendationBuilder rec = new RecommendationBuilder.Builder().matchLikedCategory(false)
                .numOfLikedTags(0).matchLikedLocation(false).matchDislikedCategory(false)
                .numOfDislikedTags(0).matchDislikedLocation(false).purchaseHistoryNum(IDENTICAL_FOOD_BONUS_LOW_NUM)
                .mostRecentPurchaseTime(NO_RECENT_FOOD_PENALTY).isLike(true).build();

        RecommendationSystem.getInstance().setUserRecommendations(rec.getUserRecommendations());
        RecommendationSystem.getInstance().updatePurchaseHistory(rec.getPurchaseHistory().getPurchaseHistoryList());

        double value = RecommendationSystem.getInstance().calculateRecommendation(rec.getFood());
        assertEquals(IDENTICAL_FOOD_BONUS_LOW + HISTORY_LOCATION_WEIGHT * IDENTICAL_FOOD_BONUS_LOW_NUM
                + HISTORY_CATEGORY_WEIGHT * IDENTICAL_FOOD_BONUS_LOW_NUM , value, EPSILON);
    }

    @Test
    public void numOfSimilarPurchase_med() {
        RecommendationBuilder rec = new RecommendationBuilder.Builder().matchLikedCategory(false)
                .numOfLikedTags(0).matchLikedLocation(false).matchDislikedCategory(false)
                .numOfDislikedTags(0).matchDislikedLocation(false).purchaseHistoryNum(IDENTICAL_FOOD_BONUS_MED_NUM)
                .mostRecentPurchaseTime(NO_RECENT_FOOD_PENALTY).isLike(true).build();

        RecommendationSystem.getInstance().setUserRecommendations(rec.getUserRecommendations());
        RecommendationSystem.getInstance().updatePurchaseHistory(rec.getPurchaseHistory().getPurchaseHistoryList());

        double value = RecommendationSystem.getInstance().calculateRecommendation(rec.getFood());
        assertEquals(IDENTICAL_FOOD_BONUS_MED + HISTORY_LOCATION_WEIGHT * IDENTICAL_FOOD_BONUS_MED_NUM
                + HISTORY_CATEGORY_WEIGHT * IDENTICAL_FOOD_BONUS_MED_NUM , value, EPSILON);
    }

    @Test
    public void numOfSimilarPurchase_high() {
        RecommendationBuilder rec = new RecommendationBuilder.Builder().matchLikedCategory(false)
                .numOfLikedTags(0).matchLikedLocation(false).matchDislikedCategory(false)
                .numOfDislikedTags(0).matchDislikedLocation(false).purchaseHistoryNum(IDENTICAL_FOOD_BONUS_HIGH_NUM)
                .mostRecentPurchaseTime(NO_RECENT_FOOD_PENALTY).isLike(true).build();

        RecommendationSystem.getInstance().setUserRecommendations(rec.getUserRecommendations());
        RecommendationSystem.getInstance().updatePurchaseHistory(rec.getPurchaseHistory().getPurchaseHistoryList());

        double value = RecommendationSystem.getInstance().calculateRecommendation(rec.getFood());
        assertEquals(IDENTICAL_FOOD_BONUS_HIGH + HISTORY_LOCATION_WEIGHT * IDENTICAL_FOOD_BONUS_HIGH_NUM
                + HISTORY_CATEGORY_WEIGHT * IDENTICAL_FOOD_BONUS_HIGH_NUM , value, EPSILON);
    }

    @Test
    public void numOfSimilarPurchase_highWithMatchingTags() {
        RecommendationBuilder rec = new RecommendationBuilder.Builder().matchLikedCategory(false)
                .numOfLikedTags(LIKED_TAG_BONUS_HIGH_NUM).matchLikedLocation(false).matchDislikedCategory(false)
                .numOfDislikedTags(0).matchDislikedLocation(false).purchaseHistoryNum(IDENTICAL_FOOD_BONUS_HIGH_NUM)
                .mostRecentPurchaseTime(NO_RECENT_FOOD_PENALTY).isLike(true).build();

        RecommendationSystem.getInstance().setUserRecommendations(rec.getUserRecommendations());
        RecommendationSystem.getInstance().updatePurchaseHistory(rec.getPurchaseHistory().getPurchaseHistoryList());

        double value = RecommendationSystem.getInstance().calculateRecommendation(rec.getFood());
        assertEquals(IDENTICAL_FOOD_BONUS_HIGH + LIKED_TAG_BONUS_HIGH
                + LIKED_TAG_WEIGHT * LIKED_TAG_BONUS_HIGH_NUM
                + HISTORY_TAG_WEIGHT * IDENTICAL_FOOD_BONUS_HIGH_NUM * LIKED_TAG_BONUS_HIGH_NUM
                + HISTORY_LOCATION_WEIGHT * IDENTICAL_FOOD_BONUS_HIGH_NUM
                + HISTORY_CATEGORY_WEIGHT * IDENTICAL_FOOD_BONUS_HIGH_NUM , value, EPSILON);
    }

}

/**
 * RecommendationBuilder helper class.
 */
class RecommendationBuilder {
    private static final String MATCH_LIKE = "matchlike";
    private static final String MATCH_DISLIKE = "matchdislike";

    private PurchaseHistory purchaseHistory;
    private UserRecommendations userRecs;
    private Food food;

    /**
     * Builder class to build the RecommendationBuilder object.
     */
    public static class Builder {
        private static final char LIKED_CHAR = 'a';
        private static final char DISLIKED_CHAR = 'b';

        private Set<Category> likedCategories = new HashSet<>();
        private Set<Tag> likedTags = new HashSet<>();
        private Set<Location> likedLocations = new HashSet<>();

        private Set<Category> dislikedCategories = new HashSet<>();
        private Set<Tag> dislikedTags = new HashSet<>();
        private Set<Location> dislikedLocations = new HashSet<>();

        private boolean isLike;
        private int purchaseHistoryNum;
        private String mostRecentPurchaseTime;

        public Builder() {}

        /**
         * Match liked category.
         */
        public Builder matchLikedCategory(boolean isMatch) {
            if (!isMatch) {
                return this;
            }
            likedCategories.add(new Category(MATCH_LIKE));
            return this;
        }

        /**
         * Num of liked tags.
         */
        public Builder numOfLikedTags(int numOfLikedTags) {
            StringBuilder str = new StringBuilder();

            for (int i = 0; i < numOfLikedTags; i++) {
                str.append(LIKED_CHAR);
                likedTags.add(new Tag(str.toString()));
            }
            return this;
        }

        /**
         * Match liked location.
         */
        public Builder matchLikedLocation(boolean isMatch) {
            if (!isMatch) {
                return this;
            }
            likedLocations.add(new Location(MATCH_LIKE));
            return this;
        }

        /**
         * Match disliked category.
         */
        public Builder matchDislikedCategory(boolean isMatch) {
            if (!isMatch) {
                return this;
            }
            dislikedCategories.add(new Category(MATCH_DISLIKE));
            return this;
        }

        /**
         * Num of disliked tags.
         */
        public Builder numOfDislikedTags(int numOfLikedTags) {
            StringBuilder str = new StringBuilder();

            for (int i = 0; i < numOfLikedTags; i++) {
                str.append(DISLIKED_CHAR);
                dislikedTags.add(new Tag(str.toString()));
            }
            return this;
        }

        /**
         * Match disliked location.
         */
        public Builder matchDislikedLocation(boolean isMatch) {
            if (!isMatch) {
                return this;
            }
            dislikedLocations.add(new Location(MATCH_DISLIKE));
            return this;
        }

        public Builder purchaseHistoryNum(int purchaseHistoryNum) {
            this.purchaseHistoryNum = purchaseHistoryNum;
            return this;
        }

        public Builder mostRecentPurchaseTime(String mostRecentPurchaseTime) {
            this.mostRecentPurchaseTime = mostRecentPurchaseTime;
            return this;
        }

        public Builder isLike(boolean isLike) {
            this.isLike = isLike;
            return this;
        }

        public RecommendationBuilder build() {
            return new RecommendationBuilder(this);
        }
    }

    private RecommendationBuilder(Builder builder) {
        requireAllNonNull(builder.likedCategories, builder.likedTags, builder.likedLocations,
                builder.dislikedCategories, builder.dislikedTags, builder.dislikedLocations,
                builder.purchaseHistoryNum, builder.mostRecentPurchaseTime);

        userRecs = new UserRecommendations(builder.likedCategories, builder.likedTags, builder.likedLocations,
                builder.dislikedCategories, builder.dislikedTags, builder.dislikedLocations);

        if (builder.isLike) {
            food = new Food(new Name(VALID_NAME_CHICKEN_RICE), new Price(VALID_PRICE_CHICKEN_RICE),
                    new Description(VALID_DESCRIPTION_CHICKEN_RICE), new Category(MATCH_LIKE), builder.likedTags,
                    new Location(MATCH_LIKE), new OpeningHours(VALID_OPENING_HOURS_CHICKEN_RICE),
                    new Restrictions(VALID_RESTRICTIONS_CHICKEN_RICE));
        } else {
            food = new Food(new Name(VALID_NAME_CHICKEN_RICE), new Price(VALID_PRICE_CHICKEN_RICE),
                    new Description(VALID_DESCRIPTION_CHICKEN_RICE), new Category(MATCH_DISLIKE), builder.dislikedTags,
                    new Location(MATCH_DISLIKE), new OpeningHours(VALID_OPENING_HOURS_CHICKEN_RICE),
                    new Restrictions(VALID_RESTRICTIONS_CHICKEN_RICE));
        }

        purchaseHistory = new PurchaseHistory();
        for (int i = 0; i < builder.purchaseHistoryNum; i++) {
            purchaseHistory.addPurchase(new Purchase(food, new TimeOfPurchase(builder.mostRecentPurchaseTime)));
        }
    }

    public UserRecommendations getUserRecommendations() {
        return userRecs;
    }

    public Food getFood() {
        return food;
    }

    public PurchaseHistory getPurchaseHistory() {
        return purchaseHistory;
    }
}
