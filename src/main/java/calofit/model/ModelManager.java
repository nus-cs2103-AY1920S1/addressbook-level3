package calofit.model;

import calofit.commons.core.GuiSettings;
import calofit.commons.core.LogsCenter;
import calofit.commons.util.CollectionUtil;
import calofit.model.meal.Meal;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Meal> filteredMeals;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredMeals = new FilteredList<>(this.addressBook.getMealList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasMeal(Meal meal) {
        requireNonNull(meal);
        return addressBook.hasMeal(meal);
    }

    @Override
    public void deleteMeal(Meal target) {
        addressBook.removeMeal(target);
    }

    @Override
    public void addMeal(Meal meal) {
        addressBook.addMeal(meal);
        updateFilteredMealList(PREDICATE_SHOW_ALL_MEALS);
    }

    @Override
    public void setMeal(Meal target, Meal editedMeal) {
        CollectionUtil.requireAllNonNull(target, editedMeal);

        addressBook.setMeal(target, editedMeal);
    }

    //=========== Filtered Meal List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Meal} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Meal> getFilteredMealList() {
        return filteredMeals;
    }

    @Override
    public void updateFilteredMealList(Predicate<Meal> predicate) {
        requireNonNull(predicate);
        filteredMeals.setPredicate(predicate);
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
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredMeals.equals(other.filteredMeals);
    }

}
