package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;

import org.junit.jupiter.api.Test;

import seedu.savenus.model.recommend.RecommendationSystem;

public class RecommendationSystemTest {

    @Test
    public void calculateRecommendation_test() {
        double recommendationValue = RecommendationSystem.getInstance().calculateRecommendation(CARBONARA);
        assertEquals(recommendationValue, 0);
    }
}
