package calofit.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import calofit.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path dishDatabaseFilePath = Paths.get("data" , "dishDb.json");
    private Path mealLogFilePath = Paths.get("data", "mealLog.json");
    private Path calorieBudgetFilePath = Paths.get("data", "budget.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setDishDatabaseFilePath(newUserPrefs.getDishDatabaseFilePath());
        setMealLogFilePath(newUserPrefs.getMealLogFilePath());
        setCalorieBudgetFilePath(newUserPrefs.getCalorieBudgetFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getDishDatabaseFilePath() {
        return dishDatabaseFilePath;
    }

    public void setDishDatabaseFilePath(Path dishDatabaseFilePath) {
        requireNonNull(dishDatabaseFilePath);
        this.dishDatabaseFilePath = dishDatabaseFilePath;
    }

    public Path getMealLogFilePath() {
        return mealLogFilePath;
    }

    public void setMealLogFilePath(Path mealLogFilePath) {
        requireNonNull(mealLogFilePath);
        this.mealLogFilePath = mealLogFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && dishDatabaseFilePath.equals(o.dishDatabaseFilePath)
                && mealLogFilePath.equals(o.mealLogFilePath)
                && calorieBudgetFilePath.equals(o.calorieBudgetFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, dishDatabaseFilePath, mealLogFilePath, calorieBudgetFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal dish database data file location : " + dishDatabaseFilePath);
        sb.append("\nLocal mealLog data file location : " + mealLogFilePath);
        sb.append("\nLocal calorie budget data file location : " + calorieBudgetFilePath);
        return sb.toString();
    }

    public Path getCalorieBudgetFilePath() {
        return calorieBudgetFilePath;
    }

    public void setCalorieBudgetFilePath(Path calorieBudgetFilePath) {
        requireNonNull(calorieBudgetFilePath);
        this.calorieBudgetFilePath = calorieBudgetFilePath;
    }
}
