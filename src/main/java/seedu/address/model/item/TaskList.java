package seedu.address.model.item;

/**
 * Object class to store all the items that are task within the program
 */
public class TaskList extends ItemList {
    public TaskList() {
        super();
    }

    /**
     * Sort the items in the task list. The items are first sorted by whether they are
     * done or not and then by their priority.
     * @return an ItemList of all the items sorted
     */
    public ItemList sort() {
        TaskList tl = new TaskList();
        for (Item item: list) {
            tl.add(item);
        }

        tl.list.sort((item1, item2) -> {
            Task task1 = item1.getTask();
            Task task2 = item2.getTask();
            if (task1.isDone() && !task2.isDone()) {
                return 1;
            } else if (!task1.isDone() && task2.isDone()) {
                return -1;
            } else {
                return task1.getPriority().compareTo(task2.getPriority());
            }
        });

        return tl;
    }

    /**
     * Finds a substring within the description of an item.
     * @param searchString a string to be search for within the description of an item
     * @return a new TaskList containing only the items that have the search string in their description
     */
    public ItemList find(String searchString) {
        TaskList tl = new TaskList();
        return find(searchString, tl);
    }
}
