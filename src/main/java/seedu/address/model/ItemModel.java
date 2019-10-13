package seedu.address.model;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.item.Item;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.VisualizeList;

/**
 * The API of the Model component.
 */
public interface ItemModel {

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
     * Returns the user prefs' address book file path.
     */
    Path getItemStorageFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setItemStorageFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setItemStorage(ItemStorage itemStorage);

    /** Returns the AddressBook */
    ItemStorage getItemStorage();

    public void addItem(Item item);

    public void replaceItem(Item item, Item newItem);

    public Item removeItem(int index);

    public Item deleteItem(int index);

    public VisualizeList getVisualList();

    public void setVisualList(String listString) throws IllegalValueException;

    public void clear();

    public VisualizeList findItem(String[] searchStrings);

    public void setVisualizeList(VisualizeList list);
}
