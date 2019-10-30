package dukecooks.model.recipe;

import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.recipe.components.Protein;

public class ProteinTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Protein(null));
    }

    @Test
    public void constructor_invalidProtein_throwsIllegalArgumentException() {
        String invalidProtein = "";
        assertThrows(IllegalArgumentException.class, () -> new Protein(invalidProtein));
    }

    @Test
    public void isValidProtein() {
        // null protein number
        assertThrows(NullPointerException.class, () -> Protein.isValidProtein(null));

        // invalid protein numbers
        assertFalse(Protein.isValidProtein("")); // empty string
        assertFalse(Protein.isValidProtein(" ")); // spaces only
        assertFalse(Protein.isValidProtein("protein")); // non-numeric
        assertFalse(Protein.isValidProtein("9011p041")); // alphabets within digits
        assertFalse(Protein.isValidProtein("9312 1534")); // spaces within digits

        // valid protein numbers
        assertTrue(Protein.isValidProtein("911")); // exactly 3 numbers
        assertTrue(Protein.isValidProtein("93121534"));
        assertTrue(Protein.isValidProtein("124293842033123")); // long protein numbers
    }
}
