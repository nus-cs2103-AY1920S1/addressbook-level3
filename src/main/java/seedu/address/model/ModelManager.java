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
import seedu.address.model.person.Food;
import seedu.address.model.person.GroceryItem;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook groceryList;
    private final UserPrefs userPrefs;
    private final FilteredList<GroceryItem> filteredGroceryItems;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook groceryList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(groceryList, userPrefs);

        logger.fine("Initializing with address book: " + groceryList + " and user prefs " + userPrefs);

        this.groceryList = new AddressBook(groceryList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredGroceryItems = new FilteredList<GroceryItem>(this.groceryList.getPersonList());
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
    public void setGroceryList(ReadOnlyAddressBook groceryList) {
        this.groceryList.resetData(groceryList);
    }

    @Override
    public ReadOnlyAddressBook getGroceryList() {
        return groceryList;
    }

    /**
     * Check if the in-memory model has the specified grocery item.
     *
     * @param food The grocery item
     * @return Returns true if the model has the grocery item.
     */
    public boolean hasGroceryItem(GroceryItem food) {
        requireNonNull(food);
        return groceryList.hasPerson(food);
    }

    public void deleteGroceryItem(GroceryItem target) {
        groceryList.removePerson(target);
    }

    @Override
    public void addGroceryItem(GroceryItem food) {
        groceryList.addPerson(food);
        updateFilteredGroceryItemList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setGroceryItem(GroceryItem target, GroceryItem editedFood) {
        requireAllNonNull(target, editedFood);

        groceryList.setGroceryItem(target, editedFood);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<GroceryItem> getFilteredGroceryItemList() {
        return filteredGroceryItems;
    }

    @Override
    public void updateFilteredGroceryItemList(Predicate<Food> predicate) {
        requireNonNull(predicate);
        filteredGroceryItems.setPredicate(predicate);
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
        return groceryList.equals(other.groceryList)
                && userPrefs.equals(other.userPrefs)
                && filteredGroceryItems.equals(other.filteredGroceryItems);
    }

}
