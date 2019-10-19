package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.IFridgeSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.Name;
import seedu.address.model.food.ShoppingItem;
import seedu.address.model.food.TemplateItem;
import seedu.address.model.food.UniqueTemplateItems;
import seedu.address.model.waste.WasteMonth;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook groceryList;
    private final TemplateList templateList;
    private WasteList wasteList;
    private final ShoppingList shoppingList;
    private final UserPrefs userPrefs;
    private final FilteredList<GroceryItem> filteredGroceryItems;
    private final FilteredList<UniqueTemplateItems> filteredTemplateList;
    private FilteredList<TemplateItem> filteredShownTemplate;
    private FilteredList<GroceryItem> filteredWasteItems;
    private FilteredList<ShoppingItem> filteredShoppingItems;
    private UniqueTemplateItems shownTemplate;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook groceryList, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyTemplateList templateList, TreeMap<WasteMonth, WasteList> wasteArchive,
                        ReadOnlyShoppingList shoppingList) {
        super();
        requireAllNonNull(groceryList, userPrefs, templateList);

        logger.fine("Initializing with address book: " + groceryList + " and user prefs " + userPrefs
            + " and template list " + templateList);

        WasteList.initialiseWasteArchive();
        WasteList.addWasteArchive(wasteArchive);

        this.groceryList = new AddressBook(groceryList);
        this.templateList = new TemplateList(templateList);
        this.wasteList = WasteList.getCurrentWasteList();
        this.shoppingList = new ShoppingList(shoppingList);
        this.userPrefs = new UserPrefs(userPrefs);
        this.shownTemplate = new UniqueTemplateItems(new Name("Displayed Template"));
        filteredGroceryItems = new FilteredList<GroceryItem>(this.groceryList.getPersonList());
        filteredTemplateList = new FilteredList<UniqueTemplateItems>(this.templateList.getTemplateList());
        filteredWasteItems = new FilteredList<GroceryItem>(this.wasteList.getWasteList());
        filteredShoppingItems = new FilteredList<ShoppingItem>(this.shoppingList.getShoppingList());
        filteredShownTemplate = new FilteredList<TemplateItem>(this.shownTemplate.getTemplate());

    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new TemplateList(),
                new TreeMap<WasteMonth, WasteList>(), new ShoppingList());
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

    @Override
    public Path getWasteListFilePath() {
        return userPrefs.getWasteArchiveFilePath();
    }

    @Override
    public void setWasteListFilePath(Path wasteListFilePath) {
        requireAllNonNull(wasteListFilePath);
        userPrefs.setWasteArchiveFilePath(wasteListFilePath);
    }

    @Override
    public Path getShoppingListFilePath() {
        return userPrefs.getShoppingListFilePath();
    }

    @Override
    public void setShoppingListFilePath(Path shoppingListFilePath) {
        requireAllNonNull(shoppingListFilePath);
        userPrefs.setShoppingListFilePath(shoppingListFilePath);
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

    // Methods supporting the toBeShown and FilteredTemplateToBeShown
    @Override
    public void setShownTemplate(UniqueTemplateItems templateToBeShown) {
        requireNonNull(templateToBeShown);

        UniqueTemplateItems editedTemplate = new UniqueTemplateItems(templateToBeShown.getName());
        editedTemplate.setTemplateItems(templateToBeShown);
        shownTemplate = editedTemplate;
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

    @Override
    public ObservableList<TemplateItem> getFilteredTemplateToBeShown() {
        filteredShownTemplate = new FilteredList<TemplateItem>(this.shownTemplate.getTemplate());

        return filteredShownTemplate;
    }

    @Override
    public ObservableList<TemplateItem> updateFilteredTemplateToBeShown() {
        filteredShownTemplate = new FilteredList<TemplateItem>(this.shownTemplate.getTemplate());

        return filteredShownTemplate;
    }

    @Override
    public Name getNameTemplateToBeShown() {
        return shownTemplate.getName();
    }

    //=========== WasteList ==================================================================================

    @Override
    public void setWasteList(ReadOnlyWasteList wasteList) {
        this.wasteList.resetData(wasteList);
    }

    @Override
    public ReadOnlyWasteList getWasteList() {
        WasteMonth currentWasteMonth = new WasteMonth(LocalDate.now());
        if (currentWasteMonth.equals(wasteList.getWasteMonth())) {
            return wasteList;
        } else {
            // Creates a new waste list, updates
            WasteList newMonthWasteList = new WasteList(currentWasteMonth);
            this.wasteList = newMonthWasteList;
            WasteList.addWastelistToArchive(currentWasteMonth, newMonthWasteList);
        }
        return wasteList;
    }

    @Override
    public TreeMap<WasteMonth, WasteList> getWasteArchive() {
        return WasteList.getWasteArchive();
    }

    @Override
    public ReadOnlyWasteList getWasteListByMonth(WasteMonth wasteMonth) {
        return WasteList.getWasteListByMonth(wasteMonth);
    }

    @Override
    public void addWasteItem(GroceryItem food) {
        wasteList.addWasteItem(food);
        updateFilteredWasteItemList(WasteMonth.getCurrentWasteMonth());
    }

    //=========== Filtered Waste List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<GroceryItem> getFilteredWasteItemList() {
        return filteredWasteItems;
    }

    @Override
    public void updateFilteredWasteItemList(WasteMonth wasteMonth) {
        requireNonNull(wasteMonth);
        WasteList wasteListForMonth = WasteList.getWasteListByMonth(wasteMonth);
        filteredWasteItems = new FilteredList<>(wasteListForMonth.getWasteList());
    }

    @Override
    public ObservableList<GroceryItem> getFilteredWasteItemListByMonth(WasteMonth wasteMonth) {
        ReadOnlyWasteList wasteListForMonth = WasteList.getWasteListByMonth(wasteMonth);
        FilteredList<GroceryItem> monthWasteItems = new FilteredList<GroceryItem>(wasteListForMonth.getWasteList());
        return monthWasteItems;
    }

    @Override
    public Set<WasteMonth> getListOfWasteMonths() {
        return WasteList.getWasteArchive().keySet();
    }

    //=========== ShoppingList ================================================================================

    @Override
    public void setShoppingList(ReadOnlyShoppingList shoppingList) {
        this.shoppingList.resetData(shoppingList);
    }

    @Override
    public ReadOnlyShoppingList getShoppingList() {
        return shoppingList;
    }

    /**
     * Check if the in-memory model has the specified shopping item.
     *
     * @param shoppingItem The shopping item
     * @return Returns true if the model has the shopping item.
     */
    public boolean hasShoppingItem(ShoppingItem shoppingItem) {
        requireNonNull(shoppingItem);
        return shoppingList.hasShoppingItem(shoppingItem);
    }

    public void deleteShoppingItem(ShoppingItem target) {
        shoppingList.removeShoppingItem(target);
    }

    @Override
    public void addShoppingItem(ShoppingItem food) {
        shoppingList.addShoppingItem(food);
        updateFilteredShoppingList(PREDICATE_SHOW_ALL_SHOPPING_ITEMS);
    }

    @Override
    public void setShoppingItem(ShoppingItem target, ShoppingItem editedShoppingItem) {
        requireAllNonNull(target, editedShoppingItem);

        shoppingList.setShoppingItem(target, editedShoppingItem);
    }

    //=========== Filtered Shopping List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ShoppingItem} backed by the internal list of
     * {@code versionedShoppingList}
     */
    @Override
    public ObservableList<ShoppingItem> getFilteredShoppingList() {
        return filteredShoppingItems;
    }

    @Override
    public void updateFilteredShoppingList(Predicate<ShoppingItem> predicate) {
        requireNonNull(predicate);
        filteredShoppingItems.setPredicate(predicate);
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
                && filteredTemplateList.equals(other.filteredTemplateList)
                && filteredWasteItems.equals(other.filteredWasteItems)
                && filteredShownTemplate.equals(other.filteredShownTemplate);
    }
}
