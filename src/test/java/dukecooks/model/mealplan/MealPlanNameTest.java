package dukecooks.model.mealplan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.mealplan.components.MealPlanName;
import dukecooks.testutil.Assert;

public class MealPlanNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MealPlanName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MealPlanName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> MealPlanName.isValidName(null));

        // invalid name
        assertFalse(MealPlanName.isValidName("")); // empty string
        assertFalse(MealPlanName.isValidName(" ")); // spaces only
        assertFalse(MealPlanName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(MealPlanName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(MealPlanName.isValidName("peter jack")); // alphabets only
        assertTrue(MealPlanName.isValidName("12345")); // numbers only
        assertTrue(MealPlanName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(MealPlanName.isValidName("Capital Tan")); // with capital letters
        assertTrue(MealPlanName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void testMealPlanNameHashCode() {
        MealPlanName name1 = new MealPlanName("A");
        MealPlanName name2 = new MealPlanName("A");
        assertEquals(name1.hashCode(), name2.hashCode());
    }
}
