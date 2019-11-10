package seedu.elisa.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.elisa.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.elisa.commons.core.GuiSettings;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.commons.core.item.ItemDescription;
import seedu.elisa.commons.core.item.Priority;
import seedu.elisa.commons.core.item.Task;
import seedu.elisa.commons.exceptions.IllegalValueException;
import seedu.elisa.model.exceptions.IllegalListException;
import seedu.elisa.model.item.EventList;
import seedu.elisa.model.item.ReminderList;
import seedu.elisa.model.item.TaskList;
import seedu.elisa.testutil.TypicalItems;

public class ItemModelManagerTest {
    private ItemModelManager testModel = new ItemModelManager(new ItemStorage(),
            new UserPrefs(), new ElisaCommandHistoryManager());
    private Item task = TypicalItems.ITEM_WITH_TASK;
    private Item event = TypicalItems.ITEM_WITH_EVENT;
    private Item reminder = TypicalItems.ITEM_WITH_REMINDER;
    private Item.ItemBuilder template = new Item.ItemBuilder().setItemDescription(new ItemDescription("test"));
    private Item validItem = TypicalItems.ITEM_WITH_ALL;

    @Test
    public void testConstructor() {
        assertEquals(new ItemStorage(), testModel.getItemStorage());
        assertEquals(new UserPrefs(), testModel.getUserPrefs());
        assertEquals(new ElisaCommandHistoryManager(), testModel.getElisaCommandHistory());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> testModel.setUserPrefs(null));
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> testModel.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        testModel.setGuiSettings(guiSettings);
        assertEquals(guiSettings, testModel.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> testModel.setItemStorageFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        testModel.setItemStorageFilePath(path);
        assertEquals(path, testModel.getItemStorageFilePath());
    }

    @Test
    public void setVisualList_taskList_changeToTaskList() throws IllegalValueException {
        testModel.setVisualList("T");
        assertTrue(testModel.getVisualList() instanceof TaskList);
    }

    @Test
    public void setVisualList_eventList_changeToEventList() throws IllegalValueException {
        testModel.setVisualList("E");
        assertTrue(testModel.getVisualList() instanceof EventList);
    }

    @Test
    public void setVisualList_reminderList_changeToReminderList() throws IllegalValueException {
        testModel.setVisualList("R");
        assertTrue(testModel.getVisualList() instanceof ReminderList);
    }

