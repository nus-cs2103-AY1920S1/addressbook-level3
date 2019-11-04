package dukecooks.model.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.recipe.components.RecipeName;
import dukecooks.testutil.Assert;

public class RecipeNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new RecipeName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new RecipeName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> RecipeName.isValidName(null));

        // invalid name
        assertFalse(RecipeName.isValidName("")); // empty string
        assertFalse(RecipeName.isValidName(" ")); // spaces only
        assertFalse(RecipeName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(RecipeName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(RecipeName.isValidName("peter jack")); // alphabets only
        assertTrue(RecipeName.isValidName("12345")); // numbers only
        assertTrue(RecipeName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(RecipeName.isValidName("Capital Tan")); // with capital letters
        assertTrue(RecipeName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void testRecipeNameToString() {
        RecipeName recipeName = new RecipeName("A");
        String expected = "A";
        assertEquals(recipeName.toString(), expected);
    }
}
