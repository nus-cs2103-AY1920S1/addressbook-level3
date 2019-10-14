package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.recipe.Recipe;

/**
 * Represents the in-memory model of Duke Cooks data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final DukeCooks dukeCooks;
    private final UserPrefs userPrefs;
    private final FilteredList<Recipe> filteredRecipes;

    /**
     * Initializes a ModelManager with the given dukeCooks and userPrefs.
     */
    public ModelManager(ReadOnlyDukeCooks dukeCooks, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(dukeCooks, userPrefs);

        logger.fine("Initializing with Duke Cooks: " + dukeCooks + " and user prefs " + userPrefs);

        this.dukeCooks = new DukeCooks(dukeCooks);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredRecipes = new FilteredList<>(this.dukeCooks.getPersonList());
    }

    public ModelManager() {
        this(new DukeCooks(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getDukeCooksFilePath() {
        return userPrefs.getDukeCooksFilePath();
    }

    @Override
    public void setDukeCooksFilePath(Path dukeCooksFilePath) {
        requireNonNull(dukeCooksFilePath);
        userPrefs.setDukeCooksFilePath(dukeCooksFilePath);
    }

    //=========== DukeBooks ================================================================================

    @Override
    public void setDukeCooks(ReadOnlyDukeCooks dukeCooks) {
        this.dukeCooks.resetData(dukeCooks);
    }

    @Override
    public ReadOnlyDukeCooks getDukeCooks() {
        return dukeCooks;
    }

    @Override
    public boolean hasPerson(Recipe recipe) {
        requireNonNull(recipe);
        return dukeCooks.hasPerson(recipe);
    }

    @Override
    public void deletePerson(Recipe target) {
        dukeCooks.removePerson(target);
    }

    @Override
    public void addPerson(Recipe recipe) {
        dukeCooks.addPerson(recipe);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Recipe target, Recipe editedRecipe) {
        requireAllNonNull(target, editedRecipe);

        dukeCooks.setPerson(target, editedRecipe);
    }

    //=========== Filtered Recipe List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Recipe} backed by the internal list of
     * {@code versionedDukeCooks}
     */
    @Override
    public ObservableList<Recipe> getFilteredPersonList() {
        return filteredRecipes;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Recipe> predicate) {
        requireNonNull(predicate);
        filteredRecipes.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return dukeCooks.equals(other.dukeCooks)
                && userPrefs.equals(other.userPrefs)
                && filteredRecipes.equals(other.filteredRecipes);
    }

}
