package dukecooks.model.recipe;

import static dukecooks.testutil.recipe.TypicalRecipes.BURGER;
import static dukecooks.testutil.recipe.TypicalRecipes.MILO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.recipe.components.UniqueRecipeList;
import dukecooks.model.recipe.exceptions.DuplicateRecipeException;
import dukecooks.model.recipe.exceptions.RecipeNotFoundException;
import dukecooks.testutil.Assert;
import dukecooks.testutil.recipe.RecipeBuilder;

public class UniqueRecipeBookListTest {

    private final UniqueRecipeList uniqueRecipeList = new UniqueRecipeList();

    @Test
    public void contains_nullRecipe_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRecipeList.contains(null));
    }

    @Test
    public void contains_recipeNotInList_returnsFalse() {
        assertFalse(uniqueRecipeList.contains(MILO));
    }

    @Test
    public void contains_recipeInList_returnsTrue() {
        uniqueRecipeList.add(MILO);
        assertTrue(uniqueRecipeList.contains(MILO));
    }

    @Test
    public void contains_recipeWithSameIdentityFieldsInList_returnsTrue() {
        uniqueRecipeList.add(MILO);
        Recipe editedAlice = new RecipeBuilder(MILO).withIngredients(CommandTestUtil.VALID_INGREDIENT_BURGER)
                .build();
        assertTrue(uniqueRecipeList.contains(editedAlice));
    }

    @Test
    public void add_nullRecipe_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRecipeList.add(null));
    }

    @Test
    public void add_duplicateRecipe_throwsDuplicateRecipeException() {
        uniqueRecipeList.add(MILO);
        Assert.assertThrows(DuplicateRecipeException.class, () -> uniqueRecipeList.add(MILO));
    }

    @Test
    public void setRecipe_nullTargetRecipe_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRecipeList.setRecipe(null, MILO));
    }

    @Test
    public void setRecipe_nullEditedRecipe_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRecipeList.setRecipe(MILO, null));
    }

    @Test
    public void setRecipe_targetRecipeNotInList_throwsRecipeNotFoundException() {
        Assert.assertThrows(RecipeNotFoundException.class, () -> uniqueRecipeList.setRecipe(MILO, MILO));
    }

    @Test
    public void setRecipe_editedRecipeIsSameRecipe_success() {
        uniqueRecipeList.add(MILO);
        uniqueRecipeList.setRecipe(MILO, MILO);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(MILO);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipe_editedRecipeHasSameIdentity_success() {
        uniqueRecipeList.add(MILO);
        Recipe editedAlice = new RecipeBuilder(MILO).withIngredients(CommandTestUtil.VALID_INGREDIENT_BURGER)
                .build();
        uniqueRecipeList.setRecipe(MILO, editedAlice);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(editedAlice);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipe_editedRecipeHasDifferentIdentity_success() {
        uniqueRecipeList.add(MILO);
        uniqueRecipeList.setRecipe(MILO, BURGER);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(BURGER);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipe_editedRecipeHasNonUniqueIdentity_throwsDuplicateRecipeException() {
        uniqueRecipeList.add(MILO);
        uniqueRecipeList.add(BURGER);
        Assert.assertThrows(DuplicateRecipeException.class, () -> uniqueRecipeList.setRecipe(MILO, BURGER));
    }

    @Test
    public void remove_nullRecipe_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRecipeList.remove(null));
    }

    @Test
    public void remove_recipeDoesNotExist_throwsRecipeNotFoundException() {
        Assert.assertThrows(RecipeNotFoundException.class, () -> uniqueRecipeList.remove(MILO));
    }

    @Test
    public void remove_existingRecipe_removesRecipe() {
        uniqueRecipeList.add(MILO);
        uniqueRecipeList.remove(MILO);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipes_nullUniqueRecipeList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRecipeList.setRecipes((UniqueRecipeList) null));
    }

    @Test
    public void setRecipes_uniqueRecipeList_replacesOwnListWithProvidedUniqueRecipeList() {
        uniqueRecipeList.add(MILO);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(BURGER);
        uniqueRecipeList.setRecipes(expectedUniqueRecipeList);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipes_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueRecipeList.setRecipes((List<Recipe>) null));
    }

    @Test
    public void setRecipes_list_replacesOwnListWithProvidedList() {
        uniqueRecipeList.add(MILO);
        List<Recipe> recipeList = Collections.singletonList(BURGER);
        uniqueRecipeList.setRecipes(recipeList);
        UniqueRecipeList expectedUniqueRecipeList = new UniqueRecipeList();
        expectedUniqueRecipeList.add(BURGER);
        assertEquals(expectedUniqueRecipeList, uniqueRecipeList);
    }

    @Test
    public void setRecipes_listWithDuplicateRecipes_throwsDuplicateRecipeException() {
        List<Recipe> listWithDuplicateRecipes = Arrays.asList(MILO, MILO);
        Assert.assertThrows(DuplicateRecipeException.class, ()
            -> uniqueRecipeList.setRecipes(listWithDuplicateRecipes));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueRecipeList.asUnmodifiableObservableList().remove(0));
    }
}
