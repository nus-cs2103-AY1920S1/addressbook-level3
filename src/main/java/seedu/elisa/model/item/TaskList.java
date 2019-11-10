package seedu.elisa.model.item;

import java.util.Comparator;
import java.util.List;

import seedu.elisa.commons.core.item.Item;
import seedu.elisa.commons.core.item.Task;

/**
 * Object class to store all the items that are task within the program
 */
public class TaskList extends VisualizeList {
    public static final Comparator<Item> COMPARATOR = (item1, item2) -> {
        Task task1 = item1.getTask().get();
        Task task2 = item2.getTask().get();
        if (task1.isComplete() && !task2.isComplete()) {
            return 1;
        } else if (!task1.isComplete() && task2.isComplete()) {
            return -1;
        } else {
            return item1.getPriority().compareTo(item2.getPriority());
        }
    };

    public TaskList() {
        super();
    }

    public TaskList(List<Item> list) {
        super(list);
    }

    /**
     * Sort the items in the task list. The items are first sorted by whether they are
     * done or not and then by their priority.
     * @return an VisualizeList of all the items sorted
     */
    public VisualizeList sort() {
        TaskList tl = new TaskList(list);
        tl.sort(COMPARATOR);
        return tl;
    }

    /**
     * Finds a substring within the description of an item.
     * @param searchString a string to be search for within the description of an item
     * @return a new TaskList containing only the items that have the search string in their description
     */
    public VisualizeList find(String[] searchString) {
        return find(searchString, new TaskList());
    }

    @Override
    public VisualizeList deepCopy() {
        return super.deepCopy(new TaskList());
    }

    public boolean belongToList(Item item) {
        return item.hasTask();
    }
}
