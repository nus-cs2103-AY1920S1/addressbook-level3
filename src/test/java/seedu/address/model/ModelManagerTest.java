package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECIPES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProfiles.ALICE;
import static seedu.address.testutil.TypicalProfiles.BENSON;
import static seedu.address.testutil.TypicalRecipes.MILO;
import static seedu.address.testutil.TypicalRecipes.OMELETTE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.recipe.RecipeNameContainsKeywordsPredicate;
import seedu.address.testutil.DukeCooksBuilder;
import seedu.address.testutil.RecipeBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new RecipeBook(), new RecipeBook(modelManager.getRecipeBook()));
        assertEquals(new UserProfile(), new UserProfile(modelManager.getUserProfile()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setRecipesFilePath(Paths.get("address/book/file/path"));
        userPrefs.setUserProfileFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setRecipesFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setUserProfileFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setDukeCooksFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserProfileFilePath(null));
    }

    @Test
    public void setRecipesFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setRecipesFilePath(null));
    }

    @Test
    public void setDukeCooksFilePath_validPath_setsDukeCooksFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setUserProfileFilePath(path);
        assertEquals(path, modelManager.getUserProfileFilePath());
    }

    @Test
    public void setRecipesFilePath_validPath_setsRecipesFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setRecipesFilePath(path);
        assertEquals(path, modelManager.getRecipesFilePath());
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getFilteredRecipeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredRecipeList().remove(0));
    }

    @Test
    public void equals() {
        UserProfile userProfile = new DukeCooksBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserProfile differentUserProfile = new UserProfile();
        RecipeBook recipeBook = new RecipeBookBuilder().withRecipe(MILO).withRecipe(OMELETTE).build();
        RecipeBook differentRecipeBook = new RecipeBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(recipeBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(recipeBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different recipeBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentRecipeBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = MILO.getName().fullName.split("\\s+");
        modelManager.updateFilteredRecipeList(new RecipeNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(recipeBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setRecipesFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(recipeBook, differentUserPrefs)));
    }
}
