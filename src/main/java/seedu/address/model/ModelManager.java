package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.IFridgeSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.TemplateItem;
import seedu.address.model.food.UniqueTemplateItems;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook groceryList;
    private final TemplateList templateList;
    private final UserPrefs userPrefs;
    private final FilteredList<GroceryItem> filteredGroceryItems;
    private final FilteredList<UniqueTemplateItems> filteredTemplateList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook groceryList, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyTemplateList templateList) {
        super();
        requireAllNonNull(groceryList, userPrefs, templateList);

        logger.fine("Initializing with address book: " + groceryList + " and user prefs " + userPrefs
            + " and template list " + templateList);

        this.groceryList = new AddressBook(groceryList);
        this.templateList = new TemplateList(templateList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredGroceryItems = new FilteredList<GroceryItem>(this.groceryList.getPersonList());
        filteredTemplateList = new FilteredList<UniqueTemplateItems>(this.templateList.getTemplateList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new TemplateList());
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
    public IFridgeSettings getIFridgeSettings() {
        return userPrefs.getIFridgeSettings();
    }

    @Override
    public void setIFridgeSettings(IFridgeSettings iFridgeSettings) {
        requireNonNull(iFridgeSettings);
        userPrefs.setIFridgeSettings(iFridgeSettings);
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

    @Override
    public Path getTemplateListFilePath() {
        return userPrefs.getTemplateListFilePath();
    }

    @Override
    public void setTemplateListFilePath(Path templateListFilePath) {
        requireNonNull(templateListFilePath);
        userPrefs.setTemplateListFilePath(templateListFilePath);
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
    public void updateFilteredGroceryItemList(Predicate<GroceryItem> predicate) {
        requireNonNull(predicate);
        filteredGroceryItems.setPredicate(predicate);
    }

    //=========== TemplateList ==================================================================================
    @Override
    public void setTemplateList(ReadOnlyTemplateList templateList) {
        this.templateList.resetData(templateList);
    }

    @Override
    public ReadOnlyTemplateList getTemplateList() {
        return templateList;
    }

    /**
     * Check if the in-memory model has the specified template.
     *
     * @param template Template
     * @return Returns true if the model has the template.
     */
    public boolean hasTemplate(UniqueTemplateItems template) {
        requireNonNull(template);
        return templateList.hasTemplate(template);
    }

    public void deleteTemplate(UniqueTemplateItems target) {
        templateList.removeTemplate(target);
    }

    @Override
    public void addTemplate(UniqueTemplateItems template) {
        templateList.addTemplate(template);
        updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
    }

    @Override
    public void setTemplate(UniqueTemplateItems target, UniqueTemplateItems editedTemplate) {
        requireAllNonNull(target, editedTemplate);

        templateList.setTemplate(target, editedTemplate);
    }

    //=========== Filtered Template List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code UniqueTemplateItems} backed by the internal list of
     * {@code versionedTemplateList}
     */
    @Override
    public ObservableList<UniqueTemplateItems> getFilteredTemplateList() {
        return filteredTemplateList;
    }

    @Override
    public void updateFilteredTemplateList(Predicate<UniqueTemplateItems> predicate) {
        requireNonNull(predicate);
        filteredTemplateList.setPredicate(predicate);
    }

    //=========== Common Accessors =============================================================
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
                && templateList.equals(other.templateList)
                && userPrefs.equals(other.userPrefs)
                && filteredGroceryItems.equals(other.filteredGroceryItems)
                && filteredTemplateList.equals(other.filteredTemplateList);
    }
}
