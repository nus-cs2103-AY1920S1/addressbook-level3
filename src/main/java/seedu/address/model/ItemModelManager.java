package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.item.EventList;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemList;
import seedu.address.model.item.ReminderList;
import seedu.address.model.item.TaskList;

/**
 * Represents the model for ELISA
 */
public class ItemModelManager implements ItemModel {
    private ItemList itemList;
    private TaskList taskList;
    private EventList eventList;
    private ReminderList reminderList;
    // The list to be used for visualizing in the Ui
    private ItemList visualList;
    private final AddressBook addressBook;
    private final UserPrefs userPrefs;

    public ItemModelManager(AddressBook addressBook, UserPrefs userPrefs) {
        this.itemList = new ItemList();
        this.taskList = new TaskList();
        this.eventList = new EventList();
        this.reminderList = new ReminderList();
        this.visualList = taskList;
        this.addressBook = addressBook;
        this.userPrefs = userPrefs;
        for (Item i: itemStorage.getList()) {
            this.addItem(i);
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

    /**
     * Adds an item to the respective list. All items will be added to the central list.
     * It will also be added to the respective list depending on whether it is a task, event or a reminder.
     * @param item the item to be added to the program
     */
    public void addItem (Item item) {
        itemList.add(item);
        if (item.isTask()) {
            taskList.add(item);
        }

        if (item.isEvent()) {
            eventList.add(item);
        }

        if (item.isReminder()) {
            reminderList.add(item);
        }
    }

    /**
     * Adds an item to a specific list
     * @param item the item to be added to the list
     * @param il the list the item is to be added to
     */
    public void addItem (Item item, ItemList il) {
        il.add(item);
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
        itemList.remove(item);
        taskList.remove(item);
        eventList.remove(item);
        reminderList.remove(item);
        return item;
    }

    public ItemList getVisualList() {
        return this.visualList;
    }

    /**
     * Set the new item list to be the visualization list.
     * @param il the item list to be visualized
     */
    public void setVisualList(ItemList il) {
        this.visualList = il;
    }

    public void setItem(Item item, Item newItem) {
        int index = visualList.indexOf(item);
        visualList.set(index, newItem);

        if ((index = itemList.indexOf(item)) >= 0) {
            taskList.set(index, newItem);
        }

        if ((index = taskList.indexOf(item)) >= 0) {
            taskList.set(index, newItem);
        }

        if ((index = eventList.indexOf(item)) >= 0) {
            eventList.set(index, newItem);
        }

        if ((index = reminderList.indexOf(item)) >= 0) {
            reminderList.set(index, newItem);
        }
    }

    public ItemList findItem(String searchString) {
        this.visualList = visualList.find(searchString);
        return this.visualList;
    }
}
