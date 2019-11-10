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

import javafx.beans.property.SimpleBooleanProperty;
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
    private PriorityExitStatus priorityExitStatus = null;
    private PriorityQueue<Item> sortedTask = null;
    private SimpleBooleanProperty focusMode = new SimpleBooleanProperty(false);

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

        repopulateLists();
    }


    /**
     * Repopulate item lists from storage
     * */

    public void repopulateLists() {
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

    @Override
    public final FutureRemindersList getFutureRemindersList() {
        return futureReminders;
    }

    @Override
    public void updateCommandHistory(Command command) {
        elisaCommandHistory.pushUndo(command);
    }

    //Deals with Storage
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

    @Override
    public void setItemStorage(ItemStorage itemStorage) {
        this.itemStorage = itemStorage;
    }

    @Override
    public ItemStorage getItemStorage() {
        return itemStorage;
    }
    //Above deals with storage

    //Edits state of model
    /**
     * Adds an item to the respective list. All items will be added to the central list.
     * It will also be added to the respective list depending on whether it is a task, event or a reminder.
     * @param item the item to be added to the program
     */
    public void addItem (Item item) {
        addToSeparateList(item);
        itemStorage.add(item);
    }

    /**
     * add given item into specified index
     * */

    public void addItem(ItemIndexWrapper wrapper) {
        if (visualList.belongToList(wrapper.getItem())) {
            visualList.addToIndex(wrapper.getVisual(), wrapper.getItem());
        }
        addToSeparateList(wrapper);
        itemStorage.add(wrapper.getStorage(), wrapper.getItem());
    }

    /**
     * Helper function to add an item to it's respective list
     * @param item the item to be added into the lists
     */
    public void addToSeparateList(Item item) {
        if (visualList.belongToList(item)) {
            visualList.add(item);
        }

        if (item.hasTask()) {
            taskList.add(item);
            if (priorityMode.getValue()) {
                sortedTask.offer(item);
                getNextTask();
            }
        }

        if (item.hasEvent()) {
            eventList.add(item);
            calendarList.add(item);
        }

        if (item.hasReminder()) {
            reminderList.add(item);
            if (item.getReminder().get().getOccurrenceDateTime().isAfter(LocalDateTime.now())) {
                futureReminders.add(item);
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
            calendarList.addToIndex(wrapper.getEve(), wrapper.getItem());
        }

        if (wrapper.getRem() != -1) {
            reminderList.addToIndex(wrapper.getRem(), wrapper.getItem());
        }

        if (wrapper.getFrem() != -1) {
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
     * Deletes an item from the program.
     * @param index the index of the item to be deleted.
     * @return the item that was deleted from the program
     */
    public Item deleteItem(int index) {
        Item item = visualList.remove(index);
        return deleteItem(item);
    }

    /**
     * Deletes an item from the program.
     * @param item the item to be deleted.
     * @return the item that was deleted from the program
     */
    public Item deleteItem(Item item) {
        visualList.remove(item);
        taskList.remove(item);
        eventList.remove(item);
        calendarList.remove(item);
        reminderList.remove(item);
        futureReminders.remove(item);
        activeReminders.remove(item);
        itemStorage.remove(item);
        if (priorityMode.getValue() && sortedTask != null) {
            sortedTask.remove(item);
            getNextTask();
        }
        return item;
    }

    public ItemIndexWrapper getIndices(int index) {
        Item item = visualList.get(index);
        return new ItemIndexWrapper(item, index, itemStorage.indexOf(item), taskList.indexOf(item),
                eventList.indexOf(item), reminderList.indexOf(item),
                futureReminders.indexOf(item), activeReminders.indexOf(item));
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
            if (priorityMode.getValue()) {
                getNextTask();
            }
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
                calendarList.setItem(index, newItem);
            } else {
                eventList.remove(index);
                calendarList.remove(index);
            }
        }

        if ((index = calendarList.indexOf(item)) >= 0) {
            if (newItem.hasEvent()) {
                calendarList.setItem(index, newItem);
            } else {
                calendarList.remove(index);
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
            if (newItem.hasTask()) {
                sortedTask.offer(newItem);
            }
            getNextTask();
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
        taskList.clear();
        eventList.clear();
        reminderList.clear();
        calendarList.clear();
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
                priorityExitStatus = PriorityExitStatus.PRIORITY_TIMEOUT;
                toggleOffPriorityMode();
            }
        }, date);
    }

    private void getNextTask() {
        Item head = sortedTask.peek();
        if (sortedTask.isEmpty() || head.getTask().get().isComplete()) {
            systemToggle = true;
            priorityExitStatus = PriorityExitStatus.ALL_TASK_COMPLETED;
            toggleOffPriorityMode();
            return;
        }

        if (visualList instanceof TaskList) {
            TaskList result = new TaskList();
            result.add(head);
            visualList = result;
        }
    }

    /**
     * Method to close the priority mode thread.
     */
    public void closePriorityModeThread() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Turns off the priority mode.
     */
    private void toggleOffPriorityMode() {
        closePriorityModeThread();

        sortedTask = null;
        focusMode.set(false);
        if (visualList instanceof TaskList) {
            this.visualList = taskList;
        }
        priorityMode.setValue(false);
    }

    /**
     * Turns on the priority mode.
     */
    private void toggleOnPriorityMode() {
        systemToggle = false;
        priorityExitStatus = null;
        priorityMode.setValue(true);

        populateQueue();

        // should not be null as it is populated by the previous method
        requireNonNull(sortedTask);
        if (sortedTask.isEmpty()) {
            priorityExitStatus = PriorityExitStatus.ALL_TASK_COMPLETED;
            priorityMode.setValue(false);
        } else {
            getNextTask();
        }
    }

    /**
     * Helper method to create the priority queue and fill it up.
     */
    private void populateQueue() {
        sortedTask = new PriorityQueue<Item>((item1, item2) -> {
            int result;
            if ((result = TaskList.COMPARATOR.compare(item1, item2)) != 0) {
                return result;
            } else {
                int index1 = taskList.indexOf(item1);
                int index2 = taskList.indexOf(item2);
                return Integer.compare(index1, index2);
            }
        });

        sortedTask.addAll(taskList.filtered(x -> !x.getTask().get().isComplete()));
    }

    /**
     * Mark an item with a task as done or not done.
     * @param index the index of the item to be marked as done or not done
     * @param status the status of the item. True means that it is done and false mean it is not done.
     * @return the item that is marked as done or not done.
     * @throws IllegalListException if the operation is not done on a task list.
     */
    public Item markComplete(int index, boolean status) throws IllegalListException {
        if (!(visualList instanceof TaskList)) {
            throw new IllegalListException();
        }

        Item item = visualList.get(index);
        Task task = item.getTask().get();
        Task newTask = status ? task.markComplete() : task.markIncomplete();
        Item newItem = item.changeTask(newTask);
        editItem(item, newItem);
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

    public PriorityExitStatus getExitStatus() {
        return priorityExitStatus;
    }

    public void toggleOnFocusMode() {
        focusMode.set(true);
    }

    public boolean isFocusMode() {
        return focusMode.get();
    }

    public SimpleBooleanProperty getFocusMode() {
        return focusMode;
    }
}
