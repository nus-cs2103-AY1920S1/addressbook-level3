package seedu.address.model;

import seedu.address.model.item.EventList;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemList;
import seedu.address.model.item.ReminderList;
import seedu.address.model.item.TaskList;

/**
 * Represents the model for ELISA
 */
public class ItemModel {
    private ItemList allItems;
    private TaskList taskList;
    private EventList eventList;
    private ReminderList reminderList;
    // The list to be used for visualizing in the Ui
    private ItemList visualList;

    public ItemModel() {
        this.allItems = new ItemList();
        this.taskList = new TaskList();
        this.eventList = new EventList();
        this.reminderList = new ReminderList();
        this.visualList = taskList;
    }

    /**
     * Adds an item to the respective list. All items will be added to the central list.
     * It will also be added to the respective list depending on whether it is a task, event or a reminder.
     * @param item the item to be added to the program
     */
    public void add (Item item) {
        allItems.add(item);
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
    public void add (Item item, ItemList il) {
        il.add(item);
    }

    /**
     * Remove an item from the current list.
     * @param index the item to be removed from the current list
     * @return the item that was removed
     */
    public Item remove(int index) {
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
    public Item delete(int index) {
        Item item = visualList.remove(index);
        allItems.remove(item);
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
}
