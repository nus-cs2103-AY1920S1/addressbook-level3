package seedu.elisa.model.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.elisa.commons.core.item.Item;
import seedu.elisa.commons.core.item.ItemDescription;
import seedu.elisa.commons.core.item.Task;
import seedu.elisa.testutil.TypicalItems;

public class TaskListTest {
    private Item validTask = TypicalItems.ITEM_WITH_TASK;
    private Item invalidTask = TypicalItems.ITEM_WITH_EVENT;
    private TaskList testList = new TaskList();

    @Test
    public void add_validTask_returnTrue() {
        assertTrue(testList.add(validTask));
    }

    @Test
    public void add_invalidTask_returnFalse() {
        assertFalse(testList.add(invalidTask));
    }

    @Test
    public void add_repeatedItem_returnFalse() {
        testList.add(validTask);
        assertFalse(testList.add(validTask));
    }

    @Test
    public void deepCopyTest () {
        testList.add(validTask);
        TaskList tempList = (TaskList) testList.deepCopy();
        assertEquals(testList, tempList);
        assertFalse(testList == tempList);
        assertEquals(validTask, tempList.get(0));
        assertFalse(validTask == tempList.get(0));
    }

    @Test
    public void sort_returnNewList () {
        testList.add(validTask);
        assertFalse(testList == testList.sort());
        assertEquals(testList, testList.sort());
    }

    private Item generateTask(String value) {
        Item template = new Item.ItemBuilder().setTask(new Task(false))
                .setItemDescription(new ItemDescription(value))
                .build();
        return template;
    }

    @Test
    public void find_randomTaskList_findRelevantTasks() {
        String valueString = Double.toString(Math.random());
        testList.add(generateTask("CS2101"));
        testList.add(generateTask("CS2101 PPP"));
        testList.add(generateTask("CS2103 PPP"));
        testList.add(generateTask("CS2103 demo"));
        testList.add(generateTask("CS2105 assignment"));
        testList.add(generateTask("CS2101 pitch"));
        assertEquals(3, testList.find(new String[] {"CS2101"}).size());
    }
}
