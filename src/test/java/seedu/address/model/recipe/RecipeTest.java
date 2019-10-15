package seedu.address.model.recipe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INGREDIENT_BURGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BURGER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecipes.BURGER;
import static seedu.address.testutil.TypicalRecipes.MILO;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.RecipeBuilder;

public class RecipeTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Recipe recipe = new RecipeBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> recipe.getIngredients().remove(0));
    }

    @Test
    public void isSameRecipe() {
        // same object -> returns true
        assertTrue(MILO.isSameRecipe(MILO));

        // null -> returns false
        assertFalse(MILO.isSameRecipe(null));

        // different name -> returns false
        Recipe editedAlice = new RecipeBuilder(MILO).withName(VALID_NAME_BURGER).build();
        assertFalse(MILO.isSameRecipe(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new RecipeBuilder(MILO)
                .withIngredients(VALID_INGREDIENT_BURGER).build();
        assertTrue(MILO.isSameRecipe(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Recipe aliceCopy = new RecipeBuilder(MILO).build();
        assertTrue(MILO.equals(aliceCopy));

        // same object -> returns true
        assertTrue(MILO.equals(MILO));

        // null -> returns false
        assertFalse(MILO.equals(null));

        // different type -> returns false
        assertFalse(MILO.equals(5));

        // different recipe -> returns false
        assertFalse(MILO.equals(BURGER));

        // different name -> returns false
        Recipe editedAlice = new RecipeBuilder(MILO).withName(VALID_NAME_BURGER).build();
        assertFalse(MILO.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new RecipeBuilder(MILO).withIngredients(VALID_INGREDIENT_BURGER).build();
        assertFalse(MILO.equals(editedAlice));
    }
}
