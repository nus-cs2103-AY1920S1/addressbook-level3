package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.IFridgeSettings;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.UniqueTemplateItems;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<GroceryItem> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<UniqueTemplateItems> PREDICATE_SHOW_ALL_TEMPLATES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' iFridge settings.
     */
    IFridgeSettings getIFridgeSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setIFridgeSettings(IFridgeSettings iFridgeSettings);

    //=========== GroceryList ==================================================================================
    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setGroceryList(ReadOnlyAddressBook groceryList);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getGroceryList();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasGroceryItem(GroceryItem food);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteGroceryItem(GroceryItem target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addGroceryItem(GroceryItem food);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setGroceryItem(GroceryItem target, GroceryItem editedFood);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<GroceryItem> getFilteredGroceryItemList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroceryItemList(Predicate<GroceryItem> predicate);

    //=========== TemplateList ==================================================================================
    /**
     * Returns the user prefs' template list file path.
     */
    Path getTemplateListFilePath();

    /**
     * Sets the user prefs' template list file path.
     */
    void setTemplateListFilePath(Path templateListFilePath);

    /**
     * Replaces template list data with the data in {@code templateList}.
     */
    void setTemplateList(ReadOnlyTemplateList templateList);

    /** Returns the TemplateList */
    ReadOnlyTemplateList getTemplateList();

    /**
     * Returns true if a UniqueTemplateItems with the same identity as {@code toAdd} exists in the template list.
     */
    boolean hasTemplate(UniqueTemplateItems toAdd);

    /**
     * Deletes the given template.
     * The template must exist in the template list.
     */
    void deleteTemplate(UniqueTemplateItems target);

    /**
     * Adds the given template.
     * {@code toAdd} must not already exist in the address book.
     */
    void addTemplate(UniqueTemplateItems toAdd);

    /**
     * Replaces the given template {@code target} with {@code editedTemplate}.
     * {@code target} must exist in the template list.
     * The template identity of {@code editedTemplate} must not be the same as another existing template
     * in the address book.
     */
    void setTemplate(UniqueTemplateItems target, UniqueTemplateItems editedTemplate);

    /** Returns an unmodifiable view of the filtered template list */
    ObservableList<UniqueTemplateItems> getFilteredTemplateList();

    /**
     * Updates the filter of the filtered template list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTemplateList(Predicate<UniqueTemplateItems> predicate);
}
