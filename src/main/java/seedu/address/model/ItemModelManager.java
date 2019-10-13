package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.item.Item;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.EventList;
import seedu.address.model.item.ReminderList;
import seedu.address.model.item.TaskList;
import seedu.address.model.item.VisualizeList;

/**
 * Represents the model for ELISA
 */
public class ItemModelManager implements ItemModel {
    private TaskList taskList;
    private EventList eventList;
    private ReminderList reminderList;
    // The list to be used for visualizing in the Ui
    private VisualizeList visualList;
    private final UserPrefs userPrefs;
    private ItemStorage itemStorage;

    public ItemModelManager(ItemStorage itemStorage, ReadOnlyUserPrefs userPrefs) {
        this.taskList = new TaskList();
        this.eventList = new EventList();
        this.reminderList = new ReminderList();
        this.visualList = taskList;
        this.itemStorage = itemStorage;
        this.userPrefs = new UserPrefs(userPrefs);

        for (int i = 0; i < itemStorage.size(); i++) {
            addToSeparateList(itemStorage.get(i));
        }
    }

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
    public Path getItemStorageFilePath() {
        return userPrefs.getItemStorageFilePath();
    }

    @Override
    public void setItemStorageFilePath(Path itemStorageFilePath) {
        requireNonNull(itemStorageFilePath);
        userPrefs.setItemStorageFilePath(itemStorageFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setItemStorage(ItemStorage itemStorage) {
        this.itemStorage = itemStorage;
    }

    @Override
    public ItemStorage getItemStorage() {
        return itemStorage;
    }

    /**
     * Adds an item to the respective list. All items will be added to the central list.
     * It will also be added to the respective list depending on whether it is a task, event or a reminder.
     * @param item the item to be added to the program
     */
    public void addItem (Item item) {
        itemStorage.add(item);
        addToSeparateList(item);
    }

    /**
     * Adds an item to a specific list
     * @param item the item to be added to the list
     * @param il the list the item is to be added to
     */
    public void addItem (Item item, VisualizeList il) {
        il.add(item);
    }

    /**
     * Helper function to add an item to it's respective list
     * @param item the item to be added into the lists
     */
    private void addToSeparateList(Item item) {
        if (item.hasTask()) {
            taskList.add(item);
        }

        if (item.hasEvent()) {
            eventList.add(item);
        }

        if (item.hasReminder()) {
            reminderList.add(item);
        }
    }

    /**
     * Remove an item from the current list.
     * @param index the item to be removed from the current list
     * @return the item that was removed
     */
    public Item removeItem(int index) {
        Item item = visualList.remove(index);
        if (visualList instanceof TaskList) {
            taskList.remove(item);
        } else if (visualList instanceof EventList) {
            eventList.remove(item);
        } else if (visualList instanceof ReminderList) {
            reminderList.remove(item);
        } else {
            // never reached here as there are only three variants for the visualList
        }

        return item;
    }

    /**
     * Deletes an item from the program.
     * @param index the index of the item to be deleted.
     * @return the item that was deleted from the program
     */
    public Item deleteItem(int index) {
        Item item = visualList.remove(index);
        itemStorage.remove(item);
        taskList.remove(item);
        eventList.remove(item);
        reminderList.remove(item);
        return item;
    }

    public VisualizeList getVisualList() {
        return this.visualList;
    }

    /**
     * Set a new item list to be the visualization list.
     * @param listString the string representation of the list to be visualized
     */
    public void setVisualList(String listString) throws IllegalValueException {
        switch(listString) {
        case "T":
            setVisualList(taskList);
            break;
        case "E":
            setVisualList(eventList);
            break;
        case "R":
            setVisualList(reminderList);
            break;
        default:
            throw new IllegalValueException(String.format("%s is no a valid list", listString));
        }
    }

    private void setVisualList(VisualizeList il) {
        this.visualList = il;
    }

    /**
     * Replaces one item with another item.
     * @param item the item to be replace
     * @param newItem the item that will replace the previous item
     */
    public void replaceItem(Item item, Item newItem) {
        int index = visualList.indexOf(item);
        visualList.setItem(index, newItem);

        if ((index = itemStorage.indexOf(item)) >= 0) {
            itemStorage.setItem(index, newItem);
        }

        if ((index = taskList.indexOf(item)) >= 0) {
            taskList.setItem(index, newItem);
        }

        if ((index = eventList.indexOf(item)) >= 0) {
            eventList.setItem(index, newItem);
        }

        if ((index = reminderList.indexOf(item)) >= 0) {
            reminderList.setItem(index, newItem);
        }
    }

    /**
     * Find an item based on its description.
     * @param searchStrings the string to search for within the description
     * @return the item list containing all the items that contain the search string
     */
    public VisualizeList findItem(String[] searchStrings) {
        this.visualList = visualList.find(searchStrings);
        return this.visualList;
    }

}
