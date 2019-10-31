package seedu.elisa.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import seedu.elisa.commons.core.GuiSettings;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.commons.core.item.Task;
import seedu.elisa.commons.exceptions.IllegalValueException;
import seedu.elisa.logic.commands.Command;
import seedu.elisa.model.exceptions.IllegalListException;
import seedu.elisa.model.item.ActiveRemindersList;
import seedu.elisa.model.item.CalendarList;
import seedu.elisa.model.item.EventList;
import seedu.elisa.model.item.FutureRemindersList;
import seedu.elisa.model.item.ReminderList;
import seedu.elisa.model.item.TaskList;
import seedu.elisa.model.item.VisualizeList;

/**
 * Represents the model for ELISA
 */
public class ItemModelManager implements ItemModel {
    private TaskList taskList;
    private EventList eventList;
    private ReminderList reminderList;
    private CalendarList calendarList;
    // The list to be used for visualizing in the Ui
    private VisualizeList visualList;
    private final UserPrefs userPrefs;
    private ItemStorage itemStorage;
    private final ElisaCommandHistory elisaCommandHistory;
    private final JokeList jokeList;
    private SimpleBooleanProperty priorityMode = new SimpleBooleanProperty(false);
    private boolean systemToggle = false;
    private PriorityQueue<Item> sortedTask = null;

    //Bryan Reminder
    //These three lists must be synchronized
    private ReminderList pastReminders;
    private ActiveRemindersList activeReminders;
    private FutureRemindersList futureReminders;

    private Timer timer = null;

    public ItemModelManager(ItemStorage itemStorage, ReadOnlyUserPrefs userPrefs,
                            ElisaCommandHistory elisaCommandHistory) {

        this.taskList = new TaskList();
        this.eventList = new EventList();
        this.reminderList = new ReminderList();
        this.calendarList = new CalendarList();
        this.visualList = taskList;
        this.itemStorage = itemStorage;
        this.userPrefs = new UserPrefs(userPrefs);
        this.elisaCommandHistory = elisaCommandHistory;

        this.jokeList = new JokeList();

        pastReminders = new ReminderList();
        activeReminders = new ActiveRemindersList(new ReminderList());
        futureReminders = new FutureRemindersList();

        updateLists();
    }


    /**
     * repopulate item lists from storage
     * */

    public void updateLists() {
        for (int i = 0; i < itemStorage.size(); i++) {
            addToSeparateList(itemStorage.get(i));
        }
    }

    /* Bryan Reminder
     *
     * Referenced: https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm
     * for property naming conventions.
     *
     */

    //Function to get property
    @Override
    public ActiveRemindersList getActiveReminderListProperty() {
        return activeReminders;
    }

    //Function get property's value
    public final ObservableList<Item> getActiveReminderList() {
        return activeReminders.get();
    }
    //Function to edit property //which should trigger a change event
    public final void addReminderToActive(Item item) {
        activeReminders.add(item);
    }

    @Override
    public final FutureRemindersList getFutureRemindersList() {
        return futureReminders;
    }

    @Override
    public void updateCommandHistory(Command command) {
        elisaCommandHistory.pushUndo(command);
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
        visualList.add(item);
        addToSeparateList(item);
        itemStorage.add(item);
    }

    /**
     * add given item into specified index
     * */

    public void addItem(ItemIndexWrapper wrapper) {
        visualList.addToIndex(wrapper.getVisual(), wrapper.getItem());
        addToSeparateList(wrapper);
        itemStorage.add(wrapper.getStorage(), wrapper.getItem());
    }

    /**
     * Helper function to add an item to it's respective list
     * @param item the item to be added into the lists
     */
    public void addToSeparateList(Item item) {
        if (item.hasTask()) {
            taskList.add(item);
        }

        if (item.hasEvent()) {
            eventList.add(item);
            calendarList.add(item);
        }

        if (item.hasReminder()) {
            reminderList.add(item);
            if (item.getReminder().get().getOccurrenceDateTime().isAfter(LocalDateTime.now())) {
                futureReminders.add(item);
            } else {
                activeReminders.add(item);
            }
        }
    }

    /**
     * add item to separate lists into given index
     * */

    public void addToSeparateList(ItemIndexWrapper wrapper) {
        if (wrapper.getTask() != -1) {
            taskList.addToIndex(wrapper.getTask(), wrapper.getItem());
        }

        if (wrapper.getEve() != -1) {
            eventList.addToIndex(wrapper.getEve(), wrapper.getItem());
        }

        if (wrapper.getRem() != -1) {
            reminderList.addToIndex(wrapper.getRem(), wrapper.getItem());
            futureReminders.add(wrapper.getFrem(), wrapper.getItem());
        }
    }

