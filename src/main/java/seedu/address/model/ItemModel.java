package seedu.address.model;

import java.nio.file.Path;
import java.util.ArrayList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.item.Item;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.model.exceptions.IllegalListException;
import seedu.address.model.item.ActiveRemindersList;
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

    public void addItem(ItemIndexWrapper wrapper);

    public void replaceItem(Item item, Item newItem);

    public Item removeItem(int index);

    public Item removeItem(Item item);

    public Item deleteItem(int index);

    public VisualizeList getVisualList();

    public void setVisualList(String listString) throws IllegalValueException;

    public void clear();

    public void emptyLists();

    public VisualizeList findItem(String[] searchStrings);

    public void setVisualizeList(VisualizeList list);

    public void sort();

    public boolean hasItem(Item item);

    public void addToSeparateList(Item item);

    public void addToSeparateList(ItemIndexWrapper wrapper);

    public ItemIndexWrapper getIndices(int index);

    public void updateLists();

    public ElisaCommandHistory getElisaCommandHistory();

    public boolean togglePriorityMode() throws IllegalListException;

    public Item markComplete(int index) throws IllegalListException;

    public Item markIncomplete(int index) throws IllegalListException;

    //Bryan Reminder
    ActiveRemindersList getActiveReminderListProperty();

    ArrayList<Item> getFutureRemindersList();

    void updateCommandHistory(Command command);
}
