package seedu.ifridge.model;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.ifridge.commons.core.GuiSettings;
import seedu.ifridge.commons.core.IFridgeSettings;
import seedu.ifridge.commons.core.LogsCenter;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.ShoppingItem;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.model.waste.WasteReport;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final GroceryList groceryList;
    private final TemplateList templateList;
    private WasteList wasteList;
    private final ShoppingList shoppingList;
    private final GroceryList boughtList;
    private final UserPrefs userPrefs;
    private final FilteredList<GroceryItem> filteredGroceryItems;
    private final FilteredList<UniqueTemplateItems> filteredTemplateList;
    private FilteredList<TemplateItem> filteredShownTemplate;
    private FilteredList<GroceryItem> filteredWasteItems;
    private FilteredList<ShoppingItem> filteredShoppingItems;
    private FilteredList<GroceryItem> filteredBoughtItems;
    private WasteReport wasteReport;
    private UniqueTemplateItems shownTemplate;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyGroceryList groceryList, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyTemplateList templateList, TreeMap<WasteMonth, WasteList> wasteArchive,
                        ReadOnlyShoppingList shoppingList, ReadOnlyGroceryList boughtList) {
        super();
        requireAllNonNull(groceryList, userPrefs, templateList, shoppingList);

        logger.fine("Initializing with address book: " + groceryList + " and user prefs " + userPrefs
            + " and template list " + templateList + " and shopping list " + shoppingList);

        WasteList.initialiseWasteArchive();
        WasteList.addWasteArchive(wasteArchive);

        this.groceryList = new GroceryList(groceryList);
        this.templateList = new TemplateList(templateList);
        this.wasteList = WasteList.getCurrentWasteList();
        this.shoppingList = new ShoppingList(shoppingList);
        this.boughtList = new GroceryList(boughtList);
        this.userPrefs = new UserPrefs(userPrefs);
        this.shownTemplate = new UniqueTemplateItems(new Name("Displayed Template"));
        filteredGroceryItems = new FilteredList<GroceryItem>(this.groceryList.getGroceryList());
        filteredTemplateList = new FilteredList<UniqueTemplateItems>(this.templateList.getTemplateList());
        filteredWasteItems = new FilteredList<GroceryItem>(this.wasteList.getWasteList());
        filteredShoppingItems = new FilteredList<ShoppingItem>(this.shoppingList.getShoppingList());
        filteredBoughtItems = new FilteredList<GroceryItem>(this.boughtList.getGroceryList());
        filteredShownTemplate = new FilteredList<TemplateItem>(this.shownTemplate.getTemplate());

    }

    public ModelManager() {
        this(new GroceryList(), new UserPrefs(), new TemplateList(),
                new TreeMap<WasteMonth, WasteList>(), new ShoppingList(), new GroceryList());
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
    public Path getGroceryListFilePath() {
        return userPrefs.getGroceryListFilePath();
    }

    @Override
    public void setGroceryListFilePath(Path groceryListFilePath) {
        requireNonNull(groceryListFilePath);
        userPrefs.setGroceryListFilePath(groceryListFilePath);
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

    @Override
    public Path getBoughtListFilePath() {
        return userPrefs.getBoughtListFilePath();
    }

    @Override
    public void setBoughtListFilePath(Path boughtListFilePath) {
        requireAllNonNull(boughtListFilePath);
        userPrefs.setBoughtListFilePath(boughtListFilePath);
    }

    //=========== GroceryList ================================================================================

    @Override
    public void setGroceryList(ReadOnlyGroceryList groceryList) {
        this.groceryList.resetData(groceryList);
    }

    @Override
    public ReadOnlyGroceryList getGroceryList() {
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
        return groceryList.hasGroceryItem(food);
    }

    public void deleteGroceryItem(GroceryItem target) {
        groceryList.removeGroceryItem(target);
    }

    @Override
    public void addGroceryItem(GroceryItem food) {
        groceryList.addGroceryItem(food);
        updateFilteredGroceryItemList(PREDICATE_SHOW_ALL_GROCERY_ITEMS);
    }

    @Override
    public void setGroceryItem(GroceryItem target, GroceryItem editedGroceryItem) {
        requireAllNonNull(target, editedGroceryItem);

        groceryList.setGroceryItem(target, editedGroceryItem);
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

    @Override
    public boolean hasWasteMonth(WasteMonth wasteMonth) {
        return WasteList.getWasteArchive().containsKey(wasteMonth);
    }

    @Override
    public WasteMonth getEarliestWasteMonth() {
        return WasteList.getWasteArchive().firstKey();
    }

    @Override
    public WasteMonth getLatestWasteMonth() {
        return WasteList.getWasteArchive().lastKey();
    }

    //=========== Waste Report Accessors =============================================================

    @Override
    public WasteReport getWasteReport() {
        requireNonNull(this.wasteReport);
        return this.wasteReport;
    }

    @Override
    public void setWasteReport(WasteReport wasteReport) {
        requireNonNull(wasteReport);
        this.wasteReport = wasteReport;
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

    //=========== BoughtList ================================================================================

    @Override
    public void setBoughtList(ReadOnlyGroceryList boughtList) {
        this.boughtList.resetData(boughtList);
    }

    @Override
    public ReadOnlyGroceryList getBoughtList() {
        return boughtList;
    }

    /**
     * Check if the in-memory model has the specified bought grocery item.
     *
     * @param food The grocery item
     * @return Returns true if the model has the bought grocery item.
     */
    public boolean hasBoughtItem(GroceryItem food) {
        requireNonNull(food);
        return boughtList.hasGroceryItem(food);
    }

    public void deleteBoughtItem(GroceryItem target) {
        boughtList.removeGroceryItem(target);
    }

    @Override
    public void addBoughtItem(GroceryItem food) {
        boughtList.addGroceryItem(food);
        updateFilteredBoughtItemList(PREDICATE_SHOW_ALL_GROCERY_ITEMS);
    }

    @Override
    public void setBoughtItem(GroceryItem target, GroceryItem editedFood) {
        requireAllNonNull(target, editedFood);

        boughtList.setGroceryItem(target, editedFood);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Grocery Item} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<GroceryItem> getFilteredBoughtItemList() {
        return filteredBoughtItems;
    }

    @Override
    public void updateFilteredBoughtItemList(Predicate<GroceryItem> predicate) {
        requireNonNull(predicate);
        filteredBoughtItems.setPredicate(predicate);
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
                && filteredShoppingItems.equals(other.filteredShoppingItems)
                && filteredShownTemplate.equals(other.filteredShownTemplate);
    }
}