    @Override
    public ElisaCommandHistory getElisaCommandHistory() {
        return elisaCommandHistory;
    }

    @Override
    public JokeList getJokeList() {
        return jokeList;
    }

    public String getJoke() {
        return jokeList.getJoke();
    }

    /**
     * Remove an item from the current list.
     * @param index the item to be removed from the current list
     * @return the item that was removed
     */
    public Item removeItem(int index) {
        Item item = visualList.removeItemFromList(index);
        return removeItem(item);
    }

    /**
     * remove the given item from the list(s)
     * */

    public Item removeItem(Item item) {
        Item removedItem = visualList.removeItemFromList(item);
        if (visualList instanceof TaskList) {
            taskList.removeItemFromList(removedItem);
        } else if (visualList instanceof EventList) {
            eventList.removeItemFromList(removedItem);
        } else if (visualList instanceof ReminderList) {
            reminderList.removeItemFromList(removedItem);
        } else {
            // never reached here as there are only three variants for the visualList
        }
        activeReminders.remove(item);
        futureReminders.remove(item);
        return removedItem;
    }

    /**
     * Removes an item from a list. Used for edit command to remove the old item.
     * @param item the item to be removed from the list
     * @return the item that is removed.
     */
    private Item removeFromSeparateList(Item item) {
        visualList.remove(item);
        taskList.remove(item);
        eventList.remove(item);
        reminderList.remove(item);
        return item;
    }

    /**
     * Deletes an item from the program.
     * @param index the index of the item to be deleted.
     * @return the item that was deleted from the program
     */
    public Item deleteItem(int index) {
        Item item = visualList.removeItemFromList(index);
        itemStorage.remove(item);
        taskList.remove(item);
        eventList.remove(item);
        reminderList.remove(item);
        futureReminders.remove(item);
        activeReminders.remove(item);
        if (priorityMode.getValue()) {
            getNextTask();
        }
        return item;
    }

    /**
     * Deletes an item from the program.
     * @param item the item to be deleted.
     * @return the item that was deleted from the program
     */
    public Item deleteItem(Item item) {
        visualList.removeItemFromList(item);
        itemStorage.remove(item);
        taskList.removeItemFromList(item);
        eventList.removeItemFromList(item);
        reminderList.removeItemFromList(item);
        activeReminders.remove(item);
        futureReminders.remove(item);
        if (priorityMode.getValue()) {
            getNextTask();
        }
        return item;
    }

    public ItemIndexWrapper getIndices(int index) {
        Item item = visualList.get(index);
        return new ItemIndexWrapper(item, index, itemStorage.indexOf(item), taskList.indexOf(item),
                eventList.indexOf(item), reminderList.indexOf(item), futureReminders.indexOf(item));
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
            if (priorityMode.getValue()) {
                setVisualList(getNextTask());
                break;
            }
            setVisualList(taskList);
            break;
        case "E":
            setVisualList(eventList);
            break;
        case "R":
            setVisualList(reminderList);
            break;
        case "C":
            setVisualList(calendarList);
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
        if (index >= 0) {
            if (visualList.belongToList(newItem)) {
                visualList.setItem(index, newItem);
            } else {
                visualList.remove(index);
            }
        }

        if ((index = itemStorage.indexOf(item)) >= 0) {
            itemStorage.setItem(index, newItem);
        }

        if ((index = taskList.indexOf(item)) >= 0) {
            if (newItem.hasTask()) {
                taskList.setItem(index, newItem);
            } else {
                taskList.remove(index);
            }
        }

        if ((index = eventList.indexOf(item)) >= 0) {
            if (newItem.hasEvent()) {
                eventList.setItem(index, newItem);
            } else {
                eventList.remove(index);
            }
        }

        if ((index = reminderList.indexOf(item)) >= 0) {
            if (newItem.hasReminder()) {
                reminderList.setItem(index, newItem);
            } else {
                reminderList.remove(index);
            }

            //Old reminder must be in active since it already rang.
            //Find the old reminder from activeReminders and remove it.
            activeReminders.remove(item);
            //Put the new reminder in futureReminders if it will be rung later.
            futureReminders.add(newItem);
        }

        if (priorityMode.getValue()) {
            sortedTask.remove(item);
            sortedTask.offer(newItem);
            visualList = getNextTask();
        }
    }

