package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.model.recommend.RecommendationSystem;

public class RecommendationSystemTest {
    private RecommendationSystem recommendationSystem;

    @BeforeEach
    public void setUp() {
        recommendationSystem = new RecommendationSystem();
    }

    @Test
    public void calculateRecommendation_test() {
        int recommendationValue = recommendationSystem.calculateRecommendation(CARBONARA);
        assertEquals(recommendationValue, 0);
    }
}
