package seedu.elisa.model;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.NoSuchElementException;

import javafx.beans.property.SimpleBooleanProperty;
import seedu.elisa.commons.core.GuiSettings;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.commons.exceptions.IllegalValueException;
import seedu.elisa.logic.commands.Command;
import seedu.elisa.model.exceptions.IllegalListException;
import seedu.elisa.model.item.ActiveRemindersList;
import seedu.elisa.model.item.EventList;
import seedu.elisa.model.item.FutureRemindersList;
import seedu.elisa.model.item.VisualizeList;

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

    public Item deleteItem(int index);

    public Item deleteItem(Item item);

    public VisualizeList getVisualList();

    public void setVisualList(String listString) throws IllegalValueException;

    public void clear();

    public void emptyLists();

    public VisualizeList findItem(String[] searchStrings);

    public void setVisualizeList(VisualizeList list);

    public void sort();

    public void sort(Comparator<Item> comparator);

    public boolean hasItem(Item item);

    public void addToSeparateList(Item item);

    public void addToSeparateList(ItemIndexWrapper wrapper);

    public ItemIndexWrapper getIndices(int index);

    public void repopulateLists();

    public ElisaCommandHistory getElisaCommandHistory();

    public boolean togglePriorityMode() throws IllegalListException;

    public void scheduleOffPriorityMode(LocalDateTime localDateTime);

    public void closePriorityModeThread();

    public Item markComplete(int index, boolean status) throws IllegalListException;

    public JokeList getJokeList();

    public String getJoke();

    //Bryan Reminder
    ActiveRemindersList getActiveReminderListProperty();

    FutureRemindersList getFutureRemindersList();

    void updateCommandHistory(Command command);

    public EventList getEventList();

    public Item getItem(int index);

    SimpleBooleanProperty getPriorityMode();

    boolean isSystemToggle();

    public Item editItem(Item oldItem, Item newItem);

    Item getLatestOccurredReminder() throws NoSuchElementException;

    PriorityExitStatus getExitStatus();

    boolean isFocusMode();

    void toggleOnFocusMode();

    SimpleBooleanProperty getFocusMode();
}