    /**
     * Edits an item with another item.
     * @param oldItem the item to be edited
     * @param newItem the edited item
     * @return the edited item
     */
    public Item editItem(Item oldItem, Item newItem) {
        replaceItem(oldItem, newItem);
        addToSeparateList(newItem);
        return newItem;
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

    @Override
    public void setVisualizeList(VisualizeList list) {
        this.visualList = list;
    }

    /**
     * Clears the storage for the current ELISA run.
     */
    public void clear() {
        setItemStorage(new ItemStorage());
        emptyLists();
        this.visualList = taskList;
    }

    /**
     * Clears the 3 lists for re-populating
     * */
    public void emptyLists() {
        this.taskList.clear();
        this.eventList.clear();
        this.reminderList.clear();
    }

    /**
     * Sort the current visual list.
     */
    public void sort() {
        this.visualList = visualList.sort();
    }

    /**
     * Sorts the current visual list based on a comparator.
     * @param comparator the comparator to sort the current list by.
     */
    public void sort(Comparator<Item> comparator) {
        VisualizeList tempList = visualList.deepCopy();
        tempList.sort(comparator);
        this.visualList = tempList;
    }

    /**
     * Checks if the item storage already contains this item.
     * @param item to check
     * @return true if the item storage contains this item, false otherwise
     */
    public boolean hasItem(Item item) {
        return itemStorage.contains(item);
    }

    /**
     * Enable and disable the priority mode
     * @return a boolean value. If true, means priority mode is on, else returns false.
     * @throws IllegalListException if the visualList is not a task list.
     */
    public boolean togglePriorityMode() throws IllegalListException {
        if (!(visualList instanceof TaskList)) {
            throw new IllegalListException();
        }

        if (priorityMode.getValue()) {
            toggleOffPriorityMode();
        } else {
            toggleOnPriorityMode();
        }
        return priorityMode.getValue();
    }

    /**
     * Schedule a timer to off the priority mode.
     * @param localDateTime the time at which the priority mode should be turned off.
     */
    public void scheduleOffPriorityMode(LocalDateTime localDateTime) {
        this.timer = new Timer();
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        Date date = Date.from(zdt.toInstant());
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                systemToggle = true;
                toggleOffPriorityMode();
            }
        }, date);
    }

    private VisualizeList getNextTask() {
        TaskList result = new TaskList();

        if (sortedTask.peek().getTask().get().isComplete()) {
            systemToggle = true;
            toggleOffPriorityMode();
            return visualList;
        }

        result.add(sortedTask.peek());
        return result;
    }

    /**
     * Method to close the priority mode thread.
     */
    public void offPriorityMode() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Turns off the priority mode.
     */
    private void toggleOffPriorityMode() {
        offPriorityMode();

        this.sortedTask = null;
        if (visualList instanceof TaskList) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    visualList.clear();
                    for (Item item : taskList) {
                        visualList.add(item);
                    }
                }
            });
        }
        priorityMode.setValue(false);
    }

    /**
     * Turns on the priority mode.
     */
    private void toggleOnPriorityMode() {
        systemToggle = false;
        this.priorityMode.setValue(true);

        sortedTask = new PriorityQueue<Item>((item1, item2) -> {
            int result;
            if ((result = TaskList.COMPARATOR.compare(item1, item2)) != 0) {
                return result;
            } else {
                int index1 = taskList.indexOf(item1);
                int index2 = taskList.indexOf(item2);
                return index1 > index2 ? 1 : -1;
            }
        });

        for (int i = 0; i < taskList.size(); i++) {
            Item item = taskList.get(i);
            if (!item.getTask().get().isComplete()) {
                sortedTask.add(item);
            }
        }
        if (sortedTask.size() == 0) {
            priorityMode.setValue(false);
        } else {
            this.visualList = getNextTask();
        }
    }

    /**
     * Mark an item with a task as done.
     * @param index the index of the item to be marked as done.
     * @return the item that is marked as done.
     * @throws IllegalListException if the operation is not done on a task list.
     */
    public Item markComplete(int index) throws IllegalListException {
        Item item;
        Item newItem;
        if (!(visualList instanceof TaskList)) {
            throw new IllegalListException();
        } else {
            item = visualList.get(index);
            Task task = item.getTask().get();
            Task newTask = task.markComplete();
            newItem = item.changeTask(newTask);
            editItem(item, newItem);
        }
        return newItem;
    }

    /**
     * mark a given task as not completed
     * */

    public Item markIncomplete(int index) throws IllegalListException {
        Item item;
        Item newItem;
        if (!(visualList instanceof TaskList)) {
            throw new IllegalListException();
        } else {
            item = visualList.get(index);
            Task task = item.getTask().get();
            Task newTask = task.markIncomplete();
            newItem = item.changeTask(newTask);
            editItem(item, newItem);
        }

        return newItem;
    }

    public EventList getEventList() {
        return this.eventList;
    }

    public Item getItem(int index) {
        return this.visualList.get(index);
    }

    public Item getLatestOccurredReminder() throws NoSuchElementException {
        return activeReminders.getLatestOccurredReminder();
    }

    public SimpleBooleanProperty getPriorityMode() {
        return this.priorityMode;
    }

    public boolean isSystemToggle() {
        return systemToggle;
    }
}
