package dukecooks.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.testutil.Assert;
import dukecooks.testutil.recipe.RecipeBuilder;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        Assert.assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setDukeCooksFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        Assert.assertThrows(NullPointerException.class, () -> userPrefs.setUserProfileFilePath(null));
    }

    @Test
    public void setRecipesFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        Assert.assertThrows(NullPointerException.class, () -> userPrefs.setRecipesFilePath(null));
    }

    @Test
    public void setMealPlansFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        Assert.assertThrows(NullPointerException.class, () -> userPrefs.setMealPlansFilePath(null));
    }

    @Test
    public void setDiaryFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        Assert.assertThrows(NullPointerException.class, () -> userPrefs.setDiaryFilePath(null));
    }

    @Test
    public void setHealthRecordsFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        Assert.assertThrows(NullPointerException.class, () -> userPrefs.setHealthRecordsFilePath(null));
    }

    @Test
    public void setExercisesFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        Assert.assertThrows(NullPointerException.class, () -> userPrefs.setExercisesFilePath(null));
    }

    @Test
    public void setDashboardFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        Assert.assertThrows(NullPointerException.class, () -> userPrefs.setDashboardFilePath(null));
    }

    @Test
    public void testUserPrefsHashCode() {
        UserPrefs prefs1 = new UserPrefs();
        UserPrefs prefs2 = new UserPrefs();

        assertEquals(prefs1.hashCode(), prefs2.hashCode());
    }

    @Test
    public void equalsUserPrefs_equalToItself_returnsTrue() {
        UserPrefs prefs1 = new UserPrefs();

        assertTrue(prefs1.equals(prefs1));
    }

    @Test
    public void equalsUserPrefs_equalToOther_returnsFalse() {
        UserPrefs prefs1 = new UserPrefs();

        assertFalse(prefs1.equals(new RecipeBuilder().build()));
    }

    @Test
    public void equalsUserPrefs_equalToNull_returnsFalse() {
        UserPrefs prefs1 = new UserPrefs();

        assertFalse(prefs1.equals(null));
    }

}
