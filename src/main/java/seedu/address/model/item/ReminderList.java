package seedu.address.model.item;

import seedu.address.commons.core.item.Item;

/**
 * Object class to store all the items that are reminders within the program
 */
public class ReminderList extends VisualizeList {

    public ReminderList() {
        super();
    }

    /**
     * Sorts the Reminders by the date.
     * @return a new ReminderList with the reminders within sorted.
     */
    public VisualizeList sort() {
        ReminderList rl = new ReminderList();
        for (Item i : list) {
            rl.add(i);
        }
        rl.list.sort((item1, item2) -> item1.getReminder().get().getDateTime()
                .compareTo(item2.getReminder().get().getDateTime()));
        return rl;
    }

    /**
     * Finds a substring within the description of an item.
     * @param searchString a string to be search for within the description of an item
     * @return a new ReminderList only containing the items that have the search string in their description
     */
    public VisualizeList find(String searchString) {
        ReminderList rl = new ReminderList();
        return super.find(searchString, rl);
    }
}