    @Test
    public void setVisualList_invalidValue_throwIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> testModel.setVisualList("B"));
    }

    @Test
    public void addItem_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> testModel.addItem((Item) null));
    }

    @Test
    public void addToSeparateList_validTask_addedToTaskList() {
        testModel.addToSeparateList(task);
        try {
            testModel.setVisualList("T");
        } catch (IllegalValueException e) {
            // should not reach this loop as it is already tested above
            fail(e);
        }
        assertEquals(1, testModel.getVisualList().size());
    }

    @Test
    public void addToSeparateList_validEvent_addedToEventList() {
        testModel.addToSeparateList(event);
        try {
            testModel.setVisualList("E");
        } catch (IllegalValueException e) {
            // should not reach this loop as it is already tested above
            fail(e);
        }
        assertEquals(1, testModel.getVisualList().size());
    }

    @Test
    public void addToSeparateList_validReminder_addedToReminderList() {
        testModel.addToSeparateList(reminder);
        try {
            testModel.setVisualList("R");
        } catch (IllegalValueException e) {
            // should not reach this loop as it is already tested above
            fail(e);
        }
        assertEquals(1, testModel.getVisualList().size());
    }

    @Test
    public void addToSeparateList_itemDoesNotBelongInVisualList_visualListEmpty() {
        try {
            testModel.setVisualList("T");
        } catch (IllegalValueException e) {
            // should not reach this loop as it is already tested above
            fail(e);
        }
        testModel.addItem(reminder);
        assertEquals(0, testModel.getVisualList().size());
    }

    @Test
    public void addToSeparateList_validItem_addedToAllList() {
        testModel.addToSeparateList(validItem);
        try {
            assertEquals(1, testModel.getVisualList().size());
            testModel.setVisualList("T");
            assertEquals(1, testModel.getVisualList().size());
            testModel.setVisualList("E");
            assertEquals(1, testModel.getVisualList().size());
            testModel.setVisualList("R");
            assertEquals(1, testModel.getVisualList().size());
            testModel.setVisualList("C");
            assertEquals(1, testModel.getVisualList().size());
        } catch (IllegalValueException e) {
            // should not reach this loop as it is already tested above
            fail(e);
        }
    }

    @Test
    public void deleteItem_validIndex_deletedFromAllList() {
        testModel.addItem(validItem);
        assertEquals(validItem, testModel.deleteItem(0));
        assertEquals(0, testModel.getVisualList().size());
        try {
            testModel.setVisualList("T");
            assertEquals(0, testModel.getVisualList().size());
            testModel.setVisualList("E");
            assertEquals(0, testModel.getVisualList().size());
            testModel.setVisualList("R");
            assertEquals(0, testModel.getVisualList().size());
            testModel.setVisualList("C");
            assertEquals(0, testModel.getVisualList().size());
        } catch (IllegalValueException e) {
            // should not reach this loop as it is already tested above
            fail(e);
        }
    }

    @Test
    public void replaceItem_validItem_itemReplaced() {
        testModel.addItem(validItem);
        Item newItem = template.setTask(new Task(true)).build();
        testModel.replaceItem(validItem, newItem);
        assertTrue(testModel.hasItem(newItem));
    }

    @Test
    public void togglePriorityMode_priorityModeCurrentlyOff_priorityModeOn() {
        testModel.addItem(task);
        try {
            assertTrue(testModel.togglePriorityMode());
        } catch (IllegalListException e) {
            fail(e);
        }
    }

    @Test
    public void togglePriorityMode_priorityModeCurrentlyOn_priorityModeOff() {
        testModel.addItem(task);
        try {
            testModel.togglePriorityMode();
            assertFalse(testModel.togglePriorityMode());
        } catch (IllegalListException e) {
            fail(e);
        }
    }

    @Test
    public void togglePriorityMode_noTaskInList_priorityModeOff() {
        try {
            testModel.togglePriorityMode();
            assertFalse(testModel.togglePriorityMode());
        } catch (IllegalListException e) {
            fail(e);
        }
    }

    @Test
    public void togglePriorityMode_noIncompleteTaskInList_priorityModeOff() {
        testModel.addItem(template.setTask(new Task(true)).build());
        try {
            testModel.togglePriorityMode();
            assertFalse(testModel.togglePriorityMode());
        } catch (IllegalListException e) {
            fail(e);
        }
    }

    @Test
    public void togglePriorityMode_onIncorrectList_throwsIllegalListException() {
        try {
            testModel.setVisualList("R");
            assertThrows(IllegalListException.class, () -> testModel.togglePriorityMode());
        } catch (IllegalValueException e) {
            fail(e);
        }
    }

    @Test
    public void markComplete_incompleteTask_markTaskAsComplete() {
        testModel.addItem(task);
        try {
            testModel.markComplete(0, true);
            assertTrue(testModel.getItem(0).getTask().get().isComplete());
        } catch (IllegalListException e) {
            fail(e);
        }
    }

    @Test
    public void done_inPriorityMode_finishAllTask() {
        testModel.addItem(task);
        try {
            testModel.togglePriorityMode();
            testModel.markComplete(0, true);
            assertFalse(testModel.getPriorityMode().getValue());
        } catch (IllegalListException e) {
            fail(e);
        }
    }

    @Test
    public void delete_inPriorityMode_finishAllTask() {
        testModel.addItem(task);
        try {
            testModel.togglePriorityMode();
            testModel.deleteItem(0);
            assertFalse(testModel.getPriorityMode().getValue());
        } catch (IllegalListException e) {
            fail(e);
        }
    }

    @Test
    public void done_inPriorityMode_getNextTask() {
        testModel.addItem(task);
        Item lowPriority = new Item.ItemBuilder()
                .setItemPriority(Priority.LOW)
                .setItemDescription(new ItemDescription("Low priority"))
                .setTask(new Task(false))
                .build();
        testModel.addItem(lowPriority);
        try {
            testModel.togglePriorityMode();
            testModel.markComplete(0, true);
            assertEquals(lowPriority, testModel.getVisualList().get(0));
        } catch (IllegalListException e) {
            fail(e);
        }
    }

    @Test
    public void delete_inPriorityMode_getNextTask() {
        testModel.addItem(task);
        Item lowPriority = new Item.ItemBuilder()
                .setItemPriority(Priority.LOW)
                .setItemDescription(new ItemDescription("Low priority"))
                .setTask(new Task(false))
                .build();
        testModel.addItem(lowPriority);
        try {
            testModel.togglePriorityMode();
            testModel.deleteItem(0);
            assertEquals(lowPriority, testModel.getVisualList().get(0));
        } catch (IllegalListException e) {
            fail(e);
        }
    }
}
