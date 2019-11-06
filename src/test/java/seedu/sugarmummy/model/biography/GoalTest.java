package seedu.sugarmummy.model.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GoalTest {

    @Test
    public void isValidGoal_emptyString() {
        assertTrue(Goal.isValidGoal(""));
    }

    @Test
    public void isValidGoal_firstExampleGoal() {
        assertTrue(Goal.isValidGoal("Lose 10kg by 2019-12-05"));
    }

    @Test
    public void isValidGoal_secondExampleGoal() {
        assertTrue(Goal.isValidGoal("Eat 2 servings of vegetables today"));
    }

    @Test
    public void testToString() {
        assertEquals("Lose 10kg by 2019-12-05", (new Goal("Lose 10kg by 2019-12-05")).toString());
    }

    @Test
    public void testEquals_sameGoal() {
        assertEquals(new Goal("Lose 10kg by 2019-12-05"),
                new Goal("Lose 10kg by 2019-12-05"));
    }

    @Test
    public void testEquals_differentGoal() {
        assertNotEquals(new Goal("Lose 10kg by 2019-12-05"),
                new Goal("Lose 5kg by 2019-12-05"));
    }

    @Test
    public void testEquals_object() {
        assertNotEquals(new Object(),
                new Goal("Lose 10kg by 2019-12-05"));
    }

    @Test
    public void testHashCode_sameGoal() {
        assertEquals(new Goal("Lose 10kg by 2019-12-05").hashCode(),
                new Goal("Lose 10kg by 2019-12-05").hashCode());
    }

    @Test
    public void testHashCode_differentGoal() {
        assertNotEquals(new Goal("Lose 10kg by 2019-12-05").hashCode(),
                new Goal("Lose 5kg by 2019-12-05").hashCode());
    }

    @Test
    public void testHashCode_object() {
        assertNotEquals(new Object().hashCode(),
                new Goal("Lose 10kg by 2019-12-05").hashCode());
    }
}
